/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package com.getqardio.android.mvp.activity_tracker;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.getqardio.android.mvp.activity_tracker.ActivityTrackerPageAdapter;

public class ActivityTrackerPageAdapter_ViewBinding
implements Unbinder {
    private ActivityTrackerPageAdapter target;

    public ActivityTrackerPageAdapter_ViewBinding(ActivityTrackerPageAdapter activityTrackerPageAdapter, View view) {
        this.target = activityTrackerPageAdapter;
        activityTrackerPageAdapter.viewPager = Utils.findRequiredViewAsType(view, 2131820830, "field 'viewPager'", ViewPager.class);
        activityTrackerPageAdapter.tabLayout = Utils.findRequiredViewAsType(view, 2131820829, "field 'tabLayout'", TabLayout.class);
    }
}

