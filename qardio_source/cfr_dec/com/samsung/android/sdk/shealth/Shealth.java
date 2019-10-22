/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ContentProviderClient
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.net.Uri
 *  android.os.Bundle
 */
package com.samsung.android.sdk.shealth;

import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

public class Shealth {
    private static int VERSION_CODE = 1003000;
    private static String VERSION_NAME = "1.3.0";
    private static Context mApplicationContext;

    int getCompatibleApiVersion() {
        block6: {
            if (mApplicationContext == null) {
                throw new IllegalStateException("Shealth is not initialized.");
            }
            Object object = mApplicationContext.getContentResolver();
            if (object != null) {
                Bundle bundle = new Bundle();
                bundle.putString("versionCode", String.valueOf(VERSION_CODE));
                object = object.call(Uri.parse((String)("content://" + "com.samsung.android.sdk.shealth")), "checkVersionCode", null, bundle);
                if (object == null) break block6;
                object = object.getString("versionCode", null);
                if (object == null) break block6;
                try {
                    int n = Integer.parseInt((String)object);
                    return n;
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
        }
        return 1001000;
    }

    public int getVersionCode() {
        return VERSION_CODE;
    }

    public void initialize(Context context) {
        Context context2;
        if (context == null) {
            throw new IllegalArgumentException("context is invalid");
        }
        try {
            context2 = context.getApplicationContext();
            if (context2 == null) {
                throw new IllegalArgumentException("Invalid arguments. context is invalid.");
            }
        }
        catch (NullPointerException nullPointerException) {
            throw new IllegalArgumentException("Invalid arguments. context is invalid.");
        }
        Uri uri = Uri.parse((String)("content://" + "com.samsung.android.sdk.shealth"));
        uri = context.getContentResolver().acquireContentProviderClient(uri);
        if (uri != null) {
            uri.release();
            context.getApplicationContext().getSharedPreferences("sdk_shealth", 4).edit().putBoolean("is_initialized", true).apply();
            mApplicationContext = context2;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean isFeatureEnabled(int n) {
        block6: {
            block5: {
                if (n == 1) break block5;
                int n2 = this.getCompatibleApiVersion();
                switch (n) {
                    default: {
                        return false;
                    }
                    case 2: {
                        if (n2 >= 1000000) break;
                        return false;
                    }
                    case 3: 
                    case 1001: 
                    case 1002: {
                        if (n2 >= 1002000) break;
                        return false;
                    }
                    case 4: {
                        if (n2 < 1003000) break block6;
                    }
                }
            }
            return true;
        }
        return false;
    }
}

