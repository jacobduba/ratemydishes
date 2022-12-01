package com.g1as6.ratemydishes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.g1as6.ratemydishes.app.AppController;
import com.g1as6.ratemydishes.app.AppVars;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class AdminSetting extends AppCompatActivity {

    private ImageButton backtoSettings;
    private String url = "http://coms-309-006.class.las.iastate.edu:8080/admin/";
    protected static AppVars.admin locations;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_setting);
    }
}
