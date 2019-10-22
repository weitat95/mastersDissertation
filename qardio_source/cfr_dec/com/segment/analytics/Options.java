/*
 * Decompiled with CFR 0.147.
 */
package com.segment.analytics;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Options {
    private final Map<String, Object> integrations = new ConcurrentHashMap<String, Object>();

    public Map<String, Object> integrations() {
        return new LinkedHashMap<String, Object>(this.integrations);
    }
}

