/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.net.wifi.ScanResult
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.getqardio.android.utils.wifi;

import android.net.wifi.ScanResult;
import android.os.Parcel;
import android.os.Parcelable;
import com.getqardio.android.utils.wifi.WifiSecurityState;
import timber.log.Timber;

public class WifiAp
implements Parcelable {
    public static final Parcelable.Creator<WifiAp> CREATOR = new Parcelable.Creator<WifiAp>(){

        public WifiAp createFromParcel(Parcel parcel) {
            return new WifiAp(parcel);
        }

        public WifiAp[] newArray(int n) {
            return new WifiAp[n];
        }
    };
    public int rssi;
    public WifiSecurityState sec;
    public String ssid;

    public WifiAp(Parcel parcel) {
        this.ssid = parcel.readString();
        this.sec = WifiSecurityState.valueOf(parcel.readString());
        this.rssi = parcel.readInt();
    }

    public WifiAp(String string2, WifiSecurityState wifiSecurityState, int n) {
        this.ssid = string2;
        this.sec = wifiSecurityState;
        this.rssi = n;
    }

    public static WifiSecurityState getSecurityString(ScanResult scanResult) {
        Timber.d("getSecurityString - %s", new Object[]{scanResult});
        if (scanResult == null || !scanResult.capabilities.toUpperCase().contains("WPA") && !scanResult.capabilities.toUpperCase().contains("WEP")) {
            return WifiSecurityState.OPEN;
        }
        return WifiSecurityState.SECURE;
    }

    public static WifiSecurityState getSecurityString(String string2) {
        Timber.d("getSecurityString - %s", string2);
        if (string2 == null || !string2.toUpperCase().contains("WPA") && !string2.toUpperCase().contains("WEP")) {
            return WifiSecurityState.OPEN;
        }
        return WifiSecurityState.SECURE;
    }

    public int describeContents() {
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block6: {
            block5: {
                if (this == object) break block5;
                if (object == null || this.getClass() != object.getClass()) {
                    return false;
                }
                object = (WifiAp)object;
                if (this.ssid != null) {
                    return this.ssid.equals(((WifiAp)object).ssid);
                }
                if (((WifiAp)object).ssid != null) break block6;
            }
            return true;
        }
        return false;
    }

    public int hashCode() {
        if (this.ssid != null) {
            return this.ssid.hashCode();
        }
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.ssid);
        parcel.writeString(this.sec.name());
        parcel.writeInt(this.rssi);
    }

}

