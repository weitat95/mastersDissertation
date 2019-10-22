/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Fragment
 *  android.app.FragmentManager
 *  android.app.FragmentTransaction
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 *  android.view.View
 */
package com.getqardio.android.mvp.activity_tracker.goals.view;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import butterknife.ButterKnife;
import com.getqardio.android.mvp.activity_tracker.goals.view.ActivityTrackerSetGoalForActivityTypeFragment;
import com.getqardio.android.mvp.activity_tracker.goals.view.ActivityTrackingEditGoalsFragment;

public class ActivityTrackerAddGoalsActivity
extends FragmentActivity {
    private int typeToGoalToBeSet;

    private void goToFragment() {
        FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
        new ActivityTrackerSetGoalForActivityTypeFragment();
        fragmentTransaction.replace(2131820806, (Fragment)ActivityTrackerSetGoalForActivityTypeFragment.newInstance(this.typeToGoalToBeSet));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public static void start(Context context, int n) {
        Intent intent = new Intent(context, ActivityTrackerAddGoalsActivity.class);
        intent.putExtra("com.getqardio.android.extra.GOAL_TYPE", n);
        context.startActivity(intent);
    }

    public void jumpToPageCycle(View view) {
        this.typeToGoalToBeSet = 2;
        this.goToFragment();
    }

    public void jumpToPageMinutesOfActivity(View view) {
        this.typeToGoalToBeSet = 5;
        this.goToFragment();
    }

    public void jumpToPageRun(View view) {
        this.typeToGoalToBeSet = 1;
        this.goToFragment();
    }

    public void jumpToPageSteps(View view) {
        this.typeToGoalToBeSet = 3;
        this.goToFragment();
    }

    public void jumpToPageWalk(View view) {
        this.typeToGoalToBeSet = 0;
        this.goToFragment();
    }

    public void jumpToPageWeight(View view) {
        this.typeToGoalToBeSet = 4;
        this.goToFragment();
    }

    @Override
    public void onBackPressed() {
        if (this.getFragmentManager().getBackStackEntryCount() > 0) {
            this.getFragmentManager().popBackStack();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2130968616);
        ButterKnife.bind(this);
        this.typeToGoalToBeSet = this.getIntent().getIntExtra("com.getqardio.android.extra.GOAL_TYPE", 6);
        if (this.typeToGoalToBeSet == 6) {
            bundle = this.getFragmentManager().beginTransaction();
            new ActivityTrackingEditGoalsFragment();
            bundle.add(2131820806, (Fragment)ActivityTrackingEditGoalsFragment.newInstance());
            bundle.commit();
            return;
        }
        this.goToFragment();
    }
}

