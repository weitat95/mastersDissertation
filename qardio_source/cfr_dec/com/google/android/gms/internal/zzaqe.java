/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.common.internal.zzbq;

public final class zzaqe {
    private final Context mApplicationContext;
    private final Context zzdts;

    public zzaqe(Context context) {
        zzbq.checkNotNull(context);
        context = context.getApplicationContext();
        zzbq.checkNotNull(context, "Application context can't be null");
        this.mApplicationContext = context;
        this.zzdts = context;
    }

    public final Context getApplicationContext() {
        return this.mApplicationContext;
    }

    public final Context zzxg() {
        return this.zzdts;
    }
}

