package com.nathanhuisman.tictactoeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameDisplay extends AppCompatActivity {

    private TicTacToeBoard ticTacToeBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_display);

        Button playAgainBTN = findViewById(R.id.playAgainButton);
        Button homeBTN = findViewById(R.id.homeButton);
        TextView playerTurn = findViewById(R.id.playerTurnView);

        playAgainBTN.setVisibility(View.GONE);
        homeBTN.setVisibility(View.GONE);

        String[] playerName = getIntent().getStringArrayExtra("PLAYER_NAMES");

        ticTacToeBoard = findViewById(R.id.ticTacToeBoard2);

        ticTacToeBoard.setUpGame(playAgainBTN, homeBTN, playerTurn, playerName);

        if(playerName != null){
            playerTurn.setText((playerName[0] + "'s Turn"));
        }
    }

    public void playAgainButtonClick(View view){
        ticTacToeBoard.resetGame();
        ticTacToeBoard.invalidate();
    }

    public void homeButtonClick(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}