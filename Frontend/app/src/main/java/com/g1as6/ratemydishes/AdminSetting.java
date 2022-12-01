package com.g1as6.ratemydishes;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AdminSetting extends AppCompatActivity {

    private ImageButton backToSettings;
    private String url = "http://coms-309-006.class.las.iastate.edu:8080/admin/";
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

        protected void populateScreen(){
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                        @Override
                        public void onResponse(JSONArray response) {
                            // Access each element in the jsonarray
                            int lastId = findViewById(R.id.welcomeText4).getId();
                            for(int i = 0; i < response.length(); i++){
                                try { // Rip readability
                                    // Lots of definitions
                                    final ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.parent);
                                    final ConstraintSet set = new ConstraintSet();
                                    final Button btn = new Button(RestaurantList.this);
                                    DisplayMetrics displayMetrics = new DisplayMetrics();
                                    JSONObject object = (JSONObject)response.get(i);
                                    String title = object.getString("title");

                                    getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

                                    btn.setId(View.generateViewId());
                                    btn.setText(title);
                                    btn.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                    btn.setTextSize(24);
                                    btn.setWidth(displayMetrics.widthPixels);
                                    btn.setBackgroundColor(0xFFF4F4D4);
                                    btn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v){
                                            // Destruct slug then send it as string for a much better life experience
                                            Intent intent = new Intent(RestaurantList.this, MenuList.class);
                                            try {
                                                intent.putExtra("slug", object.getString("slug"));
                                                startActivity(intent);
                                            }catch (JSONException e){   }

                                        }
                                    });

                                    set.constrainHeight(btn.getId(), ConstraintSet.WRAP_CONTENT);
                                    set.constrainWidth(btn.getId(), ConstraintSet.WRAP_CONTENT);

                                    layout.addView(btn,0);

                                    set.clone(layout);
                                    set.connect(btn.getId(), 3, lastId, 4);
                                    set.applyTo(layout);

                                    lastId = btn.getId();
                                }catch(JSONException e){  /* lol you expect this to get handled? lol */  }
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
    }
}
