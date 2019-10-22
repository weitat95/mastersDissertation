/*
 * Decompiled with CFR 0.147.
 */
package com.shopify.buy3;

import com.shopify.buy3.GraphError;
import okhttp3.Response;

public class GraphHttpError
extends GraphError {
    private final int code;
    private final String message;

    GraphHttpError(Response response) {
        super(GraphHttpError.formatMessage(response));
        this.code = response.code();
        this.message = response.message();
    }

    private static String formatMessage(Response response) {
        return "HTTP " + response.code() + " " + response.message();
    }
}

