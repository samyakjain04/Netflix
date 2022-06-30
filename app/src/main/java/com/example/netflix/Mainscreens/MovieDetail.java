package com.example.netflix.Mainscreens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.netflix.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MovieDetail extends AppCompatActivity {
    ImageView movieimage;
    TextView moviename;
    Button play;
    String name,moviesid,image,fileurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        movieimage=findViewById(R.id.imagedeatils);
        moviename=findViewById(R.id.moviename);
        play=findViewById(R.id.playbutton);
        moviesid=getIntent().getStringExtra("movieId");
        name=getIntent().getStringExtra("movieName");
        image=getIntent().getStringExtra("movieImageUrl");
        fileurl=getIntent().getStringExtra("movieFile");
        Glide.with(this).load(image).into(movieimage);
        moviename.setText(name);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(MovieDetail.this,VideoPlayer.class);
                i.putExtra("url",fileurl);
                startActivity(i);
            }
        });



    }
}