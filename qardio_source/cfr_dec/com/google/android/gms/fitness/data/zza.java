/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.fitness.data;

import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.HealthDataTypes;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class zza {
    static final Map<DataType, List<DataType>> zzgzt;

    static {
        HashMap<DataType, List<DataType>> hashMap = new HashMap<DataType, List<DataType>>();
        zzgzt = hashMap;
        hashMap.put(DataType.TYPE_ACTIVITY_SEGMENT, Collections.singletonList(DataType.AGGREGATE_ACTIVITY_SUMMARY));
        zzgzt.put(DataType.TYPE_BASAL_METABOLIC_RATE, Collections.singletonList(DataType.AGGREGATE_BASAL_METABOLIC_RATE_SUMMARY));
        zzgzt.put(DataType.TYPE_BODY_FAT_PERCENTAGE, Collections.singletonList(DataType.AGGREGATE_BODY_FAT_PERCENTAGE_SUMMARY));
        zzgzt.put(DataType.zzhbd, Collections.singletonList(DataType.zzhbf));
        zzgzt.put(DataType.zzhbc, Collections.singletonList(DataType.zzhbg));
        zzgzt.put(DataType.TYPE_CALORIES_CONSUMED, Collections.singletonList(DataType.AGGREGATE_CALORIES_CONSUMED));
        zzgzt.put(DataType.TYPE_CALORIES_EXPENDED, Collections.singletonList(DataType.AGGREGATE_CALORIES_EXPENDED));
        zzgzt.put(DataType.TYPE_DISTANCE_DELTA, Collections.singletonList(DataType.AGGREGATE_DISTANCE_DELTA));
        zzgzt.put(DataType.zzhay, Collections.singletonList(DataType.zzhbe));
        zzgzt.put(DataType.TYPE_LOCATION_SAMPLE, Collections.singletonList(DataType.AGGREGATE_LOCATION_BOUNDING_BOX));
        zzgzt.put(DataType.TYPE_NUTRITION, Collections.singletonList(DataType.AGGREGATE_NUTRITION_SUMMARY));
        zzgzt.put(DataType.TYPE_HYDRATION, Collections.singletonList(DataType.AGGREGATE_HYDRATION));
        zzgzt.put(DataType.TYPE_HEART_RATE_BPM, Collections.singletonList(DataType.AGGREGATE_HEART_RATE_SUMMARY));
        zzgzt.put(DataType.TYPE_POWER_SAMPLE, Collections.singletonList(DataType.AGGREGATE_POWER_SUMMARY));
        zzgzt.put(DataType.TYPE_SPEED, Collections.singletonList(DataType.AGGREGATE_SPEED_SUMMARY));
        zzgzt.put(DataType.TYPE_STEP_COUNT_DELTA, Collections.singletonList(DataType.AGGREGATE_STEP_COUNT_DELTA));
        zzgzt.put(DataType.TYPE_WEIGHT, Collections.singletonList(DataType.AGGREGATE_WEIGHT_SUMMARY));
        zzgzt.put(HealthDataTypes.TYPE_BLOOD_PRESSURE, Collections.singletonList(HealthDataTypes.AGGREGATE_BLOOD_PRESSURE_SUMMARY));
        zzgzt.put(HealthDataTypes.TYPE_BLOOD_GLUCOSE, Collections.singletonList(HealthDataTypes.AGGREGATE_BLOOD_GLUCOSE_SUMMARY));
        zzgzt.put(HealthDataTypes.TYPE_OXYGEN_SATURATION, Collections.singletonList(HealthDataTypes.AGGREGATE_OXYGEN_SATURATION_SUMMARY));
        zzgzt.put(HealthDataTypes.TYPE_BODY_TEMPERATURE, Collections.singletonList(HealthDataTypes.AGGREGATE_BODY_TEMPERATURE_SUMMARY));
        zzgzt.put(HealthDataTypes.TYPE_BASAL_BODY_TEMPERATURE, Collections.singletonList(HealthDataTypes.AGGREGATE_BASAL_BODY_TEMPERATURE_SUMMARY));
        zzgzt.put(HealthDataTypes.TYPE_CERVICAL_MUCUS, Collections.singletonList(HealthDataTypes.TYPE_CERVICAL_MUCUS));
        zzgzt.put(HealthDataTypes.TYPE_CERVICAL_POSITION, Collections.singletonList(HealthDataTypes.TYPE_CERVICAL_POSITION));
        zzgzt.put(HealthDataTypes.TYPE_MENSTRUATION, Collections.singletonList(HealthDataTypes.TYPE_MENSTRUATION));
        zzgzt.put(HealthDataTypes.TYPE_OVULATION_TEST, Collections.singletonList(HealthDataTypes.TYPE_OVULATION_TEST));
        zzgzt.put(HealthDataTypes.TYPE_VAGINAL_SPOTTING, Collections.singletonList(HealthDataTypes.TYPE_VAGINAL_SPOTTING));
    }
}

