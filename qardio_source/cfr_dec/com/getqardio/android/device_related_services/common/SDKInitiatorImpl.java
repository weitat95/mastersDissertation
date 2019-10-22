/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.getqardio.android.device_related_services.common;

import android.content.Context;
import com.getqardio.android.device_related_services.common.SDKInitiator;

public class SDKInitiatorImpl
implements SDKInitiator {
    private static SDKInitiatorImpl instance;

    private SDKInitiatorImpl() {
    }

    public static SDKInitiator newInstance() {
        if (instance == null) {
            instance = new SDKInitiatorImpl();
        }
        return instance;
    }

    @Override
    public void init(Context context) {
    }
}

