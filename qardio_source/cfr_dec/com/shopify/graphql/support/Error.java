/*
 * Decompiled with CFR 0.147.
 */
package com.shopify.graphql.support;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.io.Serializable;

public class Error
implements Serializable {
    private final int column;
    private final int line;
    private final String message;

    /*
     * Enabled aggressive block sorting
     */
    public Error(JsonObject jsonElement) {
        JsonElement jsonElement2 = ((JsonObject)jsonElement).get("message");
        this.message = jsonElement2 != null && jsonElement2.isJsonPrimitive() && jsonElement2.getAsJsonPrimitive().isString() ? jsonElement2.getAsString() : "Unknown error";
        jsonElement2 = ((JsonObject)jsonElement).get("line");
        this.line = jsonElement2 != null && jsonElement2.isJsonPrimitive() && jsonElement2.getAsJsonPrimitive().isNumber() ? jsonElement2.getAsInt() : 0;
        if ((jsonElement = ((JsonObject)jsonElement).get("column")) != null && jsonElement.isJsonPrimitive() && jsonElement.getAsJsonPrimitive().isNumber()) {
            this.column = jsonElement.getAsInt();
            return;
        }
        this.column = 0;
    }

    public String message() {
        return this.message;
    }

    public String toString() {
        return this.message();
    }
}

