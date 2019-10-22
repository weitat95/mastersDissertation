/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.database.Cursor
 *  android.os.Handler
 */
package com.getqardio.android.shealth;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import com.getqardio.android.CustomApplication;
import com.getqardio.android.datamodel.BPMeasurement;
import com.getqardio.android.datamodel.Profile;
import com.getqardio.android.datamodel.Settings;
import com.getqardio.android.datamodel.WeightMeasurement;
import com.getqardio.android.io.network.request.SHealthRequestHandler;
import com.getqardio.android.provider.DataHelper;
import com.getqardio.android.provider.MeasurementHelper;
import com.getqardio.android.shealth.ShealthUtils;
import com.getqardio.android.utils.DateUtils;
import com.getqardio.android.utils.HelperUtils;
import com.getqardio.android.utils.MetricUtils;
import com.samsung.android.sdk.healthdata.HealthData;
import com.samsung.android.sdk.healthdata.HealthDataResolver;
import com.samsung.android.sdk.healthdata.HealthDataStore;
import com.samsung.android.sdk.healthdata.HealthResultHolder;
import java.util.Date;
import timber.log.Timber;

public class ShealthDataHelper {
    private ShealthDataHelper() {
    }

    private static Settings getSettings(Context context) {
        Long l = CustomApplication.getApplication().getCurrentUserId();
        Settings settings = null;
        if (l != null) {
            settings = DataHelper.SettingsHelper.getSettings(context, l);
        }
        return settings;
    }

    public static class BpMeasurements {
        public static final String BP_HEALTH_DATA_TYPE = "com.samsung.health.blood_pressure";
        private static final String[] READ_MEASUREMENTS_PROJECTION = new String[]{"systolic", "diastolic", "pulse", "start_time", "time_offset"};

        private BpMeasurements() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static void addBpMeasurements(Context context, HealthDataStore healthDataStore, Cursor cursor) {
            if (!BpMeasurements.hasExportPermissions(context) || cursor == null) return;
            {
                try {
                    if (!cursor.moveToFirst()) return;
                    {
                        boolean bl;
                        do {
                            BpMeasurements.addMeasurementInternal(healthDataStore, MeasurementHelper.BloodPressure.parseMeasurement(cursor));
                            cursor.moveToNext();
                        } while (!(bl = cursor.isAfterLast()));
                        return;
                    }
                }
                finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                }
            }
        }

        public static void addMeasurement(Context context, HealthDataStore healthDataStore, BPMeasurement bPMeasurement) {
            if (BpMeasurements.hasExportPermissions(context)) {
                BpMeasurements.addMeasurement(healthDataStore, bPMeasurement);
            }
        }

        private static void addMeasurement(HealthDataStore healthDataStore, BPMeasurement bPMeasurement) {
            if (bPMeasurement.source == null || bPMeasurement.source.equals(0)) {
                BpMeasurements.addMeasurementInternal(healthDataStore, bPMeasurement);
            }
        }

        private static void addMeasurementInternal(HealthDataStore object, BPMeasurement object2) {
            if (object != null) {
                HealthDataResolver healthDataResolver = new HealthDataResolver((HealthDataStore)object, null);
                object2 = BpMeasurements.createHealthData((BPMeasurement)object2);
                ((HealthData)object2).setSourceDevice(ShealthUtils.getLocalDevice((HealthDataStore)object, "2ABF2888ARM"));
                object = BpMeasurements.updateMeasurement(healthDataResolver, (HealthData)object2);
                if (object != null && ((HealthResultHolder.BaseResult)object).getCount() == 0 && (object = ShealthUtils.insert(healthDataResolver, (HealthData)object2, BP_HEALTH_DATA_TYPE)) != null) {
                    Timber.d("Count of exported measurements into SHealth: %d", ((HealthResultHolder.BaseResult)object).getCount());
                }
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        private static HealthData createHealthData(BPMeasurement bPMeasurement) {
            HealthData healthData = new HealthData();
            long l = bPMeasurement.measureDate.getTime();
            healthData.putLong("create_time", l);
            healthData.putLong("start_time", l);
            double d = bPMeasurement.sys != null ? (double)bPMeasurement.sys.intValue() : 0.0;
            healthData.putDouble("systolic", d);
            d = bPMeasurement.dia != null ? (double)bPMeasurement.dia.intValue() : 0.0;
            healthData.putDouble("diastolic", d);
            healthData.putLong("time_offset", DateUtils.getTimeOffset(l));
            healthData.putDouble("mean", 0.0);
            int n = bPMeasurement.pulse != null ? bPMeasurement.pulse : 0;
            healthData.putInt("pulse", n);
            healthData.setSourceDevice(bPMeasurement.deviceId);
            return healthData;
        }

        public static void deleteMeasurement(Context context, HealthDataStore healthDataStore, long l) {
            if (BpMeasurements.hasExportPermissions(context)) {
                ShealthUtils.deleteMeasurement(healthDataStore, l, BP_HEALTH_DATA_TYPE);
            }
        }

        private static boolean hasExportPermissions(Context object) {
            return (object = ShealthDataHelper.getSettings(object)) != null && object.allowBpExportSHealth != false;
        }

        private static boolean hasImportPermissions(Context object) {
            return (object = ShealthDataHelper.getSettings(object)) != null && object.allowBpImportSHealth != false;
        }

        /*
         * Enabled aggressive block sorting
         */
        public static BPMeasurement parseMeasurement(Cursor cursor) {
            BPMeasurement bPMeasurement = new BPMeasurement();
            bPMeasurement.deviceId = "0001091";
            int n = bPMeasurement.syncStatus == null ? 1 : bPMeasurement.syncStatus | 1;
            bPMeasurement.syncStatus = n;
            bPMeasurement.sys = HelperUtils.getInt(cursor, "systolic", (Integer)0);
            bPMeasurement.dia = HelperUtils.getInt(cursor, "diastolic", (Integer)0);
            bPMeasurement.pulse = HelperUtils.getInt(cursor, "pulse", null);
            bPMeasurement.irregularHeartBeat = Boolean.FALSE;
            bPMeasurement.source = 2;
            bPMeasurement.userId = CustomApplication.getApplication().getCurrentUserId();
            bPMeasurement.timezone = DateUtils.createUtcOffsetString(true, true, HelperUtils.getInt(cursor, "time_offset", (Integer)0));
            bPMeasurement.measureDate = new Date(HelperUtils.getLong(cursor, "start_time", (Long)0L));
            return bPMeasurement;
        }

        public static void readMeasurements(Context context, HealthDataStore healthDataStore, Long l) {
            if (BpMeasurements.hasImportPermissions(context)) {
                BpMeasurements.readMeasurementsInternal(context, healthDataStore, l);
            }
        }

        private static void readMeasurementsInternal(Context context, HealthDataResolver healthDataResolver, HealthDataResolver.Filter filter) {
            try {
                healthDataResolver = ShealthUtils.read(healthDataResolver, filter, BP_HEALTH_DATA_TYPE, READ_MEASUREMENTS_PROJECTION);
                Timber.d("Count of imported measurements from SHealth: %d", healthDataResolver.getCount());
                BpMeasurements.saveMeasurements(context, (Cursor)healthDataResolver);
                return;
            }
            catch (Exception exception) {
                Timber.e(exception, "Cannot read data from SHealth", new Object[0]);
                return;
            }
        }

        private static void readMeasurementsInternal(Context context, HealthDataStore healthDataStore, Long l) {
            if (healthDataStore != null) {
                HealthDataResolver healthDataResolver = new HealthDataResolver(healthDataStore, null);
                if (l != null && l != -1L) {
                    BpMeasurements.readMeasurementsInternal(context, healthDataResolver, HealthDataResolver.Filter.and(HealthDataResolver.Filter.not(HealthDataResolver.Filter.eq("deviceuuid", ShealthUtils.getLocalDevice(healthDataStore, "2ABF2888ARM"))), HealthDataResolver.Filter.lessThan("systolic", 250), HealthDataResolver.Filter.greaterThan("systolic", 40), HealthDataResolver.Filter.lessThan("diastolic", 250), HealthDataResolver.Filter.greaterThan("diastolic", 40)));
                }
            }
        }

        public static void requestReadMeasurements(Context context, long l) {
            context.startService(SHealthRequestHandler.createReadBpMeasurementsIntent(context, l));
        }

        public static void requestSaveMeasurement(Context context, long l, BPMeasurement bPMeasurement) {
            context.startService(SHealthRequestHandler.createSaveBpMeasurementIntent(context, bPMeasurement.measureDate.getTime(), l));
        }

        public static void requestSaveMeasurements(Context context, long l) {
            context.startService(SHealthRequestHandler.createSaveBpMeasurementsIntent(context, l));
        }

        private static void saveMeasurements(Context context, Cursor cursor) {
            try {
                BpMeasurements.saveOrUpdateMeasurement(context, cursor);
                return;
            }
            finally {
                cursor.close();
            }
        }

        private static void saveOrUpdateMeasurement(Context context, Cursor cursor) {
            Long l = CustomApplication.getApplication().getCurrentUserId();
            if (l != null && cursor != null && cursor.moveToFirst()) {
                do {
                    BpMeasurements.saveOrUpdateMeasurement(context, l, BpMeasurements.parseMeasurement(cursor));
                    cursor.moveToNext();
                } while (!cursor.isAfterLast());
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        private static void saveOrUpdateMeasurement(Context context, Long l, BPMeasurement bPMeasurement) {
            Long l2 = bPMeasurement.userId;
            BPMeasurement bPMeasurement2 = null;
            if (l2 != null) {
                bPMeasurement2 = MeasurementHelper.BloodPressure.getMeasurementByTimestamp(context, bPMeasurement.userId, bPMeasurement.measureDate.getTime());
            }
            if (bPMeasurement2 == null) {
                MeasurementHelper.BloodPressure.addMeasurement(context, l, bPMeasurement, true);
                return;
            } else {
                if (bPMeasurement.isBloodPressureEqual(bPMeasurement2)) return;
                {
                    MeasurementHelper.BloodPressure.updateMeasurementBloodPressure(context, l, bPMeasurement);
                    return;
                }
            }
        }

        private static HealthResultHolder.BaseResult updateMeasurement(HealthDataResolver healthDataResolver, HealthData healthData) {
            return ShealthUtils.update(healthDataResolver, healthData, HealthDataResolver.Filter.eq("start_time", healthData.getLong("start_time")), BP_HEALTH_DATA_TYPE);
        }
    }

    public static class WeightMeasurements {
        private static final String[] READ_WEIGHT_MEASUREMENTS_PROJECTION = new String[]{"weight", "create_time", "start_time", "time_offset", "body_fat", "muscle_mass", "comment"};
        public static final String WEIGHT_HEALTH_DATA_TYPE = "com.samsung.health.weight";

        private WeightMeasurements() {
        }

        public static void addWeightMeasurement(Context context, HealthDataStore healthDataStore, WeightMeasurement weightMeasurement) {
            if (WeightMeasurements.hasExportPermissions(context) && (weightMeasurement.measurementSource == null || weightMeasurement.measurementSource.equals(0) || weightMeasurement.measurementSource.equals(4))) {
                WeightMeasurements.addWeightMeasurementInternal(context, healthDataStore, weightMeasurement);
            }
        }

        private static void addWeightMeasurementInternal(Context object, HealthDataStore object2, WeightMeasurement weightMeasurement) {
            if (object2 != null) {
                HealthDataResolver healthDataResolver = new HealthDataResolver((HealthDataStore)object2, null);
                object = WeightMeasurements.createHealthData((Context)object, weightMeasurement);
                ((HealthData)object).setSourceDevice(ShealthUtils.getLocalDevice((HealthDataStore)object2, "000000000000"));
                object2 = WeightMeasurements.updateWeightMeasurement(healthDataResolver, (HealthData)object);
                if ((object2 == null || ((HealthResultHolder.BaseResult)object2).getCount() == 0) && (object = ShealthUtils.insert(healthDataResolver, (HealthData)object, WEIGHT_HEALTH_DATA_TYPE)) != null) {
                    Timber.d("Count of exported measurements into SHealth: %d", ((HealthResultHolder.BaseResult)object).getCount());
                }
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static void addWeightMeasurements(Context context, HealthDataStore healthDataStore, Cursor cursor) {
            if (!WeightMeasurements.hasExportPermissions(context) || cursor == null) return;
            {
                try {
                    if (!cursor.moveToFirst()) return;
                    {
                        boolean bl;
                        do {
                            WeightMeasurements.addWeightMeasurement(context, healthDataStore, MeasurementHelper.Weight.parseMeasurement(cursor));
                            cursor.moveToNext();
                        } while (!(bl = cursor.isAfterLast()));
                        return;
                    }
                }
                finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                }
            }
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         */
        public static HealthData createHealthData(Context object, WeightMeasurement weightMeasurement) {
            void var1_2;
            Profile profile = DataHelper.ProfileHelper.getProfileForUser(object, CustomApplication.getApplication().getCurrentUserId());
            HealthData healthData = new HealthData();
            long l = var1_2.measureDate.getTime();
            healthData.putLong("create_time", l);
            healthData.putLong("start_time", l);
            double d = var1_2.weight != null ? (double)var1_2.weight.floatValue() : 0.0;
            healthData.putDouble("weight", d);
            if (var1_2.fat != null) {
                healthData.putDouble("body_fat", var1_2.fat.intValue());
            }
            if (var1_2.muscle != null) {
                healthData.putDouble("muscle_mass", var1_2.muscle.intValue());
            }
            if (var1_2.note != null) {
                healthData.putString("comment", var1_2.note);
            }
            if (profile.height != null) {
                healthData.putDouble("height", profile.height.floatValue());
            }
            healthData.putLong("time_offset", DateUtils.getTimeOffset(l));
            return healthData;
        }

        public static void deleteWeightMeasurement(Context context, HealthDataStore healthDataStore, long l) {
            if (WeightMeasurements.hasExportPermissions(context)) {
                ShealthUtils.deleteMeasurement(healthDataStore, l, WEIGHT_HEALTH_DATA_TYPE);
            }
        }

        private static boolean hasExportPermissions(Context object) {
            return (object = ShealthDataHelper.getSettings(object)) != null && object.allowWeightExportSHealth != false;
        }

        private static boolean hasImportPermissions(Context object) {
            return (object = ShealthDataHelper.getSettings(object)) != null && object.allowWeightImportSHealth != false;
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         */
        public static WeightMeasurement parseWeightMeasurement(Context object, Cursor cursor) {
            void var1_2;
            Profile profile = DataHelper.ProfileHelper.getProfileForUser(object, CustomApplication.getApplication().getCurrentUserId());
            WeightMeasurement weightMeasurement = new WeightMeasurement();
            if (profile != null && profile.height != null) {
                if (profile.heightUnit == 1) {
                    profile.height = Float.valueOf(MetricUtils.convertHeight(1, 0, profile.height.floatValue()));
                }
                weightMeasurement.height = Math.round(profile.height.floatValue());
            }
            int n = weightMeasurement.syncStatus == null ? 1 : weightMeasurement.syncStatus | 1;
            weightMeasurement.syncStatus = n;
            n = HelperUtils.getInt((Cursor)var1_2, "time_offset", (Integer)0);
            weightMeasurement.userId = CustomApplication.getApplication().getCurrentUserId();
            weightMeasurement.weight = HelperUtils.getFloat((Cursor)var1_2, "weight", Float.valueOf(0.0f));
            weightMeasurement.fat = Math.round(HelperUtils.getFloat((Cursor)var1_2, "body_fat", Float.valueOf(0.0f)).floatValue());
            weightMeasurement.muscle = Math.round(HelperUtils.getFloat((Cursor)var1_2, "muscle_mass", Float.valueOf(0.0f)).floatValue());
            weightMeasurement.note = HelperUtils.getString((Cursor)var1_2, "comment", "");
            weightMeasurement.timezone = DateUtils.createUtcOffsetString(true, true, n);
            weightMeasurement.measurementSource = 2;
            weightMeasurement.deviceId = "0";
            long l = HelperUtils.getLong((Cursor)var1_2, "start_time", (Long)0L);
            weightMeasurement.measureDate = new Date(l);
            weightMeasurement.measurementId = String.valueOf(l);
            return weightMeasurement;
        }

        public static void readWeightMeasurements(Context context, HealthDataStore healthDataStore, Long l) {
            if (WeightMeasurements.hasImportPermissions(context)) {
                WeightMeasurements.readWeightMeasurementsInternal(context, healthDataStore, l);
            }
        }

        private static void readWeightMeasurementsInternal(Context context, HealthDataStore healthDataStore, Long l) {
            if (healthDataStore != null) {
                HealthDataResolver healthDataResolver = new HealthDataResolver(healthDataStore, null);
                if (l != null && l != -1L && (healthDataStore = ShealthUtils.read(healthDataResolver, HealthDataResolver.Filter.eq("deviceuuid", ShealthUtils.getLocalDevice(healthDataStore, "000000000000")), WEIGHT_HEALTH_DATA_TYPE, READ_WEIGHT_MEASUREMENTS_PROJECTION)) != null) {
                    WeightMeasurements.saveWeightMeasurements(context, (Cursor)healthDataStore);
                }
            }
        }

        public static void requestReadWeightMeasurements(Context context, Long l) {
            context.startService(SHealthRequestHandler.createReadWeightMeasurementsIntent(context, l));
        }

        public static void requestSaveWeightMeasurement(Context context, long l, WeightMeasurement weightMeasurement) {
            context.startService(SHealthRequestHandler.createSaveWeightMeasurementIntent(context, weightMeasurement.measureDate.getTime(), l));
        }

        public static void requestSaveWeightMeasurements(Context context, Long l) {
            context.startService(SHealthRequestHandler.createSaveWeightMeasurementsIntent(context, l));
        }

        private static void saveOrUpdateWeightMeasurement(Context context, Cursor cursor) {
            Long l = CustomApplication.getApplication().getCurrentUserId();
            if (l != null && cursor != null && cursor.moveToFirst()) {
                do {
                    WeightMeasurements.saveOrUpdateWeightMeasurement(context, l, WeightMeasurements.parseWeightMeasurement(context, cursor));
                    cursor.moveToNext();
                } while (!cursor.isAfterLast());
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        private static void saveOrUpdateWeightMeasurement(Context context, Long l, WeightMeasurement weightMeasurement) {
            Long l2 = weightMeasurement.userId;
            WeightMeasurement weightMeasurement2 = null;
            if (l2 != null) {
                weightMeasurement2 = MeasurementHelper.Weight.getMeasurementByTimestamp(context, weightMeasurement.userId, weightMeasurement.measureDate.getTime());
            }
            if (weightMeasurement2 == null) {
                MeasurementHelper.Weight.addMeasurement(context, l, weightMeasurement, true);
                return;
            } else {
                if (weightMeasurement.isWeightEqual(weightMeasurement2)) return;
                {
                    MeasurementHelper.Weight.updateMeasurementWeight(context, l, weightMeasurement);
                    return;
                }
            }
        }

        private static void saveWeightMeasurements(Context context, Cursor cursor) {
            try {
                WeightMeasurements.saveOrUpdateWeightMeasurement(context, cursor);
                return;
            }
            finally {
                cursor.close();
            }
        }

        private static HealthResultHolder.BaseResult updateWeightMeasurement(HealthDataResolver healthDataResolver, HealthData healthData) {
            return ShealthUtils.update(healthDataResolver, healthData, HealthDataResolver.Filter.eq("start_time", healthData.getLong("start_time")), WEIGHT_HEALTH_DATA_TYPE);
        }
    }

}

