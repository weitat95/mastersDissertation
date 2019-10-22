/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.firebase.internal;

import android.content.Context;
import com.google.firebase.FirebaseApp;
import java.util.concurrent.atomic.AtomicReference;

public final class zzb {
    private static final AtomicReference<zzb> zzmba = new AtomicReference();

    private zzb(Context context) {
    }

    public static zzb zzew(Context context) {
        zzmba.compareAndSet(null, new zzb(context));
        return zzmba.get();
    }

    public static void zzg(FirebaseApp firebaseApp) {
    }
}

