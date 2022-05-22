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
import android.widget.TextView;
import android.widget.Toast;

import com.example.netflix.R;

public class FinishUpAccount extends AppCompatActivity {
   String planname,plancost,planformatofcost;
    TextView step1of3finsh,Signin;
    Button continue1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_up_account);

      Intent i= getIntent();
        planname=i.getStringExtra("PlanName");
        plancost=i.getStringExtra("PlanCost");
        planformatofcost=i.getStringExtra("PlanCostFormat");
        step1of3finsh=findViewById(R.id.step1of3finish);
        Signin=findViewById(R.id.Signin);
        continue1=findViewById(R.id.continue1);


        SpannableString st=new SpannableString("STEP 1 OF 3");
        StyleSpan boldspan=new StyleSpan(Typeface.BOLD);
        StyleSpan boldspan1=new StyleSpan(Typeface.BOLD);
        st.setSpan(boldspan,5,6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        st.setSpan(boldspan1,10,11, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        step1of3finsh.setText(st);

        Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(FinishUpAccount.this,SigninActivity.class);
                startActivity(intent);

            }
        });

        continue1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             Intent intent=new Intent(FinishUpAccount.this,StepTwo.class);
                intent.putExtra("PlanName",planname);
                intent.putExtra("PlanCost",plancost);
                intent.putExtra("PlanCostFormat",planformatofcost);
             startActivity(intent);

            }
        });


    }
}