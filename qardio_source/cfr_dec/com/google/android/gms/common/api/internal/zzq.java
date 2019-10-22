/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Dialog
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnCancelListener
 *  android.content.Intent
 */
package com.google.android.gms.common.api.internal;

import android.app.Activity;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiActivity;
import com.google.android.gms.common.api.internal.zzcf;
import com.google.android.gms.common.api.internal.zzo;
import com.google.android.gms.common.api.internal.zzp;
import com.google.android.gms.common.api.internal.zzr;
import com.google.android.gms.common.zzf;

final class zzq
implements Runnable {
    private final zzp zzfop;
    final /* synthetic */ zzo zzfoq;

    zzq(zzo zzo2, zzp zzp2) {
        this.zzfoq = zzo2;
        this.zzfop = zzp2;
    }

    @Override
    public final void run() {
        if (!this.zzfoq.mStarted) {
            return;
        }
        ConnectionResult connectionResult = this.zzfop.zzahf();
        if (connectionResult.hasResolution()) {
            this.zzfoq.zzfud.startActivityForResult(GoogleApiActivity.zza((Context)this.zzfoq.getActivity(), connectionResult.getResolution(), this.zzfop.zzahe(), false), 1);
            return;
        }
        if (((zzf)this.zzfoq.zzfmy).isUserResolvableError(connectionResult.getErrorCode())) {
            this.zzfoq.zzfmy.zza(this.zzfoq.getActivity(), this.zzfoq.zzfud, connectionResult.getErrorCode(), 2, this.zzfoq);
            return;
        }
        if (connectionResult.getErrorCode() == 18) {
            connectionResult = GoogleApiAvailability.zza(this.zzfoq.getActivity(), this.zzfoq);
            GoogleApiAvailability.zza(this.zzfoq.getActivity().getApplicationContext(), new zzr(this, (Dialog)connectionResult));
            return;
        }
        this.zzfoq.zza(connectionResult, this.zzfop.zzahe());
    }
}

