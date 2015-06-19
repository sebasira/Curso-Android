package ar.com.sebasira.clase03;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class AirplaneReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context con, Intent intento){
        if (intento.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")){
            ConnectivityManager conMan = (ConnectivityManager) con
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = conMan.getActiveNetworkInfo();

            String info = "REGISTRO ESTATICO: ";
            if (null == netInfo){
                info += "Desconectado";
            }else{
                info += netInfo.getTypeName();
            }
            Toast.makeText(con, info, Toast.LENGTH_LONG).show();
        }
    }
}
