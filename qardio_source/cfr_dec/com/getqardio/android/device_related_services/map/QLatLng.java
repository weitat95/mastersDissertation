/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.device_related_services.map;

public class QLatLng {
    public final double latitude;
    public final double longitude;

    public QLatLng(double d, double d2) {
        this.latitude = d;
        this.longitude = d2;
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
                if (Double.compare(((QLatLng)(object = (QLatLng)object)).getLatitude(), this.getLatitude()) != 0) {
                    return false;
                }
                if (Double.compare(((QLatLng)object).getLongitude(), this.getLongitude()) != 0) break block6;
            }
            return true;
        }
        return false;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public int hashCode() {
        long l = Double.doubleToLongBits(this.getLatitude());
        int n = (int)(l >>> 32 ^ l);
        l = Double.doubleToLongBits(this.getLongitude());
        return n * 31 + (int)(l >>> 32 ^ l);
    }

    public String toString() {
        return "QLatLng{latitude=" + this.latitude + ", longitude=" + this.longitude + '}';
    }
}

