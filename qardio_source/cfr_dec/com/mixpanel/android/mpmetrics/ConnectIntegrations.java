/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 */
package com.mixpanel.android.mpmetrics;

import android.os.Handler;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.mixpanel.android.util.MPLog;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

class ConnectIntegrations {
    private final MixpanelAPI mMixpanel;
    private String mSavedUrbanAirshipChannelID;
    private int mUrbanAirshipRetries;

    public ConnectIntegrations(MixpanelAPI mixpanelAPI) {
        this.mMixpanel = mixpanelAPI;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void setUrbanAirshipPeopleProp() {
        synchronized (this) {
            try {
                Object object = Class.forName("com.urbanairship.UAirship").getMethod("shared", null).invoke(null, new Object[0]);
                object = object.getClass().getMethod("getPushManager", null).invoke(object, new Object[0]);
                object = (String)object.getClass().getMethod("getChannelId", null).invoke(object, new Object[0]);
                if (object != null && !((String)object).isEmpty()) {
                    this.mUrbanAirshipRetries = 0;
                    if (this.mSavedUrbanAirshipChannelID == null || !this.mSavedUrbanAirshipChannelID.equals(object)) {
                        this.mMixpanel.getPeople().set("$android_urban_airship_channel_id", object);
                        this.mSavedUrbanAirshipChannelID = object;
                    }
                } else {
                    ++this.mUrbanAirshipRetries;
                    if (this.mUrbanAirshipRetries <= 3) {
                        new Handler().postDelayed(new Runnable(){

                            @Override
                            public void run() {
                                ConnectIntegrations.this.setUrbanAirshipPeopleProp();
                            }
                        }, 2000L);
                    }
                }
            }
            catch (ClassNotFoundException classNotFoundException) {
                MPLog.w("MixpanelAPI.CnctInts", "Urban Airship SDK not found but Urban Airship is integrated on Mixpanel", classNotFoundException);
            }
            catch (NoSuchMethodException noSuchMethodException) {
                MPLog.e("MixpanelAPI.CnctInts", "Urban Airship SDK class exists but methods do not", noSuchMethodException);
            }
            catch (InvocationTargetException invocationTargetException) {
                MPLog.e("MixpanelAPI.CnctInts", "method invocation failed", invocationTargetException);
            }
            catch (IllegalAccessException illegalAccessException) {
                MPLog.e("MixpanelAPI.CnctInts", "method invocation failed", illegalAccessException);
            }
            return;
        }
    }

    public void reset() {
        this.mSavedUrbanAirshipChannelID = null;
        this.mUrbanAirshipRetries = 0;
    }

    public void setupIntegrations(Set<String> set) {
        synchronized (this) {
            if (set.contains("urbanairship")) {
                this.setUrbanAirshipPeopleProp();
            }
            return;
        }
    }

}

