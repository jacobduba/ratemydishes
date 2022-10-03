package com.g1as6.ratemydishes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class diningCentersList extends AppCompatActivity {

    ImageButton backToWelcome;
    Button toMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dining_centers_list);

        backToWelcome = findViewById(R.id.backToWelcome);
        toMenu = findViewById(R.id.toMenu);

        backToWelcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //Edit so that this goes back to welcome instead of main
                Intent intent = new Intent(diningCentersList.this, MainActivity.class);
                startActivity(intent);
            }
        });

        toMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //Edit so that this goes back to welcome instead of main
                Intent intent2 = new Intent(diningCentersList.this, diningMenu.class);
                startActivity(intent2);
            }
        });
    }
}