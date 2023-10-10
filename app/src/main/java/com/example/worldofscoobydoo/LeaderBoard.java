package com.example.worldofscoobydoo;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LeaderBoard extends AppCompatActivity {
    TextView leaderBoard, currentScore;
    int lastScore, best1, best2, best3, best4, best5;
    String player, player1, player2, player3, player4, player5;
    String time, time1, time2, time3, time4, time5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard);

        leaderBoard = (TextView) findViewById(R.id.leaderboard);
        currentScore = (TextView) findViewById(R.id.currentScore);

        SharedPreferences pref = getSharedPreferences("PREFS", 0);
        lastScore = pref.getInt("lastScore", 0);
        best1 = pref.getInt("best1", 0);
        best2 = pref.getInt("best2", 0);
        best3 = pref.getInt("best3", 0);
        best4 = pref.getInt("best4", 0);
        best5 = pref.getInt("best5", 0);

        player = pref.getString("player", " ");
        player1 = pref.getString("player1", " ");
        player2 = pref.getString("player2", " ");
        player3 = pref.getString("player3", " ");
        player4 = pref.getString("player4", " ");
        player5 = pref.getString("player5", " ");

        time = pref.getString("time", " ");
        time1 = pref.getString("time1", " ");
        time2 = pref.getString("time2", " ");
        time3 = pref.getString("time3", " ");
        time4 = pref.getString("time4", " ");
        time5 = pref.getString("time5", " ");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        time = sdf.format(new Date());

        if (lastScore > best5) {
            best5 = lastScore;
            player5 = player;
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("best5", best5);
            editor.putString("time5",  time5);
            editor.putString("player5", player5);
            editor.apply();
        }

        if (lastScore > best4) {
            int temp = best4;
            best4 = lastScore;
            best5 = temp;
            String temp2 = player4;
            player4 = player;
            player5 = temp2;
            String temp3 = time4;
            time4 = time;
            time5 = temp3;
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("best4", best4);
            editor.putInt("best5", best5);
            editor.putString("player4", player4);
            editor.putString("player5", player5);
            editor.putString("time4", time4);
            editor.putString("time5", time5);
            editor.apply();
        }

        if (lastScore > best3) {
            int temp = best3;
            best3 = lastScore;
            best4 = temp;
            String temp2 = player3;
            player3 = player;
            player4 = temp2;
            String temp3 = time3;
            time3 = time;
            time4 = temp3;
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("best3", best3);
            editor.putInt("best4", best4);
            editor.putString("player3", player3);
            editor.putString("player4", player4);
            editor.putString("time3", time3);
            editor.putString("time4", time4);
            editor.apply();
        }

        if (lastScore > best2) {
            int temp = best2;
            best2 = lastScore;
            best3 = temp;
            String temp2 = player2;
            player2 = player;
            player3 = temp2;
            String temp3 = time2;
            time2 = time;
            time3 = temp3;
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("best2", best2);
            editor.putInt("best3", best3);
            editor.putString("player2", player2);
            editor.putString("player3", player3);
            editor.putString("time2", time2);
            editor.putString("time3", time3);
            editor.apply();
        }

        if (lastScore > best1) {
            int temp = best1;
            best1 = lastScore;
            best2 = temp;
            String temp2 = player1;
            player1 = player;
            player2 = temp2;
            String temp3 = time1;
            time1 = time;
            time2 = temp3;
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("best1", best1);
            editor.putInt("best2", best2);
            editor.putString("player1", player1);
            editor.putString("player2", player2);
            editor.putString("time1", time1);
            editor.putString("time2", time2);
            editor.apply();
        }

        leaderBoard.setText(player1 + ",       " + best1 + ",       "  + time1 + "\n" +
                player2 + ",       " + best2 + ",       " + time2 + "\n" +
                player3 + ",       " + best3 + ",       " + time3 + "\n" +
                player4 + ",       " + best4 + ",       " + time4 + "\n" +
                player5 + ",       " + best5 + ",       " + time5 + "\n");
        currentScore.setText("YOUR SCORE: " + "\n" + lastScore + "\n" + time);

        Button back = findViewById(R.id.backButton);
        back.setOnClickListener(v -> {
            Intent leaderboard = new Intent(getApplicationContext(), EndScreen.class);
            startActivity(leaderboard);
        });
    }
}
