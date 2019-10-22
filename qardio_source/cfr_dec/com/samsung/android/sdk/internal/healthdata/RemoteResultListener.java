/*
 * Decompiled with CFR 0.147.
 */
package com.samsung.android.sdk.internal.healthdata;

import com.samsung.android.sdk.healthdata.HealthResultHolder;

public interface RemoteResultListener<T extends HealthResultHolder.BaseResult> {
    public void onReceiveHealthResult(int var1, T var2);
}

