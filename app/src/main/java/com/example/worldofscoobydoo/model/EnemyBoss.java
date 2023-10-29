package com.example.worldofscoobydoo.model;

import com.example.worldofscoobydoo.viewModel.MovementFast;

import java.util.ArrayList;
import java.util.List;

public class EnemyBoss extends Enemy {

    

    public EnemyBoss() {
        type = "Boss";
        //image =
        mvStrategy = new MovementFast(movementObservable);
    }

    void createMultipleEnemies(int numEnemies) {
        List<Enemy> enemies = new ArrayList<>();
        for (int i = 1; i <= numEnemies; i++) {
            enemies.add(new EnemyBoss());
        }
    }
}
