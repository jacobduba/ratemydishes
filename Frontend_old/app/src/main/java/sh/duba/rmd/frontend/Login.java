package sh.duba.rmd.frontend;

import static android.app.PendingIntent.getActivity;
import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.AlertDialog;
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
import com.g1as6.ratemydishes.R;

import sh.duba.rmd.frontend.app.AppController;
import sh.duba.rmd.frontend.app.AppVars;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    static File token;
    static File admin;
    static File netId;
    Button loginButton;
    Button registrationButton;
    Button guestUser;
    EditText usrName;
    EditText pswd;
    TextView lginStatus;
    String tag_json_obj = "json_obj_req";
    String url = AppVars.API_URL + "/user/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppController.getInstance().getRequestQueue().start();
        setContentView(R.layout.login);
        ProgressDialog pDialog = new ProgressDialog(this);
        token = new File(this.getFilesDir(), "token.txt");
        admin = new File(this.getFilesDir(), "admin.txt");
        netId = new File(this.getFilesDir(), "netId.txt");

        if (token.exists()) {
            try {
                // The fact that this still worked after my commit is astounding
                BufferedReader reader = new BufferedReader(new FileReader(token));
                String t = reader.readLine();
                reader.close();

                BufferedReader adminReader = new BufferedReader(new FileReader(admin));
                String t1 = adminReader.readLine();
                adminReader.close();

                BufferedReader netIdReader = new BufferedReader(new FileReader(netId));
                String n = netIdReader.readLine();
                netIdReader.close();

                AppVars.userToken = t;
                AppVars.isAdmin = t1.equals("true");
                AppVars.netId = n;
                Intent intent = new Intent(Login.this, WelcomePage.class);
                startActivity(intent);
            } catch (Exception e) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                builder.setMessage("Could not read token!!")
                        .setTitle("Error!");
                AlertDialog dialog = builder.create();
            }
        } else {
            try {
                token.createNewFile();
                netId.createNewFile();
            } catch (IOException e) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                builder.setMessage("Could not create token file! You should probably contact a developer!")
                        .setTitle("Error!");
                AlertDialog dialog = builder.create();
            }
        }

        // Component Assignment
        loginButton = findViewById(R.id.loginBtn);
        lginStatus = findViewById(R.id.loginStatus);
        guestUser = findViewById(R.id.guestUser);
        registrationButton = findViewById(R.id.registerBtn);
        usrName = findViewById(R.id.usrInput);
        pswd = findViewById(R.id.pswdInput);

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
                    ((TextView) findViewById(R.id.loginStatus)).setText("Could not login!");
                }

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.POST, url, body, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                VolleyLog.d(TAG, response.toString());
                                System.out.println(response.toString());

                                pDialog.hide();

                                try {
                                    String tkn = response.get("token").toString();
                                    boolean isAdmin = response.getJSONObject("user").getBoolean("isAdmin");
                                    AppVars.isAdmin = isAdmin;

                                    // If I understand tokens correctly, no token means auth failed
                                    if (!tkn.equals("{}")) {
                                        try {
                                            // Token file should already exist
                                            //File tokenFile = new File(this.getFilesDir(), "token.txt");
                                            AppVars.userToken = tkn;
                                            AppVars.isAdmin = isAdmin;
                                            AppVars.netId = body.getString("netId");

                                            BufferedWriter writer = new BufferedWriter(new FileWriter(Login.token));
                                            writer.write(tkn);
                                            writer.close();

                                            BufferedWriter writer1 = new BufferedWriter(new FileWriter(Login.admin));
                                            writer1.write(String.valueOf(isAdmin));
                                            writer1.close();

                                            BufferedWriter netIdWriter = new BufferedWriter(new FileWriter(Login.netId));
                                            netIdWriter.write(AppVars.netId);
                                            netIdWriter.close();

                                        } catch (Exception e) {
                                        }

                                    } else {
                                        AppVars.userToken = null;
                                        lginStatus.setText("Invalid Username or Password!");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Intent intent = new Intent(Login.this, WelcomePage.class);
                                startActivity(intent);
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                lginStatus.setText("Error logging in!");

                                pDialog.hide();
                            }
                        })
                {
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
        });

        // registration button
        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Login.this, Registration.class);
                startActivity(intent);
            }
        });

        guestUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, WelcomePage.class);
                startActivity(intent);
            }
        });
    }
}
