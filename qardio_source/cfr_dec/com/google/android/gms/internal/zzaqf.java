/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbq;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class zzaqf {
    private final Map<String, String> zzbsr;
    private final String zzdrv;
    private final long zzdtt;
    private final String zzdtu;
    private final boolean zzdtv;
    private long zzdtw;

    public zzaqf(long l, String string2, String string3, boolean bl, long l2, Map<String, String> map) {
        zzbq.zzgm(string2);
        zzbq.zzgm(string3);
        this.zzdtt = 0L;
        this.zzdrv = string2;
        this.zzdtu = string3;
        this.zzdtv = bl;
        this.zzdtw = l2;
        if (map != null) {
            this.zzbsr = new HashMap<String, String>(map);
            return;
        }
        this.zzbsr = Collections.emptyMap();
    }

    public final Map<String, String> zzjh() {
        return this.zzbsr;
    }

    public final void zzm(long l) {
        this.zzdtw = l;
    }

    public final String zzvz() {
        return this.zzdrv;
    }

    public final long zzxm() {
        return this.zzdtt;
    }

    public final String zzxn() {
        return this.zzdtu;
    }

    public final boolean zzxo() {
        return this.zzdtv;
    }

    public final long zzxp() {
        return this.zzdtw;
    }
}

