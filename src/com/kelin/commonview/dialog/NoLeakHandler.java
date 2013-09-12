package com.kelin.commonview.dialog;

import java.lang.ref.WeakReference;

import android.os.Handler;
import android.os.Message;

public abstract class NoLeakHandler<T>  extends Handler {
    private final WeakReference<T> mContext;

    public NoLeakHandler(T context) {
        mContext = new WeakReference<T>(context);
    }

    @Override
    public void handleMessage(Message msg) {
        T context = mContext.get();
        if (context != null) {
            processMessage(context, msg);
        }
    }

    protected abstract void processMessage(T context, Message msg);
}
