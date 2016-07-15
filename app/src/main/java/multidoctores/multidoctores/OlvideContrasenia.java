package multidoctores.multidoctores;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class OlvideContrasenia extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olvide_contrasenia);

        Button btn_recuperar = (Button)findViewById(R.id.btn_recuperar);
        btn_recuperar.setOnClickListener(new EnviarOnClickListener());
    }

    class EnviarOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v){
            EditText e = (EditText) findViewById(R.id.user);
            String mail = e.getText().toString();

            String BASE_URL = "https://www.multidoctores.com";
            final Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            MyApiEndpointInterface apiService = retrofit.create(MyApiEndpointInterface.class);
            Call<Boolean> call = apiService.RecuperarUser(mail);

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


            Toast.makeText(getApplicationContext(),"Se ha enviado un correo de recuperación", Toast.LENGTH_LONG).show();
            Intent i = new Intent(OlvideContrasenia.this, Inicio.class);
            startActivity(i);
        }
    }
}
