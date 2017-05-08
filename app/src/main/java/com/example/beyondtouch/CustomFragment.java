package com.example.beyondtouch;

import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.Fragment;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by Christoffer on 2017-05-03.
 */

public class CustomFragment extends Fragment {

    View thisView;
    final static int LEFT = 0, TOP = 1, RIGHT = 2, BOTTOM = 3;
    int leftId = -1, rightId = -1, topId = -1, bottomId = -1;
    FrameLayout leftFL, rightFL, topFL, bottomFL;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_custom, container, false);
        leftFL = new FrameLayout(getActivity());
        rightFL = new FrameLayout(getActivity());
        topFL = new FrameLayout(getActivity());
        bottomFL = new FrameLayout(getActivity());
        leftFL.setId(leftId);
        rightFL.setId(rightId);
        topFL.setId(topId);
        bottomFL.setId(bottomId);
        leftId = View.generateViewId();
        rightId = View.generateViewId();
        topId = View.generateViewId();
        bottomId = View.generateViewId();
        LayoutInflater.from(getActivity()).inflate(leftId, null);
        LayoutInflater.from(getActivity()).inflate(rightId, null);
        LayoutInflater.from(getActivity()).inflate(topId, null);
        LayoutInflater.from(getActivity()).inflate(bottomId, null);
        //createView(BOTTOM, "5555");
        //createView(LEFT, "5555");
        //createView(TOP, "5555");
        //createView(RIGHT, "5555");

        /*
        FrameLayout newFrameLayout = null;
        switch(position){
            case LEFT :
                newFrameLayout = leftFL;
                break;
            case TOP :
                newFrameLayout = topFL;
                break;
            case RIGHT :
                newFrameLayout = rightFL;
                break;
            case BOTTOM :
                newFrameLayout = bottomFL;
                break;
        }
        int id = newFrameLayout.getId();
        ConstraintSet set = new ConstraintSet();
        set.constrainHeight(id, 0);
        set.constrainWidth(id, 70);
        newFrameLayout.setTextAlignment(FrameLayout.TEXT_ALIGNMENT_CENTER);

        switch(position){
            case BOTTOM: {
                set.setMargin(id, ConstraintSet.RIGHT, 0);
                newFrameLayout.setBackgroundColor(Integer.parseInt(0xFF+color));
                set.connect(id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
                set.setHorizontalBias(id, 0);
                set.connect(id, ConstraintSet.RIGHT, rightId, ConstraintSet.LEFT);
                set.connect(id, ConstraintSet.LEFT, leftId, ConstraintSet.RIGHT);
            }
            break;
            case LEFT : {
                set.setMargin(id, ConstraintSet.BOTTOM, 0);
                set.setMargin(id, ConstraintSet.TOP, 0);
                set.connect(id, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
                set.setVerticalBias(id, 1);
                set.setMargin(id, ConstraintSet.BOTTOM, 0);
                set.setMargin(id, ConstraintSet.TOP, 0);
                set.connect(id, ConstraintSet.BOTTOM, bottomId, ConstraintSet.TOP);
                set.connect(id,  ConstraintSet.TOP, topId, ConstraintSet.BOTTOM);
            }
            break;
            case TOP : {
                set.connect(id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
                set.connect(id, ConstraintSet.LEFT, leftId, ConstraintSet.RIGHT);
                set.setMargin(id, ConstraintSet.LEFT, 0);
                set.setMargin(id, ConstraintSet.RIGHT, 0);
                set.connect(id, ConstraintSet.RIGHT, rightId, ConstraintSet.LEFT);
            }
            break;
            case RIGHT : {
                set.setMargin(id, ConstraintSet.BOTTOM, 0);
                set.setMargin(id, ConstraintSet.TOP, 0);
                set.setMargin(id, ConstraintSet.RIGHT, 0);
                set.setVerticalBias(id, 0);
                set.connect(id, ConstraintSet.BOTTOM, bottomId, ConstraintSet.TOP);
                set.connect(id, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
                set.connect(id, ConstraintSet.TOP, topId, ConstraintSet.BOTTOM);
            }
        }
        ConstraintLayout layout = (ConstraintLayout) getActivity().findViewById(R.id.customFragment);
        set.applyTo(layout);
        return newFrameLayout;
        */
        ConstraintLayout layout = (ConstraintLayout)getActivity().findViewById(R.id.container_custom_fragment);
        //Log.e("HEJ", layout.toString());
        ConstraintSet set = new ConstraintSet();

        ImageView view = new ImageView(getActivity());
        int tempid = View.generateViewId();
        view.setId(tempid);
        LayoutInflater.from(getActivity()).inflate(tempid, null);
        layout.addView(view);
        set.clone(layout);
        view.setBackgroundColor(0xFFFFFFFF);
        set.connect(view.getId(), ConstraintSet.TOP, layout.getId(), ConstraintSet.TOP, 60);
        set.applyTo(layout);
        return v;

    }

    public View simpleLayoutSet(){
        ConstraintLayout layout = (ConstraintLayout)getActivity().findViewById(R.id.container_custom_fragment);
        Log.e("HEJ", layout.toString());
        ConstraintSet set = new ConstraintSet();

        ImageView view = new ImageView(getActivity());
        layout.addView(view, 1);
        set.clone(layout);
        view.setBackgroundColor(0xFFFFFFFF);
        set.connect(view.getId(), ConstraintSet.TOP, layout.getId(), ConstraintSet.TOP, 60);
        set.applyTo(layout);
        return layout;
    }




}
