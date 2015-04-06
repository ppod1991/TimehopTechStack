package com.peapod.timehoptechstack;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;


/**
 * Main Activity that fetches & displays top new Android Repo using the Github API
 */
public class GithubStatsActivity extends BaseActivity {

    private GithubStatsFragment fragment;
    public static final String TAG_GITHUB_STATS_FRAGMENT = "GithubStatsFragmentTag";
    private static final String TAG = "GithubStatsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github_stats);

        FragmentManager fragmentManager = getFragmentManager();
        fragment = (GithubStatsFragment) fragmentManager.findFragmentByTag(TAG_GITHUB_STATS_FRAGMENT);

        if (fragment == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new GithubStatsFragment(),TAG_GITHUB_STATS_FRAGMENT)
                    .commit();
        }
    }

    /**
     * Fragment that loads and stores results of Github API call
     */
    public static class GithubStatsFragment extends BaseFragment {

        @Inject GithubService githubService; //Injected Github API Service

        private Subscription githubSubscription;
        private String topRepoDescription = null;
        CountDownLatch latch = new CountDownLatch(1); //Countdown Latch to Alert Test Code that API call is complete

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setRetainInstance(true);

            Map<String,String> options = new HashMap<>();
            options.put("sort","stars");
            options.put("order","desc");
            options.put("q","android+language:java+created:>" + dateNDaysAgo(7));



            githubSubscription = githubService.getTopNewAndroidRepos(options)
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Action1<GithubResults>() {
                                            @Override
                                            public void call(GithubResults githubResults) {

                                                topRepoDescription = githubResults.items.get(0).toString();
                                                TextView repoTextView = (TextView) GithubStatsFragment.this.getView().findViewById(R.id.topRepoTextView);
                                                repoTextView.setText(topRepoDescription);

                                                latch.countDown(); //Notifies test code to proceed with test
                                            }
                                        }, new Action1<Throwable>() {
                                            @Override
                                            public void call(Throwable throwable) {
                                                Log.e(TAG, throwable.getLocalizedMessage());

                                                Toast toast = Toast.makeText(getActivity(), "Oh no! There was a problem: " + throwable.getLocalizedMessage() , Toast.LENGTH_LONG);
                                                toast.show();

                                                TextView repoTextView = (TextView) GithubStatsFragment.this.getView().findViewById(R.id.topRepoTextView);
                                                repoTextView.setText("Error--please try again!");
                                                latch.countDown(); //Notifies test code to proceed with test
                                            }
                                        });
        }

        /**
         * Computes the string representation of the date n days ago (e.g. returns 2015-07-05 if today is 2015-07-12 and n=7)
         * @param n Number of days in past to find date for
         * @return String representation of the past date
         */
        public static String dateNDaysAgo(int n) {
            java.text.DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1 * n);
            return dateFormat.format(cal.getTime());
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_github_stats, container, false);

            if (topRepoDescription != null) { //Provide stored result if already fetched
                TextView repoTextView = (TextView) rootView.findViewById(R.id.topRepoTextView);
                repoTextView.setText(topRepoDescription);
            }

            return rootView;
        }


        @Override
        public void onDestroy() {
            super.onDestroy();
            githubSubscription.unsubscribe();  //Unsubscribe from observable in case fragment is destroyed
        }
    }
}
