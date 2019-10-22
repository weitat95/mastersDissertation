/*
 * Decompiled with CFR 0.147.
 */
package okio;

import java.io.IOException;
import okio.Buffer;
import okio.ByteString;
import okio.Sink;

public interface BufferedSink
extends Sink {
    public Buffer buffer();

    public BufferedSink emitCompleteSegments() throws IOException;

    @Override
    public void flush() throws IOException;

    public BufferedSink write(ByteString var1) throws IOException;

    public BufferedSink write(byte[] var1) throws IOException;

    public BufferedSink write(byte[] var1, int var2, int var3) throws IOException;

    public BufferedSink writeByte(int var1) throws IOException;

    public BufferedSink writeDecimalLong(long var1) throws IOException;

    public BufferedSink writeHexadecimalUnsignedLong(long var1) throws IOException;

    public BufferedSink writeInt(int var1) throws IOException;

    public BufferedSink writeShort(int var1) throws IOException;

    public BufferedSink writeUtf8(String var1) throws IOException;
}

