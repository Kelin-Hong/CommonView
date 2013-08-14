package com.kelin.commonview.indicator.activity;

import com.kelin.commonview.BaseListActivity;

public class IndicatorListActivity extends BaseListActivity {
    private static final String INTENT_CATEGORY = "com.kelin.commonview.indicator.SAMPLE";
    @Override
    public String getCategory(){
        return INTENT_CATEGORY;
    }

}
