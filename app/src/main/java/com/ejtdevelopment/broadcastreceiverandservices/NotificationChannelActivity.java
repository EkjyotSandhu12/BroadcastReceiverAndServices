package com.ejtdevelopment.broadcastreceiverandservices;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class NotificationChannelActivity extends Application {


    public static final String CHANNEL_1 = "channel1";
    public static final String CHANNEL_2 = "channel2";

    @Override
    public void onCreate() {
        super.onCreate();

        createChannel();
    }

    void createChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // check if the version of the system is greater than api level 26
            // you can write this code, in class extending application class to apply it globally

            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1,
                    "name: channel 1", // name of the channel that u see in app info
                    NotificationManager.IMPORTANCE_HIGH);
            channel1.setDescription("description: this is channel 1"); // description of the channel that u see in app info

            NotificationChannel channel2 = new NotificationChannel(
                    CHANNEL_2,
                    "name: channel 2", // name of the channel that u see in app info
                    NotificationManager.IMPORTANCE_HIGH);
            channel2.setDescription("description: this is channel 2"); // description of the channel that u see in app info


            getSystemService(NotificationManager.class).createNotificationChannel(channel1); // add it to the channel
            getSystemService(NotificationManager.class).createNotificationChannel(channel2); // add it to the channel
        }
    }
}