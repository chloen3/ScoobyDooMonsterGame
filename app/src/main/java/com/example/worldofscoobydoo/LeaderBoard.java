package com.example.worldofscoobydoo;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

public class LeaderBoard extends AppCompatActivity {
    TextView leaderBoard;
    int lastScore;
    int best3;
    int best2;
    int best1;
    String player;
    String player1;
    String player2;
    String player3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard);

        leaderBoard = (TextView) findViewById(R.id.leaderboard);

        SharedPreferences pref = getSharedPreferences("PREFS", 0);
        lastScore = pref.getInt("lastScore", 0);
        best1 = pref.getInt("best1", 0);
        best2 = pref.getInt("best2", 0);
        best3 = pref.getInt("best3", 0);

        player = pref.getString("player", "DEFAULT5 ");
        player1 = pref.getString("player1", "DEFAULT1 ");
        player2 = pref.getString("player2", "DEFAULT2 ");
        player3 = pref.getString("player3", "DEFAULT3 ");


//        UNCOMMENT THIS , RUN ONCE TO HARD RESET LEADERBOARD, THEN RE COMMENT
//        THIS IS FOR TESTING PURPOSES
//        ALSO MAKE 2 MORE PLAYERS THANK U

        best1 = 0;
        best2 = 0;
        best3 = 0;
        player1 = "DEFAULT1";
        player2 = "DEFAULT2";
        player3 = "DEFAULT3";

        if (lastScore > best3) {
            best3 = lastScore;
            player3 = player;
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("best3", best3);
            editor.putString("player3", player3);
            editor.apply();
        }

        if (lastScore > best2) {
            int temp = best2;
            best2 = lastScore;
            best3 = temp;
            String temp2 = player2;
            player2 = player;
            player3 = temp2;
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("best2", best2);
            editor.putInt("best3", best3);
            editor.putString("player2", player2);
            editor.putString("player3", player3);
            editor.apply();
        }

        if (lastScore > best1) {
            int temp = best1;
            best1 = lastScore;
            best2 = temp;
            String temp2 = player1;
            player1 = player;
            player2 = temp2;
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("best1", best1);
            editor.putInt("best2", best2);
            editor.putString("player1", player1);
            editor.putString("player2", player2);
            editor.apply();
        }

        leaderBoard.setText("YOUR SCORE: " + lastScore + "\n" +
                player1 + " " + best1 + "\n" +
                player2 + " " + best2 + "\n" +
                player3 + " " + best3);

        Button back = findViewById(R.id.backButton);
        back.setOnClickListener(v -> {
            Intent leaderboard = new Intent(getApplicationContext(), EndScreen.class);
            startActivity(leaderboard);
        });
    }
}
