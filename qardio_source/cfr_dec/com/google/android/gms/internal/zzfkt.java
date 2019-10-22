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

public final class zzfkt
extends zzfjm<zzfkt> {
    private static volatile zzfkt[] zzprh;
    public String zzpri = "";

    public zzfkt() {
        this.zzpnc = null;
        this.zzpfd = -1;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static zzfkt[] zzdbd() {
        if (zzprh == null) {
            Object object = zzfjq.zzpnk;
            synchronized (object) {
                if (zzprh == null) {
                    zzprh = new zzfkt[0];
                }
            }
        }
        return zzprh;
    }

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
            this.zzpri = zzfjj2.readString();
        } while (true);
    }

    @Override
    public final void zza(zzfjk zzfjk2) throws IOException {
        if (this.zzpri != null && !this.zzpri.equals("")) {
            zzfjk2.zzn(1, this.zzpri);
        }
        super.zza(zzfjk2);
    }

    @Override
    protected final int zzq() {
        int n;
        int n2 = n = super.zzq();
        if (this.zzpri != null) {
            n2 = n;
            if (!this.zzpri.equals("")) {
                n2 = n + zzfjk.zzo(1, this.zzpri);
            }
        }
        return n2;
    }
}

