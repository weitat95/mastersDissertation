/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Fragment
 *  android.app.FragmentManager
 *  android.app.FragmentTransaction
 *  android.app.NotificationManager
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 */
package com.getqardio.android.ui.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.getqardio.android.ui.activity.BaseActivity;
import com.getqardio.android.ui.fragment.BPMeasurementsResultFragment;
import com.getqardio.android.ui.fragment.EngagementScreenFragment;

public class BPMeasureActivity
extends BaseActivity {
    private BPMeasurementsResultFragment bpMeasurementsResultFragment;

    private void cancelNotifications() {
        ((NotificationManager)this.getSystemService("notification")).cancel(1);
    }

    public static Intent getStartIntent(Context context, boolean bl, boolean bl2) {
        context = new Intent(context, BPMeasureActivity.class);
        context.putExtra("com.getqardio.android.extras.FROM_NOTIFICATION", bl2);
        context.putExtra("com.getqardio.android.extras.VISITOR_MODE", bl);
        return context;
    }

    @Override
    public void onBackPressed() {
        if (this.bpMeasurementsResultFragment != null) {
            this.bpMeasurementsResultFragment.onBackPressed();
            EngagementScreenFragment engagementScreenFragment = this.bpMeasurementsResultFragment.getEngagementScreenFragment();
            if (engagementScreenFragment != null) {
                engagementScreenFragment.stopHandler(true);
            }
        }
        this.finish();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2130968641);
        this.cancelNotifications();
        boolean bl = false;
        boolean bl2 = false;
        Intent intent = this.getIntent();
        if (intent != null) {
            bl = intent.getBooleanExtra("com.getqardio.android.extras.VISITOR_MODE", false);
            bl2 = intent.getBooleanExtra("com.getqardio.android.extras.FROM_NOTIFICATION", false);
        }
        this.bpMeasurementsResultFragment = bundle == null ? BPMeasurementsResultFragment.getInstance(bl, bl2) : (BPMeasurementsResultFragment)this.getFragmentManager().getFragment(bundle, "com.getqardio.android.extras.MEASUREMENT_FRAGMENT");
        bundle = this.getFragmentManager().beginTransaction();
        try {
            bundle.replace(2131820778, (Fragment)this.bpMeasurementsResultFragment);
            return;
        }
        finally {
            bundle.commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (this.bpMeasurementsResultFragment != null) {
            this.getFragmentManager().putFragment(bundle, "com.getqardio.android.extras.MEASUREMENT_FRAGMENT", (Fragment)this.bpMeasurementsResultFragment);
        }
    }

    @Override
    public void onStop() {
        EngagementScreenFragment engagementScreenFragment;
        super.onStop();
        if (this.bpMeasurementsResultFragment != null && (engagementScreenFragment = this.bpMeasurementsResultFragment.getEngagementScreenFragment()) != null && this.isFinishing()) {
            engagementScreenFragment.stopHandler(false);
        }
    }
}

