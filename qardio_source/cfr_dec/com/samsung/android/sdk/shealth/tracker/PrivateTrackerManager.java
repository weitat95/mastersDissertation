/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.res.Resources
 */
package com.samsung.android.sdk.shealth.tracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import com.samsung.android.sdk.shealth.Shealth;

class PrivateTrackerManager {
    private Context mContext;
    private boolean mIsInitialized = false;
    private String mPackageName = null;

    public PrivateTrackerManager(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context is null.");
        }
        try {
            context = context.getApplicationContext();
            if (context == null) {
                throw new IllegalArgumentException("context is invalid.");
            }
        }
        catch (NullPointerException nullPointerException) {
            throw new IllegalArgumentException("context is invalid.");
        }
        try {
            Resources resources = context.getResources();
            if (resources == null) {
                throw new IllegalArgumentException("context is invalid. - resources is null");
            }
        }
        catch (NullPointerException nullPointerException) {
            throw new IllegalArgumentException("context is invalid. - resources is null");
        }
        try {
            this.mPackageName = context.getPackageName();
        }
        catch (NullPointerException nullPointerException) {
            throw new IllegalArgumentException("context is invalid. - package name is null");
        }
        if (this.mPackageName == null || this.mPackageName.isEmpty()) {
            throw new IllegalArgumentException("context is invalid. - package name is null");
        }
        if (context.getSharedPreferences("sdk_shealth", 4) == null) {
            throw new IllegalArgumentException("context is invalid. - SharedPreferences is null");
        }
        new Shealth().isFeatureEnabled(3);
        this.mContext = context;
    }
}

