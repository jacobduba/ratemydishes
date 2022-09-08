package com.example.as1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class PictureActivity extends AppCompatActivity {

    ImageButton toCtrActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        toCtrActivity = findViewById(R.id.toCounter);

        toCtrActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(PictureActivity.this, CounterActivity.class);
                startActivity(intent3);
            }
        });


    }
}