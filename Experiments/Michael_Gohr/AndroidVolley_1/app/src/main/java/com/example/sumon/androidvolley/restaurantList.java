package com.example.sumon.androidvolley;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class restaurantList extends Activity implements OnClickListener {
    private Button toCafeList, toDiningCentersList, toFastCasualList, toGetAndGoList, toConvenienceStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_list);

        toDiningCentersList = (Button) findViewById(R.id.toDiningCentersList);
        toCafeList = (Button) findViewById(R.id.toCafeList);
        toFastCasualList = (Button) findViewById(R.id.toFastCasualList);
        toGetAndGoList = (Button) findViewById(R.id.toGetAndGoList);
        toConvenienceStore = (Button) findViewById(R.id.toConvenienceStore);

        // button click listeners
        toDiningCentersList.setOnClickListener(this);
        toCafeList.setOnClickListener(this);
        toFastCasualList.setOnClickListener(this);
        toGetAndGoList.setOnClickListener(this);
        toConvenienceStore.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toDiningCentersList:
                startActivity(new Intent(restaurantList.this,
                        DiningCenterList.class));
                break;
            case R.id.toCafeList:
                startActivity(new Intent(restaurantList.this,
                        CafeList.class));
                break;
            case R.id.toFastCasualList:
                startActivity(new Intent(restaurantList.this,
                        FastCasualList.class));
                break;
            case R.id.toGetAndGoList:
                startActivity(new Intent(restaurantList.this,
                        GetAndGoList.class));
            case R.id.toConvenienceStore:
                startActivity(new Intent(restaurantList.this,
                        ConvenienceStoreList.class));
                break;
            default:
                break;
        }
    }

}
