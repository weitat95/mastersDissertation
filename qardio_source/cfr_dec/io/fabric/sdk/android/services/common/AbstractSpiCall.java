/*
 * Decompiled with CFR 0.147.
 */
package io.fabric.sdk.android.services.common;

import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.network.HttpMethod;
import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import java.util.Collections;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractSpiCall {
    private static final Pattern PROTOCOL_AND_HOST_PATTERN = Pattern.compile("http(s?)://[^\\/]+", 2);
    protected final Kit kit;
    private final HttpMethod method;
    private final String protocolAndHostOverride;
    private final HttpRequestFactory requestFactory;
    private final String url;

    public AbstractSpiCall(Kit kit, String string2, String string3, HttpRequestFactory httpRequestFactory, HttpMethod httpMethod) {
        if (string3 == null) {
            throw new IllegalArgumentException("url must not be null.");
        }
        if (httpRequestFactory == null) {
            throw new IllegalArgumentException("requestFactory must not be null.");
        }
        this.kit = kit;
        this.protocolAndHostOverride = string2;
        this.url = this.overrideProtocolAndHost(string3);
        this.requestFactory = httpRequestFactory;
        this.method = httpMethod;
    }

    private String overrideProtocolAndHost(String string2) {
        String string3 = string2;
        if (!CommonUtils.isNullOrEmpty(this.protocolAndHostOverride)) {
            string3 = PROTOCOL_AND_HOST_PATTERN.matcher(string2).replaceFirst(this.protocolAndHostOverride);
        }
        return string3;
    }

    protected HttpRequest getHttpRequest() {
        return this.getHttpRequest(Collections.<String, String>emptyMap());
    }

    protected HttpRequest getHttpRequest(Map<String, String> map) {
        return this.requestFactory.buildHttpRequest(this.method, this.getUrl(), map).useCaches(false).connectTimeout(10000).header("User-Agent", "Crashlytics Android SDK/" + this.kit.getVersion()).header("X-CRASHLYTICS-DEVELOPER-TOKEN", "470fa2b4ae81cd56ecbcda9735803434cec591fa");
    }

    protected String getUrl() {
        return this.url;
    }
}

