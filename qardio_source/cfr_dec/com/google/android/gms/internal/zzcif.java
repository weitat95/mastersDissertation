/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.BroadcastReceiver$PendingResult
 *  android.content.Context
 *  android.content.Intent
 */
package com.google.android.gms.internal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public interface zzcif {
    public BroadcastReceiver.PendingResult doGoAsync();

    public void doStartService(Context var1, Intent var2);
}

