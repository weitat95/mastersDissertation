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
import android.support.v4.util.ArraySet;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.data.zzl;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class DataType
extends zzbfm
implements ReflectedParcelable {
    public static final DataType AGGREGATE_ACTIVITY_SUMMARY;
    public static final DataType AGGREGATE_BASAL_METABOLIC_RATE_SUMMARY;
    public static final DataType AGGREGATE_BODY_FAT_PERCENTAGE_SUMMARY;
    @Deprecated
    public static final DataType AGGREGATE_CALORIES_CONSUMED;
    public static final DataType AGGREGATE_CALORIES_EXPENDED;
    public static final DataType AGGREGATE_DISTANCE_DELTA;
    public static final DataType AGGREGATE_HEART_RATE_SUMMARY;
    public static final DataType AGGREGATE_HEIGHT_SUMMARY;
    public static final DataType AGGREGATE_HYDRATION;
    @Deprecated
    public static final Set<DataType> AGGREGATE_INPUT_TYPES;
    public static final DataType AGGREGATE_LOCATION_BOUNDING_BOX;
    public static final DataType AGGREGATE_NUTRITION_SUMMARY;
    public static final DataType AGGREGATE_POWER_SUMMARY;
    public static final DataType AGGREGATE_SPEED_SUMMARY;
    public static final DataType AGGREGATE_STEP_COUNT_DELTA;
    public static final DataType AGGREGATE_WEIGHT_SUMMARY;
    public static final Parcelable.Creator<DataType> CREATOR;
    @Deprecated
    public static final DataType TYPE_ACTIVITY_SAMPLE;
    public static final DataType TYPE_ACTIVITY_SAMPLES;
    public static final DataType TYPE_ACTIVITY_SEGMENT;
    public static final DataType TYPE_BASAL_METABOLIC_RATE;
    public static final DataType TYPE_BODY_FAT_PERCENTAGE;
    @Deprecated
    public static final DataType TYPE_CALORIES_CONSUMED;
    public static final DataType TYPE_CALORIES_EXPENDED;
    public static final DataType TYPE_CYCLING_PEDALING_CADENCE;
    public static final DataType TYPE_CYCLING_PEDALING_CUMULATIVE;
    public static final DataType TYPE_CYCLING_WHEEL_REVOLUTION;
    public static final DataType TYPE_CYCLING_WHEEL_RPM;
    @KeepName
    public static final DataType TYPE_DISTANCE_CUMULATIVE;
    public static final DataType TYPE_DISTANCE_DELTA;
    public static final DataType TYPE_HEART_RATE_BPM;
    public static final DataType TYPE_HEIGHT;
    public static final DataType TYPE_HYDRATION;
    public static final DataType TYPE_LOCATION_SAMPLE;
    public static final DataType TYPE_LOCATION_TRACK;
    public static final DataType TYPE_NUTRITION;
    public static final DataType TYPE_POWER_SAMPLE;
    public static final DataType TYPE_SPEED;
    public static final DataType TYPE_STEP_COUNT_CADENCE;
    @KeepName
    public static final DataType TYPE_STEP_COUNT_CUMULATIVE;
    public static final DataType TYPE_STEP_COUNT_DELTA;
    public static final DataType TYPE_WEIGHT;
    public static final DataType TYPE_WORKOUT_EXERCISE;
    public static final DataType zzhav;
    public static final DataType zzhaw;
    public static final DataType zzhax;
    public static final DataType zzhay;
    public static final DataType zzhaz;
    public static final DataType zzhba;
    public static final DataType zzhbb;
    public static final DataType zzhbc;
    public static final DataType zzhbd;
    public static final DataType zzhbe;
    public static final DataType zzhbf;
    public static final DataType zzhbg;
    private final String name;
    private final int versionCode;
    private final List<Field> zzhbh;
    private final String zzhbi;
    private final String zzhbj;

    static {
        TYPE_STEP_COUNT_DELTA = new DataType("com.google.step_count.delta", "https://www.googleapis.com/auth/fitness.activity.read", "https://www.googleapis.com/auth/fitness.activity.write", Field.FIELD_STEPS);
        TYPE_STEP_COUNT_CUMULATIVE = new DataType("com.google.step_count.cumulative", "https://www.googleapis.com/auth/fitness.activity.read", "https://www.googleapis.com/auth/fitness.activity.write", Field.FIELD_STEPS);
        zzhav = new DataType("com.google.step_length", "https://www.googleapis.com/auth/fitness.activity.read", "https://www.googleapis.com/auth/fitness.activity.write", Field.FIELD_STEP_LENGTH);
        TYPE_STEP_COUNT_CADENCE = new DataType("com.google.step_count.cadence", "https://www.googleapis.com/auth/fitness.activity.read", "https://www.googleapis.com/auth/fitness.activity.write", Field.FIELD_RPM);
        zzhaw = new DataType("com.google.internal.goal", Field.zzhcb);
        zzhax = new DataType("com.google.stride_model", "https://www.googleapis.com/auth/fitness.activity.read", "https://www.googleapis.com/auth/fitness.activity.write", Field.zzhcc);
        TYPE_ACTIVITY_SEGMENT = new DataType("com.google.activity.segment", "https://www.googleapis.com/auth/fitness.activity.read", "https://www.googleapis.com/auth/fitness.activity.write", Field.FIELD_ACTIVITY);
        zzhay = new DataType("com.google.floor_change", "https://www.googleapis.com/auth/fitness.activity.read", "https://www.googleapis.com/auth/fitness.activity.write", Field.FIELD_ACTIVITY, Field.FIELD_CONFIDENCE, Field.zzhcd, Field.zzhcg);
        TYPE_CALORIES_CONSUMED = new DataType("com.google.calories.consumed", "https://www.googleapis.com/auth/fitness.activity.read", "https://www.googleapis.com/auth/fitness.activity.write", Field.FIELD_CALORIES);
        TYPE_CALORIES_EXPENDED = new DataType("com.google.calories.expended", "https://www.googleapis.com/auth/fitness.activity.read", "https://www.googleapis.com/auth/fitness.activity.write", Field.FIELD_CALORIES);
        TYPE_BASAL_METABOLIC_RATE = new DataType("com.google.calories.bmr", "https://www.googleapis.com/auth/fitness.activity.read", "https://www.googleapis.com/auth/fitness.activity.write", Field.FIELD_CALORIES);
        TYPE_POWER_SAMPLE = new DataType("com.google.power.sample", "https://www.googleapis.com/auth/fitness.activity.read", "https://www.googleapis.com/auth/fitness.activity.write", Field.FIELD_WATTS);
        TYPE_ACTIVITY_SAMPLE = new DataType("com.google.activity.sample", "https://www.googleapis.com/auth/fitness.activity.read", "https://www.googleapis.com/auth/fitness.activity.write", Field.FIELD_ACTIVITY, Field.FIELD_CONFIDENCE);
        TYPE_ACTIVITY_SAMPLES = new DataType("com.google.activity.samples", "https://www.googleapis.com/auth/fitness.activity.read", "https://www.googleapis.com/auth/fitness.activity.write", Field.FIELD_ACTIVITY_CONFIDENCE);
        zzhaz = new DataType("com.google.accelerometer", "https://www.googleapis.com/auth/fitness.activity.read", "https://www.googleapis.com/auth/fitness.activity.write", Field.zza.zzhcr, Field.zza.zzhcs, Field.zza.zzhct);
        zzhba = new DataType("com.google.sensor.events", "https://www.googleapis.com/auth/fitness.activity.read", "https://www.googleapis.com/auth/fitness.activity.write", Field.zzhcj, Field.zzhcl, Field.zzhcp);
        zzhbb = new DataType("com.google.sensor.const_rate_events", "https://www.googleapis.com/auth/fitness.activity.read", "https://www.googleapis.com/auth/fitness.activity.write", Field.zzhck, Field.zzhcm, Field.zzhcn, Field.zzhco, Field.zzhcp);
        TYPE_HEART_RATE_BPM = new DataType("com.google.heart_rate.bpm", "https://www.googleapis.com/auth/fitness.body.read", "https://www.googleapis.com/auth/fitness.body.read", Field.FIELD_BPM);
        TYPE_LOCATION_SAMPLE = new DataType("com.google.location.sample", "https://www.googleapis.com/auth/fitness.location.read", "https://www.googleapis.com/auth/fitness.location.write", Field.FIELD_LATITUDE, Field.FIELD_LONGITUDE, Field.FIELD_ACCURACY, Field.FIELD_ALTITUDE);
        TYPE_LOCATION_TRACK = new DataType("com.google.location.track", "https://www.googleapis.com/auth/fitness.location.read", "https://www.googleapis.com/auth/fitness.location.write", Field.FIELD_LATITUDE, Field.FIELD_LONGITUDE, Field.FIELD_ACCURACY, Field.FIELD_ALTITUDE);
        TYPE_DISTANCE_DELTA = new DataType("com.google.distance.delta", "https://www.googleapis.com/auth/fitness.location.read", "https://www.googleapis.com/auth/fitness.location.write", Field.FIELD_DISTANCE);
        TYPE_DISTANCE_CUMULATIVE = new DataType("com.google.distance.cumulative", "https://www.googleapis.com/auth/fitness.location.read", "https://www.googleapis.com/auth/fitness.location.write", Field.FIELD_DISTANCE);
        TYPE_SPEED = new DataType("com.google.speed", "https://www.googleapis.com/auth/fitness.location.read", "https://www.googleapis.com/auth/fitness.location.write", Field.FIELD_SPEED);
        TYPE_CYCLING_WHEEL_REVOLUTION = new DataType("com.google.cycling.wheel_revolution.cumulative", "https://www.googleapis.com/auth/fitness.location.read", "https://www.googleapis.com/auth/fitness.location.write", Field.FIELD_REVOLUTIONS);
        TYPE_CYCLING_WHEEL_RPM = new DataType("com.google.cycling.wheel_revolution.rpm", "https://www.googleapis.com/auth/fitness.location.read", "https://www.googleapis.com/auth/fitness.location.write", Field.FIELD_RPM);
        TYPE_CYCLING_PEDALING_CUMULATIVE = new DataType("com.google.cycling.pedaling.cumulative", "https://www.googleapis.com/auth/fitness.activity.read", "https://www.googleapis.com/auth/fitness.activity.write", Field.FIELD_REVOLUTIONS);
        TYPE_CYCLING_PEDALING_CADENCE = new DataType("com.google.cycling.pedaling.cadence", "https://www.googleapis.com/auth/fitness.activity.read", "https://www.googleapis.com/auth/fitness.activity.write", Field.FIELD_RPM);
        TYPE_HEIGHT = new DataType("com.google.height", "https://www.googleapis.com/auth/fitness.body.read", "https://www.googleapis.com/auth/fitness.body.write", Field.FIELD_HEIGHT);
        TYPE_WEIGHT = new DataType("com.google.weight", "https://www.googleapis.com/auth/fitness.body.read", "https://www.googleapis.com/auth/fitness.body.write", Field.FIELD_WEIGHT);
        TYPE_BODY_FAT_PERCENTAGE = new DataType("com.google.body.fat.percentage", "https://www.googleapis.com/auth/fitness.body.read", "https://www.googleapis.com/auth/fitness.body.write", Field.FIELD_PERCENTAGE);
        zzhbc = new DataType("com.google.body.waist.circumference", "https://www.googleapis.com/auth/fitness.body.read", "https://www.googleapis.com/auth/fitness.body.write", Field.FIELD_CIRCUMFERENCE);
        zzhbd = new DataType("com.google.body.hip.circumference", "https://www.googleapis.com/auth/fitness.body.read", "https://www.googleapis.com/auth/fitness.body.write", Field.FIELD_CIRCUMFERENCE);
        TYPE_NUTRITION = new DataType("com.google.nutrition", "https://www.googleapis.com/auth/fitness.nutrition.read", "https://www.googleapis.com/auth/fitness.nutrition.write", Field.FIELD_NUTRIENTS, Field.FIELD_MEAL_TYPE, Field.FIELD_FOOD_ITEM);
        TYPE_HYDRATION = new DataType("com.google.hydration", "https://www.googleapis.com/auth/fitness.nutrition.read", "https://www.googleapis.com/auth/fitness.nutrition.write", Field.FIELD_VOLUME);
        TYPE_WORKOUT_EXERCISE = new DataType("com.google.activity.exercise", "https://www.googleapis.com/auth/fitness.activity.read", "https://www.googleapis.com/auth/fitness.activity.write", Field.FIELD_EXERCISE, Field.FIELD_REPETITIONS, Field.FIELD_DURATION, Field.FIELD_RESISTANCE_TYPE, Field.FIELD_RESISTANCE);
        AGGREGATE_ACTIVITY_SUMMARY = new DataType("com.google.activity.summary", "https://www.googleapis.com/auth/fitness.activity.read", "https://www.googleapis.com/auth/fitness.activity.write", Field.FIELD_ACTIVITY, Field.FIELD_DURATION, Field.FIELD_NUM_SEGMENTS);
        zzhbe = new DataType("com.google.floor_change.summary", "https://www.googleapis.com/auth/fitness.activity.read", "https://www.googleapis.com/auth/fitness.activity.write", Field.zzhbz, Field.zzhca, Field.zzhce, Field.zzhcf, Field.zzhch, Field.zzhci);
        AGGREGATE_BASAL_METABOLIC_RATE_SUMMARY = new DataType("com.google.calories.bmr.summary", "https://www.googleapis.com/auth/fitness.body.read", "https://www.googleapis.com/auth/fitness.body.write", Field.FIELD_AVERAGE, Field.FIELD_MAX, Field.FIELD_MIN);
        AGGREGATE_STEP_COUNT_DELTA = TYPE_STEP_COUNT_DELTA;
        AGGREGATE_DISTANCE_DELTA = TYPE_DISTANCE_DELTA;
        AGGREGATE_CALORIES_CONSUMED = TYPE_CALORIES_CONSUMED;
        AGGREGATE_CALORIES_EXPENDED = TYPE_CALORIES_EXPENDED;
        AGGREGATE_HEART_RATE_SUMMARY = new DataType("com.google.heart_rate.summary", "https://www.googleapis.com/auth/fitness.body.read", "https://www.googleapis.com/auth/fitness.body.write", Field.FIELD_AVERAGE, Field.FIELD_MAX, Field.FIELD_MIN);
        AGGREGATE_LOCATION_BOUNDING_BOX = new DataType("com.google.location.bounding_box", "https://www.googleapis.com/auth/fitness.location.read", "https://www.googleapis.com/auth/fitness.location.write", Field.FIELD_LOW_LATITUDE, Field.FIELD_LOW_LONGITUDE, Field.FIELD_HIGH_LATITUDE, Field.FIELD_HIGH_LONGITUDE);
        AGGREGATE_POWER_SUMMARY = new DataType("com.google.power.summary", "https://www.googleapis.com/auth/fitness.activity.read", "https://www.googleapis.com/auth/fitness.activity.write", Field.FIELD_AVERAGE, Field.FIELD_MAX, Field.FIELD_MIN);
        AGGREGATE_SPEED_SUMMARY = new DataType("com.google.speed.summary", "https://www.googleapis.com/auth/fitness.location.read", "https://www.googleapis.com/auth/fitness.location.write", Field.FIELD_AVERAGE, Field.FIELD_MAX, Field.FIELD_MIN);
        AGGREGATE_BODY_FAT_PERCENTAGE_SUMMARY = new DataType("com.google.body.fat.percentage.summary", "https://www.googleapis.com/auth/fitness.body.read", "https://www.googleapis.com/auth/fitness.body.write", Field.FIELD_AVERAGE, Field.FIELD_MAX, Field.FIELD_MIN);
        zzhbf = new DataType("com.google.body.hip.circumference.summary", "https://www.googleapis.com/auth/fitness.body.read", "https://www.googleapis.com/auth/fitness.body.write", Field.FIELD_AVERAGE, Field.FIELD_MAX, Field.FIELD_MIN);
        zzhbg = new DataType("com.google.body.waist.circumference.summary", "https://www.googleapis.com/auth/fitness.body.read", "https://www.googleapis.com/auth/fitness.body.write", Field.FIELD_AVERAGE, Field.FIELD_MAX, Field.FIELD_MIN);
        AGGREGATE_WEIGHT_SUMMARY = new DataType("com.google.weight.summary", "https://www.googleapis.com/auth/fitness.body.read", "https://www.googleapis.com/auth/fitness.body.write", Field.FIELD_AVERAGE, Field.FIELD_MAX, Field.FIELD_MIN);
        AGGREGATE_HEIGHT_SUMMARY = new DataType("com.google.height.summary", "https://www.googleapis.com/auth/fitness.body.read", "https://www.googleapis.com/auth/fitness.body.write", Field.FIELD_AVERAGE, Field.FIELD_MAX, Field.FIELD_MIN);
        AGGREGATE_NUTRITION_SUMMARY = new DataType("com.google.nutrition.summary", "https://www.googleapis.com/auth/fitness.nutrition.read", "https://www.googleapis.com/auth/fitness.nutrition.write", Field.FIELD_NUTRIENTS, Field.FIELD_MEAL_TYPE);
        AGGREGATE_HYDRATION = TYPE_HYDRATION;
        ArraySet<DataType> arraySet = new ArraySet<DataType>();
        AGGREGATE_INPUT_TYPES = arraySet;
        arraySet.add(TYPE_ACTIVITY_SEGMENT);
        AGGREGATE_INPUT_TYPES.add(TYPE_BASAL_METABOLIC_RATE);
        AGGREGATE_INPUT_TYPES.add(TYPE_BODY_FAT_PERCENTAGE);
        AGGREGATE_INPUT_TYPES.add(zzhbd);
        AGGREGATE_INPUT_TYPES.add(zzhbc);
        AGGREGATE_INPUT_TYPES.add(TYPE_CALORIES_CONSUMED);
        AGGREGATE_INPUT_TYPES.add(TYPE_CALORIES_EXPENDED);
        AGGREGATE_INPUT_TYPES.add(TYPE_DISTANCE_DELTA);
        AGGREGATE_INPUT_TYPES.add(zzhay);
        AGGREGATE_INPUT_TYPES.add(TYPE_LOCATION_SAMPLE);
        AGGREGATE_INPUT_TYPES.add(TYPE_NUTRITION);
        AGGREGATE_INPUT_TYPES.add(TYPE_HYDRATION);
        AGGREGATE_INPUT_TYPES.add(TYPE_HEART_RATE_BPM);
        AGGREGATE_INPUT_TYPES.add(TYPE_POWER_SAMPLE);
        AGGREGATE_INPUT_TYPES.add(TYPE_SPEED);
        AGGREGATE_INPUT_TYPES.add(TYPE_STEP_COUNT_DELTA);
        AGGREGATE_INPUT_TYPES.add(TYPE_WEIGHT);
        CREATOR = new zzl();
    }

    DataType(int n, String string2, List<Field> list, String string3, String string4) {
        this.versionCode = n;
        this.name = string2;
        this.zzhbh = Collections.unmodifiableList(list);
        this.zzhbi = string3;
        this.zzhbj = string4;
    }

    public DataType(String string2, String string3, String string4, Field ... arrfield) {
        this(1, string2, Arrays.asList(arrfield), string3, string4);
    }

    private DataType(String string2, Field ... arrfield) {
        this(1, string2, Arrays.asList(arrfield), null, null);
    }

    public static List<DataType> getAggregatesForInput(DataType object) {
        if ((object = com.google.android.gms.fitness.data.zza.zzgzt.get(object)) == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(object);
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean equals(Object object) {
        boolean bl = false;
        if (this == object) return true;
        boolean bl2 = bl;
        if (!(object instanceof DataType)) return bl2;
        object = (DataType)object;
        boolean bl3 = this.name.equals(((DataType)object).name) && this.zzhbh.equals(((DataType)object).zzhbh);
        bl2 = bl;
        if (!bl3) return bl2;
        return true;
    }

    public final List<Field> getFields() {
        return this.zzhbh;
    }

    public final String getName() {
        return this.name;
    }

    public final int hashCode() {
        return this.name.hashCode();
    }

    public final int indexOf(Field field) {
        int n = this.zzhbh.indexOf(field);
        if (n < 0) {
            throw new IllegalArgumentException(String.format("%s not a field of %s", field, this));
        }
        return n;
    }

    public final String toString() {
        return String.format("DataType{%s%s}", this.name, this.zzhbh);
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, this.getName(), false);
        zzbfp.zzc(parcel, 2, this.getFields(), false);
        zzbfp.zza(parcel, 3, this.zzhbi, false);
        zzbfp.zza(parcel, 4, this.zzhbj, false);
        zzbfp.zzc(parcel, 1000, this.versionCode);
        zzbfp.zzai(parcel, n);
    }

    public final String zzaqp() {
        if (this.name.startsWith("com.google.")) {
            return this.name.substring(11);
        }
        return this.name;
    }

    public static final class zza {
        public static final DataType zzhbk = new DataType("com.google.internal.session.debug", "https://www.googleapis.com/auth/fitness.activity.read", "https://www.googleapis.com/auth/fitness.activity.write", Field.zza.zzhcu);
        public static final DataType zzhbl = new DataType("com.google.internal.session.v2", "https://www.googleapis.com/auth/fitness.activity.read", "https://www.googleapis.com/auth/fitness.activity.write", Field.zza.zzhcv);
    }

}

