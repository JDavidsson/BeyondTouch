package com.example.beyondtouch;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.Timer;

/**
 * Created by jd on 2017-05-13.
 */

public class BaseFragment extends Fragment implements SensorEventListener {

    // From Home+Contacts (-Fragment)
    protected HomeFragment.OnFragmentInteractionListener mListener;
    protected SensorManager sensorManager;
    protected Point p;
    protected Sensor accelSensor;
    protected int screenHeight, screenWidth;
    protected View FLleft, FLright, FLtop, FLbottom;
    protected static int RIGHT_MARGIN, LEFT_MARGIN, TOP_MARGIN, BOTTOM_MARGIN;
    protected boolean leftFlag, rightFlag, topFlag, bottomFlag;
    public final static int RIGHT_TIMER = 0, TOP_TIMER = 1, LEFT_TIMER = 2, BOTTOM_TIMER = 3;
    protected Timer timer;
    protected OurTimerTask ott;
    public static final int time = 750; // time elapsed before app opens
    public final static float defaultAlpha = 0.3f; // alpha value when not tilted
    public final static float startAlpha = 0.5f; // alpha value when a tilt is initialized
    protected View v;
    protected ValueAnimator animationTop;
    protected ValueAnimator animationRight;
    protected ValueAnimator animationBottom;
    protected ValueAnimator animationLeft;
    protected MediaPlayer mp1;
    protected MediaPlayer mp2;
    protected Handler taskHandler;

    // From MainFragment (Shake function)
    protected long lastUpdate;
    protected long timeOut;
    protected float currX, currY, currZ, lastX, lastY, lastZ;
    protected float proximityValue;
    protected final static int SHAKE_THRESHOLD = 800;
    protected final static int SHAKECOUNT_LIMIT = 2;
    protected int shakeCounter;
    protected Sensor proximitySensor;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        accelSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        p = new Point(0,0);
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
        p.set(screenWidth/2, screenHeight/2);

        /** Needed for coordinate precission */
        RIGHT_MARGIN = screenWidth - 70;
        LEFT_MARGIN = 70;
        TOP_MARGIN = screenHeight - 70;
        BOTTOM_MARGIN = 70;

        timer = new Timer();
        ott = new OurTimerTask(-1,taskHandler);

        mp1 = MediaPlayer.create(getActivity(),R.raw.down);
        mp1.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mp2 = MediaPlayer.create(getActivity(),R.raw.up);
        mp2.setAudioStreamType(AudioManager.STREAM_MUSIC);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        /*FLleft =(FrameLayout) v.findViewById(R.id.frameLayoutLeft);
        FLright =(FrameLayout) v.findViewById(R.id.frameLayoutRight);
        FLtop =(FrameLayout) v.findViewById(R.id.frameLayoutTop);
        FLbottom=(FrameLayout) v.findViewById(R.id.frameLayoutBottom);*/

        //Inflate cirlce layout
        FLleft =(ImageView) v.findViewById(R.id.frameLayoutLeft);
        FLright =(ImageView) v.findViewById(R.id.frameLayoutRight);
        FLtop =(ImageView) v.findViewById(R.id.frameLayoutTop);
        FLbottom=(ImageView) v.findViewById(R.id.frameLayoutBottom);

        /* ---- ANIMATIONS ---- */
        animationTop = ValueAnimator.ofFloat(startAlpha,1f);
        animationTop.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator updatedAnimation) {
                FLtop.setAlpha((float) updatedAnimation.getAnimatedValue());
            }
        });
        animationRight = ValueAnimator.ofFloat(startAlpha,1f);
        animationRight.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator updatedAnimation) {
                FLright.setAlpha((float) updatedAnimation.getAnimatedValue());
            }
        });
        animationLeft = ValueAnimator.ofFloat(startAlpha,1f);
        animationLeft.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator updatedAnimation) {
                FLleft.setAlpha((float) updatedAnimation.getAnimatedValue());
            }
        });
        animationBottom = ValueAnimator.ofFloat(startAlpha,1f);
        animationBottom.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator updatedAnimation) {
                FLbottom.setAlpha((float) updatedAnimation.getAnimatedValue());
            }
        });

        return v;
    }

    protected void playOneLevelDown(){
        mp1.start();
    }
    protected void playOneLevelUp(){ mp2.start(); }

    @Override
    public void onResume() {
        sensorManager.registerListener(this, accelSensor, SensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();
    }
    @Override
    public void onPause() {
        sensorManager.unregisterListener(this);
        super.onPause();
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    public void vibrate(){
        Vibrator v = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 100 milliseconds
        v.vibrate(100);
    }

    // currently being replaced by proximity functionalities in MainActivity and ProximityListener
    protected void moveUpOneLevel(){
        //getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
        //getActivity().getSupportFragmentManager().popBackStack();
    }

    protected void shakePhone(){
        Vibrator v = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 150 milliseconds
        v.vibrate(150);
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
    // start color animation
    protected void startAnimation(ValueAnimator animation){
        if(!animation.isStarted()) {
            animation.setDuration(time);
            animation.start();
        }
    }
    // stops coloranimation
    protected void stopAnimation(ValueAnimator animation){
        animation.cancel();
    }

    public void updatePointer(SensorEvent event){
        float axisX = event.values[0];
        float axisY = event.values[1];
        //coords.setText("X:" + axisX + "\n p.x : " + p.x + "\n Y: " + axisY + "\n p.y " + p.y);

        if(axisX > 4) {
            leftFlag = true;
        } else if(axisX < -4) {
            rightFlag = true;
        } else {
            leftFlag = false;
            rightFlag = false;
        }
        if(axisY > 8 ) {
            bottomFlag = true;
            leftFlag = false;
            rightFlag = false;
        } else if(axisY < 0) {
            topFlag = true;
            rightFlag = false;
            leftFlag = false;
        } else {
            topFlag = false;
            bottomFlag = false;
        }

        if(leftFlag) {
            //XTextView.setText("LEFT");
            if(!ott.isAlreadyActive(LEFT_TIMER)) {
                timer.cancel();
                timer = new Timer();
                ott = new OurTimerTask(LEFT_TIMER, taskHandler);
                timer.schedule(ott, time);
            }FLleft.setAlpha(startAlpha);
            startAnimation(animationLeft);
        } else  {
            stopAnimation(animationLeft);
            FLleft.setAlpha(defaultAlpha);
        }
        if(rightFlag) {
            //XTextView.setText("RIGHT");

            if(!ott.isAlreadyActive(RIGHT_TIMER)) {
                timer.cancel();
                timer = new Timer();
                ott = new OurTimerTask(RIGHT_TIMER, taskHandler);
                timer.schedule(ott, time);
            }
            FLright.setAlpha(startAlpha);
            startAnimation(animationRight);
        } else  {
            stopAnimation(animationRight);
            FLright.setAlpha(defaultAlpha);
        }
        if(topFlag) {
            //XTextView.setText("TOP");
            if(!ott.isAlreadyActive(TOP_TIMER)) {
                timer.cancel();
                timer = new Timer();
                ott = new OurTimerTask(TOP_TIMER, taskHandler);
                timer.schedule(ott, time);
            }FLtop.setAlpha(startAlpha);
            startAnimation(animationTop);
        } else  {
            stopAnimation(animationTop);
            FLtop.setAlpha(defaultAlpha);
        }
        if(bottomFlag) {
            //XTextView.setText("BOTTOM");

            if(!ott.isAlreadyActive(BOTTOM_TIMER)) {
                timer.cancel();
                timer = new Timer();
                ott = new OurTimerTask(BOTTOM_TIMER, taskHandler);
                timer.schedule(ott, time);
            }FLbottom.setAlpha(startAlpha);
            startAnimation(animationBottom);
        } else  {
            stopAnimation(animationBottom);
            FLbottom.setAlpha(defaultAlpha);
        }
        if(!topFlag && !bottomFlag && !rightFlag && !leftFlag){
            timer.cancel();
            //XTextView.setText("MIDDLE");
            ott = new OurTimerTask(-1, taskHandler);
        }


    }
}
