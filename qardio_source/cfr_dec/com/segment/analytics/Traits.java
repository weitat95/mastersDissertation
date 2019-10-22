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
import com.segment.analytics.internal.Utils;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class Traits
extends ValueMap {
    public Traits() {
    }

    Traits(Map<String, Object> map) {
        super(map);
    }

    static Traits create() {
        Traits traits = new Traits(new Utils.NullableConcurrentHashMap<String, Object>());
        traits.putAnonymousId(UUID.randomUUID().toString());
        return traits;
    }

    public String anonymousId() {
        return this.getString("anonymousId");
    }

    public Traits putAge(int n) {
        return this.putValue("age", n);
    }

    Traits putAnonymousId(String string2) {
        return this.putValue("anonymousId", string2);
    }

    public Traits putGender(String string2) {
        return this.putValue("gender", string2);
    }

    Traits putUserId(String string2) {
        return this.putValue("userId", string2);
    }

    @Override
    public Traits putValue(String string2, Object object) {
        super.putValue(string2, object);
        return this;
    }

    public Traits unmodifiableCopy() {
        return new Traits(Collections.unmodifiableMap(new LinkedHashMap<String, Object>(this)));
    }

    public String userId() {
        return this.getString("userId");
    }

    static class Cache
    extends ValueMap.Cache<Traits> {
        Cache(Context context, Cartographer cartographer, String string2) {
            super(context, cartographer, "traits-" + string2, string2, Traits.class);
        }

        @Override
        public Traits create(Map<String, Object> map) {
            return new Traits(new Utils.NullableConcurrentHashMap<String, Object>(map));
        }
    }

}

