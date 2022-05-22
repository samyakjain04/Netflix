package com.example.netflix.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.netflix.Mainscreens.Mainscreen;
import com.example.netflix.R;

import java.util.Timer;
import java.util.TimerTask;

public class SigninActivity extends AppCompatActivity {
    ProgressBar progressBar;
    TextView forgetpasswordtextview,signuptextview;
     Button signinbutton;
    static int counter=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        getSupportActionBar().hide();

        forgetpasswordtextview = findViewById(R.id.forgetpasswordtextview);
        signuptextview = findViewById(R.id.signuptextview);
        signinbutton = findViewById(R.id.signinbutton);

        signinbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SigninActivity.this, Mainscreen.class);
                startActivity(intent);
            }
        });
        signuptextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SigninActivity.this, Swipscreen.class);
                startActivity(intent);
            }
        });

        forgetpasswordtextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SigninActivity.this, "forgot password", Toast.LENGTH_SHORT).show();
            }
        });
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
                    if(counter==500)
                    {

                        timer.cancel();
                    }
                }
            };
            timer.schedule(timerTask,0,100);
        }



}