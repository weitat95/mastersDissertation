/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package io.fabric.sdk.android.services.settings;

import io.fabric.sdk.android.services.common.CurrentTimeProvider;
import io.fabric.sdk.android.services.settings.SettingsData;
import org.json.JSONException;
import org.json.JSONObject;

public interface SettingsJsonTransform {
    public SettingsData buildFromJson(CurrentTimeProvider var1, JSONObject var2) throws JSONException;
}

