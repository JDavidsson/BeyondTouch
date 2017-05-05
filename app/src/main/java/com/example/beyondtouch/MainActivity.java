package com.example.beyondtouch;

import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

<<<<<<< Updated upstream
    CustomFragment ff;
=======
    private MainFragment mainFragment;
>>>>>>> Stashed changes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Fragment ff = new HomeFragment();
        ff = new CustomFragment();


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, ff).commit();
        }
    }


<<<<<<< Updated upstream
=======





>>>>>>> Stashed changes
}
