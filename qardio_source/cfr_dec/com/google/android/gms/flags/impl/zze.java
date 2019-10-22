/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.SharedPreferences
 */
package com.google.android.gms.flags.impl;

import android.content.SharedPreferences;
import java.util.concurrent.Callable;

final class zze
implements Callable<Integer> {
    private /* synthetic */ SharedPreferences zzhiy;
    private /* synthetic */ String zzhiz;
    private /* synthetic */ Integer zzhjb;

    zze(SharedPreferences sharedPreferences, String string2, Integer n) {
        this.zzhiy = sharedPreferences;
        this.zzhiz = string2;
        this.zzhjb = n;
    }

    @Override
    public final /* synthetic */ Object call() throws Exception {
        return this.zzhiy.getInt(this.zzhiz, this.zzhjb.intValue());
    }
}

