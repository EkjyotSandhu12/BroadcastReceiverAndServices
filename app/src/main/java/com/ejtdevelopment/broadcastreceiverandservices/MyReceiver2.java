package com.ejtdevelopment.broadcastreceiverandservices;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiver2 extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if("EXAMPLE_ACTION".equals(intent.getAction()))
        Toast.makeText(context, "Triggered specific braodcast", Toast.LENGTH_SHORT).show();

    }
}