package multidoctores.multidoctores;

import android.content.ClipData;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class List extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        int UserID = 9;


        Button btn_nuevaConsulta = (Button)findViewById(R.id.nuevaConsulta);
        btn_nuevaConsulta.setOnClickListener(new InicioClickListener());

        String BASE_URL = "http://www.multidoctores.com";
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        MyApiEndpointInterface apiService = retrofit.create(MyApiEndpointInterface.class);

        //new Callback<List<CustomObject>>() {
        Call<ItemChat[]> call = apiService.getChatList();

        call.enqueue(new Callback<ItemChat[]>() {
            @Override
            public void onResponse(Response<ItemChat[]> response, Retrofit retrofit) {

                ItemChat[] userPres = response.body();

                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.contenedor);

                for(ItemChat u: userPres){

                    LayoutInflater inflater = LayoutInflater.from(List.this);
                    View inflatedLayout = inflater.inflate(R.layout.chat_list, null, false);
                    TextView lbl = (TextView) inflatedLayout.findViewById(R.id.chatTitle);
                    lbl.setText(u.getIdChat());
                    linearLayout.addView(inflatedLayout);
                }



            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getApplicationContext(), "no conecta", Toast.LENGTH_SHORT).show();

            }
        });
    }


    class InicioClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v){


            Intent i = new Intent(List.this, Conversacion.class);
            startActivity(i);
        }
    }





}


