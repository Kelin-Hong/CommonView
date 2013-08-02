package com.kelin.commonview;

import java.util.ArrayList;

import com.kelin.commonview.indicator.AllFragment;
import com.kelin.commonview.indicator.MyFragmentActivity;
import com.kelin.commonview.indicator.ProbleamFragment;
import com.kelin.commonview.indicator.SystemFragment;
import com.kelin.commonview.indicator.TabInfo;

public class MainActivity extends MyFragmentActivity {
    public final static String CHECK_PREINSTALL_APPS = "preinstall";
    private final static int FRAG_ALL = 0;
    public final static int FRAG_ABNORMAL = 1;
    public final static int FRAG_SYS = 2;

    @Override
    protected int onPrepareTabInfoData(ArrayList<TabInfo> tabs) {
        tabs.add(new TabInfo(FRAG_ALL, "tab1", false,
                AllFragment.class));

        tabs.add(new TabInfo(FRAG_ABNORMAL, "tab2",
                true, ProbleamFragment.class));

        tabs.add(new TabInfo(FRAG_SYS, "tab3",
                false, SystemFragment.class));
        return FRAG_ALL;
    }

}
