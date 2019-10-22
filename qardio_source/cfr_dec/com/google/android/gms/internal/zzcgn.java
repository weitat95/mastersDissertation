/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.os.Bundle
 *  android.text.TextUtils
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zzd;
import com.google.android.gms.common.util.zzs;
import com.google.android.gms.internal.zzbhf;
import com.google.android.gms.internal.zzcgd;
import com.google.android.gms.internal.zzcgk;
import com.google.android.gms.internal.zzcgo;
import com.google.android.gms.internal.zzcgu;
import com.google.android.gms.internal.zzchc;
import com.google.android.gms.internal.zzchd;
import com.google.android.gms.internal.zzchh;
import com.google.android.gms.internal.zzchi;
import com.google.android.gms.internal.zzchk;
import com.google.android.gms.internal.zzchm;
import com.google.android.gms.internal.zzcho;
import com.google.android.gms.internal.zzchx;
import com.google.android.gms.internal.zzcig;
import com.google.android.gms.internal.zzcih;
import com.google.android.gms.internal.zzcim;
import com.google.android.gms.internal.zzcjk;
import com.google.android.gms.internal.zzcjn;
import com.google.android.gms.internal.zzckc;
import com.google.android.gms.internal.zzckg;
import com.google.android.gms.internal.zzclf;
import com.google.android.gms.internal.zzclq;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class zzcgn
extends zzcjk {
    private Boolean zzdvl;

    zzcgn(zzcim zzcim2) {
        super(zzcim2);
    }

    public static long zzayb() {
        return zzchc.zzjbg.get();
    }

    public static long zzayc() {
        return zzchc.zzjag.get();
    }

    public static boolean zzaye() {
        return zzchc.zzjab.get();
    }

    public final long zza(String string2, zzchd<Long> zzchd2) {
        if (string2 == null) {
            return zzchd2.get();
        }
        string2 = ((zzcjk)this).zzawv().zzam(string2, zzchd2.getKey());
        if (TextUtils.isEmpty((CharSequence)string2)) {
            return zzchd2.get();
        }
        try {
            long l = zzchd2.get((long)Long.valueOf(string2));
            return l;
        }
        catch (NumberFormatException numberFormatException) {
            return zzchd2.get();
        }
    }

    public final boolean zzaya() {
        Boolean bl = this.zziy("firebase_analytics_collection_deactivated");
        return bl != null && bl != false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final String zzayd() {
        try {
            return (String)Class.forName("android.os.SystemProperties").getMethod("get", String.class, String.class).invoke(null, "debug.firebase.analytics.app", "");
        }
        catch (ClassNotFoundException classNotFoundException) {
            ((zzcjk)this).zzawy().zzazd().zzj("Could not find SystemProperties class", classNotFoundException);
            do {
                return "";
                break;
            } while (true);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            ((zzcjk)this).zzawy().zzazd().zzj("Could not find SystemProperties.get() method", noSuchMethodException);
            return "";
        }
        catch (IllegalAccessException illegalAccessException) {
            ((zzcjk)this).zzawy().zzazd().zzj("Could not access SystemProperties.get()", illegalAccessException);
            return "";
        }
        catch (InvocationTargetException invocationTargetException) {
            ((zzcjk)this).zzawy().zzazd().zzj("SystemProperties.get() threw an exception", invocationTargetException);
            return "";
        }
    }

    public final int zzb(String string2, zzchd<Integer> zzchd2) {
        if (string2 == null) {
            return zzchd2.get();
        }
        string2 = ((zzcjk)this).zzawv().zzam(string2, zzchd2.getKey());
        if (TextUtils.isEmpty((CharSequence)string2)) {
            return zzchd2.get();
        }
        try {
            int n = zzchd2.get((int)Integer.valueOf(string2));
            return n;
        }
        catch (NumberFormatException numberFormatException) {
            return zzchd2.get();
        }
    }

    public final int zzix(String string2) {
        return this.zzb(string2, zzchc.zzjar);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    final Boolean zziy(String string2) {
        boolean bl;
        ApplicationInfo applicationInfo;
        Boolean bl2 = null;
        zzbq.zzgm(string2);
        try {
            if (((zzcjk)this).getContext().getPackageManager() == null) {
                ((zzcjk)this).zzawy().zzazd().log("Failed to load metadata: PackageManager is null");
                return null;
            }
            applicationInfo = zzbhf.zzdb(((zzcjk)this).getContext()).getApplicationInfo(((zzcjk)this).getContext().getPackageName(), 128);
            if (applicationInfo == null) {
                ((zzcjk)this).zzawy().zzazd().log("Failed to load metadata: ApplicationInfo is null");
                return null;
            }
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            ((zzcjk)this).zzawy().zzazd().zzj("Failed to load metadata: Package name not found", (Object)nameNotFoundException);
            return null;
        }
        {
            if (applicationInfo.metaData == null) {
                ((zzcjk)this).zzawy().zzazd().log("Failed to load metadata: Metadata bundle is null");
                return null;
            }
            if (!applicationInfo.metaData.containsKey(string2)) return bl2;
            bl = applicationInfo.metaData.getBoolean(string2);
        }
        return bl;
    }

    public final boolean zziz(String string2) {
        return "1".equals(((zzcjk)this).zzawv().zzam(string2, "gaia_collection_enabled"));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final boolean zzyp() {
        if (this.zzdvl == null) {
            synchronized (this) {
                if (this.zzdvl == null) {
                    Object object = ((zzcjk)this).getContext().getApplicationInfo();
                    String string2 = zzs.zzamo();
                    if (object != null) {
                        object = ((ApplicationInfo)object).processName;
                        boolean bl = object != null && ((String)object).equals(string2);
                        this.zzdvl = bl;
                    }
                    if (this.zzdvl == null) {
                        this.zzdvl = Boolean.TRUE;
                        ((zzcjk)this).zzawy().zzazd().log("My process not in the list of running processes");
                    }
                }
            }
        }
        return this.zzdvl;
    }
}

