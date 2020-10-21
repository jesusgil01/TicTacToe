package com.example.tictactoe;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView playerOneScore, playerTwoScore, playerStatus;
    private ImageButton [] buttons = new ImageButton[9];
    private Button resetGame;
    int width = 0;
    int height = 0;

    private int playerOneScoreCount, playerTwoScoreCount, rountCount;
    boolean activePlayer;

    //p1 => 0
    //p2 => 1
    //empty => 2
    int [] gameState = {2,2,2,2,2,2,2,2,2};

    int [][] winningPositions = {
            {0,1,2}, {3,4,5}, {6,7,8}, //renglones
            {0,3,6}, {1,4,7}, {2,5,8}, //columnas
            {0,4,8}, {2,4,6} //cruz
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerOneScore = (TextView) findViewById(R.id.playerOneScore);
        playerTwoScore = (TextView) findViewById(R.id.playerTwoScore);
        playerStatus = (TextView) findViewById(R.id.playerStatus);

        resetGame = (Button) findViewById(R.id.resetGame);

        for (int i = 0; i < buttons.length; i++){
            String buttonID = "btn_" + i;
            int resourceID = getResources().getIdentifier(buttonID, "id", getPackageName());
            buttons[i] = (ImageButton) findViewById(resourceID);
            buttons[i].setOnClickListener(this);
            width = buttons[i].getWidth();
            height = buttons[i].getHeight();
        }

        rountCount = 0;
        playerOneScoreCount = 0;
        playerTwoScoreCount = 0;
        activePlayer = true;
    }

    @Override
    public void onClick(View v) {
        if (!(((ImageButton) v).getDrawable() == null)){
            return;
        }
        String buttonID = v.getResources().getResourceEntryName(v.getId());
        int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length()-1, buttonID.length()));

        if (activePlayer){
            ((ImageButton) v).setBackgroundResource(R.drawable.ic_x);
            ((ImageButton) v).setEnabled(false);
            gameState[gameStatePointer] = 0;
        } else {
            ((ImageButton) v).setBackgroundResource(R.drawable.ic_o);
            ((ImageButton) v).setEnabled(false);;
            gameState[gameStatePointer] = 1;
        }
        rountCount++;

        if (checkWinner()) {
            if (activePlayer){
                playerOneScoreCount++;
                updatePlayerScore();
                Toast.makeText(this,"Ganó el Jugador 1!!!", Toast.LENGTH_SHORT).show();
                playAgain();
            } else {
                playerTwoScoreCount++;
                updatePlayerScore();
                Toast.makeText(this, "Ganó el Jugador 2!!!", Toast.LENGTH_SHORT).show();
                playAgain();
            }
        } else if (rountCount == 9) {
            playAgain();
            Toast.makeText(this, "Empate!", Toast.LENGTH_SHORT).show();
        } else {
            activePlayer = !activePlayer;
        }

        if (playerOneScoreCount > playerTwoScoreCount) {
            playerStatus.setText("El Jugador 1 va ganando!");
        } else if (playerTwoScoreCount > playerOneScoreCount) {
            playerStatus.setText("El Jugador 2 va ganando!");
        } else {
            playerStatus.setText("");
        }

        resetGame.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                playAgain();
                playerOneScoreCount = 0;
                playerTwoScoreCount = 0;
                playerStatus.setText("");
                updatePlayerScore();
            }
        });
    }

    public boolean checkWinner(){
        boolean winnerResult = false;

        for (int [] winningPosition: winningPositions) {
            if (gameState[winningPosition[0]] == gameState[winningPosition[1]]
                    && gameState[winningPosition[1]] == gameState[winningPosition[2]]
                    && gameState[winningPosition[0]] != 2) {
                winnerResult = true;
            }
        }
        return winnerResult;
    }

    public void updatePlayerScore(){
        playerOneScore.setText(Integer.toString(playerOneScoreCount));
        playerTwoScore.setText(Integer.toString(playerTwoScoreCount));
    }


    public void playAgain(){
        rountCount = 0;
        activePlayer = true;

        for (int i = 0; i < buttons.length; i++){
            gameState[i] = 2;
            buttons[i].setBackgroundColor(Color.parseColor("#413F43"));
            buttons[i].setEnabled(true);
        }
    }


}