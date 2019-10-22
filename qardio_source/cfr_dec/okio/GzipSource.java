/*
 * Decompiled with CFR 0.147.
 */
package okio;

import java.io.EOFException;
import java.io.IOException;
import java.util.zip.CRC32;
import java.util.zip.Inflater;
import okio.Buffer;
import okio.BufferedSource;
import okio.InflaterSource;
import okio.Okio;
import okio.Segment;
import okio.Source;
import okio.Timeout;

public final class GzipSource
implements Source {
    private final CRC32 crc = new CRC32();
    private final Inflater inflater;
    private final InflaterSource inflaterSource;
    private int section = 0;
    private final BufferedSource source;

    public GzipSource(Source source) {
        if (source == null) {
            throw new IllegalArgumentException("source == null");
        }
        this.inflater = new Inflater(true);
        this.source = Okio.buffer(source);
        this.inflaterSource = new InflaterSource(this.source, this.inflater);
    }

    private void checkEqual(String string2, int n, int n2) throws IOException {
        if (n2 != n) {
            throw new IOException(String.format("%s: actual 0x%08x != expected 0x%08x", string2, n2, n));
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void consumeHeader() throws IOException {
        long l;
        this.source.require(10L);
        byte by = this.source.buffer().getByte(3L);
        boolean bl = (by >> 1 & 1) == 1;
        if (bl) {
            this.updateCrc(this.source.buffer(), 0L, 10L);
        }
        this.checkEqual("ID1ID2", 8075, this.source.readShort());
        this.source.skip(8L);
        if ((by >> 2 & 1) == 1) {
            this.source.require(2L);
            if (bl) {
                this.updateCrc(this.source.buffer(), 0L, 2L);
            }
            short s = this.source.buffer().readShortLe();
            this.source.require(s);
            if (bl) {
                this.updateCrc(this.source.buffer(), 0L, s);
            }
            this.source.skip(s);
        }
        if ((by >> 3 & 1) == 1) {
            l = this.source.indexOf((byte)0);
            if (l == -1L) {
                throw new EOFException();
            }
            if (bl) {
                this.updateCrc(this.source.buffer(), 0L, 1L + l);
            }
            this.source.skip(1L + l);
        }
        if ((by >> 4 & 1) == 1) {
            l = this.source.indexOf((byte)0);
            if (l == -1L) {
                throw new EOFException();
            }
            if (bl) {
                this.updateCrc(this.source.buffer(), 0L, 1L + l);
            }
            this.source.skip(1L + l);
        }
        if (bl) {
            this.checkEqual("FHCRC", this.source.readShortLe(), (short)this.crc.getValue());
            this.crc.reset();
        }
    }

    private void consumeTrailer() throws IOException {
        this.checkEqual("CRC", this.source.readIntLe(), (int)this.crc.getValue());
        this.checkEqual("ISIZE", this.source.readIntLe(), (int)this.inflater.getBytesWritten());
    }

    private void updateCrc(Buffer object, long l, long l2) {
        int n;
        Object object2;
        long l3;
        object = ((Buffer)object).head;
        do {
            object2 = object;
            l3 = l;
            if (l < (long)(((Segment)object).limit - ((Segment)object).pos)) break;
            l -= (long)(((Segment)object).limit - ((Segment)object).pos);
            object = ((Segment)object).next;
        } while (true);
        for (long i = l2; i > 0L; i -= (long)n) {
            int n2 = (int)((long)((Segment)object2).pos + l3);
            n = (int)Math.min((long)(((Segment)object2).limit - n2), i);
            this.crc.update(((Segment)object2).data, n2, n);
            l3 = 0L;
            object2 = ((Segment)object2).next;
        }
    }

    @Override
    public void close() throws IOException {
        this.inflaterSource.close();
    }

    @Override
    public long read(Buffer buffer, long l) throws IOException {
        if (l < 0L) {
            throw new IllegalArgumentException("byteCount < 0: " + l);
        }
        if (l == 0L) {
            return 0L;
        }
        if (this.section == 0) {
            this.consumeHeader();
            this.section = 1;
        }
        if (this.section == 1) {
            long l2 = buffer.size;
            if ((l = this.inflaterSource.read(buffer, l)) != -1L) {
                this.updateCrc(buffer, l2, l);
                return l;
            }
            this.section = 2;
        }
        if (this.section == 2) {
            this.consumeTrailer();
            this.section = 3;
            if (!this.source.exhausted()) {
                throw new IOException("gzip finished without exhausting source");
            }
        }
        return -1L;
    }

    @Override
    public Timeout timeout() {
        return this.source.timeout();
    }
}

