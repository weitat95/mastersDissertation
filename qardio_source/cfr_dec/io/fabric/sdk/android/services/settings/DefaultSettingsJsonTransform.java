/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package io.fabric.sdk.android.services.settings;

import io.fabric.sdk.android.services.common.CurrentTimeProvider;
import io.fabric.sdk.android.services.settings.AnalyticsSettingsData;
import io.fabric.sdk.android.services.settings.AppIconSettingsData;
import io.fabric.sdk.android.services.settings.AppSettingsData;
import io.fabric.sdk.android.services.settings.BetaSettingsData;
import io.fabric.sdk.android.services.settings.FeaturesSettingsData;
import io.fabric.sdk.android.services.settings.PromptSettingsData;
import io.fabric.sdk.android.services.settings.SessionSettingsData;
import io.fabric.sdk.android.services.settings.SettingsData;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import io.fabric.sdk.android.services.settings.SettingsJsonTransform;
import org.json.JSONException;
import org.json.JSONObject;

class DefaultSettingsJsonTransform
implements SettingsJsonTransform {
    DefaultSettingsJsonTransform() {
    }

    private AnalyticsSettingsData buildAnalyticsSessionDataFrom(JSONObject jSONObject) {
        return new AnalyticsSettingsData(jSONObject.optString("url", "https://e.crashlytics.com/spi/v2/events"), jSONObject.optInt("flush_interval_secs", 600), jSONObject.optInt("max_byte_size_per_file", 8000), jSONObject.optInt("max_file_count_per_send", 1), jSONObject.optInt("max_pending_send_file_count", 100), jSONObject.optBoolean("track_custom_events", true), jSONObject.optBoolean("track_predefined_events", true), jSONObject.optInt("sampling_rate", 1), jSONObject.optBoolean("flush_on_background", true));
    }

    private AppSettingsData buildAppDataFrom(JSONObject jSONObject) throws JSONException {
        AppIconSettingsData appIconSettingsData;
        String string2 = jSONObject.getString("identifier");
        String string3 = jSONObject.getString("status");
        String string4 = jSONObject.getString("url");
        String string5 = jSONObject.getString("reports_url");
        boolean bl = jSONObject.optBoolean("update_required", false);
        AppIconSettingsData appIconSettingsData2 = appIconSettingsData = null;
        if (jSONObject.has("icon")) {
            appIconSettingsData2 = appIconSettingsData;
            if (jSONObject.getJSONObject("icon").has("hash")) {
                appIconSettingsData2 = this.buildIconDataFrom(jSONObject.getJSONObject("icon"));
            }
        }
        return new AppSettingsData(string2, string3, string4, string5, bl, appIconSettingsData2);
    }

    private BetaSettingsData buildBetaSettingsDataFrom(JSONObject jSONObject) throws JSONException {
        return new BetaSettingsData(jSONObject.optString("update_endpoint", SettingsJsonConstants.BETA_UPDATE_ENDPOINT_DEFAULT), jSONObject.optInt("update_suspend_duration", 3600));
    }

    private FeaturesSettingsData buildFeaturesSessionDataFrom(JSONObject jSONObject) {
        return new FeaturesSettingsData(jSONObject.optBoolean("prompt_enabled", false), jSONObject.optBoolean("collect_logged_exceptions", true), jSONObject.optBoolean("collect_reports", true), jSONObject.optBoolean("collect_analytics", false));
    }

    private AppIconSettingsData buildIconDataFrom(JSONObject jSONObject) throws JSONException {
        return new AppIconSettingsData(jSONObject.getString("hash"), jSONObject.getInt("width"), jSONObject.getInt("height"));
    }

    private PromptSettingsData buildPromptDataFrom(JSONObject jSONObject) throws JSONException {
        return new PromptSettingsData(jSONObject.optString("title", "Send Crash Report?"), jSONObject.optString("message", "Looks like we crashed! Please help us fix the problem by sending a crash report."), jSONObject.optString("send_button_title", "Send"), jSONObject.optBoolean("show_cancel_button", true), jSONObject.optString("cancel_button_title", "Don't Send"), jSONObject.optBoolean("show_always_send_button", true), jSONObject.optString("always_send_button_title", "Always Send"));
    }

    private SessionSettingsData buildSessionDataFrom(JSONObject jSONObject) throws JSONException {
        return new SessionSettingsData(jSONObject.optInt("log_buffer_size", 64000), jSONObject.optInt("max_chained_exception_depth", 8), jSONObject.optInt("max_custom_exception_events", 64), jSONObject.optInt("max_custom_key_value_pairs", 64), jSONObject.optInt("identifier_mask", 255), jSONObject.optBoolean("send_session_without_crash", false));
    }

    private long getExpiresAtFrom(CurrentTimeProvider currentTimeProvider, long l, JSONObject jSONObject) throws JSONException {
        if (jSONObject.has("expires_at")) {
            return jSONObject.getLong("expires_at");
        }
        return currentTimeProvider.getCurrentTimeMillis() + 1000L * l;
    }

    @Override
    public SettingsData buildFromJson(CurrentTimeProvider currentTimeProvider, JSONObject jSONObject) throws JSONException {
        int n = jSONObject.optInt("settings_version", 0);
        int n2 = jSONObject.optInt("cache_duration", 3600);
        AppSettingsData appSettingsData = this.buildAppDataFrom(jSONObject.getJSONObject("app"));
        SessionSettingsData sessionSettingsData = this.buildSessionDataFrom(jSONObject.getJSONObject("session"));
        PromptSettingsData promptSettingsData = this.buildPromptDataFrom(jSONObject.getJSONObject("prompt"));
        FeaturesSettingsData featuresSettingsData = this.buildFeaturesSessionDataFrom(jSONObject.getJSONObject("features"));
        AnalyticsSettingsData analyticsSettingsData = this.buildAnalyticsSessionDataFrom(jSONObject.getJSONObject("analytics"));
        BetaSettingsData betaSettingsData = this.buildBetaSettingsDataFrom(jSONObject.getJSONObject("beta"));
        return new SettingsData(this.getExpiresAtFrom(currentTimeProvider, n2, jSONObject), appSettingsData, sessionSettingsData, promptSettingsData, featuresSettingsData, analyticsSettingsData, betaSettingsData, n, n2);
    }
}

