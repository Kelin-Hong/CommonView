package com.kelin.commonview.titlebar;

import android.app.Activity;
import android.view.Gravity;
import android.widget.ImageButton;

import com.kelin.commonview.titlebar.Header.OnBackStackListener;


public class TitleBarUtils {

    /**
     * @param activity
     * @param titlebarId
     * @param logoResId Pass "-1" to use default logo
     * @param titleResId Pass "-1" if no title text
     * @return
     */
    public static ImageButton setupMainTitlebar(Activity activity, int titlebarId,
            int logoResId, int titleResId) {
        return setupMainTitlebar(activity, titlebarId, logoResId, titleResId, null);
    }
    public static ImageButton setupMainTitlebar(Activity activity, int titlebarId,
            int logoResId, int titleResId, OnBackStackListener listener) {
        String title = titleResId != -1 ? activity.getString(titleResId) : null;
        return setupMainTitlebar(activity, titlebarId, logoResId, title, listener);
    }
    public static ImageButton setupMainTitlebar(Activity activity, int titlebarId,
            int logoResId, CharSequence title, OnBackStackListener listener) {
        Header header = setupMainHeader(activity, titlebarId, title, logoResId, listener);
        return header.mSettingButton;
    }
    public static Header setupMainHeader(Activity activity, int titlebarId, CharSequence title, int logoRes,
            OnBackStackListener listener) {
        Header header = new Header(activity, titlebarId);
        header.setTitle(title);
        header.setLogo(logoRes);
        header.setBackListener(listener);
        return header;
    }
    public static void setMainTitleBarTextLeftAlign(Activity activity, int titlebarId) {
        Header header = new Header(activity, titlebarId);
        header.setTitleLeftAlign();
    }
   
}
