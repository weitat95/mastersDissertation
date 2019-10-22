/*
 * Decompiled with CFR 0.147.
 */
package com.segment.analytics;

import com.segment.analytics.Analytics;
import com.segment.analytics.Middleware;
import com.segment.analytics.integrations.BasePayload;
import java.util.List;

class RealMiddlewareChain
implements Middleware.Chain {
    private final Analytics analytics;
    private int index;
    private final List<Middleware> middlewares;
    private final BasePayload payload;

    RealMiddlewareChain(int n, BasePayload basePayload, List<Middleware> list, Analytics analytics) {
        this.index = n;
        this.payload = basePayload;
        this.middlewares = list;
        this.analytics = analytics;
    }

    @Override
    public void proceed(BasePayload object) {
        if (this.index < this.middlewares.size()) {
            object = new RealMiddlewareChain(this.index + 1, (BasePayload)object, this.middlewares, this.analytics);
            this.middlewares.get(this.index).intercept((Middleware.Chain)object);
            return;
        }
        this.analytics.run((BasePayload)object);
    }
}

