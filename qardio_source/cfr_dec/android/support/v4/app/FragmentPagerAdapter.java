/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcelable
 *  android.view.View
 *  android.view.ViewGroup
 */
package android.support.v4.app;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public abstract class FragmentPagerAdapter
extends PagerAdapter {
    private static final boolean DEBUG = false;
    private static final String TAG = "FragmentPagerAdapter";
    private FragmentTransaction mCurTransaction = null;
    private Fragment mCurrentPrimaryItem = null;
    private final FragmentManager mFragmentManager;

    public FragmentPagerAdapter(FragmentManager fragmentManager) {
        this.mFragmentManager = fragmentManager;
    }

    private static String makeFragmentName(int n, long l) {
        return "android:switcher:" + n + ":" + l;
    }

    @Override
    public void destroyItem(ViewGroup viewGroup, int n, Object object) {
        if (this.mCurTransaction == null) {
            this.mCurTransaction = this.mFragmentManager.beginTransaction();
        }
        this.mCurTransaction.detach((Fragment)object);
    }

    @Override
    public void finishUpdate(ViewGroup viewGroup) {
        if (this.mCurTransaction != null) {
            this.mCurTransaction.commitNowAllowingStateLoss();
            this.mCurTransaction = null;
        }
    }

    public abstract Fragment getItem(int var1);

    public long getItemId(int n) {
        return n;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public Object instantiateItem(ViewGroup object, int n) {
        if (this.mCurTransaction == null) {
            this.mCurTransaction = this.mFragmentManager.beginTransaction();
        }
        long l = this.getItemId(n);
        Object object2 = FragmentPagerAdapter.makeFragmentName(object.getId(), l);
        if ((object2 = this.mFragmentManager.findFragmentByTag((String)object2)) != null) {
            this.mCurTransaction.attach((Fragment)object2);
            object = object2;
        } else {
            object2 = this.getItem(n);
            this.mCurTransaction.add(object.getId(), (Fragment)object2, FragmentPagerAdapter.makeFragmentName(object.getId(), l));
            object = object2;
        }
        if (object != this.mCurrentPrimaryItem) {
            ((Fragment)object).setMenuVisibility(false);
            ((Fragment)object).setUserVisibleHint(false);
        }
        return object;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return ((Fragment)object).getView() == view;
    }

    @Override
    public void restoreState(Parcelable parcelable, ClassLoader classLoader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
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

