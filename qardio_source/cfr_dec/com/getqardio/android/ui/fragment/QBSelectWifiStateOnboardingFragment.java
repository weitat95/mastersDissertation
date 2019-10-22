/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.app.FragmentManager
 *  android.app.FragmentTransaction
 *  android.content.Context
 *  android.os.Bundle
 *  android.view.View
 */
package com.getqardio.android.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import com.getqardio.android.ui.fragment.AbstractQBOnboardingFragment;
import com.getqardio.android.ui.fragment.OnBoardingWifiProvider;
import com.getqardio.android.ui.fragment.QBOnboardingDataProvider;
import com.getqardio.android.ui.fragment.QBSelectWiFiOnboardingFragment;
import com.getqardio.android.utils.analytics.QardioBaseDeviceAnalyticsTracker;
import com.getqardio.android.utils.wifi.WifiAp;
import com.getqardio.android.utils.wizard.OnDoneClickListener;

public class QBSelectWifiStateOnboardingFragment
extends AbstractQBOnboardingFragment
implements QBSelectWiFiOnboardingFragment.Callback {
    private OnBoardingWifiProvider dataProvider;
    private QBSelectWiFiOnboardingFragment selectWiFiOnboardingFragment;

    public static QBSelectWifiStateOnboardingFragment newInstance(Bundle bundle) {
        QBSelectWifiStateOnboardingFragment qBSelectWifiStateOnboardingFragment = new QBSelectWifiStateOnboardingFragment();
        qBSelectWifiStateOnboardingFragment.setArguments(bundle);
        return qBSelectWifiStateOnboardingFragment;
    }

    private void showSelectPointFragment() {
        if (this.selectWiFiOnboardingFragment == null) {
            Bundle bundle = new Bundle();
            if (this.getArguments() != null && this.getArguments().containsKey("EXTRA_FROM_SETTINGS")) {
                bundle.putBoolean("EXTRA_FROM_SETTINGS", true);
            }
            if (this.getArguments() != null && this.getArguments().containsKey("EXTRA_WIFI_CHECK_NEEDED")) {
                bundle.putBoolean("EXTRA_WIFI_CHECK_NEEDED", this.getArguments().getBoolean("EXTRA_WIFI_CHECK_NEEDED"));
            }
            this.selectWiFiOnboardingFragment = QBSelectWiFiOnboardingFragment.newInstance(bundle);
        }
        this.getChildFragmentManager().beginTransaction().replace(2131820778, (Fragment)this.selectWiFiOnboardingFragment).commit();
    }

    @Override
    protected int getLayoutBackground() {
        return 2130837939;
    }

    @Override
    protected int getLayoutResource() {
        return 2130968816;
    }

    @Override
    public void onConfigureWifiAp(WifiAp wifiAp, String string2) {
        this.dataProvider.setWifiAp(wifiAp, string2);
    }

    @Override
    public void onDonePressed() {
        QardioBaseDeviceAnalyticsTracker.trackSetupQbWifi((Context)this.getActivity());
        this.onDoneClickListener.onDoneClick();
    }

    @Override
    public void onSkippedWifiConfiguration() {
        if (this.dataProvider instanceof QBOnboardingDataProvider) {
            ((QBOnboardingDataProvider)this.dataProvider).skipWifiConfiguration();
        }
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        QardioBaseDeviceAnalyticsTracker.trackChooseWifiNetworkScreen((Context)this.getActivity());
        this.dataProvider = (OnBoardingWifiProvider)this.getParentFragment();
        if (bundle == null) {
            this.showSelectPointFragment();
        }
    }
}

