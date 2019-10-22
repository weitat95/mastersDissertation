/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.SharedPreferences
 */
package com.google.android.gms.flags.impl;

import android.content.SharedPreferences;
import java.util.concurrent.Callable;

final class zzc
implements Callable<Boolean> {
    private /* synthetic */ SharedPreferences zzhiy;
    private /* synthetic */ String zzhiz;
    private /* synthetic */ Boolean zzhja;

    zzc(SharedPreferences sharedPreferences, String string2, Boolean bl) {
        this.zzhiy = sharedPreferences;
        this.zzhiz = string2;
        this.zzhja = bl;
    }

    @Override
    public final /* synthetic */ Object call() throws Exception {
        return this.zzhiy.getBoolean(this.zzhiz, this.zzhja.booleanValue());
    }
}

