package com.example.beyondtouch;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.AlarmClock;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.Timer;

public class HomeFragment extends Fragment implements SensorEventListener {

    private OnFragmentInteractionListener mListener;
    private SensorManager sensorManager;
    private Point p;
    private Sensor accelSensor;
    private int screenHeight, screenWidth;
    private View FLleft, FLright, FLtop, FLbottom;
    private static int RIGHT_MARGIN, LEFT_MARGIN, TOP_MARGIN, BOTTOM_MARGIN;
    private boolean leftFlag, rightFlag, topFlag, bottomFlag;
    public final static int RIGHT_TIMER = 0, TOP_TIMER = 1, LEFT_TIMER = 2, BOTTOM_TIMER = 3;
    private Timer timer;
    private OurTimerTask ott;
    public static final int time = 750; // time elapsed before app opens
    public final static float defaultAlpha = 0.3f; // alpha value when not tilted
    public final static float startAlpha = 0.5f; // alpha value when a tilt is initialized
    private View v;
    private ValueAnimator animationTop;
    private ValueAnimator animationRight;
    private ValueAnimator animationBottom;
    private ValueAnimator animationLeft;
    private MediaPlayer mp1;
    private MediaPlayer mp2;


    public HomeFragment() {
    }

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
        mp2 = MediaPlayer.create(getActivity(),R.raw.up);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_home, container, false);
        FLleft =(FrameLayout) v.findViewById(R.id.frameLayoutLeft);
        FLright =(FrameLayout) v.findViewById(R.id.frameLayoutRight);
        FLtop =(FrameLayout) v.findViewById(R.id.frameLayoutTop);
        FLbottom=(FrameLayout) v.findViewById(R.id.frameLayoutBottom);
        getActivity().setTitle(getContext().getString(R.string.title_home));

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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
    private void startAnimation(ValueAnimator animation){
        if(!animation.isStarted()) {
            animation.setDuration(time);
            animation.start();
        }
    }
    // stops coloranimation
    private void stopAnimation(ValueAnimator animation){
        animation.cancel();
    }



    private void playOneLevelDown(){
        mp1.start();
    }
    private void playOneLevelUp(){
        mp2.start();
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

    public void vibrate(){
        Vibrator v = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 100 milliseconds
        v.vibrate(100);
    }

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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private Handler taskHandler = new Handler(){
        public void dispatchMessage(android.os.Message msg){
            switch(msg.what) {
                case RIGHT_TIMER:
                    //aca.vibrate();
                    Log.d("RIGHT", "RIGHT");

                    Intent intent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
                    if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                        startActivity(intent);
                    }

                    break;
                case BOTTOM_TIMER:
                    Log.d("BOTTOM", "BOTTOM");
                    //aca.vibrate();
                    Intent intent2 = new Intent(AlarmClock.ACTION_SET_ALARM);
                    if (intent2.resolveActivity(getActivity().getPackageManager()) != null) {
                        intent2.putExtra(AlarmClock.EXTRA_HOUR, 8);
                        intent2.putExtra(AlarmClock.EXTRA_MINUTES, 10);
                        startActivity(intent2);
                    }
                    break;
                case LEFT_TIMER:
                    Log.d("LEFT", "LEFT");

                    playOneLevelDown();
                    //Starts a new fragment (like this one)
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.container, new ContactsFragment());
                    ft.addToBackStack(null);
                    ft.commit();

                    //aca.vibrate();
                    break;
                case TOP_TIMER:
                    Log.d("TOP", "TOP");
                    //aca.vibrate();
                    Intent intent4 = new Intent(AlarmClock.ACTION_SHOW_ALARMS);
                    if (intent4.resolveActivity(getActivity().getPackageManager()) != null) {
                        startActivity(intent4);
                    }
                    break;
            }
            vibrate();
        };
    };
}
