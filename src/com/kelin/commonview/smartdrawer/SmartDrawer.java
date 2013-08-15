package com.kelin.commonview.smartdrawer;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.LinearLayout;

import com.kelin.commonview.R;

public class SmartDrawer extends LinearLayout {
    private float mScale;

    private float mOriginHeight = -1;

    private boolean mOpened = true;

    private boolean mPendingOpened;

    private boolean mAnimating;

    private OnStateChangeListener mOnStateChangeListener;

    public SmartDrawer(Context context) {
        this(context, null);
    }

    public SmartDrawer(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SmartDrawer);
        mOriginHeight = a.getDimension(R.styleable.SmartDrawer_init_height, -1);
        mOpened = a.getBoolean(R.styleable.SmartDrawer_init_open, true);
        a.recycle();
    }

    public void setOnStateChangeListener(OnStateChangeListener listener) {
        this.mOnStateChangeListener = listener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                if (mOriginHeight == -1) {
                    mOriginHeight = SmartDrawer.this.getMeasuredHeight();
                }
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                if (mOpened) {
                    open();
                } else {
                    close();
                }
            }
        });
    }

    public void animateOpen() {
        mPendingOpened = true;
        if (mAnimating) {
            return;
        }
        mOpened = true;
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "scale", 1);
        animator.setDuration(200);
        animator.start();
        mAnimating = true;
        animator.addListener(new AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mAnimating = false;

                if (mPendingOpened != mOpened) {
                    animateClose();
                }
                if (mOnStateChangeListener != null) {
                    mOnStateChangeListener.onStateChange(mOpened);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }
        });
    }

    public void animateClose() {
        mPendingOpened = false;
        if (mAnimating) {
            return;
        }
        mOpened = false;
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "scale", 1, 0);
        animator.setDuration(200);
        animator.start();

        mAnimating = true;
        animator.addListener(new AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mAnimating = false;
                if (mPendingOpened != mOpened) {
                    animateOpen();
                }
                if (mOnStateChangeListener != null) {
                    mOnStateChangeListener.onStateChange(mOpened);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }
        });
    }

    public void animateToggle() {
        if (mOpened) {
            animateClose();
        } else {
            animateOpen();
        }
    }

    public void toggle() {
        if (mOpened) {
            close();
        } else {
            open();
        }
    }

    public void close() {
        setScale(0);
        mOpened = false;
        if (mOnStateChangeListener != null) {
            mOnStateChangeListener.onStateChange(mOpened);
        }
    }

    public void open() {
        setScale(1);
        mOpened = true;
        if (mOnStateChangeListener != null) {
            mOnStateChangeListener.onStateChange(mOpened);
        }
    }

    void setScale(float scale) {
        mScale = scale;
        int scaleHeight = (int) (mOriginHeight * mScale);
        getLayoutParams().height = scaleHeight;
        requestLayout();
    }

    float getScale() {
        return mScale;
    }

    public static interface OnStateChangeListener {
        public void onStateChange(boolean opened);
    }
}
