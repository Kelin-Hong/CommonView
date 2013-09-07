package com.kelin.commonview.progressbar;

import android.app.Activity;
import android.os.Bundle;

import com.kelin.commonview.R;

public class DotLoadingProgressBarActivity extends Activity{
    private DotLoadingProgressBar mDotLoadingProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doting_progress_bar);
        mDotLoadingProgressBar=(DotLoadingProgressBar)findViewById(R.id.dot_progressbar);
        mDotLoadingProgressBar.displayLoadingAnimation(getString(R.string.dot_progress_loading_apps_info_des));
    }

}
