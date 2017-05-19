package com.example.beyondtouch;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jd on 2017-05-17.
 */

public class ClockFragment extends BaseFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        taskHandler = new Handler(){
            public void dispatchMessage(android.os.Message msg){

                if(isAdded()) {
                    Bundle bundle = new Bundle();
                    switch (msg.what) {
                        case RIGHT_TIMER:

                            playOneLevelDown();
                            //Starts a new fragment (like this one)
                            FragmentTransaction ftRight = getFragmentManager().beginTransaction();
                            AlarmFragment fragmenRight = new AlarmFragment();
                            ftRight.replace(R.id.container, fragmenRight);
                            ftRight.addToBackStack(null);
                            ftRight.commit();

                            vibrate();
                            break;
                        case BOTTOM_TIMER:


                            break;

                        case LEFT_TIMER:
                            playOneLevelDown();
                            //Starts a new fragment (like this one)
                            FragmentTransaction ftLeft = getFragmentManager().beginTransaction();
                            TimerFragment fragmentLeft = new TimerFragment();
                            ftLeft.replace(R.id.container, fragmentLeft);
                            ftLeft.addToBackStack(null);
                            ftLeft.commit();

                            vibrate();
                            break;
                        case TOP_TIMER:

                            break;
                    }
                }
            };
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //v = inflater.inflate(R.layout.fragment_home, container, false);
        v = inflater.inflate(R.layout.circle_layout_clock, container, false);
        getActivity().setTitle(getContext().getString(R.string.title_clock));
        super.onCreateView(inflater,container,savedInstanceState);
        return v;
    }



}
