package com.example.worldofscoobydoo.model;

import com.example.worldofscoobydoo.viewModel.MovementSlow;

import java.util.ArrayList;
import java.util.List;

public class EnemyGiant extends Enemy {



    public EnemyGiant() {
        type = "Giant";
        //image =
        mvStrategy = new MovementSlow(movementObservable);
    }

    void createMultipleEnemies(int numEnemies) {
        List<Enemy> enemies = new ArrayList<>();
        for (int i = 1; i <= numEnemies; i++) {
            enemies.add(new EnemyGiant());
        }
    }
}
