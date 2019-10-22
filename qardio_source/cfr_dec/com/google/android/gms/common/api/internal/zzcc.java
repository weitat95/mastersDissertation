/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.internal.zzm;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public interface zzcc {
    public ConnectionResult blockingConnect(long var1, TimeUnit var3);

    public void connect();

    public void disconnect();

    public void dump(String var1, FileDescriptor var2, PrintWriter var3, String[] var4);

    public boolean isConnected();

    public boolean isConnecting();

    public void zzahk();

    public <A extends Api.zzb, R extends Result, T extends zzm<R, A>> T zzd(T var1);

    public <A extends Api.zzb, T extends zzm<? extends Result, A>> T zze(T var1);
}

