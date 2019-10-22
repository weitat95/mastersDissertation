/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Application
 *  android.app.Application$ActivityLifecycleCallbacks
 *  android.os.Bundle
 *  android.util.Log
 */
package android.arch.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.EmptyActivityLifecycleCallbacks;
import android.arch.lifecycle.ViewModelStore;
import android.arch.lifecycle.state.SavedStateProvider;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import java.util.HashMap;
import java.util.Map;

public class HolderFragment
extends Fragment {
    private static final HolderFragmentManager sHolderFragmentManager = new HolderFragmentManager();
    private SavedStateProvider mSavedStateProvider = new SavedStateProvider();
    private ViewModelStore mViewModelStore = new ViewModelStore();

    public HolderFragment() {
        this.setRetainInstance(true);
    }

    public static HolderFragment holderFragmentFor(FragmentActivity fragmentActivity) {
        return sHolderFragmentManager.holderFragmentFor(fragmentActivity);
    }

    public ViewModelStore getViewModelStore() {
        return this.mViewModelStore;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mSavedStateProvider.restoreState(bundle);
        sHolderFragmentManager.holderFragmentCreated(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mViewModelStore.clear();
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        this.mSavedStateProvider.saveState(bundle);
    }

    static class HolderFragmentManager {
        private Application.ActivityLifecycleCallbacks mActivityCallbacks;
        private boolean mActivityCallbacksIsAdded = false;
        private Map<Activity, HolderFragment> mNotCommittedActivityHolders = new HashMap<Activity, HolderFragment>();
        private Map<Fragment, HolderFragment> mNotCommittedFragmentHolders = new HashMap<Fragment, HolderFragment>();
        private FragmentManager.FragmentLifecycleCallbacks mParentDestroyedCallback;

        HolderFragmentManager() {
            this.mActivityCallbacks = new EmptyActivityLifecycleCallbacks(){

                @Override
                public void onActivityDestroyed(Activity activity) {
                    if ((HolderFragment)HolderFragmentManager.this.mNotCommittedActivityHolders.remove((Object)activity) != null) {
                        Log.e((String)"ViewModelStores", (String)("Failed to save a ViewModel for " + (Object)activity));
                    }
                }
            };
            this.mParentDestroyedCallback = new FragmentManager.FragmentLifecycleCallbacks(){

                @Override
                public void onFragmentDestroyed(FragmentManager fragmentManager, Fragment fragment) {
                    super.onFragmentDestroyed(fragmentManager, fragment);
                    if ((HolderFragment)HolderFragmentManager.this.mNotCommittedFragmentHolders.remove(fragment) != null) {
                        Log.e((String)"ViewModelStores", (String)("Failed to save a ViewModel for " + fragment));
                    }
                }
            };
        }

        private static HolderFragment createHolderFragment(FragmentManager fragmentManager) {
            HolderFragment holderFragment = new HolderFragment();
            fragmentManager.beginTransaction().add(holderFragment, "android.arch.lifecycle.state.StateProviderHolderFragment").commitAllowingStateLoss();
            return holderFragment;
        }

        private static HolderFragment findHolderFragment(FragmentManager object) {
            if (((FragmentManager)object).isDestroyed()) {
                throw new IllegalStateException("Can't access ViewModels from onDestroy");
            }
            if ((object = ((FragmentManager)object).findFragmentByTag("android.arch.lifecycle.state.StateProviderHolderFragment")) != null && !(object instanceof HolderFragment)) {
                throw new IllegalStateException("Unexpected fragment instance was returned by HOLDER_TAG");
            }
            return (HolderFragment)object;
        }

        void holderFragmentCreated(Fragment fragment) {
            Fragment fragment2 = fragment.getParentFragment();
            if (fragment2 != null) {
                this.mNotCommittedFragmentHolders.remove(fragment2);
                fragment2.getFragmentManager().unregisterFragmentLifecycleCallbacks(this.mParentDestroyedCallback);
                return;
            }
            this.mNotCommittedActivityHolders.remove(fragment.getActivity());
        }

        HolderFragment holderFragmentFor(FragmentActivity fragmentActivity) {
            Object object = fragmentActivity.getSupportFragmentManager();
            HolderFragment holderFragment = HolderFragmentManager.findHolderFragment((FragmentManager)object);
            if (holderFragment != null) {
                return holderFragment;
            }
            holderFragment = this.mNotCommittedActivityHolders.get(fragmentActivity);
            if (holderFragment != null) {
                return holderFragment;
            }
            if (!this.mActivityCallbacksIsAdded) {
                this.mActivityCallbacksIsAdded = true;
                fragmentActivity.getApplication().registerActivityLifecycleCallbacks(this.mActivityCallbacks);
            }
            object = HolderFragmentManager.createHolderFragment((FragmentManager)object);
            this.mNotCommittedActivityHolders.put(fragmentActivity, (HolderFragment)object);
            return object;
        }

    }

}

