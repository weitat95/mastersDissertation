/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 */
package com.getqardio.android.utils.alarms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.getqardio.android.provider.DataHelper;

public class OnRebootReceiver
extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        DataHelper.ReminderHelper.requestReminderAlarmUpdate(context);
    }
}

