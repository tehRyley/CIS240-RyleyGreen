package com.example.uberfareapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity3 extends AppCompatActivity {
    private TextView txtTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        int hour = (int)(Math.random() * 8) +2;
        int minutes = (int)(Math.random() * 58) +2;
        txtTimer = findViewById(R.id.txtTimer);
        txtTimer.setText("Arriving in " + hour + " hours and " + minutes + " minutes");

    }
}