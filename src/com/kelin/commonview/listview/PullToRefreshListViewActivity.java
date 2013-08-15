package com.kelin.commonview.listview;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ArrayAdapter;

import com.kelin.commonview.R;
import com.kelin.commonview.listview.PullToRefreshListView.PullDownStateListener;

public class PullToRefreshListViewActivity extends Activity {
    private PullToRefreshListView mListView;
    private ArrayList<String> mDataList = new ArrayList<String>();
    private ArrayAdapter<String> mAdapter;
    private int mCount = 0;
    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            if (msg.what == 1) {
                for (int i = 0; i < 5; i++) {
                    mDataList.add(i,"item" + mCount++);
                    mAdapter.notifyDataSetChanged();
                }
                mListView.setRefreshing(false);
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pulltorefreshlistview);
        mListView = (PullToRefreshListView) findViewById(R.id.pull_to_refresh_listview);
        mListView.setRefreshing(true);
        mHandler.sendEmptyMessage(1);
        mListView.setPullDownStateListener(new PullDownStateListener() {

            @Override
            public void onRefresh(PullToRefreshListView listView) {

                mListView.setRefreshing(true);
                new Thread() {

                    @Override
                    public void run() {
                        try {
                            sleep(1000);

                            mHandler.sendEmptyMessage(1);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                }.start();

            }
        });

        mAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, mDataList);
        mListView.setAdapter(mAdapter);
    }

}
