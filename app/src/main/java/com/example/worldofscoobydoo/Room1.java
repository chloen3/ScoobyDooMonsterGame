package com.example.worldofscoobydoo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Room1 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen1);

        Button nextButton1 = findViewById(R.id.nextButton1);
        nextButton1.setOnClickListener(v -> {
            Intent leaderboard = new Intent(getApplicationContext(), EndScreen.class);
            startActivity(leaderboard);
        });

        Button restartButton = findViewById(R.id.playAgain);
        restartButton.setOnClickListener(v -> {
            Intent restart = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(restart);
        });
    }
}
