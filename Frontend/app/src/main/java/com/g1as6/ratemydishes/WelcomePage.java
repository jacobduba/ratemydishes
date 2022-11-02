package com.g1as6.ratemydishes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.g1as6.ratemydishes.app.AppVars;

public class WelcomePage extends AppCompatActivity {
    Button loginBtn;
    Button diningCenters;
    AlertDialog Alert;
    Button cafe;
    Button cs;
    Button fastCasual;
    Button getAndGo;
    ImageButton toSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);

        diningCenters = findViewById(R.id.toDiningCenters);
        cafe = findViewById(R.id.toCafe);
        fastCasual = findViewById(R.id.toFastCasual);
        cs = findViewById(R.id.toCS);
        getAndGo = findViewById(R.id.toGetAndGo);
        toSettings = findViewById(R.id.toSettings);

        diningCenters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(WelcomePage.this, RestaurantList.class);
                intent.putExtra("type", AppVars.Restaurant.DINING_CENTER);

                startActivity(intent);
            }
        });

        cafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(WelcomePage.this, RestaurantList.class);
                intent.putExtra("type", AppVars.Restaurant.CAFE);

                startActivity(intent);
            }
        });

        cs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(WelcomePage.this, RestaurantList.class);
                intent.putExtra("type", AppVars.Restaurant.CONVENIENCE_STORES);

                startActivity(intent);
            }
        });

        fastCasual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(WelcomePage.this, RestaurantList.class);
                intent.putExtra("type", AppVars.Restaurant.FAST_CASUAL);

                startActivity(intent);
            }
        });

        getAndGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(WelcomePage.this, RestaurantList.class);
                intent.putExtra("type", AppVars.Restaurant.GET_AND_GO);

                startActivity(intent);
            }
        });

        toSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(WelcomePage.this, Settings.class);
                startActivity(intent);
            }
        });
    }
}