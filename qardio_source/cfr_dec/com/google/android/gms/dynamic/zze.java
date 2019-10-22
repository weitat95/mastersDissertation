/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.FrameLayout
 */
package com.google.android.gms.dynamic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.google.android.gms.dynamic.LifecycleDelegate;
import com.google.android.gms.dynamic.zza;
import com.google.android.gms.dynamic.zzi;

final class zze
implements zzi {
    private /* synthetic */ ViewGroup val$container;
    private /* synthetic */ Bundle zzail;
    private /* synthetic */ zza zzgwh;
    private /* synthetic */ FrameLayout zzgwj;
    private /* synthetic */ LayoutInflater zzgwk;

    zze(zza zza2, FrameLayout frameLayout, LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.zzgwh = zza2;
        this.zzgwj = frameLayout;
        this.zzgwk = layoutInflater;
        this.val$container = viewGroup;
        this.zzail = bundle;
    }

    @Override
    public final int getState() {
        return 2;
    }

    @Override
    public final void zzb(LifecycleDelegate lifecycleDelegate) {
        this.zzgwj.removeAllViews();
        this.zzgwj.addView(zza.zzb(this.zzgwh).onCreateView(this.zzgwk, this.val$container, this.zzail));
    }
}

