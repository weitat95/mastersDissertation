/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.ActivityNotFoundException
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.Intent
 *  android.util.Log
 */
package com.google.android.gms.common.internal;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;
import com.google.android.gms.common.api.internal.zzcf;
import com.google.android.gms.common.internal.zzw;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.common.internal.zzy;

public abstract class zzv
implements DialogInterface.OnClickListener {
    public static zzv zza(Activity activity, Intent intent, int n) {
        return new zzw(intent, activity, n);
    }

    public static zzv zza(Fragment fragment, Intent intent, int n) {
        return new zzx(intent, fragment, n);
    }

    public static zzv zza(zzcf zzcf2, Intent intent, int n) {
        return new zzy(intent, zzcf2, 2);
    }

    public void onClick(DialogInterface dialogInterface, int n) {
        try {
            this.zzale();
            return;
        }
        catch (ActivityNotFoundException activityNotFoundException) {
            Log.e((String)"DialogRedirect", (String)"Failed to start resolution intent", (Throwable)activityNotFoundException);
            return;
        }
        finally {
            dialogInterface.dismiss();
        }
    }

    protected abstract void zzale();
}

