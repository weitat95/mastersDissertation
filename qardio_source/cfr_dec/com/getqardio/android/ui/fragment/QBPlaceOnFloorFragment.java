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
 *  android.widget.TextView
 */
package com.getqardio.android.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.getqardio.android.ui.fragment.AbstractQBOnboardingFragment;
import com.getqardio.android.ui.fragment.QBPlaceOnFloorFragment$$Lambda$1;
import com.getqardio.android.utils.analytics.QardioBaseDeviceAnalyticsTracker;
import com.getqardio.android.utils.wizard.OnDoneClickListener;

public class QBPlaceOnFloorFragment
extends AbstractQBOnboardingFragment {
    private OnDoneClickListener listener;

    public static QBPlaceOnFloorFragment newInstance(Bundle bundle) {
        QBPlaceOnFloorFragment qBPlaceOnFloorFragment = new QBPlaceOnFloorFragment();
        qBPlaceOnFloorFragment.setArguments(bundle);
        return qBPlaceOnFloorFragment;
    }

    @Override
    protected int getLayoutBackground() {
        return 2130837943;
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
        QardioBaseDeviceAnalyticsTracker.trackQB2SetOnFloor((Context)this.getActivity());
        this.listener = (OnDoneClickListener)this.getParentFragment();
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        bundle = (Button)this.rootView.findViewById(2131821057);
        bundle.setOnClickListener(QBPlaceOnFloorFragment$$Lambda$1.lambdaFactory$(this));
        bundle.setText((CharSequence)this.getString(2131362265));
        ((TextView)view.findViewById(2131821056)).setText((CharSequence)this.getString(2131362309));
    }

    @Override
    protected int predictNextImage() {
        return 2130837945;
    }
}

