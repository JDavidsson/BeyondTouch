package com.example.beyondtouch;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.AlarmClock;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;

public class HomeFragment extends BaseFragment{

    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        taskHandler = new Handler(){
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
                        Bundle bundle = new Bundle();
                        bundle.putInt("Level", ContactsFragment.SUBLEVEL_CONTACTS);
                        //Starts a new fragment (like this one)
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ContactsFragment fragment = new ContactsFragment();
                        fragment.setArguments(bundle);
                        ft.replace(R.id.container, fragment);
                        ft.addToBackStack(null);
                        ft.commit();

                        //aca.vibrate();
                        break;
                    case TOP_TIMER:
                        Log.d("TOP", "TOP");
                        //aca.vibrate();
                        /*
                        Calendar c = Calendar.getInstance();
                        Intent intent4 = new Intent(Intent.ACTION_INSERT)
                                .setData(CalendarContract.Events.CONTENT_URI)
                                .putExtra(CalendarContract.Events.TITLE, "Tvättid")
                                .putExtra(CalendarContract.Events.EVENT_LOCATION, "Tvättstugan")
                                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, System.currentTimeMillis())
                                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, (System.currentTimeMillis() + 1000*60*60));
                        if (intent4.resolveActivity(getActivity().getPackageManager()) != null) {
                            startActivity(intent4);
                        }
                        */
                        ConstraintLayout cl = (ConstraintLayout)v.findViewById(R.id.information_holder);
                        cl.setVisibility(View.VISIBLE);
                        onPause();
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
        v = inflater.inflate(R.layout.circle_layout_home, container, false);
        getActivity().setTitle(getContext().getString(R.string.title_home));
        super.onCreateView(inflater,container,savedInstanceState);
        return v;
    }
}
