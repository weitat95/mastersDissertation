/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Service
 *  android.app.job.JobInfo
 *  android.app.job.JobInfo$Builder
 *  android.app.job.JobParameters
 *  android.app.job.JobScheduler
 *  android.app.job.JobServiceEngine
 *  android.app.job.JobWorkItem
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.os.AsyncTask
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.IBinder
 *  android.os.PowerManager
 *  android.os.PowerManager$WakeLock
 */
package android.support.v4.app;

import android.app.Service;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobServiceEngine;
import android.app.job.JobWorkItem;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Executor;

public abstract class JobIntentService
extends Service {
    static final boolean DEBUG = false;
    static final String TAG = "JobIntentService";
    static final HashMap<ComponentName, WorkEnqueuer> sClassWorkEnqueuer;
    static final Object sLock;
    final ArrayList<CompatWorkItem> mCompatQueue;
    WorkEnqueuer mCompatWorkEnqueuer;
    CommandProcessor mCurProcessor;
    boolean mDestroyed = false;
    boolean mInterruptIfStopped = false;
    CompatJobEngine mJobImpl;
    boolean mStopped = false;

    static {
        sLock = new Object();
        sClassWorkEnqueuer = new HashMap();
    }

    public JobIntentService() {
        if (Build.VERSION.SDK_INT >= 26) {
            this.mCompatQueue = null;
            return;
        }
        this.mCompatQueue = new ArrayList();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void enqueueWork(Context object, ComponentName componentName, int n, Intent intent) {
        if (intent == null) {
            throw new IllegalArgumentException("work must not be null");
        }
        Object object2 = sLock;
        synchronized (object2) {
            object = JobIntentService.getWorkEnqueuer((Context)object, componentName, true, n);
            ((WorkEnqueuer)object).ensureJobId(n);
            ((WorkEnqueuer)object).enqueueWork(intent);
            return;
        }
    }

    public static void enqueueWork(Context context, Class class_, int n, Intent intent) {
        JobIntentService.enqueueWork(context, new ComponentName(context, class_), n, intent);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    static WorkEnqueuer getWorkEnqueuer(Context object, ComponentName componentName, boolean bl, int n) {
        void var0_2;
        WorkEnqueuer workEnqueuer;
        void var1_4;
        WorkEnqueuer workEnqueuer2 = workEnqueuer = sClassWorkEnqueuer.get(var1_4);
        if (workEnqueuer != null) return workEnqueuer2;
        if (Build.VERSION.SDK_INT >= 26) {
            void var3_6;
            void var2_5;
            if (var2_5 == false) {
                throw new IllegalArgumentException("Can't be here without a job id");
            }
            JobWorkEnqueuer jobWorkEnqueuer = new JobWorkEnqueuer((Context)object, (ComponentName)var1_4, (int)var3_6);
        } else {
            CompatWorkEnqueuer compatWorkEnqueuer = new CompatWorkEnqueuer((Context)object, (ComponentName)var1_4);
        }
        sClassWorkEnqueuer.put((ComponentName)var1_4, (WorkEnqueuer)var0_2);
        return var0_2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    GenericWorkItem dequeueWork() {
        if (this.mJobImpl != null) {
            return this.mJobImpl.dequeueWork();
        }
        ArrayList<CompatWorkItem> arrayList = this.mCompatQueue;
        synchronized (arrayList) {
            if (this.mCompatQueue.size() <= 0) return null;
            return this.mCompatQueue.remove(0);
        }
    }

    boolean doStopCurrentWork() {
        if (this.mCurProcessor != null) {
            this.mCurProcessor.cancel(this.mInterruptIfStopped);
        }
        this.mStopped = true;
        return this.onStopCurrentWork();
    }

    void ensureProcessorRunningLocked(boolean bl) {
        if (this.mCurProcessor == null) {
            this.mCurProcessor = new CommandProcessor();
            if (this.mCompatWorkEnqueuer != null && bl) {
                this.mCompatWorkEnqueuer.serviceProcessingStarted();
            }
            this.mCurProcessor.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Object[])new Void[0]);
        }
    }

    public boolean isStopped() {
        return this.mStopped;
    }

    public IBinder onBind(Intent intent) {
        if (this.mJobImpl != null) {
            return this.mJobImpl.compatGetBinder();
        }
        return null;
    }

    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= 26) {
            this.mJobImpl = new JobServiceEngineImpl(this);
            this.mCompatWorkEnqueuer = null;
            return;
        }
        this.mJobImpl = null;
        this.mCompatWorkEnqueuer = JobIntentService.getWorkEnqueuer((Context)this, new ComponentName((Context)this, ((Object)((Object)this)).getClass()), false, 0);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void onDestroy() {
        super.onDestroy();
        if (this.mCompatQueue != null) {
            ArrayList<CompatWorkItem> arrayList = this.mCompatQueue;
            synchronized (arrayList) {
                this.mDestroyed = true;
                this.mCompatWorkEnqueuer.serviceProcessingFinished();
                return;
            }
        }
    }

    protected abstract void onHandleWork(Intent var1);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int onStartCommand(Intent intent, int n, int n2) {
        if (this.mCompatQueue == null) {
            return 2;
        }
        this.mCompatWorkEnqueuer.serviceStartReceived();
        ArrayList<CompatWorkItem> arrayList = this.mCompatQueue;
        synchronized (arrayList) {
            ArrayList<CompatWorkItem> arrayList2 = this.mCompatQueue;
            if (intent == null) {
                intent = new Intent();
            }
            arrayList2.add(new CompatWorkItem(intent, n2));
            this.ensureProcessorRunningLocked(true);
            return 3;
        }
    }

    public boolean onStopCurrentWork() {
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void processorFinished() {
        if (this.mCompatQueue == null) {
            return;
        }
        ArrayList<CompatWorkItem> arrayList = this.mCompatQueue;
        synchronized (arrayList) {
            this.mCurProcessor = null;
            if (this.mCompatQueue != null && this.mCompatQueue.size() > 0) {
                this.ensureProcessorRunningLocked(false);
            } else if (!this.mDestroyed) {
                this.mCompatWorkEnqueuer.serviceProcessingFinished();
            }
            return;
        }
    }

    public void setInterruptIfStopped(boolean bl) {
        this.mInterruptIfStopped = bl;
    }

    final class CommandProcessor
    extends AsyncTask<Void, Void, Void> {
        CommandProcessor() {
        }

        protected Void doInBackground(Void ... object) {
            while ((object = JobIntentService.this.dequeueWork()) != null) {
                JobIntentService.this.onHandleWork(object.getIntent());
                object.complete();
            }
            return null;
        }

        protected void onCancelled(Void void_) {
            JobIntentService.this.processorFinished();
        }

        protected void onPostExecute(Void void_) {
            JobIntentService.this.processorFinished();
        }
    }

    static interface CompatJobEngine {
        public IBinder compatGetBinder();

        public GenericWorkItem dequeueWork();
    }

    static final class CompatWorkEnqueuer
    extends WorkEnqueuer {
        private final Context mContext;
        private final PowerManager.WakeLock mLaunchWakeLock;
        boolean mLaunchingService;
        private final PowerManager.WakeLock mRunWakeLock;
        boolean mServiceProcessing;

        CompatWorkEnqueuer(Context context, ComponentName componentName) {
            super(context, componentName);
            this.mContext = context.getApplicationContext();
            context = (PowerManager)context.getSystemService("power");
            this.mLaunchWakeLock = context.newWakeLock(1, componentName.getClassName() + ":launch");
            this.mLaunchWakeLock.setReferenceCounted(false);
            this.mRunWakeLock = context.newWakeLock(1, componentName.getClassName() + ":run");
            this.mRunWakeLock.setReferenceCounted(false);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        void enqueueWork(Intent intent) {
            intent = new Intent(intent);
            intent.setComponent(this.mComponentName);
            if (this.mContext.startService(intent) == null) {
                return;
            }
            synchronized (this) {
                if (!this.mLaunchingService) {
                    this.mLaunchingService = true;
                    if (!this.mServiceProcessing) {
                        this.mLaunchWakeLock.acquire(60000L);
                    }
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void serviceProcessingFinished() {
            synchronized (this) {
                if (this.mServiceProcessing) {
                    if (this.mLaunchingService) {
                        this.mLaunchWakeLock.acquire(60000L);
                    }
                    this.mServiceProcessing = false;
                    this.mRunWakeLock.release();
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void serviceProcessingStarted() {
            synchronized (this) {
                if (!this.mServiceProcessing) {
                    this.mServiceProcessing = true;
                    this.mRunWakeLock.acquire(600000L);
                    this.mLaunchWakeLock.release();
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void serviceStartReceived() {
            synchronized (this) {
                this.mLaunchingService = false;
                return;
            }
        }
    }

    final class CompatWorkItem
    implements GenericWorkItem {
        final Intent mIntent;
        final int mStartId;

        CompatWorkItem(Intent intent, int n) {
            this.mIntent = intent;
            this.mStartId = n;
        }

        @Override
        public void complete() {
            JobIntentService.this.stopSelf(this.mStartId);
        }

        @Override
        public Intent getIntent() {
            return this.mIntent;
        }
    }

    static interface GenericWorkItem {
        public void complete();

        public Intent getIntent();
    }

    static final class JobServiceEngineImpl
    extends JobServiceEngine
    implements CompatJobEngine {
        static final boolean DEBUG = false;
        static final String TAG = "JobServiceEngineImpl";
        final Object mLock = new Object();
        JobParameters mParams;
        final JobIntentService mService;

        JobServiceEngineImpl(JobIntentService jobIntentService) {
            super((Service)jobIntentService);
            this.mService = jobIntentService;
        }

        @Override
        public IBinder compatGetBinder() {
            return this.getBinder();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Override
        public GenericWorkItem dequeueWork() {
            Object object = this.mLock;
            // MONITORENTER : object
            if (this.mParams == null) {
                // MONITOREXIT : object
                return null;
            }
            JobWorkItem jobWorkItem = this.mParams.dequeueWork();
            // MONITOREXIT : object
            if (jobWorkItem == null) return null;
            jobWorkItem.getIntent().setExtrasClassLoader(this.mService.getClassLoader());
            return new WrapperWorkItem(jobWorkItem);
        }

        public boolean onStartJob(JobParameters jobParameters) {
            this.mParams = jobParameters;
            this.mService.ensureProcessorRunningLocked(false);
            return true;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public boolean onStopJob(JobParameters object) {
            boolean bl = this.mService.doStopCurrentWork();
            object = this.mLock;
            synchronized (object) {
                this.mParams = null;
                return bl;
            }
        }

        final class WrapperWorkItem
        implements GenericWorkItem {
            final JobWorkItem mJobWork;

            WrapperWorkItem(JobWorkItem jobWorkItem) {
                this.mJobWork = jobWorkItem;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void complete() {
                Object object = JobServiceEngineImpl.this.mLock;
                synchronized (object) {
                    if (JobServiceEngineImpl.this.mParams != null) {
                        JobServiceEngineImpl.this.mParams.completeWork(this.mJobWork);
                    }
                    return;
                }
            }

            @Override
            public Intent getIntent() {
                return this.mJobWork.getIntent();
            }
        }

    }

    static final class JobWorkEnqueuer
    extends WorkEnqueuer {
        private final JobInfo mJobInfo;
        private final JobScheduler mJobScheduler;

        JobWorkEnqueuer(Context context, ComponentName componentName, int n) {
            super(context, componentName);
            this.ensureJobId(n);
            this.mJobInfo = new JobInfo.Builder(n, this.mComponentName).setOverrideDeadline(0L).build();
            this.mJobScheduler = (JobScheduler)context.getApplicationContext().getSystemService("jobscheduler");
        }

        @Override
        void enqueueWork(Intent intent) {
            this.mJobScheduler.enqueue(this.mJobInfo, new JobWorkItem(intent));
        }
    }

    static abstract class WorkEnqueuer {
        final ComponentName mComponentName;
        boolean mHasJobId;
        int mJobId;

        WorkEnqueuer(Context context, ComponentName componentName) {
            this.mComponentName = componentName;
        }

        abstract void enqueueWork(Intent var1);

        /*
         * Enabled aggressive block sorting
         */
        void ensureJobId(int n) {
            if (!this.mHasJobId) {
                this.mHasJobId = true;
                this.mJobId = n;
                return;
            } else {
                if (this.mJobId == n) return;
                {
                    throw new IllegalArgumentException("Given job ID " + n + " is different than previous " + this.mJobId);
                }
            }
        }

        public void serviceProcessingFinished() {
        }

        public void serviceProcessingStarted() {
        }

        public void serviceStartReceived() {
        }
    }

}

