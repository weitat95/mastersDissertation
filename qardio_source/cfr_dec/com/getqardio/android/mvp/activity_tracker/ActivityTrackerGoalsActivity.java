/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 */
package com.getqardio.android.mvp.activity_tracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ActivityTrackerGoalsActivity
extends AppCompatActivity {
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2130968621);
        this.mSectionsPagerAdapter = new SectionsPagerAdapter(this.getSupportFragmentManager());
        this.mViewPager = (ViewPager)((Object)this.findViewById(2131820820));
        this.mViewPager.setAdapter(this.mSectionsPagerAdapter);
    }

    public static class PlaceholderFragment
    extends Fragment {
        public static PlaceholderFragment newInstance(int n) {
            PlaceholderFragment placeholderFragment = new PlaceholderFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("section_number", n);
            placeholderFragment.setArguments(bundle);
            return placeholderFragment;
        }

        @Override
        public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
            return layoutInflater.inflate(2130968618, viewGroup, false);
        }
    }

    public class SectionsPagerAdapter
    extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Fragment getItem(int n) {
            return PlaceholderFragment.newInstance(n + 1);
        }

        @Override
        public CharSequence getPageTitle(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 0: {
                    return "SECTION 1";
                }
                case 1: {
                    return "SECTION 2";
                }
                case 2: 
            }
            return "SECTION 3";
        }
    }

}

