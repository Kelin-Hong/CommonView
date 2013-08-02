package com.kelin.commonview.indicator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.commonview.R;
import com.kelin.commonview.BaseFragment;

public class AllFragment extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_sample,
                container, false);
        TextView textView = new TextView(getActivity());
        textView.setText("page 1");
        rootView.addView(textView);
        return rootView;

    }
}