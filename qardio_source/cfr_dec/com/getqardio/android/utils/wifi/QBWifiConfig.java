/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.utils.wifi;

public class QBWifiConfig {
    private String ssid;
    private String wifi;

    public static QBWifiConfig empty() {
        QBWifiConfig qBWifiConfig = new QBWifiConfig();
        qBWifiConfig.setSsid("");
        qBWifiConfig.setWifi("");
        return qBWifiConfig;
    }

    public String getSsid() {
        return this.ssid;
    }

    public void setSsid(String string2) {
        this.ssid = string2;
    }

    public void setWifi(String string2) {
        this.wifi = string2;
    }
}

