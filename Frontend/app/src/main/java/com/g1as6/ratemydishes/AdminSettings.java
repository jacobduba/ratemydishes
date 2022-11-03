package com.g1as6.ratemydishes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import com.g1as6.ratemydishes.app.AppVars;

import androidx.appcompat.app.AppCompatActivity;

public class AdminSettings extends AppCompatActivity {

    ImageButton backToSettings;
    Button switchWhirlyBirds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_settings);

        backToSettings = findViewById(R.id.adminToSettings);

        backToSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //Edit so that this goes back to welcome instead of main
                Intent intent = new Intent(AdminSettings.this, Settings.class);
                startActivity(intent);
            }
        });
    }
}
