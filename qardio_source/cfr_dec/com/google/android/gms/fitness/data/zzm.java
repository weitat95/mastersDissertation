/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.fitness.data;

import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.HealthDataTypes;

public final class zzm {
    private static DataType[] zzhbm = new DataType[]{DataType.zzhaz, DataType.TYPE_WORKOUT_EXERCISE, DataType.TYPE_ACTIVITY_SAMPLE, DataType.TYPE_ACTIVITY_SAMPLES, DataType.TYPE_ACTIVITY_SEGMENT, DataType.AGGREGATE_ACTIVITY_SUMMARY, HealthDataTypes.TYPE_BASAL_BODY_TEMPERATURE, HealthDataTypes.AGGREGATE_BASAL_BODY_TEMPERATURE_SUMMARY, HealthDataTypes.TYPE_BLOOD_GLUCOSE, HealthDataTypes.AGGREGATE_BLOOD_GLUCOSE_SUMMARY, HealthDataTypes.TYPE_BLOOD_PRESSURE, HealthDataTypes.AGGREGATE_BLOOD_PRESSURE_SUMMARY, DataType.TYPE_BODY_FAT_PERCENTAGE, DataType.AGGREGATE_BODY_FAT_PERCENTAGE_SUMMARY, DataType.zzhbd, DataType.zzhbf, HealthDataTypes.TYPE_BODY_TEMPERATURE, HealthDataTypes.AGGREGATE_BODY_TEMPERATURE_SUMMARY, DataType.zzhbc, DataType.zzhbg, DataType.TYPE_BASAL_METABOLIC_RATE, DataType.AGGREGATE_BASAL_METABOLIC_RATE_SUMMARY, DataType.TYPE_CALORIES_CONSUMED, DataType.TYPE_CALORIES_EXPENDED, HealthDataTypes.TYPE_CERVICAL_MUCUS, HealthDataTypes.TYPE_CERVICAL_POSITION, DataType.TYPE_CYCLING_PEDALING_CADENCE, DataType.TYPE_CYCLING_PEDALING_CUMULATIVE, DataType.TYPE_CYCLING_WHEEL_REVOLUTION, DataType.TYPE_CYCLING_WHEEL_RPM, DataType.TYPE_DISTANCE_CUMULATIVE, DataType.TYPE_DISTANCE_DELTA, DataType.zzhay, DataType.zzhbe, DataType.zzhaw, DataType.TYPE_HEART_RATE_BPM, DataType.AGGREGATE_HEART_RATE_SUMMARY, DataType.TYPE_HEIGHT, DataType.AGGREGATE_HEIGHT_SUMMARY, DataType.AGGREGATE_LOCATION_BOUNDING_BOX, DataType.TYPE_LOCATION_SAMPLE, DataType.TYPE_LOCATION_TRACK, HealthDataTypes.TYPE_MENSTRUATION, DataType.TYPE_NUTRITION, DataType.TYPE_HYDRATION, DataType.AGGREGATE_NUTRITION_SUMMARY, HealthDataTypes.TYPE_OVULATION_TEST, HealthDataTypes.TYPE_OXYGEN_SATURATION, HealthDataTypes.AGGREGATE_OXYGEN_SATURATION_SUMMARY, DataType.TYPE_POWER_SAMPLE, DataType.AGGREGATE_POWER_SUMMARY, DataType.zzhbb, DataType.zzhba, DataType.TYPE_SPEED, DataType.AGGREGATE_SPEED_SUMMARY, DataType.TYPE_STEP_COUNT_CADENCE, DataType.zzhax, DataType.TYPE_STEP_COUNT_CUMULATIVE, DataType.TYPE_STEP_COUNT_DELTA, DataType.zzhav, HealthDataTypes.TYPE_VAGINAL_SPOTTING, DataType.TYPE_WEIGHT, DataType.AGGREGATE_WEIGHT_SUMMARY};
    private static DataType[] zzhbn = new DataType[]{HealthDataTypes.TYPE_BASAL_BODY_TEMPERATURE, HealthDataTypes.AGGREGATE_BASAL_BODY_TEMPERATURE_SUMMARY, HealthDataTypes.TYPE_BLOOD_GLUCOSE, HealthDataTypes.AGGREGATE_BLOOD_GLUCOSE_SUMMARY, HealthDataTypes.TYPE_BLOOD_PRESSURE, HealthDataTypes.AGGREGATE_BLOOD_PRESSURE_SUMMARY, HealthDataTypes.TYPE_BODY_TEMPERATURE, HealthDataTypes.AGGREGATE_BODY_TEMPERATURE_SUMMARY, HealthDataTypes.TYPE_CERVICAL_MUCUS, HealthDataTypes.TYPE_CERVICAL_POSITION, HealthDataTypes.TYPE_MENSTRUATION, HealthDataTypes.TYPE_OVULATION_TEST, HealthDataTypes.TYPE_OXYGEN_SATURATION, HealthDataTypes.AGGREGATE_OXYGEN_SATURATION_SUMMARY, HealthDataTypes.TYPE_VAGINAL_SPOTTING};

    /*
     * Enabled aggressive block sorting
     */
    public static DataType zzhf(String string2) {
        int n = -1;
        switch (string2.hashCode()) {
            case -1783842905: {
                if (!string2.equals("com.google.accelerometer")) break;
                n = 0;
                break;
            }
            case -164586193: {
                if (!string2.equals("com.google.activity.exercise")) break;
                n = 1;
                break;
            }
            case -1099695423: {
                if (!string2.equals("com.google.activity.sample")) break;
                n = 2;
                break;
            }
            case 269180370: {
                if (!string2.equals("com.google.activity.samples")) break;
                n = 3;
                break;
            }
            case 378060028: {
                if (!string2.equals("com.google.activity.segment")) break;
                n = 4;
                break;
            }
            case 841663855: {
                if (!string2.equals("com.google.activity.summary")) break;
                n = 5;
                break;
            }
            case 1483133089: {
                if (!string2.equals("com.google.body.temperature.basal")) break;
                n = 6;
                break;
            }
            case -1487055015: {
                if (!string2.equals("com.google.body.temperature.basal.summary")) break;
                n = 7;
                break;
            }
            case 682891187: {
                if (!string2.equals("com.google.body.fat.percentage")) break;
                n = 8;
                break;
            }
            case -217611775: {
                if (!string2.equals("com.google.blood_glucose")) break;
                n = 9;
                break;
            }
            case -1939429191: {
                if (!string2.equals("com.google.blood_glucose.summary")) break;
                n = 10;
                break;
            }
            case 936279698: {
                if (!string2.equals("com.google.blood_pressure")) break;
                n = 11;
                break;
            }
            case 53773386: {
                if (!string2.equals("com.google.blood_pressure.summary")) break;
                n = 12;
                break;
            }
            case 1111714923: {
                if (!string2.equals("com.google.body.fat.percentage.summary")) break;
                n = 13;
                break;
            }
            case -43729332: {
                if (!string2.equals("com.google.body.hip.circumference")) break;
                n = 14;
                break;
            }
            case 1674865156: {
                if (!string2.equals("com.google.body.hip.circumference.summary")) break;
                n = 15;
                break;
            }
            case -362418992: {
                if (!string2.equals("com.google.body.temperature")) break;
                n = 16;
                break;
            }
            case 2131809416: {
                if (!string2.equals("com.google.body.temperature.summary")) break;
                n = 17;
                break;
            }
            case 2484093: {
                if (!string2.equals("com.google.body.waist.circumference")) break;
                n = 18;
                break;
            }
            case 1819660853: {
                if (!string2.equals("com.google.body.waist.circumference.summary")) break;
                n = 19;
                break;
            }
            case -56824761: {
                if (!string2.equals("com.google.calories.bmr")) break;
                n = 20;
                break;
            }
            case 296250623: {
                if (!string2.equals("com.google.calories.bmr.summary")) break;
                n = 21;
                break;
            }
            case -2027664088: {
                if (!string2.equals("com.google.calories.consumed")) break;
                n = 22;
                break;
            }
            case 899666941: {
                if (!string2.equals("com.google.calories.expended")) break;
                n = 23;
                break;
            }
            case 1975902189: {
                if (!string2.equals("com.google.cervical_mucus")) break;
                n = 24;
                break;
            }
            case 1925848149: {
                if (!string2.equals("com.google.cervical_position")) break;
                n = 25;
                break;
            }
            case -900592674: {
                if (!string2.equals("com.google.cycling.pedaling.cadence")) break;
                n = 26;
                break;
            }
            case -922976890: {
                if (!string2.equals("com.google.cycling.pedaling.cumulative")) break;
                n = 27;
                break;
            }
            case 1524007137: {
                if (!string2.equals("com.google.cycling.wheel_revolution.cumulative")) break;
                n = 28;
                break;
            }
            case -2060095039: {
                if (!string2.equals("com.google.cycling.wheel_revolution.rpm")) break;
                n = 29;
                break;
            }
            case 1921738212: {
                if (!string2.equals("com.google.distance.cumulative")) break;
                n = 30;
                break;
            }
            case -1248818137: {
                if (!string2.equals("com.google.distance.delta")) break;
                n = 31;
                break;
            }
            case 1098265835: {
                if (!string2.equals("com.google.floor_change")) break;
                n = 32;
                break;
            }
            case -1466904157: {
                if (!string2.equals("com.google.floor_change.summary")) break;
                n = 33;
                break;
            }
            case -700668164: {
                if (!string2.equals("com.google.internal.goal")) break;
                n = 34;
                break;
            }
            case -98150574: {
                if (!string2.equals("com.google.heart_rate.bpm")) break;
                n = 35;
                break;
            }
            case -777285735: {
                if (!string2.equals("com.google.heart_rate.summary")) break;
                n = 36;
                break;
            }
            case -1091068721: {
                if (!string2.equals("com.google.height")) break;
                n = 37;
                break;
            }
            case -1431431801: {
                if (!string2.equals("com.google.height.summary")) break;
                n = 38;
                break;
            }
            case -2023954015: {
                if (!string2.equals("com.google.location.bounding_box")) break;
                n = 39;
                break;
            }
            case -1757812901: {
                if (!string2.equals("com.google.location.sample")) break;
                n = 40;
                break;
            }
            case -886569606: {
                if (!string2.equals("com.google.location.track")) break;
                n = 41;
                break;
            }
            case -1659958877: {
                if (!string2.equals("com.google.menstruation")) break;
                n = 42;
                break;
            }
            case 1633152752: {
                if (!string2.equals("com.google.nutrition")) break;
                n = 43;
                break;
            }
            case 946706510: {
                if (!string2.equals("com.google.hydration")) break;
                n = 44;
                break;
            }
            case -177293656: {
                if (!string2.equals("com.google.nutrition.summary")) break;
                n = 45;
                break;
            }
            case 1439932546: {
                if (!string2.equals("com.google.ovulation_test")) break;
                n = 46;
                break;
            }
            case 1404118825: {
                if (!string2.equals("com.google.oxygen_saturation")) break;
                n = 47;
                break;
            }
            case 2051843553: {
                if (!string2.equals("com.google.oxygen_saturation.summary")) break;
                n = 48;
                break;
            }
            case 529727579: {
                if (!string2.equals("com.google.power.sample")) break;
                n = 49;
                break;
            }
            case -185830635: {
                if (!string2.equals("com.google.power.summary")) break;
                n = 50;
                break;
            }
            case 634821360: {
                if (!string2.equals("com.google.sensor.const_rate_events")) break;
                n = 51;
                break;
            }
            case 295793957: {
                if (!string2.equals("com.google.sensor.events")) break;
                n = 52;
                break;
            }
            case 2053496735: {
                if (!string2.equals("com.google.speed")) break;
                n = 53;
                break;
            }
            case 1980033842: {
                if (!string2.equals("com.google.internal.session.debug")) break;
                n = 54;
                break;
            }
            case -1196687875: {
                if (!string2.equals("com.google.internal.session.v2")) break;
                n = 55;
                break;
            }
            case 877955159: {
                if (!string2.equals("com.google.speed.summary")) break;
                n = 56;
                break;
            }
            case 324760871: {
                if (!string2.equals("com.google.step_count.cadence")) break;
                n = 57;
                break;
            }
            case 657433501: {
                if (!string2.equals("com.google.step_count.cumulative")) break;
                n = 58;
                break;
            }
            case -1102520626: {
                if (!string2.equals("com.google.step_count.delta")) break;
                n = 59;
                break;
            }
            case -1063046895: {
                if (!string2.equals("com.google.step_length")) break;
                n = 60;
                break;
            }
            case 946938859: {
                if (!string2.equals("com.google.stride_model")) break;
                n = 61;
                break;
            }
            case 1214093899: {
                if (!string2.equals("com.google.vaginal_spotting")) break;
                n = 62;
                break;
            }
            case -661631456: {
                if (!string2.equals("com.google.weight")) break;
                n = 63;
                break;
            }
            case -424876584: {
                if (!string2.equals("com.google.weight.summary")) break;
                n = 64;
                break;
            }
        }
        switch (n) {
            default: {
                return null;
            }
            case 0: {
                return DataType.zzhaz;
            }
            case 1: {
                return DataType.TYPE_WORKOUT_EXERCISE;
            }
            case 2: {
                return DataType.TYPE_ACTIVITY_SAMPLE;
            }
            case 3: {
                return DataType.TYPE_ACTIVITY_SAMPLES;
            }
            case 4: {
                return DataType.TYPE_ACTIVITY_SEGMENT;
            }
            case 5: {
                return DataType.AGGREGATE_ACTIVITY_SUMMARY;
            }
            case 6: {
                return HealthDataTypes.TYPE_BASAL_BODY_TEMPERATURE;
            }
            case 7: {
                return HealthDataTypes.AGGREGATE_BASAL_BODY_TEMPERATURE_SUMMARY;
            }
            case 8: {
                return DataType.TYPE_BODY_FAT_PERCENTAGE;
            }
            case 9: {
                return HealthDataTypes.TYPE_BLOOD_GLUCOSE;
            }
            case 10: {
                return HealthDataTypes.AGGREGATE_BLOOD_GLUCOSE_SUMMARY;
            }
            case 11: {
                return HealthDataTypes.TYPE_BLOOD_PRESSURE;
            }
            case 12: {
                return HealthDataTypes.AGGREGATE_BLOOD_PRESSURE_SUMMARY;
            }
            case 13: {
                return DataType.AGGREGATE_BODY_FAT_PERCENTAGE_SUMMARY;
            }
            case 14: {
                return DataType.zzhbd;
            }
            case 15: {
                return DataType.zzhbf;
            }
            case 16: {
                return HealthDataTypes.TYPE_BODY_TEMPERATURE;
            }
            case 17: {
                return HealthDataTypes.AGGREGATE_BODY_TEMPERATURE_SUMMARY;
            }
            case 18: {
                return DataType.zzhbc;
            }
            case 19: {
                return DataType.zzhbg;
            }
            case 20: {
                return DataType.TYPE_BASAL_METABOLIC_RATE;
            }
            case 21: {
                return DataType.AGGREGATE_BASAL_METABOLIC_RATE_SUMMARY;
            }
            case 22: {
                return DataType.TYPE_CALORIES_CONSUMED;
            }
            case 23: {
                return DataType.TYPE_CALORIES_EXPENDED;
            }
            case 24: {
                return HealthDataTypes.TYPE_CERVICAL_MUCUS;
            }
            case 25: {
                return HealthDataTypes.TYPE_CERVICAL_POSITION;
            }
            case 26: {
                return DataType.TYPE_CYCLING_PEDALING_CADENCE;
            }
            case 27: {
                return DataType.TYPE_CYCLING_PEDALING_CUMULATIVE;
            }
            case 28: {
                return DataType.TYPE_CYCLING_WHEEL_REVOLUTION;
            }
            case 29: {
                return DataType.TYPE_CYCLING_WHEEL_RPM;
            }
            case 30: {
                return DataType.TYPE_DISTANCE_CUMULATIVE;
            }
            case 31: {
                return DataType.TYPE_DISTANCE_DELTA;
            }
            case 32: {
                return DataType.zzhay;
            }
            case 33: {
                return DataType.zzhbe;
            }
            case 34: {
                return DataType.zzhaw;
            }
            case 35: {
                return DataType.TYPE_HEART_RATE_BPM;
            }
            case 36: {
                return DataType.AGGREGATE_HEART_RATE_SUMMARY;
            }
            case 37: {
                return DataType.TYPE_HEIGHT;
            }
            case 38: {
                return DataType.AGGREGATE_HEIGHT_SUMMARY;
            }
            case 39: {
                return DataType.AGGREGATE_LOCATION_BOUNDING_BOX;
            }
            case 40: {
                return DataType.TYPE_LOCATION_SAMPLE;
            }
            case 41: {
                return DataType.TYPE_LOCATION_TRACK;
            }
            case 42: {
                return HealthDataTypes.TYPE_MENSTRUATION;
            }
            case 43: {
                return DataType.TYPE_NUTRITION;
            }
            case 44: {
                return DataType.TYPE_HYDRATION;
            }
            case 45: {
                return DataType.AGGREGATE_NUTRITION_SUMMARY;
            }
            case 46: {
                return HealthDataTypes.TYPE_OVULATION_TEST;
            }
            case 47: {
                return HealthDataTypes.TYPE_OXYGEN_SATURATION;
            }
            case 48: {
                return HealthDataTypes.AGGREGATE_OXYGEN_SATURATION_SUMMARY;
            }
            case 49: {
                return DataType.TYPE_POWER_SAMPLE;
            }
            case 50: {
                return DataType.AGGREGATE_POWER_SUMMARY;
            }
            case 51: {
                return DataType.zzhbb;
            }
            case 52: {
                return DataType.zzhba;
            }
            case 53: {
                return DataType.TYPE_SPEED;
            }
            case 54: {
                return DataType.zza.zzhbk;
            }
            case 55: {
                return DataType.zza.zzhbl;
            }
            case 56: {
                return DataType.AGGREGATE_SPEED_SUMMARY;
            }
            case 57: {
                return DataType.TYPE_STEP_COUNT_CADENCE;
            }
            case 58: {
                return DataType.TYPE_STEP_COUNT_CUMULATIVE;
            }
            case 59: {
                return DataType.TYPE_STEP_COUNT_DELTA;
            }
            case 60: {
                return DataType.zzhav;
            }
            case 61: {
                return DataType.zzhax;
            }
            case 62: {
                return HealthDataTypes.TYPE_VAGINAL_SPOTTING;
            }
            case 63: {
                return DataType.TYPE_WEIGHT;
            }
            case 64: 
        }
        return DataType.AGGREGATE_WEIGHT_SUMMARY;
    }
}

