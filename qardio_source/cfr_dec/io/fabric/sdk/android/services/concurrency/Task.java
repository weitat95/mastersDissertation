/*
 * Decompiled with CFR 0.147.
 */
package io.fabric.sdk.android.services.concurrency;

public interface Task {
    public boolean isFinished();

    public void setError(Throwable var1);

    public void setFinished(boolean var1);
}

