package com.g1as6.ratemydishes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.g1as6.ratemydishes.app.AppController;
import com.g1as6.ratemydishes.fragment.DemoCollectionAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Review extends AppCompatActivity {

    private ImageButton backToDining;
    private JSONArray food;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_page);

        try {
            food = new JSONArray(getIntent().getExtras().getString("food"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        backToDining = findViewById(R.id.backToDining);

        backToDining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Review.this, WelcomePage.class);
                startActivity(intent);
            }
        });

        try {
            setupPage(food);
        } catch (JSONException e) { e.printStackTrace(); }
    }

    private void setupPage(JSONArray food) throws JSONException {
        ((RatingBar) findViewById(R.id.avgRatings)).setNumStars(food.getJSONObject(6).getInt("average-rating"));
        ((TextView) findViewById(R.id.foodTitle)).setText(food.getJSONObject(0).getString("name"));
        ((TextView) findViewById(R.id.cal)).setText(food.getJSONObject(3).getString("total-calories") + " calories");

        if(food.getJSONObject(1).getInt("isHalal") == 0) {
            ((TextView) findViewById(R.id.hala)).setText("Not Halal Friendly");
        }else{
            ((TextView) findViewById(R.id.hala)).setText("Halal Friendly");
        }

        if(food.getJSONObject(2).getInt("isVegan") == 0) {
            ((TextView) findViewById(R.id.vega)).setText("Not Vegan Friendly");
        }else{
            ((TextView) findViewById(R.id.vega)).setText("Vegan Friendly");
        }

        if(food.getJSONObject(4).getInt("isVegetarian") == 0) {
            ((TextView) findViewById(R.id.vege)).setText("Not Vegetarian Friendly");
        }else{
            ((TextView) findViewById(R.id.vege)).setText("Vegetarian Friendly");
        }
    }
}