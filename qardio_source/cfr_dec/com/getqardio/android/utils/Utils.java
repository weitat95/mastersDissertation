/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.graphics.Point
 *  android.net.ConnectivityManager
 *  android.net.NetworkInfo
 *  android.net.Uri
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Environment
 *  android.view.Display
 *  android.view.WindowManager
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.getqardio.android.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.view.Display;
import android.view.WindowManager;
import com.getqardio.android.utils.ui.Convert;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;
import timber.log.Timber;

public class Utils {
    private static final Locale DEFAULT_LOCALE = Locale.UK;
    private static final Set<String> SUPPORTED_LOCALES = new HashSet<String>(Arrays.asList("en", "de", "es", "fi", "fr", "it", "nl", "pt", "ru", "nb", "sv", "da", "zh", "ar", "bg", "cs"));

    public static String arrayToString(Object[] arrobject) {
        if (arrobject == null) {
            return "null";
        }
        if (arrobject.length == 0) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder(arrobject.length * 7);
        stringBuilder.append(arrobject[0]);
        for (int i = 1; i < arrobject.length; ++i) {
            stringBuilder.append(",");
            stringBuilder.append(arrobject[i]);
        }
        return stringBuilder.toString();
    }

    public static String convertQbSerialNumberToBluetoothAddress(int n) {
        StringBuilder stringBuilder = new StringBuilder(Integer.toHexString(n));
        for (n = stringBuilder.length(); n < 6; ++n) {
            stringBuilder.insert(0, '0');
        }
        for (n = 2; n > 0; --n) {
            stringBuilder.insert(n * 2, ':');
        }
        return ("5c:d6:1f:" + stringBuilder.toString()).toUpperCase();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static boolean copyFile(File object, File file) {
        object = new FileInputStream((File)object);
        fileOutputStream = new FileOutputStream(file);
        try {
            arrby = new byte[1024];
            while ((n = object.read(arrby)) > 0) {
                fileOutputStream.write(arrby, 0, n);
            }
            ** GOTO lbl21
        }
        catch (Throwable var4_5) {
            try {
                object.close();
                fileOutputStream.close();
                throw var4_5;
            }
            catch (IOException iOException) {
                if (file.exists() == false) return false;
                file.delete();
                return false;
            }
lbl21:
            // 2 sources
            object.close();
            fileOutputStream.close();
            return true;
        }
    }

    public static JSONObject createQardioBaseWifiConfig(String string2, String string3, boolean bl) {
        JSONObject jSONObject;
        block3: {
            jSONObject = new JSONObject();
            try {
                jSONObject.putOpt("ssid", (Object)string2);
                string2 = string3;
                if (string3 != null) break block3;
                string2 = "";
            }
            catch (JSONException jSONException) {
                Timber.e(jSONException, "Cannot create wifi config object", new Object[0]);
                return jSONObject;
            }
        }
        jSONObject.putOpt("passkey", (Object)string2);
        jSONObject.putOpt("wifi", (Object)String.valueOf(Convert.booleanToInteger(bl)));
        return jSONObject;
    }

    public static String encodeString(String string2) {
        try {
            string2 = URLEncoder.encode(string2, "UTF-8");
            return string2;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            Timber.e(unsupportedEncodingException);
            return "";
        }
    }

    public static boolean equals(Object object, Object object2) {
        if (object == null && object2 == null) {
            return true;
        }
        if (object == null && object2 != null) {
            return false;
        }
        return object.equals(object2);
    }

    public static String formatFloat(float f) {
        return String.format(Utils.getLocale(), "%.1f", Float.valueOf(f));
    }

    public static String formatInteger(int n) {
        return String.format(Utils.getLocale(), "%d", n);
    }

    public static String getAndroidVersion() {
        return Build.VERSION.RELEASE;
    }

    public static int getBatteryLevel(Context context) {
        context = context.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        int n = 0;
        if (context != null) {
            n = context.getIntExtra("level", 0);
        }
        return n;
    }

    public static long getDateByMonthNumber(int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(1, n / 12);
        calendar.set(2, n % 12);
        return calendar.getTimeInMillis();
    }

    public static String getDeviceModel() {
        return Build.MODEL;
    }

    public static File getExternalCacheDir(Context context) {
        return context.getExternalCacheDir();
    }

    public static String getFileExtension(File object) {
        String string2 = "";
        String string3 = ((File)object).getName();
        int n = string3.lastIndexOf(46);
        object = string2;
        if (n > 0) {
            object = string2;
            if (n < string3.length() - 1) {
                object = string3.substring(n + 1).toLowerCase();
            }
        }
        return object;
    }

    public static Intent getGooglePlayIntent(String string2) {
        string2 = new Intent("android.intent.action.VIEW", Uri.parse((String)("market://details?id=" + string2)));
        string2.addFlags(1208483840);
        return string2;
    }

    public static Locale getLocale() {
        Locale locale = Locale.getDefault();
        if (SUPPORTED_LOCALES.contains(locale.getLanguage())) {
            return locale;
        }
        return DEFAULT_LOCALE;
    }

    public static int getMonthNumber(long l) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(l);
        return calendar.get(1) * 12 + calendar.get(2);
    }

    private static PackageInfo getPackageInfo(Context context) {
        try {
            context = context.getPackageManager().getPackageInfo(context.getPackageName(), 128);
            return context;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            Timber.e(nameNotFoundException, "Package not found", new Object[0]);
            return null;
        }
    }

    public static File getPregnancyGalleryCacheDir() {
        return new File(Environment.getExternalStorageDirectory() + File.separator + "qardio/pregnancy");
    }

    public static Point getScreenSize(Context context) {
        context = ((WindowManager)context.getSystemService("window")).getDefaultDisplay();
        Point point = new Point();
        context.getSize(point);
        return point;
    }

    public static int getScreenWidth(Context context) {
        return Utils.getScreenSize((Context)context).x;
    }

    public static int getVersionCode(Context context) {
        context = Utils.getPackageInfo(context);
        int n = 0;
        if (context != null) {
            n = context.versionCode;
        }
        return n;
    }

    public static String getVersionName(Context object) {
        try {
            object = object.getPackageManager().getPackageInfo((String)object.getPackageName(), (int)0).versionName;
            return object;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            Timber.e(nameNotFoundException, "Package not found", new Object[0]);
            return "Unknown";
        }
    }

    public static boolean isNetworkAvaliable(Context context) {
        return (context = ((ConnectivityManager)context.getSystemService("connectivity")).getActiveNetworkInfo()) != null && context.isConnected();
    }

    public static boolean isNetworkWiFi(Context context) {
        return (context = ((ConnectivityManager)context.getSystemService("connectivity")).getActiveNetworkInfo()) != null && context.getType() == 1;
    }

    public static byte[] mergeArrays(byte[] arrby, byte[] arrby2) {
        byte[] arrby3 = new byte[arrby.length + arrby2.length];
        System.arraycopy(arrby, 0, arrby3, 0, arrby.length);
        System.arraycopy(arrby2, 0, arrby3, arrby.length, arrby2.length);
        return arrby3;
    }

    private static Float parseFloatWithComma(String string2) {
        float f;
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Utils.getLocale());
        try {
            f = numberFormat.parse(string2).floatValue();
        }
        catch (ParseException parseException) {
            return Utils.parseFloatWithDot(string2);
        }
        return Float.valueOf(f);
    }

    private static Float parseFloatWithDot(String object) {
        try {
            object = Float.valueOf((String)object);
            return object;
        }
        catch (NumberFormatException numberFormatException) {
            Timber.i("Cannot parse number", new Object[0]);
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public static Float parseNumber(String object) {
        object = DecimalFormatSymbols.getInstance(Utils.getLocale()).getDecimalSeparator() == '.' ? Utils.parseFloatWithDot((String)object) : Utils.parseFloatWithComma((String)object);
        Object object2 = object;
        if (object != null) return object2;
        return Float.valueOf(0.0f);
    }

    public static float round(Float f, int n) {
        if (f.equals(Float.valueOf(Float.NaN))) {
            return 0.0f;
        }
        return new BigDecimal(Float.toString(f.floatValue())).setScale(n, 4).floatValue();
    }
}

