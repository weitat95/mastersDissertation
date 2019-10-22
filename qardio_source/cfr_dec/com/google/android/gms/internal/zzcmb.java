/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzcmc;
import com.google.android.gms.internal.zzfjj;
import com.google.android.gms.internal.zzfjk;
import com.google.android.gms.internal.zzfjm;
import com.google.android.gms.internal.zzfjo;
import com.google.android.gms.internal.zzfjq;
import com.google.android.gms.internal.zzfjs;
import com.google.android.gms.internal.zzfjv;
import java.io.IOException;

public final class zzcmb
extends zzfjm<zzcmb> {
    private static volatile zzcmb[] zzjlg;
    public Integer count = null;
    public String name = null;
    public zzcmc[] zzjlh = zzcmc.zzbbi();
    public Long zzjli = null;
    public Long zzjlj = null;

    public zzcmb() {
        this.zzpnc = null;
        this.zzpfd = -1;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static zzcmb[] zzbbh() {
        if (zzjlg == null) {
            Object object = zzfjq.zzpnk;
            synchronized (object) {
                if (zzjlg == null) {
                    zzjlg = new zzcmb[0];
                }
            }
        }
        return zzjlg;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean equals(Object object) {
        block11: {
            block10: {
                if (object == this) break block10;
                if (!(object instanceof zzcmb)) {
                    return false;
                }
                object = (zzcmb)object;
                if (!zzfjq.equals(this.zzjlh, ((zzcmb)object).zzjlh)) {
                    return false;
                }
                if (this.name == null ? ((zzcmb)object).name != null : !this.name.equals(((zzcmb)object).name)) {
                    return false;
                }
                if (this.zzjli == null ? ((zzcmb)object).zzjli != null : !this.zzjli.equals(((zzcmb)object).zzjli)) {
                    return false;
                }
                if (this.zzjlj == null ? ((zzcmb)object).zzjlj != null : !this.zzjlj.equals(((zzcmb)object).zzjlj)) {
                    return false;
                }
                if (this.count == null ? ((zzcmb)object).count != null : !this.count.equals(((zzcmb)object).count)) {
                    return false;
                }
                if (this.zzpnc != null && !this.zzpnc.isEmpty()) {
                    return this.zzpnc.equals(((zzcmb)object).zzpnc);
                }
                if (((zzcmb)object).zzpnc != null && !((zzcmb)object).zzpnc.isEmpty()) break block11;
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
        int n3 = zzfjq.hashCode(this.zzjlh);
        int n4 = this.name == null ? 0 : this.name.hashCode();
        int n5 = this.zzjli == null ? 0 : this.zzjli.hashCode();
        int n6 = this.zzjlj == null ? 0 : this.zzjlj.hashCode();
        int n7 = this.count == null ? 0 : this.count.hashCode();
        int n8 = n;
        if (this.zzpnc == null) return (n7 + (n6 + (n5 + (n4 + ((n2 + 527) * 31 + n3) * 31) * 31) * 31) * 31) * 31 + n8;
        if (this.zzpnc.isEmpty()) {
            n8 = n;
            return (n7 + (n6 + (n5 + (n4 + ((n2 + 527) * 31 + n3) * 31) * 31) * 31) * 31) * 31 + n8;
        }
        n8 = this.zzpnc.hashCode();
        return (n7 + (n6 + (n5 + (n4 + ((n2 + 527) * 31 + n3) * 31) * 31) * 31) * 31) * 31 + n8;
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
                case 10: {
                    int n2 = zzfjv.zzb(zzfjj2, 10);
                    n = this.zzjlh == null ? 0 : this.zzjlh.length;
                    zzcmc[] arrzzcmc = new zzcmc[n2 + n];
                    n2 = n;
                    if (n != 0) {
                        System.arraycopy(this.zzjlh, 0, arrzzcmc, 0, n);
                        n2 = n;
                    }
                    while (n2 < arrzzcmc.length - 1) {
                        arrzzcmc[n2] = new zzcmc();
                        zzfjj2.zza(arrzzcmc[n2]);
                        zzfjj2.zzcvt();
                        ++n2;
                    }
                    arrzzcmc[n2] = new zzcmc();
                    zzfjj2.zza(arrzzcmc[n2]);
                    this.zzjlh = arrzzcmc;
                    continue block8;
                }
                case 18: {
                    this.name = zzfjj2.readString();
                    continue block8;
                }
                case 24: {
                    this.zzjli = zzfjj2.zzcwn();
                    continue block8;
                }
                case 32: {
                    this.zzjlj = zzfjj2.zzcwn();
                    continue block8;
                }
                case 40: 
            }
            this.count = zzfjj2.zzcwi();
        } while (true);
    }

    @Override
    public final void zza(zzfjk zzfjk2) throws IOException {
        if (this.zzjlh != null && this.zzjlh.length > 0) {
            for (int i = 0; i < this.zzjlh.length; ++i) {
                zzcmc zzcmc2 = this.zzjlh[i];
                if (zzcmc2 == null) continue;
                zzfjk2.zza(1, zzcmc2);
            }
        }
        if (this.name != null) {
            zzfjk2.zzn(2, this.name);
        }
        if (this.zzjli != null) {
            zzfjk2.zzf(3, this.zzjli);
        }
        if (this.zzjlj != null) {
            zzfjk2.zzf(4, this.zzjlj);
        }
        if (this.count != null) {
            zzfjk2.zzaa(5, this.count);
        }
        super.zza(zzfjk2);
    }

    @Override
    protected final int zzq() {
        int n;
        int n2 = n = super.zzq();
        if (this.zzjlh != null) {
            n2 = n;
            if (this.zzjlh.length > 0) {
                int n3 = 0;
                do {
                    n2 = n;
                    if (n3 >= this.zzjlh.length) break;
                    zzcmc zzcmc2 = this.zzjlh[n3];
                    n2 = n;
                    if (zzcmc2 != null) {
                        n2 = n + zzfjk.zzb(1, zzcmc2);
                    }
                    ++n3;
                    n = n2;
                } while (true);
            }
        }
        n = n2;
        if (this.name != null) {
            n = n2 + zzfjk.zzo(2, this.name);
        }
        n2 = n;
        if (this.zzjli != null) {
            n2 = n + zzfjk.zzc(3, this.zzjli);
        }
        n = n2;
        if (this.zzjlj != null) {
            n = n2 + zzfjk.zzc(4, this.zzjlj);
        }
        n2 = n;
        if (this.count != null) {
            n2 = n + zzfjk.zzad(5, this.count);
        }
        return n2;
    }
}

