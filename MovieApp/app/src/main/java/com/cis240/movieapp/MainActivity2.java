package com.cis240.movieapp;

import static com.cis240.movieapp.FavoriteList.favorites;
import static com.cis240.movieapp.FavoriteList.writeToFile;
import static com.cis240.movieapp.MainActivity.queue;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;

public class MainActivity2 extends AppCompatActivity {
    Button backBtn;
    static int value = 0;
    String favoritesUrl = "https://api.themoviedb.org/3/movie/"+ favorites.get(value) +"?api_key=8159d23abb93295d11bd8c077eb4629d&language=en-US";
    String id;
    String MOVIE_STATE;
    ImageView imageView;
    TextView titleView;
    TextView releaseView;
    TextView scoreView;
    TextView descView;
    Button favBtn;
    ImageView leftBtn;
    ImageView rightBtn;
    String unfavorite = "Unfavorite";
    String favorite = "Favorite";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        imageView = findViewById(R.id.poster);
        titleView = findViewById(R.id.title);
        releaseView = findViewById(R.id.release);
        favBtn = findViewById(R.id.button);
        scoreView = findViewById(R.id.rating);
        descView = findViewById(R.id.desc);
        //Check for saved instance
        if (savedInstanceState == null) {
            favoriteLoader(favoritesUrl);
        } else {
            String movieState = savedInstanceState.getString(MOVIE_STATE);
            setState(movieState);
        }
        leftBtn = findViewById(R.id.leftBtn);
        leftBtn.setOnClickListener(view -> {
            value -= 1;
            if(value < 0) {
                value = favorites.size()-1;
            }
            favoritesUrl = "https://api.themoviedb.org/3/movie/"+ favorites.get(value) +"?api_key=8159d23abb93295d11bd8c077eb4629d&language=en-US";
            favoriteLoader(favoritesUrl);
        });
        rightBtn = findViewById(R.id.rightBtn);
        rightBtn.setOnClickListener(view -> {
            value += 1;
            if(value > favorites.size()-1) {
                value = 0;
            }
            favoritesUrl = "https://api.themoviedb.org/3/movie/"+ favorites.get(value) +"?api_key=8159d23abb93295d11bd8c077eb4629d&language=en-US";
            favoriteLoader(favoritesUrl);
        });
        favBtn.setOnClickListener(view -> {
            if (favorites.contains(id)) {
                favorites.remove(id);
                favBtn.setBackgroundColor(ContextCompat.getColor(this, R.color.yellow));
                favBtn.setTextColor(ContextCompat.getColor(this, R.color.black));
                favBtn.setText(favorite);
            } else {
                favorites.add(id);
                favBtn.setBackgroundColor(ContextCompat.getColor(this, R.color.dark));
                favBtn.setTextColor(ContextCompat.getColor(this, R.color.grey));
                favBtn.setText(unfavorite);
            }
            try {
                writeToFile(this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (favorites.size() == 0) {
                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(intent);
            }
        });
        backBtn = findViewById(R.id.backBtn);
        backBtn.setBackgroundColor(ContextCompat.getColor(this, R.color.red));
        backBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity2.this, MainActivity.class);
            startActivity(intent);
        });
    }

    public void favoriteLoader(String url){
        StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
            try {
                JSONObject root = new JSONObject(response);
                String title = root.get("title").toString();
                String release = root.get("release_date").toString();
                String score = String.valueOf(root.get("vote_average"));
                String poster = root.get("poster_path").toString();
                String desc = root.get("overview").toString();
                id = String.valueOf(root.get("id"));
                favBtn.setBackgroundColor(ContextCompat.getColor(this, R.color.dark));
                favBtn.setTextColor(ContextCompat.getColor(this, R.color.grey));
                favBtn.setText(unfavorite);
                MovieBuilder.buildMovie(title, release, score, poster, desc, imageView, titleView, releaseView, descView, scoreView);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }, error -> Log.d("error", error.toString()));
        queue.add(request);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(MOVIE_STATE, getState());
    }


    public static String getState() {
        return String.valueOf(value);
    }

    //Parse current index and page to maintain same movie
    public void setState(String movieState) {
        valueSet(movieState);
    }

    public void valueSet(String movieValue) {
        value = Integer.parseInt(movieValue);
        value = Integer.parseInt(movieValue);
        favoritesUrl = "https://api.themoviedb.org/3/movie/"+ favorites.get(value) +"?api_key=8159d23abb93295d11bd8c077eb4629d&language=en-US";
        favoriteLoader(favoritesUrl);
    }

}