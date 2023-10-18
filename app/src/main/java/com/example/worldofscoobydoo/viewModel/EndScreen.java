package com.example.worldofscoobydoo.viewModel;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.worldofscoobydoo.R;
import com.example.worldofscoobydoo.model.Player;

public class EndScreen extends AppCompatActivity implements Observer {

    private EditText lastScore;
    private Player player;

    public void setFinalGameStatus(TextView finalGameStatus) {
        this.finalGameStatus = finalGameStatus;
    }

    private TextView finalGameStatus;
    private int prevScore;

    public EditText getLastScore() {
        return lastScore;
    }

    public int getPrevScore() {
        return prevScore;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // removes top bar title
        getSupportActionBar().hide(); // removes top bar
        setContentView(R.layout.endscreen);

        notifyObservers();

        lastScore = findViewById(R.id.userStatus);
        SharedPreferences pref = getSharedPreferences("PREFS", 0);
        prevScore = pref.getInt("lastScore", 0);
        lastScore.setText("Your Score: " + player.getScore());
        Button leaderButton = findViewById(R.id.finalLeaderboard);
        leaderButton.setOnClickListener(v -> {
            Intent leaderboard = new Intent(getApplicationContext(), LeaderBoard.class);
            startActivity(leaderboard);
        });

        Button restartButton = findViewById(R.id.playAgain);
        restartButton.setOnClickListener(v -> {
            Intent restart = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(restart);
        });
    }

    @Override
    public void notifyObservers() {
        player = Player.getPlayer();
        int score = player.getScore();
        setFinalGameStatus(findViewById(R.id.finalGameStatus));
        if (score > 0) {
            finalGameStatus.setText("You Win!");
        } else {
            finalGameStatus.setText("You Lose!");
        }


    }
}
