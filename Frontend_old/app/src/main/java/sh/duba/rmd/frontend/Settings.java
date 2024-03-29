package sh.duba.rmd.frontend;

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
import com.android.volley.toolbox.JsonObjectRequest;
import com.g1as6.ratemydishes.R;

import sh.duba.rmd.frontend.app.AppController;
import sh.duba.rmd.frontend.app.AppVars;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Settings extends AppCompatActivity {
    ImageButton settingsToWelcome;
    Button logout;
    Button toAdmin;
    Button changePassword;
    Button deleteAccount;
    TextView deleteStatus;
    EditText confirmDelete;
    EditText confirmNewPass;
    EditText deleteAcct;
    TextView passStat;
    Button changePass;
    Button acctDelete;
    EditText oldPassword;
    String tag_json_obj = "json_obj_req";
    String url = AppVars.API_URL + "/user/delete";
    String loginUrl = AppVars.API_URL + "/user/login";
    String changePasswordUrl = AppVars.API_URL + "/user/changepw";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        ProgressDialog pDialog = new ProgressDialog(this);
        File token = new File(this.getFilesDir(), "token.txt");
        File admin = new File(this.getFilesDir(), "admin.txt");

        settingsToWelcome = findViewById(R.id.adminToSettings);
        logout = findViewById(R.id.toLogin);
        toAdmin = findViewById(R.id.toAdmin);
        changePassword = findViewById(R.id.changePassword);
        deleteAccount = findViewById(R.id.deleteAccount);
        deleteStatus = findViewById(R.id.deleteStatus);
        confirmDelete = findViewById(R.id.confirmDelete);
        confirmNewPass = findViewById(R.id.confirmNewPass);
        passStat = findViewById(R.id.passStat);
        changePass = findViewById(R.id.changePass);
        oldPassword = findViewById(R.id.oldPassword);
        deleteAcct = findViewById(R.id.deleteAcct);
        acctDelete = findViewById(R.id.acctDelete);
        acctDelete.setVisibility(View.INVISIBLE);
        deleteAcct.setVisibility(View.INVISIBLE);
        oldPassword.setVisibility(View.INVISIBLE);
        changePass.setVisibility(View.INVISIBLE);
        confirmDelete.setVisibility(View.INVISIBLE);
        confirmNewPass.setVisibility(View.INVISIBLE);


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
                AppVars.isAdmin = false;

                BufferedWriter writer = null;
                try {
                    writer = new BufferedWriter(new FileWriter(token));
                    writer.write("");
                    writer.close();
                    BufferedWriter writer1 = new BufferedWriter(new FileWriter(admin));
                    writer1.write("");
                    writer1.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                token.delete();

                Intent intent = new Intent(Settings.this, Login.class);
                startActivity(intent);

            }
        });

        if (AppVars.isAdmin) {
            toAdmin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Settings.this, AdminSetting.class);
                    //intent.putExtra("enabled" , AppVars.isEnabled);
                    startActivity(intent);

                }
            });
        } else {
            toAdmin.setVisibility(View.INVISIBLE);
        }


        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                changePass.setVisibility(View.VISIBLE);
                confirmDelete.setVisibility(View.VISIBLE);
                confirmNewPass.setVisibility(View.VISIBLE);
                oldPassword.setVisibility(View.VISIBLE);
                passStat.setText("");
                changePass.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean validPass = false;

                        String passText = confirmNewPass.getText().toString();

                        if(passText.equals(confirmDelete.getText().toString())) {
                            if(passText.length() > 6){
                                validPass = true;
                            }else{
                                passStat.setText("Password must be greater than 6 characters!");
                                validPass = false;
                            }
                        }else{
                            passStat.setText("Passwords do not match!");
                        }
                        if(validPass) {
                            JSONObject body = new JSONObject();
                            try {
                                body.put("token",AppVars.userToken);
                                body.put("newPassword", confirmDelete.getText());
                                body.put("oldPassword" , oldPassword.getText());
                            } catch (JSONException e) {
                            }

                            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                                    (Request.Method.POST, changePasswordUrl, body, new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            try {
                                                String accepted = response.get("status").toString();
                                                // If I understand tokens correctly, no token means auth failed
                                                if (accepted.toString().equals("ACCEPTED")) {
                                                    passStat.setText("Password Changed.");
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            passStat.setText("Something went wrong!");
                                        }
                                    }) {
                            };
                            AppController.getInstance().addToRequestQueue(jsonObjectRequest, tag_json_obj);
                        }
                        changePass.setVisibility(View.INVISIBLE);
                        confirmDelete.setVisibility(View.INVISIBLE);
                        confirmNewPass.setVisibility(View.INVISIBLE);
                    }
                });

            }
        });

        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAcct.setVisibility(View.VISIBLE);
                acctDelete.setVisibility(View.VISIBLE);
                acctDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pDialog.setMessage("Deleting Account...");
                        pDialog.show();

                        JSONObject body = new JSONObject();
                        try {
                            body.put("token", AppVars.userToken);
                            body.put("password", deleteAcct.getText());
                        } catch (JSONException e) {
                            ((TextView) findViewById(R.id.deleteResponse)).setText("Could not delete!");
                        }

                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                                (Request.Method.POST, url, body, new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        ((TextView)findViewById(R.id.deleteResponse)).setText(response.toString());

                                        pDialog.hide();

                                        deleteStatus.setText("Account deleted");
                                        BufferedWriter writer = null;
                                        try {
                                            writer = new BufferedWriter(new FileWriter(token));
                                            writer.write("");
                                            writer.close();
                                            BufferedWriter writer1 = new BufferedWriter(new FileWriter(admin));
                                            writer1.write("");
                                            writer1.close();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        token.delete();

                                        Intent intent = new Intent(Settings.this, Login.class);
                                        startActivity(intent);
                                    }

                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        deleteStatus.setText("Something went wrong");
                                        pDialog.hide();
                                    }
                                });
                        AppController.getInstance().addToRequestQueue(jsonObjectRequest, tag_json_obj);
                    }
                });
            }
        });
    }
}

