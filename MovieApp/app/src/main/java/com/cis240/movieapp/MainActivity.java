package com.cis240.movieapp;


import static com.cis240.movieapp.FavoriteList.favorites;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
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

import java.util.Objects;
import java.util.Random;

@SuppressWarnings("deprecation")
public class MainActivity extends AppCompatActivity {
    //Declare some variables
    static Random rand = new Random();
    static RequestQueue queue;
    private final String MOVIE_STATE = "movieState";
    static int ind = rand.nextInt(19);
    static int page = (rand.nextInt(19)) + 1;
    static String urlPop = "https://api.themoviedb.org/3/discover/movie?api_key=8159d23abb93295d11bd8c077eb4629d&language=en-US&sort_by=popularity.desc&include_adult=false&page=" + page;
    static String query;
    static String searchUrl = "https://api.themoviedb.org/3/search/movie?api_key=8159d23abb93295d11bd8c077eb4629d&language=en-US&query="+ query +"&page=1&include_adult=false";
    static int searchToggle = 0;
    int yellow = R.color.yellow;
    String unfavorite = "Unfavorite";
    String favorite = "Favorite";
    ImageView imageView;
    TextView titleView;
    TextView releaseView;
    TextView scoreView;
    TextView descView;
    SearchView searchView;
    Button favBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Set initial night mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        //Set to nodes
        imageView = findViewById(R.id.poster);
        titleView = findViewById(R.id.title);
        releaseView = findViewById(R.id.release);
        favBtn = findViewById(R.id.button);
        scoreView = findViewById(R.id.rating);
        descView = findViewById(R.id.desc);
        //When tested on physical device the search bar doesn't auto focus (makes it less annoying)
        searchView = findViewById(R.id.searchView2);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchMovie(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.length() > 0) {
                    searchMovie(s);
                }
                return false;
            }
        });
        queue = Volley.newRequestQueue(this);
        //Button to get new index and page then build movie
        Button button = findViewById(R.id.movieGen);
        button.setOnClickListener(view -> {
            ind = rand.nextInt(19);
            page = (rand.nextInt(19)) + 1;
            urlPop = "https://api.themoviedb.org/3/discover/movie?api_key=8159d23abb93295d11bd8c077eb4629d&language=en-US&sort_by=popularity.desc&include_adult=false&page=" + page;
            movieLoader(ind, urlPop);
        });
        //Check for saved instance
        if (savedInstanceState == null) {
            ind = rand.nextInt(19);
            page = (rand.nextInt(19)) + 1;
            urlPop = "https://api.themoviedb.org/3/discover/movie?api_key=8159d23abb93295d11bd8c077eb4629d&language=en-US&sort_by=popularity.desc&include_adult=false&page=" + page;
            movieLoader(ind, urlPop);
        } else {
            String movieState = savedInstanceState.getString(MOVIE_STATE);
            setState(movieState);
        }
        favBtn.setBackgroundColor(getResources().getColor(yellow));
        favBtn.setOnClickListener(view -> {
            if (favorites.contains(titleView.getText().toString())) {
                favorites.remove(titleView.getText().toString());
                favBtn.setBackgroundColor(getResources().getColor(R.color.yellow));
                favBtn.setTextColor(getResources().getColor(R.color.black));
                favBtn.setText(favorite);
            } else {
                favorites.add(titleView.getText().toString());
                favBtn.setBackgroundColor(getResources().getColor(R.color.dark));
                favBtn.setTextColor(getResources().getColor(R.color.grey));
                favBtn.setText(unfavorite);
            }
        });
    }

    private void searchMovie(String query) {
        searchUrl = "https://api.themoviedb.org/3/search/movie?api_key=8159d23abb93295d11bd8c077eb4629d&language=en-US&query="+ query +"&page=1&include_adult=false";
        ind = 0;
        page = 1;
        searchToggle = 1;
        movieLoader(0, searchUrl);
    }

    //Volley request and parse JSON for required info
    public void movieLoader(int ind, String url) {
        if (Objects.equals(url, urlPop)) {
            searchToggle = 0;
        } else {
            searchToggle = 1;
        }

        //Future reference
        //https://api.themoviedb.org/3/movie/575322/credits?api_key=8159d23abb93295d11bd8c077eb4629d&language=en-US <movie cast call
        //https://image.tmdb.org/t/p/w500/yQceTOuTGiNuy31LuehQW432t7p.jpg <cast image

        //Grabs random page from TMDB API with a set of parameters (Rarely movies get passed the TMDB filters)
        StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
            try {
                JSONObject root = new JSONObject(response);
                JSONArray result = root.getJSONArray("results");
                //Checks if result is empty or not
                if (result.length() > 0) {
                    String title = result.getJSONObject(ind).get("title").toString();
                    String release = result.getJSONObject(ind).get("release_date").toString();
                    String score = String.valueOf(result.getJSONObject(ind).get("vote_average"));
                    String poster = result.getJSONObject(ind).get("poster_path").toString();
                    String desc = result.getJSONObject(ind).get("overview").toString();
                    if (favorites.contains(title)) {
                        favBtn.setBackgroundColor(getResources().getColor(R.color.dark));
                        favBtn.setTextColor(getResources().getColor(R.color.grey));
                        favBtn.setText(unfavorite);
                    } else {
                        favBtn.setBackgroundColor(getResources().getColor(R.color.yellow));
                        favBtn.setTextColor(getResources().getColor(R.color.black));
                        favBtn.setText(favorite);
                    }
                    MovieBuilder.buildMovie(title, release, score, poster, desc, imageView, titleView, releaseView, descView, scoreView);
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }, error -> Log.d("error", error.toString()));
        queue.add(request);
    }

    //Grabs current index and page into string
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(MOVIE_STATE, getState());
    }


    public static String getState() {
        return "ind:" + ind + "one" + "page:" + page + "two" + searchToggle;
    }

    //Parse current index and page to maintain same movie
    public void setState(String movieState) {
         searchToggle = Integer.parseInt(movieState.substring(movieState.indexOf("two") + 3));
         if (searchToggle == 0) {
             ind = Integer.parseInt(movieState.substring(movieState.indexOf("ind") + 4, movieState.lastIndexOf("one")));
             page = Integer.parseInt(movieState.substring(movieState.indexOf("page") + 5, movieState.lastIndexOf("two")));
             urlPop = "https://api.themoviedb.org/3/discover/movie?api_key=8159d23abb93295d11bd8c077eb4629d&language=en-US&sort_by=popularity.desc&include_adult=false&page=" + page;
             movieLoader(ind, urlPop);
         } else {
             movieLoader(0, searchUrl);
         }

    }
}