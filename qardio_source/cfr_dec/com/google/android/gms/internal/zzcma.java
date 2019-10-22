/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzcmf;
import com.google.android.gms.internal.zzfjj;
import com.google.android.gms.internal.zzfjk;
import com.google.android.gms.internal.zzfjm;
import com.google.android.gms.internal.zzfjo;
import com.google.android.gms.internal.zzfjq;
import com.google.android.gms.internal.zzfjs;
import java.io.IOException;

public final class zzcma
extends zzfjm<zzcma> {
    private static volatile zzcma[] zzjlc;
    public Integer zzjjs = null;
    public zzcmf zzjld = null;
    public zzcmf zzjle = null;
    public Boolean zzjlf = null;

    public zzcma() {
        this.zzpnc = null;
        this.zzpfd = -1;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static zzcma[] zzbbg() {
        if (zzjlc == null) {
            Object object = zzfjq.zzpnk;
            synchronized (object) {
                if (zzjlc == null) {
                    zzjlc = new zzcma[0];
                }
            }
        }
        return zzjlc;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean equals(Object object) {
        block10: {
            block9: {
                if (object == this) break block9;
                if (!(object instanceof zzcma)) {
                    return false;
                }
                object = (zzcma)object;
                if (this.zzjjs == null ? ((zzcma)object).zzjjs != null : !this.zzjjs.equals(((zzcma)object).zzjjs)) {
                    return false;
                }
                if (this.zzjld == null ? ((zzcma)object).zzjld != null : !this.zzjld.equals(((zzcma)object).zzjld)) {
                    return false;
                }
                if (this.zzjle == null ? ((zzcma)object).zzjle != null : !this.zzjle.equals(((zzcma)object).zzjle)) {
                    return false;
                }
                if (this.zzjlf == null ? ((zzcma)object).zzjlf != null : !this.zzjlf.equals(((zzcma)object).zzjlf)) {
                    return false;
                }
                if (this.zzpnc != null && !this.zzpnc.isEmpty()) {
                    return this.zzpnc.equals(((zzcma)object).zzpnc);
                }
                if (((zzcma)object).zzpnc != null && !((zzcma)object).zzpnc.isEmpty()) break block10;
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
        int n3 = this.zzjjs == null ? 0 : this.zzjjs.hashCode();
        zzcmf zzcmf2 = this.zzjld;
        int n4 = zzcmf2 == null ? 0 : zzcmf2.hashCode();
        zzcmf2 = this.zzjle;
        int n5 = zzcmf2 == null ? 0 : zzcmf2.hashCode();
        int n6 = this.zzjlf == null ? 0 : this.zzjlf.hashCode();
        int n7 = n;
        if (this.zzpnc == null) return (n6 + (n5 + (n4 + (n3 + (n2 + 527) * 31) * 31) * 31) * 31) * 31 + n7;
        if (this.zzpnc.isEmpty()) {
            n7 = n;
            return (n6 + (n5 + (n4 + (n3 + (n2 + 527) * 31) * 31) * 31) * 31) * 31 + n7;
        }
        n7 = this.zzpnc.hashCode();
        return (n6 + (n5 + (n4 + (n3 + (n2 + 527) * 31) * 31) * 31) * 31) * 31 + n7;
    }

    @Override
    public final /* synthetic */ zzfjs zza(zzfjj zzfjj2) throws IOException {
        block7: do {
            int n = zzfjj2.zzcvt();
            switch (n) {
                default: {
                    if (super.zza(zzfjj2, n)) continue block7;
                }
                case 0: {
                    return this;
                }
                case 8: {
                    this.zzjjs = zzfjj2.zzcwi();
                    continue block7;
                }
                case 18: {
                    if (this.zzjld == null) {
                        this.zzjld = new zzcmf();
                    }
                    zzfjj2.zza(this.zzjld);
                    continue block7;
                }
                case 26: {
                    if (this.zzjle == null) {
                        this.zzjle = new zzcmf();
                    }
                    zzfjj2.zza(this.zzjle);
                    continue block7;
                }
                case 32: 
            }
            this.zzjlf = zzfjj2.zzcvz();
        } while (true);
    }

    @Override
    public final void zza(zzfjk zzfjk2) throws IOException {
        if (this.zzjjs != null) {
            zzfjk2.zzaa(1, this.zzjjs);
        }
        if (this.zzjld != null) {
            zzfjk2.zza(2, this.zzjld);
        }
        if (this.zzjle != null) {
            zzfjk2.zza(3, this.zzjle);
        }
        if (this.zzjlf != null) {
            zzfjk2.zzl(4, this.zzjlf);
        }
        super.zza(zzfjk2);
    }

    @Override
    protected final int zzq() {
        int n;
        int n2 = n = super.zzq();
        if (this.zzjjs != null) {
            n2 = n + zzfjk.zzad(1, this.zzjjs);
        }
        n = n2;
        if (this.zzjld != null) {
            n = n2 + zzfjk.zzb(2, this.zzjld);
        }
        n2 = n;
        if (this.zzjle != null) {
            n2 = n + zzfjk.zzb(3, this.zzjle);
        }
        n = n2;
        if (this.zzjlf != null) {
            this.zzjlf.booleanValue();
            n = n2 + (zzfjk.zzlg(4) + 1);
        }
        return n;
    }
}

