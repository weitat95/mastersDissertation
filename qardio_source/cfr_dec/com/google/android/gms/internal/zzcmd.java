/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzcme;
import com.google.android.gms.internal.zzfjj;
import com.google.android.gms.internal.zzfjk;
import com.google.android.gms.internal.zzfjm;
import com.google.android.gms.internal.zzfjo;
import com.google.android.gms.internal.zzfjq;
import com.google.android.gms.internal.zzfjs;
import com.google.android.gms.internal.zzfjv;
import java.io.IOException;

public final class zzcmd
extends zzfjm<zzcmd> {
    public zzcme[] zzjlm = zzcme.zzbbj();

    public zzcmd() {
        this.zzpnc = null;
        this.zzpfd = -1;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean equals(Object object) {
        block7: {
            block6: {
                if (object == this) break block6;
                if (!(object instanceof zzcmd)) {
                    return false;
                }
                object = (zzcmd)object;
                if (!zzfjq.equals(this.zzjlm, ((zzcmd)object).zzjlm)) {
                    return false;
                }
                if (this.zzpnc != null && !this.zzpnc.isEmpty()) {
                    return this.zzpnc.equals(((zzcmd)object).zzpnc);
                }
                if (((zzcmd)object).zzpnc != null && !((zzcmd)object).zzpnc.isEmpty()) break block7;
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
        int n3 = zzfjq.hashCode(this.zzjlm);
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
            n = this.zzjlm == null ? 0 : this.zzjlm.length;
            zzcme[] arrzzcme = new zzcme[n2 + n];
            n2 = n;
            if (n != 0) {
                System.arraycopy(this.zzjlm, 0, arrzzcme, 0, n);
                n2 = n;
            }
            while (n2 < arrzzcme.length - 1) {
                arrzzcme[n2] = new zzcme();
                zzfjj2.zza(arrzzcme[n2]);
                zzfjj2.zzcvt();
                ++n2;
            }
            arrzzcme[n2] = new zzcme();
            zzfjj2.zza(arrzzcme[n2]);
            this.zzjlm = arrzzcme;
        } while (true);
    }

    @Override
    public final void zza(zzfjk zzfjk2) throws IOException {
        if (this.zzjlm != null && this.zzjlm.length > 0) {
            for (int i = 0; i < this.zzjlm.length; ++i) {
                zzcme zzcme2 = this.zzjlm[i];
                if (zzcme2 == null) continue;
                zzfjk2.zza(1, zzcme2);
            }
        }
        super.zza(zzfjk2);
    }

    @Override
    protected final int zzq() {
        int n;
        int n2 = n = super.zzq();
        if (this.zzjlm != null) {
            n2 = n;
            if (this.zzjlm.length > 0) {
                int n3 = 0;
                do {
                    n2 = n;
                    if (n3 >= this.zzjlm.length) break;
                    zzcme zzcme2 = this.zzjlm[n3];
                    n2 = n;
                    if (zzcme2 != null) {
                        n2 = n + zzfjk.zzb(1, zzcme2);
                    }
                    ++n3;
                    n = n2;
                } while (true);
            }
        }
        return n2;
    }
}

