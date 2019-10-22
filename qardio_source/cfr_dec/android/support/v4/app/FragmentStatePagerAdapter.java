/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.util.Log
 *  android.view.View
 *  android.view.ViewGroup
 */
package android.support.v4.app;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Set;

public abstract class FragmentStatePagerAdapter
extends PagerAdapter {
    private static final boolean DEBUG = false;
    private static final String TAG = "FragmentStatePagerAdapt";
    private FragmentTransaction mCurTransaction = null;
    private Fragment mCurrentPrimaryItem = null;
    private final FragmentManager mFragmentManager;
    private ArrayList<Fragment> mFragments;
    private ArrayList<Fragment.SavedState> mSavedState = new ArrayList();

    public FragmentStatePagerAdapter(FragmentManager fragmentManager) {
        this.mFragments = new ArrayList();
        this.mFragmentManager = fragmentManager;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    @Override
    public void destroyItem(ViewGroup object, int n, Object object2) {
        Fragment fragment;
        void var1_3;
        void var2_5;
        fragment = fragment;
        if (this.mCurTransaction == null) {
            this.mCurTransaction = this.mFragmentManager.beginTransaction();
        }
        while (this.mSavedState.size() <= var2_5) {
            this.mSavedState.add(null);
        }
        ArrayList<Fragment.SavedState> arrayList = this.mSavedState;
        if (fragment.isAdded()) {
            Fragment.SavedState savedState = this.mFragmentManager.saveFragmentInstanceState(fragment);
        } else {
            Object var1_4 = null;
        }
        arrayList.set((int)var2_5, (Fragment.SavedState)var1_3);
        this.mFragments.set((int)var2_5, null);
        this.mCurTransaction.remove(fragment);
    }

    @Override
    public void finishUpdate(ViewGroup viewGroup) {
        if (this.mCurTransaction != null) {
            this.mCurTransaction.commitNowAllowingStateLoss();
            this.mCurTransaction = null;
        }
    }

    public abstract Fragment getItem(int var1);

    @Override
    public Object instantiateItem(ViewGroup viewGroup, int n) {
        Fragment.SavedState savedState;
        Fragment fragment;
        if (this.mFragments.size() > n && (fragment = this.mFragments.get(n)) != null) {
            return fragment;
        }
        if (this.mCurTransaction == null) {
            this.mCurTransaction = this.mFragmentManager.beginTransaction();
        }
        fragment = this.getItem(n);
        if (this.mSavedState.size() > n && (savedState = this.mSavedState.get(n)) != null) {
            fragment.setInitialSavedState(savedState);
        }
        while (this.mFragments.size() <= n) {
            this.mFragments.add(null);
        }
        fragment.setMenuVisibility(false);
        fragment.setUserVisibleHint(false);
        this.mFragments.set(n, fragment);
        this.mCurTransaction.add(viewGroup.getId(), fragment);
        return fragment;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return ((Fragment)object).getView() == view;
    }

    @Override
    public void restoreState(Parcelable parcelable, ClassLoader object) {
        if (parcelable != null) {
            int n;
            parcelable = (Bundle)parcelable;
            parcelable.setClassLoader((ClassLoader)object);
            object = parcelable.getParcelableArray("states");
            this.mSavedState.clear();
            this.mFragments.clear();
            if (object != null) {
                for (n = 0; n < ((Parcelable[])object).length; ++n) {
                    this.mSavedState.add((Fragment.SavedState)object[n]);
                }
            }
            for (String string2 : parcelable.keySet()) {
                if (!string2.startsWith("f")) continue;
                n = Integer.parseInt(string2.substring(1));
                Fragment fragment = this.mFragmentManager.getFragment((Bundle)parcelable, string2);
                if (fragment != null) {
                    while (this.mFragments.size() <= n) {
                        this.mFragments.add(null);
                    }
                    fragment.setMenuVisibility(false);
                    this.mFragments.set(n, fragment);
                    continue;
                }
                Log.w((String)"FragmentStatePagerAdapt", (String)("Bad fragment at key " + string2));
            }
        }
    }

    @Override
    public Parcelable saveState() {
        Bundle bundle;
        Object object = null;
        if (this.mSavedState.size() > 0) {
            object = new Bundle();
            bundle = new Fragment.SavedState[this.mSavedState.size()];
            this.mSavedState.toArray((T[])bundle);
            object.putParcelableArray("states", (Parcelable[])bundle);
        }
        for (int i = 0; i < this.mFragments.size(); ++i) {
            Fragment fragment = this.mFragments.get(i);
            bundle = object;
            if (fragment != null) {
                bundle = object;
                if (fragment.isAdded()) {
                    bundle = object;
                    if (object == null) {
                        bundle = new Bundle();
                    }
                    object = "f" + i;
                    this.mFragmentManager.putFragment(bundle, (String)object, fragment);
                }
            }
            object = bundle;
        }
        return object;
    }

    @Override
    public void setPrimaryItem(ViewGroup object, int n, Object object2) {
        object = (Fragment)object2;
        if (object != this.mCurrentPrimaryItem) {
            if (this.mCurrentPrimaryItem != null) {
                this.mCurrentPrimaryItem.setMenuVisibility(false);
                this.mCurrentPrimaryItem.setUserVisibleHint(false);
            }
            if (object != null) {
                ((Fragment)object).setMenuVisibility(true);
                ((Fragment)object).setUserVisibleHint(true);
            }
            this.mCurrentPrimaryItem = object;
        }
    }

    @Override
    public void startUpdate(ViewGroup viewGroup) {
        if (viewGroup.getId() == -1) {
            throw new IllegalStateException("ViewPager with adapter " + this + " requires a view id");
        }
    }
}

