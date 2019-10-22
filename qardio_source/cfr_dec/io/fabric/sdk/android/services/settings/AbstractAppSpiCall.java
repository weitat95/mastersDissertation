/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.Resources$NotFoundException
 */
package io.fabric.sdk.android.services.settings;

import android.content.Context;
import android.content.res.Resources;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.KitInfo;
import io.fabric.sdk.android.services.common.AbstractSpiCall;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.ResponseParser;
import io.fabric.sdk.android.services.network.HttpMethod;
import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import io.fabric.sdk.android.services.settings.AppRequestData;
import io.fabric.sdk.android.services.settings.IconRequest;
import java.io.Closeable;
import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;

abstract class AbstractAppSpiCall
extends AbstractSpiCall {
    public AbstractAppSpiCall(Kit kit, String string2, String string3, HttpRequestFactory httpRequestFactory, HttpMethod httpMethod) {
        super(kit, string2, string3, httpRequestFactory, httpMethod);
    }

    private HttpRequest applyHeadersTo(HttpRequest httpRequest, AppRequestData appRequestData) {
        return httpRequest.header("X-CRASHLYTICS-API-KEY", appRequestData.apiKey).header("X-CRASHLYTICS-API-CLIENT-TYPE", "android").header("X-CRASHLYTICS-API-CLIENT-VERSION", this.kit.getVersion());
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private HttpRequest applyMultipartDataTo(HttpRequest var1_1, AppRequestData var2_3) {
        var5_6 = var1_1.part("app[identifier]", var2_3.appId).part("app[name]", var2_3.name).part("app[display_version]", var2_3.displayVersion).part("app[build_version]", var2_3.buildVersion).part("app[source]", var2_3.source).part("app[minimum_sdk_version]", var2_3.minSdkVersion).part("app[built_sdk_version]", var2_3.builtSdkVersion);
        if (!CommonUtils.isNullOrEmpty(var2_3.instanceIdentifier)) {
            var5_6.part("app[instance_identifier]", var2_3.instanceIdentifier);
        }
        if (var2_3.icon == null) ** GOTO lbl21
        var3_7 = null;
        var1_1 = null;
        try {
            var4_8 = this.kit.getContext().getResources().openRawResource(var2_3.icon.iconResourceId);
            var1_1 = var4_8;
            var3_7 = var4_8;
            var5_6.part("app[icon][hash]", var2_3.icon.hash).part("app[icon][data]", "icon.png", "application/octet-stream", var4_8).part("app[icon][width]", var2_3.icon.width).part("app[icon][height]", var2_3.icon.height);
        }
        catch (Resources.NotFoundException var4_9) {
            var3_7 = var1_1;
            try {
                Fabric.getLogger().e("Fabric", "Failed to find app icon with resource ID: " + var2_3.icon.iconResourceId, var4_9);
            }
            catch (Throwable var1_2) {
                CommonUtils.closeOrLog(var3_7, "Failed to close app icon InputStream.");
                throw var1_2;
            }
            CommonUtils.closeOrLog((Closeable)var1_1, "Failed to close app icon InputStream.");
lbl21:
            // 3 sources
            if (var2_3.sdkKits == null) return var5_6;
            var1_1 = var2_3.sdkKits.iterator();
            while (var1_1.hasNext() != false) {
                var2_5 = var1_1.next();
                var5_6.part(this.getKitVersionKey(var2_5), var2_5.getVersion());
                var5_6.part(this.getKitBuildTypeKey(var2_5), var2_5.getBuildType());
            }
            return var5_6;
        }
        CommonUtils.closeOrLog(var4_8, "Failed to close app icon InputStream.");
        ** GOTO lbl21
    }

    String getKitBuildTypeKey(KitInfo kitInfo) {
        return String.format(Locale.US, "app[build][libraries][%s][type]", kitInfo.getIdentifier());
    }

    String getKitVersionKey(KitInfo kitInfo) {
        return String.format(Locale.US, "app[build][libraries][%s][version]", kitInfo.getIdentifier());
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean invoke(AppRequestData object) {
        HttpRequest httpRequest = this.applyMultipartDataTo(this.applyHeadersTo(this.getHttpRequest(), (AppRequestData)object), (AppRequestData)object);
        Fabric.getLogger().d("Fabric", "Sending app info to " + this.getUrl());
        if (((AppRequestData)object).icon != null) {
            Fabric.getLogger().d("Fabric", "App icon hash is " + object.icon.hash);
            Fabric.getLogger().d("Fabric", "App icon size is " + object.icon.width + "x" + object.icon.height);
        }
        int n = httpRequest.code();
        object = "POST".equals(httpRequest.method()) ? "Create" : "Update";
        Fabric.getLogger().d("Fabric", (String)object + " app request ID: " + httpRequest.header("X-REQUEST-ID"));
        Fabric.getLogger().d("Fabric", "Result was " + n);
        return ResponseParser.parse(n) == 0;
    }
}

