/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.google.android.gms.tagmanager;

import android.util.Log;
import com.google.android.gms.tagmanager.zzdk;

public final class zzba
implements zzdk {
    private int zzdvo = 5;

    @Override
    public final void e(String string2) {
        if (this.zzdvo <= 6) {
            Log.e((String)"GoogleTagManager", (String)string2);
        }
    }

    @Override
    public final void v(String string2) {
        if (this.zzdvo <= 2) {
            Log.v((String)"GoogleTagManager", (String)string2);
        }
    }

    @Override
    public final void zzb(String string2, Throwable throwable) {
        if (this.zzdvo <= 6) {
            Log.e((String)"GoogleTagManager", (String)string2, (Throwable)throwable);
        }
    }

    @Override
    public final void zzc(String string2, Throwable throwable) {
        if (this.zzdvo <= 5) {
            Log.w((String)"GoogleTagManager", (String)string2, (Throwable)throwable);
        }
    }

    @Override
    public final void zzct(String string2) {
        if (this.zzdvo <= 4) {
            Log.i((String)"GoogleTagManager", (String)string2);
        }
    }

    @Override
    public final void zzcu(String string2) {
        if (this.zzdvo <= 5) {
            Log.w((String)"GoogleTagManager", (String)string2);
        }
    }
}

