package com.example.yourdoctordemo3;

import android.content.Context;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import org.w3c.dom.Text;

import java.text.DecimalFormat;

public class Steps extends WearableActivity implements SensorEventListener {

    private static final String TAG = "Steps";
    private TextView steps;
    private SensorManager mSensorManager;
    private Sensor stepsensor;
    private TextView distance;
    private ImageView face;
    private TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);

        steps = (TextView) findViewById(R.id.stepscount);
        distance = (TextView) findViewById(R.id.distance);
        status = (TextView) findViewById(R.id.status);
        // Enables Always-on
        setAmbientEnabled();

        mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        stepsensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        getStep();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        String msg1 = "" + (int) sensorEvent.values[0];
        steps.setText(msg1);
        Log.d(TAG, msg1);

        //Distance
        int flag = Integer.valueOf(msg1);
        float flagdistance = (float) (flag*78)/(float)100000;//metatropi steps to km
        distance.setText(new DecimalFormat("###.##").format(flagdistance));


        if (flag >=3000){
            status.setText("Well Done");
        }

    }

    private void getStep(){
        SensorManager mSensorManager = ((SensorManager)getSystemService(SENSOR_SERVICE));
        Sensor mStepSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        mSensorManager.registerListener(this, mStepSensor, SensorManager.SENSOR_DELAY_UI);
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}