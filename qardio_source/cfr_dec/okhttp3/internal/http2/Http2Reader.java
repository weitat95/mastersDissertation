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
import okio.BufferedSource;
import okio.ByteString;
import okio.Source;
import okio.Timeout;

final class Http2Reader
implements Closeable {
    static final Logger logger = Logger.getLogger(Http2.class.getName());
    private final boolean client;
    private final ContinuationSource continuation;
    final Hpack.Reader hpackReader;
    private final BufferedSource source;

    public Http2Reader(BufferedSource bufferedSource, boolean bl) {
        this.source = bufferedSource;
        this.client = bl;
        this.continuation = new ContinuationSource(this.source);
        this.hpackReader = new Hpack.Reader(4096, this.continuation);
    }

    static int lengthWithoutPadding(int n, byte by, short s) throws IOException {
        int n2 = n;
        if ((by & 8) != 0) {
            n2 = n - 1;
        }
        if (s > n2) {
            throw Http2.ioException("PROTOCOL_ERROR padding %s > remaining length %s", s, n2);
        }
        return (short)(n2 - s);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void readData(Handler handler, int n, byte by, int n2) throws IOException {
        boolean bl = true;
        short s = 0;
        boolean bl2 = (by & 1) != 0;
        if ((by & 0x20) == 0) {
            bl = false;
        }
        if (bl) {
            throw Http2.ioException("PROTOCOL_ERROR: FLAG_COMPRESSED without SETTINGS_COMPRESS_DATA", new Object[0]);
        }
        if ((by & 8) != 0) {
            s = (short)(this.source.readByte() & 0xFF);
        }
        n = Http2Reader.lengthWithoutPadding(n, by, s);
        handler.data(bl2, n2, this.source, n);
        this.source.skip(s);
    }

    private void readGoAway(Handler handler, int n, byte by, int n2) throws IOException {
        if (n < 8) {
            throw Http2.ioException("TYPE_GOAWAY length < 8: %s", n);
        }
        if (n2 != 0) {
            throw Http2.ioException("TYPE_GOAWAY streamId != 0", new Object[0]);
        }
        by = (byte)this.source.readInt();
        n2 = this.source.readInt();
        n -= 8;
        ErrorCode errorCode = ErrorCode.fromHttp2(n2);
        if (errorCode == null) {
            throw Http2.ioException("TYPE_GOAWAY unexpected error code: %d", n2);
        }
        ByteString byteString = ByteString.EMPTY;
        if (n > 0) {
            byteString = this.source.readByteString(n);
        }
        handler.goAway(by, errorCode, byteString);
    }

    private List<Header> readHeaderBlock(int n, short s, byte by, int n2) throws IOException {
        ContinuationSource continuationSource = this.continuation;
        this.continuation.left = n;
        continuationSource.length = n;
        this.continuation.padding = s;
        this.continuation.flags = by;
        this.continuation.streamId = n2;
        this.hpackReader.readHeaders();
        return this.hpackReader.getAndResetHeaderList();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void readHeaders(Handler handler, int n, byte by, int n2) throws IOException {
        short s = 0;
        if (n2 == 0) {
            throw Http2.ioException("PROTOCOL_ERROR: TYPE_HEADERS streamId == 0", new Object[0]);
        }
        boolean bl = (by & 1) != 0;
        if ((by & 8) != 0) {
            s = (short)(this.source.readByte() & 0xFF);
        }
        int n3 = n;
        if ((by & 0x20) != 0) {
            this.readPriority(handler, n2);
            n3 = n - 5;
        }
        handler.headers(bl, n2, -1, this.readHeaderBlock(Http2Reader.lengthWithoutPadding(n3, by, s), s, by, n2));
    }

    static int readMedium(BufferedSource bufferedSource) throws IOException {
        return (bufferedSource.readByte() & 0xFF) << 16 | (bufferedSource.readByte() & 0xFF) << 8 | bufferedSource.readByte() & 0xFF;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void readPing(Handler handler, int n, byte by, int n2) throws IOException {
        boolean bl = true;
        if (n != 8) {
            throw Http2.ioException("TYPE_PING length != 8: %s", n);
        }
        if (n2 != 0) {
            throw Http2.ioException("TYPE_PING streamId != 0", new Object[0]);
        }
        n = this.source.readInt();
        n2 = this.source.readInt();
        if ((by & 1) == 0) {
            bl = false;
        }
        handler.ping(bl, n, n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void readPriority(Handler handler, int n) throws IOException {
        int n2 = this.source.readInt();
        boolean bl = (Integer.MIN_VALUE & n2) != 0;
        handler.priority(n, n2 & Integer.MAX_VALUE, (this.source.readByte() & 0xFF) + 1, bl);
    }

    private void readPriority(Handler handler, int n, byte by, int n2) throws IOException {
        if (n != 5) {
            throw Http2.ioException("TYPE_PRIORITY length: %d != 5", n);
        }
        if (n2 == 0) {
            throw Http2.ioException("TYPE_PRIORITY streamId == 0", new Object[0]);
        }
        this.readPriority(handler, n2);
    }

    private void readPushPromise(Handler handler, int n, byte by, int n2) throws IOException {
        short s = 0;
        if (n2 == 0) {
            throw Http2.ioException("PROTOCOL_ERROR: TYPE_PUSH_PROMISE streamId == 0", new Object[0]);
        }
        if ((by & 8) != 0) {
            s = (short)(this.source.readByte() & 0xFF);
        }
        handler.pushPromise(n2, this.source.readInt() & Integer.MAX_VALUE, this.readHeaderBlock(Http2Reader.lengthWithoutPadding(n - 4, by, s), s, by, n2));
    }

    private void readRstStream(Handler handler, int n, byte by, int n2) throws IOException {
        if (n != 4) {
            throw Http2.ioException("TYPE_RST_STREAM length: %d != 4", n);
        }
        if (n2 == 0) {
            throw Http2.ioException("TYPE_RST_STREAM streamId == 0", new Object[0]);
        }
        n = this.source.readInt();
        ErrorCode errorCode = ErrorCode.fromHttp2(n);
        if (errorCode == null) {
            throw Http2.ioException("TYPE_RST_STREAM unexpected error code: %d", n);
        }
        handler.rstStream(n2, errorCode);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void readSettings(Handler var1_1, int var2_2, byte var3_3, int var4_4) throws IOException {
        if (var4_4 != 0) {
            throw Http2.ioException("TYPE_SETTINGS streamId != 0", new Object[0]);
        }
        if ((var3_3 & 1) != 0) {
            if (var2_2 != 0) {
                throw Http2.ioException("FRAME_SIZE_ERROR ack frame should be empty!", new Object[0]);
            }
            var1_1.ackSettings();
            return;
        }
        if (var2_2 % 6 != 0) {
            throw Http2.ioException("TYPE_SETTINGS length %% 6 != 0: %s", new Object[]{var2_2});
        }
        var7_5 = new Settings();
        var4_4 = 0;
        do {
            if (var4_4 >= var2_2) {
                var1_1.settings(false, var7_5);
                return;
            }
            var5_6 = this.source.readShort();
            var6_7 = this.source.readInt();
            var3_3 = (byte)var5_6;
            switch (var5_6) {
                default: {
                    var3_3 = (byte)var5_6;
                    break;
                }
                case 2: {
                    var3_3 = (byte)var5_6;
                    if (var6_7 != 0) {
                        var3_3 = (byte)var5_6;
                        if (var6_7 != 1) {
                            throw Http2.ioException("PROTOCOL_ERROR SETTINGS_ENABLE_PUSH != 0 or 1", new Object[0]);
                        }
                    }
                    ** GOTO lbl37
                }
                case 3: {
                    var3_3 = (byte)4;
                    break;
                }
                case 4: {
                    var3_3 = (byte)7;
                    if (var6_7 < 0) {
                        throw Http2.ioException("PROTOCOL_ERROR SETTINGS_INITIAL_WINDOW_SIZE > 2^31 - 1", new Object[0]);
                    }
                }
lbl37:
                // 4 sources
                case 1: 
                case 6: {
                    break;
                }
                case 5: {
                    if (var6_7 < 16384) throw Http2.ioException("PROTOCOL_ERROR SETTINGS_MAX_FRAME_SIZE: %s", new Object[]{var6_7});
                    var3_3 = (byte)var5_6;
                    if (var6_7 <= 16777215) break;
                    throw Http2.ioException("PROTOCOL_ERROR SETTINGS_MAX_FRAME_SIZE: %s", new Object[]{var6_7});
                }
            }
            var7_5.set(var3_3, var6_7);
            var4_4 += 6;
        } while (true);
    }

    private void readWindowUpdate(Handler handler, int n, byte by, int n2) throws IOException {
        if (n != 4) {
            throw Http2.ioException("TYPE_WINDOW_UPDATE length !=4: %s", n);
        }
        long l = (long)this.source.readInt() & Integer.MAX_VALUE;
        if (l == 0L) {
            throw Http2.ioException("windowSizeIncrement was 0", l);
        }
        handler.windowUpdate(n2, l);
    }

    @Override
    public void close() throws IOException {
        this.source.close();
    }

    public boolean nextFrame(boolean bl, Handler handler) throws IOException {
        try {
            this.source.require(9L);
        }
        catch (IOException iOException) {
            return false;
        }
        int n = Http2Reader.readMedium(this.source);
        if (n < 0 || n > 16384) {
            throw Http2.ioException("FRAME_SIZE_ERROR: %s", n);
        }
        byte by = (byte)(this.source.readByte() & 0xFF);
        if (bl && by != 4) {
            throw Http2.ioException("Expected a SETTINGS frame but was %s", by);
        }
        byte by2 = (byte)(this.source.readByte() & 0xFF);
        int n2 = this.source.readInt() & Integer.MAX_VALUE;
        if (logger.isLoggable(Level.FINE)) {
            logger.fine(Http2.frameLog(true, n2, n, by, by2));
        }
        switch (by) {
            default: {
                this.source.skip(n);
                return true;
            }
            case 0: {
                this.readData(handler, n, by2, n2);
                return true;
            }
            case 1: {
                this.readHeaders(handler, n, by2, n2);
                return true;
            }
            case 2: {
                this.readPriority(handler, n, by2, n2);
                return true;
            }
            case 3: {
                this.readRstStream(handler, n, by2, n2);
                return true;
            }
            case 4: {
                this.readSettings(handler, n, by2, n2);
                return true;
            }
            case 5: {
                this.readPushPromise(handler, n, by2, n2);
                return true;
            }
            case 6: {
                this.readPing(handler, n, by2, n2);
                return true;
            }
            case 7: {
                this.readGoAway(handler, n, by2, n2);
                return true;
            }
            case 8: 
        }
        this.readWindowUpdate(handler, n, by2, n2);
        return true;
    }

    public void readConnectionPreface(Handler object) throws IOException {
        if (this.client) {
            if (!this.nextFrame(true, (Handler)object)) {
                throw Http2.ioException("Required SETTINGS preface not received", new Object[0]);
            }
        } else {
            object = this.source.readByteString(Http2.CONNECTION_PREFACE.size());
            if (logger.isLoggable(Level.FINE)) {
                logger.fine(Util.format("<< CONNECTION %s", ((ByteString)object).hex()));
            }
            if (!Http2.CONNECTION_PREFACE.equals(object)) {
                throw Http2.ioException("Expected a connection header but was %s", ((ByteString)object).utf8());
            }
        }
    }

    static final class ContinuationSource
    implements Source {
        byte flags;
        int left;
        int length;
        short padding;
        private final BufferedSource source;
        int streamId;

        public ContinuationSource(BufferedSource bufferedSource) {
            this.source = bufferedSource;
        }

        private void readContinuationHeader() throws IOException {
            int n;
            int n2 = this.streamId;
            this.left = n = Http2Reader.readMedium(this.source);
            this.length = n;
            byte by = (byte)(this.source.readByte() & 0xFF);
            this.flags = (byte)(this.source.readByte() & 0xFF);
            if (logger.isLoggable(Level.FINE)) {
                logger.fine(Http2.frameLog(true, this.streamId, this.length, by, this.flags));
            }
            this.streamId = this.source.readInt() & Integer.MAX_VALUE;
            if (by != 9) {
                throw Http2.ioException("%s != TYPE_CONTINUATION", by);
            }
            if (this.streamId != n2) {
                throw Http2.ioException("TYPE_CONTINUATION streamId changed", new Object[0]);
            }
        }

        @Override
        public void close() throws IOException {
        }

        @Override
        public long read(Buffer buffer, long l) throws IOException {
            while (this.left == 0) {
                this.source.skip(this.padding);
                this.padding = 0;
                if ((this.flags & 4) != 0) {
                    return -1L;
                }
                this.readContinuationHeader();
            }
            if ((l = this.source.read(buffer, Math.min(l, (long)this.left))) == -1L) {
                return -1L;
            }
            this.left = (int)((long)this.left - l);
            return l;
        }

        @Override
        public Timeout timeout() {
            return this.source.timeout();
        }
    }

    static interface Handler {
        public void ackSettings();

        public void data(boolean var1, int var2, BufferedSource var3, int var4) throws IOException;

        public void goAway(int var1, ErrorCode var2, ByteString var3);

        public void headers(boolean var1, int var2, int var3, List<Header> var4);

        public void ping(boolean var1, int var2, int var3);

        public void priority(int var1, int var2, int var3, boolean var4);

        public void pushPromise(int var1, int var2, List<Header> var3) throws IOException;

        public void rstStream(int var1, ErrorCode var2);

        public void settings(boolean var1, Settings var2);

        public void windowUpdate(int var1, long var2);
    }

}

