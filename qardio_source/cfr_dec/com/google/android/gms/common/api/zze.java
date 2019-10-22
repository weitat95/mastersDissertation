/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.common.api;

import java.util.Map;
import java.util.WeakHashMap;

public abstract class zze {
    private static final Object sLock;
    private static final Map<Object, zze> zzfng;

    static {
        zzfng = new WeakHashMap<Object, zze>();
        sLock = new Object();
    }

    public abstract void remove(int var1);
}

