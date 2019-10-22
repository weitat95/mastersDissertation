/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.BroadcastReceiver$PendingResult
 */
package com.google.android.gms.analytics;

import android.content.BroadcastReceiver;
import com.google.android.gms.analytics.CampaignTrackingReceiver;

final class zzc
implements Runnable {
    private /* synthetic */ BroadcastReceiver.PendingResult zzdop;

    zzc(CampaignTrackingReceiver campaignTrackingReceiver, BroadcastReceiver.PendingResult pendingResult) {
        this.zzdop = pendingResult;
    }

    @Override
    public final void run() {
        if (this.zzdop != null) {
            this.zzdop.finish();
        }
    }
}

