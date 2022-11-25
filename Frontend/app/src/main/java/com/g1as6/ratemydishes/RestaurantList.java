package com.g1as6.ratemydishes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.g1as6.ratemydishes.app.AppController;
import com.g1as6.ratemydishes.app.AppVars;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RestaurantList extends AppCompatActivity {

    private ImageButton backToDining;
    private String url = "http://coms-309-006.class.las.iastate.edu:8080/location/";
    //private String url = "https://ce96178b-dd5e-4c1b-80e8-def480aa7eb4.mock.pstmn.io/location/";
    private Button lastButton;
    protected static AppVars.Restaurant loc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_list);

        backToDining = findViewById(R.id.backToDining);

        backToDining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(RestaurantList.this, WelcomePage.class);
                startActivity(intent);

            }
        });

        Bundle extras = getIntent().getExtras();
        loc = (AppVars.Restaurant)extras.getSerializable("type");

        switch(loc){
            case CAFE:
                url += "get-cafe";
                break;
            case GET_AND_GO:
                url += "get-get-go";
                break;
            case FAST_CASUAL:
                url += "get-fast-casual";
                break;
            case DINING_CENTER:
                url += "get-dining-centers";
                break;
            default:
                break;
        }

        populateScreen();
    }

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