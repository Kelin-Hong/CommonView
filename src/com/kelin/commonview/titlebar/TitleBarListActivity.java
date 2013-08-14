package com.kelin.commonview.titlebar;

import com.kelin.commonview.BaseListActivity;

public class TitleBarListActivity extends BaseListActivity {
    private static final String INTENT_CATEGORY = "com.kelin.commonview.titlebar.SAMPLE";
    @Override
    public String getCategory(){
        return INTENT_CATEGORY;
    }
}
