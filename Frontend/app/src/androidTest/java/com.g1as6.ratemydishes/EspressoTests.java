package com.g1as6.ratemydishes;

import static android.app.PendingIntent.getActivity;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class EspressoTests {
    @Rule
    public ActivityScenarioRule<Login> activityRule =
            new ActivityScenarioRule<>(Login.class);

    @Test
    public void goesToWelcomeScreen() {
        onView(withId(R.id.imageView)).check(matches(isDisplayed()));
    }

    @Test
    public void canGoToCafe() {
        onView(withId(R.id.toCafe)).perform(click());

        onView(withId(R.id.welcomeText4)).check(matches(isDisplayed()));
    }

    @Test
    public void canGoToDiningCenter() {
        onView(withId(R.id.toDiningCenters)).perform(click());

        onView(withId(R.id.welcomeText4)).check(matches(isDisplayed()));
    }

    @Test
    public void canGoToConvienence() {
        onView(withId(R.id.toCS)).perform(click());

        onView(withId(R.id.welcomeText4)).check(matches(isDisplayed()));
    }

    @Test
    public void canGoToFast() {
        onView(withId(R.id.toFastCasual)).perform(click());

        onView(withId(R.id.welcomeText4)).check(matches(isDisplayed()));
    }

    @Test
    public void canGoToGet() {
        onView(withId(R.id.toGetAndGo)).perform(click());

        onView(withId(R.id.welcomeText4)).check(matches(isDisplayed()));
    }

    @Test
    public void canGoBack() {
        onView(withId(R.id.toGetAndGo)).perform(click());
        onView(withId(R.id.backToDining)).perform(click());

        onView(withId(R.id.imageView)).check(matches(isDisplayed()));
    }
}
