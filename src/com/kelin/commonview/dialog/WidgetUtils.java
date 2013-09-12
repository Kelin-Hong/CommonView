package com.kelin.commonview.dialog;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.kelin.commonview.R;
import com.kelin.commonview.titlebar.Header.OnBackStackListener;

public class WidgetUtils {

    public static View getListSeparatorView(Context cxt) {
        View sepView = new View(cxt);
        sepView.setVisibility(View.INVISIBLE);
        sepView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 2)); // 2 pixels height
        sepView.setBackgroundResource(R.drawable.list_divider);
        return sepView;
    }

    public static void applyDxButtonGreenBigStyle(TextView v) {
        Resources res = v.getContext().getResources();
        v.setBackgroundResource(R.drawable.dx_btn_green_big);
        v.setTextColor(res.getColor(R.color.common_white));
        // v.setShadowLayer(0, 1f, 1f, res.getColor(R.color.common_white_shadow));
    }

    public static void applyDxButtonWhiteBigStyle(TextView v) {
        Resources res = v.getContext().getResources();
        v.setBackgroundResource(R.drawable.dx_btn_white_big);
        v.setTextColor(res.getColor(R.color.common_dark));
        // v.setShadowLayer(0, 1f, 1f, res.getColor(R.color.common_dark_shadow));
    }

}
