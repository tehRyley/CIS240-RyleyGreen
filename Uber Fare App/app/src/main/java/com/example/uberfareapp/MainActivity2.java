package com.example.uberfareapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {
    private TextView txtChosenCar;
    private TextView txtChosenMiles;
    private TextView txtChosenTotal;

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

        txtChosenCar = findViewById(R.id.txtChosenCar);
        txtChosenCar.setText(car);
        txtChosenMiles = findViewById(R.id.txtChosenMiles);
        txtChosenMiles.setText(miles + " Miles");
        txtChosenTotal = findViewById(R.id.txtChosenTotal);
        txtChosenTotal.setText("Total: " + "$" +cost);
    }
}