package com.kelin.commonview.listview;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kelin.commonview.R;

public class PullToRefreshListView extends ListView {
    private int mTriggerRefreshHeight;

    private boolean mTracking = false;

    private float mStartY = 0;

    private int mCurOffsetY = 0;

    private boolean mIsRefreshing = false;

    private Animation mAnimRotate, mAnimRotateBack;

    private boolean mCanRefresh = false;

    private PullDownStateListener mRefreshListener;

    private View mPullHeader = null;

    private View mFirstHeader;

    private View mContainer = null;

    private ImageView mBackgroundImageView = null;

    private int mMaxHeaderHeight = Integer.MAX_VALUE;

    private boolean mPullDownEnabled = true;

    private boolean mCanPullDown = true;

    private boolean mAnimating;

    private String mPullString;

    private String mRefreshingString;

    private String mReleaseString;

    private TextView mMajorText;

    private TextView mMinorText;

    private ImageView mIndicator;

    private ViewGroup.LayoutParams mContainerLayoutParams;

    public PullToRefreshListView(Context context) {
        this(context, null);
    }
    
    public PullToRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setHeaderDividersEnabled(false);
        Resources resources = getResources();
        
        mTriggerRefreshHeight = resources
                .getDimensionPixelSize(R.dimen.pulldown_trigger_refresh_height);
        mPullHeader = LayoutInflater.from(getContext()).inflate(R.layout.pulldown_header, null);
        mPullHeader.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
            }
        });
        mContainer = mPullHeader.findViewById(R.id.pull_header);
        mContainerLayoutParams = mContainer.getLayoutParams();
        mBackgroundImageView = (ImageView) mPullHeader.findViewById(R.id.img_bkg);
        mMajorText = (TextView) mPullHeader.findViewById(R.id.pull_header_major_text);
        mMinorText = (TextView) mPullHeader.findViewById(R.id.pull_header_minor_text);
        mIndicator = (ImageView) mPullHeader.findViewById(R.id.pullheader_indicator);

        mFirstHeader = mPullHeader;

        addHeaderView(mPullHeader);

        mPullString = resources.getString(R.string.pulldown_pull);
        mRefreshingString = resources.getString(R.string.pulldown_refreshing);
        mReleaseString = resources.getString(R.string.pulldown_release);

        mAnimRotate = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_180);
        mAnimRotate.setFillAfter(true);

        mAnimRotateBack = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_back_180);
        mAnimRotateBack.setFillAfter(true);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!mCanPullDown) {
            return super.dispatchTouchEvent(ev);
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mPullDownEnabled) {
                    mCanRefresh = false;
                    if (!mIsRefreshing && isOnTop()) {
                        prepareTracking(ev);
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (mTracking) {
                    float curY = ev.getY();

                    if ((curY - mStartY) > 10) {
                        requestDisallowInterceptTouchEvent(true);
                        mCurOffsetY = (int) ((curY - mStartY) / 2);
                        if ((mCurOffsetY) < mMaxHeaderHeight) {
                            setContainerHeight(mCurOffsetY);
                            if (mCurOffsetY >= mTriggerRefreshHeight) {
                                if (!mCanRefresh) {
                                    mMajorText.setText(mReleaseString);
                                    mIndicator.startAnimation(mAnimRotate);
                                    mCanRefresh = true;
                                }
                            } else {
                                if (mCanRefresh) {
                                    mMajorText.setText(mPullString);
                                    mIndicator.startAnimation(mAnimRotateBack);
                                    mCanRefresh = false;
                                }
                            }
                        } else {
                            mCurOffsetY = Math.max(0, mMaxHeaderHeight);
                        }
                        ev.setAction(MotionEvent.ACTION_CANCEL);
                        super.dispatchTouchEvent(ev);
                        return true;
                    }
                } else if (mPullDownEnabled && !mTracking && !mIsRefreshing && isOnTop()) {
                    prepareTracking(ev);
                }
                break;
            case MotionEvent.ACTION_UP:  
     
            case MotionEvent.ACTION_CANCEL:
              startBuncingBack();
                if (mTracking) {
                    if (mCanRefresh) {
                        if (mRefreshListener != null) {
                            mRefreshListener.onRefresh(PullToRefreshListView.this);
                        }
                    }
                    stopTracking();
                }
                break;
        }
        try {
            return super.dispatchTouchEvent(ev);
        } catch (ArrayIndexOutOfBoundsException e) {
            // ignore;
            return false;
        } catch (IndexOutOfBoundsException e) {
            // ignore;
            return false;
        }
    }

    public void setPullDownEnabled(boolean mPullDownEnabled) {
        this.mPullDownEnabled = mPullDownEnabled;
    }

    private void setContainerHeight(int height) {
        mContainerLayoutParams.height = height;
        mContainer.setLayoutParams(mContainerLayoutParams);
    }

    private int getContainerHeight() {
        return mContainerLayoutParams.height;
    }

    private boolean isOnTop() {
        return getFirstVisiblePosition() <= 0 && mFirstHeader.getTop() >= 0;
    }

    private void prepareTracking(MotionEvent ev) {
        mMajorText.setText(mPullString);
        mTracking = true;
        mStartY = ev.getY();
        if (mRefreshListener != null) {
            mRefreshListener.onPullDownStarted(PullToRefreshListView.this);
        }
    }

    private void stopTracking() {
        mTracking = false;
        requestDisallowInterceptTouchEvent(false);
    }

    public void setBackgroundImage(Bitmap bitmap) {
        mBackgroundImageView.setImageBitmap(bitmap);
    }

    public void setIndicatorRes(int resId) {
        mIndicator.setImageResource(resId);
    }

    public View getPullHeader() {
        return mPullHeader;
    }

    public TextView getMajorTextView() {
        return mMajorText;
    }

    public void setMajorText(String text) {
        mMajorText.setText(text);
    }

    public void setMinorText(String text) {
        mMinorText.setText(text);
    }

    public void setPullString(String mPullString) {
        this.mPullString = mPullString;
    }

    public void setRefreshingString(String mRefreshingString) {
        this.mRefreshingString = mRefreshingString;
    }

    public void setReleaseString(String mReleaseString) {
        this.mReleaseString = mReleaseString;
    }

    public void setPullDownStateListener(PullDownStateListener listener) {
        mRefreshListener = listener;
    }

    public boolean isRefreshing() {
        return mIsRefreshing;
    }

    public void setRefreshing(boolean refreshing) {
        if (mIsRefreshing == refreshing) {
            return;
        }
        mIsRefreshing = refreshing;
        View prog = findViewById(R.id.pull_header_prog);
        if (mIsRefreshing) {
            // set headerHeight
            if (getContainerHeight() < mTriggerRefreshHeight) {
                mCurOffsetY = mTriggerRefreshHeight;
                animateExpand();
            }

            mIndicator.clearAnimation();
            mIndicator.setVisibility(View.INVISIBLE);
            prog.setVisibility(View.VISIBLE);
            mMajorText.setText(mRefreshingString);
        } else {
            mIndicator.setVisibility(View.VISIBLE);
            mMajorText.setText(mPullString);
            prog.setVisibility(View.INVISIBLE);
            if (!mAnimating) {
                startBuncingBack();
            }
        }
    }

    public void animateExpand() {
        final ObjectAnimator animator = ObjectAnimator.ofInt(this, "containerHeight",
                mTriggerRefreshHeight);
        animator.setDuration(200);
        animator.start();
        animator.addListener(new AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                mAnimating = true;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mAnimating = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mAnimating = false;
            }
        });
    }

    public void startBuncingBack() {
        final ObjectAnimator animator = ObjectAnimator.ofInt(this, "containerHeight", 0);
        animator.setDuration(200);
        animator.start();
//        animator.setEvaluator(new TypeEvaluator<Integer>() {
//
//            @Override
//            public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
//                int result = (int) (startValue - fraction * ((float) startValue - (float) endValue));
//                if (mIsRefreshing && result <= mTriggerRefreshHeight) {
//                    animator.cancel();
//                    result = mTriggerRefreshHeight;
//                }
//                return result;
//            }
//        });
        animator.addListener(new AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                mAnimating = true;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mAnimating = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mAnimating = false;
            }
        });
    }

    public static abstract class PullDownStateListener {
        public void onPullDownStarted(final PullToRefreshListView listView) {

        }

        public abstract void onRefresh(final PullToRefreshListView listView);

        public void onBouncingEnd(final PullToRefreshListView listView) {

        }
    }
}
