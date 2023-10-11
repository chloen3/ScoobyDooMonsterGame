package com.example.worldofscoobydoo.model;
import java.util.ArrayList;
import java.util.List;

public class LeaderboardModel {
        private static LeaderboardModel instance;
        private LeaderboardEntry[] entries;

        private LeaderboardModel() {
            entries = new LeaderboardEntry[5];
        }

        public static LeaderboardModel getInstance() {
            if (instance == null) {
                instance = new LeaderboardModel();
            }
            return instance;
        }

        public LeaderboardEntry[] getEntries() {
            return entries;
        }
}
