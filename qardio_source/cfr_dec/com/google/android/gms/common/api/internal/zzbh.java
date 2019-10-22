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
import com.google.android.gms.common.api.internal.zzm;

public interface zzbh {
    public void begin();

    public void connect();

    public boolean disconnect();

    public void onConnected(Bundle var1);

    public void onConnectionSuspended(int var1);

    public void zza(ConnectionResult var1, Api<?> var2, boolean var3);

    public <A extends Api.zzb, R extends Result, T extends zzm<R, A>> T zzd(T var1);

    public <A extends Api.zzb, T extends zzm<? extends Result, A>> T zze(T var1);
}

