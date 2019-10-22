/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.os.Bundle
 */
package android.arch.lifecycle;

import android.app.Activity;
import android.app.Fragment;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.os.Bundle;

public class ReportFragment
extends Fragment {
    private ActivityInitializationListener mProcessListener;

    private void dispatch(Lifecycle.Event event) {
        if (this.getActivity() instanceof LifecycleRegistryOwner) {
            ((LifecycleRegistryOwner)this.getActivity()).getLifecycle().handleLifecycleEvent(event);
        }
    }

    private void dispatchCreate(ActivityInitializationListener activityInitializationListener) {
        if (activityInitializationListener != null) {
            activityInitializationListener.onCreate();
        }
    }

    private void dispatchResume(ActivityInitializationListener activityInitializationListener) {
        if (activityInitializationListener != null) {
            activityInitializationListener.onResume();
        }
    }

    private void dispatchStart(ActivityInitializationListener activityInitializationListener) {
        if (activityInitializationListener != null) {
            activityInitializationListener.onStart();
        }
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.dispatchCreate(this.mProcessListener);
        this.dispatch(Lifecycle.Event.ON_CREATE);
    }

    public void onDestroy() {
        super.onDestroy();
        this.dispatch(Lifecycle.Event.ON_DESTROY);
        this.mProcessListener = null;
    }

    public void onPause() {
        super.onPause();
        this.dispatch(Lifecycle.Event.ON_PAUSE);
    }

    public void onResume() {
        super.onResume();
        this.dispatchResume(this.mProcessListener);
        this.dispatch(Lifecycle.Event.ON_RESUME);
    }

    public void onStart() {
        super.onStart();
        this.dispatchStart(this.mProcessListener);
        this.dispatch(Lifecycle.Event.ON_START);
    }

    public void onStop() {
        super.onStop();
        this.dispatch(Lifecycle.Event.ON_STOP);
    }

    void setProcessListener(ActivityInitializationListener activityInitializationListener) {
        this.mProcessListener = activityInitializationListener;
    }

    static interface ActivityInitializationListener {
        public void onCreate();

        public void onResume();

        public void onStart();
    }

}

