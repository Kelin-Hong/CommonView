package com.kelin.commonview.indicator.activity;

import java.lang.reflect.Constructor;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.kelin.commonview.R;
import com.kelin.commonview.indicator.CirclePageIndicator;
import com.kelin.commonview.indicator.fragment.BaseFragment;
import com.kelin.commonview.indicator.fragment.TestFragment1;
import com.kelin.commonview.indicator.fragment.TestFragment2;
import com.kelin.commonview.indicator.fragment.TestFragment3;

public class CirclePageIndicatorActivity extends FragmentActivity{
    private Class[] fragmentclasslist={ TestFragment1.class,TestFragment2.class,TestFragment3.class};
	private ViewPager mViewPager;
	private CirclePageIndicator mIndicator;
    @Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_circle_indicator);
		mIndicator=(CirclePageIndicator)findViewById(R.id.indicator);
		mViewPager=(ViewPager)findViewById(R.id.pager);
		mViewPager.setAdapter(new CircleIndicatorAdapter(getSupportFragmentManager()));
		mIndicator.setViewPager(mViewPager);
	}
    class CircleIndicatorAdapter extends FragmentPagerAdapter{

		public CircleIndicatorAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {
			return createFragment(fragmentclasslist[arg0]);
		}

		@Override
		public int getCount() {
			return fragmentclasslist.length;
		}
	    public BaseFragment createFragment(Class fragmentClass) {
	    	BaseFragment fragment=null;
	            Constructor constructor;
	            try {
	                constructor = fragmentClass.getConstructor(new Class[0]);
	                fragment = (BaseFragment) constructor.newInstance(new Object[0]);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        
	        return fragment;
	    }
    }
}
