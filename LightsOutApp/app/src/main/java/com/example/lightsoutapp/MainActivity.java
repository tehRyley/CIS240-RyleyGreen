package com.example.lightsoutapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
    private int mLightOnColorId;
    private final String GAME_STATE = "gameState";
    private final String GAME_COLOR = "gameColor";
    private LightsOutGame mGame;
    private ColorActivity mColor;
    private GridLayout mLightGrid;
    private int mLightOnColor;
    private int mLightOffColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLightOnColorId = R.color.yellow;

        mLightGrid = findViewById(R.id.light_grid);

        // Add the same click handler to all grid buttons
        for (int buttonIndex = 0; buttonIndex < mLightGrid.getChildCount(); buttonIndex++) {
            Button gridButton = (Button) mLightGrid.getChildAt(buttonIndex);
            gridButton.setOnClickListener(this::onLightButtonClick);
        }

        mLightOnColor = ContextCompat.getColor(this, R.color.yellow);
        mLightOffColor = ContextCompat.getColor(this, R.color.black);

        mGame = new LightsOutGame();
        if (savedInstanceState == null) {
            startGame();
        } else {
            String gameState = savedInstanceState.getString(GAME_STATE);
            String gameColor = savedInstanceState.getString(GAME_COLOR);
            mGame.setState(gameState);
//            mColor.setGameColor(Integer.parseInt(gameColor));
            setButtonColors();
        }

        //Adds free win by long clicking top left square
        Button cheatButton = (Button) mLightGrid.getChildAt(0);
        cheatButton.setOnLongClickListener(view -> {
            Toast.makeText(this, R.string.congrats, Toast.LENGTH_SHORT).show();
            setButtonsOff();
            return true;
        });

    }

    public void onChangeColorClick(View view) {
        // Send the current color ID to ColorActivity
        Intent intent = new Intent(this, ColorActivity.class);
        intent.putExtra(ColorActivity.EXTRA_COLOR, mLightOnColorId);
        mColorResultLauncher.launch(intent);
    }

    ActivityResultLauncher<Intent> mColorResultLauncher = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(),
        new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        // Create the "on" button color from the chosen color ID from ColorActivity
                        mLightOnColorId = data.getIntExtra(ColorActivity.EXTRA_COLOR, R.color.yellow);
                        mLightOnColor = ContextCompat.getColor(MainActivity.this, mLightOnColorId);
                        setButtonColors();
                    }
                }
            }
        });

    public void onHelpClick(View view) {
        Intent intent = new Intent(this, HelpActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(GAME_STATE, mGame.getState());
//        outState.putInt(GAME_COLOR, mColor.setGameColor(mLightOnColorId));
    }

    private void startGame() {
        mGame.newGame();
        setButtonColors();
    }

    private void onLightButtonClick(View view) {

        // Find the button's row and col
        int buttonIndex = mLightGrid.indexOfChild(view);
        int row = buttonIndex / LightsOutGame.GRID_SIZE;
        int col = buttonIndex % LightsOutGame.GRID_SIZE;

        mGame.selectLight(row, col);
        setButtonColors();

        // Congratulate the user if the game is over
        if (mGame.isGameOver()) {
            Toast.makeText(this, R.string.congrats, Toast.LENGTH_SHORT).show();
        }
    }

    private void setButtonColors() {

        for (int buttonIndex = 0; buttonIndex < mLightGrid.getChildCount(); buttonIndex++) {
            Button gridButton = (Button) mLightGrid.getChildAt(buttonIndex);

            // Find the button's row and col
            int row = buttonIndex / LightsOutGame.GRID_SIZE;
            int col = buttonIndex % LightsOutGame.GRID_SIZE;

            if (mGame.isLightOn(row, col)) {
                gridButton.setBackgroundColor(mLightOnColor);
                gridButton.setText(R.string.on);
                gridButton.setTextColor(Color.BLACK);
            } else {
                gridButton.setBackgroundColor(mLightOffColor);
                gridButton.setText(R.string.off);
                gridButton.setTextColor(mLightOnColor);
            }
        }
    }
    private void setButtonsOff() {
        for (int buttonIndex = 0; buttonIndex < mLightGrid.getChildCount(); buttonIndex++) {
            Button gridButton = (Button) mLightGrid.getChildAt(buttonIndex);
            gridButton.setBackgroundColor(mLightOffColor);
            gridButton.setText(R.string.off);
            gridButton.setTextColor(mLightOnColor);
        }
    }

    public void onNewGameClick(View view) {
        startGame();
    }
}