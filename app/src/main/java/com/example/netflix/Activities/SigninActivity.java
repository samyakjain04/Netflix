package com.example.netflix.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.netflix.Mainscreens.Mainscreen;
import com.example.netflix.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class SigninActivity extends AppCompatActivity {
    ProgressBar progressBar;
    TextView forgetpasswordtextview,signuptextview;
     Button signinbutton;
    static int counter=0;
    EditText emailedittext,passwordedittext;
    String resetemail,authemail,authpassword,firstname,lastname,contactnumber,UserID;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    Date validate,today;
    DocumentReference userRef;
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        getSupportActionBar().hide();
        forgetpasswordtextview = findViewById(R.id.forgetpasswordtextview);
        progressBar=findViewById(R.id.signinprograssbar);
        progressBar.setVisibility(View.GONE);
        signuptextview = findViewById(R.id.signuptextview);
        signinbutton = findViewById(R.id.signinbutton);
        emailedittext=findViewById(R.id.emailedittext);
        passwordedittext=findViewById(R.id.passwordedittext);
          firebaseAuth= FirebaseAuth.getInstance();
          firebaseFirestore=firebaseFirestore.getInstance();
        Calendar c=Calendar.getInstance();
        today=c.getTime();
        signinbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authemail = emailedittext.getText().toString();
                authpassword = passwordedittext.getText().toString();
                progressBar.setVisibility(View.VISIBLE);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                if (emailedittext.getText().toString().length() > 8 && emailedittext.getText().toString().matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$") && passwordedittext.getText().toString().length() > 7)

                    {
                        firebaseAuth.signInWithEmailAndPassword(authemail, authpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    UserID=firebaseAuth.getCurrentUser().getUid();
                                    userRef=firebaseFirestore.collection("Users").document(UserID);
                                    userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            validate=documentSnapshot.getDate("Valid_date");
                                            firstname=documentSnapshot.getString("First_Name");
                                            lastname=documentSnapshot.getString("Last_name");
                                            contactnumber=documentSnapshot.getString("Contact_number");
                                            if(validate.compareTo(today)>=0)
                                            {
                                                Intent i = new Intent(SigninActivity.this, Mainscreen.class);
                                                startActivity(i);
                                                progressBar.setVisibility(view.GONE);
                                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                                            }
                                            else
                                            {
                                                Intent i= new Intent(SigninActivity.this,PaymentOverdue.class);
                                                i.putExtra("firstname",firstname);
                                                i.putExtra("lastname",lastname);
                                                i.putExtra("contact",contactnumber);
                                                i.putExtra("email",authemail);
                                                i.putExtra("Uid",UserID);
                                                startActivity(i);
                                                progressBar.setVisibility(view.GONE);
                                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                                            }

                                        }
                                    });

                                } else { if(task.getException() instanceof FirebaseNetworkException)
                                    Toast.makeText(getApplicationContext(),"No internet connection",Toast.LENGTH_SHORT).show();
                                    if(task.getException() instanceof FirebaseAuthInvalidUserException)
                                    { emailedittext.setError("User does not exist");}
                                    if(task.getException() instanceof FirebaseAuthInvalidCredentialsException)
                                        passwordedittext.setError("Invalid Password");
                                    progressBar.setVisibility(View.GONE);
                                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                }
                            }
                        });
                    }
                else
                    {
                        if(emailedittext.getText().toString().length()<=7 ||!emailedittext.getText().toString().matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$") )
                        {emailedittext.setError("Enter a valid email id");
                            progressBar.setVisibility(View.GONE);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        }
                        if(passwordedittext.getText().toString().length()<=7)
                        {passwordedittext.setError("Wrong password");
                            progressBar.setVisibility(View.GONE);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        }
                        if(emailedittext.getText().toString().length()==0)
                        {emailedittext.setError("Field cannot be empty");
                            progressBar.setVisibility(View.GONE);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        }
                        if(passwordedittext.getText().toString().length()==0)
                        {passwordedittext.setError("Field cannot be empty");
                            progressBar.setVisibility(View.GONE);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        }
                        else
                        {
                            passwordedittext.setError("Wrong password");
                        }
                        progressBar.setVisibility(View.GONE);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);}
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
                if(emailedittext.getText().toString().length()>8 && emailedittext.getText().toString().matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$"))
                {
                    AlertDialog.Builder passwordreset= new AlertDialog.Builder(view.getContext());
                    passwordreset.setTitle("Reset Password?");
                    passwordreset.setMessage("Press YES to receive the reset link");
                    passwordreset.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            resetemail=emailedittext.getText().toString();
                            firebaseAuth.sendPasswordResetEmail(resetemail).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getApplicationContext(),"Email reset link sent",Toast.LENGTH_SHORT).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    if(e instanceof FirebaseNetworkException)
                                    { Toast.makeText(getApplicationContext(),"No internet connection",Toast.LENGTH_SHORT).show();}
                                    Toast.makeText(getApplicationContext(),"Email reset link not sent as no user exist by this email",Toast.LENGTH_SHORT).show();
                                }
                            });


                        }
                    });
                    passwordreset.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    passwordreset.create().show();

                }
                else
                {emailedittext.setError("Enter a valid email");}
            }
        });

        }

        public void progress()

        {
            Timer timer = new Timer();
            TimerTask timerTask=new TimerTask()
            {
                @Override
                public void run()
                {
                    counter++;
                    progressBar.setProgress(counter);
                    if (counter == 500)
                    {

                        timer.cancel();
                    }

                }
            };
            timer.schedule(timerTask,0,100);
        }
    }
