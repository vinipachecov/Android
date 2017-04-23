package com.example.root.connect3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // 0 = Yellow , 1 = Red;
    int activePlayer = 0;

    boolean gameIsActive = true;

    // 2 means unplayed

    int [] gameStates = {2 ,2 ,2 ,2 ,2 ,2 ,2 ,2 ,2};

    int [][] winningPositions = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6} , {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};

    public void dropIn(View view){
        ImageView counter = (ImageView) view;

        System.out.println(counter.getTag().toString());

        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if (gameStates[tappedCounter] == 2 && gameIsActive) {

            gameStates[tappedCounter] = activePlayer;

            counter.setTranslationY(-1000f);

            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;
            }

            counter.animate().translationYBy(1000f).rotation(360).setDuration(300);

            for (int [] winningPosition : winningPositions){
                if(gameStates[winningPosition[0]] == gameStates[winningPosition[1]] &&
                        gameStates[winningPosition[1]] == gameStates[winningPosition[2]] &&
                        gameStates[winningPosition[0]] != 2){

                    gameIsActive = false;

                    String winner = "Red";

                    if(gameStates[winningPosition[0]] == 0){

                        winner = "Yellow";

                    }

                    //someone has won :D


                    TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);

                    winnerMessage.setText(winner + " has won");

                    LinearLayout lin = (LinearLayout) findViewById(R.id.playAgainLayout);

                    lin.setVisibility(view.VISIBLE);

                    lin.bringToFront();


                }else {

                    boolean gameIsOver = true;

                    for(int counterState : gameStates){

                        if(counterState == 2)
                            gameIsOver = false;
                    }

                    if(gameIsOver){

                        TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);

                        winnerMessage.setText("It's a draw");

                        LinearLayout lin = (LinearLayout) findViewById(R.id.playAgainLayout);

                        lin.setVisibility(view.VISIBLE);

                        lin.bringToFront();

                    }
                }
            }

        }
    }


    public void playAgain(View view){

        LinearLayout lin = (LinearLayout) findViewById(R.id.playAgainLayout);

        lin.setVisibility(view.INVISIBLE);

        activePlayer = 0;

        // 2 means unplayed

        for(int i = 0; i < gameStates.length; i++){
            gameStates[i] = 2;
        }

        GridLayout gridlayout = (GridLayout)findViewById(R.id.GridLayout);

        for(int i = 0; i < gridlayout.getChildCount(); i++){
            ((ImageView) gridlayout.getChildAt(i)).setImageResource(0);
        }

        gameIsActive = true;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
