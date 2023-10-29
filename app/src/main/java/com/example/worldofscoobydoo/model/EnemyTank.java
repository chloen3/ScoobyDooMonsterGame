package com.example.worldofscoobydoo.model;

import com.example.worldofscoobydoo.R;
import com.example.worldofscoobydoo.viewModel.MovementMedium;
import android.widget.ImageView;
import com.example.worldofscoobydoo.R;

import java.util.ArrayList;
import java.util.List;

public class EnemyTank extends Enemy {

    public EnemyTank() {
        type = "Tank";
        //image =
        mvStrategy = new MovementMedium(movementObservable);
    }

    void createMultipleEnemies(int numEnemies) {
        List<Enemy> enemies = new ArrayList<>();
        for (int i = 1; i <= numEnemies; i++) {
            enemies.add(new EnemyTank());
        }
    }
}
