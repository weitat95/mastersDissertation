/*
 * Decompiled with CFR 0.147.
 */
package bolts;

import bolts.CancellationTokenSource;
import java.util.Locale;

public class CancellationToken {
    private final CancellationTokenSource tokenSource;

    public boolean isCancellationRequested() {
        return this.tokenSource.isCancellationRequested();
    }

    public String toString() {
        return String.format(Locale.US, "%s@%s[cancellationRequested=%s]", this.getClass().getName(), Integer.toHexString(this.hashCode()), Boolean.toString(this.tokenSource.isCancellationRequested()));
    }
}

