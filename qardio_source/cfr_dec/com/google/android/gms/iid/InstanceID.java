/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.os.Bundle
 *  android.os.Looper
 *  android.util.Base64
 *  android.util.Log
 */
package com.google.android.gms.iid;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.util.ArrayMap;
import android.util.Base64;
import android.util.Log;
import com.google.android.gms.iid.zzl;
import com.google.android.gms.iid.zzo;
import java.io.IOException;
import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.Map;

public class InstanceID {
    private static Map<String, InstanceID> zzifg = new ArrayMap<String, InstanceID>();
    private static zzo zzifh;
    private static zzl zzifi;
    private static String zzifm;
    private Context mContext;
    private KeyPair zzifj;
    private String zzifk = "";
    private long zzifl;

    private InstanceID(Context context, String string2, Bundle bundle) {
        this.mContext = context.getApplicationContext();
        this.zzifk = string2;
    }

    public static InstanceID getInstance(Context context) {
        return InstanceID.getInstance(context, null);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static InstanceID getInstance(Context object, Bundle bundle) {
        synchronized (InstanceID.class) {
            void var0_3;
            InstanceID instanceID;
            void var1_4;
            String string2 = var1_4 == null ? "" : var1_4.getString("subtype");
            if (string2 == null) {
                string2 = "";
            }
            Context context = object.getApplicationContext();
            if (zzifh == null) {
                zzifh = new zzo(context);
                zzifi = new zzl(context);
            }
            zzifm = Integer.toString(InstanceID.zzdm(context));
            InstanceID instanceID2 = instanceID = zzifg.get(string2);
            if (instanceID == null) {
                InstanceID instanceID3 = new InstanceID(context, string2, (Bundle)var1_4);
                zzifg.put(string2, instanceID3);
            }
            return var0_3;
        }
    }

    static String zza(KeyPair object) {
        object = object.getPublic().getEncoded();
        try {
            object = MessageDigest.getInstance("SHA1").digest((byte[])object);
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            Log.w((String)"InstanceID", (String)"Unexpected error, device missing required algorithms");
            return null;
        }
        object[0] = (byte)((object[0] & 0xF) + 112);
        object = Base64.encodeToString((byte[])object, (int)0, (int)8, (int)11);
        return object;
    }

    private final KeyPair zzave() {
        if (this.zzifj == null) {
            this.zzifj = zzifh.zzib(this.zzifk);
        }
        if (this.zzifj == null) {
            this.zzifl = System.currentTimeMillis();
            this.zzifj = zzifh.zzc(this.zzifk, this.zzifl);
        }
        return this.zzifj;
    }

    public static zzo zzavg() {
        return zzifh;
    }

    static int zzdm(Context context) {
        try {
            int n = context.getPackageManager().getPackageInfo((String)context.getPackageName(), (int)0).versionCode;
            return n;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            String string2 = String.valueOf((Object)nameNotFoundException);
            Log.w((String)"InstanceID", (String)new StringBuilder(String.valueOf(string2).length() + 38).append("Never happens: can't find own package ").append(string2).toString());
            return 0;
        }
    }

    static String zzdn(Context object) {
        try {
            object = object.getPackageManager().getPackageInfo((String)object.getPackageName(), (int)0).versionName;
            return object;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            String string2 = String.valueOf((Object)nameNotFoundException);
            Log.w((String)"InstanceID", (String)new StringBuilder(String.valueOf(string2).length() + 38).append("Never happens: can't find own package ").append(string2).toString());
            return null;
        }
    }

    static String zzo(byte[] arrby) {
        return Base64.encodeToString((byte[])arrby, (int)11);
    }

    /*
     * Enabled aggressive block sorting
     */
    public String getToken(String string2, String string3, Bundle object) throws IOException {
        String string4;
        block12: {
            String string5;
            block11: {
                boolean bl;
                boolean bl2 = false;
                boolean bl3 = true;
                if (Looper.getMainLooper() == Looper.myLooper()) {
                    throw new IOException("MAIN_THREAD");
                }
                string5 = zzifh.get("appVersion");
                if (string5 == null || !string5.equals(zzifm)) {
                    bl = true;
                } else {
                    string5 = zzifh.get("lastToken");
                    if (string5 == null) {
                        bl = true;
                    } else {
                        long l = Long.parseLong(string5);
                        bl = System.currentTimeMillis() / 1000L - Long.valueOf(l) > 604800L;
                    }
                }
                string5 = bl ? null : zzifh.zze(this.zzifk, string2, string3);
                if (string5 != null) break block11;
                string5 = object;
                if (object == null) {
                    string5 = new Bundle();
                }
                bl = bl3;
                if (string5.getString("ttl") != null) {
                    bl = false;
                }
                if ("jwt".equals(string5.getString("type"))) {
                    bl = bl2;
                }
                string5 = string4 = this.zzb(string2, string3, (Bundle)string5);
                if (string4 == null) break block11;
                string5 = string4;
                if (bl) break block12;
            }
            return string5;
        }
        zzifh.zza(this.zzifk, string2, string3, string4, zzifm);
        return string4;
    }

    public final void zzavf() {
        this.zzifl = 0L;
        zzifh.zzhz(String.valueOf(this.zzifk).concat("|"));
        this.zzifj = null;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final String zzb(String string2, String string3, Bundle bundle) throws IOException {
        if (string3 != null) {
            bundle.putString("scope", string3);
        }
        bundle.putString("sender", string2);
        string3 = "".equals(this.zzifk) ? string2 : this.zzifk;
        if (!bundle.containsKey("legacy.register")) {
            bundle.putString("subscription", string2);
            bundle.putString("subtype", string3);
            bundle.putString("X-subscription", string2);
            bundle.putString("X-subtype", string3);
        }
        return zzl.zzj(zzifi.zza(bundle, this.zzave()));
    }
}

