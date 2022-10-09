package com.example.sumon.androidvolley;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {
    private Button toCafeList, toDiningCentersList, toFastCasualList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toDiningCentersList = (Button) findViewById(R.id.toDiningCentersList);
        toCafeList = (Button) findViewById(R.id.toCafeList);
        toFastCasualList = (Button) findViewById(R.id.toFastCasualList);

        // button click listeners
        toDiningCentersList.setOnClickListener(this);
        toCafeList.setOnClickListener(this);
        toFastCasualList.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toDiningCentersList:
                startActivity(new Intent(MainActivity.this,
                        DiningCenterList.class));
                break;
            case R.id.toCafeList:
                startActivity(new Intent(MainActivity.this,
                        CafeList.class));
                break;
            case R.id.toFastCasualList:
                startActivity(new Intent(MainActivity.this,
                        FastCasualList.class));
                break;
            default:
                break;
        }
    }

}
