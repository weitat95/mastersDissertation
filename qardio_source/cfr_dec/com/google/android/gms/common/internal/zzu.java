/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.text.TextUtils
 *  android.util.Log
 */
package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v4.util.SimpleArrayMap;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.R;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.util.zzi;
import com.google.android.gms.internal.zzbhf;
import java.util.Locale;

public final class zzu {
    private static final SimpleArrayMap<String, String> zzfzl = new SimpleArrayMap();

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static String zzcm(Context var0) {
        var1_4 = var0 /* !! */ .getPackageName();
        try {
            var0_1 = var2_5 = zzbhf.zzdb(var0 /* !! */ ).zzgt(var1_4).toString();
            return var0_2;
        }
        catch (NullPointerException var2_6) {}
        ** GOTO lbl-1000
        catch (PackageManager.NameNotFoundException var2_8) {}
lbl-1000:
        // 2 sources
        {
            var2_7 = var0 /* !! */ .getApplicationInfo().name;
            var0_3 = var1_4;
            if (TextUtils.isEmpty((CharSequence)var2_7) != false) return var0_2;
            return var2_7;
        }
    }

    public static String zzcn(Context context) {
        return context.getResources().getString(R.string.common_google_play_services_notification_channel_name);
    }

    public static String zzg(Context context, int n) {
        Resources resources = context.getResources();
        switch (n) {
            default: {
                Log.e((String)"GoogleApiAvailability", (String)new StringBuilder(33).append("Unexpected error code ").append(n).toString());
            }
            case 4: 
            case 6: 
            case 18: {
                return null;
            }
            case 1: {
                return resources.getString(R.string.common_google_play_services_install_title);
            }
            case 3: {
                return resources.getString(R.string.common_google_play_services_enable_title);
            }
            case 2: {
                return resources.getString(R.string.common_google_play_services_update_title);
            }
            case 9: {
                Log.e((String)"GoogleApiAvailability", (String)"Google Play services is invalid. Cannot recover.");
                return null;
            }
            case 7: {
                Log.e((String)"GoogleApiAvailability", (String)"Network error occurred. Please retry request later.");
                return zzu.zzw(context, "common_google_play_services_network_error_title");
            }
            case 8: {
                Log.e((String)"GoogleApiAvailability", (String)"Internal error occurred. Please see logs for detailed information");
                return null;
            }
            case 10: {
                Log.e((String)"GoogleApiAvailability", (String)"Developer error occurred. Please see logs for detailed information");
                return null;
            }
            case 5: {
                Log.e((String)"GoogleApiAvailability", (String)"An invalid account was specified when connecting. Please provide a valid account.");
                return zzu.zzw(context, "common_google_play_services_invalid_account_title");
            }
            case 11: {
                Log.e((String)"GoogleApiAvailability", (String)"The application is not licensed to the user.");
                return null;
            }
            case 16: {
                Log.e((String)"GoogleApiAvailability", (String)"One of the API components you attempted to connect to is not available.");
                return null;
            }
            case 17: {
                Log.e((String)"GoogleApiAvailability", (String)"The specified account could not be signed in.");
                return zzu.zzw(context, "common_google_play_services_sign_in_failed_title");
            }
            case 20: 
        }
        Log.e((String)"GoogleApiAvailability", (String)"The current user profile is restricted and could not use authenticated features.");
        return zzu.zzw(context, "common_google_play_services_restricted_profile_title");
    }

    /*
     * Enabled aggressive block sorting
     */
    public static String zzh(Context context, int n) {
        String string2 = n == 6 ? zzu.zzw(context, "common_google_play_services_resolution_required_title") : zzu.zzg(context, n);
        String string3 = string2;
        if (string2 != null) return string3;
        return context.getResources().getString(R.string.common_google_play_services_notification_ticker);
    }

    public static String zzi(Context context, int n) {
        Resources resources = context.getResources();
        String string2 = zzu.zzcm(context);
        switch (n) {
            default: {
                return resources.getString(R.string.common_google_play_services_unknown_issue, new Object[]{string2});
            }
            case 1: {
                return resources.getString(R.string.common_google_play_services_install_text, new Object[]{string2});
            }
            case 3: {
                return resources.getString(R.string.common_google_play_services_enable_text, new Object[]{string2});
            }
            case 18: {
                return resources.getString(R.string.common_google_play_services_updating_text, new Object[]{string2});
            }
            case 2: {
                if (zzi.zzct(context)) {
                    return resources.getString(R.string.common_google_play_services_wear_update_text);
                }
                return resources.getString(R.string.common_google_play_services_update_text, new Object[]{string2});
            }
            case 9: {
                return resources.getString(R.string.common_google_play_services_unsupported_text, new Object[]{string2});
            }
            case 7: {
                return zzu.zzl(context, "common_google_play_services_network_error_text", string2);
            }
            case 5: {
                return zzu.zzl(context, "common_google_play_services_invalid_account_text", string2);
            }
            case 16: {
                return zzu.zzl(context, "common_google_play_services_api_unavailable_text", string2);
            }
            case 17: {
                return zzu.zzl(context, "common_google_play_services_sign_in_failed_text", string2);
            }
            case 20: 
        }
        return zzu.zzl(context, "common_google_play_services_restricted_profile_text", string2);
    }

    public static String zzj(Context context, int n) {
        if (n == 6) {
            return zzu.zzl(context, "common_google_play_services_resolution_required_text", zzu.zzcm(context));
        }
        return zzu.zzi(context, n);
    }

    public static String zzk(Context context, int n) {
        context = context.getResources();
        switch (n) {
            default: {
                return context.getString(17039370);
            }
            case 1: {
                return context.getString(R.string.common_google_play_services_install_button);
            }
            case 3: {
                return context.getString(R.string.common_google_play_services_enable_button);
            }
            case 2: 
        }
        return context.getString(R.string.common_google_play_services_update_button);
    }

    private static String zzl(Context object, String string2, String string3) {
        Resources resources = object.getResources();
        string2 = zzu.zzw(object, string2);
        object = string2;
        if (string2 == null) {
            object = resources.getString(R.string.common_google_play_services_unknown_issue);
        }
        return String.format(resources.getConfiguration().locale, (String)object, string3);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static String zzw(Context object, String string2) {
        SimpleArrayMap<String, String> simpleArrayMap = zzfzl;
        synchronized (simpleArrayMap) {
            String string3 = zzfzl.get(string2);
            if (string3 != null) {
                return string3;
            }
            if ((object = GooglePlayServicesUtil.getRemoteResource((Context)object)) == null) {
                return null;
            }
            int n = object.getIdentifier(string2, "string", "com.google.android.gms");
            if (n == 0) {
                object = String.valueOf(string2);
                object = ((String)object).length() != 0 ? "Missing resource: ".concat((String)object) : new String("Missing resource: ");
                Log.w((String)"GoogleApiAvailability", (String)object);
                return null;
            }
            if (!TextUtils.isEmpty((CharSequence)(object = object.getString(n)))) {
                zzfzl.put(string2, (String)object);
                return object;
            }
            object = String.valueOf(string2);
            object = ((String)object).length() != 0 ? "Got empty resource: ".concat((String)object) : new String("Got empty resource: ");
            Log.w((String)"GoogleApiAvailability", (String)object);
            return null;
        }
    }
}

