/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.net.ConnectivityManager
 *  android.net.NetworkInfo
 *  android.os.AsyncTask
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.telephony.TelephonyManager
 *  android.util.DisplayMetrics
 *  android.view.Display
 *  android.view.WindowManager
 */
package com.segment.analytics;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import com.segment.analytics.GetAdvertisingIdTask;
import com.segment.analytics.Traits;
import com.segment.analytics.ValueMap;
import com.segment.analytics.integrations.Logger;
import com.segment.analytics.internal.Utils;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.CountDownLatch;

public class AnalyticsContext
extends ValueMap {
    AnalyticsContext(Map<String, Object> map) {
        super(map);
    }

    static AnalyticsContext create(Context context, Traits traits, boolean bl) {
        synchronized (AnalyticsContext.class) {
            AnalyticsContext analyticsContext = new AnalyticsContext(new Utils.NullableConcurrentHashMap<String, Object>());
            analyticsContext.putApp(context);
            analyticsContext.setTraits(traits);
            analyticsContext.putDevice(context, bl);
            analyticsContext.putLibrary();
            analyticsContext.put("locale", (Object)(Locale.getDefault().getLanguage() + "-" + Locale.getDefault().getCountry()));
            analyticsContext.putNetwork(context);
            analyticsContext.putOs();
            analyticsContext.putScreen(context);
            AnalyticsContext.putUndefinedIfNull(analyticsContext, "userAgent", System.getProperty("http.agent"));
            AnalyticsContext.putUndefinedIfNull(analyticsContext, "timezone", TimeZone.getDefault().getID());
            return analyticsContext;
        }
    }

    static void putUndefinedIfNull(Map<String, Object> map, String string2, CharSequence charSequence) {
        if (Utils.isNullOrEmpty(charSequence)) {
            map.put(string2, "undefined");
            return;
        }
        map.put(string2, charSequence);
    }

    void attachAdvertisingId(Context context, CountDownLatch countDownLatch, Logger logger) {
        if (Utils.isOnClassPath("com.google.android.gms.ads.identifier.AdvertisingIdClient")) {
            new GetAdvertisingIdTask(this, countDownLatch, logger).execute((Object[])new Context[]{context});
            return;
        }
        logger.debug("Not collecting advertising ID because com.google.android.gms.ads.identifier.AdvertisingIdClient was not found on the classpath.", new Object[0]);
        countDownLatch.countDown();
    }

    public Device device() {
        return this.getValueMap("device", Device.class);
    }

    void putApp(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            context = packageManager.getPackageInfo(context.getPackageName(), 0);
            Map<String, Object> map = Utils.createMap();
            AnalyticsContext.putUndefinedIfNull(map, "name", context.applicationInfo.loadLabel(packageManager));
            AnalyticsContext.putUndefinedIfNull(map, "version", context.versionName);
            AnalyticsContext.putUndefinedIfNull(map, "namespace", context.packageName);
            map.put("build", context.versionCode);
            this.put("app", map);
            return;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            return;
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    void putDevice(Context object, boolean bl) {
        void var1_3;
        void var2_5;
        Device device = new Device();
        if (var2_5 != false) {
            String string2 = Utils.getDeviceId(object);
        } else {
            String string3 = this.traits().anonymousId();
        }
        device.put("id", (Object)var1_3);
        device.put("manufacturer", (Object)Build.MANUFACTURER);
        device.put("model", (Object)Build.MODEL);
        device.put("name", (Object)Build.DEVICE);
        this.put("device", (Object)device);
    }

    void putLibrary() {
        Map map = Utils.createMap();
        map.put("name", "analytics-android");
        map.put("version", "4.3.1");
        this.put("library", map);
    }

    /*
     * Enabled aggressive block sorting
     */
    void putNetwork(Context context) {
        ConnectivityManager connectivityManager;
        boolean bl = true;
        Map map = Utils.createMap();
        if (Utils.hasPermission(context, "android.permission.ACCESS_NETWORK_STATE") && (connectivityManager = (ConnectivityManager)Utils.getSystemService(context, "connectivity")) != null) {
            NetworkInfo networkInfo = connectivityManager.getNetworkInfo(1);
            boolean bl2 = networkInfo != null && networkInfo.isConnected();
            map.put("wifi", bl2);
            networkInfo = connectivityManager.getNetworkInfo(7);
            bl2 = networkInfo != null && networkInfo.isConnected();
            map.put("bluetooth", bl2);
            connectivityManager = connectivityManager.getNetworkInfo(0);
            bl2 = connectivityManager != null && connectivityManager.isConnected() ? bl : false;
            map.put("cellular", bl2);
        }
        if ((context = (TelephonyManager)Utils.getSystemService(context, "phone")) != null) {
            map.put("carrier", context.getNetworkOperatorName());
        } else {
            map.put("carrier", "unknown");
        }
        this.put("network", map);
    }

    void putOs() {
        Map map = Utils.createMap();
        map.put("name", "Android");
        map.put("version", Build.VERSION.RELEASE);
        this.put("os", map);
    }

    void putScreen(Context context) {
        Map map = Utils.createMap();
        context = ((WindowManager)Utils.getSystemService(context, "window")).getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        context.getMetrics(displayMetrics);
        map.put("density", Float.valueOf(displayMetrics.density));
        map.put("height", displayMetrics.heightPixels);
        map.put("width", displayMetrics.widthPixels);
        this.put("screen", map);
    }

    @Override
    public AnalyticsContext putValue(String string2, Object object) {
        super.putValue(string2, object);
        return this;
    }

    void setTraits(Traits traits) {
        this.put("traits", (Object)traits.unmodifiableCopy());
    }

    public Traits traits() {
        return this.getValueMap("traits", Traits.class);
    }

    public AnalyticsContext unmodifiableCopy() {
        return new AnalyticsContext(Collections.unmodifiableMap(new LinkedHashMap<String, Object>(this)));
    }

    public static class Device
    extends ValueMap {
        Device() {
        }

        void putAdvertisingInfo(String string2, boolean bl) {
            if (bl && !Utils.isNullOrEmpty(string2)) {
                this.put("advertisingId", (Object)string2);
            }
            this.put("adTrackingEnabled", (Object)bl);
        }

        @Override
        public Device putValue(String string2, Object object) {
            super.putValue(string2, object);
            return this;
        }
    }

}

