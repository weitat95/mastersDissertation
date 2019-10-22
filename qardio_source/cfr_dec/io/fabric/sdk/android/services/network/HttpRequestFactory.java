/*
 * Decompiled with CFR 0.147.
 */
package io.fabric.sdk.android.services.network;

import io.fabric.sdk.android.services.network.HttpMethod;
import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.network.PinningInfoProvider;
import java.util.Map;

public interface HttpRequestFactory {
    public HttpRequest buildHttpRequest(HttpMethod var1, String var2, Map<String, String> var3);

    public void setPinningInfoProvider(PinningInfoProvider var1);
}

