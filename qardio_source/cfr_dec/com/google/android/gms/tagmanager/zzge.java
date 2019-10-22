/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.tagmanager.zzei;

final class zzge {
    static final /* synthetic */ int[] zzkkf;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static {
        zzkkf = new int[zzei.zza.values().length];
        try {
            zzge.zzkkf[zzei.zza.zzkhq.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            zzge.zzkkf[zzei.zza.zzkhr.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            zzge.zzkkf[zzei.zza.zzkhs.ordinal()] = 3;
            return;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            return;
        }
    }
}

