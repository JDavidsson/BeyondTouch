package com.example.beyondtouch;


import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;

public class ProximityListener extends Service implements SensorEventListener {
    private SensorManager sensorManager = null;
    private Sensor sensor = null;

    @Override

    public void onCreate(){

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("STARTED SERVICE");
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sensorManager.registerListener(this, sensor,
                SensorManager.SENSOR_DELAY_NORMAL);


        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // do nothing
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.values[0] == 0) {
            System.out.println("PROXIMITY COVERED");
            Intent p = new Intent();
            p.setAction("Proximity");
            sendBroadcast(p);

        }
    }

}