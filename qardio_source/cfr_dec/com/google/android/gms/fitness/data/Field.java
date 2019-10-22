/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.fitness.data.zzq;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;

public final class Field
extends zzbfm {
    public static final Parcelable.Creator<Field> CREATOR;
    public static final Field FIELD_ACCURACY;
    public static final Field FIELD_ACTIVITY;
    public static final Field FIELD_ACTIVITY_CONFIDENCE;
    public static final Field FIELD_ALTITUDE;
    public static final Field FIELD_AVERAGE;
    public static final Field FIELD_BPM;
    public static final Field FIELD_CALORIES;
    public static final Field FIELD_CIRCUMFERENCE;
    public static final Field FIELD_CONFIDENCE;
    public static final Field FIELD_DISTANCE;
    public static final Field FIELD_DURATION;
    public static final Field FIELD_EXERCISE;
    public static final Field FIELD_FOOD_ITEM;
    public static final Field FIELD_HEIGHT;
    public static final Field FIELD_HIGH_LATITUDE;
    public static final Field FIELD_HIGH_LONGITUDE;
    public static final Field FIELD_LATITUDE;
    public static final Field FIELD_LONGITUDE;
    public static final Field FIELD_LOW_LATITUDE;
    public static final Field FIELD_LOW_LONGITUDE;
    public static final Field FIELD_MAX;
    public static final Field FIELD_MEAL_TYPE;
    public static final Field FIELD_MIN;
    public static final Field FIELD_NUM_SEGMENTS;
    public static final Field FIELD_NUTRIENTS;
    public static final Field FIELD_OCCURRENCES;
    public static final Field FIELD_PERCENTAGE;
    public static final Field FIELD_REPETITIONS;
    public static final Field FIELD_RESISTANCE;
    public static final Field FIELD_RESISTANCE_TYPE;
    public static final Field FIELD_REVOLUTIONS;
    public static final Field FIELD_RPM;
    public static final Field FIELD_SPEED;
    public static final Field FIELD_STEPS;
    public static final Field FIELD_STEP_LENGTH;
    public static final Field FIELD_VOLUME;
    public static final Field FIELD_WATTS;
    public static final Field FIELD_WEIGHT;
    private static Field zzhbx;
    private static Field zzhby;
    public static final Field zzhbz;
    public static final Field zzhca;
    public static final Field zzhcb;
    public static final Field zzhcc;
    public static final Field zzhcd;
    public static final Field zzhce;
    public static final Field zzhcf;
    public static final Field zzhcg;
    public static final Field zzhch;
    public static final Field zzhci;
    public static final Field zzhcj;
    public static final Field zzhck;
    public static final Field zzhcl;
    public static final Field zzhcm;
    public static final Field zzhcn;
    public static final Field zzhco;
    public static final Field zzhcp;
    private final int format;
    private final String name;
    private final int versionCode;
    private final Boolean zzhcq;

    static {
        FIELD_ACTIVITY = Field.zzhg("activity");
        FIELD_CONFIDENCE = Field.zzhi("confidence");
        FIELD_ACTIVITY_CONFIDENCE = Field.zzhk("activity_confidence");
        FIELD_STEPS = Field.zzhg("steps");
        FIELD_STEP_LENGTH = Field.zzhi("step_length");
        FIELD_DURATION = Field.zzhg("duration");
        zzhbx = Field.zzhh("duration");
        zzhby = Field.zzhk("activity_duration");
        zzhbz = Field.zzhk("activity_duration.ascending");
        zzhca = Field.zzhk("activity_duration.descending");
        FIELD_BPM = Field.zzhi("bpm");
        FIELD_LATITUDE = Field.zzhi("latitude");
        FIELD_LONGITUDE = Field.zzhi("longitude");
        FIELD_ACCURACY = Field.zzhi("accuracy");
        FIELD_ALTITUDE = new Field("altitude", 2, true);
        FIELD_DISTANCE = Field.zzhi("distance");
        FIELD_HEIGHT = Field.zzhi("height");
        FIELD_WEIGHT = Field.zzhi("weight");
        FIELD_CIRCUMFERENCE = Field.zzhi("circumference");
        FIELD_PERCENTAGE = Field.zzhi("percentage");
        FIELD_SPEED = Field.zzhi("speed");
        FIELD_RPM = Field.zzhi("rpm");
        zzhcb = Field.zzhl("google.android.fitness.GoalV2");
        zzhcc = Field.zzhl("google.android.fitness.StrideModel");
        FIELD_REVOLUTIONS = Field.zzhg("revolutions");
        FIELD_CALORIES = Field.zzhi("calories");
        FIELD_WATTS = Field.zzhi("watts");
        FIELD_VOLUME = Field.zzhi("volume");
        FIELD_MEAL_TYPE = Field.zzhg("meal_type");
        FIELD_FOOD_ITEM = Field.zzhj("food_item");
        FIELD_NUTRIENTS = Field.zzhk("nutrients");
        zzhcd = Field.zzhi("elevation.change");
        zzhce = Field.zzhk("elevation.gain");
        zzhcf = Field.zzhk("elevation.loss");
        zzhcg = Field.zzhi("floors");
        zzhch = Field.zzhk("floor.gain");
        zzhci = Field.zzhk("floor.loss");
        FIELD_EXERCISE = Field.zzhj("exercise");
        FIELD_REPETITIONS = Field.zzhg("repetitions");
        FIELD_RESISTANCE = Field.zzhi("resistance");
        FIELD_RESISTANCE_TYPE = Field.zzhg("resistance_type");
        FIELD_NUM_SEGMENTS = Field.zzhg("num_segments");
        FIELD_AVERAGE = Field.zzhi("average");
        FIELD_MAX = Field.zzhi("max");
        FIELD_MIN = Field.zzhi("min");
        FIELD_LOW_LATITUDE = Field.zzhi("low_latitude");
        FIELD_LOW_LONGITUDE = Field.zzhi("low_longitude");
        FIELD_HIGH_LATITUDE = Field.zzhi("high_latitude");
        FIELD_HIGH_LONGITUDE = Field.zzhi("high_longitude");
        FIELD_OCCURRENCES = Field.zzhg("occurrences");
        zzhcj = Field.zzhg("sensor_type");
        zzhck = Field.zzhg("sensor_types");
        zzhcl = new Field("timestamps", 5);
        zzhcm = Field.zzhg("sample_period");
        zzhcn = Field.zzhg("num_samples");
        zzhco = Field.zzhg("num_dimensions");
        zzhcp = new Field("sensor_values", 6);
        CREATOR = new zzq();
    }

    Field(int n, String string2, int n2, Boolean bl) {
        this.versionCode = n;
        this.name = zzbq.checkNotNull(string2);
        this.format = n2;
        this.zzhcq = bl;
    }

    private Field(String string2, int n) {
        this(2, string2, n, null);
    }

    private Field(String string2, int n, Boolean bl) {
        this(2, string2, n, bl);
    }

    private static Field zzhg(String string2) {
        return new Field(string2, 1);
    }

    static Field zzhh(String string2) {
        return new Field(string2, 1, true);
    }

    static Field zzhi(String string2) {
        return new Field(string2, 2);
    }

    private static Field zzhj(String string2) {
        return new Field(string2, 3);
    }

    private static Field zzhk(String string2) {
        return new Field(string2, 4);
    }

    private static Field zzhl(String string2) {
        return new Field(string2, 7);
    }

    static Field zzhm(String string2) {
        return new Field(string2, 7, true);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Field)) return false;
        object = (Field)object;
        if (!this.name.equals(((Field)object).name)) return false;
        if (this.format != ((Field)object).format) return false;
        return true;
    }

    public final int getFormat() {
        return this.format;
    }

    public final String getName() {
        return this.name;
    }

    public final int hashCode() {
        return this.name.hashCode();
    }

    public final Boolean isOptional() {
        return this.zzhcq;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final String toString() {
        String string2;
        String string3 = this.name;
        if (this.format == 1) {
            string2 = "i";
            do {
                return String.format("%s(%s)", string3, string2);
                break;
            } while (true);
        }
        string2 = "f";
        return String.format("%s(%s)", string3, string2);
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, this.getName(), false);
        zzbfp.zzc(parcel, 2, this.getFormat());
        zzbfp.zza(parcel, 3, this.isOptional(), false);
        zzbfp.zzc(parcel, 1000, this.versionCode);
        zzbfp.zzai(parcel, n);
    }

    public static final class zza {
        public static final Field zzhcr = Field.zzhi("x");
        public static final Field zzhcs = Field.zzhi("y");
        public static final Field zzhct = Field.zzhi("z");
        public static final Field zzhcu = Field.zzhm("debug_session");
        public static final Field zzhcv = Field.zzhm("google.android.fitness.SessionV2");
    }

}

