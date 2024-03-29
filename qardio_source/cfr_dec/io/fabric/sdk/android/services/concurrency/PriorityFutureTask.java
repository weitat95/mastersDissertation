/*
 * Decompiled with CFR 0.147.
 */
package io.fabric.sdk.android.services.concurrency;

import io.fabric.sdk.android.services.concurrency.Dependency;
import io.fabric.sdk.android.services.concurrency.Priority;
import io.fabric.sdk.android.services.concurrency.PriorityProvider;
import io.fabric.sdk.android.services.concurrency.PriorityTask;
import io.fabric.sdk.android.services.concurrency.Task;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class PriorityFutureTask<V>
extends FutureTask<V>
implements Dependency<Task>,
PriorityProvider,
Task {
    final Object delegate;

    public PriorityFutureTask(Runnable runnable, V v) {
        super(runnable, v);
        this.delegate = this.checkAndInitDelegate(runnable);
    }

    public PriorityFutureTask(Callable<V> callable) {
        super(callable);
        this.delegate = this.checkAndInitDelegate(callable);
    }

    @Override
    public void addDependency(Task task) {
        ((Dependency)((Object)((PriorityProvider)this.getDelegate()))).addDependency(task);
    }

    @Override
    public boolean areDependenciesMet() {
        return ((Dependency)((Object)((PriorityProvider)this.getDelegate()))).areDependenciesMet();
    }

    protected <T extends Dependency<Task> & PriorityProvider> T checkAndInitDelegate(Object object) {
        if (PriorityTask.isProperDelegate(object)) {
            return (T)((Dependency)object);
        }
        return (T)new PriorityTask();
    }

    @Override
    public int compareTo(Object object) {
        return ((PriorityProvider)this.getDelegate()).compareTo(object);
    }

    public <T extends Dependency<Task> & PriorityProvider> T getDelegate() {
        return (T)((Dependency)this.delegate);
    }

    @Override
    public Collection<Task> getDependencies() {
        return ((Dependency)((Object)((PriorityProvider)this.getDelegate()))).getDependencies();
    }

    @Override
    public Priority getPriority() {
        return ((PriorityProvider)this.getDelegate()).getPriority();
    }

    @Override
    public boolean isFinished() {
        return ((Task)((Object)((PriorityProvider)this.getDelegate()))).isFinished();
    }

    @Override
    public void setError(Throwable throwable) {
        ((Task)((Object)((PriorityProvider)this.getDelegate()))).setError(throwable);
    }

    @Override
    public void setFinished(boolean bl) {
        ((Task)((Object)((PriorityProvider)this.getDelegate()))).setFinished(bl);
    }
}

