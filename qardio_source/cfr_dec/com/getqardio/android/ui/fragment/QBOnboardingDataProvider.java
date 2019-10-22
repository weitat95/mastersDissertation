/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.fragment;

import com.getqardio.android.baseble.QardioBaseDevice;
import com.getqardio.android.datamodel.Profile;
import com.getqardio.android.ui.fragment.OnBoardingProfileProvider;
import com.getqardio.android.ui.fragment.OnBoardingWifiProvider;

public interface QBOnboardingDataProvider
extends OnBoardingProfileProvider,
OnBoardingWifiProvider {
    public QardioBaseDevice.BaseMode getMode();

    public boolean isWifiConfigurationSkippped();

    public void setMode(QardioBaseDevice.BaseMode var1);

    public void setProfile(Profile var1);

    public void skipWifiConfiguration();
}

