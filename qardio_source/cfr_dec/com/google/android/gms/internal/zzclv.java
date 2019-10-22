/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzclt;
import com.google.android.gms.internal.zzfjj;
import com.google.android.gms.internal.zzfjk;
import com.google.android.gms.internal.zzfjm;
import com.google.android.gms.internal.zzfjo;
import com.google.android.gms.internal.zzfjq;
import com.google.android.gms.internal.zzfjs;
import java.io.IOException;

public final class zzclv
extends zzfjm<zzclv> {
    private static volatile zzclv[] zzjkl;
    public Integer zzjjw = null;
    public String zzjkm = null;
    public zzclt zzjkn = null;

    public zzclv() {
        this.zzpnc = null;
        this.zzpfd = -1;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static zzclv[] zzbbd() {
        if (zzjkl == null) {
            Object object = zzfjq.zzpnk;
            synchronized (object) {
                if (zzjkl == null) {
                    zzjkl = new zzclv[0];
                }
            }
        }
        return zzjkl;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean equals(Object object) {
        block9: {
            block8: {
                if (object == this) break block8;
                if (!(object instanceof zzclv)) {
                    return false;
                }
                object = (zzclv)object;
                if (this.zzjjw == null ? ((zzclv)object).zzjjw != null : !this.zzjjw.equals(((zzclv)object).zzjjw)) {
                    return false;
                }
                if (this.zzjkm == null ? ((zzclv)object).zzjkm != null : !this.zzjkm.equals(((zzclv)object).zzjkm)) {
                    return false;
                }
                if (this.zzjkn == null ? ((zzclv)object).zzjkn != null : !this.zzjkn.equals(((zzclv)object).zzjkn)) {
                    return false;
                }
                if (this.zzpnc != null && !this.zzpnc.isEmpty()) {
                    return this.zzpnc.equals(((zzclv)object).zzpnc);
                }
                if (((zzclv)object).zzpnc != null && !((zzclv)object).zzpnc.isEmpty()) break block9;
            }
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final int hashCode() {
        int n = 0;
        int n2 = this.getClass().getName().hashCode();
        int n3 = this.zzjjw == null ? 0 : this.zzjjw.hashCode();
        int n4 = this.zzjkm == null ? 0 : this.zzjkm.hashCode();
        zzclt zzclt2 = this.zzjkn;
        int n5 = zzclt2 == null ? 0 : zzclt2.hashCode();
        int n6 = n;
        if (this.zzpnc == null) return (n5 + (n4 + (n3 + (n2 + 527) * 31) * 31) * 31) * 31 + n6;
        if (this.zzpnc.isEmpty()) {
            n6 = n;
            return (n5 + (n4 + (n3 + (n2 + 527) * 31) * 31) * 31) * 31 + n6;
        }
        n6 = this.zzpnc.hashCode();
        return (n5 + (n4 + (n3 + (n2 + 527) * 31) * 31) * 31) * 31 + n6;
    }

    @Override
    public final /* synthetic */ zzfjs zza(zzfjj zzfjj2) throws IOException {
        block6: do {
            int n = zzfjj2.zzcvt();
            switch (n) {
                default: {
                    if (super.zza(zzfjj2, n)) continue block6;
                }
                case 0: {
                    return this;
                }
                case 8: {
                    this.zzjjw = zzfjj2.zzcwi();
                    continue block6;
                }
                case 18: {
                    this.zzjkm = zzfjj2.readString();
                    continue block6;
                }
                case 26: 
            }
            if (this.zzjkn == null) {
                this.zzjkn = new zzclt();
            }
            zzfjj2.zza(this.zzjkn);
        } while (true);
    }

    @Override
    public final void zza(zzfjk zzfjk2) throws IOException {
        if (this.zzjjw != null) {
            zzfjk2.zzaa(1, this.zzjjw);
        }
        if (this.zzjkm != null) {
            zzfjk2.zzn(2, this.zzjkm);
        }
        if (this.zzjkn != null) {
            zzfjk2.zza(3, this.zzjkn);
        }
        super.zza(zzfjk2);
    }

    @Override
    protected final int zzq() {
        int n;
        int n2 = n = super.zzq();
        if (this.zzjjw != null) {
            n2 = n + zzfjk.zzad(1, this.zzjjw);
        }
        n = n2;
        if (this.zzjkm != null) {
            n = n2 + zzfjk.zzo(2, this.zzjkm);
        }
        n2 = n;
        if (this.zzjkn != null) {
            n2 = n + zzfjk.zzb(3, this.zzjkn);
        }
        return n2;
    }
}

