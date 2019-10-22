/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.internal.zzba;
import com.google.android.gms.common.api.internal.zzbh;
import com.google.android.gms.common.api.internal.zzbi;
import com.google.android.gms.common.api.internal.zzm;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public final class zzaz
implements zzbh {
    private final zzbi zzfqv;

    public zzaz(zzbi zzbi2) {
        this.zzfqv = zzbi2;
    }

    @Override
    public final void begin() {
        Iterator<Api.zze> iterator = this.zzfqv.zzfsb.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().disconnect();
        }
        this.zzfqv.zzfpi.zzfsc = Collections.emptySet();
    }

    @Override
    public final void connect() {
        this.zzfqv.zzain();
    }

    @Override
    public final boolean disconnect() {
        return true;
    }

    @Override
    public final void onConnected(Bundle bundle) {
    }

    @Override
    public final void onConnectionSuspended(int n) {
    }

    @Override
    public final void zza(ConnectionResult connectionResult, Api<?> api, boolean bl) {
    }

    @Override
    public final <A extends Api.zzb, R extends Result, T extends zzm<R, A>> T zzd(T t) {
        this.zzfqv.zzfpi.zzfqg.add(t);
        return t;
    }

    @Override
    public final <A extends Api.zzb, T extends zzm<? extends Result, A>> T zze(T t) {
        throw new IllegalStateException("GoogleApiClient is not connected yet.");
    }
}

