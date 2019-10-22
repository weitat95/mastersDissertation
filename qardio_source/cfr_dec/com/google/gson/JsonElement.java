/*
 * Decompiled with CFR 0.147.
 */
package com.google.gson;

import com.google.gson.JsonArray;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

public abstract class JsonElement {
    public boolean getAsBoolean() {
        throw new UnsupportedOperationException(this.getClass().getSimpleName());
    }

    Boolean getAsBooleanWrapper() {
        throw new UnsupportedOperationException(this.getClass().getSimpleName());
    }

    public double getAsDouble() {
        throw new UnsupportedOperationException(this.getClass().getSimpleName());
    }

    public int getAsInt() {
        throw new UnsupportedOperationException(this.getClass().getSimpleName());
    }

    public JsonArray getAsJsonArray() {
        if (this.isJsonArray()) {
            return (JsonArray)this;
        }
        throw new IllegalStateException("This is not a JSON Array.");
    }

    public JsonObject getAsJsonObject() {
        if (this.isJsonObject()) {
            return (JsonObject)this;
        }
        throw new IllegalStateException("Not a JSON Object: " + this);
    }

    public JsonPrimitive getAsJsonPrimitive() {
        if (this.isJsonPrimitive()) {
            return (JsonPrimitive)this;
        }
        throw new IllegalStateException("This is not a JSON Primitive.");
    }

    public long getAsLong() {
        throw new UnsupportedOperationException(this.getClass().getSimpleName());
    }

    public Number getAsNumber() {
        throw new UnsupportedOperationException(this.getClass().getSimpleName());
    }

    public String getAsString() {
        throw new UnsupportedOperationException(this.getClass().getSimpleName());
    }

    public boolean isJsonArray() {
        return this instanceof JsonArray;
    }

    public boolean isJsonNull() {
        return this instanceof JsonNull;
    }

    public boolean isJsonObject() {
        return this instanceof JsonObject;
    }

    public boolean isJsonPrimitive() {
        return this instanceof JsonPrimitive;
    }

    public String toString() {
        try {
            Object object = new StringWriter();
            JsonWriter jsonWriter = new JsonWriter((Writer)object);
            jsonWriter.setLenient(true);
            Streams.write(this, jsonWriter);
            object = ((StringWriter)object).toString();
            return object;
        }
        catch (IOException iOException) {
            throw new AssertionError(iOException);
        }
    }
}

