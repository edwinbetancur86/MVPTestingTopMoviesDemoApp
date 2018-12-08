package com.example.edwinb.topmoviesdemoapp;

import android.Manifest;
import android.graphics.drawable.ColorDrawable;
import android.support.test.espresso.IdlingPolicies;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.content.ContextCompat;
import android.text.format.DateUtils;
import android.view.View;
import com.example.edwinb.topmoviesdemoapp.topmovies.TopMoviesActivity;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.concurrent.TimeUnit;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**********************************************
 * A test suite example for running regular activity
 * tests.
 */
@RunWith(AndroidJUnit4.class)
public class TopMoviesActivityTest {

    private final String BOOK_TITLE_TO_TEST = "The Godfather";

    // The activity to test on or to start off with
    @Rule
    public ActivityTestRule<TopMoviesActivity> mActivityTestRule = new ActivityTestRule<>(TopMoviesActivity.class);

    // To automatically grant specific permissions that the device may use (For testing purposes)
    @Rule
    public GrantPermissionRule mGrantPermissionRule = GrantPermissionRule.grant(Manifest.permission.CAMERA);

    // For waiting on Asynchronous calls (Network calls, background work etc.)
    private IdlingResource idlingResource;

    @BeforeClass
    public static void setupClass() {
    }

    @Before
    public void setup() {
        IdlingPolicies.setMasterPolicyTimeout(60, TimeUnit.SECONDS);
        IdlingPolicies.setIdlingResourceTimeout(26, TimeUnit.SECONDS);
    }

    @AfterClass
    public static void tearDownClass() {

    }

    @Test
    public void testIfRecyclerViewIsShowingAndIsClickable() {
        testIfRecyclerViewIsShowingAndIsClickable(DateUtils.SECOND_IN_MILLIS * 3);
    }

    @Test
    public void recyclerViewShouldShowAnItemListWithTextTheGodfather() {
        recyclerViewShouldShowAnItemListWithTextTheGodfather(DateUtils.SECOND_IN_MILLIS * 3);
    }

    @Test
    public void recyclerViewShouldScrollToTheNextPagination() {
        recyclerViewShouldScrollToTheNextPagination(DateUtils.SECOND_IN_MILLIS * 3);
    }

    @Test
    public void buttonShouldBeClickable() {
        buttonShouldBeClickable(DateUtils.SECOND_IN_MILLIS * 3);
    }

    private void testIfRecyclerViewIsShowingAndIsClickable(long waitingTime) {

        // Make sure Espresso does not time out
        IdlingPolicies.setMasterPolicyTimeout(waitingTime * 2, TimeUnit.MILLISECONDS);
        IdlingPolicies.setIdlingResourceTimeout(waitingTime * 2, TimeUnit.MILLISECONDS);

        idlingResource = new ElapsedTimeIdlingResource(waitingTime);
        IdlingRegistry.getInstance().register(idlingResource);

        // Stop and verify
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()));
        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));

        // Clean up
        IdlingRegistry.getInstance().unregister(idlingResource);
    }

    private void recyclerViewShouldShowAnItemListWithTextTheGodfather(long waitingTime) {

        // Make sure Espresso does not time out
        IdlingPolicies.setMasterPolicyTimeout(waitingTime * 2, TimeUnit.MILLISECONDS);
        IdlingPolicies.setIdlingResourceTimeout(waitingTime * 2, TimeUnit.MILLISECONDS);

        idlingResource = new ElapsedTimeIdlingResource(waitingTime);
        IdlingRegistry.getInstance().register(idlingResource);

        onView(withRecyclerView(R.id.recycler_view).atPosition(2)).check(matches(hasDescendant(withText(BOOK_TITLE_TO_TEST))));

        // Clean up
        IdlingRegistry.getInstance().unregister(idlingResource);
    }

    private void recyclerViewShouldScrollToTheNextPagination(long waitingTime) {
        // Make sure Espresso does not time out
        IdlingPolicies.setMasterPolicyTimeout(waitingTime * 2, TimeUnit.MILLISECONDS);
        IdlingPolicies.setIdlingResourceTimeout(waitingTime * 2, TimeUnit.MILLISECONDS);

        idlingResource = new ElapsedTimeIdlingResource(waitingTime);
        IdlingRegistry.getInstance().register(idlingResource);

        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.scrollToPosition(19));

        IdlingRegistry.getInstance().unregister(idlingResource);
    }

    public void buttonShouldBeClickable(long waitingTime) {
        // Make sure Espresso does not time out
        IdlingPolicies.setMasterPolicyTimeout(waitingTime * 2, TimeUnit.MILLISECONDS);
        IdlingPolicies.setIdlingResourceTimeout(waitingTime * 2, TimeUnit.MILLISECONDS);

        idlingResource = new ElapsedTimeIdlingResource(waitingTime);
        IdlingRegistry.getInstance().register(idlingResource);

        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, clickChildViewWithId(R.id.button)));

        IdlingRegistry.getInstance().unregister(idlingResource);
    }


    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }

    /*******************************************
     * This is a modifiable custom method that allow
     * you to perform a child view action within
     * a parent view such as a recyclerview.
     * @param id the id of the view view within the recyclerview
     * @return A ViewAction
     */
    public ViewAction clickChildViewWithId(final int id) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return null;
            }

            @Override
            public String getDescription() {
                return "Click on a child view with specified id.";
            }

            @Override
            public void perform(UiController uiController, View view) {
                View v = view.findViewById(id);
                v.performClick();
            }
        };
    }

    /*************************************************
     * Bounded Matcher method example to test against a view.
     ************************************************/
    private Matcher<? super View> withToolbarBackGroundColor() {
        return new BoundedMatcher<View, View>(View.class) {
            @Override
            public boolean matchesSafely(View view) {
                // Get the toolbar background as a ColorDrawable
                final ColorDrawable buttonColor = (ColorDrawable) view.getBackground();

                // Match the toolbar background to the apps primary color
                return ContextCompat
                        .getColor(mActivityTestRule.getActivity(), R.color.colorPrimary) ==
                        buttonColor.getColor();
            }

            @Override
            public void describeTo(Description description) {
                // do nothing, we could technically add some additional text to the description
                // here if wanted
            }
        };
    }
}





