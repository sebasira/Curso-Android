package ar.com.sebasira.clase03;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
    final String myReceiverAction = "LaAccionEsElTextoQueAMiSeMeOcurra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button botonMiReceiver = (Button) findViewById(R.id.botonMiReceiver);
        botonMiReceiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setAction(myReceiverAction);
                sendBroadcast(i);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        IntentFilter filter;

        filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(airplaneReceiver, filter);

        filter = new IntentFilter();
        filter.addAction(myReceiverAction);
        registerReceiver(myReceiver, filter);
    }


    @Override
    public void onPause() {
        unregisterReceiver(airplaneReceiver);
        unregisterReceiver(myReceiver);

        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

/*************************************************************************/
/** PRIVATE RECEIVERS **/
/***********************/

    private BroadcastReceiver airplaneReceiver =  new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent){
            ConnectivityManager conMan = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = conMan.getActiveNetworkInfo();

            String info = "REGISTRO DINAMICO: ";
            if (null == netInfo){
                info += "Desconectado";
            }else{
                info += netInfo.getTypeName();
            }
            Toast.makeText(context, info, Toast.LENGTH_LONG).show();
        }
    };


    private BroadcastReceiver myReceiver =  new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent){
            Toast.makeText(context, "Soy UnReceiver", Toast.LENGTH_SHORT).show();
        }
    };
}
