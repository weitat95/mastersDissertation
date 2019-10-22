/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.common.util.zzd;
import com.google.android.gms.internal.zzchm;
import com.google.android.gms.internal.zzcho;
import com.google.android.gms.internal.zzchx;
import com.google.android.gms.internal.zzcih;
import com.google.android.gms.internal.zzcjk;
import com.google.android.gms.internal.zzcjn;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeoutException;

final class zzcjy
implements Callable<String> {
    private /* synthetic */ zzcjn zzjhc;

    zzcjy(zzcjn zzcjn2) {
        this.zzjhc = zzcjn2;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final /* synthetic */ Object call() throws Exception {
        String string2 = ((zzcjk)this.zzjhc).zzawz().zzazn();
        if (string2 != null) {
            return string2;
        }
        zzcjn zzcjn2 = ((zzcjk)this.zzjhc).zzawm();
        if (((zzcjk)zzcjn2).zzawx().zzazs()) {
            ((zzcjk)zzcjn2).zzawy().zzazd().log("Cannot retrieve app instance id from analytics worker thread");
            string2 = null;
        } else {
            ((zzcjk)zzcjn2).zzawx();
            if (zzcih.zzau()) {
                ((zzcjk)zzcjn2).zzawy().zzazd().log("Cannot retrieve app instance id from main thread");
                string2 = null;
            } else {
                long l = ((zzcjk)zzcjn2).zzws().elapsedRealtime();
                String string3 = zzcjn2.zzbd(120000L);
                l = ((zzcjk)zzcjn2).zzws().elapsedRealtime() - l;
                string2 = string3;
                if (string3 == null) {
                    string2 = string3;
                    if (l < 120000L) {
                        string2 = zzcjn2.zzbd(120000L - l);
                    }
                }
            }
        }
        if (string2 == null) {
            throw new TimeoutException();
        }
        ((zzcjk)this.zzjhc).zzawz().zzjp(string2);
        return string2;
    }
}

