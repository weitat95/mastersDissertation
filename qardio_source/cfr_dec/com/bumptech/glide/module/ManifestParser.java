/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.os.Bundle
 */
package com.bumptech.glide.module;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import com.bumptech.glide.module.GlideModule;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class ManifestParser {
    private final Context context;

    public ManifestParser(Context context) {
        this.context = context;
    }

    private static GlideModule parseModule(String object) {
        Object t;
        try {
            object = Class.forName((String)object);
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new IllegalArgumentException("Unable to find GlideModule implementation", classNotFoundException);
        }
        try {
            t = ((Class)object).newInstance();
        }
        catch (InstantiationException instantiationException) {
            throw new RuntimeException("Unable to instantiate GlideModule implementation for " + object, instantiationException);
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new RuntimeException("Unable to instantiate GlideModule implementation for " + object, illegalAccessException);
        }
        if (!(t instanceof GlideModule)) {
            throw new RuntimeException("Expected instanceof GlideModule, but found: " + t);
        }
        return (GlideModule)t;
    }

    public List<GlideModule> parse() {
        ArrayList<GlideModule> arrayList = new ArrayList<GlideModule>();
        try {
            ApplicationInfo applicationInfo = this.context.getPackageManager().getApplicationInfo(this.context.getPackageName(), 128);
            if (applicationInfo.metaData != null) {
                for (String string2 : applicationInfo.metaData.keySet()) {
                    if (!"GlideModule".equals(applicationInfo.metaData.get(string2))) continue;
                    arrayList.add(ManifestParser.parseModule(string2));
                }
            }
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            throw new RuntimeException("Unable to find metadata to parse GlideModules", nameNotFoundException);
        }
        return arrayList;
    }
}

