package com.example.yourdoctordemo3;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;


public class Heart_Rate extends WearableActivity implements SensorEventListener {

    private static final String TAG = "Heart_Rate";
    private TextView hearttext;
    private ImageButton startrate;
    private ImageButton stoprate;
    private SensorManager mSensorManager;
    private Sensor mheartratesensor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart__rate);

        hearttext = (TextView) findViewById(R.id.heart_rate_text);
        startrate = (ImageButton) findViewById(R.id.start_heart_rate_btn);
        stoprate = (ImageButton) findViewById(R.id.stop_heart_rate_btn);

        startrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hearttext.setText("please wait...");
                startMeasure();
            }
        });
        stoprate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hearttext.setText("--");
                stopMeasure();
            }
        });

        setAmbientEnabled();
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mheartratesensor = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
    }
    private void startMeasure(){
        boolean sensorRegistered = mSensorManager.registerListener(this,mheartratesensor,SensorManager.SENSOR_DELAY_FASTEST);
        Log.d("Sensor Status:", " Sensor registered: " + (sensorRegistered ? "yes" : "no"));
    }
    private void stopMeasure() {
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        String msg = "" + (int)event.values[0];
        hearttext.setText(msg);

        //FireStore

        String path = "first user";
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        HashMap<String,Object> data = new HashMap<>();
        data.put("Heart Rate", msg);
        data.put("name","johny");
        db.collection("users").document(path).set(data);

        Log.d(TAG, msg);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}