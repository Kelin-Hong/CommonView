package com.kelin.commonview.smartdrawer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.kelin.commonview.R;

public class SmartDrawerActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smartdrawer);
        getData();
    }
    public void getData(){
        List<Map<String,Object>> menuList=new ArrayList<Map<String,Object>>();
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("name","kelin hong");
        map.put("type","hust wuhan");
        map.put("avatar", R.drawable.conn_no_photo);
        menuList.add(map);
        Map<String,Object> map1=new HashMap<String, Object>();
        map1.put("name","kelin hong");
        map1.put("type","hust wuhan");
        map1.put("avatar", R.drawable.conn_no_photo);
        menuList.add(map1);
        Map<String,Object> map2=new HashMap<String, Object>();
        map2.put("name","kelin");
        map2.put("type","hust wuhan");
        map2.put("avatar", R.drawable.conn_no_photo);
        menuList.add(map2);
        Map<String,Object> map3=new HashMap<String, Object>();
     
        setListAdapter(new SimpleAdapter(this,menuList,R.layout.listitem_smartdrawer,new String[]{"name","type","avatar"},new int[]{R.id.name,R.id.school,R.id.avatar}));
    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);
        SmartDrawer smartDrawer=(SmartDrawer)v.findViewById(R.id.drawer);
        smartDrawer.animateToggle();
    }
    public void dismissSmartDrawer(View v){
        final LinearLayout linearLayout=  (LinearLayout)v;
        linearLayout.postDelayed(new Runnable() {
            
            @Override
            public void run() {
                SmartDrawer smartDrawer=(SmartDrawer) linearLayout.getParent().getParent();
                smartDrawer.animateClose();
            }
        }, 200);
    }
}
