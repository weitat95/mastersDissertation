/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.os.Bundle
 *  android.os.Parcelable
 */
package com.google.android.gms.common.internal;

import android.app.PendingIntent;
import android.os.Bundle;
import android.os.Parcelable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.zzd;
import com.google.android.gms.common.internal.zzi;

abstract class zze
extends zzi<Boolean> {
    private int statusCode;
    private Bundle zzfyz;
    private /* synthetic */ zzd zzfza;

    protected zze(zzd zzd2, int n, Bundle bundle) {
        this.zzfza = zzd2;
        super(zzd2, (Object)true);
        this.statusCode = n;
        this.zzfyz = bundle;
    }

    protected abstract boolean zzakr();

    protected abstract void zzj(ConnectionResult var1);

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected final /* synthetic */ void zzw(Object object) {
        Object var2_2 = null;
        if ((Boolean)object == null) {
            zzd.zza(this.zzfza, 1, null);
            return;
        }
        switch (this.statusCode) {
            default: {
                zzd.zza(this.zzfza, 1, null);
                object = var2_2;
                if (this.zzfyz != null) {
                    object = (PendingIntent)this.zzfyz.getParcelable("pendingIntent");
                }
                this.zzj(new ConnectionResult(this.statusCode, (PendingIntent)object));
                return;
            }
            case 0: {
                if (this.zzakr()) return;
                {
                    zzd.zza(this.zzfza, 1, null);
                    this.zzj(new ConnectionResult(8, null));
                    return;
                }
            }
            case 10: 
        }
        zzd.zza(this.zzfza, 1, null);
        throw new IllegalStateException("A fatal developer error has occurred. Check the logs for further information.");
    }
}

