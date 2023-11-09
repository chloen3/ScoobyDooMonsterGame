package com.example.worldofscoobydoo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.widget.ImageView;

import com.example.worldofscoobydoo.model.EnemyGiant;
import com.example.worldofscoobydoo.model.EnemyTank;
import com.example.worldofscoobydoo.model.Ghost;
import com.example.worldofscoobydoo.model.Mummy;
import com.example.worldofscoobydoo.view.InitialConfiguration;
import com.example.worldofscoobydoo.model.Enemy;
import com.example.worldofscoobydoo.viewModel.MovementObservable;

import org.junit.Test;

public class EnemyTest {

    @Test
    public void enemyIsBoss() {
        String type = "Boss";
        assertTrue(Enemy.enemyIsValid(type));
    }
    @Test
    public void enemyIsBasic() {
        String type = "Basic";
        assertTrue(Enemy.enemyIsValid(type));
    }
    @Test
    public void enemyIsTank() {
        String type = "Tank";
        assertTrue(Enemy.enemyIsValid(type));
    }
    @Test
    public void enemyIsGiant() {
        String type = "Giant";
        assertTrue(Enemy.enemyIsValid(type));
    }

    @Test
    public void testGhostMovement() {
        Ghost ghost = new Ghost();
        ghost.testGhostMovement(1, ghost);
        assertEquals(ghost.getX(), 100);
        assertEquals(ghost.getY(), 0);
    }
    @Test
    public void testMummyMovement() {
        Mummy mummy = new Mummy();
        mummy.testMummyMovement(4, mummy);
        assertEquals(mummy.getX(), 100);
        assertEquals(mummy.getY(), 100);
    }
    @Test
    public void testEnemyGiantMovement() {
        EnemyGiant giant = new EnemyGiant();
        giant.testGiantMovement(2, giant);
        assertEquals(giant.getX(), 100);
        assertEquals(giant.getY(), 0);
    }
    @Test
    public void testEnemyTankMovement() {
        EnemyTank tank = new EnemyTank();
        tank.testTankMovement(3, tank);
        assertEquals(tank.getX(), 100);
        assertEquals(tank.getY(), 0);
    }

}
