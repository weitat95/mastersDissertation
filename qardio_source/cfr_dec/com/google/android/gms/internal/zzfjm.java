/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzfjj;
import com.google.android.gms.internal.zzfjk;
import com.google.android.gms.internal.zzfjo;
import com.google.android.gms.internal.zzfjp;
import com.google.android.gms.internal.zzfjq;
import com.google.android.gms.internal.zzfjs;
import com.google.android.gms.internal.zzfju;
import java.io.IOException;

public abstract class zzfjm<M extends zzfjm<M>>
extends zzfjs {
    protected zzfjo zzpnc;

    @Override
    public /* synthetic */ Object clone() throws CloneNotSupportedException {
        return this.zzdaf();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void zza(zzfjk zzfjk2) throws IOException {
        if (this.zzpnc != null) {
            for (int i = 0; i < this.zzpnc.size(); ++i) {
                this.zzpnc.zzmk(i).zza(zzfjk2);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected final boolean zza(zzfjj object, int n) throws IOException {
        int n2 = ((zzfjj)object).getPosition();
        if (!((zzfjj)object).zzkq(n)) {
            return false;
        }
        int n3 = n >>> 3;
        zzfju zzfju2 = new zzfju(n, ((zzfjj)object).zzal(n2, ((zzfjj)object).getPosition() - n2));
        object = null;
        if (this.zzpnc == null) {
            this.zzpnc = new zzfjo();
        } else {
            object = this.zzpnc.zzmj(n3);
        }
        Object object2 = object;
        if (object == null) {
            object2 = new zzfjp();
            this.zzpnc.zza(n3, (zzfjp)object2);
        }
        ((zzfjp)object2).zza(zzfju2);
        return true;
    }

    public M zzdaf() throws CloneNotSupportedException {
        zzfjm zzfjm2 = (zzfjm)super.zzdag();
        zzfjq.zza(this, zzfjm2);
        return (M)zzfjm2;
    }

    @Override
    public /* synthetic */ zzfjs zzdag() throws CloneNotSupportedException {
        return (zzfjm)((zzfjs)this).clone();
    }

    @Override
    protected int zzq() {
        int n;
        int n2 = 0;
        if (this.zzpnc != null) {
            int n3 = 0;
            do {
                n = n3;
                if (n2 < this.zzpnc.size()) {
                    n3 += this.zzpnc.zzmk(n2).zzq();
                    ++n2;
                    continue;
                }
                break;
            } while (true);
        } else {
            n = 0;
        }
        return n;
    }
}

