/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 */
package com.crashlytics.android.answers;

import android.app.Activity;
import com.crashlytics.android.answers.CustomEvent;
import com.crashlytics.android.answers.SessionEventMetadata;
import java.util.Collections;
import java.util.Map;

final class SessionEvent {
    public final Map<String, Object> customAttributes;
    public final String customType;
    public final Map<String, String> details;
    public final Map<String, Object> predefinedAttributes;
    public final String predefinedType;
    public final SessionEventMetadata sessionEventMetadata;
    private String stringRepresentation;
    public final long timestamp;
    public final Type type;

    private SessionEvent(SessionEventMetadata sessionEventMetadata, long l, Type type, Map<String, String> map, String string2, Map<String, Object> map2, String string3, Map<String, Object> map3) {
        this.sessionEventMetadata = sessionEventMetadata;
        this.timestamp = l;
        this.type = type;
        this.details = map;
        this.customType = string2;
        this.customAttributes = map2;
        this.predefinedType = string3;
        this.predefinedAttributes = map3;
    }

    public static Builder crashEventBuilder(String object) {
        object = Collections.singletonMap("sessionId", object);
        return new Builder(Type.CRASH).details((Map<String, String>)object);
    }

    public static Builder crashEventBuilder(String string2, String string3) {
        return SessionEvent.crashEventBuilder(string2).customAttributes(Collections.singletonMap("exceptionName", string3));
    }

    public static Builder customEventBuilder(CustomEvent customEvent) {
        return new Builder(Type.CUSTOM).customType(customEvent.getCustomType()).customAttributes(customEvent.getCustomAttributes());
    }

    public static Builder installEventBuilder(long l) {
        return new Builder(Type.INSTALL).details(Collections.singletonMap("installedAt", String.valueOf(l)));
    }

    public static Builder lifecycleEventBuilder(Type type, Activity object) {
        object = Collections.singletonMap("activity", object.getClass().getName());
        return new Builder(type).details((Map<String, String>)object);
    }

    public String toString() {
        if (this.stringRepresentation == null) {
            this.stringRepresentation = "[" + this.getClass().getSimpleName() + ": " + "timestamp=" + this.timestamp + ", type=" + (Object)((Object)this.type) + ", details=" + this.details + ", customType=" + this.customType + ", customAttributes=" + this.customAttributes + ", predefinedType=" + this.predefinedType + ", predefinedAttributes=" + this.predefinedAttributes + ", metadata=[" + this.sessionEventMetadata + "]]";
        }
        return this.stringRepresentation;
    }

    static class Builder {
        Map<String, Object> customAttributes;
        String customType;
        Map<String, String> details;
        Map<String, Object> predefinedAttributes;
        String predefinedType;
        final long timestamp;
        final Type type;

        public Builder(Type type) {
            this.type = type;
            this.timestamp = System.currentTimeMillis();
            this.details = null;
            this.customType = null;
            this.customAttributes = null;
            this.predefinedType = null;
            this.predefinedAttributes = null;
        }

        public SessionEvent build(SessionEventMetadata sessionEventMetadata) {
            return new SessionEvent(sessionEventMetadata, this.timestamp, this.type, this.details, this.customType, this.customAttributes, this.predefinedType, this.predefinedAttributes);
        }

        public Builder customAttributes(Map<String, Object> map) {
            this.customAttributes = map;
            return this;
        }

        public Builder customType(String string2) {
            this.customType = string2;
            return this;
        }

        public Builder details(Map<String, String> map) {
            this.details = map;
            return this;
        }
    }

    static enum Type {
        START,
        RESUME,
        PAUSE,
        STOP,
        CRASH,
        INSTALL,
        CUSTOM,
        PREDEFINED;

    }

}

