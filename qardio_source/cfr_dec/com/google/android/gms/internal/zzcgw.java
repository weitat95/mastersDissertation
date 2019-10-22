/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbq;

final class zzcgw {
    final String mAppId;
    final String mName;
    final long zzizk;
    final long zzizl;
    final long zzizm;
    final long zzizn;
    final Long zzizo;
    final Long zzizp;
    final Boolean zzizq;

    /*
     * Enabled aggressive block sorting
     */
    zzcgw(String string2, String string3, long l, long l2, long l3, long l4, Long l5, Long l6, Boolean bl) {
        zzbq.zzgm(string2);
        zzbq.zzgm(string3);
        boolean bl2 = l >= 0L;
        zzbq.checkArgument(bl2);
        bl2 = l2 >= 0L;
        zzbq.checkArgument(bl2);
        bl2 = l4 >= 0L;
        zzbq.checkArgument(bl2);
        this.mAppId = string2;
        this.mName = string3;
        this.zzizk = l;
        this.zzizl = l2;
        this.zzizm = l3;
        this.zzizn = l4;
        this.zzizo = l5;
        this.zzizp = l6;
        this.zzizq = bl;
    }

    final zzcgw zza(Long l, Long l2, Boolean bl) {
        if (bl != null && !bl.booleanValue()) {
            bl = null;
        }
        return new zzcgw(this.mAppId, this.mName, this.zzizk, this.zzizl, this.zzizm, this.zzizn, l, l2, bl);
    }

    final zzcgw zzayw() {
        return new zzcgw(this.mAppId, this.mName, this.zzizk + 1L, this.zzizl + 1L, this.zzizm, this.zzizn, this.zzizo, this.zzizp, this.zzizq);
    }

    final zzcgw zzbb(long l) {
        return new zzcgw(this.mAppId, this.mName, this.zzizk, this.zzizl, l, this.zzizn, this.zzizo, this.zzizp, this.zzizq);
    }

    final zzcgw zzbc(long l) {
        return new zzcgw(this.mAppId, this.mName, this.zzizk, this.zzizl, this.zzizm, l, this.zzizo, this.zzizp, this.zzizq);
    }
}

