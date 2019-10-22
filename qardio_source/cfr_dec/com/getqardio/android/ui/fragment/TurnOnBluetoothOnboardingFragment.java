/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.Button
 */
package com.getqardio.android.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.getqardio.android.ui.fragment.AbstractQBOnboardingFragment;
import com.getqardio.android.ui.fragment.TurnOnBluetoothOnboardingFragment$$Lambda$1;
import com.getqardio.android.utils.analytics.QardioBaseDeviceAnalyticsTracker;

public class TurnOnBluetoothOnboardingFragment
extends AbstractQBOnboardingFragment {
    public static TurnOnBluetoothOnboardingFragment newInstance() {
        return new TurnOnBluetoothOnboardingFragment();
    }

    @Override
    protected int getLayoutBackground() {
        return 2130837939;
    }

    @Override
    protected int getLayoutResource() {
        return 2130968845;
    }

    @Override
    protected boolean isTransitionEnabled() {
        return false;
    }

    /* synthetic */ void lambda$onViewCreated$0(View view) {
        this.startActivity(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"));
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        QardioBaseDeviceAnalyticsTracker.trackTurnOnBluetoothScreen((Context)this.getActivity());
        ((Button)this.rootView.findViewById(2131821397)).setOnClickListener(TurnOnBluetoothOnboardingFragment$$Lambda$1.lambdaFactory$(this));
    }
}

