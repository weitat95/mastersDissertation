/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzcls;
import com.google.android.gms.internal.zzclv;
import com.google.android.gms.internal.zzfjj;
import com.google.android.gms.internal.zzfjk;
import com.google.android.gms.internal.zzfjm;
import com.google.android.gms.internal.zzfjo;
import com.google.android.gms.internal.zzfjq;
import com.google.android.gms.internal.zzfjs;
import com.google.android.gms.internal.zzfjv;
import java.io.IOException;

public final class zzclr
extends zzfjm<zzclr> {
    private static volatile zzclr[] zzjjr;
    public Integer zzjjs = null;
    public zzclv[] zzjjt = zzclv.zzbbd();
    public zzcls[] zzjju = zzcls.zzbbb();

    public zzclr() {
        this.zzpnc = null;
        this.zzpfd = -1;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static zzclr[] zzbba() {
        if (zzjjr == null) {
            Object object = zzfjq.zzpnk;
            synchronized (object) {
                if (zzjjr == null) {
                    zzjjr = new zzclr[0];
                }
            }
        }
        return zzjjr;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean equals(Object object) {
        block9: {
            block8: {
                if (object == this) break block8;
                if (!(object instanceof zzclr)) {
                    return false;
                }
                object = (zzclr)object;
                if (this.zzjjs == null ? ((zzclr)object).zzjjs != null : !this.zzjjs.equals(((zzclr)object).zzjjs)) {
                    return false;
                }
                if (!zzfjq.equals(this.zzjjt, ((zzclr)object).zzjjt)) {
                    return false;
                }
                if (!zzfjq.equals(this.zzjju, ((zzclr)object).zzjju)) {
                    return false;
                }
                if (this.zzpnc != null && !this.zzpnc.isEmpty()) {
                    return this.zzpnc.equals(((zzclr)object).zzpnc);
                }
                if (((zzclr)object).zzpnc != null && !((zzclr)object).zzpnc.isEmpty()) break block9;
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
        int n4 = zzfjq.hashCode(this.zzjjt);
        int n5 = zzfjq.hashCode(this.zzjju);
        int n6 = n;
        if (this.zzpnc == null) return (((n3 + (n2 + 527) * 31) * 31 + n4) * 31 + n5) * 31 + n6;
        if (this.zzpnc.isEmpty()) {
            n6 = n;
            return (((n3 + (n2 + 527) * 31) * 31 + n4) * 31 + n5) * 31 + n6;
        }
        n6 = this.zzpnc.hashCode();
        return (((n3 + (n2 + 527) * 31) * 31 + n4) * 31 + n5) * 31 + n6;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final /* synthetic */ zzfjs zza(zzfjj zzfjj2) throws IOException {
        block6: do {
            int n;
            zzfjm[] arrzzfjm;
            int n2 = zzfjj2.zzcvt();
            switch (n2) {
                default: {
                    if (super.zza(zzfjj2, n2)) continue block6;
                }
                case 0: {
                    return this;
                }
                case 8: {
                    this.zzjjs = zzfjj2.zzcwi();
                    continue block6;
                }
                case 18: {
                    n = zzfjv.zzb(zzfjj2, 18);
                    n2 = this.zzjjt == null ? 0 : this.zzjjt.length;
                    arrzzfjm = new zzclv[n + n2];
                    n = n2;
                    if (n2 != 0) {
                        System.arraycopy(this.zzjjt, 0, arrzzfjm, 0, n2);
                        n = n2;
                    }
                    while (n < arrzzfjm.length - 1) {
                        arrzzfjm[n] = new zzclv();
                        zzfjj2.zza(arrzzfjm[n]);
                        zzfjj2.zzcvt();
                        ++n;
                    }
                    arrzzfjm[n] = new zzclv();
                    zzfjj2.zza(arrzzfjm[n]);
                    this.zzjjt = arrzzfjm;
                    continue block6;
                }
                case 26: 
            }
            n = zzfjv.zzb(zzfjj2, 26);
            n2 = this.zzjju == null ? 0 : this.zzjju.length;
            arrzzfjm = new zzcls[n + n2];
            n = n2;
            if (n2 != 0) {
                System.arraycopy(this.zzjju, 0, arrzzfjm, 0, n2);
                n = n2;
            }
            while (n < arrzzfjm.length - 1) {
                arrzzfjm[n] = new zzcls();
                zzfjj2.zza(arrzzfjm[n]);
                zzfjj2.zzcvt();
                ++n;
            }
            arrzzfjm[n] = new zzcls();
            zzfjj2.zza(arrzzfjm[n]);
            this.zzjju = arrzzfjm;
        } while (true);
    }

    @Override
    public final void zza(zzfjk zzfjk2) throws IOException {
        zzfjm zzfjm2;
        int n;
        int n2 = 0;
        if (this.zzjjs != null) {
            zzfjk2.zzaa(1, this.zzjjs);
        }
        if (this.zzjjt != null && this.zzjjt.length > 0) {
            for (n = 0; n < this.zzjjt.length; ++n) {
                zzfjm2 = this.zzjjt[n];
                if (zzfjm2 == null) continue;
                zzfjk2.zza(2, zzfjm2);
            }
        }
        if (this.zzjju != null && this.zzjju.length > 0) {
            for (n = n2; n < this.zzjju.length; ++n) {
                zzfjm2 = this.zzjju[n];
                if (zzfjm2 == null) continue;
                zzfjk2.zza(3, zzfjm2);
            }
        }
        super.zza(zzfjk2);
    }

    @Override
    protected final int zzq() {
        int n;
        zzfjm zzfjm2;
        int n2;
        int n3 = 0;
        int n4 = n2 = super.zzq();
        if (this.zzjjs != null) {
            n4 = n2 + zzfjk.zzad(1, this.zzjjs);
        }
        n2 = n4;
        if (this.zzjjt != null) {
            n2 = n4;
            if (this.zzjjt.length > 0) {
                n2 = n4;
                for (n4 = 0; n4 < this.zzjjt.length; ++n4) {
                    zzfjm2 = this.zzjjt[n4];
                    n = n2;
                    if (zzfjm2 != null) {
                        n = n2 + zzfjk.zzb(2, zzfjm2);
                    }
                    n2 = n;
                }
            }
        }
        n = n2;
        if (this.zzjju != null) {
            n = n2;
            if (this.zzjju.length > 0) {
                n4 = n3;
                do {
                    n = n2;
                    if (n4 >= this.zzjju.length) break;
                    zzfjm2 = this.zzjju[n4];
                    n = n2;
                    if (zzfjm2 != null) {
                        n = n2 + zzfjk.zzb(3, zzfjm2);
                    }
                    ++n4;
                    n2 = n;
                } while (true);
            }
        }
        return n;
    }
}

