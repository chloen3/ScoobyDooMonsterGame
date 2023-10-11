package com.example.worldofscoobydoo;

import org.junit.Test;
import static org.junit.Assert.*;

import com.example.worldofscoobydoo.model.LeaderboardModel;
import com.example.worldofscoobydoo.view.LeaderBoard;

public class LeaderboardTest {
    @Test
    public void decreaseOrder() {
        int val1 = 1;
        int val2 = 3;
        int val3 = 5;
        int val4 = 7;
        int val5 = 9;
        assertFalse(LeaderBoard.orderIsValid(val1, val2, val3, val4, val5));
    }

    @Test
    public void orderNumber() {
        LeaderboardModel model = LeaderboardModel.getInstance();
        assertFalse(LeaderBoard.correctLengthBoard(model));
    }
}
