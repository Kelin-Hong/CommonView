package com.kelin.commonview.progressbar;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelin.commonview.R;

public class DotLoadingProgressBar extends LinearLayout {
    public static final int DOTSCOUNT = 3;
    private DotsMarquee dotsMarquee;
    private TextView mToolboxLoadingDes;
    private LinearLayout mToolboxLoading;
    private Context mContext;
    private ImageView[] dotsLoading = new ImageView[DOTSCOUNT];

    public DotLoadingProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
        for (int i = 0; i < dotsLoading.length; i++) {
            dotsLoading[i].setAlpha(0.5f);
        }
    }

    private void initViews(Context context) {
        mContext = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.widget_dot_progressbar, this);
        mToolboxLoadingDes = (TextView) view.findViewById(R.id.dot_progressbar_loading_des);
        dotsLoading[0] = (ImageView) view.findViewById(R.id.dot_progressbar_loading_dot_a);
        dotsLoading[1] = (ImageView) view.findViewById(R.id.dot_progressbar_loading_dot_b);
        dotsLoading[2] = (ImageView) view.findViewById(R.id.dot_progressbar_loading_dot_c);
    }

    public void displayLoadingAnimation(String loadingStr) {
        mToolboxLoadingDes.setText(loadingStr);
        this.setVisibility(View.VISIBLE);
        if (dotsMarquee != null && dotsMarquee.getStatus() == AsyncTask.Status.RUNNING) {
            dotsMarquee.cancel(true);
            for (int i = 0; i < dotsLoading.length; i++)
                dotsLoading[i].setAlpha(0.5f);
        }
        dotsMarquee = new DotsMarquee();
        dotsMarquee.execute();
    }

    public void cancleProgressDialog() {
        if (dotsMarquee != null && dotsMarquee.getStatus() == AsyncTask.Status.RUNNING) {
            dotsMarquee.cancel(true);
            for (int i = 0; i < dotsLoading.length; i++)
                dotsLoading[i].setAlpha(0.5f);
        }
        DotLoadingProgressBar.this.setVisibility(View.GONE);
    }

    private class DotsMarquee extends AsyncTask<Void, Integer, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            int i = 0;
            int t = 0;
            // 435 * 100 = 43.5s
            while (t < 100) {
                if (isCancelled())
                    break;
                // 21 * 5 = 105ms
                int alpha = 50;
                while (alpha < 255) {
                    publishProgress(i, alpha);
                    alpha += 10;
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // 80ms

                try {
                    Thread.sleep(80);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                alpha -= 10;
                // 20 * 5 = 100ms
                while (alpha >= 50) {
                    publishProgress(i, alpha);
                    alpha -= 10;
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // 150ms
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i = (i + 1) % dotsLoading.length;
                t++;
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(final Integer... params) {
            // Message message = new Message();
            // message.what = REFRESH_LOADING_POINT;
            // message.arg1 = params[0];
            // message.arg2 = params[1];
            // mHandler.sendMessage(message);
            dotsLoading[params[0]].setAlpha(params[1]/255f);
// ((Activity) mContext).runOnUiThread(new Runnable() {
// public void run() {
// dotsLoading[params[0]].setAlpha(params[1]);
// }
// });
        }

        @Override
        protected void onPostExecute(Void params) {
            cancleProgressDialog();
            DotLoadingProgressBar.this.setVisibility(View.GONE);
            // mHandler.sendMessage(mHandler.obtainMessage(DISMISS_LOADING));
            super.onPostExecute(params);
        }
    }
}
