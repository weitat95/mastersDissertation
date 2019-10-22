/*
 * Decompiled with CFR 0.147.
 */
package okhttp3.internal.http;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;

public final class HttpHeaders {
    private static final Pattern PARAMETER = Pattern.compile(" +([^ \"=]*)=(:?\"([^\"]*)\"|([^ \"=]*)) *(:?,|$)");

    public static long contentLength(Headers headers) {
        return HttpHeaders.stringToLong(headers.get("Content-Length"));
    }

    public static long contentLength(Response response) {
        return HttpHeaders.contentLength(response.headers());
    }

    /*
     * Enabled aggressive block sorting
     */
    public static boolean hasBody(Response response) {
        block5: {
            block4: {
                if (response.request().method().equals("HEAD")) break block4;
                int n = response.code();
                if ((n < 100 || n >= 200) && n != 204 && n != 304) {
                    return true;
                }
                if (HttpHeaders.contentLength(response) != -1L || "chunked".equalsIgnoreCase(response.header("Transfer-Encoding"))) break block5;
            }
            return false;
        }
        return true;
    }

    public static int parseSeconds(String string2, int n) {
        long l;
        block3: {
            try {
                l = Long.parseLong(string2);
                if (l > Integer.MAX_VALUE) {
                    return Integer.MAX_VALUE;
                }
                if (l >= 0L) break block3;
                return 0;
            }
            catch (NumberFormatException numberFormatException) {
                return n;
            }
        }
        return (int)l;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void receiveHeaders(CookieJar cookieJar, HttpUrl httpUrl, Headers list) {
        if (cookieJar == CookieJar.NO_COOKIES || (list = Cookie.parseAll(httpUrl, list)).isEmpty()) {
            return;
        }
        cookieJar.saveFromResponse(httpUrl, list);
    }

    public static int skipUntil(String string2, int n, String string3) {
        while (n < string2.length() && string3.indexOf(string2.charAt(n)) == -1) {
            ++n;
        }
        return n;
    }

    public static int skipWhitespace(String string2, int n) {
        char c;
        while (n < string2.length() && ((c = string2.charAt(n)) == ' ' || c == '\t')) {
            ++n;
        }
        return n;
    }

    private static long stringToLong(String string2) {
        if (string2 == null) {
            return -1L;
        }
        try {
            long l = Long.parseLong(string2);
            return l;
        }
        catch (NumberFormatException numberFormatException) {
            return -1L;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public static Set<String> varyFields(Headers headers) {
        Set<String> set = Collections.emptySet();
        int n = 0;
        int n2 = headers.size();
        while (n < n2) {
            if ("Vary".equalsIgnoreCase(headers.name(n))) {
                String[] arrstring = headers.value(n);
                Set<String> set2 = set;
                if (set.isEmpty()) {
                    set2 = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
                }
                arrstring = arrstring.split(",");
                int n3 = arrstring.length;
                int n4 = 0;
                do {
                    set = set2;
                    if (n4 >= n3) break;
                    set2.add(arrstring[n4].trim());
                    ++n4;
                } while (true);
            }
            ++n;
        }
        return set;
    }

    public static Headers varyHeaders(Headers headers, Headers object) {
        if ((object = HttpHeaders.varyFields((Headers)object)).isEmpty()) {
            return new Headers.Builder().build();
        }
        Headers.Builder builder = new Headers.Builder();
        int n = headers.size();
        for (int i = 0; i < n; ++i) {
            String string2 = headers.name(i);
            if (!object.contains(string2)) continue;
            builder.add(string2, headers.value(i));
        }
        return builder.build();
    }

    public static Headers varyHeaders(Response response) {
        return HttpHeaders.varyHeaders(response.networkResponse().request().headers(), response.headers());
    }
}

