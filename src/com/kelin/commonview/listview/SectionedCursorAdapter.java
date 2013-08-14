package com.kelin.commonview.listview;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.view.ViewGroup;

public class SectionedCursorAdapter extends CursorAdapter implements PinnedSectionedHeaderAdapter {

    public SectionedCursorAdapter(Context context, Cursor c, boolean autoRequery) {

        super(context, c, autoRequery);
        // TODO Auto-generated constructor stub
    }

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
    public void bindView(View arg0, Context arg1, Cursor arg2) {

        // TODO Auto-generated method stub
        
    }

    @Override
    public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {

        // TODO Auto-generated method stub
        return null;
    }

}
