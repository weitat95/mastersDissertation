/*
 * Decompiled with CFR 0.147.
 */
package com.segment.analytics.integrations;

import com.segment.analytics.ValueMap;
import com.segment.analytics.internal.Utils;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public abstract class BasePayload
extends ValueMap {
    BasePayload(Type type, String string2, Date date, Map<String, Object> map, Map<String, Object> map2, String string3, String string4) {
        this.put("channel", (Object)Channel.mobile);
        this.put("type", (Object)type);
        this.put("messageId", (Object)string2);
        this.put("timestamp", (Object)Utils.toISO8601String(date));
        this.put("context", map);
        this.put("integrations", map2);
        if (!Utils.isNullOrEmpty(string3)) {
            this.put("userId", (Object)string3);
        }
        this.put("anonymousId", (Object)string4);
    }

    public String anonymousId() {
        return this.getString("anonymousId");
    }

    public ValueMap integrations() {
        return this.getValueMap("integrations");
    }

    @Override
    public BasePayload putValue(String string2, Object object) {
        super.putValue(string2, object);
        return this;
    }

    public Type type() {
        return this.getEnum(Type.class, "type");
    }

    public String userId() {
        return this.getString("userId");
    }

    public static abstract class Builder<P extends BasePayload, B extends Builder> {
        private String anonymousId;
        private Map<String, Object> context;
        private Map<String, Object> integrationsBuilder;
        private String messageId;
        private Date timestamp;
        private String userId;

        Builder() {
        }

        public B anonymousId(String string2) {
            this.anonymousId = Utils.assertNotNullOrEmpty(string2, "anonymousId");
            return this.self();
        }

        /*
         * Enabled aggressive block sorting
         */
        public P build() {
            if (Utils.isNullOrEmpty(this.userId) && Utils.isNullOrEmpty(this.anonymousId)) {
                throw new NullPointerException("either userId or anonymousId is required");
            }
            Map<Object, Object> map = Utils.isNullOrEmpty(this.integrationsBuilder) ? Collections.emptyMap() : Utils.immutableCopyOf(this.integrationsBuilder);
            if (Utils.isNullOrEmpty(this.messageId)) {
                this.messageId = UUID.randomUUID().toString();
            }
            if (this.timestamp == null) {
                this.timestamp = new Date();
            }
            if (Utils.isNullOrEmpty(this.context)) {
                this.context = Collections.emptyMap();
            }
            return this.realBuild(this.messageId, this.timestamp, this.context, map, this.userId, this.anonymousId);
        }

        public B context(Map<String, ?> map) {
            Utils.assertNotNull(map, "context");
            this.context = Collections.unmodifiableMap(new LinkedHashMap(map));
            return this.self();
        }

        public B integrations(Map<String, ?> map) {
            if (Utils.isNullOrEmpty(map)) {
                return this.self();
            }
            if (this.integrationsBuilder == null) {
                this.integrationsBuilder = new LinkedHashMap<String, Object>();
            }
            this.integrationsBuilder.putAll(map);
            return this.self();
        }

        abstract P realBuild(String var1, Date var2, Map<String, Object> var3, Map<String, Object> var4, String var5, String var6);

        abstract B self();

        public B userId(String string2) {
            this.userId = Utils.assertNotNullOrEmpty(string2, "userId");
            return this.self();
        }
    }

    public static enum Channel {
        browser,
        mobile,
        server;

    }

    public static enum Type {
        alias,
        group,
        identify,
        screen,
        track;

    }

}

