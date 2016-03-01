package com.johnriggsdev.tictactoex.fragments;

import android.app.Fragment;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.johnriggsdev.tictactoex.R;
import com.johnriggsdev.tictactoex.models.Game;
import com.johnriggsdev.tictactoex.models.Player;

import static com.johnriggsdev.tictactoex.utils.Constants.*;

import java.util.ArrayList;

public class GameFragment extends Fragment {

    Player p1;
    Player p2;
    int lastSquarePlayed;
    int lastWinningPlayer;
    int lastStartingPlayer;
    boolean gameIsWon;
    boolean gameIsDraw;
    boolean lastGameWon;
    int currentPlayer;
    int draws;
    boolean isModeX;
    ArrayList<Integer> boardSquares;
    View boardColor;

    int[][] victoryRow = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};

    ImageView gameBoard;

    ImageView p1Pointer;
    ImageView p2Pointer;
    TextView p1Name;
    TextView p2Name;
    TextView p1Wins;
    TextView p2Wins;
    TextView p1Tokens;
    TextView p2Tokens;
    TextView p1Swaps;
    TextView p2Swaps;
    View p1NameUnderline;
    View p2NameUnderline;
    RelativeLayout p1SwapsParent;
    RelativeLayout p2SwapsParent;

    RelativeLayout resultParent;
    TextView result;
    TextView p1Score;
    TextView resultDraws;
    TextView p2Score;
    Button buttonRematch;
    Button buttonSelections;
    Button buttonExit;

    RelativeLayout parent0;
    RelativeLayout parent1;
    RelativeLayout parent2;
    RelativeLayout parent3;
    RelativeLayout parent4;
    RelativeLayout parent5;
    RelativeLayout parent6;
    RelativeLayout parent7;
    RelativeLayout parent8;
    ImageView player2_0;
    ImageView player2_1;
    ImageView player2_2;
    ImageView player2_3;
    ImageView player2_4;
    ImageView player2_5;
    ImageView player2_6;
    ImageView player2_7;
    ImageView player2_8;
    ImageView player1_0;
    ImageView player1_1;
    ImageView player1_2;
    ImageView player1_3;
    ImageView player1_4;
    ImageView player1_5;
    ImageView player1_6;
    ImageView player1_7;
    ImageView player1_8;
    ArrayList<RelativeLayout> parentList;
    ArrayList<ImageView> p1List;
    ArrayList<ImageView> p2List;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        parentList = new ArrayList<>();
        p1List = new ArrayList<>();
        p2List = new ArrayList<>();
        associateMembersToViews();
        populateArrayLists();
        setupSquareListeners();
        setupButtonListeners();
        initNewGame();
        initGame();
    }

    private void setupSquareListeners(){
        for (final RelativeLayout rl : parentList){
            rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    attemptToPlay(parentList.indexOf(rl));
                }
            });
        }
    }

    private void setupButtonListeners(){
        buttonRematch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearBoard();
                initGame();
            }
        });

        buttonSelections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endFragment();
//                BusDriver.getBus().post(new GameStateEvent(STATE_SETTINGS));
            }
        });

        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    private void clearBoard(){
        for (int square : boardSquares){
            boardSquares.set(boardSquares.indexOf(square), 0);
        }
    }

    private void endFragment(){
        getActivity().getFragmentManager().popBackStack();
    }

    private void initNewGame(){
        p1 = new Player();
        p1.wins = 0;
        p2 = new Player();
        p2.wins = 0;

        /**
         * Create an ArrayList to represent the squares on the tic tac toe board.
         * 0 = empty square
         * 1 = player1
         * 2 = player2
         */
        boardSquares = new ArrayList<>();
        boardSquares.add(0);
        boardSquares.add(0);
        boardSquares.add(0);
        boardSquares.add(0);
        boardSquares.add(0);
        boardSquares.add(0);
        boardSquares.add(0);
        boardSquares.add(0);
        boardSquares.add(0);
        draws = 0;
        isModeX = Game.getGame().getGameMode().equals(GAME_X);
        currentPlayer = 0; //Used to determine starting player at game start
        lastGameWon = false;
        lastWinningPlayer = 0; //Used to determine starting player for subsequent games
        lastStartingPlayer = 0; //Also used to determine starting player for subsequent games
    }

    private void initGame(){
        gameIsWon = false;
        gameIsDraw = false;
        resultParent.setVisibility(View.GONE);
        resultParent.setAlpha(0f);
        lastSquarePlayed = -1; //Out of bounds for boardSquares to allow any square to be played to start the game.
        setupPlayersTokensAndSwaps();
        setBoardColor();
        setPlayerNamesAndColors();
        setPointer();
        setupSwapViews();
        initializeTokens();
        displayCounters();
    }

    private void setupPlayersTokensAndSwaps(){
        determineStartingPlayer();

        if (isModeX){
            switch (currentPlayer){
                case (1):
                    p1.tokensRemaining = 5;
                    p1.swapsRemaining = getSecondarySwaps();
                    p2.tokensRemaining = 4;
                    p2.swapsRemaining = getPrimarySwaps();
                    break;
                case (2):
                    p2.tokensRemaining = 5;
                    p2.swapsRemaining = getSecondarySwaps();
                    p1.tokensRemaining = 4;
                    p1.swapsRemaining = getPrimarySwaps();
            }
        } else {
            switch (currentPlayer){
                case (1):
                    p1.tokensRemaining = 5;
                    p1.swapsRemaining = 0;
                    p2.tokensRemaining = 4;
                    p2.swapsRemaining = 0;
                    break;
                case (2):
                    p2.tokensRemaining = 5;
                    p2.swapsRemaining = 0;
                    p1.tokensRemaining = 4;
                    p1.swapsRemaining = 0;
            }
        }
    }

    private void setPointer(){
        switch (currentPlayer){
            case (1):
                p1Pointer.animate().alpha(1f).setDuration(1000);
                p2Pointer.animate().alpha(0f).setDuration(1000);
                break;
            case (2):
                p1Pointer.animate().alpha(0f).setDuration(1000);
                p2Pointer.animate().alpha(1f).setDuration(1000);
                break;
        }
    }

    private void determineStartingPlayer(){
        if (currentPlayer == 0){
            currentPlayer = 1;
        } else if (lastGameWon){
            switch (lastWinningPlayer){
                case (1):
                    currentPlayer = 2;
                    lastStartingPlayer = 2;
                    break;
                case (2):
                    currentPlayer = 1;
                    lastStartingPlayer = 1;
                    break;
                default:
                    currentPlayer = 1;
                    lastStartingPlayer = 1;
                    break;
            }
        } else {
            switch (lastStartingPlayer){
                case (1):
                    currentPlayer = 2;
                    lastStartingPlayer = 2;
                    break;
                case (2):
                    currentPlayer = 1;
                    lastStartingPlayer = 1;
                    break;
                default:
                    currentPlayer = 1;
                    lastStartingPlayer = 1;
                    break;
            }
        }
    }

    private int getPrimarySwaps(){
        switch (Game.getGame().getTotalSwaps()){
            case (5):
                return 3;
            case (9):
                return 5;
            case (13):
                return 7;
            default:
                //something went wrong if we are here;
                return 0;
        }
    }

    private int getSecondarySwaps(){
        switch (Game.getGame().getTotalSwaps()){
            case (5):
                return 2;
            case (9):
                return 4;
            case (13):
                return 6;
            default:
                //something went wrong if we are here;
                return 0;
        }
    }

    //TODO refactor deprecated code to handle appropriate API levels
    private void setBoardColor() {
        switch (Game.getGame().getBoardColor()) {
            case (0):
                boardColor.setBackgroundColor(getResources().getColor(R.color.green));
                break;
            case (1):
                boardColor.setBackgroundColor(getResources().getColor(R.color.blue));
                break;
            case (2):
                boardColor.setBackgroundColor(getResources().getColor(R.color.purple));
                break;
            case (3):
                boardColor.setBackgroundColor(getResources().getColor(R.color.red));
                break;
            case (4):
                boardColor.setBackgroundColor(getResources().getColor(R.color.orange));
                break;
            case (5):
                boardColor.setBackgroundColor(getResources().getColor(R.color.yellow));
                break;
            case (6):
                boardColor.setBackgroundColor(getResources().getColor(R.color.white));
                break;
        }

        boardColor.setAlpha(.7f);
    }

    private void setPlayerNamesAndColors() {
        p1Name.setText(getActivity().getResources().getString(R.string.player_1));
        p2Name.setText(getActivity().getResources().getString(R.string.player_2));

        switch (Game.getGame().getP1Color()) {
            case (0):
                p1Name.setTextColor(getActivity().getResources().getColor(R.color.green));
                p1NameUnderline.setBackgroundColor(getActivity().getResources().getColor(R.color.green));
                p1Pointer.setColorFilter(getActivity().getResources().getColor(R.color.green), PorterDuff.Mode.MULTIPLY);
                break;
            case (1):
                p1Name.setTextColor(getActivity().getResources().getColor(R.color.blue));
                p1NameUnderline.setBackgroundColor(getActivity().getResources().getColor(R.color.blue));
                p1Pointer.setColorFilter(getActivity().getResources().getColor(R.color.blue), PorterDuff.Mode.MULTIPLY);
                break;
            case (2):
                p1Name.setTextColor(getActivity().getResources().getColor(R.color.purple));
                p1NameUnderline.setBackgroundColor(getActivity().getResources().getColor(R.color.purple));
                p1Pointer.setColorFilter(getActivity().getResources().getColor(R.color.purple), PorterDuff.Mode.MULTIPLY);
                break;
            case (3):
                p1Name.setTextColor(getActivity().getResources().getColor(R.color.red));
                p1NameUnderline.setBackgroundColor(getActivity().getResources().getColor(R.color.red));
                p1Pointer.setColorFilter(getActivity().getResources().getColor(R.color.red), PorterDuff.Mode.MULTIPLY);
                break;
            case (4):
                p1Name.setTextColor(getActivity().getResources().getColor(R.color.orange));
                p1NameUnderline.setBackgroundColor(getActivity().getResources().getColor(R.color.orange));
                p1Pointer.setColorFilter(getActivity().getResources().getColor(R.color.orange), PorterDuff.Mode.MULTIPLY);
                break;
            case (5):
                p1Name.setTextColor(getActivity().getResources().getColor(R.color.yellow));
                p1NameUnderline.setBackgroundColor(getActivity().getResources().getColor(R.color.yellow));
                p1Pointer.setColorFilter(getActivity().getResources().getColor(R.color.yellow), PorterDuff.Mode.MULTIPLY);
                break;
            case (6):
                p1Name.setTextColor(getActivity().getResources().getColor(R.color.grey_dark));
                p1NameUnderline.setBackgroundColor(getActivity().getResources().getColor(R.color.grey_dark));
                p1Pointer.setColorFilter(getActivity().getResources().getColor(R.color.white), PorterDuff.Mode.MULTIPLY);
                break;
        }

        switch (Game.getGame().getP2Color()) {
            case (0):
                p2Name.setTextColor(getActivity().getResources().getColor(R.color.green));
                p2NameUnderline.setBackgroundColor(getActivity().getResources().getColor(R.color.green));
                p2Pointer.setColorFilter(getActivity().getResources().getColor(R.color.green), PorterDuff.Mode.MULTIPLY);
                break;
            case (1):
                p2Name.setTextColor(getActivity().getResources().getColor(R.color.blue));
                p2NameUnderline.setBackgroundColor(getActivity().getResources().getColor(R.color.blue));
                p2Pointer.setColorFilter(getActivity().getResources().getColor(R.color.blue), PorterDuff.Mode.MULTIPLY);
                break;
            case (2):
                p2Name.setTextColor(getActivity().getResources().getColor(R.color.purple));
                p2NameUnderline.setBackgroundColor(getActivity().getResources().getColor(R.color.purple));
                p2Pointer.setColorFilter(getActivity().getResources().getColor(R.color.purple), PorterDuff.Mode.MULTIPLY);
                break;
            case (3):
                p2Name.setTextColor(getActivity().getResources().getColor(R.color.red));
                p2NameUnderline.setBackgroundColor(getActivity().getResources().getColor(R.color.red));
                p2Pointer.setColorFilter(getActivity().getResources().getColor(R.color.red), PorterDuff.Mode.MULTIPLY);
                break;
            case (4):
                p2Name.setTextColor(getActivity().getResources().getColor(R.color.orange));
                p2NameUnderline.setBackgroundColor(getActivity().getResources().getColor(R.color.orange));
                p2Pointer.setColorFilter(getActivity().getResources().getColor(R.color.orange), PorterDuff.Mode.MULTIPLY);
                break;
            case (5):
                p2Name.setTextColor(getActivity().getResources().getColor(R.color.yellow));
                p2NameUnderline.setBackgroundColor(getActivity().getResources().getColor(R.color.yellow));
                p2Pointer.setColorFilter(getActivity().getResources().getColor(R.color.yellow), PorterDuff.Mode.MULTIPLY);
                break;
            case (6):
                p2Name.setTextColor(getActivity().getResources().getColor(R.color.grey_dark));
                p2NameUnderline.setBackgroundColor(getActivity().getResources().getColor(R.color.grey_dark));
                p2Pointer.setColorFilter(getActivity().getResources().getColor(R.color.white), PorterDuff.Mode.MULTIPLY);
                break;
        }

    }

    private void setImageViewColor(ImageView image, int color) {
        switch (color) {
            case (0):
                image.setColorFilter(getResources().getColor(R.color.green), PorterDuff.Mode.MULTIPLY);
                break;
            case (1):
                image.setColorFilter(getResources().getColor(R.color.blue), PorterDuff.Mode.MULTIPLY);
                break;
            case (2):
                image.setColorFilter(getResources().getColor(R.color.purple), PorterDuff.Mode.MULTIPLY);
                break;
            case (3):
                image.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.MULTIPLY);
                break;
            case (4):
                image.setColorFilter(getResources().getColor(R.color.orange), PorterDuff.Mode.MULTIPLY);
                break;
            case (5):
                image.setColorFilter(getResources().getColor(R.color.yellow), PorterDuff.Mode.MULTIPLY);
                break;
            case (6):
                image.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.MULTIPLY);
                break;
        }

    }

    private void initializeTokens(){
        setTokensColor(p1List, Game.getGame().getP1Color());
        setTokensColor(p2List, Game.getGame().getP2Color());
        setTokensAlpha(p1List, 0f);
        setTokensAlpha(p2List, 0f);
    }

    private void setTokensColor(ArrayList<ImageView> views, int color){
        for (ImageView view : views){
            setImageViewColor(view, color);
        }
    }

    private void setTokensAlpha(ArrayList<ImageView> views, float alpha){
        for (ImageView view : views){
            view.animate().alpha(alpha).setDuration(1000);
        }
    }

    private void setupSwapViews(){
        if (!isModeX){
            p1SwapsParent.setVisibility(View.GONE);
            p2SwapsParent.setVisibility(View.GONE);
        } else {
            p1SwapsParent.setVisibility(View.VISIBLE);
            p2SwapsParent.setVisibility(View.VISIBLE);
        }
    }

    private void displayCounters(){
        p1Wins.setText(String.valueOf(p1.wins));
        p1Tokens.setText(String.valueOf(p1.tokensRemaining));
        p1Swaps.setText(String.valueOf(p1.swapsRemaining));
        p2Wins.setText(String.valueOf(p2.wins));
        p2Tokens.setText(String.valueOf(p2.tokensRemaining));
        p2Swaps.setText(String.valueOf(p2.swapsRemaining));
    }

    /**
     * This is the main game logic handled within the GameFragment class. attemptToPlay(int square)
     * is called by the associated onClickListerner for each gameboard square.
     */

    private void attemptToPlay(int square){
        if (!gameIsWon && !gameIsDraw) {
            if (boardSquares.get(square) == 0) {
                playTokenAtSquare(square);
            } else if (!isModeX && boardSquares.get(square) != 0) {
                Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.cannot_play_occupied), Toast.LENGTH_SHORT).show();
            } else if (boardSquares.get(square) == currentPlayer) {
                Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.cannot_play_own_square), Toast.LENGTH_SHORT).show();
            } else if (isModeX && square == lastSquarePlayed) {
                Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.cannot_play_last_square), Toast.LENGTH_LONG).show();
            } else if (isModeX) {
                playSwapAtSquare(square);
            }
        }
    }

    private void playTokenAtSquare(int square){

        switch (currentPlayer){
            case (1):
                if (p1.tokensRemaining > 0){
                    boardSquares.set(square, currentPlayer);
                    p1.tokensRemaining--;
                    p1List.get(square).animate().alpha(1f).setDuration(1000);
                } else {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.cannot_play_token), Toast.LENGTH_LONG).show();
                    return;
                }
                break;
            case (2):
                if (p2.tokensRemaining > 0){
                    boardSquares.set(square, currentPlayer);
                    p2.tokensRemaining--;
                    p2List.get(square).animate().alpha(1f).setDuration(1000);
                } else {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.cannot_play_token), Toast.LENGTH_LONG).show();
                    return;
                }
                break;
        }

        lastSquarePlayed = square;
        finishTurn();
    }

    private void playSwapAtSquare(int square){

        switch (currentPlayer){
            case (1):
                if (p1.swapsRemaining > 0){
                    boardSquares.set(square, currentPlayer);
                    p1.swapsRemaining--;
                    p1List.get(square).animate().alpha(1f).setDuration(1000);
                    p2List.get(square).animate().alpha(0f).setDuration(1000);
                } else {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.cannot_play_swap), Toast.LENGTH_LONG).show();
                    return;
                }
                break;
            case (2):
                if (p2.swapsRemaining > 0){
                    boardSquares.set(square, currentPlayer);
                    p2.swapsRemaining--;
                    p1List.get(square).animate().alpha(0f).setDuration(1000);
                    p2List.get(square).animate().alpha(1f).setDuration(1000);
                } else {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.cannot_play_swap), Toast.LENGTH_LONG).show();
                    return;
                }
                break;
        }

        lastSquarePlayed = square;
        finishTurn();
    }

    private void finishTurn(){
        displayCounters();

        gameIsWon = checkForVictory();

        if (!gameIsWon) {
            gameIsDraw = checkForDraw();
        }

        if (gameIsWon){
            gameWon();
        } else if (gameIsDraw){
            gameDraw();
        } else {
            changePlayers();
        }
    }

    private void changePlayers(){
        switch (currentPlayer){
            case (1):
                currentPlayer = 2;
                break;
            case (2):
                currentPlayer = 1;
                break;
        }

        setPointer();
    }

    private boolean checkForVictory(){
        for (int[] row : victoryRow){
            if (boardSquares.get(row[0]) == currentPlayer && boardSquares.get(row[1]) == currentPlayer && boardSquares.get(row[2] )== currentPlayer){
                return true;
            }
        }
        return false;
    }

    private boolean checkForDraw(){
        if (p1.swapsRemaining == 0 && p1.tokensRemaining == 0 && p2.swapsRemaining == 0 && p2.tokensRemaining == 0){
            return true;
        }

        return false;
    }

    private void gameWon(){
        lastGameWon = true;
        switch (currentPlayer){
            case (1):
                result.setText(getActivity().getResources().getString(R.string.player_1) + " " + getActivity().getResources().getString(R.string.results_wins));
                p1.wins++;
                lastWinningPlayer = 1;
                break;
            case (2):
                result.setText(getActivity().getResources().getString(R.string.player_2) + " " + getActivity().getResources().getString(R.string.results_wins));
                p2.wins++;
                lastWinningPlayer = 2;
                break;
        }

        p1Score.setText(String.valueOf(p1.wins));
        p2Score.setText(String.valueOf(p2.wins));
        resultDraws.setText(String.valueOf(draws));
        resultParent.setVisibility(View.VISIBLE);
        resultParent.animate().alpha(1f).setDuration(1000);
    }

    private void gameDraw(){
        result.setText(getActivity().getResources().getString(R.string.result_draw));
        lastGameWon = false;
        draws++;

        p1Score.setText(String.valueOf(p1.wins));
        p2Score.setText(String.valueOf(p2.wins));
        resultDraws.setText(String.valueOf(draws));
        resultParent.setVisibility(View.VISIBLE);
        resultParent.animate().alpha(1f).setDuration(1000);
    }

    private void associateMembersToViews() {
        boardColor = getActivity().findViewById(R.id.board_parent);
        gameBoard = (ImageView) getActivity().findViewById(R.id.gameboard);
        p1Pointer = (ImageView) getActivity().findViewById(R.id.player1_board).findViewById(R.id.pointer_image);
        p2Pointer = (ImageView) getActivity().findViewById(R.id.player2_board).findViewById(R.id.pointer_image);
        p1Name = (TextView) getActivity().findViewById(R.id.player1_board).findViewById(R.id.name);
        p2Name = (TextView) getActivity().findViewById(R.id.player2_board).findViewById(R.id.name);
        p1Wins = (TextView) getActivity().findViewById(R.id.player1_board).findViewById(R.id.wins_qty);
        p2Wins = (TextView) getActivity().findViewById(R.id.player2_board).findViewById(R.id.wins_qty);
        p1Tokens = (TextView) getActivity().findViewById(R.id.player1_board).findViewById(R.id.tokens_qty);
        p2Tokens = (TextView) getActivity().findViewById(R.id.player2_board).findViewById(R.id.tokens_qty);
        p1Swaps = (TextView) getActivity().findViewById(R.id.player1_board).findViewById(R.id.swaps_qty);
        p2Swaps = (TextView) getActivity().findViewById(R.id.player2_board).findViewById(R.id.swaps_qty);
        p1NameUnderline = getActivity().findViewById(R.id.player1_board).findViewById(R.id.underline);
        p2NameUnderline = getActivity().findViewById(R.id.player2_board).findViewById(R.id.underline);
        p1SwapsParent = (RelativeLayout) getActivity().findViewById(R.id.player1_board).findViewById(R.id.swaps_parent);
        p2SwapsParent = (RelativeLayout) getActivity().findViewById(R.id.player2_board).findViewById(R.id.swaps_parent);
        resultParent = (RelativeLayout) getActivity().findViewById(R.id.game_result_parent);
        result = (TextView) getActivity().findViewById(R.id.game_result);
        p1Score = (TextView) getActivity().findViewById(R.id.p1_score);
        resultDraws = (TextView) getActivity().findViewById(R.id.draws_score);
        p2Score = (TextView) getActivity().findViewById(R.id.p2_score);
        buttonRematch = (Button) getActivity().findViewById(R.id.rematch);
        buttonSelections = (Button) getActivity().findViewById(R.id.selections);
        buttonExit = (Button) getActivity().findViewById(R.id.exit);
        parent0 = (RelativeLayout) getActivity().findViewById(R.id.parent_0);
        parent1 = (RelativeLayout) getActivity().findViewById(R.id.parent_1);
        parent2 = (RelativeLayout) getActivity().findViewById(R.id.parent_2);
        parent3 = (RelativeLayout) getActivity().findViewById(R.id.parent_3);
        parent4 = (RelativeLayout) getActivity().findViewById(R.id.parent_4);
        parent5 = (RelativeLayout) getActivity().findViewById(R.id.parent_5);
        parent6 = (RelativeLayout) getActivity().findViewById(R.id.parent_6);
        parent7 = (RelativeLayout) getActivity().findViewById(R.id.parent_7);
        parent8 = (RelativeLayout) getActivity().findViewById(R.id.parent_8);
        player2_0 = (ImageView) getActivity().findViewById(R.id.player2_0);
        player2_1 = (ImageView) getActivity().findViewById(R.id.player2_1);
        player2_2 = (ImageView) getActivity().findViewById(R.id.player2_2);
        player2_3 = (ImageView) getActivity().findViewById(R.id.player2_3);
        player2_4 = (ImageView) getActivity().findViewById(R.id.player2_4);
        player2_5 = (ImageView) getActivity().findViewById(R.id.player2_5);
        player2_6 = (ImageView) getActivity().findViewById(R.id.player2_6);
        player2_7 = (ImageView) getActivity().findViewById(R.id.player2_7);
        player2_8 = (ImageView) getActivity().findViewById(R.id.player2_8);
        player1_0 = (ImageView) getActivity().findViewById(R.id.player1_0);
        player1_1 = (ImageView) getActivity().findViewById(R.id.player1_1);
        player1_2 = (ImageView) getActivity().findViewById(R.id.player1_2);
        player1_3 = (ImageView) getActivity().findViewById(R.id.player1_3);
        player1_4 = (ImageView) getActivity().findViewById(R.id.player1_4);
        player1_5 = (ImageView) getActivity().findViewById(R.id.player1_5);
        player1_6 = (ImageView) getActivity().findViewById(R.id.player1_6);
        player1_7 = (ImageView) getActivity().findViewById(R.id.player1_7);
        player1_8 = (ImageView) getActivity().findViewById(R.id.player1_8);
    }

    private void populateArrayLists(){
        parentList.add(parent0);
        parentList.add(parent1);
        parentList.add(parent2);
        parentList.add(parent3);
        parentList.add(parent4);
        parentList.add(parent5);
        parentList.add(parent6);
        parentList.add(parent7);
        parentList.add(parent8);
        p2List.add(player2_0);
        p2List.add(player2_1);
        p2List.add(player2_2);
        p2List.add(player2_3);
        p2List.add(player2_4);
        p2List.add(player2_5);
        p2List.add(player2_6);
        p2List.add(player2_7);
        p2List.add(player2_8);
        p1List.add(player1_0);
        p1List.add(player1_1);
        p1List.add(player1_2);
        p1List.add(player1_3);
        p1List.add(player1_4);
        p1List.add(player1_5);
        p1List.add(player1_6);
        p1List.add(player1_7);
        p1List.add(player1_8);
    }
}
