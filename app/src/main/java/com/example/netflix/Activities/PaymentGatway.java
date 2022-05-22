package com.example.netflix.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.netflix.R;

public class PaymentGatway extends AppCompatActivity {
    String planname,plancost,planformatofcost,UserEmailId,UserPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_payment_gatway);
        Intent i= getIntent();
        planname=i.getStringExtra("PlanName");
        plancost=i.getStringExtra("PlanCost");
        planformatofcost=i.getStringExtra("PlanCostFormat");
        UserEmailId=i.getStringExtra("EmailId");
        UserPassword=i.getStringExtra("Password");
        Toast.makeText(this,""+planname+"\n"+plancost+"\n"+planformatofcost+"\n"+UserEmailId+"\n"+UserPassword,Toast.LENGTH_LONG).show();
    }
}