package com.peapod.timehoptechstack;

import android.app.Application;

import dagger.ObjectGraph;

/**
 * Override of Application class that contains the Application object graph and helper methods
 */
public class TechStackApp extends Application {
    protected ObjectGraph objectGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        createObjectGraph(getModules());
    }

    public Object[] getModules() {
        return Modules.list();
    }

    public ObjectGraph getObjectGraph() {
        return objectGraph;
    }

    public void inject(Object o) {
        objectGraph.inject(o);
    }

    /**
     * Creates and sets the Application's object graph using the passed in modules
     * @param modules
     */
    public void createObjectGraph(Object[] modules) {
        objectGraph  = ObjectGraph.create(modules);
    }

}
