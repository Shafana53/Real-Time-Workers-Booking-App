package com.example.labourbooking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.labourbooking.databinding.ActivityMainBinding;

public class Home extends AppCompatActivity {
//    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
 //       binding = ActivityMainBinding.inflate(getLayoutInflater());
        //      setContentView(binding.getRoot());
//        ele=findViewById(R.id.ele);
//        mason=findViewById(R.id.mason);
//        plumber=findViewById(R.id.plumber);
//        farmlabour=findViewById(R.id.farmlabour);
//        carpainter=findViewById(R.id.carpainter);
//        painter=findViewById(R.id.painter);
//
//        ele.setOnClickListener(this);
//        mason.setOnClickListener(this);
//        plumber.setOnClickListener(this);
//        farmlabour.setOnClickListener(this);
//        carpainter.setOnClickListener(this);
//        painter.setOnClickListener(this);

        CardView electrician=findViewById(R.id.ele);
        electrician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), Electrician.class);
                startActivity(intent);

//                startActivity(new Intent(Home.this,Electrician.class));

            }
        });
        CardView mason=findViewById(R.id.maso);
        mason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this,Mason.class));
            }
        });

        CardView plumber=findViewById(R.id.plumb);
        plumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this,Plumber.class));
            }
        });

        CardView farmlabour=findViewById(R.id.farml);
        farmlabour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this,Farmlabour.class));
            }
        });

        CardView carpenter=findViewById(R.id.carp);
        carpenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this,Carpenter.class));
            }
        });

        CardView painter=findViewById(R.id.paint);
        painter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this,Painter.class));
            }
        });





        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    }
