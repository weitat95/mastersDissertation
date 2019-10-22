/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.Context
 */
package com.google.android.gms.common.api.internal;

import android.app.PendingIntent;
import android.content.Context;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.zzao;
import com.google.android.gms.common.api.internal.zzap;
import com.google.android.gms.common.api.internal.zzaq;
import com.google.android.gms.common.api.internal.zzas;
import com.google.android.gms.common.api.internal.zzat;
import com.google.android.gms.common.api.internal.zzay;
import com.google.android.gms.common.api.internal.zzbh;
import com.google.android.gms.common.api.internal.zzbj;
import com.google.android.gms.common.internal.zzj;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

final class zzar
extends zzay {
    final /* synthetic */ zzao zzfrl;
    private final Map<Api.zze, zzaq> zzfrn;

    public zzar(Map<Api.zze, zzaq> map) {
        this.zzfrl = var1_1;
        super(var1_1, null);
        this.zzfrn = map;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final void zzaib() {
        boolean bl;
        boolean bl2;
        Api.zze zze2;
        Iterator<Api.zze> iterator;
        int n;
        boolean bl3;
        block10: {
            boolean bl4 = true;
            n = 0;
            iterator = this.zzfrn.keySet().iterator();
            bl = true;
            bl2 = false;
            while (iterator.hasNext()) {
                block13: {
                    block12: {
                        block11: {
                            zze2 = iterator.next();
                            if (!zze2.zzagg()) break block11;
                            if (!zzaq.zza(this.zzfrn.get(zze2))) {
                                bl2 = true;
                                bl3 = bl4;
                                break block10;
                            }
                            break block12;
                        }
                        bl3 = false;
                        bl = bl2;
                        bl2 = bl3;
                        break block13;
                    }
                    bl2 = bl;
                    bl = true;
                }
                bl3 = bl;
                bl = bl2;
                bl2 = bl3;
            }
            bl3 = bl2;
            bl2 = false;
        }
        if (bl3) {
            n = zzao.zzb(this.zzfrl).isGooglePlayServicesAvailable(zzao.zza(this.zzfrl));
        }
        if (n != 0 && (bl2 || bl)) {
            iterator = new ConnectionResult(n, null);
            zzao.zzd(this.zzfrl).zza(new zzas(this, this.zzfrl, (ConnectionResult)((Object)iterator)));
            return;
        }
        if (zzao.zze(this.zzfrl)) {
            zzao.zzf(this.zzfrl).connect();
        }
        iterator = this.zzfrn.keySet().iterator();
        while (iterator.hasNext()) {
            zze2 = iterator.next();
            zzj zzj2 = this.zzfrn.get(zze2);
            if (zze2.zzagg() && n != 0) {
                zzao.zzd(this.zzfrl).zza(new zzat(this, this.zzfrl, zzj2));
                continue;
            }
            zze2.zza(zzj2);
        }
        return;
    }
}

