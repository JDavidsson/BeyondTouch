package com.example.beyondtouch;

import android.content.Intent;
import android.hardware.SensorEvent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactsFragment extends BaseFragment {

    public final static int SUBLEVEL_MAT = 1, SUBLEVEL_FAMILJ = 2, SUBLEVEL_CONTACTS = 3;
    public final static int ACTION_NEWSUBLEVEL = 4, ACTION_CALL = 5;
    private Bundle bundle;
    private ImageView mainCircle;

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
                bundle.putInt("circleSrc", R.drawable.circle4_nostroke);
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
                bundle.putInt("circleSrc", R.drawable.circle5_nostroke);
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
                bundle.putInt("circleSrc", R.drawable.circle6_nostroke);
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
                //Sublevel mat
                ((ImageView)view.findViewById(R.id.frameLayoutRight)).setImageResource(R.drawable.circle5);
            }
            if(bundle.getInt("LEFT_action") == ACTION_NEWSUBLEVEL){
                //Sublevel familj
                ((ImageView)view.findViewById(R.id.frameLayoutLeft)).setImageResource(R.drawable.circle6);
            }
        } else {
            Log.e("Bundle", "No bundle");
        }
    }

    private void startNewSublevel(Bundle bundle){
        //Starts a new fragment (like this one)
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
        if(level == SUBLEVEL_CONTACTS){
            Log.e("Level", "Contacts");
        } else if(level == SUBLEVEL_MAT){
            Log.e("Level", "Mat");
        } else if(level == SUBLEVEL_FAMILJ) {
            Log.e("Level", "Familj");
        }
        setUpLowerLevel(level);

        taskHandler = new Handler(){
            public void dispatchMessage(android.os.Message msg){
                if(isAdded()) {
                    Intent intent = null;
                    int action;
                    switch (msg.what) {
                        case RIGHT_TIMER:
                            Log.d("RIGHT", "RIGHT");
                            //Intent intent = new Intent(Intent.ACTION_CALL);
                            action = bundle.getInt("RIGHT_action");
                            if(action == ACTION_CALL){
                                intent = new Intent(Intent.ACTION_DIAL);
                                intent.setData(Uri.parse("tel:" + bundle.getString("RIGHT_info")));
                                startActivity(intent);
                            } else if (action == ACTION_NEWSUBLEVEL){
                                startNewSublevel(setUpLowerLevel(bundle.getInt("RIGHT_info")));
                            }
                            break;
                        case BOTTOM_TIMER:
                            Log.d("BOTTOM", "BOTTOM");
                            //Intent intent = new Intent(Intent.ACTION_CALL);
                            action = bundle.getInt("LEFT_action");
                            if(action == ACTION_CALL){
                                intent = new Intent(Intent.ACTION_DIAL);
                                intent.setData(Uri.parse("tel:" + bundle.getString("BOTTOM_info")));
                                startActivity(intent);
                            } else if (action == ACTION_NEWSUBLEVEL){
                                startNewSublevel(setUpLowerLevel(bundle.getInt("BOTTOM_info")));
                            }
                            break;
                        case LEFT_TIMER:
                            Log.d("LEFT", "LEFT");
                            action = bundle.getInt("LEFT_action");
                            if(action == ACTION_CALL){
                                intent = new Intent(Intent.ACTION_DIAL);
                                intent.setData(Uri.parse("tel:" + bundle.getString("LEFT_info")));
                                startActivity(intent);
                            } else if (action == ACTION_NEWSUBLEVEL){
                                startNewSublevel(setUpLowerLevel(bundle.getInt("LEFT_info")));
                            }
                            break;
                        case TOP_TIMER:
                            Log.d("TOP", "TOP");
                            action = bundle.getInt("LEFT_action");
                            if(action == ACTION_CALL){
                                intent = new Intent(Intent.ACTION_DIAL);
                                intent.setData(Uri.parse("tel:" + bundle.getString("TOP_info")));
                                startActivity(intent);
                            } else if (action == ACTION_NEWSUBLEVEL){
                                startNewSublevel(setUpLowerLevel(bundle.getInt("TOP_info")));
                            }
                            break;
                    }
                    vibrate();
                }
            };
        };
    }

    private String verticalText(String s){
        String temp = "";
        for(int i = 0; i < s.length()-1; i++){
            temp += s.charAt(i) + "\n";
        }
        return (temp + s.charAt(s.length()-1));
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
        getActivity().setTitle(getContext().getString(R.string.title_home) + " >> " + "Contacts");
        super.onCreateView(inflater,container,savedInstanceState);
        visualSetUp(v);
        return v;
    }
}
