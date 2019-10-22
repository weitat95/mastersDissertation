/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzdmd;
import com.google.android.gms.internal.zzfjj;
import com.google.android.gms.internal.zzfjk;
import com.google.android.gms.internal.zzfjm;
import com.google.android.gms.internal.zzfjo;
import com.google.android.gms.internal.zzfjq;
import com.google.android.gms.internal.zzfjs;
import java.io.IOException;

public final class zzdmc
extends zzfjm<zzdmc> {
    private static volatile zzdmc[] zzlmj;
    public String name = "";
    public zzdmd zzlmk = null;

    public zzdmc() {
        this.zzpnc = null;
        this.zzpfd = -1;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static zzdmc[] zzbki() {
        if (zzlmj == null) {
            Object object = zzfjq.zzpnk;
            synchronized (object) {
                if (zzlmj == null) {
                    zzlmj = new zzdmc[0];
                }
            }
        }
        return zzlmj;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean equals(Object object) {
        block8: {
            block7: {
                if (object == this) break block7;
                if (!(object instanceof zzdmc)) {
                    return false;
                }
                object = (zzdmc)object;
                if (this.name == null ? ((zzdmc)object).name != null : !this.name.equals(((zzdmc)object).name)) {
                    return false;
                }
                if (this.zzlmk == null ? ((zzdmc)object).zzlmk != null : !this.zzlmk.equals(((zzdmc)object).zzlmk)) {
                    return false;
                }
                if (this.zzpnc != null && !this.zzpnc.isEmpty()) {
                    return this.zzpnc.equals(((zzdmc)object).zzpnc);
                }
                if (((zzdmc)object).zzpnc != null && !((zzdmc)object).zzpnc.isEmpty()) break block8;
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
        zzdmd zzdmd2 = this.zzlmk;
        int n4 = zzdmd2 == null ? 0 : zzdmd2.hashCode();
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
                    this.name = zzfjj2.readString();
                    continue block5;
                }
                case 18: 
            }
            if (this.zzlmk == null) {
                this.zzlmk = new zzdmd();
            }
            zzfjj2.zza(this.zzlmk);
        } while (true);
    }

    @Override
    public final void zza(zzfjk zzfjk2) throws IOException {
        zzfjk2.zzn(1, this.name);
        if (this.zzlmk != null) {
            zzfjk2.zza(2, this.zzlmk);
        }
        super.zza(zzfjk2);
    }

    @Override
    protected final int zzq() {
        int n;
        int n2 = n = super.zzq() + zzfjk.zzo(1, this.name);
        if (this.zzlmk != null) {
            n2 = n + zzfjk.zzb(2, this.zzlmk);
        }
        return n2;
    }
}

