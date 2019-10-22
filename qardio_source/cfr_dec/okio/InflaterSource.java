/*
 * Decompiled with CFR 0.147.
 */
package okio;

import java.io.EOFException;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import okio.Buffer;
import okio.BufferedSource;
import okio.Segment;
import okio.SegmentPool;
import okio.Source;
import okio.Timeout;

public final class InflaterSource
implements Source {
    private int bufferBytesHeldByInflater;
    private boolean closed;
    private final Inflater inflater;
    private final BufferedSource source;

    InflaterSource(BufferedSource bufferedSource, Inflater inflater) {
        if (bufferedSource == null) {
            throw new IllegalArgumentException("source == null");
        }
        if (inflater == null) {
            throw new IllegalArgumentException("inflater == null");
        }
        this.source = bufferedSource;
        this.inflater = inflater;
    }

    private void releaseInflatedBytes() throws IOException {
        if (this.bufferBytesHeldByInflater == 0) {
            return;
        }
        int n = this.bufferBytesHeldByInflater - this.inflater.getRemaining();
        this.bufferBytesHeldByInflater -= n;
        this.source.skip(n);
    }

    @Override
    public void close() throws IOException {
        if (this.closed) {
            return;
        }
        this.inflater.end();
        this.closed = true;
        this.source.close();
    }

    @Override
    public long read(Buffer buffer, long l) throws IOException {
        block11: {
            if (l < 0L) {
                throw new IllegalArgumentException("byteCount < 0: " + l);
            }
            if (this.closed) {
                throw new IllegalStateException("closed");
            }
            if (l == 0L) {
                return 0L;
            }
            do {
                Segment segment;
                boolean bl;
                block10: {
                    bl = this.refill();
                    segment = buffer.writableSegment(1);
                    int n = this.inflater.inflate(segment.data, segment.limit, 8192 - segment.limit);
                    if (n <= 0) break block10;
                    segment.limit += n;
                    buffer.size += (long)n;
                    return n;
                }
                if (!this.inflater.finished() && !this.inflater.needsDictionary()) continue;
                this.releaseInflatedBytes();
                if (segment.pos == segment.limit) {
                    buffer.head = segment.pop();
                    SegmentPool.recycle(segment);
                }
                break block11;
                if (!bl) continue;
                break;
            } while (true);
            try {
                throw new EOFException("source exhausted prematurely");
            }
            catch (DataFormatException dataFormatException) {
                throw new IOException(dataFormatException);
            }
        }
        return -1L;
    }

    public boolean refill() throws IOException {
        if (!this.inflater.needsInput()) {
            return false;
        }
        this.releaseInflatedBytes();
        if (this.inflater.getRemaining() != 0) {
            throw new IllegalStateException("?");
        }
        if (this.source.exhausted()) {
            return true;
        }
        Segment segment = this.source.buffer().head;
        this.bufferBytesHeldByInflater = segment.limit - segment.pos;
        this.inflater.setInput(segment.data, segment.pos, this.bufferBytesHeldByInflater);
        return false;
    }

    @Override
    public Timeout timeout() {
        return this.source.timeout();
    }
}

