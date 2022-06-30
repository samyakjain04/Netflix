package com.example.netflix.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.netflix.Mainscreens.Mainscreen;
import com.example.netflix.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class SplashSreeen extends AppCompatActivity {

    ProgressBar progressBar;
    static int counter=0;
    static int duration=5000;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    DocumentReference reference;
    Date today,validate;

    String firstname,lastname,contactnumber,UserID,authemail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_sreeen);
        getSupportActionBar().hide();
        progressBar=findViewById(R.id.progressBar);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        Calendar c=Calendar.getInstance();
        today=c.getTime();
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
                  if (firebaseAuth.getCurrentUser() != null) {
                      UserID = firebaseAuth.getCurrentUser().getUid();
                      reference = firebaseFirestore.collection("Users").document(UserID);
                      reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                          @Override
                          public void onSuccess(DocumentSnapshot documentSnapshot) {
                              validate = documentSnapshot.getDate("Valid_date");
                              firstname = documentSnapshot.getString("First_Name");
                              lastname = documentSnapshot.getString("Last_name");
                              contactnumber = documentSnapshot.getString("Contact_number");
                              authemail = documentSnapshot.getString("Email");
                              if (validate.compareTo(today) >= 0) {
                                  Intent i = new Intent(SplashSreeen.this, Mainscreen.class);
                                  startActivity(i);
                                  finish();
                                  // hello brother you have theree erors please confirm in group my 3 erros has been resolved..im discooting bye

                              } else {
                                  Intent i = new Intent(SplashSreeen.this, PaymentOverdue.class);
                                  i.putExtra("firstname", firstname);
                                  i.putExtra("lastname", lastname);
                                  i.putExtra("contact", contactnumber);
                                  i.putExtra("email", authemail);
                                  i.putExtra("Uid", UserID);
                                  startActivity(i);
                                  finish();

                              }

                          }
                      }).addOnFailureListener(new OnFailureListener() {
                          @Override
                          public void onFailure(@NonNull Exception e) {
                              if (e instanceof FirebaseNetworkException) {
                                  Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_SHORT).show();
                              }
                              Toast.makeText(getApplicationContext(), "Error data not fetched", Toast.LENGTH_SHORT).show();
                          }
                      });

                  } else {
                      startActivity(new Intent(SplashSreeen.this, SigninActivity.class));
                      finish();

                  }
              }
          },duration);



    }


}