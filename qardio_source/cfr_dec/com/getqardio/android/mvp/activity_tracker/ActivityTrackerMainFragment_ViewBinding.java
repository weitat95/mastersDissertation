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
import com.getqardio.android.mvp.activity_tracker.ActivityTrackerMainFragment;

public class ActivityTrackerMainFragment_ViewBinding
implements Unbinder {
    private ActivityTrackerMainFragment target;

    public ActivityTrackerMainFragment_ViewBinding(ActivityTrackerMainFragment activityTrackerMainFragment, View view) {
        this.target = activityTrackerMainFragment;
        activityTrackerMainFragment.viewPager = Utils.findRequiredViewAsType(view, 2131820830, "field 'viewPager'", ViewPager.class);
        activityTrackerMainFragment.tabLayout = Utils.findRequiredViewAsType(view, 2131820829, "field 'tabLayout'", TabLayout.class);
    }
}

