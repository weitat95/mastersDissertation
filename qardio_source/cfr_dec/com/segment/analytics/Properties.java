/*
 * Decompiled with CFR 0.147.
 */
package com.segment.analytics;

import com.segment.analytics.ValueMap;
import java.util.Map;

public class Properties
extends ValueMap {
    public Properties() {
    }

    public Properties(int n) {
        super(n);
    }

    Properties(Map<String, Object> map) {
        super(map);
    }

    @Override
    public Properties putValue(String string2, Object object) {
        super.putValue(string2, object);
        return this;
    }

    public double revenue() {
        return this.getDouble("revenue", 0.0);
    }
}

