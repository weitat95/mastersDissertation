/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzdmc;
import com.google.android.gms.internal.zzfjj;
import com.google.android.gms.internal.zzfjk;
import com.google.android.gms.internal.zzfjm;
import com.google.android.gms.internal.zzfjo;
import com.google.android.gms.internal.zzfjq;
import com.google.android.gms.internal.zzfjr;
import com.google.android.gms.internal.zzfjs;
import com.google.android.gms.internal.zzfjv;
import java.io.IOException;

public final class zzdmb
extends zzfjm<zzdmb> {
    public zzdmc[] zzlmi = zzdmc.zzbki();

    public zzdmb() {
        this.zzpnc = null;
        this.zzpfd = -1;
    }

    public static zzdmb zzaa(byte[] arrby) throws zzfjr {
        return zzfjs.zza(new zzdmb(), arrby);
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean equals(Object object) {
        block7: {
            block6: {
                if (object == this) break block6;
                if (!(object instanceof zzdmb)) {
                    return false;
                }
                object = (zzdmb)object;
                if (!zzfjq.equals(this.zzlmi, ((zzdmb)object).zzlmi)) {
                    return false;
                }
                if (this.zzpnc != null && !this.zzpnc.isEmpty()) {
                    return this.zzpnc.equals(((zzdmb)object).zzpnc);
                }
                if (((zzdmb)object).zzpnc != null && !((zzdmb)object).zzpnc.isEmpty()) break block7;
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
        int n3 = zzfjq.hashCode(this.zzlmi);
        if (this.zzpnc == null || this.zzpnc.isEmpty()) {
            n = 0;
            do {
                return n + ((n2 + 527) * 31 + n3) * 31;
                break;
            } while (true);
        }
        n = this.zzpnc.hashCode();
        return n + ((n2 + 527) * 31 + n3) * 31;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final /* synthetic */ zzfjs zza(zzfjj zzfjj2) throws IOException {
        block4: do {
            int n = zzfjj2.zzcvt();
            switch (n) {
                default: {
                    if (super.zza(zzfjj2, n)) continue block4;
                }
                case 0: {
                    return this;
                }
                case 10: 
            }
            int n2 = zzfjv.zzb(zzfjj2, 10);
            n = this.zzlmi == null ? 0 : this.zzlmi.length;
            zzdmc[] arrzzdmc = new zzdmc[n2 + n];
            n2 = n;
            if (n != 0) {
                System.arraycopy(this.zzlmi, 0, arrzzdmc, 0, n);
                n2 = n;
            }
            while (n2 < arrzzdmc.length - 1) {
                arrzzdmc[n2] = new zzdmc();
                zzfjj2.zza(arrzzdmc[n2]);
                zzfjj2.zzcvt();
                ++n2;
            }
            arrzzdmc[n2] = new zzdmc();
            zzfjj2.zza(arrzzdmc[n2]);
            this.zzlmi = arrzzdmc;
        } while (true);
    }

    @Override
    public final void zza(zzfjk zzfjk2) throws IOException {
        if (this.zzlmi != null && this.zzlmi.length > 0) {
            for (int i = 0; i < this.zzlmi.length; ++i) {
                zzdmc zzdmc2 = this.zzlmi[i];
                if (zzdmc2 == null) continue;
                zzfjk2.zza(1, zzdmc2);
            }
        }
        super.zza(zzfjk2);
    }

    @Override
    protected final int zzq() {
        int n;
        int n2 = n = super.zzq();
        if (this.zzlmi != null) {
            n2 = n;
            if (this.zzlmi.length > 0) {
                int n3 = 0;
                do {
                    n2 = n;
                    if (n3 >= this.zzlmi.length) break;
                    zzdmc zzdmc2 = this.zzlmi[n3];
                    n2 = n;
                    if (zzdmc2 != null) {
                        n2 = n + zzfjk.zzb(1, zzdmc2);
                    }
                    ++n3;
                    n = n2;
                } while (true);
            }
        }
        return n2;
    }
}

