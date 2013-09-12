package com.kelin.commonview.dialog;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.kelin.commonview.R;

public class SelectionDialog extends CommonDialog {
    public static interface OnSelectListener {
        void onSelect(int which);
    }

    private static String KEY = "key";
    private int mValue;
    private ArrayList<HashMap<String, CharSequence>> mEntries;
    private Context mContext;
    private OnSelectListener mOnSelectListener;

    public SelectionDialog(Context context) {
        super(context);
        mContext = context;
    }

    public SelectionDialog setEntries(int[] resids) {
        if (resids == null) return this;
        mEntries = new ArrayList<HashMap<String, CharSequence>>(resids.length);
        for (int i = 0; i < resids.length; i++) {
            HashMap<String, CharSequence> value = new HashMap<String, CharSequence>();
            value.put(KEY, mContext.getString(resids[i]));
            mEntries.add(value);
        }
        return this;
    }

    public SelectionDialog setEntries(CharSequence[] entries) {
        if (entries == null) return this;
        mEntries = new ArrayList<HashMap<String, CharSequence>>(entries.length);
        for (int i = 0; i < entries.length; i++) {
            HashMap<String, CharSequence> value = new HashMap<String, CharSequence>();
            value.put(KEY, entries[i]);
            mEntries.add(value);
        }
        return this;
    }

    public SelectionDialog setValue(int selection) {
        mValue = selection;
        return this;
    }

    public SelectionDialog setOnSelectListener(OnSelectListener listener) {
        mOnSelectListener = listener;
        return this;
    }

    public void show() {
        if (mEntries != null && mEntries.size() > 0) {
        ListView listView = setListAdapter(new SimpleAdapter(mContext, mEntries,
                R.layout.common_dialog_list_item_checkedtext, new String[] { KEY },
                new int[] { R.id.text }), mValue);
        if (mValue >= 0) listView.setSelection(mValue);

        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
                dismiss();
                if (mOnSelectListener != null) {
                    mOnSelectListener.onSelect(position);
                }
            }
        });
        }
        super.show();
    }
}
