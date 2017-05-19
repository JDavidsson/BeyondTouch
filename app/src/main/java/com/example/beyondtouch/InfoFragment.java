package com.example.beyondtouch;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jd on 2017-05-17.
 */

public class InfoFragment extends BaseFragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        taskHandler = new Handler(){

            public void dispatchMessage(android.os.Message msg){

            };
        };

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //v = inflater.inflate(R.layout.fragment_home, container, false);
        v = inflater.inflate(R.layout.circle_layout_info, container, false);
        getActivity().setTitle(getContext().getString(R.string.title_info));
        super.onCreateView(inflater,container,savedInstanceState);
        return v;
    }




}