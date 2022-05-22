package com.example.netflix.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import com.example.netflix.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashSreeen extends AppCompatActivity {

    ProgressBar progressBar;
    static int counter=0;
    static int duration=5000;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_sreeen);
        getSupportActionBar().hide();
        progressBar=findViewById(R.id.progressBar);
        progress();
        start();

    }
    public void progress()
    {
        Timer timer = new Timer();
        TimerTask timerTask=new TimerTask() {
            @Override
            public void run()
            {
            counter++;
            progressBar.setProgress(counter);
            if(counter==5000)
            {

                timer.cancel();
            }
            }
        };
        timer.schedule(timerTask,0,5000);
    }

    public void start()
    {
          new Handler().postDelayed(new Runnable() {
              @Override
              public void run() {
                  Intent intent=new Intent(SplashSreeen.this,SigninActivity.class);
                  startActivity(intent);
                  finish();
              }
          },duration);



    }


}