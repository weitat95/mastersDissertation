/*
 * Decompiled with CFR 0.147.
 */
package okio;

import java.io.IOException;
import okio.Buffer;
import okio.BufferedSink;
import okio.ByteString;
import okio.Sink;
import okio.Timeout;
import okio.Util;

final class RealBufferedSink
implements BufferedSink {
    public final Buffer buffer = new Buffer();
    boolean closed;
    public final Sink sink;

    RealBufferedSink(Sink sink) {
        if (sink == null) {
            throw new NullPointerException("sink == null");
        }
        this.sink = sink;
    }

    @Override
    public Buffer buffer() {
        return this.buffer;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void close() throws IOException {
        Throwable throwable;
        block12: {
            block11: {
                block10: {
                    if (this.closed) break block11;
                    Throwable throwable2 = throwable = null;
                    try {
                        if (this.buffer.size > 0L) {
                            this.sink.write(this.buffer, this.buffer.size);
                            throwable2 = throwable;
                        }
                    }
                    catch (Throwable throwable3) {}
                    try {
                        this.sink.close();
                        throwable = throwable2;
                    }
                    catch (Throwable throwable4) {
                        throwable = throwable2;
                        if (throwable2 != null) break block10;
                        throwable = throwable4;
                    }
                }
                this.closed = true;
                if (throwable != null) break block12;
            }
            return;
        }
        Util.sneakyRethrow(throwable);
    }

    @Override
    public BufferedSink emitCompleteSegments() throws IOException {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        long l = this.buffer.completeSegmentByteCount();
        if (l > 0L) {
            this.sink.write(this.buffer, l);
        }
        return this;
    }

    @Override
    public void flush() throws IOException {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        if (this.buffer.size > 0L) {
            this.sink.write(this.buffer, this.buffer.size);
        }
        this.sink.flush();
    }

    @Override
    public Timeout timeout() {
        return this.sink.timeout();
    }

    public String toString() {
        return "buffer(" + this.sink + ")";
    }

    @Override
    public BufferedSink write(ByteString byteString) throws IOException {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        this.buffer.write(byteString);
        return this.emitCompleteSegments();
    }

    @Override
    public BufferedSink write(byte[] arrby) throws IOException {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        this.buffer.write(arrby);
        return this.emitCompleteSegments();
    }

    @Override
    public BufferedSink write(byte[] arrby, int n, int n2) throws IOException {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        this.buffer.write(arrby, n, n2);
        return this.emitCompleteSegments();
    }

    @Override
    public void write(Buffer buffer, long l) throws IOException {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        this.buffer.write(buffer, l);
        this.emitCompleteSegments();
    }

    @Override
    public BufferedSink writeByte(int n) throws IOException {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        this.buffer.writeByte(n);
        return this.emitCompleteSegments();
    }

    @Override
    public BufferedSink writeDecimalLong(long l) throws IOException {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        this.buffer.writeDecimalLong(l);
        return this.emitCompleteSegments();
    }

    @Override
    public BufferedSink writeHexadecimalUnsignedLong(long l) throws IOException {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        this.buffer.writeHexadecimalUnsignedLong(l);
        return this.emitCompleteSegments();
    }

    @Override
    public BufferedSink writeInt(int n) throws IOException {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        this.buffer.writeInt(n);
        return this.emitCompleteSegments();
    }

    @Override
    public BufferedSink writeShort(int n) throws IOException {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        this.buffer.writeShort(n);
        return this.emitCompleteSegments();
    }

    @Override
    public BufferedSink writeUtf8(String string2) throws IOException {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        this.buffer.writeUtf8(string2);
        return this.emitCompleteSegments();
    }
}

