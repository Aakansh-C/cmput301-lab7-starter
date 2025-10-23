package com.example.androiduitesting;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertTrue;

import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest

public class ShowActivityTest {

    @Test
    public void testActivityCorrectlySwitched() {
        // Create an intent with city name
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), ShowActivity.class);
        intent.putExtra("CITY_NAME", "Toronto");

        // Launch the activity
        ActivityScenario<ShowActivity> scenario = ActivityScenario.launch(intent);

        // Verify that ShowActivity is displayed
        scenario.onActivity(activity -> {
            assertTrue("ShowActivity should be running", !activity.isFinishing());
        });

        // Verify that the city name TextView is displayed
        onView(withId(R.id.cityNameTextView)).check(matches(isDisplayed()));

        // Verify that the back button is displayed
        onView(withId(R.id.backButton)).check(matches(isDisplayed()));

        scenario.close();
    }

    @Test
    public void testCityNameConsistent_Toronto() {
        // Test with Toronto
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), ShowActivity.class);
        intent.putExtra("CITY_NAME", "Toronto");

        ActivityScenario<ShowActivity> scenario = ActivityScenario.launch(intent);

        // Check if the TextView displays "Toronto"
        onView(withId(R.id.cityNameTextView)).check(matches(withText("Toronto")));

        scenario.close();
    }

    @Test
    public void testCityNameConsistent_Vancouver() {
        // Test with Vancouver
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), ShowActivity.class);
        intent.putExtra("CITY_NAME", "Vancouver");

        ActivityScenario<ShowActivity> scenario = ActivityScenario.launch(intent);

        // Check if the TextView displays "Vancouver"
        onView(withId(R.id.cityNameTextView)).check(matches(withText("Vancouver")));

        scenario.close();
    }

    @Test
    public void testCityNameConsistent_Montreal() {
        // Test with Montreal
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), ShowActivity.class);
        intent.putExtra("CITY_NAME", "Montreal");

        ActivityScenario<ShowActivity> scenario = ActivityScenario.launch(intent);

        // Check if the TextView displays "Montreal"
        onView(withId(R.id.cityNameTextView)).check(matches(withText("Montreal")));

        scenario.close();
    }

    @Test
    public void testBackButton() {
        // Create an intent with city name
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), ShowActivity.class);
        intent.putExtra("CITY_NAME", "Calgary");

        ActivityScenario<ShowActivity> scenario = ActivityScenario.launch(intent);

        // Click the back button
        onView(withId(R.id.backButton)).perform(click());

        // Verify that the activity is finishing after back button click
        scenario.onActivity(activity -> {
            assertTrue("Activity should be finishing after back button click",
                    activity.isFinishing());
        });

        scenario.close();
    }

    @Test
    public void testBackButtonDisplayed() {
        // Create an intent with city name
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), ShowActivity.class);
        intent.putExtra("CITY_NAME", "Edmonton");

        ActivityScenario<ShowActivity> scenario = ActivityScenario.launch(intent);

        // Verify back button is displayed
        onView(withId(R.id.backButton)).check(matches(isDisplayed()));

        scenario.close();
    }
}