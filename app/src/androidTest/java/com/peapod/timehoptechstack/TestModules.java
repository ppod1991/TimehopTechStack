package com.peapod.timehoptechstack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Lists the test-specific dependencies in addition to main Application dependencies
 */
public class TestModules {

    static Object[] list() {

        List<Object> testModules = new ArrayList<Object>();
        testModules.addAll(Arrays.asList(Modules.list()));
        testModules.add(new TestNetworkModule());
        return testModules.toArray();
    }
}
