package com.example.worldofscoobydoo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class InitialConfiguration extends AppCompatActivity {
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.initial_configuration);

        Button startBtn = findViewById(R.id.button);
        EditText nameInput = findViewById(R.id.editTextText);
//         Set difficulty based on difficulty checked
        startBtn.setOnClickListener(v -> {
            name = nameInput.getText().toString();

            RadioGroup difficultyRadioGroup = findViewById(R.id.difficultyRadioGroup);
            double difficulty = 1;

            switch (difficultyRadioGroup.getCheckedRadioButtonId()) {
                case R.id.radioEasy:
                    difficulty = 0.5;
                    break;
                case R.id.radioMedium:
                    difficulty = 0.75;
                    break;
                case R.id.radioHard:
                    difficulty = 1;
                    break;
                default:
                    difficulty = 0.5;
                    break;
            }
//            Intent game = new Intent(InitialConfiguration.this, GameActivity.class);
//            game.putExtra("difficulty", difficulty);
//            game.putExtra("name", name);
//            startActivity(game);
//            finish();
       });
    }
}
