/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.IntentFilter
 *  android.os.Bundle
 *  android.view.View
 *  android.widget.ProgressBar
 */
package com.getqardio.android.ui.fragment;

import android.app.Activity;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.ProgressBar;
import com.getqardio.android.ui.fragment.AbstractQBOnboardingStateFragment;
import com.getqardio.android.utils.ui.ActivityUtils;

public class QBSettingsStepOnStateFragment
extends AbstractQBOnboardingStateFragment {
    public static QBSettingsStepOnStateFragment newInstance(int n, int n2, int n3, boolean bl) {
        QBSettingsStepOnStateFragment qBSettingsStepOnStateFragment = new QBSettingsStepOnStateFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("com.getqardio.android.argument.TITLE_RES_ID", n);
        bundle.putInt("com.getqardio.android.argument.MESSAGE_RES_ID", n2);
        bundle.putInt("com.getqardio.android.argument.IMAGE_RES_ID", n3);
        bundle.putBoolean("com.getqardio.android.argument.SHOW_PROGRESS", bl);
        qBSettingsStepOnStateFragment.setArguments(bundle);
        return qBSettingsStepOnStateFragment;
    }

    @Override
    protected int getLayoutBackground() {
        return 2130837945;
    }

    public void onActivityCreated(Bundle object) {
        super.onActivityCreated((Bundle)object);
        object = ActivityUtils.getActionBar(this.getActivity());
        if (object != null) {
            ((ActionBar)object).setTitle(2131362337);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.qardio.base.STATE");
        intentFilter.addAction("com.qardio.base.CONNECTED");
        intentFilter.addAction("com.qardio.base.DISCONNECTED");
        intentFilter.addAction("com.qardio.base.CONFIGURATION_MODE");
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        ((ProgressBar)this.rootView.findViewById(2131821254)).setVisibility(0);
    }
}

