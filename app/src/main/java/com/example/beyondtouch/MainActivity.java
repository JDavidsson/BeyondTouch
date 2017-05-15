package com.example.beyondtouch;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    CustomFragment ff;
    private BroadcastReceiver receiver;
    private MediaPlayer mp2;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        mp2 = MediaPlayer.create(this,R.raw.up);
        mp2.setAudioStreamType(AudioManager.STREAM_MUSIC);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(getApplicationContext(), ProximityListener.class));

        Fragment ff = new HomeFragment();
        //ff = new CustomFragment();

         receiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent == null)
                    return;
                if (intent.filterEquals(new Intent("Proximity"))){
                    System.out.println("Proximity intent recived");
                    mp2.start();
                    Intent i = getBaseContext().getPackageManager()
                            .getLaunchIntentForPackage(getBaseContext().getPackageName());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
                else {
                    //do something you need when broadcast received
                    System.out.println("RECIVED");
                    Intent i = getBaseContext().getPackageManager()
                            .getLaunchIntentForPackage(getBaseContext().getPackageName());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }

            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction("Proximity");

        this.registerReceiver(receiver, filter);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, ff).commit();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction("Proximity");
        try{
            this.registerReceiver(receiver, filter);
        }catch(IllegalArgumentException e) {
            System.err.println(e.getStackTrace() + ": Unable to stop activity. Receiver not registered: null");
        }
    }

    @Override
    public void onDestroy() {
        try{
            this.unregisterReceiver(receiver);
            receiver = null;
        }catch(IllegalArgumentException e) {
            System.err.println(e.getStackTrace() + ": Unable to stop activity. Receiver not registered: null");
        }
        super.onDestroy();
    }
}