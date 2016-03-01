package com.johnriggsdev.tictactoex.models;

import static com.johnriggsdev.tictactoex.utils.Constants.*;

public class Game {
    private static Game game;

    private int boardColor;
    private int p1Color;
    private int p2Color;
    private int totalSwaps;

    private String gameMode;
    private String gameState;

    private Game(){
        game = this;
        initMembers();
    }

    public static Game getGame(){
        if (game == null){
            game = new Game();
        }

        return game;
    }

    private void initMembers(){
        boardColor = BLUE;
        p1Color = WHITE;
        p2Color = WHITE;
        totalSwaps = 9;

        gameMode = GAME_CLASSIC;
        gameState = STATE_SETTINGS;
    }

    public void setBoardColor(int color){
        boardColor = color;
    }

    public int getBoardColor(){
        return boardColor;
    }

    public void setP1Color(int color){
        p1Color = color;
    }

    public int getP1Color(){
        return p1Color;
    }

    public void setP2Color(int color){
       p2Color = color;
    }

    public int getP2Color(){
        return p2Color;
    }

    public void setTotalSwaps(int qty){
        totalSwaps = qty;
    }

    public int getTotalSwaps(){
        return totalSwaps;
    }

    public void setGameMode(String mode){
        gameMode = mode;
    }

    public String getGameMode(){
        return gameMode;
    }

    public void setGameState(String state){
        gameState = state;
    }

    public String getGameState(){
        return gameState;
    }
}
