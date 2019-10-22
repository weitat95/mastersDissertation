/*
 * Decompiled with CFR 0.147.
 */
package okhttp3.internal.http2;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import okhttp3.internal.Util;
import okhttp3.internal.http2.ErrorCode;
import okhttp3.internal.http2.Header;
import okhttp3.internal.http2.Hpack;
import okhttp3.internal.http2.Http2;
import okhttp3.internal.http2.Settings;
import okio.Buffer;
import okio.BufferedSink;
import okio.ByteString;

final class Http2Writer
implements Closeable {
    private static final Logger logger = Logger.getLogger(Http2.class.getName());
    private final boolean client;
    private boolean closed;
    private final Buffer hpackBuffer;
    final Hpack.Writer hpackWriter;
    private int maxFrameSize;
    private final BufferedSink sink;

    public Http2Writer(BufferedSink bufferedSink, boolean bl) {
        this.sink = bufferedSink;
        this.client = bl;
        this.hpackBuffer = new Buffer();
        this.hpackWriter = new Hpack.Writer(this.hpackBuffer);
        this.maxFrameSize = 16384;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void writeContinuationFrames(int n, long l) throws IOException {
        while (l > 0L) {
            int n2;
            byte by = (l -= (long)(n2 = (int)Math.min((long)this.maxFrameSize, l))) == 0L ? (byte)4 : 0;
            this.frameHeader(n, n2, (byte)9, by);
            this.sink.write(this.hpackBuffer, n2);
        }
        return;
    }

    private static void writeMedium(BufferedSink bufferedSink, int n) throws IOException {
        bufferedSink.writeByte(n >>> 16 & 0xFF);
        bufferedSink.writeByte(n >>> 8 & 0xFF);
        bufferedSink.writeByte(n & 0xFF);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void applyAndAckSettings(Settings settings) throws IOException {
        synchronized (this) {
            if (this.closed) {
                throw new IOException("closed");
            }
            this.maxFrameSize = settings.getMaxFrameSize(this.maxFrameSize);
            if (settings.getHeaderTableSize() != -1) {
                this.hpackWriter.setHeaderTableSizeSetting(settings.getHeaderTableSize());
            }
            this.frameHeader(0, 0, (byte)4, (byte)1);
            this.sink.flush();
            return;
        }
    }

    @Override
    public void close() throws IOException {
        synchronized (this) {
            this.closed = true;
            this.sink.close();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void connectionPreface() throws IOException {
        synchronized (this) {
            if (this.closed) {
                throw new IOException("closed");
            }
            boolean bl = this.client;
            if (bl) {
                if (logger.isLoggable(Level.FINE)) {
                    logger.fine(Util.format(">> CONNECTION %s", Http2.CONNECTION_PREFACE.hex()));
                }
                this.sink.write(Http2.CONNECTION_PREFACE.toByteArray());
                this.sink.flush();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void data(boolean bl, int n, Buffer buffer, int n2) throws IOException {
        synchronized (this) {
            if (this.closed) {
                throw new IOException("closed");
            }
            byte by = 0;
            if (bl) {
                by = (byte)(true ? 1 : 0);
            }
            this.dataFrame(n, by, buffer, n2);
            return;
        }
    }

    void dataFrame(int n, byte by, Buffer buffer, int n2) throws IOException {
        this.frameHeader(n, n2, (byte)0, by);
        if (n2 > 0) {
            this.sink.write(buffer, n2);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void flush() throws IOException {
        synchronized (this) {
            if (this.closed) {
                throw new IOException("closed");
            }
            this.sink.flush();
            return;
        }
    }

    public void frameHeader(int n, int n2, byte by, byte by2) throws IOException {
        if (logger.isLoggable(Level.FINE)) {
            logger.fine(Http2.frameLog(false, n, n2, by, by2));
        }
        if (n2 > this.maxFrameSize) {
            throw Http2.illegalArgument("FRAME_SIZE_ERROR length > %d: %d", this.maxFrameSize, n2);
        }
        if ((Integer.MIN_VALUE & n) != 0) {
            throw Http2.illegalArgument("reserved bit set: %s", n);
        }
        Http2Writer.writeMedium(this.sink, n2);
        this.sink.writeByte(by & 0xFF);
        this.sink.writeByte(by2 & 0xFF);
        this.sink.writeInt(Integer.MAX_VALUE & n);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void goAway(int n, ErrorCode errorCode, byte[] arrby) throws IOException {
        synchronized (this) {
            if (this.closed) {
                throw new IOException("closed");
            }
            if (errorCode.httpCode == -1) {
                throw Http2.illegalArgument("errorCode.httpCode == -1", new Object[0]);
            }
            this.frameHeader(0, arrby.length + 8, (byte)7, (byte)0);
            this.sink.writeInt(n);
            this.sink.writeInt(errorCode.httpCode);
            if (arrby.length > 0) {
                this.sink.write(arrby);
            }
            this.sink.flush();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    void headers(boolean bl, int n, List<Header> list) throws IOException {
        if (this.closed) {
            throw new IOException("closed");
        }
        this.hpackWriter.writeHeaders(list);
        long l = this.hpackBuffer.size();
        int n2 = (int)Math.min((long)this.maxFrameSize, l);
        byte by = l == (long)n2 ? (byte)4 : 0;
        byte by2 = by;
        if (bl) {
            by2 = (byte)(by | 1);
        }
        this.frameHeader(n, n2, (byte)1, by2);
        this.sink.write(this.hpackBuffer, n2);
        if (l > (long)n2) {
            this.writeContinuationFrames(n, l - (long)n2);
        }
    }

    public int maxDataLength() {
        return this.maxFrameSize;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void ping(boolean bl, int n, int n2) throws IOException {
        synchronized (this) {
            if (this.closed) {
                throw new IOException("closed");
            }
            byte by = bl ? (byte)1 : 0;
            this.frameHeader(0, 8, (byte)6, by);
            this.sink.writeInt(n);
            this.sink.writeInt(n2);
            this.sink.flush();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void pushPromise(int n, int n2, List<Header> list) throws IOException {
        synchronized (this) {
            if (this.closed) {
                throw new IOException("closed");
            }
            this.hpackWriter.writeHeaders(list);
            long l = this.hpackBuffer.size();
            int n3 = (int)Math.min((long)(this.maxFrameSize - 4), l);
            byte by = l == (long)n3 ? (byte)4 : 0;
            this.frameHeader(n, n3 + 4, (byte)5, by);
            this.sink.writeInt(Integer.MAX_VALUE & n2);
            this.sink.write(this.hpackBuffer, n3);
            if (l > (long)n3) {
                this.writeContinuationFrames(n, l - (long)n3);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void rstStream(int n, ErrorCode errorCode) throws IOException {
        synchronized (this) {
            if (this.closed) {
                throw new IOException("closed");
            }
            if (errorCode.httpCode == -1) {
                throw new IllegalArgumentException();
            }
            this.frameHeader(n, 4, (byte)3, (byte)0);
            this.sink.writeInt(errorCode.httpCode);
            this.sink.flush();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void settings(Settings settings) throws IOException {
        synchronized (this) {
            if (this.closed) {
                throw new IOException("closed");
            }
            this.frameHeader(0, settings.size() * 6, (byte)4, (byte)0);
            int n = 0;
            do {
                if (n >= 10) {
                    this.sink.flush();
                    return;
                }
                if (settings.isSet(n)) {
                    int n2;
                    int n3 = n;
                    if (n3 == 4) {
                        n2 = 3;
                    } else {
                        n2 = n3;
                        if (n3 == 7) {
                            n2 = 4;
                        }
                    }
                    this.sink.writeShort(n2);
                    this.sink.writeInt(settings.get(n));
                }
                ++n;
            } while (true);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void synStream(boolean bl, int n, int n2, List<Header> list) throws IOException {
        synchronized (this) {
            if (this.closed) {
                throw new IOException("closed");
            }
            this.headers(bl, n, list);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void windowUpdate(int n, long l) throws IOException {
        synchronized (this) {
            if (this.closed) {
                throw new IOException("closed");
            }
            if (l != 0L && l <= Integer.MAX_VALUE) {
                this.frameHeader(n, 4, (byte)8, (byte)0);
                this.sink.writeInt((int)l);
                this.sink.flush();
                return;
            }
            throw Http2.illegalArgument("windowSizeIncrement == 0 || windowSizeIncrement > 0x7fffffffL: %s", l);
        }
    }
}

