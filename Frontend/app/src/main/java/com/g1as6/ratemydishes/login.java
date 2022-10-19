package com.g1as6.ratemydishes;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.g1as6.ratemydishes.app.AppController;
import com.g1as6.ratemydishes.app.AppVars;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity {
    Button loginButton;
    Button registrationButton;
    EditText usrName;
    EditText pswd;
    TextView lginStatus;
    String tag_json_obj = "json_obj_req";
    String url = "http://coms-309-006.class.las.iastate.edu:8080/user/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppController.getInstance().getRequestQueue().start();
        setContentView(R.layout.login);
        ProgressDialog pDialog = new ProgressDialog(this);

        // Component Assignment
        loginButton = findViewById(R.id.loginBtn);
        lginStatus = findViewById(R.id.loginStatus);
        registrationButton = findViewById(R.id.registerBtn);
        usrName = findViewById(R.id.usrInput);
        pswd = findViewById(R.id.pswdInput);

        // Assign some vars and stuff
        AppVars.userToken = null;

        // login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pDialog.setMessage("Logging in...");
                pDialog.show();

                JSONObject body = new JSONObject();
                try {
                    body.put("netId", usrName.getText().toString());
                    body.put("password", pswd.getText().toString());
                } catch (JSONException e) {
                    ((TextView)findViewById(R.id.response)).setText("Could not login!");
                }

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.POST, url, body, new Response.Listener<JSONObject>()
                        {
                            @Override
                            public void onResponse(JSONObject response) {
                                VolleyLog.d(TAG, response.toString());
                                ((TextView)findViewById(R.id.response)).setText(response.toString());
                                System.out.println(response.toString());

                                pDialog.hide();

                                try {
                                    String token = response.get("token").toString();

                                    // If I understand tokens correctly, no token means auth failed
                                    if (!token.toString().equals("{}")) {
                                        //((TextView) findViewById(R.id.response)).setText(token.toString());
                                        AppVars.userToken = token;

                                        Intent intent = new Intent(login.this, WelcomePage.class);
                                        startActivity(intent);
                                    }else{
                                        AppVars.userToken = null;
                                        lginStatus.setText("Invalid Username or Password!");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                lginStatus.setText("Invalid Username or Password!");

                                pDialog.hide();
                            }
                        }) {
                    @Override
                    public Map getHeaders() throws AuthFailureError{
                        HashMap headers = new HashMap();
                        headers.put("Content-Type", "application/json");
                        headers.put("Accept", "application/json");

                        return headers;
                    }
                };

                AppController.getInstance().addToRequestQueue(jsonObjectRequest, tag_json_obj);
                }
            });

        // registration button
        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(login.this, registration.class);
                startActivity(intent);
            }
        });
    }
}
