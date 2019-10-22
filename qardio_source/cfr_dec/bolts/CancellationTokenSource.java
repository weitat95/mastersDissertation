/*
 * Decompiled with CFR 0.147.
 */
package bolts;

import bolts.CancellationTokenRegistration;
import java.io.Closeable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ScheduledFuture;

public class CancellationTokenSource
implements Closeable {
    private boolean cancellationRequested;
    private boolean closed;
    private final Object lock;
    private final List<CancellationTokenRegistration> registrations;
    private ScheduledFuture<?> scheduledCancellation;

    private void cancelScheduledCancellation() {
        if (this.scheduledCancellation != null) {
            this.scheduledCancellation.cancel(true);
            this.scheduledCancellation = null;
        }
    }

    private void throwIfClosed() {
        if (this.closed) {
            throw new IllegalStateException("Object already closed");
        }
    }

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
            this.cancelScheduledCancellation();
            Iterator<CancellationTokenRegistration> iterator = this.registrations.iterator();
            do {
                if (!iterator.hasNext()) {
                    this.registrations.clear();
                    this.closed = true;
                    return;
                }
                iterator.next().close();
            } while (true);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isCancellationRequested() {
        Object object = this.lock;
        synchronized (object) {
            this.throwIfClosed();
            return this.cancellationRequested;
        }
    }

    public String toString() {
        return String.format(Locale.US, "%s@%s[cancellationRequested=%s]", this.getClass().getName(), Integer.toHexString(this.hashCode()), Boolean.toString(this.isCancellationRequested()));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void unregister(CancellationTokenRegistration cancellationTokenRegistration) {
        Object object = this.lock;
        synchronized (object) {
            this.throwIfClosed();
            this.registrations.remove(cancellationTokenRegistration);
            return;
        }
    }
}

