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

    public Bundle setUpLowerLevel(int levelType){
        switch(levelType){
            case SUBLEVEL_CONTACTS : {
                bundle.putInt("Level", SUBLEVEL_CONTACTS);
                bundle.putString("RIGHT_name", verticalText("Mat"));
                bundle.putString("LEFT_name", verticalText("Familj"));
                bundle.putString("TOP_name", "Bästisen");
                bundle.putString("BOTTOM_name", "112");
                bundle.putInt("RIGHT_action", ACTION_NEWSUBLEVEL);
                bundle.putInt("LEFT_action", ACTION_NEWSUBLEVEL);
                bundle.putInt("TOP_action", ACTION_CALL);
                bundle.putInt("BOTTOM_action", ACTION_CALL);
                bundle.putInt("RIGHT_info", SUBLEVEL_MAT);
                bundle.putInt("LEFT_info", SUBLEVEL_FAMILJ);
                bundle.putString("TOP_info", "555-343153");
                bundle.putString("BOTTOM_info", "555-341134");
                bundle.putInt("circleSrc", CIRCLE_CONTACTS);
            }
            break;
            case SUBLEVEL_MAT: {
                bundle.putInt("Level", SUBLEVEL_MAT);
                bundle.putString("RIGHT_name", verticalText("Sibylla"));
                bundle.putString("LEFT_name", verticalText("Challes"));
                bundle.putString("TOP_name", "Delphi Pizzeria");
                bundle.putString("BOTTOM_name","Bästa Thaien");
                bundle.putInt("RIGHT_action", ACTION_CALL);
                bundle.putInt("LEFT_action", ACTION_CALL);
                bundle.putInt("TOP_action", ACTION_CALL);
                bundle.putInt("BOTTOM_action", ACTION_CALL);
                bundle.putString("RIGHT_info", "555-051253");
                bundle.putString("LEFT_info", "555-353431");
                bundle.putString("TOP_info", "555-343153");
                bundle.putString("BOTTOM_info", "555-341134");
                bundle.putInt("circleSrc", CIRCLE_MAT);
            }
            break;
            case SUBLEVEL_FAMILJ: {
                bundle.putInt("Level", SUBLEVEL_FAMILJ);
                bundle.putString("RIGHT_name", verticalText("Mamma"));
                bundle.putString("LEFT_name", verticalText("Pappa"));
                bundle.putString("TOP_name", "Syrran");
                bundle.putString("BOTTOM_name", "Brorsan");
                bundle.putInt("RIGHT_action", ACTION_CALL);
                bundle.putInt("LEFT_action", ACTION_CALL);
                bundle.putInt("TOP_action", ACTION_CALL);
                bundle.putInt("BOTTOM_action", ACTION_CALL);
                bundle.putString("RIGHT_info", "555-051253");
                bundle.putString("LEFT_info", "555-353431");
                bundle.putString("TOP_info", "555-343153");
                bundle.putString("BOTTOM_info", "555-341134");
                bundle.putInt("circleSrc", CIRCLE_FAMILJ);
            }
            break;
            case SUBLEVEL_TIMES : {
                bundle.putInt("Level", SUBLEVEL_TIMES);
                bundle.putString("RIGHT_name", verticalText("Alarm"));
                bundle.putString("LEFT_name", verticalText("Timer"));
                bundle.putString("TOP_name", "");
                bundle.putString("BOTTOM_name", "");
                bundle.putInt("RIGHT_action", ACTION_NEWSUBLEVEL);
                bundle.putInt("LEFT_action", ACTION_NEWSUBLEVEL);
                bundle.putInt("TOP_action", BLANK);
                bundle.putInt("BOTTOM_action", BLANK);
                bundle.putInt("RIGHT_info", SUBLEVEL_ALARM);
                bundle.putInt("LEFT_info", SUBLEVEL_TIMER);
                bundle.putString("TOP_info", "");
                bundle.putString("BOTTOM_info", "");
                bundle.putInt("circleSrc", R.drawable.circle3_nostroke);
            }
            break;
            case SUBLEVEL_TIMER : {
                bundle.putInt("Level", SUBLEVEL_TIMER);
                bundle.putString("RIGHT_name", "30");
                bundle.putString("LEFT_name", "10");
                bundle.putString("TOP_name", "15");
                bundle.putString("BOTTOM_name", "5");
                bundle.putInt("RIGHT_action", ACTION_TIMER);
                bundle.putInt("LEFT_action", ACTION_TIMER);
                bundle.putInt("TOP_action", ACTION_TIMER);
                bundle.putInt("BOTTOM_action", ACTION_TIMER);
                bundle.putInt("RIGHT_info", 30);
                bundle.putInt("LEFT_info", 10);
                bundle.putInt("TOP_info", 15);
                bundle.putInt("BOTTOM_info", 5);
                bundle.putInt("circleSrc", CIRCLE_TIMER);
            }
            break;
            case SUBLEVEL_ALARM : {
                bundle.putInt("Level", SUBLEVEL_ALARM);
                bundle.putString("RIGHT_name", "8:10");
                bundle.putString("LEFT_name", "9:20");
                bundle.putString("TOP_name", "10:15");
                bundle.putString("BOTTOM_name", "11:00");
                bundle.putInt("RIGHT_action", ACTION_ALARM);
                bundle.putInt("LEFT_action", ACTION_ALARM);
                bundle.putInt("TOP_action", ACTION_ALARM);
                bundle.putInt("BOTTOM_action", ACTION_ALARM);
                bundle.putString("RIGHT_info", "8 10");
                bundle.putString("LEFT_info", "9 20");
                bundle.putString("TOP_info", "10 15");
                bundle.putString("BOTTOM_info", "11 00");
                bundle.putInt("circleSrc", CIRCLE_ALARM);
            }
            break;
            default : {

            }
            break;
        }
        return bundle;
    }

    private void visualSetUp(View view){
        Bundle bundle = this.getArguments();
        if(bundle != null){
            ((TextView)view.findViewById(R.id.textView_bottom)).setText(bundle.getString("BOTTOM_name"));
            ((TextView)view.findViewById(R.id.textView_left)).setText(bundle.getString("LEFT_name"));
            ((TextView)view.findViewById(R.id.textView_top)).setText(bundle.getString("TOP_name"));
            ((TextView)view.findViewById(R.id.textView_right)).setText(bundle.getString("RIGHT_name"));
            ((ImageView)view.findViewById(R.id.frameLayoutBottom)).setImageResource(bundle.getInt("circleSrc"));
            ((ImageView)view.findViewById(R.id.frameLayoutLeft)).setImageResource(bundle.getInt("circleSrc"));
            ((ImageView)view.findViewById(R.id.frameLayoutRight)).setImageResource(bundle.getInt("circleSrc"));
            ((ImageView)view.findViewById(R.id.frameLayoutTop)).setImageResource(bundle.getInt("circleSrc"));
            Log.e("CircleSrc", bundle.getInt("circleSrc") + "");
            if(bundle.getInt("RIGHT_action") == ACTION_NEWSUBLEVEL){
                int sublevel = bundle.getInt("RIGHT_info");
                if(sublevel == SUBLEVEL_ALARM){
                    ((ImageView)view.findViewById(R.id.frameLayoutRight)).setImageResource(CIRCLE_ALARM_SUBLEVEL);
                } else if (sublevel == SUBLEVEL_MAT){
                    ((ImageView)view.findViewById(R.id.frameLayoutRight)).setImageResource(CIRCLE_MAT_SUBLEVEL);
                }
            } else {
                ((ImageView)view.findViewById(R.id.frameLayoutRight)).setImageAlpha(50);
            }
            if(bundle.getInt("LEFT_action") == ACTION_NEWSUBLEVEL){
                int sublevel = bundle.getInt("LEFT_info");
                switch(sublevel){
                    case SUBLEVEL_FAMILJ :
                        ((ImageView)view.findViewById(R.id.frameLayoutLeft)).setImageResource(CIRCLE_FAMILJ_SUBLEVEL);
                        break;
                    case SUBLEVEL_TIMER :
                        ((ImageView)view.findViewById(R.id.frameLayoutLeft)).setImageResource(CIRCLE_TIMER_SUBLEVEL);
                        break;
                }
            } else {
                ((ImageView)view.findViewById(R.id.frameLayoutLeft)).setImageAlpha(180);
            }
            if(bundle.getInt("TOP_action") == BLANK) {
                ((ImageView)view.findViewById(R.id.frameLayoutTop)).setImageResource(BLANK);
            } else {
                ((ImageView)view.findViewById(R.id.frameLayoutTop)).setImageAlpha(255);
            }
            if(bundle.getInt("BOTTOM_action") == BLANK) {
                ((ImageView)view.findViewById(R.id.frameLayoutBottom)).setImageResource(BLANK);
            } else {
                ((ImageView)view.findViewById(R.id.frameLayoutBottom)).setImageAlpha(100);
            }
        } else {
            Log.e("Bundle", "No bundle");
        }
    }

    private void startNewSublevel(Bundle bundle){
        //Starts a new fragment (like this one)
        playOneLevelDown();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ContactsFragment fragment = new ContactsFragment();
        fragment.setArguments(bundle);
        ft.replace(R.id.container, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int level = 0;
        bundle = this.getArguments();
        if(bundle != null){
            level = bundle.getInt("Level");
        }

        setUpLowerLevel(level);

        taskHandler = new Handler(){
            public void dispatchMessage(android.os.Message msg){
                if(isAdded()) {
                    Intent intent = null;
                    int action = -1;
                    String stringInfo = "";
                    int integerInfo = -1;
                    switch (msg.what) {
                        case RIGHT_TIMER:
                            Log.d("RIGHT", "RIGHT");
                            //Intent intent = new Intent(Intent.ACTION_CALL);
                            action = bundle.getInt("RIGHT_action");
                            stringInfo = bundle.getString("RIGHT_info");
                            integerInfo = bundle.getInt("RIGHT_info");
                        break;
                        case BOTTOM_TIMER:
                            Log.d("BOTTOM", "BOTTOM");
                            //Intent intent = new Intent(Intent.ACTION_CALL);

                            action = bundle.getInt("BOTTOM_action");
                            stringInfo = bundle.getString("BOTTOM_info");
                            integerInfo = bundle.getInt("BOTTOM_info");
                        break;
                        case LEFT_TIMER:
                            Log.d("LEFT", "LEFT");
                            action = bundle.getInt("LEFT_action");
                            stringInfo = bundle.getString("LEFT_info");
                            integerInfo = bundle.getInt("LEFT_info");
                        break;
                        case TOP_TIMER:
                            Log.d("TOP", "TOP");
                            action = bundle.getInt("TOP_action");
                            stringInfo = bundle.getString("TOP_info");
                            integerInfo = bundle.getInt("TOP_info");
                        break;
                    }

                    switch(action){
                        case ACTION_CALL : {
                            intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel:" + stringInfo));
                            startActivity(intent);
                        }
                        break;
                        case ACTION_TIMER : {
                            intent = new Intent(AlarmClock.ACTION_SET_TIMER)
                                    .putExtra(AlarmClock.EXTRA_MESSAGE, "Timer")
                                    .putExtra(AlarmClock.EXTRA_LENGTH, integerInfo*60);
                            startActivity(intent);
                        }
                        break;
                        case ACTION_ALARM : {
                            String[] hourAndMinutes = stringInfo.split(" ");
                            intent = new Intent(AlarmClock.ACTION_SET_ALARM)
                                .putExtra(AlarmClock.EXTRA_HOUR, Integer.parseInt(hourAndMinutes[0]))
                                .putExtra(AlarmClock.EXTRA_MINUTES, Integer.parseInt(hourAndMinutes[1]));
                            startActivity(intent);
                        }
                        break;
                        case ACTION_NEWSUBLEVEL : {
                            startNewSublevel(setUpLowerLevel(integerInfo));
                        }
                        break;
                    }


                    vibrate();
                }
            };
        };
    }

    private String verticalText(String s){
        return s;
        /*
        String temp = "";
        for(int i = 0; i < s.length()-1; i++){
            temp += s.charAt(i) + "\n";
        }
        return (temp + s.charAt(s.length()-1));*/
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
        v = inflater.inflate(R.layout.circle_layout_contacts, container, false);
        super.onCreateView(inflater,container,savedInstanceState);
        visualSetUp(v);
        return v;
    }
}
