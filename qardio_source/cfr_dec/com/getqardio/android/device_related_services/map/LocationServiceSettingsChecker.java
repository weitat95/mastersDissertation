/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.getqardio.android.device_related_services.map;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

public interface LocationServiceSettingsChecker {
    public void enableLocation(FragmentActivity var1, int var2);

    public boolean isLocationAvailable(Context var1);

    public void stopChecking();
}

