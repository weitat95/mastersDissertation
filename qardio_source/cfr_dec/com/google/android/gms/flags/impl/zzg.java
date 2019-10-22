/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.SharedPreferences
 */
package com.google.android.gms.flags.impl;

import android.content.SharedPreferences;
import java.util.concurrent.Callable;

final class zzg
implements Callable<Long> {
    private /* synthetic */ SharedPreferences zzhiy;
    private /* synthetic */ String zzhiz;
    private /* synthetic */ Long zzhjc;

    zzg(SharedPreferences sharedPreferences, String string2, Long l) {
        this.zzhiy = sharedPreferences;
        this.zzhiz = string2;
        this.zzhjc = l;
    }

    @Override
    public final /* synthetic */ Object call() throws Exception {
        return this.zzhiy.getLong(this.zzhiz, this.zzhjc.longValue());
    }
}

