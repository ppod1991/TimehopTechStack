package com.peapod.timehoptechstack;

import android.app.Activity;
import android.os.Bundle;

/**
 * Base activity to extend from. Injects itself into the main application object graph.
 */
public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TechStackApp app = (TechStackApp) getApplication();
        app.getObjectGraph().inject(this);
    }
}
