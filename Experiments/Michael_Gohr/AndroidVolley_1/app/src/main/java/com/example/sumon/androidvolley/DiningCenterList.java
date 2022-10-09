package com.example.sumon.androidvolley;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.example.sumon.androidvolley.app.AppController;
import com.example.sumon.androidvolley.utils.Const;

public class DiningCenterList extends Activity {

    private String TAG = DiningCenterList.class.getSimpleName();
    private Button btnDiningLocations;
    private TextView diningLocations;
    private ProgressDialog loadScreen;

    // This tag will be used to cancel the request
    private String tag_string_req = "string_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dining_center_list);

        btnDiningLocations = (Button) findViewById(R.id.btnDiningLocations);
        diningLocations = (TextView) findViewById(R.id.diningLocations);

        loadScreen = new ProgressDialog(this);
        loadScreen.setMessage("Loading...");
        loadScreen.setCancelable(false);

        btnDiningLocations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDiningLocations();
            }
        });
    }

    private void showProgressDialog() {
        if (!loadScreen.isShowing())
            loadScreen.show();
    }

    private void hideProgressDialog() {
        if (loadScreen.isShowing())
            loadScreen.hide();
    }

    private void getDiningLocations() {
        showProgressDialog();

        StringRequest strReq = new StringRequest(Method.GET, Const.URL_STRING_REQ, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                diningLocations.setText(response.toString());
                hideProgressDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hideProgressDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }
}