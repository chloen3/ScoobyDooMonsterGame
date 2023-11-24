package com.example.worldofscoobydoo;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.espresso.Espresso;
import com.example.worldofscoobydoo.view.HowToPlay;
import com.example.worldofscoobydoo.view.InitialConfiguration;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class HowToPlayTest {

    @Rule
    public IntentsTestRule<HowToPlay> intentsTestRule = new IntentsTestRule<>(HowToPlay.class);

    //Check if continue button correctly takes us to initial configuration screen
    @Test
    public void testContinueButtonLaunchesInitialConfiguration() {
        onView(withId(R.id.continue_button)).perform(click());
        intended(hasComponent(InitialConfiguration.class.getName()));
    }


}

