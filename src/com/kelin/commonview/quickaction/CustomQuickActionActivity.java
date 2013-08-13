package com.kelin.commonview.quickaction;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.kelin.commonview.R;

public class CustomQuickActionActivity extends Activity {
      private Button mQCTest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_quickaction);
        mQCTest=(Button)findViewById(R.id.btn_qc_test);
//        mQCTest.setOnClickListener(new OnClickListener() {
//            
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                
//            }
//        });
    }
    public void  showQuickAction(View v){
      CustomQuickAction qa=new CustomQuickAction(v);
      for(int i=0;i<5;i++){
      ActionItem item=new ActionItem();
      item.setTitle("Action Item "+i);
      qa.addActionItem(item);
    
      }
      qa.show();
    }
}
