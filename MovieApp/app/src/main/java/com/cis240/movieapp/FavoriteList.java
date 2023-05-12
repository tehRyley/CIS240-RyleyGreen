package com.cis240.movieapp;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class FavoriteList extends AppCompatActivity {
    static public List<String> favorites = new ArrayList<>();
    static File favFile = new File( "favorites.txt");
    public static void writeToFile(Context context) throws IOException {
        FileOutputStream outputStream = context.openFileOutput(favFile.getName(), Context.MODE_PRIVATE);
        PrintWriter writer = new PrintWriter(outputStream);
        for (String item : favorites) {
            writer.println(item);
        }
        writer.close();
    }

    public static void readFromFile(Context context) throws IOException {
        FileInputStream fis = context.openFileInput(favFile.getName());
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(fis))) {
            favorites.clear();
            String line;
            while ((line = reader.readLine()) != null) {
                favorites.add(line);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
