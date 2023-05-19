package sh.duba.rmd.frontend;

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
import com.g1as6.ratemydishes.R;

import sh.duba.rmd.frontend.app.AppController;
import sh.duba.rmd.frontend.app.AppVars;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
    String url = AppVars.API_URL + "/user/register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        ProgressDialog pDialog = new ProgressDialog(this);
        File token = new File(this.getFilesDir(), "token.txt");
        File admin = new File(this.getFilesDir(), "admin.txt");
        File nId = new File(this.getFilesDir(), "netId.txt");

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
                boolean validNet;
                boolean validPass = false;
                pDialog.setMessage("Making account...");
                pDialog.show();

                String netText = netId.getText().toString().toLowerCase();
                String passText = pswd.getText().toString();

                // Check to see if everything is valid
                if(netText.length() >= 3 && netText.length() <= 8 && netText.matches("^[a-zA-Z0-9]*$")){
                    netStatus.setText("");
                    validNet = true;
                } else {
                    netStatus.setText("Invalid Net Id");
                    validNet = false;
                }

                if(passText.equals(pswdConf.getText().toString())) {
                    if(passText.length() >= 6){
                        passStat.setText("");
                        validPass = true;
                    } else {
                        passStat.setText("Password must be greater than 6 characters!");
                        validPass = false;
                    }
                } else {
                    passStat.setText("Passwords do not match!");
                    validNet = false;
                }


                if (validNet && validPass) {
                    JSONObject body = new JSONObject();
                    try {
                        body.put("netId", netText);
                        body.put("password", passText);
                    } catch (JSONException e) {
                    }

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                            (Request.Method.POST, url, body, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        String tokenString = response.get("token").toString();
                                        boolean isAdmin = response.getJSONObject("user").getBoolean("isAdmin");

                                        // If I understand tokens correctly, no token means auth failed
                                        if (!tokenString.toString().equals("{}")) {
                                            try {
                                                AppVars.isAdmin = isAdmin;
                                                AppVars.userToken = tokenString;
                                                AppVars.netId = netId.getText().toString();

                                                Intent intent = new Intent(Registration.this, WelcomePage.class);
                                                startActivity(intent);
                                                BufferedWriter writer = new BufferedWriter(new FileWriter(token));
                                                writer.write(tokenString);
                                                writer.close();

                                                BufferedWriter writer1 = new BufferedWriter(new FileWriter(admin));
                                                writer1.write(String.valueOf(isAdmin));
                                                writer1.close();

                                                BufferedWriter netWriter = new BufferedWriter(new FileWriter(nId));
                                                netWriter.write(AppVars.netId);
                                                netWriter.close();
                                            } catch (Exception e) {
                                            }

                                            Intent intent = new Intent(Registration.this, WelcomePage.class);
                                            startActivity(intent);
                                        } else {
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
