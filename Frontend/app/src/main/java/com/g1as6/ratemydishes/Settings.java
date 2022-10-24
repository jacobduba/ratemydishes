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
    Button toAdmin;
    Button changePassword;
    //Button deleteAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        settingsToWelcome = findViewById(R.id.adminToSettings);
        logout = findViewById(R.id.toLogin);
        toAdmin = findViewById(R.id.toAdmin);


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
                AppVars.userToken = null;
                Intent intent = new Intent(Settings.this, Login.class);
                startActivity(intent);


            }
        });

        if(AppVars.isAdmin) {
            toAdmin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Settings.this, AdminSettings.class);
                    startActivity(intent);

                }
            });
        }


        /*changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //Edit so that this goes back to welcome instead of main
                Intent intent = new Intent(Settings.this, Login.class);
                startActivity(intent);

            }
        });*/

    }
}
