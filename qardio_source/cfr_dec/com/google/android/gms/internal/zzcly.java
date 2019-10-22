/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzclr;
import com.google.android.gms.internal.zzclx;
import com.google.android.gms.internal.zzclz;
import com.google.android.gms.internal.zzfjj;
import com.google.android.gms.internal.zzfjk;
import com.google.android.gms.internal.zzfjm;
import com.google.android.gms.internal.zzfjo;
import com.google.android.gms.internal.zzfjq;
import com.google.android.gms.internal.zzfjs;
import com.google.android.gms.internal.zzfjv;
import java.io.IOException;

public final class zzcly
extends zzfjm<zzcly> {
    public String zzixs = null;
    public Long zzjkw = null;
    private Integer zzjkx = null;
    public zzclz[] zzjky = zzclz.zzbbf();
    public zzclx[] zzjkz = zzclx.zzbbe();
    public zzclr[] zzjla = zzclr.zzbba();

    public zzcly() {
        this.zzpnc = null;
        this.zzpfd = -1;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean equals(Object object) {
        block12: {
            block11: {
                if (object == this) break block11;
                if (!(object instanceof zzcly)) {
                    return false;
                }
                object = (zzcly)object;
                if (this.zzjkw == null ? ((zzcly)object).zzjkw != null : !this.zzjkw.equals(((zzcly)object).zzjkw)) {
                    return false;
                }
                if (this.zzixs == null ? ((zzcly)object).zzixs != null : !this.zzixs.equals(((zzcly)object).zzixs)) {
                    return false;
                }
                if (this.zzjkx == null ? ((zzcly)object).zzjkx != null : !this.zzjkx.equals(((zzcly)object).zzjkx)) {
                    return false;
                }
                if (!zzfjq.equals(this.zzjky, ((zzcly)object).zzjky)) {
                    return false;
                }
                if (!zzfjq.equals(this.zzjkz, ((zzcly)object).zzjkz)) {
                    return false;
                }
                if (!zzfjq.equals(this.zzjla, ((zzcly)object).zzjla)) {
                    return false;
                }
                if (this.zzpnc != null && !this.zzpnc.isEmpty()) {
                    return this.zzpnc.equals(((zzcly)object).zzpnc);
                }
                if (((zzcly)object).zzpnc != null && !((zzcly)object).zzpnc.isEmpty()) break block12;
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
        int n3 = this.zzjkw == null ? 0 : this.zzjkw.hashCode();
        int n4 = this.zzixs == null ? 0 : this.zzixs.hashCode();
        int n5 = this.zzjkx == null ? 0 : this.zzjkx.hashCode();
        int n6 = zzfjq.hashCode(this.zzjky);
        int n7 = zzfjq.hashCode(this.zzjkz);
        int n8 = zzfjq.hashCode(this.zzjla);
        int n9 = n;
        if (this.zzpnc == null) return ((((n5 + (n4 + (n3 + (n2 + 527) * 31) * 31) * 31) * 31 + n6) * 31 + n7) * 31 + n8) * 31 + n9;
        if (this.zzpnc.isEmpty()) {
            n9 = n;
            return ((((n5 + (n4 + (n3 + (n2 + 527) * 31) * 31) * 31) * 31 + n6) * 31 + n7) * 31 + n8) * 31 + n9;
        }
        n9 = this.zzpnc.hashCode();
        return ((((n5 + (n4 + (n3 + (n2 + 527) * 31) * 31) * 31) * 31 + n6) * 31 + n7) * 31 + n8) * 31 + n9;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final /* synthetic */ zzfjs zza(zzfjj zzfjj2) throws IOException {
        block9: do {
            int n;
            zzfjm[] arrzzfjm;
            int n2 = zzfjj2.zzcvt();
            switch (n2) {
                default: {
                    if (super.zza(zzfjj2, n2)) continue block9;
                }
                case 0: {
                    return this;
                }
                case 8: {
                    this.zzjkw = zzfjj2.zzcwn();
                    continue block9;
                }
                case 18: {
                    this.zzixs = zzfjj2.readString();
                    continue block9;
                }
                case 24: {
                    this.zzjkx = zzfjj2.zzcwi();
                    continue block9;
                }
                case 34: {
                    n = zzfjv.zzb(zzfjj2, 34);
                    n2 = this.zzjky == null ? 0 : this.zzjky.length;
                    arrzzfjm = new zzclz[n + n2];
                    n = n2;
                    if (n2 != 0) {
                        System.arraycopy(this.zzjky, 0, arrzzfjm, 0, n2);
                        n = n2;
                    }
                    while (n < arrzzfjm.length - 1) {
                        arrzzfjm[n] = new zzclz();
                        zzfjj2.zza(arrzzfjm[n]);
                        zzfjj2.zzcvt();
                        ++n;
                    }
                    arrzzfjm[n] = new zzclz();
                    zzfjj2.zza(arrzzfjm[n]);
                    this.zzjky = arrzzfjm;
                    continue block9;
                }
                case 42: {
                    n = zzfjv.zzb(zzfjj2, 42);
                    n2 = this.zzjkz == null ? 0 : this.zzjkz.length;
                    arrzzfjm = new zzclx[n + n2];
                    n = n2;
                    if (n2 != 0) {
                        System.arraycopy(this.zzjkz, 0, arrzzfjm, 0, n2);
                        n = n2;
                    }
                    while (n < arrzzfjm.length - 1) {
                        arrzzfjm[n] = new zzclx();
                        zzfjj2.zza(arrzzfjm[n]);
                        zzfjj2.zzcvt();
                        ++n;
                    }
                    arrzzfjm[n] = new zzclx();
                    zzfjj2.zza(arrzzfjm[n]);
                    this.zzjkz = arrzzfjm;
                    continue block9;
                }
                case 50: 
            }
            n = zzfjv.zzb(zzfjj2, 50);
            n2 = this.zzjla == null ? 0 : this.zzjla.length;
            arrzzfjm = new zzclr[n + n2];
            n = n2;
            if (n2 != 0) {
                System.arraycopy(this.zzjla, 0, arrzzfjm, 0, n2);
                n = n2;
            }
            while (n < arrzzfjm.length - 1) {
                arrzzfjm[n] = new zzclr();
                zzfjj2.zza(arrzzfjm[n]);
                zzfjj2.zzcvt();
                ++n;
            }
            arrzzfjm[n] = new zzclr();
            zzfjj2.zza(arrzzfjm[n]);
            this.zzjla = arrzzfjm;
        } while (true);
    }

    @Override
    public final void zza(zzfjk zzfjk2) throws IOException {
        zzfjm zzfjm2;
        int n;
        int n2 = 0;
        if (this.zzjkw != null) {
            zzfjk2.zzf(1, this.zzjkw);
        }
        if (this.zzixs != null) {
            zzfjk2.zzn(2, this.zzixs);
        }
        if (this.zzjkx != null) {
            zzfjk2.zzaa(3, this.zzjkx);
        }
        if (this.zzjky != null && this.zzjky.length > 0) {
            for (n = 0; n < this.zzjky.length; ++n) {
                zzfjm2 = this.zzjky[n];
                if (zzfjm2 == null) continue;
                zzfjk2.zza(4, zzfjm2);
            }
        }
        if (this.zzjkz != null && this.zzjkz.length > 0) {
            for (n = 0; n < this.zzjkz.length; ++n) {
                zzfjm2 = this.zzjkz[n];
                if (zzfjm2 == null) continue;
                zzfjk2.zza(5, zzfjm2);
            }
        }
        if (this.zzjla != null && this.zzjla.length > 0) {
            for (n = n2; n < this.zzjla.length; ++n) {
                zzfjm2 = this.zzjla[n];
                if (zzfjm2 == null) continue;
                zzfjk2.zza(6, zzfjm2);
            }
        }
        super.zza(zzfjk2);
    }

    @Override
    protected final int zzq() {
        int n;
        int n2;
        zzfjm zzfjm2;
        int n3 = 0;
        int n4 = n2 = super.zzq();
        if (this.zzjkw != null) {
            n4 = n2 + zzfjk.zzc(1, this.zzjkw);
        }
        n2 = n4;
        if (this.zzixs != null) {
            n2 = n4 + zzfjk.zzo(2, this.zzixs);
        }
        n4 = n2;
        if (this.zzjkx != null) {
            n4 = n2 + zzfjk.zzad(3, this.zzjkx);
        }
        n2 = n4;
        if (this.zzjky != null) {
            n2 = n4;
            if (this.zzjky.length > 0) {
                for (n2 = 0; n2 < this.zzjky.length; ++n2) {
                    zzfjm2 = this.zzjky[n2];
                    n = n4;
                    if (zzfjm2 != null) {
                        n = n4 + zzfjk.zzb(4, zzfjm2);
                    }
                    n4 = n;
                }
                n2 = n4;
            }
        }
        n4 = n2;
        if (this.zzjkz != null) {
            n4 = n2;
            if (this.zzjkz.length > 0) {
                n4 = n2;
                for (n2 = 0; n2 < this.zzjkz.length; ++n2) {
                    zzfjm2 = this.zzjkz[n2];
                    n = n4;
                    if (zzfjm2 != null) {
                        n = n4 + zzfjk.zzb(5, zzfjm2);
                    }
                    n4 = n;
                }
            }
        }
        n = n4;
        if (this.zzjla != null) {
            n = n4;
            if (this.zzjla.length > 0) {
                n2 = n3;
                do {
                    n = n4;
                    if (n2 >= this.zzjla.length) break;
                    zzfjm2 = this.zzjla[n2];
                    n = n4;
                    if (zzfjm2 != null) {
                        n = n4 + zzfjk.zzb(6, zzfjm2);
                    }
                    ++n2;
                    n4 = n;
                } while (true);
            }
        }
        return n;
    }
}

