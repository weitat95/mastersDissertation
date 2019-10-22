/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.common;

import com.google.android.gms.common.zzh;
import java.lang.ref.WeakReference;

abstract class zzj
extends zzh {
    private static final WeakReference<byte[]> zzfle = new WeakReference<Object>(null);
    private WeakReference<byte[]> zzfld = zzfle;

    zzj(byte[] arrby) {
        super(arrby);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    final byte[] getBytes() {
        synchronized (this) {
            byte[] arrby;
            byte[] arrby2 = arrby = (byte[])this.zzfld.get();
            if (arrby == null) {
                arrby2 = this.zzagc();
                this.zzfld = new WeakReference<byte[]>(arrby2);
            }
            return arrby2;
        }
    }

    protected abstract byte[] zzagc();
}

