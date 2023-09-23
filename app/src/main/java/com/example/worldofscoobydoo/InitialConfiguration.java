package com.example.worldofscoobydoo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class InitialConfiguration extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.initial_configuration);
        Button startBtn = findViewById(R.id.button);

//        // Set difficulty based on difficulty checked
//        startBtn.setOnClickListener(v -> {
//            RadioGroup difficultyRadioGroup = findViewById(R.id.difficultyRadioGroup);
//            double difficulty = 1;
//
//            switch (difficultyRadioGroup.getCheckedRadioButtonId()) {
//                case R.id.radioButton:
//                    difficulty = 0.5;
//                    break;
//                case R.id.radioButton2:
//                    difficulty = 0.75;
//                    break;
//                case R.id.radioButton3:
//                    difficulty = 1;
//                    break;
//                default:
//                    difficulty = 0.5;
//                    break;
//            }
//            Intent game = new Intent(MainActivity.this, GameActivity.class);
//            game.putExtra("difficulty", difficulty);
//            startActivity(game);
//            finish();
//        });
    }
}
