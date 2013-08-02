package com.kelin.commonview;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.view.View;

import com.example.commonview.R;

public abstract class BaseFragment extends Fragment {
    protected Activity mActivity = null;
    protected Resources mRes = null;
    protected View mMainView;

    private boolean mIsShowing = false;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
        mRes = activity.getResources();
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mActivity = null;
    }

    public boolean onBackPressed() {
        return false;
    }

    protected View findViewById(int resid) {
        return mMainView.findViewById(resid);
    }

    /**
     * onScrollOut was called when viewpager scroll out of this fragment
     */
    public void onScrollOut() {
        // You can do some code here
        mIsShowing = false;
        onDismiss();
    }

    /**
     * onScrollIn was called when viewpager scroll to this fragment
     */
    public void onScrollIn() {
        // You can do some code here
        mIsShowing = true;
        onShow();
    }

    /**     * when the fragment is current fragment and showing
     */
    public void onShow() {
        // You can do some code here
    }

    /**
     * when the fragment is leave or pause
     */
    public void onDismiss() {
        // You can do some code here
    }

    /**
     * called when main activity's onUserLeaveHint
     */
    public void onUserLeaveHint() {
        // You can do some thing here
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mIsShowing) {
            onDismiss();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mIsShowing) {
            onShow();
        }
    }

    /**
     * onScrolled when viewpager scrolled
     */
    public void onScrolled() {
        // You can do some thing here
    }

    public void startActivityForResultWithAnim(Intent intent, int regcode) {
       // intent.putExtra(SingleActivity.EXTRA_HAS_ANIM, true);
      //  startActivityForResult(intent, regcode);
        mActivity.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
    }

    public void startActivityWithAnim(Intent intent) {
     //   intent.putExtra(SingleActivity.EXTRA_HAS_ANIM, true);
       // mActivity.startActivity(intent);
        mActivity.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
    }
}
