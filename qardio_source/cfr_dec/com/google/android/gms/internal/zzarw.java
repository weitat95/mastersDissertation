/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.net.ConnectivityManager
 *  android.net.NetworkInfo
 */
package com.google.android.gms.internal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.google.android.gms.analytics.zzj;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzapr;
import com.google.android.gms.internal.zzapt;
import com.google.android.gms.internal.zzapz;
import com.google.android.gms.internal.zzaqc;
import com.google.android.gms.internal.zzarv;

class zzarw
extends BroadcastReceiver {
    private static String zzdyg = zzarw.class.getName();
    private boolean mRegistered;
    private final zzaqc zzdta;
    private boolean zzdyh;

    zzarw(zzaqc zzaqc2) {
        zzbq.checkNotNull(zzaqc2);
        this.zzdta = zzaqc2;
    }

    private final void zzzq() {
        this.zzdta.zzwt();
        this.zzdta.zzwx();
    }

    private final boolean zzzs() {
        block3: {
            ConnectivityManager connectivityManager = (ConnectivityManager)this.zzdta.getContext().getSystemService("connectivity");
            try {
                connectivityManager = connectivityManager.getActiveNetworkInfo();
                if (connectivityManager == null) break block3;
            }
            catch (SecurityException securityException) {
                return false;
            }
            boolean bl = connectivityManager.isConnected();
            if (!bl) break block3;
            return true;
        }
        return false;
    }

    public final boolean isConnected() {
        if (!this.mRegistered) {
            this.zzdta.zzwt().zzdx("Connectivity unknown. Receiver not registered");
        }
        return this.zzdyh;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onReceive(Context object, Intent intent) {
        this.zzzq();
        object = intent.getAction();
        this.zzdta.zzwt().zza("NetworkBroadcastReceiver received action", object);
        if ("android.net.conn.CONNECTIVITY_CHANGE".equals(object)) {
            boolean bl = this.zzzs();
            if (this.zzdyh == bl) return;
            {
                this.zzdyh = bl;
                object = this.zzdta.zzwx();
                ((zzapz)object).zza("Network connectivity status changed", bl);
                ((zzapz)object).zzwv().zzc(new zzapt((zzapr)object, bl));
                return;
            }
        } else {
            if (!"com.google.analytics.RADIO_POWERED".equals(object)) {
                this.zzdta.zzwt().zzd("NetworkBroadcastReceiver received unknown action", object);
                return;
            }
            if (intent.hasExtra(zzdyg)) return;
            {
                object = this.zzdta.zzwx();
                ((zzapz)object).zzdu("Radio powered up");
                ((zzapr)object).zzwn();
                return;
            }
        }
    }

    public final void unregister() {
        if (!this.mRegistered) {
            return;
        }
        this.zzdta.zzwt().zzdu("Unregistering connectivity change receiver");
        this.mRegistered = false;
        this.zzdyh = false;
        Context context = this.zzdta.getContext();
        try {
            context.unregisterReceiver((BroadcastReceiver)this);
            return;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            this.zzdta.zzwt().zze("Failed to unregister the network broadcast receiver", illegalArgumentException);
            return;
        }
    }

    public final void zzzp() {
        this.zzzq();
        if (this.mRegistered) {
            return;
        }
        Context context = this.zzdta.getContext();
        context.registerReceiver((BroadcastReceiver)this, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        IntentFilter intentFilter = new IntentFilter("com.google.analytics.RADIO_POWERED");
        intentFilter.addCategory(context.getPackageName());
        context.registerReceiver((BroadcastReceiver)this, intentFilter);
        this.zzdyh = this.zzzs();
        this.zzdta.zzwt().zza("Registering connectivity change receiver. Network connected", this.zzdyh);
        this.mRegistered = true;
    }

    public final void zzzr() {
        Context context = this.zzdta.getContext();
        Intent intent = new Intent("com.google.analytics.RADIO_POWERED");
        intent.addCategory(context.getPackageName());
        intent.putExtra(zzdyg, true);
        context.sendOrderedBroadcast(intent, null);
    }
}

