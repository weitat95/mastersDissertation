/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.pm.ActivityInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.pm.ServiceInfo
 *  android.text.TextUtils
 */
package com.google.android.gms.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.text.TextUtils;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zzm;
import com.google.android.gms.internal.zzape;
import com.google.android.gms.internal.zzapz;
import com.google.android.gms.internal.zzarv;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public final class zzasl {
    private static final char[] zzdzj = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static double zza(String string2, double d) {
        if (string2 == null) {
            return 100.0;
        }
        try {
            d = Double.parseDouble(string2);
            return d;
        }
        catch (NumberFormatException numberFormatException) {
            return 100.0;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static zzape zza(zzarv object, String object2) {
        zzbq.checkNotNull(object);
        if (TextUtils.isEmpty(object2)) {
            return null;
        }
        new HashMap();
        try {
            object2 = String.valueOf(object2);
            object2 = ((String)object2).length() != 0 ? "?".concat((String)object2) : new String("?");
            object2 = zzm.zza(new URI((String)object2), "UTF-8");
        }
        catch (URISyntaxException uRISyntaxException) {
            ((zzapz)object).zzd("No valid campaign data found", uRISyntaxException);
            return null;
        }
        object = new zzape();
        ((zzape)object).zzdj(object2.get("utm_content"));
        ((zzape)object).zzdh(object2.get("utm_medium"));
        ((zzape)object).setName(object2.get("utm_campaign"));
        ((zzape)object).zzdg(object2.get("utm_source"));
        ((zzape)object).zzdi(object2.get("utm_term"));
        ((zzape)object).zzdk(object2.get("utm_id"));
        ((zzape)object).zzdl(object2.get("anid"));
        ((zzape)object).zzdm(object2.get("gclid"));
        ((zzape)object).zzdn(object2.get("dclid"));
        ((zzape)object).zzdo(object2.get("aclid"));
        return object;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static String zza(Locale locale) {
        String string2;
        if (locale == null || TextUtils.isEmpty((CharSequence)(string2 = locale.getLanguage()))) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2.toLowerCase());
        if (!TextUtils.isEmpty((CharSequence)locale.getCountry())) {
            stringBuilder.append("-").append(locale.getCountry().toLowerCase());
        }
        return stringBuilder.toString();
    }

    public static void zza(Map<String, String> map, String string2, Map<String, String> map2) {
        zzasl.zzb(map, string2, map2.get(string2));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean zza(double d, String string2) {
        int n;
        boolean bl = true;
        if (d <= 0.0) return false;
        if (d >= 100.0) {
            return false;
        }
        if (!TextUtils.isEmpty((CharSequence)string2)) {
            int n2 = string2.length() - 1;
            int n3 = 0;
            do {
                n = n3;
                if (n2 >= 0) {
                    n = string2.charAt(n2);
                    n = (n3 << 6 & 0xFFFFFFF) + n + (n << 14);
                    int n4 = 0xFE00000 & n;
                    n3 = n;
                    if (n4 != 0) {
                        n3 = n ^ n4 >> 21;
                    }
                    --n2;
                    continue;
                }
                break;
            } while (true);
        } else {
            n = 1;
        }
        if ((double)(n % 10000) >= 100.0 * d) return bl;
        return false;
    }

    public static String zzak(boolean bl) {
        if (bl) {
            return "1";
        }
        return "0";
    }

    public static void zzb(Map<String, String> map, String string2, String string3) {
        if (string3 != null && !map.containsKey(string2)) {
            map.put(string2, string3);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void zzb(Map<String, String> map, String string2, boolean bl) {
        if (!map.containsKey(string2)) {
            String string3 = bl ? "1" : "0";
            map.put(string2, string3);
        }
    }

    public static boolean zzb(Context context, String string2, boolean bl) {
        block5: {
            block6: {
                context = context.getPackageManager().getReceiverInfo(new ComponentName(context, string2), 2);
                if (context == null) break block5;
                if (!context.enabled) break block5;
                if (!bl) break block6;
                try {
                    bl = context.exported;
                    if (!bl) break block5;
                }
                catch (PackageManager.NameNotFoundException nameNotFoundException) {
                    // empty catch block
                }
            }
            return true;
        }
        return false;
    }

    public static void zzc(Map<String, String> map, String string2, String string3) {
        if (string3 != null && TextUtils.isEmpty((CharSequence)map.get(string2))) {
            map.put(string2, string3);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public static boolean zzd(String string2, boolean bl) {
        return string2 == null || string2.equalsIgnoreCase("true") || string2.equalsIgnoreCase("yes") || string2.equalsIgnoreCase("1") || !string2.equalsIgnoreCase("false") && !string2.equalsIgnoreCase("no") && !string2.equalsIgnoreCase("0");
    }

    public static long zzei(String string2) {
        if (string2 == null) {
            return 0L;
        }
        try {
            long l = Long.parseLong(string2);
            return l;
        }
        catch (NumberFormatException numberFormatException) {
            return 0L;
        }
    }

    public static MessageDigest zzek(String string2) {
        for (int i = 0; i < 2; ++i) {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance(string2);
                if (messageDigest == null) continue;
                return messageDigest;
            }
            catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                // empty catch block
            }
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static boolean zzel(String string2) {
        return TextUtils.isEmpty((CharSequence)string2) || !string2.startsWith("http:");
    }

    public static boolean zzt(Context context, String string2) {
        block4: {
            context = context.getPackageManager().getServiceInfo(new ComponentName(context, string2), 4);
            if (context == null) break block4;
            try {
                boolean bl = context.enabled;
                if (bl) {
                    return true;
                }
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {
                // empty catch block
            }
        }
        return false;
    }
}

