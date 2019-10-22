/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.os.Bundle
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.Button
 *  android.widget.TextView
 */
package com.getqardio.android.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.getqardio.android.ui.fragment.AbstractQBOnboardingFragment;
import com.getqardio.android.ui.fragment.QBOnboardingReadyFragment$$Lambda$1;
import com.getqardio.android.utils.analytics.QardioBaseDeviceAnalyticsTracker;
import com.getqardio.android.utils.wizard.OnDoneClickListener;

public class QBOnboardingReadyFragment
extends AbstractQBOnboardingFragment {
    private TextView messageView;

    public static QBOnboardingReadyFragment newInstance(boolean bl) {
        QBOnboardingReadyFragment qBOnboardingReadyFragment = new QBOnboardingReadyFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("com.getqardio.android.argument.SHOW_MESSAGE", bl);
        qBOnboardingReadyFragment.setArguments(bundle);
        return qBOnboardingReadyFragment;
    }

    @Override
    protected int getLayoutBackground() {
        return 2130837939;
    }

    @Override
    protected int getLayoutResource() {
        return 2130968795;
    }

    /* synthetic */ void lambda$onViewCreated$0(View view) {
        this.onDoneClickListener.onDoneClick();
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.hideCloseOnBoardingButton();
        bundle = this.getArguments();
        if (bundle != null && !bundle.getBoolean("com.getqardio.android.argument.SHOW_MESSAGE")) {
            this.messageView.setVisibility(4);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.messageView = (TextView)this.rootView.findViewById(2131821253);
        ((Button)this.rootView.findViewById(2131821251)).setOnClickListener(QBOnboardingReadyFragment$$Lambda$1.lambdaFactory$(this));
        QardioBaseDeviceAnalyticsTracker.trackQbSetupCompleted((Context)this.getActivity());
        this.hideCloseOnBoardingButton();
        view.findViewById(2131821252).setVisibility(0);
    }
}

