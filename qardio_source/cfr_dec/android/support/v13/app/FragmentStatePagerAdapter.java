/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Fragment
 *  android.app.Fragment$SavedState
 *  android.app.FragmentManager
 *  android.app.FragmentTransaction
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.util.Log
 *  android.view.View
 *  android.view.ViewGroup
 */
package android.support.v13.app;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v13.app.FragmentCompat;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Set;

public abstract class FragmentStatePagerAdapter
extends PagerAdapter {
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
     * Enabled aggressive block sorting
     */
    @Override
    public void destroyItem(ViewGroup object, int n, Object object2) {
        object2 = (Fragment)object2;
        if (this.mCurTransaction == null) {
            this.mCurTransaction = this.mFragmentManager.beginTransaction();
        }
        while (this.mSavedState.size() <= n) {
            this.mSavedState.add(null);
        }
        ArrayList<Fragment.SavedState> arrayList = this.mSavedState;
        object = object2.isAdded() ? this.mFragmentManager.saveFragmentInstanceState((Fragment)object2) : null;
        arrayList.set(n, (Fragment.SavedState)object);
        this.mFragments.set(n, null);
        this.mCurTransaction.remove((Fragment)object2);
    }

    @Override
    public void finishUpdate(ViewGroup viewGroup) {
        if (this.mCurTransaction != null) {
            this.mCurTransaction.commitAllowingStateLoss();
            this.mCurTransaction = null;
            this.mFragmentManager.executePendingTransactions();
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
        FragmentCompat.setUserVisibleHint(fragment, false);
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
                    FragmentCompat.setMenuVisibility(fragment, false);
                    this.mFragments.set(n, fragment);
                    continue;
                }
                Log.w((String)"FragStatePagerAdapter", (String)("Bad fragment at key " + string2));
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
    public void setPrimaryItem(ViewGroup viewGroup, int n, Object object) {
        viewGroup = (Fragment)object;
        if (viewGroup != this.mCurrentPrimaryItem) {
            if (this.mCurrentPrimaryItem != null) {
                this.mCurrentPrimaryItem.setMenuVisibility(false);
                FragmentCompat.setUserVisibleHint(this.mCurrentPrimaryItem, false);
            }
            if (viewGroup != null) {
                viewGroup.setMenuVisibility(true);
                FragmentCompat.setUserVisibleHint((Fragment)viewGroup, true);
            }
            this.mCurrentPrimaryItem = viewGroup;
        }
    }

    @Override
    public void startUpdate(ViewGroup viewGroup) {
        if (viewGroup.getId() == -1) {
            throw new IllegalStateException("ViewPager with adapter " + this + " requires a view id");
        }
    }
}

