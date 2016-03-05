package multidoctores.multidoctores;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class Inicio extends Activity {

    SessionManagement session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);





        TextView btn_ir = (TextView)findViewById(R.id.quiero);
        btn_ir.setOnClickListener(new RegistroOnClickListener());

        TextView btn_recuperar = (TextView)findViewById(R.id.recuperar);
        btn_recuperar.setOnClickListener(new RecuperarOnClickListener());
        Button btn_login = (Button)findViewById(R.id.btnLogin);
        btn_login.setOnClickListener(new LoginClickListener());


        session = new SessionManagement(getApplicationContext());





    }

    class LoginClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v){


            EditText campoUser = (EditText) findViewById(R.id.user);
            EditText campoClave = (EditText) findViewById(R.id.pwd);

            String usuario = campoUser.getText().toString();
            String clave = campoClave.getText().toString();

            String BASE_URL = "http://www.multidoctores.com";
            final Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            MyApiEndpointInterface apiService = retrofit.create(MyApiEndpointInterface.class);
            Call<itemAutentico> call = apiService.getAutenticar(usuario, clave);

            call.enqueue(new Callback<itemAutentico>() {
                @Override
                public void onResponse(Response<itemAutentico> response, Retrofit retrofit) {
                    itemAutentico userPres = response.body();
                    Toast.makeText(getApplicationContext(), userPres.getIdUsuario(), Toast.LENGTH_LONG).show();
                    if(userPres.getIdUsuario().equals(0)){

                        Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrecto", Toast.LENGTH_LONG).show();

                    }else{

                        String correo = userPres.getCorreo();
                        String id = userPres.getIdUsuario();
                        session.createLoginSession(id, correo);
                        Intent i = new Intent(Inicio.this, List.class);
                        startActivity(i);

                    }



                }

                @Override
                public void onFailure(Throwable t) {
                    Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrecto", Toast.LENGTH_LONG).show();
                }
            });


        }
    }

    class RegistroOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v){


            Intent i = new Intent(Inicio.this, Registrar.class);
            startActivity(i);
        }
    }

    class RecuperarOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v){


            Intent i = new Intent(Inicio.this, OlvideContrasenia.class);
            startActivity(i);
        }
    }

}
