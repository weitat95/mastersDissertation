/*
 * Decompiled with CFR 0.147.
 */
package okio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import okio.AsyncTimeout;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.RealBufferedSink;
import okio.RealBufferedSource;
import okio.Segment;
import okio.SegmentPool;
import okio.Sink;
import okio.Source;
import okio.Timeout;
import okio.Util;

public final class Okio {
    static final Logger logger = Logger.getLogger(Okio.class.getName());

    private Okio() {
    }

    public static Sink appendingSink(File file) throws FileNotFoundException {
        if (file == null) {
            throw new IllegalArgumentException("file == null");
        }
        return Okio.sink(new FileOutputStream(file, true));
    }

    public static Sink blackhole() {
        return new Sink(){

            @Override
            public void close() throws IOException {
            }

            @Override
            public void flush() throws IOException {
            }

            @Override
            public Timeout timeout() {
                return Timeout.NONE;
            }

            @Override
            public void write(Buffer buffer, long l) throws IOException {
                buffer.skip(l);
            }
        };
    }

    public static BufferedSink buffer(Sink sink) {
        return new RealBufferedSink(sink);
    }

    public static BufferedSource buffer(Source source) {
        return new RealBufferedSource(source);
    }

    static boolean isAndroidGetsocknameError(AssertionError assertionError) {
        return ((Throwable)((Object)assertionError)).getCause() != null && ((Throwable)((Object)assertionError)).getMessage() != null && ((Throwable)((Object)assertionError)).getMessage().contains("getsockname failed");
    }

    public static Sink sink(File file) throws FileNotFoundException {
        if (file == null) {
            throw new IllegalArgumentException("file == null");
        }
        return Okio.sink(new FileOutputStream(file));
    }

    public static Sink sink(OutputStream outputStream) {
        return Okio.sink(outputStream, new Timeout());
    }

    private static Sink sink(final OutputStream outputStream, final Timeout timeout) {
        if (outputStream == null) {
            throw new IllegalArgumentException("out == null");
        }
        if (timeout == null) {
            throw new IllegalArgumentException("timeout == null");
        }
        return new Sink(){

            @Override
            public void close() throws IOException {
                outputStream.close();
            }

            @Override
            public void flush() throws IOException {
                outputStream.flush();
            }

            @Override
            public Timeout timeout() {
                return timeout;
            }

            public String toString() {
                return "sink(" + outputStream + ")";
            }

            @Override
            public void write(Buffer buffer, long l) throws IOException {
                Util.checkOffsetAndCount(buffer.size, 0L, l);
                while (l > 0L) {
                    timeout.throwIfReached();
                    Segment segment = buffer.head;
                    int n = (int)Math.min(l, (long)(segment.limit - segment.pos));
                    outputStream.write(segment.data, segment.pos, n);
                    segment.pos += n;
                    long l2 = l - (long)n;
                    buffer.size -= (long)n;
                    l = l2;
                    if (segment.pos != segment.limit) continue;
                    buffer.head = segment.pop();
                    SegmentPool.recycle(segment);
                    l = l2;
                }
            }
        };
    }

    public static Sink sink(Socket socket) throws IOException {
        if (socket == null) {
            throw new IllegalArgumentException("socket == null");
        }
        AsyncTimeout asyncTimeout = Okio.timeout(socket);
        return asyncTimeout.sink(Okio.sink(socket.getOutputStream(), asyncTimeout));
    }

    public static Source source(File file) throws FileNotFoundException {
        if (file == null) {
            throw new IllegalArgumentException("file == null");
        }
        return Okio.source(new FileInputStream(file));
    }

    public static Source source(InputStream inputStream) {
        return Okio.source(inputStream, new Timeout());
    }

    private static Source source(final InputStream inputStream, final Timeout timeout) {
        if (inputStream == null) {
            throw new IllegalArgumentException("in == null");
        }
        if (timeout == null) {
            throw new IllegalArgumentException("timeout == null");
        }
        return new Source(){

            @Override
            public void close() throws IOException {
                inputStream.close();
            }

            @Override
            public long read(Buffer buffer, long l) throws IOException {
                int n;
                block6: {
                    if (l < 0L) {
                        throw new IllegalArgumentException("byteCount < 0: " + l);
                    }
                    if (l == 0L) {
                        return 0L;
                    }
                    try {
                        timeout.throwIfReached();
                        Segment segment = buffer.writableSegment(1);
                        n = (int)Math.min(l, (long)(8192 - segment.limit));
                        n = inputStream.read(segment.data, segment.limit, n);
                        if (n != -1) break block6;
                        return -1L;
                    }
                    catch (AssertionError assertionError) {
                        if (Okio.isAndroidGetsocknameError(assertionError)) {
                            throw new IOException((Throwable)((Object)assertionError));
                        }
                        throw assertionError;
                    }
                }
                segment.limit += n;
                buffer.size += (long)n;
                return n;
            }

            @Override
            public Timeout timeout() {
                return timeout;
            }

            public String toString() {
                return "source(" + inputStream + ")";
            }
        };
    }

    public static Source source(Socket socket) throws IOException {
        if (socket == null) {
            throw new IllegalArgumentException("socket == null");
        }
        AsyncTimeout asyncTimeout = Okio.timeout(socket);
        return asyncTimeout.source(Okio.source(socket.getInputStream(), asyncTimeout));
    }

    private static AsyncTimeout timeout(final Socket socket) {
        return new AsyncTimeout(){

            @Override
            protected IOException newTimeoutException(IOException iOException) {
                SocketTimeoutException socketTimeoutException = new SocketTimeoutException("timeout");
                if (iOException != null) {
                    socketTimeoutException.initCause(iOException);
                }
                return socketTimeoutException;
            }

            @Override
            protected void timedOut() {
                try {
                    socket.close();
                    return;
                }
                catch (Exception exception) {
                    logger.log(Level.WARNING, "Failed to close timed out socket " + socket, exception);
                    return;
                }
                catch (AssertionError assertionError) {
                    if (Okio.isAndroidGetsocknameError(assertionError)) {
                        logger.log(Level.WARNING, "Failed to close timed out socket " + socket, (Throwable)((Object)assertionError));
                        return;
                    }
                    throw assertionError;
                }
            }
        };
    }

}

