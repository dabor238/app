package multidoctores.multidoctores;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;


public class Chat extends Activity {
    TextView resultadoTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        resultadoTextView = (TextView)findViewById(R.id.textViewResultado);

        String Mail = "dabor238@gmail.com";
        String BASE_URL = "https://www.multidoctores.com";
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();






        MyApiEndpointInterface apiService = retrofit.create(MyApiEndpointInterface.class);
        Call<Usuario> call = apiService.getFicha(Mail);

        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Response<Usuario> response, Retrofit retrofit) {
                int statusCode = response.code();
                Usuario user = response.body();
                String hola = user.getTelefono().toString();
                resultadoTextView.setText(hola);
                Toast.makeText(getApplicationContext(), user.getNombre().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Throwable t) {
                // Log error here since request failed
                Log.w("myApp", "no network");
            }
        });

        Button btn_ir = (Button)findViewById(R.id.button);
        btn_ir.setOnClickListener(new ClickOnClickListener());

     //  Call<Usuarios> call2 = apiService.getVarios();

         /*call2.enqueue(new Callback<Usuarios>() {
            @Override
            public void onResponse(Response<Usuarios> response, Retrofit retrofit) {
                int statusCode = response.code();
                Usuarios user = response.body();
                String algo = "algo";

            }

            @Override
            public void onFailure(Throwable t) {
                // Log error here since request failed
                Log.w("myApp", "no network");
            }
        });*/




       /*MyApiEndpointInterface apiService =
                retrofit.create(MyApiEndpointInterface.class);

        Usuario usuario = (Usuario) apiService.getUser();

        String username = "sarahjean";*/
       /* Call<Usuario> call = apiService.getUser();
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Response<Usuario> response, Retrofit retrofit) {
                int statusCode = response.code();
                Usuario user = response.body();
            }

            @Override
            public void onFailure(Throwable t) {
                // Log error here since request failed
                Log.w("myApp", "no network");
            }
        });*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class ClickOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v){


            Intent i = new Intent(Chat.this, Inicio.class);
            startActivity(i);
        }
    }
}
