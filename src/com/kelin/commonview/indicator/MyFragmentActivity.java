package com.kelin.commonview.indicator;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.ViewGroup;
import android.view.Window;

import com.kelin.commonview.R;
import com.kelin.commonview.indicator.fragment.BaseFragment;


public abstract class  MyFragmentActivity extends FragmentActivity implements OnPageChangeListener{


    public static final String EXTRA_TAB = "tab";
    public static final String EXTRA_QUIT = "extra.quit";

    protected int mCurrentTab = 0;
    protected int mLastTab = -1;
    protected ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();
    protected MyAdapter myAdapter = null;
    protected ViewPager mPager;
    protected TitleIndicator mIndicator;

    private boolean mHasAnimation = false;

    public TitleIndicator getIndicator() {
        return mIndicator;
    }

    public class MyAdapter extends FragmentPagerAdapter {
        ArrayList<TabInfo> tabs = null;
        Context context = null;

        public MyAdapter(Context context, FragmentManager fm, ArrayList<TabInfo> tabs) {
            super(fm);
            this.tabs = tabs;
            this.context = context;
        }

        @Override
        public Fragment getItem(int pos) {
            Fragment fragment = null;
            if (tabs != null && pos < tabs.size()) {
                TabInfo tab = tabs.get(pos);
                if (tab == null)
                    return null;
                fragment = tab.createFragment();
            }
            return fragment;
         //   return MyPageFragment.create(pos);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public int getCount() {
            if (tabs != null && tabs.size() > 0)
                return tabs.size();
            return 0;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TabInfo tab = tabs.get(position);
            BaseFragment fragment = (BaseFragment) super.instantiateItem(container, position);
            tab.fragment = fragment;
            return fragment;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        try {
          //  mHasAnimation = getIntent().getBooleanExtra(SingleActivity.EXTRA_HAS_ANIM, false);
        } catch (Exception e) {
            //ignore
        }

        setContentView(getMainViewResId());
        initViews();

      mPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.page_margin_width));
      mPager.setPageMarginDrawable(R.color.page_viewer_margin_color);
    }

    @Override
    protected void onDestroy() {
        mTabs.clear();
        mTabs = null;
        myAdapter.notifyDataSetChanged();
        myAdapter = null;
        mPager.setAdapter(null);
        mPager = null;
        mIndicator = null;

        super.onDestroy();
    }

    private final void initViews() {
        // Show the main screen by default
        mCurrentTab = onPrepareTabInfoData(mTabs);
        Intent intent = getIntent();
        if (intent != null) {
            mCurrentTab = intent.getIntExtra(EXTRA_TAB, mCurrentTab);
        }
     
        
        myAdapter = new MyAdapter(this, getSupportFragmentManager(), mTabs);

        // Setup the views
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(myAdapter);
       mPager.setOnPageChangeListener(this);
        mPager.setOffscreenPageLimit(mTabs.size());

       mIndicator = (TitleIndicator) findViewById(R.id.pagerindicator);
       mIndicator.init(mCurrentTab, mTabs, mPager);

        mPager.setCurrentItem(mCurrentTab);
        final Message msg = getInitMessage();
        mPager.post(new Runnable() {
            @Override
            public void run() {
                TabInfo ti = getFragmentById(mCurrentTab);
                mLastTab = mCurrentTab;
                if (ti != null) {
                    if (ti.fragment != null) ti.fragment.onScrollIn();
                }
                if (msg != null) {
                    msg.sendToTarget();
                }
            }
        });
    }

    /**
     * add a tab info
     * @param tab
     */
    public void addTabInfo(TabInfo tab) {
        mTabs.add(tab);
        myAdapter.notifyDataSetChanged();
    }

    /**
     * add a list of tabinfo
     * @param tabs
     */
    public void addTabInfos(ArrayList<TabInfo> tabs) {
        mTabs.addAll(tabs);
        myAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mIndicator.onScrolled((mPager.getWidth() + mPager.getPageMargin()) * position + positionOffsetPixels);
        for (int i = 0; i < mTabs.size(); i++) {
            TabInfo tab = mTabs.get(i);
            if (tab.fragment != null) {
                tab.fragment.onScrolled();
            }
        }
    }

    @Override
    public void onPageSelected(int position) {
        mIndicator.onSwitched(position);
        mCurrentTab = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            if (mLastTab != mCurrentTab && mLastTab >= 0 && mLastTab < mTabs.size()) {
                TabInfo tab = mTabs.get(mLastTab);
                if (tab.fragment != null) {
                    tab.fragment.onScrollOut();
                }
            }
            if (mCurrentTab != mLastTab) {
                TabInfo tab = mTabs.get(mCurrentTab);
                if (tab.fragment != null) {
                    tab.fragment.onScrollIn();
                }
            }
            mLastTab = mCurrentTab;
        }
    }

    @Override
    protected void onUserLeaveHint() {
      super.onUserLeaveHint();
        for (TabInfo tab : mTabs) {
            if (tab.fragment != null) {
                tab.fragment.onUserLeaveHint();
            }
        }
    }

    protected TabInfo getFragmentById(int tabId) {
        if (mTabs == null) return null;
        for (int index = 0, count = mTabs.size(); index < count; index++) {
            TabInfo tab = mTabs.get(index);
            if (tab.getId() == tabId) {
                return tab;
            }
        }
        return null;
    }
    /**
     * Navigate to target tab
     * @param tabId the tab id, not the index
     */
    public void navigate(int tabId) {
        for (int index = 0, count = mTabs.size(); index < count; index++) {
            if (mTabs.get(index).getId() == tabId) {
                mPager.setCurrentItem(index);
            }
        }
    }

    /**
     * called when onBackPressed and check whether current fragment allow back
     * @return false allow
     */
    protected boolean callBackStack() {
        TabInfo tab = mTabs.get(mCurrentTab);
        if (tab.fragment != null) {
            return tab.fragment.onBackPressed();
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (!callBackStack()) {
            finish();
        }
    }

    /**
     * You can implement this to make a init callback
     * @return null will has no init callback
     */
    protected Message getInitMessage() {
        return null;
    }

    /**
     * Return the main content layout id
     * @return layout id
     */
    protected int getMainViewResId() {
        return R.layout.fragment_tab_activity;
    }

    /**
     * Initial the TabInfo list
     * @param tabs won't be null
     * @return current tab
     */
    protected abstract int onPrepareTabInfoData(ArrayList<TabInfo> tabs);

    @Override
    public void finish() {
        super.finish();
        if (mHasAnimation) {
            overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
        }
    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        // for fix a known issue in support library
//        // https://code.google.com/p/android/issues/detail?id=19917
//        outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
//        super.onSaveInstanceSt/ate(outState);
//    }

    public void startActivityWithAnim(Intent intent) {
    //    intent.putExtra(SingleActivity.EXTRA_HAS_ANIM, true);
      //  startActivity(intent);
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
    }

}
