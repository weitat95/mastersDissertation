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
 */
package com.getqardio.android.ui.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import com.getqardio.android.ui.activity.BaseActivity;
import com.getqardio.android.ui.activity.QBWifiConfigurationFragment;
import com.getqardio.android.utils.ui.ActivityUtils;

public class QBWifiConfigurationActivity
extends BaseActivity
implements QBWifiConfigurationFragment.WifiConfigDoneListener {
    public static Intent createStartIntentFromSettings(Context context) {
        context = new Intent(context, QBWifiConfigurationActivity.class);
        context.putExtra("com.getqardio.android.extra.WIFI_CONFIGURATION_FROM_SETTINS", true);
        return context;
    }

    @Override
    public void done() {
        this.setResult(-1, new Intent());
        this.finish();
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2130968859);
        ActivityUtils.changeStatusBarColor(this, -16777216);
        this.getToolbar().setVisibility(8);
        if (bundle == null) {
            this.getFragmentManager().beginTransaction().replace(2131820778, (Fragment)QBWifiConfigurationFragment.newInstance(this.getIntent().getExtras().getBoolean("com.getqardio.android.extra.WIFI_CONFIGURATION_FROM_SETTINS"))).commit();
        }
    }
}

