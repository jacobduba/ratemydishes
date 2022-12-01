package com.g1as6.ratemydishes;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
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
    private String url = "http://coms-309-006.class.las.iastate.edu:8080/admin/toggle-category";
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

    }
    protected void populateScreen(){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        // Access each element in the jsonarray
                        //int lastId = findViewById(R.id.welcomeText4).getId();
                        try {
                            JSONArray locations = response.getJSONArray(Integer.parseInt("name"));


                            for(int i = 0; i < locations.length(); i++){
                                JSONObject inLocation = locations.getJSONObject(i);
                                JSONArray

                            }
                        }
                        catch(JSONException e){

                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        System.out.print(error.toString());
                    }
                });

        AppController.getInstance().addToRequestQueue(jsonArrayRequest, "tag_json_array");
    }

    /*protected void populateScreen(){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        // Access each element in the jsonarray
                        //int lastId = findViewById(R.id.welcomeText4).getId();
                        for(int i = 0; i < response.length(); i++){
                            try { // Rip readability
                                // Lots of definitions
                                final ScrollView layout = (ScrollView) findViewById(R.id.adminParent);
                                final ConstraintSet set = new ConstraintSet();
                                final Switch swt = new Switch(AdminSetting.this);
                                DisplayMetrics displayMetrics = new DisplayMetrics();
                                JSONObject object = (JSONObject)response.get(i);
                                String title = object.getString("name");

                                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

                                swt.setId(View.generateViewId());
                                swt.setText(title);
                                swt.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                swt.setTextSize(24);
                                swt.setWidth(displayMetrics.widthPixels);
                                swt.setBackgroundColor(0xFFF4F4D4);
                                swt.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v){
                                        // Destruct slug then send it as string for a much better life experience
                                        Intent intent = new Intent(AdminSetting.this, Settings.class);
                                        try {
                                            intent.putExtra("slug", object.getString("slug"));
                                            startActivity(intent);
                                        }catch (JSONException e){   }

                                    }
                                });
                                set.constrainHeight(swt.getId(), ConstraintSet.WRAP_CONTENT);
                                set.constrainWidth(swt.getId(), ConstraintSet.WRAP_CONTENT);

                                layout.addView(swt,0);

                                //set.clone(layout);
                                //set.connect(swt.getId(), 3, lastId, 4);
                                //set.applyTo(layout);

                                //lastId = swt.getId();
                            }catch(JSONException e){  }
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        System.out.print(error.toString());
                    }
                });

        AppController.getInstance().addToRequestQueue(jsonArrayRequest, "tag_json_array");
    }*/
}
