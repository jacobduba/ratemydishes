package com.g1as6.ratemydishes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;

import com.g1as6.ratemydishes.app.AppVars;

import androidx.appcompat.app.AppCompatActivity;

public class AdminSettings extends AppCompatActivity {

    ImageButton backToSettings;
    Switch switchSeasons;
    Switch switchUnion;
    Switch switchConversations;
    Switch switchFriley;
    Switch switchClyde;
    Switch switchHawthorn;
    Switch switchMU;
    Switch switchLance;
    Switch switchDolce;
    Switch switchHeaping;
    Switch switchBookends;
    Switch switchBusiness;
    Switch switchRoast;
    Switch switchCourt;
    Switch switchDesign;
    Switch switchWhirly;
    Switch switchGentle;
    Switch switchGlobal;
    Switch switchMUMarket;
    Switch switchAbe;
    Switch switchGet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_settings);

        backToSettings = findViewById(R.id.adminToSettings);
        switchSeasons = findViewById(R.id.switchSeasons);
        switchUnion = findViewById(R.id.switchUnion);
        switchConversations = findViewById(R.id.switchConversations);
        switchFriley = findViewById(R.id.switchFriley);
        switchClyde = findViewById(R.id.switchClyde);
        switchHawthorn = findViewById(R.id.switchHawthorn);
        switchMU = findViewById(R.id.switchMU);
        switchLance = findViewById(R.id.switchLance);
        switchDolce = findViewById(R.id.switchDolce);
        switchHeaping = findViewById(R.id.switchHeaping);
        switchBookends = findViewById(R.id.switchBookends);
        switchBusiness = findViewById(R.id.switchBusiness);
        switchRoast = findViewById(R.id.switchRoast);
        switchCourt = findViewById(R.id.switchCourt);
        switchDesign = findViewById(R.id.switchDesign);
        switchWhirly = findViewById(R.id.switchWhirly);
        switchGentle = findViewById(R.id.switchGentle);
        switchGlobal = findViewById(R.id.switchGlobal);
        switchMUMarket = findViewById(R.id.switchMUMarket);
        switchAbe = findViewById(R.id.switchAbe);
        switchGet = findViewById(R.id.switchGet);


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
