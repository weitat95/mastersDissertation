/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.samsung.android.sdk.internal.healthdata;

import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.sdk.healthdata.HealthDataResolver;
import java.util.ArrayList;
import java.util.List;

public final class ReadRequestImpl
implements Parcelable,
HealthDataResolver.ReadRequest {
    public static final Parcelable.Creator<ReadRequestImpl> CREATOR = new Parcelable.Creator<ReadRequestImpl>(){

        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new ReadRequestImpl(parcel);
        }
    };
    private final String a;
    private String b;
    private String c;
    private long d;
    private long e;
    private int f;
    private int g;
    private HealthDataResolver.Filter h;
    private List<Projection> i = null;
    private List<String> j = null;
    private byte k;
    private long l;
    private String m;
    private String n;
    private long o;
    private long p;

    public ReadRequestImpl(Parcel parcel) {
        this.a = parcel.readString();
        this.b = parcel.readString();
        this.c = parcel.readString();
        this.d = parcel.readLong();
        this.e = parcel.readLong();
        this.f = parcel.readInt();
        this.g = parcel.readInt();
        this.h = (HealthDataResolver.Filter)parcel.readParcelable(HealthDataResolver.Filter.class.getClassLoader());
        this.i = parcel.createTypedArrayList(Projection.CREATOR);
        this.j = new ArrayList<String>();
        parcel.readStringList(this.j);
        this.k = parcel.readByte();
        this.l = parcel.readLong();
        this.m = parcel.readString();
        this.n = parcel.readString();
        this.o = parcel.readLong();
        this.p = parcel.readLong();
    }

    public ReadRequestImpl(String string2, String string3, HealthDataResolver.Filter filter, List<Projection> list, List<String> list2, byte by, String string4, long l, long l2, int n, int n2, long l3, String string5, String string6, Long l4, Long l5) {
        this.a = string2;
        this.c = string3;
        this.b = string4;
        this.d = l;
        this.e = l2;
        this.f = n;
        this.g = n2;
        this.h = filter;
        this.i = list;
        this.j = list2;
        this.k = by;
        this.l = l3;
        this.m = string5;
        this.n = string6;
        this.o = l4;
        this.p = l5;
    }

    public final int describeContents() {
        return 0;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.a);
        parcel.writeString(this.b);
        parcel.writeString(this.c);
        parcel.writeLong(this.d);
        parcel.writeLong(this.e);
        parcel.writeInt(this.f);
        parcel.writeInt(this.g);
        parcel.writeParcelable((Parcelable)this.h, 0);
        parcel.writeTypedList(this.i);
        parcel.writeStringList(this.j);
        parcel.writeByte(this.k);
        parcel.writeLong(this.l);
        parcel.writeString(this.m);
        parcel.writeString(this.n);
        parcel.writeLong(this.o);
        parcel.writeLong(this.p);
    }

    public static class Projection
    implements Parcelable {
        public static final Parcelable.Creator<Projection> CREATOR = new Parcelable.Creator<Projection>(){

            public final /* synthetic */ Object createFromParcel(Parcel parcel) {
                return new Projection(parcel);
            }
        };
        private String a;
        private String b;

        public Projection(Parcel parcel) {
            this.a = parcel.readString();
            this.b = parcel.readString();
        }

        public Projection(String string2, String string3) {
            if (string2 == null || string2.isEmpty()) {
                throw new IllegalArgumentException("Null or empty property for read request is not allowed");
            }
            this.a = string2;
            this.b = string3;
        }

        public int describeContents() {
            return 0;
        }

        public String getAlias() {
            return this.b;
        }

        public String getProperty() {
            return this.a;
        }

        public String toString() {
            return this.a;
        }

        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeString(this.a);
            parcel.writeString(this.b);
        }

    }

}

