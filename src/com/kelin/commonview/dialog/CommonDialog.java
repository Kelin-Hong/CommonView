package com.kelin.commonview.dialog;

import java.text.NumberFormat;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.kelin.commonview.R;


@SuppressWarnings("static-access")
public class CommonDialog extends Dialog {
    private static class MyHandler extends NoLeakHandler<CommonDialog> {
        MyHandler(CommonDialog context) {
            super(context);
        }

        @Override
        protected void processMessage(CommonDialog context, Message msg) {
            if (msg.what == MSG_DISMISS_TIMER_UPDATED) {
                context.mTimerListener.onTimeUpdated(msg.arg1);
            }
        }
    }

    private static final String TAG = "CommonDialog";

    public interface TimerListener {
        public void onTimeUpdated(int leftSeconds);
    }

    public static final int DEFAULT_BTN_LEFT = 1;
    public static final int DEFAULT_BTN_MID = 2;
    public static final int DEFAULT_BTN_RIGHT = 3;

    private static final int MSG_DISMISS_TIMER_UPDATED = 1;

    private ProgressBar mProgressBar;
    private TextView mProgressPercent;
    private NumberFormat mPercentFormatter;
    private Button mDefaultBtn;
    private ScrollView mContentHolder;
    private LinearLayout mLinearLayout;
    private View mLayoutButtons;
    private Context mContext;
    private Button mOkButton;
    TextView mTitleView;

    private TimerListener mTimerListener;
    private Timer mTimer;
    private int mLeftSeconds;

    private Handler mHandler = new MyHandler(this);

    public CommonDialog(Context context) {
        super(context, R.style.MyTheme_CustomDialog);
        setContentView(R.layout.widget_common_dialog);
        mContentHolder = (ScrollView) findViewById(R.id.content_holder);
        mLinearLayout = (LinearLayout) findViewById(R.id.content_holder2);
        mLayoutButtons = findViewById(R.id.btn_panel);
        mOkButton = (Button) findViewById(R.id.ok_btn);
        mTitleView = (TextView) findViewById(R.id.title);
        mContext = context;
    }

    public void setTitle(int resId) {
        mTitleView.setText(resId);
    }

    public void setTitle(String title) {
        mTitleView.setText(title);
    }

    public void setNoTitleBar() {
        findViewById(R.id.dlg_view).setBackgroundResource(
                R.drawable.common_dialog_bkg_no_title);
        mTitleView.setVisibility(View.GONE);
    }

    public void setContentView(View view) {
        ViewGroup contentHolder = mContentHolder;
        contentHolder.removeAllViews();
        contentHolder.addView(view);
    }

    public void setContentPadding(int width) {
        mContentHolder.setPadding(width, mContentHolder.getPaddingTop(), width,
                mContentHolder.getPaddingBottom());
    }

    public void setContentPadding(int left,int top,int right,int bottom){
        mContentHolder.setPadding(left, top, right, bottom);
    }

    public void addContentView(View view) {
        ViewGroup contentHolder = mLinearLayout;
        contentHolder.addView(view);
    }

    public void setMessage(int resId) {
        TextView content = (TextView) findViewById(R.id.message);
        content.setText(resId);
        content.setVisibility(View.VISIBLE);
    }

    public void setMessage(CharSequence msg) {
        TextView content = (TextView) findViewById(R.id.message);
        content.setText(msg);
        content.setVisibility(View.VISIBLE);
    }

    public ListView setListAdapter(ListAdapter adapter, int selected) {
        ListView listView = setListAdapter(adapter);
        if (adapter instanceof SimpleAdapter) {
            SimpleAdapter ada = (SimpleAdapter) adapter;
            if (ada.getViewBinder() == null) {
                SimpleAdapter.ViewBinder binder = new SimpleAdapter.ViewBinder() {
                    @Override
                    public boolean setViewValue(View view, Object obj, String s) {
                        if (view instanceof TextView && obj instanceof String) {
                            ((TextView) view).setText((String) obj);
                            return true;
                        }
                        return false;
                    }
                };
                ada.setViewBinder(binder);
            }
        }
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        if (selected >= 0) listView.setItemChecked(selected, true);
        return listView;
    }

    public ListView setListAdapter(ListAdapter adapter) {
        mContentHolder.setVisibility(View.GONE);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setVisibility(View.VISIBLE);
        listView.setAdapter(adapter);
        return listView;
    }

    public void setListHeader(int resId) {
        TextView header = (TextView) findViewById(R.id.list_header);
        header.setText(resId);
        header.setVisibility(View.VISIBLE);
    }

    public TextView getListHeader(){
        return (TextView) findViewById(R.id.list_header);
    }

    private void setupProgressViews() {
        if (mProgressBar == null) {
            findViewById(R.id.progress_panel).setVisibility(View.VISIBLE);
            mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        }
    }

    public void setProgressMax(int maxProgress) {
        setupProgressViews();
        mProgressBar.setMax(maxProgress);
    }

    public void setProgress(int progress) {
        setupProgressViews();
        mProgressBar.setProgress(progress);
    }

    public void setSecondaryProgress(int progress) {
        setupProgressViews();
        mProgressBar.setSecondaryProgress(progress);
    }

    public void setPercentProgress(int progress) {
        setupProgressViews();
        if (mProgressPercent == null) {
            mProgressPercent = (TextView) findViewById(R.id.progress_percent);
            mProgressPercent.setVisibility(View.VISIBLE);
            mPercentFormatter = NumberFormat.getPercentInstance();
        }
        double percent = progress / (double) mProgressBar.getMax();
        String percentStr = mPercentFormatter.format(percent);
        mProgressPercent.setText(percentStr);
    }

    public CheckBox setCheckBox(boolean initialValue, CharSequence text) {
        CheckBox cb = (CheckBox) findViewById(R.id.checkbox);
        cb.setVisibility(View.VISIBLE);
        cb.setText(text);
        cb.setChecked(initialValue);
        return cb;
    }

    public CheckBox setCheckBox(boolean initialValue, int resId) {
        CheckBox cb = (CheckBox) findViewById(R.id.checkbox);
        cb.setVisibility(View.VISIBLE);
        cb.setText(resId);
        cb.setChecked(initialValue);
        return cb;
    }

    public void setOkBtn(int resId, View.OnClickListener clickListener) {
        mLayoutButtons.setVisibility(View.VISIBLE);
        Button okBtn = mOkButton;
        okBtn.setVisibility(View.VISIBLE);

        if (resId > 0) {
            okBtn.setText(resId);
        }

        if (clickListener != null) {
            okBtn.setOnClickListener(new ExternalListener(clickListener));
        } else {
            okBtn.setOnClickListener(new CloseListener());
        }
    }

    /**
     * This method should have click listener and dismiss dialog by yourself
     */
    public void setOkBtnNotDismiss(int resId, View.OnClickListener clickListener) {
        mLayoutButtons.setVisibility(View.VISIBLE);
        Button okBtn = mOkButton;
        okBtn.setVisibility(View.VISIBLE);
        if (resId > 0) okBtn.setText(resId);
        okBtn.setOnClickListener(clickListener);
    }

    public void setWarningOkBtn() {
        mOkButton.setBackgroundResource(R.drawable.dx_btn_green_big);
    }

    public void setNormalOkBtn() {
        WidgetUtils.applyDxButtonWhiteBigStyle(mOkButton);
    }

    public void setBtnEnable(int whichBtn, boolean enable) {
        Button button = null;

        if (whichBtn == DEFAULT_BTN_LEFT) {
            button = mOkButton;
        } else if (whichBtn == DEFAULT_BTN_MID) {
            button = (Button) findViewById(R.id.mid_btn);
        } else if (whichBtn == DEFAULT_BTN_RIGHT) {
            button = (Button) findViewById(R.id.cancel_btn);
        } else {
            button = null;
            Log.w(TAG, "Set Bad bt: " + whichBtn);
        }

        if(button != null) {
            button.setEnabled(enable);
        }
    }

    public void setCancelBtn(int resId, View.OnClickListener clickListener) {
        mLayoutButtons.setVisibility(View.VISIBLE);
        Button cancelBtn = (Button) findViewById(R.id.cancel_btn);
        cancelBtn.setVisibility(View.VISIBLE);

        if (resId > 0) {
            cancelBtn.setText(resId);
        }

        if (clickListener != null) {
            cancelBtn.setOnClickListener(new ExternalListener(clickListener));
        } else {
            cancelBtn.setOnClickListener(new CloseListener());
        }
    }

    public void setMidBtn(int resId, View.OnClickListener clickListener) {
        Button midBtn = (Button) findViewById(R.id.mid_btn);
        midBtn.setVisibility(View.VISIBLE);

        if (resId > 0) {
            midBtn.setText(resId);
        }

        if (clickListener != null) {
            midBtn.setOnClickListener(new ExternalListener(clickListener));
        } else {
            midBtn.setOnClickListener(new CloseListener());
        }
    }

    /**
     * Set the default button, which will be focused.
     * @param defaultBtn Must be one of {@link #DEFAULT_BTN_LEFT}
     *        or {@link #DEFAULT_BTN_MID} or {@link #DEFAULT_BTN_RIGHT}
     */
    public void setDefaultButton(int whichBtn) {
        if (whichBtn == DEFAULT_BTN_LEFT) {
            mDefaultBtn = mOkButton;
        } else if (whichBtn == DEFAULT_BTN_MID) {
            mDefaultBtn = (Button) findViewById(R.id.mid_btn);
        } else if (whichBtn == DEFAULT_BTN_RIGHT) {
            mDefaultBtn = (Button) findViewById(R.id.cancel_btn);
        } else {
            mDefaultBtn = null;
            Log.w(TAG, "Bad default bt: " + whichBtn);
        }

        if (mDefaultBtn != null) {
            mDefaultBtn.setSelected(true);
        }
    }

    public void showWithAutoDimiss(int seconds, TimerListener listener) {
        if (isShowing() || seconds <= 0) {
            return;
        }

        mLeftSeconds = seconds;
        mTimerListener = listener;
        mTimer = new Timer();
        mTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                --mLeftSeconds;
                if (mTimerListener != null) {
                    mHandler.obtainMessage(MSG_DISMISS_TIMER_UPDATED,
                            mLeftSeconds, 0).sendToTarget();
                }
                if (mLeftSeconds == 0) {
                    mTimer.cancel();
                    dismiss();
                }
            }
        }, 1000, 1000);
        super.show();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mDefaultBtn != null) {
            mDefaultBtn.setSelected(false);
            mDefaultBtn = null;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int keyCode = event.getKeyCode();
        if (keyCode == KeyEvent.KEYCODE_SEARCH) {
            return true;  // ignore back key & search key
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mTimer != null) {
                mTimer.cancel();
                mTimer = null;
            }
            // Let super class to handle this key event
        }
        return super.dispatchKeyEvent(event);
    }

    private class CloseListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            cancel();
        }
    }

    private class ExternalListener implements View.OnClickListener {
        private View.OnClickListener mListener;

        public ExternalListener(View.OnClickListener listener) {
            mListener = listener;
        }

        @Override
        public void onClick(View v) {
            CommonDialog.this.dismiss();
            mListener.onClick(v);
        }
    }

    public void setSettingBtn(View.OnClickListener clickListener) {
        ImageButton settingButton = (ImageButton) findViewById(R.id.settings);
        settingButton.setVisibility(View.VISIBLE);
        if (clickListener != null) {
            settingButton.setOnClickListener(new ExternalListener(clickListener));
        } else {
            settingButton.setOnClickListener(new CloseListener());
        }
    }
}
