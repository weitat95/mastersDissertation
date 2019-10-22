/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzclu;
import com.google.android.gms.internal.zzclw;
import com.google.android.gms.internal.zzfjj;
import com.google.android.gms.internal.zzfjk;
import com.google.android.gms.internal.zzfjm;
import com.google.android.gms.internal.zzfjo;
import com.google.android.gms.internal.zzfjq;
import com.google.android.gms.internal.zzfjs;
import java.io.IOException;

public final class zzclt
extends zzfjm<zzclt> {
    private static volatile zzclt[] zzjkb;
    public zzclw zzjkc = null;
    public zzclu zzjkd = null;
    public Boolean zzjke = null;
    public String zzjkf = null;

    public zzclt() {
        this.zzpnc = null;
        this.zzpfd = -1;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static zzclt[] zzbbc() {
        if (zzjkb == null) {
            Object object = zzfjq.zzpnk;
            synchronized (object) {
                if (zzjkb == null) {
                    zzjkb = new zzclt[0];
                }
            }
        }
        return zzjkb;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean equals(Object object) {
        block10: {
            block9: {
                if (object == this) break block9;
                if (!(object instanceof zzclt)) {
                    return false;
                }
                object = (zzclt)object;
                if (this.zzjkc == null ? ((zzclt)object).zzjkc != null : !this.zzjkc.equals(((zzclt)object).zzjkc)) {
                    return false;
                }
                if (this.zzjkd == null ? ((zzclt)object).zzjkd != null : !this.zzjkd.equals(((zzclt)object).zzjkd)) {
                    return false;
                }
                if (this.zzjke == null ? ((zzclt)object).zzjke != null : !this.zzjke.equals(((zzclt)object).zzjke)) {
                    return false;
                }
                if (this.zzjkf == null ? ((zzclt)object).zzjkf != null : !this.zzjkf.equals(((zzclt)object).zzjkf)) {
                    return false;
                }
                if (this.zzpnc != null && !this.zzpnc.isEmpty()) {
                    return this.zzpnc.equals(((zzclt)object).zzpnc);
                }
                if (((zzclt)object).zzpnc != null && !((zzclt)object).zzpnc.isEmpty()) break block10;
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
        zzfjm zzfjm2 = this.zzjkc;
        int n3 = zzfjm2 == null ? 0 : ((zzclw)zzfjm2).hashCode();
        zzfjm2 = this.zzjkd;
        int n4 = zzfjm2 == null ? 0 : ((zzclu)zzfjm2).hashCode();
        int n5 = this.zzjke == null ? 0 : this.zzjke.hashCode();
        int n6 = this.zzjkf == null ? 0 : this.zzjkf.hashCode();
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
                case 10: {
                    if (this.zzjkc == null) {
                        this.zzjkc = new zzclw();
                    }
                    zzfjj2.zza(this.zzjkc);
                    continue block7;
                }
                case 18: {
                    if (this.zzjkd == null) {
                        this.zzjkd = new zzclu();
                    }
                    zzfjj2.zza(this.zzjkd);
                    continue block7;
                }
                case 24: {
                    this.zzjke = zzfjj2.zzcvz();
                    continue block7;
                }
                case 34: 
            }
            this.zzjkf = zzfjj2.readString();
        } while (true);
    }

    @Override
    public final void zza(zzfjk zzfjk2) throws IOException {
        if (this.zzjkc != null) {
            zzfjk2.zza(1, this.zzjkc);
        }
        if (this.zzjkd != null) {
            zzfjk2.zza(2, this.zzjkd);
        }
        if (this.zzjke != null) {
            zzfjk2.zzl(3, this.zzjke);
        }
        if (this.zzjkf != null) {
            zzfjk2.zzn(4, this.zzjkf);
        }
        super.zza(zzfjk2);
    }

    @Override
    protected final int zzq() {
        int n;
        int n2 = n = super.zzq();
        if (this.zzjkc != null) {
            n2 = n + zzfjk.zzb(1, this.zzjkc);
        }
        n = n2;
        if (this.zzjkd != null) {
            n = n2 + zzfjk.zzb(2, this.zzjkd);
        }
        n2 = n;
        if (this.zzjke != null) {
            this.zzjke.booleanValue();
            n2 = n + (zzfjk.zzlg(3) + 1);
        }
        n = n2;
        if (this.zzjkf != null) {
            n = n2 + zzfjk.zzo(4, this.zzjkf);
        }
        return n;
    }
}

