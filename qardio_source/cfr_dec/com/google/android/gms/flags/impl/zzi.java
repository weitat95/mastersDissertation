/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.SharedPreferences
 */
package com.google.android.gms.flags.impl;

import android.content.SharedPreferences;
import java.util.concurrent.Callable;

final class zzi
implements Callable<String> {
    private /* synthetic */ SharedPreferences zzhiy;
    private /* synthetic */ String zzhiz;
    private /* synthetic */ String zzhjd;

    zzi(SharedPreferences sharedPreferences, String string2, String string3) {
        this.zzhiy = sharedPreferences;
        this.zzhiz = string2;
        this.zzhjd = string3;
    }

    @Override
    public final /* synthetic */ Object call() throws Exception {
        return this.zzhiy.getString(this.zzhiz, this.zzhjd);
    }
}

