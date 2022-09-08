package com.example.demo1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.demo1.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // .xml style listener
    public void toMelon(View view){
        Intent intent = new Intent(this, MelonAndFinn.class);
        startActivity(intent);
    }

    public void toHello(View view){
        Intent intent = new Intent(this, Hello.class);
        EditText editText = (EditText) findViewById(R.id.txtIn);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}