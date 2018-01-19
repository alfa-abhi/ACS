package com.tiki.scarnedice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageView Dice;
    Integer MScore = 0;
    Integer AIScore = 0;
    Integer miniMe = 0;
    Integer miniAI = 0;
    Boolean player = true;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Dice = (ImageView) findViewById(R.id.imageView);
        textView = (TextView) findViewById(R.id.textView);
        textView.setText("Your Score: " + MScore + " Comp Score: " + AIScore + "\n" + " Your Turn Score: " + miniMe + " Comp Turn Score: " + miniAI);
        findViewById(R.id.roll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x;
                if (player)
                    x = rollDice();
            }
        });
        findViewById(R.id.hold).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player) {
                    MScore += miniMe;
                    AIScore += miniAI;
                    miniAI = 0;
                    miniMe = 0;
                    textView.setText("Your Score: " + MScore + " Comp Score: " + AIScore + "\n" + " Your Turn Score: " + miniMe + " Comp Turn Score: " + miniAI);
                    computerTurn();
                }
            }
        });
        findViewById(R.id.reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MScore = 0;
                AIScore = 0;
                textView.setText("Your Score: " + MScore + " Comp Score: " + AIScore + "\n" + " Your Turn Score: " + miniMe + " Comp Turn Score: " + miniAI);
                Dice.setImageResource(R.drawable.dice1);
            }
        });

    }

    private int rollDice() {
        Random random = new Random();
        int value = random.nextInt(6) + 1;
        if (value == 1) {
            miniAI = 0;
            miniMe = 0;
            if (player)
                computerTurn();
        } else {
            if (player)
                miniMe += value;
        }
        switch (value) {
            case 1: {
                Dice.setImageResource(R.drawable.dice1);
                break;
            }
            case 2: {
                Dice.setImageResource(R.drawable.dice2);
                break;
            }
            case 3: {
                Dice.setImageResource(R.drawable.dice3);
                break;
            }
            case 4: {
                Dice.setImageResource(R.drawable.dice4);
                break;
            }
            case 5: {
                Dice.setImageResource(R.drawable.dice5);
                break;
            }
            case 6: {
                Dice.setImageResource(R.drawable.dice6);
                break;
            }
        }
        textView.setText("Your Score: " + MScore + " Comp Score: " + AIScore + "\n" + " Your Turn Score: " + miniMe + " Comp Turn Score: " + miniAI);
        return value;
    }

    private void computerTurn() {
        player = false;
        int val = rollDice();
        while (val != 1 || miniAI < MScore) {
            val = rollDice();
            miniAI += val;
            if (miniAI > miniMe) {
                AIScore += miniAI;
                miniAI = 0;
                break;
            }
            if (val == 1)
                break;
        }
        player = true;
    }
}
