package com.example.tictactoe;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import java.util.Timer;
import java.util.TimerTask;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements OnClickListener{

    private Button gameGrid[][]=new Button[3][3];
    private Button newGameButton;
    private TextView messageTextView;

    private int turn;
    private String message;
    private boolean gameOver;
    private String gameString;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameGrid[0][0]=(Button) findViewById(R.id.square1);
        gameGrid[0][1]=(Button) findViewById(R.id.square2);
        gameGrid[0][2]=(Button) findViewById(R.id.square3);
        gameGrid[1][0]=(Button) findViewById(R.id.square4);
        gameGrid[1][1]=(Button) findViewById(R.id.square5);
        gameGrid[1][2]=(Button) findViewById(R.id.square6);
        gameGrid[2][0]=(Button) findViewById(R.id.square7);
        gameGrid[2][1]=(Button) findViewById(R.id.square8);
        gameGrid[2][2]=(Button) findViewById(R.id.square9);

        newGameButton=(Button) findViewById(R.id.newGameButton);
        messageTextView=(TextView) findViewById(R.id.messageTextView);

        newGameNoise();

        //event handlers
        for(int x=0; x<gameGrid.length; x++)
        {
            for(int y=0; y< gameGrid[x].length; y++)
            {
                gameGrid[x][y].setOnClickListener(this);
            }
        }

        newGameButton.setOnClickListener(this);

        //clear
        for(int x=0; x<gameGrid.length; x++)
        {
            for(int y=0; y< gameGrid[x].length; y++)
            {
                gameGrid[x][y].setText(" ");
            }
        }

        //start values
        turn=1;
        gameOver=false;
        message="Player X's turn";
        messageTextView.setText(message);
        gameString="        ";

    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.newGameButton:
                //clear;
                for(int x=0; x<gameGrid.length; x++)
                {
                    for(int y=0; y< gameGrid[x].length; y++)
                    {
                        gameGrid[x][y].setText(" ");
                    }
                }
                newGameNoise();
                turn=1;
                gameOver=false;
                message="Player X's turn";
                messageTextView.setText(message);
                gameString="         ";
                break;
            default:
                if(!gameOver)
                {
                    Button b=(Button) v;
                    if(b.getText().equals(" ")) {
                        if (turn % 2 != 0) {
                            b.setText("X");
                            goodClickNoise();
                            message="Player O's turn";
                        }
                        else
                        {
                            b.setText("O");
                            goodClickNoise();
                            message="Player X's turn";
                        }
                        turn++;
                        checkforGameOver();
                    }
                    else{
                        badClickNoise();
                        message="That square is taken. Try again.";}
                }
        }
        messageTextView.setText(message);
    }

    public void goodClickNoise(){
        MediaPlayer mplayer;
        mplayer = MediaPlayer.create(this,R.raw.bassdrop);
        mplayer.start();

    }

    public void badClickNoise(){
        MediaPlayer mplayer;
        mplayer = MediaPlayer.create(this,R.raw.error);
        mplayer.start();
    }

    public void winnerNoise(){
        MediaPlayer mplayer;
        mplayer = MediaPlayer.create(this,R.raw.winner);
        mplayer.start();
    }
    public void tieNoise(){
        MediaPlayer mplayer;
        mplayer = MediaPlayer.create(this,R.raw.slidewhistle);
        mplayer.start();
    }
    public void newGameNoise(){
        MediaPlayer mplayer;
        mplayer = MediaPlayer.create(this,R.raw.starting);
        mplayer.start();
    }

    private void checkforGameOver()
    {
        //rows
        for(int x=0; x<3;x++)
        {
            if(!gameGrid[x][0].getText().equals(" ")&&
                    gameGrid[x][0].getText().equals(gameGrid[x][1].getText())&&
                    gameGrid[x][1].getText().equals(gameGrid[x][2].getText())
            )
            {
                // Hear winning song
                winnerNoise();
                //See the btn tiles flash colors
                gameGrid[x][0].setBackgroundColor(getResources().getColor(R.color.black));
                gameGrid[x][1].setBackgroundColor(getResources().getColor(R.color.black));
                gameGrid[x][2].setBackgroundColor(getResources().getColor(R.color.black));
                int finalX = x;
                TimerTask tt = new TimerTask() {
                    @Override
                    public void run() {

                        gameGrid[finalX][0].setBackgroundColor(getResources().getColor(R.color.title_bar_color));
                        gameGrid[finalX][1].setBackgroundColor(getResources().getColor(R.color.title_bar_color));
                        gameGrid[finalX][2].setBackgroundColor(getResources().getColor(R.color.title_bar_color));
                    }
                };
                Timer clearColors = new Timer();
                clearColors.schedule(tt,500,2000);

                message=gameGrid[x][0].getText()+" wins";
                gameOver=true;
                return;
            }
        }

        //columns
        for(int y=0; y<3;y++)
        {
            if(!gameGrid[0][y].getText().equals(" ")&&
                    gameGrid[0][y].getText().equals(gameGrid[1][y].getText())&&
                    gameGrid[1][y].getText().equals(gameGrid[2][y].getText())
            )
            {
                winnerNoise();

                //See the btn tiles flash colors
                gameGrid[0][y].setBackgroundColor(getResources().getColor(R.color.black));
                gameGrid[1][y].setBackgroundColor(getResources().getColor(R.color.black));
                gameGrid[2][y].setBackgroundColor(getResources().getColor(R.color.black));
                int finalY = y;
                TimerTask tt = new TimerTask() {
                    @Override
                    public void run() {

                        gameGrid[0][finalY].setBackgroundColor(getResources().getColor(R.color.title_bar_color));
                        gameGrid[1][finalY].setBackgroundColor(getResources().getColor(R.color.title_bar_color));
                        gameGrid[2][finalY].setBackgroundColor(getResources().getColor(R.color.title_bar_color));
                    }
                };
                Timer clearColors = new Timer();
                clearColors.schedule(tt,500,2000);
                message=gameGrid[0][y].getText()+" wins";
                gameOver=true;
                return;
            }
        }

        //diagonal 1
        if(!gameGrid[0][0].getText().equals(" ")&&
                gameGrid[0][0].getText().equals(gameGrid[1][1].getText())&&
                gameGrid[1][1].getText().equals(gameGrid[2][2].getText())
        )
        {
            winnerNoise();
            //See the btn tiles flash colors
            gameGrid[0][0].setBackgroundColor(getResources().getColor(R.color.black));
            gameGrid[1][1].setBackgroundColor(getResources().getColor(R.color.black));
            gameGrid[2][2].setBackgroundColor(getResources().getColor(R.color.black));

            TimerTask tt = new TimerTask() {
                @Override
                public void run() {

                    gameGrid[0][0].setBackgroundColor(getResources().getColor(R.color.title_bar_color));
                    gameGrid[1][1].setBackgroundColor(getResources().getColor(R.color.title_bar_color));
                    gameGrid[2][2].setBackgroundColor(getResources().getColor(R.color.title_bar_color));
                }
            };
            Timer clearColors = new Timer();
            clearColors.schedule(tt,500,2000);
            message=gameGrid[0][0].getText()+" wins";
            gameOver=true;
            return;
        }
        //diagonal 2
        if(!gameGrid[2][0].getText().equals(" ")&&
                gameGrid[2][0].getText().equals(gameGrid[1][1].getText())&&
                gameGrid[0][2].getText().equals(gameGrid[1][1].getText())
        )
        {
            winnerNoise();
            //See the btn tiles flash colors
            gameGrid[0][2].setBackgroundColor(getResources().getColor(R.color.black));
            gameGrid[1][1].setBackgroundColor(getResources().getColor(R.color.black));
            gameGrid[2][0].setBackgroundColor(getResources().getColor(R.color.black));

            TimerTask tt = new TimerTask() {
                @Override
                public void run() {

                    gameGrid[0][2].setBackgroundColor(getResources().getColor(R.color.title_bar_color));
                    gameGrid[1][1].setBackgroundColor(getResources().getColor(R.color.title_bar_color));
                    gameGrid[2][0].setBackgroundColor(getResources().getColor(R.color.title_bar_color));
                }
            };
            Timer clearColors = new Timer();
            clearColors.schedule(tt,500,2000);
            message=gameGrid[2][0].getText()+" wins";
            gameOver=true;
            return;
        }

        //

        if(turn>9)
        {
            tieNoise();
            message="It is a tie";
            gameOver=true;
            return;
        }
        gameOver=false;
    }
}
