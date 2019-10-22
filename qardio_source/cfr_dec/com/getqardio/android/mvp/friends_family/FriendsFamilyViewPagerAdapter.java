/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Fragment
 *  android.app.FragmentManager
 *  android.content.Context
 */
package com.getqardio.android.mvp.friends_family;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v13.app.FragmentStatePagerAdapter;
import com.getqardio.android.mvp.friends_family.follow_me.view.FollowMeFragment;
import com.getqardio.android.mvp.friends_family.i_follow.view.IFollowFragment;

public class FriendsFamilyViewPagerAdapter
extends FragmentStatePagerAdapter {
    private final Context context;

    public FriendsFamilyViewPagerAdapter(Context context, FragmentManager fragmentManager) {
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
                return IFollowFragment.newInstance();
            }
            case 1: 
        }
        return FollowMeFragment.newInstance();
    }

    @Override
    public CharSequence getPageTitle(int n) {
        switch (n) {
            default: {
                throw new IllegalArgumentException();
            }
            case 0: {
                return this.context.getString(2131361922);
            }
            case 1: 
        }
        return this.context.getString(2131361921);
    }
}

