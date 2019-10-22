/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.util.Log
 */
package com.google.android.gms.flags.impl;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.util.Log;
import com.google.android.gms.common.util.DynamiteApi;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.zzn;
import com.google.android.gms.flags.impl.zzb;
import com.google.android.gms.flags.impl.zzd;
import com.google.android.gms.flags.impl.zzf;
import com.google.android.gms.flags.impl.zzh;
import com.google.android.gms.flags.impl.zzj;
import com.google.android.gms.internal.zzcaz;

@DynamiteApi
public class FlagProviderImpl
extends zzcaz {
    private boolean zzare = false;
    private SharedPreferences zzbhh;

    @Override
    public boolean getBooleanFlagValue(String string2, boolean bl, int n) {
        if (!this.zzare) {
            return bl;
        }
        return zzb.zza(this.zzbhh, string2, bl);
    }

    @Override
    public int getIntFlagValue(String string2, int n, int n2) {
        if (!this.zzare) {
            return n;
        }
        return zzd.zza(this.zzbhh, string2, n);
    }

    @Override
    public long getLongFlagValue(String string2, long l, int n) {
        if (!this.zzare) {
            return l;
        }
        return zzf.zza(this.zzbhh, string2, l);
    }

    @Override
    public String getStringFlagValue(String string2, String string3, int n) {
        if (!this.zzare) {
            return string3;
        }
        return zzh.zza(this.zzbhh, string2, string3);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void init(IObjectWrapper iObjectWrapper) {
        iObjectWrapper = (Context)zzn.zzx(iObjectWrapper);
        if (this.zzare) {
            return;
        }
        try {
            this.zzbhh = zzj.zzdi(iObjectWrapper.createPackageContext("com.google.android.gms", 0));
            this.zzare = true;
            return;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            return;
        }
        catch (Exception exception) {
            String string2 = String.valueOf(exception.getMessage());
            string2 = string2.length() != 0 ? "Could not retrieve sdk flags, continuing with defaults: ".concat(string2) : new String("Could not retrieve sdk flags, continuing with defaults: ");
            Log.w((String)"FlagProviderImpl", (String)string2);
            return;
        }
    }
}

