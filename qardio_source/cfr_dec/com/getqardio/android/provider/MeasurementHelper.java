/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.ContentProviderOperation
 *  android.content.ContentProviderOperation$Builder
 *  android.content.ContentProviderResult
 *  android.content.ContentResolver
 *  android.content.ContentValues
 *  android.content.Context
 *  android.content.CursorLoader
 *  android.content.Intent
 *  android.content.OperationApplicationException
 *  android.database.Cursor
 *  android.net.Uri
 *  android.net.Uri$Builder
 *  android.os.RemoteException
 */
package com.getqardio.android.provider;

import android.content.ComponentName;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import com.getqardio.android.datamodel.AverageBPMeasurement;
import com.getqardio.android.datamodel.AveragePulsMeasurement;
import com.getqardio.android.datamodel.AverageWeightMeasurement;
import com.getqardio.android.datamodel.BPMeasurement;
import com.getqardio.android.datamodel.ClaimMeasurement;
import com.getqardio.android.datamodel.MeasurementsHistory;
import com.getqardio.android.datamodel.MinMaxDate;
import com.getqardio.android.datamodel.MinMaxMeasurement;
import com.getqardio.android.datamodel.Profile;
import com.getqardio.android.datamodel.SyncStatusMetadata;
import com.getqardio.android.datamodel.VisitorMeasurement;
import com.getqardio.android.datamodel.WeightMeasurement;
import com.getqardio.android.io.network.request.BPMeasurementsRequestHandler;
import com.getqardio.android.io.network.request.ClaimMeasurementsRequestHandler;
import com.getqardio.android.io.network.request.WeightMeasurementsRequestHandler;
import com.getqardio.android.provider.AppProvideContract;
import com.getqardio.android.provider.DataHelper;
import com.getqardio.android.utils.DateUtils;
import com.getqardio.android.utils.HelperUtils;
import com.getqardio.android.utils.MetricUtils;
import com.getqardio.android.utils.QardioBaseUtils;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import timber.log.Timber;

public abstract class MeasurementHelper {

    public static abstract class BloodPressure {
        public static final String[] AVERAGE_MEASUREMENT_PROJECTION;
        static final String[] COUNT_FOR_SYNC_PROJECTION;
        public static final String[] DELETING_PROJECTION;
        public static final String[] DETECT_LOCATION_TAG_PROJECTION;
        static final String[] MEASUREMENTS_DATE_BOUNDS_PROJECTION;
        public static final String[] MEASUREMENTS_LIST_PROJECTION;
        public static final String[] MEASUREMENTS_LOCATIONS_PROJECTION;
        static final String[] MEASUREMENTS_STATUSES_PROJECTION;
        public static final String[] MEASUREMENT_TIMESTAMPS_PROJECTION;
        public static final String[] MIN_MAX_AVG_BP_MEASUREMENTS_PROJECTION;
        public static final String[] PERIODIC_BP_MEASUREMENTS_PROJECTION;
        static final String[] SHEALTH_TILE_PROJECTION;
        public static final String[] UPDATE_LOCATION_TAG_PROJECTION;

        static {
            MEASUREMENTS_DATE_BOUNDS_PROJECTION = new String[]{"min(measure_date)", "max(measure_date)"};
            COUNT_FOR_SYNC_PROJECTION = new String[]{"_id"};
            MEASUREMENTS_LIST_PROJECTION = new String[]{"_id", "puls", "dia", "sys", "irregular_heart_beat", "measure_date", "tag", "note", "source"};
            AVERAGE_MEASUREMENT_PROJECTION = new String[]{"dia", "sys", "puls"};
            DELETING_PROJECTION = new String[]{"_id", "measure_date"};
            MEASUREMENTS_STATUSES_PROJECTION = new String[]{"measure_date", "sync_status"};
            PERIODIC_BP_MEASUREMENTS_PROJECTION = new String[]{"measure_date", "avg(dia) as dia", "avg(sys) as sys", "avg(puls) as puls"};
            MIN_MAX_AVG_BP_MEASUREMENTS_PROJECTION = new String[]{"measure_date", "avg(dia) as dia", "avg(sys) as sys", "avg(puls) as puls", "min(dia)", "min(sys)", "min(puls)", "max(dia)", "max(sys)", "max(puls)"};
            MEASUREMENTS_LOCATIONS_PROJECTION = new String[]{"_id", "puls", "dia", "sys", "measure_date", "longitude", "latitude", "tag"};
            DETECT_LOCATION_TAG_PROJECTION = new String[]{"longitude", "latitude", "tag"};
            UPDATE_LOCATION_TAG_PROJECTION = new String[]{"measure_date", "longitude", "latitude", "tag"};
            MEASUREMENT_TIMESTAMPS_PROJECTION = new String[]{"measure_date"};
            SHEALTH_TILE_PROJECTION = new String[]{"sys", "dia", "puls", "measure_date"};
        }

        /*
         * Enabled aggressive block sorting
         */
        public static void addMeasurement(Context context, long l, BPMeasurement bPMeasurement, boolean bl) {
            Uri uri = BloodPressure.buildMeasurementsUri(l);
            int n = bPMeasurement.syncStatus == null ? 1 : bPMeasurement.syncStatus | 1;
            bPMeasurement.syncStatus = n;
            ContentValues contentValues = new ContentValues(14);
            HelperUtils.put(contentValues, "user_id", bPMeasurement.userId);
            HelperUtils.put(contentValues, "sync_status", bPMeasurement.syncStatus);
            HelperUtils.put(contentValues, "puls", bPMeasurement.pulse);
            HelperUtils.put(contentValues, "dia", bPMeasurement.dia);
            HelperUtils.put(contentValues, "sys", bPMeasurement.sys);
            HelperUtils.put(contentValues, "irregular_heart_beat", bPMeasurement.irregularHeartBeat);
            HelperUtils.put(contentValues, "measure_date", bPMeasurement.measureDate);
            HelperUtils.put(contentValues, "note", bPMeasurement.note);
            HelperUtils.put(contentValues, "device_id", bPMeasurement.deviceId);
            HelperUtils.put(contentValues, "timezone", bPMeasurement.timezone);
            HelperUtils.put(contentValues, "latitude", bPMeasurement.latitude);
            HelperUtils.put(contentValues, "longitude", bPMeasurement.longitude);
            HelperUtils.put(contentValues, "tag", bPMeasurement.tag);
            HelperUtils.put(contentValues, "source", bPMeasurement.source);
            context.getContentResolver().insert(uri, contentValues);
            if (bl) {
                BloodPressure.requestBPMeasurementsSync(context, l);
            }
        }

        private static Uri buildDailyMeasurementsUri(long l) {
            return AppProvideContract.BASE_CONTENT_URI.buildUpon().appendPath("users").appendPath(Long.toString(l)).appendPath("bp_measurements").appendPath("daily").build();
        }

        private static Uri buildMeasurementUri(long l, long l2) {
            return AppProvideContract.BASE_CONTENT_URI.buildUpon().appendPath("users").appendPath(Long.toString(l)).appendPath("bp_measurements").appendPath(Long.toString(l2)).build();
        }

        public static Uri buildMeasurementsUri(long l) {
            return AppProvideContract.BASE_CONTENT_URI.buildUpon().appendPath("users").appendPath(Long.toString(l)).appendPath("bp_measurements").build();
        }

        private static Uri buildMonthlyMeasurementsUri(long l) {
            return AppProvideContract.BASE_CONTENT_URI.buildUpon().appendPath("users").appendPath(Long.toString(l)).appendPath("bp_measurements").appendPath("monthly").build();
        }

        private static Uri buildVisitorMeasurementsUri(long l) {
            return AppProvideContract.BASE_CONTENT_URI.buildUpon().appendPath("users").appendPath(Long.toString(l)).appendPath("visitor_measurements").build();
        }

        private static Uri buildVisitorMeasurementsUri(long l, long l2) {
            return AppProvideContract.BASE_CONTENT_URI.buildUpon().appendPath("users").appendPath(Long.toString(l)).appendPath("visitor_measurements").appendPath(Long.toString(l2)).build();
        }

        private static Uri buildWeeklyMeasurementsUri(long l) {
            return AppProvideContract.BASE_CONTENT_URI.buildUpon().appendPath("users").appendPath(Long.toString(l)).appendPath("bp_measurements").appendPath("weekly").build();
        }

        /*
         * Enabled aggressive block sorting
         */
        public static void changeMeasurementNote(Context context, long l, long l2, String string2, boolean bl) {
            Uri uri = BloodPressure.buildMeasurementsUri(l);
            Integer n = BloodPressure.getMeasurementStatus(context, l, l2);
            int n2 = n == null ? 1 : n | 1;
            n = new ContentValues(2);
            n.put("note", string2);
            n.put("sync_status", Integer.valueOf(n2));
            n2 = context.getContentResolver().update(uri, (ContentValues)n, "measure_date=?", new String[]{Long.toString(l2)});
            if (bl && n2 > 0) {
                BloodPressure.requestBPMeasurementsSync(context, l);
            }
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        public static void changeMeasurementsTag(Context var0, long var1_1, Collection<Long> var3_2, int var4_6) {
            var6_7 = BloodPressure.buildMeasurementsUri(var1_1);
            var7_8 = new ArrayList<ContentProviderOperation>(var3_2.size());
            var3_2 = var3_2.iterator();
            while (var3_2.hasNext()) {
                var8_10 = (Long)var3_2.next();
                var9_11 = BloodPressure.getMeasurementStatus(var0, var1_1, var8_10);
                var5_9 = var9_11 == null ? 1 : var9_11 | 1;
                var9_11 = new ContentValues(2);
                var9_11.put("tag", Integer.valueOf(var4_6));
                var9_11.put("sync_status", Integer.valueOf(var5_9));
                var7_8.add(ContentProviderOperation.newUpdate((Uri)var6_7).withSelection("measure_date=?", new String[]{Long.toString(var8_10)}).withValues((ContentValues)var9_11).build());
            }
            try {
                var0.getContentResolver().applyBatch("com.getqardio.android", var7_8);
            }
            catch (RemoteException var3_3) {}
            ** GOTO lbl-1000
            catch (OperationApplicationException var3_5) {}
lbl-1000:
            // 2 sources
            {
                Timber.e((Throwable)var3_4);
            }
            BloodPressure.requestBPMeasurementsSync(var0, var1_1);
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        public static void changeMeasurementsTag(Context var0, long var1_1, List<BPMeasurement> var3_2, int var4_6) {
            var6_7 = BloodPressure.buildMeasurementsUri(var1_1);
            var7_8 = new ArrayList<ContentProviderOperation>(var3_2.size());
            var3_2 = var3_2.iterator();
            while (var3_2.hasNext()) {
                var8_10 = (BPMeasurement)var3_2.next();
                var9_11 = BloodPressure.getMeasurementStatus(var0, var1_1, var8_10.measureDate.getTime());
                var5_9 = var9_11 == null ? 1 : var9_11 | 1;
                var9_11 = new ContentValues(2);
                var9_11.put("tag", Integer.valueOf(var4_6));
                var9_11.put("sync_status", Integer.valueOf(var5_9));
                var7_8.add(ContentProviderOperation.newUpdate((Uri)var6_7).withSelection("measure_date=?", new String[]{Long.toString(var8_10.measureDate.getTime())}).withValues((ContentValues)var9_11).build());
            }
            try {
                var0.getContentResolver().applyBatch("com.getqardio.android", var7_8);
            }
            catch (RemoteException var3_3) {}
            ** GOTO lbl-1000
            catch (OperationApplicationException var3_5) {}
lbl-1000:
            // 2 sources
            {
                Timber.e((Throwable)var3_4);
            }
            BloodPressure.requestBPMeasurementsSync(var0, var1_1);
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        public static void changeSyncStatuses(Context var0, long var1_4, Collection<SyncStatusMetadata> var3_5) {
            var4_6 = new ArrayList<ContentProviderOperation>(var3_5.size());
            var3_5 = var3_5.iterator();
            while (var3_5.hasNext()) {
                var7_9 = (SyncStatusMetadata)var3_5.next();
                var5_7 = BloodPressure.buildMeasurementUri(var1_4, var7_9.id);
                var6_8 = new ContentValues(1);
                var6_8.put("sync_status", Integer.valueOf(var7_9.syncStatus));
                var7_9 = Integer.toString(var7_9.revision);
                var4_6.add(ContentProviderOperation.newUpdate((Uri)var5_7).withValues(var6_8).withSelection("revision<=?", new String[]{var7_9}).build());
            }
            try {
                var0.getContentResolver().applyBatch("com.getqardio.android", var4_6);
                return;
            }
            catch (RemoteException var0_1) {}
            ** GOTO lbl-1000
            catch (OperationApplicationException var0_3) {}
lbl-1000:
            // 2 sources
            {
                Timber.e((Throwable)var0_2);
                return;
            }
        }

        public static boolean deleteMeasurement(Context context, long l, long l2) {
            Uri uri = BloodPressure.buildMeasurementsUri(l);
            ContentValues contentValues = new ContentValues(1);
            contentValues.put("sync_status", Integer.valueOf(4));
            int n = context.getContentResolver().update(uri, contentValues, "measure_date=?", new String[]{Long.toString(l2)});
            if (n > 0) {
                BloodPressure.requestBPMeasurementsSync(context, l);
            }
            return n > 0;
        }

        public static int deleteMeasurementFromCache(Context context, long l, long l2) {
            Uri uri = BloodPressure.buildMeasurementUri(l, l2);
            return context.getContentResolver().delete(uri, null, null);
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        public static void deleteMeasurementsFromCache(Context var0, long var1_4, Collection<Long> var3_5) {
            var4_6 = new ArrayList<ContentProviderOperation>(var3_5.size());
            var3_5 = var3_5.iterator();
            while (var3_5.hasNext()) {
                var4_6.add(ContentProviderOperation.newDelete((Uri)BloodPressure.buildMeasurementUri(var1_4, (Long)var3_5.next())).build());
            }
            try {
                var0.getContentResolver().applyBatch("com.getqardio.android", var4_6);
                return;
            }
            catch (RemoteException var0_1) {}
            ** GOTO lbl-1000
            catch (OperationApplicationException var0_3) {}
lbl-1000:
            // 2 sources
            {
                Timber.e((Throwable)var0_2);
                return;
            }
        }

        public static int deleteVisitorMeasurement(Context context, long l, long l2) {
            Uri uri = BloodPressure.buildVisitorMeasurementsUri(l, l2);
            return context.getContentResolver().delete(uri, null, null);
        }

        public static CursorLoader getDailyMeasurementsLoader(Context context, long l, String[] arrstring) {
            return BloodPressure.getPeriodicMeasurementsLoader(context, BloodPressure.buildDailyMeasurementsUri(l), arrstring);
        }

        /*
         * Loose catch block
         * WARNING - void declaration
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        public static BPMeasurement getLastMeasurement(Context object, long l) {
            Uri uri;
            void var3_7;
            block3: {
                void var1_3;
                Object var3_4 = null;
                uri = BloodPressure.buildMeasurementsUri((long)var1_3);
                String string2 = "(sync_status | " + 3 + " = " + 3 + ")";
                uri = object.getContentResolver().query(uri, SHEALTH_TILE_PROJECTION, string2, null, "measure_date DESC");
                if (uri != null) {
                    BPMeasurement bPMeasurement;
                    if (!uri.moveToFirst()) break block3;
                    BPMeasurement bPMeasurement2 = bPMeasurement = BloodPressure.parseMeasurement((Cursor)uri);
                    if (uri == null) return var3_7;
                    uri.close();
                    BPMeasurement bPMeasurement3 = bPMeasurement;
                    return var3_7;
                }
            }
            if (uri == null) return var3_7;
            uri.close();
            return null;
            catch (Throwable throwable) {
                if (uri == null) throw throwable;
                uri.close();
                throw throwable;
            }
        }

        /*
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        public static BPMeasurement getMeasurementByTimestamp(Context object, Long l, long l2) {
            Uri uri;
            Throwable throwable;
            block15: {
                throwable = null;
                uri = BloodPressure.buildMeasurementsUri(l);
                l = null;
                uri = object.getContentResolver().query(uri, null, "measure_date=?", new String[]{Long.toString(l2)}, null);
                object = l;
                if (uri != null) {
                    object = l;
                    if (!uri.moveToFirst()) break block15;
                    object = BloodPressure.parseMeasurement((Cursor)uri);
                }
            }
            if (uri == null) {
            }
            if (!false) {
                uri.close();
                return object;
            }
            uri.close();
            catch (Throwable throwable2) {
                try {
                    throw throwable2;
                }
                catch (Throwable throwable3) {
                    Throwable throwable4;
                    block16: {
                        throwable = throwable2;
                        throwable4 = throwable3;
                        break block16;
                        finally {
                            return object;
                        }
                        catch (Throwable throwable5) {}
                    }
                    if (uri == null) throw throwable4;
                    if (throwable == null) {
                        uri.close();
                        throw throwable4;
                    }
                    try {
                        uri.close();
                    }
                    finally {
                        throw throwable4;
                    }
                }
            }
        }

        public static List<Calendar> getMeasurementDates(Cursor cursor) {
            ArrayList<Calendar> arrayList = new ArrayList<Calendar>();
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(HelperUtils.getLong(cursor, "measure_date", (Long)0L));
                arrayList.add(calendar);
                cursor.moveToNext();
            }
            return arrayList;
        }

        /*
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        static Integer getMeasurementStatus(Context object, long l, long l2) {
            Throwable throwable;
            Object var5_8;
            Uri uri;
            block16: {
                throwable = null;
                var5_8 = null;
                uri = BloodPressure.buildMeasurementsUri(l);
                object = object.getContentResolver();
                String string2 = Long.toString(l2);
                if ((uri = object.query(uri, new String[]{"sync_status"}, "measure_date=?", new String[]{string2}, null)) != null) {
                    block17: {
                        if (!uri.moveToFirst()) break block16;
                        object = HelperUtils.getInt((Cursor)uri, "sync_status", null);
                        if (uri == null) return object;
                        if (!false) break block17;
                        uri.close();
                        return object;
                    }
                    uri.close();
                    return object;
                }
            }
            object = var5_8;
            if (uri == null) return object;
            if (!false) {
                uri.close();
                return null;
            }
            try {
                uri.close();
            }
            finally {
                return null;
            }
            catch (Throwable throwable2) {
                try {
                    throw throwable2;
                }
                catch (Throwable throwable3) {
                    Throwable throwable4;
                    block18: {
                        throwable = throwable2;
                        throwable4 = throwable3;
                        break block18;
                        catch (Throwable throwable5) {
                            return object;
                        }
                        catch (Throwable throwable6) {}
                    }
                    if (uri == null) throw throwable4;
                    if (throwable == null) {
                        uri.close();
                        throw throwable4;
                    }
                    try {
                        uri.close();
                    }
                    finally {
                        throw throwable4;
                    }
                }
            }
        }

        public static CursorLoader getMeasurementsByTagLoader(Context context, long l, int n, String[] arrstring) {
            return new CursorLoader(context, BloodPressure.buildMeasurementsUri(l), arrstring, "(sync_status | " + 3 + " = " + 3 + ") AND " + "tag" + "=?", new String[]{Integer.toString(n)}, "measure_date DESC");
        }

        /*
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        public static int getMeasurementsCountForSync(Context object, long l) {
            int n;
            Object var4_4 = null;
            Uri uri = BloodPressure.buildMeasurementsUri(l);
            uri = object.getContentResolver().query(uri, COUNT_FOR_SYNC_PROJECTION, "(sync_status & 1 = 1) OR (sync_status & 4 = 4)", null, null);
            if (uri != null) {
                object = var4_4;
                n = uri.getCount();
            } else {
                n = 0;
            }
            if (uri == null) {
            }
            if (!false) {
                uri.close();
                return n;
            }
            uri.close();
            catch (Throwable throwable) {
                object = throwable;
                try {
                    throw throwable;
                }
                catch (Throwable throwable2) {
                    if (uri == null) throw throwable2;
                    if (object == null) {
                        uri.close();
                        throw throwable2;
                    }
                    try {
                        uri.close();
                    }
                    finally {
                        throw throwable2;
                    }
                }
            }
        }

        public static Cursor getMeasurementsForDelete(Context context, long l, String[] arrstring) {
            Uri uri = BloodPressure.buildMeasurementsUri(l);
            return context.getContentResolver().query(uri, arrstring, "(sync_status & 4 = 4)", null, null);
        }

        public static Cursor getMeasurementsForSHealth(Context context, long l) {
            Uri uri = BloodPressure.buildMeasurementsUri(l);
            String string2 = "(source=0) AND (sync_status | " + 3 + " = " + 3 + ")";
            return context.getContentResolver().query(uri, null, string2, null, null);
        }

        public static Cursor getMeasurementsForUpload(Context context, long l, String[] arrstring) {
            Uri uri = BloodPressure.buildMeasurementsUri(l);
            return context.getContentResolver().query(uri, arrstring, "(sync_status & 1 = 1)", null, null);
        }

        public static CursorLoader getMeasurementsLoader(Context context, long l, String[] arrstring) {
            return new CursorLoader(context, BloodPressure.buildMeasurementsUri(l), arrstring, "(sync_status | " + 3 + " = " + 3 + ")", null, "measure_date DESC");
        }

        public static CursorLoader getMeasurementsLoaderByDate(Context context, long l, long l2, String[] arrstring) {
            return new CursorLoader(context, BloodPressure.buildMeasurementsUri(l), arrstring, "measure_date=?", new String[]{Long.toString(l2)}, null);
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        static Map<Date, Integer> getMeasurementsStatuses(Context var0, long var1_5) {
            var4_6 = null;
            var3_7 /* !! */  = BloodPressure.buildMeasurementsUri(var1_5);
            var5_11 = var0.getContentResolver().query(var3_7 /* !! */ , BloodPressure.MEASUREMENTS_STATUSES_PROJECTION, "sync_status != 0", null, null);
            if (var5_11 == null) {
                if (var5_11 == null) {
                }
                if (!false) {
                    var5_11.close();
                    return null;
                }
                var5_11.close();
            }
            try {
                var0 = new HashMap<K, V>(var5_11.getCount());
                var5_11.moveToFirst();
                while (!var5_11.isAfterLast()) {
                    var3_7 /* !! */  = BloodPressure.parseMeasurement(var5_11);
                    if (var3_7 /* !! */ .measureDate != null && var3_7 /* !! */ .syncStatus != null) {
                        var0.put(var3_7 /* !! */ .measureDate, var3_7 /* !! */ .syncStatus);
                    }
                    var5_11.moveToNext();
                }
                ** GOTO lbl32
            }
            catch (Throwable var0_1) {
                try {
                    throw var0_1;
                }
                catch (Throwable var3_8) {
                    block20: {
                        var4_6 = var0_1;
                        var0_2 = var3_8;
                        break block20;
lbl32:
                        // 1 sources
                        if (var5_11 == null) return var0;
                        if (!false) {
                            var5_11.close();
                            return var0;
                        }
                        try {
                            var5_11.close();
                            return var0;
                        }
                        catch (Throwable var3_9) {
                            return var0;
                        }
                        finally {
                            return null;
                        }
                        catch (Throwable var0_4) {}
                    }
                    if (var5_11 == null) throw var0_2;
                    if (var4_6 != null) {
                        var5_11.close();
                    }
                    var5_11.close();
                    throw var0_2;
                    finally {
                        throw var0_2;
                    }
                }
            }
        }

        public static Cursor getMeasurementsWithLocation(Context context, long l, String[] arrstring) {
            Uri uri = BloodPressure.buildMeasurementsUri(l);
            String string2 = "(sync_status | " + 3 + " = " + 3 + ") AND " + "latitude" + " NOT NULL AND " + "longitude" + " NOT NULL";
            return context.getContentResolver().query(uri, arrstring, string2, null, null);
        }

        public static CursorLoader getMeasurementsWithLocationLoader(Context context, long l, String[] arrstring) {
            return new CursorLoader(context, BloodPressure.buildMeasurementsUri(l), arrstring, "(sync_status | " + 3 + " = " + 3 + ") AND " + "latitude" + " NOT NULL AND " + "longitude" + " NOT NULL AND NOT (" + "latitude" + "=0 AND " + "longitude" + "=0)", null, "measure_date DESC");
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static MinMaxDate getMinMaxMeasurementsDate(Context var0, long var1_5) {
            block17: {
                var6_6 = null;
                var5_7 = BloodPressure.buildMeasurementsUri(var1_5);
                var7_11 = var0 /* !! */ .getContentResolver().query(var5_7, BloodPressure.MEASUREMENTS_DATE_BOUNDS_PROJECTION, null, null, null);
                if (var7_11 != null) {
                    block18: {
                        var7_11.moveToFirst();
                        if (var7_11.isAfterLast()) break block17;
                        var3_12 = var7_11.getColumnIndex("min(measure_date)");
                        var4_13 = var7_11.getColumnIndex("max(measure_date)");
                        if (var7_11.isNull(var3_12) || var7_11.isNull(var4_13)) break block17;
                        var0 /* !! */  = new MinMaxDate();
                        var0 /* !! */ .minDate = new Date(var7_11.getLong(var3_12));
                        var0 /* !! */ .maxDate = new Date(var7_11.getLong(var4_13));
                        if (var7_11 == null) break block18;
                        if (!false) break;
                        var7_11.close();
                    }
                    var7_11.close();
                    return var0 /* !! */ ;
                }
            }
            if (var7_11 == null) return null;
            if (!false) {
                var7_11.close();
                return null;
            }
            var7_11.close();
            return null;
            catch (Throwable var0_1) {
                try {
                    throw var0_1;
                }
                catch (Throwable var5_8) {
                    var6_6 = var0_1;
                    var0_2 = var5_8;
lbl38:
                    // 2 sources
                    do {
                        if (var7_11 == null) throw var0_2;
                        if (var6_6 == null) {
                            var7_11.close();
                            throw var0_2;
                        }
                        try {
                            var7_11.close();
                        }
                        finally {
                            throw var0_2;
                        }
                        break;
                    } while (true);
                }
            }
            catch (Throwable var0_3) {
                return null;
            }
            finally {
                return var0 /* !! */ ;
            }
            catch (Throwable var0_4) {
                ** continue;
            }
        }

        public static CursorLoader getMonthlyMeasurementsLoader(Context context, long l, String[] arrstring) {
            return BloodPressure.getPeriodicMeasurementsLoader(context, BloodPressure.buildMonthlyMeasurementsUri(l), arrstring);
        }

        private static CursorLoader getPeriodicMeasurementsLoader(Context context, Uri uri, String[] arrstring) {
            return new CursorLoader(context, uri, arrstring, "(sync_status | " + 3 + " = " + 3 + ")", null, "measure_date ASC");
        }

        public static VisitorMeasurement getVisitorMeasurement(Context object, long l, int n) {
            Uri uri;
            block5: {
                uri = BloodPressure.buildVisitorMeasurementsUri(l, n);
                Object var4_5 = null;
                uri = object.getContentResolver().query(uri, null, null, null, null);
                object = var4_5;
                if (uri != null) {
                    object = var4_5;
                    if (!uri.moveToFirst()) break block5;
                    object = BloodPressure.parseVisitorMeasurement((Cursor)uri);
                }
            }
            return object;
            finally {
                if (uri != null) {
                    uri.close();
                }
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static List<VisitorMeasurement> getVisitorMeasurementsList(Context context, long l) {
            Uri uri = BloodPressure.buildVisitorMeasurementsUri(l);
            ArrayList<VisitorMeasurement> arrayList = new ArrayList<VisitorMeasurement>();
            if ((context = context.getContentResolver().query(uri, null, null, null, null)) == null) return arrayList;
            {
                try {
                    if (!context.moveToFirst()) return arrayList;
                    {
                        boolean bl;
                        do {
                            arrayList.add(BloodPressure.parseVisitorMeasurement((Cursor)context));
                            context.moveToNext();
                        } while (!(bl = context.isAfterLast()));
                        return arrayList;
                    }
                }
                finally {
                    if (context != null) {
                        context.close();
                    }
                }
            }
        }

        public static CursorLoader getWeeklyMeasurementsLoader(Context context, long l, String[] arrstring) {
            return BloodPressure.getPeriodicMeasurementsLoader(context, BloodPressure.buildWeeklyMeasurementsUri(l), arrstring);
        }

        public static AverageBPMeasurement parseAverageBPMeasurement(Cursor cursor) {
            AverageBPMeasurement averageBPMeasurement = new AverageBPMeasurement();
            averageBPMeasurement.measureDate = HelperUtils.getDate(cursor, "measure_date", null);
            averageBPMeasurement.sys = HelperUtils.getFloat(cursor, "sys", null);
            averageBPMeasurement.dia = HelperUtils.getFloat(cursor, "dia", null);
            return averageBPMeasurement;
        }

        public static AveragePulsMeasurement parseAveragePulsMeasurement(Cursor cursor) {
            AveragePulsMeasurement averagePulsMeasurement = new AveragePulsMeasurement();
            averagePulsMeasurement.measureDate = HelperUtils.getDate(cursor, "measure_date", null);
            averagePulsMeasurement.puls = HelperUtils.getFloat(cursor, "puls", null);
            return averagePulsMeasurement;
        }

        public static BPMeasurement parseMeasurement(Cursor cursor) {
            BPMeasurement bPMeasurement = new BPMeasurement();
            bPMeasurement._id = HelperUtils.getLong(cursor, "_id", null);
            bPMeasurement.userId = HelperUtils.getLong(cursor, "user_id", null);
            bPMeasurement.syncStatus = HelperUtils.getInt(cursor, "sync_status", null);
            bPMeasurement.revision = HelperUtils.getInt(cursor, "revision", (Integer)0);
            bPMeasurement.pulse = HelperUtils.getInt(cursor, "puls", (Integer)0);
            bPMeasurement.dia = HelperUtils.getInt(cursor, "dia", (Integer)0);
            bPMeasurement.sys = HelperUtils.getInt(cursor, "sys", (Integer)0);
            bPMeasurement.irregularHeartBeat = HelperUtils.getBoolean(cursor, "irregular_heart_beat", null);
            bPMeasurement.measureDate = HelperUtils.getDate(cursor, "measure_date", null);
            bPMeasurement.note = HelperUtils.getString(cursor, "note", null);
            bPMeasurement.deviceId = HelperUtils.getString(cursor, "device_id", null);
            bPMeasurement.timezone = HelperUtils.getString(cursor, "timezone", null);
            bPMeasurement.latitude = HelperUtils.getDouble(cursor, "latitude", bPMeasurement.latitude);
            bPMeasurement.longitude = HelperUtils.getDouble(cursor, "longitude", bPMeasurement.longitude);
            bPMeasurement.tag = HelperUtils.getInt(cursor, "tag", bPMeasurement.tag);
            bPMeasurement.source = HelperUtils.getInt(cursor, "source", bPMeasurement.source);
            return bPMeasurement;
        }

        public static MinMaxMeasurement parseMinMaxMeasurement(Cursor cursor) {
            MinMaxMeasurement minMaxMeasurement = new MinMaxMeasurement();
            minMaxMeasurement.measureDate = HelperUtils.getDate(cursor, "measure_date", null);
            minMaxMeasurement.avgSys = HelperUtils.getFloat(cursor, "sys", null);
            minMaxMeasurement.avgDia = HelperUtils.getFloat(cursor, "dia", null);
            minMaxMeasurement.avgPulse = HelperUtils.getFloat(cursor, "puls", null);
            minMaxMeasurement.minSys = HelperUtils.getFloat(cursor, "min(sys)", null);
            minMaxMeasurement.minDia = HelperUtils.getFloat(cursor, "min(dia)", null);
            minMaxMeasurement.minPulse = HelperUtils.getFloat(cursor, "min(puls)", null);
            minMaxMeasurement.maxSys = HelperUtils.getFloat(cursor, "max(sys)", null);
            minMaxMeasurement.maxDia = HelperUtils.getFloat(cursor, "max(dia)", null);
            minMaxMeasurement.maxPulse = HelperUtils.getFloat(cursor, "max(puls)", null);
            return minMaxMeasurement;
        }

        static VisitorMeasurement parseVisitorMeasurement(Cursor cursor) {
            VisitorMeasurement visitorMeasurement = new VisitorMeasurement();
            visitorMeasurement._id = HelperUtils.getLong(cursor, "_id", null);
            visitorMeasurement.userId = HelperUtils.getLong(cursor, "user_id", null);
            visitorMeasurement.pulse = HelperUtils.getInt(cursor, "puls", null);
            visitorMeasurement.dia = HelperUtils.getInt(cursor, "dia", null);
            visitorMeasurement.sys = HelperUtils.getInt(cursor, "sys", null);
            visitorMeasurement.irregularHeartBeat = HelperUtils.getBoolean(cursor, "irregular_heart_beat", null);
            visitorMeasurement.measureDate = HelperUtils.getDate(cursor, "measure_date", null);
            visitorMeasurement.deviceId = HelperUtils.getString(cursor, "device_id", null);
            visitorMeasurement.timezone = HelperUtils.getString(cursor, "timezone", null);
            visitorMeasurement.memberName = HelperUtils.getString(cursor, "member_name", null);
            return visitorMeasurement;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        public static void replaceMeasurements(Context var0, long var1_4, List<BPMeasurement> var3_5) {
            var4_6 = BloodPressure.buildMeasurementsUri(var1_4);
            var5_7 = new ArrayList<ContentProviderOperation>(var3_5.size() + 1);
            var5_7.add(ContentProviderOperation.newDelete((Uri)var4_6).build());
            var6_8 = BloodPressure.getMeasurementsStatuses(var0, var1_4);
            var3_5 = var3_5.iterator();
            while (var3_5.hasNext()) {
                var7_9 = (BPMeasurement)var3_5.next();
                var8_10 = new ContentValues(13);
                HelperUtils.put(var8_10, "puls", var7_9.pulse);
                HelperUtils.put(var8_10, "dia", var7_9.dia);
                HelperUtils.put(var8_10, "sys", var7_9.sys);
                HelperUtils.put(var8_10, "irregular_heart_beat", var7_9.irregularHeartBeat);
                HelperUtils.put(var8_10, "measure_date", var7_9.measureDate);
                HelperUtils.put(var8_10, "note", var7_9.note);
                HelperUtils.put(var8_10, "device_id", var7_9.deviceId);
                HelperUtils.put(var8_10, "timezone", var7_9.timezone);
                HelperUtils.put(var8_10, "latitude", var7_9.latitude);
                HelperUtils.put(var8_10, "longitude", var7_9.longitude);
                HelperUtils.put(var8_10, "tag", var7_9.tag);
                HelperUtils.put(var8_10, "source", var7_9.source);
                if (var6_8 != null && var6_8.containsKey(var7_9.measureDate)) {
                    HelperUtils.put(var8_10, "sync_status", var6_8.get(var7_9.measureDate));
                }
                var5_7.add(ContentProviderOperation.newInsert((Uri)var4_6).withValues(var8_10).build());
            }
            try {
                var0.getContentResolver().applyBatch("com.getqardio.android", var5_7);
                return;
            }
            catch (RemoteException var0_1) {}
            ** GOTO lbl-1000
            catch (OperationApplicationException var0_3) {}
lbl-1000:
            // 2 sources
            {
                Timber.e((Throwable)var0_2);
                return;
            }
        }

        public static void requestBPMeasurementsSync(Context context, long l) {
            context.startService(BPMeasurementsRequestHandler.createSyncBPMeasurementsIntent(context, l));
        }

        public static void requestBPMeasurementsUpdate(Context context, long l) {
            context.startService(BPMeasurementsRequestHandler.createGetBPMeasurementsIntent(context, l));
        }

        static void requestSaveOldVisitoreMeasurements(Context context, long l) {
            context.startService(BPMeasurementsRequestHandler.createSaveOldVisitorMeasurementsIntent(context, l));
        }

        public static void requestSaveVisitorMeasurement(Context context, long l, int n, String string2) {
            context.startService(BPMeasurementsRequestHandler.createSaveVisitorMeasurementIntent(context, l, n, string2));
        }

        public static int saveVisitorMeasurement(Context context, long l, BPMeasurement bPMeasurement) {
            Uri uri = BloodPressure.buildVisitorMeasurementsUri(l);
            ContentValues contentValues = new ContentValues(8);
            HelperUtils.put(contentValues, "user_id", l);
            HelperUtils.put(contentValues, "puls", bPMeasurement.pulse);
            HelperUtils.put(contentValues, "dia", bPMeasurement.dia);
            HelperUtils.put(contentValues, "sys", bPMeasurement.sys);
            HelperUtils.put(contentValues, "irregular_heart_beat", bPMeasurement.irregularHeartBeat);
            HelperUtils.put(contentValues, "measure_date", bPMeasurement.measureDate);
            HelperUtils.put(contentValues, "device_id", bPMeasurement.deviceId);
            HelperUtils.put(contentValues, "timezone", bPMeasurement.timezone);
            context = context.getContentResolver().insert(uri, contentValues);
            int n = 0;
            if (context != null) {
                n = Integer.parseInt(context.getLastPathSegment());
            }
            return n;
            finally {
                Timber.e("Cannot save visitor measurement", new Object[0]);
            }
        }

        public static void updateMeasurementBloodPressure(Context context, Long l, BPMeasurement bPMeasurement) {
            l = BloodPressure.buildMeasurementsUri(l);
            ContentValues contentValues = new ContentValues(3);
            contentValues.put("sys", bPMeasurement.sys);
            contentValues.put("dia", bPMeasurement.dia);
            contentValues.put("puls", bPMeasurement.pulse);
            long l2 = bPMeasurement.measureDate.getTime();
            context.getContentResolver().update((Uri)l, contentValues, "measure_date=?", new String[]{String.valueOf(l2)});
        }

        public static int updateVisitorMeasurement(Context context, long l, long l2, String string2) {
            Uri uri = BloodPressure.buildVisitorMeasurementsUri(l, l2);
            ContentValues contentValues = new ContentValues(1);
            contentValues.put("member_name", string2);
            return context.getContentResolver().update(uri, contentValues, null, null);
        }
    }

    public static class Claim {
        public static final String[] CLAIM_MEASUREMENTS_PROJECTION = new String[]{"_id", "fullName", "time", "data1"};

        public static Uri buildClaimMeasurementUri(long l) {
            return AppProvideContract.BASE_CONTENT_URI.buildUpon().appendPath("users").appendPath(Long.toString(l)).appendPath("claim_measurements").build();
        }

        private static Uri buildClaimMeasurementUri(long l, long l2) {
            return AppProvideContract.BASE_CONTENT_URI.buildUpon().appendPath("users").appendPath(Long.toString(l)).appendPath("claim_measurements").appendPath(Long.toString(l2)).build();
        }

        public static boolean claimMeasurement(Context context, long l, int n, String string2) {
            Uri uri = Claim.buildClaimMeasurementUri(l, n);
            ContentValues contentValues = new ContentValues(1);
            contentValues.put("sync_status", Integer.valueOf(1));
            contentValues.put("fullName", string2);
            n = context.getContentResolver().update(uri, contentValues, "_id=?", new String[]{Long.toString(n)});
            if (n > 0) {
                Claim.requestClaimMeasurementsUpdate(context, l, DataHelper.DeviceIdHelper.getDeviceId(context, l));
            }
            return n > 0;
        }

        public static void deleteClaimMeasurements(Context context, long l) {
            Uri uri = Claim.buildClaimMeasurementUri(l);
            context.getContentResolver().delete(uri, null, null);
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        public static void deleteClaimMeasurementsFromCache(Context var0, long var1_4, Collection<Integer> var3_5) {
            var4_6 = new ArrayList<ContentProviderOperation>(var3_5.size());
            var3_5 = var3_5.iterator();
            while (var3_5.hasNext()) {
                var4_6.add(ContentProviderOperation.newDelete((Uri)Claim.buildClaimMeasurementUri(var1_4, ((Integer)var3_5.next()).intValue())).build());
            }
            try {
                var0.getContentResolver().applyBatch("com.getqardio.android", var4_6);
                return;
            }
            catch (RemoteException var0_1) {}
            ** GOTO lbl-1000
            catch (OperationApplicationException var0_3) {}
lbl-1000:
            // 2 sources
            {
                Timber.e((Throwable)var0_2);
                return;
            }
        }

        public static boolean deleteMeasurement(Context context, long l, long l2) {
            Uri uri = Claim.buildClaimMeasurementUri(l);
            ContentValues contentValues = new ContentValues(1);
            contentValues.put("sync_status", Integer.valueOf(4));
            int n = context.getContentResolver().update(uri, contentValues, "time=?", new String[]{Long.toString(l2)});
            if (n > 0) {
                Claim.requestClaimMeasurementsUpdate(context, l, DataHelper.DeviceIdHelper.getDeviceId(context, l));
            }
            return n > 0;
        }

        public static CursorLoader getAllClaimMeasurements(Context context, long l, String[] arrstring) {
            String string2 = "sync_status | " + 1 + " = " + 1;
            return new CursorLoader(context, Claim.buildClaimMeasurementUri(l), arrstring, string2, null, "time DESC");
        }

        public static Cursor getAllClaimMeasurementsCursor(Context context, long l, String[] arrstring) {
            String string2 = "sync_status | " + 0 + " = " + 0;
            return context.getContentResolver().query(Claim.buildClaimMeasurementUri(l), arrstring, string2, null, "time DESC");
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        static ClaimMeasurement getClaimedMeasurementByMeasurementId(Context object, long l, String string2) {
            ClaimMeasurement claimMeasurement;
            ClaimMeasurement claimMeasurement2;
            Uri uri;
            block3: {
                void var1_3;
                claimMeasurement = null;
                uri = Claim.buildClaimMeasurementUri((long)var1_3);
                uri = object.getContentResolver().query(uri, null, "measurement_id=?", new String[]{claimMeasurement2}, null);
                if (uri != null) {
                    ClaimMeasurement claimMeasurement3;
                    try {
                        if (!uri.moveToNext()) break block3;
                        claimMeasurement2 = claimMeasurement3 = Claim.parseClaimMeasurement((Cursor)uri);
                        if (uri == null) return claimMeasurement2;
                    }
                    catch (Throwable throwable) {
                        if (uri == null) throw throwable;
                        uri.close();
                        throw throwable;
                    }
                    uri.close();
                    return claimMeasurement3;
                }
            }
            claimMeasurement2 = claimMeasurement;
            if (uri == null) return claimMeasurement2;
            uri.close();
            return null;
        }

        public static Cursor getMeasurementsForClaim(Context context, long l, String[] arrstring) {
            Uri uri = Claim.buildClaimMeasurementUri(l);
            return context.getContentResolver().query(uri, arrstring, "(sync_status & 1 = 1)", null, null);
        }

        public static Cursor getMeasurementsForDelete(Context context, long l, String[] arrstring) {
            Uri uri = Claim.buildClaimMeasurementUri(l);
            return context.getContentResolver().query(uri, arrstring, "(sync_status & 4 = 4)", null, null);
        }

        public static ClaimMeasurement parseClaimMeasurement(Cursor cursor) {
            ClaimMeasurement claimMeasurement = new ClaimMeasurement();
            claimMeasurement.id = HelperUtils.getInt(cursor, "_id", (Integer)0);
            claimMeasurement.fullname = HelperUtils.getString(cursor, "fullName", "");
            claimMeasurement.measureDate = new Date(HelperUtils.getLong(cursor, "time", (Long)0L));
            claimMeasurement.measurementId = HelperUtils.getString(cursor, "measurement_id", "");
            claimMeasurement.data1 = HelperUtils.getString(cursor, "data1", "");
            return claimMeasurement;
        }

        public static void removeAllClaimMeasurements(Context context, long l) {
            Uri uri = Claim.buildClaimMeasurementUri(l);
            context.getContentResolver().delete(uri, null, null);
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        public static void replaceClaimMeasurements(Context var0, long var1_4, List<ClaimMeasurement> var3_5) {
            var4_6 = Claim.buildClaimMeasurementUri(var1_4);
            var5_7 = new ArrayList<ContentProviderOperation>(var3_5.size() + 1);
            var3_5 = var3_5.iterator();
            while (var3_5.hasNext()) {
                var6_8 = (ClaimMeasurement)var3_5.next();
                var7_9 = new ContentValues(18);
                var7_9.put("_id", Integer.valueOf(var6_8.id));
                var7_9.put("measurement_id", var6_8.measurementId);
                var7_9.put("userId", var6_8.userId);
                var7_9.put("fullName", var6_8.fullname);
                var7_9.put("deviceId", var6_8.deviceId);
                var7_9.put("data1", var6_8.data1);
                var7_9.put("data2", var6_8.data2);
                var7_9.put("data3", var6_8.data3);
                var7_9.put("note", var6_8.note);
                var7_9.put("sync_status", var6_8.syncStatus);
                var7_9.put("memberId", var6_8.memberId);
                var7_9.put("irregularHb", var6_8.irregularHeartBeat);
                var7_9.put("longitude", var6_8.longitude);
                var7_9.put("latitude", var6_8.latitude);
                var7_9.put("source", var6_8.source);
                var7_9.put("timezone", var6_8.timezone);
                var7_9.put("tag", var6_8.tag);
                var7_9.put("time", Long.valueOf(var6_8.measureDate.getTime()));
                var5_7.add(ContentProviderOperation.newInsert((Uri)var4_6).withValues(var7_9).build());
            }
            try {
                var0.getContentResolver().applyBatch("com.getqardio.android", var5_7);
                return;
            }
            catch (RemoteException var0_1) {}
            ** GOTO lbl-1000
            catch (OperationApplicationException var0_3) {}
lbl-1000:
            // 2 sources
            {
                Timber.e((Throwable)var0_2);
                return;
            }
        }

        public static void requestClaimMeasurementsUpdate(Context context, long l, String string2) {
            context.startService(ClaimMeasurementsRequestHandler.createGetClaimMeasurementsIntent(context, l, string2));
        }
    }

    public static abstract class History {
        private static Uri buildMeasurementHistoryUri() {
            return AppProvideContract.BASE_CONTENT_URI.buildUpon().appendPath("measurements_history").build();
        }

        private static Uri buildMeasurementHistoryUri(long l) {
            return AppProvideContract.BASE_CONTENT_URI.buildUpon().appendPath("measurements_history").appendPath(Long.toString(l)).build();
        }

        public static int deleteMeasurementHistory(Context context, long l) {
            Uri uri = History.buildMeasurementHistoryUri(l);
            return context.getContentResolver().delete(uri, null, null);
        }

        public static MeasurementsHistory getMeasurementHistory(Context object, long l) {
            Cursor cursor;
            block5: {
                cursor = object.getContentResolver().query(History.buildMeasurementHistoryUri(l), null, null, null, null);
                Object var3_4 = null;
                object = var3_4;
                if (cursor != null) {
                    object = var3_4;
                    if (!cursor.moveToFirst()) break block5;
                    object = History.parseMeasurementHistory(cursor);
                }
            }
            return object;
            finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static List<MeasurementsHistory> getMeasurementsHistoryList(Context context) {
            ArrayList<MeasurementsHistory> arrayList = new ArrayList<MeasurementsHistory>();
            if ((context = context.getContentResolver().query(History.buildMeasurementHistoryUri(), null, null, null, null)) == null) return arrayList;
            {
                try {
                    if (!context.moveToFirst()) return arrayList;
                    {
                        boolean bl;
                        do {
                            arrayList.add(History.parseMeasurementHistory((Cursor)context));
                            context.moveToNext();
                        } while (!(bl = context.isAfterLast()));
                        return arrayList;
                    }
                }
                finally {
                    if (context != null) {
                        context.close();
                    }
                }
            }
        }

        public static long insertMeasurementHistory(Context context, MeasurementsHistory measurementsHistory) {
            long l = -1L;
            Uri uri = History.buildMeasurementHistoryUri();
            ContentValues contentValues = new ContentValues(4);
            contentValues.put("target_email", measurementsHistory.targetEmail);
            contentValues.put("target_name", measurementsHistory.targetName);
            contentValues.put("user_id", Long.valueOf(measurementsHistory.userId));
            contentValues.put("note", measurementsHistory.note);
            context = context.getContentResolver().insert(uri, contentValues);
            if (context != null) {
                l = Long.valueOf(context.getLastPathSegment());
            }
            return l;
        }

        static MeasurementsHistory parseMeasurementHistory(Cursor cursor) {
            MeasurementsHistory measurementsHistory = new MeasurementsHistory();
            measurementsHistory._id = HelperUtils.getLong(cursor, "_id", (Long)0L);
            measurementsHistory.targetEmail = HelperUtils.getString(cursor, "target_email", "");
            measurementsHistory.targetName = HelperUtils.getString(cursor, "target_name", "");
            measurementsHistory.userId = HelperUtils.getLong(cursor, "user_id", (Long)0L);
            measurementsHistory.note = HelperUtils.getString(cursor, "note", "");
            return measurementsHistory;
        }
    }

    public static abstract class Weight {
        static final String[] COUNT_FOR_SYNC_PROJECTION;
        public static final String[] DELETING_PROJECTION;
        static final String[] MEASUREMENTS_STATUSES_PROJECTION;
        public static final String[] PERIODIC_WEIGHT_MEASUREMENTS_PROJECTION;
        public static final String[] PREGNANCY_GALLERY_PROJECTION;
        public static final String[] SHEALTH_TILE_PROJECTION;
        static final String[] WEIGHT_PROJECTION;

        static {
            PREGNANCY_GALLERY_PROJECTION = new String[]{"measure_date", "weight"};
            PERIODIC_WEIGHT_MEASUREMENTS_PROJECTION = new String[]{"measure_date", "avg(weight) as weight", "avg(bmi) as bmi", "avg(fat) as fat"};
            MEASUREMENTS_STATUSES_PROJECTION = new String[]{"measure_date", "sync_status"};
            COUNT_FOR_SYNC_PROJECTION = new String[]{"_id"};
            DELETING_PROJECTION = new String[]{"_id", "measure_date"};
            WEIGHT_PROJECTION = new String[]{"weight"};
            SHEALTH_TILE_PROJECTION = new String[]{"weight", "measure_date"};
        }

        /*
         * Enabled aggressive block sorting
         */
        public static void addMeasurement(Context context, long l, WeightMeasurement weightMeasurement, boolean bl) {
            Uri uri = Weight.buildMeasurementsUri(l);
            int n = weightMeasurement.syncStatus == null ? 1 : weightMeasurement.syncStatus | 1;
            weightMeasurement.syncStatus = n;
            ContentValues contentValues = new ContentValues(24);
            HelperUtils.put(contentValues, "user_id", weightMeasurement.userId);
            HelperUtils.put(contentValues, "sync_status", weightMeasurement.syncStatus);
            HelperUtils.put(contentValues, "weight", weightMeasurement.weight);
            HelperUtils.put(contentValues, "full_name", weightMeasurement.fullName);
            HelperUtils.put(contentValues, "device_id", weightMeasurement.deviceId);
            HelperUtils.put(contentValues, "height", weightMeasurement.height);
            HelperUtils.put(contentValues, "sex", weightMeasurement.sex);
            HelperUtils.put(contentValues, "battery", weightMeasurement.battery);
            HelperUtils.put(contentValues, "age", weightMeasurement.age);
            HelperUtils.put(contentValues, "fat", weightMeasurement.fat);
            HelperUtils.put(contentValues, "mt", weightMeasurement.muscle);
            HelperUtils.put(contentValues, "tbw", weightMeasurement.water);
            HelperUtils.put(contentValues, "bmc", weightMeasurement.bone);
            if (QardioBaseUtils.canCalculateBmi(weightMeasurement)) {
                HelperUtils.put(contentValues, "bmi", Float.valueOf(QardioBaseUtils.bmi(weightMeasurement.weight.floatValue(), weightMeasurement.height)));
            }
            HelperUtils.put(contentValues, "z", weightMeasurement.z);
            HelperUtils.put(contentValues, "source", weightMeasurement.source);
            HelperUtils.put(contentValues, "measurement_id", weightMeasurement.measurementId);
            HelperUtils.put(contentValues, "qb_user_id", weightMeasurement.qbUserId);
            HelperUtils.put(contentValues, "user", weightMeasurement.user);
            HelperUtils.put(contentValues, "measure_date", weightMeasurement.measureDate);
            HelperUtils.put(contentValues, "note", weightMeasurement.note);
            HelperUtils.put(contentValues, "member_name", weightMeasurement.memberName);
            HelperUtils.put(contentValues, "timezone", weightMeasurement.timezone);
            HelperUtils.put(contentValues, "measurements_source", weightMeasurement.measurementSource);
            HelperUtils.put(contentValues, "device_serial_number", weightMeasurement.deviceSerialNumber);
            HelperUtils.put(contentValues, "visitor", weightMeasurement.visitor);
            HelperUtils.put(contentValues, "fw", weightMeasurement.firmwareVersion);
            context.getContentResolver().insert(uri, contentValues);
            if (bl) {
                Weight.requestWeightMeasurementsSync(context, l);
            }
        }

        public static Uri buildDailyMeasurementsUri(long l) {
            return AppProvideContract.BASE_CONTENT_URI.buildUpon().appendPath("users").appendPath(Long.toString(l)).appendPath("weight_measurements").appendPath("daily").build();
        }

        private static Uri buildMeasurementUri(long l, long l2) {
            return AppProvideContract.BASE_CONTENT_URI.buildUpon().appendPath("users").appendPath(Long.toString(l)).appendPath("weight_measurements").appendPath(Long.toString(l2)).build();
        }

        public static Uri buildMeasurementsUri(long l) {
            return AppProvideContract.BASE_CONTENT_URI.buildUpon().appendPath("users").appendPath(Long.toString(l)).appendPath("weight_measurements").build();
        }

        private static Uri buildMonthlyMeasurementsUri(long l) {
            return AppProvideContract.BASE_CONTENT_URI.buildUpon().appendPath("users").appendPath(Long.toString(l)).appendPath("weight_measurements").appendPath("monthly").build();
        }

        private static Uri buildWeeklyMeasurementsUri(long l) {
            return AppProvideContract.BASE_CONTENT_URI.buildUpon().appendPath("users").appendPath(Long.toString(l)).appendPath("weight_measurements").appendPath("weekly").build();
        }

        /*
         * Enabled aggressive block sorting
         */
        public static void changeMeasurementNote(Context context, long l, long l2, String string2, boolean bl) {
            Uri uri = Weight.buildMeasurementsUri(l);
            Integer n = Weight.getMeasurementStatus(context, l, l2);
            int n2 = n == null ? 1 : n | 1;
            n = new ContentValues(2);
            n.put("note", string2);
            n.put("sync_status", Integer.valueOf(n2));
            n2 = context.getContentResolver().update(uri, (ContentValues)n, "measure_date=?", new String[]{Long.toString(l2)});
            if (bl && n2 > 0) {
                Weight.requestWeightMeasurementsSync(context, l);
            }
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        public static void changeSyncStatuses(Context var0, long var1_4, Collection<SyncStatusMetadata> var3_5) {
            var4_6 = new ArrayList<ContentProviderOperation>(var3_5.size());
            var3_5 = var3_5.iterator();
            while (var3_5.hasNext()) {
                var5_7 = (SyncStatusMetadata)var3_5.next();
                var6_8 = Weight.buildMeasurementUri(var1_4, var5_7.id);
                var7_9 = new ContentValues(1);
                var7_9.put("sync_status", Integer.valueOf(var5_7.syncStatus));
                var4_6.add(ContentProviderOperation.newUpdate((Uri)var6_8).withValues(var7_9).build());
            }
            try {
                var0.getContentResolver().applyBatch("com.getqardio.android", var4_6);
                return;
            }
            catch (RemoteException var0_1) {}
            ** GOTO lbl-1000
            catch (OperationApplicationException var0_3) {}
lbl-1000:
            // 2 sources
            {
                Timber.e((Throwable)var0_2);
                return;
            }
        }

        public static boolean deleteMeasurement(Context context, long l, long l2) {
            Uri uri = Weight.buildMeasurementsUri(l);
            ContentValues contentValues = new ContentValues(1);
            contentValues.put("sync_status", Integer.valueOf(4));
            int n = context.getContentResolver().update(uri, contentValues, "measure_date=?", new String[]{Long.toString(l2)});
            if (n > 0) {
                Weight.requestWeightMeasurementsSync(context, l);
            }
            return n > 0;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        public static void deleteMeasurementsFromCache(Context var0, long var1_4, Collection<Long> var3_5) {
            var4_6 = new ArrayList<ContentProviderOperation>(var3_5.size());
            var3_5 = var3_5.iterator();
            while (var3_5.hasNext()) {
                var4_6.add(ContentProviderOperation.newDelete((Uri)Weight.buildMeasurementUri(var1_4, (Long)var3_5.next())).build());
            }
            try {
                var0.getContentResolver().applyBatch("com.getqardio.android", var4_6);
                return;
            }
            catch (RemoteException var0_1) {}
            ** GOTO lbl-1000
            catch (OperationApplicationException var0_3) {}
lbl-1000:
            // 2 sources
            {
                Timber.e((Throwable)var0_2);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        public static Cursor getDailyMeasurementsForPeriod(Context context, long l, Date object, Date date, String[] arrstring, String string2) {
            Date date2 = DateUtils.getEndOfTheDay(date);
            object = DateUtils.getStartOfTheDay((Date)object);
            date = Weight.buildDailyMeasurementsUri(l);
            StringBuilder stringBuilder = new StringBuilder().append("(sync_status | ").append(3).append(" = ").append(3).append(") and  ").append("measure_date").append(" >= ").append(((Date)object).getTime());
            object = date2 != null ? " and measure_date <= " + date2.getTime() : "";
            object = stringBuilder.append((String)object).toString();
            return context.getContentResolver().query((Uri)date, arrstring, (String)object, null, string2);
        }

        public static CursorLoader getDailyMeasurementsLoader(Context context, long l, String[] arrstring) {
            return Weight.getPeriodicMeasurementsLoader(context, Weight.buildDailyMeasurementsUri(l), arrstring);
        }

        public static WeightMeasurement getLastMeasurement(Context object, long l, String[] arrstring) {
            Uri uri = Weight.buildMeasurementsUri(l);
            String string2 = "(sync_status | " + 3 + " = " + 3 + ")";
            uri = object.getContentResolver().query(uri, arrstring, string2, null, "measure_date DESC LIMIT 1");
            object = arrstring = null;
            if (uri != null) {
                object = arrstring;
                if (uri.moveToFirst()) {
                    object = Weight.parseMeasurement((Cursor)uri);
                }
            }
            return object;
            finally {
                uri.close();
            }
        }

        public static CursorLoader getLastMeasurementLoader(Context context, long l, String[] arrstring) {
            return new CursorLoader(context, Weight.buildMeasurementsUri(l), arrstring, "(sync_status | " + 3 + " = " + 3 + ")AND (" + "visitor" + " IS null or " + "visitor" + " = 0)", null, "measure_date DESC LIMIT 1");
        }

        public static WeightMeasurement getMeasurementByTimestamp(Context object, Long l, long l2) {
            Cursor cursor;
            block4: {
                l = Weight.buildMeasurementsUri(l);
                cursor = object.getContentResolver().query((Uri)l, null, "measure_date=?", new String[]{Long.toString(l2)}, null);
                object = null;
                l = null;
                if (cursor != null) {
                    object = l;
                    if (!cursor.moveToFirst()) break block4;
                    object = Weight.parseMeasurement(cursor);
                }
            }
            return object;
            finally {
                cursor.close();
            }
        }

        public static List<Calendar> getMeasurementDates(Cursor cursor) {
            ArrayList<Calendar> arrayList = new ArrayList<Calendar>();
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(HelperUtils.getLong(cursor, "measure_date", (Long)0L));
                arrayList.add(calendar);
                cursor.moveToNext();
            }
            return arrayList;
        }

        /*
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        static Integer getMeasurementStatus(Context object, long l, long l2) {
            Throwable throwable;
            Object var5_8;
            Uri uri;
            block16: {
                throwable = null;
                var5_8 = null;
                uri = Weight.buildMeasurementsUri(l);
                object = object.getContentResolver();
                String string2 = Long.toString(l2);
                if ((uri = object.query(uri, new String[]{"sync_status"}, "measure_date=?", new String[]{string2}, null)) != null) {
                    block17: {
                        if (!uri.moveToFirst()) break block16;
                        object = HelperUtils.getInt((Cursor)uri, "sync_status", null);
                        if (uri == null) return object;
                        if (!false) break block17;
                        uri.close();
                        return object;
                    }
                    uri.close();
                    return object;
                }
            }
            object = var5_8;
            if (uri == null) return object;
            if (!false) {
                uri.close();
                return null;
            }
            try {
                uri.close();
            }
            finally {
                return null;
            }
            catch (Throwable throwable2) {
                try {
                    throw throwable2;
                }
                catch (Throwable throwable3) {
                    Throwable throwable4;
                    block18: {
                        throwable = throwable2;
                        throwable4 = throwable3;
                        break block18;
                        catch (Throwable throwable5) {
                            return object;
                        }
                        catch (Throwable throwable6) {}
                    }
                    if (uri == null) throw throwable4;
                    if (throwable == null) {
                        uri.close();
                        throw throwable4;
                    }
                    try {
                        uri.close();
                    }
                    finally {
                        throw throwable4;
                    }
                }
            }
        }

        /*
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        public static int getMeasurementsCountForSync(Context object, long l) {
            int n;
            Object var4_4 = null;
            Uri uri = Weight.buildMeasurementsUri(l);
            uri = object.getContentResolver().query(uri, COUNT_FOR_SYNC_PROJECTION, "(sync_status & 1 = 1) OR (sync_status & 4 = 4)", null, null);
            if (uri != null) {
                object = var4_4;
                n = uri.getCount();
            } else {
                n = 0;
            }
            if (uri == null) {
            }
            if (!false) {
                uri.close();
                return n;
            }
            uri.close();
            catch (Throwable throwable) {
                object = throwable;
                try {
                    throw throwable;
                }
                catch (Throwable throwable2) {
                    if (uri == null) throw throwable2;
                    if (object == null) {
                        uri.close();
                        throw throwable2;
                    }
                    try {
                        uri.close();
                    }
                    finally {
                        throw throwable2;
                    }
                }
            }
        }

        public static Cursor getMeasurementsForDelete(Context context, long l, String[] arrstring) {
            Uri uri = Weight.buildMeasurementsUri(l);
            return context.getContentResolver().query(uri, arrstring, "(sync_status & 4 = 4)", null, null);
        }

        public static Cursor getMeasurementsForUpload(Context context, long l, String[] arrstring) {
            Uri uri = Weight.buildMeasurementsUri(l);
            return context.getContentResolver().query(uri, arrstring, "(sync_status & 1 = 1)", null, null);
        }

        public static CursorLoader getMeasurementsLoader(Context context, long l, String[] arrstring) {
            return new CursorLoader(context, Weight.buildMeasurementsUri(l), arrstring, "(sync_status | " + 3 + " = " + 3 + ") AND (" + "visitor" + " IS null or " + "visitor" + " = 0)", null, "measure_date DESC");
        }

        public static CursorLoader getMeasurementsLoaderByDate(Context context, long l, long l2, String[] arrstring) {
            return new CursorLoader(context, Weight.buildMeasurementsUri(l), arrstring, "measure_date=?", new String[]{Long.toString(l2)}, null);
        }

        public static CursorLoader getMeasurementsLoaderForPeriod(Context context, long l, long l2, long l3, String[] arrstring, String string2) {
            return new CursorLoader(context, Weight.buildMeasurementsUri(l), arrstring, "(sync_status | " + 3 + " = " + 3 + ") and " + "measure_date" + " >= " + l2 + " and " + "measure_date" + " <= " + l3, null, string2);
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        static Map<Date, Integer> getMeasurementsStatuses(Context var0, long var1_5) {
            var4_6 = null;
            var3_7 /* !! */  = Weight.buildMeasurementsUri(var1_5);
            var5_11 = var0.getContentResolver().query(var3_7 /* !! */ , Weight.MEASUREMENTS_STATUSES_PROJECTION, "sync_status != 0", null, null);
            if (var5_11 == null) {
                if (var5_11 == null) {
                }
                if (!false) {
                    var5_11.close();
                    return null;
                }
                var5_11.close();
            }
            try {
                var0 = new HashMap<K, V>(var5_11.getCount());
                var5_11.moveToFirst();
                while (!var5_11.isAfterLast()) {
                    var3_7 /* !! */  = Weight.parseMeasurement(var5_11);
                    if (var3_7 /* !! */ .measureDate != null && var3_7 /* !! */ .syncStatus != null) {
                        var0.put(var3_7 /* !! */ .measureDate, var3_7 /* !! */ .syncStatus);
                    }
                    var5_11.moveToNext();
                }
                ** GOTO lbl32
            }
            catch (Throwable var0_1) {
                try {
                    throw var0_1;
                }
                catch (Throwable var3_8) {
                    block20: {
                        var4_6 = var0_1;
                        var0_2 = var3_8;
                        break block20;
lbl32:
                        // 1 sources
                        if (var5_11 == null) return var0;
                        if (!false) {
                            var5_11.close();
                            return var0;
                        }
                        try {
                            var5_11.close();
                            return var0;
                        }
                        catch (Throwable var3_9) {
                            return var0;
                        }
                        finally {
                            return null;
                        }
                        catch (Throwable var0_4) {}
                    }
                    if (var5_11 == null) throw var0_2;
                    if (var4_6 != null) {
                        var5_11.close();
                    }
                    var5_11.close();
                    throw var0_2;
                    finally {
                        throw var0_2;
                    }
                }
            }
        }

        public static CursorLoader getMonthlyMeasurementsLoader(Context context, long l, String[] arrstring) {
            return Weight.getPeriodicMeasurementsLoader(context, Weight.buildMonthlyMeasurementsUri(l), arrstring);
        }

        private static CursorLoader getPeriodicMeasurementsLoader(Context context, Uri uri, String[] arrstring) {
            return new CursorLoader(context, uri, arrstring, "(sync_status | " + 3 + " = " + 3 + ")", null, "measure_date ASC");
        }

        public static CursorLoader getPregnancyHistory(Context context, long l) {
            return new CursorLoader(context, AppProvideContract.BASE_CONTENT_URI.buildUpon().appendPath("users").appendPath(String.valueOf(l)).appendPath("pregnancy_mode_data").build(), new String[]{"_id", "start_date", "due_date", "end_date"}, null, null, null);
        }

        public static Float getThisWeekDifference(Context object, long l) {
            Uri uri = Weight.buildMeasurementsUri(l);
            l = DateUtils.getThisWeekStartTime();
            Float f = Weight.getWeight((Context)object, uri, l, "measure_date ASC LIMIT 1");
            object = Weight.getWeight((Context)object, uri, l, "measure_date DESC LIMIT 1");
            if (f == null) {
                return null;
            }
            return Float.valueOf(((Float)object).floatValue() - f.floatValue());
        }

        public static CursorLoader getWeeklyMeasurementsLoader(Context context, long l, String[] arrstring) {
            return Weight.getPeriodicMeasurementsLoader(context, Weight.buildWeeklyMeasurementsUri(l), arrstring);
        }

        private static Float getWeight(Context object, Uri uri, float f, String string2) {
            block6: {
                String string3 = "sync_status | " + 3 + " = " + 3 + " AND " + "measure_date" + " > ?";
                Uri uri2 = null;
                Object var5_7 = null;
                try {
                    uri = object.getContentResolver().query(uri, WEIGHT_PROJECTION, string3, new String[]{String.valueOf(f)}, string2);
                    object = var5_7;
                    if (uri == null) break block6;
                    object = var5_7;
                    uri2 = uri;
                }
                catch (Throwable throwable) {
                    if (uri2 != null) {
                        uri2.close();
                    }
                    throw throwable;
                }
                if (!uri.moveToFirst()) break block6;
                uri2 = uri;
                object = HelperUtils.getFloat((Cursor)uri, "weight", null);
            }
            if (uri != null) {
                uri.close();
            }
            return object;
        }

        public static Cursor getWeightMeasurementsForSHealth(Context context, long l) {
            Uri uri = Weight.buildMeasurementsUri(l);
            String string2 = "(measurements_source=0 OR measurements_source=4) AND (sync_status | " + 3 + " = " + 3 + ")";
            return context.getContentResolver().query(uri, null, string2, null, null);
        }

        public static AverageWeightMeasurement parseAverageWeightMeasurement(Cursor cursor) {
            AverageWeightMeasurement averageWeightMeasurement = new AverageWeightMeasurement();
            averageWeightMeasurement.measureDate = HelperUtils.getDate(cursor, "measure_date", null);
            averageWeightMeasurement.weight = HelperUtils.getFloat(cursor, "weight", null);
            averageWeightMeasurement.fat = HelperUtils.getFloat(cursor, "fat", null);
            averageWeightMeasurement.bmi = HelperUtils.getFloat(cursor, "bmi", null);
            return averageWeightMeasurement;
        }

        public static WeightMeasurement parseMeasurement(Cursor cursor) {
            WeightMeasurement weightMeasurement = new WeightMeasurement();
            weightMeasurement._id = HelperUtils.getLong(cursor, "_id", null);
            weightMeasurement.userId = HelperUtils.getLong(cursor, "user_id", null);
            weightMeasurement.syncStatus = HelperUtils.getInt(cursor, "sync_status", null);
            weightMeasurement.weight = HelperUtils.getFloat(cursor, "weight", null);
            weightMeasurement.fullName = HelperUtils.getString(cursor, "full_name", null);
            weightMeasurement.deviceId = HelperUtils.getString(cursor, "device_id", null);
            weightMeasurement.measurementId = HelperUtils.getString(cursor, "measurement_id", null);
            weightMeasurement.height = HelperUtils.getInt(cursor, "height", null);
            weightMeasurement.sex = HelperUtils.getString(cursor, "sex", null);
            weightMeasurement.fat = HelperUtils.getInt(cursor, "fat", null);
            weightMeasurement.bone = HelperUtils.getInt(cursor, "bmc", null);
            weightMeasurement.water = HelperUtils.getInt(cursor, "tbw", null);
            weightMeasurement.muscle = HelperUtils.getInt(cursor, "mt", null);
            weightMeasurement.battery = HelperUtils.getInt(cursor, "battery", null);
            weightMeasurement.age = HelperUtils.getInt(cursor, "age", null);
            weightMeasurement.z = HelperUtils.getInt(cursor, "z", null);
            weightMeasurement.source = HelperUtils.getString(cursor, "source", null);
            weightMeasurement.user = HelperUtils.getString(cursor, "user", null);
            weightMeasurement.qbUserId = HelperUtils.getLong(cursor, "qb_user_id", null);
            weightMeasurement.measureDate = HelperUtils.getDate(cursor, "measure_date", null);
            weightMeasurement.note = HelperUtils.getString(cursor, "note", null);
            weightMeasurement.memberName = HelperUtils.getString(cursor, "member_name", null);
            weightMeasurement.timezone = HelperUtils.getString(cursor, "timezone", null);
            weightMeasurement.measurementSource = HelperUtils.getInt(cursor, "measurements_source", (Integer)0);
            weightMeasurement.deviceSerialNumber = HelperUtils.getString(cursor, "device_serial_number", null);
            weightMeasurement.firmwareVersion = HelperUtils.getString(cursor, "fw", null);
            weightMeasurement.visitor = HelperUtils.getBoolean(cursor, "visitor", (Boolean)false);
            return weightMeasurement;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        public static void replaceMeasurements(Context var0, long var1_4, List<WeightMeasurement> var3_5) {
            var4_6 = Weight.buildMeasurementsUri(var1_4);
            var5_7 = new ArrayList<ContentProviderOperation>(var3_5.size() + 1);
            var5_7.add(ContentProviderOperation.newDelete((Uri)var4_6).build());
            var6_8 = Weight.getMeasurementsStatuses(var0, var1_4);
            var7_9 = DataHelper.ProfileHelper.getProfileForUser(var0, var1_4);
            var3_5 = var3_5.iterator();
            while (var3_5.hasNext()) {
                var8_10 = (WeightMeasurement)var3_5.next();
                var9_11 = new ContentValues(24);
                HelperUtils.put(var9_11, "weight", var8_10.weight);
                HelperUtils.put(var9_11, "full_name", var8_10.fullName);
                HelperUtils.put(var9_11, "device_id", var8_10.deviceId);
                HelperUtils.put(var9_11, "sex", var8_10.sex);
                HelperUtils.put(var9_11, "battery", var8_10.battery);
                HelperUtils.put(var9_11, "age", var8_10.age);
                HelperUtils.put(var9_11, "fat", var8_10.fat);
                HelperUtils.put(var9_11, "mt", var8_10.muscle);
                HelperUtils.put(var9_11, "tbw", var8_10.water);
                HelperUtils.put(var9_11, "bmc", var8_10.bone);
                if (var8_10.height == null) {
                    if (Claim.getClaimedMeasurementByMeasurementId(var0, var1_4, var8_10.measurementId) != null && var7_9 != null) {
                        HelperUtils.put(var9_11, "height", Float.valueOf(MetricUtils.convertHeight(var7_9.heightUnit, 0, var7_9.height.floatValue())));
                        HelperUtils.put(var9_11, "sync_status", 1);
                    }
                } else {
                    HelperUtils.put(var9_11, "height", var8_10.height);
                }
                if (QardioBaseUtils.canCalculateBmi(var8_10)) {
                    HelperUtils.put(var9_11, "bmi", Float.valueOf(QardioBaseUtils.bmi(var8_10.weight.floatValue(), var8_10.height)));
                }
                HelperUtils.put(var9_11, "z", var8_10.z);
                HelperUtils.put(var9_11, "source", var8_10.source);
                HelperUtils.put(var9_11, "measurement_id", var8_10.measurementId);
                HelperUtils.put(var9_11, "qb_user_id", var8_10.qbUserId);
                HelperUtils.put(var9_11, "user", var8_10.user);
                HelperUtils.put(var9_11, "measure_date", var8_10.measureDate);
                HelperUtils.put(var9_11, "note", var8_10.note);
                HelperUtils.put(var9_11, "timezone", var8_10.timezone);
                HelperUtils.put(var9_11, "measurements_source", var8_10.measurementSource);
                HelperUtils.put(var9_11, "device_serial_number", var8_10.deviceSerialNumber);
                if (var6_8 != null && var6_8.containsKey(var8_10.measureDate)) {
                    HelperUtils.put(var9_11, "sync_status", var6_8.get(var8_10.measureDate));
                }
                var5_7.add(ContentProviderOperation.newInsert((Uri)var4_6).withValues(var9_11).build());
            }
            try {
                var0.getContentResolver().applyBatch("com.getqardio.android", var5_7);
                return;
            }
            catch (RemoteException var0_1) {}
            ** GOTO lbl-1000
            catch (OperationApplicationException var0_3) {}
lbl-1000:
            // 2 sources
            {
                Timber.e((Throwable)var0_2);
                return;
            }
        }

        static void requestWeightMeasurementsSync(Context context, long l) {
            context.startService(WeightMeasurementsRequestHandler.createSyncWeightMeasurementsIntent(context, l));
        }

        public static void requestWeightMeasurementsUpdate(Context context, long l) {
            context.startService(WeightMeasurementsRequestHandler.createGetWeightMeasurementsIntent(context, l));
        }

        public static void updateMeasurementWeight(Context context, Long l, WeightMeasurement weightMeasurement) {
            l = Weight.buildMeasurementsUri(l);
            ContentValues contentValues = new ContentValues(1);
            contentValues.put("weight", weightMeasurement.weight);
            long l2 = weightMeasurement.measureDate.getTime();
            context.getContentResolver().update((Uri)l, contentValues, "measure_date=?", new String[]{String.valueOf(l2)});
        }
    }

}

