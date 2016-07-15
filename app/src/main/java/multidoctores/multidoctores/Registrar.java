package multidoctores.multidoctores;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;


public class Registrar extends Activity {

    SessionManagement session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        TextView btn_ir = (TextView)findViewById(R.id.tengo);
        btn_ir.setOnClickListener(new ClickOnClickListener());

        Button btn_Reg = (Button)findViewById(R.id.btnReg);
        btn_Reg.setOnClickListener(new RegistroClickListener());


        session = new SessionManagement(getApplicationContext());

        if(session.isLoggedIn()){
            this.finish();
            Intent i = new Intent(Registrar.this, List.class);
            startActivity(i);

        }


    }



    @Override
    public void onStop() {
        super.onStop();

    }


    class ClickOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v){

            Intent i = new Intent(Registrar.this, Inicio.class);
            startActivity(i);
        }
    }

    class RegistroClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v){
            EditText mail = (EditText) findViewById(R.id.email);
            String email = mail.getText().toString();

            EditText name = (EditText) findViewById(R.id.user);

            String username = name.getText().toString();

            EditText pass1 = (EditText) findViewById(R.id.pwd);
            String pass = pass1.getText().toString();

            String BASE_URL = "https://www.multidoctores.com";
            final Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            MyApiEndpointInterface apiService = retrofit.create(MyApiEndpointInterface.class);
            Call<Boolean> call = apiService.createUser(username,pass,email);

            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Response<Boolean> response, Retrofit retrofit) {
                    boolean newU = response.body();

                    if(newU){
                        EditText mail = (EditText) findViewById(R.id.email);
                        String email = mail.getText().toString();
                        EditText pass1 = (EditText) findViewById(R.id.pwd);
                        String clave = pass1.getText().toString();

                        String BASE_URL = "https://www.multidoctores.com";
                        final Retrofit retrofit2 = new Retrofit.Builder()
                                .baseUrl(BASE_URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        MyApiEndpointInterface apiService = retrofit2.create(MyApiEndpointInterface.class);
                        Call<itemAutentico> call = apiService.getAutenticar(email, clave);

                        call.enqueue(new Callback<itemAutentico>() {
                            @Override
                            public void onResponse(Response<itemAutentico> response, Retrofit retrofit) {
                                itemAutentico userPres = response.body();

                                if (userPres.isEntra()) {

                                    String correo = userPres.getCorreo();
                                    String id = userPres.getIdUsuario();
                                    session.createLoginSession(id, correo);
                                    Intent i = new Intent(Registrar.this, List.class);
                                    startActivity(i);

                                } else {


                                    Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrecto", Toast.LENGTH_LONG).show();


                                }


                            }

                            @Override
                            public void onFailure(Throwable t) {
                                Toast.makeText(getApplicationContext(), "Problemas al iniciar sesión. Intente de nuevo", Toast.LENGTH_LONG).show();
                            }
                        });

                    }else{

                        Toast.makeText(getApplicationContext(), "Este correo ya está registrado.", Toast.LENGTH_LONG).show();


                    }





                }

                @Override
                public void onFailure(Throwable t) {
                    // Log error here since request failed
                    Toast.makeText(getApplicationContext(), "Correo ya registrado", Toast.LENGTH_SHORT).show();
                }
            });


        }
    }
}
