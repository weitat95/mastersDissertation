/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 */
package com.getqardio.android.utils.gcm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.getqardio.android.CustomApplication;
import com.mixpanel.android.mpmetrics.GCMReceiver;
import timber.log.Timber;

public class MixpanelNotificationsBroadcastReceiver
extends GCMReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (CustomApplication.getApplication().getCurrentUserId() != null) {
            if (!intent.getExtras().containsKey("mp_icnm")) {
                intent.putExtra("mp_icnm", "q_white");
            }
            if (!intent.getExtras().containsKey("mp_icnm_w")) {
                intent.putExtra("mp_icnm_w", "q_white");
            }
            super.onReceive(context, intent);
        }
        Timber.d("onReceive %s", intent.toUri(0));
    }
}

