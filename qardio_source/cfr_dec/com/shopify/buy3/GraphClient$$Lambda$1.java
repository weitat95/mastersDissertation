/*
 * Decompiled with CFR 0.147.
 */
package com.shopify.buy3;

import com.shopify.buy3.GraphClient;
import java.util.concurrent.ThreadFactory;

final class GraphClient$$Lambda$1
implements ThreadFactory {
    private static final GraphClient$$Lambda$1 instance = new GraphClient$$Lambda$1();

    private GraphClient$$Lambda$1() {
    }

    public static ThreadFactory lambdaFactory$() {
        return instance;
    }

    @Override
    public Thread newThread(Runnable runnable) {
        return GraphClient.lambda$new$0(runnable);
    }
}

