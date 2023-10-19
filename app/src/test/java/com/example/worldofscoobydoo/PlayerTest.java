package com.example.worldofscoobydoo;

import org.junit.Test;
import static org.junit.Assert.*;

import com.example.worldofscoobydoo.model.Player;
import com.example.worldofscoobydoo.viewModel.GameActivity;
import com.example.worldofscoobydoo.viewModel.InitialConfiguration;

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

}