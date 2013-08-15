package com.kelin.commonview.listview;

import android.app.Activity;
import android.os.Bundle;

import com.kelin.commonview.R;

public class PinnedHeadderListViewActivity extends Activity {
	PinnedHeaderListView mListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activtiy_pinnedheader_listview);
		mListView = (PinnedHeaderListView) findViewById(R.id.listview);
		ActivityListAdapter adapter = new ActivityListAdapter();
		mListView.setAdapter(adapter);
	}

}
