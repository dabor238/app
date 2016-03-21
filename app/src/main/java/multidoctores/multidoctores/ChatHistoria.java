package multidoctores.multidoctores;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    SessionManagement session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_historia);


        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar2);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();

        ab.setDisplayShowTitleEnabled(true);
        ab.setDisplayShowHomeEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String chatId = extras.getString("Id");


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
                        if (!"Fin-del-chat".equals(u.getMensaje())){

                            if (u.isEscribe()) {
                                View inflatedLayout = inflater.inflate(R.layout.chat_item_rcv, null, false);
                                TextView lbl = (TextView) inflatedLayout.findViewById(R.id.lbl1);
                                lbl.setText(u.getMensaje());
                                linearLayout.addView(inflatedLayout);

                            } else {

                                View inflatedLayout = inflater.inflate(R.layout.chat_item_sent, null, false);
                                TextView lbl = (TextView) inflatedLayout.findViewById(R.id.lbl1);
                                lbl.setText(u.getMensaje());
                                linearLayout.addView(inflatedLayout);


                            }

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

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bar, menu);
        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                session.logoutUser();
                return true;

            case android.R.id.home:
                onBackPressed();
                return true;

          /*  case R.id.action_favorite:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...

                session.logoutUser();
                return true;*/

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
