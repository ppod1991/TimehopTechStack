package com.peapod.timehoptechstack;


/**
 * Override of Application class to provide Test-specific dependencies
 */
public class MockTechStackApp extends TechStackApp {

    public MockTechStackApp() {
        onCreate();
    }


    @Override
    public Object[] getModules() {
        return TestModules.list();
    }
}
