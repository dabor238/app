package multidoctores.multidoctores;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Conectando extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conectando);
        hand.postDelayed(run, 20000); // For delay seconds

    }




    Handler hand = new Handler();
    Runnable run = new Runnable() {
        @Override

        public void run() {

            Intent i = new Intent(Conectando.this, Conversacion.class);
            startActivity(i);
        }

    };
}
