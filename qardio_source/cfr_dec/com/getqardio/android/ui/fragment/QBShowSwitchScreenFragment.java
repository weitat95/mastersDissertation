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
import com.getqardio.android.ui.fragment.QBShowSwitchScreenFragment$$Lambda$1;
import com.getqardio.android.utils.analytics.QardioBaseDeviceAnalyticsTracker;
import com.getqardio.android.utils.wizard.OnDoneClickListener;

public class QBShowSwitchScreenFragment
extends AbstractQBOnboardingFragment {
    private OnDoneClickListener listener;

    public static QBShowSwitchScreenFragment newInstance(Bundle bundle) {
        QBShowSwitchScreenFragment qBShowSwitchScreenFragment = new QBShowSwitchScreenFragment();
        qBShowSwitchScreenFragment.setArguments(bundle);
        return qBShowSwitchScreenFragment;
    }

    @Override
    protected int getLayoutBackground() {
        return 2130837937;
    }

    @Override
    protected int getLayoutResource() {
        return 2130968710;
    }

    /* synthetic */ void lambda$onViewCreated$0(View view) {
        this.listener.onDoneClick();
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        QardioBaseDeviceAnalyticsTracker.trackQB2SwitchOnScreen((Context)this.getActivity());
        this.listener = (OnDoneClickListener)this.getParentFragment();
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.listener = null;
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        ((Button)this.rootView.findViewById(2131821057)).setOnClickListener(QBShowSwitchScreenFragment$$Lambda$1.lambdaFactory$(this));
    }

    @Override
    protected int predictNextImage() {
        return 2130837943;
    }
}

