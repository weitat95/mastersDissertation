/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.SystemClock
 *  android.text.TextUtils
 */
package io.fabric.sdk.android;

import android.os.SystemClock;
import android.text.TextUtils;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.KitInfo;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

class FabricKitsFinder
implements Callable<Map<String, KitInfo>> {
    final String apkFileName;

    FabricKitsFinder(String string2) {
        this.apkFileName = string2;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private KitInfo loadKitInfo(ZipEntry zipEntry, ZipFile closeable) {
        Object object;
        Object object2;
        String string2;
        Closeable closeable2 = null;
        Closeable closeable3 = null;
        try {
            block7: {
                try {
                    closeable3 = closeable = closeable.getInputStream(zipEntry);
                    closeable2 = closeable;
                    object2 = new Properties();
                    closeable3 = closeable;
                    closeable2 = closeable;
                    ((Properties)object2).load((InputStream)closeable);
                    closeable3 = closeable;
                    closeable2 = closeable;
                    object = ((Properties)object2).getProperty("fabric-identifier");
                    closeable3 = closeable;
                    closeable2 = closeable;
                    string2 = ((Properties)object2).getProperty("fabric-version");
                    closeable3 = closeable;
                    closeable2 = closeable;
                    object2 = ((Properties)object2).getProperty("fabric-build-type");
                    closeable3 = closeable;
                    closeable2 = closeable;
                    if (!TextUtils.isEmpty((CharSequence)object)) {
                        closeable3 = closeable;
                        closeable2 = closeable;
                        if (!TextUtils.isEmpty((CharSequence)string2)) break block7;
                    }
                    closeable3 = closeable;
                    closeable2 = closeable;
                    throw new IllegalStateException("Invalid format of fabric file," + zipEntry.getName());
                }
                catch (IOException iOException) {
                    closeable2 = closeable3;
                    Fabric.getLogger().e("Fabric", "Error when parsing fabric properties " + zipEntry.getName(), iOException);
                    CommonUtils.closeQuietly(closeable3);
                    return null;
                }
            }
            closeable3 = closeable;
            closeable2 = closeable;
        }
        catch (Throwable throwable) {
            CommonUtils.closeQuietly(closeable2);
            throw throwable;
        }
        {
            object = new KitInfo((String)object, string2, (String)object2);
        }
        CommonUtils.closeQuietly(closeable);
        return object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Map<String, KitInfo> call() throws Exception {
        HashMap<String, KitInfo> hashMap = new HashMap<String, KitInfo>();
        long l = SystemClock.elapsedRealtime();
        int n = 0;
        ZipFile zipFile = this.loadApkFile();
        Enumeration<? extends ZipEntry> enumeration = zipFile.entries();
        while (enumeration.hasMoreElements()) {
            int n2 = n + 1;
            Object object = enumeration.nextElement();
            n = n2;
            if (!((ZipEntry)object).getName().startsWith("fabric/")) continue;
            n = n2;
            if (((ZipEntry)object).getName().length() <= "fabric/".length()) continue;
            object = this.loadKitInfo((ZipEntry)object, zipFile);
            n = n2;
            if (object == null) continue;
            hashMap.put(((KitInfo)object).getIdentifier(), (KitInfo)object);
            Fabric.getLogger().v("Fabric", String.format("Found kit:[%s] version:[%s]", ((KitInfo)object).getIdentifier(), ((KitInfo)object).getVersion()));
            n = n2;
        }
        if (zipFile != null) {
            try {
                zipFile.close();
            }
            catch (IOException iOException) {}
        }
        Fabric.getLogger().v("Fabric", "finish scanning in " + (SystemClock.elapsedRealtime() - l) + " reading:" + n);
        return hashMap;
    }

    protected ZipFile loadApkFile() throws IOException {
        return new ZipFile(this.apkFileName);
    }
}

