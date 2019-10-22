/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package io.fabric.sdk.android.services.common;

import android.content.Context;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.common.AdvertisingInfo;
import io.fabric.sdk.android.services.common.AdvertisingInfoStrategy;
import java.lang.reflect.Method;

class AdvertisingInfoReflectionStrategy
implements AdvertisingInfoStrategy {
    private final Context context;

    public AdvertisingInfoReflectionStrategy(Context context) {
        this.context = context.getApplicationContext();
    }

    private String getAdvertisingId() {
        try {
            String string2 = (String)Class.forName("com.google.android.gms.ads.identifier.AdvertisingIdClient$Info").getMethod("getId", new Class[0]).invoke(this.getInfo(), new Object[0]);
            return string2;
        }
        catch (Exception exception) {
            Fabric.getLogger().w("Fabric", "Could not call getId on com.google.android.gms.ads.identifier.AdvertisingIdClient$Info");
            return null;
        }
    }

    private Object getInfo() {
        try {
            Object object = Class.forName("com.google.android.gms.ads.identifier.AdvertisingIdClient").getMethod("getAdvertisingIdInfo", Context.class).invoke(null, new Object[]{this.context});
            return object;
        }
        catch (Exception exception) {
            Fabric.getLogger().w("Fabric", "Could not call getAdvertisingIdInfo on com.google.android.gms.ads.identifier.AdvertisingIdClient");
            return null;
        }
    }

    private boolean isLimitAdTrackingEnabled() {
        try {
            boolean bl = (Boolean)Class.forName("com.google.android.gms.ads.identifier.AdvertisingIdClient$Info").getMethod("isLimitAdTrackingEnabled", new Class[0]).invoke(this.getInfo(), new Object[0]);
            return bl;
        }
        catch (Exception exception) {
            Fabric.getLogger().w("Fabric", "Could not call isLimitAdTrackingEnabled on com.google.android.gms.ads.identifier.AdvertisingIdClient$Info");
            return false;
        }
    }

    @Override
    public AdvertisingInfo getAdvertisingInfo() {
        if (this.isGooglePlayServiceAvailable(this.context)) {
            return new AdvertisingInfo(this.getAdvertisingId(), this.isLimitAdTrackingEnabled());
        }
        return null;
    }

    boolean isGooglePlayServiceAvailable(Context context) {
        try {
            int n = (Integer)Class.forName("com.google.android.gms.common.GooglePlayServicesUtil").getMethod("isGooglePlayServicesAvailable", Context.class).invoke(null, new Object[]{context});
            return n == 0;
        }
        catch (Exception exception) {
            return false;
        }
    }
}

