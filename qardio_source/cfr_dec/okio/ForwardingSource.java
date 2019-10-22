/*
 * Decompiled with CFR 0.147.
 */
package okio;

import java.io.IOException;
import okio.Buffer;
import okio.Source;
import okio.Timeout;

public abstract class ForwardingSource
implements Source {
    private final Source delegate;

    public ForwardingSource(Source source) {
        if (source == null) {
            throw new IllegalArgumentException("delegate == null");
        }
        this.delegate = source;
    }

    @Override
    public void close() throws IOException {
        this.delegate.close();
    }

    @Override
    public long read(Buffer buffer, long l) throws IOException {
        return this.delegate.read(buffer, l);
    }

    @Override
    public Timeout timeout() {
        return this.delegate.timeout();
    }

    public String toString() {
        return this.getClass().getSimpleName() + "(" + this.delegate.toString() + ")";
    }
}

