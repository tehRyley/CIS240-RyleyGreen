package com.example.uberfareapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button buttonTwo = findViewById(R.id.btnRequest);
        buttonTwo.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
            startActivity(intent);
        });

        Button button = findViewById(R.id.btnBack);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity2.this, MainActivity.class);
            startActivity(intent);
        });

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String cost = sharedPreferences.getString("Cost", "");
        String miles = sharedPreferences.getString("Miles", "");
        String car = sharedPreferences.getString("Car", "");
        String value = sharedPreferences.getString("Value", "");

        switch (value) {
            case "1":
                value = " +$2";
                break;
            case "2":
                value = "";
                break;
            case "3":
                value = " +$5";
                break;
        }
        TextView txtChosenCar = findViewById(R.id.txtChosenCar);
        String carText = car + value;
        txtChosenCar.setText(carText);
        TextView txtChosenMiles = findViewById(R.id.txtChosenMiles);
        String milesText = "$3 + " + "(" + miles + " Miles * $3.25)";
        txtChosenMiles.setText(milesText);
        TextView txtChosenTotal = findViewById(R.id.txtChosenTotal);
        String totalText = "Total: " + "$" + cost;
        txtChosenTotal.setText(totalText);
    }
}