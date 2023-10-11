package com.example.worldofscoobydoo.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;

import com.example.worldofscoobydoo.model.LeaderboardEntry;
import com.example.worldofscoobydoo.model.LeaderboardModel;



public class LeaderboardViewModel extends ViewModel {
    private LeaderboardModel leaderboard;
    private LeaderboardEntry[] leaderboardEntriesList;

    public LeaderboardViewModel() {
        leaderboard = LeaderboardModel.getInstance();
        leaderboardEntriesList = leaderboard.getEntries();
    }

    public LeaderboardEntry[] getLeaderboardEntries() {
        return leaderboardEntriesList;
    }
}