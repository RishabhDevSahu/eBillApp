package com.example.ebill;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity {

    SharedPref sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        sharedPref = new SharedPref(getApplicationContext());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(sharedPref.getLoginInfo()){
                    Toast.makeText(SplashActivity.this, "Created By Rishabh Dev Sahu", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                    SplashActivity.this.finish();
                }
                else{
                    Toast.makeText(SplashActivity.this, "Created By Rishabh Dev Sahu", Toast.LENGTH_SHORT).show();
                    Intent i2 = new Intent(SplashActivity.this, UserLoginActivity.class);
                    startActivity(i2);
                    SplashActivity.this.finish();
                }

            }
        }, 1000);
    }
}