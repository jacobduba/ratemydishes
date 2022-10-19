package com.g1as6.ratemydishes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.g1as6.ratemydishes.app.AppVars;

public class restaurantList extends AppCompatActivity {

    Button loginBtn;
    Button diningCenters;
    AlertDialog Alert;
    Button cafe;
    Button fastCasual;
    Button getAndGo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_list);

        diningCenters = findViewById(R.id.toDiningCenters);
        cafe = findViewById(R.id.toCafe);
        fastCasual = findViewById(R.id.toFastCasual);
        getAndGo = findViewById(R.id.toGetAndGo);
        new AlertDialog.Builder(this)
                .setTitle("Token")
                .setMessage(AppVars.userToken)

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

        diningCenters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(restaurantList.this, diningCentersList.class);
                startActivity(intent);
            }
        });

        cafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(restaurantList.this, diningCentersList.class);
                startActivity(intent);
            }
        });

        fastCasual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(restaurantList.this, diningCentersList.class);
                startActivity(intent);
            }
        });

        getAndGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(restaurantList.this, diningCentersList.class);
                startActivity(intent);
            }
        });
    }
}