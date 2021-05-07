package com.nathanhuisman.tictactoeapp;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;


class TicTacToeBoard extends View {

    private final int boardColor;
    private final int XColor;
    private final int OColor;
    private final int winningLineColor;

    private final Paint paint = new Paint();

    private final GameLogic game;

    private int cellSize = getWidth()/3;

    private boolean winningLine = false;

    public TicTacToeBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        game = new GameLogic();

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TicTacToeBoard, 0, 0);

        try{
            boardColor = a.getInteger(R.styleable.TicTacToeBoard_boardColor, 0);
            XColor = a.getInteger(R.styleable.TicTacToeBoard_XColor, 0);
            OColor = a.getInteger(R.styleable.TicTacToeBoard_OColor, 0);
            winningLineColor = a.getInteger(R.styleable.TicTacToeBoard_winningLineColor, 0);

        }finally{
            a.recycle();
        }

    }

    @Override
    protected void onMeasure(int width, int height){
        super.onMeasure(width, height);

        //find whether or width or height is less so that a perfect square can be created
        int dimensions = Math.min(getMeasuredWidth(), getMeasuredHeight());

        //size of each of the 9 squares
        cellSize = dimensions/3;

        //set the dimensions to the said square
        setMeasuredDimension(dimensions, dimensions);
    }

    @Override
    protected void onDraw(Canvas canvas){
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        drawGameBoard(canvas);
        drawMarkers(canvas);

        if(winningLine){
            paint.setColor(winningLineColor);
            drawWinningLine(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        float x = event.getX();
        float y = event.getY();

        int action = event.getAction();

        //checks to see if the screen is clicked
        if(action == MotionEvent.ACTION_DOWN){
            int row = (int) Math.ceil(y/cellSize);
            int col = (int) Math.ceil(x/cellSize);

            if(!winningLine){
                if(game.updateGameBoard(row, col)){
                    invalidate();

                    if(game.winnerCheck()){
                        winningLine= true;
                        invalidate();
                    }

                    //finds out which player's turn it is and updating it
                    if(game.getPlayer() % 2 == 0){
                        game.setPlayer(game.getPlayer()-1);
                    } else {
                        game.setPlayer(game.getPlayer()+1);
                    }
                }
            }


            invalidate();

            return true;
        }
        return false;
    }

    //draw the grid
    private void drawGameBoard(Canvas canvas){
        paint.setColor(boardColor);
        paint.setStrokeWidth(16);

        //columns
        for(int c=1; c<3; c++){
            canvas.drawLine(cellSize*c, 0, cellSize*c, canvas.getWidth(), paint);
        }

        //rows
        for(int r=1; r<3; r++){
            canvas.drawLine(0, cellSize*r, canvas.getWidth(), cellSize*r, paint);
        }
    }

    private void drawMarkers(Canvas canvas){
        for (int r=0; r<3; r++){
            for (int c=0; c<3; c++){
                if (game.getGameBoard()[r][c] != 0) {
                    //1 will be x and 2 will be y
                    if(game.getGameBoard()[r][c] == 1){
                        drawX(canvas, r, c);
                    } else {
                        drawO(canvas, r, c);
                    }
                }
            }
        }
    }



    //draw X
    private void drawX(Canvas canvas, int row, int col){
        paint.setColor(XColor);

        //draw an x. essentially is two lines going from corner to corner.
        //the floats and the *0.2's are for padding so the shape doesn't overlap with the grid
        canvas.drawLine((float)((col+1)*cellSize - cellSize*0.2),
                        (float)(row*cellSize + cellSize*0.2),
                        (float)(col*cellSize + cellSize*0.2),
                        (float)((row+1)*cellSize - cellSize*0.2),
                        paint);
        canvas.drawLine((float)(col*cellSize + cellSize*0.2),
                        (float)(row*cellSize + cellSize*0.2),
                        (float)((col+1)*cellSize - cellSize*0.2),
                        (float)((row+1)*cellSize - cellSize*0.2),
                        paint);
    }

    //draw O
    private void drawO(Canvas canvas, int row, int col){
        paint.setColor(OColor);

        //draw an O. definitely had to look up how to use this
        canvas.drawOval((float)(col*cellSize + cellSize*0.2),
                        (float)(row*cellSize + cellSize*0.2),
                        (float)((col*cellSize + cellSize - cellSize*0.2)),
                        (float)((row*cellSize + cellSize - cellSize*0.2)),
                        paint);
    }


    //draws horizontal lines on the winning row
    private void drawHorizontalLine(Canvas canvas, int row, int col){
        canvas.drawLine(col, row*cellSize + (float)cellSize/2,
                        cellSize*3, row*cellSize + (float)cellSize/2,
                              paint);
    }

    //draws vertical lines
    private void drawVerticalLine(Canvas canvas, int row, int col){
        canvas.drawLine(col*cellSize + (float)cellSize/2,
                        row,
                col*cellSize + (float)cellSize/2,
                cellSize*3,
                paint);
    }

    //draw diagonal lines
    private void drawTopLeftToBottomRightLine(Canvas canvas){
        canvas.drawLine(0, cellSize*3,
                cellSize*3, 0, paint);
    }

    private void drawBottomLeftToTopRightLine(Canvas canvas){
        canvas.drawLine(0, 0,
                cellSize*3, cellSize*3, paint);
    }

    private void drawWinningLine(Canvas canvas){
        int row = game.getWinType()[0];
        int col = game.getWinType()[1];

        switch(game.getWinType()[2]){
            case 1:
                drawHorizontalLine(canvas, row, col);
                break;
            case 2:
                drawVerticalLine(canvas, row, col);
                break;
            case 3:
                drawBottomLeftToTopRightLine(canvas);
                break;
            case 4:
                drawTopLeftToBottomRightLine(canvas);
                break;
        }
    }

    public void setUpGame(Button playAgainButton, Button homeButton, TextView playerDisplay, String[] names){
        game.setPlayAgainBtn(playAgainButton);
        game.setHomeBtn(homeButton);
        game.setPlayerTurn(playerDisplay);
        game.setPlayerNames(names);
    }

    public void resetGame(){
        game.resetGame();
        winningLine = false;
    }
}
