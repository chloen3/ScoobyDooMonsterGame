package com.example.worldofscoobydoo.model;

import com.example.worldofscoobydoo.viewModel.MovementSlow;
import java.util.ArrayList;
import java.util.List;

public class EnemyBasic extends Enemy {

    public EnemyBasic() {
        type = "Basic";
        //image =
        //or can implement it using type in the activities
        mvStrategy = new MovementSlow(movementObservable);
    }

    void createMultipleEnemies(int numEnemies) {
        List<Enemy> enemies = new ArrayList<>();
        for (int i = 1; i <= numEnemies; i++) {
            enemies.add(new EnemyBasic());
        }
    }

}
