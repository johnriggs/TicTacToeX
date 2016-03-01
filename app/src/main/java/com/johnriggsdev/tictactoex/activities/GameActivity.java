package com.johnriggsdev.tictactoex.activities;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.johnriggsdev.tictactoex.R;
import com.johnriggsdev.tictactoex.events.GameStateEvent;
import com.johnriggsdev.tictactoex.fragments.GameFragment;
import com.johnriggsdev.tictactoex.fragments.SelectionFragment;
import com.johnriggsdev.tictactoex.models.Game;

import static com.johnriggsdev.tictactoex.utils.Constants.*;

import com.johnriggsdev.tictactoex.utils.BusDriver;
import com.squareup.otto.Subscribe;


public class GameActivity extends Activity {

    SelectionFragment selectFrag;
    GameFragment gameFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        BusDriver.getBus().register(this);

        selectFrag = new SelectionFragment();
        gameFrag = new GameFragment();

        setFragment();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        BusDriver.getBus().unregister(this);
    }

    private void setFragment(){
        if (Game.getGame().getGameState().equals(STATE_SETTINGS)){
            setSelectionFragment();
        } else if (Game.getGame().getGameState().equals(STATE_PLAY)){
            setGameFragment();
        }
    }

    public void setSelectionFragment() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.game_frame, selectFrag);
        ft.commit();
    }

    public void setGameFragment() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.addToBackStack("Selection Fragment");
        ft.replace(R.id.game_frame, gameFrag);
        ft.commit();
    }


    @Subscribe public void changeGameState(GameStateEvent event){
        setFragment();
    }
}
