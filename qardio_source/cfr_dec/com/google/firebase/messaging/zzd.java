/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 *  android.util.Base64
 *  android.util.Log
 */
package com.google.firebase.messaging;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import com.google.android.gms.measurement.AppMeasurement;
import com.google.firebase.messaging.zzb;
import com.google.firebase.messaging.zzc;

final class zzd {
    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void zzb(Context object, String string2, Intent object2) {
        Bundle bundle = new Bundle();
        String string3 = object2.getStringExtra("google.c.a.c_id");
        if (string3 != null) {
            bundle.putString("_nmid", string3);
        }
        if ((string3 = object2.getStringExtra("google.c.a.c_l")) != null) {
            bundle.putString("_nmn", string3);
        }
        if ((string3 = object2.getStringExtra("from")) == null || !string3.startsWith("/topics/")) {
            string3 = null;
        }
        if (string3 != null) {
            bundle.putString("_nt", string3);
        }
        try {
            bundle.putInt("_nmt", Integer.valueOf(object2.getStringExtra("google.c.a.ts")).intValue());
        }
        catch (NumberFormatException numberFormatException) {
            Log.w((String)"FirebaseMessaging", (String)"Error while parsing timestamp in GCM event", (Throwable)numberFormatException);
        }
        if (object2.hasExtra("google.c.a.udt")) {
            try {
                bundle.putInt("_ndt", Integer.valueOf(object2.getStringExtra("google.c.a.udt")).intValue());
            }
            catch (NumberFormatException numberFormatException) {
                Log.w((String)"FirebaseMessaging", (String)"Error while parsing use_device_time in GCM event", (Throwable)numberFormatException);
            }
        }
        if (Log.isLoggable((String)"FirebaseMessaging", (int)3)) {
            String string4 = String.valueOf((Object)bundle);
            Log.d((String)"FirebaseMessaging", (String)new StringBuilder(String.valueOf(string2).length() + 22 + String.valueOf(string4).length()).append("Sending event=").append(string2).append(" params=").append(string4).toString());
        }
        if ((object = zzd.zzdc((Context)object)) != null) {
            ((AppMeasurement)object).logEventInternal("fcm", string2, bundle);
            return;
        }
        Log.w((String)"FirebaseMessaging", (String)"Unable to log event: analytics library is missing");
    }

    private static AppMeasurement zzdc(Context object) {
        try {
            object = AppMeasurement.getInstance(object);
            return object;
        }
        catch (NoClassDefFoundError noClassDefFoundError) {
            return null;
        }
    }

    public static void zzf(Context context, Intent intent) {
        String string2 = intent.getStringExtra("google.c.a.abt");
        if (string2 != null) {
            zzc.zza(context, "fcm", Base64.decode((String)string2, (int)0), new zzb(), 1);
        }
        zzd.zzb(context, "_nr", intent);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void zzg(Context context, Intent intent) {
        if (intent != null) {
            if ("1".equals(intent.getStringExtra("google.c.a.tc"))) {
                AppMeasurement appMeasurement = zzd.zzdc(context);
                if (Log.isLoggable((String)"FirebaseMessaging", (int)3)) {
                    Log.d((String)"FirebaseMessaging", (String)"Received event with track-conversion=true. Setting user property and reengagement event");
                }
                if (appMeasurement != null) {
                    String string2 = intent.getStringExtra("google.c.a.c_id");
                    appMeasurement.setUserPropertyInternal("fcm", "_ln", string2);
                    Bundle bundle = new Bundle();
                    bundle.putString("source", "Firebase");
                    bundle.putString("medium", "notification");
                    bundle.putString("campaign", string2);
                    appMeasurement.logEventInternal("fcm", "_cmp", bundle);
                } else {
                    Log.w((String)"FirebaseMessaging", (String)"Unable to set user property for conversion tracking:  analytics library is missing");
                }
            } else if (Log.isLoggable((String)"FirebaseMessaging", (int)3)) {
                Log.d((String)"FirebaseMessaging", (String)"Received event with track-conversion=false. Do not set user property");
            }
        }
        zzd.zzb(context, "_no", intent);
    }

    public static void zzh(Context context, Intent intent) {
        zzd.zzb(context, "_nd", intent);
    }

    public static void zzi(Context context, Intent intent) {
        zzd.zzb(context, "_nf", intent);
    }
}

