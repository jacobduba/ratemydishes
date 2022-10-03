package com.g1as6.ratemydishes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONArray;

public class diningCentersList extends AppCompatActivity implements OnClickListener {

    private String TAG = diningCentersList.class.getSimpleName();
    private ImageButton backToWelcome;
    private Button toMenu;
    private ProgressDialog pDialog;

    private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dining_centers_list);

        backToWelcome = (ImageButton) findViewById(R.id.backToWelcome);
        toMenu = findViewById(R.id.toSeasonsMenu);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        backToWelcome.setOnClickListener(this);
        toMenu.setOnClickListener(this);

    }

    private void showProgressDialog() {
        if (!pDialog.isShowing())

            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backToWelcome:
                startActivity(new Intent(diningCentersList.this,
                        MainActivity.class));
                break;
            case R.id.toSeasonsMenu:
                startActivity(new Intent(diningCentersList.this,
                        diningMenu.class));
                break;
        }

    }

}