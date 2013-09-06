package com.kelin.commonview.circlemenubutton;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.kelin.commonview.R;

public class CircleMenuButton extends FrameLayout {
    private ImageView mLedFlash;
    private ImageView mLedLight;
    private ImageView mPoliceLight;
    private ImageView mScreenLight;
    private ImageView mWarningLight;
    private int mCurrentIndex, mLastIndex;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private View mRoot;
    private ImageView mNormalBtn;
    private float mX, mY, mCircleCenterX, mCircleCenterY;
    private float R1 = 110f, R2 = 200f;
    private List<ImageView> mImageViewList = new ArrayList<ImageView>();
    private OnItemClickListener mOnItemClickListener;

    public CircleMenuButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mRoot = mLayoutInflater.inflate(R.layout.widget_circle_menu_button,
                this);
        inits(mRoot);
    }

    public CircleMenuButton(Context context) {
        super(context);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        getViewTreeObserver().addOnGlobalLayoutListener(
                new OnGlobalLayoutListener() {

                    @Override
                    public void onGlobalLayout() {
                        // getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        // Rect RectSrc = new Rect();
                        // CircleMenuButton.this.getLocalVisibleRect(RectSrc);
                        mCircleCenterX = CircleMenuButton.this
                                .getMeasuredWidth() / 2;
                        mCircleCenterY = CircleMenuButton.this
                                .getMeasuredHeight() / 2;
                        // mCircleCenterX = (RectSrc.left + RectSrc.right) / 2;
                        // mCircleCenterY = (RectSrc.top + RectSrc.bottom) / 2;

                    }
                });
    }

    private void inits(View view) {
        mNormalBtn = (ImageView) view.findViewById(R.id.cirlce_menu_btn);
        mLedFlash = (ImageView) view.findViewById(R.id.led_flash);
        mLedLight = (ImageView) view.findViewById(R.id.led_light);
        mPoliceLight = (ImageView) view.findViewById(R.id.police_lights);
        mScreenLight = (ImageView) view.findViewById(R.id.screen_light);
        mWarningLight = (ImageView) view.findViewById(R.id.warning_light);
        mImageViewList.add(mScreenLight);
        mImageViewList.add(mPoliceLight);
        mImageViewList.add(mWarningLight);
        mImageViewList.add(mLedFlash);
        mImageViewList.add(mLedLight);
        R2 = getResources().getDimension(R.dimen.large_radius);
        R1 = getResources().getDimension(R.dimen.little_radius);
    }

    private boolean isInCirle() {
        float r = (float) Math.sqrt((mY - mCircleCenterY) * (mY - mCircleCenterY)
                + (mX - mCircleCenterX) * (mX - mCircleCenterX));
        boolean flag = true;
        if (r < R1) {
            flag = false;
        }
        if (r > R2) {
            flag = false;
        }
        return flag;
    }

    private double calculateAngle() {
        double u = Math.atan(-(mY - mCircleCenterY) / (mX - mCircleCenterX));
        if (mY - mCircleCenterY <= 0 && mX - mCircleCenterX <= 0)
            u += Math.PI;
        if (mY - mCircleCenterY >= 0 && mX - mCircleCenterX <= 0)
            u += Math.PI;
        if (mY - mCircleCenterY >= 0 && mX - mCircleCenterX >= 0)
            u += Math.PI * 2;
        return u + Math.PI / 6;
    }

    private void calculateIndexByAngle(double u) {
        mLastIndex = mCurrentIndex;
        mCurrentIndex = (int) (3 * u / Math.PI) % 6;
        if (mCurrentIndex > 4)
            mCurrentIndex = 4;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mX = event.getX();
                mY = event.getY();
                if (!isInCirle()) {
                    return false;
                }
                calculateIndexByAngle(calculateAngle());

                mImageViewList.get(mLastIndex).setVisibility(View.GONE);
                if (mCurrentIndex < 5) {
                    mImageViewList.get(mCurrentIndex).setVisibility(View.VISIBLE);

                }
// else {
// if (mOnItemClickListener != null)
// mOnItemClickListener.onItemClick(
// mImageViewList.get(mCurrentIndex), mCurrentIndex);
// // return;
// // mImageViewList.get(mCurrentIndex).setBackgroundColor(
// // getResources().getColor(R.color.black_80));
// if (mImageViewList.get(mCurrentIndex).getVisibility() == View.GONE) {
// mImageViewList.get(mCurrentIndex).setVisibility(
// View.VISIBLE);
// } else {
// mImageViewList.get(mCurrentIndex).setVisibility(View.GONE);
// }
// }
                break;
            case MotionEvent.ACTION_UP:
                if (mCurrentIndex < 5) {
                    mImageViewList.get(mCurrentIndex).setVisibility(View.GONE);
                    if (mOnItemClickListener != null)
                        mOnItemClickListener.onItemClick(
                                mImageViewList.get(mCurrentIndex), mCurrentIndex);
                }
                break;
        }
        return true;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public interface OnItemClickListener {
        public void onItemClick(View v, int position);
    }
}
