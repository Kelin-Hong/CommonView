package com.kelin.commonview.indicator.activity;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class IndicatorListActivity extends ListActivity {
	String[] data = { "TitleIndicator", "CirclePageIndicator" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, data);
		getListView().setAdapter(adapter);
	}

	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> myData = new ArrayList<Map<String, Object>>();
		PackageManager pm = getPackageManager();
		Intent intent = new Intent(Intent.ACTION_MAIN, null);
		List<ResolveInfo> list=pm.queryIntentActivities(intent,0);
		if(null==list) return null;
		for(int i=0;i<list.size();i++){
			ResolveInfo info=list.get(i);
			
		}
		return null;
	}

	private Comparator<Map<String, Object>> comparator = new Comparator<Map<String, Object>>() {
		private Collator collator = Collator.getInstance();

		@Override
		public int compare(Map<String, Object> lhs, Map<String, Object> rhs) {
			return collator.compare(lhs.get("title"), rhs.get("title"));
		}
	};

	protected void addItem(List<Map<String, Object>> data, String name,
			Intent intent) {
		Map<String, Object> temp = new HashMap<String, Object>();
		temp.put("title", name);
		temp.put("intent", intent);
		data.add(temp);
	}

	protected Intent activityIntent(String pkg, String componentName) {
		Intent result = new Intent();
		result.setClassName(pkg, componentName);
		return result;
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		switch (position) {
		case 0:
			Intent intent0 = new Intent(IndicatorListActivity.this,
					TitleIndicatorActivity.class);
			startActivity(intent0);break;
		case 1:
			Intent intent1 = new Intent(IndicatorListActivity.this,
					CirclePageIndicatorActivity.class);
			startActivity(intent1);break;
		}
	}

}
