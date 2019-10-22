/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.bluetooth.BluetoothAdapter
 *  android.content.AsyncQueryHandler
 *  android.content.ComponentName
 *  android.content.ContentProviderOperation
 *  android.content.ContentProviderOperation$Builder
 *  android.content.ContentProviderResult
 *  android.content.ContentResolver
 *  android.content.ContentValues
 *  android.content.Context
 *  android.content.CursorLoader
 *  android.content.Intent
 *  android.content.Loader
 *  android.content.OperationApplicationException
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.database.Cursor
 *  android.net.Uri
 *  android.net.Uri$Builder
 *  android.os.CancellationSignal
 *  android.os.RemoteException
 *  android.text.TextUtils
 */
package com.getqardio.android.provider;

import android.bluetooth.BluetoothAdapter;
import android.content.AsyncQueryHandler;
import android.content.ComponentName;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.OperationApplicationException;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.CancellationSignal;
import android.os.RemoteException;
import android.text.TextUtils;
import com.getqardio.android.CustomApplication;
import com.getqardio.android.datamodel.DeviceAssociation;
import com.getqardio.android.datamodel.Faq;
import com.getqardio.android.datamodel.FlickrPhotoMetadata;
import com.getqardio.android.datamodel.Goal;
import com.getqardio.android.datamodel.MeasurementNote;
import com.getqardio.android.datamodel.PregnancyData;
import com.getqardio.android.datamodel.Profile;
import com.getqardio.android.datamodel.Reminder;
import com.getqardio.android.datamodel.Settings;
import com.getqardio.android.datamodel.Statistic;
import com.getqardio.android.io.network.request.CurrentGoalRequestHandler;
import com.getqardio.android.io.network.request.DeviceAssociationsRequestHandler;
import com.getqardio.android.io.network.request.FAQRequestHandler;
import com.getqardio.android.io.network.request.FlickrRequestHandler;
import com.getqardio.android.io.network.request.PregnancyModeRequestHandler;
import com.getqardio.android.io.network.request.ProfileRequestHandler;
import com.getqardio.android.io.network.request.ReminderRequestHandler;
import com.getqardio.android.io.network.request.SendHistoryRequestHandler;
import com.getqardio.android.io.network.request.SettingsRequestHandler;
import com.getqardio.android.io.network.request.StatisticRequestHandler;
import com.getqardio.android.io.network.request.SupportRequestHandler;
import com.getqardio.android.provider.AppProvideContract;
import com.getqardio.android.provider.AuthHelper;
import com.getqardio.android.utils.ChooseDeviceUtils;
import com.getqardio.android.utils.HelperUtils;
import com.getqardio.android.utils.Utils;
import com.getqardio.android.utils.alarms.ReminderAlarmGenerator;
import com.getqardio.android.utils.ui.Convert;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import timber.log.Timber;

public abstract class DataHelper {

    public static abstract class CurrentGoalHelper {
        public static final String[] COUNT_FOR_SYNC_PROJECTION;
        public static final String[] DELETE_GOAL_PROJECTION;
        public static final String[] LOAD_GOAL_PROJECTION;

        static {
            DELETE_GOAL_PROJECTION = new String[0];
            COUNT_FOR_SYNC_PROJECTION = new String[]{"_id"};
            LOAD_GOAL_PROJECTION = new String[]{"start_date", "target", "target_per_week"};
        }

        private static Uri buildGoalUri(long l) {
            return AppProvideContract.BASE_CONTENT_URI.buildUpon().appendPath("users").appendPath(Long.toString(l)).appendPath("current_goal").build();
        }

        public static boolean changeSyncStatus(Context context, long l, int n) {
            Uri uri = CurrentGoalHelper.buildGoalUri(l);
            ContentValues contentValues = new ContentValues(1);
            HelperUtils.put(contentValues, "sync_status", n);
            return context.getContentResolver().update(uri, contentValues, null, null) > 0;
        }

        public static boolean deleteGoal(Context context, long l) {
            Uri uri = CurrentGoalHelper.buildGoalUri(l);
            ContentValues contentValues = new ContentValues(1);
            HelperUtils.put(contentValues, "sync_status", 4);
            int n = context.getContentResolver().update(uri, contentValues, null, null);
            CurrentGoalHelper.requestGoalSync(context, l);
            return n > 0;
        }

        public static boolean deleteGoalFromLocalCache(Context context, long l) {
            Uri uri = CurrentGoalHelper.buildGoalUri(l);
            return context.getContentResolver().delete(uri, null, null) > 0;
        }

        public static CursorLoader getGoalLoader(Context context, long l, String[] arrstring) {
            return new CursorLoader(context, CurrentGoalHelper.buildGoalUri(l), arrstring, "(sync_status | " + 3 + " = " + 3 + ")", null, null);
        }

        public static int getGoalsCountForSync(Context context, long l) {
            Uri uri = CurrentGoalHelper.buildGoalUri(l);
            context = context.getContentResolver().query(uri, COUNT_FOR_SYNC_PROJECTION, "(sync_status & 1 = 1) OR (sync_status & 4 = 4)", null, null);
            try {
                int n = context.getCount();
                return n;
            }
            finally {
                context.close();
            }
        }

        public static Cursor getGoalsForDelete(Context context, long l, String[] arrstring) {
            Uri uri = CurrentGoalHelper.buildGoalUri(l);
            return context.getContentResolver().query(uri, arrstring, "(sync_status & 4 = 4)", null, null);
        }

        public static Cursor getGoalsForUpload(Context context, long l, String[] arrstring) {
            Uri uri = CurrentGoalHelper.buildGoalUri(l);
            return context.getContentResolver().query(uri, arrstring, "(sync_status & 1 = 1)", null, null);
        }

        public static Goal parseGoal(Cursor cursor) {
            Goal goal = new Goal();
            goal._id = HelperUtils.getLong(cursor, "_id", goal._id);
            goal.userId = HelperUtils.getLong(cursor, "user_id", goal.userId);
            goal.syncStatus = HelperUtils.getInt(cursor, "sync_status", goal.syncStatus);
            goal.type = HelperUtils.getString(cursor, "type", goal.type);
            goal.startDate = HelperUtils.getLong(cursor, "start_date", goal.startDate);
            goal.target = HelperUtils.getFloat(cursor, "target", goal.target);
            goal.targetPerWeek = HelperUtils.getFloat(cursor, "target_per_week", goal.targetPerWeek);
            goal.positiveBoundary = HelperUtils.getInt(cursor, "positive_boundary", goal.positiveBoundary);
            goal.neutralBoundary = HelperUtils.getInt(cursor, "neutral_boundary", goal.neutralBoundary);
            goal.excellentBoundary = HelperUtils.getInt(cursor, "excellent_boundary", goal.excellentBoundary);
            return goal;
        }

        public static void requestGoalSync(Context context, long l) {
            context.startService(CurrentGoalRequestHandler.createSyncCurrentGoalIntent(context, l));
        }

        public static void requestGoalUpdate(Context context, long l) {
            context.startService(CurrentGoalRequestHandler.createUpdateCurrentGoalIntent(context, l));
        }

        /*
         * Enabled aggressive block sorting
         */
        public static void saveGoal(Context context, Goal goal, boolean bl) {
            Uri uri = CurrentGoalHelper.buildGoalUri(goal.userId);
            if (bl) {
                int n = goal.syncStatus == null ? 1 : goal.syncStatus | 1;
                goal.syncStatus = n;
            }
            ContentValues contentValues = new ContentValues(6);
            HelperUtils.put(contentValues, "sync_status", goal.syncStatus);
            HelperUtils.put(contentValues, "type", goal.type);
            HelperUtils.put(contentValues, "start_date", goal.startDate);
            HelperUtils.put(contentValues, "target", goal.target);
            HelperUtils.put(contentValues, "target_per_week", goal.targetPerWeek);
            HelperUtils.put(contentValues, "excellent_boundary", goal.excellentBoundary);
            HelperUtils.put(contentValues, "neutral_boundary", goal.neutralBoundary);
            HelperUtils.put(contentValues, "positive_boundary", goal.positiveBoundary);
            if (context.getContentResolver().update(uri, contentValues, null, null) == 0) {
                context.getContentResolver().insert(uri, contentValues);
            }
            if (bl) {
                CurrentGoalHelper.requestGoalSync(context, goal.userId);
            }
        }
    }

    public static abstract class DeviceAddressHelper {
        public static void deleteDeviceAddress(Context context, long l) {
            context = context.getSharedPreferences("qbase_address_sp_storage", 0).edit();
            context.remove(Long.toString(l));
            context.commit();
        }

        public static String getDeviceAddress(Context context, long l) {
            return context.getSharedPreferences("qbase_address_sp_storage", 0).getString(Long.toString(l), null);
        }

        public static void setDeviceAddress(Context context, long l, String string2) {
            Timber.d("setDeviceAddress - %s", string2);
            context = context.getSharedPreferences("qbase_address_sp_storage", 0).edit();
            context.putString(Long.toString(l), string2);
            context.commit();
        }
    }

    public static abstract class DeviceAssociationsHelper {
        public static final String[] COUNT_FOR_SYNC_PROJECTION = new String[]{"_id"};
        public static final String[] DELETING_PROJECTION = new String[]{"_id", "device_id"};

        public static void addDeviceAssociation(Context context, long l, String string2, String string3, String string4) {
            Uri uri = DeviceAssociationsHelper.buildUri(l);
            ContentValues contentValues = new ContentValues(4);
            HelperUtils.put(contentValues, "user_id", l);
            HelperUtils.put(contentValues, "sync_status", 1);
            HelperUtils.put(contentValues, "device_id", string2);
            HelperUtils.put(contentValues, "serial_number", string3);
            HelperUtils.put(contentValues, "mac_address", string4);
            context.getContentResolver().insert(uri, contentValues);
            DeviceAssociationsHelper.requestDeviceAssociationsSync(context, l);
        }

        private static Uri buildUri(long l) {
            return AppProvideContract.BASE_CONTENT_URI.buildUpon().appendPath("users").appendPath(Long.toString(l)).appendPath("device_associations").build();
        }

        private static Uri buildUri(long l, long l2) {
            return AppProvideContract.BASE_CONTENT_URI.buildUpon().appendPath("users").appendPath(Long.toString(l)).appendPath("device_associations").appendPath(Long.toString(l2)).build();
        }

        public static boolean changeSyncStatus(Context context, long l, long l2, int n) {
            Uri uri = DeviceAssociationsHelper.buildUri(l, l2);
            ContentValues contentValues = new ContentValues(1);
            HelperUtils.put(contentValues, "sync_status", n);
            return context.getContentResolver().update(uri, contentValues, null, null) > 0;
        }

        public static boolean deleteDeviceAssociation(Context context, long l, String string2) {
            Uri uri = DeviceAssociationsHelper.buildUri(l);
            ContentValues contentValues = new ContentValues(1);
            contentValues.put("sync_status", Integer.valueOf(4));
            int n = context.getContentResolver().update(uri, contentValues, "serial_number=?", new String[]{string2});
            if (n > 0) {
                DeviceAssociationsHelper.requestDeviceAssociationsSync(context, l);
            }
            return n > 0;
        }

        public static boolean deleteDeviceAssociationFromLocalCache(Context context, long l, long l2) {
            Uri uri = DeviceAssociationsHelper.buildUri(l, l2);
            return context.getContentResolver().delete(uri, null, null) > 0;
        }

        private static ContentValues getDeviceAssociationsContentValuesfrom(DeviceAssociation deviceAssociation) {
            ContentValues contentValues = new ContentValues(6);
            HelperUtils.put(contentValues, "user_id", deviceAssociation.userId);
            HelperUtils.put(contentValues, "device_id", deviceAssociation.deviceId);
            HelperUtils.put(contentValues, "serial_number", deviceAssociation.serialNumber);
            HelperUtils.put(contentValues, "sync_status", deviceAssociation.syncStatus);
            return contentValues;
        }

        public static int getDeviceAssociationsCountForSync(Context context, long l) {
            Uri uri = DeviceAssociationsHelper.buildUri(l);
            context = context.getContentResolver().query(uri, COUNT_FOR_SYNC_PROJECTION, "(sync_status & 1 = 1) OR (sync_status & 4 = 4)", null, null);
            try {
                int n = context.getCount();
                return n;
            }
            finally {
                context.close();
            }
        }

        public static Cursor getDeviceAssociationsForDelete(Context context, long l, String[] arrstring) {
            Uri uri = DeviceAssociationsHelper.buildUri(l);
            return context.getContentResolver().query(uri, arrstring, "(sync_status & 4 = 4)", null, null);
        }

        public static Cursor getDeviceAssociationsForUpload(Context context, long l, String[] arrstring) {
            Uri uri = DeviceAssociationsHelper.buildUri(l);
            return context.getContentResolver().query(uri, arrstring, "(sync_status & 1 = 1)", null, null);
        }

        public static DeviceAssociation parseDeviceAssociation(Cursor cursor) {
            DeviceAssociation deviceAssociation = new DeviceAssociation();
            deviceAssociation._id = HelperUtils.getLong(cursor, "_id", deviceAssociation._id);
            deviceAssociation.userId = HelperUtils.getLong(cursor, "user_id", deviceAssociation.userId);
            deviceAssociation.syncStatus = HelperUtils.getInt(cursor, "sync_status", deviceAssociation.syncStatus);
            deviceAssociation.deviceId = HelperUtils.getString(cursor, "device_id", deviceAssociation.deviceId);
            deviceAssociation.serialNumber = HelperUtils.getString(cursor, "serial_number", deviceAssociation.serialNumber);
            return deviceAssociation;
        }

        public static void requestDeviceAssociationsGet(Context context, long l) {
            context.startService(DeviceAssociationsRequestHandler.createUpdateDeviceAssociationsIntent(context, l));
        }

        public static void requestDeviceAssociationsSync(Context context, long l) {
            context.startService(DeviceAssociationsRequestHandler.createSyncDeviceAssociationsIntent(context, l));
        }

        public static boolean updateDeviceAssociationsAfterGet(Context context, long l, List<DeviceAssociation> list) {
            Uri uri = DeviceAssociationsHelper.buildUri(l);
            ArrayList<ContentProviderOperation> arrayList = new ArrayList<ContentProviderOperation>(list.size() + 1);
            arrayList.add(ContentProviderOperation.newDelete((Uri)uri).build());
            int n = 0;
            for (DeviceAssociation deviceAssociation : list) {
                int n2 = n + 1;
                Object object = DeviceAssociationsHelper.getDeviceAssociationsContentValuesfrom(deviceAssociation);
                arrayList.add(ContentProviderOperation.newInsert((Uri)uri).withValues(object).build());
                n = n2;
                if (deviceAssociation.serialNumber == null) continue;
                n = n2;
                if (list.size() != n2) continue;
                object = Utils.convertQbSerialNumberToBluetoothAddress(Integer.valueOf(deviceAssociation.serialNumber));
                Timber.d("deviceAddress - %s", object);
                if (BluetoothAdapter.checkBluetoothAddress((String)object)) {
                    Timber.d("deviceAddress is valid", new Object[0]);
                    DeviceAddressHelper.setDeviceAddress(context, l, (String)object);
                    DeviceIdHelper.setDeviceId(context, l, deviceAssociation.deviceId);
                    DeviceSnHelper.setDeviceSn(context, l, deviceAssociation.serialNumber);
                    ChooseDeviceUtils.setQardioBaseKnown(context, l, true);
                    OnBoardingHelper.setOnboardingFinished(context, l, true);
                    n = n2;
                    continue;
                }
                Timber.d("deviceAddress is invalid", new Object[0]);
                DeviceAddressHelper.setDeviceAddress(context, l, null);
                ChooseDeviceUtils.setQardioBaseKnown(context, l, false);
                OnBoardingHelper.setOnboardingFinished(context, l, false);
                DeviceIdHelper.setDeviceId(context, l, null);
                DeviceSnHelper.setDeviceSn(context, l, null);
                n = n2;
            }
            try {
                context.getContentResolver().applyBatch("com.getqardio.android", arrayList);
                return true;
            }
            catch (RemoteException remoteException) {
                Timber.d("RemoteException:", new Object[]{remoteException});
                return false;
            }
            catch (OperationApplicationException operationApplicationException) {
                Timber.d("OperationApplicationException:", new Object[]{operationApplicationException});
                return false;
            }
        }
    }

    public static abstract class DeviceIdHelper {
        public static String getDeviceId(Context context, long l) {
            return context.getSharedPreferences("qbase_id_sp_storage", 0).getString(Long.toString(l), "");
        }

        public static void setDeviceId(Context context, long l, String string2) {
            context = context.getSharedPreferences("qbase_id_sp_storage", 0).edit();
            context.putString(Long.toString(l), string2);
            context.commit();
        }
    }

    public static abstract class DeviceSnHelper {
        public static String getDeviceSn(Context context, long l) {
            return context.getSharedPreferences("qbase_sn_sp_storage", 0).getString(Long.toString(l), "");
        }

        public static void setDeviceSn(Context context, long l, String string2) {
            context = context.getSharedPreferences("qbase_sn_sp_storage", 0).edit();
            context.putString(Long.toString(l), string2);
            context.commit();
        }
    }

    public static abstract class FaqHelper {
        public static final String[] FAQ_DETAILS_PROJECTION;
        public static final String[] FAQ_LIST_PROJECTION;

        static {
            FAQ_LIST_PROJECTION = new String[]{"faq_id as _id", "question", "device_type"};
            FAQ_DETAILS_PROJECTION = new String[]{"faq_id as _id", "question", "answer"};
        }

        public static void deleteAllFaq(Context context, String string2) {
            context.getContentResolver().delete(AppProvideContract.Tables.Faqs.CONTENT_URI, "device_type=?", new String[]{string2});
        }

        public static CursorLoader getFaqListLoader(Context context, String[] arrstring) {
            return new CursorLoader(context, AppProvideContract.Tables.Faqs.CONTENT_URI, arrstring, null, null, null);
        }

        public static CursorLoader getFaqLoader(Context context, long l, String[] arrstring) {
            return new CursorLoader(context, AppProvideContract.Tables.Faqs.CONTENT_URI, arrstring, "faq_id=?", new String[]{Long.toString(l)}, null);
        }

        public static Faq parseFaq(Cursor cursor) {
            Faq faq = new Faq();
            faq._id = HelperUtils.getLong(cursor, "_id", faq._id);
            faq.faqId = HelperUtils.getLong(cursor, "faq_id", faq.faqId);
            faq.createDate = HelperUtils.getLong(cursor, "create_date", faq.createDate);
            faq.parent = HelperUtils.getLong(cursor, "parent", faq.parent);
            faq.category = HelperUtils.getString(cursor, "category", faq.category);
            faq.question = HelperUtils.getString(cursor, "question", faq.question);
            faq.answer = HelperUtils.getString(cursor, "answer", faq.answer);
            faq.frequency = HelperUtils.getInt(cursor, "frequency", faq.frequency);
            faq.deviceType = HelperUtils.getString(cursor, "device_type", faq.deviceType);
            return faq;
        }

        public static void requestArmFaqUpdate(Context context, long l) {
            context.startService(FAQRequestHandler.createUpdateArmFAQListIntent(context, l));
        }

        public static void requestBaseFaqUpdate(Context context, long l) {
            context.startService(FAQRequestHandler.createUpdateBaseFAQListIntent(context, l));
        }

        public static boolean setFaqList(Context context, List<Faq> object) {
            ArrayList<ContentProviderOperation> arrayList = new ArrayList<ContentProviderOperation>();
            object = object.iterator();
            while (object.hasNext()) {
                Faq faq = (Faq)object.next();
                ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert((Uri)AppProvideContract.Tables.Faqs.CONTENT_URI);
                if (faq._id != null) {
                    builder.withValue("_id", (Object)faq._id);
                }
                if (faq.faqId != null) {
                    builder.withValue("faq_id", (Object)faq.faqId);
                }
                if (faq.createDate != null) {
                    builder.withValue("create_date", (Object)faq.createDate);
                }
                if (faq.parent != null) {
                    builder.withValue("parent", (Object)faq.parent);
                }
                if (faq.category != null) {
                    builder.withValue("category", (Object)faq.category);
                }
                if (faq.question != null) {
                    builder.withValue("question", (Object)faq.question);
                }
                if (faq.answer != null) {
                    builder.withValue("answer", (Object)faq.answer);
                }
                if (faq.frequency != null) {
                    builder.withValue("frequency", (Object)faq.frequency);
                }
                if (faq.deviceType != null) {
                    builder.withValue("device_type", (Object)faq.deviceType);
                }
                arrayList.add(builder.build());
            }
            try {
                context.getContentResolver().applyBatch("com.getqardio.android", arrayList);
                return true;
            }
            catch (RemoteException remoteException) {
                Timber.e(remoteException, "", new Object[0]);
                return false;
            }
            catch (OperationApplicationException operationApplicationException) {
                Timber.e(operationApplicationException, "", new Object[0]);
                return false;
            }
        }
    }

    public static abstract class FlickrHelper {
        public static Uri FLICKR_PHOTOS_METADATA_URI = Uri.withAppendedPath((Uri)AppProvideContract.BASE_CONTENT_URI, (String)"flickr_photo_metadata");

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        public static List<FlickrPhotoMetadata> getAllFlickrPhotosMetadata(Context var0) {
            var2_5 = null;
            var1_6 = new ArrayList<FlickrPhotoMetadata>();
            var3_9 = var0.getContentResolver().query(FlickrHelper.FLICKR_PHOTOS_METADATA_URI, null, null, null, null);
            try {
                var3_9.moveToFirst();
                while (!var3_9.isAfterLast()) {
                    var1_6.add(FlickrHelper.parseFlickrPhoto(var3_9));
                    var3_9.moveToNext();
                }
                ** GOTO lbl21
            }
            catch (Throwable var0_1) {
                try {
                    throw var0_1;
                }
                catch (Throwable var1_7) {
                    block15: {
                        var2_5 = var0_1;
                        var0_2 = var1_7;
                        break block15;
lbl21:
                        // 1 sources
                        if (var3_9 == null) {
                        }
                        if (!false) {
                            var3_9.close();
                            return var1_6;
                        }
                        try {
                            var3_9.close();
                        }
                        finally {
                            return var1_6;
                        }
                        catch (Throwable var0_4) {}
                    }
                    if (var3_9 == null) throw var0_2;
                    if (var2_5 != null) {
                        var3_9.close();
                    }
                    var3_9.close();
                    throw var0_2;
                    finally {
                        throw var0_2;
                    }
                }
            }
        }

        public static FlickrPhotoMetadata parseFlickrPhoto(Cursor cursor) {
            FlickrPhotoMetadata flickrPhotoMetadata = new FlickrPhotoMetadata();
            flickrPhotoMetadata._id = HelperUtils.getLong(cursor, "_id", null);
            flickrPhotoMetadata.id = HelperUtils.getString(cursor, "flickr_id", null);
            flickrPhotoMetadata.urlZ = HelperUtils.getString(cursor, "urlz", null);
            flickrPhotoMetadata.loadDate = HelperUtils.getLong(cursor, "load_date", null);
            return flickrPhotoMetadata;
        }

        public static void removeFlickrPhotosMetadata(Context context, FlickrPhotoMetadata flickrPhotoMetadata) {
            context.getContentResolver().delete(FLICKR_PHOTOS_METADATA_URI, "flickr_id=?", new String[]{flickrPhotoMetadata.id});
        }

        public static void requestFlickrSync(Context context, long l) {
            context.startService(FlickrRequestHandler.createSyncFlickrIntent(context, l));
        }

        public static void saveFlickrPhotosMetadata(Context context, FlickrPhotoMetadata flickrPhotoMetadata) {
            ContentValues contentValues = new ContentValues(3);
            HelperUtils.put(contentValues, "flickr_id", flickrPhotoMetadata.id);
            HelperUtils.put(contentValues, "urlz", flickrPhotoMetadata.urlZ);
            HelperUtils.put(contentValues, "load_date", flickrPhotoMetadata.loadDate);
            context.getContentResolver().insert(FLICKR_PHOTOS_METADATA_URI, contentValues);
        }
    }

    public static abstract class HistoryHelper {
        public static void requestSendHistory(Context context, long l, long l2) {
            context.startService(SendHistoryRequestHandler.createSendHistoryIntent(context, l, l2));
        }

        public static void requestSendOldHistory(Context context, long l) {
            context.startService(SendHistoryRequestHandler.createSendOldHistoryIntent(context, l));
        }
    }

    public static abstract class NotesHelper {
        public static final String[] NOTES_LIST_PROJECTION = new String[]{"_id", "note_type", "icon_res", "text_res", "note_text"};
        private static final int[] PREDEFINED_BP_NOTES_IDS = new int[]{1, 2, 3, 4};
        private static final int[] PREDEFINED_WEIGHT_NOTES_IDS = new int[]{11, 10, 9, 8, 7, 6};

        public static void addMeasurementNoteAsync(Context context, long l, MeasurementNote measurementNote) {
            ContentValues contentValues = new ContentValues(7);
            HelperUtils.put(contentValues, "user_id", measurementNote.userId);
            HelperUtils.put(contentValues, "measurement_type", measurementNote.measurementType);
            HelperUtils.put(contentValues, "note_type", measurementNote.noteType);
            HelperUtils.put(contentValues, "icon_res", measurementNote.iconRes);
            HelperUtils.put(contentValues, "text_res", measurementNote.textRes);
            HelperUtils.put(contentValues, "note_text", measurementNote.noteText);
            HelperUtils.put(contentValues, "last_used", measurementNote.lastUsed);
            measurementNote = NotesHelper.buildNotesUri(l);
            new AsyncQueryHandler(context.getContentResolver()){}.startInsert(0, null, (Uri)measurementNote, contentValues);
        }

        public static void addPredefinedBPMeasurementNotes(Context context, long l) {
            Uri uri = NotesHelper.buildNotesUri(l);
            ArrayList<ContentProviderOperation> arrayList = new ArrayList<ContentProviderOperation>(PREDEFINED_BP_NOTES_IDS.length);
            for (int i = 0; i < PREDEFINED_BP_NOTES_IDS.length; ++i) {
                ContentValues contentValues = new ContentValues(6);
                HelperUtils.put(contentValues, "user_id", l);
                HelperUtils.put(contentValues, "measurement_type", "bp");
                HelperUtils.put(contentValues, "note_type", 0);
                HelperUtils.put(contentValues, "icon_res", PREDEFINED_BP_NOTES_IDS[i]);
                HelperUtils.put(contentValues, "text_res", PREDEFINED_BP_NOTES_IDS[i]);
                HelperUtils.put(contentValues, "last_used", System.currentTimeMillis() + (long)i);
                arrayList.add(ContentProviderOperation.newInsert((Uri)uri).withValues(contentValues).build());
            }
            try {
                context.getContentResolver().applyBatch("com.getqardio.android", arrayList);
                return;
            }
            catch (RemoteException remoteException) {
                Timber.e(remoteException);
                return;
            }
            catch (OperationApplicationException operationApplicationException) {
                Timber.e(operationApplicationException);
                return;
            }
        }

        public static void addPredefinedWeightMeasurementNotes(Context context, long l) {
            Uri uri = NotesHelper.buildNotesUri(l);
            ArrayList<ContentProviderOperation> arrayList = new ArrayList<ContentProviderOperation>(PREDEFINED_WEIGHT_NOTES_IDS.length);
            for (int i = 0; i < PREDEFINED_WEIGHT_NOTES_IDS.length; ++i) {
                ContentValues contentValues = new ContentValues(6);
                HelperUtils.put(contentValues, "user_id", l);
                HelperUtils.put(contentValues, "measurement_type", "weight");
                HelperUtils.put(contentValues, "note_type", 0);
                HelperUtils.put(contentValues, "icon_res", PREDEFINED_WEIGHT_NOTES_IDS[i]);
                HelperUtils.put(contentValues, "text_res", PREDEFINED_WEIGHT_NOTES_IDS[i]);
                HelperUtils.put(contentValues, "last_used", System.currentTimeMillis() + (long)i);
                arrayList.add(ContentProviderOperation.newInsert((Uri)uri).withValues(contentValues).build());
            }
            try {
                context.getContentResolver().applyBatch("com.getqardio.android", arrayList);
                return;
            }
            catch (RemoteException remoteException) {
                Timber.e(remoteException);
                return;
            }
            catch (OperationApplicationException operationApplicationException) {
                Timber.e(operationApplicationException);
                return;
            }
        }

        private static Uri buildNotesUri(long l) {
            return AppProvideContract.BASE_CONTENT_URI.buildUpon().appendPath("users").appendPath(Long.toString(l)).appendPath("notes").build();
        }

        public static void deleteMeasurementNoteAsync(Context context, long l, long l2) {
            Uri uri = Uri.withAppendedPath((Uri)NotesHelper.buildNotesUri(l), (String)Long.toString(l2));
            new AsyncQueryHandler(context.getContentResolver()){}.startDelete(0, null, uri, null, null);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public static CursorLoader getNotesLoader(Context context, long l, String string2, String[] arrstring, boolean bl) {
            String string3;
            Uri uri = NotesHelper.buildNotesUri(l);
            if (bl) {
                string3 = "last_used DESC";
                do {
                    return new CursorLoader(context, uri, arrstring, "measurement_type=?", new String[]{string2}, string3);
                    break;
                } while (true);
            }
            string3 = null;
            return new CursorLoader(context, uri, arrstring, "measurement_type=?", new String[]{string2}, string3);
        }

        public static boolean hasPredefinedBPMeasurementNotes(Context context, long l) {
            return context.getSharedPreferences("notes_sp_storage", 0).getBoolean(Long.toString(l), false);
        }

        public static boolean hasPredefinedWeightMeasurementNotes(Context context, long l) {
            return context.getSharedPreferences("weight_notes_sp_storage", 0).getBoolean(Long.toString(l), false);
        }

        public static MeasurementNote parseMeasurementNote(Cursor cursor) {
            MeasurementNote measurementNote = new MeasurementNote();
            measurementNote._id = HelperUtils.getLong(cursor, "_id", null);
            measurementNote.measurementType = HelperUtils.getString(cursor, "measurement_type", null);
            measurementNote.userId = HelperUtils.getLong(cursor, "user_id", null);
            measurementNote.noteType = HelperUtils.getInt(cursor, "note_type", null);
            measurementNote.iconRes = HelperUtils.getInt(cursor, "icon_res", null);
            measurementNote.textRes = HelperUtils.getInt(cursor, "text_res", null);
            measurementNote.noteText = HelperUtils.getString(cursor, "note_text", null);
            measurementNote.lastUsed = HelperUtils.getDate(cursor, "last_used", null);
            return measurementNote;
        }

        public static void repairPredefinedBPMeasurementNotes(Context context, long l) {
            Uri uri = NotesHelper.buildNotesUri(l);
            if (context.getContentResolver().delete(uri, "measurement_type = ? AND text_res > ?", new String[]{"bp", "100"}) > 0) {
                NotesHelper.addPredefinedBPMeasurementNotes(context, l);
            }
        }

        public static void setHasPredefinedBPMeasurementNotes(Context context, long l, boolean bl) {
            context = context.getSharedPreferences("notes_sp_storage", 0).edit();
            try {
                context.putBoolean(Long.toString(l), bl);
                return;
            }
            finally {
                context.commit();
            }
        }

        public static void setHasPredefinedWeightMeasurementNotes(Context context, long l, boolean bl) {
            context = context.getSharedPreferences("weight_notes_sp_storage", 0).edit();
            try {
                context.putBoolean(Long.toString(l), bl);
                return;
            }
            finally {
                context.commit();
            }
        }

        public static void setUsageDateAsync(Context context, long l, long l2, long l3) {
            Uri uri = Uri.withAppendedPath((Uri)NotesHelper.buildNotesUri(l), (String)Long.toString(l2));
            ContentValues contentValues = new ContentValues(1);
            HelperUtils.put(contentValues, "last_used", l3);
            new AsyncQueryHandler(context.getContentResolver()){}.startUpdate(0, null, uri, contentValues, null, null);
        }

        public static void updateMeasurementNoteAsync(Context context, long l, MeasurementNote measurementNote) {
            Uri uri = Uri.withAppendedPath((Uri)NotesHelper.buildNotesUri(l), (String)Long.toString(measurementNote._id));
            ContentValues contentValues = new ContentValues(7);
            HelperUtils.put(contentValues, "user_id", measurementNote.userId);
            HelperUtils.put(contentValues, "measurement_type", measurementNote.measurementType);
            HelperUtils.put(contentValues, "note_type", measurementNote.noteType);
            HelperUtils.put(contentValues, "icon_res", measurementNote.iconRes);
            HelperUtils.put(contentValues, "text_res", measurementNote.textRes);
            HelperUtils.put(contentValues, "note_text", measurementNote.noteText);
            HelperUtils.put(contentValues, "last_used", measurementNote.lastUsed);
            new AsyncQueryHandler(context.getContentResolver()){}.startUpdate(0, null, uri, contentValues, null, null);
        }

    }

    public static abstract class OnBoardingHelper {
        public static boolean isOnboardingFinished(Context context, long l) {
            return context.getSharedPreferences("qbase_sp_storage", 0).getBoolean(Long.toString(l), false);
        }

        public static void setOnboardingFinished(Context context, long l, boolean bl) {
            context = context.getSharedPreferences("qbase_sp_storage", 0).edit();
            context.putBoolean(Long.toString(l), bl);
            context.commit();
        }
    }

    public static abstract class PregnancyDataHelper {
        private static final String[] PREGNANCY_HISTORY_PROJECTION = new String[]{"_id", "start_date", "due_date", "end_date"};

        public static Uri buildUriForUser(long l) {
            return AppProvideContract.BASE_CONTENT_URI.buildUpon().appendPath("users").appendPath(String.valueOf(l)).appendPath("pregnancy_mode_data").build();
        }

        public static boolean changeSyncStatus(Context context, long l, long l2, int n) {
            Uri uri = PregnancyDataHelper.buildUriForUser(l);
            ContentValues contentValues = new ContentValues(1);
            HelperUtils.put(contentValues, "sync_status", n);
            return context.getContentResolver().update(uri, contentValues, "_id = ? ", new String[]{String.valueOf(l2)}) > 0;
        }

        public static Cursor getPregnancyDataForSync(Context context, long l) {
            Uri uri = PregnancyDataHelper.buildUriForUser(l);
            return context.getContentResolver().query(uri, null, "(sync_status > 0)", null, null);
        }

        public static boolean isPregnancyModeActive(Context context, long l) {
            boolean bl = false;
            if (context != null) {
                bl = context.getSharedPreferences("PREGNANCY_MODE_SP_STORAGE", 0).getBoolean(Long.toString(l), false);
            }
            return bl;
        }

        public static void requestPregnancyModeGetHistory(Context context, long l) {
            context.startService(PregnancyModeRequestHandler.createPregnancyModeGetHistoryIntent(context, l));
        }

        private static void requestPregnancyModeSync(Context context, long l) {
            context.startService(PregnancyModeRequestHandler.createSyncPregnancyModeIntent(context, l));
        }

        public static void requestPregnancyModeUpdate(Context context, long l) {
            context.startService(PregnancyModeRequestHandler.createPregnancyModeUpdateIntent(context, l));
        }

        /*
         * Enabled aggressive block sorting
         */
        public static void savePregnancyData(Context context, long l, PregnancyData pregnancyData, boolean bl) {
            int n;
            Uri uri = PregnancyDataHelper.buildUriForUser(l);
            ContentValues contentValues = new ContentValues();
            if (bl) {
                n = pregnancyData.syncStatus == null ? 1 : pregnancyData.syncStatus | 1;
                pregnancyData.syncStatus = n;
            }
            contentValues.put("sync_status", pregnancyData.syncStatus);
            contentValues.put("user_id", Long.valueOf(l));
            contentValues.put("cloud_id", pregnancyData.cloudId);
            if (pregnancyData.isShowWeight != null) {
                contentValues.put("is_show_weight", pregnancyData.isShowWeight);
            }
            Object object = pregnancyData.startDate != null ? Long.valueOf(pregnancyData.startDate.getTime()) : null;
            contentValues.put("start_date", (Long)object);
            object = pregnancyData.dueDate != null ? Long.valueOf(pregnancyData.dueDate.getTime()) : null;
            contentValues.put("due_date", (Long)object);
            contentValues.put("start_weight", Float.valueOf(pregnancyData.prePregnancyWeight));
            object = pregnancyData.endDate != null ? Long.valueOf(pregnancyData.endDate.getTime()) : null;
            contentValues.put("end_date", (Long)object);
            n = 0;
            if (pregnancyData.id != null || pregnancyData.cloudId != null) {
                String string2 = null;
                object = null;
                if (pregnancyData.id != null) {
                    string2 = "_id = ? ";
                    object = new String[]{String.valueOf(pregnancyData.id)};
                } else if (pregnancyData.cloudId != null) {
                    string2 = "cloud_id = ? ";
                    object = new String[]{String.valueOf(pregnancyData.cloudId)};
                }
                n = context.getContentResolver().update(uri, contentValues, string2, object);
            }
            if (n == 0) {
                context.getContentResolver().insert(uri, contentValues);
            }
            if (bl) {
                PregnancyDataHelper.requestPregnancyModeSync(context, l);
            }
        }

        public static void setPregnancyModeActive(Context context, long l, boolean bl) {
            context = context.getSharedPreferences("PREGNANCY_MODE_SP_STORAGE", 0).edit();
            context.putBoolean(Long.toString(l), bl);
            context.apply();
        }

        public static void stopCurrentPregnancy(Context context, long l) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("end_date", Long.valueOf(System.currentTimeMillis()));
            contentValues.put("sync_status", Integer.valueOf(2));
            context.getContentResolver().update(PregnancyDataHelper.buildUriForUser(l), contentValues, "end_date is null", null);
            PregnancyDataHelper.requestPregnancyModeSync(context, l);
        }

        public static boolean updateCloudId(Context context, long l, long l2, long l3) {
            Uri uri = PregnancyDataHelper.buildUriForUser(l);
            ContentValues contentValues = new ContentValues(1);
            HelperUtils.put(contentValues, "cloud_id", l3);
            return context.getContentResolver().update(uri, contentValues, "_id = ? ", new String[]{String.valueOf(l2)}) > 0;
        }
    }

    public static abstract class ProfileHelper {
        public static final String[] FIRST_NAME_PROJECTION;
        public static final String[] PROFILE_SCREEN_PROJECTION;
        public static final String[] SET_GOAL_PROJECTION;
        public static final String[] WEIGHT_UNIT_PROJECTION;

        static {
            PROFILE_SCREEN_PROJECTION = new String[]{"user_id", "first_name", "last_name", "email", "gender", "height", "height_unit", "weight", "weight_unit", "dob", "locale", "sync_status", "doctor_email", "doctor_name"};
            FIRST_NAME_PROJECTION = new String[]{"first_name"};
            SET_GOAL_PROJECTION = new String[]{"weight", "height"};
            WEIGHT_UNIT_PROJECTION = new String[]{"weight_unit"};
        }

        private static Uri buildProfileUri(long l) {
            if (l < 0L) {
                throw new RuntimeException("User id must be >= 0");
            }
            return AppProvideContract.BASE_CONTENT_URI.buildUpon().appendPath("users").appendPath(Long.toString(l)).appendPath("profile").build();
        }

        public static boolean changeSyncStatus(Context context, long l, int n) {
            Uri uri = ProfileHelper.buildProfileUri(l);
            ContentValues contentValues = new ContentValues(1);
            HelperUtils.put(contentValues, "sync_status", n);
            return context.getContentResolver().update(uri, contentValues, null, null) > 0;
        }

        public static int getHeightUnit(Context context, long l) {
            block5: {
                Uri uri = ProfileHelper.buildProfileUri(l);
                if ((context = context.getContentResolver().query(uri, WEIGHT_UNIT_PROJECTION, null, null, null, null)) != null) {
                    if (!context.moveToFirst()) break block5;
                    int n = HelperUtils.getInt((Cursor)context, "height_unit", (Integer)0);
                    return n;
                }
            }
            return 0;
            finally {
                if (context != null) {
                    context.close();
                }
            }
        }

        public static Profile getProfileForUser(Context object, long l) {
            Uri uri = ProfileHelper.buildProfileUri(l);
            Object var3_4 = null;
            uri = object.getContentResolver().query(uri, null, null, null, null);
            object = var3_4;
            try {
                if (uri.moveToFirst()) {
                    object = ProfileHelper.parseProfile((Cursor)uri);
                }
                return object;
            }
            finally {
                uri.close();
            }
        }

        public static CursorLoader getProfileLoader(Context context, long l, String[] arrstring) {
            return new CursorLoader(context, ProfileHelper.buildProfileUri(l), arrstring, null, null, null);
        }

        public static int getWeightUnit(Context context, long l) {
            block5: {
                Uri uri = ProfileHelper.buildProfileUri(l);
                if ((context = context.getContentResolver().query(uri, WEIGHT_UNIT_PROJECTION, null, null, null, null)) != null) {
                    if (!context.moveToFirst()) break block5;
                    int n = HelperUtils.getInt((Cursor)context, "weight_unit", (Integer)0);
                    return n;
                }
            }
            return 0;
            finally {
                if (context != null) {
                    context.close();
                }
            }
        }

        public static float parseHeight(Cursor cursor) {
            return HelperUtils.getFloat(cursor, "height", Float.valueOf(0.0f)).floatValue();
        }

        public static Profile parseProfile(Cursor cursor) {
            Profile profile = new Profile();
            profile._id = HelperUtils.getLong(cursor, "_id", profile._id);
            profile.userId = HelperUtils.getLong(cursor, "user_id", profile.userId);
            profile.refId = HelperUtils.getLong(cursor, "ref_id", profile.refId);
            profile.syncStatus = HelperUtils.getInt(cursor, "sync_status", profile.syncStatus);
            profile.setEmail(HelperUtils.getString(cursor, "email", profile.getEmail()));
            profile.firstName = HelperUtils.getString(cursor, "first_name", profile.firstName);
            profile.lastName = HelperUtils.getString(cursor, "last_name", profile.lastName);
            profile.dob = HelperUtils.getDate(cursor, "dob", profile.dob);
            profile.height = HelperUtils.getFloat(cursor, "height", profile.height);
            profile.heightUnit = HelperUtils.getInt(cursor, "height_unit", profile.heightUnit);
            profile.weight = HelperUtils.getFloat(cursor, "weight", profile.weight);
            profile.weightUnit = HelperUtils.getInt(cursor, "weight_unit", profile.weightUnit);
            profile.address = HelperUtils.getString(cursor, "address", profile.address);
            profile.phone = HelperUtils.getString(cursor, "phone", profile.phone);
            profile.gender = HelperUtils.getInt(cursor, "gender", profile.gender);
            profile.latitude = HelperUtils.getInt(cursor, "latitude", profile.latitude);
            profile.longitude = HelperUtils.getInt(cursor, "longitude", profile.longitude);
            profile.zip = HelperUtils.getString(cursor, "zip", profile.zip);
            profile.state = HelperUtils.getString(cursor, "state", profile.state);
            profile.country = HelperUtils.getString(cursor, "country", profile.country);
            profile.locale = HelperUtils.getString(cursor, "locale", profile.locale);
            profile.doctorEmail = HelperUtils.getString(cursor, "doctor_email", profile.doctorEmail);
            profile.doctorName = HelperUtils.getString(cursor, "doctor_name", profile.doctorName);
            profile.qbVisibleName = HelperUtils.getString(cursor, "qb_visible_name", profile.qbVisibleName);
            return profile;
        }

        public static float parseWeight(Cursor cursor) {
            return HelperUtils.getFloat(cursor, "weight", Float.valueOf(0.0f)).floatValue();
        }

        public static void requestProfileSync(Context context, long l, boolean bl, String string2) {
            context.startService(ProfileRequestHandler.createSyncProfileIntent(context, l, bl, string2));
        }

        public static void requestProfileUpdate(Context context, long l) {
            context.startService(ProfileRequestHandler.createGetProfileIntent(context, l));
        }

        public static void rollBackEmailChanges(Context context, long l) {
            Uri uri = ProfileHelper.buildProfileUri(l);
            String string2 = AuthHelper.getUserEmail(context, l);
            if (!TextUtils.isEmpty((CharSequence)string2)) {
                ContentValues contentValues = new ContentValues(1);
                contentValues.put("email", string2);
                context.getContentResolver().update(uri, contentValues, null, null);
            }
        }

        public static void saveProfile(Context context, Profile profile, boolean bl) {
            ProfileHelper.saveProfile(context, profile, bl, false, null);
        }

        /*
         * Enabled aggressive block sorting
         */
        public static void saveProfile(Context context, Profile profile, boolean bl, boolean bl2, String string2) {
            Uri uri = ProfileHelper.buildProfileUri(profile.userId);
            if (bl) {
                int n = profile.syncStatus == null ? 1 : profile.syncStatus | 1;
                profile.syncStatus = n;
            }
            ContentValues contentValues = new ContentValues(21);
            HelperUtils.put(contentValues, "ref_id", profile.refId);
            HelperUtils.put(contentValues, "sync_status", profile.syncStatus);
            HelperUtils.put(contentValues, "email", profile.getEmail());
            HelperUtils.put(contentValues, "first_name", profile.firstName);
            HelperUtils.put(contentValues, "last_name", profile.lastName);
            HelperUtils.put(contentValues, "dob", profile.dob);
            HelperUtils.put(contentValues, "height", profile.height);
            HelperUtils.put(contentValues, "height_unit", profile.heightUnit);
            HelperUtils.put(contentValues, "weight", profile.weight);
            HelperUtils.put(contentValues, "weight_unit", profile.weightUnit);
            HelperUtils.put(contentValues, "address", profile.address);
            HelperUtils.put(contentValues, "phone", profile.phone);
            HelperUtils.put(contentValues, "gender", profile.gender);
            HelperUtils.put(contentValues, "latitude", profile.latitude);
            HelperUtils.put(contentValues, "longitude", profile.longitude);
            HelperUtils.put(contentValues, "zip", profile.zip);
            HelperUtils.put(contentValues, "state", profile.state);
            HelperUtils.put(contentValues, "country", profile.country);
            HelperUtils.put(contentValues, "locale", profile.locale);
            HelperUtils.put(contentValues, "doctor_email", profile.doctorEmail);
            HelperUtils.put(contentValues, "doctor_name", profile.doctorName);
            HelperUtils.put(contentValues, "qb_visible_name", profile.qbVisibleName);
            if (context.getContentResolver().update(uri, contentValues, null, null) == 0) {
                context.getContentResolver().insert(uri, contentValues);
            }
            if (bl) {
                ProfileHelper.requestProfileSync(context, profile.userId, bl2, string2);
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        public static void setProfileWeight(Context context, long l, float f, int n, Integer n2) {
            Uri uri = ProfileHelper.buildProfileUri(l);
            int n3 = n2 == null ? 1 : n2 | 1;
            n2 = new ContentValues(4);
            HelperUtils.put((ContentValues)n2, "sync_status", n3);
            HelperUtils.put((ContentValues)n2, "weight", Float.valueOf(f));
            HelperUtils.put((ContentValues)n2, "weight_unit", n);
            if (context.getContentResolver().update(uri, (ContentValues)n2, null, null) > 0) {
                ProfileHelper.requestProfileSync(context, l, false, null);
            }
        }
    }

    public static abstract class QbUpdateDialogWasShown {
        public static boolean isWasShown(Context context, long l) {
            return context.getSharedPreferences("qbase_update_dialog_was_shown_storage", 0).getBoolean(Long.toString(l), false);
        }

        public static void setWasShown(Context context, long l) {
            context = context.getSharedPreferences("qbase_update_dialog_was_shown_storage", 0).edit();
            context.putBoolean(Long.toString(l), true);
            context.apply();
        }
    }

    public static abstract class ReminderHelper {
        private static Uri buildReminderUri(long l, long l2) {
            return AppProvideContract.BASE_CONTENT_URI.buildUpon().appendPath("users").appendPath(Long.toString(l)).appendPath("reminders").appendPath(Long.toString(l2)).build();
        }

        private static Uri buildRemindersUri(long l) {
            return AppProvideContract.BASE_CONTENT_URI.buildUpon().appendPath("users").appendPath(Long.toString(l)).appendPath("reminders").build();
        }

        /*
         * Enabled aggressive block sorting
         */
        public static void createReminder(Context context, long l, Reminder reminder) {
            Uri uri = ReminderHelper.buildRemindersUri(l);
            int n = reminder.syncStatus == null ? 1 : reminder.syncStatus | 1;
            reminder.syncStatus = n;
            ContentValues contentValues = ReminderHelper.getReminderContentValues(reminder);
            context.getContentResolver().insert(uri, contentValues);
            ReminderHelper.requestReminderSync(context, l, new String[]{reminder.type});
        }

        /*
         * Enabled aggressive block sorting
         */
        public static boolean deleteReminder(Context context, long l, Reminder reminder) {
            Uri uri = ReminderHelper.buildReminderUri(l, reminder._id);
            int n = reminder.syncStatus == null ? 4 : reminder.syncStatus | 4;
            reminder.syncStatus = n;
            ContentValues contentValues = new ContentValues(1);
            HelperUtils.put(contentValues, "sync_status", reminder.syncStatus);
            n = context.getContentResolver().update(uri, contentValues, null, null);
            ReminderHelper.requestReminderSync(context, l, new String[]{reminder.type});
            ReminderHelper.requestReminderAlarmUpdate(context);
            return n > 0;
        }

        public static boolean deleteReminderAfterSync(Context context, long l, long l2) {
            Uri uri = ReminderHelper.buildReminderUri(l, l2);
            return context.getContentResolver().delete(uri, null, null) > 0;
        }

        public static List<Reminder> getActiveRemindersForAlarm(Context context) {
            Long l = CustomApplication.getApplication().getLastUserId();
            if (l == null) {
                return null;
            }
            l = ReminderHelper.buildRemindersUri(l);
            String string2 = "(sync_status | " + 3 + " = " + 3 + ")";
            return ReminderHelper.parseReminderList(context.getContentResolver().query((Uri)l, new String[]{"remind_time", "days", "type"}, string2, null, null));
        }

        private static Set<Long> getExistingRemindersIds(Context context, long l, String[] object) {
            String string2;
            Uri uri = ReminderHelper.buildRemindersUri(l);
            String string3 = string2 = null;
            if (object != null) {
                string3 = string2;
                if (((String[])object).length > 0) {
                    string2 = "type='" + object[0] + "'";
                    int n = 1;
                    do {
                        string3 = string2;
                        if (n >= ((String[])object).length) break;
                        string2 = string2 + " OR type='" + object[n] + "'";
                        ++n;
                    } while (true);
                }
            }
            context = context.getContentResolver().query(uri, new String[]{"reminder_id"}, string3, null, null);
            object = new HashSet(context.getCount());
            try {
                context.moveToFirst();
                while (!context.isAfterLast()) {
                    l = HelperUtils.getLong((Cursor)context, "reminder_id", (Long)-1L);
                    if (l != -1L) {
                        object.add(l);
                    }
                    context.moveToNext();
                }
            }
            finally {
                context.close();
            }
            return object;
        }

        public static Reminder getReminderById(Context object, long l, long l2) {
            Cursor cursor;
            block5: {
                Uri uri = ReminderHelper.buildRemindersUri(l);
                cursor = object.getContentResolver().query(uri, null, "_id=?", new String[]{String.valueOf(l2)}, null);
                uri = null;
                object = uri;
                if (cursor != null) {
                    object = uri;
                    if (!cursor.moveToFirst()) break block5;
                    object = ReminderHelper.parseReminder(cursor);
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
         */
        private static ContentValues getReminderContentValues(Reminder reminder) {
            ContentValues contentValues = new ContentValues(6);
            HelperUtils.put(contentValues, "user_id", reminder.userId);
            long l = reminder.reminderId == null ? -1L : reminder.reminderId;
            HelperUtils.put(contentValues, "reminder_id", l);
            HelperUtils.put(contentValues, "sync_status", reminder.syncStatus);
            HelperUtils.put(contentValues, "days", reminder.days);
            HelperUtils.put(contentValues, "remind_time", reminder.remindTime);
            HelperUtils.put(contentValues, "repeat", Convert.booleanToInteger(reminder.repeat));
            HelperUtils.put(contentValues, "type", reminder.type);
            return contentValues;
        }

        public static Cursor getRemindersForSync(Context context, String[] arrstring, long l, String[] arrstring2) {
            Uri uri = ReminderHelper.buildRemindersUri(l);
            String string2 = "(sync_status > 0)";
            if (arrstring2.length > 0) {
                string2 = "(sync_status > 0)" + " AND (";
                string2 = string2 + "type='" + arrstring2[0] + "'";
                for (int i = 1; i < arrstring2.length; ++i) {
                    string2 = string2 + " OR type='" + arrstring2[i] + "'";
                }
                string2 = string2 + ")";
            }
            return context.getContentResolver().query(uri, arrstring, string2, null, null);
        }

        public static CursorLoader getRemindersLoader(Context context, long l, String string2, String[] arrstring) {
            String string3;
            Uri uri = ReminderHelper.buildRemindersUri(l);
            String[] arrstring2 = null;
            String string4 = string3 = "(sync_status | " + 3 + " = " + 3 + ")";
            if (string2 != null) {
                string4 = string3 + " AND (type=?)";
                arrstring2 = new String[]{string2};
            }
            return new CursorLoader(context, uri, arrstring, string4, arrstring2, null);
        }

        /*
         * Enabled aggressive block sorting
         */
        public static Reminder parseReminder(Cursor cursor) {
            Reminder reminder = new Reminder();
            reminder._id = HelperUtils.getLong(cursor, "_id", null);
            reminder.userId = HelperUtils.getLong(cursor, "user_id", null);
            long l = HelperUtils.getLong(cursor, "reminder_id", (Long)-1L);
            Long l2 = l == -1L ? null : Long.valueOf(l);
            reminder.reminderId = l2;
            reminder.syncStatus = HelperUtils.getInt(cursor, "sync_status", null);
            reminder.days = HelperUtils.getInt(cursor, "days", (Integer)0);
            reminder.remindTime = HelperUtils.getLong(cursor, "remind_time", (Long)0L);
            reminder.repeat = Convert.integerToBoolean(HelperUtils.getInt(cursor, "days", (Integer)0));
            reminder.type = HelperUtils.getString(cursor, "type", "bp");
            return reminder;
        }

        public static List<Reminder> parseReminderList(Cursor cursor) {
            ArrayList<Reminder> arrayList = new ArrayList<Reminder>(cursor.getCount());
            try {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    arrayList.add(ReminderHelper.parseReminder(cursor));
                    cursor.moveToNext();
                }
            }
            finally {
                cursor.close();
            }
            return arrayList;
        }

        public static void requestReminderAlarmUpdate(Context context) {
            ReminderAlarmGenerator.enqueueWork(context.getApplicationContext(), ReminderAlarmGenerator.class, 1000, new Intent(context, ReminderAlarmGenerator.class));
        }

        public static void requestReminderSync(Context context, long l, String[] arrstring) {
            context.startService(ReminderRequestHandler.createSyncRemindersIntent(context, l, arrstring));
        }

        public static void requestReminderUpdate(Context context, long l, String[] arrstring) {
            context.startService(ReminderRequestHandler.createUpdateRemindersIntent(context, l, arrstring));
        }

        /*
         * Enabled aggressive block sorting
         */
        public static void saveReminder(Context context, long l, Reminder reminder) {
            if (reminder._id == null || reminder._id == -1L) {
                ReminderHelper.createReminder(context, l, reminder);
            } else {
                ReminderHelper.updateReminder(context, l, reminder);
            }
            ReminderHelper.requestReminderAlarmUpdate(context);
        }

        public static boolean updateIdAndSyncStatusAfterSync(Context context, long l, long l2, long l3, int n) {
            Uri uri = ReminderHelper.buildReminderUri(l, l2);
            ContentValues contentValues = new ContentValues(2);
            HelperUtils.put(contentValues, "sync_status", n);
            HelperUtils.put(contentValues, "reminder_id", l3);
            return context.getContentResolver().update(uri, contentValues, null, null) > 0;
        }

        /*
         * Enabled aggressive block sorting
         */
        public static boolean updateReminder(Context context, long l, Reminder reminder) {
            Uri uri = ReminderHelper.buildReminderUri(l, reminder._id);
            int n = reminder.syncStatus == null ? 2 : reminder.syncStatus | 2;
            reminder.syncStatus = n;
            ContentValues contentValues = ReminderHelper.getReminderContentValues(reminder);
            n = context.getContentResolver().update(uri, contentValues, null, null);
            ReminderHelper.requestReminderSync(context, l, new String[]{reminder.type});
            return n > 0;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static boolean updateRemindersAfterGet(Context context, long l, List<Reminder> iterator, String[] object) {
            Uri uri = ReminderHelper.buildRemindersUri(l);
            Object object2 = ReminderHelper.getExistingRemindersIds(context, l, (String[])object);
            object = new ArrayList();
            iterator = iterator.iterator();
            while (iterator.hasNext()) {
                Reminder reminder = (Reminder)((Object)iterator.next());
                ContentValues contentValues = ReminderHelper.getReminderContentValues(reminder);
                if (object2.contains(reminder.reminderId)) {
                    ((ArrayList)object).add(ContentProviderOperation.newUpdate((Uri)uri).withSelection("reminder_id=?", new String[]{Long.toString(reminder.reminderId)}).withValues(contentValues).build());
                    object2.remove(reminder.reminderId);
                    continue;
                }
                ((ArrayList)object).add(ContentProviderOperation.newInsert((Uri)uri).withValues(contentValues).build());
            }
            iterator = object2.iterator();
            while (iterator.hasNext()) {
                object2 = iterator.next();
                ((ArrayList)object).add(ContentProviderOperation.newDelete((Uri)uri).withSelection("reminder_id=?", new String[]{Long.toString((Long)object2)}).build());
            }
            boolean bl = true;
            try {
                context.getContentResolver().applyBatch("com.getqardio.android", (ArrayList)object);
            }
            catch (RemoteException remoteException) {
                bl = false;
                Timber.e(remoteException, "", new Object[0]);
            }
            catch (OperationApplicationException operationApplicationException) {
                bl = false;
                Timber.e(operationApplicationException, "", new Object[0]);
            }
            ReminderHelper.requestReminderAlarmUpdate(context);
            return bl;
        }
    }

    public static abstract class SettingsHelper {
        public static Uri buildSettingUri(long l) {
            return AppProvideContract.BASE_CONTENT_URI.buildUpon().appendPath("users").appendPath(Long.toString(l)).appendPath("settings").build();
        }

        public static boolean changeSyncStatus(Context context, long l, int n) {
            Uri uri = SettingsHelper.buildSettingUri(l);
            ContentValues contentValues = new ContentValues(1);
            HelperUtils.put(contentValues, "sync_status", n);
            return context.getContentResolver().update(uri, contentValues, null, null) > 0;
        }

        public static Settings getDefaultSettings(long l) {
            return Settings.getDefault(l);
        }

        public static Settings getSettings(Context object, long l) {
            Object var3_3 = null;
            Cursor cursor = object.getContentResolver().query(SettingsHelper.buildSettingUri(l), null, null, null, null);
            object = var3_3;
            try {
                if (cursor.moveToFirst()) {
                    object = SettingsHelper.parseSettings(cursor);
                }
                return object;
            }
            finally {
                cursor.close();
            }
        }

        public static CursorLoader getSettingsLoader(Context context, long l, String[] arrstring) {
            return new CursorLoader(context, SettingsHelper.buildSettingUri(l), arrstring, null, null, null);
        }

        public static Settings parseSettings(Cursor cursor) {
            Settings settings = new Settings();
            settings._id = HelperUtils.getLong(cursor, "_id", settings._id);
            settings.userId = HelperUtils.getLong(cursor, "user_id", settings.userId);
            settings.syncStatus = HelperUtils.getInt(cursor, "sync_status", settings.syncStatus);
            settings.measurementsNumber = HelperUtils.getInt(cursor, "measurements_number", settings.measurementsNumber);
            settings.pauseSize = HelperUtils.getInt(cursor, "pause_size", settings.pauseSize);
            settings.showPhoto = HelperUtils.getBoolean(cursor, "show_photo", settings.showPhoto);
            settings.allowLocation = HelperUtils.getBoolean(cursor, "allow_location", settings.allowLocation);
            settings.albums = HelperUtils.getInt(cursor, "albums", settings.albums);
            settings.allowBpImportSHealth = HelperUtils.getBoolean(cursor, "allow_import_shealth", settings.allowBpImportSHealth);
            settings.allowBpExportSHealth = HelperUtils.getBoolean(cursor, "allow_export_shealth", settings.allowBpExportSHealth);
            settings.allowWeightImportSHealth = HelperUtils.getBoolean(cursor, "allow_weight_import_shealth", settings.allowWeightImportSHealth);
            settings.allowWeightExportSHealth = HelperUtils.getBoolean(cursor, "allow_weight_export_shealth", settings.allowWeightExportSHealth);
            settings.allowNotifications = HelperUtils.getBoolean(cursor, "allow_notifications", settings.allowNotifications);
            settings.allowMixpanelNotifications = HelperUtils.getBoolean(cursor, "allow_mixpanel_notifications", settings.allowMixpanelNotifications);
            settings.allowIntegrationGoogleFit = HelperUtils.getBoolean(cursor, "allow_integration_google_fit", settings.allowIntegrationGoogleFit);
            return settings;
        }

        public static void requestSettingsSync(Context context, long l) {
            context.startService(SettingsRequestHandler.createSyncSettingsIntent(context, l));
        }

        public static void requestSettingsUpdate(Context context, long l) {
            context.startService(SettingsRequestHandler.createGetSettingsIntent(context, l));
        }

        /*
         * Enabled aggressive block sorting
         */
        public static void saveSettings(Context context, Settings settings, boolean bl) {
            Uri uri = SettingsHelper.buildSettingUri(settings.userId);
            if (bl) {
                int n = settings.syncStatus == null ? 1 : settings.syncStatus | 1;
                settings.syncStatus = n;
            }
            ContentValues contentValues = new ContentValues(11);
            HelperUtils.put(contentValues, "sync_status", settings.syncStatus);
            HelperUtils.put(contentValues, "measurements_number", settings.measurementsNumber);
            HelperUtils.put(contentValues, "pause_size", settings.pauseSize);
            HelperUtils.put(contentValues, "show_photo", settings.showPhoto);
            HelperUtils.put(contentValues, "allow_location", settings.allowLocation);
            HelperUtils.put(contentValues, "albums", settings.albums);
            HelperUtils.put(contentValues, "allow_export_shealth", settings.allowBpExportSHealth);
            HelperUtils.put(contentValues, "allow_import_shealth", settings.allowBpImportSHealth);
            HelperUtils.put(contentValues, "allow_weight_import_shealth", settings.allowWeightImportSHealth);
            HelperUtils.put(contentValues, "allow_weight_export_shealth", settings.allowWeightExportSHealth);
            HelperUtils.put(contentValues, "allow_notifications", settings.allowNotifications);
            HelperUtils.put(contentValues, "allow_mixpanel_notifications", settings.allowMixpanelNotifications);
            HelperUtils.put(contentValues, "allow_integration_google_fit", settings.allowIntegrationGoogleFit);
            if (context.getContentResolver().update(uri, contentValues, null, null) == 0) {
                context.getContentResolver().insert(uri, contentValues);
            }
            if (bl) {
                SettingsHelper.requestSettingsSync(context, settings.userId);
            }
        }
    }

    public static abstract class StatisticHelper {
        private static Uri buildStatisticUri(long l, String string2) {
            return AppProvideContract.BASE_CONTENT_URI.buildUpon().appendPath("users").appendPath(Long.toString(l)).appendPath("statistic").appendPath(string2).build();
        }

        public static Loader<Cursor> createGetStatisticLoader(Context context, long l, String string2) {
            return new CursorLoader(context, StatisticHelper.buildStatisticUri(l, string2), null, null, null, null);
        }

        public static Statistic getStatistic(Context object, long l, String string2) {
            Uri uri = StatisticHelper.buildStatisticUri(l, string2);
            string2 = null;
            uri = object.getContentResolver().query(uri, null, null, null, null);
            object = string2;
            try {
                if (uri.moveToFirst()) {
                    object = StatisticHelper.parseStatistic((Cursor)uri);
                }
                return object;
            }
            finally {
                uri.close();
            }
        }

        public static void insertStatistic(Context context, Statistic statistic) {
            if (statistic != null && !TextUtils.isEmpty((CharSequence)statistic.deviceId) && statistic.userId != -1L) {
                Uri uri = StatisticHelper.buildStatisticUri(statistic.userId, statistic.deviceId);
                ContentValues contentValues = new ContentValues();
                contentValues.put("measurements_count", Integer.valueOf(statistic.measurementsCount));
                contentValues.put("badMeasurementsCount", Integer.valueOf(statistic.badMeasurementsCount));
                contentValues.put("changedBatteriesCount", Integer.valueOf(statistic.changedBatteriesCount));
                contentValues.put("battery_status", Integer.valueOf(statistic.batteryStatus));
                contentValues.put("is_active", Boolean.valueOf(statistic.isActive));
                contentValues.put("firmware", statistic.firmware);
                context.getContentResolver().insert(uri, contentValues);
            }
        }

        public static Statistic parseStatistic(Cursor cursor) {
            Statistic statistic = new Statistic();
            statistic.userId = HelperUtils.getInt(cursor, "user_id", (Integer)-1).intValue();
            statistic.deviceId = HelperUtils.getString(cursor, "device_id", null);
            statistic.measurementsCount = HelperUtils.getInt(cursor, "measurements_count", (Integer)0);
            statistic.badMeasurementsCount = HelperUtils.getInt(cursor, "badMeasurementsCount", (Integer)0);
            statistic.changedBatteriesCount = HelperUtils.getInt(cursor, "changedBatteriesCount", (Integer)0);
            statistic.batteryStatus = HelperUtils.getInt(cursor, "battery_status", (Integer)0);
            statistic.isActive = HelperUtils.getBoolean(cursor, "is_active", (Boolean)false);
            statistic.firmware = HelperUtils.getString(cursor, "firmware", null);
            return statistic;
        }

        public static void requestGetStatistic(Context context, long l, String string2) {
            context.startService(StatisticRequestHandler.createGetStatisticIntent(context, l, string2));
        }

        public static void requestUpdateStatistic(Context context, long l, String string2) {
            context.startService(StatisticRequestHandler.createUpdateStatisticIntent(context, l, string2));
        }
    }

    public static abstract class SupportHelper {
        public static void requestSendSupport(Context context, long l, int n) {
            context.startService(SupportRequestHandler.createSendTicketIntent(context, l, n));
        }
    }

}

