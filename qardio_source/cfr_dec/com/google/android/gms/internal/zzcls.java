/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzclt;
import com.google.android.gms.internal.zzclu;
import com.google.android.gms.internal.zzfjj;
import com.google.android.gms.internal.zzfjk;
import com.google.android.gms.internal.zzfjm;
import com.google.android.gms.internal.zzfjo;
import com.google.android.gms.internal.zzfjq;
import com.google.android.gms.internal.zzfjs;
import com.google.android.gms.internal.zzfjv;
import java.io.IOException;

public final class zzcls
extends zzfjm<zzcls> {
    private static volatile zzcls[] zzjjv;
    public Integer zzjjw = null;
    public String zzjjx = null;
    public zzclt[] zzjjy = zzclt.zzbbc();
    private Boolean zzjjz = null;
    public zzclu zzjka = null;

    public zzcls() {
        this.zzpnc = null;
        this.zzpfd = -1;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static zzcls[] zzbbb() {
        if (zzjjv == null) {
            Object object = zzfjq.zzpnk;
            synchronized (object) {
                if (zzjjv == null) {
                    zzjjv = new zzcls[0];
                }
            }
        }
        return zzjjv;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean equals(Object object) {
        block11: {
            block10: {
                if (object == this) break block10;
                if (!(object instanceof zzcls)) {
                    return false;
                }
                object = (zzcls)object;
                if (this.zzjjw == null ? ((zzcls)object).zzjjw != null : !this.zzjjw.equals(((zzcls)object).zzjjw)) {
                    return false;
                }
                if (this.zzjjx == null ? ((zzcls)object).zzjjx != null : !this.zzjjx.equals(((zzcls)object).zzjjx)) {
                    return false;
                }
                if (!zzfjq.equals(this.zzjjy, ((zzcls)object).zzjjy)) {
                    return false;
                }
                if (this.zzjjz == null ? ((zzcls)object).zzjjz != null : !this.zzjjz.equals(((zzcls)object).zzjjz)) {
                    return false;
                }
                if (this.zzjka == null ? ((zzcls)object).zzjka != null : !this.zzjka.equals(((zzcls)object).zzjka)) {
                    return false;
                }
                if (this.zzpnc != null && !this.zzpnc.isEmpty()) {
                    return this.zzpnc.equals(((zzcls)object).zzpnc);
                }
                if (((zzcls)object).zzpnc != null && !((zzcls)object).zzpnc.isEmpty()) break block11;
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
        int n4 = this.zzjjx == null ? 0 : this.zzjjx.hashCode();
        int n5 = zzfjq.hashCode(this.zzjjy);
        int n6 = this.zzjjz == null ? 0 : this.zzjjz.hashCode();
        zzclu zzclu2 = this.zzjka;
        int n7 = zzclu2 == null ? 0 : zzclu2.hashCode();
        int n8 = n;
        if (this.zzpnc == null) return (n7 + (n6 + ((n4 + (n3 + (n2 + 527) * 31) * 31) * 31 + n5) * 31) * 31) * 31 + n8;
        if (this.zzpnc.isEmpty()) {
            n8 = n;
            return (n7 + (n6 + ((n4 + (n3 + (n2 + 527) * 31) * 31) * 31 + n5) * 31) * 31) * 31 + n8;
        }
        n8 = this.zzpnc.hashCode();
        return (n7 + (n6 + ((n4 + (n3 + (n2 + 527) * 31) * 31) * 31 + n5) * 31) * 31) * 31 + n8;
    }

    /*
     * Enabled aggressive block sorting
     */
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
                case 8: {
                    this.zzjjw = zzfjj2.zzcwi();
                    continue block8;
                }
                case 18: {
                    this.zzjjx = zzfjj2.readString();
                    continue block8;
                }
                case 26: {
                    int n2 = zzfjv.zzb(zzfjj2, 26);
                    n = this.zzjjy == null ? 0 : this.zzjjy.length;
                    zzclt[] arrzzclt = new zzclt[n2 + n];
                    n2 = n;
                    if (n != 0) {
                        System.arraycopy(this.zzjjy, 0, arrzzclt, 0, n);
                        n2 = n;
                    }
                    while (n2 < arrzzclt.length - 1) {
                        arrzzclt[n2] = new zzclt();
                        zzfjj2.zza(arrzzclt[n2]);
                        zzfjj2.zzcvt();
                        ++n2;
                    }
                    arrzzclt[n2] = new zzclt();
                    zzfjj2.zza(arrzzclt[n2]);
                    this.zzjjy = arrzzclt;
                    continue block8;
                }
                case 32: {
                    this.zzjjz = zzfjj2.zzcvz();
                    continue block8;
                }
                case 42: 
            }
            if (this.zzjka == null) {
                this.zzjka = new zzclu();
            }
            zzfjj2.zza(this.zzjka);
        } while (true);
    }

    @Override
    public final void zza(zzfjk zzfjk2) throws IOException {
        if (this.zzjjw != null) {
            zzfjk2.zzaa(1, this.zzjjw);
        }
        if (this.zzjjx != null) {
            zzfjk2.zzn(2, this.zzjjx);
        }
        if (this.zzjjy != null && this.zzjjy.length > 0) {
            for (int i = 0; i < this.zzjjy.length; ++i) {
                zzclt zzclt2 = this.zzjjy[i];
                if (zzclt2 == null) continue;
                zzfjk2.zza(3, zzclt2);
            }
        }
        if (this.zzjjz != null) {
            zzfjk2.zzl(4, this.zzjjz);
        }
        if (this.zzjka != null) {
            zzfjk2.zza(5, this.zzjka);
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
        if (this.zzjjx != null) {
            n = n2 + zzfjk.zzo(2, this.zzjjx);
        }
        n2 = n;
        if (this.zzjjy != null) {
            n2 = n;
            if (this.zzjjy.length > 0) {
                for (n2 = 0; n2 < this.zzjjy.length; ++n2) {
                    zzclt zzclt2 = this.zzjjy[n2];
                    int n3 = n;
                    if (zzclt2 != null) {
                        n3 = n + zzfjk.zzb(3, zzclt2);
                    }
                    n = n3;
                }
                n2 = n;
            }
        }
        n = n2;
        if (this.zzjjz != null) {
            this.zzjjz.booleanValue();
            n = n2 + (zzfjk.zzlg(4) + 1);
        }
        n2 = n;
        if (this.zzjka != null) {
            n2 = n + zzfjk.zzb(5, this.zzjka);
        }
        return n2;
    }
}

