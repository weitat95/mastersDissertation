/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 *  android.util.Log
 */
package com.google.android.gms.common;

import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.internal.zzat;
import com.google.android.gms.common.internal.zzau;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zzl;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.zzn;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

abstract class zzh
extends zzau {
    private int zzflb;

    protected zzh(byte[] arrby) {
        boolean bl = false;
        Object object = arrby;
        if (arrby.length != 25) {
            int n = arrby.length;
            object = zzl.zza(arrby, 0, arrby.length, false);
            Log.wtf((String)"GoogleCertificates", (String)new StringBuilder(String.valueOf(object).length() + 51).append("Cert hash data has incorrect length (").append(n).append("):\n").append((String)object).toString(), (Throwable)new Exception());
            object = Arrays.copyOfRange(arrby, 0, 25);
            if (((byte[])object).length == 25) {
                bl = true;
            }
            n = ((byte[])object).length;
            zzbq.checkArgument(bl, new StringBuilder(55).append("cert hash data has incorrect length. length=").append(n).toString());
        }
        this.zzflb = Arrays.hashCode(object);
    }

    protected static byte[] zzfx(String arrby) {
        try {
            arrby = arrby.getBytes("ISO-8859-1");
            return arrby;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new AssertionError(unsupportedEncodingException);
        }
    }

    public boolean equals(Object arrby) {
        block6: {
            block5: {
                if (arrby == null || !(arrby instanceof zzat)) {
                    return false;
                }
                try {
                    arrby = (zzat)arrby;
                    if (arrby.zzagb() == this.hashCode()) break block5;
                    return false;
                }
                catch (RemoteException remoteException) {
                    Log.e((String)"GoogleCertificates", (String)"Failed to get Google certificates from remote", (Throwable)remoteException);
                    return false;
                }
            }
            arrby = arrby.zzaga();
            if (arrby != null) break block6;
            return false;
        }
        arrby = (byte[])zzn.zzx((IObjectWrapper)arrby);
        boolean bl = Arrays.equals(this.getBytes(), arrby);
        return bl;
    }

    abstract byte[] getBytes();

    public int hashCode() {
        return this.zzflb;
    }

    @Override
    public final IObjectWrapper zzaga() {
        return zzn.zzz(this.getBytes());
    }

    @Override
    public final int zzagb() {
        return this.hashCode();
    }
}

