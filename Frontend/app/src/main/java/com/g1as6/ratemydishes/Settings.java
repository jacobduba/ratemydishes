package com.g1as6.ratemydishes;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.g1as6.ratemydishes.app.AppController;
import com.g1as6.ratemydishes.app.AppVars;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class Settings extends AppCompatActivity {

    ImageButton settingsToWelcome;
    Button logout;
    Button toAdmin;
    Button changePassword;
    Button deleteAccount;
    TextView deleteStatus;
    EditText confirmDelete;
    String tag_json_obj = "json_obj_req";
    String url = "http://coms-309-006.class.las.iastate.edu:8080/user/delete";
    String loginUrl = "http://coms-309-006.class.las.iastate.edu:8080/user/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ProgressDialog pDialog = new ProgressDialog(this);

        settingsToWelcome = findViewById(R.id.adminToSettings);
        logout = findViewById(R.id.toLogin);
        toAdmin = findViewById(R.id.toAdmin);
        changePassword = findViewById(R.id.changePassword);
        deleteAccount = findViewById(R.id.deleteAccount);
        deleteStatus = findViewById(R.id.deleteStatus);
        confirmDelete = findViewById(R.id.confirmDelete);
        confirmDelete.setVisibility(View.INVISIBLE);


        settingsToWelcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Edit so that this goes back to welcome instead of main
                Intent intent = new Intent(Settings.this, WelcomePage.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppVars.userToken = null;
                Intent intent = new Intent(Settings.this, Login.class);
                startActivity(intent);


            }
        });

        if (AppVars.isAdmin) {
            toAdmin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Settings.this, AdminSettings.class);
                    startActivity(intent);

                }
            });
        } else {
            toAdmin.setVisibility(View.INVISIBLE);
        }


        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDelete.setVisibility(View.VISIBLE);
                //Edit so that this goes back to welcome instead of main
                //Intent intent = new Intent(Settings.this, Registration.class);
                //startActivity(intent);


            }
        });

        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pDialog.setMessage("Deleting Account...");
                pDialog.show();

                JSONObject body = new JSONObject();
                try {
                    body.put("token", "mocktoken");
                    body.put("password", "test");
                } catch (JSONException e) {
                    ((TextView) findViewById(R.id.deleteResponse)).setText("Could not delete!");
                }

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.POST, url, body, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                VolleyLog.d(TAG, response.toString());
                                ((TextView)findViewById(R.id.deleteResponse)).setText(response.toString());

                                pDialog.hide();

                                try {
                                    String status = response.get("Status").toString();

                                    if (status.equals("ACCEPTED")) {
                                        deleteStatus.setText("Account deleted");

                                    }else{
                                        deleteStatus.setText("Error Deleting!");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                deleteStatus.setText("No response from server");

                                pDialog.hide();
                            }
                        });
                AppController.getInstance().addToRequestQueue(jsonObjectRequest, tag_json_obj);
            }

        });
    }
}

