package com.example.worldofscoobydoo.view;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.worldofscoobydoo.R;
import com.example.worldofscoobydoo.model.LeaderboardEntry;
import com.example.worldofscoobydoo.model.LeaderboardModel;
import com.example.worldofscoobydoo.view.EndScreen;
import com.example.worldofscoobydoo.viewModel.LeaderboardViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LeaderBoard extends AppCompatActivity {
    int lastScore, score1, score2, score3, score4, score5;
    String player;
    String time;
    LeaderboardViewModel leaderboardViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard);

        SharedPreferences pref = getSharedPreferences("PREFS", 0);
        lastScore = pref.getInt("lastScore", 0);
        player = pref.getString("player", " ");
        time = pref.getString("time", " ");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        time = sdf.format(new Date());

        TextView currentTracker = findViewById(R.id.currentScore);
        currentTracker.setText(player + "       " + lastScore + "\n" + time);

        leaderboardViewModel = new ViewModelProvider(this).get(LeaderboardViewModel.class);
        LeaderboardEntry[] entries = leaderboardViewModel.getLeaderboardEntries();
        LeaderboardEntry latestEntry = new LeaderboardEntry(player, lastScore, time);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_leaderboard);
        if (entries[0] == null) {
            entries[0] = new LeaderboardEntry("Default", 0, "0");
        }
        if (entries[1] == null) {
            entries[1] = new LeaderboardEntry("Default", 0, "0");
        }
        if (entries[2] == null) {
            entries[2] = new LeaderboardEntry("Default", 0, "0");
        }
        if (entries[3] == null) {
            entries[3] = new LeaderboardEntry("Default", 0, "0");
        }
        if (entries[4] == null) {
            entries[4] = new LeaderboardEntry("Default", 0, "0");
        }
        LeaderboardEntry best0 = entries[0];
        LeaderboardEntry best1 = entries[1];
        LeaderboardEntry best2 = entries[2];
        LeaderboardEntry best3 = entries[3];
        LeaderboardEntry best4 = entries[4];

        if(lastScore > best0.getScore()) {
            entries[0] = latestEntry;
            entries[1] = best0;
            entries[2] = best1;
            entries[3] = best2;
            entries[4] = best3;
        } else if (lastScore > best1.getScore()) {
            entries[1] = latestEntry;
            entries[2] = best1;
            entries[3] = best2;
            entries[4] = best3;
        } else if (lastScore > best2.getScore()) {
            entries[2] = latestEntry;
            entries[3] = best2;
            entries[4] = best3;
        } else if (lastScore > best3.getScore()) {
            entries[3] = latestEntry;
            entries[4] = best3;
        } else if (lastScore > best4.getScore()) {
            entries[4] = latestEntry;
        }

//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        LeaderboardAdapter leaderboardAdapter = new LeaderboardAdapter(entries); // Create your adapter
        recyclerView.setAdapter(leaderboardAdapter);

        int[] scores = new int[5];
        for (int i = 0; i < entries.length; i++) {
            LeaderboardEntry curr = entries[i];
            int num = curr.getScore();
            scores[i] = num;
        }
        score1 = scores[0];
        score2 = scores[1];
        score3 = scores[2];
        score4 = scores[3];
        score5 = scores[4];


////
////        leaderBoard = (TextView) findViewById(R.id.leaderboard);
////        currentScore = (TextView) findViewById(R.id.currentScore);
////
//
////        best1 = first.getScore();
////        best2 = second.getScore();
////        best3 = third.getScore();
////        best4 = fourth.getScore();
////        best5 = fifth.getScore();
//
//
////        player1 = first.getPlayerName();
////        player2 = second.getPlayerName();
////        player3 = third.getPlayerName();
////        player4 = fourth.getPlayerName();
////        player5 = fifth.getPlayerName();
////
//
////        time1 = first.getDate();
////        time2 = second.getDate();
////        time3 = third.getDate();
////        time4 = fourth.getDate();
////        time5 = fifth.getDate();
//
//
////
//        newEntry = new LeaderboardEntry(player, lastScore, time);
//        leaderboardViewModel.editLeaderboardEntry(newEntry);
//        leaderboardViewModel.getLeaderboardEntries().observe(this, leaderboardEntries -> {
//            // Update the adapter with new data
//            leaderboardAdapter.submitList(leaderboardEntries);
//        });
//        leaderboardAdapter.notifyItemChanged(index);
////        if (lastScore > best5) {
////            best5 = lastScore;
////            player5 = player;
////            time5 = time;
////            fifth = new LeaderboardEntry(player, lastScore, time);
////        }
////
////        if (lastScore > best4) {
////            int temp = best4;
////            best4 = lastScore;
////            best5 = temp;
////            String temp2 = player4;
////            player4 = player;
////            player5 = temp2;
////            String temp3 = time4;
////            time4 = time;
////            time5 = temp3;
//////            SharedPreferences.Editor editor = pref.edit();
//////            editor.putInt("best4", best4);
//////            editor.putInt("best5", best5);
//////            editor.putString("player4", player4);
//////            editor.putString("player5", player5);
//////            editor.putString("time4", time4);
//////            editor.putString("time5", time5);
//////            editor.apply();
////            fifth = new LeaderboardEntry(player5, best5, time5);
////            fourth = new LeaderboardEntry(player4, best4, time4);
////        }
////
////        if (lastScore > best3) {
////            int temp = best3;
////            best3 = lastScore;
////            best4 = temp;
////            String temp2 = player3;
////            player3 = player;
////            player4 = temp2;
////            String temp3 = time3;
////            time3 = time;
////            time4 = temp3;
//////            SharedPreferences.Editor editor = pref.edit();
//////            editor.putInt("best3", best3);
//////            editor.putInt("best4", best4);
//////            editor.putString("player3", player3);
//////            editor.putString("player4", player4);
//////            editor.putString("time3", time3);
//////            editor.putString("time4", time4);
//////            editor.apply();
////            fourth = new LeaderboardEntry(player4, best4, time4);
////            third = new LeaderboardEntry(player3, best3, time3);
////        }
////
////        if (lastScore > best2) {
////            int temp = best2;
////            best2 = lastScore;
////            best3 = temp;
////            String temp2 = player2;
////            player2 = player;
////            player3 = temp2;
////            String temp3 = time2;
////            time2 = time;
////            time3 = temp3;
//////            SharedPreferences.Editor editor = pref.edit();
//////            editor.putInt("best2", best2);
//////            editor.putInt("best3", best3);
//////            editor.putString("player2", player2);
//////            editor.putString("player3", player3);
//////            editor.putString("time2", time2);
//////            editor.putString("time3", time3);
//////            editor.apply();
////            second = new LeaderboardEntry(player2, best2, time2);
////            third = new LeaderboardEntry(player3, best3, time3);
////        }
////
////        if (lastScore > best1) {
////            int temp = best1;
////            best1 = lastScore;
////            best2 = temp;
////            String temp2 = player1;
////            player1 = player;
////            player2 = temp2;
////            String temp3 = time1;
////            time1 = time;
////            time2 = temp3;
//////            SharedPreferences.Editor editor = pref.edit();
//////            editor.putInt("best1", best1);
//////            editor.putInt("best2", best2);
//////            editor.putString("player1", player1);
//////            editor.putString("player2", player2);
//////            editor.putString("time1", time1);
//////            editor.putString("time2", time2);
//////            editor.apply();
////            first = new LeaderboardEntry(player1, best1, time1);
////            second = new LeaderboardEntry(player2, best2, time2);
////        }
//
////        leaderBoard.setText(player1 + ",       " + best1 + ",      "  + time1 + "\n" +
////                player2 + ",       " + best2 + ",       " + time2 + "\n" +
////                player3 + ",       " + best3 + ",       " + time3 + "\n" +
////                player4 + ",       " + best4 + ",       " + time4 + "\n" +
////                player5 + ",       " + best5 + ",       " + time5 + "\n");
        // currentScore.setText("YOUR SCORE: " + "\n" + lastScore + "\n" + time);

        Button back = findViewById(R.id.backButton);
        back.setOnClickListener(v -> {
            Intent leaderboard = new Intent(getApplicationContext(), EndScreen.class);
            startActivity(leaderboard);
        });
    }

    public static boolean orderIsValid(int val1, int val2, int val3, int val4, int val5){
        if (val1 >= val2 && val2 >= val3 && val3 >= val4 && val4 >= val5) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean correctLengthBoard(LeaderboardModel model) {
        if (model.getSize() == 5) {
            return true;
        } else {
            return false;
        }
    }
}
