package com.cis240.movieapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Random rand = new Random();
    JSONObject root;
    RequestQueue queue;
    static ImageView imageView;
    static TextView titleView;
    static TextView releaseView;
    static TextView scoreView;
    static TextView descView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.poster);
        titleView = findViewById(R.id.title);
        releaseView = findViewById(R.id.release);
        scoreView = findViewById(R.id.rating);
        descView = findViewById(R.id.desc);
        queue = Volley.newRequestQueue(this);
        Button button = findViewById(R.id.movieGen);
        button.setOnClickListener(view -> movieLoader());
        movieLoader();
    }

    public void movieLoader() {
        int ind = rand.nextInt(19);
        int page = (rand.nextInt(19)) + 1;
        String urlPop = "https://api.themoviedb.org/3/discover/movie?api_key=8159d23abb93295d11bd8c077eb4629d&language=en-US&sort_by=popularity.desc&include_adult=false&page=" + page;
        StringRequest request = new StringRequest(Request.Method.GET, urlPop, response -> {
            try {
                root = new JSONObject(response);
                JSONArray result = root.getJSONArray("results");
                String title = result.getJSONObject(ind).get("title").toString();
                String release = result.getJSONObject(ind).get("release_date").toString();
                String score = String.valueOf(result.getJSONObject(ind).get("vote_average"));
                String poster = result.getJSONObject(ind).get("poster_path").toString();
                String desc = result.getJSONObject(ind).get("overview").toString();
                MovieBuilder.buildMovie(title, release, score, poster, desc);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }, error -> Log.d("error",error.toString()));
        queue.add(request);
    }

}