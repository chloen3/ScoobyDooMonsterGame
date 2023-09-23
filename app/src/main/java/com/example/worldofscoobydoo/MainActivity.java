package com.example.worldofscoobydoo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(v -> {
            Intent game = new Intent(MainActivity.this, InitialConfiguration.class);
            startActivity(game);
            finish();
        });
        Button exitButton = findViewById(R.id.exitButton);
        startButton.setOnClickListener(v -> {
            finish();
            System.exit(0);
        });
    }
}