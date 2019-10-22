/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Fragment
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 */
package com.getqardio.android.mvp.activity_tracker.goals.view;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;

public class ActivityTrackingEditGoalsFragment
extends Fragment {
    public static ActivityTrackingEditGoalsFragment newInstance() {
        return new ActivityTrackingEditGoalsFragment();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layoutInflater = (ViewGroup)layoutInflater.inflate(2130968615, viewGroup, false);
        ButterKnife.bind((Object)this, (View)layoutInflater);
        return layoutInflater;
    }
}

