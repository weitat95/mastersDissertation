/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.zzbg;
import com.google.android.gms.internal.zzbs;
import com.google.android.gms.tagmanager.zza;
import com.google.android.gms.tagmanager.zzbr;
import com.google.android.gms.tagmanager.zzgk;
import java.util.Map;

final class zzf
extends zzbr {
    private static final String ID = zzbg.zzhu.toString();
    private final zza zzkcu;

    public zzf(Context context) {
        this(zza.zzeb(context));
    }

    private zzf(zza zza2) {
        super(ID, new String[0]);
        this.zzkcu = zza2;
    }

    @Override
    public final boolean zzbdp() {
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public final zzbs zzv(Map<String, zzbs> map) {
        boolean bl;
        if (!this.zzkcu.isLimitAdTrackingEnabled()) {
            bl = true;
            do {
                return zzgk.zzam(bl);
                break;
            } while (true);
        }
        bl = false;
        return zzgk.zzam(bl);
    }
}

