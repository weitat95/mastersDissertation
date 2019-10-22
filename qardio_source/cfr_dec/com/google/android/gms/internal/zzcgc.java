/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Looper
 */
package com.google.android.gms.internal;

import android.os.Looper;
import com.google.android.gms.common.internal.zzbq;

public final class zzcgc {
    /*
     * Enabled aggressive block sorting
     */
    public static Looper zzavy() {
        boolean bl = Looper.myLooper() != null;
        zzbq.zza(bl, "Can't create handler inside thread that has not called Looper.prepare()");
        return Looper.myLooper();
    }
}

