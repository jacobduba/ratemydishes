package com.g1as6.ratemydishes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.g1as6.ratemydishes.app.AppVars;

public class Menu extends AppCompatActivity {

    private ImageButton backToDining;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        backToDining = findViewById(R.id.backToDining);

        backToDining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Menu.this, WelcomePage.class);
                startActivity(intent);

            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            // AppVars.Restaurant is gross ew
            AppVars.Restaurant value = (AppVars.Restaurant)extras.getSerializable("type");
        }
    }
}