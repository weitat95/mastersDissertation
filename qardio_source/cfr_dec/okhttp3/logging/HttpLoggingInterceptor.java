/*
 * Decompiled with CFR 0.147.
 */
package okhttp3.logging;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.concurrent.TimeUnit;
import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okhttp3.internal.platform.Platform;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;

public final class HttpLoggingInterceptor
implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");
    private volatile Level level = Level.NONE;
    private final Logger logger;

    public HttpLoggingInterceptor() {
        this(Logger.DEFAULT);
    }

    public HttpLoggingInterceptor(Logger logger) {
        this.logger = logger;
    }

    private boolean bodyEncoded(Headers object) {
        return (object = ((Headers)object).get("Content-Encoding")) != null && !((String)object).equalsIgnoreCase("identity");
    }

    static boolean isPlaintext(Buffer buffer) {
        long l = 64L;
        Buffer buffer2 = new Buffer();
        if (buffer.size() < 64L) {
            l = buffer.size();
        }
        buffer.copyTo(buffer2, 0L, l);
        for (int i = 0; i < 16; ++i) {
            try {
                boolean bl;
                if (buffer2.exhausted()) break;
                int n = buffer2.readUtf8CodePoint();
                if (!Character.isISOControl(n) || (bl = Character.isWhitespace(n))) continue;
                return false;
            }
            catch (EOFException eOFException) {
                return false;
            }
        }
        return true;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public Response intercept(Interceptor.Chain object) throws IOException {
        Object object2;
        Object object3;
        long l;
        block26: {
            Object object4;
            object2 = this.level;
            object3 = object.request();
            if (object2 == Level.NONE) {
                return object.proceed((Request)object3);
            }
            boolean bl = object2 == Level.BODY;
            int n = bl || object2 == Level.HEADERS ? 1 : 0;
            Object object5 = ((Request)object3).body();
            int n2 = object5 != null ? 1 : 0;
            object2 = object.connection();
            object2 = object2 != null ? object2.protocol() : Protocol.HTTP_1_1;
            object2 = object4 = "--> " + ((Request)object3).method() + ' ' + ((Request)object3).url() + ' ' + (Object)object2;
            if (n == 0) {
                object2 = object4;
                if (n2 != 0) {
                    object2 = (String)object4 + " (" + ((RequestBody)object5).contentLength() + "-byte body)";
                }
            }
            this.logger.log((String)object2);
            if (n != 0) {
                if (n2 != 0) {
                    if (((RequestBody)object5).contentType() != null) {
                        this.logger.log("Content-Type: " + ((RequestBody)object5).contentType());
                    }
                    if (((RequestBody)object5).contentLength() != -1L) {
                        this.logger.log("Content-Length: " + ((RequestBody)object5).contentLength());
                    }
                }
                object2 = ((Request)object3).headers();
                int n3 = ((Headers)object2).size();
                for (int i = 0; i < n3; ++i) {
                    object4 = ((Headers)object2).name(i);
                    if ("Content-Type".equalsIgnoreCase((String)object4) || "Content-Length".equalsIgnoreCase((String)object4)) continue;
                    this.logger.log((String)object4 + ": " + ((Headers)object2).value(i));
                }
                if (!bl || n2 == 0) {
                    this.logger.log("--> END " + ((Request)object3).method());
                } else if (this.bodyEncoded(((Request)object3).headers())) {
                    this.logger.log("--> END " + ((Request)object3).method() + " (encoded body omitted)");
                } else {
                    object4 = new Buffer();
                    ((RequestBody)object5).writeTo((BufferedSink)object4);
                    object2 = UTF8;
                    MediaType mediaType = ((RequestBody)object5).contentType();
                    if (mediaType != null) {
                        object2 = mediaType.charset(UTF8);
                    }
                    this.logger.log("");
                    if (HttpLoggingInterceptor.isPlaintext((Buffer)object4)) {
                        this.logger.log(((Buffer)object4).readString((Charset)object2));
                        this.logger.log("--> END " + ((Request)object3).method() + " (" + ((RequestBody)object5).contentLength() + "-byte body)");
                    } else {
                        this.logger.log("--> END " + ((Request)object3).method() + " (binary " + ((RequestBody)object5).contentLength() + "-byte body omitted)");
                    }
                }
            }
            long l2 = System.nanoTime();
            try {
                object = object.proceed((Request)object3);
                l2 = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - l2);
                object4 = ((Response)object).body();
                l = ((ResponseBody)object4).contentLength();
                object2 = l != -1L ? l + "-byte" : "unknown-length";
            }
            catch (Exception exception) {
                this.logger.log("<-- HTTP FAILED: " + exception);
                throw exception;
            }
            object3 = this.logger;
            object5 = new StringBuilder().append("<-- ").append(((Response)object).code()).append(' ').append(((Response)object).message()).append(' ').append(((Response)object).request().url()).append(" (").append(l2).append("ms");
            object2 = n == 0 ? ", " + (String)object2 + " body" : "";
            object3.log(((StringBuilder)object5).append((String)object2).append(')').toString());
            object2 = object;
            if (n == 0) return object2;
            object2 = ((Response)object).headers();
            n2 = ((Headers)object2).size();
            for (n = 0; n < n2; ++n) {
                this.logger.log(((Headers)object2).name(n) + ": " + ((Headers)object2).value(n));
            }
            if (!bl || !HttpHeaders.hasBody((Response)object)) {
                this.logger.log("<-- END HTTP");
                return object;
            }
            if (this.bodyEncoded(((Response)object).headers())) {
                this.logger.log("<-- END HTTP (encoded body omitted)");
                return object;
            }
            object2 = ((ResponseBody)object4).source();
            object2.request(Long.MAX_VALUE);
            object3 = object2.buffer();
            object2 = UTF8;
            object4 = ((ResponseBody)object4).contentType();
            if (object4 != null) {
                object2 = ((MediaType)object4).charset(UTF8);
            }
            if (!HttpLoggingInterceptor.isPlaintext((Buffer)object3)) {
                this.logger.log("");
                this.logger.log("<-- END HTTP (binary " + ((Buffer)object3).size() + "-byte body omitted)");
                return object;
            }
            break block26;
            catch (UnsupportedCharsetException unsupportedCharsetException) {
                this.logger.log("");
                this.logger.log("Couldn't decode the response body; charset is likely malformed.");
                this.logger.log("<-- END HTTP");
                return object;
            }
        }
        if (l != 0L) {
            this.logger.log("");
            this.logger.log(((Buffer)((Buffer)object3).clone()).readString((Charset)object2));
        }
        this.logger.log("<-- END HTTP (" + ((Buffer)object3).size() + "-byte body)");
        return object;
    }

    public HttpLoggingInterceptor setLevel(Level level) {
        if (level == null) {
            throw new NullPointerException("level == null. Use Level.NONE instead.");
        }
        this.level = level;
        return this;
    }

    public static enum Level {
        NONE,
        BASIC,
        HEADERS,
        BODY;

    }

    public static interface Logger {
        public static final Logger DEFAULT = new Logger(){

            @Override
            public void log(String string2) {
                Platform.get().log(4, string2, null);
            }
        };

        public void log(String var1);

    }

}

