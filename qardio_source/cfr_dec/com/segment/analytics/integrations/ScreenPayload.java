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

public class ScreenPayload
extends BasePayload {
    ScreenPayload(String string2, Date date, Map<String, Object> map, Map<String, Object> map2, String string3, String string4, String string5, String string6, Map<String, Object> map3) {
        super(BasePayload.Type.screen, string2, date, map, map2, string3, string4);
        if (!Utils.isNullOrEmpty(string5)) {
            this.put("name", (Object)string5);
        }
        if (!Utils.isNullOrEmpty(string6)) {
            this.put("category", (Object)string6);
        }
        this.put("properties", map3);
    }

    @Deprecated
    public String category() {
        return this.getString("category");
    }

    public String event() {
        String string2 = this.name();
        if (!Utils.isNullOrEmpty(string2)) {
            return string2;
        }
        return this.category();
    }

    public String name() {
        return this.getString("name");
    }

    public Properties properties() {
        return this.getValueMap("properties", Properties.class);
    }

    @Override
    public String toString() {
        return "ScreenPayload{name=\"" + this.name() + ",category=\"" + this.category() + "\"}";
    }

    public static class Builder
    extends BasePayload.Builder<ScreenPayload, Builder> {
        private String category;
        private String name;
        private Map<String, Object> properties;

        @Deprecated
        public Builder category(String string2) {
            this.category = string2;
            return this;
        }

        public Builder name(String string2) {
            this.name = string2;
            return this;
        }

        public Builder properties(Map<String, ?> map) {
            Utils.assertNotNull(map, "properties");
            this.properties = Collections.unmodifiableMap(new LinkedHashMap(map));
            return this;
        }

        @Override
        protected ScreenPayload realBuild(String string2, Date date, Map<String, Object> map, Map<String, Object> map2, String string3, String string4) {
            Map<String, Object> map3;
            if (Utils.isNullOrEmpty(this.name) && Utils.isNullOrEmpty(this.category)) {
                throw new NullPointerException("either name or category is required");
            }
            Map<String, Object> map4 = map3 = this.properties;
            if (Utils.isNullOrEmpty(map3)) {
                map4 = Collections.emptyMap();
            }
            return new ScreenPayload(string2, date, map, map2, string3, string4, this.name, this.category, map4);
        }

        @Override
        Builder self() {
            return this;
        }
    }

}

