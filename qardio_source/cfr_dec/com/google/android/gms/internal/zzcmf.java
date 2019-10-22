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
import com.google.android.gms.internal.zzfjv;
import java.io.IOException;

public final class zzcmf
extends zzfjm<zzcmf> {
    public long[] zzjmp = zzfjv.zzpnq;
    public long[] zzjmq = zzfjv.zzpnq;

    public zzcmf() {
        this.zzpnc = null;
        this.zzpfd = -1;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean equals(Object object) {
        block8: {
            block7: {
                if (object == this) break block7;
                if (!(object instanceof zzcmf)) {
                    return false;
                }
                object = (zzcmf)object;
                if (!zzfjq.equals(this.zzjmp, ((zzcmf)object).zzjmp)) {
                    return false;
                }
                if (!zzfjq.equals(this.zzjmq, ((zzcmf)object).zzjmq)) {
                    return false;
                }
                if (this.zzpnc != null && !this.zzpnc.isEmpty()) {
                    return this.zzpnc.equals(((zzcmf)object).zzpnc);
                }
                if (((zzcmf)object).zzpnc != null && !((zzcmf)object).zzpnc.isEmpty()) break block8;
            }
            return true;
        }
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final int hashCode() {
        int n;
        int n2 = this.getClass().getName().hashCode();
        int n3 = zzfjq.hashCode(this.zzjmp);
        int n4 = zzfjq.hashCode(this.zzjmq);
        if (this.zzpnc == null || this.zzpnc.isEmpty()) {
            n = 0;
            do {
                return n + (((n2 + 527) * 31 + n3) * 31 + n4) * 31;
                break;
            } while (true);
        }
        n = this.zzpnc.hashCode();
        return n + (((n2 + 527) * 31 + n3) * 31 + n4) * 31;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final /* synthetic */ zzfjs zza(zzfjj zzfjj2) throws IOException {
        block7: do {
            int n;
            long[] arrl;
            int n2;
            int n3 = zzfjj2.zzcvt();
            switch (n3) {
                default: {
                    if (super.zza(zzfjj2, n3)) continue block7;
                }
                case 0: {
                    return this;
                }
                case 8: {
                    n = zzfjv.zzb(zzfjj2, 8);
                    n3 = this.zzjmp == null ? 0 : this.zzjmp.length;
                    arrl = new long[n + n3];
                    n = n3;
                    if (n3 != 0) {
                        System.arraycopy(this.zzjmp, 0, arrl, 0, n3);
                        n = n3;
                    }
                    while (n < arrl.length - 1) {
                        arrl[n] = zzfjj2.zzcwn();
                        zzfjj2.zzcvt();
                        ++n;
                    }
                    arrl[n] = zzfjj2.zzcwn();
                    this.zzjmp = arrl;
                    continue block7;
                }
                case 10: {
                    n2 = zzfjj2.zzks(zzfjj2.zzcwi());
                    n3 = zzfjj2.getPosition();
                    n = 0;
                    while (zzfjj2.zzcwk() > 0) {
                        zzfjj2.zzcwn();
                        ++n;
                    }
                    zzfjj2.zzmg(n3);
                    n3 = this.zzjmp == null ? 0 : this.zzjmp.length;
                    arrl = new long[n + n3];
                    n = n3;
                    if (n3 != 0) {
                        System.arraycopy(this.zzjmp, 0, arrl, 0, n3);
                        n = n3;
                    }
                    while (n < arrl.length) {
                        arrl[n] = zzfjj2.zzcwn();
                        ++n;
                    }
                    this.zzjmp = arrl;
                    zzfjj2.zzkt(n2);
                    continue block7;
                }
                case 16: {
                    n = zzfjv.zzb(zzfjj2, 16);
                    n3 = this.zzjmq == null ? 0 : this.zzjmq.length;
                    arrl = new long[n + n3];
                    n = n3;
                    if (n3 != 0) {
                        System.arraycopy(this.zzjmq, 0, arrl, 0, n3);
                        n = n3;
                    }
                    while (n < arrl.length - 1) {
                        arrl[n] = zzfjj2.zzcwn();
                        zzfjj2.zzcvt();
                        ++n;
                    }
                    arrl[n] = zzfjj2.zzcwn();
                    this.zzjmq = arrl;
                    continue block7;
                }
                case 18: 
            }
            n2 = zzfjj2.zzks(zzfjj2.zzcwi());
            n3 = zzfjj2.getPosition();
            n = 0;
            while (zzfjj2.zzcwk() > 0) {
                zzfjj2.zzcwn();
                ++n;
            }
            zzfjj2.zzmg(n3);
            n3 = this.zzjmq == null ? 0 : this.zzjmq.length;
            arrl = new long[n + n3];
            n = n3;
            if (n3 != 0) {
                System.arraycopy(this.zzjmq, 0, arrl, 0, n3);
                n = n3;
            }
            while (n < arrl.length) {
                arrl[n] = zzfjj2.zzcwn();
                ++n;
            }
            this.zzjmq = arrl;
            zzfjj2.zzkt(n2);
        } while (true);
    }

    @Override
    public final void zza(zzfjk zzfjk2) throws IOException {
        int n;
        int n2 = 0;
        if (this.zzjmp != null && this.zzjmp.length > 0) {
            for (n = 0; n < this.zzjmp.length; ++n) {
                zzfjk2.zza(1, this.zzjmp[n]);
            }
        }
        if (this.zzjmq != null && this.zzjmq.length > 0) {
            for (n = n2; n < this.zzjmq.length; ++n) {
                zzfjk2.zza(2, this.zzjmq[n]);
            }
        }
        super.zza(zzfjk2);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected final int zzq() {
        int n;
        int n2;
        int n3 = 0;
        int n4 = super.zzq();
        if (this.zzjmp != null && this.zzjmp.length > 0) {
            n = 0;
            for (n2 = 0; n2 < this.zzjmp.length; n += zzfjk.zzdi(this.zzjmp[n2]), ++n2) {
            }
            n2 = n4 + n + this.zzjmp.length * 1;
        } else {
            n2 = n4;
        }
        n = n2;
        if (this.zzjmq == null) return n;
        n = n2;
        if (this.zzjmq.length <= 0) return n;
        n4 = 0;
        n = n3;
        while (n < this.zzjmq.length) {
            n4 += zzfjk.zzdi(this.zzjmq[n]);
            ++n;
        }
        return n2 + n4 + this.zzjmq.length * 1;
    }
}

