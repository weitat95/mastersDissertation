/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.util.Log
 *  android.util.SparseArray
 */
package com.google.android.gms.common.api.internal;

import android.util.Log;
import android.util.SparseArray;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.LifecycleCallback;
import com.google.android.gms.common.api.internal.zzce;
import com.google.android.gms.common.api.internal.zzcf;
import com.google.android.gms.common.api.internal.zzo;
import com.google.android.gms.common.api.internal.zzp;
import com.google.android.gms.common.internal.zzbq;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicReference;

public class zzi
extends zzo {
    private final SparseArray<zza> zzfnx = new SparseArray();

    private zzi(zzcf zzcf2) {
        super(zzcf2);
        this.zzfud.zza("AutoManageHelper", this);
    }

    public static zzi zza(zzce object) {
        zzi zzi2 = (object = zzi.zzb((zzce)object)).zza("AutoManageHelper", zzi.class);
        if (zzi2 != null) {
            return zzi2;
        }
        return new zzi((zzcf)object);
    }

    private final zza zzbs(int n) {
        if (this.zzfnx.size() <= n) {
            return null;
        }
        return (zza)this.zzfnx.get(this.zzfnx.keyAt(n));
    }

    @Override
    public final void dump(String string2, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        for (int i = 0; i < this.zzfnx.size(); ++i) {
            zza zza2 = this.zzbs(i);
            if (zza2 == null) continue;
            printWriter.append(string2).append("GoogleApiClient #").print(zza2.zzfny);
            printWriter.println(":");
            zza2.zzfnz.dump(String.valueOf(string2).concat("  "), fileDescriptor, printWriter, arrstring);
        }
    }

    @Override
    public final void onStart() {
        super.onStart();
        boolean bl = this.mStarted;
        Object object = String.valueOf(this.zzfnx);
        Log.d((String)"AutoManageHelper", (String)new StringBuilder(String.valueOf(object).length() + 14).append("onStart ").append(bl).append(" ").append((String)object).toString());
        if (this.zzfol.get() == null) {
            for (int i = 0; i < this.zzfnx.size(); ++i) {
                object = this.zzbs(i);
                if (object == null) continue;
                ((zza)object).zzfnz.connect();
            }
        }
    }

    @Override
    public final void onStop() {
        super.onStop();
        for (int i = 0; i < this.zzfnx.size(); ++i) {
            zza zza2 = this.zzbs(i);
            if (zza2 == null) continue;
            zza2.zzfnz.disconnect();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void zza(int n, GoogleApiClient googleApiClient, GoogleApiClient.OnConnectionFailedListener object) {
        zzbq.checkNotNull(googleApiClient, "GoogleApiClient instance cannot be null");
        boolean bl = this.zzfnx.indexOfKey(n) < 0;
        zzbq.zza(bl, new StringBuilder(54).append("Already managing a GoogleApiClient with id ").append(n).toString());
        zzp zzp2 = (zzp)this.zzfol.get();
        bl = this.mStarted;
        String string2 = String.valueOf(zzp2);
        Log.d((String)"AutoManageHelper", (String)new StringBuilder(String.valueOf(string2).length() + 49).append("starting AutoManage for client ").append(n).append(" ").append(bl).append(" ").append(string2).toString());
        object = new zza(this, n, googleApiClient, (GoogleApiClient.OnConnectionFailedListener)object);
        this.zzfnx.put(n, object);
        if (this.mStarted && zzp2 == null) {
            object = String.valueOf(googleApiClient);
            Log.d((String)"AutoManageHelper", (String)new StringBuilder(String.valueOf(object).length() + 11).append("connecting ").append((String)object).toString());
            googleApiClient.connect();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected final void zza(ConnectionResult connectionResult, int n) {
        Log.w((String)"AutoManageHelper", (String)"Unresolved error while connecting client. Stopping auto-manage.");
        if (n < 0) {
            Log.wtf((String)"AutoManageHelper", (String)"AutoManageLifecycleHelper received onErrorResolutionFailed callback but no failing client ID is set", (Throwable)new Exception());
            return;
        } else {
            GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener = (zza)this.zzfnx.get(n);
            if (onConnectionFailedListener == null) return;
            {
                this.zzbr(n);
                onConnectionFailedListener = ((zza)onConnectionFailedListener).zzfoa;
                if (onConnectionFailedListener == null) return;
                {
                    onConnectionFailedListener.onConnectionFailed(connectionResult);
                    return;
                }
            }
        }
    }

    @Override
    protected final void zzagz() {
        for (int i = 0; i < this.zzfnx.size(); ++i) {
            zza zza2 = this.zzbs(i);
            if (zza2 == null) continue;
            zza2.zzfnz.connect();
        }
    }

    public final void zzbr(int n) {
        zza zza2 = (zza)this.zzfnx.get(n);
        this.zzfnx.remove(n);
        if (zza2 != null) {
            zza2.zzfnz.unregisterConnectionFailedListener(zza2);
            zza2.zzfnz.disconnect();
        }
    }

    final class zza
    implements GoogleApiClient.OnConnectionFailedListener {
        public final int zzfny;
        public final GoogleApiClient zzfnz;
        public final GoogleApiClient.OnConnectionFailedListener zzfoa;
        private /* synthetic */ zzi zzfob;

        public zza(zzi zzi2, int n, GoogleApiClient googleApiClient, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
            this.zzfob = zzi2;
            this.zzfny = n;
            this.zzfnz = googleApiClient;
            this.zzfoa = onConnectionFailedListener;
            googleApiClient.registerConnectionFailedListener(this);
        }

        @Override
        public final void onConnectionFailed(ConnectionResult connectionResult) {
            String string2 = String.valueOf(connectionResult);
            Log.d((String)"AutoManageHelper", (String)new StringBuilder(String.valueOf(string2).length() + 27).append("beginFailureResolution for ").append(string2).toString());
            this.zzfob.zzb(connectionResult, this.zzfny);
        }
    }

}

