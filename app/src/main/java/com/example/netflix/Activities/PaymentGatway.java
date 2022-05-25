package com.example.netflix.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.netflix.Mainscreens.Mainscreen;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.example.netflix.R;

import org.json.JSONObject;

public class PaymentGatway extends AppCompatActivity implements PaymentResultListener {
    String planname,plancost,planformatofcost,UserEmailId,UserPassword;
    EditText firstnameedittext,lastnameedittext,contactnumberedittext;
    Button startmembershipbutton;
    CheckBox iagree;
    TextView textUrl,step3of3,Change,costtoset,plannametoset;
    String firstname,lastname,contactnumber;//userd for fetching the detail(edittext) by button
    String TAG="payment error";
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_payment_gatway);
        Checkout.preload(this);
        Intent i= getIntent();
        planname=i.getStringExtra("PlanName");
        plancost=i.getStringExtra("PlanCost");
        planformatofcost=i.getStringExtra("PlanCostFormat");
        UserEmailId=i.getStringExtra("EmailId");
        UserPassword=i.getStringExtra("Password");
      //  Toast.makeText(this,""+planname+"\n"+plancost+"\n"+planformatofcost+"\n"+UserEmailId+"\n"+UserPassword,Toast.LENGTH_LONG).show();

        firstnameedittext=findViewById(R.id.firstnameedittext);
        lastnameedittext=findViewById((R.id.lastnameedittext));
        contactnumberedittext=findViewById(R.id.contactnumberedittext);
        startmembershipbutton=findViewById(R.id.startmembershipbutton);
        textUrl=findViewById(R.id.toputurltext);
        iagree=findViewById(R.id.iagree);
        Change=findViewById(R.id.Change);
         step3of3=findViewById(R.id.step3of3);

         costtoset=findViewById(R.id.costtoset);
         costtoset.setText(planformatofcost);

         plannametoset=findViewById(R.id.plannametoset);
         plannametoset.setText(planname);
         firebaseAuth=FirebaseAuth.getInstance();


        SpannableString stt=new SpannableString("STEP 3 OF 3");
        StyleSpan boldspan=new StyleSpan(Typeface.BOLD);
        StyleSpan boldspan1=new StyleSpan(Typeface.BOLD);
        stt.setSpan(boldspan,5,6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        stt.setSpan(boldspan1,10,11, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        step3of3.setText(stt);

        SpannableString ss=new SpannableString("By checking the checkbox below, you agree to our Terms of Use, Privacy Statement, and that you are over 18. Netflix will automatically continue your membership and charge the monthly membership fee to your payment method until you cancel. You may cancel at any time to avoid future charges.");
        ClickableSpan clickableSpan=new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://help.netflix.com/en")));
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE);
            }
        };

        ClickableSpan clickableSpan1=new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://help.netflix.com/legal/privacy")));
            }
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE);
            }
        };
        ss.setSpan(clickableSpan,49,61, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(clickableSpan1,63,80, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textUrl.setText(ss);
        textUrl.setMovementMethod(LinkMovementMethod.getInstance());


        Change.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent=new Intent(PaymentGatway.this,ChooseYourPlan.class);
                 startActivity(intent);
             }
         });
             startmembershipbutton.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view)
                 {
                     startPayment();
                 }
             });
    }
public void startPayment() {
    Checkout checkout = new Checkout();
    final Activity activity = this;
    firstname = firstnameedittext.getText().toString();
    lastname = lastnameedittext.getText().toString();
    contactnumber = contactnumberedittext.getText().toString();
    String name = firstname+lastname;

    try {
        JSONObject options=new JSONObject();
        options.put("name",name);
        options.put("description","APP PAYMENT");
        options.put("currency","INR");
        String payment=plancost;
        double total=Double.parseDouble(payment);
        total=total*100;
        options.put("amount",total);
        options.put("prefill.email",UserEmailId);
        options.put("prefill.contact",contactnumber);
        checkout.open(activity,options);

    } catch (Exception e) {

        Log.e(TAG,"error occures",e);
    }
}

    @Override
    public void onPaymentSuccess(String s) {
        firebaseAuth.createUserWithEmailAndPassword(UserEmailId,UserPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Intent i = new Intent(PaymentGatway.this, Mainscreen.class);
                startActivity(i);


            }
        });



    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this,"paymrnt failed",Toast.LENGTH_SHORT).show();

    }
}