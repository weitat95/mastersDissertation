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
import com.getqardio.android.ui.activity.BaseActivity;
import com.getqardio.android.ui.fragment.OnBoardingFragment;
import com.getqardio.android.utils.wizard.OnboardingPrefsManager;

public class OnBoardingActivity
extends BaseActivity {
    public static Intent createLowBatteryStartIntent(Context context) {
        context = new Intent(context, OnBoardingActivity.class);
        context.putExtra("com.getqardio.android.extra.LOW_BATTERY", true);
        return context;
    }

    public static Intent createStartIntent(Context context, boolean bl) {
        context = new Intent(context, OnBoardingActivity.class);
        context.putExtra("com.getqardio.android.extra.IS_OUTRO", bl);
        return context;
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2130968772);
        if (bundle == null) {
            bundle = this.getIntent().getExtras().containsKey("com.getqardio.android.extra.LOW_BATTERY") ? OnBoardingFragment.newLowBatteryInstance() : OnBoardingFragment.newInstance(this.getIntent().getBooleanExtra("com.getqardio.android.extra.IS_OUTRO", false));
            this.getFragmentManager().beginTransaction().add(2131820778, (Fragment)bundle).commit();
        }
        OnboardingPrefsManager.updateOnboardingDiscovered();
    }
}

