package com.example.vinipachecov.braiuntrainer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

//    Buttons
    Button startButton;
    Button button0;
    Button button1;
    Button button2;
    Button button3;
    Button playAgainButton;

//    TextView
    TextView sumTextView;
    TextView resultTextView;
    TextView pointsTextView;
    TextView timerTextView;

//    Game
    RelativeLayout gameRelativeLayout;

// Questions


    ArrayList<Integer> answers = new ArrayList<Integer>();

//    Integers
    Integer locationOfCorrectAnswer;
    Integer score = 0;
    Integer numberOfQuestions = 0;



    public void playAgain(View view){

        score = 0;
        numberOfQuestions = 0;
        timerTextView.setText("30s");
        pointsTextView.setText("0/0");
        resultTextView.setText("");
        playAgainButton.setVisibility(View.INVISIBLE);

        generateQuestion();
        

    //        Creating the timer
        new CountDownTimer(30100, 1000){
            @Override
            public void onTick(long millisUntilFinished){
                timerTextView.setText(String.valueOf(millisUntilFinished / 1000)+ "s");

            }

            @Override
            public void onFinish(){
//                put final results

                playAgainButton.setVisibility(View.VISIBLE);
                timerTextView.setText("0s");
                resultTextView.setText("Your Score is: " +score.toString() + "/" + numberOfQuestions.toString());

            }

        }.start();


    }


    public void generateQuestion(){


        Random rand = new Random();

        int a = rand.nextInt(21);
        int b = rand.nextInt(21);

        sumTextView.setText(Integer.toString(a) + " + " + Integer.toString(b));
        playAgainButton.setVisibility(View.INVISIBLE);

//    avoid duplicate answers

        int incorrectAnswer;

//        set 3 incorrect answers and one correct

        locationOfCorrectAnswer = rand.nextInt(4);

        answers.clear();

        for (int i = 0; i < 4; i++){

            if ( i == locationOfCorrectAnswer){
                answers.add(a + b);
            } else {

                incorrectAnswer = rand.nextInt(41);

//                update while incorrect answer generate a correct answer

                while(incorrectAnswer == a + b){

                    incorrectAnswer = rand.nextInt(41);

                }

                answers.add(incorrectAnswer);

            }

        }

        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));



    }


    public void chooseAnswer(View view){
//    testing
//        Log.i("Log", view.getTag().toString());

        if ( view.getTag().toString().equals(locationOfCorrectAnswer.toString())){
            Log.i("Correct", "correct answer!");
            score++;
            resultTextView.setText("Correct!");
        } else {

            resultTextView.setText("Wrong!");
        }

        numberOfQuestions++;
        pointsTextView.setText(score.toString() + "/" + numberOfQuestions.toString());

        generateQuestion();
    }

    public void start(View view){

        startButton.setVisibility(View.INVISIBLE);
        gameRelativeLayout.setVisibility(RelativeLayout.VISIBLE);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startButton = (Button)findViewById(R.id.startbutton);
        sumTextView = (TextView)findViewById(R.id.sumtextView);
        button0 = (Button)findViewById(R.id.button0);
        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);
        resultTextView = (TextView)findViewById(R.id.resultTextView);
        pointsTextView = (TextView)findViewById(R.id.pointsTextView);
        timerTextView = (TextView)findViewById(R.id.timertextView);
        playAgainButton = (Button)findViewById(R.id.playAgainbutton);
        gameRelativeLayout = (RelativeLayout)findViewById(R.id.gameRelativeLayout);

//         two letters that we represent as numbers to be summed


        playAgain((Button)findViewById(R.id.playAgainbutton));

    }
}
