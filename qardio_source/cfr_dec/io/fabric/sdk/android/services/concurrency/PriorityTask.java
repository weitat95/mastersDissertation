/*
 * Decompiled with CFR 0.147.
 */
package io.fabric.sdk.android.services.concurrency;

import io.fabric.sdk.android.services.concurrency.Dependency;
import io.fabric.sdk.android.services.concurrency.Priority;
import io.fabric.sdk.android.services.concurrency.PriorityProvider;
import io.fabric.sdk.android.services.concurrency.Task;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class PriorityTask
implements Dependency<Task>,
PriorityProvider,
Task {
    private final List<Task> dependencies = new ArrayList<Task>();
    private final AtomicBoolean hasRun = new AtomicBoolean(false);
    private final AtomicReference<Throwable> throwable = new AtomicReference<Object>(null);

    public static boolean isProperDelegate(Object object) {
        boolean bl = false;
        try {
            Dependency dependency = (Dependency)object;
            Task task = (Task)object;
            object = (PriorityProvider)object;
            boolean bl2 = bl;
            if (dependency != null) {
                bl2 = bl;
                if (task != null) {
                    bl2 = bl;
                    if (object != null) {
                        bl2 = true;
                    }
                }
            }
            return bl2;
        }
        catch (ClassCastException classCastException) {
            return false;
        }
    }

    @Override
    public void addDependency(Task task) {
        synchronized (this) {
            this.dependencies.add(task);
            return;
        }
    }

    @Override
    public boolean areDependenciesMet() {
        Iterator<Task> iterator = this.getDependencies().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().isFinished()) continue;
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Object object) {
        return Priority.compareTo(this, object);
    }

    @Override
    public Collection<Task> getDependencies() {
        synchronized (this) {
            Collection<Task> collection = Collections.unmodifiableCollection(this.dependencies);
            return collection;
        }
    }

    @Override
    public Priority getPriority() {
        return Priority.NORMAL;
    }

    @Override
    public boolean isFinished() {
        return this.hasRun.get();
    }

    @Override
    public void setError(Throwable throwable) {
        this.throwable.set(throwable);
    }

    @Override
    public void setFinished(boolean bl) {
        synchronized (this) {
            this.hasRun.set(bl);
            return;
        }
    }
}

