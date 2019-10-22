/*
 * Decompiled with CFR 0.147.
 */
package com.crashlytics.android.answers;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.services.common.AbstractSpiCall;
import io.fabric.sdk.android.services.common.ResponseParser;
import io.fabric.sdk.android.services.events.FilesSender;
import io.fabric.sdk.android.services.network.HttpMethod;
import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import java.io.File;
import java.util.List;

class SessionAnalyticsFilesSender
extends AbstractSpiCall
implements FilesSender {
    private final String apiKey;

    public SessionAnalyticsFilesSender(Kit kit, String string2, String string3, HttpRequestFactory httpRequestFactory, String string4) {
        super(kit, string2, string3, httpRequestFactory, HttpMethod.POST);
        this.apiKey = string4;
    }

    @Override
    public boolean send(List<File> list) {
        HttpRequest httpRequest = this.getHttpRequest().header("X-CRASHLYTICS-API-CLIENT-TYPE", "android").header("X-CRASHLYTICS-API-CLIENT-VERSION", this.kit.getVersion()).header("X-CRASHLYTICS-API-KEY", this.apiKey);
        int n = 0;
        for (File file : list) {
            httpRequest.part("session_analytics_file_" + n, file.getName(), "application/vnd.crashlytics.android.events", file);
            ++n;
        }
        Fabric.getLogger().d("Answers", "Sending " + list.size() + " analytics files to " + this.getUrl());
        n = httpRequest.code();
        Fabric.getLogger().d("Answers", "Response code for analytics file send is " + n);
        return ResponseParser.parse(n) == 0;
    }
}

