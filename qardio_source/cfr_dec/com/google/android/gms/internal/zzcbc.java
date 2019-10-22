/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.StrictMode
 *  android.os.StrictMode$ThreadPolicy
 */
package com.google.android.gms.internal;

import android.os.StrictMode;
import java.util.concurrent.Callable;

public final class zzcbc {
    public static <T> T zzb(Callable<T> callable) throws Exception {
        StrictMode.ThreadPolicy threadPolicy = StrictMode.getThreadPolicy();
        try {
            StrictMode.setThreadPolicy((StrictMode.ThreadPolicy)StrictMode.ThreadPolicy.LAX);
            callable = callable.call();
            return (T)callable;
        }
        finally {
            StrictMode.setThreadPolicy((StrictMode.ThreadPolicy)threadPolicy);
        }
    }
}

