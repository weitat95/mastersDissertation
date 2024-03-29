/*
 * Decompiled with CFR 0.147.
 */
package io.fabric.sdk.android.services.concurrency;

import io.fabric.sdk.android.services.concurrency.AsyncTask;
import io.fabric.sdk.android.services.concurrency.Dependency;
import io.fabric.sdk.android.services.concurrency.Priority;
import io.fabric.sdk.android.services.concurrency.PriorityFutureTask;
import io.fabric.sdk.android.services.concurrency.PriorityProvider;
import io.fabric.sdk.android.services.concurrency.PriorityTask;
import io.fabric.sdk.android.services.concurrency.Task;
import java.util.Collection;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

public abstract class PriorityAsyncTask<Params, Progress, Result>
extends AsyncTask<Params, Progress, Result>
implements Dependency<Task>,
PriorityProvider,
Task {
    private final PriorityTask priorityTask = new PriorityTask();

    @Override
    public void addDependency(Task task) {
        if (this.getStatus() != AsyncTask.Status.PENDING) {
            throw new IllegalStateException("Must not add Dependency after task is running");
        }
        ((Dependency)((Object)((PriorityProvider)this.getDelegate()))).addDependency(task);
    }

    @Override
    public boolean areDependenciesMet() {
        return ((Dependency)((Object)((PriorityProvider)this.getDelegate()))).areDependenciesMet();
    }

    @Override
    public int compareTo(Object object) {
        return Priority.compareTo(this, object);
    }

    public final void executeOnExecutor(ExecutorService executorService, Params ... arrParams) {
        super.executeOnExecutor(new ProxyExecutor(executorService, this), arrParams);
    }

    public <T extends Dependency<Task> & PriorityProvider> T getDelegate() {
        return (T)this.priorityTask;
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

    private static class ProxyExecutor<Result>
    implements Executor {
        private final Executor executor;
        private final PriorityAsyncTask task;

        public ProxyExecutor(Executor executor, PriorityAsyncTask priorityAsyncTask) {
            this.executor = executor;
            this.task = priorityAsyncTask;
        }

        @Override
        public void execute(Runnable runnable) {
            this.executor.execute(new PriorityFutureTask<Result>(runnable, null){

                @Override
                public <T extends Dependency<Task> & PriorityProvider> T getDelegate() {
                    return (T)ProxyExecutor.this.task;
                }
            });
        }

    }

}

