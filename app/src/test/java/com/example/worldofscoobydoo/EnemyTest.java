package com.example.worldofscoobydoo;

import static org.junit.Assert.assertFalse;

import com.example.worldofscoobydoo.view.InitialConfiguration;

import org.junit.Test;

public class EnemyTest {

    @Test
    public void enemyIsBoss() {
        String type = "Boss";
        assertFalse(InitialConfiguration.nameIsValid(type));
    }
    @Test
    public void enemyIsBasic() {
        String type = "Basic";
        assertFalse(InitialConfiguration.nameIsValid(type));
    }
    @Test
    public void enemyIsTank() {
        String type = "Tank";
        assertFalse(InitialConfiguration.nameIsValid(type));
    }
    @Test
    public void enemyIsGiant() {
        String type = "Giant";
        assertFalse(InitialConfiguration.nameIsValid(type));
    }

}
