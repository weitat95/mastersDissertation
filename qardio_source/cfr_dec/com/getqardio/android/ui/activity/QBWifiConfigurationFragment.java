/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.app.FragmentManager
 *  android.app.FragmentTransaction
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 */
package com.getqardio.android.ui.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.getqardio.android.ui.activity.QBStepOnActivity;
import com.getqardio.android.ui.fragment.OnBoardingWifiProvider;
import com.getqardio.android.ui.fragment.QBSelectWifiStateOnboardingFragment;
import com.getqardio.android.utils.analytics.QardioBaseDeviceAnalyticsTracker;
import com.getqardio.android.utils.wifi.WifiAp;
import com.getqardio.android.utils.wifi.WifiSecurityState;
import com.getqardio.android.utils.wizard.OnDoneClickListener;

public class QBWifiConfigurationFragment
extends Fragment
implements OnBoardingWifiProvider,
OnDoneClickListener {
    private boolean isWifiConfigurationFromSettings;
    private String password;
    private WifiAp wifiAp;

    public static QBWifiConfigurationFragment newInstance(boolean bl) {
        QBWifiConfigurationFragment qBWifiConfigurationFragment = new QBWifiConfigurationFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("com.getqardio.android.extra.WIFI_CONFIGURATION_FROM_SETTINS", bl);
        qBWifiConfigurationFragment.setArguments(bundle);
        return qBWifiConfigurationFragment;
    }

    @Override
    public WifiAp getWifiAp() {
        return this.wifiAp;
    }

    @Override
    public String getWifiPassword() {
        return this.password;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.isWifiConfigurationFromSettings = false;
        if (this.getArguments() != null && this.getArguments().containsKey("com.getqardio.android.extra.WIFI_CONFIGURATION_FROM_SETTINS")) {
            this.isWifiConfigurationFromSettings = this.getArguments().getBoolean("com.getqardio.android.extra.WIFI_CONFIGURATION_FROM_SETTINS");
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.setHasOptionsMenu(true);
        return layoutInflater.inflate(2130968860, viewGroup, false);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onDoneClick() {
        Activity activity = this.getActivity();
        if (this.isWifiConfigurationFromSettings) {
            if (this.getWifiAp() == null) return;
            {
                boolean bl = this.getWifiAp().sec == WifiSecurityState.SECURE;
                this.startActivity(QBStepOnActivity.createStartIntentForWifi((Context)this.getActivity(), this.getWifiAp().ssid, this.getWifiPassword(), bl));
                this.getActivity().finish();
                return;
            }
        } else {
            if (activity == null || !(activity instanceof WifiConfigDoneListener)) return;
            {
                ((WifiConfigDoneListener)activity).done();
                return;
            }
        }
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        QardioBaseDeviceAnalyticsTracker.trackEnterWifiPasswordScreen((Context)this.getActivity());
        view = new Bundle();
        view.putBoolean("EXTRA_FROM_SETTINGS", true);
        this.getChildFragmentManager().beginTransaction().replace(2131820778, (Fragment)QBSelectWifiStateOnboardingFragment.newInstance((Bundle)view)).commit();
    }

    @Override
    public void setWifiAp(WifiAp wifiAp, String string2) {
        this.wifiAp = wifiAp;
        this.password = string2;
    }

    public static interface WifiConfigDoneListener {
        public void done();
    }

}

