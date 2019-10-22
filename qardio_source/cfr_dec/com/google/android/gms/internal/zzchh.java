/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.text.TextUtils
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzbz;
import com.google.android.gms.common.util.zzd;
import com.google.android.gms.internal.zzbhd;
import com.google.android.gms.internal.zzcgd;
import com.google.android.gms.internal.zzcgi;
import com.google.android.gms.internal.zzcgk;
import com.google.android.gms.internal.zzcgn;
import com.google.android.gms.internal.zzcgo;
import com.google.android.gms.internal.zzcgu;
import com.google.android.gms.internal.zzchi;
import com.google.android.gms.internal.zzchk;
import com.google.android.gms.internal.zzchm;
import com.google.android.gms.internal.zzcho;
import com.google.android.gms.internal.zzchx;
import com.google.android.gms.internal.zzcig;
import com.google.android.gms.internal.zzcih;
import com.google.android.gms.internal.zzcim;
import com.google.android.gms.internal.zzcjk;
import com.google.android.gms.internal.zzcjl;
import com.google.android.gms.internal.zzcjn;
import com.google.android.gms.internal.zzckc;
import com.google.android.gms.internal.zzckg;
import com.google.android.gms.internal.zzclf;
import com.google.android.gms.internal.zzclq;
import com.google.firebase.iid.FirebaseInstanceId;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Locale;

public final class zzchh
extends zzcjl {
    private String mAppId;
    private String zzcwz;
    private String zzdqz;
    private String zzdra;
    private String zzixc;
    private long zzixg;
    private int zzjbk;
    private long zzjbl;
    private int zzjbm;

    zzchh(zzcim zzcim2) {
        super(zzcim2);
    }

    private final String zzaxd() {
        ((zzcjk)this).zzve();
        try {
            String string2 = FirebaseInstanceId.getInstance().getId();
            return string2;
        }
        catch (IllegalStateException illegalStateException) {
            ((zzcjk)this).zzawy().zzazf().log("Failed to retrieve Firebase Instance Id");
            return null;
        }
    }

    final String getAppId() {
        this.zzxf();
        return this.mAppId;
    }

    final String getGmpAppId() {
        this.zzxf();
        return this.zzcwz;
    }

    @Override
    protected final boolean zzaxz() {
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected final void zzayy() {
        Object object;
        Object object2;
        int n;
        String string2;
        int n2 = 1;
        String string3 = "unknown";
        String string4 = "Unknown";
        int n3 = Integer.MIN_VALUE;
        String string5 = "Unknown";
        String string6 = ((zzcjk)this).getContext().getPackageName();
        Object object3 = ((zzcjk)this).getContext().getPackageManager();
        if (object3 == null) {
            ((zzcjk)this).zzawy().zzazd().zzj("PackageManager is null, app identity information might be inaccurate. appId", zzchm.zzjk(string6));
            object = string3;
            string2 = string4;
            n = n3;
            string3 = string5;
        } else {
            try {
                object2 = object3.getInstallerPackageName(string6);
                string3 = object2;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                ((zzcjk)this).zzawy().zzazd().zzj("Error retrieving app installer package name. appId", zzchm.zzjk(string6));
            }
            if (string3 == null) {
                object2 = "manual_install";
            } else {
                object2 = string3;
                if ("com.android.vending".equals(string3)) {
                    object2 = "";
                }
            }
            object = string5;
            string2 = string4;
            try {
                PackageInfo packageInfo = object3.getPackageInfo(((zzcjk)this).getContext().getPackageName(), 0);
                string3 = string5;
                n = n3;
                string2 = string4;
                object = object2;
                if (packageInfo != null) {
                    object = string5;
                    string2 = string4;
                    object3 = object3.getApplicationLabel(packageInfo.applicationInfo);
                    string3 = string5;
                    object = string5;
                    string2 = string4;
                    if (!TextUtils.isEmpty((CharSequence)object3)) {
                        object = string5;
                        string2 = string4;
                        string3 = object3.toString();
                    }
                    object = string3;
                    string2 = string4;
                    string5 = packageInfo.versionName;
                    object = string3;
                    string2 = string5;
                    n = packageInfo.versionCode;
                    string2 = string5;
                    object = object2;
                }
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {
                ((zzcjk)this).zzawy().zzazd().zze("Error retrieving package info. appId, appName", zzchm.zzjk(string6), object);
                string3 = object;
                n = n3;
                object = object2;
            }
        }
        this.mAppId = string6;
        this.zzixc = object;
        this.zzdra = string2;
        this.zzjbk = n;
        this.zzdqz = string3;
        this.zzjbl = 0L;
        object2 = zzbz.zzck(((zzcjk)this).getContext());
        n = object2 != null && ((Status)object2).isSuccess() ? 1 : 0;
        if (n == 0) {
            if (object2 == null) {
                ((zzcjk)this).zzawy().zzazd().log("GoogleService failed to initialize (no status)");
            } else {
                ((zzcjk)this).zzawy().zzazd().zze("GoogleService failed to initialize, status", ((Status)object2).getStatusCode(), ((Status)object2).getStatusMessage());
            }
        }
        if (n != 0) {
            object2 = ((zzcjk)this).zzaxa().zziy("firebase_analytics_collection_enabled");
            if (((zzcjk)this).zzaxa().zzaya()) {
                ((zzcjk)this).zzawy().zzazh().log("Collection disabled with firebase_analytics_collection_deactivated=1");
                n = 0;
            } else if (object2 != null && !((Boolean)object2).booleanValue()) {
                ((zzcjk)this).zzawy().zzazh().log("Collection disabled with firebase_analytics_collection_enabled=0");
                n = 0;
            } else if (object2 == null && zzbz.zzaji()) {
                ((zzcjk)this).zzawy().zzazh().log("Collection disabled with google_app_measurement_enable=0");
                n = 0;
            } else {
                ((zzcjk)this).zzawy().zzazj().log("Collection enabled");
                n = 1;
            }
        } else {
            n = 0;
        }
        this.zzcwz = "";
        this.zzixg = 0L;
        try {
            string3 = zzbz.zzajh();
            object2 = string3;
            if (TextUtils.isEmpty((CharSequence)string3)) {
                object2 = "";
            }
            this.zzcwz = object2;
            if (n != 0) {
                ((zzcjk)this).zzawy().zzazj().zze("App package, google app id", this.mAppId, this.zzcwz);
            }
        }
        catch (IllegalStateException illegalStateException) {
            ((zzcjk)this).zzawy().zzazd().zze("getGoogleAppId or isMeasurementEnabled failed with exception. appId", zzchm.zzjk(string6), illegalStateException);
        }
        if (Build.VERSION.SDK_INT >= 16) {
            n = zzbhd.zzcz(((zzcjk)this).getContext()) ? n2 : 0;
            this.zzjbm = n;
            return;
        }
        this.zzjbm = 0;
    }

    final String zzayz() {
        byte[] arrby = new byte[16];
        ((zzcjk)this).zzawu().zzbaz().nextBytes(arrby);
        return String.format(Locale.US, "%032x", new BigInteger(1, arrby));
    }

    final int zzaza() {
        this.zzxf();
        return this.zzjbk;
    }

    final int zzazb() {
        this.zzxf();
        return this.zzjbm;
    }

    /*
     * Enabled aggressive block sorting
     */
    final zzcgi zzjg(String string2) {
        boolean bl;
        ((zzcjk)this).zzve();
        String string3 = this.getAppId();
        String string4 = this.getGmpAppId();
        this.zzxf();
        String string5 = this.zzdra;
        long l = this.zzaza();
        this.zzxf();
        String string6 = this.zzixc;
        this.zzxf();
        ((zzcjk)this).zzve();
        if (this.zzjbl == 0L) {
            this.zzjbl = this.zziwf.zzawu().zzaf(((zzcjk)this).getContext(), ((zzcjk)this).getContext().getPackageName());
        }
        long l2 = this.zzjbl;
        boolean bl2 = this.zziwf.isEnabled();
        boolean bl3 = !this.zzawz().zzjdj;
        String string7 = this.zzaxd();
        this.zzxf();
        long l3 = this.zziwf.zzbaf();
        int n = this.zzazb();
        Boolean bl4 = ((zzcjk)this).zzaxa().zziy("google_analytics_adid_collection_enabled");
        if (bl4 != null && !bl4.booleanValue()) {
            bl = false;
            return new zzcgi(string3, string4, string5, l, string6, 11910L, l2, string2, bl2, bl3, string7, 0L, l3, n, bl);
        }
        bl = true;
        return new zzcgi(string3, string4, string5, l, string6, 11910L, l2, string2, bl2, bl3, string7, 0L, l3, n, bl);
    }
}

