package com.example.netflix.Mainscreens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.netflix.Activities.PaymentOverdue;
import com.example.netflix.Activities.SigninActivity;
import com.example.netflix.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class Settings extends AppCompatActivity {
    EditText newpassword;
    TextView emailsettings,plansettings,datesettings;
    FirebaseFirestore firebaseFirestore= FirebaseFirestore.getInstance();
    FirebaseUser user;
    DocumentReference reference;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    Button resetpasswordbutton,signoutbutton;
    String Uid,emailstring,planstring;
    Date validate;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_settings);
        BottomNavigationView bottomNavigationView;
        bottomNavigationView=findViewById(R.id.bottom_navigation);
        firebaseAuth=FirebaseAuth.getInstance();
        newpassword=findViewById(R.id.resetpasswordedittext);
        resetpasswordbutton=findViewById(R.id.resetpasswordbutton);
        signoutbutton=findViewById(R.id.signoutbutton);
        emailsettings=findViewById(R.id.emailsettings);
        plansettings=findViewById(R.id.plansettings);
        datesettings=findViewById(R.id.datesettings);
        user=firebaseAuth.getInstance().getCurrentUser();
        progressDialog=new ProgressDialog(Settings.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        if(firebaseAuth.getCurrentUser()!=null) {
            Uid = firebaseAuth.getCurrentUser().getUid();
            reference = firebaseFirestore.collection("Users").document(Uid);
            reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    validate = documentSnapshot.getDate("Valid_date");
                    emailstring = documentSnapshot.getString("Email");
                    planstring = documentSnapshot.getString("Plan_cost");
                    emailsettings.setText(emailstring);
                    plansettings.setText("₹ " + planstring + "/mo.");
                    datesettings.setText(validate.toString());
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    progressDialog.cancel();


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    if (e instanceof FirebaseNetworkException) {
                        Toast.makeText(getApplicationContext(), "NO internet connection", Toast.LENGTH_SHORT).show();

                    }
                    Toast.makeText(getApplicationContext(), "Error data not fetched", Toast.LENGTH_SHORT).show();
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    progressDialog.cancel();

                }
            });


        }

        Menu menu=bottomNavigationView.getMenu();
        MenuItem menuItem=menu.getItem(0);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.homeicon:
                        Intent l=new Intent(Settings.this, Mainscreen.class);
                        startActivity(l);
                        break;
                    case R.id.searchicon:
                        Intent i=new Intent(Settings.this, Search.class);
                        startActivity(i);
                        break;
                    case R.id.settingsicon:
                        break;
                }
                return false;
            }
        });

        signoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder signoutbutton= new AlertDialog.Builder(view.getContext());
               signoutbutton.setTitle("Do you really wants to signout?");
               signoutbutton.setMessage("Press YES to signout");
               signoutbutton.setCancelable(false);
                signoutbutton.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        FirebaseAuth.getInstance().signOut();
                        Intent x=new Intent(Settings.this,SigninActivity.class);
                        startActivity(x);
                        finish();



                    }
                });
                signoutbutton.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                signoutbutton.create().show();







            }
        });
        resetpasswordbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(Settings.this);
                progressDialog.setMessage("Loading...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                if (newpassword.getText().toString().length() > 7)
                {firebaseAuth.signInWithEmailAndPassword(emailstring,newpassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {

                        EditText changepassword = new EditText(view.getContext());
                        AlertDialog.Builder updatepassword = new AlertDialog.Builder(view.getContext());
                        updatepassword.setTitle("update Password?");
                        updatepassword.setCancelable(false);
                        changepassword.setHint("New password");
                        changepassword.setSingleLine();
                        updatepassword.setView(changepassword);

                        updatepassword.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                progressDialog.show();
                                String newpasswordstring = changepassword.getText().toString();
                                if (newpasswordstring.length() > 7) {

                                    user.updatePassword(newpasswordstring).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(getApplicationContext(), "Password Updated", Toast.LENGTH_SHORT).show();
                                            newpassword.setText("");
                                            progressDialog.cancel();

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                            if (e instanceof FirebaseNetworkException)
                                                Toast.makeText(getApplicationContext(), "NO internet connection", Toast.LENGTH_SHORT).show();
                                            Toast.makeText(getApplicationContext(), "Password not updated", Toast.LENGTH_SHORT).show();
                                            progressDialog.cancel();
                                        }
                                    });
                                } else {
                                    Toast.makeText(getApplicationContext(), "password to short please retry", Toast.LENGTH_SHORT).show();
                                    progressDialog.cancel();


                                }


                            }
                        });
                        updatepassword.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                newpassword.setText("");
                                progressDialog.cancel();

                            }
                        });
                        updatepassword.create().show();

                    }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (e instanceof FirebaseNetworkException)
                            Toast.makeText(getApplicationContext(), "NO internet connection", Toast.LENGTH_SHORT).show();
                        if(e instanceof FirebaseAuthInvalidCredentialsException)
                            newpassword.setError("Invalid password");
                        else
                            newpassword.setError("Incorrect password");
                        progressDialog.cancel();

                    }
                });


                } else
                {


                    newpassword.setError("Incorrect password");
                    progressDialog.cancel();
                }



            }
        });

    }
}