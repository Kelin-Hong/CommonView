
package com.kelin.commonview.stylepreference;

import java.util.HashSet;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelin.commonview.R;

public class StylePreference extends LinearLayout {
    public static interface OnPrefenceChangeListener {
        void onChange(StylePreference pref, Object value);
    }

    protected static final boolean DEBUG = true;
    public static final String TYPE_NORMAL = "normal";
    public static final String TYPE_SWITCH = "switch";

    private OnClickListener mOnClickListener = null;
    protected OnPrefenceChangeListener mOnPrefenceChangeListener = null;
    private Resources mRes;

    protected String mTitle = null, mNameString = null, mSummaryString = null;
    protected TextView mName;
    protected TextView mSummary;
    protected ImageView mMore;
    protected StyleToggleButton mSwitch;
    protected View mNewTip;
    protected String mValue = null;
    protected int mLayoutId;
    protected boolean mEnable;
    protected boolean mIsSwitch;
    protected String mType = null;
    private StylePreference mDependence;
    private HashSet<StylePreference> mChilds = new HashSet<StylePreference>();

    public StylePreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        mRes = getResources();
        setGravity(Gravity.CENTER_VERTICAL);
        setOrientation(HORIZONTAL);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StylePreference);
        initLayoutId();
        if (mLayoutId == 0) {
            mLayoutId = a.getResourceId(R.styleable.StylePreference_layout, R.layout.preference);
        }
        mTitle = a.getString(R.styleable.StylePreference_title);
        mNameString = a.getString(R.styleable.StylePreference_name);
        mSummaryString = a.getString(R.styleable.StylePreference_summary);
        mEnable = a.getBoolean(R.styleable.StylePreference_enabled, true);
        mType = a.getString(R.styleable.StylePreference_type);
        mIsSwitch = TYPE_SWITCH.equals(mType);
        a.recycle();
        inflate(getContext(), mLayoutId, this);
    }

    protected void initLayoutId() {
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mMore = (ImageView) findViewById(R.id.more);
        mName = (TextView) findViewById(R.id.name);
        if (mNameString != null) {
            mName.setText(mNameString);
            mName.setVisibility(VISIBLE);
        }
        mSummary = (TextView) findViewById(R.id.summary);
        if (!TextUtils.isEmpty(mSummaryString)) {
            setSummary(mSummaryString);
        } else {
            mSummary.setVisibility(View.GONE);
        }
        mSwitch = (StyleToggleButton) findViewById(R.id.toggle);
        if (mIsSwitch) {
            mSwitch.setVisibility(View.VISIBLE);
            mMore.setVisibility(View.GONE);
            setChecked(isChecked());
        } else {
            mSwitch.setVisibility(View.GONE);
            mMore.setVisibility(View.VISIBLE);
        }
        this.setFocusable(true);
        this.setDescendantFocusability(FOCUS_BLOCK_DESCENDANTS);
        updateEnabled();
        if (getBackground() == null) {
           this.setBackgroundResource(R.drawable.common_list_item_bkg);
        }
        mNewTip = findViewById(R.id.new_tip);
    }

    public void setEnabled(boolean enable) {
        mEnable = enable;
        updateEnabled();
    }

    public void setDependence(StylePreference pref) {
        if (pref == this) return;
        mDependence = pref;
        if (mDependence != null && mDependence.mIsSwitch) {
            mDependence.mChilds.add(this);
        }
    }

    private void updateEnabled() {
        mName.setEnabled(mEnable);
        if (mSummary != null)
            mSummary.setEnabled(mEnable);
        if (mMore != null)
            mMore.setEnabled(mEnable);
        if (mSwitch != null) {
            mSwitch.setEnabled(mEnable);
        }
        this.setClickable(mEnable);
        this.setFocusable(mEnable);
    }

    @Override
    public void setOnClickListener(OnClickListener listener) {
        mOnClickListener = listener;
        // super.setOnClickListener(listener);
    }

    public void setChecked(boolean ischeck) {
        if (mSwitch != null) {
            mSwitch.setChecked(ischeck);
            for (StylePreference pref : mChilds) {
                pref.setEnabled(ischeck);
            }
        }
    }

    public boolean isChecked() {
        if (mSwitch != null) {
            return mSwitch.isChecked();
        }
        return false;
    }

    public void setValue(String text) {
        mValue = text;
        mSummary.setText(text);
    }

    public void setValue(int rid) {
        setValue(mRes.getString(rid));
    }

    public void setSummary(CharSequence spanned) {
        if (mSummary != null) {
            if (spanned != null && spanned.length() > 0) {
                mSummary.setVisibility(VISIBLE);
                mSummary.setText(spanned);
            } else {
                mSummary.setVisibility(GONE);
            }
        }
    }

    public void setSummary(int sid) {
        if (mSummary != null) {
            mSummary.setVisibility(VISIBLE);
            mSummary.setText(sid);
        }
    }

    public void setTitle(int sid) {
        mTitle = mRes.getString(sid);
    }

    public void setIndicator(int resid) {
        mName.setCompoundDrawablesWithIntrinsicBounds(0, 0, resid, 0);
    }

    public void setName(CharSequence text) {
        mName.setText(text);
    }

    public void setName(int rid) {
        setName(mRes.getString(rid));
    }

    @Override
    public void setPressed(boolean pressed) {
        if (mMore != null)
            mMore.setPressed(pressed);
        super.setPressed(pressed);
    }

    @Override
    public boolean performClick() {
        if (!mEnable) return false;
        if (DEBUG)
            Log.d(this.getClass().getSimpleName(), "doClick");
        if (mIsSwitch) {
            boolean checked = !isChecked();
            setChecked(checked);
            if (mOnPrefenceChangeListener != null) {
                mOnPrefenceChangeListener.onChange(this, checked);
            }
        } else {
            if (mOnClickListener != null) {
                mOnClickListener.onClick(this);
            }
        }
        return true;
    }

    public String getValue() {
        return mValue;
    }

    public CharSequence getText() {
        return mSummary.getText();
    }

    public void hideMore() {
        if (mMore != null) {
            mMore.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    public void setOnPrefenceChangeListener(OnPrefenceChangeListener listener) {
        mOnPrefenceChangeListener = listener;
    }

    public void showNewTip(boolean isShow) {
        mNewTip.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

}
