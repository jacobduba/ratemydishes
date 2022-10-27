package com.g1as6.ratemydishes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
        getAndGo = findViewById(R.id.toGetAndGo);
        toSettings = findViewById(R.id.toSettings);

        diningCenters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(WelcomePage.this, DiningMenu.class);
                startActivity(intent);
            }
        });

        cafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(WelcomePage.this, DiningMenu.class);
                startActivity(intent);
            }
        });

        fastCasual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(WelcomePage.this, DiningMenu.class);
                startActivity(intent);
            }
        });

        getAndGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(WelcomePage.this, DiningMenu.class);
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