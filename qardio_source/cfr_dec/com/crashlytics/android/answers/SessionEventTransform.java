/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.os.Build
 *  android.os.Build$VERSION
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.crashlytics.android.answers;

import android.annotation.TargetApi;
import android.os.Build;
import com.crashlytics.android.answers.SessionEvent;
import com.crashlytics.android.answers.SessionEventMetadata;
import io.fabric.sdk.android.services.events.EventTransform;
import java.io.IOException;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

class SessionEventTransform
implements EventTransform<SessionEvent> {
    SessionEventTransform() {
    }

    @TargetApi(value=9)
    public JSONObject buildJsonForEvent(SessionEvent sessionEvent) throws IOException {
        try {
            JSONObject jSONObject = new JSONObject();
            SessionEventMetadata sessionEventMetadata = sessionEvent.sessionEventMetadata;
            jSONObject.put("appBundleId", (Object)sessionEventMetadata.appBundleId);
            jSONObject.put("executionId", (Object)sessionEventMetadata.executionId);
            jSONObject.put("installationId", (Object)sessionEventMetadata.installationId);
            jSONObject.put("androidId", (Object)sessionEventMetadata.androidId);
            jSONObject.put("advertisingId", (Object)sessionEventMetadata.advertisingId);
            jSONObject.put("limitAdTrackingEnabled", (Object)sessionEventMetadata.limitAdTrackingEnabled);
            jSONObject.put("betaDeviceToken", (Object)sessionEventMetadata.betaDeviceToken);
            jSONObject.put("buildId", (Object)sessionEventMetadata.buildId);
            jSONObject.put("osVersion", (Object)sessionEventMetadata.osVersion);
            jSONObject.put("deviceModel", (Object)sessionEventMetadata.deviceModel);
            jSONObject.put("appVersionCode", (Object)sessionEventMetadata.appVersionCode);
            jSONObject.put("appVersionName", (Object)sessionEventMetadata.appVersionName);
            jSONObject.put("timestamp", sessionEvent.timestamp);
            jSONObject.put("type", (Object)sessionEvent.type.toString());
            if (sessionEvent.details != null) {
                jSONObject.put("details", (Object)new JSONObject(sessionEvent.details));
            }
            jSONObject.put("customType", (Object)sessionEvent.customType);
            if (sessionEvent.customAttributes != null) {
                jSONObject.put("customAttributes", (Object)new JSONObject(sessionEvent.customAttributes));
            }
            jSONObject.put("predefinedType", (Object)sessionEvent.predefinedType);
            if (sessionEvent.predefinedAttributes != null) {
                jSONObject.put("predefinedAttributes", (Object)new JSONObject(sessionEvent.predefinedAttributes));
            }
            return jSONObject;
        }
        catch (JSONException jSONException) {
            if (Build.VERSION.SDK_INT >= 9) {
                throw new IOException(jSONException.getMessage(), jSONException);
            }
            throw new IOException(jSONException.getMessage());
        }
    }

    @Override
    public byte[] toBytes(SessionEvent sessionEvent) throws IOException {
        return this.buildJsonForEvent(sessionEvent).toString().getBytes("UTF-8");
    }
}

