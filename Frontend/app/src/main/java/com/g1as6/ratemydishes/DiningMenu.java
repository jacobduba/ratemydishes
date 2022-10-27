package com.g1as6.ratemydishes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class DiningMenu extends AppCompatActivity {

    private ImageButton backToDining;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dining_menu);

        backToDining = findViewById(R.id.backToDining);

        backToDining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //Edit so that this goes back to welcome instead of main
                Intent intent = new Intent(DiningMenu.this, WelcomePage.class);
                startActivity(intent);
            }
        });
    }
}