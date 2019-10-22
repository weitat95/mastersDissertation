/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.database.Cursor
 */
package com.getqardio.android.device_related_services.fit;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import com.getqardio.android.CustomApplication;
import com.getqardio.android.datamodel.Settings;
import com.getqardio.android.datamodel.WeightMeasurement;
import com.getqardio.android.device_related_services.fit.GoogleFitUtils;
import com.getqardio.android.io.network.request.GoogleFitRequestHandler;
import com.getqardio.android.provider.DataHelper;
import com.getqardio.android.provider.MeasurementHelper;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.data.Value;
import com.google.android.gms.fitness.request.DataDeleteRequest;
import com.google.android.gms.internal.zzbfm;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import timber.log.Timber;

public class GoogleFitDataHelper {

    public static class Weight {
        private static TimeUnit DEFAULT_TIME_UNIT;
        private static DataType WEIGHT_DATA_TYPE;

        static {
            WEIGHT_DATA_TYPE = DataType.TYPE_WEIGHT;
            DEFAULT_TIME_UNIT = TimeUnit.MILLISECONDS;
        }

        private static DataPoint createDataPoint(DataSet zzbfm2, WeightMeasurement weightMeasurement) {
            zzbfm2 = ((DataSet)zzbfm2).createDataPoint();
            long l = weightMeasurement.measureDate.getTime();
            ((DataPoint)zzbfm2).setTimestamp(l, DEFAULT_TIME_UNIT);
            ((DataPoint)zzbfm2).setTimeInterval(l, l, DEFAULT_TIME_UNIT);
            ((DataPoint)zzbfm2).getValue(Field.FIELD_WEIGHT).setFloat(weightMeasurement.weight.floatValue());
            return zzbfm2;
        }

        public static void delete(Context context, GoogleApiClient googleApiClient, long l, long l2) {
            if (Weight.hasGoogleFitIntegration(context, l2)) {
                Weight.delete(googleApiClient, l);
            }
        }

        public static void delete(GoogleApiClient googleApiClient, long l) {
            Timber.d("delete from fit - %d -> %d", l, l + 1L);
            GoogleFitUtils.delete(googleApiClient, new DataDeleteRequest.Builder().setTimeInterval(l, l + 1L, DEFAULT_TIME_UNIT).addDataType(WEIGHT_DATA_TYPE).build());
        }

        private static Settings getSettings(Context context, Long l) {
            Settings settings = null;
            if (l != null) {
                settings = DataHelper.SettingsHelper.getSettings(context, l);
            }
            return settings;
        }

        public static boolean hasGoogleFitIntegration(Context object, Long l) {
            return (object = Weight.getSettings(object, l)) != null && object.allowIntegrationGoogleFit != false;
        }

        private static void insert(Context context, GoogleApiClient googleApiClient, Cursor cursor) {
            if (cursor != null && cursor.moveToFirst()) {
                ArrayList<WeightMeasurement> arrayList = new ArrayList<WeightMeasurement>(0);
                do {
                    arrayList.add(MeasurementHelper.Weight.parseMeasurement(cursor));
                    cursor.moveToNext();
                } while (!cursor.isAfterLast());
                Weight.insert(context, googleApiClient, arrayList);
            }
            return;
            finally {
                cursor.close();
            }
        }

        public static void insert(Context context, GoogleApiClient googleApiClient, Cursor cursor, Long l) {
            if (Weight.hasGoogleFitIntegration(context, l)) {
                Weight.insert(context, googleApiClient, cursor);
            }
        }

        public static void insert(Context context, GoogleApiClient googleApiClient, WeightMeasurement weightMeasurement, Long serializable) {
            if (Weight.hasGoogleFitIntegration(context, (Long)serializable)) {
                serializable = new ArrayList(1);
                ((ArrayList)serializable).add(weightMeasurement);
                Weight.insert(context, googleApiClient, (List<WeightMeasurement>)((Object)serializable));
            }
        }

        public static void insert(Context object, GoogleApiClient googleApiClient, List<WeightMeasurement> list) {
            object = DataSet.create(new DataSource.Builder().setAppPackageName((Context)object).setDataType(WEIGHT_DATA_TYPE).setName("Qardio-weight").setType(0).build());
            for (int i = 0; i < list.size(); ++i) {
                ((DataSet)object).add(Weight.createDataPoint((DataSet)object, list.get(i)));
            }
            GoogleFitUtils.insert(googleApiClient, (DataSet)object);
        }

        /*
         * Enabled aggressive block sorting
         */
        private static WeightMeasurement parseDataPoint(DataPoint dataPoint) {
            WeightMeasurement weightMeasurement = new WeightMeasurement();
            int n = weightMeasurement.syncStatus == null ? 1 : weightMeasurement.syncStatus | 1;
            weightMeasurement.syncStatus = n;
            weightMeasurement.userId = CustomApplication.getApplication().getCurrentUserId();
            weightMeasurement.weight = Float.valueOf(dataPoint.getValue(Field.FIELD_WEIGHT).asFloat());
            weightMeasurement.measurementSource = 3;
            weightMeasurement.deviceId = "0";
            long l = dataPoint.getStartTime(DEFAULT_TIME_UNIT);
            weightMeasurement.measureDate = new Date(l);
            weightMeasurement.measurementId = String.valueOf(l);
            return weightMeasurement;
        }

        public static List<WeightMeasurement> read(Context context, GoogleApiClient googleApiClient, long l) {
            List<WeightMeasurement> list = null;
            if (Weight.hasGoogleFitIntegration(context, l)) {
                list = Weight.read(googleApiClient);
            }
            return list;
        }

        public static List<WeightMeasurement> read(GoogleApiClient iterator) {
            ArrayList<WeightMeasurement> arrayList = new ArrayList<WeightMeasurement>();
            if ((iterator = GoogleFitUtils.read((GoogleApiClient)((Object)iterator), WEIGHT_DATA_TYPE)) != null) {
                iterator = iterator.iterator();
                while (iterator.hasNext()) {
                    Iterator<DataPoint> iterator2 = ((DataSet)iterator.next()).getDataPoints().iterator();
                    while (iterator2.hasNext()) {
                        arrayList.add(Weight.parseDataPoint(iterator2.next()));
                    }
                }
            }
            return arrayList;
        }

        public static void requestDeleteFromGoogleFit(Context context, long l, long l2) {
            context.startService(GoogleFitRequestHandler.createDeleteMeasurementIntent(context, l, l2));
        }

        public static void requestReadFromGoogleFit(Context context, long l) {
            context.startService(GoogleFitRequestHandler.createReadMeasurementsIntent(context, l));
        }

        public static void requestSaveToGoogleFit(Context context, long l) {
            context.startService(GoogleFitRequestHandler.createSaveMeasurementsIntent(context, l));
        }

        public static void requestSaveToGoogleFit(Context context, long l, WeightMeasurement weightMeasurement) {
            context.startService(GoogleFitRequestHandler.createSaveMeasurementIntent(context, weightMeasurement.measureDate.getTime(), l));
        }
    }

}

