/*
 * Decompiled with CFR 0.147.
 */
package okhttp3;

import java.util.concurrent.TimeUnit;
import okhttp3.Headers;
import okhttp3.internal.http.HttpHeaders;

public final class CacheControl {
    public static final CacheControl FORCE_CACHE;
    public static final CacheControl FORCE_NETWORK;
    String headerValue;
    private final boolean isPrivate;
    private final boolean isPublic;
    private final int maxAgeSeconds;
    private final int maxStaleSeconds;
    private final int minFreshSeconds;
    private final boolean mustRevalidate;
    private final boolean noCache;
    private final boolean noStore;
    private final boolean noTransform;
    private final boolean onlyIfCached;
    private final int sMaxAgeSeconds;

    static {
        FORCE_NETWORK = new Builder().noCache().build();
        FORCE_CACHE = new Builder().onlyIfCached().maxStale(Integer.MAX_VALUE, TimeUnit.SECONDS).build();
    }

    CacheControl(Builder builder) {
        this.noCache = builder.noCache;
        this.noStore = builder.noStore;
        this.maxAgeSeconds = builder.maxAgeSeconds;
        this.sMaxAgeSeconds = -1;
        this.isPrivate = false;
        this.isPublic = false;
        this.mustRevalidate = false;
        this.maxStaleSeconds = builder.maxStaleSeconds;
        this.minFreshSeconds = builder.minFreshSeconds;
        this.onlyIfCached = builder.onlyIfCached;
        this.noTransform = builder.noTransform;
    }

    private CacheControl(boolean bl, boolean bl2, int n, int n2, boolean bl3, boolean bl4, boolean bl5, int n3, int n4, boolean bl6, boolean bl7, String string2) {
        this.noCache = bl;
        this.noStore = bl2;
        this.maxAgeSeconds = n;
        this.sMaxAgeSeconds = n2;
        this.isPrivate = bl3;
        this.isPublic = bl4;
        this.mustRevalidate = bl5;
        this.maxStaleSeconds = n3;
        this.minFreshSeconds = n4;
        this.onlyIfCached = bl6;
        this.noTransform = bl7;
        this.headerValue = string2;
    }

    private String headerValue() {
        StringBuilder stringBuilder = new StringBuilder();
        if (this.noCache) {
            stringBuilder.append("no-cache, ");
        }
        if (this.noStore) {
            stringBuilder.append("no-store, ");
        }
        if (this.maxAgeSeconds != -1) {
            stringBuilder.append("max-age=").append(this.maxAgeSeconds).append(", ");
        }
        if (this.sMaxAgeSeconds != -1) {
            stringBuilder.append("s-maxage=").append(this.sMaxAgeSeconds).append(", ");
        }
        if (this.isPrivate) {
            stringBuilder.append("private, ");
        }
        if (this.isPublic) {
            stringBuilder.append("public, ");
        }
        if (this.mustRevalidate) {
            stringBuilder.append("must-revalidate, ");
        }
        if (this.maxStaleSeconds != -1) {
            stringBuilder.append("max-stale=").append(this.maxStaleSeconds).append(", ");
        }
        if (this.minFreshSeconds != -1) {
            stringBuilder.append("min-fresh=").append(this.minFreshSeconds).append(", ");
        }
        if (this.onlyIfCached) {
            stringBuilder.append("only-if-cached, ");
        }
        if (this.noTransform) {
            stringBuilder.append("no-transform, ");
        }
        if (stringBuilder.length() == 0) {
            return "";
        }
        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        return stringBuilder.toString();
    }

    /*
     * Enabled aggressive block sorting
     */
    public static CacheControl parse(Headers headers) {
        boolean bl = false;
        boolean bl2 = false;
        int n = -1;
        int n2 = -1;
        boolean bl3 = false;
        boolean bl4 = false;
        boolean bl5 = false;
        int n3 = -1;
        int n4 = -1;
        boolean bl6 = false;
        boolean bl7 = false;
        boolean bl8 = true;
        String string2 = null;
        int n5 = 0;
        int n6 = headers.size();
        do {
            boolean bl9;
            boolean bl10;
            int n7;
            boolean bl11;
            String string3;
            boolean bl12;
            boolean bl13;
            boolean bl14;
            boolean bl15;
            int n8;
            boolean bl16;
            int n9;
            int n10;
            block22: {
                String string4;
                String string5;
                block21: {
                    block19: {
                        block20: {
                            if (n5 >= n6) break block19;
                            string5 = headers.name(n5);
                            string4 = headers.value(n5);
                            if (!string5.equalsIgnoreCase("Cache-Control")) break block20;
                            if (string2 != null) {
                                bl8 = false;
                            } else {
                                string2 = string4;
                            }
                            break block21;
                        }
                        bl14 = bl;
                        bl16 = bl2;
                        n9 = n;
                        n10 = n2;
                        bl10 = bl3;
                        bl12 = bl4;
                        bl11 = bl5;
                        n7 = n3;
                        n8 = n4;
                        bl15 = bl6;
                        bl13 = bl7;
                        string3 = string2;
                        bl9 = bl8;
                        if (!string5.equalsIgnoreCase("Pragma")) break block22;
                        bl8 = false;
                        break block21;
                    }
                    if (!bl8) {
                        string2 = null;
                    }
                    return new CacheControl(bl, bl2, n, n2, bl3, bl4, bl5, n3, n4, bl6, bl7, string2);
                }
                int n11 = 0;
                do {
                    bl14 = bl;
                    bl16 = bl2;
                    n9 = n;
                    n10 = n2;
                    bl10 = bl3;
                    bl12 = bl4;
                    bl11 = bl5;
                    n7 = n3;
                    n8 = n4;
                    bl15 = bl6;
                    bl13 = bl7;
                    string3 = string2;
                    bl9 = bl8;
                    if (n11 >= string4.length()) break;
                    n9 = HttpHeaders.skipUntil(string4, n11, "=,;");
                    string5 = string4.substring(n11, n9).trim();
                    if (n9 == string4.length() || string4.charAt(n9) == ',' || string4.charAt(n9) == ';') {
                        ++n9;
                        string3 = null;
                    } else {
                        n11 = HttpHeaders.skipWhitespace(string4, n9 + 1);
                        if (n11 < string4.length() && string4.charAt(n11) == '\"') {
                            n9 = n11 + 1;
                            n11 = HttpHeaders.skipUntil(string4, n9, "\"");
                            string3 = string4.substring(n9, n11);
                            n9 = n11 + 1;
                        } else {
                            n9 = HttpHeaders.skipUntil(string4, n11, ",;");
                            string3 = string4.substring(n11, n9).trim();
                        }
                    }
                    if ("no-cache".equalsIgnoreCase(string5)) {
                        bl = true;
                        n11 = n9;
                        continue;
                    }
                    if ("no-store".equalsIgnoreCase(string5)) {
                        bl2 = true;
                        n11 = n9;
                        continue;
                    }
                    if ("max-age".equalsIgnoreCase(string5)) {
                        n = HttpHeaders.parseSeconds(string3, -1);
                        n11 = n9;
                        continue;
                    }
                    if ("s-maxage".equalsIgnoreCase(string5)) {
                        n2 = HttpHeaders.parseSeconds(string3, -1);
                        n11 = n9;
                        continue;
                    }
                    if ("private".equalsIgnoreCase(string5)) {
                        bl3 = true;
                        n11 = n9;
                        continue;
                    }
                    if ("public".equalsIgnoreCase(string5)) {
                        bl4 = true;
                        n11 = n9;
                        continue;
                    }
                    if ("must-revalidate".equalsIgnoreCase(string5)) {
                        bl5 = true;
                        n11 = n9;
                        continue;
                    }
                    if ("max-stale".equalsIgnoreCase(string5)) {
                        n3 = HttpHeaders.parseSeconds(string3, Integer.MAX_VALUE);
                        n11 = n9;
                        continue;
                    }
                    if ("min-fresh".equalsIgnoreCase(string5)) {
                        n4 = HttpHeaders.parseSeconds(string3, -1);
                        n11 = n9;
                        continue;
                    }
                    if ("only-if-cached".equalsIgnoreCase(string5)) {
                        bl6 = true;
                        n11 = n9;
                        continue;
                    }
                    n11 = n9;
                    if (!"no-transform".equalsIgnoreCase(string5)) continue;
                    bl7 = true;
                    n11 = n9;
                } while (true);
            }
            ++n5;
            bl = bl14;
            bl2 = bl16;
            n = n9;
            n2 = n10;
            bl3 = bl10;
            bl4 = bl12;
            bl5 = bl11;
            n3 = n7;
            n4 = n8;
            bl6 = bl15;
            bl7 = bl13;
            string2 = string3;
            bl8 = bl9;
        } while (true);
    }

    public boolean isPrivate() {
        return this.isPrivate;
    }

    public boolean isPublic() {
        return this.isPublic;
    }

    public int maxAgeSeconds() {
        return this.maxAgeSeconds;
    }

    public int maxStaleSeconds() {
        return this.maxStaleSeconds;
    }

    public int minFreshSeconds() {
        return this.minFreshSeconds;
    }

    public boolean mustRevalidate() {
        return this.mustRevalidate;
    }

    public boolean noCache() {
        return this.noCache;
    }

    public boolean noStore() {
        return this.noStore;
    }

    public boolean onlyIfCached() {
        return this.onlyIfCached;
    }

    public String toString() {
        String string2 = this.headerValue;
        if (string2 != null) {
            return string2;
        }
        this.headerValue = string2 = this.headerValue();
        return string2;
    }

    public static final class Builder {
        int maxAgeSeconds = -1;
        int maxStaleSeconds = -1;
        int minFreshSeconds = -1;
        boolean noCache;
        boolean noStore;
        boolean noTransform;
        boolean onlyIfCached;

        public CacheControl build() {
            return new CacheControl(this);
        }

        /*
         * Enabled aggressive block sorting
         */
        public Builder maxStale(int n, TimeUnit timeUnit) {
            if (n < 0) {
                throw new IllegalArgumentException("maxStale < 0: " + n);
            }
            long l = timeUnit.toSeconds(n);
            n = l > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int)l;
            this.maxStaleSeconds = n;
            return this;
        }

        public Builder noCache() {
            this.noCache = true;
            return this;
        }

        public Builder onlyIfCached() {
            this.onlyIfCached = true;
            return this;
        }
    }

}

