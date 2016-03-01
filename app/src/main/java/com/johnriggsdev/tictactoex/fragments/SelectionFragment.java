package com.johnriggsdev.tictactoex.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.johnriggsdev.tictactoex.R;
import com.johnriggsdev.tictactoex.events.GameStateEvent;
import com.johnriggsdev.tictactoex.models.Game;
import com.johnriggsdev.tictactoex.utils.BusDriver;

import java.util.ArrayList;

import static com.johnriggsdev.tictactoex.utils.Constants.*;

/**
 * Created by johnriggs on 2/27/16.
 */
public class SelectionFragment extends Fragment {
    String gameMode;

    TextView gameboardTV;
    TextView player1TV;
    TextView player2TV;
    TextView swaps;
    RadioGroup swapsParent;
    RadioButton swaps5;
    RadioButton swaps9;
    RadioButton swaps13;

    ArrayList<View> boardViews;
    ArrayList<View> player1Views;
    ArrayList<View> player2Views;
    ArrayList<TextView> boardChecks;
    ArrayList<TextView> player1Checks;
    ArrayList<TextView> player2Checks;
    ArrayList<ArrayList<View>> views;
    View boardGreen;
    View boardBlue;
    View boardPurple;
    View boardRed;
    View boardOrange;
    View boardYellow;
    View boardWhite;
    View p1Green;
    View p1Blue;
    View p1Purple;
    View p1Red;
    View p1Orange;
    View p1Yellow;
    View p1White;
    View p2Green;
    View p2Blue;
    View p2Purple;
    View p2Red;
    View p2Orange;
    View p2Yellow;
    View p2White;
    TextView boardGreenCheck;
    TextView boardBlueCheck;
    TextView boardPurpleCheck;
    TextView boardRedCheck;
    TextView boardOrangeCheck;
    TextView boardYellowCheck;
    TextView boardWhiteCheck;
    TextView p1GreenCheck;
    TextView p1BlueCheck;
    TextView p1PurpleCheck;
    TextView p1RedCheck;
    TextView p1OrangeCheck;
    TextView p1YellowCheck;
    TextView p1WhiteCheck;
    TextView p2GreenCheck;
    TextView p2BlueCheck;
    TextView p2PurpleCheck;
    TextView p2RedCheck;
    TextView p2OrangeCheck;
    TextView p2YellowCheck;
    TextView p2WhiteCheck;

    Button play;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_selection, container, false);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        boardViews = new ArrayList<>();
        player1Views = new ArrayList<>();
        player2Views = new ArrayList<>();
        boardChecks = new ArrayList<>();
        player1Checks = new ArrayList<>();
        player2Checks = new ArrayList<>();
        views = new ArrayList<>();

        assignMembersToViews();
        populateArrayLists();
        setupListeners();
    }

    @Override
    public void onResume() {
        super.onResume();

        //Set GameState to match current fragment in case of returning via back button
        Game.getGame().setGameState(STATE_SETTINGS);

        printLabels();
        setSwapsWithGameMode();
        setBoardChecks();
        setPlayer1Checks();
        setPlayer2Checks();
        setSwaps();
    }

    private void setupListeners(){
        for (final ArrayList<View> view : views){
            for (final View v : view){
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (views.indexOf(view)) {
                            case (0):
                                Game.getGame().setBoardColor(view.indexOf(v));
                                setBoardChecks();
                                break;
                            case (1):
                                Game.getGame().setP1Color(view.indexOf(v));
                                setPlayer1Checks();
                                break;
                            case (2):
                                Game.getGame().setP2Color(view.indexOf(v));
                                setPlayer2Checks();
                                break;
                        }
                    }
                });
            }
        }

        swaps5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Game.getGame().setTotalSwaps(5);
                }
            }
        });

        swaps9.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Game.getGame().setTotalSwaps(9);
                }
            }
        });

        swaps13.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Game.getGame().setTotalSwaps(13);
                }
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BusDriver.getBus().post(new GameStateEvent(STATE_PLAY));
            }
        });
    }

    private void printLabels(){
        gameboardTV.setText("Board");
        player1TV.setText("Player 1");
        player2TV.setText("Player 2");
    }

    private void setSwapsWithGameMode(){
        if (Game.getGame().getGameMode().equals(GAME_CLASSIC)){
            swaps.setVisibility(View.GONE);
            swapsParent.setVisibility(View.GONE);
        } else {
            swaps.setVisibility(View.VISIBLE);
            swapsParent.setVisibility(View.VISIBLE);
        }
    }

    private void setBoardChecks(){
        for (TextView tv : boardChecks){
            tv.setVisibility(View.GONE);
        }

        boardChecks.get(Game.getGame().getBoardColor()).setVisibility(View.VISIBLE);
    }

    private void setPlayer1Checks(){
        for (TextView tv : player1Checks){
            tv.setVisibility(View.GONE);
        }

        player1Checks.get(Game.getGame().getP1Color()).setVisibility(View.VISIBLE);
    }

    private void setPlayer2Checks(){
        for (TextView tv : player2Checks){
            tv.setVisibility(View.GONE);
        }

        player2Checks.get(Game.getGame().getP2Color()).setVisibility(View.VISIBLE);
    }

    private void setSwaps(){
        switch (Game.getGame().getTotalSwaps()){
            case (5):
                swaps5.setChecked(true);
                swaps9.setChecked(false);
                swaps13.setChecked(false);
                break;
            case (9):
                swaps5.setChecked(false);
                swaps9.setChecked(true);
                swaps13.setChecked(false);
                break;
            case (13):
                swaps5.setChecked(false);
                swaps9.setChecked(false);
                swaps13.setChecked(true);
                break;
        }
    }

    private void assignMembersToViews(){
        gameboardTV = (TextView) getActivity().findViewById(R.id.gameboard).findViewById(R.id.title);
        player1TV = (TextView) getActivity().findViewById(R.id.player1).findViewById(R.id.title);
        player2TV = (TextView) getActivity().findViewById(R.id.player2).findViewById(R.id.title);
        swaps = (TextView) getActivity().findViewById(R.id.swaps);

        swapsParent = (RadioGroup) getActivity().findViewById(R.id.swaps_qty_parent);
        swaps5 = (RadioButton) getActivity().findViewById(R.id.swaps_5);
        swaps9 = (RadioButton) getActivity().findViewById(R.id.swaps_9);
        swaps13 = (RadioButton) getActivity().findViewById(R.id.swaps_13);
        play = (Button) getActivity().findViewById(R.id.start_game);

        boardGreen = getActivity().findViewById(R.id.gameboard).findViewById(R.id.green);
        boardBlue  = getActivity().findViewById(R.id.gameboard).findViewById(R.id.blue);
        boardPurple = getActivity().findViewById(R.id.gameboard).findViewById(R.id.purple);
        boardRed = getActivity().findViewById(R.id.gameboard).findViewById(R.id.red);
        boardOrange = getActivity().findViewById(R.id.gameboard).findViewById(R.id.orange);
        boardYellow = getActivity().findViewById(R.id.gameboard).findViewById(R.id.yellow);
        boardWhite = getActivity().findViewById(R.id.gameboard).findViewById(R.id.white);
        p1Green = getActivity().findViewById(R.id.player1).findViewById(R.id.green);
        p1Blue = getActivity().findViewById(R.id.player1).findViewById(R.id.blue);
        p1Purple = getActivity().findViewById(R.id.player1).findViewById(R.id.purple);
        p1Red = getActivity().findViewById(R.id.player1).findViewById(R.id.red);
        p1Orange = getActivity().findViewById(R.id.player1).findViewById(R.id.orange);
        p1Yellow = getActivity().findViewById(R.id.player1).findViewById(R.id.yellow);
        p1White = getActivity().findViewById(R.id.player1).findViewById(R.id.white);
        p2Green = getActivity().findViewById(R.id.player2).findViewById(R.id.green);
        p2Blue = getActivity().findViewById(R.id.player2).findViewById(R.id.blue);
        p2Purple = getActivity().findViewById(R.id.player2).findViewById(R.id.purple);
        p2Red = getActivity().findViewById(R.id.player2).findViewById(R.id.red);
        p2Orange = getActivity().findViewById(R.id.player2).findViewById(R.id.orange);
        p2Yellow = getActivity().findViewById(R.id.player2).findViewById(R.id.yellow);
        p2White = getActivity().findViewById(R.id.player2).findViewById(R.id.white);
        boardGreenCheck = (TextView) getActivity().findViewById(R.id.gameboard).findViewById(R.id.green_check);
        boardBlueCheck = (TextView) getActivity().findViewById(R.id.gameboard).findViewById(R.id.blue_check);
        boardPurpleCheck = (TextView) getActivity().findViewById(R.id.gameboard).findViewById(R.id.purple_check);
        boardRedCheck = (TextView) getActivity().findViewById(R.id.gameboard).findViewById(R.id.red_check);
        boardOrangeCheck = (TextView) getActivity().findViewById(R.id.gameboard).findViewById(R.id.orange_check);
        boardYellowCheck = (TextView) getActivity().findViewById(R.id.gameboard).findViewById(R.id.yellow_check);
        boardWhiteCheck = (TextView) getActivity().findViewById(R.id.gameboard).findViewById(R.id.white_check);
        p1GreenCheck = (TextView) getActivity().findViewById(R.id.player1).findViewById(R.id.green_check);
        p1BlueCheck = (TextView) getActivity().findViewById(R.id.player1).findViewById(R.id.blue_check);
        p1PurpleCheck = (TextView) getActivity().findViewById(R.id.player1).findViewById(R.id.purple_check);
        p1RedCheck = (TextView) getActivity().findViewById(R.id.player1).findViewById(R.id.red_check);
        p1OrangeCheck = (TextView) getActivity().findViewById(R.id.player1).findViewById(R.id.orange_check);
        p1YellowCheck = (TextView) getActivity().findViewById(R.id.player1).findViewById(R.id.yellow_check);
        p1WhiteCheck = (TextView) getActivity().findViewById(R.id.player1).findViewById(R.id.white_check);
        p2GreenCheck = (TextView) getActivity().findViewById(R.id.player2).findViewById(R.id.green_check);
        p2BlueCheck = (TextView) getActivity().findViewById(R.id.player2).findViewById(R.id.blue_check);
        p2PurpleCheck = (TextView) getActivity().findViewById(R.id.player2).findViewById(R.id.purple_check);
        p2RedCheck = (TextView) getActivity().findViewById(R.id.player2).findViewById(R.id.red_check);
        p2OrangeCheck = (TextView) getActivity().findViewById(R.id.player2).findViewById(R.id.orange_check);
        p2YellowCheck = (TextView) getActivity().findViewById(R.id.player2).findViewById(R.id.yellow_check);
        p2WhiteCheck = (TextView) getActivity().findViewById(R.id.player2).findViewById(R.id.white_check);
    }

    private void populateArrayLists(){
        views.add(boardViews);
        views.add(player1Views);
        views.add(player2Views);
        boardViews.add(boardGreen);
        boardViews.add(boardBlue);
        boardViews.add(boardPurple);
        boardViews.add(boardRed);
        boardViews.add(boardOrange);
        boardViews.add(boardYellow);
        boardViews.add(boardWhite);
        player1Views.add(p1Green);
        player1Views.add(p1Blue);
        player1Views.add(p1Purple);
        player1Views.add(p1Red);
        player1Views.add(p1Orange);
        player1Views.add(p1Yellow);
        player1Views.add(p1White);
        player2Views.add(p2Green);
        player2Views.add(p2Blue);
        player2Views.add(p2Purple);
        player2Views.add(p2Red);
        player2Views.add(p2Orange);
        player2Views.add(p2Yellow);
        player2Views.add(p2White);
        boardChecks.add(boardGreenCheck);
        boardChecks.add(boardBlueCheck);
        boardChecks.add(boardPurpleCheck);
        boardChecks.add(boardRedCheck);
        boardChecks.add(boardOrangeCheck);
        boardChecks.add(boardYellowCheck);
        boardChecks.add(boardWhiteCheck);
        player1Checks.add(p1GreenCheck);
        player1Checks.add(p1BlueCheck);
        player1Checks.add(p1PurpleCheck);
        player1Checks.add(p1RedCheck);
        player1Checks.add(p1OrangeCheck);
        player1Checks.add(p1YellowCheck);
        player1Checks.add(p1WhiteCheck);
        player2Checks.add(p2GreenCheck);
        player2Checks.add(p2BlueCheck);
        player2Checks.add(p2PurpleCheck);
        player2Checks.add(p2RedCheck);
        player2Checks.add(p2OrangeCheck);
        player2Checks.add(p2YellowCheck);
        player2Checks.add(p2WhiteCheck);
    }
}
