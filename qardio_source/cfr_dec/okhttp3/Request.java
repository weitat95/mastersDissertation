/*
 * Decompiled with CFR 0.147.
 */
package okhttp3;

import okhttp3.CacheControl;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.RequestBody;
import okhttp3.internal.http.HttpMethod;

public final class Request {
    final RequestBody body;
    private volatile CacheControl cacheControl;
    final Headers headers;
    final String method;
    final Object tag;
    final HttpUrl url;

    /*
     * Enabled aggressive block sorting
     */
    Request(Builder object) {
        this.url = ((Builder)object).url;
        this.method = ((Builder)object).method;
        this.headers = ((Builder)object).headers.build();
        this.body = ((Builder)object).body;
        object = ((Builder)object).tag != null ? ((Builder)object).tag : this;
        this.tag = object;
    }

    public RequestBody body() {
        return this.body;
    }

    public CacheControl cacheControl() {
        CacheControl cacheControl = this.cacheControl;
        if (cacheControl != null) {
            return cacheControl;
        }
        this.cacheControl = cacheControl = CacheControl.parse(this.headers);
        return cacheControl;
    }

    public String header(String string2) {
        return this.headers.get(string2);
    }

    public Headers headers() {
        return this.headers;
    }

    public boolean isHttps() {
        return this.url.isHttps();
    }

    public String method() {
        return this.method;
    }

    public Builder newBuilder() {
        return new Builder(this);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public String toString() {
        Object object;
        StringBuilder stringBuilder = new StringBuilder().append("Request{method=").append(this.method).append(", url=").append(this.url).append(", tag=");
        if (this.tag != this) {
            object = this.tag;
            do {
                return stringBuilder.append(object).append('}').toString();
                break;
            } while (true);
        }
        object = null;
        return stringBuilder.append(object).append('}').toString();
    }

    public HttpUrl url() {
        return this.url;
    }

    public static class Builder {
        RequestBody body;
        Headers.Builder headers;
        String method;
        Object tag;
        HttpUrl url;

        public Builder() {
            this.method = "GET";
            this.headers = new Headers.Builder();
        }

        Builder(Request request) {
            this.url = request.url;
            this.method = request.method;
            this.body = request.body;
            this.tag = request.tag;
            this.headers = request.headers.newBuilder();
        }

        public Builder addHeader(String string2, String string3) {
            this.headers.add(string2, string3);
            return this;
        }

        public Request build() {
            if (this.url == null) {
                throw new IllegalStateException("url == null");
            }
            return new Request(this);
        }

        public Builder header(String string2, String string3) {
            this.headers.set(string2, string3);
            return this;
        }

        public Builder headers(Headers headers) {
            this.headers = headers.newBuilder();
            return this;
        }

        public Builder method(String string2, RequestBody requestBody) {
            if (string2 == null) {
                throw new NullPointerException("method == null");
            }
            if (string2.length() == 0) {
                throw new IllegalArgumentException("method.length() == 0");
            }
            if (requestBody != null && !HttpMethod.permitsRequestBody(string2)) {
                throw new IllegalArgumentException("method " + string2 + " must not have a request body.");
            }
            if (requestBody == null && HttpMethod.requiresRequestBody(string2)) {
                throw new IllegalArgumentException("method " + string2 + " must have a request body.");
            }
            this.method = string2;
            this.body = requestBody;
            return this;
        }

        public Builder post(RequestBody requestBody) {
            return this.method("POST", requestBody);
        }

        public Builder removeHeader(String string2) {
            this.headers.removeAll(string2);
            return this;
        }

        /*
         * Enabled aggressive block sorting
         */
        public Builder url(String object) {
            Object object2;
            if (object == null) {
                throw new NullPointerException("url == null");
            }
            if (((String)object).regionMatches(true, 0, "ws:", 0, 3)) {
                object2 = "http:" + ((String)object).substring(3);
            } else {
                object2 = object;
                if (((String)object).regionMatches(true, 0, "wss:", 0, 4)) {
                    object2 = "https:" + ((String)object).substring(4);
                }
            }
            if ((object = HttpUrl.parse((String)object2)) == null) {
                throw new IllegalArgumentException("unexpected url: " + (String)object2);
            }
            return this.url((HttpUrl)object);
        }

        public Builder url(HttpUrl httpUrl) {
            if (httpUrl == null) {
                throw new NullPointerException("url == null");
            }
            this.url = httpUrl;
            return this;
        }
    }

}

