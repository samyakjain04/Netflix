package com.example.netflix.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.netflix.R;

import java.util.Timer;
import java.util.TimerTask;

public class StepTwo extends AppCompatActivity {
    String planname,plancost,planformatofcost,UserEmailId,UserPassword;
    TextView Signin,step2of3;

    ProgressBar progressbarsteptwo;
    Button continuesteptwo;
    EditText emailedittextsteptwo,passwordedittextsteptwo;
    static int counter=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_two);
        Signin=findViewById(R.id.Signin);
        progressbarsteptwo=findViewById(R.id.progressbarsteptwo);
        progressbarsteptwo.setVisibility(View.GONE);
        continuesteptwo=findViewById(R.id.continuesteptwo);
        passwordedittextsteptwo=findViewById(R.id.passwordedittextsteptwo);
        emailedittextsteptwo=findViewById(R.id.emailedittextsteptwo);
        step2of3=findViewById(R.id.step1of3);


        Intent i= getIntent();
        planname=i.getStringExtra("PlanName");
        plancost=i.getStringExtra("PlanCost");
        planformatofcost=i.getStringExtra("PlanCostFormat");
        Toast.makeText(this,""+planname+"\n"+plancost+"\n"+planformatofcost,Toast.LENGTH_SHORT).show();

        Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(StepTwo.this,SigninActivity.class);
                startActivity(intent);

            }
        });


        continuesteptwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserEmailId=emailedittextsteptwo.getText().toString();
                UserPassword=passwordedittextsteptwo.getText().toString();
                Intent intent=new Intent(StepTwo.this,StepThree.class);
                intent.putExtra("PlanName",planname);
                intent.putExtra("PlanCost",plancost);
                intent.putExtra("PlanCostFormat",planformatofcost);
                intent.putExtra("EmailId",UserEmailId);
                intent.putExtra("Password",UserPassword);
                startActivity(intent);

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
                progressbarsteptwo.setProgress(counter);
                if(counter==1000)
                {

                    timer.cancel();
                }
            }
        };
        timer.schedule(timerTask,0,100);


    }

}