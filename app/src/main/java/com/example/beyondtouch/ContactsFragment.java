package com.example.beyondtouch;

import android.content.Intent;
import android.hardware.SensorEvent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.AlarmClock;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactsFragment extends BaseFragment {

    public final static int SUBLEVEL_MAT = 1, SUBLEVEL_FAMILJ = 2, SUBLEVEL_CONTACTS = 3, SUBLEVEL_TIMES = 4, SUBLEVEL_ALARM = 5, SUBLEVEL_TIMER = 6;
    public final static int ACTION_NEWSUBLEVEL = 14, ACTION_CALL = 15, ACTION_ALARM = 16, ACTION_TIMER = 17;
    public final static int BLANK = 0;

    public final static int CIRCLE_CONTACTS = R.drawable.circle4_nostroke, CIRCLE_MAT = R.drawable.circle5_nostroke, CIRCLE_FAMILJ = R.drawable.circle6_nostroke,
            CIRCLE_ALARM = R.drawable.circle7_nostroke, CIRCLE_TIMER = R.drawable.circle8_nostroke;
    public final static int CIRCLE_CONTACTS_SUBLEVEL = R.drawable.circle4, CIRCLE_MAT_SUBLEVEL = R.drawable.circle5, CIRCLE_FAMILJ_SUBLEVEL = R.drawable.circle6,
            CIRCLE_ALARM_SUBLEVEL = R.drawable.circle7, CIRCLE_TIMER_SUBLEVEL = R.drawable.circle8;
    private Bundle bundle;

    public ContactsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        taskHandler = new Handler(){
            public void dispatchMessage(android.os.Message msg){

                if(isAdded()) {
                    Bundle bundle = new Bundle();
                    switch (msg.what) {
                        case RIGHT_TIMER:
                            Log.d("LEFT", "LEFT");
                            playOneLevelDown();
                            //Starts a new fragment (like this one)
                            FragmentTransaction ftRight = getFragmentManager().beginTransaction();
                            FoodFragment fragmenRight = new FoodFragment();
                            ftRight.replace(R.id.container, fragmenRight);
                            ftRight.addToBackStack(null);
                            ftRight.commit();

                            break;
                        case BOTTOM_TIMER:

                            //Intent intent = new Intent(Intent.ACTION_CALL);
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel:" + "90510"));
                            startActivity(intent);
                            break;

                        case LEFT_TIMER:

                            Log.d("LEFT", "LEFT");
                            playOneLevelDown();
                            //Starts a new fragment (like this one)
                            FragmentTransaction ftLeft = getFragmentManager().beginTransaction();
                            FamilyFragment fragmentLeft = new FamilyFragment();
                            ftLeft.replace(R.id.container, fragmentLeft);
                            ftLeft.addToBackStack(null);
                            ftLeft.commit();

                            break;
                        case TOP_TIMER:
                            //Intent intent = new Intent(Intent.ACTION_CALL);
                            Intent intent2 = new Intent(Intent.ACTION_DIAL);
                            intent2.setData(Uri.parse("tel:" + "90510"));
                            startActivity(intent2);

                            break;
                    }
                    vibrate();
                }
            };
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //v = inflater.inflate(R.layout.fragment_home, container, false);
        v = inflater.inflate(R.layout.circle_layout_contacts, container, false);
        getActivity().setTitle(getContext().getString(R.string.title_contacts ));
        super.onCreateView(inflater,container,savedInstanceState);
        return v;
    }

}