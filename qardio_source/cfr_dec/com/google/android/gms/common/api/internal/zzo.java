/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnCancelListener
 *  android.content.Intent
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Parcelable
 */
package com.google.android.gms.common.api.internal;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.internal.LifecycleCallback;
import com.google.android.gms.common.api.internal.zzcf;
import com.google.android.gms.common.api.internal.zzp;
import com.google.android.gms.common.api.internal.zzq;
import java.util.concurrent.atomic.AtomicReference;

public abstract class zzo
extends LifecycleCallback
implements DialogInterface.OnCancelListener {
    protected volatile boolean mStarted;
    protected final GoogleApiAvailability zzfmy;
    protected final AtomicReference<zzp> zzfol = new AtomicReference<Object>(null);
    private final Handler zzfom = new Handler(Looper.getMainLooper());

    protected zzo(zzcf zzcf2) {
        this(zzcf2, GoogleApiAvailability.getInstance());
    }

    private zzo(zzcf zzcf2, GoogleApiAvailability googleApiAvailability) {
        super(zzcf2);
        this.zzfmy = googleApiAvailability;
    }

    private static int zza(zzp zzp2) {
        if (zzp2 == null) {
            return -1;
        }
        return zzp2.zzahe();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public final void onActivityResult(int var1_1, int var2_2, Intent var3_3) {
        block8: {
            var4_4 = 13;
            var5_5 = this.zzfol.get();
            switch (var1_1) {
                case 2: {
                    var4_4 = this.zzfmy.isGooglePlayServicesAvailable((Context)this.getActivity());
                    var2_2 = var4_4 == 0 ? 1 : 0;
                }
                case 1: {
                    if (var2_2 != -1) ** GOTO lbl12
                    var1_1 = 1;
                    var3_3 = var5_5;
                    break block8;
lbl12:
                    // 1 sources
                    if (var2_2 != 0) ** GOTO lbl20
                    var1_1 = var4_4;
                    if (var3_3 != null) {
                        var1_1 = var3_3.getIntExtra("<<ResolutionFailureErrorDetail>>", 13);
                    }
                    var3_3 = new zzp(new ConnectionResult(var1_1, null), zzo.zza(var5_5));
                    this.zzfol.set((zzp)var3_3);
                    var1_1 = 0;
                    break block8;
                }
lbl20:
                // 2 sources
                default: {
                    var1_1 = 0;
                    var3_3 = var5_5;
                    break block8;
                }
            }
            if (var5_5 == null) return;
            var3_3 = var5_5;
            var1_1 = var2_2;
            if (var5_5.zzahf().getErrorCode() == 18) {
                var3_3 = var5_5;
                var1_1 = var2_2;
                if (var4_4 == 18) {
                    return;
                }
            }
        }
        if (var1_1 != 0) {
            this.zzahd();
            return;
        }
        if (var3_3 == null) return;
        this.zza(var3_3.zzahf(), var3_3.zzahe());
    }

    public void onCancel(DialogInterface dialogInterface) {
        this.zza(new ConnectionResult(13, null), zzo.zza(this.zzfol.get()));
        this.zzahd();
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    @Override
    public final void onCreate(Bundle object) {
        super.onCreate((Bundle)object);
        if (object != null) {
            void var1_3;
            AtomicReference<zzp> atomicReference = this.zzfol;
            if (object.getBoolean("resolving_error", false)) {
                zzp zzp2 = new zzp(new ConnectionResult(object.getInt("failed_status"), (PendingIntent)object.getParcelable("failed_resolution")), object.getInt("failed_client_id", -1));
            } else {
                Object var1_4 = null;
            }
            atomicReference.set((zzp)var1_3);
        }
    }

    @Override
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        zzp zzp2 = this.zzfol.get();
        if (zzp2 != null) {
            bundle.putBoolean("resolving_error", true);
            bundle.putInt("failed_client_id", zzp2.zzahe());
            bundle.putInt("failed_status", zzp2.zzahf().getErrorCode());
            bundle.putParcelable("failed_resolution", (Parcelable)zzp2.zzahf().getResolution());
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        this.mStarted = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        this.mStarted = false;
    }

    protected abstract void zza(ConnectionResult var1, int var2);

    protected abstract void zzagz();

    protected final void zzahd() {
        this.zzfol.set(null);
        this.zzagz();
    }

    public final void zzb(ConnectionResult object, int n) {
        if (this.zzfol.compareAndSet(null, (zzp)(object = new zzp((ConnectionResult)object, n)))) {
            this.zzfom.post((Runnable)new zzq(this, (zzp)object));
        }
    }
}

