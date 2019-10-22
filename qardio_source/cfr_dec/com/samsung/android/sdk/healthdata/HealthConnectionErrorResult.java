/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.pm.PackageManager
 */
package com.samsung.android.sdk.healthdata;

import android.content.pm.PackageManager;

public final class HealthConnectionErrorResult {
    private PackageManager a;
    private final int b;
    private final boolean c;

    public HealthConnectionErrorResult(int n, boolean bl) {
        this.b = n;
        this.c = bl;
    }

    public final int getErrorCode() {
        return this.b;
    }

    public final void setPackageManager(PackageManager packageManager) {
        this.a = packageManager;
    }
}

