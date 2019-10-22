/*
 * Decompiled with CFR 0.147.
 */
package com.segment.analytics;

import com.segment.analytics.integrations.BasePayload;

public interface Middleware {
    public void intercept(Chain var1);

    public static interface Chain {
        public void proceed(BasePayload var1);
    }

}

