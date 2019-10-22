/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.ActivityManager
 *  android.app.ActivityManager$MemoryInfo
 *  android.app.ActivityManager$RunningAppProcessInfo
 *  android.content.BroadcastReceiver
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.SharedPreferences
 *  android.content.pm.ApplicationInfo
 *  android.content.res.Resources
 *  android.hardware.Sensor
 *  android.hardware.SensorManager
 *  android.net.ConnectivityManager
 *  android.net.NetworkInfo
 *  android.os.Build
 *  android.os.Debug
 *  android.os.StatFs
 *  android.provider.Settings
 *  android.provider.Settings$Secure
 *  android.text.TextUtils
 */
package io.fabric.sdk.android.services.common;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Debug;
import android.os.StatFs;
import android.provider.Settings;
import android.text.TextUtils;
import io.fabric.sdk.android.Fabric;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public class CommonUtils {
    public static final Comparator<File> FILE_MODIFIED_COMPARATOR;
    private static final char[] HEX_VALUES;
    private static Boolean clsTrace;
    private static long totalRamInBytes;

    static {
        clsTrace = null;
        HEX_VALUES = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        totalRamInBytes = -1L;
        FILE_MODIFIED_COMPARATOR = new Comparator<File>(){

            @Override
            public int compare(File file, File file2) {
                return (int)(file.lastModified() - file2.lastModified());
            }
        };
    }

    public static long calculateFreeRamInBytes(Context context) {
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        ((ActivityManager)context.getSystemService("activity")).getMemoryInfo(memoryInfo);
        return memoryInfo.availMem;
    }

    public static long calculateUsedDiskSpaceInBytes(String string2) {
        string2 = new StatFs(string2);
        long l = string2.getBlockSize();
        return l * (long)string2.getBlockCount() - l * (long)string2.getAvailableBlocks();
    }

    public static boolean canTryConnection(Context context) {
        return !CommonUtils.checkPermission(context, "android.permission.ACCESS_NETWORK_STATE") || (context = ((ConnectivityManager)context.getSystemService("connectivity")).getActiveNetworkInfo()) != null && context.isConnectedOrConnecting();
    }

    public static boolean checkPermission(Context context, String string2) {
        return context.checkCallingOrSelfPermission(string2) == 0;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static void closeOrLog(Closeable closeable, String string2) {
        if (closeable == null) return;
        try {
            closeable.close();
            return;
        }
        catch (IOException iOException) {
            Fabric.getLogger().e("Fabric", string2, iOException);
            return;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static void closeQuietly(Closeable closeable) {
        if (closeable == null) return;
        try {
            closeable.close();
            return;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (Exception exception) {
            return;
        }
    }

    static long convertMemInfoToBytes(String string2, String string3, int n) {
        return Long.parseLong(string2.split(string3)[0].trim()) * (long)n;
    }

    public static void copyStream(InputStream inputStream, OutputStream outputStream, byte[] arrby) throws IOException {
        int n;
        while ((n = inputStream.read(arrby)) != -1) {
            outputStream.write(arrby, 0, n);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public static String createInstanceIdFrom(String ... object) {
        block5: {
            block4: {
                if (object == null || ((Object)object).length == 0) break block4;
                Object object2 = new ArrayList<String>();
                for (Object object3 : object) {
                    if (object3 == null) continue;
                    object2.add(((String)object3).replace("-", "").toLowerCase(Locale.US));
                }
                Collections.sort(object2);
                object = new StringBuilder();
                object2 = object2.iterator();
                while (object2.hasNext()) {
                    ((StringBuilder)object).append((String)object2.next());
                }
                if (((String)(object = ((StringBuilder)object).toString())).length() > 0) break block5;
            }
            return null;
        }
        return CommonUtils.sha1((String)object);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static String extractFieldFromSystemFile(File var0, String var1_4) {
        block8: {
            var2_5 = null;
            var4_19 = null;
            if (var0.exists() == false) return var2_12;
            var2_6 = null;
            var5_20 = null;
            var3_21 = new BufferedReader(new FileReader(var0), 1024);
            do {
                var5_20 = var3_21.readLine();
                var2_8 = var4_19;
                if (var5_20 == null) break block8;
            } while ((var2_9 = Pattern.compile("\\s*:\\s*").split(var5_20, 2)).length <= 1 || !var2_9[0].equals(var1_4));
            var2_10 = var2_9[1];
        }
        CommonUtils.closeOrLog((Closeable)var3_21, "Failed to close system file reader.");
        return var2_12;
        catch (Exception var3_22) {
            block9: {
                var1_4 = var5_20;
                break block9;
                catch (Throwable var0_3) {
                    var2_17 = var3_21;
                    ** GOTO lbl-1000
                }
                catch (Exception var2_18) {
                    var1_4 = var3_21;
                    var3_21 = var2_18;
                }
            }
            var2_14 = var1_4;
            try {
                Fabric.getLogger().e("Fabric", "Error parsing " + var0, (Throwable)var3_21);
            }
            catch (Throwable var0_1) lbl-1000:
            // 2 sources
            {
                CommonUtils.closeOrLog((Closeable)var2_16, "Failed to close system file reader.");
                throw var0_2;
            }
            CommonUtils.closeOrLog((Closeable)var1_4, "Failed to close system file reader.");
            return null;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static void flushOrLog(Flushable flushable, String string2) {
        if (flushable == null) return;
        try {
            flushable.flush();
            return;
        }
        catch (IOException iOException) {
            Fabric.getLogger().e("Fabric", string2, iOException);
            return;
        }
    }

    public static String getAppIconHashOrNull(Context object) {
        Object object2;
        block6: {
            Object object3 = null;
            object2 = null;
            object2 = object = object.getResources().openRawResource(CommonUtils.getAppIconResourceId(object));
            object3 = object;
            String string2 = CommonUtils.sha1((InputStream)object);
            object2 = object;
            object3 = object;
            try {
                boolean bl = CommonUtils.isNullOrEmpty(string2);
                object2 = string2;
                if (!bl) break block6;
                object2 = null;
            }
            catch (Exception exception) {
                object3 = object2;
                try {
                    Fabric.getLogger().e("Fabric", "Could not calculate hash for app icon.", exception);
                }
                catch (Throwable throwable) {
                    CommonUtils.closeOrLog(object3, "Failed to close icon input stream.");
                    throw throwable;
                }
                CommonUtils.closeOrLog((Closeable)object2, "Failed to close icon input stream.");
                return null;
            }
        }
        CommonUtils.closeOrLog((Closeable)object, "Failed to close icon input stream.");
        return object2;
    }

    public static int getAppIconResourceId(Context context) {
        return context.getApplicationContext().getApplicationInfo().icon;
    }

    public static ActivityManager.RunningAppProcessInfo getAppProcessInfo(String string2, Context context) {
        Object object = ((ActivityManager)context.getSystemService("activity")).getRunningAppProcesses();
        Object var2_3 = null;
        context = var2_3;
        if (object != null) {
            object = object.iterator();
            do {
                context = var2_3;
                if (!object.hasNext()) break;
                context = (ActivityManager.RunningAppProcessInfo)object.next();
            } while (!context.processName.equals(string2));
        }
        return context;
    }

    public static Float getBatteryLevel(Context context) {
        if ((context = context.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"))) == null) {
            return null;
        }
        int n = context.getIntExtra("level", -1);
        int n2 = context.getIntExtra("scale", -1);
        return Float.valueOf((float)n / (float)n2);
    }

    public static int getBatteryVelocity(Context object, boolean bl) {
        object = CommonUtils.getBatteryLevel((Context)object);
        if (!bl || object == null) {
            return 1;
        }
        if ((double)((Float)object).floatValue() >= 99.0) {
            return 3;
        }
        if ((double)((Float)object).floatValue() < 99.0) {
            return 2;
        }
        return 0;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean getBooleanResourceValue(Context context, String string2, boolean bl) {
        boolean bl2 = bl;
        if (context == null) return bl2;
        Resources resources = context.getResources();
        bl2 = bl;
        if (resources == null) return bl2;
        int n = CommonUtils.getResourcesIdentifier(context, string2, "bool");
        if (n > 0) {
            return resources.getBoolean(n);
        }
        n = CommonUtils.getResourcesIdentifier(context, string2, "string");
        bl2 = bl;
        if (n <= 0) return bl2;
        return Boolean.parseBoolean(context.getString(n));
    }

    public static int getCpuArchitectureInt() {
        return Architecture.getValue().ordinal();
    }

    public static int getDeviceState(Context context) {
        int n = 0;
        if (CommonUtils.isEmulator(context)) {
            n = false | true;
        }
        int n2 = n;
        if (CommonUtils.isRooted(context)) {
            n2 = n | 2;
        }
        n = n2;
        if (CommonUtils.isDebuggerAttached()) {
            n = n2 | 4;
        }
        return n;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static boolean getProximitySensorEnabled(Context context) {
        return !CommonUtils.isEmulator(context) && ((SensorManager)context.getSystemService("sensor")).getDefaultSensor(8) != null;
    }

    public static String getResourcePackageName(Context context) {
        int n = context.getApplicationContext().getApplicationInfo().icon;
        if (n > 0) {
            return context.getResources().getResourcePackageName(n);
        }
        return context.getPackageName();
    }

    public static int getResourcesIdentifier(Context context, String string2, String string3) {
        return context.getResources().getIdentifier(string2, string3, CommonUtils.getResourcePackageName(context));
    }

    public static SharedPreferences getSharedPrefs(Context context) {
        return context.getSharedPreferences("com.crashlytics.prefs", 0);
    }

    public static String getStringsFileValue(Context context, String string2) {
        int n = CommonUtils.getResourcesIdentifier(context, string2, "string");
        if (n > 0) {
            return context.getString(n);
        }
        return "";
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static long getTotalRamInBytes() {
        synchronized (CommonUtils.class) {
            if (totalRamInBytes != -1L) return totalRamInBytes;
            long l = 0L;
            String string2 = CommonUtils.extractFieldFromSystemFile(new File("/proc/meminfo"), "MemTotal");
            long l2 = l;
            if (!TextUtils.isEmpty((CharSequence)string2)) {
                string2 = string2.toUpperCase(Locale.US);
                try {
                    if (string2.endsWith("KB")) {
                        l2 = CommonUtils.convertMemInfoToBytes(string2, "KB", 1024);
                    } else if (string2.endsWith("MB")) {
                        l2 = CommonUtils.convertMemInfoToBytes(string2, "MB", 1048576);
                    } else if (string2.endsWith("GB")) {
                        l2 = CommonUtils.convertMemInfoToBytes(string2, "GB", 1073741824);
                    } else {
                        Fabric.getLogger().d("Fabric", "Unexpected meminfo format while computing RAM: " + string2);
                        l2 = l;
                    }
                }
                catch (NumberFormatException numberFormatException) {
                    Fabric.getLogger().e("Fabric", "Unexpected meminfo format while computing RAM: " + string2, numberFormatException);
                    l2 = l;
                }
            }
            totalRamInBytes = l2;
            return totalRamInBytes;
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static String hash(InputStream object, String object2) {
        try {
            int n;
            object2 = MessageDigest.getInstance("SHA-1");
            byte[] arrby = new byte[1024];
            while ((n = ((InputStream)object).read(arrby)) != -1) {
                ((MessageDigest)object2).update(arrby, 0, n);
            }
            return CommonUtils.hexify(((MessageDigest)object2).digest());
        }
        catch (Exception exception) {
            Fabric.getLogger().e("Fabric", "Could not calculate hash for app icon.", exception);
            return "";
        }
    }

    private static String hash(String string2, String string3) {
        return CommonUtils.hash(string2.getBytes(), string3);
    }

    private static String hash(byte[] arrby, String string2) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance(string2);
            messageDigest.update(arrby);
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            Fabric.getLogger().e("Fabric", "Could not create hashing algorithm: " + string2 + ", returning empty string.", noSuchAlgorithmException);
            return "";
        }
        return CommonUtils.hexify(messageDigest.digest());
    }

    public static String hexify(byte[] arrby) {
        char[] arrc = new char[arrby.length * 2];
        for (int i = 0; i < arrby.length; ++i) {
            int n = arrby[i] & 0xFF;
            arrc[i * 2] = HEX_VALUES[n >>> 4];
            arrc[i * 2 + 1] = HEX_VALUES[n & 0xF];
        }
        return new String(arrc);
    }

    public static boolean isAppDebuggable(Context context) {
        return (context.getApplicationInfo().flags & 2) != 0;
    }

    public static boolean isClsTrace(Context context) {
        if (clsTrace == null) {
            clsTrace = CommonUtils.getBooleanResourceValue(context, "com.crashlytics.Trace", false);
        }
        return clsTrace;
    }

    public static boolean isDebuggerAttached() {
        return Debug.isDebuggerConnected() || Debug.waitingForDebugger();
    }

    public static boolean isEmulator(Context object) {
        object = Settings.Secure.getString((ContentResolver)object.getContentResolver(), (String)"android_id");
        return "sdk".equals(Build.PRODUCT) || "google_sdk".equals(Build.PRODUCT) || object == null;
    }

    public static boolean isNullOrEmpty(String string2) {
        return string2 == null || string2.length() == 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static boolean isRooted(Context object) {
        block3: {
            block2: {
                boolean bl = CommonUtils.isEmulator((Context)object);
                object = Build.TAGS;
                if (!bl && object != null && ((String)object).contains("test-keys") || new File("/system/app/Superuser.apk").exists()) break block2;
                object = new File("/system/xbin/su");
                if (bl || !((File)object).exists()) break block3;
            }
            return true;
        }
        return false;
    }

    public static void logControlled(Context context, int n, String string2, String string3) {
        if (CommonUtils.isClsTrace(context)) {
            Fabric.getLogger().log(n, "Fabric", string3);
        }
    }

    public static void logControlled(Context context, String string2) {
        if (CommonUtils.isClsTrace(context)) {
            Fabric.getLogger().d("Fabric", string2);
        }
    }

    public static void logControlledError(Context context, String string2, Throwable throwable) {
        if (CommonUtils.isClsTrace(context)) {
            Fabric.getLogger().e("Fabric", string2);
        }
    }

    public static String logPriorityToString(int n) {
        switch (n) {
            default: {
                return "?";
            }
            case 7: {
                return "A";
            }
            case 3: {
                return "D";
            }
            case 6: {
                return "E";
            }
            case 4: {
                return "I";
            }
            case 2: {
                return "V";
            }
            case 5: 
        }
        return "W";
    }

    public static String padWithZerosToMaxIntWidth(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("value must be zero or greater");
        }
        return String.format(Locale.US, "%1$10s", n).replace(' ', '0');
    }

    public static String resolveBuildId(Context context) {
        int n;
        String string2 = null;
        int n2 = n = CommonUtils.getResourcesIdentifier(context, "io.fabric.android.build_id", "string");
        if (n == 0) {
            n2 = CommonUtils.getResourcesIdentifier(context, "com.crashlytics.android.build_id", "string");
        }
        if (n2 != 0) {
            string2 = context.getResources().getString(n2);
            Fabric.getLogger().d("Fabric", "Build ID is: " + string2);
        }
        return string2;
    }

    public static String sha1(InputStream inputStream) {
        return CommonUtils.hash(inputStream, "SHA-1");
    }

    public static String sha1(String string2) {
        return CommonUtils.hash(string2, "SHA-1");
    }

    public static String streamToString(InputStream closeable) throws IOException {
        if (((Scanner)(closeable = new Scanner((InputStream)closeable).useDelimiter("\\A"))).hasNext()) {
            return ((Scanner)closeable).next();
        }
        return "";
    }

    static enum Architecture {
        X86_32,
        X86_64,
        ARM_UNKNOWN,
        PPC,
        PPC64,
        ARMV6,
        ARMV7,
        UNKNOWN,
        ARMV7S,
        ARM64;

        private static final Map<String, Architecture> matcher;

        static {
            matcher = new HashMap<String, Architecture>(4);
            matcher.put("armeabi-v7a", ARMV7);
            matcher.put("armeabi", ARMV6);
            matcher.put("arm64-v8a", ARM64);
            matcher.put("x86", X86_32);
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         */
        static Architecture getValue() {
            void var0_2;
            String string2 = Build.CPU_ABI;
            if (TextUtils.isEmpty((CharSequence)string2)) {
                Fabric.getLogger().d("Fabric", "Architecture#getValue()::Build.CPU_ABI returned null or empty");
                Architecture architecture = UNKNOWN;
                return var0_2;
            } else {
                Architecture architecture;
                String string3 = string2.toLowerCase(Locale.US);
                Architecture architecture2 = architecture = matcher.get(string3);
                if (architecture != null) return var0_2;
                return UNKNOWN;
            }
        }
    }

}

