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

final class zze
extends zzbr {
    private static final String ID = zzbg.zzht.toString();
    private final zza zzkcu;

    public zze(Context context) {
        this(zza.zzeb(context));
    }

    private zze(zza zza2) {
        super(ID, new String[0]);
        this.zzkcu = zza2;
        this.zzkcu.zzbdj();
    }

    @Override
    public final boolean zzbdp() {
        return false;
    }

    @Override
    public final zzbs zzv(Map<String, zzbs> object) {
        object = this.zzkcu.zzbdj();
        if (object == null) {
            return zzgk.zzbgs();
        }
        return zzgk.zzam(object);
    }
}

