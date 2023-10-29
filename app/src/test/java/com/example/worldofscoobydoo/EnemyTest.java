package com.example.worldofscoobydoo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.worldofscoobydoo.view.InitialConfiguration;
import com.example.worldofscoobydoo.model.Enemy;

import org.junit.Test;

public class EnemyTest {

    @Test
    public void enemyIsBoss() {
        String type = "Boss";
        assertTrue(Enemy.EnemyIsValid(type));
    }
    @Test
    public void enemyIsBasic() {
        String type = "Basic";
        assertTrue(Enemy.EnemyIsValid(type));
    }
    @Test
    public void enemyIsTank() {
        String type = "Tank";
        assertTrue(Enemy.EnemyIsValid(type));
    }
    @Test
    public void enemyIsGiant() {
        String type = "Giant";
        assertTrue(Enemy.EnemyIsValid(type));
    }

}
