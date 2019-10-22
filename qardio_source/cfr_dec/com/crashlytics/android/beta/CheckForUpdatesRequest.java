/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.crashlytics.android.beta;

import com.crashlytics.android.beta.BuildProperties;
import com.crashlytics.android.beta.CheckForUpdatesResponse;
import com.crashlytics.android.beta.CheckForUpdatesResponseTransform;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.services.common.AbstractSpiCall;
import io.fabric.sdk.android.services.network.HttpMethod;
import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

class CheckForUpdatesRequest
extends AbstractSpiCall {
    private final CheckForUpdatesResponseTransform responseTransform;

    public CheckForUpdatesRequest(Kit kit, String string2, String string3, HttpRequestFactory httpRequestFactory, CheckForUpdatesResponseTransform checkForUpdatesResponseTransform) {
        super(kit, string2, string3, httpRequestFactory, HttpMethod.GET);
        this.responseTransform = checkForUpdatesResponseTransform;
    }

    private HttpRequest applyHeadersTo(HttpRequest httpRequest, String string2, String string3) {
        return httpRequest.header("Accept", "application/json").header("User-Agent", "Crashlytics Android SDK/" + this.kit.getVersion()).header("X-CRASHLYTICS-DEVELOPER-TOKEN", "470fa2b4ae81cd56ecbcda9735803434cec591fa").header("X-CRASHLYTICS-API-CLIENT-TYPE", "android").header("X-CRASHLYTICS-API-CLIENT-VERSION", this.kit.getVersion()).header("X-CRASHLYTICS-API-KEY", string2).header("X-CRASHLYTICS-BETA-TOKEN", CheckForUpdatesRequest.createBetaTokenHeaderValueFor(string3));
    }

    static String createBetaTokenHeaderValueFor(String string2) {
        return "3:" + string2;
    }

    private Map<String, String> getQueryParamsFor(BuildProperties buildProperties) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("build_version", buildProperties.versionCode);
        hashMap.put("display_version", buildProperties.versionName);
        hashMap.put("instance", buildProperties.buildId);
        hashMap.put("source", "3");
        return hashMap;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public CheckForUpdatesResponse invoke(String object, String object2, BuildProperties object3) {
        Object object4;
        Object object5;
        block5: {
            HttpRequest httpRequest = null;
            HttpRequest httpRequest2 = null;
            object5 = httpRequest2;
            object4 = httpRequest;
            Map<String, String> map = this.getQueryParamsFor((BuildProperties)object3);
            object5 = httpRequest2;
            object4 = httpRequest;
            object5 = object3 = this.getHttpRequest(map);
            object4 = object3;
            object5 = object = this.applyHeadersTo((HttpRequest)object3, (String)object, (String)object2);
            object4 = object;
            Fabric.getLogger().d("Beta", "Checking for updates from " + this.getUrl());
            object5 = object;
            object4 = object;
            Fabric.getLogger().d("Beta", "Checking for updates query params are: " + map);
            object5 = object;
            object4 = object;
            if (!((HttpRequest)object).ok()) break block5;
            object5 = object;
            object4 = object;
            Fabric.getLogger().d("Beta", "Checking for updates was successful");
            object5 = object;
            object4 = object;
            object2 = new JSONObject(((HttpRequest)object).body());
            object5 = object;
            object4 = object;
            object2 = this.responseTransform.fromJson((JSONObject)object2);
            if (object == null) return object2;
            object = ((HttpRequest)object).header("X-REQUEST-ID");
            Fabric.getLogger().d("Fabric", "Checking for updates request ID: " + (String)object);
            return object2;
        }
        object5 = object;
        object4 = object;
        try {
            Fabric.getLogger().e("Beta", "Checking for updates failed. Response code: " + ((HttpRequest)object).code());
            if (object == null) return null;
        }
        catch (Exception exception) {
            object4 = object5;
            try {
                Fabric.getLogger().e("Beta", "Error while checking for updates from " + this.getUrl(), exception);
                if (object5 == null) return null;
            }
            catch (Throwable throwable) {
                if (object4 == null) throw throwable;
                object2 = ((HttpRequest)object4).header("X-REQUEST-ID");
                Fabric.getLogger().d("Fabric", "Checking for updates request ID: " + (String)object2);
                throw throwable;
            }
            String string2 = ((HttpRequest)object5).header("X-REQUEST-ID");
            Fabric.getLogger().d("Fabric", "Checking for updates request ID: " + string2);
            return null;
        }
        object = ((HttpRequest)object).header("X-REQUEST-ID");
        Fabric.getLogger().d("Fabric", "Checking for updates request ID: " + (String)object);
        return null;
    }
}

