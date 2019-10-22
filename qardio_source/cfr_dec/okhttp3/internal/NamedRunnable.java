/*
 * Decompiled with CFR 0.147.
 */
package okhttp3.internal;

import okhttp3.internal.Util;

public abstract class NamedRunnable
implements Runnable {
    protected final String name;

    public NamedRunnable(String string2, Object ... arrobject) {
        this.name = Util.format(string2, arrobject);
    }

    protected abstract void execute();

    @Override
    public final void run() {
        String string2 = Thread.currentThread().getName();
        Thread.currentThread().setName(this.name);
        try {
            this.execute();
            return;
        }
        finally {
            Thread.currentThread().setName(string2);
        }
    }
}

