package com.kelin.commonview.quickaction;

import com.kelin.commonview.BaseListActivity;

public class QuickActionListActivity extends BaseListActivity {
    private static final String INTENT_CATEGORY = "com.kelin.commonview.quickaction.SAMPLE";
    @Override
    public String getCategory(){
        return INTENT_CATEGORY;
    }
}
