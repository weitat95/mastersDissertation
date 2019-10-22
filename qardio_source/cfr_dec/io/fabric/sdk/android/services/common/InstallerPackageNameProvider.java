/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.PackageManager
 */
package io.fabric.sdk.android.services.common;

import android.content.Context;
import android.content.pm.PackageManager;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.cache.MemoryValueCache;
import io.fabric.sdk.android.services.cache.ValueLoader;

public class InstallerPackageNameProvider {
    private final MemoryValueCache<String> installerPackageNameCache;
    private final ValueLoader<String> installerPackageNameLoader = new ValueLoader<String>(){

        @Override
        public String load(Context object) throws Exception {
            String string2 = object.getPackageManager().getInstallerPackageName(object.getPackageName());
            object = string2;
            if (string2 == null) {
                object = "";
            }
            return object;
        }
    };

    public InstallerPackageNameProvider() {
        this.installerPackageNameCache = new MemoryValueCache();
    }

    public String getInstallerPackageName(Context object) {
        try {
            object = this.installerPackageNameCache.get((Context)object, this.installerPackageNameLoader);
            boolean bl = "".equals(object);
            if (bl) {
                object = null;
            }
            return object;
        }
        catch (Exception exception) {
            Fabric.getLogger().e("Fabric", "Failed to determine installer package name", exception);
            return null;
        }
    }

}

