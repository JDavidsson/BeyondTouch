package com.example.beyondtouch;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.TimerTask;

/**
 * Created by Philip skola on 2017-04-04.
 */

public class OurTimerTask extends TimerTask {

    private int area;
    private Handler handler;

    OurTimerTask(int area, Handler handler){
        super();
        this.area = area;
        this.handler = handler;
    }

    boolean isAlreadyActive(int currArea){
        return area == currArea;
    }


    @Override
    public void run() {
        handler.sendEmptyMessage(area);

    }
}
