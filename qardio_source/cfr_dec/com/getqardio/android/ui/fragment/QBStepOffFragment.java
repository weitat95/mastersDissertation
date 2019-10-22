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
 *  android.widget.Button
 */
package com.getqardio.android.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.getqardio.android.ui.fragment.AbstractQBOnboardingFragment;
import com.getqardio.android.ui.fragment.QBStepOffFragment$$Lambda$1;
import com.getqardio.android.utils.analytics.QardioBaseDeviceAnalyticsTracker;
import com.getqardio.android.utils.wizard.OnDoneClickListener;

public class QBStepOffFragment
extends AbstractQBOnboardingFragment {
    private OnDoneClickListener listener;

    public static AbstractQBOnboardingFragment newInstance(Bundle bundle) {
        QBStepOffFragment qBStepOffFragment = new QBStepOffFragment();
        qBStepOffFragment.setArguments(bundle);
        return qBStepOffFragment;
    }

    @Override
    protected int getLayoutBackground() {
        return 2130837940;
    }

    @Override
    protected int getLayoutResource() {
        return 2130968711;
    }

    /* synthetic */ void lambda$onViewCreated$0(View view) {
        this.listener.onDoneClick();
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        QardioBaseDeviceAnalyticsTracker.trackStepOffOptionalScreen((Context)this.getActivity());
        this.listener = (OnDoneClickListener)this.getParentFragment();
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        QardioBaseDeviceAnalyticsTracker.trackStepOffRequiredScreen((Context)this.getActivity());
        ((Button)this.rootView.findViewById(2131821057)).setOnClickListener(QBStepOffFragment$$Lambda$1.lambdaFactory$(this));
    }

    @Override
    protected int predictNextImage() {
        return 2130837939;
    }
}

