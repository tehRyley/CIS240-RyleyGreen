package com.cis240.movieapp;

import android.widget.ImageView;
import com.squareup.picasso.Picasso;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class MovieBuilder {
    static String posterUrl = "https://image.tmdb.org/t/p/w500";
    public static void buildMovie(String title, String release, String score, String poster, String desc) {
        Picasso.get().load(posterUrl + poster).into(MainActivity.imageView);
        MainActivity.titleView.setText(title);
        MainActivity.releaseView.setText(release);
        MainActivity.descView.setText(desc);
        if (score.equals("0")) {
            MainActivity.scoreView.setText("Not Released");
        } else {
            MainActivity.scoreView.setText(score + "/10");
        }
    }

}