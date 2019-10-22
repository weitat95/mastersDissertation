/*
 * Decompiled with CFR 0.147.
 */
package com.segment.analytics.integrations;

import com.segment.analytics.Properties;
import com.segment.analytics.ValueMap;
import com.segment.analytics.integrations.BasePayload;
import com.segment.analytics.internal.Utils;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class TrackPayload
extends BasePayload {
    TrackPayload(String string2, Date date, Map<String, Object> map, Map<String, Object> map2, String string3, String string4, String string5, Map<String, Object> map3) {
        super(BasePayload.Type.track, string2, date, map, map2, string3, string4);
        this.put("event", (Object)string5);
        this.put("properties", map3);
    }

    public String event() {
        return this.getString("event");
    }

    public Properties properties() {
        return this.getValueMap("properties", Properties.class);
    }

    @Override
    public String toString() {
        return "TrackPayload{event=\"" + this.event() + "\"}";
    }

    public static class Builder
    extends BasePayload.Builder<TrackPayload, Builder> {
        private String event;
        private Map<String, Object> properties;

        public Builder event(String string2) {
            this.event = Utils.assertNotNullOrEmpty(string2, "event");
            return this;
        }

        public Builder properties(Map<String, ?> map) {
            Utils.assertNotNull(map, "properties");
            this.properties = Collections.unmodifiableMap(new LinkedHashMap(map));
            return this;
        }

        @Override
        protected TrackPayload realBuild(String string2, Date date, Map<String, Object> map, Map<String, Object> map2, String string3, String string4) {
            Map<String, Object> map3;
            Utils.assertNotNullOrEmpty(this.event, "event");
            Map<String, Object> map4 = map3 = this.properties;
            if (Utils.isNullOrEmpty(map3)) {
                map4 = Collections.emptyMap();
            }
            return new TrackPayload(string2, date, map, map2, string3, string4, this.event, map4);
        }

        @Override
        Builder self() {
            return this;
        }
    }

}

