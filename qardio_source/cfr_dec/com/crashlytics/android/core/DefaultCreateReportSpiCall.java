/*
 * Decompiled with CFR 0.147.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.CreateReportRequest;
import com.crashlytics.android.core.CreateReportSpiCall;
import com.crashlytics.android.core.Report;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.services.common.AbstractSpiCall;
import io.fabric.sdk.android.services.common.ResponseParser;
import io.fabric.sdk.android.services.network.HttpMethod;
import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

class DefaultCreateReportSpiCall
extends AbstractSpiCall
implements CreateReportSpiCall {
    public DefaultCreateReportSpiCall(Kit kit, String string2, String string3, HttpRequestFactory httpRequestFactory) {
        super(kit, string2, string3, httpRequestFactory, HttpMethod.POST);
    }

    private HttpRequest applyHeadersTo(HttpRequest httpRequest, CreateReportRequest object) {
        httpRequest = httpRequest.header("X-CRASHLYTICS-API-KEY", ((CreateReportRequest)object).apiKey).header("X-CRASHLYTICS-API-CLIENT-TYPE", "android").header("X-CRASHLYTICS-API-CLIENT-VERSION", this.kit.getVersion());
        object = ((CreateReportRequest)object).report.getCustomHeaders().entrySet().iterator();
        while (object.hasNext()) {
            httpRequest = httpRequest.header((Map.Entry)object.next());
        }
        return httpRequest;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private HttpRequest applyMultipartDataTo(HttpRequest httpRequest, Report report) {
        httpRequest.part("report[identifier]", report.getIdentifier());
        if (report.getFiles().length == 1) {
            Fabric.getLogger().d("CrashlyticsCore", "Adding single file " + report.getFileName() + " to report " + report.getIdentifier());
            return httpRequest.part("report[file]", report.getFileName(), "application/octet-stream", report.getFile());
        }
        int n = 0;
        File[] arrfile = report.getFiles();
        int n2 = arrfile.length;
        int n3 = 0;
        do {
            Object object = httpRequest;
            if (n3 >= n2) return object;
            object = arrfile[n3];
            Fabric.getLogger().d("CrashlyticsCore", "Adding file " + ((File)object).getName() + " to report " + report.getIdentifier());
            httpRequest.part("report[file" + n + "]", ((File)object).getName(), "application/octet-stream", (File)object);
            ++n;
            ++n3;
        } while (true);
    }

    @Override
    public boolean invoke(CreateReportRequest object) {
        object = this.applyMultipartDataTo(this.applyHeadersTo(this.getHttpRequest(), (CreateReportRequest)object), ((CreateReportRequest)object).report);
        Fabric.getLogger().d("CrashlyticsCore", "Sending report to: " + this.getUrl());
        int n = ((HttpRequest)object).code();
        Fabric.getLogger().d("CrashlyticsCore", "Create report request ID: " + ((HttpRequest)object).header("X-REQUEST-ID"));
        Fabric.getLogger().d("CrashlyticsCore", "Result was: " + n);
        return ResponseParser.parse(n) == 0;
    }
}

