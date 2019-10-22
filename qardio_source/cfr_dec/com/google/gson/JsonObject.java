/*
 * Decompiled with CFR 0.147.
 */
package com.google.gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;
import com.google.gson.internal.LinkedTreeMap;
import java.util.Map;
import java.util.Set;

public final class JsonObject
extends JsonElement {
    private final LinkedTreeMap<String, JsonElement> members = new LinkedTreeMap();

    public void add(String string2, JsonElement jsonElement) {
        JsonElement jsonElement2 = jsonElement;
        if (jsonElement == null) {
            jsonElement2 = JsonNull.INSTANCE;
        }
        this.members.put(string2, jsonElement2);
    }

    public Set<Map.Entry<String, JsonElement>> entrySet() {
        return this.members.entrySet();
    }

    public boolean equals(Object object) {
        return object == this || object instanceof JsonObject && ((JsonObject)object).members.equals(this.members);
    }

    public JsonElement get(String string2) {
        return this.members.get(string2);
    }

    public JsonObject getAsJsonObject(String string2) {
        return (JsonObject)this.members.get(string2);
    }

    public JsonPrimitive getAsJsonPrimitive(String string2) {
        return (JsonPrimitive)this.members.get(string2);
    }

    public boolean has(String string2) {
        return this.members.containsKey(string2);
    }

    public int hashCode() {
        return this.members.hashCode();
    }
}

