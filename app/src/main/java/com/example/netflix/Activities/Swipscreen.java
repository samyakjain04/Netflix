package com.example.netflix.Activities;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.netflix.Adapters.ViewPagerAdapter;
import com.example.netflix.R;

public class Swipscreen extends AppCompatActivity {
    TextView signintextview,helptextview,privacytextview;
    Button getstrated;
    LinearLayout silderdots;
    ViewPager viewpagerswipescreen;
    private int dotscount;
    private ImageView[] dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipscreen);
        getSupportActionBar().hide();
        helptextview=findViewById(R.id.helptextview);
        signintextview=findViewById(R.id.signintextview);
        privacytextview=findViewById(R.id.privacytextview);
        getstrated=findViewById(R.id.getstarted);
        viewpagerswipescreen=findViewById(R.id.viewpagerswipescreen);
        silderdots=findViewById(R.id.silderdots);
        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(this);
        viewpagerswipescreen.setAdapter(viewPagerAdapter);
        dotscount=viewPagerAdapter.getCount();
        dots=new ImageView[dotscount];
        for (int i =0 ; i<dotscount;i++){
            dots[i]=new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.inactivedots));
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(4,0,4,0);
            silderdots.addView(dots[i],params);}
        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.activedots));

        viewpagerswipescreen.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for(int i=0;i<dotscount;i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.inactivedots));
                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.activedots));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        signintextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Swipscreen.this, SigninActivity.class);
                startActivity(i);
            }
        });
        privacytextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://help.netflix.com/en/node/100628")));
            }
        });
        helptextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://help.netflix.com/en/")));
            }
        });
        getstrated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Swipscreen.this,StepOne.class);
                startActivity(i);
            }
        });

    }
}