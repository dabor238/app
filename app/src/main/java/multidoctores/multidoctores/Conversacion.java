package multidoctores.multidoctores;


import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import microsoft.aspnet.signalr.client.Platform;
import microsoft.aspnet.signalr.client.SignalRFuture;
import microsoft.aspnet.signalr.client.http.android.AndroidPlatformComponent;
import microsoft.aspnet.signalr.client.hubs.HubConnection;
import microsoft.aspnet.signalr.client.hubs.HubProxy;
import microsoft.aspnet.signalr.client.hubs.SubscriptionHandler1;




public class Conversacion extends Activity {

    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversacion);



        Platform.loadPlatformComponent(new AndroidPlatformComponent());
        // Change to the IP address and matching port of your SignalR server.
        String host = "http://184.106.77.232";
        HubConnection connection = new HubConnection( host );
        HubProxy hub = connection.createHubProxy( "ChatHub" );



       SignalRFuture<Void> awaitConnection = connection.start();
        try {
            awaitConnection.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return;
        }

        //hub.subscribe( this);

        hub.on("ActualizarClientes",
                new SubscriptionHandler1<String>() {
                    @Override
                    public void run(String status) {
                        // Since we are updating the UI,
                        // we need to use a handler of the UI thread.

                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "caca", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
                , String.class );

       /* try {
            hub.invoke( "SendChatMessage", "dabor238@gmail.com", "doctor1@multidoctores.com", "ola ke ace" ).get();
          *//*hub.invoke( "SendChatMessage",
                    new CustomType() {{ Name = "Universe"; Id = 42; }} ).get();*//*
        } catch (InterruptedException e) {
            // Handle ...
        } catch (ExecutionException e) {
            // Handle ...
        }
*/

    }

/*
    public void ActualizarClientes()
    {
        //final String fStatus = status;
        mHandler.post(new Runnable(){
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_SHORT).show();
            }});
    }*/


}
