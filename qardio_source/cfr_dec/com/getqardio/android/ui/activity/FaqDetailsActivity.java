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
import com.getqardio.android.ui.fragment.FaqDetailsFragment;
import com.getqardio.android.utils.ui.ActivityUtils;

public class FaqDetailsActivity
extends BaseActivity {
    public static Intent getStartIntent(Context context, long l) {
        context = new Intent(context, FaqDetailsActivity.class);
        context.putExtra("com.getqardio.android.extras.FAQ_ID", l);
        return context;
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2130968703);
        ActivityUtils.getActionBar(this).setDisplayHomeAsUpEnabled(true);
        long l = -1L;
        Intent intent = this.getIntent();
        if (intent != null) {
            l = intent.getLongExtra("com.getqardio.android.extras.FAQ_ID", -1L);
        }
        if (bundle == null) {
            bundle = this.getFragmentManager().beginTransaction();
            bundle.add(2131820778, (Fragment)FaqDetailsFragment.newInstance(l));
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
        this.finish();
        return true;
    }
}

