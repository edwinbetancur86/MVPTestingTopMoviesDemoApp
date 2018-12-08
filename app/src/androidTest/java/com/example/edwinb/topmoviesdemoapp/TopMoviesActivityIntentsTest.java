package com.example.edwinb.topmoviesdemoapp;

import android.Manifest;
import android.graphics.drawable.ColorDrawable;
import android.provider.MediaStore;
import android.support.test.espresso.IdlingPolicies;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.BoundedMatcher;
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
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/************************************************
 * A test suite for testing intent data such as
 * parsed Uri's, package names and intent actions.
 ************************************************/
@RunWith(AndroidJUnit4.class)
public class TopMoviesActivityIntentsTest {

    @Rule
    public IntentsTestRule<TopMoviesActivity> mActivityIntentRule = new IntentsTestRule<>(TopMoviesActivity.class);

    // To automatically grant specific runtime permissions that the device may use (For testing purposes)
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
    public void shouldOpenCameraWhenTakePhotoButtonIsClicked() {
        shouldOpenCameraWhenTakePhotoButtonIsClicked(DateUtils.SECOND_IN_MILLIS * 3);
    }

    public void shouldOpenCameraWhenTakePhotoButtonIsClicked(long waitingTime) {
        // Make sure Espresso does not time out
        IdlingPolicies.setMasterPolicyTimeout(waitingTime * 2, TimeUnit.MILLISECONDS);
        IdlingPolicies.setIdlingResourceTimeout(waitingTime * 2, TimeUnit.MILLISECONDS);

        idlingResource = new ElapsedTimeIdlingResource(waitingTime);
        IdlingRegistry.getInstance().register(idlingResource);

        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, clickChildViewWithId(R.id.captureFront)));

        /*intended(allOf(
                hasAction(MediaStore.ACTION_IMAGE_CAPTURE),
                hasData(INTENT_DATA_PHONE_NUMBER),
                toPackage(PACKAGE_ANDROID_DIALER)));*/

        intended(hasAction(MediaStore.ACTION_IMAGE_CAPTURE));

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
                        .getColor(mActivityIntentRule.getActivity(), R.color.colorPrimary) ==
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
