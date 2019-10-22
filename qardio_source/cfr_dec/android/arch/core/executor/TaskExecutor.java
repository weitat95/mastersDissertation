/*
 * Decompiled with CFR 0.147.
 */
package android.arch.core.executor;

public abstract class TaskExecutor {
    public abstract boolean isMainThread();

    public abstract void postToMainThread(Runnable var1);
}

