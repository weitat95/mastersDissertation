/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ContentProvider
 *  android.content.ContentProviderOperation
 *  android.content.ContentProviderResult
 *  android.content.ContentValues
 *  android.content.Context
 *  android.content.OperationApplicationException
 *  android.content.UriMatcher
 *  android.database.Cursor
 *  android.database.sqlite.SQLiteDatabase
 *  android.database.sqlite.SQLiteOpenHelper
 *  android.net.Uri
 */
package com.getqardio.android.provider;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import com.getqardio.android.provider.AppSQLStorage;
import com.getqardio.android.provider.ClaimMeasurementItemUriHandler;
import com.getqardio.android.provider.ClaimMeasurementListUriHandler;
import com.getqardio.android.provider.CurrentGoalListUriHandler;
import com.getqardio.android.provider.DailyMeasurementListUriHandler;
import com.getqardio.android.provider.DailyWeightMeasurementListUriHandler;
import com.getqardio.android.provider.DeviceAssociationsItemUriHandler;
import com.getqardio.android.provider.DeviceAssociationsListUriHandler;
import com.getqardio.android.provider.DeviceItemUriHandler;
import com.getqardio.android.provider.DeviceListUriHandler;
import com.getqardio.android.provider.FaqItemUriHandler;
import com.getqardio.android.provider.FaqListUriHandler;
import com.getqardio.android.provider.FickrPhotoMetadataItemUriHandler;
import com.getqardio.android.provider.FickrPhotoMetadataListUriHandler;
import com.getqardio.android.provider.FollowerItemUriHandler;
import com.getqardio.android.provider.FollowerListUriHandler;
import com.getqardio.android.provider.FollowingItemUriHandler;
import com.getqardio.android.provider.FollowingListUriHandler;
import com.getqardio.android.provider.FollowingMetadataItemUriHandler;
import com.getqardio.android.provider.FollowingMetadataListUriHandler;
import com.getqardio.android.provider.FollowingUserInfoItemUriHandler;
import com.getqardio.android.provider.FollowingUserInfoListUriHandler;
import com.getqardio.android.provider.MeasurementItemUriHandler;
import com.getqardio.android.provider.MeasurementListUriHandler;
import com.getqardio.android.provider.MeasurementsHistoryItemUriHandler;
import com.getqardio.android.provider.MeasurementsHistoryListUriHandler;
import com.getqardio.android.provider.MonthlyMeasurementListUriHandler;
import com.getqardio.android.provider.MonthlyWeightMeasurementListUriHandler;
import com.getqardio.android.provider.NotesItemUriHandler;
import com.getqardio.android.provider.NotesListUriHandler;
import com.getqardio.android.provider.NotificationTokensItemUriHandler;
import com.getqardio.android.provider.NotificationTokensListUriHandler;
import com.getqardio.android.provider.PregnancyModeDataItemHandler;
import com.getqardio.android.provider.PregnancyModeDataListUriHandler;
import com.getqardio.android.provider.ProfileItemUriHandler;
import com.getqardio.android.provider.ReminderItemUriHandler;
import com.getqardio.android.provider.ReminderListUriHandler;
import com.getqardio.android.provider.SettingsItemUriHandler;
import com.getqardio.android.provider.StatisticItemUriHandler;
import com.getqardio.android.provider.SupportTicketsItemUriHandler;
import com.getqardio.android.provider.SupportTicketsListUriHandler;
import com.getqardio.android.provider.TooltipsListUriHandler;
import com.getqardio.android.provider.UriHandler;
import com.getqardio.android.provider.UserItemUriHandler;
import com.getqardio.android.provider.UserListUriHandler;
import com.getqardio.android.provider.VisitorMeasurementsItemUriHandler;
import com.getqardio.android.provider.VisitorMeasurementsListUriHandler;
import com.getqardio.android.provider.WeeklyMeasurementListUriHandler;
import com.getqardio.android.provider.WeeklyWeightMeasurementListUriHandler;
import com.getqardio.android.provider.WeightMeasurementItemUriHandler;
import com.getqardio.android.provider.WeightMeasurementListUriHandler;
import java.util.ArrayList;

public class AppProvider
extends ContentProvider {
    private static final UriMatcher uriMatcher = new UriMatcher(-1);
    private UriHandler handler;
    private AppSQLStorage sqlStorage;

    static {
        uriMatcher.addURI("com.getqardio.android", "faqs", 1);
        uriMatcher.addURI("com.getqardio.android", "faqs/#", 2);
        uriMatcher.addURI("com.getqardio.android", "devices", 3);
        uriMatcher.addURI("com.getqardio.android", "devices/#", 4);
        uriMatcher.addURI("com.getqardio.android", "users", 5);
        uriMatcher.addURI("com.getqardio.android", "users/#", 6);
        uriMatcher.addURI("com.getqardio.android", "users/#/profile", 7);
        uriMatcher.addURI("com.getqardio.android", "users/#/settings", 8);
        uriMatcher.addURI("com.getqardio.android", "users/#/bp_measurements", 9);
        uriMatcher.addURI("com.getqardio.android", "users/#/bp_measurements/#", 10);
        uriMatcher.addURI("com.getqardio.android", "users/#/reminders", 11);
        uriMatcher.addURI("com.getqardio.android", "users/#/reminders/#", 12);
        uriMatcher.addURI("com.getqardio.android", "users/#/followers", 13);
        uriMatcher.addURI("com.getqardio.android", "users/#/followers/#", 14);
        uriMatcher.addURI("com.getqardio.android", "users/#/followings", 15);
        uriMatcher.addURI("com.getqardio.android", "users/#/followings/#", 16);
        uriMatcher.addURI("com.getqardio.android", "users/#/bp_measurements/daily", 17);
        uriMatcher.addURI("com.getqardio.android", "users/#/bp_measurements/weekly", 18);
        uriMatcher.addURI("com.getqardio.android", "users/#/bp_measurements/monthly", 19);
        uriMatcher.addURI("com.getqardio.android", "tooltips", 20);
        uriMatcher.addURI("com.getqardio.android", "users/#/visitor_measurements", 21);
        uriMatcher.addURI("com.getqardio.android", "users/#/visitor_measurements/#", 22);
        uriMatcher.addURI("com.getqardio.android", "support_tickets", 23);
        uriMatcher.addURI("com.getqardio.android", "support_tickets/#", 24);
        uriMatcher.addURI("com.getqardio.android", "measurements_history", 25);
        uriMatcher.addURI("com.getqardio.android", "measurements_history/#", 26);
        uriMatcher.addURI("com.getqardio.android", "flickr_photo_metadata", 27);
        uriMatcher.addURI("com.getqardio.android", "flickr_photo_metadata/#", 28);
        uriMatcher.addURI("com.getqardio.android", "users/#/notes", 29);
        uriMatcher.addURI("com.getqardio.android", "users/#/notes/#", 30);
        uriMatcher.addURI("com.getqardio.android", "users/#/statistic/*", 31);
        uriMatcher.addURI("com.getqardio.android", "users/#/weight_measurements", 32);
        uriMatcher.addURI("com.getqardio.android", "users/#/weight_measurements/#", 33);
        uriMatcher.addURI("com.getqardio.android", "users/#/weight_measurements/daily", 34);
        uriMatcher.addURI("com.getqardio.android", "users/#/weight_measurements/weekly", 35);
        uriMatcher.addURI("com.getqardio.android", "users/#/weight_measurements/monthly", 36);
        uriMatcher.addURI("com.getqardio.android", "users/#/claim_measurements", 38);
        uriMatcher.addURI("com.getqardio.android", "users/#/claim_measurements/#", 37);
        uriMatcher.addURI("com.getqardio.android", "users/#/current_goal", 39);
        uriMatcher.addURI("com.getqardio.android", "users/#/device_associations", 40);
        uriMatcher.addURI("com.getqardio.android", "users/#/device_associations/#", 41);
        uriMatcher.addURI("com.getqardio.android", "users/#/pregnancy_mode_data/", 42);
        uriMatcher.addURI("com.getqardio.android", "users/#/pregnancy_mode_data/#", 43);
        uriMatcher.addURI("com.getqardio.android", "users/#/notifications_device_tokens", 44);
        uriMatcher.addURI("com.getqardio.android", "users/#/notifications_device_tokens/*", 45);
        uriMatcher.addURI("com.getqardio.android", "users/#/followings/user_info", 46);
        uriMatcher.addURI("com.getqardio.android", "users/#/followings/user_info/#", 47);
        uriMatcher.addURI("com.getqardio.android", "users/#/followings/user_info/#/metadata", 48);
        uriMatcher.addURI("com.getqardio.android", "users/#/followings/user_info/#/metadata/#", 49);
    }

    private static UriHandler createHandlers(SQLiteOpenHelper sQLiteOpenHelper) {
        UriHandler.Builder builder = new UriHandler.Builder();
        builder.addHandler(new UserListUriHandler(5, sQLiteOpenHelper)).addHandler(new UserItemUriHandler(6, sQLiteOpenHelper)).addHandler(new MeasurementListUriHandler(9, sQLiteOpenHelper)).addHandler(new MeasurementItemUriHandler(10, sQLiteOpenHelper)).addHandler(new WeightMeasurementListUriHandler(32, sQLiteOpenHelper)).addHandler(new WeightMeasurementItemUriHandler(33, sQLiteOpenHelper)).addHandler(new ClaimMeasurementItemUriHandler(37, sQLiteOpenHelper)).addHandler(new ClaimMeasurementListUriHandler(38, sQLiteOpenHelper)).addHandler(new DailyMeasurementListUriHandler(17, sQLiteOpenHelper)).addHandler(new WeeklyMeasurementListUriHandler(18, sQLiteOpenHelper)).addHandler(new MonthlyMeasurementListUriHandler(19, sQLiteOpenHelper)).addHandler(new DailyWeightMeasurementListUriHandler(34, sQLiteOpenHelper)).addHandler(new WeeklyWeightMeasurementListUriHandler(35, sQLiteOpenHelper)).addHandler(new MonthlyWeightMeasurementListUriHandler(36, sQLiteOpenHelper)).addHandler(new SettingsItemUriHandler(8, sQLiteOpenHelper)).addHandler(new ProfileItemUriHandler(7, sQLiteOpenHelper)).addHandler(new DeviceListUriHandler(3, sQLiteOpenHelper)).addHandler(new DeviceItemUriHandler(4, sQLiteOpenHelper)).addHandler(new ReminderListUriHandler(11, sQLiteOpenHelper)).addHandler(new ReminderItemUriHandler(12, sQLiteOpenHelper)).addHandler(new FaqListUriHandler(1, sQLiteOpenHelper)).addHandler(new FaqItemUriHandler(2, sQLiteOpenHelper)).addHandler(new FollowerListUriHandler(13, sQLiteOpenHelper)).addHandler(new FollowerItemUriHandler(14, sQLiteOpenHelper)).addHandler(new FollowingListUriHandler(15, sQLiteOpenHelper)).addHandler(new FollowingItemUriHandler(16, sQLiteOpenHelper)).addHandler(new TooltipsListUriHandler(20, sQLiteOpenHelper)).addHandler(new NotesListUriHandler(29, sQLiteOpenHelper)).addHandler(new NotesItemUriHandler(30, sQLiteOpenHelper)).addHandler(new VisitorMeasurementsListUriHandler(21, sQLiteOpenHelper)).addHandler(new VisitorMeasurementsItemUriHandler(22, sQLiteOpenHelper)).addHandler(new SupportTicketsListUriHandler(23, sQLiteOpenHelper)).addHandler(new SupportTicketsItemUriHandler(24, sQLiteOpenHelper)).addHandler(new MeasurementsHistoryListUriHandler(25, sQLiteOpenHelper)).addHandler(new MeasurementsHistoryItemUriHandler(26, sQLiteOpenHelper)).addHandler(new FickrPhotoMetadataListUriHandler(27, sQLiteOpenHelper)).addHandler(new FickrPhotoMetadataItemUriHandler(28, sQLiteOpenHelper)).addHandler(new StatisticItemUriHandler(31, sQLiteOpenHelper)).addHandler(new CurrentGoalListUriHandler(39, sQLiteOpenHelper)).addHandler(new DeviceAssociationsListUriHandler(40, sQLiteOpenHelper)).addHandler(new DeviceAssociationsItemUriHandler(41, sQLiteOpenHelper)).addHandler(new PregnancyModeDataListUriHandler(42, sQLiteOpenHelper)).addHandler(new PregnancyModeDataItemHandler(43, sQLiteOpenHelper)).addHandler(new NotificationTokensItemUriHandler(45, sQLiteOpenHelper)).addHandler(new NotificationTokensListUriHandler(44, sQLiteOpenHelper)).addHandler(new FollowingUserInfoListUriHandler(46, sQLiteOpenHelper)).addHandler(new FollowingUserInfoItemUriHandler(47, sQLiteOpenHelper)).addHandler(new FollowingMetadataListUriHandler(48, sQLiteOpenHelper)).addHandler(new FollowingMetadataItemUriHandler(49, sQLiteOpenHelper));
        return builder.create();
    }

    public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> arrayList) throws OperationApplicationException {
        SQLiteDatabase sQLiteDatabase = this.sqlStorage.getWritableDatabase();
        sQLiteDatabase.beginTransaction();
        int n = arrayList.size();
        ContentProviderResult[] arrcontentProviderResult = new ContentProviderResult[n];
        for (int i = 0; i < n; ++i) {
            arrcontentProviderResult[i] = arrayList.get(i).apply((ContentProvider)this, arrcontentProviderResult, i);
            continue;
        }
        try {
            sQLiteDatabase.setTransactionSuccessful();
            return arrcontentProviderResult;
        }
        catch (Throwable throwable) {
            throw throwable;
        }
        finally {
            sQLiteDatabase.endTransaction();
        }
    }

    public int delete(Uri uri, String string2, String[] arrstring) {
        return this.handler.delete(this.getContext(), uriMatcher.match(uri), uri, string2, arrstring);
    }

    public String getType(Uri uri) {
        throw new UnsupportedOperationException("AppProvider doesn't implement getType() method");
    }

    public Uri insert(Uri uri, ContentValues contentValues) {
        return this.handler.insert(this.getContext(), uriMatcher.match(uri), uri, contentValues);
    }

    public boolean onCreate() {
        this.sqlStorage = new AppSQLStorage(this.getContext());
        this.handler = AppProvider.createHandlers(this.sqlStorage);
        return true;
    }

    public Cursor query(Uri uri, String[] arrstring, String string2, String[] arrstring2, String string3) {
        return this.handler.query(this.getContext(), uriMatcher.match(uri), uri, arrstring, string2, arrstring2, string3);
    }

    public int update(Uri uri, ContentValues contentValues, String string2, String[] arrstring) {
        return this.handler.update(this.getContext(), uriMatcher.match(uri), uri, contentValues, string2, arrstring);
    }
}

