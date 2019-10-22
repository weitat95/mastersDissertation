/*
 * Decompiled with CFR 0.147.
 */
package okhttp3;

import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import okhttp3.internal.Util;
import okio.Buffer;

public final class HttpUrl {
    private static final char[] HEX_DIGITS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private final String fragment;
    final String host;
    private final String password;
    private final List<String> pathSegments;
    final int port;
    private final List<String> queryNamesAndValues;
    final String scheme;
    private final String url;
    private final String username;

    /*
     * Enabled aggressive block sorting
     */
    HttpUrl(Builder builder) {
        Object var3_2 = null;
        this.scheme = builder.scheme;
        this.username = HttpUrl.percentDecode(builder.encodedUsername, false);
        this.password = HttpUrl.percentDecode(builder.encodedPassword, false);
        this.host = builder.host;
        this.port = builder.effectivePort();
        this.pathSegments = this.percentDecode(builder.encodedPathSegments, false);
        List<String> list = builder.encodedQueryNamesAndValues != null ? this.percentDecode(builder.encodedQueryNamesAndValues, true) : null;
        this.queryNamesAndValues = list;
        list = var3_2;
        if (builder.encodedFragment != null) {
            list = HttpUrl.percentDecode(builder.encodedFragment, false);
        }
        this.fragment = list;
        this.url = builder.toString();
    }

    static String canonicalize(String string2, int n, int n2, String string3, boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        int n3;
        for (int i = n; i < n2; i += Character.charCount(n3)) {
            n3 = string2.codePointAt(i);
            if (!(n3 < 32 || n3 == 127 || n3 >= 128 && bl4 || string3.indexOf(n3) != -1 || n3 == 37 && (!bl || bl2 && !HttpUrl.percentEncoded(string2, i, n2))) && (n3 != 43 || !bl3)) continue;
            Buffer buffer = new Buffer();
            buffer.writeUtf8(string2, n, i);
            HttpUrl.canonicalize(buffer, string2, i, n2, string3, bl, bl2, bl3, bl4);
            return buffer.readUtf8();
        }
        return string2.substring(n, n2);
    }

    static String canonicalize(String string2, String string3, boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        return HttpUrl.canonicalize(string2, 0, string2.length(), string3, bl, bl2, bl3, bl4);
    }

    /*
     * Unable to fully structure code
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static void canonicalize(Buffer var0, String var1_1, int var2_2, int var3_3, String var4_4, boolean var5_5, boolean var6_6, boolean var7_7, boolean var8_8) {
        var11_9 = null;
        block0 : while (var2_2 < var3_3) {
            block12: {
                block10: {
                    block11: {
                        block8: {
                            block9: {
                                var9_10 = var1_1.codePointAt(var2_2);
                                if (!var5_5) break block8;
                                var13_13 = var11_9;
                                if (var9_10 == 9) break block9;
                                var13_13 = var11_9;
                                if (var9_10 == 10) break block9;
                                var13_13 = var11_9;
                                if (var9_10 == 12) break block9;
                                if (var9_10 != 13) break block8;
                                var13_13 = var11_9;
                            }
lbl14:
                            // 4 sources
                            do {
                                var2_2 += Character.charCount(var9_10);
                                var11_9 = var13_13;
                                continue block0;
                                break;
                            } while (true);
                        }
                        if (var9_10 != 43 || !var7_7) break block10;
                        if (!var5_5) break block11;
                        var12_12 = "+";
lbl22:
                        // 2 sources
                        do {
                            var0.writeUtf8((String)var12_12);
                            var13_13 = var11_9;
                            ** GOTO lbl14
                            break;
                        } while (true);
                    }
                    var12_12 = "%2B";
                    ** continue;
                }
                if (var9_10 >= 32 && var9_10 != 127 && (var9_10 < 128 || !var8_8) && var4_4.indexOf(var9_10) == -1 && (var9_10 != 37 || var5_5 && (!var6_6 || HttpUrl.percentEncoded(var1_1, var2_2, var3_3)))) break block12;
                var12_12 = var11_9;
                if (var11_9 == null) {
                    var12_12 = new Buffer();
                }
                var12_12.writeUtf8CodePoint(var9_10);
                do {
                    var13_13 = var12_12;
                    if (var12_12.exhausted()) ** GOTO lbl14
                    var10_11 = var12_12.readByte() & 255;
                    var0.writeByte(37);
                    var0.writeByte(HttpUrl.HEX_DIGITS[var10_11 >> 4 & 15]);
                    var0.writeByte(HttpUrl.HEX_DIGITS[var10_11 & 15]);
                } while (true);
            }
            var0.writeUtf8CodePoint(var9_10);
            var13_13 = var11_9;
            ** continue;
        }
    }

    static int decodeHexDigit(char c) {
        if (c >= '0' && c <= '9') {
            return c - 48;
        }
        if (c >= 'a' && c <= 'f') {
            return c - 97 + 10;
        }
        if (c >= 'A' && c <= 'F') {
            return c - 65 + 10;
        }
        return -1;
    }

    public static int defaultPort(String string2) {
        if (string2.equals("http")) {
            return 80;
        }
        if (string2.equals("https")) {
            return 443;
        }
        return -1;
    }

    static void namesAndValuesToQueryString(StringBuilder stringBuilder, List<String> list) {
        int n = list.size();
        for (int i = 0; i < n; i += 2) {
            String string2 = list.get(i);
            String string3 = list.get(i + 1);
            if (i > 0) {
                stringBuilder.append('&');
            }
            stringBuilder.append(string2);
            if (string3 == null) continue;
            stringBuilder.append('=');
            stringBuilder.append(string3);
        }
    }

    public static HttpUrl parse(String string2) {
        HttpUrl httpUrl = null;
        Builder builder = new Builder();
        if (builder.parse(null, string2) == Builder.ParseResult.SUCCESS) {
            httpUrl = builder.build();
        }
        return httpUrl;
    }

    static void pathSegmentsToString(StringBuilder stringBuilder, List<String> list) {
        int n = list.size();
        for (int i = 0; i < n; ++i) {
            stringBuilder.append('/');
            stringBuilder.append(list.get(i));
        }
    }

    static String percentDecode(String string2, int n, int n2, boolean bl) {
        for (int i = n; i < n2; ++i) {
            char c = string2.charAt(i);
            if (c != '%' && (c != '+' || !bl)) continue;
            Buffer buffer = new Buffer();
            buffer.writeUtf8(string2, n, i);
            HttpUrl.percentDecode(buffer, string2, i, n2, bl);
            return buffer.readUtf8();
        }
        return string2.substring(n, n2);
    }

    static String percentDecode(String string2, boolean bl) {
        return HttpUrl.percentDecode(string2, 0, string2.length(), bl);
    }

    /*
     * Enabled aggressive block sorting
     */
    private List<String> percentDecode(List<String> list, boolean bl) {
        int n = list.size();
        ArrayList<String> arrayList = new ArrayList<String>(n);
        int n2 = 0;
        while (n2 < n) {
            String string2 = list.get(n2);
            string2 = string2 != null ? HttpUrl.percentDecode(string2, bl) : null;
            arrayList.add(string2);
            ++n2;
        }
        return Collections.unmodifiableList(arrayList);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    static void percentDecode(Buffer var0, String var1_1, int var2_2, int var3_3, boolean var4_4) {
        while (var2_2 < var3_3) {
            block4: {
                block3: {
                    var5_5 = var1_1.codePointAt(var2_2);
                    if (var5_5 != 37 || var2_2 + 2 >= var3_3) break block3;
                    var6_6 = HttpUrl.decodeHexDigit(var1_1.charAt(var2_2 + 1));
                    var7_7 = HttpUrl.decodeHexDigit(var1_1.charAt(var2_2 + 2));
                    if (var6_6 == -1 || var7_7 == -1) ** GOTO lbl-1000
                    var0.writeByte((var6_6 << 4) + var7_7);
                    var2_2 += 2;
                    break block4;
                }
                if (var5_5 == 43 && var4_4) {
                    var0.writeByte(32);
                } else lbl-1000:
                // 2 sources
                {
                    var0.writeUtf8CodePoint(var5_5);
                }
            }
            var2_2 += Character.charCount(var5_5);
        }
    }

    static boolean percentEncoded(String string2, int n, int n2) {
        return n + 2 < n2 && string2.charAt(n) == '%' && HttpUrl.decodeHexDigit(string2.charAt(n + 1)) != -1 && HttpUrl.decodeHexDigit(string2.charAt(n + 2)) != -1;
    }

    /*
     * Enabled aggressive block sorting
     */
    static List<String> queryStringToNamesAndValues(String string2) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n = 0;
        while (n <= string2.length()) {
            int n2;
            int n3 = n2 = string2.indexOf(38, n);
            if (n2 == -1) {
                n3 = string2.length();
            }
            if ((n2 = string2.indexOf(61, n)) == -1 || n2 > n3) {
                arrayList.add(string2.substring(n, n3));
                arrayList.add(null);
            } else {
                arrayList.add(string2.substring(n, n2));
                arrayList.add(string2.substring(n2 + 1, n3));
            }
            n = n3 + 1;
        }
        return arrayList;
    }

    public String encodedFragment() {
        if (this.fragment == null) {
            return null;
        }
        int n = this.url.indexOf(35);
        return this.url.substring(n + 1);
    }

    public String encodedPassword() {
        if (this.password.isEmpty()) {
            return "";
        }
        int n = this.url.indexOf(58, this.scheme.length() + 3);
        int n2 = this.url.indexOf(64);
        return this.url.substring(n + 1, n2);
    }

    public String encodedPath() {
        int n = this.url.indexOf(47, this.scheme.length() + 3);
        int n2 = Util.delimiterOffset(this.url, n, this.url.length(), "?#");
        return this.url.substring(n, n2);
    }

    public List<String> encodedPathSegments() {
        int n = this.url.indexOf(47, this.scheme.length() + 3);
        int n2 = Util.delimiterOffset(this.url, n, this.url.length(), "?#");
        ArrayList<String> arrayList = new ArrayList<String>();
        while (n < n2) {
            int n3 = n + 1;
            n = Util.delimiterOffset(this.url, n3, n2, '/');
            arrayList.add(this.url.substring(n3, n));
        }
        return arrayList;
    }

    public String encodedQuery() {
        if (this.queryNamesAndValues == null) {
            return null;
        }
        int n = this.url.indexOf(63) + 1;
        int n2 = Util.delimiterOffset(this.url, n + 1, this.url.length(), '#');
        return this.url.substring(n, n2);
    }

    public String encodedUsername() {
        if (this.username.isEmpty()) {
            return "";
        }
        int n = this.scheme.length() + 3;
        int n2 = Util.delimiterOffset(this.url, n, this.url.length(), ":@");
        return this.url.substring(n, n2);
    }

    public boolean equals(Object object) {
        return object instanceof HttpUrl && ((HttpUrl)object).url.equals(this.url);
    }

    public int hashCode() {
        return this.url.hashCode();
    }

    public String host() {
        return this.host;
    }

    public boolean isHttps() {
        return this.scheme.equals("https");
    }

    /*
     * Enabled aggressive block sorting
     */
    public Builder newBuilder() {
        Builder builder = new Builder();
        builder.scheme = this.scheme;
        builder.encodedUsername = this.encodedUsername();
        builder.encodedPassword = this.encodedPassword();
        builder.host = this.host;
        int n = this.port != HttpUrl.defaultPort(this.scheme) ? this.port : -1;
        builder.port = n;
        builder.encodedPathSegments.clear();
        builder.encodedPathSegments.addAll(this.encodedPathSegments());
        builder.encodedQuery(this.encodedQuery());
        builder.encodedFragment = this.encodedFragment();
        return builder;
    }

    public Builder newBuilder(String string2) {
        Builder builder = new Builder();
        if (builder.parse(this, string2) == Builder.ParseResult.SUCCESS) {
            return builder;
        }
        return null;
    }

    public List<String> pathSegments() {
        return this.pathSegments;
    }

    public int port() {
        return this.port;
    }

    public String query() {
        if (this.queryNamesAndValues == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        HttpUrl.namesAndValuesToQueryString(stringBuilder, this.queryNamesAndValues);
        return stringBuilder.toString();
    }

    public String redact() {
        return this.newBuilder("/...").username("").password("").build().toString();
    }

    public HttpUrl resolve(String object) {
        if ((object = this.newBuilder((String)object)) != null) {
            return ((Builder)object).build();
        }
        return null;
    }

    public String scheme() {
        return this.scheme;
    }

    public String toString() {
        return this.url;
    }

    public URI uri() {
        Object object = this.newBuilder().reencodeForUri().toString();
        try {
            URI uRI = new URI((String)object);
            return uRI;
        }
        catch (URISyntaxException uRISyntaxException) {
            try {
                object = URI.create(((String)object).replaceAll("[\\u0000-\\u001F\\u007F-\\u009F\\p{javaWhitespace}]", ""));
                return object;
            }
            catch (Exception exception) {
                throw new RuntimeException(uRISyntaxException);
            }
        }
    }

    public static final class Builder {
        String encodedFragment;
        String encodedPassword = "";
        final List<String> encodedPathSegments = new ArrayList<String>();
        List<String> encodedQueryNamesAndValues;
        String encodedUsername = "";
        String host;
        int port = -1;
        String scheme;

        public Builder() {
            this.encodedPathSegments.add("");
        }

        /*
         * Enabled aggressive block sorting
         */
        private static String canonicalizeHost(String object, int n, int n2) {
            if (!((String)(object = HttpUrl.percentDecode((String)object, n, n2, false))).contains(":")) {
                return Util.domainToAscii((String)object);
            }
            if ((object = ((String)object).startsWith("[") && ((String)object).endsWith("]") ? Builder.decodeIpv6((String)object, 1, ((String)object).length() - 1) : Builder.decodeIpv6((String)object, 0, ((String)object).length())) == null) {
                return null;
            }
            if (((Object)(object = ((InetAddress)object).getAddress())).length == 16) {
                return Builder.inet6AddressToAscii((byte[])object);
            }
            throw new AssertionError();
        }

        /*
         * Enabled aggressive block sorting
         */
        private static boolean decodeIpv4Suffix(String string2, int n, int n2, byte[] arrby, int n3) {
            int n4 = n3;
            int n5 = n;
            block0: do {
                if (n5 < n2) {
                    if (n4 == arrby.length) break;
                    n = n5;
                    if (n4 != n3) {
                        if (string2.charAt(n5) != '.') break;
                        n = n5 + 1;
                    }
                } else {
                    if (n4 != n3 + 4) break;
                    return true;
                }
                int n6 = 0;
                n5 = n;
                do {
                    char c;
                    if (n5 >= n2 || (c = string2.charAt(n5)) < '0' || c > '9') {
                        if (n5 - n == 0) break block0;
                        arrby[n4] = (byte)n6;
                        ++n4;
                        continue block0;
                    }
                    if (n6 == 0 && n != n5 || (n6 = n6 * 10 + c - 48) > 255) break block0;
                    ++n5;
                } while (true);
                break;
            } while (true);
            return false;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        private static InetAddress decodeIpv6(String var0, int var1_2, int var2_3) {
            var9_4 = new byte[16];
            var3_5 = 0;
            var4_6 = -1;
            var8_7 = -1;
            var5_8 = var1_2;
            do {
                block16: {
                    block15: {
                        var1_2 = var3_5;
                        var6_9 = var4_6;
                        if (var5_8 >= var2_3) ** GOTO lbl38
                        if (var3_5 == var9_4.length) {
                            return null;
                        }
                        if (var5_8 + 2 > var2_3 || !var0.regionMatches(var5_8, "::", 0, 2)) break block15;
                        if (var4_6 != -1) {
                            return null;
                        }
                        var4_6 = var3_5 += 2;
                        var7_10 = var3_5;
                        var6_9 = var4_6;
                        var1_2 = var5_8 += 2;
                        if (var5_8 != var2_3) break block16;
                        var6_9 = var4_6;
                        var1_2 = var3_5;
                        ** GOTO lbl38
                    }
                    var7_10 = var3_5;
                    var6_9 = var4_6;
                    var1_2 = var5_8;
                    if (var3_5 == 0) break block16;
                    if (var0.regionMatches(var5_8, ":", 0, 1)) {
                        var1_2 = var5_8 + 1;
                        var6_9 = var4_6;
                        var7_10 = var3_5;
                    } else {
                        if (var0.regionMatches(var5_8, ".", 0, 1) == false) return null;
                        if (!Builder.decodeIpv4Suffix((String)var0, var8_7, var2_3, var9_4, var3_5 - 2)) {
                            return null;
                        }
                        var1_2 = var3_5 + 2;
                        var6_9 = var4_6;
lbl38:
                        // 3 sources
                        if (var1_2 != var9_4.length) {
                            if (var6_9 == -1) {
                                return null;
                            }
                            System.arraycopy(var9_4, var6_9, var9_4, var9_4.length - (var1_2 - var6_9), var1_2 - var6_9);
                            Arrays.fill(var9_4, var6_9, var9_4.length - var1_2 + var6_9, (byte)0);
                        }
                        try {
                            return InetAddress.getByAddress(var9_4);
                        }
                        catch (UnknownHostException var0_1) {
                            throw new AssertionError();
                        }
                    }
                }
                var3_5 = 0;
                var5_8 = var1_2;
                do {
                    if (var1_2 >= var2_3 || (var4_6 = HttpUrl.decodeHexDigit(var0.charAt(var1_2))) == -1) {
                        var4_6 = var1_2 - var5_8;
                        if (var4_6 == 0) return null;
                        if (var4_6 <= 4) break;
                        return null;
                    }
                    var3_5 = (var3_5 << 4) + var4_6;
                    ++var1_2;
                } while (true);
                var8_7 = var7_10 + 1;
                var9_4[var7_10] = (byte)(var3_5 >>> 8 & 255);
                var4_6 = var8_7 + 1;
                var9_4[var8_7] = (byte)(var3_5 & 255);
                var3_5 = var4_6;
                var4_6 = var6_9;
                var8_7 = var5_8;
                var5_8 = var1_2;
            } while (true);
        }

        private static String inet6AddressToAscii(byte[] arrby) {
            int n;
            int n2 = -1;
            int n3 = 0;
            int n4 = 0;
            while (n4 < arrby.length) {
                int n5;
                n = n4;
                while ((n5 = n) < 16 && arrby[n5] == 0 && arrby[n5 + 1] == 0) {
                    n = n5 + 2;
                }
                int n6 = n5 - n4;
                n = n3;
                if (n6 > n3) {
                    n = n6;
                    n2 = n4;
                }
                n4 = n5 + 2;
                n3 = n;
            }
            Buffer buffer = new Buffer();
            n4 = 0;
            while (n4 < arrby.length) {
                if (n4 == n2) {
                    buffer.writeByte(58);
                    n4 = n = n4 + n3;
                    if (n != 16) continue;
                    buffer.writeByte(58);
                    n4 = n;
                    continue;
                }
                if (n4 > 0) {
                    buffer.writeByte(58);
                }
                buffer.writeHexadecimalUnsignedLong((arrby[n4] & 0xFF) << 8 | arrby[n4 + 1] & 0xFF);
                n4 += 2;
            }
            return buffer.readUtf8();
        }

        private boolean isDot(String string2) {
            return string2.equals(".") || string2.equalsIgnoreCase("%2e");
        }

        private boolean isDotDot(String string2) {
            return string2.equals("..") || string2.equalsIgnoreCase("%2e.") || string2.equalsIgnoreCase(".%2e") || string2.equalsIgnoreCase("%2e%2e");
        }

        private static int parsePort(String string2, int n, int n2) {
            try {
                n = Integer.parseInt(HttpUrl.canonicalize(string2, n, n2, "", false, false, false, true));
                if (n > 0 && n <= 65535) {
                    return n;
                }
                return -1;
            }
            catch (NumberFormatException numberFormatException) {
                return -1;
            }
        }

        private void pop() {
            if (this.encodedPathSegments.remove(this.encodedPathSegments.size() - 1).isEmpty() && !this.encodedPathSegments.isEmpty()) {
                this.encodedPathSegments.set(this.encodedPathSegments.size() - 1, "");
                return;
            }
            this.encodedPathSegments.add("");
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        private static int portColonOffset(String var0, int var1_1, int var2_2) {
            block4: do {
                if (var1_1 >= var2_2) {
                    return var2_2;
                }
                var3_3 = var1_1;
                var4_4 = var1_1;
                switch (var0.charAt(var1_1)) {
                    default: {
                        var3_3 = var1_1;
                        ** GOTO lbl17
                    }
                    case '[': {
                        do {
                            var3_3 = var1_1 = var3_3 + 1;
                            if (var1_1 >= var2_2) ** GOTO lbl17
                            var3_3 = var1_1;
                        } while (var0.charAt(var1_1) != ']');
                        var3_3 = var1_1;
lbl17:
                        // 3 sources
                        var1_1 = var3_3 + 1;
                        continue block4;
                    }
                    case ':': 
                }
                break;
            } while (true);
            return var4_4;
        }

        /*
         * Enabled aggressive block sorting
         */
        private void push(String string2, int n, int n2, boolean bl, boolean bl2) {
            block7: {
                block6: {
                    if (this.isDot(string2 = HttpUrl.canonicalize(string2, n, n2, " \"<>^`{}|/\\?#", bl2, false, false, true))) break block6;
                    if (this.isDotDot(string2)) {
                        this.pop();
                        return;
                    }
                    if (this.encodedPathSegments.get(this.encodedPathSegments.size() - 1).isEmpty()) {
                        this.encodedPathSegments.set(this.encodedPathSegments.size() - 1, string2);
                    } else {
                        this.encodedPathSegments.add(string2);
                    }
                    if (bl) break block7;
                }
                return;
            }
            this.encodedPathSegments.add("");
        }

        /*
         * Enabled aggressive block sorting
         */
        private void resolvePath(String string2, int n, int n2) {
            if (n != n2) {
                int n3 = string2.charAt(n);
                if (n3 == 47 || n3 == 92) {
                    this.encodedPathSegments.clear();
                    this.encodedPathSegments.add("");
                    ++n;
                } else {
                    this.encodedPathSegments.set(this.encodedPathSegments.size() - 1, "");
                }
                while (n < n2) {
                    n3 = Util.delimiterOffset(string2, n, n2, "/\\");
                    boolean bl = n3 < n2;
                    this.push(string2, n, n3, bl, true);
                    n = n3;
                    if (!bl) continue;
                    n = n3 + 1;
                }
            }
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        private static int schemeDelimiterOffset(String string2, int n, int n2) {
            char c;
            block5: {
                if (n2 - n < 2) {
                    return -1;
                }
                c = string2.charAt(n);
                if (c < 'a' || c > 'z') {
                    if (c < 'A') return -1;
                    if (c > 'Z') {
                        return -1;
                    }
                }
                ++n;
                while (n < n2) {
                    c = string2.charAt(n);
                    if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c >= '0' && c <= '9' || c == '+' || c == '-' || c == '.') {
                        ++n;
                        continue;
                    }
                    break block5;
                }
                return -1;
            }
            if (c == ':') return n;
            return -1;
        }

        private static int slashCount(String string2, int n, int n2) {
            char c;
            int n3 = 0;
            while (n < n2 && ((c = string2.charAt(n)) == '\\' || c == '/')) {
                ++n3;
                ++n;
            }
            return n3;
        }

        /*
         * Enabled aggressive block sorting
         */
        public Builder addEncodedQueryParameter(String string2, String string3) {
            if (string2 == null) {
                throw new NullPointerException("encodedName == null");
            }
            if (this.encodedQueryNamesAndValues == null) {
                this.encodedQueryNamesAndValues = new ArrayList<String>();
            }
            this.encodedQueryNamesAndValues.add(HttpUrl.canonicalize(string2, " \"'<>#&=", true, false, true, true));
            List<String> list = this.encodedQueryNamesAndValues;
            string2 = string3 != null ? HttpUrl.canonicalize(string3, " \"'<>#&=", true, false, true, true) : null;
            list.add(string2);
            return this;
        }

        /*
         * Enabled aggressive block sorting
         */
        public Builder addQueryParameter(String string2, String string3) {
            if (string2 == null) {
                throw new NullPointerException("name == null");
            }
            if (this.encodedQueryNamesAndValues == null) {
                this.encodedQueryNamesAndValues = new ArrayList<String>();
            }
            this.encodedQueryNamesAndValues.add(HttpUrl.canonicalize(string2, " \"'<>#&=", false, false, true, true));
            List<String> list = this.encodedQueryNamesAndValues;
            string2 = string3 != null ? HttpUrl.canonicalize(string3, " \"'<>#&=", false, false, true, true) : null;
            list.add(string2);
            return this;
        }

        public HttpUrl build() {
            if (this.scheme == null) {
                throw new IllegalStateException("scheme == null");
            }
            if (this.host == null) {
                throw new IllegalStateException("host == null");
            }
            return new HttpUrl(this);
        }

        int effectivePort() {
            if (this.port != -1) {
                return this.port;
            }
            return HttpUrl.defaultPort(this.scheme);
        }

        /*
         * Enabled aggressive block sorting
         */
        public Builder encodedQuery(String list) {
            list = list != null ? HttpUrl.queryStringToNamesAndValues(HttpUrl.canonicalize(list, " \"'<>#", true, false, true, true)) : null;
            this.encodedQueryNamesAndValues = list;
            return this;
        }

        public Builder host(String string2) {
            if (string2 == null) {
                throw new NullPointerException("host == null");
            }
            String string3 = Builder.canonicalizeHost(string2, 0, string2.length());
            if (string3 == null) {
                throw new IllegalArgumentException("unexpected host: " + string2);
            }
            this.host = string3;
            return this;
        }

        /*
         * Enabled aggressive block sorting
         */
        ParseResult parse(HttpUrl object, String string2) {
            int n;
            int n2;
            int n3;
            block25: {
                int n4;
                block22: {
                    boolean bl;
                    int n5;
                    block23: {
                        block24: {
                            n = Util.skipLeadingAsciiWhitespace(string2, 0, string2.length());
                            if (Builder.schemeDelimiterOffset(string2, n, n3 = Util.skipTrailingAsciiWhitespace(string2, n, string2.length())) != -1) {
                                if (string2.regionMatches(true, n, "https:", 0, 6)) {
                                    this.scheme = "https";
                                    n += "https:".length();
                                } else {
                                    if (!string2.regionMatches(true, n, "http:", 0, 5)) {
                                        return ParseResult.UNSUPPORTED_SCHEME;
                                    }
                                    this.scheme = "http";
                                    n += "http:".length();
                                }
                            } else {
                                if (object == null) {
                                    return ParseResult.MISSING_SCHEME;
                                }
                                this.scheme = ((HttpUrl)object).scheme;
                            }
                            bl = false;
                            n2 = 0;
                            n5 = Builder.slashCount(string2, n, n3);
                            if (n5 >= 2 || object == null || !((HttpUrl)object).scheme.equals(this.scheme)) break block23;
                            this.encodedUsername = ((HttpUrl)object).encodedUsername();
                            this.encodedPassword = ((HttpUrl)object).encodedPassword();
                            this.host = ((HttpUrl)object).host;
                            this.port = ((HttpUrl)object).port;
                            this.encodedPathSegments.clear();
                            this.encodedPathSegments.addAll(((HttpUrl)object).encodedPathSegments());
                            if (n == n3) break block24;
                            n2 = n;
                            if (string2.charAt(n) != '#') break block25;
                        }
                        this.encodedQuery(((HttpUrl)object).encodedQuery());
                        n2 = n;
                        break block25;
                    }
                    n5 = n + n5;
                    n = n2;
                    n2 = n5;
                    block4: do {
                        n5 = (n4 = Util.delimiterOffset(string2, n2, n3, "@/\\?#")) != n3 ? (int)string2.charAt(n4) : -1;
                        switch (n5) {
                            default: {
                                continue block4;
                            }
                            case -1: 
                            case 35: 
                            case 47: 
                            case 63: 
                            case 92: {
                                n = Builder.portColonOffset(string2, n2, n4);
                                if (n + 1 >= n4) break block4;
                                this.host = Builder.canonicalizeHost(string2, n2, n);
                                this.port = Builder.parsePort(string2, n + 1, n4);
                                if (this.port == -1) {
                                    return ParseResult.INVALID_PORT;
                                }
                                break block22;
                            }
                            case 64: {
                                if (n == 0) {
                                    n5 = Util.delimiterOffset(string2, n2, n4, ':');
                                    String string3 = HttpUrl.canonicalize(string2, n2, n5, " \"':;<=>@[]^`{}|/\\?#", true, false, false, true);
                                    object = string3;
                                    if (bl) {
                                        object = this.encodedUsername + "%40" + string3;
                                    }
                                    this.encodedUsername = object;
                                    if (n5 != n4) {
                                        n = 1;
                                        this.encodedPassword = HttpUrl.canonicalize(string2, n5 + 1, n4, " \"':;<=>@[]^`{}|/\\?#", true, false, false, true);
                                    }
                                    bl = true;
                                } else {
                                    this.encodedPassword = this.encodedPassword + "%40" + HttpUrl.canonicalize(string2, n2, n4, " \"':;<=>@[]^`{}|/\\?#", true, false, false, true);
                                }
                                n2 = n4 + 1;
                                continue block4;
                            }
                        }
                        break;
                    } while (true);
                    this.host = Builder.canonicalizeHost(string2, n2, n);
                    this.port = HttpUrl.defaultPort(this.scheme);
                }
                if (this.host == null) {
                    return ParseResult.INVALID_HOST;
                }
                n2 = n4;
            }
            n = Util.delimiterOffset(string2, n2, n3, "?#");
            this.resolvePath(string2, n2, n);
            n2 = n;
            if (n < n3) {
                n2 = n;
                if (string2.charAt(n) == '?') {
                    n2 = Util.delimiterOffset(string2, n, n3, '#');
                    this.encodedQueryNamesAndValues = HttpUrl.queryStringToNamesAndValues(HttpUrl.canonicalize(string2, n + 1, n2, " \"'<>#", true, false, true, true));
                }
            }
            if (n2 < n3 && string2.charAt(n2) == '#') {
                this.encodedFragment = HttpUrl.canonicalize(string2, n2 + 1, n3, "", true, false, false, false);
            }
            return ParseResult.SUCCESS;
        }

        public Builder password(String string2) {
            if (string2 == null) {
                throw new NullPointerException("password == null");
            }
            this.encodedPassword = HttpUrl.canonicalize(string2, " \"':;<=>@[]^`{}|/\\?#", false, false, false, true);
            return this;
        }

        public Builder port(int n) {
            if (n <= 0 || n > 65535) {
                throw new IllegalArgumentException("unexpected port: " + n);
            }
            this.port = n;
            return this;
        }

        Builder reencodeForUri() {
            String string2;
            int n;
            int n2 = this.encodedPathSegments.size();
            for (n = 0; n < n2; ++n) {
                string2 = this.encodedPathSegments.get(n);
                this.encodedPathSegments.set(n, HttpUrl.canonicalize(string2, "[]", true, true, false, true));
            }
            if (this.encodedQueryNamesAndValues != null) {
                n2 = this.encodedQueryNamesAndValues.size();
                for (n = 0; n < n2; ++n) {
                    string2 = this.encodedQueryNamesAndValues.get(n);
                    if (string2 == null) continue;
                    this.encodedQueryNamesAndValues.set(n, HttpUrl.canonicalize(string2, "\\^`{|}", true, true, true, true));
                }
            }
            if (this.encodedFragment != null) {
                this.encodedFragment = HttpUrl.canonicalize(this.encodedFragment, " \"#<>\\^`{|}", true, true, false, false);
            }
            return this;
        }

        public Builder scheme(String string2) {
            if (string2 == null) {
                throw new NullPointerException("scheme == null");
            }
            if (string2.equalsIgnoreCase("http")) {
                this.scheme = "http";
                return this;
            }
            if (string2.equalsIgnoreCase("https")) {
                this.scheme = "https";
                return this;
            }
            throw new IllegalArgumentException("unexpected scheme: " + string2);
        }

        /*
         * Enabled aggressive block sorting
         */
        public String toString() {
            int n;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.scheme);
            stringBuilder.append("://");
            if (!this.encodedUsername.isEmpty() || !this.encodedPassword.isEmpty()) {
                stringBuilder.append(this.encodedUsername);
                if (!this.encodedPassword.isEmpty()) {
                    stringBuilder.append(':');
                    stringBuilder.append(this.encodedPassword);
                }
                stringBuilder.append('@');
            }
            if (this.host.indexOf(58) != -1) {
                stringBuilder.append('[');
                stringBuilder.append(this.host);
                stringBuilder.append(']');
            } else {
                stringBuilder.append(this.host);
            }
            if ((n = this.effectivePort()) != HttpUrl.defaultPort(this.scheme)) {
                stringBuilder.append(':');
                stringBuilder.append(n);
            }
            HttpUrl.pathSegmentsToString(stringBuilder, this.encodedPathSegments);
            if (this.encodedQueryNamesAndValues != null) {
                stringBuilder.append('?');
                HttpUrl.namesAndValuesToQueryString(stringBuilder, this.encodedQueryNamesAndValues);
            }
            if (this.encodedFragment != null) {
                stringBuilder.append('#');
                stringBuilder.append(this.encodedFragment);
            }
            return stringBuilder.toString();
        }

        public Builder username(String string2) {
            if (string2 == null) {
                throw new NullPointerException("username == null");
            }
            this.encodedUsername = HttpUrl.canonicalize(string2, " \"':;<=>@[]^`{}|/\\?#", false, false, false, true);
            return this;
        }

        static enum ParseResult {
            SUCCESS,
            MISSING_SCHEME,
            UNSUPPORTED_SCHEME,
            INVALID_PORT,
            INVALID_HOST;

        }

    }

}

