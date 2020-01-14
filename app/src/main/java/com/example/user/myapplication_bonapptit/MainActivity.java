package com.example.user.myapplication_bonapptit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT=3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    CheckUser();

                }
            }, SPLASH_TIME_OUT);

        }

        //******************************

    public void CheckUser(){
        Boolean Check = Boolean.valueOf(SharedPrefs.readSharedSetting(MainActivity.this, "logged", "true"));

        Intent introIntent = new Intent(MainActivity.this, Main2Activity.class);
        introIntent.putExtra("logged", Check);
        if (Check) {
            startActivity(introIntent);
        }
        else{
            Intent intent = new Intent(MainActivity.this, menur1Activity.class);
            startActivity(intent);
        }
    }

    //
    }

