/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.os.AsyncTask
 *  android.provider.Settings
 *  android.provider.Settings$Secure
 *  android.util.Pair
 */
package com.segment.analytics;

import android.content.ContentResolver;
import android.content.Context;
import android.os.AsyncTask;
import android.provider.Settings;
import android.util.Pair;
import com.segment.analytics.AnalyticsContext;
import com.segment.analytics.integrations.Logger;
import java.lang.reflect.Method;
import java.util.concurrent.CountDownLatch;

class GetAdvertisingIdTask
extends AsyncTask<Context, Void, Pair<String, Boolean>> {
    private final AnalyticsContext analyticsContext;
    private final CountDownLatch latch;
    private final Logger logger;

    GetAdvertisingIdTask(AnalyticsContext analyticsContext, CountDownLatch countDownLatch, Logger logger) {
        this.analyticsContext = analyticsContext;
        this.latch = countDownLatch;
        this.logger = logger;
    }

    /*
     * Enabled aggressive block sorting
     */
    private Pair<String, Boolean> getAmazonFireAdvertisingID(Context context) throws Exception {
        boolean bl = Settings.Secure.getInt((ContentResolver)(context = context.getContentResolver()), (String)"limit_ad_tracking") != 0;
        if (bl) {
            this.logger.debug("Not collecting advertising ID because limit_ad_tracking (Amazon Fire OS) is true.", new Object[0]);
            return Pair.create(null, (Object)false);
        }
        return Pair.create((Object)Settings.Secure.getString((ContentResolver)context, (String)"advertising_id"), (Object)true);
    }

    private Pair<String, Boolean> getGooglePlayServicesAdvertisingID(Context object) throws Exception {
        Object[] arrobject = new Object[]{object};
        object = Class.forName("com.google.android.gms.ads.identifier.AdvertisingIdClient").getMethod("getAdvertisingIdInfo", Context.class).invoke(null, arrobject);
        if (((Boolean)object.getClass().getMethod("isLimitAdTrackingEnabled", new Class[0]).invoke(object, new Object[0])).booleanValue()) {
            this.logger.debug("Not collecting advertising ID because isLimitAdTrackingEnabled (Google Play Services) is true.", new Object[0]);
            return Pair.create(null, (Object)false);
        }
        return Pair.create((Object)((String)object.getClass().getMethod("getId", new Class[0]).invoke(object, new Object[0])), (Object)true);
    }

    protected Pair<String, Boolean> doInBackground(Context ... pair) {
        pair = pair[0];
        try {
            Pair<String, Boolean> pair2 = this.getGooglePlayServicesAdvertisingID((Context)pair);
            return pair2;
        }
        catch (Exception exception) {
            this.logger.error(exception, "Unable to collect advertising ID from Google Play Services.", new Object[0]);
            try {
                pair = this.getAmazonFireAdvertisingID((Context)pair);
                return pair;
            }
            catch (Exception exception2) {
                this.logger.error(exception2, "Unable to collect advertising ID from Amazon Fire OS.", new Object[0]);
                this.logger.debug("Unable to collect advertising ID from Amazon Fire OS and Google Play Services.", new Object[0]);
                return null;
            }
        }
    }

    protected void onPostExecute(Pair<String, Boolean> pair) {
        super.onPostExecute(pair);
        if (pair == null) {
            this.latch.countDown();
            return;
        }
        try {
            AnalyticsContext.Device device = this.analyticsContext.device();
            if (device == null) {
                this.logger.debug("Not collecting advertising ID because context.device is null.", new Object[0]);
                return;
            }
            device.putAdvertisingInfo((String)pair.first, (Boolean)pair.second);
            return;
        }
        finally {
            this.latch.countDown();
        }
    }
}

