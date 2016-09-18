package com.nemanjaasuv1912.systemForCollaborativeLearning.ui.activity.base;

import android.view.View;
import android.widget.ProgressBar;

/**
 * Created by nemanjamarkicevic on 8/14/16.
 */
public class ProgressBarActivity extends BaseActivity {

    protected ProgressBar progressBar;

    protected void showProgressBar() {
        if (progressBar != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    protected void hideProgressBar() {
        if (progressBar != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });
        }
    }

    protected boolean progressBarVisible() {
        return progressBar != null && progressBar.getVisibility() == View.VISIBLE;
    }
}
