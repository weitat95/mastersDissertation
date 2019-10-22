/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.bluetooth.BluetoothAdapter
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.net.ConnectivityManager
 *  android.net.NetworkInfo
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.telephony.TelephonyManager
 *  android.util.DisplayMetrics
 *  android.view.Display
 *  android.view.WindowManager
 */
package com.mixpanel.android.mpmetrics;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import com.mixpanel.android.util.MPLog;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class SystemInformation {
    private static SystemInformation sInstance;
    private static final Object sInstanceLock;
    private final String mAppName;
    private final Integer mAppVersionCode;
    private final String mAppVersionName;
    private final Context mContext;
    private final DisplayMetrics mDisplayMetrics;
    private final Boolean mHasNFC;
    private final Boolean mHasTelephony;

    static {
        sInstanceLock = new Object();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private SystemInformation(Context object) {
        int n;
        PackageInfo packageInfo;
        this.mContext = object;
        PackageManager packageManager = this.mContext.getPackageManager();
        Object object2 = null;
        Object object3 = null;
        Object object4 = object2;
        try {
            packageInfo = packageManager.getPackageInfo(this.mContext.getPackageName(), 0);
            object4 = object2;
            object4 = object2 = packageInfo.versionName;
            n = packageInfo.versionCode;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            MPLog.w("MixpanelAPI.SysInfo", "System information constructed with a context that apparently doesn't exist.");
            object2 = object3;
        }
        object3 = n;
        object4 = object2;
        object2 = object3;
        object3 = object.getApplicationInfo();
        n = ((ApplicationInfo)object3).labelRes;
        this.mAppVersionName = object4;
        this.mAppVersionCode = object2;
        object = n == 0 ? (((ApplicationInfo)object3).nonLocalizedLabel == null ? "Misc" : ((ApplicationInfo)object3).nonLocalizedLabel.toString()) : object.getString(n);
        this.mAppName = object;
        object = packageManager.getClass();
        object3 = null;
        try {
            object3 = object = ((Class)object).getMethod("hasSystemFeature", String.class);
        }
        catch (NoSuchMethodException noSuchMethodException) {}
        Object var7_12 = null;
        object4 = null;
        object = null;
        packageInfo = null;
        object2 = packageInfo;
        if (object3 != null) {
            object = var7_12;
            object2 = object4;
            try {
                object4 = (Boolean)((Method)object3).invoke((Object)packageManager, "android.hardware.nfc");
                object = object4;
                object2 = object4;
                object3 = (Boolean)((Method)object3).invoke((Object)packageManager, "android.hardware.telephony");
                object2 = object3;
                object = object4;
            }
            catch (InvocationTargetException invocationTargetException) {
                MPLog.w("MixpanelAPI.SysInfo", "System version appeared to support PackageManager.hasSystemFeature, but we were unable to call it.");
                object2 = packageInfo;
            }
            catch (IllegalAccessException illegalAccessException) {
                MPLog.w("MixpanelAPI.SysInfo", "System version appeared to support PackageManager.hasSystemFeature, but we were unable to call it.");
                object = object2;
                object2 = packageInfo;
            }
        }
        this.mHasNFC = object;
        this.mHasTelephony = object2;
        this.mDisplayMetrics = new DisplayMetrics();
        ((WindowManager)this.mContext.getSystemService("window")).getDefaultDisplay().getMetrics(this.mDisplayMetrics);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static SystemInformation getInstance(Context context) {
        Object object = sInstanceLock;
        synchronized (object) {
            if (sInstance == null) {
                sInstance = new SystemInformation(context.getApplicationContext());
            }
            return sInstance;
        }
    }

    public String getAppName() {
        return this.mAppName;
    }

    public Integer getAppVersionCode() {
        return this.mAppVersionCode;
    }

    public String getAppVersionName() {
        return this.mAppVersionName;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public String getBluetoothVersion() {
        String string2 = "none";
        if (Build.VERSION.SDK_INT >= 18 && this.mContext.getPackageManager().hasSystemFeature("android.hardware.bluetooth_le")) {
            return "ble";
        }
        if (!this.mContext.getPackageManager().hasSystemFeature("android.hardware.bluetooth")) return string2;
        return "classic";
    }

    public String getCurrentNetworkOperator() {
        String string2 = null;
        TelephonyManager telephonyManager = (TelephonyManager)this.mContext.getSystemService("phone");
        if (telephonyManager != null) {
            string2 = telephonyManager.getNetworkOperatorName();
        }
        return string2;
    }

    public DisplayMetrics getDisplayMetrics() {
        return this.mDisplayMetrics;
    }

    public boolean hasNFC() {
        return this.mHasNFC;
    }

    public boolean hasTelephony() {
        return this.mHasTelephony;
    }

    @SuppressLint(value={"MissingPermission"})
    public Boolean isBluetoothEnabled() {
        Boolean bl;
        block4: {
            boolean bl2;
            Boolean bl3;
            bl = bl3 = null;
            if (this.mContext.getPackageManager().checkPermission("android.permission.BLUETOOTH", this.mContext.getPackageName()) != 0) break block4;
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            bl = bl3;
            if (bluetoothAdapter == null) break block4;
            try {
                bl2 = bluetoothAdapter.isEnabled();
            }
            catch (NoClassDefFoundError noClassDefFoundError) {
                return null;
            }
            catch (SecurityException securityException) {
                return null;
            }
            bl = bl2;
        }
        return bl;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    @SuppressLint(value={"MissingPermission"})
    public Boolean isWifiConnected() {
        void var2_5;
        boolean bl = true;
        Object var2_2 = null;
        if (this.mContext.checkCallingOrSelfPermission("android.permission.ACCESS_NETWORK_STATE") != 0) return var2_5;
        NetworkInfo networkInfo = ((ConnectivityManager)this.mContext.getSystemService("connectivity")).getActiveNetworkInfo();
        if (networkInfo == null || networkInfo.getType() != 1 || !networkInfo.isConnected()) {
            bl = false;
        }
        Boolean bl2 = bl;
        return var2_5;
    }
}

