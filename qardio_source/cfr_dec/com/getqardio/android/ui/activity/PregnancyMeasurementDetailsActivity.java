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
import com.getqardio.android.ui.fragment.PregnancyMeasurementFragment;
import com.getqardio.android.utils.ui.ActivityUtils;
import java.io.Serializable;
import java.util.Date;

public class PregnancyMeasurementDetailsActivity
extends BaseActivity {
    public static Intent getStartIntent(Context context, long l, int n, Long l2, Date date) {
        context = new Intent(context, PregnancyMeasurementDetailsActivity.class);
        context.putExtra("ARG_USER_ID", l);
        context.putExtra("ARG_WEIGHT_UNIT", n);
        context.putExtra("ARG_MEASUREMENT_PREGNANCY_ID", (Serializable)l2);
        if (date != null) {
            context.putExtra("ARG_MEASUREMENT_DATE", date.getTime());
        }
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
    protected void onCreate(Bundle object) {
        super.onCreate((Bundle)object);
        this.setContentView(2130968608);
        ActivityUtils.getActionBar(this).setDisplayHomeAsUpEnabled(true);
        if (object != null) return;
        FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
        Intent intent = this.getIntent();
        if (intent == null) return;
        object = null;
        if (intent.hasExtra("ARG_MEASUREMENT_DATE")) {
            object = new Date(intent.getLongExtra("ARG_MEASUREMENT_DATE", 0L));
        }
        fragmentTransaction.add(2131820778, (Fragment)PregnancyMeasurementFragment.newInstance(intent.getLongExtra("ARG_USER_ID", -1L), intent.getIntExtra("ARG_WEIGHT_UNIT", 0), intent.getLongExtra("ARG_MEASUREMENT_PREGNANCY_ID", -1L), (Date)object));
        return;
        finally {
            fragmentTransaction.commit();
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

