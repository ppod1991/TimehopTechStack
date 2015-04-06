package com.peapod.timehoptechstack;

import java.util.ArrayList;

/**
 * Retrofit model to hold result of Github Repo API call
 */
public class GithubResults {

    ArrayList<Repo> items;
    Integer total_count;

    public GithubResults(ArrayList<Repo> myItems) {
        items = myItems;
        total_count = items.size();
    }

}
