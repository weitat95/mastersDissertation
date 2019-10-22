/*
 * Decompiled with CFR 0.147.
 */
package com.segment.analytics.integrations;

import com.segment.analytics.Traits;
import com.segment.analytics.ValueMap;
import com.segment.analytics.integrations.BasePayload;
import com.segment.analytics.internal.Utils;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class IdentifyPayload
extends BasePayload {
    IdentifyPayload(String string2, Date date, Map<String, Object> map, Map<String, Object> map2, String string3, String string4, Map<String, Object> map3) {
        super(BasePayload.Type.identify, string2, date, map, map2, string3, string4);
        this.put("traits", map3);
    }

    @Override
    public String toString() {
        return "IdentifyPayload{\"userId=\"" + this.userId() + "\"}";
    }

    public Traits traits() {
        return this.getValueMap("traits", Traits.class);
    }

    public static class Builder
    extends BasePayload.Builder<IdentifyPayload, Builder> {
        private Map<String, Object> traits;

        @Override
        IdentifyPayload realBuild(String string2, Date date, Map<String, Object> map, Map<String, Object> map2, String string3, String string4) {
            if (Utils.isNullOrEmpty(string3) && Utils.isNullOrEmpty(this.traits)) {
                throw new NullPointerException("either userId or traits are required");
            }
            return new IdentifyPayload(string2, date, map, map2, string3, string4, this.traits);
        }

        @Override
        Builder self() {
            return this;
        }

        public Builder traits(Map<String, ?> map) {
            Utils.assertNotNull(map, "traits");
            this.traits = Collections.unmodifiableMap(new LinkedHashMap(map));
            return this;
        }
    }

}

