/*
 * Decompiled with CFR 0.147.
 */
package bolts;

import bolts.CancellationTokenSource;
import java.io.Closeable;

public class CancellationTokenRegistration
implements Closeable {
    private Runnable action;
    private boolean closed;
    private final Object lock;
    private CancellationTokenSource tokenSource;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void close() {
        Object object = this.lock;
        synchronized (object) {
            if (this.closed) {
                return;
            }
            this.closed = true;
            this.tokenSource.unregister(this);
            this.tokenSource = null;
            this.action = null;
            return;
        }
    }
}

