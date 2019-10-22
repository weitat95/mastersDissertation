/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package io.fabric.sdk.android.services.settings;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.services.common.AbstractSpiCall;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.network.HttpMethod;
import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import io.fabric.sdk.android.services.settings.SettingsRequest;
import io.fabric.sdk.android.services.settings.SettingsSpiCall;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

class DefaultSettingsSpiCall
extends AbstractSpiCall
implements SettingsSpiCall {
    public DefaultSettingsSpiCall(Kit kit, String string2, String string3, HttpRequestFactory httpRequestFactory) {
        this(kit, string2, string3, httpRequestFactory, HttpMethod.GET);
    }

    DefaultSettingsSpiCall(Kit kit, String string2, String string3, HttpRequestFactory httpRequestFactory, HttpMethod httpMethod) {
        super(kit, string2, string3, httpRequestFactory, httpMethod);
    }

    private HttpRequest applyHeadersTo(HttpRequest httpRequest, SettingsRequest settingsRequest) {
        this.applyNonNullHeader(httpRequest, "X-CRASHLYTICS-API-KEY", settingsRequest.apiKey);
        this.applyNonNullHeader(httpRequest, "X-CRASHLYTICS-API-CLIENT-TYPE", "android");
        this.applyNonNullHeader(httpRequest, "X-CRASHLYTICS-API-CLIENT-VERSION", this.kit.getVersion());
        this.applyNonNullHeader(httpRequest, "Accept", "application/json");
        this.applyNonNullHeader(httpRequest, "X-CRASHLYTICS-DEVICE-MODEL", settingsRequest.deviceModel);
        this.applyNonNullHeader(httpRequest, "X-CRASHLYTICS-OS-BUILD-VERSION", settingsRequest.osBuildVersion);
        this.applyNonNullHeader(httpRequest, "X-CRASHLYTICS-OS-DISPLAY-VERSION", settingsRequest.osDisplayVersion);
        this.applyNonNullHeader(httpRequest, "X-CRASHLYTICS-ADVERTISING-TOKEN", settingsRequest.advertisingId);
        this.applyNonNullHeader(httpRequest, "X-CRASHLYTICS-INSTALLATION-ID", settingsRequest.installationId);
        this.applyNonNullHeader(httpRequest, "X-CRASHLYTICS-ANDROID-ID", settingsRequest.androidId);
        return httpRequest;
    }

    private void applyNonNullHeader(HttpRequest httpRequest, String string2, String string3) {
        if (string3 != null) {
            httpRequest.header(string2, string3);
        }
    }

    private JSONObject getJsonObjectFrom(String string2) {
        try {
            JSONObject jSONObject = new JSONObject(string2);
            return jSONObject;
        }
        catch (Exception exception) {
            Fabric.getLogger().d("Fabric", "Failed to parse settings JSON from " + this.getUrl(), exception);
            Fabric.getLogger().d("Fabric", "Settings response " + string2);
            return null;
        }
    }

    private Map<String, String> getQueryParamsFor(SettingsRequest object) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("build_version", ((SettingsRequest)object).buildVersion);
        hashMap.put("display_version", ((SettingsRequest)object).displayVersion);
        hashMap.put("source", Integer.toString(((SettingsRequest)object).source));
        if (((SettingsRequest)object).iconHash != null) {
            hashMap.put("icon_hash", ((SettingsRequest)object).iconHash);
        }
        if (!CommonUtils.isNullOrEmpty((String)(object = ((SettingsRequest)object).instanceId))) {
            hashMap.put("instance", (String)object);
        }
        return hashMap;
    }

    JSONObject handleResponse(HttpRequest httpRequest) {
        int n = httpRequest.code();
        Fabric.getLogger().d("Fabric", "Settings result was: " + n);
        if (this.requestWasSuccessful(n)) {
            return this.getJsonObjectFrom(httpRequest.body());
        }
        Fabric.getLogger().e("Fabric", "Failed to retrieve settings from " + this.getUrl());
        return null;
    }

    @Override
    public JSONObject invoke(SettingsRequest object) {
        Map<String, String> map;
        HttpRequest httpRequest = null;
        Object object2 = httpRequest;
        try {
            map = this.getQueryParamsFor((SettingsRequest)object);
            object2 = httpRequest;
        }
        catch (Throwable throwable) {
            if (object2 != null) {
                Fabric.getLogger().d("Fabric", "Settings request ID: " + ((HttpRequest)object2).header("X-REQUEST-ID"));
            }
            throw throwable;
        }
        httpRequest = this.getHttpRequest(map);
        object2 = httpRequest;
        object2 = object = this.applyHeadersTo(httpRequest, (SettingsRequest)object);
        Fabric.getLogger().d("Fabric", "Requesting settings from " + this.getUrl());
        object2 = object;
        Fabric.getLogger().d("Fabric", "Settings query params were: " + map);
        object2 = object;
        httpRequest = this.handleResponse((HttpRequest)object);
        if (object != null) {
            Fabric.getLogger().d("Fabric", "Settings request ID: " + ((HttpRequest)object).header("X-REQUEST-ID"));
        }
        return httpRequest;
    }

    boolean requestWasSuccessful(int n) {
        return n == 200 || n == 201 || n == 202 || n == 203;
    }
}

