/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 */
package com.bumptech.glide.signature;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.signature.StringSignature;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class ApplicationVersionSignature {
    private static final ConcurrentHashMap<String, Key> PACKAGE_NAME_TO_KEY = new ConcurrentHashMap();

    public static Key obtain(Context object) {
        Key key;
        String string2 = object.getPackageName();
        Key key2 = key = PACKAGE_NAME_TO_KEY.get(string2);
        if (key == null) {
            object = ApplicationVersionSignature.obtainVersionSignature(object);
            key2 = key = PACKAGE_NAME_TO_KEY.putIfAbsent(string2, (Key)object);
            if (key == null) {
                key2 = object;
            }
        }
        return key2;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static Key obtainVersionSignature(Context object) {
        void var0_2;
        void var0_4;
        Object var1_8 = null;
        try {
            PackageInfo packageInfo = object.getPackageManager().getPackageInfo(object.getPackageName(), 0);
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            nameNotFoundException.printStackTrace();
            Object var0_6 = var1_8;
        }
        if (var0_2 != null) {
            String string2 = String.valueOf(var0_2.versionCode);
            return new StringSignature((String)var0_4);
        }
        String string3 = UUID.randomUUID().toString();
        return new StringSignature((String)var0_4);
    }
}

