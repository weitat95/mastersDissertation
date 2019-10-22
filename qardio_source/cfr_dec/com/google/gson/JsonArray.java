/*
 * Decompiled with CFR 0.147.
 */
package com.google.gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class JsonArray
extends JsonElement
implements Iterable<JsonElement> {
    private final List<JsonElement> elements = new ArrayList<JsonElement>();

    public void add(JsonElement jsonElement) {
        JsonElement jsonElement2 = jsonElement;
        if (jsonElement == null) {
            jsonElement2 = JsonNull.INSTANCE;
        }
        this.elements.add(jsonElement2);
    }

    public boolean equals(Object object) {
        return object == this || object instanceof JsonArray && ((JsonArray)object).elements.equals(this.elements);
    }

    public JsonElement get(int n) {
        return this.elements.get(n);
    }

    @Override
    public boolean getAsBoolean() {
        if (this.elements.size() == 1) {
            return this.elements.get(0).getAsBoolean();
        }
        throw new IllegalStateException();
    }

    @Override
    public double getAsDouble() {
        if (this.elements.size() == 1) {
            return this.elements.get(0).getAsDouble();
        }
        throw new IllegalStateException();
    }

    @Override
    public int getAsInt() {
        if (this.elements.size() == 1) {
            return this.elements.get(0).getAsInt();
        }
        throw new IllegalStateException();
    }

    @Override
    public long getAsLong() {
        if (this.elements.size() == 1) {
            return this.elements.get(0).getAsLong();
        }
        throw new IllegalStateException();
    }

    @Override
    public Number getAsNumber() {
        if (this.elements.size() == 1) {
            return this.elements.get(0).getAsNumber();
        }
        throw new IllegalStateException();
    }

    @Override
    public String getAsString() {
        if (this.elements.size() == 1) {
            return this.elements.get(0).getAsString();
        }
        throw new IllegalStateException();
    }

    public int hashCode() {
        return this.elements.hashCode();
    }

    @Override
    public Iterator<JsonElement> iterator() {
        return this.elements.iterator();
    }

    public int size() {
        return this.elements.size();
    }
}

