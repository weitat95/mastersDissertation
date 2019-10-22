/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.BroadcastReceiver$PendingResult
 *  android.content.Context
 *  android.content.Intent
 *  android.text.TextUtils
 */
package com.google.android.gms.analytics;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.google.android.gms.analytics.zzc;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzapr;
import com.google.android.gms.internal.zzaqc;
import com.google.android.gms.internal.zzard;
import com.google.android.gms.internal.zzarv;
import com.google.android.gms.internal.zzasl;

public class CampaignTrackingReceiver
extends BroadcastReceiver {
    private static Boolean zzdoo;

    public static boolean zzbk(Context context) {
        zzbq.checkNotNull(context);
        if (zzdoo != null) {
            return zzdoo;
        }
        boolean bl = zzasl.zzb(context, "com.google.android.gms.analytics.CampaignTrackingReceiver", true);
        zzdoo = bl;
        return bl;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public void onReceive(Context object, Intent object2) {
        void var1_3;
        void var2_5;
        zzaqc zzaqc2 = zzaqc.zzbm(object);
        zzarv zzarv2 = zzaqc2.zzwt();
        if (var2_5 == null) {
            zzarv2.zzdx("CampaignTrackingReceiver received null intent");
            return;
        }
        String string2 = var2_5.getStringExtra("referrer");
        String string3 = var2_5.getAction();
        zzarv2.zza("CampaignTrackingReceiver received", string3);
        if (!"com.android.vending.INSTALL_REFERRER".equals(string3) || TextUtils.isEmpty((CharSequence)string2)) {
            zzarv2.zzdx("CampaignTrackingReceiver received unexpected intent without referrer extra");
            return;
        }
        this.zzr((Context)object, string2);
        int n = zzard.zzyr();
        if (string2.length() <= n) {
            String string4 = string2;
        } else {
            zzarv2.zzc("Campaign data exceed the maximum supported size and will be clipped. size, limit", string2.length(), n);
            String string5 = string2.substring(0, n);
        }
        BroadcastReceiver.PendingResult pendingResult = this.goAsync();
        zzaqc2.zzwx().zza((String)var1_3, new zzc(this, pendingResult));
    }

    protected void zzr(Context context, String string2) {
    }
}

