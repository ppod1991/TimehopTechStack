package com.peapod.timehoptechstack;

import android.util.Log;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.MockRestAdapter;
import retrofit.RestAdapter;

/**
 * Dagger Module that provides Test specific dependencies that override some production Application dependencies for Testing purposes
 */
@Module(injects = {GithubStatsActivity.class, GithubStatsActivity.GithubStatsFragment.class,GithubStatsActivityTestCase.class}, overrides = true)
public class TestNetworkModule {

    /**
     * Provides a mock Retrofit rest adapter that simulates network conditions for testing purposes
     * @return THe mock Rest Adapter
     */
    @Provides @Singleton
    public MockRestAdapter provideMockRestAdapter() {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("mock://").build();
        MockRestAdapter mockRestAdapter = MockRestAdapter.from(restAdapter);
        mockRestAdapter.setDelay(2000);
        return mockRestAdapter;
    }

    /**
     * Provides a Mock Github Service that uses a MockRestAdapter for testing purposes
     * @param mockRestAdapter A Mock Rest Adapter
     * @return The mock Github Service
     */
    @Provides @Singleton
    public GithubService provideGithubService(MockRestAdapter mockRestAdapter) {
        return mockRestAdapter.create(GithubService.class, new MockGithubService());
    }

}
