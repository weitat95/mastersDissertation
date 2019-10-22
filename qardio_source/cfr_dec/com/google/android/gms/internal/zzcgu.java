/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.google.android.gms.common.util.zzd;
import com.google.android.gms.internal.zzcgd;
import com.google.android.gms.internal.zzcgk;
import com.google.android.gms.internal.zzcgn;
import com.google.android.gms.internal.zzcgo;
import com.google.android.gms.internal.zzchh;
import com.google.android.gms.internal.zzchi;
import com.google.android.gms.internal.zzchk;
import com.google.android.gms.internal.zzchm;
import com.google.android.gms.internal.zzchx;
import com.google.android.gms.internal.zzcig;
import com.google.android.gms.internal.zzcih;
import com.google.android.gms.internal.zzcim;
import com.google.android.gms.internal.zzcjl;
import com.google.android.gms.internal.zzcjn;
import com.google.android.gms.internal.zzckc;
import com.google.android.gms.internal.zzckg;
import com.google.android.gms.internal.zzclf;
import com.google.android.gms.internal.zzclq;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public final class zzcgu
extends zzcjl {
    private long zzizf;
    private String zzizg;
    private Boolean zzizh;

    zzcgu(zzcim zzcim2) {
        super(zzcim2);
    }

    @Override
    protected final boolean zzaxz() {
        Object object = Calendar.getInstance();
        Object object2 = TimeUnit.MINUTES;
        int n = ((Calendar)object).get(15);
        this.zzizf = ((TimeUnit)((Object)object2)).convert(((Calendar)object).get(16) + n, TimeUnit.MILLISECONDS);
        object2 = Locale.getDefault();
        object = ((Locale)object2).getLanguage().toLowerCase(Locale.ENGLISH);
        object2 = ((Locale)object2).getCountry().toLowerCase(Locale.ENGLISH);
        this.zzizg = new StringBuilder(String.valueOf(object).length() + 1 + String.valueOf(object2).length()).append((String)object).append("-").append((String)object2).toString();
        return false;
    }

    public final long zzayu() {
        this.zzxf();
        return this.zzizf;
    }

    public final String zzayv() {
        this.zzxf();
        return this.zzizg;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final boolean zzdw(Context context) {
        if (this.zzizh != null) return this.zzizh;
        this.zzizh = false;
        try {
            context = context.getPackageManager();
            if (context == null) return this.zzizh;
            context.getPackageInfo("com.google.android.gms", 128);
            this.zzizh = true;
            return this.zzizh;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            return this.zzizh;
        }
    }
}

