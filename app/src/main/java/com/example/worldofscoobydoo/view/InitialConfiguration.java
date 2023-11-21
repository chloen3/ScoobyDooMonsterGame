package com.example.worldofscoobydoo.view;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.example.worldofscoobydoo.R;
import com.example.worldofscoobydoo.model.Player;
import com.example.worldofscoobydoo.viewModel.MovementFast;
import com.example.worldofscoobydoo.viewModel.MovementMedium;
import com.example.worldofscoobydoo.viewModel.MovementObservable;
import com.example.worldofscoobydoo.viewModel.MovementSlow;
import com.example.worldofscoobydoo.viewModel.MovementStrategy;

public class InitialConfiguration extends AppCompatActivity {
    private String name;
    private String sprite;
    private Player player;
    public static MediaPlayer mySong;
    private Button playButton;
    public void setName(String name) {
        this.name = name;
    }

    public void setSprite(String sprite) {
        this.sprite = sprite;
    }

    /** @noinspection checkstyle:Indentation*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  // starts it all
        requestWindowFeature(Window.FEATURE_NO_TITLE); // removes top bar title
        getSupportActionBar().hide(); // removes top bar
        setContentView(R.layout.initial_configuration); // sets to layout
        mySong = MediaPlayer.create(InitialConfiguration.this, R.raw.scooby);
        playButton = findViewById(R.id.playButton);


        Button startBtn = findViewById(R.id.button); // continue button
        EditText nameInput = findViewById(R.id.editTextText); // Name Input

        player = Player.getPlayer(); // gets Singleton instance

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start playing the music when the button is clicked
                if (mySong != null) {
                    mySong.start();
                }
            }
        });

        // Sets Difficulty
        startBtn.setOnClickListener(v -> {
            boolean setDifficulty = false;
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
            default:
            }

            // Set Sprite
            switch (spriteRadioGroup.getCheckedRadioButtonId()) {
            case R.id.ScoobyBtn:
                setSprite("scooby");
                break;
            case R.id.DaphneBtn:
                setSprite("daphne");
                break;
            case R.id.FredBtn:
                setSprite("fred");
                break;
            case R.id.VelmaBtn:
                setSprite("velma");
                break;
            case R.id.ShaggyBtn:
                setSprite("shaggy");
                break;
            default:
            }

            EditText input = findViewById(R.id.editTextText);
            String inputName = input.getText().toString();
            setName(inputName);

            if (!nameIsValid(name)) {
                nameInput.setError("Please enter a name");
            } else if (!setDifficulty) {
                nameInput.setError("Choose a difficulty.");
            } else if (!characterIsValid(sprite)) {
                nameInput.setError("Choose a character.");
            } else {
                Intent game = new Intent(InitialConfiguration.this, GameActivity.class);
                player.setDifficulty(difficulty);
                player.setName(inputName);
                player.setSprite(sprite);
                startActivity(game);
                finish();
            }
        });
    }
    public static boolean nameIsValid(String inputName) {
        return !(inputName == null || inputName.isEmpty() || (inputName.trim().length() == 0));
    }

    public static boolean difficultyIsValid(double difficulty) {
        return difficulty == 1 || difficulty == 0.75 || difficulty == 0.5;
    }

    public static boolean characterIsValid(String sprite) {
        return sprite == "scooby" || sprite == "daphne" || sprite == "fred" || sprite == "velma"
                || sprite == "shaggy";
    }

    public static boolean slowSpeedIsValid(MovementStrategy mv, double difficulty) {
        MovementObservable test = new MovementObservable();
        MovementSlow mvs = new MovementSlow(test);
        return mvs.toString().equals(mv.toString()) && difficulty == 0.5;
    }
    public static boolean mediumSpeedIsValid(MovementStrategy mv, double difficulty) {
        MovementObservable test = new MovementObservable();
        MovementMedium mvm = new MovementMedium(test);
        return mvm.toString().equals(mv.toString()) && difficulty == 0.75;
    }
    public static boolean fastSpeedIsValid(MovementStrategy mv, double difficulty) {
        MovementObservable test = new MovementObservable();
        MovementFast mvm = new MovementFast(test);
        return mvm.toString().equals(mv.toString()) && difficulty == 1.0;
    }
}
