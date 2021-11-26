package com.ejtdevelopment.broadcastreceiverandservices;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class NotificationActivity extends AppCompatActivity {

    public static final String CHANNEL_1 = "channel1";
    public static final String CHANNEL_2 = "channel2";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        CreateChannel(); // after api 26 we need to create channel for each notification
    }

    void CreateChannel(){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){ // check if the version of the system is greater than api level 26
        // you can write this code, in class extending application class to apply it globally
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1,
                    "name: channel 1",
                    NotificationManager.IMPORTANCE_HIGH);
            channel1.setDescription("description: this is channel 1");
            getSystemService(NotificationManager.class).createNotificationChannel(channel1); // add it to the channel
        }
    }

    public void channel1(View view) {

        Intent intent = new Intent(this,JobSchedulerActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);
        // pending intent is used to trigger an intent without the app running.
        //you can start service/broadcast with pending intent

        Intent addAction = new Intent(this, MyReceiver.class);
        addAction.putExtra("msg","This is Notification Broadcast");
        PendingIntent actionIntent = PendingIntent.getBroadcast(this, // getBroadcast is similar to sendBroadcast()
                0,
                addAction,
                PendingIntent.FLAG_UPDATE_CURRENT); // if the msg changes, the values should be updated

        Notification notification1 = new NotificationCompat.Builder(this, CHANNEL_1) //we can directly create this for systems that dont require channel
                .setSmallIcon(R.drawable.ic_noti1) // small icon is mandatory
                .setContentTitle("My notification")
                .setContentText("Hello World!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setColor(getResources().getColor(R.color.purple_200)) // change overall color of the notification
                .setContentIntent(pendingIntent) // to setup an pending intent // on click
                .addAction(R.mipmap.ic_launcher,"click for broadcast",actionIntent)
                .setAutoCancel(true) // which automatically removes the notification when the user taps it.
                .setOnlyAlertOnce(true) //it will stay silent the second time if we didnt update it
                .build();

        getSystemService(NotificationManager.class).notify(1,notification1);
    }

}