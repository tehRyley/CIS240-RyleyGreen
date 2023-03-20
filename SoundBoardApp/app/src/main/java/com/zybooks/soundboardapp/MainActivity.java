package com.zybooks.soundboardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ImageView disJoin, disNotif, ding, disLeve, pew, reh, sponge, vio;
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        disJoin = findViewById(R.id.btnJoin);
        disJoin.setOnClickListener(play);
        disNotif = findViewById(R.id.notif);
        disNotif.setOnClickListener(play);
        ding = findViewById(R.id.ding);
        ding.setOnClickListener(play);
        disLeve = findViewById(R.id.leave);
        disLeve.setOnClickListener(play);
        pew = findViewById(R.id.pew);
        pew.setOnClickListener(play);
        reh = findViewById(R.id.reh);
        reh.setOnClickListener(play);
        sponge = findViewById(R.id.sponge);
        sponge.setOnClickListener(play);
        vio = findViewById(R.id.vio);
        vio.setOnClickListener(play);

    }
    View.OnClickListener play = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnJoin:
                    mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.discordsounds);
                    mediaPlayer.start();
                    break;
                case R.id.notif:
                    mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.discordnotification);
                    mediaPlayer.start();
                    break;
                case R.id.ding:
                    mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.ding);
                    mediaPlayer.start();
                    break;
                case R.id.leave:
                    mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.discordleave);
                    mediaPlayer.start();
                    break;
                case R.id.pew:
                    mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.pew);
                    mediaPlayer.start();
                    break;
                case R.id.reh:
                    mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.reh);
                    mediaPlayer.start();
                    break;
                case R.id.sponge:
                    mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.spongebob);
                    mediaPlayer.start();
                    break;
                case R.id.vio:
                    mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.vio);
                    mediaPlayer.start();
                    break;
                }
            }
        };
    }