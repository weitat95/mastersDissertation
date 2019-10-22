/*
 * Decompiled with CFR 0.147.
 */
package com.shopify.buy3;

import com.shopify.buy3.GraphClient;
import okhttp3.Interceptor;
import okhttp3.Response;

final class GraphClient$Builder$$Lambda$1
implements Interceptor {
    private final String arg$1;
    private final String arg$2;

    private GraphClient$Builder$$Lambda$1(String string2, String string3) {
        this.arg$1 = string2;
        this.arg$2 = string3;
    }

    public static Interceptor lambdaFactory$(String string2, String string3) {
        return new GraphClient$Builder$$Lambda$1(string2, string3);
    }

    @Override
    public Response intercept(Interceptor.Chain chain) {
        return GraphClient.Builder.lambda$sdkHeaderInterceptor$0(this.arg$1, this.arg$2, chain);
    }
}

