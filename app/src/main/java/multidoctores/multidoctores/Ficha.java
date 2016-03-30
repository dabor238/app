package multidoctores.multidoctores;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class Ficha extends AppCompatActivity {

    SessionManagement session;
    Button btn_guardarDatos;
    String Mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha);
        btn_guardarDatos = (Button)findViewById(R.id.guardarFicha);
        btn_guardarDatos.setOnClickListener(new GuardarClickListener());

        session = new SessionManagement(getApplicationContext());
        session.checkLogin();

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
        Mail = user.get(SessionManagement.KEY_EMAIL);


        String BASE_URL = "http://www.multidoctores.com";
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        MyApiEndpointInterface apiService = retrofit.create(MyApiEndpointInterface.class);


        Call<Usuario> call = apiService.getFicha(Mail);

        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Response<Usuario> response, Retrofit retrofit) {

                Usuario userPres = response.body();

                TextView Nombre = (TextView) findViewById(R.id.textTitulo);
                Nombre.setText("Ficha médica de " + userPres.getNombre());

                TextView Telefono = (TextView) findViewById(R.id.Telefono);
                Telefono.setText(userPres.getTelefono());

                TextView Alergias = (TextView) findViewById(R.id.Alergias);
                Alergias.setText(userPres.getAlergias());

                TextView Enfermedades = (TextView) findViewById(R.id.Enfermedades);
                Enfermedades.setText(userPres.getEnfermedades());

                Spinner genero = (Spinner) findViewById(R.id.Sexo);
                if(userPres.isSexo()){
                genero.setSelection(0);
                }else{
                    genero.setSelection(1);
                }

                Spinner dia = (Spinner) findViewById(R.id.spinner);
                String compareDia = userPres.getDia();
                ArrayAdapter<CharSequence> adapterDia = ArrayAdapter.createFromResource(Ficha.this, R.array.dia, android.R.layout.simple_spinner_item);
                adapterDia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dia.setAdapter(adapterDia);
                if (!compareDia.equals(null)) {
                    int spinnerPosition = adapterDia.getPosition(compareDia);
                    dia.setSelection(spinnerPosition);
                }




                Spinner mes = (Spinner)findViewById(R.id.spinner2);


                String compareMes = userPres.getMes();
                ArrayAdapter<CharSequence> adapterMes = ArrayAdapter.createFromResource(Ficha.this, R.array.mes, android.R.layout.simple_spinner_item);
                adapterMes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mes.setAdapter(adapterMes);
                if (!compareMes.equals(null)) {
                    int spinnerPosition = adapterMes.getPosition(compareMes);
                    mes.setSelection(spinnerPosition);
                }


                Spinner anio = (Spinner)findViewById(R.id.spinner3);


                String compareValue = userPres.getAno();
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(Ficha.this, R.array.anio, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                anio.setAdapter(adapter);
                if (!compareValue.equals(null)) {
                    int spinnerPosition = adapter.getPosition(compareValue);
                    anio.setSelection(spinnerPosition);
                }



            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getApplicationContext(), "Problemas al conectar, intente de nuevo.", Toast.LENGTH_SHORT).show();


            }


        });
    }



    class GuardarClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v){
            EditText textAlergia = (EditText)findViewById(R.id.Alergias);
            String alergia = textAlergia.getText().toString();
            EditText textEnfermedad = (EditText)findViewById(R.id.Enfermedades);
            String enfermedad = textEnfermedad.getText().toString();
            EditText textCelular = (EditText)findViewById(R.id.Telefono);
            String celular = textCelular.getText().toString();

            Spinner textSexo = (Spinner)findViewById(R.id.Sexo);

            String genero = textSexo.getSelectedItem().toString();

            Spinner SpinDia = (Spinner)findViewById(R.id.spinner);

            String dia = SpinDia.getSelectedItem().toString();

            Spinner SpinMes = (Spinner)findViewById(R.id.spinner2);

            String mes = SpinMes.getSelectedItem().toString();

            Spinner SpinAnio = (Spinner)findViewById(R.id.spinner3);

            String anio = SpinAnio.getSelectedItem().toString();

            String pais = "256";




            String usuario = Mail;

            String BASE_URL = "http://www.multidoctores.com";
            final Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            MyApiEndpointInterface apiService = retrofit.create(MyApiEndpointInterface.class);
            Call<Boolean> call = apiService.updateFicha(usuario,alergia,enfermedad, dia, mes, anio, genero, celular, pais);

            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Response<Boolean> response, Retrofit retrofit) {


                    Toast.makeText(getApplicationContext(), "Ficha Actualizada", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(Ficha.this, List.class);
                    startActivity(i);


                }

                @Override
                public void onFailure(Throwable t) {
                    Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrecto", Toast.LENGTH_LONG).show();
                }
            });


        }
    }
}
