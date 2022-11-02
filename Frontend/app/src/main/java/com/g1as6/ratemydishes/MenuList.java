package com.g1as6.ratemydishes;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.g1as6.ratemydishes.app.AppController;
import com.g1as6.ratemydishes.utils.MenuFragment;

import org.json.JSONException;
import org.json.JSONObject;

public class MenuList extends AppCompatActivity {

    private ImageButton backToDining;
    private String url = "http://coms-309-006.class.las.iastate.edu:8080/menu/get-menu/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String u = url;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_list);

        backToDining = findViewById(R.id.backToDining);

        backToDining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MenuList.this, WelcomePage.class);
                startActivity(intent);
            }
        });

        Bundle extras = getIntent().getExtras();
        JSONObject slug = reconstructJSON(extras.getString("slug"));

        try {
            // Slugs have quotes sadge
            String s = slug.getString("slug");

            u = u + s;
        } catch (JSONException e) { /* Bro this has literally never failed */   }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, u, slug, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        int lastId = findViewById(R.id.welcomeText4).getId();


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        System.out.print("Sad Test");
                    }
                }) {
        };

        AppController.getInstance().addToRequestQueue(jsonObjectRequest, "tag_json_obj");
    }

    public JSONObject reconstructJSON(String s){
        try{
            JSONObject slug = new JSONObject();
            slug.put("slug", s);

            return slug;
        }catch (JSONException e){
            return null;
        }
    }
}