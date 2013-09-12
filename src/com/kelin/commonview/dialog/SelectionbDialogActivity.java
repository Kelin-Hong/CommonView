package com.kelin.commonview.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.kelin.commonview.R;

public class SelectionbDialogActivity extends Activity {
    public static final int[] sLanguage = {
            R.string.language_auto,
            R.string.language_cn,
            R.string.language_tw,
            R.string.language_en
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        TextView textView=new TextView(this);
        setContentView(textView);
        showLanguageSettingDialog();
    }

    private void showLanguageSettingDialog() {
        final SelectionDialog dlg = new SelectionDialog(this);
        dlg.setTitle("select language");
        dlg.setEntries(sLanguage).setValue(0);
        dlg.setOnSelectListener(new SelectionDialog.OnSelectListener() {
            @Override
            public void onSelect(int position) {
            }
        });
        dlg.show();
    }
}
