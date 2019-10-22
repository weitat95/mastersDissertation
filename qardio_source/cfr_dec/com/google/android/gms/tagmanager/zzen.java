/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.DisplayMetrics
 *  android.view.Display
 *  android.view.WindowManager
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import com.google.android.gms.internal.zzbg;
import com.google.android.gms.internal.zzbs;
import com.google.android.gms.tagmanager.zzbr;
import com.google.android.gms.tagmanager.zzgk;
import java.util.Map;

final class zzen
extends zzbr {
    private static final String ID = zzbg.zziu.toString();
    private final Context mContext;

    public zzen(Context context) {
        super(ID, new String[0]);
        this.mContext = context;
    }

    @Override
    public final boolean zzbdp() {
        return true;
    }

    @Override
    public final zzbs zzv(Map<String, zzbs> displayMetrics) {
        displayMetrics = new DisplayMetrics();
        ((WindowManager)this.mContext.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        int n = displayMetrics.widthPixels;
        int n2 = displayMetrics.heightPixels;
        return zzgk.zzam(new StringBuilder(23).append(n).append("x").append(n2).toString());
    }
}

