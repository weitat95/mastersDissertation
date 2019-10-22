/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.app.ActivityManager
 *  android.app.ActivityManager$MemoryInfo
 *  android.content.ContentResolver
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
 *  android.os.Process
 *  android.provider.Settings
 *  android.provider.Settings$Secure
 *  android.text.TextUtils
 *  android.util.DisplayMetrics
 *  android.view.Display
 *  android.view.WindowManager
 */
package io.branch.referral;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Process;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import io.branch.referral.ApkParser;
import io.branch.referral.BranchAsyncTask;
import io.branch.referral.PrefHelper;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

class SystemObserver {
    String GAIDString_ = null;
    int LATVal_ = 0;
    private Context context_;
    private boolean isRealHardwareId;

    SystemObserver(Context context) {
        this.context_ = context;
        this.isRealHardwareId = true;
    }

    private Object getAdInfoObject() {
        try {
            Object object = Class.forName("com.google.android.gms.ads.identifier.AdvertisingIdClient").getMethod("getAdvertisingIdInfo", Context.class).invoke(null, new Object[]{this.context_});
            return object;
        }
        catch (Throwable throwable) {
            return null;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private String getAdvertisingId(Object object) {
        try {
            this.GAIDString_ = (String)object.getClass().getMethod("getId", new Class[0]).invoke(object, new Object[0]);
            do {
                return this.GAIDString_;
                break;
            } while (true);
        }
        catch (Exception exception) {
            return this.GAIDString_;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private int getLATValue(Object object) {
        try {
            int n = (Boolean)object.getClass().getMethod("isLimitAdTrackingEnabled", new Class[0]).invoke(object, new Object[0]) != false ? 1 : 0;
            this.LATVal_ = n;
            return this.LATVal_;
        }
        catch (Exception exception) {
            return this.LATVal_;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    static String getLocalIPAddress() {
        var1 = "";
        var2_2 = var1;
        try {
            var4_3 = Collections.list(NetworkInterface.getNetworkInterfaces()).iterator();
        }
        catch (Throwable var1_1) {
            return var2_2;
        }
        block2: do {
            var2_2 = var1;
            var3_5 = var1;
            if (var4_3.hasNext() == false) return var3_5;
            var2_2 = var1;
            var5_6 = Collections.list(((NetworkInterface)var4_3.next()).getInetAddresses()).iterator();
            do lbl-1000:
            // 3 sources
            {
                var2_2 = var1;
                if (!var5_6.hasNext()) continue block2;
                var2_2 = var1;
                var3_5 = (InetAddress)var5_6.next();
                var2_2 = var1;
                if (var3_5.isLoopbackAddress()) ** GOTO lbl-1000
                var2_2 = var1;
                var3_5 = var3_5.getHostAddress();
                var2_2 = var1;
            } while ((var0_4 = (var0_4 = var3_5.indexOf(58)) < 0 ? 1 : 0) == 0);
            var1 = var3_5;
        } while (true);
    }

    /*
     * Unable to fully structure code
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private String getURIScheme(String var1_1) {
        block29: {
            var2_18 = var3_17 = "bnc_no_value";
            if (this.isLowOnMemory() != false) return var2_30;
            var4_49 = this.context_.getPackageManager();
            var2_19 = var3_17;
            var4_49 = var4_49.getApplicationInfo((String)var1_1 /* !! */ , (int)0).publicSourceDir;
            var6_50 = null;
            var1_2 = null;
            var2_20 = null;
            var8_53 = null;
            var7_54 = null;
            var5_55 = null;
            var4_49 = new JarFile((String)var4_49);
            var1_3 = var8_53;
            var2_21 = var7_54;
            var1_4 = var5_55 = var4_49.getInputStream(var4_49.getEntry("AndroidManifest.xml"));
            var2_22 = var5_55;
            var6_51 = new byte[var5_55.available()];
            var1_5 = var5_55;
            var2_23 = var5_55;
            var5_55.read(var6_51);
            var1_6 = var5_55;
            var2_24 = var5_55;
            var1_7 = var6_52 = new ApkParser().decompressXML(var6_51);
            if (var5_55 == null) break block29;
            var2_25 = var1_7;
            try {
                var5_55.close();
            }
            catch (IOException var2_31) {
                return var1_7;
            }
        }
        if (var4_49 != null) {
            var2_27 = var1_7;
            var4_49.close();
        }
        var2_29 = var1_7;
        do {
            return var2_30;
            break;
        } while (true);
        catch (Exception var2_32) {
            var4_49 = var5_55;
lbl47:
            // 2 sources
            do {
                if (var4_49 != null) {
                    var2_34 = var3_17;
                    var4_49.close();
                }
                var2_36 = var3_17;
                if (var1_8 == null) ** continue;
                var2_37 = var3_17;
                try {
                    var1_8.close();
                    return "bnc_no_value";
                }
                catch (IOException var1_9) {
                    return "bnc_no_value";
                }
                break;
            } while (true);
        }
        catch (Throwable var1_10) {
            var5_55 = var6_50;
            var4_49 = var2_20;
lbl63:
            // 2 sources
            do {
                if (var4_49 != null) {
                    var2_39 = var3_17;
                    var4_49.close();
                }
                if (var5_55 != null) {
                    var2_41 = var3_17;
                    var5_55.close();
                }
lbl72:
                // 4 sources
                do {
                    var2_43 = var3_17;
                    throw var1_11;
                    {
                        catch (PackageManager.NameNotFoundException var1_12) {
                            return var2_44;
                        }
                    }
                    break;
                } while (true);
                catch (IOException var2_45) {
                    ** continue;
                }
                break;
            } while (true);
        }
        catch (Throwable var2_46) {
            var5_55 = var4_49;
            var4_49 = var1_13;
            var1_14 = var2_46;
            ** continue;
        }
        catch (Exception var1_15) {
            var1_16 = var4_49;
            var4_49 = var2_47;
            ** continue;
        }
    }

    String getAppVersion() {
        try {
            PackageInfo packageInfo = this.context_.getPackageManager().getPackageInfo(this.context_.getPackageName(), 0);
            if (packageInfo.versionName != null) {
                return packageInfo.versionName;
            }
            return "bnc_no_value";
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            return "bnc_no_value";
        }
    }

    String getISO2CountryCode() {
        if (Locale.getDefault() != null) {
            return Locale.getDefault().getCountry();
        }
        return "";
    }

    String getISO2LanguageCode() {
        if (Locale.getDefault() != null) {
            return Locale.getDefault().getLanguage();
        }
        return "";
    }

    String getOS() {
        return "Android";
    }

    int getOSVersion() {
        return Build.VERSION.SDK_INT;
    }

    String getPackageName() {
        try {
            String string2 = this.context_.getPackageManager().getPackageInfo((String)this.context_.getPackageName(), (int)0).packageName;
            return string2;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            nameNotFoundException.printStackTrace();
            return "";
        }
    }

    String getPhoneBrand() {
        return Build.MANUFACTURER;
    }

    String getPhoneModel() {
        return Build.MODEL;
    }

    DisplayMetrics getScreenDisplay() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager)this.context_.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    String getURIScheme() {
        return this.getURIScheme(this.context_.getPackageName());
    }

    String getUniqueID(boolean bl) {
        if (this.context_ != null) {
            String string2 = null;
            if (!bl) {
                string2 = Settings.Secure.getString((ContentResolver)this.context_.getContentResolver(), (String)"android_id");
            }
            String string3 = string2;
            if (string2 == null) {
                string3 = UUID.randomUUID().toString();
                this.isRealHardwareId = false;
            }
            return string3;
        }
        return "bnc_no_value";
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @SuppressLint(value={"NewApi"})
    int getUpdateState() {
        PrefHelper prefHelper = PrefHelper.getInstance(this.context_);
        String string2 = this.getAppVersion();
        if ("bnc_no_value".equals(prefHelper.getAppVersion())) {
            if (Build.VERSION.SDK_INT < 9) return 0;
            try {
                prefHelper = this.context_.getPackageManager().getPackageInfo(this.context_.getPackageName(), 0);
                long l = ((PackageInfo)prefHelper).lastUpdateTime;
                long l2 = ((PackageInfo)prefHelper).firstInstallTime;
                if (l == l2) return 0;
                return 2;
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {
                // empty catch block
                return 0;
            }
        }
        if (prefHelper.getAppVersion().equals(string2)) return 1;
        return 2;
    }

    public boolean getWifiConnected() {
        if (this.context_.checkCallingOrSelfPermission("android.permission.ACCESS_NETWORK_STATE") == 0) {
            NetworkInfo networkInfo = ((ConnectivityManager)this.context_.getSystemService("connectivity")).getNetworkInfo(1);
            return networkInfo != null && networkInfo.isConnected();
        }
        return false;
    }

    boolean hasRealHardwareId() {
        return this.isRealHardwareId;
    }

    boolean isLowOnMemory() {
        ActivityManager activityManager = (ActivityManager)this.context_.getSystemService("activity");
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        return memoryInfo.lowMemory;
    }

    boolean prefetchGAdsParams(GAdsParamsFetchEvents gAdsParamsFetchEvents) {
        boolean bl = false;
        if (TextUtils.isEmpty((CharSequence)this.GAIDString_)) {
            bl = true;
            new GAdsPrefetchTask(gAdsParamsFetchEvents).executeTask(new Void[0]);
        }
        return bl;
    }

    static interface GAdsParamsFetchEvents {
        public void onGAdsFetchFinished();
    }

    private class GAdsPrefetchTask
    extends BranchAsyncTask<Void, Void, Void> {
        private final GAdsParamsFetchEvents callback_;

        public GAdsPrefetchTask(GAdsParamsFetchEvents gAdsParamsFetchEvents) {
            this.callback_ = gAdsParamsFetchEvents;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        protected Void doInBackground(Void ... object) {
            object = new CountDownLatch(1);
            new Thread(new Runnable((CountDownLatch)object){
                final /* synthetic */ CountDownLatch val$latch;
                {
                    this.val$latch = countDownLatch;
                }

                @Override
                public void run() {
                    Process.setThreadPriority((int)-19);
                    Object object = SystemObserver.this.getAdInfoObject();
                    SystemObserver.this.getAdvertisingId(object);
                    SystemObserver.this.getLATValue(object);
                    this.val$latch.countDown();
                }
            }).start();
            try {
                ((CountDownLatch)object).await(1500L, TimeUnit.MILLISECONDS);
                do {
                    return null;
                    break;
                } while (true);
            }
            catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
                return null;
            }
        }

        protected void onPostExecute(Void void_) {
            super.onPostExecute((Object)void_);
            if (this.callback_ != null) {
                this.callback_.onGAdsFetchFinished();
            }
        }

    }

}

