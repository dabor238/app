package multidoctores.multidoctores;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import microsoft.aspnet.signalr.client.Platform;
import microsoft.aspnet.signalr.client.SignalRFuture;
import microsoft.aspnet.signalr.client.http.android.AndroidPlatformComponent;
import microsoft.aspnet.signalr.client.hubs.HubConnection;
import microsoft.aspnet.signalr.client.hubs.HubProxy;
import microsoft.aspnet.signalr.client.hubs.SubscriptionHandler1;
import microsoft.aspnet.signalr.client.hubs.SubscriptionHandler4;
import microsoft.aspnet.signalr.client.transport.ClientTransport;
import microsoft.aspnet.signalr.client.transport.ServerSentEventsTransport;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import android.widget.ListView;


public class Conversacion extends AppCompatActivity {

    private final Context mContext = this;
    private SignalRService mService;
    private boolean mBound = false;

    private HubConnection mHubConnection;
    private HubProxy mHubProxy;
    private Handler mHandler; // to display Toast message
    SignalRService servicio = new SignalRService();
    TextView resultado;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversacion);
        Button enviar = (Button)findViewById(R.id.Enviar);
        enviar.setOnClickListener(new EnviarClickListener());
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);


        String usuario = "dabor238@gmail.com";

        String BASE_URL = "http://www.multidoctores.com";
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MyApiEndpointInterface apiService = retrofit.create(MyApiEndpointInterface.class);
        Call<Boolean> call = apiService.AsignarUser(usuario);

        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Response<Boolean> response, Retrofit retrofit) {
                Toast.makeText(getApplicationContext(), "chat creado", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Throwable t) {
                // Log error here since request failed
            }
        });



        servicio.startSignalR();


        String CLIENT_METHOD_BROADAST_MESSAGE = "addChatMessage";
        servicio.mHubProxy.on(CLIENT_METHOD_BROADAST_MESSAGE,
                new SubscriptionHandler4<String, String, String, String>() {
                    @Override
                    public void run(final String who, final String message, final String hora, final String notificar) {



                     /*   Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                        LinearLayout linearLayout1 = (LinearLayout)findViewById(R.id.LinearRellenar);
                        TextView valueTV2 = new TextView(Conversacion.this);
                        valueTV2.setText(message);
                        valueTV2.setTextColor(getResources().getColor(R.color.white));
                        linearLayout1.addView(valueTV2);*/
                        //System.out.println(message);
                        runOnUiThread(new Runnable() {

                            public void run() {
                               /* LinearLayout linearLayout = (LinearLayout) findViewById(R.id.LinearRellenar);
                                TextView valueTV2 = new TextView(Conversacion.this);
                                valueTV2.setText(message);
                                valueTV2.setTextColor(getResources().getColor(R.color.white));
                                linearLayout.addView(valueTV2);*/
                                String doctor = "doctor1@multidoctores.com";

                                if(who.equals(doctor)) {
                                    String recibeMsm = message;
                                    LinearLayout linearLayout = (LinearLayout) findViewById(R.id.LinearRellenar);

                                    LayoutInflater inflater = LayoutInflater.from(Conversacion.this);
                                    View inflatedLayout = inflater.inflate(R.layout.chat_item_rcv, null, false);
                                    TextView lbl = (TextView) inflatedLayout.findViewById(R.id.lbl1);
                                    lbl.setText(recibeMsm);
                                    TextView lbl2 = (TextView) inflatedLayout.findViewById(R.id.lbl2);
                                    lbl2.setText(hora);
                                    linearLayout.addView(inflatedLayout);

                                    ScrollView mainScrollView = (ScrollView) findViewById(R.id.scroll);
                                    mainScrollView.fullScroll(ScrollView.FOCUS_DOWN);
                                }



                            }
                        });

                    }
                }
                , String.class, String.class, String.class, String.class);



    }

    class EnviarClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v){
            EditText mensaje1 = (EditText) findViewById(R.id.mensaje);
            String message = mensaje1.getText().toString();
            servicio.sendMessage(message);
            mensaje1.setText("");



            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.LinearRellenar);

            LayoutInflater inflater = LayoutInflater.from(Conversacion.this);
            View inflatedLayout = inflater.inflate(R.layout.chat_item_sent, null, false);
            TextView lbl = (TextView) inflatedLayout.findViewById(R.id.lbl1);
            lbl.setText(message);
            TextView lbl2 = (TextView) inflatedLayout.findViewById(R.id.lbl2);
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            Date now = new Date();
            String strDate = sdf.format(now);
            lbl2.setText(strDate);
            linearLayout.addView(inflatedLayout);

            ScrollView mainScrollView = (ScrollView)findViewById(R.id.scroll);
            mainScrollView.fullScroll(ScrollView.FOCUS_DOWN);



        }
    }





    @Override
    protected void onStop() {
        // Unbind from the service
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
        super.onStop();
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
                return true;

            case R.id.action_favorite:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }



    /**
     * Defines callbacks for service binding, passed to bindService()
     */
    private final ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to SignalRService, cast the IBinder and get SignalRService instance
            SignalRService.LocalBinder binder = (SignalRService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

    public void startSignalR() {
        Platform.loadPlatformComponent(new AndroidPlatformComponent());

        String serverUrl = "http://www.multidoctores.com";
        mHubConnection = new HubConnection(serverUrl);
        String SERVER_HUB_CHAT = "chatHub";
        mHubProxy = mHubConnection.createHubProxy(SERVER_HUB_CHAT);


        ClientTransport clientTransport = new ServerSentEventsTransport(mHubConnection.getLogger());
        SignalRFuture<Void> signalRFuture = mHubConnection.start(clientTransport);

        try {
            signalRFuture.get();
            System.out.println("Todo bien");
        } catch (InterruptedException | ExecutionException e) {
            Log.e("SimpleSignalR", e.toString());
            return;
        }

        /*mHubProxy.invoke(String.class, "actualizarClientes", "android", "android", "android").done(new Action<String>() {
            @Override
            public void run(String s) throws Exception {
                System.out.println("yess");
            }
        });*/

        try {
            mHubProxy.invoke("joinGroup", "dabor238@gmail.com;dabor238@gmail.com").get();
            System.out.println("Todo bien3333");
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
            System.out.println("Todo mal");
        } catch (ExecutionException e) {
            System.out.println("Todo mal");
            System.out.println(e.getMessage());
        }
        //starHandlersConnection();

        //sendMessage("Hello from BNK!");

        String CLIENT_METHOD_BROADAST_MESSAGE = "sendHelloObject";
        mHubProxy.on(CLIENT_METHOD_BROADAST_MESSAGE,
                new SubscriptionHandler1<HelloModel>() {
                    @Override
                    public void run(final HelloModel msg) {
                        final String finalMsg = msg.Age;
                        System.out.println("Todo bien2");
                        // display Toast message

                    }
                }
                , HelloModel.class);
    }

}