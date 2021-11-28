package com.ejtdevelopment.broadcastreceiverandservices;

import static com.ejtdevelopment.broadcastreceiverandservices.NotificationChannelActivity.CHANNEL_2;

import java.security.Provider;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class ExampleService extends Service {

    Notification notification;

    @Override
    public void onCreate() { // runs once in a life time when services gets created
        Toast.makeText(this, "foreground Service created", Toast.LENGTH_SHORT).show();
        super.onCreate();
    }

    @Override
    public void onDestroy() { // when services gets destroyed
        super.onDestroy();
    }

    // it runs on main thread/ UI thread by default
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) { // everytime u run startService() on the this service

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_2)
                .setContentText(intent.getStringExtra("msg"))
                .setContentTitle("service")
                .setSmallIcon(R.drawable.ic_noti1)
                .build();

        startForeground(1,notification);

        // stopSelf();  // u can use this method to stop the service

        return START_NOT_STICKY;

              /*
                These constants define what happens when the system kills our service
                NOT_STICKY means our service will just be gone and not started again
                STICKY means the system will restart our service as soon as possible but the intent we passed to it will be null
                REDELIVER_INTENT means it will be started it again
              */
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // Bound services are services where other components can communicate back and forth by binding to it
        return null;
    }
}
