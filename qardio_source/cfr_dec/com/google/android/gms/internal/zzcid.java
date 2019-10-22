/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.BroadcastReceiver$PendingResult
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.ActivityInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.net.Uri
 *  android.os.Bundle
 */
package com.google.android.gms.internal;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzchm;
import com.google.android.gms.internal.zzcho;
import com.google.android.gms.internal.zzcie;
import com.google.android.gms.internal.zzcif;
import com.google.android.gms.internal.zzcih;
import com.google.android.gms.internal.zzcim;
import com.google.android.gms.internal.zzclq;

public final class zzcid {
    private final zzcif zzjds;

    public zzcid(zzcif zzcif2) {
        zzbq.checkNotNull(zzcif2);
        this.zzjds = zzcif2;
    }

    public static boolean zzbk(Context context) {
        block6: {
            PackageManager packageManager;
            block5: {
                zzbq.checkNotNull(context);
                packageManager = context.getPackageManager();
                if (packageManager != null) break block5;
                return false;
            }
            context = packageManager.getReceiverInfo(new ComponentName(context, "com.google.android.gms.measurement.AppMeasurementReceiver"), 2);
            if (context == null) break block6;
            try {
                boolean bl = context.enabled;
                if (bl) {
                    return true;
                }
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {
                // empty catch block
            }
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void onReceive(Context context, Intent intent) {
        zzcim zzcim2 = zzcim.zzdx(context);
        zzchm zzchm2 = zzcim2.zzawy();
        if (intent == null) {
            zzchm2.zzazf().log("Receiver called with null intent");
            return;
        }
        String string2 = intent.getAction();
        zzchm2.zzazj().zzj("Local receiver got", string2);
        if ("com.google.android.gms.measurement.UPLOAD".equals(string2)) {
            intent = new Intent().setClassName(context, "com.google.android.gms.measurement.AppMeasurementService");
            intent.setAction("com.google.android.gms.measurement.UPLOAD");
            zzchm2.zzazj().log("Starting wakeful intent.");
            this.zzjds.doStartService(context, intent);
            return;
        }
        if (!"com.android.vending.INSTALL_REFERRER".equals(string2)) return;
        BroadcastReceiver.PendingResult pendingResult = this.zzjds.doGoAsync();
        String string3 = intent.getStringExtra("referrer");
        if (string3 == null) {
            zzchm2.zzazj().log("Install referrer extras are null");
            if (pendingResult == null) return;
            {
                pendingResult.finish();
                return;
            }
        }
        zzchm2.zzazh().zzj("Install referrer extras are", string3);
        string2 = string3;
        if (!string3.contains("?")) {
            string2 = String.valueOf(string3);
            string2 = string2.length() != 0 ? "?".concat(string2) : new String("?");
        }
        string2 = Uri.parse((String)string2);
        string2 = zzcim2.zzawu().zzp((Uri)string2);
        if (string2 == null) {
            zzchm2.zzazj().log("No campaign defined in install referrer broadcast");
            if (pendingResult == null) return;
            {
                pendingResult.finish();
                return;
            }
        }
        long l = 1000L * intent.getLongExtra("referrer_timestamp_seconds", 0L);
        if (l == 0L) {
            zzchm2.zzazf().log("Install referrer is missing timestamp");
        }
        zzcim2.zzawx().zzg(new zzcie(this, zzcim2, l, (Bundle)string2, context, zzchm2, pendingResult));
    }
}

