package com.g1as6.ratemydishes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.g1as6.ratemydishes.app.AppController;
import com.g1as6.ratemydishes.app.AppVars;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {
    ImageButton backBtn;
    Button createBtn;
    TextView passStat;
    TextView netStatus;
    EditText netId;
    EditText pswd;
    EditText pswdConf;

    String tag_json_obj = "json_obj_req";
    String url = "http://coms-309-006.class.las.iastate.edu:8080/user/register";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        ProgressDialog pDialog = new ProgressDialog(this);


        // Create widgets
        backBtn = findViewById(R.id.back);
        createBtn = findViewById(R.id.createAccount);

        // Component Assignment
        netId = findViewById(R.id.netId);
        pswd = findViewById(R.id.pass);
        pswdConf = findViewById(R.id.confPass);
        passStat = findViewById(R.id.passStatus);
        netStatus = findViewById(R.id.netStatus);

        // Add listeners for buttons
        // Back button
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Registration.this, Login.class);
                startActivity(intent);
            }
        });

        // Create Button
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                boolean validNet = false;
                boolean validPass = false;
                pDialog.setMessage("Making account...");
                pDialog.show();

                String netText = netId.getText().toString().toLowerCase();
                String passText = pswd.getText().toString();

                // Check to see if everything is valid
                if(netText.length() > 3 && netText.length() < 8 && netText.matches("^[a-zA-Z0-9]*$")){
                    netStatus.setText("");
                    validNet = true;
                }else {
                    netStatus.setText("Invalid Net Id");
                    validNet = false;
                }

                if(passText.equals(pswdConf.getText().toString())) {
                    if(passText.length() > 6){
                        passStat.setText("");
                        validPass = true;
                    }else{
                        passStat.setText("Password must be greater than 6 characters!");
                        validPass = false;
                    }
                }else{
                    passStat.setText("Passwords do not match!");
                    validNet = false;
                }


                if(validNet && validPass){
                    JSONObject body = new JSONObject();
                    try {
                        body.put("netId", netText);
                        body.put("password", passText);
                    } catch (JSONException e) { }

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                            (Request.Method.POST, url, body, new Response.Listener<JSONObject>()
                            {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        String token = response.get("token").toString();
                                        boolean isAdmin = response.getBoolean("isAdmin");
                                        AppVars.isAdmin = isAdmin;

                                        // If I understand tokens correctly, no token means auth failed
                                        if (!token.toString().equals("{}")) {
                                            //((TextView) findViewById(R.id.response)).setText(token.toString());
                                            AppVars.userToken = token;

                                            Intent intent = new Intent(Registration.this, WelcomePage.class);
                                            startActivity(intent);
                                        }else{
                                            AppVars.userToken = null;
                                            passStat.setText("Something went wrong.");
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    passStat.setText("Something went wrong!");
                                    pDialog.hide();
                                }
                            }) {
                        @Override
                        public Map getHeaders() throws AuthFailureError {
                            HashMap headers = new HashMap();
                            headers.put("Content-Type", "application/json");
                            headers.put("Accept", "application/json");

                            return headers;
                        }
                    };

                    AppController.getInstance().addToRequestQueue(jsonObjectRequest, tag_json_obj);
                }
                pDialog.hide();
            }
        });
    }
}
