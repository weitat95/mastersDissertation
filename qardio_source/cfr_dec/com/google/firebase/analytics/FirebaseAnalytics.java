/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.os.Bundle
 */
package com.google.firebase.analytics;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Keep;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzcim;
import com.google.android.gms.internal.zzcjn;
import com.google.android.gms.internal.zzckc;
import com.google.android.gms.measurement.AppMeasurement;
import com.google.android.gms.tasks.Task;

@Keep
public final class FirebaseAnalytics {
    private final zzcim zziwf;

    public FirebaseAnalytics(zzcim zzcim2) {
        zzbq.checkNotNull(zzcim2);
        this.zziwf = zzcim2;
    }

    @Keep
    public static FirebaseAnalytics getInstance(Context context) {
        return zzcim.zzdx(context).zzbaa();
    }

    public final Task<String> getAppInstanceId() {
        return this.zziwf.zzawm().getAppInstanceId();
    }

    public final void logEvent(String string2, Bundle bundle) {
        this.zziwf.zzazz().logEvent(string2, bundle);
    }

    public final void resetAnalyticsData() {
        this.zziwf.zzawm().resetAnalyticsData();
    }

    public final void setAnalyticsCollectionEnabled(boolean bl) {
        this.zziwf.zzazz().setMeasurementEnabled(bl);
    }

    @Keep
    public final void setCurrentScreen(Activity activity, String string2, String string3) {
        this.zziwf.zzawq().setCurrentScreen(activity, string2, string3);
    }

    public final void setMinimumSessionDuration(long l) {
        this.zziwf.zzazz().setMinimumSessionDuration(l);
    }

    public final void setSessionTimeoutDuration(long l) {
        this.zziwf.zzazz().setSessionTimeoutDuration(l);
    }

    public final void setUserId(String string2) {
        this.zziwf.zzazz().setUserPropertyInternal("app", "_id", string2);
    }

    public final void setUserProperty(String string2, String string3) {
        this.zziwf.zzazz().setUserProperty(string2, string3);
    }

    public static class Event {
    }

    public static class Param {
    }

    public static class UserProperty {
    }

}

