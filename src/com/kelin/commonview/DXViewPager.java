package com.kelin.commonview;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class DXViewPager extends ViewPager {
    private boolean mViewTouchMode = false;
    private Handler mInitHandler = null;

    public DXViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setInitMessage(Handler msg) {
        mInitHandler = msg;
    }

    public void setViewTouchMode(boolean b) {
        if (b && !isFakeDragging()) {
// System.out.println("beginFakeDrag!");
            beginFakeDrag();
        } else if (!b && isFakeDragging()) {
// System.out.println("endFakeDrag!");
            endFakeDrag();
        }
        mViewTouchMode = b;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
// System.out.println("mViewTouchMode is " + mViewTouchMode);
        if (mViewTouchMode) {
            return false;
        }
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try {
            return super.onTouchEvent(ev);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean arrowScroll(int direction) {
        if (mViewTouchMode) return false;
        if (direction != FOCUS_LEFT && direction != FOCUS_RIGHT) return false;
        return super.arrowScroll(direction);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (mInitHandler != null) {
            mInitHandler.sendEmptyMessageDelayed(100, 10);
            mInitHandler = null;
        }
    }
}
