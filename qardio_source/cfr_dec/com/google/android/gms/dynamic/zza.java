/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.Button
 *  android.widget.FrameLayout
 *  android.widget.FrameLayout$LayoutParams
 *  android.widget.LinearLayout
 *  android.widget.TextView
 */
package com.google.android.gms.dynamic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.dynamic.LifecycleDelegate;
import com.google.android.gms.dynamic.zzb;
import com.google.android.gms.dynamic.zzc;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.dynamic.zzf;
import com.google.android.gms.dynamic.zzg;
import com.google.android.gms.dynamic.zzh;
import com.google.android.gms.dynamic.zzi;
import com.google.android.gms.dynamic.zzo;
import java.util.LinkedList;

public abstract class zza<T extends LifecycleDelegate> {
    private T zzgwd;
    private Bundle zzgwe;
    private LinkedList<zzi> zzgwf;
    private final zzo<T> zzgwg = new zzb(this);

    static /* synthetic */ Bundle zza(zza zza2, Bundle bundle) {
        zza2.zzgwe = null;
        return null;
    }

    static /* synthetic */ LifecycleDelegate zza(zza zza2, LifecycleDelegate lifecycleDelegate) {
        zza2.zzgwd = lifecycleDelegate;
        return lifecycleDelegate;
    }

    static /* synthetic */ LinkedList zza(zza zza2) {
        return zza2.zzgwf;
    }

    /*
     * Enabled aggressive block sorting
     */
    private final void zza(Bundle bundle, zzi zzi2) {
        if (this.zzgwd != null) {
            zzi2.zzb((LifecycleDelegate)this.zzgwd);
            return;
        }
        if (this.zzgwf == null) {
            this.zzgwf = new LinkedList();
        }
        this.zzgwf.add(zzi2);
        if (bundle != null) {
            if (this.zzgwe == null) {
                this.zzgwe = (Bundle)bundle.clone();
            } else {
                this.zzgwe.putAll(bundle);
            }
        }
        this.zza(this.zzgwg);
    }

    static /* synthetic */ LifecycleDelegate zzb(zza zza2) {
        return zza2.zzgwd;
    }

    public static void zzb(FrameLayout frameLayout) {
        Object object = GoogleApiAvailability.getInstance();
        Context context = frameLayout.getContext();
        int n = ((com.google.android.gms.common.zzf)object).isGooglePlayServicesAvailable(context);
        String string2 = zzu.zzi(context, n);
        object = zzu.zzk(context, n);
        LinearLayout linearLayout = new LinearLayout(frameLayout.getContext());
        linearLayout.setOrientation(1);
        linearLayout.setLayoutParams((ViewGroup.LayoutParams)new FrameLayout.LayoutParams(-2, -2));
        frameLayout.addView((View)linearLayout);
        frameLayout = new TextView(frameLayout.getContext());
        frameLayout.setLayoutParams((ViewGroup.LayoutParams)new FrameLayout.LayoutParams(-2, -2));
        frameLayout.setText((CharSequence)string2);
        linearLayout.addView((View)frameLayout);
        frameLayout = com.google.android.gms.common.zzf.zza(context, n, null);
        if (frameLayout != null) {
            string2 = new Button(context);
            string2.setId(16908313);
            string2.setLayoutParams((ViewGroup.LayoutParams)new FrameLayout.LayoutParams(-2, -2));
            string2.setText((CharSequence)object);
            linearLayout.addView((View)string2);
            string2.setOnClickListener((View.OnClickListener)new zzf(context, (Intent)frameLayout));
        }
    }

    private final void zzcz(int n) {
        while (!this.zzgwf.isEmpty() && this.zzgwf.getLast().getState() >= n) {
            this.zzgwf.removeLast();
        }
    }

    public final void onCreate(Bundle bundle) {
        this.zza(bundle, new zzd(this, bundle));
    }

    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        FrameLayout frameLayout = new FrameLayout(layoutInflater.getContext());
        this.zza(bundle, new zze(this, frameLayout, layoutInflater, viewGroup, bundle));
        if (this.zzgwd == null) {
            this.zza(frameLayout);
        }
        return frameLayout;
    }

    public final void onDestroy() {
        if (this.zzgwd != null) {
            this.zzgwd.onDestroy();
            return;
        }
        this.zzcz(1);
    }

    public final void onDestroyView() {
        if (this.zzgwd != null) {
            this.zzgwd.onDestroyView();
            return;
        }
        this.zzcz(2);
    }

    public final void onInflate(Activity activity, Bundle bundle, Bundle bundle2) {
        this.zza(bundle2, new zzc(this, activity, bundle, bundle2));
    }

    public final void onLowMemory() {
        if (this.zzgwd != null) {
            this.zzgwd.onLowMemory();
        }
    }

    public final void onPause() {
        if (this.zzgwd != null) {
            this.zzgwd.onPause();
            return;
        }
        this.zzcz(5);
    }

    public final void onResume() {
        this.zza(null, new zzh(this));
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void onSaveInstanceState(Bundle bundle) {
        if (this.zzgwd != null) {
            this.zzgwd.onSaveInstanceState(bundle);
            return;
        } else {
            if (this.zzgwe == null) return;
            {
                bundle.putAll(this.zzgwe);
                return;
            }
        }
    }

    public final void onStart() {
        this.zza(null, new zzg(this));
    }

    public final void onStop() {
        if (this.zzgwd != null) {
            this.zzgwd.onStop();
            return;
        }
        this.zzcz(4);
    }

    protected void zza(FrameLayout frameLayout) {
        Object object = GoogleApiAvailability.getInstance();
        Context context = frameLayout.getContext();
        int n = ((com.google.android.gms.common.zzf)object).isGooglePlayServicesAvailable(context);
        String string2 = zzu.zzi(context, n);
        object = zzu.zzk(context, n);
        LinearLayout linearLayout = new LinearLayout(frameLayout.getContext());
        linearLayout.setOrientation(1);
        linearLayout.setLayoutParams((ViewGroup.LayoutParams)new FrameLayout.LayoutParams(-2, -2));
        frameLayout.addView((View)linearLayout);
        frameLayout = new TextView(frameLayout.getContext());
        frameLayout.setLayoutParams((ViewGroup.LayoutParams)new FrameLayout.LayoutParams(-2, -2));
        frameLayout.setText((CharSequence)string2);
        linearLayout.addView((View)frameLayout);
        frameLayout = com.google.android.gms.common.zzf.zza(context, n, null);
        if (frameLayout != null) {
            string2 = new Button(context);
            string2.setId(16908313);
            string2.setLayoutParams((ViewGroup.LayoutParams)new FrameLayout.LayoutParams(-2, -2));
            string2.setText((CharSequence)object);
            linearLayout.addView((View)string2);
            string2.setOnClickListener((View.OnClickListener)new zzf(context, (Intent)frameLayout));
        }
    }

    protected abstract void zza(zzo<T> var1);

    public final T zzapw() {
        return this.zzgwd;
    }
}

