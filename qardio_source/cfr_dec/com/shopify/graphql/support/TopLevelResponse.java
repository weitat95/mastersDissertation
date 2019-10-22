/*
 * Decompiled with CFR 0.147.
 */
package com.shopify.graphql.support;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.shopify.graphql.support.Error;
import com.shopify.graphql.support.InvalidGraphQLException;
import java.util.ArrayList;
import java.util.List;

public class TopLevelResponse {
    private JsonObject data = null;
    private final List<Error> errors = new ArrayList<Error>();

    /*
     * Enabled aggressive block sorting
     */
    public TopLevelResponse(JsonObject jsonElement3) throws InvalidGraphQLException {
        Object object = ((JsonObject)jsonElement3).get("errors");
        JsonElement jsonElement2 = ((JsonObject)jsonElement3).get("data");
        jsonElement3 = jsonElement2;
        if (jsonElement2 != null) {
            jsonElement3 = jsonElement2;
            if (jsonElement2.isJsonNull()) {
                jsonElement3 = null;
            }
        }
        if (object == null && jsonElement3 == null) {
            throw new InvalidGraphQLException("Response must contain a top-level 'data' or 'errors' entry");
        }
        if (jsonElement3 != null) {
            if (!jsonElement3.isJsonObject()) {
                throw new InvalidGraphQLException("'data' entry in response must be a map");
            }
            this.data = jsonElement3.getAsJsonObject();
        }
        if (object != null) {
            if (!((JsonElement)object).isJsonArray()) {
                throw new InvalidGraphQLException("'errors' entry in response must be an array");
            }
            for (JsonElement jsonElement3 : ((JsonElement)object).getAsJsonArray()) {
                object = this.errors;
                jsonElement3 = jsonElement3.isJsonObject() ? jsonElement3.getAsJsonObject() : new JsonObject();
                object.add(new Error((JsonObject)jsonElement3));
            }
        }
    }

    public JsonObject getData() {
        return this.data;
    }

    public List<Error> getErrors() {
        return this.errors;
    }
}

