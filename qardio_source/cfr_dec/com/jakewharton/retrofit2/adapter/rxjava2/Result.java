/*
 * Decompiled with CFR 0.147.
 */
package com.jakewharton.retrofit2.adapter.rxjava2;

import retrofit2.Response;

public final class Result<T> {
    private final Throwable error;
    private final Response<T> response;

    private Result(Response<T> response, Throwable throwable) {
        this.response = response;
        this.error = throwable;
    }

    public static <T> Result<T> error(Throwable throwable) {
        if (throwable == null) {
            throw new NullPointerException("error == null");
        }
        return new Result<T>(null, throwable);
    }

    public static <T> Result<T> response(Response<T> response) {
        if (response == null) {
            throw new NullPointerException("response == null");
        }
        return new Result<T>(response, null);
    }
}

