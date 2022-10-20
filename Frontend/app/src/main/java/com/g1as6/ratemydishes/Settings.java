package com.g1as6.ratemydishes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import com.g1as6.ratemydishes.app.AppVars;

import androidx.appcompat.app.AppCompatActivity;

public class Settings extends AppCompatActivity {

    ImageButton settingsToWelcome;
    Button logout;
    //Button toAdmin;
    //Button changePassword;
    //Button deleteAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        settingsToWelcome = findViewById(R.id.settingsToWelcome);
        logout = findViewById(R.id.toLogin);


        settingsToWelcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //Edit so that this goes back to welcome instead of main
                Intent intent = new Intent(Settings.this, WelcomePage.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //Edit so that this goes back to welcome instead of main
                Intent intent = new Intent(Settings.this, Login.class);
                startActivity(intent);
                AppVars.userToken = null;

            }
        });

        /*toAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //Edit so that this goes back to welcome instead of main
                Intent intent = new Intent(Settings.this, AdminSettings.class);
                startActivity(intent);

            }
        });
        */

        /*changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //Edit so that this goes back to welcome instead of main
                Intent intent = new Intent(Settings.this, Login.class);
                startActivity(intent);

            }
        });
        */
    }
}
