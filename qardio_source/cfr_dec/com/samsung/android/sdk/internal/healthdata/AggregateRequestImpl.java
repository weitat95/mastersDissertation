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

public final class AggregateRequestImpl
implements Parcelable,
HealthDataResolver.AggregateRequest {
    public static final Parcelable.Creator<AggregateRequestImpl> CREATOR = new Parcelable.Creator<AggregateRequestImpl>(){

        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new AggregateRequestImpl(parcel, 0);
        }
    };
    private final String a;
    private final String b;
    private final List<AggregatePair> c;
    private final List<Group> d;
    private final TimeGroup e;
    private final HealthDataResolver.Filter f;
    private final List<String> g;
    private final String h;
    private final long i;
    private final long j;
    private final String k;
    private final String l;
    private final long m;
    private final long n;

    private AggregateRequestImpl(Parcel parcel) {
        this.a = parcel.readString();
        this.b = parcel.readString();
        this.c = parcel.createTypedArrayList(AggregatePair.CREATOR);
        this.d = parcel.createTypedArrayList(Group.CREATOR);
        this.e = (TimeGroup)parcel.readParcelable(TimeGroup.class.getClassLoader());
        this.f = (HealthDataResolver.Filter)parcel.readParcelable(HealthDataResolver.Filter.class.getClassLoader());
        this.g = new ArrayList<String>();
        parcel.readStringList(this.g);
        this.h = parcel.readString();
        this.i = parcel.readLong();
        this.j = parcel.readLong();
        this.k = parcel.readString();
        this.l = parcel.readString();
        this.m = parcel.readLong();
        this.n = parcel.readLong();
    }

    /* synthetic */ AggregateRequestImpl(Parcel parcel, byte by) {
        this(parcel);
    }

    public final int describeContents() {
        return 0;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.a);
        parcel.writeString(this.b);
        parcel.writeTypedList(this.c);
        parcel.writeTypedList(this.d);
        parcel.writeParcelable((Parcelable)this.e, 0);
        parcel.writeParcelable((Parcelable)this.f, 0);
        parcel.writeStringList(this.g);
        parcel.writeString(this.h);
        parcel.writeLong(this.i);
        parcel.writeLong(this.j);
        parcel.writeString(this.k);
        parcel.writeString(this.l);
        parcel.writeLong(this.m);
        parcel.writeLong(this.n);
    }

    public static class AggregatePair
    implements Parcelable {
        public static final Parcelable.Creator<AggregatePair> CREATOR = new Parcelable.Creator<AggregatePair>(){

            public final /* synthetic */ Object createFromParcel(Parcel parcel) {
                return new AggregatePair(parcel);
            }
        };
        private int a;
        private String b;
        private String c;

        public AggregatePair(Parcel parcel) {
            this.a = parcel.readInt();
            this.b = parcel.readString();
            this.c = parcel.readString();
        }

        public int describeContents() {
            return 0;
        }

        public String toString() {
            return HealthDataResolver.AggregateRequest.AggregateFunction.from(this.a).toSqlLiteral() + '(' + this.b + ')';
        }

        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.a);
            parcel.writeString(this.b);
            parcel.writeString(this.c);
        }

    }

    public static class Group
    implements Parcelable {
        public static final Parcelable.Creator<Group> CREATOR = new Parcelable.Creator<Group>(){

            public final /* synthetic */ Object createFromParcel(Parcel parcel) {
                return new Group(parcel);
            }
        };
        private String a;
        private String b;

        public Group(Parcel parcel) {
            this.a = parcel.readString();
            this.b = parcel.readString();
        }

        public int describeContents() {
            return 0;
        }

        public String toString() {
            return this.a;
        }

        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeString(this.a);
            parcel.writeString(this.b);
        }

    }

    public static class TimeGroup
    implements Parcelable {
        public static final Parcelable.Creator<TimeGroup> CREATOR = new Parcelable.Creator<TimeGroup>(){

            public final /* synthetic */ Object createFromParcel(Parcel parcel) {
                return new TimeGroup(parcel);
            }
        };
        private int a;
        private int b;
        private String c;
        private String d;
        private String e;

        public TimeGroup(Parcel parcel) {
            this.a = parcel.readInt();
            this.b = parcel.readInt();
            this.c = parcel.readString();
            this.d = parcel.readString();
            this.e = parcel.readString();
        }

        public int describeContents() {
            return 0;
        }

        public String toString() {
            return HealthDataResolver.AggregateRequest.TimeGroupUnit.from(this.a).toSqlLiteral(this.c, this.d, this.b);
        }

        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.a);
            parcel.writeInt(this.b);
            parcel.writeString(this.c);
            parcel.writeString(this.d);
            parcel.writeString(this.e);
        }

    }

}

