/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.DeadObjectException
 */
package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import android.os.DeadObjectException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BasePendingResult;
import com.google.android.gms.common.api.internal.zzam;
import com.google.android.gms.common.api.internal.zzan;
import com.google.android.gms.common.api.internal.zzba;
import com.google.android.gms.common.api.internal.zzbh;
import com.google.android.gms.common.api.internal.zzbi;
import com.google.android.gms.common.api.internal.zzbj;
import com.google.android.gms.common.api.internal.zzcd;
import com.google.android.gms.common.api.internal.zzdg;
import com.google.android.gms.common.api.internal.zzdj;
import com.google.android.gms.common.api.internal.zzm;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.internal.zzbz;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class zzal
implements zzbh {
    private final zzbi zzfqv;
    private boolean zzfqw = false;

    public zzal(zzbi zzbi2) {
        this.zzfqv = zzbi2;
    }

    static /* synthetic */ zzbi zza(zzal zzal2) {
        return zzal2.zzfqv;
    }

    @Override
    public final void begin() {
    }

    @Override
    public final void connect() {
        if (this.zzfqw) {
            this.zzfqw = false;
            this.zzfqv.zza(new zzan(this, this));
        }
    }

    @Override
    public final boolean disconnect() {
        if (this.zzfqw) {
            return false;
        }
        if (this.zzfqv.zzfpi.zzail()) {
            this.zzfqw = true;
            Iterator<zzdg> iterator = this.zzfqv.zzfpi.zzfsg.iterator();
            while (iterator.hasNext()) {
                iterator.next().zzajs();
            }
            return false;
        }
        this.zzfqv.zzg(null);
        return true;
    }

    @Override
    public final void onConnected(Bundle bundle) {
    }

    @Override
    public final void onConnectionSuspended(int n) {
        this.zzfqv.zzg(null);
        this.zzfqv.zzfsu.zzf(n, this.zzfqw);
    }

    @Override
    public final void zza(ConnectionResult connectionResult, Api<?> api, boolean bl) {
    }

    final void zzaia() {
        if (this.zzfqw) {
            this.zzfqw = false;
            this.zzfqv.zzfpi.zzfsh.release();
            this.disconnect();
        }
    }

    @Override
    public final <A extends Api.zzb, R extends Result, T extends zzm<R, A>> T zzd(T t) {
        return this.zze(t);
    }

    @Override
    public final <A extends Api.zzb, T extends zzm<? extends Result, A>> T zze(T t) {
        Object object;
        Api.zzg zzg2;
        block4: {
            try {
                this.zzfqv.zzfpi.zzfsh.zzb(t);
                zzg2 = this.zzfqv.zzfpi;
                object = t.zzagf();
                object = ((zzba)zzg2).zzfsb.get(object);
                zzbq.checkNotNull(object, "Appropriate Api was not requested.");
                if (object.isConnected() || !this.zzfqv.zzfsq.containsKey(t.zzagf())) break block4;
                t.zzu(new Status(17));
            }
            catch (DeadObjectException deadObjectException) {
                this.zzfqv.zza(new zzam(this, this));
                return t;
            }
            return t;
        }
        zzg2 = object;
        if (object instanceof zzbz) {
            zzg2 = zzbz.zzals();
        }
        t.zzb(zzg2);
        return t;
    }
}

