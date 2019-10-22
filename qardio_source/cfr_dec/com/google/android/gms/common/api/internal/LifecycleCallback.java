/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Intent
 *  android.os.Bundle
 */
package com.google.android.gms.common.api.internal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Keep;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.common.api.internal.zzce;
import com.google.android.gms.common.api.internal.zzcf;
import com.google.android.gms.common.api.internal.zzcg;
import com.google.android.gms.common.api.internal.zzdb;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public class LifecycleCallback {
    protected final zzcf zzfud;

    protected LifecycleCallback(zzcf zzcf2) {
        this.zzfud = zzcf2;
    }

    @Keep
    private static zzcf getChimeraLifecycleFragmentImpl(zzce zzce2) {
        throw new IllegalStateException("Method not available in SDK.");
    }

    protected static zzcf zzb(zzce zzce2) {
        if (zzce2.zzajj()) {
            return zzdb.zza(zzce2.zzajm());
        }
        if (zzce2.zzajk()) {
            return zzcg.zzo(zzce2.zzajl());
        }
        throw new IllegalArgumentException("Can't get fragment for unexpected activity.");
    }

    public void dump(String string2, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
    }

    public final Activity getActivity() {
        return this.zzfud.zzajn();
    }

    public void onActivityResult(int n, int n2, Intent intent) {
    }

    public void onCreate(Bundle bundle) {
    }

    public void onDestroy() {
    }

    public void onResume() {
    }

    public void onSaveInstanceState(Bundle bundle) {
    }

    public void onStart() {
    }

    public void onStop() {
    }
}

