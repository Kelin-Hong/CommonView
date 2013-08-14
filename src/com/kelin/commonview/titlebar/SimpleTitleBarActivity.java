package com.kelin.commonview.titlebar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.kelin.commonview.R;

public class SimpleTitleBarActivity extends Activity{
  private ImageButton mSettingsView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_title_bar);
        mSettingsView = TitleBarUtils.setupMainTitlebar(this, R.id.titlebar,
                R.drawable.ic_launcher, R.string.app_name);
        TitleBarUtils.setMainTitleBarTextLeftAlign(this, R.id.titlebar);
        mSettingsView.setVisibility(View.VISIBLE);
        mSettingsView.setImageResource(R.drawable.menu_more);
    }
  
}
