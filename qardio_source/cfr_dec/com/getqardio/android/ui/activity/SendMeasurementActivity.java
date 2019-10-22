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
 *  android.view.MenuItem
 *  android.view.View
 */
package com.getqardio.android.ui.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import com.getqardio.android.ui.activity.BaseActivity;
import com.getqardio.android.ui.fragment.ProgressDialogFragment;
import com.getqardio.android.ui.fragment.SendMeasurementFragment;
import com.getqardio.android.ui.fragment.SendMeasurementResultFragment;
import com.getqardio.android.utils.analytics.AnalyticsScreenTracker;
import com.getqardio.android.utils.ui.ActivityUtils;
import com.getqardio.android.utils.ui.KeyboardHelper;

public class SendMeasurementActivity
extends BaseActivity
implements SendMeasurementFragment.Callback {
    private boolean isMeasurementSent = false;
    private ProgressDialogFragment progressDialogFragment;

    private void changeFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
        fragmentTransaction.replace(2131821295, fragment);
        fragmentTransaction.commit();
    }

    public static Intent createStartIntent(Context context, int n) {
        context = new Intent(context, SendMeasurementActivity.class);
        context.putExtra("com.getqardio.android.extra.VISITOR_MEASUREMENT_ID", n);
        return context;
    }

    @Override
    public void onBackPressed() {
        KeyboardHelper.hideKeyboard((Context)this, this.getCurrentFocus());
        if (this.isMeasurementSent) {
            this.setResult(-1);
        }
        this.finish();
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2130968819);
        ActivityUtils.getActionBar(this).setDisplayHomeAsUpEnabled(true);
        AnalyticsScreenTracker.sendScreen((Context)this, "Email measurement");
        this.progressDialogFragment = ProgressDialogFragment.newInstance(false);
        if (bundle == null) {
            this.changeFragment(SendMeasurementFragment.newInstance(this.getIntent().getIntExtra("com.getqardio.android.extra.VISITOR_MEASUREMENT_ID", 0)));
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            default: {
                do {
                    return super.onOptionsItemSelected(menuItem);
                    break;
                } while (true);
            }
            case 16908332: 
        }
        this.onBackPressed();
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onSendingFinished() {
        this.progressDialogFragment.dismissAllowingStateLoss();
        this.isMeasurementSent = true;
        this.changeFragment(SendMeasurementResultFragment.newInstance());
    }

    @Override
    public void onSendingStarted() {
        this.progressDialogFragment.show(this.getFragmentManager(), null);
    }
}

