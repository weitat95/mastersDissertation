/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.samsung.android.sdk.healthdata;

import android.os.Parcel;
import android.os.Parcelable;

public final class HealthDevice
implements Parcelable {
    public static final Parcelable.Creator<HealthDevice> CREATOR = new Parcelable.Creator<HealthDevice>(){

        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new HealthDevice(parcel, 0);
        }
    };
    private final String a;
    private final String b;
    private final String c;
    private final String d;
    private final int e;
    private final String f;

    private HealthDevice(Parcel parcel) {
        this.a = parcel.readString();
        this.b = parcel.readString();
        this.c = parcel.readString();
        this.d = parcel.readString();
        this.e = parcel.readInt();
        this.f = parcel.readString();
    }

    /* synthetic */ HealthDevice(Parcel parcel, byte by) {
        this(parcel);
    }

    private HealthDevice(Builder builder) {
        this.a = null;
        this.b = builder.a;
        this.c = builder.b;
        this.d = builder.c;
        this.e = builder.e;
        this.f = builder.d;
    }

    /* synthetic */ HealthDevice(Builder builder, byte by) {
        this(builder);
    }

    public final int describeContents() {
        return 0;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean equals(Object object) {
        boolean bl = false;
        if (object == this) {
            return true;
        }
        boolean bl2 = bl;
        if (!(object instanceof HealthDevice)) return bl2;
        object = (HealthDevice)object;
        bl2 = bl;
        if (this.f == null) return bl2;
        bl2 = bl;
        if (((HealthDevice)object).f == null) return bl2;
        return this.f.equals(((HealthDevice)object).f);
    }

    public final String getUuid() {
        return this.a;
    }

    public final int hashCode() {
        if (this.f == null) {
            return 0;
        }
        return this.f.hashCode();
    }

    public final void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.a);
        parcel.writeString(this.b);
        parcel.writeString(this.c);
        parcel.writeString(this.d);
        parcel.writeInt(this.e);
        parcel.writeString(this.f);
    }

    public static class Builder {
        private String a;
        private String b;
        private String c;
        private String d;
        private int e;

        public HealthDevice build() {
            if (this.d == null || this.d.isEmpty()) {
                throw new IllegalStateException("Seed is not specified");
            }
            switch (this.e) {
                default: {
                    throw new IllegalStateException("Device group is not set correctly");
                }
                case 0: 
                case 360001: 
                case 360002: 
                case 360003: 
            }
            return new HealthDevice(this, 0);
        }

        public Builder setDeviceSeed(String string2) {
            this.d = string2;
            return this;
        }

        public Builder setGroup(int n) {
            this.e = n;
            return this;
        }

        public Builder setManufacturer(String string2) {
            this.c = string2;
            return this;
        }

        public Builder setModel(String string2) {
            this.b = string2;
            return this;
        }
    }

}

