package com.nathanhuisman.tictactoeapp;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameLogic {

    //create an array to store which places have been played upon
    private int[][] gameBoard;

    //row, col, win type
    private int[] winType = {-1, -1, -1};

    private String[] playerNames = {"Player 1", "Player 2"};

    private Button playAgainBtn;
    private Button homeBtn;
    private TextView playerTurn;

    private int player = 1; //player one always goes first

    GameLogic(){
        gameBoard = new int[3][3];

        //set all values on the game board initially to 0
        for (int r=0; r<3; r++){
            for (int c=0; c<3; c++){
                gameBoard[r][c] = 0;
            }
        }
    }

    public boolean updateGameBoard(int row, int col){
        //if spot is empty draw the appropriate shape
        if (gameBoard[row-1][col-1] == 0){
            gameBoard[row-1][col-1] = player;

            if(player == 1){
                playerTurn.setText((playerNames[1] + "'s Turn"));
            } else {
                playerTurn.setText((playerNames[0] + "'s Turn"));
            }

            return true;
        }
        //if filled return false and do nothing
        else{
            return false;
        }
    }

    //returns true if there was a winner
    public boolean winnerCheck(){
        boolean isWinner = false;

        // horizontal check winType = 1
        for(int r=0; r<3; r++){
            if(gameBoard[r][0] == gameBoard[r][1] && gameBoard[r][0] == gameBoard[r][2] &&
                    gameBoard[r][0] != 0){
                winType = new int[] {r, 0, 1};
                isWinner = true;
            }
        }

        //vertical check winType = 2
        for(int c=0; c<3; c++){
            if(gameBoard[0][c] == gameBoard[1][c] && gameBoard[0][c] == gameBoard[2][c] &&
                    gameBoard[0][c] != 0){
                winType = new int[] {0, c, 2};
                isWinner = true;
            }
        }

        //top left to bottom right check winType = 3
        if(gameBoard[0][0] == gameBoard[1][1] && gameBoard[0][0] == gameBoard[2][2] &&
                gameBoard[0][0] != 0){
            winType = new int[] {0, 2, 3};
            isWinner = true;
        }

        //bottom left to top right check winType = 4
        if(gameBoard[2][0] == gameBoard[1][1] && gameBoard[2][0] == gameBoard[0][2] &&
                gameBoard[2][0] != 0){
            winType = new int[] {2, 2, 4};
            isWinner = true;
        }

        int boardFilled = 0;

        //check to see if the board was filled
        for (int r=0; r<3; r++){
            for (int c=0; c<3; c++){
                if (gameBoard[r][c] != 0){
                    boardFilled +=1;
                }
            }
        }

        //if there is a winner make the buttons visible and declare who won
        if(isWinner){
            playAgainBtn.setVisibility(View.VISIBLE);
            homeBtn.setVisibility(View.VISIBLE);
            playerTurn.setText((playerNames[player-1] + " Won!!!"));
            return true;
        } else if(boardFilled == 9){
            playAgainBtn.setVisibility(View.VISIBLE);
            homeBtn.setVisibility(View.VISIBLE);
            playerTurn.setText(("The game was a tie!"));
            return true;
        } else {
            return false;
        }

    }

    public void resetGame(){
        for (int r=0; r<3; r++){
            for (int c=0; c<3; c++){
                gameBoard[r][c] = 0;
            }
        }

        player = 1;

        playAgainBtn.setVisibility(View.GONE);
        homeBtn.setVisibility(View.GONE);

        playerTurn.setText((playerNames[0] + "'s turn"));


    }

    public void setPlayAgainBtn(Button playAgainBtn) {
        this.playAgainBtn = playAgainBtn;
    }

    public void setHomeBtn(Button homeBtn) {
        this.homeBtn = homeBtn;
    }

    public void setPlayerTurn(TextView playerTurn) {
        this.playerTurn = playerTurn;
    }

    public void setPlayerNames(String[] playerNames) {
        this.playerNames = playerNames;
    }

    //getter for the game board
    public int[][] getGameBoard() {
        return gameBoard;
    }

    public void setPlayer(int player){
        this.player = player;
    }

    public int getPlayer(){
        return player;
    }

    public int[] getWinType() {
        return winType;
    }
}
