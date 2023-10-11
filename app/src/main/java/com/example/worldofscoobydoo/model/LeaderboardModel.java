package com.example.worldofscoobydoo.model;

public class LeaderboardModel {
    private static volatile LeaderboardModel instance;

    public void setEntries(LeaderboardEntry[] entries) {
        this.entries = entries;
    }

    private LeaderboardEntry[] entries = new LeaderboardEntry[5];

    private LeaderboardModel() { };

    public static LeaderboardModel getInstance() {
        if (instance == null) {
            instance = new LeaderboardModel();
        }
        return instance;
    }

    public LeaderboardEntry[] getEntries() {
        return entries;
    }

    public int getSize() {
        return 5;
    }
}
