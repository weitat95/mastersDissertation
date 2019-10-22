/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 */
package com.crashlytics.android.beta;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.cache.ValueLoader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class DeviceTokenLoader
implements ValueLoader<String> {
    String determineDeviceToken(ZipInputStream object) throws IOException {
        if ((object = ((ZipInputStream)object).getNextEntry()) != null && ((String)(object = ((ZipEntry)object).getName())).startsWith("assets/com.crashlytics.android.beta/dirfactor-device-token=")) {
            return ((String)object).substring("assets/com.crashlytics.android.beta/dirfactor-device-token=".length(), ((String)object).length() - 1);
        }
        return "";
    }

    ZipInputStream getZipInputStreamOfApkFrom(Context context, String string2) throws PackageManager.NameNotFoundException, FileNotFoundException {
        return new ZipInputStream(new FileInputStream(context.getPackageManager().getApplicationInfo((String)string2, (int)0).sourceDir));
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public String load(Context var1_1) throws Exception {
        var4_10 = System.nanoTime();
        var10_11 = "";
        var8_12 = null;
        var9_13 = null;
        var6_14 = null;
        var7_16 = null;
        try {
            var7_16 = var1_1 = this.getZipInputStreamOfApkFrom((Context)var1_1, "io.crash.air");
            var8_12 = var1_1;
            var9_13 = var1_1;
            var6_14 = var1_1;
            var11_17 = this.determineDeviceToken((ZipInputStream)var1_1);
            var6_14 = var7_16 = var11_17;
            ** if (var1_1 == null) goto lbl22
        }
        catch (PackageManager.NameNotFoundException var1_3) {
            var6_14 = var7_16;
            Fabric.getLogger().d("Beta", "Beta by Crashlytics app is not installed");
            var6_14 = var10_11;
            ** if (var7_16 == null) goto lbl36
lbl-1000:
            // 1 sources
            {
                try {
                    var7_16.close();
                    var6_14 = var10_11;
                }
                catch (IOException var1_4) {
                    Fabric.getLogger().e("Beta", "Failed to close the APK file", var1_4);
                    var6_14 = var10_11;
                }
            }
lbl36:
            // 2 sources
            ** GOTO lbl72
            catch (FileNotFoundException var1_5) {
                var6_14 = var8_12;
                Fabric.getLogger().e("Beta", "Failed to find the APK file", var1_5);
                var6_14 = var10_11;
                ** if (var8_12 == null) goto lbl50
lbl-1000:
                // 1 sources
                {
                    try {
                        var8_12.close();
                        var6_14 = var10_11;
                    }
                    catch (IOException var1_6) {
                        Fabric.getLogger().e("Beta", "Failed to close the APK file", var1_6);
                        var6_14 = var10_11;
                    }
                }
lbl50:
                // 2 sources
                ** GOTO lbl72
                catch (IOException var1_7) {
                    block21: {
                        var6_14 = var9_13;
                        try {
                            Fabric.getLogger().e("Beta", "Failed to read the APK file", var1_7);
                            var6_14 = var10_11;
                            if (var9_13 == null) break block21;
                        }
                        catch (Throwable var1_9) {
                            if (var6_14 == null) throw var1_9;
                            try {
                                var6_14.close();
                            }
                            catch (IOException var6_15) {
                                Fabric.getLogger().e("Beta", "Failed to close the APK file", var6_15);
                                throw var1_9;
                            }
                            throw var1_9;
                        }
                        try {
                            var9_13.close();
                            var6_14 = var10_11;
                        }
                        catch (IOException var1_8) {
                            Fabric.getLogger().e("Beta", "Failed to close the APK file", var1_8);
                            var6_14 = var10_11;
                        }
                    }
                    var2_18 = (double)(System.nanoTime() - var4_10) / 1000000.0;
                    Fabric.getLogger().d("Beta", "Beta device token load took " + var2_18 + "ms");
                    return var6_14;
                }
            }
        }
lbl-1000:
        // 1 sources
        {
            try {
                var1_1.close();
                var6_14 = var7_16;
            }
            catch (IOException var1_2) {
                Fabric.getLogger().e("Beta", "Failed to close the APK file", var1_2);
                var6_14 = var7_16;
            }
        }
lbl22:
        // 2 sources
        ** GOTO lbl72
    }
}

