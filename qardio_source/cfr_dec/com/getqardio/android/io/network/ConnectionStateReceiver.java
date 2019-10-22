/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 */
package com.getqardio.android.io.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.getqardio.android.CustomApplication;
import com.getqardio.android.mvp.MvpApplication;
import com.getqardio.android.provider.SyncHelper;
import com.getqardio.android.utils.ApplicationUtils;
import timber.log.Timber;

public class ConnectionStateReceiver
extends BroadcastReceiver {
    private boolean firstEvent = true;

    /*
     * Enabled aggressive block sorting
     */
    public void onReceive(Context context, Intent object) {
        if (this.firstEvent) {
            this.firstEvent = false;
            return;
        } else {
            Long l;
            Timber.d("onReceive", new Object[0]);
            object = ((MvpApplication)context.getApplicationContext()).getSyncHelper();
            if (!ApplicationUtils.isApplicationInForeground(context) || (l = CustomApplication.getApplication().getCurrentUserId()) == null) return;
            {
                ((SyncHelper)object).syncAll(context, l);
                return;
            }
        }
    }
}

