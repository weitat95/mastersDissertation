/*
 * Decompiled with CFR 0.147.
 */
package com.crashlytics.android.answers.shim;

import com.crashlytics.android.answers.CustomEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class KitEvent {
    private final Map<String, Object> attributes = new HashMap<String, Object>();
    private final String eventName;

    public KitEvent(String string2) {
        this.eventName = string2;
    }

    public KitEvent putAttribute(String string2, String string3) {
        this.attributes.put(string2, string3);
        return this;
    }

    CustomEvent toCustomEvent() {
        CustomEvent customEvent = new CustomEvent(this.eventName);
        for (String string2 : this.attributes.keySet()) {
            Object object = this.attributes.get(string2);
            if (object instanceof String) {
                customEvent.putCustomAttribute(string2, (String)object);
                continue;
            }
            if (!(object instanceof Number)) continue;
            customEvent.putCustomAttribute(string2, (Number)object);
        }
        return customEvent;
    }
}

