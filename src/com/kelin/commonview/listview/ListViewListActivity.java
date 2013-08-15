package com.kelin.commonview.listview;

import com.kelin.commonview.BaseListActivity;

public class ListViewListActivity extends BaseListActivity{
    private static final String INTENT_CATEGORY = "com.kelin.commonview.listview.SAMPLE";
    @Override
    public String getCategory(){
        return INTENT_CATEGORY;
    }
}
