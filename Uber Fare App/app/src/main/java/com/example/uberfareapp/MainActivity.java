package com.example.uberfareapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText txtMiles;
    private RadioGroup car;
    private RadioButton radioButton;
    private double cost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.btnCost);
        button.setOnClickListener(view -> {
            txtMiles = findViewById(R.id.editMiles);
            double miles = Integer.parseInt(txtMiles.getText().toString());
            car = findViewById(R.id.radioGroup);
            int selectedId = car.getCheckedRadioButtonId();
            radioButton = findViewById(selectedId);
            String radioVal = radioButton.getText().toString();
            double base = 3.00 + (miles * 3.25);
            switch (radioVal) {
                case "Smart Car":
                    cost = base + 2.00;
                    break;
                case "Traditional Sedan":
                    cost = base;
                    break;
                case "Minivan":
                    cost = base + 5.00;
                    break;
            }

            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("Cost", String.valueOf(cost));
            editor.putString("Miles", String.valueOf(miles));
            editor.putString("Car", radioVal);
            editor.apply();

            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            startActivity(intent);
        });
    }
}