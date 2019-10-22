/*
 * Decompiled with CFR 0.147.
 */
package retrofit2;

import okhttp3.ResponseBody;

public final class Response<T> {
    private final T body;
    private final ResponseBody errorBody;
    private final okhttp3.Response rawResponse;

    private Response(okhttp3.Response response, T t, ResponseBody responseBody) {
        this.rawResponse = response;
        this.body = t;
        this.errorBody = responseBody;
    }

    public static <T> Response<T> error(ResponseBody responseBody, okhttp3.Response response) {
        if (responseBody == null) {
            throw new NullPointerException("body == null");
        }
        if (response == null) {
            throw new NullPointerException("rawResponse == null");
        }
        if (response.isSuccessful()) {
            throw new IllegalArgumentException("rawResponse should not be successful response");
        }
        return new Response<Object>(response, null, responseBody);
    }

    public static <T> Response<T> success(T t, okhttp3.Response response) {
        if (response == null) {
            throw new NullPointerException("rawResponse == null");
        }
        if (!response.isSuccessful()) {
            throw new IllegalArgumentException("rawResponse must be successful response");
        }
        return new Response<T>(response, t, null);
    }

    public T body() {
        return this.body;
    }

    public int code() {
        return this.rawResponse.code();
    }

    public boolean isSuccessful() {
        return this.rawResponse.isSuccessful();
    }

    public String message() {
        return this.rawResponse.message();
    }

    public okhttp3.Response raw() {
        return this.rawResponse;
    }
}

