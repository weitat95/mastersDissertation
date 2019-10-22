/*
 * Decompiled with CFR 0.147.
 */
package okhttp3.internal.cache;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import okhttp3.CacheControl;
import okhttp3.Handshake;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.Internal;
import okhttp3.internal.http.HttpDate;
import okhttp3.internal.http.HttpHeaders;

public final class CacheStrategy {
    public final Response cacheResponse;
    public final Request networkRequest;

    CacheStrategy(Request request, Response response) {
        this.networkRequest = request;
        this.cacheResponse = response;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public static boolean isCacheable(Response response, Request request) {
        switch (response.code()) {
            default: {
                return false;
            }
            case 302: 
            case 307: {
                if (response.header("Expires") != null || response.cacheControl().maxAgeSeconds() != -1 || response.cacheControl().isPublic()) break;
                if (!response.cacheControl().isPrivate()) return false;
                break;
            }
            case 200: 
            case 203: 
            case 204: 
            case 300: 
            case 301: 
            case 308: 
            case 404: 
            case 405: 
            case 410: 
            case 414: 
            case 501: 
        }
        if (response.cacheControl().noStore()) return false;
        if (request.cacheControl().noStore()) return false;
        return true;
    }

    public static class Factory {
        private int ageSeconds = -1;
        final Response cacheResponse;
        private String etag;
        private Date expires;
        private Date lastModified;
        private String lastModifiedString;
        final long nowMillis;
        private long receivedResponseMillis;
        final Request request;
        private long sentRequestMillis;
        private Date servedDate;
        private String servedDateString;

        /*
         * Enabled aggressive block sorting
         */
        public Factory(long l, Request object, Response object2) {
            this.nowMillis = l;
            this.request = object;
            this.cacheResponse = object2;
            if (object2 != null) {
                this.sentRequestMillis = ((Response)object2).sentRequestAtMillis();
                this.receivedResponseMillis = ((Response)object2).receivedResponseAtMillis();
                object = ((Response)object2).headers();
                int n = ((Headers)object).size();
                for (int i = 0; i < n; ++i) {
                    object2 = ((Headers)object).name(i);
                    String string2 = ((Headers)object).value(i);
                    if ("Date".equalsIgnoreCase((String)object2)) {
                        this.servedDate = HttpDate.parse(string2);
                        this.servedDateString = string2;
                        continue;
                    }
                    if ("Expires".equalsIgnoreCase((String)object2)) {
                        this.expires = HttpDate.parse(string2);
                        continue;
                    }
                    if ("Last-Modified".equalsIgnoreCase((String)object2)) {
                        this.lastModified = HttpDate.parse(string2);
                        this.lastModifiedString = string2;
                        continue;
                    }
                    if ("ETag".equalsIgnoreCase((String)object2)) {
                        this.etag = string2;
                        continue;
                    }
                    if (!"Age".equalsIgnoreCase((String)object2)) continue;
                    this.ageSeconds = HttpHeaders.parseSeconds(string2, -1);
                }
            }
        }

        private long cacheResponseAge() {
            long l = 0L;
            if (this.servedDate != null) {
                l = Math.max(0L, this.receivedResponseMillis - this.servedDate.getTime());
            }
            if (this.ageSeconds != -1) {
                l = Math.max(l, TimeUnit.SECONDS.toMillis(this.ageSeconds));
            }
            return l + (this.receivedResponseMillis - this.sentRequestMillis) + (this.nowMillis - this.receivedResponseMillis);
        }

        /*
         * Enabled aggressive block sorting
         */
        private long computeFreshnessLifetime() {
            long l = 0L;
            CacheControl cacheControl = this.cacheResponse.cacheControl();
            if (cacheControl.maxAgeSeconds() != -1) {
                return TimeUnit.SECONDS.toMillis(cacheControl.maxAgeSeconds());
            }
            if (this.expires != null) {
                long l2 = this.servedDate != null ? this.servedDate.getTime() : this.receivedResponseMillis;
                l2 = this.expires.getTime() - l2;
                if (l2 <= 0L) return 0L;
                return l2;
            }
            long l3 = l;
            if (this.lastModified == null) return l3;
            l3 = l;
            if (this.cacheResponse.request().url().query() != null) return l3;
            l3 = this.servedDate != null ? this.servedDate.getTime() : this.sentRequestMillis;
            long l4 = l3 - this.lastModified.getTime();
            l3 = l;
            if (l4 <= 0L) return l3;
            return l4 / 10L;
        }

        /*
         * Enabled aggressive block sorting
         */
        private CacheStrategy getCandidate() {
            long l;
            if (this.cacheResponse == null) {
                return new CacheStrategy(this.request, null);
            }
            if (this.request.isHttps() && this.cacheResponse.handshake() == null) {
                return new CacheStrategy(this.request, null);
            }
            if (!CacheStrategy.isCacheable(this.cacheResponse, this.request)) {
                return new CacheStrategy(this.request, null);
            }
            Object object = this.request.cacheControl();
            if (((CacheControl)object).noCache() || Factory.hasConditions(this.request)) {
                return new CacheStrategy(this.request, null);
            }
            long l2 = this.cacheResponseAge();
            long l3 = l = this.computeFreshnessLifetime();
            if (((CacheControl)object).maxAgeSeconds() != -1) {
                l3 = Math.min(l, TimeUnit.SECONDS.toMillis(((CacheControl)object).maxAgeSeconds()));
            }
            l = 0L;
            if (((CacheControl)object).minFreshSeconds() != -1) {
                l = TimeUnit.SECONDS.toMillis(((CacheControl)object).minFreshSeconds());
            }
            long l4 = 0L;
            Object object2 = this.cacheResponse.cacheControl();
            long l5 = l4;
            if (!((CacheControl)object2).mustRevalidate()) {
                l5 = l4;
                if (((CacheControl)object).maxStaleSeconds() != -1) {
                    l5 = TimeUnit.SECONDS.toMillis(((CacheControl)object).maxStaleSeconds());
                }
            }
            if (!((CacheControl)object2).noCache() && l2 + l < l3 + l5) {
                object = this.cacheResponse.newBuilder();
                if (l2 + l >= l3) {
                    ((Response.Builder)object).addHeader("Warning", "110 HttpURLConnection \"Response is stale\"");
                }
                if (l2 > 86400000L && this.isFreshnessLifetimeHeuristic()) {
                    ((Response.Builder)object).addHeader("Warning", "113 HttpURLConnection \"Heuristic expiration\"");
                }
                return new CacheStrategy(null, ((Response.Builder)object).build());
            }
            if (this.etag != null) {
                object = "If-None-Match";
                object2 = this.etag;
            } else if (this.lastModified != null) {
                object = "If-Modified-Since";
                object2 = this.lastModifiedString;
            } else {
                if (this.servedDate == null) {
                    return new CacheStrategy(this.request, null);
                }
                object = "If-Modified-Since";
                object2 = this.servedDateString;
            }
            Headers.Builder builder = this.request.headers().newBuilder();
            Internal.instance.addLenient(builder, (String)object, (String)object2);
            return new CacheStrategy(this.request.newBuilder().headers(builder.build()).build(), this.cacheResponse);
        }

        private static boolean hasConditions(Request request) {
            return request.header("If-Modified-Since") != null || request.header("If-None-Match") != null;
        }

        private boolean isFreshnessLifetimeHeuristic() {
            return this.cacheResponse.cacheControl().maxAgeSeconds() == -1 && this.expires == null;
        }

        public CacheStrategy get() {
            CacheStrategy cacheStrategy;
            CacheStrategy cacheStrategy2 = cacheStrategy = this.getCandidate();
            if (cacheStrategy.networkRequest != null) {
                cacheStrategy2 = cacheStrategy;
                if (this.request.cacheControl().onlyIfCached()) {
                    cacheStrategy2 = new CacheStrategy(null, null);
                }
            }
            return cacheStrategy2;
        }
    }

}

