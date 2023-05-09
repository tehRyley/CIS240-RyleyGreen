package com.cis240.movieapp;

import static java.lang.Double.parseDouble;
import android.graphics.Color;
import com.squareup.picasso.Picasso;
public class MovieBuilder {
    static String posterUrl = "https://image.tmdb.org/t/p/w500";
    public static void buildMovie(String title, String release, String score, String poster, String desc) {
        Picasso.get().load(posterUrl + poster).into(MainActivity.imageView);
        MainActivity.titleView.setText(title);
        MainActivity.releaseView.setText(release);
        MainActivity.descView.setText(desc);
        if (score.equals("0")) {
            String notReleased = "Not Released";
            MainActivity.scoreView.setText(notReleased);
            MainActivity.scoreView.setTextColor(Color.WHITE);
        } else {
            String newScore = score + "/10";
            MainActivity.scoreView.setText(newScore);
            if (parseDouble(score) < 5) {
                MainActivity.scoreView.setTextColor(Color.RED);
            } else if (parseDouble(score) > 7) {
                MainActivity.scoreView.setTextColor(Color.GREEN);
            } else {
                MainActivity.scoreView.setTextColor(Color.YELLOW);
            }
        }
    }

    public static String getState() {
        return "ind:" +
                MainActivity.ind + "one" +
                "page:" +
                MainActivity.page + "two";
    }

    public static void setState(String movieState) {
        int ind = Integer.parseInt(movieState.substring(movieState.indexOf("ind")+4, movieState.lastIndexOf("one")));
        int page = Integer.parseInt(movieState.substring(movieState.indexOf("page")+5, movieState.lastIndexOf("two")));
        MainActivity.movieLoader(ind, page);
    }

}