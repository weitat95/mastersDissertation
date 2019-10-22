/*
 * Decompiled with CFR 0.147.
 */
package okhttp3.internal;

import java.io.Closeable;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.lang.reflect.Array;
import java.net.IDN;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import okhttp3.HttpUrl;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;
import okio.Source;
import okio.Timeout;

public final class Util {
    public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    public static final RequestBody EMPTY_REQUEST;
    public static final ResponseBody EMPTY_RESPONSE;
    public static final String[] EMPTY_STRING_ARRAY;
    public static final TimeZone UTC;
    private static final Charset UTF_16_BE;
    private static final ByteString UTF_16_BE_BOM;
    private static final Charset UTF_16_LE;
    private static final ByteString UTF_16_LE_BOM;
    private static final Charset UTF_32_BE;
    private static final ByteString UTF_32_BE_BOM;
    private static final Charset UTF_32_LE;
    private static final ByteString UTF_32_LE_BOM;
    public static final Charset UTF_8;
    private static final ByteString UTF_8_BOM;
    private static final Pattern VERIFY_AS_IP_ADDRESS;

    static {
        EMPTY_STRING_ARRAY = new String[0];
        EMPTY_RESPONSE = ResponseBody.create(null, EMPTY_BYTE_ARRAY);
        EMPTY_REQUEST = RequestBody.create(null, EMPTY_BYTE_ARRAY);
        UTF_8_BOM = ByteString.decodeHex("efbbbf");
        UTF_16_BE_BOM = ByteString.decodeHex("feff");
        UTF_16_LE_BOM = ByteString.decodeHex("fffe");
        UTF_32_BE_BOM = ByteString.decodeHex("0000ffff");
        UTF_32_LE_BOM = ByteString.decodeHex("ffff0000");
        UTF_8 = Charset.forName("UTF-8");
        UTF_16_BE = Charset.forName("UTF-16BE");
        UTF_16_LE = Charset.forName("UTF-16LE");
        UTF_32_BE = Charset.forName("UTF-32BE");
        UTF_32_LE = Charset.forName("UTF-32LE");
        UTC = TimeZone.getTimeZone("GMT");
        VERIFY_AS_IP_ADDRESS = Pattern.compile("([0-9a-fA-F]*:[0-9a-fA-F:.]*)|([\\d.]+)");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static Charset bomAwareCharset(BufferedSource bufferedSource, Charset charset) throws IOException {
        if (bufferedSource.rangeEquals(0L, UTF_8_BOM)) {
            bufferedSource.skip(UTF_8_BOM.size());
            return UTF_8;
        }
        if (bufferedSource.rangeEquals(0L, UTF_16_BE_BOM)) {
            bufferedSource.skip(UTF_16_BE_BOM.size());
            return UTF_16_BE;
        }
        if (bufferedSource.rangeEquals(0L, UTF_16_LE_BOM)) {
            bufferedSource.skip(UTF_16_LE_BOM.size());
            return UTF_16_LE;
        }
        if (bufferedSource.rangeEquals(0L, UTF_32_BE_BOM)) {
            bufferedSource.skip(UTF_32_BE_BOM.size());
            return UTF_32_BE;
        }
        if (!bufferedSource.rangeEquals(0L, UTF_32_LE_BOM)) return charset;
        bufferedSource.skip(UTF_32_LE_BOM.size());
        return UTF_32_LE;
    }

    public static void checkOffsetAndCount(long l, long l2, long l3) {
        if ((l2 | l3) < 0L || l2 > l || l - l2 < l3) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static void closeQuietly(Closeable closeable) {
        if (closeable == null) return;
        try {
            closeable.close();
            return;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (Exception exception) {
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void closeQuietly(Socket socket) {
        if (socket == null) return;
        try {
            socket.close();
            return;
        }
        catch (AssertionError assertionError) {
            if (Util.isAndroidGetsocknameError(assertionError)) return;
            throw assertionError;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (Exception exception) {
            return;
        }
    }

    public static String[] concat(String[] arrstring, String string2) {
        String[] arrstring2 = new String[arrstring.length + 1];
        System.arraycopy(arrstring, 0, arrstring2, 0, arrstring.length);
        arrstring2[arrstring2.length - 1] = string2;
        return arrstring2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static boolean containsInvalidHostnameAsciiCodes(String string2) {
        int n = 0;
        while (n < string2.length()) {
            char c = string2.charAt(n);
            if (c <= '\u001f' || c >= '\u007f' || " #%/:?@[\\]".indexOf(c) != -1) {
                return true;
            }
            ++n;
        }
        return false;
    }

    public static int delimiterOffset(String string2, int n, int n2, char c) {
        while (n < n2) {
            if (string2.charAt(n) == c) {
                return n;
            }
            ++n;
        }
        return n2;
    }

    public static int delimiterOffset(String string2, int n, int n2, String string3) {
        while (n < n2) {
            if (string3.indexOf(string2.charAt(n)) != -1) {
                return n;
            }
            ++n;
        }
        return n2;
    }

    public static boolean discard(Source source, int n, TimeUnit timeUnit) {
        try {
            boolean bl = Util.skipAll(source, n, timeUnit);
            return bl;
        }
        catch (IOException iOException) {
            return false;
        }
    }

    public static String domainToAscii(String string2) {
        block4: {
            string2 = IDN.toASCII(string2).toLowerCase(Locale.US);
            if (!string2.isEmpty()) break block4;
            return null;
        }
        try {
            boolean bl = Util.containsInvalidHostnameAsciiCodes(string2);
            if (bl) {
                return null;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            string2 = null;
        }
        return string2;
    }

    public static boolean equal(Object object, Object object2) {
        return object == object2 || object != null && object.equals(object2);
    }

    public static String format(String string2, Object ... arrobject) {
        return String.format(Locale.US, string2, arrobject);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static String hostHeader(HttpUrl httpUrl, boolean bl) {
        String string2 = httpUrl.host().contains(":") ? "[" + httpUrl.host() + "]" : httpUrl.host();
        if (bl) return string2 + ":" + httpUrl.port();
        String string3 = string2;
        if (httpUrl.port() == HttpUrl.defaultPort(httpUrl.scheme())) return string3;
        return string2 + ":" + httpUrl.port();
    }

    public static <T> List<T> immutableList(List<T> list) {
        return Collections.unmodifiableList(new ArrayList<T>(list));
    }

    public static <T> List<T> immutableList(T ... arrT) {
        return Collections.unmodifiableList(Arrays.asList((Object[])arrT.clone()));
    }

    public static <T> int indexOf(T[] arrT, T t) {
        int n = arrT.length;
        for (int i = 0; i < n; ++i) {
            if (!Util.equal(arrT[i], t)) continue;
            return i;
        }
        return -1;
    }

    public static int indexOfControlOrNonAscii(String string2) {
        int n = string2.length();
        for (int i = 0; i < n; ++i) {
            char c = string2.charAt(i);
            if (c > '\u001f' && c < '\u007f') continue;
            return i;
        }
        return -1;
    }

    private static <T> List<T> intersect(T[] arrT, T[] arrT2) {
        ArrayList<T> arrayList = new ArrayList<T>();
        block0: for (T t : arrT) {
            int n = arrT2.length;
            int n2 = 0;
            do {
                if (n2 >= n) continue block0;
                T t2 = arrT2[n2];
                if (t.equals(t2)) {
                    arrayList.add(t2);
                    continue block0;
                }
                ++n2;
            } while (true);
        }
        return arrayList;
    }

    public static <T> T[] intersect(Class<T> class_, T[] object, T[] arrT) {
        object = Util.intersect(object, arrT);
        return object.toArray((Object[])Array.newInstance(class_, object.size()));
    }

    public static boolean isAndroidGetsocknameError(AssertionError assertionError) {
        return ((Throwable)((Object)assertionError)).getCause() != null && ((Throwable)((Object)assertionError)).getMessage() != null && ((Throwable)((Object)assertionError)).getMessage().contains("getsockname failed");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean skipAll(Source source, int n, TimeUnit object) throws IOException {
        long l;
        long l2;
        block7: {
            l = System.nanoTime();
            l2 = source.timeout().hasDeadline() ? source.timeout().deadlineNanoTime() - l : Long.MAX_VALUE;
            source.timeout().deadlineNanoTime(Math.min(l2, ((TimeUnit)((Object)object)).toNanos(n)) + l);
            try {
                object = new Buffer();
                while (source.read((Buffer)object, 8192L) != -1L) {
                    ((Buffer)object).clear();
                }
            }
            catch (InterruptedIOException interruptedIOException) {
                if (l2 == Long.MAX_VALUE) {
                    source.timeout().clearDeadline();
                    return false;
                }
                break block7;
            }
            catch (Throwable throwable) {
                if (l2 == Long.MAX_VALUE) {
                    source.timeout().clearDeadline();
                    throw throwable;
                }
                source.timeout().deadlineNanoTime(l + l2);
                throw throwable;
            }
            if (l2 == Long.MAX_VALUE) {
                source.timeout().clearDeadline();
                return true;
            }
            source.timeout().deadlineNanoTime(l + l2);
            return true;
        }
        source.timeout().deadlineNanoTime(l + l2);
        return false;
    }

    public static int skipLeadingAsciiWhitespace(String string2, int n, int n2) {
        while (n < n2) {
            switch (string2.charAt(n)) {
                default: {
                    return n;
                }
                case '\t': 
                case '\n': 
                case '\f': 
                case '\r': 
                case ' ': 
            }
            ++n;
        }
        return n2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static int skipTrailingAsciiWhitespace(String string2, int n, int n2) {
        --n2;
        do {
            int n3 = n;
            if (n2 < n) return n3;
            switch (string2.charAt(n2)) {
                default: {
                    return n2 + 1;
                }
                case '\t': 
                case '\n': 
                case '\f': 
                case '\r': 
                case ' ': 
            }
            --n2;
        } while (true);
    }

    public static ThreadFactory threadFactory(final String string2, final boolean bl) {
        return new ThreadFactory(){

            @Override
            public Thread newThread(Runnable runnable) {
                runnable = new Thread(runnable, string2);
                ((Thread)runnable).setDaemon(bl);
                return runnable;
            }
        };
    }

    public static String trimSubstring(String string2, int n, int n2) {
        n = Util.skipLeadingAsciiWhitespace(string2, n, n2);
        return string2.substring(n, Util.skipTrailingAsciiWhitespace(string2, n, n2));
    }

    public static boolean verifyAsIpAddress(String string2) {
        return VERIFY_AS_IP_ADDRESS.matcher(string2).matches();
    }

}

