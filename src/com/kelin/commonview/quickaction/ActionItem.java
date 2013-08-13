package com.kelin.commonview.quickaction;

import java.lang.ref.WeakReference;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.kelin.commonview.R;;


/**
 * Action item, displayed as menu with icon and text.
 *
 * @author Lorensius. W. L. T
 */
@SuppressWarnings("static-access")
public class ActionItem {
    private class ViewHolder {
        View container;
        ImageView iconView;
        TextView textView;
        TextView newTip;
    }

    private Drawable icon = null;
    private String title = null;
    private OnClickListener listener;
    private Drawable indicator = null;
    private boolean mEnable = true;
    private WeakReference<ViewHolder> mViewRef;
    private boolean isShowNewTip = false;

    private boolean isShowNewTip() {
        return isShowNewTip;
    }

    public void setShowNewTip(boolean isShowNewTip) {
        this.isShowNewTip = isShowNewTip;
    }

    /**
     * Constructor
     */
    public ActionItem() {
    }

    /**
     * Constructor
     *
     * @param icon {@link Drawable} action icon
     */
    public ActionItem(Drawable icon) {
        this.icon = icon;
    }

    public void setViews(View v) {
        if (v == null) {
            mViewRef = null;
            return;
        }
        ViewHolder vh = new ViewHolder();
        vh.container = v;
        vh.iconView = (ImageView) v.findViewById(R.id.quickaction2_icon);
        vh.textView = (TextView) v.findViewById(R.id.quickaction2_title);
        vh.newTip = (TextView) v.findViewById(R.id.new_tip);
        mViewRef = new WeakReference<ViewHolder>(vh);
        updateViews();
    }

    private void updateViews() {
        ViewHolder vh = getViews();
        if (vh == null) {
            return;
        }
        if (icon != null) {
            vh.iconView.setImageDrawable(icon);
        }

        if (title != null) {
            vh.textView.setText(title);
        }

        // vh.textView.setCompoundDrawablesWithIntrinsicBounds(null, null,
        // indicator, null);
        if (isShowNewTip()) {
            vh.newTip.setVisibility(View.VISIBLE);
        }

        if (mEnable) {
            vh.container.setOnClickListener(listener);
            vh.container.setFocusable(true);
            vh.container.setClickable(true);
        } else {
            vh.container.setOnClickListener(null);
            vh.container.setFocusable(false);
            vh.container.setClickable(false);
        }
    }

    public ViewHolder getViews() {
        return mViewRef != null ? mViewRef.get() : null;
    }

    public boolean isEnabled() {
        return mEnable;
    }

    public void setEnable(boolean enable) {
        this.mEnable = enable;
    }

    public void update() {
        updateViews();
    }

    /**
     * Set action title
     *
     * @param title action title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get action title
     *
     * @return action title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Set action icon
     *
     * @param icon {@link Drawable} action icon
     */
    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public void setIcon(int iconId, Context context) {
        this.icon = context.getResources().getDrawable(iconId);
    }

    /**
     * Get action icon
     *
     * @return {@link Drawable} action icon
     */
    public Drawable getIcon() {
        return this.icon;
    }

    public void setIndicator(int iconId, Context context) {
        this.indicator = context.getResources().getDrawable(iconId);
    }

    public Drawable getIndicator() {
        return this.indicator;
    }

    /**
     * Set on click listener
     *
     * @param listener on click listener {@link View.OnClickListener}
     */
    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    /**
     * Get on click listener
     *
     * @return on click listener {@link View.OnClickListener}
     */
    public OnClickListener getListener() {
        return this.listener;
    }
}
