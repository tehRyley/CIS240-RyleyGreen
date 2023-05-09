package com.cis240.movieapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    static Random rand = new Random();
    static RequestQueue queue;
    private final String MOVIE_STATE = "movieState";
    static int ind = rand.nextInt(19);
    static int page = (rand.nextInt(19)) + 1;
    ImageView imageView;
    TextView titleView;
    TextView releaseView;
    TextView scoreView;
    TextView descView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        imageView = findViewById(R.id.poster);
        titleView = findViewById(R.id.title);
        releaseView = findViewById(R.id.release);
        scoreView = findViewById(R.id.rating);
        descView = findViewById(R.id.desc);
        queue = Volley.newRequestQueue(this);
        Button button = findViewById(R.id.movieGen);
        button.setOnClickListener(view -> {
            ind = rand.nextInt(19);
            page = (rand.nextInt(19)) + 1;
            movieLoader(ind, page);
        });
        if (savedInstanceState == null) {
            ind = rand.nextInt(19);
            page = (rand.nextInt(19)) + 1;
            movieLoader(ind, page);
        } else {
            String movieState = savedInstanceState.getString(MOVIE_STATE);
            setState(movieState);
        }
    }

    public void movieLoader(int ind, int page) {
        String urlPop = "https://api.themoviedb.org/3/discover/movie?api_key=8159d23abb93295d11bd8c077eb4629d&language=en-US&sort_by=popularity.desc&include_adult=false&page=" + page;
        StringRequest request = new StringRequest(Request.Method.GET, urlPop, response -> {
            try {
                JSONObject root = new JSONObject(response);
                JSONArray result = root.getJSONArray("results");
                String title = result.getJSONObject(ind).get("title").toString();
                String release = result.getJSONObject(ind).get("release_date").toString();
                String score = String.valueOf(result.getJSONObject(ind).get("vote_average"));
                String poster = result.getJSONObject(ind).get("poster_path").toString();
                String desc = result.getJSONObject(ind).get("overview").toString();
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
        return "ind:" + MainActivity.ind + "one" + "page:" + MainActivity.page + "two";
    }

    public void setState(String movieState) {
        int ind = Integer.parseInt(movieState.substring(movieState.indexOf("ind") + 4, movieState.lastIndexOf("one")));
        int page = Integer.parseInt(movieState.substring(movieState.indexOf("page") + 5, movieState.lastIndexOf("two")));
        movieLoader(ind, page);
    }

}