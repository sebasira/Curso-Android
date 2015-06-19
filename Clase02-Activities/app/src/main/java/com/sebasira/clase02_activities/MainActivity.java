package com.sebasira.clase02_activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.protocol.HTTP;


public class MainActivity extends ActionBarActivity {
    final String TAG = "MainActivity";
    final static public int KEY_RESULTADO_NOMBRE = 23;

    static View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG, "On CREATE");

        Button btnIrA2 = (Button) findViewById(R.id.botonAsegunda);
        btnIrA2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Context contexto = getApplicationContext();
                Intent i = new Intent(contexto, SecondActivity.class);
                startActivityForResult(i, KEY_RESULTADO_NOMBRE);
            }
        });

        Button botonImplicit = (Button) findViewById(R.id.botonImplicit);
        botonImplicit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intento = new Intent();             // Creamos el intento
                intento.setAction(Intent.ACTION_SEND);     // Definimos la acción ¿Que queremos hacer? En esta caso emulamos el SHARE (compartir)
                intento.putExtra(Intent.EXTRA_TEXT, "texto a enviar");   // A modo de ejemplo, agregamos un texto que deseamos compartir
                intento.setType(HTTP.PLAIN_TEXT_TYPE);     // Tipo de lo que vamos a compartir
                startActivity(intento);

                //Si quisiéramos podemos customizar el titulo del selector de aplicación,
                // en esta caso deberíamos hacer las siguientes modificaciones
                /*
                Intent chooser = Intent.createChooser(intento, "COMO QUIERE ENVIAR?");   // Texto del selector
                startActivity(chooser);                   // Iniciamos con el selector, no con el intento (el intento esta dentro del selector)
                */

            }
        });

    }


    @Override
    protected void onStart(){
        super.onStart();
        Log.i(TAG, "On START");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.i(TAG, "On RESUME");
    }

    @Override
    protected void onPause(){
        Log.i(TAG, "On PAUSE");
        super.onPause();
    }

    @Override
    protected void onStop(){
        Log.i(TAG, "On STOP");
        super.onStop();
    }

    @Override
    protected void onDestroy(){
        Log.i(TAG, "On DESTROY");
        super.onDestroy();
    }



    @Override
    protected  void onActivityResult (int key, int status, Intent data ){
        super.onActivityResult(key,status, data);

        Log.i(TAG, "Volvio de 2");

        if (key == KEY_RESULTADO_NOMBRE){
            Log.i(TAG, "SI concide KEY");
            TextView tv = (TextView) findViewById(R.id.textView1);

            if (status == RESULT_OK){
                String nom = data.getExtras().getString("resulNom");
                tv.setText("HOLA "+nom);
                Toast.makeText(getApplicationContext(), "Resultado OK", Toast.LENGTH_LONG).show();

            }else{
                tv.setText("HOLA DESCONOCIDO");
                Toast.makeText(getApplicationContext(), "Resultado CANCELADO", Toast.LENGTH_LONG).show();
            }
        }else{
            Log.i(TAG, "No concide KEY"+key);
        }
    }
}
