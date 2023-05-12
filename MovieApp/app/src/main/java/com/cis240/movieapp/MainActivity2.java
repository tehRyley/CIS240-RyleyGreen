package com.cis240.movieapp;

import static com.cis240.movieapp.FavoriteList.favorites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity2 extends AppCompatActivity {
    Button backBtn;
    String favoritesUrl = "https://api.themoviedb.org/3/movie/"+ favorites.get(0) +"?api_key=8159d23abb93295d11bd8c077eb4629d&language=en-US";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        backBtn = findViewById(R.id.backBtn);
        backBtn.setBackgroundColor(getResources().getColor(R.color.red));
        backBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity2.this, MainActivity.class);
            startActivity(intent);
        });
    }
}