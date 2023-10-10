package com.example.worldofscoobydoo;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.worldofscoobydoo.view.InitialConfiguration;

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
//    @Test
//    public void nameIsEmpty() {
//        String name = " ";
//        assertFalse(InitialConfiguration.nameIsValid(name));
//    }
//    @Test
//    public void nameIsValid() {
//        String name = "Ronit";
//        assertTrue(InitialConfiguration.nameIsValid(name));
//    }
}