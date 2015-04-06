package com.peapod.timehoptechstack;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.test.ActivityUnitTestCase;
import android.util.Log;
import android.widget.TextView;


import javax.inject.Inject;


/**
 * Instrumentation Tests for GithubStatsActivity
 */
public class GithubStatsActivityTestCase extends ActivityUnitTestCase<GithubStatsActivity> {

    private GithubStatsActivity.GithubStatsFragment fragment;
    private GithubStatsActivity activity;

    public GithubStatsActivityTestCase() {
        super(GithubStatsActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        //Provides a mock application that replaces the production object graph with test object graph
        TechStackApp techStackApp = new MockTechStackApp();
        setApplication(techStackApp);

    }

    /**
     * Tests that the GithubStatsActivity uses the Mock Github Service and displays the dummy result on screen
     */
    public void testGithubService() {

        startActivity(new Intent(getInstrumentation().getTargetContext(),GithubStatsActivity.class), null, null);
        activity = getActivity();
        activity.getFragmentManager().executePendingTransactions();
        fragment = (GithubStatsActivity.GithubStatsFragment) activity.getFragmentManager().findFragmentByTag(GithubStatsActivity.TAG_GITHUB_STATS_FRAGMENT);

        final String testString = "square/picasso: A powerful image downloading and caching library for Android http://square.github.io/picasso/";

        try {
            fragment.latch.await(); //Wait for the Activity to complete the API fetch
            TextView githubStatsTextView = (TextView) fragment.getView().findViewById(R.id.topRepoTextView);

            assertEquals(testString,githubStatsTextView.getText().toString());
        } catch (InterruptedException e) {

            Log.e("Thread was interrupted",e.getLocalizedMessage());
            assertFalse(true);
        }
    }

    /**
     * Tests that the GithubStatsActivity makes exactly 1 API call, even on device rotation
     */
    public void testGithubServiceRotation() {

        startActivity(new Intent(getInstrumentation().getTargetContext(),GithubStatsActivity.class), null, null);

        MockGithubService.methodCallCount = 0; //Reset API call count

        activity = getActivity();
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //Set initial orientation
        activity.getFragmentManager().executePendingTransactions();
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); //Rotate orientation

        fragment = (GithubStatsActivity.GithubStatsFragment) activity.getFragmentManager().findFragmentByTag(GithubStatsActivity.TAG_GITHUB_STATS_FRAGMENT);

        try {
            fragment.latch.await(); //Wait for API call to complete
            assertEquals(1,MockGithubService.methodCallCount);
        } catch (InterruptedException e) {
            Log.e("Thread was interrupted",e.getLocalizedMessage());
            assertFalse(true);
        }
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }


}
