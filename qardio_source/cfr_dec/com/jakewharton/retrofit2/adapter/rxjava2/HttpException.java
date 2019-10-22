/*
 * Decompiled with CFR 0.147.
 */
package com.jakewharton.retrofit2.adapter.rxjava2;

import retrofit2.Response;

public final class HttpException
extends Exception {
    private final int code;
    private final String message;
    private final transient Response<?> response;

    public HttpException(Response<?> response) {
        super(HttpException.getMessage(response));
        this.code = response.code();
        this.message = response.message();
        this.response = response;
    }

    private static String getMessage(Response<?> response) {
        if (response == null) {
            throw new NullPointerException("response == null");
        }
        return "HTTP " + response.code() + " " + response.message();
    }

    public Response<?> response() {
        return this.response;
    }
}

