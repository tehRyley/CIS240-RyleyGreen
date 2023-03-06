package com.example.uberfareapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        int hour = (int)(Math.random() * 8) +2;
        int minutes = (int)(Math.random() * 58) +2;
        TextView txtTimer = findViewById(R.id.txtTimer);
        String timer = "Arriving in " + hour + " hours and " + minutes + " minutes";
        txtTimer.setText(timer);

    }
}