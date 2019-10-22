/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.PendingIntent
 *  android.app.PendingIntent$CanceledException
 *  android.content.Intent
 *  android.content.IntentSender
 *  android.content.IntentSender$SendIntentException
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.util.Log
 */
package com.google.android.gms.internal;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzdlq;
import com.google.android.gms.wallet.FullWallet;
import com.google.android.gms.wallet.MaskedWallet;
import java.lang.ref.WeakReference;

final class zzdlp
extends zzdlq {
    private final WeakReference<Activity> zzebo;
    private final int zzfnf;

    public zzdlp(Activity activity, int n) {
        this.zzebo = new WeakReference<Activity>(activity);
        this.zzfnf = n;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void zza(int n, FullWallet fullWallet, Bundle object) {
        int n2;
        Activity activity = (Activity)this.zzebo.get();
        if (activity == null) {
            Log.d((String)"WalletClientImpl", (String)"Ignoring onFullWalletLoaded, Activity has gone");
            return;
        }
        PendingIntent pendingIntent = null;
        if (object != null) {
            pendingIntent = (PendingIntent)object.getParcelable("com.google.android.gms.wallet.EXTRA_PENDING_INTENT");
        }
        if (((ConnectionResult)(object = new ConnectionResult(n, pendingIntent))).hasResolution()) {
            try {
                ((ConnectionResult)object).startResolutionForResult(activity, this.zzfnf);
                return;
            }
            catch (IntentSender.SendIntentException sendIntentException) {
                Log.w((String)"WalletClientImpl", (String)"Exception starting pending intent", (Throwable)sendIntentException);
                return;
            }
        }
        pendingIntent = new Intent();
        if (((ConnectionResult)object).isSuccess()) {
            n2 = -1;
            pendingIntent.putExtra("com.google.android.gms.wallet.EXTRA_FULL_WALLET", (Parcelable)fullWallet);
        } else {
            n2 = n == 408 ? 0 : 1;
            pendingIntent.putExtra("com.google.android.gms.wallet.EXTRA_ERROR_CODE", n);
        }
        if ((fullWallet = activity.createPendingResult(this.zzfnf, (Intent)pendingIntent, 1073741824)) == null) {
            Log.w((String)"WalletClientImpl", (String)"Null pending result returned for onFullWalletLoaded");
            return;
        }
        try {
            fullWallet.send(n2);
            return;
        }
        catch (PendingIntent.CanceledException canceledException) {
            Log.w((String)"WalletClientImpl", (String)"Exception setting pending result", (Throwable)canceledException);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void zza(int n, MaskedWallet maskedWallet, Bundle object) {
        int n2;
        Activity activity = (Activity)this.zzebo.get();
        if (activity == null) {
            Log.d((String)"WalletClientImpl", (String)"Ignoring onMaskedWalletLoaded, Activity has gone");
            return;
        }
        PendingIntent pendingIntent = null;
        if (object != null) {
            pendingIntent = (PendingIntent)object.getParcelable("com.google.android.gms.wallet.EXTRA_PENDING_INTENT");
        }
        if (((ConnectionResult)(object = new ConnectionResult(n, pendingIntent))).hasResolution()) {
            try {
                ((ConnectionResult)object).startResolutionForResult(activity, this.zzfnf);
                return;
            }
            catch (IntentSender.SendIntentException sendIntentException) {
                Log.w((String)"WalletClientImpl", (String)"Exception starting pending intent", (Throwable)sendIntentException);
                return;
            }
        }
        pendingIntent = new Intent();
        if (((ConnectionResult)object).isSuccess()) {
            n2 = -1;
            pendingIntent.putExtra("com.google.android.gms.wallet.EXTRA_MASKED_WALLET", (Parcelable)maskedWallet);
        } else {
            n2 = n == 408 ? 0 : 1;
            pendingIntent.putExtra("com.google.android.gms.wallet.EXTRA_ERROR_CODE", n);
        }
        if ((maskedWallet = activity.createPendingResult(this.zzfnf, (Intent)pendingIntent, 1073741824)) == null) {
            Log.w((String)"WalletClientImpl", (String)"Null pending result returned for onMaskedWalletLoaded");
            return;
        }
        try {
            maskedWallet.send(n2);
            return;
        }
        catch (PendingIntent.CanceledException canceledException) {
            Log.w((String)"WalletClientImpl", (String)"Exception setting pending result", (Throwable)canceledException);
            return;
        }
    }

    @Override
    public final void zza(int n, boolean bl, Bundle bundle) {
        bundle = (Activity)this.zzebo.get();
        if (bundle == null) {
            Log.d((String)"WalletClientImpl", (String)"Ignoring onPreAuthorizationDetermined, Activity has gone");
            return;
        }
        Intent intent = new Intent();
        intent.putExtra("com.google.android.gm.wallet.EXTRA_IS_USER_PREAUTHORIZED", bl);
        bundle = bundle.createPendingResult(this.zzfnf, intent, 1073741824);
        if (bundle == null) {
            Log.w((String)"WalletClientImpl", (String)"Null pending result returned for onPreAuthorizationDetermined");
            return;
        }
        try {
            bundle.send(-1);
            return;
        }
        catch (PendingIntent.CanceledException canceledException) {
            Log.w((String)"WalletClientImpl", (String)"Exception setting pending result", (Throwable)canceledException);
            return;
        }
    }

    @Override
    public final void zzg(int n, Bundle object) {
        zzbq.checkNotNull(object, "Bundle should not be null");
        Activity activity = (Activity)this.zzebo.get();
        if (activity == null) {
            Log.d((String)"WalletClientImpl", (String)"Ignoring onWalletObjectsCreated, Activity has gone");
            return;
        }
        if (((ConnectionResult)(object = new ConnectionResult(n, (PendingIntent)object.getParcelable("com.google.android.gms.wallet.EXTRA_PENDING_INTENT")))).hasResolution()) {
            try {
                ((ConnectionResult)object).startResolutionForResult(activity, this.zzfnf);
                return;
            }
            catch (IntentSender.SendIntentException sendIntentException) {
                Log.w((String)"WalletClientImpl", (String)"Exception starting pending intent", (Throwable)sendIntentException);
                return;
            }
        }
        object = String.valueOf(object);
        Log.e((String)"WalletClientImpl", (String)new StringBuilder(String.valueOf(object).length() + 75).append("Create Wallet Objects confirmation UI will not be shown connection result: ").append((String)object).toString());
        object = new Intent();
        object.putExtra("com.google.android.gms.wallet.EXTRA_ERROR_CODE", 413);
        object = activity.createPendingResult(this.zzfnf, (Intent)object, 1073741824);
        if (object == null) {
            Log.w((String)"WalletClientImpl", (String)"Null pending result returned for onWalletObjectsCreated");
            return;
        }
        try {
            object.send(1);
            return;
        }
        catch (PendingIntent.CanceledException canceledException) {
            Log.w((String)"WalletClientImpl", (String)"Exception setting pending result", (Throwable)canceledException);
            return;
        }
    }
}

