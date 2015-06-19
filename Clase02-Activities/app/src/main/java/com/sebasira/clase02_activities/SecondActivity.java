package com.sebasira.clase02_activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by XT-SOFT on 09/06/2015.
 */
public class SecondActivity extends ActionBarActivity {
    EditText ingreso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        ingreso = (EditText) findViewById(R.id.nombre);

        if (getIntent().hasExtra(Intent.EXTRA_TEXT)){
            ingreso.setText(getIntent().getStringExtra(Intent.EXTRA_TEXT));
        }


        Button volver = (Button) findViewById(R.id.botonVolver);
        volver.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String nombreIngresado = ingreso.getText().toString();

                Intent intento = new Intent();
                intento.putExtra("resulNom", nombreIngresado);

                if (nombreIngresado.equals("")){
                    setResult(RESULT_CANCELED, intento);
                }else{

                    setResult(RESULT_OK, intento);
                }

                finish();
            }
        });
    }

}
