/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 */
package com.google.android.gms.measurement;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Keep;
import android.support.v4.util.ArrayMap;
import com.google.android.gms.common.api.internal.zzbz;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzcgd;
import com.google.android.gms.internal.zzchm;
import com.google.android.gms.internal.zzcho;
import com.google.android.gms.internal.zzcim;
import com.google.android.gms.internal.zzcjn;
import com.google.android.gms.internal.zzckc;
import com.google.android.gms.internal.zzcln;
import com.google.android.gms.internal.zzclq;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Deprecated
@Keep
public class AppMeasurement {
    public static final String CRASH_ORIGIN = "crash";
    public static final String FCM_ORIGIN = "fcm";
    private final zzcim zziwf;

    public AppMeasurement(zzcim zzcim2) {
        zzbq.checkNotNull(zzcim2);
        this.zziwf = zzcim2;
    }

    @Deprecated
    @Keep
    public static AppMeasurement getInstance(Context context) {
        return zzcim.zzdx(context).zzazz();
    }

    @Keep
    public void beginAdUnitExposure(String string2) {
        this.zziwf.zzawk().beginAdUnitExposure(string2);
    }

    @Keep
    protected void clearConditionalUserProperty(String string2, String string3, Bundle bundle) {
        this.zziwf.zzawm().clearConditionalUserProperty(string2, string3, bundle);
    }

    @Keep
    protected void clearConditionalUserPropertyAs(String string2, String string3, String string4, Bundle bundle) {
        this.zziwf.zzawm().clearConditionalUserPropertyAs(string2, string3, string4, bundle);
    }

    @Keep
    public void endAdUnitExposure(String string2) {
        this.zziwf.zzawk().endAdUnitExposure(string2);
    }

    @Keep
    public long generateEventId() {
        return this.zziwf.zzawu().zzbay();
    }

    @Keep
    public String getAppInstanceId() {
        return this.zziwf.zzawm().zzazn();
    }

    @Keep
    protected List<ConditionalUserProperty> getConditionalUserProperties(String string2, String string3) {
        return this.zziwf.zzawm().getConditionalUserProperties(string2, string3);
    }

    @Keep
    protected List<ConditionalUserProperty> getConditionalUserPropertiesAs(String string2, String string3, String string4) {
        return this.zziwf.zzawm().getConditionalUserPropertiesAs(string2, string3, string4);
    }

    @Keep
    public String getCurrentScreenClass() {
        zzb zzb2 = this.zziwf.zzawq().zzbap();
        if (zzb2 != null) {
            return zzb2.zziwl;
        }
        return null;
    }

    @Keep
    public String getCurrentScreenName() {
        zzb zzb2 = this.zziwf.zzawq().zzbap();
        if (zzb2 != null) {
            return zzb2.zziwk;
        }
        return null;
    }

    @Keep
    public String getGmpAppId() {
        try {
            String string2 = zzbz.zzajh();
            return string2;
        }
        catch (IllegalStateException illegalStateException) {
            this.zziwf.zzawy().zzazd().zzj("getGoogleAppId failed with exception", illegalStateException);
            return null;
        }
    }

    @Keep
    protected int getMaxUserProperties(String string2) {
        this.zziwf.zzawm();
        zzbq.zzgm(string2);
        return 25;
    }

    @Keep
    protected Map<String, Object> getUserProperties(String string2, String string3, boolean bl) {
        return this.zziwf.zzawm().getUserProperties(string2, string3, bl);
    }

    public Map<String, Object> getUserProperties(boolean bl) {
        Object object = this.zziwf.zzawm().zzbq(bl);
        ArrayMap<String, Object> arrayMap = new ArrayMap<String, Object>(object.size());
        object = object.iterator();
        while (object.hasNext()) {
            zzcln zzcln2 = (zzcln)object.next();
            arrayMap.put(zzcln2.name, zzcln2.getValue());
        }
        return arrayMap;
    }

    @Keep
    protected Map<String, Object> getUserPropertiesAs(String string2, String string3, String string4, boolean bl) {
        return this.zziwf.zzawm().getUserPropertiesAs(string2, string3, string4, bl);
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void logEvent(String string2, Bundle object) {
        int n;
        Object object2 = object;
        if (object == null) {
            object2 = new Bundle();
        }
        if (!"_iap".equals(string2) && (n = this.zziwf.zzawu().zzka(string2)) != 0) {
            this.zziwf.zzawu();
            String string3 = zzclq.zza(string2, 40, true);
            int n2 = string2 != null ? string2.length() : 0;
            this.zziwf.zzawu().zza(n, "_ev", string3, n2);
            return;
        }
        this.zziwf.zzawm().zza("app", string2, (Bundle)object2, true);
    }

    @Keep
    public void logEventInternal(String string2, String string3, Bundle bundle) {
        Bundle bundle2 = bundle;
        if (bundle == null) {
            bundle2 = new Bundle();
        }
        this.zziwf.zzawm().zzc(string2, string3, bundle2);
    }

    public void logEventInternalNoInterceptor(String string2, String string3, Bundle bundle, long l) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        this.zziwf.zzawm().zza(string2, string3, bundle, l);
    }

    public void registerOnMeasurementEventListener(OnEventListener onEventListener) {
        this.zziwf.zzawm().registerOnMeasurementEventListener(onEventListener);
    }

    @Keep
    public void registerOnScreenChangeCallback(zza zza2) {
        this.zziwf.zzawq().registerOnScreenChangeCallback(zza2);
    }

    @Keep
    protected void setConditionalUserProperty(ConditionalUserProperty conditionalUserProperty) {
        this.zziwf.zzawm().setConditionalUserProperty(conditionalUserProperty);
    }

    @Keep
    protected void setConditionalUserPropertyAs(ConditionalUserProperty conditionalUserProperty) {
        this.zziwf.zzawm().setConditionalUserPropertyAs(conditionalUserProperty);
    }

    public void setEventInterceptor(EventInterceptor eventInterceptor) {
        this.zziwf.zzawm().setEventInterceptor(eventInterceptor);
    }

    @Deprecated
    public void setMeasurementEnabled(boolean bl) {
        this.zziwf.zzawm().setMeasurementEnabled(bl);
    }

    public final void setMinimumSessionDuration(long l) {
        this.zziwf.zzawm().setMinimumSessionDuration(l);
    }

    public final void setSessionTimeoutDuration(long l) {
        this.zziwf.zzawm().setSessionTimeoutDuration(l);
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void setUserProperty(String string2, String string3) {
        int n = this.zziwf.zzawu().zzkc(string2);
        if (n == 0) {
            this.setUserPropertyInternal("app", string2, string3);
            return;
        }
        this.zziwf.zzawu();
        string3 = zzclq.zza(string2, 24, true);
        int n2 = string2 != null ? string2.length() : 0;
        this.zziwf.zzawu().zza(n, "_ev", string3, n2);
    }

    public void setUserPropertyInternal(String string2, String string3, Object object) {
        this.zziwf.zzawm().zzb(string2, string3, object);
    }

    public void unregisterOnMeasurementEventListener(OnEventListener onEventListener) {
        this.zziwf.zzawm().unregisterOnMeasurementEventListener(onEventListener);
    }

    @Keep
    public void unregisterOnScreenChangeCallback(zza zza2) {
        this.zziwf.zzawq().unregisterOnScreenChangeCallback(zza2);
    }

    public static class ConditionalUserProperty {
        @Keep
        public boolean mActive;
        @Keep
        public String mAppId;
        @Keep
        public long mCreationTimestamp;
        @Keep
        public String mExpiredEventName;
        @Keep
        public Bundle mExpiredEventParams;
        @Keep
        public String mName;
        @Keep
        public String mOrigin;
        @Keep
        public long mTimeToLive;
        @Keep
        public String mTimedOutEventName;
        @Keep
        public Bundle mTimedOutEventParams;
        @Keep
        public String mTriggerEventName;
        @Keep
        public long mTriggerTimeout;
        @Keep
        public String mTriggeredEventName;
        @Keep
        public Bundle mTriggeredEventParams;
        @Keep
        public long mTriggeredTimestamp;
        @Keep
        public Object mValue;

        public ConditionalUserProperty() {
        }

        public ConditionalUserProperty(ConditionalUserProperty conditionalUserProperty) {
            zzbq.checkNotNull(conditionalUserProperty);
            this.mAppId = conditionalUserProperty.mAppId;
            this.mOrigin = conditionalUserProperty.mOrigin;
            this.mCreationTimestamp = conditionalUserProperty.mCreationTimestamp;
            this.mName = conditionalUserProperty.mName;
            if (conditionalUserProperty.mValue != null) {
                this.mValue = zzclq.zzag(conditionalUserProperty.mValue);
                if (this.mValue == null) {
                    this.mValue = conditionalUserProperty.mValue;
                }
            }
            this.mValue = conditionalUserProperty.mValue;
            this.mActive = conditionalUserProperty.mActive;
            this.mTriggerEventName = conditionalUserProperty.mTriggerEventName;
            this.mTriggerTimeout = conditionalUserProperty.mTriggerTimeout;
            this.mTimedOutEventName = conditionalUserProperty.mTimedOutEventName;
            if (conditionalUserProperty.mTimedOutEventParams != null) {
                this.mTimedOutEventParams = new Bundle(conditionalUserProperty.mTimedOutEventParams);
            }
            this.mTriggeredEventName = conditionalUserProperty.mTriggeredEventName;
            if (conditionalUserProperty.mTriggeredEventParams != null) {
                this.mTriggeredEventParams = new Bundle(conditionalUserProperty.mTriggeredEventParams);
            }
            this.mTriggeredTimestamp = conditionalUserProperty.mTriggeredTimestamp;
            this.mTimeToLive = conditionalUserProperty.mTimeToLive;
            this.mExpiredEventName = conditionalUserProperty.mExpiredEventName;
            if (conditionalUserProperty.mExpiredEventParams != null) {
                this.mExpiredEventParams = new Bundle(conditionalUserProperty.mExpiredEventParams);
            }
        }
    }

    public static final class Event
    extends FirebaseAnalytics.Event {
        public static final String[] zziwg = new String[]{"app_clear_data", "app_exception", "app_remove", "app_upgrade", "app_install", "app_update", "firebase_campaign", "error", "first_open", "first_visit", "in_app_purchase", "notification_dismiss", "notification_foreground", "notification_open", "notification_receive", "os_update", "session_start", "user_engagement", "ad_exposure", "adunit_exposure", "ad_query", "ad_activeview", "ad_impression", "ad_click", "screen_view", "firebase_extra_parameter"};
        public static final String[] zziwh = new String[]{"_cd", "_ae", "_ui", "_ug", "_in", "_au", "_cmp", "_err", "_f", "_v", "_iap", "_nd", "_nf", "_no", "_nr", "_ou", "_s", "_e", "_xa", "_xu", "_aq", "_aa", "_ai", "_ac", "_vs", "_ep"};

        public static String zziq(String string2) {
            return zzclq.zza(string2, zziwg, zziwh);
        }
    }

    public static interface EventInterceptor {
        public void interceptEvent(String var1, String var2, Bundle var3, long var4);
    }

    public static interface OnEventListener {
        public void onEvent(String var1, String var2, Bundle var3, long var4);
    }

    public static final class Param
    extends FirebaseAnalytics.Param {
        public static final String[] zziwi = new String[]{"firebase_conversion", "engagement_time_msec", "exposure_time", "ad_event_id", "ad_unit_id", "firebase_error", "firebase_error_value", "firebase_error_length", "firebase_event_origin", "firebase_screen", "firebase_screen_class", "firebase_screen_id", "firebase_previous_screen", "firebase_previous_class", "firebase_previous_id", "message_device_time", "message_id", "message_name", "message_time", "previous_app_version", "previous_os_version", "topic", "update_with_analytics", "previous_first_open_count", "system_app", "system_app_update", "previous_install_count", "firebase_event_id", "firebase_extra_params_ct", "firebase_group_name", "firebase_list_length", "firebase_index", "firebase_event_name"};
        public static final String[] zziwj = new String[]{"_c", "_et", "_xt", "_aeid", "_ai", "_err", "_ev", "_el", "_o", "_sn", "_sc", "_si", "_pn", "_pc", "_pi", "_ndt", "_nmid", "_nmn", "_nmt", "_pv", "_po", "_nt", "_uwa", "_pfo", "_sys", "_sysu", "_pin", "_eid", "_epc", "_gn", "_ll", "_i", "_en"};

        public static String zziq(String string2) {
            return zzclq.zza(string2, zziwi, zziwj);
        }
    }

    public static final class UserProperty
    extends FirebaseAnalytics.UserProperty {
        public static final String[] zziwn = new String[]{"firebase_last_notification", "first_open_time", "first_visit_time", "last_deep_link_referrer", "user_id", "first_open_after_install"};
        public static final String[] zziwo = new String[]{"_ln", "_fot", "_fvt", "_ldl", "_id", "_fi"};

        public static String zziq(String string2) {
            return zzclq.zza(string2, zziwn, zziwo);
        }
    }

    public static interface zza {
        public boolean zza(zzb var1, zzb var2);
    }

    public static class zzb {
        public String zziwk;
        public String zziwl;
        public long zziwm;

        public zzb() {
        }

        public zzb(zzb zzb2) {
            this.zziwk = zzb2.zziwk;
            this.zziwl = zzb2.zziwl;
            this.zziwm = zzb2.zziwm;
        }
    }

}

