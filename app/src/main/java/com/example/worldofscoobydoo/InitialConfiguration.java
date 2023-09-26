package com.example.worldofscoobydoo;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class InitialConfiguration extends AppCompatActivity {
    String name;
    String sprite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.initial_configuration);

        Button startBtn = findViewById(R.id.button);
        EditText nameInput = findViewById(R.id.editTextText);

        startBtn.setOnClickListener(v -> {
            name = nameInput.getText().toString();
            boolean setDifficulty = false;
            boolean setCharacter = false;
            double difficulty = 1;


            RadioGroup spriteRadioGroup = findViewById(R.id.spriteRadio);
            RadioGroup difficultyRadioGroup = findViewById(R.id.difficultyRadioGroup);
            switch (difficultyRadioGroup.getCheckedRadioButtonId()) {
                case R.id.radioEasy:
                    difficulty = 1;
                    setDifficulty = true;
                    break;
                case R.id.radioMedium:
                    difficulty = 0.75;
                    setDifficulty = true;
                    break;
                case R.id.radioHard:
                    difficulty = 0.5;
                    setDifficulty = true;
                    break;
            }


            switch (spriteRadioGroup.getCheckedRadioButtonId()) {
                case R.id.Scooby:
                    sprite = "scooby";
                    setCharacter = true;
                    break;
                case R.id.Daphne:
                    sprite = "daphne";
                    setCharacter = true;
                    break;
                case R.id.Fred:
                    sprite = "fred";
                    setCharacter = true;
                    break;
            }


            if (name == null || name.trim().isEmpty()) {
                nameInput.setError("Name cannot be empty or null or white space");
                //Set difficulty based on difficulty checked
            } else if (!setDifficulty) {
                nameInput.setError("Choose a dfficulty");
            } else if (!setCharacter) {
                nameInput.setError("Choose a character");
            }

            else {
                Intent game = new Intent(InitialConfiguration.this, GameActivity.class);
                game.putExtra("difficulty", difficulty);
                game.putExtra("name", name);
                game.putExtra("sprite", sprite);
                startActivity(game);
                finish();
            }
       });
    }
}
