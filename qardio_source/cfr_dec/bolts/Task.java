/*
 * Decompiled with CFR 0.147.
 */
package bolts;

import bolts.AndroidExecutors;
import bolts.BoltsExecutors;
import bolts.CancellationToken;
import bolts.Continuation;
import bolts.ExecutorException;
import bolts.TaskCompletionSource;
import bolts.UnobservedErrorNotifier;
import bolts.UnobservedTaskException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

public class Task<TResult> {
    public static final ExecutorService BACKGROUND_EXECUTOR = BoltsExecutors.background();
    private static final Executor IMMEDIATE_EXECUTOR = BoltsExecutors.immediate();
    private static Task<?> TASK_CANCELLED;
    private static Task<Boolean> TASK_FALSE;
    private static Task<?> TASK_NULL;
    private static Task<Boolean> TASK_TRUE;
    public static final Executor UI_THREAD_EXECUTOR;
    private static volatile UnobservedExceptionHandler unobservedExceptionHandler;
    private boolean cancelled;
    private boolean complete;
    private List<Continuation<TResult, Void>> continuations;
    private Exception error;
    private boolean errorHasBeenObserved;
    private final Object lock = new Object();
    private TResult result;
    private UnobservedErrorNotifier unobservedErrorNotifier;

    static {
        UI_THREAD_EXECUTOR = AndroidExecutors.uiThread();
        TASK_NULL = new Task<Object>(null);
        TASK_TRUE = new Task<Boolean>(Boolean.valueOf(true));
        TASK_FALSE = new Task<Boolean>(Boolean.valueOf(false));
        TASK_CANCELLED = new Task<TResult>(true);
    }

    Task() {
        this.continuations = new ArrayList<Continuation<TResult, Void>>();
    }

    private Task(TResult TResult) {
        this.continuations = new ArrayList<Continuation<TResult, Void>>();
        this.trySetResult(TResult);
    }

    private Task(boolean bl) {
        this.continuations = new ArrayList<Continuation<TResult, Void>>();
        if (bl) {
            this.trySetCancelled();
            return;
        }
        this.trySetResult(null);
    }

    public static <TResult> Task<TResult> call(Callable<TResult> callable, Executor executor) {
        return Task.call(callable, executor, null);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static <TResult> Task<TResult> call(final Callable<TResult> callable, Executor executor, final CancellationToken cancellationToken) {
        final TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        try {
            executor.execute(new Runnable(){

                @Override
                public void run() {
                    if (cancellationToken != null && cancellationToken.isCancellationRequested()) {
                        taskCompletionSource.setCancelled();
                        return;
                    }
                    try {
                        taskCompletionSource.setResult(callable.call());
                        return;
                    }
                    catch (CancellationException cancellationException) {
                        taskCompletionSource.setCancelled();
                        return;
                    }
                    catch (Exception exception) {
                        taskCompletionSource.setError(exception);
                        return;
                    }
                }
            });
            do {
                return taskCompletionSource.getTask();
                break;
            } while (true);
        }
        catch (Exception exception) {
            taskCompletionSource.setError(new ExecutorException(exception));
            return taskCompletionSource.getTask();
        }
    }

    private static <TContinuationResult, TResult> void completeAfterTask(final TaskCompletionSource<TContinuationResult> taskCompletionSource, final Continuation<TResult, Task<TContinuationResult>> continuation, final Task<TResult> task, Executor executor, final CancellationToken cancellationToken) {
        try {
            executor.execute(new Runnable(){

                /*
                 * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 */
                @Override
                public void run() {
                    Task task2;
                    if (cancellationToken != null && cancellationToken.isCancellationRequested()) {
                        taskCompletionSource.setCancelled();
                        return;
                    }
                    try {
                        task2 = (Task)continuation.then(task);
                        if (task2 == null) {
                            taskCompletionSource.setResult(null);
                            return;
                        }
                    }
                    catch (CancellationException cancellationException) {
                        taskCompletionSource.setCancelled();
                        return;
                    }
                    catch (Exception exception) {
                        taskCompletionSource.setError(exception);
                        return;
                    }
                    {
                        task2.continueWith(new Continuation<TContinuationResult, Void>(){

                            @Override
                            public Void then(Task<TContinuationResult> task) {
                                if (cancellationToken != null && cancellationToken.isCancellationRequested()) {
                                    taskCompletionSource.setCancelled();
                                    return null;
                                }
                                if (task.isCancelled()) {
                                    taskCompletionSource.setCancelled();
                                    return null;
                                }
                                if (task.isFaulted()) {
                                    taskCompletionSource.setError(task.getError());
                                    return null;
                                }
                                taskCompletionSource.setResult(task.getResult());
                                return null;
                            }
                        });
                        return;
                    }
                }

            });
            return;
        }
        catch (Exception exception) {
            taskCompletionSource.setError(new ExecutorException(exception));
            return;
        }
    }

    private static <TContinuationResult, TResult> void completeImmediately(final TaskCompletionSource<TContinuationResult> taskCompletionSource, final Continuation<TResult, TContinuationResult> continuation, final Task<TResult> task, Executor executor, final CancellationToken cancellationToken) {
        try {
            executor.execute(new Runnable(){

                @Override
                public void run() {
                    if (cancellationToken != null && cancellationToken.isCancellationRequested()) {
                        taskCompletionSource.setCancelled();
                        return;
                    }
                    try {
                        Object TContinuationResult = continuation.then(task);
                        taskCompletionSource.setResult(TContinuationResult);
                        return;
                    }
                    catch (CancellationException cancellationException) {
                        taskCompletionSource.setCancelled();
                        return;
                    }
                    catch (Exception exception) {
                        taskCompletionSource.setError(exception);
                        return;
                    }
                }
            });
            return;
        }
        catch (Exception exception) {
            taskCompletionSource.setError(new ExecutorException(exception));
            return;
        }
    }

    public static <TResult> Task<TResult> forError(Exception exception) {
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        taskCompletionSource.setError(exception);
        return taskCompletionSource.getTask();
    }

    public static <TResult> Task<TResult> forResult(TResult TResult) {
        if (TResult == null) {
            return TASK_NULL;
        }
        if (TResult instanceof Boolean) {
            if (((Boolean)TResult).booleanValue()) {
                return TASK_TRUE;
            }
            return TASK_FALSE;
        }
        TaskCompletionSource<TResult> taskCompletionSource = new TaskCompletionSource<TResult>();
        taskCompletionSource.setResult(TResult);
        return taskCompletionSource.getTask();
    }

    public static UnobservedExceptionHandler getUnobservedExceptionHandler() {
        return unobservedExceptionHandler;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void runContinuations() {
        Object object = this.lock;
        synchronized (object) {
            Iterator<Continuation<TResult, Void>> iterator = this.continuations.iterator();
            do {
                if (!iterator.hasNext()) {
                    this.continuations = null;
                    return;
                }
                Continuation<TResult, Void> continuation = iterator.next();
                try {
                    continuation.then(this);
                }
                catch (RuntimeException runtimeException) {
                    throw runtimeException;
                }
                catch (Exception exception) {
                    throw new RuntimeException(exception);
                }
            } while (true);
        }
    }

    public <TContinuationResult> Task<TContinuationResult> continueWith(Continuation<TResult, TContinuationResult> continuation) {
        return this.continueWith(continuation, IMMEDIATE_EXECUTOR, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public <TContinuationResult> Task<TContinuationResult> continueWith(final Continuation<TResult, TContinuationResult> continuation, final Executor executor, final CancellationToken cancellationToken) {
        final TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        Object object = this.lock;
        // MONITORENTER : object
        boolean bl = this.isCompleted();
        if (!bl) {
            this.continuations.add(new Continuation<TResult, Void>(){

                @Override
                public Void then(Task<TResult> task) {
                    Task.completeImmediately(taskCompletionSource, continuation, task, executor, cancellationToken);
                    return null;
                }
            });
        }
        // MONITOREXIT : object
        if (!bl) return taskCompletionSource.getTask();
        Task.completeImmediately(taskCompletionSource, continuation, this, executor, cancellationToken);
        return taskCompletionSource.getTask();
    }

    public <TContinuationResult> Task<TContinuationResult> continueWithTask(Continuation<TResult, Task<TContinuationResult>> continuation) {
        return this.continueWithTask(continuation, IMMEDIATE_EXECUTOR, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public <TContinuationResult> Task<TContinuationResult> continueWithTask(final Continuation<TResult, Task<TContinuationResult>> continuation, final Executor executor, final CancellationToken cancellationToken) {
        final TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        Object object = this.lock;
        // MONITORENTER : object
        boolean bl = this.isCompleted();
        if (!bl) {
            this.continuations.add(new Continuation<TResult, Void>(){

                @Override
                public Void then(Task<TResult> task) {
                    Task.completeAfterTask(taskCompletionSource, continuation, task, executor, cancellationToken);
                    return null;
                }
            });
        }
        // MONITOREXIT : object
        if (!bl) return taskCompletionSource.getTask();
        Task.completeAfterTask(taskCompletionSource, continuation, this, executor, cancellationToken);
        return taskCompletionSource.getTask();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Exception getError() {
        Object object = this.lock;
        synchronized (object) {
            if (this.error == null) return this.error;
            this.errorHasBeenObserved = true;
            if (this.unobservedErrorNotifier == null) return this.error;
            this.unobservedErrorNotifier.setObserved();
            this.unobservedErrorNotifier = null;
            return this.error;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public TResult getResult() {
        Object object = this.lock;
        synchronized (object) {
            TResult TResult = this.result;
            return TResult;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isCancelled() {
        Object object = this.lock;
        synchronized (object) {
            return this.cancelled;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isCompleted() {
        Object object = this.lock;
        synchronized (object) {
            return this.complete;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isFaulted() {
        Object object = this.lock;
        synchronized (object) {
            if (this.getError() == null) return false;
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    boolean trySetCancelled() {
        Object object = this.lock;
        synchronized (object) {
            if (this.complete) {
                return false;
            }
            this.complete = true;
            this.cancelled = true;
            this.lock.notifyAll();
            this.runContinuations();
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    boolean trySetError(Exception exception) {
        Object object = this.lock;
        synchronized (object) {
            if (this.complete) {
                return false;
            }
            this.complete = true;
            this.error = exception;
            this.errorHasBeenObserved = false;
            this.lock.notifyAll();
            this.runContinuations();
            if (!this.errorHasBeenObserved && Task.getUnobservedExceptionHandler() != null) {
                this.unobservedErrorNotifier = new UnobservedErrorNotifier(this);
            }
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    boolean trySetResult(TResult TResult) {
        Object object = this.lock;
        synchronized (object) {
            if (this.complete) {
                return false;
            }
            this.complete = true;
            this.result = TResult;
            this.lock.notifyAll();
            this.runContinuations();
            return true;
        }
    }

    public static interface UnobservedExceptionHandler {
        public void unobservedException(Task<?> var1, UnobservedTaskException var2);
    }

}

