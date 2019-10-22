/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.segment.analytics;

import android.content.Context;
import com.segment.analytics.Cartographer;
import com.segment.analytics.ValueMap;
import java.util.Collections;
import java.util.Map;

class ProjectSettings
extends ValueMap {
    ProjectSettings(Map<String, Object> map) {
        super(Collections.unmodifiableMap(map));
    }

    static ProjectSettings create(Map<String, Object> map) {
        map.put("timestamp", System.currentTimeMillis());
        return new ProjectSettings(map);
    }

    ValueMap integrations() {
        return this.getValueMap("integrations");
    }

    ValueMap plan() {
        return this.getValueMap("plan");
    }

    long timestamp() {
        return this.getLong("timestamp", 0L);
    }

    ValueMap trackingPlan() {
        ValueMap valueMap = this.plan();
        if (valueMap == null) {
            return null;
        }
        return valueMap.getValueMap("track");
    }

    static class Cache
    extends ValueMap.Cache<ProjectSettings> {
        Cache(Context context, Cartographer cartographer, String string2) {
            super(context, cartographer, "project-settings-plan-" + string2, string2, ProjectSettings.class);
        }

        @Override
        public ProjectSettings create(Map<String, Object> map) {
            return new ProjectSettings(map);
        }
    }

}

