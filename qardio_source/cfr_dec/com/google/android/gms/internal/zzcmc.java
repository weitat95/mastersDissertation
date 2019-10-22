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

public final class zzcmc
extends zzfjm<zzcmc> {
    private static volatile zzcmc[] zzjlk;
    public String name = null;
    public String zzgcc = null;
    private Float zzjjk = null;
    public Double zzjjl = null;
    public Long zzjll = null;

    public zzcmc() {
        this.zzpnc = null;
        this.zzpfd = -1;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static zzcmc[] zzbbi() {
        if (zzjlk == null) {
            Object object = zzfjq.zzpnk;
            synchronized (object) {
                if (zzjlk == null) {
                    zzjlk = new zzcmc[0];
                }
            }
        }
        return zzjlk;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean equals(Object object) {
        block11: {
            block10: {
                if (object == this) break block10;
                if (!(object instanceof zzcmc)) {
                    return false;
                }
                object = (zzcmc)object;
                if (this.name == null ? ((zzcmc)object).name != null : !this.name.equals(((zzcmc)object).name)) {
                    return false;
                }
                if (this.zzgcc == null ? ((zzcmc)object).zzgcc != null : !this.zzgcc.equals(((zzcmc)object).zzgcc)) {
                    return false;
                }
                if (this.zzjll == null ? ((zzcmc)object).zzjll != null : !this.zzjll.equals(((zzcmc)object).zzjll)) {
                    return false;
                }
                if (this.zzjjk == null ? ((zzcmc)object).zzjjk != null : !this.zzjjk.equals(((zzcmc)object).zzjjk)) {
                    return false;
                }
                if (this.zzjjl == null ? ((zzcmc)object).zzjjl != null : !this.zzjjl.equals(((zzcmc)object).zzjjl)) {
                    return false;
                }
                if (this.zzpnc != null && !this.zzpnc.isEmpty()) {
                    return this.zzpnc.equals(((zzcmc)object).zzpnc);
                }
                if (((zzcmc)object).zzpnc != null && !((zzcmc)object).zzpnc.isEmpty()) break block11;
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
        int n3 = this.name == null ? 0 : this.name.hashCode();
        int n4 = this.zzgcc == null ? 0 : this.zzgcc.hashCode();
        int n5 = this.zzjll == null ? 0 : this.zzjll.hashCode();
        int n6 = this.zzjjk == null ? 0 : this.zzjjk.hashCode();
        int n7 = this.zzjjl == null ? 0 : this.zzjjl.hashCode();
        int n8 = n;
        if (this.zzpnc == null) return (n7 + (n6 + (n5 + (n4 + (n3 + (n2 + 527) * 31) * 31) * 31) * 31) * 31) * 31 + n8;
        if (this.zzpnc.isEmpty()) {
            n8 = n;
            return (n7 + (n6 + (n5 + (n4 + (n3 + (n2 + 527) * 31) * 31) * 31) * 31) * 31) * 31 + n8;
        }
        n8 = this.zzpnc.hashCode();
        return (n7 + (n6 + (n5 + (n4 + (n3 + (n2 + 527) * 31) * 31) * 31) * 31) * 31) * 31 + n8;
    }

    @Override
    public final /* synthetic */ zzfjs zza(zzfjj zzfjj2) throws IOException {
        block8: do {
            int n = zzfjj2.zzcvt();
            switch (n) {
                default: {
                    if (super.zza(zzfjj2, n)) continue block8;
                }
                case 0: {
                    return this;
                }
                case 10: {
                    this.name = zzfjj2.readString();
                    continue block8;
                }
                case 18: {
                    this.zzgcc = zzfjj2.readString();
                    continue block8;
                }
                case 24: {
                    this.zzjll = zzfjj2.zzcwn();
                    continue block8;
                }
                case 37: {
                    this.zzjjk = Float.valueOf(Float.intBitsToFloat(zzfjj2.zzcwo()));
                    continue block8;
                }
                case 41: 
            }
            this.zzjjl = Double.longBitsToDouble(zzfjj2.zzcwp());
        } while (true);
    }

    @Override
    public final void zza(zzfjk zzfjk2) throws IOException {
        if (this.name != null) {
            zzfjk2.zzn(1, this.name);
        }
        if (this.zzgcc != null) {
            zzfjk2.zzn(2, this.zzgcc);
        }
        if (this.zzjll != null) {
            zzfjk2.zzf(3, this.zzjll);
        }
        if (this.zzjjk != null) {
            zzfjk2.zzc(4, this.zzjjk.floatValue());
        }
        if (this.zzjjl != null) {
            zzfjk2.zza(5, this.zzjjl);
        }
        super.zza(zzfjk2);
    }

    @Override
    protected final int zzq() {
        int n;
        int n2 = n = super.zzq();
        if (this.name != null) {
            n2 = n + zzfjk.zzo(1, this.name);
        }
        n = n2;
        if (this.zzgcc != null) {
            n = n2 + zzfjk.zzo(2, this.zzgcc);
        }
        n2 = n;
        if (this.zzjll != null) {
            n2 = n + zzfjk.zzc(3, this.zzjll);
        }
        n = n2;
        if (this.zzjjk != null) {
            this.zzjjk.floatValue();
            n = n2 + (zzfjk.zzlg(4) + 4);
        }
        n2 = n;
        if (this.zzjjl != null) {
            this.zzjjl.doubleValue();
            n2 = n + (zzfjk.zzlg(5) + 8);
        }
        return n2;
    }
}

