package com.example.worldofscoobydoo;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.worldofscoobydoo.view.MainActivity;
import com.example.worldofscoobydoo.view.HowToPlay;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Rule
    public IntentsTestRule<MainActivity> intentsTestRule = new IntentsTestRule<>(MainActivity.class);

    //test if start button takes us to how to play screen
    @Test
    public void testStartButtonLaunchesHowToPlay() {
        onView(withId(R.id.startButton)).perform(click());
        intended(hasComponent(HowToPlay.class.getName()));
    }
}

