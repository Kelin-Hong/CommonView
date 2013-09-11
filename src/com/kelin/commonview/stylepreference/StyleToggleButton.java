package com.kelin.commonview.stylepreference;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;

import com.kelin.commonview.R;

public class StyleToggleButton extends TextView {
    private static final int NO_ALPHA = 0xFF;

    private Context mContext;
    private boolean mChecked = false;
    private CharSequence mTextOn;
    private CharSequence mTextOff;
    private float mDisabledAlpha;

    private static final int[] CHECKED_STATE_SET = {
        android.R.attr.state_checked
    };

    public StyleToggleButton(Context context) {
        super(context);
        init(context);
    }

    public StyleToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StyleToggleButton);
        mTextOn = a.getText(R.styleable.StyleToggleButton_textOn);
        mTextOff = a.getText(R.styleable.StyleToggleButton_textOff);
        mDisabledAlpha = a.getFloat(R.styleable.StyleToggleButton_disabledAlpha, 0.5f);
        a.recycle();
        init(context);
    }

    private void init(Context cxt) {
        mContext = cxt;

        setGravity(Gravity.CENTER);
        setTextColor(cxt.getResources().getColorStateList(R.color.toggle_button_text));
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);

        if (mTextOn == null) {
            mTextOn = mContext.getText(R.string.common_state_on);
        }
        if (mTextOff == null) {
            mTextOff = mContext.getText(R.string.common_state_off);
        }

        syncText();
        syncBackground();
    }

    private void syncText() {
        if (mChecked) {
            setText(mTextOn != null ? mTextOn : "");
        } else {
            setText(mTextOff != null ? mTextOff : "");
        }
    }

    private void syncBackground() {
        if (mChecked) {
            setBackgroundResource(R.drawable.toggle_button_on);
        } else {
            setBackgroundResource(R.drawable.toggle_button_off);
        }
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable bkgDrawable = getBackground();
        if (bkgDrawable != null) {
            bkgDrawable.setAlpha(isEnabled() ? NO_ALPHA : (int) (NO_ALPHA * mDisabledAlpha));
        }
    }

    @Override
    public boolean performClick() {
        if (!super.performClick()) {
            // If no OnClickListener setup, toggle this button as default behavior
            toggle();
            return false;
        }
        return true;
    }

    public boolean isChecked() {
        return mChecked;
    }

    public void setChecked(boolean checked) {
        if (mChecked == checked) {
            return; // no change
        }
        mChecked = checked;
        refreshDrawableState();
        syncText();
        syncBackground();
    }

    public void toggle() {
        setChecked(!mChecked);
    }

    public void setToggleText(int textOnResId, int textOffResId) {
        mTextOn = mContext.getText(textOnResId);
        mTextOff = mContext.getText(textOffResId);
        syncText();
    }

    public void setToggleText(CharSequence textOn, CharSequence textOff) {
        mTextOn = textOn;
        mTextOff = textOff;
        syncText();
    }
}
