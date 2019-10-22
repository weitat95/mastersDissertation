/*
 * Decompiled with CFR 0.147.
 */
package okhttp3;

import java.io.Closeable;
import okhttp3.CacheControl;
import okhttp3.Handshake;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.ResponseBody;

public final class Response
implements Closeable {
    final ResponseBody body;
    private volatile CacheControl cacheControl;
    final Response cacheResponse;
    final int code;
    final Handshake handshake;
    final Headers headers;
    final String message;
    final Response networkResponse;
    final Response priorResponse;
    final Protocol protocol;
    final long receivedResponseAtMillis;
    final Request request;
    final long sentRequestAtMillis;

    Response(Builder builder) {
        this.request = builder.request;
        this.protocol = builder.protocol;
        this.code = builder.code;
        this.message = builder.message;
        this.handshake = builder.handshake;
        this.headers = builder.headers.build();
        this.body = builder.body;
        this.networkResponse = builder.networkResponse;
        this.cacheResponse = builder.cacheResponse;
        this.priorResponse = builder.priorResponse;
        this.sentRequestAtMillis = builder.sentRequestAtMillis;
        this.receivedResponseAtMillis = builder.receivedResponseAtMillis;
    }

    public ResponseBody body() {
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

    @Override
    public void close() {
        this.body.close();
    }

    public int code() {
        return this.code;
    }

    public Handshake handshake() {
        return this.handshake;
    }

    public String header(String string2) {
        return this.header(string2, null);
    }

    public String header(String string2, String string3) {
        if ((string2 = this.headers.get(string2)) != null) {
            return string2;
        }
        return string3;
    }

    public Headers headers() {
        return this.headers;
    }

    public boolean isSuccessful() {
        return this.code >= 200 && this.code < 300;
    }

    public String message() {
        return this.message;
    }

    public Response networkResponse() {
        return this.networkResponse;
    }

    public Builder newBuilder() {
        return new Builder(this);
    }

    public Protocol protocol() {
        return this.protocol;
    }

    public long receivedResponseAtMillis() {
        return this.receivedResponseAtMillis;
    }

    public Request request() {
        return this.request;
    }

    public long sentRequestAtMillis() {
        return this.sentRequestAtMillis;
    }

    public String toString() {
        return "Response{protocol=" + (Object)((Object)this.protocol) + ", code=" + this.code + ", message=" + this.message + ", url=" + this.request.url() + '}';
    }

    public static class Builder {
        ResponseBody body;
        Response cacheResponse;
        int code = -1;
        Handshake handshake;
        Headers.Builder headers;
        String message;
        Response networkResponse;
        Response priorResponse;
        Protocol protocol;
        long receivedResponseAtMillis;
        Request request;
        long sentRequestAtMillis;

        public Builder() {
            this.headers = new Headers.Builder();
        }

        Builder(Response response) {
            this.request = response.request;
            this.protocol = response.protocol;
            this.code = response.code;
            this.message = response.message;
            this.handshake = response.handshake;
            this.headers = response.headers.newBuilder();
            this.body = response.body;
            this.networkResponse = response.networkResponse;
            this.cacheResponse = response.cacheResponse;
            this.priorResponse = response.priorResponse;
            this.sentRequestAtMillis = response.sentRequestAtMillis;
            this.receivedResponseAtMillis = response.receivedResponseAtMillis;
        }

        private void checkPriorResponse(Response response) {
            if (response.body != null) {
                throw new IllegalArgumentException("priorResponse.body != null");
            }
        }

        private void checkSupportResponse(String string2, Response response) {
            if (response.body != null) {
                throw new IllegalArgumentException(string2 + ".body != null");
            }
            if (response.networkResponse != null) {
                throw new IllegalArgumentException(string2 + ".networkResponse != null");
            }
            if (response.cacheResponse != null) {
                throw new IllegalArgumentException(string2 + ".cacheResponse != null");
            }
            if (response.priorResponse != null) {
                throw new IllegalArgumentException(string2 + ".priorResponse != null");
            }
        }

        public Builder addHeader(String string2, String string3) {
            this.headers.add(string2, string3);
            return this;
        }

        public Builder body(ResponseBody responseBody) {
            this.body = responseBody;
            return this;
        }

        public Response build() {
            if (this.request == null) {
                throw new IllegalStateException("request == null");
            }
            if (this.protocol == null) {
                throw new IllegalStateException("protocol == null");
            }
            if (this.code < 0) {
                throw new IllegalStateException("code < 0: " + this.code);
            }
            return new Response(this);
        }

        public Builder cacheResponse(Response response) {
            if (response != null) {
                this.checkSupportResponse("cacheResponse", response);
            }
            this.cacheResponse = response;
            return this;
        }

        public Builder code(int n) {
            this.code = n;
            return this;
        }

        public Builder handshake(Handshake handshake) {
            this.handshake = handshake;
            return this;
        }

        public Builder headers(Headers headers) {
            this.headers = headers.newBuilder();
            return this;
        }

        public Builder message(String string2) {
            this.message = string2;
            return this;
        }

        public Builder networkResponse(Response response) {
            if (response != null) {
                this.checkSupportResponse("networkResponse", response);
            }
            this.networkResponse = response;
            return this;
        }

        public Builder priorResponse(Response response) {
            if (response != null) {
                this.checkPriorResponse(response);
            }
            this.priorResponse = response;
            return this;
        }

        public Builder protocol(Protocol protocol) {
            this.protocol = protocol;
            return this;
        }

        public Builder receivedResponseAtMillis(long l) {
            this.receivedResponseAtMillis = l;
            return this;
        }

        public Builder request(Request request) {
            this.request = request;
            return this;
        }

        public Builder sentRequestAtMillis(long l) {
            this.sentRequestAtMillis = l;
            return this;
        }
    }

}

