package com.example.androiduitesting;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.junit.Assert.assertTrue;

import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ShowActivityTest {

    // Open main activity
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    // This checks if the show Activity loads up by its self
    @Test
    public void testShowActivityLoadsUp() {
        // Create an intent with city name
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), ShowActivity.class);
        intent.putExtra("CITY_NAME", "Toronto");

        // Launch the activity
        ActivityScenario<ShowActivity> scenario = ActivityScenario.launch(intent);

        // Verify activity successfully loads and is displayed
        scenario.onActivity(activity -> {
            assertTrue("ShowActivity should be running and loaded", !activity.isFinishing());
        });

        // Verify all UI elements are loaded
        onView(withId(R.id.cityNameTextView)).check(matches(isDisplayed()));
        onView(withId(R.id.backButton)).check(matches(isDisplayed()));

        scenario.close();
    }

    // Tests the navigation from main to show activity
    @Test
    public void testNavigationFromMainActivityToShowActivityAndBack() {

        // Add a city to the list
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(typeText("Toronto"), closeSoftKeyboard());
        onView(withId(R.id.button_confirm)).perform(click());

        // Click on the city in the list to navigate to ShowActivity
        onData(anything())
                .inAdapterView(withId(R.id.city_list))
                .atPosition(0)
                .perform(click());

        // Verify we're now in ShowActivity
        onView(withId(R.id.cityNameTextView))
                .check(matches(isDisplayed()))
                .check(matches(withText("Toronto")));
        onView(withId(R.id.backButton)).check(matches(isDisplayed()));

        // Click the back button
        onView(withId(R.id.backButton)).perform(click());

        // Verify we're back on MainActivity
        onView(withId(R.id.city_list)).check(matches(isDisplayed()));
        onView(withId(R.id.button_add)).check(matches(isDisplayed()));
        onView(withId(R.id.button_clear)).check(matches(isDisplayed()));
    }

    // The name passed from Main to Show activity through Intent is correct
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

    // Tests the back button functions
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

    // Tests the functionality in a realistic case, where there are multiple cities in the list, then a click on any item navigates
    @Test
    public void testMultipleCityNavigations() {
        // Add multiple cities
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(typeText("Vancouver"), closeSoftKeyboard());
        onView(withId(R.id.button_confirm)).perform(click());

        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(typeText("Montreal"), closeSoftKeyboard());
        onView(withId(R.id.button_confirm)).perform(click());

        // Navigate to first city
        onData(anything())
                .inAdapterView(withId(R.id.city_list))
                .atPosition(0)
                .perform(click());

        onView(withId(R.id.cityNameTextView)).check(matches(withText("Vancouver")));
        onView(withId(R.id.backButton)).perform(click());

        // Back on MainActivity, navigate to second city
        onData(anything())
                .inAdapterView(withId(R.id.city_list))
                .atPosition(1)
                .perform(click());

        onView(withId(R.id.cityNameTextView)).check(matches(withText("Montreal")));
        onView(withId(R.id.backButton)).perform(click());

        // Verify we're back on MainActivity
        onView(withId(R.id.city_list)).check(matches(isDisplayed()));
    }
}