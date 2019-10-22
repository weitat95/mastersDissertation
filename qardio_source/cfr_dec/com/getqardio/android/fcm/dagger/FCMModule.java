/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.getqardio.android.fcm.dagger;

import android.content.Context;
import com.getqardio.android.fcm.FCMManager;
import com.getqardio.android.mvp.MvpApplication;

public class FCMModule {
    private final Context context;

    public FCMModule(Context context) {
        this.context = context.getApplicationContext();
    }

    public FCMManager provideFCMManager() {
        return ((MvpApplication)this.context).getFCMManager();
    }
}

