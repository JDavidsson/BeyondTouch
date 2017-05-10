package com.example.beyondtouch;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.util.Log;

/**
 * Created by Christoffer on 2017-05-05.
 */

public class MainFragment extends Fragment implements SensorEventListener {

    private SensorManager sensorManager;
    private long lastUpdate;
    private long timeOut;
    private float currX, currY, currZ, lastX, lastY, lastZ;
    private float proximityValue;
    private final static int SHAKE_THRESHOLD = 800;
    private final static int SHAKECOUNT_LIMIT = 2;
    private int shakeCounter;
    private Sensor proximitySensor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.getDefaultSensor(SensorManager.SENSOR_DELAY_GAME);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        shakeCounter = 0;
    }
    @Override
    public void onSensorChanged(SensorEvent event) {

        // implement proximity sensor to go back
        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            proximityValue = event.values[0];
            if (proximityValue == 0) {
                Vibrator v = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                // Vibrate for 150 milliseconds
                v.vibrate(150);
                moveUpOneLevel();
            }
            System.out.println("PROX VALUE = " + proximityValue);
        }

        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            long curTime = System.currentTimeMillis();
            // only allow one update every 100ms.
            if ((curTime - lastUpdate) > 100) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                currX = event.values[SensorManager.DATA_X];
                currY = event.values[SensorManager.DATA_Y];
                currZ = event.values[SensorManager.DATA_Z];

                float speed = Math.abs(currX + currY + currZ - lastX - lastY - lastZ) / diffTime * 10000;

                if(curTime - timeOut > 500 && shakeCounter > 0){
                    shakeCounter = 0;
                    Log.e("RESET", "Counter reset");
                }

                if (speed > SHAKE_THRESHOLD) {
                    shakeCounter++;
                    timeOut = curTime;
                    if(shakeCounter > SHAKECOUNT_LIMIT){
                        Log.d("sensor", "shake detected w/ speed: " + speed);
                        Vibrator v = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                        // Vibrate for 150 milliseconds
                        v.vibrate(150);
                        moveUpOneLevel();
                    } else {
                        Log.d("counter", shakeCounter + "");
                    }
                } else if (speed > 400){
                    Log.d("sensor fail", "shake detected w/ speed: " + speed);
                }
                lastX = currX;
                lastY = currY;
                lastZ = currZ;
            }
        }



    }



    public void moveUpOneLevel(){
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
        getActivity().getSupportFragmentManager().popBackStack();
    }

    private void shakePhone(){
        Vibrator v = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 150 milliseconds
        v.vibrate(150);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
