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
import com.getqardio.android.ui.fragment.BPMeasurementsHistoryDetailsFragment;
import com.getqardio.android.utils.ui.ActivityUtils;

public class BPMeasurementsHistoryDetailsActivity
extends BaseActivity {
    public static Intent getStartIntent(Context context, long l, long l2, boolean bl) {
        context = new Intent(context, BPMeasurementsHistoryDetailsActivity.class);
        context.putExtra("com.getqardio.android.extras.USER_ID", l);
        context.putExtra("com.getqardio.android.extras.MEASUREMENT_DATE", l2);
        context.putExtra("com.getqardio.android.extras.EXTRA_IS_EDITABLE", bl);
        return context;
    }

    @Override
    public void onBackPressed() {
        this.finish();
        this.overridePendingTransition(2131034130, 2131034133);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2130968642);
        ActivityUtils.getActionBar(this).setDisplayHomeAsUpEnabled(true);
        if (bundle != null) return;
        bundle = this.getFragmentManager().beginTransaction();
        Intent intent = this.getIntent();
        if (intent == null) return;
        bundle.add(2131820778, (Fragment)BPMeasurementsHistoryDetailsFragment.newInstance(intent.getLongExtra("com.getqardio.android.extras.USER_ID", -1L), intent.getLongExtra("com.getqardio.android.extras.MEASUREMENT_DATE", -1L), intent.getBooleanExtra("com.getqardio.android.extras.EXTRA_IS_EDITABLE", false)));
        return;
        finally {
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

