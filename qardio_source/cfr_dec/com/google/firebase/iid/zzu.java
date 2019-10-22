/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.util.Base64
 *  android.util.Log
 */
package com.google.firebase.iid;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Base64;
import android.util.Log;
import com.google.android.gms.common.util.zzq;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.List;

final class zzu {
    private final Context zzair;
    private String zzct;
    private String zznzk;
    private int zznzl;
    private int zznzm = 0;

    public zzu(Context context) {
        this.zzair = context;
    }

    public static String zzb(KeyPair object) {
        object = object.getPublic().getEncoded();
        try {
            object = MessageDigest.getInstance("SHA1").digest((byte[])object);
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            Log.w((String)"FirebaseInstanceId", (String)"Unexpected error, device missing required algorithms");
            return null;
        }
        object[0] = (byte)((object[0] & 0xF) + 112);
        object = Base64.encodeToString((byte[])object, (int)0, (int)8, (int)11);
        return object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final void zzcjj() {
        synchronized (this) {
            PackageInfo packageInfo = this.zzoa(this.zzair.getPackageName());
            if (packageInfo != null) {
                this.zznzk = Integer.toString(packageInfo.versionCode);
                this.zzct = packageInfo.versionName;
            }
            return;
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public static String zzf(FirebaseApp object) {
        void var0_2;
        String string2 = ((FirebaseApp)object).getOptions().getGcmSenderId();
        if (string2 != null) {
            String string3 = string2;
            return var0_2;
        } else {
            String string4 = string2 = ((FirebaseApp)object).getOptions().getApplicationId();
            if (!string2.startsWith("1:")) return var0_2;
            {
                String[] arrstring = string2.split(":");
                if (arrstring.length < 2) {
                    return null;
                }
                String string5 = string2 = arrstring[1];
                if (!string2.isEmpty()) return var0_2;
                return null;
            }
        }
    }

    private final PackageInfo zzoa(String string2) {
        try {
            string2 = this.zzair.getPackageManager().getPackageInfo(string2, 0);
            return string2;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            String string3 = String.valueOf((Object)nameNotFoundException);
            Log.w((String)"FirebaseInstanceId", (String)new StringBuilder(String.valueOf(string3).length() + 23).append("Failed to find package ").append(string3).toString());
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final int zzcjf() {
        int n = 0;
        synchronized (this) {
            if (this.zznzm != 0) {
                return this.zznzm;
            }
            Object object = this.zzair.getPackageManager();
            if (object.checkPermission("com.google.android.c2dm.permission.SEND", "com.google.android.gms") != -1) {
                Object object2;
                if (!zzq.isAtLeastO()) {
                    object2 = new Intent("com.google.android.c2dm.intent.REGISTER");
                    object2.setPackage("com.google.android.gms");
                    object2 = object.queryIntentServices((Intent)object2, 0);
                    if (object2 != null && object2.size() > 0) {
                        this.zznzm = 1;
                        return this.zznzm;
                    }
                }
                object2 = new Intent("com.google.iid.TOKEN_REQUEST");
                object2.setPackage("com.google.android.gms");
                object = object.queryBroadcastReceivers((Intent)object2, 0);
                if (object != null && object.size() > 0) {
                    this.zznzm = 2;
                    return this.zznzm;
                }
                Log.w((String)"FirebaseInstanceId", (String)"Failed to resolve IID implementation package, falling back");
                this.zznzm = zzq.isAtLeastO() ? 2 : 1;
                return this.zznzm;
            }
            Log.e((String)"FirebaseInstanceId", (String)"Google Play services missing or without correct permission.");
            return n;
        }
    }

    public final String zzcjg() {
        synchronized (this) {
            if (this.zznzk == null) {
                this.zzcjj();
            }
            String string2 = this.zznzk;
            return string2;
        }
    }

    public final String zzcjh() {
        synchronized (this) {
            if (this.zzct == null) {
                this.zzcjj();
            }
            String string2 = this.zzct;
            return string2;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final int zzcji() {
        synchronized (this) {
            if (this.zznzl != 0) return this.zznzl;
            PackageInfo packageInfo = this.zzoa("com.google.android.gms");
            if (packageInfo == null) return this.zznzl;
            this.zznzl = packageInfo.versionCode;
            return this.zznzl;
        }
    }
}

