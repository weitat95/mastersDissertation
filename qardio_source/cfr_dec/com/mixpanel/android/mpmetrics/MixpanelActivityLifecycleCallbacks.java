/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.app.Activity
 *  android.app.Application
 *  android.app.Application$ActivityLifecycleCallbacks
 *  android.content.Intent
 *  android.os.BadParcelableException
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.mixpanel.android.mpmetrics;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.BadParcelableException;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import com.mixpanel.android.mpmetrics.MPConfig;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.mixpanel.android.viewcrawler.GestureTracker;
import java.text.NumberFormat;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

@TargetApi(value=14)
class MixpanelActivityLifecycleCallbacks
implements Application.ActivityLifecycleCallbacks {
    private static Double sStartSessionTime;
    private Runnable check;
    private final MPConfig mConfig;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private boolean mIsForeground = true;
    private final MixpanelAPI mMpInstance;
    private boolean mPaused = true;

    public MixpanelActivityLifecycleCallbacks(MixpanelAPI mixpanelAPI, MPConfig mPConfig) {
        this.mMpInstance = mixpanelAPI;
        this.mConfig = mPConfig;
        if (sStartSessionTime == null) {
            sStartSessionTime = System.currentTimeMillis();
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void trackCampaignOpenedIfNeeded(Intent var1_1) {
        block6: {
            if (var1_1 == null) break block6;
            try {
                if (!var1_1.hasExtra("mp_campaign_id") || !var1_1.hasExtra("mp_message_id")) break block6;
                var3_3 = var1_1.getStringExtra("mp_campaign_id");
                var4_4 = var1_1.getStringExtra("mp_message_id");
                var2_5 = var1_1.getStringExtra("mp");
                if (var2_5 == null) ** GOTO lbl-1000
                try {
                    var2_5 = new JSONObject(var2_5);
                }
                catch (JSONException var2_6) {
                    ** GOTO lbl21
                }
lbl13:
                // 2 sources
                do {
                    var2_5.put("campaign_id", Integer.valueOf(var3_3).intValue());
                    var2_5.put("message_id", Integer.valueOf(var4_4).intValue());
                    var2_5.put("message_type", (Object)"push");
                    this.mMpInstance.track("$app_open", (JSONObject)var2_5);
lbl21:
                    // 2 sources
                    var1_1.removeExtra("mp_campaign_id");
                    var1_1.removeExtra("mp_message_id");
                    var1_1.removeExtra("mp");
                    return;
                    break;
                } while (true);
            }
            catch (BadParcelableException var1_2) {
                return;
            }
        }
        return;
lbl-1000:
        // 1 sources
        {
            var2_5 = new JSONObject();
            ** continue;
        }
    }

    protected boolean isInForeground() {
        return this.mIsForeground;
    }

    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    public void onActivityDestroyed(Activity activity) {
    }

    public void onActivityPaused(Activity activity) {
        Runnable runnable;
        this.mPaused = true;
        if (this.check != null) {
            this.mHandler.removeCallbacks(this.check);
        }
        activity = this.mHandler;
        this.check = runnable = new Runnable(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void run() {
                if (MixpanelActivityLifecycleCallbacks.this.mIsForeground && MixpanelActivityLifecycleCallbacks.this.mPaused) {
                    MixpanelActivityLifecycleCallbacks.this.mIsForeground = false;
                    try {
                        double d = (double)System.currentTimeMillis() - sStartSessionTime;
                        if (d >= (double)MixpanelActivityLifecycleCallbacks.this.mConfig.getMinimumSessionDuration() && d < (double)MixpanelActivityLifecycleCallbacks.this.mConfig.getSessionTimeoutDuration()) {
                            Object object = NumberFormat.getNumberInstance(Locale.ENGLISH);
                            ((NumberFormat)object).setMaximumFractionDigits(1);
                            object = ((NumberFormat)object).format(((double)System.currentTimeMillis() - sStartSessionTime) / 1000.0);
                            JSONObject jSONObject = new JSONObject();
                            jSONObject.put("$ae_session_length", object);
                            MixpanelActivityLifecycleCallbacks.this.mMpInstance.track("$ae_session", jSONObject, true);
                        }
                    }
                    catch (JSONException jSONException) {
                        jSONException.printStackTrace();
                    }
                    MixpanelActivityLifecycleCallbacks.this.mMpInstance.onBackground();
                }
            }
        };
        activity.postDelayed(runnable, 500L);
    }

    public void onActivityResumed(Activity activity) {
        boolean bl = false;
        if (Build.VERSION.SDK_INT >= 16 && this.mConfig.getAutoShowMixpanelUpdates()) {
            this.mMpInstance.getPeople().joinExperimentIfAvailable();
        }
        this.mPaused = false;
        if (!this.mIsForeground) {
            bl = true;
        }
        this.mIsForeground = true;
        if (this.check != null) {
            this.mHandler.removeCallbacks(this.check);
        }
        if (bl) {
            sStartSessionTime = System.currentTimeMillis();
        }
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    public void onActivityStarted(Activity activity) {
        this.trackCampaignOpenedIfNeeded(activity.getIntent());
        if (Build.VERSION.SDK_INT >= 16 && this.mConfig.getAutoShowMixpanelUpdates()) {
            this.mMpInstance.getPeople().showNotificationIfAvailable(activity);
        }
        new GestureTracker(this.mMpInstance, activity);
    }

    public void onActivityStopped(Activity activity) {
    }

}

