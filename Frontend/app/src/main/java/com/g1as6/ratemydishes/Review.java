package com.g1as6.ratemydishes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.g1as6.ratemydishes.app.AppController;
import com.g1as6.ratemydishes.app.AppVars;
import com.g1as6.ratemydishes.fragment.CustomAdapter;
import com.g1as6.ratemydishes.fragment.DemoCollectionAdapter;
import com.g1as6.ratemydishes.fragment.ReviewAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

// TODO: IMPORTANT! Dataset doesn't update unless scrolled by user!
// Find a way to fix this
public class Review extends AppCompatActivity {

    private ImageButton backToDining;
    private RecyclerView recycler;
    private ReviewAdapter adapter;
    private LinearLayoutManager manager;
    private RatingBar bar;
    private EditText review;
    private Button btn;
    private JSONArray food;
    private String token;
    private WebSocketClient client;
    private ArrayList<String> messages = new ArrayList<String>();
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_page);

        bar = findViewById(R.id.ratingBar);
        btn = findViewById(R.id.reviewBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject message = new JSONObject();
                ReviewAdapter adapter = (ReviewAdapter) recycler.getAdapter();

                float rating = bar.getRating();
                String msg = review.getText().toString();

                try {
                    message.put("comment", msg);
                    message.put("rating", rating);

                    client.send(message.toString());
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) { e.printStackTrace(); }

            }
        });

        review = findViewById(R.id.reviewBox);

        try {
            food = new JSONArray(getIntent().getExtras().getString("food"));

            connectURI(AppVars.netId, food.getJSONObject(5).getInt("id"));

            recycler = (RecyclerView) findViewById(R.id.recyclerView);
            recycler.smoothScrollBy(1, 1);
            manager = new LinearLayoutManager(this);
            adapter = new ReviewAdapter(messages);

            recycler.setAdapter(adapter);
            recycler.setLayoutManager(manager);

            setupPage(food);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        backToDining = findViewById(R.id.backToDining);

        backToDining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Review.this, MenuList.class);
                intent.putExtra("slug", getIntent().getExtras().getString("slug"));

                startActivity(intent);
            }
        });

    }

    private void setupPage(JSONArray food) throws JSONException {
        ((RatingBar) findViewById(R.id.avgRatings)).setRating((float) food.getJSONObject(6).getDouble("average-rating"));
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

    // Connect to websocket
    public void connectURI(String token, int id) {
        URI uri;

        try {
            uri = new URI("ws://coms-309-006.class.las.iastate.edu:8080/review/"+token+"/"+id);

            client = new WebSocketClient(uri) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {

                }

                @Override
                public void onMessage(String message) {
                    boolean found = false;

                    for(int i = 0; i < messages.size(); i++){
                        // A little sloppy but I'm tired
                        // Check to see if a message already exists
                        try{
                            JSONObject guest = new JSONObject(message);
                            JSONObject host = new JSONObject(messages.get(i));

                            if(guest.getString("netId").equals(host.getString("netId"))){
                                found = true;

                                messages.set(i, message);
                            }

                        }catch(JSONException e) { e.printStackTrace(); }
                    }

                    if(!found) {
                        messages.add(message);
                    }

                    recycler.post(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {

                }

                @Override
                public void onError(Exception ex) {

                }
            };

            client.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }
    }
}