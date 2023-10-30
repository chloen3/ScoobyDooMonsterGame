package com.example.worldofscoobydoo;

import org.junit.Test;
import static org.junit.Assert.*;

import com.badlogic.gdx.Game;
import com.example.worldofscoobydoo.model.Player;
import com.example.worldofscoobydoo.view.GameActivity;
import com.example.worldofscoobydoo.view.InitialConfiguration;
import com.example.worldofscoobydoo.viewModel.MovementFast;
import com.example.worldofscoobydoo.viewModel.MovementMedium;
import com.example.worldofscoobydoo.viewModel.MovementObservable;
import com.example.worldofscoobydoo.viewModel.MovementSlow;
import com.example.worldofscoobydoo.viewModel.MovementStrategy;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class PlayerTest {
    @Test
    public void nameIsBlank() {
        String name = "";
        assertFalse(InitialConfiguration.nameIsValid(name));
    }
    @Test
    public void nameIsNull() {
        String name = null;
        assertFalse(InitialConfiguration.nameIsValid(name));
    }
    @Test
    public void nameIsEmpty() {
        String name = " ";
        assertFalse(InitialConfiguration.nameIsValid(name));
    }
    @Test
    public void nameIsValid() {
        String name = "Ronit";
        assertTrue(InitialConfiguration.nameIsValid(name));
    }

    @Test
    public void characterIsFred() {
        String sprite = "fred";
        assertTrue(InitialConfiguration.characterIsValid(sprite));
    }

    @Test
    public void characterIsDaphne() {
        String sprite = "daphne";
        assertTrue(InitialConfiguration.characterIsValid(sprite));
    }

    @Test
    public void characterIsScooby() {
        String sprite = "scooby";
        assertTrue(InitialConfiguration.characterIsValid(sprite));
    }

    @Test
    public void characterIsntValid() {
        String sprite = "ronit";
        assertFalse(InitialConfiguration.characterIsValid(sprite));
    }

      @Test
      public void difficultyIsValidOne() {
          int difficulty = 1;
          assertTrue(InitialConfiguration.difficultyIsValid(difficulty));
      }

    @Test
    public void difficultyIsValidTwo() {
        double difficulty = 0.75;
        assertTrue(InitialConfiguration.difficultyIsValid(difficulty));
    }

    @Test
    public void difficultyIsValidThree() {
        double difficulty = 0.5;
        assertTrue(InitialConfiguration.difficultyIsValid(difficulty));
    }
    @Test
    public void difficultyIsNotValid() {
        double difficulty = 0.3;
        assertFalse(InitialConfiguration.difficultyIsValid(difficulty));
    }

    @Test
    public void healthIsSet() {
        Player player = Player.getPlayer();
        assertFalse(GameActivity.healthValid(player));
    }

    @Test
    public void slowSpeedIsValid() {
        double difficulty = 0.5;
        MovementObservable test = new MovementObservable();
        MovementStrategy mv = new MovementSlow(test);
        assertTrue(InitialConfiguration.slowSpeedIsValid(mv, difficulty));
    }
    @Test
    public void mediumSpeedIsValid() {
        double difficulty = 0.75;
        MovementObservable test = new MovementObservable();
        MovementStrategy mv = new MovementMedium(test);
        assertTrue(InitialConfiguration.mediumSpeedIsValid(mv, difficulty));
    }
    @Test
    public void fastSpeedIsValid() {
        double difficulty = 1.0;
        MovementObservable test = new MovementObservable();
        MovementStrategy mv = new MovementFast(test);
        assertTrue(InitialConfiguration.fastSpeedIsValid(mv, difficulty));
    }
    @Test
    public void speedIsInvalid() {
        double difficulty = 1.0;
        MovementObservable test = new MovementObservable();
        MovementStrategy mv = new MovementMedium(test);
        assertFalse(InitialConfiguration.fastSpeedIsValid(mv, difficulty));
    }

    @Test
    public void testPlayerMovementUp() {
        // Create an instance of your Player class
        Player player =  Player.getPlayer();

        player.moveUp();
        assertEquals("up", player.getCurrentDirection());
    }

    @Test
    public void testPlayerMovementDown() {
        // Create an instance of your Player class
        Player player =  Player.getPlayer();

        player.moveDown();
        assertEquals("down", player.getCurrentDirection());
    }

    @Test
    public void testPlayerMovementLeft() {
        // Create an instance of your Player class
        Player player =  Player.getPlayer();

        player.moveLeft();
        assertEquals("left", player.getCurrentDirection());
    }

    @Test
    public void testPlayerMovementRight() {
        // Create an instance of your Player class
        Player player =  Player.getPlayer();

        player.moveRight();
        assertEquals("right", player.getCurrentDirection());
    }

    @Test
    public void testObserverRunnable() {
        Player player = Player.getPlayer();
        assertFalse(player.isRunning());
    }

    @Test
    public void validDecrease() {
        assertFalse(GameActivity.enemyAttack());
    }
}