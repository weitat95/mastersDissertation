/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.common.remote;

import java.io.IOException;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RetrofitException
extends RuntimeException {
    private Kind kind;
    private Response response;
    private Retrofit retrofit;
    private String url;

    private RetrofitException(String string2, String string3, Response response, Kind kind, Throwable throwable, Retrofit retrofit) {
        super(string2, throwable);
        this.url = string3;
        this.response = response;
        this.kind = kind;
        this.retrofit = retrofit;
    }

    public static RetrofitException httpError(String string2, Response response, Retrofit retrofit) {
        return new RetrofitException(response.code() + " " + response.message(), string2, response, Kind.HTTP, null, retrofit);
    }

    public static RetrofitException networkError(IOException iOException) {
        return new RetrofitException(iOException.getMessage(), null, null, Kind.NETWORK, iOException, null);
    }

    public static RetrofitException unexpectedError(Throwable throwable) {
        return new RetrofitException(throwable.getMessage(), null, null, Kind.UNEXPECTED, throwable, null);
    }

    public static enum Kind {
        NETWORK,
        HTTP,
        UNEXPECTED;

    }

}

