package com.kelin.commonview.progressbar;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
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

public class ProgressBarListActivity extends ListActivity {
    private static final String INTENT_DEFAULT = "com.kelin.commonview.progressbar.SAMPLE";
    List<Map<String, Object>> mData = new ArrayList<Map<String, Object>>();
    List<String> mTitleList=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        getData();
        Iterator<Map<String, Object>> it=mData.iterator();
        while(it.hasNext()){
            mTitleList.add(it.next().get("title").toString());
            
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, mTitleList);
        getListView().setAdapter(adapter);
    }

    private void getData() {
        PackageManager pm = getPackageManager();
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(INTENT_DEFAULT);
        List<ResolveInfo> list = pm.queryIntentActivities(mainIntent, 0);
        if (null == list) return;
        for (int i = 0; i < list.size(); i++) {
            ResolveInfo info = list.get(i);
            String lable = info.loadLabel(pm).toString();
            if (lable.contains("/")) {
                String[] lableArr = lable.split("/");
                String title = lableArr[1];
                String packageName = info.activityInfo.applicationInfo.packageName;
                String className = info.activityInfo.name;
                Intent intent = new Intent();
                intent.setClassName(packageName, className);
                Map<String, Object> temp = new HashMap<String, Object>();
                temp.put("title", title);
                temp.put("intent", intent);
                mData.add(temp);
            }
        }
        Collections.sort(mData, comparator);
        return;
    }

    private Comparator<Map<String, Object>> comparator = new Comparator<Map<String, Object>>() {
        private Collator collator = Collator.getInstance();

        @Override
        public int compare(Map<String, Object> lhs, Map<String, Object> rhs) {
            return collator.compare(lhs.get("title"), rhs.get("title"));
        }
    };



    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);
        startActivity((Intent)mData.get(position).get("intent"));
    }
}
