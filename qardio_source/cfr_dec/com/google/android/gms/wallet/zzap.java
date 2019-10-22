/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Looper
 */
package com.google.android.gms.wallet;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.internal.zzdlo;
import com.google.android.gms.wallet.Wallet;

final class zzap
extends Api.zza<zzdlo, Wallet.WalletOptions> {
    zzap() {
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public final /* synthetic */ Api.zze zza(Context context, Looper looper, zzr zzr2, Object object, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        if ((object = (Wallet.WalletOptions)object) != null) {
            do {
                return new zzdlo(context, looper, zzr2, connectionCallbacks, onConnectionFailedListener, ((Wallet.WalletOptions)object).environment, ((Wallet.WalletOptions)object).theme, ((Wallet.WalletOptions)object).zzldz);
                break;
            } while (true);
        }
        object = new Wallet.WalletOptions(null);
        return new zzdlo(context, looper, zzr2, connectionCallbacks, onConnectionFailedListener, ((Wallet.WalletOptions)object).environment, ((Wallet.WalletOptions)object).theme, ((Wallet.WalletOptions)object).zzldz);
    }
}

