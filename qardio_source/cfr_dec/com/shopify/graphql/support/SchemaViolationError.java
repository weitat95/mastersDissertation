/*
 * Decompiled with CFR 0.147.
 */
package com.shopify.graphql.support;

import com.google.gson.JsonElement;
import com.shopify.graphql.support.AbstractResponse;

public class SchemaViolationError
extends Exception {
    private final String field;
    private final AbstractResponse object;
    private final JsonElement value;

    public SchemaViolationError(AbstractResponse abstractResponse, String string2, JsonElement jsonElement) {
        super("Invalid value " + jsonElement.toString() + " for field " + abstractResponse.getClass().getSimpleName() + "." + string2);
        this.object = abstractResponse;
        this.field = string2;
        this.value = jsonElement;
    }
}

