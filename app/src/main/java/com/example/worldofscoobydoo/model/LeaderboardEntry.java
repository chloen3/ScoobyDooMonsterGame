package com.example.worldofscoobydoo.model;

import java.util.UUID;

public class LeaderboardEntry {
    private String playerName;
    private int score;
    private String date;

    public LeaderboardEntry(String playerName, int score, String date) {
        this.playerName = playerName;
        this.score = score;
        this.date = date;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }

    public String getDate() {
        return date;
    }
}
