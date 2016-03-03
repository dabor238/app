package multidoctores.multidoctores;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class ChatHistoria extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_historia);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String chatId = extras.getString("idChat");
            Toast.makeText(ChatHistoria.this, chatId, Toast.LENGTH_SHORT).show();

            String BASE_URL = "http://www.multidoctores.com";
            final Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


            MyApiEndpointInterface apiService = retrofit.create(MyApiEndpointInterface.class);


            Call<itemHistoria[]> call = apiService.getChatHistoria(chatId);

            call.enqueue(new Callback<itemHistoria[]>() {
                @Override
                public void onResponse(Response<itemHistoria[]> response, Retrofit retrofit) {

                    itemHistoria[] userPres = response.body();

                    LinearLayout linearLayout = (LinearLayout) findViewById(R.id.content);

                    for (itemHistoria u : userPres) {

                        LayoutInflater inflater = LayoutInflater.from(ChatHistoria.this);

                        if(u.isEscribe()){
                            View inflatedLayout = inflater.inflate(R.layout.chat_item_rcv, null, false);
                            TextView lbl = (TextView) inflatedLayout.findViewById(R.id.lbl1);
                            lbl.setText(u.getMensaje());
                            linearLayout.addView(inflatedLayout);

                        }else{

                            View inflatedLayout = inflater.inflate(R.layout.chat_item_sent, null, false);
                            TextView lbl = (TextView) inflatedLayout.findViewById(R.id.lbl1);
                            lbl.setText(u.getMensaje());
                            linearLayout.addView(inflatedLayout);


                        }

                    }


                }

                @Override
                public void onFailure(Throwable t) {
                    Toast.makeText(getApplicationContext(), "no conecta", Toast.LENGTH_SHORT).show();

                }


            });

        }
    }
}
