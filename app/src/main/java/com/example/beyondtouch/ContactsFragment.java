package com.example.beyondtouch;

import android.content.Intent;
import android.hardware.SensorEvent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ContactsFragment extends BaseFragment {

    public ContactsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        taskHandler = new Handler(){
            public void dispatchMessage(android.os.Message msg){
                if(isAdded()) {
                    Intent intent = null;
                    switch (msg.what) {
                        case RIGHT_TIMER:
                            Log.d("RIGHT", "RIGHT");
                            //Intent intent = new Intent(Intent.ACTION_CALL);
                            intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel:" + "90510"));
                            startActivity(intent);
                            break;
                        case BOTTOM_TIMER:
                            Log.d("BOTTOM", "BOTTOM");
                            //Intent intent = new Intent(Intent.ACTION_CALL);
                            intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel:" + "90510"));
                            startActivity(intent);
                            break;
                        case LEFT_TIMER:
                            Log.d("LEFT", "LEFT");
                            //Intent intent = new Intent(Intent.ACTION_CALL);
                            intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel:" + "90510"));
                            startActivity(intent);
                            startActivity(intent);
                            break;
                        case TOP_TIMER:
                            Log.d("TOP", "TOP");
                            //Intent intent = new Intent(Intent.ACTION_CALL);
                            intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel:" + "90510"));
                            startActivity(intent);
                            startActivity(intent);
                            break;
                    }
                    vibrate();
                }
            };
        };
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        super.onSensorChanged(event);
        // Put shake code from MainFragment here, if to be used.

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_contacts, container, false);
        getActivity().setTitle(getContext().getString(R.string.title_home) + " >> " + "Contacs");
        super.onCreateView(inflater,container,savedInstanceState);
        return v;
    }
}