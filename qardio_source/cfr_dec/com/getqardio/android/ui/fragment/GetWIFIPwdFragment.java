/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.content.Context
 *  android.os.Bundle
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.getqardio.android.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import com.getqardio.android.ui.fragment.AbstractQBOnboardingFragment;
import com.getqardio.android.ui.fragment.GetWIFIPwdFragment$$Lambda$1;
import com.getqardio.android.utils.analytics.QardioBaseDeviceAnalyticsTracker;
import com.getqardio.android.utils.wizard.OnDoneClickListener;

public class GetWIFIPwdFragment
extends AbstractQBOnboardingFragment {
    private OnDoneClickListener listener;

    public static final GetWIFIPwdFragment newInstance(Bundle bundle) {
        GetWIFIPwdFragment getWIFIPwdFragment = new GetWIFIPwdFragment();
        getWIFIPwdFragment.setArguments(bundle);
        return getWIFIPwdFragment;
    }

    @Override
    protected int getLayoutBackground() {
        return 2130837939;
    }

    @Override
    protected int getLayoutResource() {
        return 2130968708;
    }

    /* synthetic */ void lambda$onViewCreated$0(View view) {
        this.listener.onDoneClick();
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.listener = (OnDoneClickListener)this.getParentFragment();
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        QardioBaseDeviceAnalyticsTracker.trackWifiReminderScreen((Context)this.getActivity());
        view.findViewById(2131821052).setOnClickListener(GetWIFIPwdFragment$$Lambda$1.lambdaFactory$(this));
    }

    @Override
    protected int predictNextImage() {
        return 2130837937;
    }
}

