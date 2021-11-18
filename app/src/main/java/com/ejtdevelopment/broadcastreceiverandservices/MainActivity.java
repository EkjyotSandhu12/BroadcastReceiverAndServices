package com.ejtdevelopment.broadcastreceiverandservices;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    MyReceiver myReceiver = new MyReceiver();
    Button sendBroadcast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IntentFilter IF = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION); //Dynamic BroadCaster
        IF.addAction("CUSTOM_BROADCAST"); //adding custom broadcast to intent receiver
        registerReceiver(myReceiver, IF); //Registering broadcaste
    }

    @Override
    protected void onDestroy() {
       unregisterReceiver(myReceiver);
        super.onDestroy();

    }

    public void sendBroadCast(View view) {
        Intent intent = new Intent("CUSTOM_BROADCAST");
        intent.putExtra("message","this is custom Broadcast");
        sendBroadcast(intent);
    }

    public void SendSpecificBroadcast(View view){

        Intent intent = new Intent(this,MyReceiver2.class);

        /*// IMPLICIT BROAD CAST
        intent.setClass(this,MyReceiver2.class);
        // or send to same app receiver
        ComponentName cn = new ComponentName("com.ejtdevelopment.broadcastreceiverandservices", "com.ejtdevelopment.broadcastreceiverandservices.MyReceiver2");
        intent.setComponent(cn);
        //or EXPLICIT INTENT REQUIRES, STATIC BROAD CAST
        intent = new Intent("EXAMPLE_ACTION"); // THIS IS REQUIRES MANIFEST DECLARATION
        intent.setPackage("com.ejtdevelopment.broadcastreceiverandservices");

        //or TO TRIGGER SPECIFIC BROADCASTS IN OTHER APPS
        PackageManager packageManager = getPackageManager();

        List<ResolveInfo> infos = packageManager.queryBroadcastReceivers(intent, 0);

        for (ResolveInfo info : infos) {
            ComponentName cn = new ComponentName(info.activityInfo.packageName,
                    info.activityInfo.name);
            intent.setComponent(cn);
            sendBroadcast(intent);
        }*/

        sendBroadcast(intent);
    }
}