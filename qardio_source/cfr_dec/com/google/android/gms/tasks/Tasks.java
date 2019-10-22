/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.tasks;

import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.gms.tasks.zzn;
import com.google.android.gms.tasks.zzo;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public final class Tasks {
    public static <TResult> TResult await(Task<TResult> task) throws ExecutionException, InterruptedException {
        zzbq.zzgn("Must not be called on the main application thread");
        zzbq.checkNotNull(task, "Task must not be null");
        if (task.isComplete()) {
            return Tasks.zzc(task);
        }
        zza zza2 = new zza(null);
        Tasks.zza(task, zza2);
        zza2.await();
        return Tasks.zzc(task);
    }

    public static <TResult> TResult await(Task<TResult> task, long l, TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException {
        zzbq.zzgn("Must not be called on the main application thread");
        zzbq.checkNotNull(task, "Task must not be null");
        zzbq.checkNotNull(timeUnit, "TimeUnit must not be null");
        if (task.isComplete()) {
            return Tasks.zzc(task);
        }
        zza zza2 = new zza(null);
        Tasks.zza(task, zza2);
        if (!zza2.await(l, timeUnit)) {
            throw new TimeoutException("Timed out waiting for Task");
        }
        return Tasks.zzc(task);
    }

    public static <TResult> Task<TResult> call(Executor executor, Callable<TResult> callable) {
        zzbq.checkNotNull(executor, "Executor must not be null");
        zzbq.checkNotNull(callable, "Callback must not be null");
        zzn zzn2 = new zzn();
        executor.execute(new zzo(zzn2, callable));
        return zzn2;
    }

    public static <TResult> Task<TResult> forException(Exception exception) {
        zzn zzn2 = new zzn();
        zzn2.setException(exception);
        return zzn2;
    }

    public static <TResult> Task<TResult> forResult(TResult TResult) {
        zzn<TResult> zzn2 = new zzn<TResult>();
        zzn2.setResult(TResult);
        return zzn2;
    }

    private static void zza(Task<?> task, zzb zzb2) {
        task.addOnSuccessListener(TaskExecutors.zzkum, zzb2);
        task.addOnFailureListener(TaskExecutors.zzkum, zzb2);
    }

    private static <TResult> TResult zzc(Task<TResult> task) throws ExecutionException {
        if (task.isSuccessful()) {
            return task.getResult();
        }
        throw new ExecutionException(task.getException());
    }

    static final class zza
    implements zzb {
        private final CountDownLatch zzapd = new CountDownLatch(1);

        private zza() {
        }

        /* synthetic */ zza(zzo zzo2) {
            this();
        }

        public final void await() throws InterruptedException {
            this.zzapd.await();
        }

        public final boolean await(long l, TimeUnit timeUnit) throws InterruptedException {
            return this.zzapd.await(l, timeUnit);
        }

        @Override
        public final void onFailure(Exception exception) {
            this.zzapd.countDown();
        }

        @Override
        public final void onSuccess(Object object) {
            this.zzapd.countDown();
        }
    }

    static interface zzb
    extends OnFailureListener,
    OnSuccessListener<Object> {
    }

}

