/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 */
package com.google.android.gms.tagmanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

final class zzfu {
    @SuppressLint(value={"CommitPrefEdits"})
    static void zze(Context context, String string2, String string3, String string4) {
        context = context.getSharedPreferences(string2, 0).edit();
        context.putString(string3, string4);
        context.apply();
    }
}

