package com.kelin.commonview.listview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.kelin.commonview.R;

public class PinnedHeadderListViewActivity extends Activity{
    PinnedHeaderListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activtiy_pinnedheader_listview);
        mListView=(PinnedHeaderListView)findViewById(R.id.listview);
    }
   class PinnedHeaderAdapter implements PinnedSectionedHeaderAdapter{

    @Override
    public boolean isSectionHeader(int position) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int getSectionForPosition(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getSectionHeaderViewType(int section) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 0;
    }
       
   }
}
