/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Fragment
 *  android.app.FragmentManager
 *  android.content.Context
 */
package com.getqardio.android.mvp.activity_tracker;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import butterknife.BindView;
import com.getqardio.android.mvp.activity_tracker.history.view.ActivityTrackerHistoryFragment;
import com.getqardio.android.mvp.activity_tracker.today.view.ActivityTrackerTodayFragment;

class ActivityTrackerPageAdapter
extends FragmentStatePagerAdapter {
    private final Context context;
    @BindView
    TabLayout tabLayout;
    @BindView
    ViewPager viewPager;

    ActivityTrackerPageAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        this.context = context;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(int n) {
        switch (n) {
            default: {
                throw new IllegalArgumentException();
            }
            case 0: {
                return ActivityTrackerTodayFragment.newInstance(System.currentTimeMillis());
            }
            case 1: 
        }
        return ActivityTrackerHistoryFragment.newInstance();
    }

    @Override
    public CharSequence getPageTitle(int n) {
        switch (n) {
            default: {
                throw new IllegalArgumentException();
            }
            case 0: {
                return this.context.getString(2131362133);
            }
            case 1: 
        }
        return this.context.getString(2131362106);
    }
}

