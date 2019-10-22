/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Application
 *  android.app.Application$ActivityLifecycleCallbacks
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.Handler
 */
package android.arch.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.EmptyActivityLifecycleCallbacks;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleDispatcher;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.ReportFragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

public class ProcessLifecycleOwner
implements LifecycleOwner {
    private static final ProcessLifecycleOwner sInstance = new ProcessLifecycleOwner();
    private Runnable mDelayedPauseRunnable;
    private Handler mHandler;
    private ReportFragment.ActivityInitializationListener mInitializationListener;
    private boolean mPauseSent = true;
    private final LifecycleRegistry mRegistry = new LifecycleRegistry(this);
    private int mResumedCounter = 0;
    private int mStartedCounter = 0;
    private boolean mStopSent = true;

    private ProcessLifecycleOwner() {
        this.mDelayedPauseRunnable = new Runnable(){

            @Override
            public void run() {
                ProcessLifecycleOwner.this.dispatchPauseIfNeeded();
                ProcessLifecycleOwner.this.dispatchStopIfNeeded();
            }
        };
        this.mInitializationListener = new ReportFragment.ActivityInitializationListener(){

            @Override
            public void onCreate() {
            }

            @Override
            public void onResume() {
                ProcessLifecycleOwner.this.activityResumed();
            }

            @Override
            public void onStart() {
                ProcessLifecycleOwner.this.activityStarted();
            }
        };
    }

    private void dispatchPauseIfNeeded() {
        if (this.mResumedCounter == 0) {
            this.mPauseSent = true;
            this.mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
        }
    }

    private void dispatchStopIfNeeded() {
        if (this.mStartedCounter == 0 && this.mPauseSent) {
            this.mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
            this.mStopSent = true;
        }
    }

    static void init(Context context) {
        sInstance.attach(context);
    }

    void activityPaused() {
        --this.mResumedCounter;
        if (this.mResumedCounter == 0) {
            this.mHandler.postDelayed(this.mDelayedPauseRunnable, 700L);
        }
    }

    void activityResumed() {
        block3: {
            block2: {
                ++this.mResumedCounter;
                if (this.mResumedCounter != 1) break block2;
                if (!this.mPauseSent) break block3;
                this.mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
                this.mPauseSent = false;
            }
            return;
        }
        this.mHandler.removeCallbacks(this.mDelayedPauseRunnable);
    }

    void activityStarted() {
        ++this.mStartedCounter;
        if (this.mStartedCounter == 1 && this.mStopSent) {
            this.mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
            this.mStopSent = false;
        }
    }

    void activityStopped() {
        --this.mStartedCounter;
        this.dispatchStopIfNeeded();
    }

    void attach(Context context) {
        this.mHandler = new Handler();
        this.mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
        ((Application)context.getApplicationContext()).registerActivityLifecycleCallbacks((Application.ActivityLifecycleCallbacks)new EmptyActivityLifecycleCallbacks(){

            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {
                LifecycleDispatcher.get(activity).setProcessListener(ProcessLifecycleOwner.this.mInitializationListener);
            }

            @Override
            public void onActivityPaused(Activity activity) {
                ProcessLifecycleOwner.this.activityPaused();
            }

            @Override
            public void onActivityStopped(Activity activity) {
                ProcessLifecycleOwner.this.activityStopped();
            }
        });
    }

    @Override
    public Lifecycle getLifecycle() {
        return this.mRegistry;
    }

}

