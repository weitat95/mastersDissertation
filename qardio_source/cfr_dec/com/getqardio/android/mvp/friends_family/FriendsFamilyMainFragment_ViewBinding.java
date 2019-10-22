/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package com.getqardio.android.mvp.friends_family;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.getqardio.android.mvp.friends_family.FriendsFamilyMainFragment;

public class FriendsFamilyMainFragment_ViewBinding
implements Unbinder {
    private FriendsFamilyMainFragment target;

    public FriendsFamilyMainFragment_ViewBinding(FriendsFamilyMainFragment friendsFamilyMainFragment, View view) {
        this.target = friendsFamilyMainFragment;
        friendsFamilyMainFragment.viewPager = Utils.findRequiredViewAsType(view, 2131820830, "field 'viewPager'", ViewPager.class);
        friendsFamilyMainFragment.tabLayout = Utils.findRequiredViewAsType(view, 2131820829, "field 'tabLayout'", TabLayout.class);
    }
}

