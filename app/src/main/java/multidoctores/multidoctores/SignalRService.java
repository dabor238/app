package multidoctores.multidoctores;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.hardware.Camera;

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
import microsoft.aspnet.signalr.client.Action;
import multidoctores.multidoctores.HelloModel;

public class SignalRService {
    private HubConnection mHubConnection;
    public HubProxy mHubProxy;
    private Handler mHandler; // to display Toast message
    private final IBinder mBinder = new LocalBinder(); // Binder given to clients




    public SignalRService() {

    }




    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
        public SignalRService getService() {
            // Return this instance of SignalRService so clients can call public methods
            return SignalRService.this;
        }
    }

    /**
     * method for clients (activities)
     */
    public void sendMessage(String message) {
        try {
            mHubProxy.invoke("sendChatMessage", "dabor238@gmail.com", "dabor238@gmail.com", message).get();
            System.out.println("Todo bien3Hey");
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
            System.out.println("Todo mal");
        } catch (ExecutionException e) {
            System.out.println("Todo mal");
            System.out.println(e.getMessage());
        }

    }

    /**
     * method for clients (activities)
     */
    public void sendMessage_To(String receiverName, String message) {
        //String SERVER_METHOD_SEND_TO = "SendChatMessage";
        //mHubProxy.invoke(SERVER_METHOD_SEND_TO, receiverName, message);
        try {
            mHubProxy.invoke("SendChatMessage", "android", "android","android").get();
            System.out.println("Todo bienChao");
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
            System.out.println("Todo mal");
        } catch (ExecutionException e) {
            System.out.println("Todo mal");
            System.out.println(e.getMessage());
        }
    }

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
            System.out.println("Todo bien3Hey");
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
            System.out.println("Todo mal");
        } catch (ExecutionException e) {
            System.out.println("Todo mal");
            System.out.println(e.getMessage());
        }



        //starHandlersConnection();

        //sendMessage("Hello from BNK!");




        /*String CLIENT_METHOD_BROADAST_MESSAGE = "addChatMessage";
        mHubProxy.on(CLIENT_METHOD_BROADAST_MESSAGE,
                new SubscriptionHandler4<String, String, String, String>() {
                    @Override
                    public void run(final String who, final String message, final String hora, final String notificar) {

                        System.out.println(message);


                      *//*  View view = getLayoutInflater().inflate(R.layout.summarydetail_view, null);
                        setContentView(view);*//*


                    }
                }
                , String.class,String.class,String.class,String.class);*/
    }




    public void ActualizarClientes( String status )
    {
        final String fStatus = status;
        System.out.println("Todo very Yes");
    }


    private SubscriptionHandler1 handlerCon;
    private void starHandlersConnection(){
        handlerCon = new SubscriptionHandler1<String>() {
            @Override
            public void run(String p1) {
                System.out.println("Todo very Yes");//Here is where we get back the response from the server. Do stuffs
            }
        };

        mHubProxy.on("ActualizarClientes",handlerCon,String.class);
    }
}
