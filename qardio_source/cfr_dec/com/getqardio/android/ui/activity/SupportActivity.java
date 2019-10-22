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
import com.getqardio.android.ui.fragment.ProgressDialogFragment;
import com.getqardio.android.ui.fragment.SupportHostFragment;
import com.getqardio.android.utils.ui.ActivityUtils;

public class SupportActivity
extends BaseActivity
implements SupportHostFragment.Callback {
    private ProgressDialogFragment progressDialogFragment;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, SupportActivity.class);
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2130968836);
        ActivityUtils.getActionBar(this).setDisplayHomeAsUpEnabled(true);
        this.progressDialogFragment = ProgressDialogFragment.newInstance(false);
        if (bundle == null) {
            bundle = this.getFragmentManager().beginTransaction();
            bundle.replace(2131820778, (Fragment)SupportHostFragment.newInstance());
            bundle.commit();
        }
    }

    @Override
    public void onHideProgress() {
        this.progressDialogFragment.dismiss();
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

    @Override
    public void onShowProgress() {
        this.progressDialogFragment.show(this.getFragmentManager(), null);
    }
}

