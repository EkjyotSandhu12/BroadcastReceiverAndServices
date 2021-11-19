package com.ejtdevelopment.broadcastreceiverandservices;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)){

            Boolean disconnected = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
            if(disconnected) Toast.makeText(context, "DISCONNECTED", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(context, "CONNECTED", Toast.LENGTH_SHORT).show();
            }

        if (intent.getAction().equals(Intent.ACTION_TIMEZONE_CHANGED))
            Toast.makeText(context, "Time Zone Changed", Toast.LENGTH_SHORT).show();

        if(intent.getAction().equals("CUSTOM_BROADCAST"))
            Toast.makeText(context,"same app " + " msg: " + intent.getStringExtra("message"),Toast.LENGTH_SHORT).show();
    }
}
