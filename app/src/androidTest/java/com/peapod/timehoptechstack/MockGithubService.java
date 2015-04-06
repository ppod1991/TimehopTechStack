package com.peapod.timehoptechstack;

import android.test.ActivityUnitTestCase;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import retrofit.http.QueryMap;
import rx.Observable;

/**
 * Implements the GithubService providing dummy values for testing
 */
public class MockGithubService implements GithubService {

    public static int methodCallCount = 0; //Records number of service calls for testing purposes

    @Override
    public Observable<GithubResults> getTopNewAndroidRepos(@QueryMap Map<String, String> queryMap) {

        methodCallCount++;

        //Create dummy result
        Repo mockRepo = new Repo("square/picasso",
                "A powerful image downloading and caching library for Android http://square.github.io/picasso/",
                "https://github.com/square/picasso");
        ArrayList<Repo> mockRepos = new ArrayList<Repo>();
        mockRepos.add(mockRepo);

        return Observable.just(new GithubResults(mockRepos));
    }

}
