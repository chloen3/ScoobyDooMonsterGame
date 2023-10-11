package com.example.worldofscoobydoo;

import org.junit.Test;
import static org.junit.Assert.*;
import com.example.worldofscoobydoo.model.LeaderboardEntry;
import com.example.worldofscoobydoo.model.LeaderboardModel;
import com.example.worldofscoobydoo.view.LeaderBoard;

public class LeaderboardTest {

    LeaderboardModel instance = LeaderboardModel.getInstance();

    @Test
    public void decreaseOrder() {
        LeaderboardEntry[] entries = instance.getEntries();
        int val1 = entries[0].getScore();
        int val2 = entries[1].getScore();
        int val3 = entries[2].getScore();
        int val4 = entries[3].getScore();
        int val5 = entries[4].getScore();
        assertFalse(LeaderBoard.orderIsValid(val1, val2, val3, val4, val5));
    }
    @Test
    public void entryValues() {
        LeaderboardModel leaderboard = LeaderboardModel.getInstance();
        assertFalse(LeaderBoard.isCurrentScore(leaderboard));
    }
}
