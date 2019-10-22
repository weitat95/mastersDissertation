/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.SystemClock
 *  javax.annotation.concurrent.GuardedBy
 */
package com.facebook.imagepipeline.producers;

import android.os.SystemClock;
import com.facebook.imagepipeline.image.EncodedImage;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import javax.annotation.concurrent.GuardedBy;

public class JobScheduler {
    private final Runnable mDoJobRunnable;
    @GuardedBy(value="this")
    EncodedImage mEncodedImage;
    private final Executor mExecutor;
    @GuardedBy(value="this")
    boolean mIsLast;
    private final JobRunnable mJobRunnable;
    @GuardedBy(value="this")
    long mJobStartTime;
    @GuardedBy(value="this")
    JobState mJobState;
    @GuardedBy(value="this")
    long mJobSubmitTime;
    private final int mMinimumJobIntervalMs;
    private final Runnable mSubmitJobRunnable;

    public JobScheduler(Executor executor, JobRunnable jobRunnable, int n) {
        this.mExecutor = executor;
        this.mJobRunnable = jobRunnable;
        this.mMinimumJobIntervalMs = n;
        this.mDoJobRunnable = new Runnable(){

            @Override
            public void run() {
                JobScheduler.this.doJob();
            }
        };
        this.mSubmitJobRunnable = new Runnable(){

            @Override
            public void run() {
                JobScheduler.this.submitJob();
            }
        };
        this.mEncodedImage = null;
        this.mIsLast = false;
        this.mJobState = JobState.IDLE;
        this.mJobSubmitTime = 0L;
        this.mJobStartTime = 0L;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void doJob() {
        EncodedImage encodedImage;
        boolean bl;
        long l = SystemClock.uptimeMillis();
        synchronized (this) {
            encodedImage = this.mEncodedImage;
            bl = this.mIsLast;
            this.mEncodedImage = null;
            this.mIsLast = false;
            this.mJobState = JobState.RUNNING;
            this.mJobStartTime = l;
        }
        try {
            if (!JobScheduler.shouldProcess(encodedImage, bl)) return;
            this.mJobRunnable.run(encodedImage, bl);
            return;
        }
        finally {
            EncodedImage.closeSafely(encodedImage);
            this.onJobFinished();
        }
    }

    private void enqueueJob(long l) {
        if (l > 0L) {
            JobStartExecutorSupplier.get().schedule(this.mSubmitJobRunnable, l, TimeUnit.MILLISECONDS);
            return;
        }
        this.mSubmitJobRunnable.run();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void onJobFinished() {
        long l = SystemClock.uptimeMillis();
        long l2 = 0L;
        boolean bl = false;
        // MONITORENTER : this
        if (this.mJobState == JobState.RUNNING_AND_PENDING) {
            l2 = Math.max(this.mJobStartTime + (long)this.mMinimumJobIntervalMs, l);
            bl = true;
            this.mJobSubmitTime = l;
            this.mJobState = JobState.QUEUED;
        } else {
            this.mJobState = JobState.IDLE;
        }
        // MONITOREXIT : this
        if (!bl) return;
        this.enqueueJob(l2 - l);
    }

    private static boolean shouldProcess(EncodedImage encodedImage, boolean bl) {
        return bl || EncodedImage.isValid(encodedImage);
    }

    private void submitJob() {
        this.mExecutor.execute(this.mDoJobRunnable);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void clearJob() {
        EncodedImage encodedImage;
        synchronized (this) {
            encodedImage = this.mEncodedImage;
            this.mEncodedImage = null;
            this.mIsLast = false;
        }
        EncodedImage.closeSafely(encodedImage);
    }

    public long getQueuedTime() {
        synchronized (this) {
            long l = this.mJobStartTime;
            long l2 = this.mJobSubmitTime;
            return l - l2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public boolean scheduleJob() {
        long l = SystemClock.uptimeMillis();
        long l2 = 0L;
        boolean bl = false;
        // MONITORENTER : this
        if (!JobScheduler.shouldProcess(this.mEncodedImage, this.mIsLast)) {
            // MONITOREXIT : this
            return false;
        }
        boolean bl2 = bl;
        long l3 = l2;
        switch (this.mJobState) {
            case IDLE: {
                l3 = Math.max(this.mJobStartTime + (long)this.mMinimumJobIntervalMs, l);
                bl2 = true;
                this.mJobSubmitTime = l;
                this.mJobState = JobState.QUEUED;
                break;
            }
            case RUNNING: {
                this.mJobState = JobState.RUNNING_AND_PENDING;
                bl2 = bl;
                l3 = l2;
            }
            case QUEUED: {
                break;
            }
            default: {
                bl2 = bl;
                l3 = l2;
            }
        }
        // MONITOREXIT : this
        if (!bl2) return true;
        this.enqueueJob(l3 - l);
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean updateJob(EncodedImage encodedImage, boolean bl) {
        EncodedImage encodedImage2;
        if (!JobScheduler.shouldProcess(encodedImage, bl)) {
            return false;
        }
        synchronized (this) {
            encodedImage2 = this.mEncodedImage;
            this.mEncodedImage = EncodedImage.cloneOrNull(encodedImage);
            this.mIsLast = bl;
        }
        EncodedImage.closeSafely(encodedImage2);
        return true;
    }

    public static interface JobRunnable {
        public void run(EncodedImage var1, boolean var2);
    }

    static class JobStartExecutorSupplier {
        private static ScheduledExecutorService sJobStarterExecutor;

        static ScheduledExecutorService get() {
            if (sJobStarterExecutor == null) {
                sJobStarterExecutor = Executors.newSingleThreadScheduledExecutor();
            }
            return sJobStarterExecutor;
        }
    }

    static enum JobState {
        IDLE,
        QUEUED,
        RUNNING,
        RUNNING_AND_PENDING;

    }

}

