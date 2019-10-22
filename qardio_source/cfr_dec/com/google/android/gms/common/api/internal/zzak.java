/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public class zzak
extends GoogleApiClient {
    private final String zzfqu;

    public zzak(String string2) {
        this.zzfqu = string2;
    }

    @Override
    public ConnectionResult blockingConnect(long l, TimeUnit timeUnit) {
        throw new UnsupportedOperationException(this.zzfqu);
    }

    @Override
    public void connect() {
        throw new UnsupportedOperationException(this.zzfqu);
    }

    @Override
    public void disconnect() {
        throw new UnsupportedOperationException(this.zzfqu);
    }

    @Override
    public void dump(String string2, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        throw new UnsupportedOperationException(this.zzfqu);
    }

    @Override
    public boolean isConnected() {
        throw new UnsupportedOperationException(this.zzfqu);
    }

    @Override
    public boolean isConnecting() {
        throw new UnsupportedOperationException(this.zzfqu);
    }

    @Override
    public void reconnect() {
        throw new UnsupportedOperationException(this.zzfqu);
    }

    @Override
    public void registerConnectionCallbacks(GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        throw new UnsupportedOperationException(this.zzfqu);
    }

    @Override
    public void registerConnectionFailedListener(GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        throw new UnsupportedOperationException(this.zzfqu);
    }

    @Override
    public void unregisterConnectionFailedListener(GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        throw new UnsupportedOperationException(this.zzfqu);
    }
}

