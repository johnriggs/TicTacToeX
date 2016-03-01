package com.johnriggsdev.tictactoex.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.crashlytics.android.Crashlytics;
import com.johnriggsdev.tictactoex.R;
import com.johnriggsdev.tictactoex.models.Game;

import io.fabric.sdk.android.Fabric;

import static com.johnriggsdev.tictactoex.utils.Constants.*;

public class MainActivity extends Activity{
    Button tttClassic;
    Button tttX;

    Intent intent;

    boolean hasBeenAnimated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);

        tttClassic = (Button) findViewById(R.id.ttt_classic);
        tttX = (Button) findViewById(R.id.ttt_x);
        initButtons();

        hasBeenAnimated = false;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!hasBeenAnimated) {
            animateButtons();
        }
        Game.getGame().setGameState(STATE_SETTINGS);

    }

    private void initButtons(){

        tttClassic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hasBeenAnimated = false;
                Game.getGame().setGameMode(GAME_CLASSIC);
                intent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });

        tttX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hasBeenAnimated = false;
                Game.getGame().setGameMode(GAME_X);
                intent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });


    }

    private void animateButtons(){
        tttClassic.setTranslationX(-1000f);
        tttX.setTranslationX(1000f);
        tttClassic.animate().translationX(0f).setDuration(1500);
        tttX.animate().translationX(0f).setDuration(1500);
        hasBeenAnimated = true;
    }
}
