/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.zzbg;
import com.google.android.gms.internal.zzbh;
import com.google.android.gms.internal.zzbs;
import com.google.android.gms.tagmanager.zzbr;
import com.google.android.gms.tagmanager.zzcx;
import com.google.android.gms.tagmanager.zzgk;
import java.util.Map;

final class zzcw
extends zzbr {
    private static final String ID = zzbg.zzjf.toString();
    private static final String zzkcv = zzbh.zzob.toString();
    private final Context zzair;

    public zzcw(Context context) {
        super(ID, new String[0]);
        this.zzair = context;
    }

    @Override
    public final boolean zzbdp() {
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final zzbs zzv(Map<String, zzbs> object) {
        if ((object = zzcx.zzaj(this.zzair, (String)(object = (zzbs)object.get(zzkcv) != null ? zzgk.zzb((zzbs)object.get(zzkcv)) : null))) != null) {
            return zzgk.zzam(object);
        }
        return zzgk.zzbgs();
    }
}

