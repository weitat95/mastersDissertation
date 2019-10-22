/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageManager
 *  android.content.res.Resources
 *  android.os.Bundle
 *  android.text.TextUtils
 */
package io.fabric.sdk.android.services.common;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.common.CommonUtils;

public class ApiKey {
    protected String buildApiKeyInstructions() {
        return "Fabric could not be initialized, API key missing from AndroidManifest.xml. Add the following tag to your Application element \n\t<meta-data android:name=\"io.fabric.ApiKey\" android:value=\"YOUR_API_KEY\"/>";
    }

    protected String getApiKeyFromManifest(Context object) {
        block6: {
            String string2;
            String string3 = null;
            Object var4_4 = null;
            String string4 = string3;
            try {
                string2 = object.getPackageName();
                string4 = string3;
            }
            catch (Exception exception) {
                Fabric.getLogger().d("Fabric", "Caught non-fatal exception while retrieving apiKey: " + exception);
                return string4;
            }
            string2 = object.getPackageManager().getApplicationInfo((String)string2, (int)128).metaData;
            object = var4_4;
            if (string2 == null) break block6;
            string4 = string3;
            string3 = string2.getString("io.fabric.ApiKey");
            object = string3;
            if (string3 != null) break block6;
            string4 = string3;
            Fabric.getLogger().d("Fabric", "Falling back to Crashlytics key lookup from Manifest");
            string4 = string3;
            object = string2.getString("com.crashlytics.ApiKey");
        }
        return object;
    }

    protected String getApiKeyFromStrings(Context context) {
        int n;
        String string2 = null;
        int n2 = n = CommonUtils.getResourcesIdentifier(context, "io.fabric.ApiKey", "string");
        if (n == 0) {
            Fabric.getLogger().d("Fabric", "Falling back to Crashlytics key lookup from Strings");
            n2 = CommonUtils.getResourcesIdentifier(context, "com.crashlytics.ApiKey", "string");
        }
        if (n2 != 0) {
            string2 = context.getResources().getString(n2);
        }
        return string2;
    }

    public String getValue(Context context) {
        String string2;
        String string3 = string2 = this.getApiKeyFromManifest(context);
        if (TextUtils.isEmpty((CharSequence)string2)) {
            string3 = this.getApiKeyFromStrings(context);
        }
        if (TextUtils.isEmpty((CharSequence)string3)) {
            this.logErrorOrThrowException(context);
        }
        return string3;
    }

    protected void logErrorOrThrowException(Context context) {
        if (Fabric.isDebuggable() || CommonUtils.isAppDebuggable(context)) {
            throw new IllegalArgumentException(this.buildApiKeyInstructions());
        }
        Fabric.getLogger().e("Fabric", this.buildApiKeyInstructions());
    }
}

