/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Application
 *  android.app.Application$ActivityLifecycleCallbacks
 *  android.app.Fragment
 *  android.app.FragmentManager
 *  android.app.FragmentTransaction
 *  android.content.Context
 *  android.os.Bundle
 */
package android.arch.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.arch.lifecycle.EmptyActivityLifecycleCallbacks;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.ReportFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

class LifecycleDispatcher {
    private static AtomicBoolean sInitialized = new AtomicBoolean(false);

    private static void dispatchIfLifecycleOwner(android.support.v4.app.Fragment fragment, Lifecycle.Event event) {
        if (fragment instanceof LifecycleRegistryOwner) {
            ((LifecycleRegistryOwner)((Object)fragment)).getLifecycle().handleLifecycleEvent(event);
        }
    }

    static ReportFragment get(Activity activity) {
        return (ReportFragment)activity.getFragmentManager().findFragmentByTag("android.arch.lifecycle.LifecycleDispatcher.report_fragment_tag");
    }

    static void init(Context context) {
        if (sInitialized.getAndSet(true)) {
            return;
        }
        ((Application)context.getApplicationContext()).registerActivityLifecycleCallbacks((Application.ActivityLifecycleCallbacks)new DispatcherActivityCallback());
    }

    private static void markState(FragmentActivity fragmentActivity, Lifecycle.State state) {
        LifecycleDispatcher.markStateIn(fragmentActivity, state);
        LifecycleDispatcher.markState(fragmentActivity.getSupportFragmentManager(), state);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static void markState(FragmentManager iterator, Lifecycle.State state) {
        if ((iterator = ((FragmentManager)((Object)iterator)).getFragments()) != null) {
            iterator = iterator.iterator();
            while (iterator.hasNext()) {
                android.support.v4.app.Fragment fragment = (android.support.v4.app.Fragment)iterator.next();
                if (fragment == null) continue;
                LifecycleDispatcher.markStateIn(fragment, state);
                if (!fragment.isAdded()) continue;
                LifecycleDispatcher.markState(fragment.getChildFragmentManager(), state);
            }
        }
    }

    private static void markStateIn(Object object, Lifecycle.State state) {
        if (object instanceof LifecycleRegistryOwner) {
            ((LifecycleRegistryOwner)object).getLifecycle().markState(state);
        }
    }

    public static class DestructionReportFragment
    extends android.support.v4.app.Fragment {
        protected void dispatch(Lifecycle.Event event) {
            LifecycleDispatcher.dispatchIfLifecycleOwner(this.getParentFragment(), event);
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            this.dispatch(Lifecycle.Event.ON_DESTROY);
        }

        @Override
        public void onPause() {
            super.onPause();
            this.dispatch(Lifecycle.Event.ON_PAUSE);
        }

        @Override
        public void onStop() {
            super.onStop();
            this.dispatch(Lifecycle.Event.ON_STOP);
        }
    }

    static class DispatcherActivityCallback
    extends EmptyActivityLifecycleCallbacks {
        private final FragmentCallback mFragmentCallback = new FragmentCallback();

        DispatcherActivityCallback() {
        }

        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {
            if (activity instanceof FragmentActivity) {
                ((FragmentActivity)activity).getSupportFragmentManager().registerFragmentLifecycleCallbacks(this.mFragmentCallback, true);
            }
            if ((activity = activity.getFragmentManager()).findFragmentByTag("android.arch.lifecycle.LifecycleDispatcher.report_fragment_tag") == null) {
                activity.beginTransaction().add((Fragment)new ReportFragment(), "android.arch.lifecycle.LifecycleDispatcher.report_fragment_tag").commit();
                activity.executePendingTransactions();
            }
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
            if (activity instanceof FragmentActivity) {
                LifecycleDispatcher.markState((FragmentActivity)activity, Lifecycle.State.CREATED);
            }
        }

        @Override
        public void onActivityStopped(Activity activity) {
            if (activity instanceof FragmentActivity) {
                LifecycleDispatcher.markState((FragmentActivity)activity, Lifecycle.State.CREATED);
            }
        }
    }

    static class FragmentCallback
    extends FragmentManager.FragmentLifecycleCallbacks {
        FragmentCallback() {
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void onFragmentCreated(FragmentManager fragmentManager, android.support.v4.app.Fragment fragment, Bundle bundle) {
            LifecycleDispatcher.dispatchIfLifecycleOwner(fragment, Lifecycle.Event.ON_CREATE);
            if (!(fragment instanceof LifecycleRegistryOwner) || fragment.getChildFragmentManager().findFragmentByTag("android.arch.lifecycle.LifecycleDispatcher.report_fragment_tag") != null) {
                return;
            }
            fragment.getChildFragmentManager().beginTransaction().add(new DestructionReportFragment(), "android.arch.lifecycle.LifecycleDispatcher.report_fragment_tag").commit();
        }

        @Override
        public void onFragmentResumed(FragmentManager fragmentManager, android.support.v4.app.Fragment fragment) {
            LifecycleDispatcher.dispatchIfLifecycleOwner(fragment, Lifecycle.Event.ON_RESUME);
        }

        @Override
        public void onFragmentStarted(FragmentManager fragmentManager, android.support.v4.app.Fragment fragment) {
            LifecycleDispatcher.dispatchIfLifecycleOwner(fragment, Lifecycle.Event.ON_START);
        }
    }

}

