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
 */
package com.getqardio.android.ui.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import com.getqardio.android.ui.activity.BaseActivity;
import com.getqardio.android.ui.fragment.AddBPManualMeasurementFragment;
import com.getqardio.android.ui.fragment.AddWeightManualMeasurementFragment;
import com.getqardio.android.utils.ui.ActivityUtils;

public class AddManualMeasurementActivity
extends BaseActivity {
    private Fragment createFragment(long l, int n) {
        switch (n) {
            default: {
                throw new IllegalArgumentException("Unknown measurement type " + n);
            }
            case 1: {
                return AddBPManualMeasurementFragment.newInstance(l);
            }
            case 2: 
        }
        return AddWeightManualMeasurementFragment.newInstance(l);
    }

    public static void start(Context context, long l, int n) {
        Intent intent = new Intent(context, AddManualMeasurementActivity.class);
        intent.putExtra("com.getqardio.android.extra.USER_ID", l);
        intent.putExtra("com.getqardio.android.extra.MEASUREMENT_TYPE", n);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2130968635);
        ActivityUtils.getActionBar(this).setDisplayHomeAsUpEnabled(true);
        if (bundle == null) {
            long l = this.getIntent().getLongExtra("com.getqardio.android.extra.USER_ID", -1L);
            int n = this.getIntent().getIntExtra("com.getqardio.android.extra.MEASUREMENT_TYPE", 1);
            bundle = this.getFragmentManager().beginTransaction();
            bundle.add(2131820778, this.createFragment(l, n));
            bundle.commit();
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            default: {
                return super.onOptionsItemSelected(menuItem);
            }
            case 16908332: 
        }
        this.onBackPressed();
        return true;
    }
}

