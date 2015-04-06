package com.peapod.timehoptechstack;

import android.util.Log;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;

/**
 * Provides the dependencies needed for Network communication.
 */

@Module(injects = {GithubStatsActivity.class, GithubStatsActivity.GithubStatsFragment.class})
public class NetworkModule {

    private final String mEndpoint; //The endpoint to be reached via network calls

    public NetworkModule(String endpoint) {
        mEndpoint = endpoint;
    }

    @Provides @Singleton
    public RestAdapter provideRestAdapter() {
        return new RestAdapter.Builder().setEndpoint(mEndpoint).build();
    }

    @Provides @Singleton
    public GithubService provideGithubService(RestAdapter restAdapter) {
        return restAdapter.create(GithubService.class);
    }

}
