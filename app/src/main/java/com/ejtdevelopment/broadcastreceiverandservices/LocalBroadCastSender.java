package com.ejtdevelopment.broadcastreceiverandservices;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LocalBroadCastSender extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_broad_cast_sender);

        EditText localText = findViewById(R.id.localMessage);
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(this);
        Button button = findViewById(R.id.sendLocalBroadcast);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("LOCAL_BROADCAST");
                intent.putExtra("value", localText.getText().toString());
                manager.sendBroadcast(intent);
            }
        });
    }
}