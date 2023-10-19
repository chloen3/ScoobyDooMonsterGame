package com.example.worldofscoobydoo.viewModel;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.worldofscoobydoo.R;
import com.example.worldofscoobydoo.model.LeaderboardEntry;
import com.example.worldofscoobydoo.model.LeaderboardModel;
import com.example.worldofscoobydoo.model.Player;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LeaderBoard extends AppCompatActivity {
    private int lastScore;

    public int getLastScore() {
        return lastScore;
    }

    private int score1;
    private int score2;
    private int score3;
    private int score4;
    private int score5;
    private String player;
    private String time;
    private LeaderboardViewModel leaderboardViewModel;
    private Player instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // removes top bar title
        getSupportActionBar().hide(); // removes top bar
        setContentView(R.layout.leaderboard);
        instance = Player.getPlayer();

        SharedPreferences pref = getSharedPreferences("PREFS", 0);
        lastScore = pref.getInt("lastScore", 0);
        player = pref.getString("player", " ");
        time = pref.getString("time", " ");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        time = sdf.format(new Date());

        TextView currentTracker = findViewById(R.id.currentScore);
        currentTracker.setText(player + "       " + instance.getScore() + "\n" + time);

        leaderboardViewModel = new ViewModelProvider(this).get(LeaderboardViewModel.class);
        LeaderboardEntry[] entries = leaderboardViewModel.getLeaderboardEntries();
        LeaderboardEntry latestEntry = new LeaderboardEntry(player, instance.getScore(), time);

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

        if (lastScore > best0.getScore()) {
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

        // recyclerView.setLayoutManager(new LinearLayoutManager(this));
        LeaderboardAdapter leaderboardAdapter = new LeaderboardAdapter(entries);
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
        Button back = findViewById(R.id.backButton);
        back.setOnClickListener(v -> {
            Intent leaderboard = new Intent(getApplicationContext(), EndScreen.class);
            startActivity(leaderboard);
        });
    }

    public static boolean orderIsValid(LeaderboardModel leader) {
        LeaderboardEntry[] entries = leader.getEntries();
        boolean result = false;
        for (int i = 1; i < entries.length; i++) {
            if (!(entries[i - 1] == null)) {
                if (entries[i - 1].getScore() >= entries[i].getScore()) {
                    result = true;
                } else {
                    result = false;
                }
            }
        }
        return result;
    }

    public static boolean isCurrentScore(LeaderboardModel leaderboard) {
        LeaderboardEntry[] entries = leaderboard.getEntries();
        boolean result = false;
        for (int i = 0; i < entries.length; i++) {
            if (entries[i] == null) {
                return false;
            } else {
                if (entries[i].getPlayerName() != null && entries[i].getScore() != 0
                        && entries[i].getDate() != null) {
                    result = true;
                } else {
                    result = false;
                }
            }
        }
        return result;
    }

    public static boolean isBoardInstantiated(LeaderboardModel leaderboard) {
        LeaderboardEntry[] entries = leaderboard.getEntries();
        boolean result = false;
        int count = 0;
        for (int i = 0; i < entries.length; i++) {
            if (entries[i] != null) {
                count++;
            }
        }
        if (count == 5) {
            result = true;
        }
        return result;
    }
}
