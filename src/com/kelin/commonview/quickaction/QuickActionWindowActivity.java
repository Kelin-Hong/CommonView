package com.kelin.commonview.quickaction;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.kelin.commonview.R;

public class QuickActionWindowActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_quickaction);
    }

    public  void showQuickAction(View v) {

        int[] xy = new int[2];
        v.getLocationInWindow(xy);
        Rect rect = new Rect(xy[0], xy[1], xy[0] + v.getWidth(), xy[1] + v.getHeight());
        final QuickActionWindow qa = new QuickActionWindow(this, v, rect);
        qa.addItem(getResources().getDrawable(android.R.drawable.ic_menu_my_calendar),
                "ActionItem1", new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        qa.dismiss();
                    }
                });

      
            qa.addItem(getResources().getDrawable(android.R.drawable.ic_menu_view),
                    "ActionItem2", new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            
                            qa.dismiss();
                         
                        }
                    });
       
       
       
            qa.addItem(getResources().getDrawable(android.R.drawable.ic_menu_close_clear_cancel),
                    "ActionItem3", new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                          
                            qa.dismiss();
                        }
                    });
            qa.addItem(getResources().getDrawable(android.R.drawable.ic_menu_mylocation),
                    "ActionItem4", new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                       
                            qa.dismiss();
                        }
            });
        qa.show();
    }
}
