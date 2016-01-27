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


public class Registrar extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        TextView btn_ir = (TextView)findViewById(R.id.tengo);
        btn_ir.setOnClickListener(new ClickOnClickListener());

        Button btn_Reg = (Button)findViewById(R.id.btnReg);
        btn_Reg.setOnClickListener(new RegistroClickListener());



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

            String BASE_URL = "http://www.multidoctores.com";
            final Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            MyApiEndpointInterface apiService = retrofit.create(MyApiEndpointInterface.class);
            Call<Boolean> call = apiService.createUser(username,pass,email);

            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Response<Boolean> response, Retrofit retrofit) {
                    int statusCode = response.code();

                }

                @Override
                public void onFailure(Throwable t) {
                    // Log error here since request failed
                }
            });


        }
    }
}
