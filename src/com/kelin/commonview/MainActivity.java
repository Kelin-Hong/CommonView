package com.kelin.commonview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.kelin.commonview.indicator.activity.IndicatorListActivity;

public class MainActivity extends Activity implements OnItemClickListener {
    String[] data={"indicator","progress"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ListView listView=(ListView)findViewById(R.id.list);
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
	
		
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
		Intent intent=new Intent(this,IndicatorListActivity.class);
		startActivity(intent);
	}
	
  
}
