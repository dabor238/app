package multidoctores.multidoctores;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class BioDoctor extends AppCompatActivity {
    SessionManagement session;
    String BioDoc;
    String NombreDoc;
    String Foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bio_doctor);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        final ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(false);
        session = new SessionManagement(getApplicationContext());
        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        if(b!=null)
        {
            String chatId =(String) b.get("CHATID");
            String BASE_URL = "http://www.multidoctores.com";
            final Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            final MyApiEndpointInterface apiService = retrofit.create(MyApiEndpointInterface.class);
            Call<Bio> call2 = apiService.getBio(chatId);
            call2.enqueue(new Callback<Bio>() {
                @Override
                public void onResponse(Response<Bio> response, Retrofit retrofit) {
                    // success call back

                    Bio docBio = response.body();
                    BioDoc = docBio.getBio();
                    NombreDoc = docBio.getNombre();
                    Foto = docBio.getFoto();


                    ImageView image = (ImageView) findViewById(R.id.FotoDoc);
                    Picasso.with(BioDoctor.this).load(Foto).into(image);
                    TextView BioDoctor = (TextView)findViewById(R.id.BioDoc);
                    BioDoctor.setText(BioDoc);
                    TextView topText = (TextView)findViewById(R.id.tituloTop);
                    topText.setText(NombreDoc);


                }

                @Override
                public void onFailure(Throwable t) {
                    // Log error here since request failed
                }
            });

        }else{
            Toast.makeText(getApplicationContext(), "Compruebe su conexión a Internet", Toast.LENGTH_SHORT).show();
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

            case R.id.action_historial:

                Intent i = new Intent(getApplicationContext(), Ficha.class);

                startActivity(i);

                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
