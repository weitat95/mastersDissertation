/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzfjj;
import com.google.android.gms.internal.zzfjk;
import com.google.android.gms.internal.zzfjm;
import com.google.android.gms.internal.zzfjo;
import com.google.android.gms.internal.zzfjq;
import com.google.android.gms.internal.zzfjs;
import java.io.IOException;

public final class zzcmg
extends zzfjm<zzcmg> {
    private static volatile zzcmg[] zzjmr;
    public String name = null;
    public String zzgcc = null;
    private Float zzjjk = null;
    public Double zzjjl = null;
    public Long zzjll = null;
    public Long zzjms = null;

    public zzcmg() {
        this.zzpnc = null;
        this.zzpfd = -1;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static zzcmg[] zzbbk() {
        if (zzjmr == null) {
            Object object = zzfjq.zzpnk;
            synchronized (object) {
                if (zzjmr == null) {
                    zzjmr = new zzcmg[0];
                }
            }
        }
        return zzjmr;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean equals(Object object) {
        block12: {
            block11: {
                if (object == this) break block11;
                if (!(object instanceof zzcmg)) {
                    return false;
                }
                object = (zzcmg)object;
                if (this.zzjms == null ? ((zzcmg)object).zzjms != null : !this.zzjms.equals(((zzcmg)object).zzjms)) {
                    return false;
                }
                if (this.name == null ? ((zzcmg)object).name != null : !this.name.equals(((zzcmg)object).name)) {
                    return false;
                }
                if (this.zzgcc == null ? ((zzcmg)object).zzgcc != null : !this.zzgcc.equals(((zzcmg)object).zzgcc)) {
                    return false;
                }
                if (this.zzjll == null ? ((zzcmg)object).zzjll != null : !this.zzjll.equals(((zzcmg)object).zzjll)) {
                    return false;
                }
                if (this.zzjjk == null ? ((zzcmg)object).zzjjk != null : !this.zzjjk.equals(((zzcmg)object).zzjjk)) {
                    return false;
                }
                if (this.zzjjl == null ? ((zzcmg)object).zzjjl != null : !this.zzjjl.equals(((zzcmg)object).zzjjl)) {
                    return false;
                }
                if (this.zzpnc != null && !this.zzpnc.isEmpty()) {
                    return this.zzpnc.equals(((zzcmg)object).zzpnc);
                }
                if (((zzcmg)object).zzpnc != null && !((zzcmg)object).zzpnc.isEmpty()) break block12;
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
        int n3 = this.zzjms == null ? 0 : this.zzjms.hashCode();
        int n4 = this.name == null ? 0 : this.name.hashCode();
        int n5 = this.zzgcc == null ? 0 : this.zzgcc.hashCode();
        int n6 = this.zzjll == null ? 0 : this.zzjll.hashCode();
        int n7 = this.zzjjk == null ? 0 : this.zzjjk.hashCode();
        int n8 = this.zzjjl == null ? 0 : this.zzjjl.hashCode();
        int n9 = n;
        if (this.zzpnc == null) return (n8 + (n7 + (n6 + (n5 + (n4 + (n3 + (n2 + 527) * 31) * 31) * 31) * 31) * 31) * 31) * 31 + n9;
        if (this.zzpnc.isEmpty()) {
            n9 = n;
            return (n8 + (n7 + (n6 + (n5 + (n4 + (n3 + (n2 + 527) * 31) * 31) * 31) * 31) * 31) * 31) * 31 + n9;
        }
        n9 = this.zzpnc.hashCode();
        return (n8 + (n7 + (n6 + (n5 + (n4 + (n3 + (n2 + 527) * 31) * 31) * 31) * 31) * 31) * 31) * 31 + n9;
    }

    @Override
    public final /* synthetic */ zzfjs zza(zzfjj zzfjj2) throws IOException {
        block9: do {
            int n = zzfjj2.zzcvt();
            switch (n) {
                default: {
                    if (super.zza(zzfjj2, n)) continue block9;
                }
                case 0: {
                    return this;
                }
                case 8: {
                    this.zzjms = zzfjj2.zzcwn();
                    continue block9;
                }
                case 18: {
                    this.name = zzfjj2.readString();
                    continue block9;
                }
                case 26: {
                    this.zzgcc = zzfjj2.readString();
                    continue block9;
                }
                case 32: {
                    this.zzjll = zzfjj2.zzcwn();
                    continue block9;
                }
                case 45: {
                    this.zzjjk = Float.valueOf(Float.intBitsToFloat(zzfjj2.zzcwo()));
                    continue block9;
                }
                case 49: 
            }
            this.zzjjl = Double.longBitsToDouble(zzfjj2.zzcwp());
        } while (true);
    }

    @Override
    public final void zza(zzfjk zzfjk2) throws IOException {
        if (this.zzjms != null) {
            zzfjk2.zzf(1, this.zzjms);
        }
        if (this.name != null) {
            zzfjk2.zzn(2, this.name);
        }
        if (this.zzgcc != null) {
            zzfjk2.zzn(3, this.zzgcc);
        }
        if (this.zzjll != null) {
            zzfjk2.zzf(4, this.zzjll);
        }
        if (this.zzjjk != null) {
            zzfjk2.zzc(5, this.zzjjk.floatValue());
        }
        if (this.zzjjl != null) {
            zzfjk2.zza(6, this.zzjjl);
        }
        super.zza(zzfjk2);
    }

    @Override
    protected final int zzq() {
        int n;
        int n2 = n = super.zzq();
        if (this.zzjms != null) {
            n2 = n + zzfjk.zzc(1, this.zzjms);
        }
        n = n2;
        if (this.name != null) {
            n = n2 + zzfjk.zzo(2, this.name);
        }
        n2 = n;
        if (this.zzgcc != null) {
            n2 = n + zzfjk.zzo(3, this.zzgcc);
        }
        n = n2;
        if (this.zzjll != null) {
            n = n2 + zzfjk.zzc(4, this.zzjll);
        }
        n2 = n;
        if (this.zzjjk != null) {
            this.zzjjk.floatValue();
            n2 = n + (zzfjk.zzlg(5) + 4);
        }
        n = n2;
        if (this.zzjjl != null) {
            this.zzjjl.doubleValue();
            n = n2 + (zzfjk.zzlg(6) + 8);
        }
        return n;
    }
}

