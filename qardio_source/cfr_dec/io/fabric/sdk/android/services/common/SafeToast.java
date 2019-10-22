/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Handler
 *  android.os.Looper
 *  android.widget.Toast
 */
package io.fabric.sdk.android.services.common;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;
import io.fabric.sdk.android.services.concurrency.PriorityRunnable;

public class SafeToast
extends Toast {
    public SafeToast(Context context) {
        super(context);
    }

    public void show() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            super.show();
            return;
        }
        new Handler(Looper.getMainLooper()).post((Runnable)new PriorityRunnable(){

            @Override
            public void run() {
                SafeToast.super.show();
            }
        });
    }

}

