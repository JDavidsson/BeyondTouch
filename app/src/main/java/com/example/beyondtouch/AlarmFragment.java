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
                switch(msg.what) {
                    case RIGHT_TIMER:
                        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                            intent.putExtra(AlarmClock.EXTRA_HOUR, 7);
                            startActivity(intent);
                        }
                        break;
                    case BOTTOM_TIMER:
                        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                            intent.putExtra(AlarmClock.EXTRA_HOUR, 8);
                            intent.putExtra(AlarmClock.EXTRA_MINUTES, 15);
                            startActivity(intent);
                        }
                        break;

                    case LEFT_TIMER:
                        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                            intent.putExtra(AlarmClock.EXTRA_HOUR, 9);
                            intent.putExtra(AlarmClock.EXTRA_MINUTES, 30);
                            startActivity(intent);
                        }
                        break;
                    case TOP_TIMER:
                        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                            intent.putExtra(AlarmClock.EXTRA_HOUR, 10);
                            intent.putExtra(AlarmClock.EXTRA_MINUTES, 30);
                            startActivity(intent);
                        }
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
            v = inflater.inflate(R.layout.circle_layout_alarm, container, false);
            getActivity().setTitle(getContext().getString(R.string.title_alarm));
            super.onCreateView(inflater,container,savedInstanceState);
            return v;
        }



    }
