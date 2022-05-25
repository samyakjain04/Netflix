package com.example.netflix.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.netflix.Mainscreens.Mainscreen;
import com.example.netflix.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Timer;
import java.util.TimerTask;

public class SigninActivity extends AppCompatActivity {
    ProgressBar progressBar;
    TextView forgetpasswordtextview,signuptextview;
     Button signinbutton;
    static int counter=0;
    EditText emailedittext,passwordedittext;
    String authemail,authpassword;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        getSupportActionBar().hide();

        forgetpasswordtextview = findViewById(R.id.forgetpasswordtextview);
        signuptextview = findViewById(R.id.signuptextview);
        signinbutton = findViewById(R.id.signinbutton);
        emailedittext=findViewById(R.id.emailedittext);
        passwordedittext=findViewById(R.id.passwordedittext);
          firebaseAuth= FirebaseAuth.getInstance();

        signinbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authemail=emailedittext.getText().toString();
                authpassword=passwordedittext.getText().toString();

                firebaseAuth.signInWithEmailAndPassword(authemail,authpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(SigninActivity.this, "sucesses full", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SigninActivity.this, Mainscreen.class);
                            startActivity(intent);
                        }
                        else
                        {

                            Toast.makeText(SigninActivity.this, "wrong credential", Toast.LENGTH_SHORT).show();

                        }
                    }

                });

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