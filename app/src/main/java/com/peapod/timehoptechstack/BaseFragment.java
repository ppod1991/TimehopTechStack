package com.peapod.timehoptechstack;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

/**
 * Base fragment to extend from. Injects itself into application object graph.
 */
public class BaseFragment extends Fragment {

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        TechStackApp app = (TechStackApp) activity.getApplication();
        app.getObjectGraph().inject(this);
    }
}
