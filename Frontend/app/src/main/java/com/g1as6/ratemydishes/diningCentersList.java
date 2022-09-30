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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dining_centers_list);

        backToWelcome = findViewById(R.id.backToWelcome);

        backToWelcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //Edit so that this goes back to welcome instead of main
                Intent intent = new Intent(diningCentersList.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}