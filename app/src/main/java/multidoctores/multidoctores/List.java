package multidoctores.multidoctores;

import android.content.ClipData;
import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class List extends AppCompatActivity {

    SessionManagement session;
    Button btn_nuevaConsulta;
    boolean estado;
    String IdChat;
    String idUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);


        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar2);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayShowTitleEnabled(true);

        btn_nuevaConsulta = (Button)findViewById(R.id.nuevaConsulta);
        btn_nuevaConsulta.setOnClickListener(new InicioClickListener());
        session = new SessionManagement(getApplicationContext());
        session.checkLogin();

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        // name
         idUser = user.get(SessionManagement.KEY_NAME);

        // email
        String email = user.get(SessionManagement.KEY_EMAIL);
        Toast.makeText(getApplicationContext(), email, Toast.LENGTH_SHORT).show();


        String BASE_URL = "http://www.multidoctores.com";
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        MyApiEndpointInterface apiService = retrofit.create(MyApiEndpointInterface.class);

        //new Callback<List<CustomObject>>() {
        Call<ItemChat[]> call = apiService.getChatList(idUser);

        call.enqueue(new Callback<ItemChat[]>() {
            @Override
            public void onResponse(Response<ItemChat[]> response, Retrofit retrofit) {

                ItemChat[] userPres = response.body();

                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.contenedor);

                for (ItemChat u : userPres) {

                    LayoutInflater inflater = LayoutInflater.from(List.this);
                    View inflatedLayout = inflater.inflate(R.layout.chat_list, null, false);


                    String date = u.getFecha();
                    String[] parts = date.split("-");
                    String[] dia = parts[2].split("T");
                    String unido = dia[0] + "/" + parts[1] + "/" +parts[0];

                    TextView fecha = (TextView) inflatedLayout.findViewById(R.id.fecha);
                    fecha.setText(unido);

                    TextView titulo = (TextView) inflatedLayout.findViewById(R.id.mensajeChat);
                    titulo.setText(u.getTitulo());

                    TextView doctor = (TextView) inflatedLayout.findViewById(R.id.chatTitle);
                    doctor.setText(u.getDoctor());



                    LinearLayout lista = (LinearLayout) inflatedLayout.findViewById(R.id.v1);
                    lista.setId(Integer.parseInt(u.getIdChat()));
                    lista.setClickable(true);
                    lista.setOnClickListener(new clickChatHistoria());

                    LinearLayout lista2 = (LinearLayout) inflatedLayout.findViewById(R.id.v2);
                    lista2.setId(Integer.parseInt(u.getIdChat()));
                    lista2.setClickable(true);
                    lista2.setOnClickListener(new clickChatHistoria());

                    linearLayout.addView(inflatedLayout);


                }


            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getApplicationContext(), "no conecta", Toast.LENGTH_SHORT).show();

            }


        });





            Call<itemActivo> call2 = apiService.getActivo(idUser);

                call2.enqueue(new Callback<itemActivo>() {
                @Override
                public void onResponse(Response<itemActivo> response, Retrofit retrofit) {

                    itemActivo activo = response.body();
                    if(activo.isActivo()){
                        estado = true;
                        IdChat = activo.getIdChat();

                        btn_nuevaConsulta.setText("Chat activo");


                    }else{
                        estado = false;
                        IdChat = activo.getIdChat();
                        btn_nuevaConsulta.setText("Nueva consulta");
                    }


                }

                @Override
                public void onFailure(Throwable t) {
                    Toast.makeText(getApplicationContext(), "no trae chat activo", Toast.LENGTH_SHORT).show();

                }



            });

    }



    public class clickChatHistoria implements View.OnClickListener {

        @Override
        public void onClick(View v) {


            LinearLayout Linear = ((LinearLayout)v);
            String Id = String.valueOf(Linear.getId());

            Intent i = new Intent(getApplicationContext(), ChatHistoria.class);
            i.putExtra("Id",Id);
            startActivity(i);


        }

    }




    class InicioClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v){



            Intent i = new Intent(List.this, Conversacion.class);
          /*  i.putExtra("estado",estado);
            i.putExtra("IdChat",IdChat);*/
            startActivity(i);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bar, menu);
        return true;


    }

    @Override
     public void onBackPressed() {
        moveTaskToBack(false);
    }

    @Override
    public void onResume(){
        super.onResume();


        String BASE_URL = "http://www.multidoctores.com";
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        MyApiEndpointInterface apiService = retrofit.create(MyApiEndpointInterface.class);



        Call<itemActivo> call2 = apiService.getActivo(idUser);

        call2.enqueue(new Callback<itemActivo>() {
            @Override
            public void onResponse(Response<itemActivo> response, Retrofit retrofit) {

                itemActivo activo = response.body();
                if (activo.isActivo()) {
                    estado = true;
                    IdChat = activo.getIdChat();

                    btn_nuevaConsulta.setText("Chat activo");


                } else {
                    estado = false;
                    IdChat = activo.getIdChat();
                    btn_nuevaConsulta.setText("Nueva consulta");
                }


            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getApplicationContext(), "no trae chat activo", Toast.LENGTH_SHORT).show();

            }


        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                session.logoutUser();
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


