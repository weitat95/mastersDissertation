/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.samsung.android.sdk.healthdata;

import android.content.Context;

public final class HealthDataService {
    static boolean a;

    public final void initialize(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context is invalid.");
        }
        try {
            context.getApplicationContext();
            a = true;
            return;
        }
        catch (Exception exception) {
            throw new IllegalArgumentException("context is invalid.");
        }
    }
}

