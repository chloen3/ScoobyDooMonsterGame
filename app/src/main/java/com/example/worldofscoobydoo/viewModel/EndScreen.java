package com.example.worldofscoobydoo.viewModel;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.worldofscoobydoo.R;

public class EndScreen extends AppCompatActivity {

    private EditText lastScore;
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
        setContentView(R.layout.endscreen);

        lastScore = findViewById(R.id.userStatus);
        SharedPreferences pref = getSharedPreferences("PREFS", 0);
        prevScore = pref.getInt("lastScore", 0);
        lastScore.setText("Your Score: " + prevScore);
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
}
