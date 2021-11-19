package com.ejtdevelopment.broadcastreceiverandservices;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText textMsg;
    MyReceiver myReceiver = new MyReceiver();
    LocalBroadcastManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        manager = LocalBroadcastManager.getInstance(this);
        textMsg = findViewById(R.id.broadcasttext);

        IntentFilter IF = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION); //Dynamic BroadCaster
        IF.addAction("CUSTOM_BROADCAST"); //adding custom broadcast to intent receiver
        registerReceiver(myReceiver, IF); //Registering broadcaste



        manager.registerReceiver(LocalBroadcastReceiver,new IntentFilter("LOCAL_BROADCAST"));

    }


    BroadcastReceiver LocalBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Intent activity = new Intent(context, MainActivity.class);
            startActivity(activity);
            Toast.makeText(context, intent.getStringExtra("value"), Toast.LENGTH_SHORT).show();
            TextView tv = findViewById(R.id.localText); // incomplete
        }
    };



    @Override
    protected void onDestroy() {
       unregisterReceiver(myReceiver);
        super.onDestroy();

    }

    public void sendBroadCast(View view) { //implicit broadcast
        Intent intent = new Intent("CUSTOM_BROADCAST");
        intent.putExtra("message",textMsg.getText().toString());
        sendBroadcast(intent);
    }

    //explict means we do not specify the action, we trigger the broadcast we want to access directly
    public void ExplictBroadcast(View view){

        Intent intent = new Intent();

        intent = new Intent(this,MyReceiver2.class); // EXPLICIT BROADCAST
        //or
        //intent.setClass(this,MyReceiver2.class);

        sendBroadcast(intent);
    }

    public void ExplictAnotherAppBroadcast(View view){
        Intent intent = new Intent();

/*
        //to another app (specify package) specific receiver (NO ACTION)
        ComponentName cn = new ComponentName("com.ejtdevelopment.receiver2test",
                "com.ejtdevelopment.receiver2test.MyReceiver");
        intent.setComponent(cn);
*/
        // specifying PACKAGE automaticlly makes it EXPLICIT (WITH ACTION)
        intent = new Intent("EXPLICIT_BROADCAST");
        intent.setPackage("com.ejtdevelopment.receiver2test");

 sendBroadcast(intent);
    }

    public void priortyBroadcasting(View view){ //dynamic broadcast will run first and then static
        Intent intent = new Intent("PRIORITY_BROADCASTING");
        intent.setPackage("com.ejtdevelopment.receiver2test");
        sendOrderedBroadcast(intent, null);
        // https://www.youtube.com/watch?v=Eyuez4D-qLg&list=PLrnPJCHvNZuBhmqlWEQfvxbNtY6B_XJ3n&index=9&ab_channel=CodinginFlow
        // to understand more functionalities of ordered broadcast
    }

    public void StartLocalBroadCast(View view){

        Intent intent = new Intent(this,LocalBroadCastSender.class);
        startActivity(intent);
    }
}