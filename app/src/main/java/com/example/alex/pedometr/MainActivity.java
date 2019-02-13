package com.example.alex.pedometr;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements SensorEventListener, StepListener {
    private TextView TvSteps, TvCalories;
    private Button BtnStart, BtnStop, BtnProf, BtnStat;

    private TextView textView;
    private StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel;
    private String weight="90",height="193";
    private int numSteps;
    private double numCalories;
    private long starttime, stoptime, totaltime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Get an instance of the SensorManager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(this);

        TvSteps = (TextView) findViewById(R.id.tv_steps);
        TvCalories = (TextView) findViewById(R.id.tv_calories);
        BtnStart = (Button) findViewById(R.id.btn_start);
        BtnStop = (Button) findViewById(R.id.btn_stop);


        /*BtnStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                numSteps = 0;
                sensorManager.registerListener(MainActivity.this, accel, SensorManager.SENSOR_DELAY_FASTEST);
                starttime = System.currentTimeMillis();

            }
        });


        BtnStop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                sensorManager.unregisterListener(MainActivity.this);
                stoptime = System.currentTimeMillis();

            }
        });*/


    }

    public void onProfClick(View view){
        Intent intent = new Intent(this,Profil.class);
        startActivityForResult(intent, 1);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {return;}
        weight = data.getStringExtra("weight");
        height = data.getStringExtra("height");
    }
    public void onStartClick(View view) {
        numSteps = 0;
        TvSteps.setText("0");
        TvCalories.setText("0");
        sensorManager.registerListener(MainActivity.this, accel, SensorManager.SENSOR_DELAY_FASTEST);

        starttime = System.currentTimeMillis();
    }
    public void onStopClick(View view) {
        sensorManager.unregisterListener(MainActivity.this);
        stoptime = System.currentTimeMillis();

        if(starttime != 0 ) {
            totaltime = stoptime - starttime;
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(totaltime);
            SimpleDateFormat format = new SimpleDateFormat("mm:ss");
            String s = "Время тренировки: "+ format.format(cal.getTime());
            Toast toast = Toast.makeText(getApplicationContext(),
                    s,
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            starttime = 0;
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector.updateAccel(
                    event.timestamp, event.values[0], event.values[1], event.values[2]);
        }
    }

    @Override
    public void step(long timeNs) {
        numSteps++;
        numCalories = (Double.valueOf(weight)/Double.valueOf(height))*(numSteps * 0.1);
        TvSteps.setText(Integer.valueOf(numSteps).toString());
        TvCalories.setText(new DecimalFormat("#.###").format(numCalories));

    }

}