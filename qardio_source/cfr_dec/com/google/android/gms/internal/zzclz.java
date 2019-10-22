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

public final class zzclz
extends zzfjm<zzclz> {
    private static volatile zzclz[] zzjlb;
    public String key = null;
    public String value = null;

    public zzclz() {
        this.zzpnc = null;
        this.zzpfd = -1;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static zzclz[] zzbbf() {
        if (zzjlb == null) {
            Object object = zzfjq.zzpnk;
            synchronized (object) {
                if (zzjlb == null) {
                    zzjlb = new zzclz[0];
                }
            }
        }
        return zzjlb;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean equals(Object object) {
        block8: {
            block7: {
                if (object == this) break block7;
                if (!(object instanceof zzclz)) {
                    return false;
                }
                object = (zzclz)object;
                if (this.key == null ? ((zzclz)object).key != null : !this.key.equals(((zzclz)object).key)) {
                    return false;
                }
                if (this.value == null ? ((zzclz)object).value != null : !this.value.equals(((zzclz)object).value)) {
                    return false;
                }
                if (this.zzpnc != null && !this.zzpnc.isEmpty()) {
                    return this.zzpnc.equals(((zzclz)object).zzpnc);
                }
                if (((zzclz)object).zzpnc != null && !((zzclz)object).zzpnc.isEmpty()) break block8;
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
        int n3 = this.key == null ? 0 : this.key.hashCode();
        int n4 = this.value == null ? 0 : this.value.hashCode();
        int n5 = n;
        if (this.zzpnc == null) return (n4 + (n3 + (n2 + 527) * 31) * 31) * 31 + n5;
        if (this.zzpnc.isEmpty()) {
            n5 = n;
            return (n4 + (n3 + (n2 + 527) * 31) * 31) * 31 + n5;
        }
        n5 = this.zzpnc.hashCode();
        return (n4 + (n3 + (n2 + 527) * 31) * 31) * 31 + n5;
    }

    @Override
    public final /* synthetic */ zzfjs zza(zzfjj zzfjj2) throws IOException {
        block5: do {
            int n = zzfjj2.zzcvt();
            switch (n) {
                default: {
                    if (super.zza(zzfjj2, n)) continue block5;
                }
                case 0: {
                    return this;
                }
                case 10: {
                    this.key = zzfjj2.readString();
                    continue block5;
                }
                case 18: 
            }
            this.value = zzfjj2.readString();
        } while (true);
    }

    @Override
    public final void zza(zzfjk zzfjk2) throws IOException {
        if (this.key != null) {
            zzfjk2.zzn(1, this.key);
        }
        if (this.value != null) {
            zzfjk2.zzn(2, this.value);
        }
        super.zza(zzfjk2);
    }

    @Override
    protected final int zzq() {
        int n;
        int n2 = n = super.zzq();
        if (this.key != null) {
            n2 = n + zzfjk.zzo(1, this.key);
        }
        n = n2;
        if (this.value != null) {
            n = n2 + zzfjk.zzo(2, this.value);
        }
        return n;
    }
}

