package com.example.netflix.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.netflix.R;

public class StepThree extends AppCompatActivity {
    String planname,plancost,planformatofcost,UserEmailId,UserPassword;
    TextView Signout,stepthree;
    LinearLayout paymentlinearlayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_step_three);
        Intent i= getIntent();
        planname=i.getStringExtra("PlanName");
        plancost=i.getStringExtra("PlanCost");
        planformatofcost=i.getStringExtra("PlanCostFormat");
        UserEmailId=i.getStringExtra("EmailId");
        UserPassword=i.getStringExtra("Password");
        Toast.makeText(this,""+planname+"\n"+plancost+"\n"+planformatofcost+"\n"+UserEmailId+"\n"+UserPassword,Toast.LENGTH_SHORT).show();
       Signout=findViewById(R.id.Signoutstepthree);
       stepthree=findViewById(R.id.stepthree);
       paymentlinearlayout=findViewById(R.id.paymentlinearlayout);
       Signout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent=new Intent(StepThree.this,SigninActivity.class);
               startActivity(intent);
           }
       });
        SpannableString st=new SpannableString("STEP 3 OF 3");
        StyleSpan boldspan=new StyleSpan(Typeface.BOLD);
        StyleSpan boldspan1=new StyleSpan(Typeface.BOLD);
        st.setSpan(boldspan,5,6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        st.setSpan(boldspan1,10,11, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        stepthree.setText(st);
        paymentlinearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(StepThree.this,PaymentGatway.class);
                intent.putExtra("PlanName",planname);
                intent.putExtra("PlanCost",plancost);
                intent.putExtra("PlanCostFormat",planformatofcost);
                intent.putExtra("EmailId",UserEmailId);
                intent.putExtra("Password",UserPassword);
                startActivity(intent);

            }
        });






}
}