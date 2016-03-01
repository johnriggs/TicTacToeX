package com.johnriggsdev.tictactoex.events;

import com.johnriggsdev.tictactoex.models.Game;

/**
 * Created by johnriggs on 2/28/16.
 */
public class GameStateEvent {

    public GameStateEvent(String state){
        Game.getGame().setGameState(state);
    }
}
