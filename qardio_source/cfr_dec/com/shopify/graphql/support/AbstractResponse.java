/*
 * Decompiled with CFR 0.147.
 */
package com.shopify.graphql.support;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.shopify.graphql.support.SchemaViolationError;
import java.io.Serializable;
import java.util.HashMap;

public abstract class AbstractResponse<T extends AbstractResponse>
implements Serializable {
    private String aliasSuffix = null;
    public final HashMap<String, Object> optimisticData;
    public final HashMap<String, Object> responseData = new HashMap();

    public AbstractResponse() {
        this.optimisticData = new HashMap();
    }

    public Object get(String string2) {
        if (this.optimisticData.containsKey(string2 = this.getKey(string2))) {
            return this.optimisticData.get(string2);
        }
        return this.responseData.get(string2);
    }

    protected String getFieldName(String string2) {
        int n = string2.lastIndexOf("__");
        String string3 = string2;
        if (n > 1) {
            string3 = string2.substring(0, n);
        }
        return string3;
    }

    protected String getKey(String string2) {
        String string3 = string2;
        if (this.aliasSuffix != null) {
            string3 = string2 + "__" + this.aliasSuffix;
            this.aliasSuffix = null;
        }
        return string3;
    }

    protected JsonArray jsonAsArray(JsonElement jsonElement, String string2) throws SchemaViolationError {
        if (!jsonElement.isJsonArray()) {
            throw new SchemaViolationError(this, string2, jsonElement);
        }
        return jsonElement.getAsJsonArray();
    }

    protected Boolean jsonAsBoolean(JsonElement jsonElement, String string2) throws SchemaViolationError {
        if (!jsonElement.isJsonPrimitive() || !jsonElement.getAsJsonPrimitive().isBoolean()) {
            throw new SchemaViolationError(this, string2, jsonElement);
        }
        return jsonElement.getAsJsonPrimitive().getAsBoolean();
    }

    protected Double jsonAsDouble(JsonElement jsonElement, String string2) throws SchemaViolationError {
        if (!jsonElement.isJsonPrimitive() || !jsonElement.getAsJsonPrimitive().isNumber()) {
            throw new SchemaViolationError(this, string2, jsonElement);
        }
        return jsonElement.getAsJsonPrimitive().getAsDouble();
    }

    protected Integer jsonAsInteger(JsonElement jsonElement, String string2) throws SchemaViolationError {
        int n;
        if (!jsonElement.isJsonPrimitive() || !jsonElement.getAsJsonPrimitive().isNumber()) {
            throw new SchemaViolationError(this, string2, jsonElement);
        }
        try {
            n = jsonElement.getAsJsonPrimitive().getAsInt();
        }
        catch (NumberFormatException numberFormatException) {
            throw new SchemaViolationError(this, string2, jsonElement);
        }
        return n;
    }

    protected JsonObject jsonAsObject(JsonElement jsonElement, String string2) throws SchemaViolationError {
        if (!jsonElement.isJsonObject()) {
            throw new SchemaViolationError(this, string2, jsonElement);
        }
        return jsonElement.getAsJsonObject();
    }

    protected String jsonAsString(JsonElement jsonElement, String string2) throws SchemaViolationError {
        if (!jsonElement.isJsonPrimitive() || !jsonElement.getAsJsonPrimitive().isString()) {
            throw new SchemaViolationError(this, string2, jsonElement);
        }
        return jsonElement.getAsJsonPrimitive().getAsString();
    }
}

