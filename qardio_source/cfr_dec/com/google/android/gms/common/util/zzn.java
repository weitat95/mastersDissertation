/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.common.util;

import java.io.Closeable;
import java.io.IOException;

public final class zzn {
    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static void closeQuietly(Closeable closeable) {
        if (closeable == null) return;
        try {
            closeable.close();
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }
}

