/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.net.ConnectivityManager
 *  android.net.NetworkInfo
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.google.android.gms.common.util.zzd;
import com.google.android.gms.internal.zzcgd;
import com.google.android.gms.internal.zzcgk;
import com.google.android.gms.internal.zzcgn;
import com.google.android.gms.internal.zzcgo;
import com.google.android.gms.internal.zzcgu;
import com.google.android.gms.internal.zzchh;
import com.google.android.gms.internal.zzchi;
import com.google.android.gms.internal.zzchk;
import com.google.android.gms.internal.zzchm;
import com.google.android.gms.internal.zzchx;
import com.google.android.gms.internal.zzcig;
import com.google.android.gms.internal.zzcih;
import com.google.android.gms.internal.zzcim;
import com.google.android.gms.internal.zzcjk;
import com.google.android.gms.internal.zzcjl;
import com.google.android.gms.internal.zzcjn;
import com.google.android.gms.internal.zzckc;
import com.google.android.gms.internal.zzckg;
import com.google.android.gms.internal.zzclf;
import com.google.android.gms.internal.zzclq;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URLConnection;

public final class zzchq
extends zzcjl {
    public zzchq(zzcim zzcim2) {
        super(zzcim2);
    }

    static /* synthetic */ byte[] zza(zzchq zzchq2, HttpURLConnection httpURLConnection) throws IOException {
        return zzchq.zzc(httpURLConnection);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static byte[] zzc(HttpURLConnection object) throws IOException {
        byte[] arrby;
        block6: {
            ByteArrayOutputStream byteArrayOutputStream;
            arrby = null;
            Object object2 = arrby;
            try {
                byteArrayOutputStream = new ByteArrayOutputStream();
                object2 = arrby;
                object2 = object = ((URLConnection)object).getInputStream();
                arrby = new byte[1024];
                do {
                    object2 = object;
                    int n = ((InputStream)object).read(arrby);
                    if (n > 0) {
                        object2 = object;
                        byteArrayOutputStream.write(arrby, 0, n);
                        continue;
                    }
                    break;
                } while (true);
            }
            catch (Throwable throwable) {
                if (object2 != null) {
                    ((InputStream)object2).close();
                }
                throw throwable;
            }
            object2 = object;
            {
                arrby = byteArrayOutputStream.toByteArray();
                if (object == null) break block6;
            }
            ((InputStream)object).close();
        }
        return arrby;
    }

    @Override
    protected final boolean zzaxz() {
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean zzzs() {
        this.zzxf();
        ConnectivityManager connectivityManager = (ConnectivityManager)((zzcjk)this).getContext().getSystemService("connectivity");
        try {
            connectivityManager = connectivityManager.getActiveNetworkInfo();
            if (connectivityManager == null) return false;
        }
        catch (SecurityException securityException) {
            return false;
        }
        if (!connectivityManager.isConnected()) return false;
        return true;
    }
}

