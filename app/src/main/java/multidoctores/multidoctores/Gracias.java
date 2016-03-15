package multidoctores.multidoctores;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Gracias extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gracias);

        Button btn_Inicio = (Button)findViewById(R.id.irInicio);
        btn_Inicio.setOnClickListener(new InicioClickListener());




    }

    class InicioClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v){


            Intent i = new Intent(Gracias.this, List.class);
            startActivity(i);
        }
    }
}
