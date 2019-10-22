/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.analytics.CampaignTrackingReceiver;
import com.google.android.gms.tagmanager.zzcx;

public final class InstallReferrerReceiver
extends CampaignTrackingReceiver {
    @Override
    protected final void zzr(Context context, String string2) {
        zzcx.zzlr(string2);
        zzcx.zzai(context, string2);
    }
}

