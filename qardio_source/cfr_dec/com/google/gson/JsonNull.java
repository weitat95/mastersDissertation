/*
 * Decompiled with CFR 0.147.
 */
package com.google.gson;

import com.google.gson.JsonElement;

public final class JsonNull
extends JsonElement {
    public static final JsonNull INSTANCE = new JsonNull();

    public boolean equals(Object object) {
        return this == object || object instanceof JsonNull;
    }

    public int hashCode() {
        return JsonNull.class.hashCode();
    }
}

