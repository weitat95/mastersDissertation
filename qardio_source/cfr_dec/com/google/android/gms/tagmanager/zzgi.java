/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzbs;
import com.google.android.gms.tagmanager.zzbr;
import com.google.android.gms.tagmanager.zzgk;
import java.util.Map;

abstract class zzgi
extends zzbr {
    public zzgi(String string2, String ... arrstring) {
        super(string2, arrstring);
    }

    @Override
    public boolean zzbdp() {
        return false;
    }

    @Override
    public zzbs zzv(Map<String, zzbs> map) {
        this.zzx(map);
        return zzgk.zzbgs();
    }

    public abstract void zzx(Map<String, zzbs> var1);
}

