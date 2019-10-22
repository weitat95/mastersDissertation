/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.fragment;

import com.getqardio.android.utils.wifi.WifiAp;

public interface OnBoardingWifiProvider {
    public WifiAp getWifiAp();

    public String getWifiPassword();

    public void setWifiAp(WifiAp var1, String var2);
}

