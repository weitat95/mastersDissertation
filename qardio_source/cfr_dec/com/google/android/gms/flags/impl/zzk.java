/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 */
package com.google.android.gms.flags.impl;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.concurrent.Callable;

final class zzk
implements Callable<SharedPreferences> {
    private /* synthetic */ Context val$context;

    zzk(Context context) {
        this.val$context = context;
    }

    @Override
    public final /* synthetic */ Object call() throws Exception {
        return this.val$context.getSharedPreferences("google_sdk_flags", 0);
    }
}

