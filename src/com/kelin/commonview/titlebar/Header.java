package com.kelin.commonview.titlebar;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelin.commonview.R;

public final class Header {
    View mRoot;
    ImageView mLogo;
    TextView mTitle;
    LinearLayout mPanel;
    ImageView mProgress;
    ImageButton mSettingButton;
    ImageButton mExtendButton;

    public interface OnBackStackListener {
        public void onBackStack();
    }

    public Header(Activity activity, int titleId) {
        this(activity.getWindow().getDecorView(), titleId);
    }

    public Header(View view, int titleId) {
        mRoot = view.findViewById(titleId);
        mLogo = (ImageView) mRoot.findViewById(R.id.logo);
        mTitle = (TextView) mRoot.findViewById(R.id.title);
        mPanel = (LinearLayout) mRoot.findViewById(R.id.title_panel);
        mProgress = (ImageView) mRoot.findViewById(R.id.title_progress);
        mSettingButton = (ImageButton) mRoot.findViewById(R.id.settings);
        mExtendButton = (ImageButton) mRoot.findViewById(R.id.btn_extend);
    }

    public void setTitle(CharSequence title) {
        mTitle.setText(title);
    }

    public void setTitle(int titleRes) {
        mTitle.setText(titleRes);
    }

    public void setLogo(int logoResId) {
        if (logoResId > 0) mLogo.setImageResource(logoResId);
    }

    public void setBackListener(final OnBackStackListener listener) {
        if (listener != null) {
            mLogo.setBackgroundResource(R.drawable.title_left_bg);
            mLogo.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            listener.onBackStack();
                        }
                    });
            mLogo.setFocusable(true);
        }
    }

    public void setTitleLeftAlign() {
        mPanel.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
    }

    public void setExtendButton(int resid, OnClickListener listener) {
        if (resid > 0) {
            mExtendButton.setImageResource(resid);
            mExtendButton.setOnClickListener(listener);
            mExtendButton.setVisibility(View.VISIBLE);
        } else {
            mExtendButton.setVisibility(View.GONE);
        }
    }

    public void setSettingButton(int resid, OnClickListener listener) {
        if (resid > 0) {
            mSettingButton.setImageResource(resid);
        }
        if (listener != null) {
            mSettingButton.setOnClickListener(listener);
            mSettingButton.setVisibility(View.VISIBLE);
        } else {
            mSettingButton.setVisibility(View.GONE);
        }
    }

    public void startProgress() {
        mProgress.setVisibility(View.VISIBLE);
        mProgress.startAnimation(AnimationUtils.loadAnimation(mProgress.getContext(),
                R.anim.dx_score_rotate));
    }

    public void stopProgress() {
        mProgress.clearAnimation();
        mProgress.setVisibility(View.GONE);
    }
}
