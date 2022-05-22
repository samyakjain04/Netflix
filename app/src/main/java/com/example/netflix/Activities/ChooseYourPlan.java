package com.example.netflix.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.netflix.R;

public class ChooseYourPlan extends AppCompatActivity {
  RadioButton radiobuttonforbasic,radiobuttonforstandard,radiobuttonforpremium;
  Button continuebuttonchooseplan;
  TextView Signin;
  String planname,plancost,planformatofcost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_your_plan);
        getSupportActionBar().hide();
        Signin = findViewById(R.id.Signin);
        continuebuttonchooseplan = findViewById(R.id.continuebuttonchooseplan);
        radiobuttonforbasic = findViewById(R.id.radiobuttonforbasic);
        radiobuttonforstandard = findViewById(R.id.radiobuttonforstandard);
        radiobuttonforpremium = findViewById(R.id.radiobuttonforpremium);
        radiobuttonforbasic.setOnCheckedChangeListener(new Radio_check());
        radiobuttonforbasic.setChecked(true);
        radiobuttonforstandard.setOnCheckedChangeListener(new Radio_check());
        radiobuttonforpremium.setOnCheckedChangeListener(new Radio_check());
        continuebuttonchooseplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ChooseYourPlan.this,FinishUpAccount.class);
                intent.putExtra("PlanName",planname);
                intent.putExtra("PlanCost",plancost);
                intent.putExtra("PlanCostFormat",planformatofcost);
                startActivity(intent);
            }
        });

        Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ChooseYourPlan.this, SigninActivity.class);
                startActivity(intent);

            }
        });
    }


        class Radio_check implements CompoundButton.OnCheckedChangeListener {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked)
            {
                if(isChecked)
                {
                    if(compoundButton.getId()== R.id.radiobuttonforbasic){
                        planname="Basic";
                        plancost="349";
                        planformatofcost="₹ 349/month";
                        radiobuttonforstandard.setChecked(false);
                        radiobuttonforpremium.setChecked(false);
                    }
                    if(compoundButton.getId()== R.id.radiobuttonforstandard){
                        planname="Standard";
                        plancost="649";
                        planformatofcost="₹ 649/month";
                        radiobuttonforbasic.setChecked(false);
                        radiobuttonforpremium.setChecked(false);
                    }
                    if(compoundButton.getId()== R.id.radiobuttonforpremium){
                        planname="Premium";
                        plancost="799";
                        planformatofcost="₹ 799/month";
                        radiobuttonforstandard.setChecked(false);
                        radiobuttonforbasic.setChecked(false);
                    }


                }



            }
        }




    }


