package com.kelin.commonview.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kelin.commonview.R;

public class ActivityListAdapter extends SectionedBaseAdapter {

    @Override
    public Object getItem(int section, int position) {

        return null;
    }

    @Override
    public long getItemId(int section, int position) {

        return 0;
    }

    @Override
    public int getSectionCount() {

        return 4;
    }

    @Override
    public int getCountForSection(int section) {

        return 7;
    }

    @Override
    public View getItemView(int section, int position, View convertView, ViewGroup parent) {
        final Context context=parent.getContext();
        LayoutInflater inflater=(LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ContentViewHolder holder=null;
        View view=convertView;
        if(view==null)
        {
        view=inflater.inflate(R.layout.listitem_activity,null);
        holder=new ContentViewHolder();
        //holder.image=(ImageView)view.findViewById(R.id.listitem_activity_img);
        holder.address=(TextView)view.findViewById(R.id.listitem_activity_address);
        holder.joinnum=(TextView)view.findViewById(R.id.listitem_activity_join_number);
        holder.time=(TextView)view.findViewById(R.id.listitem_activity_time);
        holder.name=(TextView)view.findViewById(R.id.listitem_activity_name);
        view.setTag(holder);
        }else
        {
          holder=(ContentViewHolder)view.getTag();
        }
      
         return view;
    }

    @Override
    public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {

       LayoutInflater inflater=(LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       HeaderViewHolder holder=null;
       View view=convertView;
       if(view==null)
       {
       view=inflater.inflate(R.layout.listitem_activity_header,null);
       holder=new HeaderViewHolder();
       holder.date=(TextView)view.findViewById(R.id.header_date);
       holder.dayOfWeek=(TextView)view.findViewById(R.id.header_day_of_week);
       view.setTag(holder);
       }else
       {
         holder=(HeaderViewHolder)view.getTag();
       }
        holder.date.setText("2013.12.12");
        holder.dayOfWeek.setText("周一");
        return view;
    }
    static class ContentViewHolder
    {
       // ImageView image;
        TextView name;
        TextView address;
        TextView joinnum;
        TextView time;
    }
     static class HeaderViewHolder
     {
         TextView date;
         TextView dayOfWeek;
     }
}
