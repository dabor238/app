package multidoctores.multidoctores;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.TextView;

public class Inicio extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);


        TextView btn_ir = (TextView)findViewById(R.id.quiero);
        btn_ir.setOnClickListener(new ClickOnClickListener());


    }

    class ClickOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v){


            Intent i = new Intent(Inicio.this, Registrar.class);
            startActivity(i);
        }
    }

}
