/*
 * Decompiled with CFR 0.147.
 */
package okhttp3.internal.http;

import java.net.Proxy;
import okhttp3.HttpUrl;
import okhttp3.Request;

public final class RequestLine {
    /*
     * Enabled aggressive block sorting
     */
    public static String get(Request request, Proxy.Type type) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(request.method());
        stringBuilder.append(' ');
        if (RequestLine.includeAuthorityInRequestLine(request, type)) {
            stringBuilder.append(request.url());
        } else {
            stringBuilder.append(RequestLine.requestPath(request.url()));
        }
        stringBuilder.append(" HTTP/1.1");
        return stringBuilder.toString();
    }

    private static boolean includeAuthorityInRequestLine(Request request, Proxy.Type type) {
        return !request.isHttps() && type == Proxy.Type.HTTP;
    }

    public static String requestPath(HttpUrl object) {
        String string2 = ((HttpUrl)object).encodedPath();
        String string3 = ((HttpUrl)object).encodedQuery();
        object = string2;
        if (string3 != null) {
            object = string2 + '?' + string3;
        }
        return object;
    }
}

