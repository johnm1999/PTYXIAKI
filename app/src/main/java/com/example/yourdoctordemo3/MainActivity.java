package com.example.yourdoctordemo3;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends WearableActivity {


    private ImageButton heartbtn;
    private ImageButton stepsbtn;
    private ImageButton historybtn;
    private ImageButton notificationbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        heartbtn = findViewById(R.id.heartrate);
        stepsbtn = findViewById(R.id.steps);
        historybtn = findViewById(R.id.history);
        notificationbtn = findViewById(R.id.notification);

        heartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHeartActivity();
            }
        });
        stepsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStepsActivity();
            }
        });
        historybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHistoryActivity();
            }
        });
        notificationbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNotificationActivity();
            }
        });

        // Enables Always-on
        setAmbientEnabled();
    }
    public void openHeartActivity(){
        Intent intent = new Intent(this, Heart_Rate.class);
        startActivity(intent);
    }
    public void openStepsActivity(){
        Intent intent = new Intent(this, Steps.class);
        startActivity(intent);
    }
    public void openHistoryActivity(){
        Intent intent = new Intent(this,History.class);
        startActivity(intent);
    }
    public void openNotificationActivity(){
        Intent intent = new Intent(this, Notifications.class);
        startActivity(intent);
    }
}