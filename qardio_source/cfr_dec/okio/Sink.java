/*
 * Decompiled with CFR 0.147.
 */
package okio;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import okio.Buffer;
import okio.Timeout;

public interface Sink
extends Closeable,
Flushable {
    @Override
    public void close() throws IOException;

    @Override
    public void flush() throws IOException;

    public Timeout timeout();

    public void write(Buffer var1, long var2) throws IOException;
}

