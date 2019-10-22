/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.google.android.gms.internal.zzbg;
import com.google.android.gms.internal.zzbs;
import com.google.android.gms.tagmanager.zzbr;
import com.google.android.gms.tagmanager.zzdj;
import com.google.android.gms.tagmanager.zzgk;
import java.util.Map;

final class zzl
extends zzbr {
    private static final String ID = zzbg.zzme.toString();
    private final Context mContext;

    public zzl(Context context) {
        super(ID, new String[0]);
        this.mContext = context;
    }

    @Override
    public final boolean zzbdp() {
        return true;
    }

    @Override
    public final zzbs zzv(Map<String, zzbs> object) {
        try {
            object = zzgk.zzam(this.mContext.getPackageManager().getPackageInfo((String)this.mContext.getPackageName(), (int)0).versionName);
            return object;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            object = this.mContext.getPackageName();
            String string2 = nameNotFoundException.getMessage();
            zzdj.e(new StringBuilder(String.valueOf(object).length() + 25 + String.valueOf(string2).length()).append("Package name ").append((String)object).append(" not found. ").append(string2).toString());
            return zzgk.zzbgs();
        }
    }
}

