/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  org.json.JSONObject
 */
package com.segment.analytics;

import android.content.Context;
import android.content.SharedPreferences;
import com.segment.analytics.Cartographer;
import com.segment.analytics.internal.Utils;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import org.json.JSONObject;

public class ValueMap
implements Map<String, Object> {
    private final Map<String, Object> delegate;

    public ValueMap() {
        this.delegate = new LinkedHashMap<String, Object>();
    }

    public ValueMap(int n) {
        this.delegate = new LinkedHashMap<String, Object>(n);
    }

    public ValueMap(Map<String, Object> map) {
        if (map == null) {
            throw new IllegalArgumentException("Map must not be null.");
        }
        this.delegate = map;
    }

    private <T extends ValueMap> T coerceToValueMap(Object object, Class<T> class_) {
        if (object == null) {
            return null;
        }
        if (class_.isAssignableFrom(object.getClass())) {
            return (T)((ValueMap)object);
        }
        if (object instanceof Map) {
            return ValueMap.createValueMap((Map)object, class_);
        }
        return null;
    }

    static <T extends ValueMap> T createValueMap(Map map, Class<T> class_) {
        try {
            Constructor<T> constructor = class_.getDeclaredConstructor(Map.class);
            constructor.setAccessible(true);
            map = (ValueMap)constructor.newInstance(map);
        }
        catch (Exception exception) {
            throw new AssertionError((Object)("Could not create instance of " + class_.getCanonicalName() + ".\n" + exception));
        }
        return (T)map;
    }

    @Override
    public void clear() {
        this.delegate.clear();
    }

    @Override
    public boolean containsKey(Object object) {
        return this.delegate.containsKey(object);
    }

    @Override
    public boolean containsValue(Object object) {
        return this.delegate.containsValue(object);
    }

    @Override
    public Set<Map.Entry<String, Object>> entrySet() {
        return this.delegate.entrySet();
    }

    @Override
    public boolean equals(Object object) {
        return object == this || this.delegate.equals(object);
    }

    @Override
    public Object get(Object object) {
        return this.delegate.get(object);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean getBoolean(String object, boolean bl) {
        if ((object = this.get(object)) instanceof Boolean) {
            return (Boolean)object;
        }
        if (!(object instanceof String)) return bl;
        return Boolean.valueOf((String)object);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public double getDouble(String object, double d) {
        if ((object = this.get(object)) instanceof Double) {
            return (Double)object;
        }
        if (object instanceof Number) {
            return ((Number)object).doubleValue();
        }
        double d2 = d;
        if (!(object instanceof String)) return d2;
        try {
            return Double.valueOf((String)object);
        }
        catch (NumberFormatException numberFormatException) {
            return d;
        }
    }

    public <T extends Enum<T>> T getEnum(Class<T> class_, String object) {
        if (class_ == null) {
            throw new IllegalArgumentException("enumType may not be null");
        }
        if (class_.isInstance(object = this.get(object))) {
            return (T)((Enum)object);
        }
        if (object instanceof String) {
            return Enum.valueOf(class_, (String)object);
        }
        return null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public long getLong(String object, long l) {
        if ((object = this.get(object)) instanceof Long) {
            return (Long)object;
        }
        if (object instanceof Number) {
            return ((Number)object).longValue();
        }
        long l2 = l;
        if (!(object instanceof String)) return l2;
        try {
            return Long.valueOf((String)object);
        }
        catch (NumberFormatException numberFormatException) {
            return l;
        }
    }

    public String getString(String object) {
        if ((object = this.get(object)) instanceof String) {
            return (String)object;
        }
        if (object != null) {
            return String.valueOf(object);
        }
        return null;
    }

    public ValueMap getValueMap(Object object) {
        if ((object = this.get(object)) instanceof ValueMap) {
            return (ValueMap)object;
        }
        if (object instanceof Map) {
            return new ValueMap((Map)object);
        }
        return null;
    }

    public <T extends ValueMap> T getValueMap(String string2, Class<T> class_) {
        return this.coerceToValueMap(this.get(string2), class_);
    }

    @Override
    public int hashCode() {
        return this.delegate.hashCode();
    }

    @Override
    public boolean isEmpty() {
        return this.delegate.isEmpty();
    }

    @Override
    public Set<String> keySet() {
        return this.delegate.keySet();
    }

    @Override
    public Object put(String string2, Object object) {
        return this.delegate.put(string2, object);
    }

    @Override
    public void putAll(Map<? extends String, ?> map) {
        this.delegate.putAll(map);
    }

    public ValueMap putValue(String string2, Object object) {
        this.delegate.put(string2, object);
        return this;
    }

    @Override
    public Object remove(Object object) {
        return this.delegate.remove(object);
    }

    @Override
    public int size() {
        return this.delegate.size();
    }

    public JSONObject toJsonObject() {
        return Utils.toJsonObject(this.delegate);
    }

    public String toString() {
        return this.delegate.toString();
    }

    @Override
    public Collection<Object> values() {
        return this.delegate.values();
    }

    static class Cache<T extends ValueMap> {
        private final Cartographer cartographer;
        private final Class<T> clazz;
        private final String key;
        private final SharedPreferences preferences;
        private T value;

        Cache(Context context, Cartographer cartographer, String string2, String string3, Class<T> class_) {
            this.cartographer = cartographer;
            this.preferences = Utils.getSegmentSharedPreferences(context, string3);
            this.key = string2;
            this.clazz = class_;
        }

        T create(Map<String, Object> map) {
            return ValueMap.createValueMap(map, this.clazz);
        }

        void delete() {
            this.preferences.edit().remove(this.key).apply();
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        T get() {
            if (this.value != null) return this.value;
            String string2 = this.preferences.getString(this.key, null);
            if (Utils.isNullOrEmpty(string2)) {
                return null;
            }
            try {
                this.value = this.create(this.cartographer.fromJson(string2));
            }
            catch (IOException iOException) {
                return null;
            }
            return this.value;
        }

        boolean isSet() {
            return this.preferences.contains(this.key);
        }

        void set(T object) {
            this.value = object;
            object = this.cartographer.toJson((Map<?, ?>)object);
            this.preferences.edit().putString(this.key, object).apply();
        }
    }

}

