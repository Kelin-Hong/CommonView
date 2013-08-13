package com.kelin.commonview.progressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kelin.commonview.R;


public class CircleProgressBar extends RelativeLayout {
    private TextView mMsgInsizeView;
    private TextView mMsgBelowView;
    private ProgressBar mProgressBar;
    private Drawable mIndeterminateDrawable;
    public CircleProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
        TypedArray a=context.obtainStyledAttributes(attrs, R.styleable.CircleProgressBar);
        mIndeterminateDrawable=a.getDrawable(R.styleable.CircleProgressBar_mode);
        a.recycle();
        initViews(context);
        }

    
    public CircleProgressBar(Context context) {
        super(context);
        initViews(context);
    }
    private void initViews(Context cxt) {
        LayoutInflater inflater = LayoutInflater.from(cxt);
      View view=inflater.inflate(R.layout.view_circle_progressbar,this);
      mProgressBar=(ProgressBar)view.findViewById(R.id.progressbar);
      mMsgInsizeView = (TextView) view.findViewById(R.id.msg_inside);
      mMsgBelowView = (TextView) view.findViewById(R.id.msg_below);
      mProgressBar.setIndeterminateDrawable(mIndeterminateDrawable);
    }

    public void updateProgRs(int percent) {
        mMsgInsizeView.setText(String.valueOf(percent) + "%");
    }

    public void updateInsideMessage(CharSequence msg) {
        mMsgInsizeView.setText(msg);
    }

    public void updateInsideMessage(int RId) {
        mMsgInsizeView.setText(RId);
    }


    public void updateBelowMessage(CharSequence msg) {
        mMsgBelowView.setText(msg);
    }

    public void updateBelowMessage(int ResId) {
        mMsgBelowView.setText(ResId);
    }
}
