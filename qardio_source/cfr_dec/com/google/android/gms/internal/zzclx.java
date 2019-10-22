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

public final class zzclx
extends zzfjm<zzclx> {
    private static volatile zzclx[] zzjks;
    public String name = null;
    public Boolean zzjkt = null;
    public Boolean zzjku = null;
    public Integer zzjkv = null;

    public zzclx() {
        this.zzpnc = null;
        this.zzpfd = -1;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static zzclx[] zzbbe() {
        if (zzjks == null) {
            Object object = zzfjq.zzpnk;
            synchronized (object) {
                if (zzjks == null) {
                    zzjks = new zzclx[0];
                }
            }
        }
        return zzjks;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean equals(Object object) {
        block10: {
            block9: {
                if (object == this) break block9;
                if (!(object instanceof zzclx)) {
                    return false;
                }
                object = (zzclx)object;
                if (this.name == null ? ((zzclx)object).name != null : !this.name.equals(((zzclx)object).name)) {
                    return false;
                }
                if (this.zzjkt == null ? ((zzclx)object).zzjkt != null : !this.zzjkt.equals(((zzclx)object).zzjkt)) {
                    return false;
                }
                if (this.zzjku == null ? ((zzclx)object).zzjku != null : !this.zzjku.equals(((zzclx)object).zzjku)) {
                    return false;
                }
                if (this.zzjkv == null ? ((zzclx)object).zzjkv != null : !this.zzjkv.equals(((zzclx)object).zzjkv)) {
                    return false;
                }
                if (this.zzpnc != null && !this.zzpnc.isEmpty()) {
                    return this.zzpnc.equals(((zzclx)object).zzpnc);
                }
                if (((zzclx)object).zzpnc != null && !((zzclx)object).zzpnc.isEmpty()) break block10;
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
        int n4 = this.zzjkt == null ? 0 : this.zzjkt.hashCode();
        int n5 = this.zzjku == null ? 0 : this.zzjku.hashCode();
        int n6 = this.zzjkv == null ? 0 : this.zzjkv.hashCode();
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
                    this.name = zzfjj2.readString();
                    continue block7;
                }
                case 16: {
                    this.zzjkt = zzfjj2.zzcvz();
                    continue block7;
                }
                case 24: {
                    this.zzjku = zzfjj2.zzcvz();
                    continue block7;
                }
                case 32: 
            }
            this.zzjkv = zzfjj2.zzcwi();
        } while (true);
    }

    @Override
    public final void zza(zzfjk zzfjk2) throws IOException {
        if (this.name != null) {
            zzfjk2.zzn(1, this.name);
        }
        if (this.zzjkt != null) {
            zzfjk2.zzl(2, this.zzjkt);
        }
        if (this.zzjku != null) {
            zzfjk2.zzl(3, this.zzjku);
        }
        if (this.zzjkv != null) {
            zzfjk2.zzaa(4, this.zzjkv);
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
        if (this.zzjkt != null) {
            this.zzjkt.booleanValue();
            n = n2 + (zzfjk.zzlg(2) + 1);
        }
        n2 = n;
        if (this.zzjku != null) {
            this.zzjku.booleanValue();
            n2 = n + (zzfjk.zzlg(3) + 1);
        }
        n = n2;
        if (this.zzjkv != null) {
            n = n2 + zzfjk.zzad(4, this.zzjkv);
        }
        return n;
    }
}

