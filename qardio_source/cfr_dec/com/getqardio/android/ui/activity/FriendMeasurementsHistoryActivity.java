/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Application
 *  android.app.Fragment
 *  android.app.FragmentManager
 *  android.app.FragmentTransaction
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 *  android.view.MenuItem
 */
package com.getqardio.android.ui.activity;

import android.app.Application;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import com.getqardio.android.mvp.MvpApplication;
import com.getqardio.android.provider.DataHelper;
import com.getqardio.android.ui.activity.BaseActivity;
import com.getqardio.android.ui.fragment.BPMeasurementsHistoryFragment;
import com.getqardio.android.ui.fragment.WeightMeasurementHistoryFragment;
import com.getqardio.android.utils.ui.ActivityUtils;

public class FriendMeasurementsHistoryActivity
extends BaseActivity {
    private boolean isContentViewSet = false;

    /*
     * Enabled aggressive block sorting
     */
    private void displayMeasurementsHistoryDetailsFragment() {
        long l = this.getIntent().getLongExtra("com.getqardio.android.extras.USER_ID", -1L);
        Object object = this.getIntent().getStringExtra("com.getqardio.android.extras.USER_EMAIL");
        if (this.getIntent().getBooleanExtra("com.getqardio.android.extras.BP", false)) {
            object = BPMeasurementsHistoryFragment.newInstance(l, (String)object, true);
        } else {
            MvpApplication mvpApplication = (MvpApplication)this.getApplication();
            object = WeightMeasurementHistoryFragment.newInstance(l, (String)object, DataHelper.ProfileHelper.getWeightUnit((Context)this, mvpApplication.getCurrentUserId()), DataHelper.ProfileHelper.getHeightUnit((Context)this, mvpApplication.getCurrentUserId()), 2131821427, true);
        }
        this.getFragmentManager().beginTransaction().replace(2131821020, (Fragment)object).commit();
    }

    public static Intent getBpStartIntent(Context context, long l, String string2) {
        context = new Intent(context, FriendMeasurementsHistoryActivity.class);
        context.putExtra("com.getqardio.android.extras.USER_ID", l);
        context.putExtra("com.getqardio.android.extras.USER_EMAIL", string2);
        context.putExtra("com.getqardio.android.extras.BP", true);
        return context;
    }

    public static Intent getWeightStartIntent(Context context, long l, String string2) {
        context = new Intent(context, FriendMeasurementsHistoryActivity.class);
        context.putExtra("com.getqardio.android.extras.USER_ID", l);
        context.putExtra("com.getqardio.android.extras.USER_EMAIL", string2);
        return context;
    }

    @Override
    public void onBackPressed() {
        this.finish();
        this.overridePendingTransition(2131034130, 2131034133);
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2130968712);
        ActivityUtils.getActionBar(this).setDisplayHomeAsUpEnabled(true);
        if (!this.isContentViewSet) {
            this.isContentViewSet = true;
            this.displayMeasurementsHistoryDetailsFragment();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (this.isContentViewSet) {
            this.displayMeasurementsHistoryDetailsFragment();
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

