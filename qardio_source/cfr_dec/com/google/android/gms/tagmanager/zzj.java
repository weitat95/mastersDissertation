/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import com.google.android.gms.internal.zzbg;
import com.google.android.gms.internal.zzbs;
import com.google.android.gms.tagmanager.zzbr;
import com.google.android.gms.tagmanager.zzdj;
import com.google.android.gms.tagmanager.zzgk;
import java.util.Map;

final class zzj
extends zzbr {
    private static final String ID = zzbg.zzhw.toString();
    private final Context mContext;

    public zzj(Context context) {
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
            object = this.mContext.getPackageManager();
            object = zzgk.zzam(object.getApplicationLabel(object.getApplicationInfo(this.mContext.getPackageName(), 0)).toString());
            return object;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            zzdj.zzb("App name is not found.", nameNotFoundException);
            return zzgk.zzbgs();
        }
    }
}

