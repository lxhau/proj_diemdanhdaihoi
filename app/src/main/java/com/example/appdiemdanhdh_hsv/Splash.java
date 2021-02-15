package com.example.appdiemdanhdh_hsv;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.appdiemdanhdh_hsv.R;
import com.google.firebase.auth.FirebaseAuth;

public class Splash extends AppCompatActivity {

    private ImageView iv;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.dark_nav)); // Navigation bar the soft bottom of some phones like nexus and some Samsung note series
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.statusbar)); //status bar or the time bar at the top
        }

        iv=(ImageView) findViewById(R.id.logosplash);
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.animation_fade_out);
        iv.startAnimation(animation);

        mAuth = FirebaseAuth.getInstance();

        Thread timer=new Thread(){
            public void run(){
                try{
                    sleep(3000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                finally {
                    if (mAuth.getCurrentUser() != null) {
                        startActivity(new Intent(getApplicationContext(),Info_BKT.class));
                        finish();
                    }
                    else{
                        startActivity(new Intent(getApplicationContext(), Wellcome.class));
                        finish();
                    }
                }
            }
        };
        timer.start();
    }
}
