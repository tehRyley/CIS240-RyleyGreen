package com.cis240.movieapp;

import static java.lang.Double.parseDouble;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

public class MovieBuilder {
    static String posterUrl = "https://image.tmdb.org/t/p/w500";

    //Fill in with parsed movie info
    public static void buildMovie(String title, String release, String score, String poster, String desc, ImageView imageView, TextView titleView, TextView releaseView, TextView descView, TextView scoreView) {
        Picasso.get().load(posterUrl + poster).into(imageView);
        titleView.setText(title);
        releaseView.setText(release);
        descView.setText(desc);
        if (parseDouble(score) == 0) {
            String notReleased = "Unreleased";
            scoreView.setText(notReleased);
            scoreView.setTextColor(Color.WHITE);
        } else {
            String newScore = parseDouble(score) + "/10";
            scoreView.setText(newScore);
            if (parseDouble(score) < 5) {
                scoreView.setTextColor(Color.RED);
            } else if (parseDouble(score) > 7) {
                scoreView.setTextColor(Color.GREEN);
            } else {
                scoreView.setTextColor(Color.YELLOW);
            }
        }
    }
}