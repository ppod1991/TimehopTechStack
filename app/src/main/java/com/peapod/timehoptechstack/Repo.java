package com.peapod.timehoptechstack;

/**
 * Retrofit model containing information about an individual Github repository
 */
public class Repo {

    String full_name;
    String description;
    String url;

    public Repo(String myName, String myDescription, String myURL) {
        full_name = myName;
        description = myDescription;
        url = myURL;
    }

    public String toString() {
        return full_name + ": " + description;
    }

}
