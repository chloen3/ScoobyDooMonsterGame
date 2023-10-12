package com.example.worldofscoobydoo;

import org.junit.Test;
import static org.junit.Assert.*;
import com.example.worldofscoobydoo.model.LeaderboardEntry;
import com.example.worldofscoobydoo.model.LeaderboardModel;
import com.example.worldofscoobydoo.viewModel.LeaderBoard;

public class LeaderboardTest {

    LeaderboardModel instance = LeaderboardModel.getInstance();

    @Test
    public void decreaseOrder() {
        LeaderboardEntry[] entries = instance.getEntries();
        assertFalse(LeaderBoard.orderIsValid(instance));
    }
    @Test
    public void entryValues() {
        LeaderboardModel leaderboard = LeaderboardModel.getInstance();
        assertFalse(LeaderBoard.isCurrentScore(leaderboard));
    }
}
