/*
 * Decompiled with CFR 0.147.
 */
package io.fabric.sdk.android.services.common;

class AdvertisingInfo {
    public final String advertisingId;
    public final boolean limitAdTrackingEnabled;

    AdvertisingInfo(String string2, boolean bl) {
        this.advertisingId = string2;
        this.limitAdTrackingEnabled = bl;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) return false;
        if (this.getClass() != object.getClass()) {
            return false;
        }
        object = (AdvertisingInfo)object;
        if (this.limitAdTrackingEnabled != ((AdvertisingInfo)object).limitAdTrackingEnabled) {
            return false;
        }
        if (this.advertisingId != null) {
            if (this.advertisingId.equals(((AdvertisingInfo)object).advertisingId)) return true;
            return false;
        }
        if (((AdvertisingInfo)object).advertisingId == null) return true;
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public int hashCode() {
        int n = 0;
        int n2 = this.advertisingId != null ? this.advertisingId.hashCode() : 0;
        if (this.limitAdTrackingEnabled) {
            n = 1;
        }
        return n2 * 31 + n;
    }
}

