/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.app.FragmentManager
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.content.res.Resources
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.TransitionDrawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.preference.PreferenceManager
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 */
package com.getqardio.android.mvp.friends_family;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.getqardio.android.mvp.MvpApplication;
import com.getqardio.android.mvp.friends_family.FriendsFamilyMainFragment$$Lambda$1;
import com.getqardio.android.mvp.friends_family.FriendsFamilyViewPagerAdapter;
import com.getqardio.android.ui.activity.MainActivity;
import com.getqardio.android.utils.notifications.AppNotificationAssistant;
import com.getqardio.android.utils.ui.ActivityUtils;
import com.getqardio.android.utils.ui.Convert;

public class FriendsFamilyMainFragment
extends Fragment {
    private static String FnF_NOTIFICATION_DISABLED_COUNT = "FnF_notification_disabled_count";
    @BindView
    TabLayout tabLayout;
    private boolean tabTransitionShown;
    private TransitionDrawable transition;
    @BindView
    ViewPager viewPager;

    static /* synthetic */ void lambda$onCreateView$0(View view) {
        MvpApplication.get(view.getContext()).getNotificationAssistant().openNotificationChannelSetting("progress_channel_id");
    }

    public static FriendsFamilyMainFragment newInstance() {
        return new FriendsFamilyMainFragment();
    }

    private void setupViews() {
        ActivityUtils.getActionBar(this.getActivity()).setTitle(2131361935);
        this.viewPager.setAdapter(new FriendsFamilyViewPagerAdapter((Context)this.getActivity(), this.getChildFragmentManager()));
        this.tabLayout.setupWithViewPager(this.viewPager);
        this.transition = (TransitionDrawable)this.tabLayout.getBackground();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void enableContextualToolbarMode(boolean bl) {
        if (this.getActivity() == null) return;
        if (bl) {
            ActivityUtils.changeStatusBarColor(this.getActivity(), ContextCompat.getColor((Context)this.getActivity(), 2131689549));
            this.tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor((Context)this.getActivity(), 2131689621));
            if (this.tabTransitionShown) return;
            {
                this.transition.startTransition(250);
                this.tabTransitionShown = true;
                return;
            }
        }
        ActivityUtils.changeStatusBarColor(this.getActivity(), ContextCompat.getColor((Context)this.getActivity(), 2131689600));
        this.tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor((Context)this.getActivity(), 2131689548));
        if (!this.tabTransitionShown) {
            return;
        }
        this.transition.reverseTransition(250);
        this.tabTransitionShown = false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layoutInflater = layoutInflater.inflate(2130968718, viewGroup, false);
        ButterKnife.bind((Object)this, (View)layoutInflater);
        this.setupViews();
        this.setHasOptionsMenu(true);
        this.transition = (TransitionDrawable)this.tabLayout.getBackground();
        this.tabTransitionShown = false;
        if (Build.VERSION.SDK_INT < 26) return layoutInflater;
        {
            int n = PreferenceManager.getDefaultSharedPreferences((Context)this.getActivity()).getInt(FnF_NOTIFICATION_DISABLED_COUNT, 0);
            if (!MvpApplication.get((Context)this.getActivity()).getNotificationAssistant().checkChannelEnabled("progress_channel_id")) {
                if (n == 10) {
                    ((Snackbar)Snackbar.make((View)layoutInflater, 2131362235, 0).setDuration(5000)).setAction(this.getString(2131362228).toUpperCase(), FriendsFamilyMainFragment$$Lambda$1.lambdaFactory$()).show();
                    PreferenceManager.getDefaultSharedPreferences((Context)this.getActivity()).edit().putInt(FnF_NOTIFICATION_DISABLED_COUNT, n + 1).apply();
                    return layoutInflater;
                } else {
                    if (n >= 11) return layoutInflater;
                    {
                        PreferenceManager.getDefaultSharedPreferences((Context)this.getActivity()).edit().putInt(FnF_NOTIFICATION_DISABLED_COUNT, n + 1).apply();
                        return layoutInflater;
                    }
                }
            } else {
                if (n == 0) return layoutInflater;
                {
                    PreferenceManager.getDefaultSharedPreferences((Context)this.getActivity()).edit().putInt(FnF_NOTIFICATION_DISABLED_COUNT, 0).apply();
                    return layoutInflater;
                }
            }
        }
    }

    public void onPause() {
        super.onPause();
    }

    public void onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT >= 21) {
            ((MainActivity)this.getActivity()).getToolbar().setElevation(Convert.convertDpToPixel(0.0f, (Context)this.getActivity()));
        }
    }

    public void onStop() {
        super.onStop();
        if (Build.VERSION.SDK_INT >= 21) {
            ((MainActivity)this.getActivity()).getToolbar().setElevation(this.getActivity().getResources().getDimension(2131427686));
        }
    }
}

