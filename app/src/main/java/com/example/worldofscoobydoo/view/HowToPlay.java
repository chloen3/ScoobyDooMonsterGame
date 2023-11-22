package com.example.worldofscoobydoo.view;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;

import com.example.worldofscoobydoo.R;

public class HowToPlay extends AppCompatActivity {

    private Button continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // removes top bar title
        getSupportActionBar().hide(); // removes top bar
        setContentView(R.layout.how_to_play);

        continueButton = findViewById(R.id.continue_button);
        continueButton.setOnClickListener(v -> {
            Intent game = new Intent(HowToPlay.this, InitialConfiguration.class);
            startActivity(game);
            finish();
        });


    }

}
