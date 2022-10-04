package com.g1as6.ratemydishes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class registration extends AppCompatActivity {
    ImageButton backBtn;
    Button createBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);


        // Create widgets
        backBtn = findViewById(R.id.back);
        createBtn = findViewById(R.id.createAccount);

        // Add listeners for buttons
        // Back button
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(registration.this, login.class);
                startActivity(intent);
            }
        });

        // Create Button
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(registration.this, restaurantList.class);
                startActivity(intent);
            }
        });
    }
}
