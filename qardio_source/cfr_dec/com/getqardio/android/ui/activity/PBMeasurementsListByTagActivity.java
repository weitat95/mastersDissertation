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
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import com.getqardio.android.ui.activity.BaseActivity;
import com.getqardio.android.ui.fragment.BPMeasurementsListByTagFragment;
import com.getqardio.android.utils.ui.ActivityUtils;

public class PBMeasurementsListByTagActivity
extends BaseActivity {
    private ActionBar actionBar;
    private int measurementsTag;

    public static Intent getStartIntent(Context context, long l, int n) {
        context = new Intent(context, PBMeasurementsListByTagActivity.class);
        context.putExtra("com.getqardio.android.extras.USER_ID", l);
        context.putExtra("com.getqardio.android.extras.MEASUREMENTS_TAG", n);
        return context;
    }

    private void setTitleByTag(int n) {
        switch (n) {
            default: {
                return;
            }
            case 1: {
                this.actionBar.setIcon(2130837851);
                this.setTitle(2131361952);
                return;
            }
            case 2: {
                this.actionBar.setIcon(2130837853);
                this.setTitle(2131362091);
                return;
            }
            case 3: {
                this.actionBar.setIcon(2130837857);
                this.setTitle(2131362082);
                return;
            }
            case 4: {
                this.actionBar.setIcon(2130837849);
                this.setTitle(2131361940);
                return;
            }
            case 5: {
                this.actionBar.setIcon(2130837847);
                this.setTitle(2131361902);
                return;
            }
            case 6: {
                this.actionBar.setIcon(2130837855);
                this.setTitle(2131362011);
                return;
            }
            case 0: 
        }
        this.actionBar.setIcon(2130837798);
        this.setTitle(2131362011);
    }

    @Override
    public void onBackPressed() {
        this.finish();
        this.overridePendingTransition(2131034130, 2131034133);
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2130968645);
        this.actionBar = ActivityUtils.getActionBar(this);
        this.actionBar.setDisplayHomeAsUpEnabled(true);
        if (bundle == null) {
            bundle = this.getIntent();
            if (bundle != null) {
                long l = bundle.getLongExtra("com.getqardio.android.extras.USER_ID", -1L);
                this.measurementsTag = bundle.getIntExtra("com.getqardio.android.extras.MEASUREMENTS_TAG", 0);
                this.setTitleByTag(this.measurementsTag);
                bundle = this.getFragmentManager().beginTransaction();
                bundle.add(2131820778, (Fragment)BPMeasurementsListByTagFragment.getInstance(l, this.measurementsTag));
            }
            return;
            finally {
                bundle.commit();
            }
        }
        this.measurementsTag = bundle.getInt("com.getqardio.android.extras.EXTRA_SAVED_MEASUREMENTS_TAG");
        this.setTitleByTag(this.measurementsTag);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            long l = intent.getLongExtra("com.getqardio.android.extras.USER_ID", -1L);
            this.measurementsTag = intent.getIntExtra("com.getqardio.android.extras.MEASUREMENTS_TAG", 0);
            this.setTitleByTag(this.measurementsTag);
            intent = this.getFragmentManager().beginTransaction();
            intent.replace(2131820778, (Fragment)BPMeasurementsListByTagFragment.getInstance(l, this.measurementsTag));
        }
        return;
        finally {
            intent.commit();
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

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt("com.getqardio.android.extras.EXTRA_SAVED_MEASUREMENTS_TAG", this.measurementsTag);
    }
}

