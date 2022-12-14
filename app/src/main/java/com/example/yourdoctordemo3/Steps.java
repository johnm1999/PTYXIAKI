package com.example.yourdoctordemo3;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Steps extends WearableActivity implements SensorEventListener {

    private static final String TAG = "Steps";
    private TextView steps;
    private SensorManager mSensorManager;
    private Sensor stepsensor;
    private TextView distance;
    private TextView status;
    private TextView status2;
    private boolean isCounterSensorPresent;
    private String date;
    int stepcount;
    int stepcount2;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat simpleformat = new SimpleDateFormat("dd/MMMM/yyyy hh:mm:s");
        SimpleDateFormat f = new SimpleDateFormat(" EEEE");
        date = f.format(new Date());

        steps = (TextView) findViewById(R.id.stepscount);
        distance = (TextView) findViewById(R.id.distance);
        status = (TextView) findViewById(R.id.status);
        status2 = (TextView) findViewById(R.id.status2);
        //Enables Always-on
        setAmbientEnabled();

        mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);

        if(mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)!=null){
            stepsensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            isCounterSensorPresent = true;
        }
        else{
            steps.setText("Counter Sensro is not Present");
            isCounterSensorPresent = false;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        sp = getSharedPreferences("StepsData",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        stepcount = sp.getInt("steps",stepcount);


        if(sensorEvent.sensor == stepsensor){
            if((stepcount<1 && date.equals(sp.getString("date",date))) || sp.getString("date",date) == null)
            {
                stepcount = (int) sensorEvent.values[0];
                steps.setText(String.valueOf(stepcount));
                status.setText(date);
                status2.setText(sp.getString("date",date));
                editor.putInt("steps",stepcount);
                editor.putString("date",date);
                editor.commit();
            }
            else if(sp.getInt("steps",stepcount)>=1 && date.equals(sp.getString("date",date)))
            {
                if (stepcount2==0){
                    stepcount2 = (int) sensorEvent.values[0] + stepcount;
                }
                else{
                    stepcount2 = (int) sensorEvent.values[0];
                }
                steps.setText(String.valueOf(stepcount));
                status2.setText(sp.getString("date",date));
                editor.putInt("steps",stepcount2);
                editor.apply();
            }
            else if(!date.equals(sp.getString("date",date)) && sp.getString("date",date) != null)
            {
                stepcount = 0;
                steps.setText(String.valueOf(stepcount));
                status2.setText(sp.getString("date",date));
                editor.putInt("steps",stepcount);
                editor.putString("date",date);
                editor.apply();
            }

        }
        if(stepcount2<3000){
            status.setText("Start walking");
        }
        else if(stepcount2>3000 && stepcount2<6000){
            status.setText("Healthy");
        }
        else if(stepcount2<9000){
            status.setText("athlete");
        }
        else{
            status.setText("Champion");
        }
        float flagdistance = (float) (stepcount*78)/(float)100000;//metatropi steps to km
        distance.setText(new DecimalFormat("###.##").format(flagdistance));

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onResume(){
        super.onResume();
        if(mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)!=null)
        {
            mSensorManager.registerListener(this,stepsensor,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }
    @Override
    public void onPause(){
        super.onPause();
        if(mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)!=null){
            mSensorManager.unregisterListener(this,stepsensor);
        }
    }
}