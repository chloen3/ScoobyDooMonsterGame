package com.example.worldofscoobydoo.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;

import com.example.worldofscoobydoo.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // removes top bar title
        getSupportActionBar().hide(); // removes top bar
        setContentView(R.layout.activity_main);

        Button startButton1 = findViewById(R.id.startButton);
        startButton1.setOnClickListener(v -> {
            Intent game = new Intent(MainActivity.this, InitialConfiguration.class);
            startActivity(game);
            finish();
        });

        Button exitButton = findViewById(R.id.exitButton);
        exitButton.setOnClickListener(x -> {
            this.finishAffinity();
        });
    }
}