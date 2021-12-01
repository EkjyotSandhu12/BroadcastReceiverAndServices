package com.ejtdevelopment.broadcastreceiverandservices;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ForegroundServiceActivity extends AppCompatActivity {

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foreground_service);

        intent = new Intent(this, ForegroundService.class);
        intent.putExtra("msg", "Foreground Service");
    }

    public void startForegroundService(View view) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent); // if startForeground() isnt triggered from ExampleService.class withing 5 seconds, service will be destroyed
        } else {

            startService(intent); // if  u want to start your service while app is in the background u should use startForegroundService();
        }
    }

    public void stopForegroundService(View view) {

        stopService(intent);
    }
}