/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Fragment
 *  android.app.FragmentManager
 *  android.app.FragmentTransaction
 *  android.os.Bundle
 *  android.view.MenuItem
 */
package com.getqardio.android.ui.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;
import com.getqardio.android.ui.activity.BaseActivity;
import com.getqardio.android.ui.fragment.FaqDetailsFragment;
import com.getqardio.android.ui.fragment.FaqListFragment;
import com.getqardio.android.utils.ui.ActivityUtils;

public class FaqListActivity
extends BaseActivity
implements FaqListFragment.Callback {
    @Override
    public void displayFAQDetailsFragment(long l) {
        if (this.getFragmentManager().findFragmentByTag("FAQ_DETAILS_FRAGMENT_TAG") == null) {
            this.getFragmentManager().beginTransaction().replace(2131820778, (Fragment)FaqDetailsFragment.newInstance(l), "FAQ_DETAILS_FRAGMENT_TAG").addToBackStack(null).commit();
        }
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2130968705);
        ActivityUtils.getActionBar(this).setDisplayHomeAsUpEnabled(true);
        if (bundle == null) {
            bundle = this.getFragmentManager().beginTransaction();
            bundle.add(2131820778, (Fragment)FaqListFragment.newInstance());
        }
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

