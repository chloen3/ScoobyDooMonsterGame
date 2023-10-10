package com.example.worldofscoobydoo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Layout;
import android.text.StaticLayout;
import android.widget.Button;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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