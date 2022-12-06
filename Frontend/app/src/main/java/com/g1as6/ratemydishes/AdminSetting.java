package com.g1as6.ratemydishes;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.Switch;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.g1as6.ratemydishes.app.AppController;
import com.g1as6.ratemydishes.app.AppVars;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.app.BundleCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AdminSetting extends AppCompatActivity {

    private ImageButton backToSettings;
    private String url = "http://coms-309-006.class.las.iastate.edu:8080/admin/toggle-location";
    private String urlTwo = "http://coms-309-006.class.las.iastate.edu:8080/admin/get-settings";
    private String urlThree = "http://coms-309-006.class.las.iastate.edu:8080/admin/toggle-category";
    //protected static AppVars.isEnabled locations;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_setting);
        backToSettings = findViewById(R.id.adminToSettings);

        backToSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //Edit so that this goes back to welcome instead of main
                Intent intent = new Intent(AdminSetting.this, Settings.class);
                startActivity(intent);
            }

        });
        populateScreen();
    }

    protected void switchOnOrOff(boolean state , String title)throws JSONException{
        JSONObject body = new JSONObject();
        try {
            body.put("token",AppVars.userToken);
            body.put("name" , title);
            body.put("enabled" , state);
        } catch (JSONException e) {
        }
        JsonObjectRequest request = new JsonObjectRequest
        (Request.Method.POST, url, body, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                System.out.print(response.toString());

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                System.out.print(error.toString());
            }
        });
        AppController.getInstance().addToRequestQueue(request, "tag_json_array");
    }

    protected void switchOnOrOffCategories(boolean state , String title)throws JSONException{
        JSONObject body = new JSONObject();
        try {
            body.put("token",AppVars.userToken);
            body.put("name" , title);
            body.put("enabled" , state);
        } catch (JSONException e) {
        }
        JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.POST, urlThree, body, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.print(response.toString());

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        System.out.print(error.toString());
                    }
                });
        AppController.getInstance().addToRequestQueue(request, "tag_json_array");
    }

    protected void populateScreen(){
        JSONObject body = new JSONObject();
        try {
            body.put("token",AppVars.userToken);
        } catch (JSONException e) {
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, urlTwo, body, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray array = new JSONArray();
                        JSONArray categories = new JSONArray();
                        try {
                            array = response.getJSONArray("locations");
                            categories = response.getJSONArray("categories");
                            int lastId = (R.id.welcomeText5);
                            for(int i = 0; i < array.length(); i++){
                                try { // Rip readability
                                    // Lots of definitions
                                    final ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.layoutId);
                                    final ConstraintSet set = new ConstraintSet();
                                    final Switch swt = new Switch(AdminSetting.this);
                                    DisplayMetrics displayMetrics = new DisplayMetrics();
                                    JSONObject object = (JSONObject)array.get((i));
                                    String title = object.getString("name");
                                    Boolean enabled = object.getBoolean("enabled");

                                    getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

                                    swt.setId(View.generateViewId());
                                    swt.setText(title);
                                    swt.setChecked(enabled);
                                    swt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                        @Override
                                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                            try {
                                                switchOnOrOff(b, title);
                                            } catch (JSONException e) {
                                            }
                                        }
                                    });
                                    set.constrainHeight(swt.getId(), ConstraintSet.WRAP_CONTENT);
                                    set.constrainWidth(swt.getId(), ConstraintSet.WRAP_CONTENT);

                                    layout.addView(swt,0);

                                    set.clone(layout);
                                    set.connect(swt.getId(), 3, lastId, 4);
                                    set.applyTo(layout);

                                    lastId = swt.getId();
                                }catch(JSONException e){  }

                                for(int j = 0; j < categories.length(); j= j + 80){
                                    try { // Rip readability
                                        // Lots of definitions
                                        final ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.layoutId);
                                        final ConstraintSet set = new ConstraintSet();
                                        final Switch swt = new Switch(AdminSetting.this);
                                        DisplayMetrics displayMetrics = new DisplayMetrics();
                                        JSONObject object = (JSONObject)categories.get((i));
                                        String title = object.getString("name");
                                        Boolean enabled = object.getBoolean("enabled");

                                        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

                                        swt.setId(View.generateViewId());
                                        swt.setText(title);
                                        swt.setChecked(enabled);
                                        swt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                            @Override
                                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                                try {
                                                    switchOnOrOffCategories(b, title);
                                                } catch (JSONException e) {
                                                }
                                            }
                                        });
                                        set.constrainHeight(swt.getId(), ConstraintSet.WRAP_CONTENT);
                                        set.constrainWidth(swt.getId(), ConstraintSet.WRAP_CONTENT);

                                        layout.addView(swt,0);

                                        set.clone(layout);
                                        set.connect(swt.getId(), 3, lastId, 4);
                                        set.applyTo(layout);

                                        lastId = swt.getId();
                                    }catch(JSONException e){  }

                                }

                            }
                        } catch (JSONException e) {
                            //e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        System.out.print(error.toString());
                    }
                });

        AppController.getInstance().addToRequestQueue(jsonObjectRequest, "tag_json_array");
    }
}
