package com.example.beyondtouch;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.AlarmClock;
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

public class AlarmFragment extends BaseFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            taskHandler = new Handler(){
                public void dispatchMessage(android.os.Message msg){
                Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
                Bundle bundle = new Bundle();
                    int hour = 0;
                    int minutes = 0;
                switch(msg.what) {
                    case RIGHT_TIMER:
                        hour = 9;
                        minutes = 20;
                        break;
                    case BOTTOM_TIMER:
                        hour = 10;
                        minutes = 15;
                        break;

                    case LEFT_TIMER:
                        hour = 11;
                        minutes = 0;
                        break;
                    case TOP_TIMER:
                        hour = 8;
                        minutes = 10;
                        break;
                }

                    intent.putExtra(AlarmClock.EXTRA_HOUR, hour);
                    intent.putExtra(AlarmClock.EXTRA_MINUTES, minutes);
                    startActivity(intent);
                vibrate();
                };
            };
            }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            // Inflate the layout for this fragment

            //v = inflater.inflate(R.layout.fragment_home, container, false);
            v = inflater.inflate(R.layout.circle_layout_alarm, container, false);
            getActivity().setTitle(getContext().getString(R.string.title_alarm));
            super.onCreateView(inflater,container,savedInstanceState);
            return v;
        }



    }
