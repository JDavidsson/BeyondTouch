package com.example.beyondtouch;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * Created by jd on 2017-05-17.
 */

public class TimerFragment extends BaseFragment {


    public TimerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        taskHandler = new Handler(){
            public void dispatchMessage(android.os.Message msg){

                Bundle bundle = new Bundle();
                switch(msg.what) {
                    case RIGHT_TIMER:


                        break;
                    case BOTTOM_TIMER:


                        break;

                    case LEFT_TIMER:

                        break;
                    case TOP_TIMER:

                        break;
                }
                vibrate();
            };
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //v = inflater.inflate(R.layout.fragment_home, container, false);
        v = inflater.inflate(R.layout.circle_layout_timer, container, false);
        getActivity().setTitle(getContext().getString(R.string.title_timer));
        super.onCreateView(inflater,container,savedInstanceState);
        return v;
    }



}