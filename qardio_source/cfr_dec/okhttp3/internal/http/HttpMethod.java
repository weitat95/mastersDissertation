/*
 * Decompiled with CFR 0.147.
 */
package okhttp3.internal.http;

public final class HttpMethod {
    public static boolean invalidatesCache(String string2) {
        return string2.equals("POST") || string2.equals("PATCH") || string2.equals("PUT") || string2.equals("DELETE") || string2.equals("MOVE");
    }

    public static boolean permitsRequestBody(String string2) {
        return HttpMethod.requiresRequestBody(string2) || string2.equals("OPTIONS") || string2.equals("DELETE") || string2.equals("PROPFIND") || string2.equals("MKCOL") || string2.equals("LOCK");
    }

    public static boolean redirectsToGet(String string2) {
        return !string2.equals("PROPFIND");
    }

    public static boolean redirectsWithBody(String string2) {
        return string2.equals("PROPFIND");
    }

    public static boolean requiresRequestBody(String string2) {
        return string2.equals("POST") || string2.equals("PUT") || string2.equals("PATCH") || string2.equals("PROPPATCH") || string2.equals("REPORT");
    }
}

