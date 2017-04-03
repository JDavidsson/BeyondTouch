package com.example.beyondtouch;

import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Point p;
    private Sensor accelSensor;
    private TextView coords, XTextView;
    private int screenHeight, screenWidth;
    private FrameLayout FLleft, FLright, FLtop, FLbottom;

    private static int RIGHT_MARGIN, LEFT_MARGIN, TOP_MARGIN, BOTTOM_MARGIN;
    private boolean leftFlag, rightFlag, topFlag, bottomFlag;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        p = new Point(0,0);
        coords = (TextView) findViewById(R.id.coordinates);
        XTextView = (TextView) findViewById(R.id.textView2);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
        p.set(screenWidth/2, screenHeight/2);

        FLleft =(FrameLayout) findViewById(R.id.frameLayoutLeft);
        FLright =(FrameLayout) findViewById(R.id.frameLayoutRight);
        FLtop =(FrameLayout) findViewById(R.id.frameLayoutTop);
        FLbottom=(FrameLayout) findViewById(R.id.frameLayoutBottom);

        /** Needed for coordinate precission */
        RIGHT_MARGIN = screenWidth - 70;
        LEFT_MARGIN = 70;
        TOP_MARGIN = screenHeight - 70;
        BOTTOM_MARGIN = 70;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            updatePointer(event);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public void updatePointer(SensorEvent event){
        float axisX = event.values[0];
        float axisY = event.values[1];
        coords.setText("X:" + axisX + "\n p.x : " + p.x + "\n Y: " + axisY + "\n p.y " + p.y);

        if(axisX > 4) {
            leftFlag = true;
        } else if(axisX < -4) {
           rightFlag = true;
        } else {
            leftFlag = false;
            rightFlag = false;
        }
        if(axisY > 3 ) {
            bottomFlag = true;
        } else if(axisY < -3) {
            topFlag = true;
        } else {
           topFlag = false;
            bottomFlag = false;
        }

        if(leftFlag) {
            XTextView.setText("LEFT");
            FLleft.setBackgroundColor(0x330099CC);
        } else  {
            XTextView.setText("MIDDLE");
            FLleft.setBackgroundColor(0xFF0099CC);
        }
        if(rightFlag) {
            XTextView.setText("RIGHT");
            FLright.setBackgroundColor(0x33669900);
        } else {
            XTextView.setText("MIDDLE");
            FLright.setBackgroundColor(0xFF669900);
        }
        if(topFlag) {
            XTextView.setText("TOP");
            FLtop.setBackgroundColor(0x33AA66CC);
        } else {
            XTextView.setText("MIDDLE");
            FLtop.setBackgroundColor(0xFFAA66CC);
        }
        if(bottomFlag) {
            XTextView.setText("BOTTOM");
            FLbottom.setBackgroundColor(0x33ff8800);
        } else {
            XTextView.setText("MIDDLE");
            FLbottom.setBackgroundColor(0xFFff8800);
        }


        /** Using coordinates for more precission */
        /*if(axisX > 4) {
            if(p.x > 0) {
                p.set(p.x - 400, p.y);
                if (p.x < 0) {
                    p.set(0, p.y);
                }
            }
        } else if(axisX < -4) {
            if(p.x < screenWidth) {
                p.set(p.x + 400, p.y);
                if (p.x > screenWidth) {
                    p.set(screenWidth, p.y);
                }
            }
        } else {
            p.set(screenWidth/2, p.y);
        }
        if(axisY > 3 ) {
            if(p.y > 0) {
                p.set(p.x, p.y - 400);
                if (p.y < 0) {
                    p.set(p.x, 0);
                }
            }
        } else if(axisY < -3) {
            if(p.y < screenHeight) {
                p.set(p.x, p.y + 400);
                if (p.y > screenHeight) {
                    p.set(p.x, screenHeight);
                }
            }
        } else {
            p.set(p.x, screenHeight/2);
        }


        if(p.x < LEFT_MARGIN && !leftFlag) {
            XTextView.setText("LEFT");
            leftFlag = true;
            FLleft.setBackgroundColor(0x330099CC);
        } else if(p.x > LEFT_MARGIN && leftFlag) {
            XTextView.setText("MIDDLE");
            leftFlag = false;
            FLleft.setBackgroundColor(0xFF0099CC);
        }
        if(p.x > RIGHT_MARGIN && !rightFlag) {
            XTextView.setText("RIGHT");
            rightFlag = true;
            FLright.setBackgroundColor(0x33669900);
        } else if(p.x < RIGHT_MARGIN && rightFlag) {
            XTextView.setText("MIDDLE");
            rightFlag = false;
            FLright.setBackgroundColor(0xFF669900);
        }
        if(p.y > TOP_MARGIN && !topFlag) {
            XTextView.setText("TOP");
            topFlag = true;
            FLtop.setBackgroundColor(0x33AA66CC);
        } else if(p.y < TOP_MARGIN && topFlag) {
            XTextView.setText("MIDDLE");
            topFlag = false;
            FLtop.setBackgroundColor(0xFFAA66CC);
        }
        if(p.y < BOTTOM_MARGIN && !bottomFlag) {
            XTextView.setText("BOTTOM");
            bottomFlag = true;
            FLbottom.setBackgroundColor(0x33ff8800);
        } else if(p.y > BOTTOM_MARGIN && bottomFlag) {
            XTextView.setText("MIDDLE");
            bottomFlag = false;
            FLbottom.setBackgroundColor(0xFFff8800);
        }
        */
    }



    @Override
    protected void onResume() {
        sensorManager.registerListener(this, accelSensor, SensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();
    }
    @Override
    protected void onPause() {
        sensorManager.unregisterListener(this);
        super.onPause();
    }

}
