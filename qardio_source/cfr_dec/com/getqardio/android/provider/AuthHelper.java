/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.ContentResolver
 *  android.content.ContentValues
 *  android.content.Context
 *  android.content.CursorLoader
 *  android.content.Intent
 *  android.database.Cursor
 *  android.net.Uri
 *  android.text.TextUtils
 */
package com.getqardio.android.provider;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import com.getqardio.android.CustomApplication;
import com.getqardio.android.fcm.FCMManager;
import com.getqardio.android.io.network.request.CreateNewUserRequestHandler;
import com.getqardio.android.io.network.request.ForgotPasswordRequestHandler;
import com.getqardio.android.io.network.request.LoginRequestHandler;
import com.getqardio.android.io.network.request.LogoutRequestHandler;
import com.getqardio.android.mvp.MvpApplication;
import com.getqardio.android.mvp.common.local.model.User;
import com.getqardio.android.provider.AppProvideContract;
import com.getqardio.android.provider.AuthHelper$$Lambda$1;
import com.getqardio.android.provider.SyncHelper;
import com.getqardio.android.utils.CipherManager;
import com.getqardio.android.utils.HelperUtils;
import com.google.firebase.iid.FirebaseInstanceId;
import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmQuery;
import timber.log.Timber;

public class AuthHelper {
    public static final String[] PASSWORD_PROJECTION = new String[]{"password"};

    public static void createNewUser(Context context, String string2, String string3, String string4, boolean bl) {
        context.startService(CreateNewUserRequestHandler.createNewUserIntent(context, string2, string3, string4, bl));
    }

    public static void forgotPassword(Context context, String string2) {
        context.startService(ForgotPasswordRequestHandler.createForgotPasswordIntent(context, string2));
    }

    static com.getqardio.android.datamodel.User getUserByEmail(Context context, Context object, String string2) {
        Cursor cursor = object.getContentResolver().query(AppProvideContract.Tables.Users.CONTENT_URI, null, "email = ? ", new String[]{CipherManager.encrypt(context, string2)}, null);
        string2 = null;
        object = string2;
        if (cursor != null) {
            object = string2;
            if (cursor.moveToFirst()) {
                object = AuthHelper.parseUser(context, cursor);
            }
        }
        return object;
        finally {
            cursor.close();
        }
    }

    public static com.getqardio.android.datamodel.User getUserByEmail(Context context, String string2) {
        return AuthHelper.getUserByEmail(context, context, string2);
    }

    public static com.getqardio.android.datamodel.User getUserById(Context context, long l) {
        Object object = Uri.withAppendedPath((Uri)AppProvideContract.Tables.Users.CONTENT_URI, (String)Long.toString(l));
        Cursor cursor = context.getContentResolver().query(object, null, null, null, null);
        Object var4_5 = null;
        object = var4_5;
        if (cursor != null) {
            object = var4_5;
            if (cursor.moveToFirst()) {
                object = AuthHelper.parseUser(context, cursor);
            }
        }
        return object;
        finally {
            cursor.close();
        }
    }

    public static String getUserEmail(Context context, long l) {
        Object object = Uri.withAppendedPath((Uri)AppProvideContract.Tables.Users.CONTENT_URI, (String)Long.toString(l));
        Object var4_4 = null;
        Cursor cursor = context.getContentResolver().query(object, new String[]{"email"}, null, null, null);
        object = var4_4;
        if (cursor != null) {
            object = var4_4;
            if (cursor.moveToFirst()) {
                object = HelperUtils.getString(context, cursor, "email", null, true);
            }
        }
        return object;
        finally {
            cursor.close();
        }
    }

    public static com.getqardio.android.datamodel.User getUserEmailAndIdByToken(Context context, String object) {
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = AppProvideContract.Tables.Users.CONTENT_URI;
        object = CipherManager.encrypt(context, (String)object);
        uri = contentResolver.query(uri, new String[]{"_id", "email"}, "token = ? ", new String[]{object}, null);
        contentResolver = null;
        object = contentResolver;
        if (uri != null) {
            object = contentResolver;
            if (uri.moveToFirst()) {
                object = AuthHelper.parseUser(context, (Cursor)uri);
            }
        }
        return object;
        finally {
            uri.close();
        }
    }

    public static Long getUserId(Context object, String string2) {
        Object var2_3 = null;
        ContentResolver contentResolver = object.getContentResolver();
        Uri uri = AppProvideContract.Tables.Users.CONTENT_URI;
        object = CipherManager.encrypt(object, string2);
        string2 = contentResolver.query(uri, new String[]{"_id"}, "email = ? ", new String[]{object}, null);
        object = var2_3;
        if (string2 != null) {
            object = var2_3;
            if (string2.moveToFirst()) {
                object = HelperUtils.getLong((Cursor)string2, "_id", null);
            }
        }
        return object;
        finally {
            string2.close();
        }
    }

    public static CursorLoader getUserLoader(Context context, long l, String[] arrstring) {
        return new CursorLoader(context, Uri.withAppendedPath((Uri)AppProvideContract.Tables.Users.CONTENT_URI, (String)Long.toString(l)), null, null, null, null);
    }

    static /* synthetic */ void lambda$saveOrUpdateUserInRealm$0(String string2, String string3, long l, Long l2, String string4, String string5, Realm realm) {
        User user;
        Timber.d("saveOrUpdateUserInRealm email - %s, token - %s", string2, string3);
        User user2 = user = realm.where(User.class).equalTo("email", string2).findFirst();
        if (user == null) {
            user2 = new User();
            user2.setEmail(string2);
            user2.setUserId(l);
        }
        if (string3 != null) {
            user2.setToken(string3);
        }
        if (l2 != null) {
            user2.setTokenExpired(l2);
        }
        if (string4 != null) {
            user2.setTrackingId(string4);
        }
        if (string5 != null) {
            user2.setPassword(string5);
        }
        realm.copyToRealmOrUpdate(user2);
    }

    public static void login(Context context, String string2, String string3) {
        context.startService(LoginRequestHandler.createLoginIntent(context, string2, string3));
    }

    public static void logout(Context context) {
        CustomApplication.getApplication().flushMixpanel();
        String string2 = CustomApplication.getApplication().getCurrentUserToken();
        CustomApplication.getApplication().getCurrentUserId();
        SyncHelper.cancelAllRequests(context);
        CustomApplication.getApplication().setCurrentUserToken(null);
        CustomApplication.getApplication().setCurrentUserTrackingId(null);
        MvpApplication.get(context).getFCMManager().setRegistrationPending();
        if (FirebaseInstanceId.getInstance().getToken() != null) {
            context.startService(LogoutRequestHandler.createLogoutIntent(context, string2, FirebaseInstanceId.getInstance().getToken()));
        }
    }

    public static String parsePassword(Context context, Cursor cursor) {
        return HelperUtils.getString(context, cursor, "password", null, true);
    }

    private static com.getqardio.android.datamodel.User parseUser(Context context, Cursor cursor) {
        com.getqardio.android.datamodel.User user = new com.getqardio.android.datamodel.User();
        user._id = HelperUtils.getLong(cursor, "_id", user._id);
        user.email = HelperUtils.getString(context, cursor, "email", user.email, true);
        user.emailHash = HelperUtils.getString(cursor, "email_hash", user.emailHash);
        user.password = HelperUtils.getString(context, cursor, "password", user.password, true);
        user.token = HelperUtils.getString(context, cursor, "token", user.token, true);
        user.tokenExpired = HelperUtils.getLong(cursor, "token_expired", user.tokenExpired);
        user.trackingId = HelperUtils.getString(cursor, "tracking_id", user.trackingId);
        return user;
    }

    public static void saveOrUpdateUserInRealm(long l, String string2, String string3, Long l2, String string4, String string5) {
        Realm.getDefaultInstance().executeTransaction(AuthHelper$$Lambda$1.lambdaFactory$(string2, string3, l, l2, string4, string5));
    }

    static void setUser(Context context, Context context2, com.getqardio.android.datamodel.User user) {
        ContentValues contentValues = new ContentValues();
        HelperUtils.put(context, contentValues, "email", user.email, true);
        HelperUtils.put(contentValues, "email_hash", user.emailHash);
        HelperUtils.put(context, contentValues, "password", user.password, true);
        HelperUtils.put(context, contentValues, "token", user.token, true);
        HelperUtils.put(contentValues, "token_expired", user.tokenExpired);
        HelperUtils.put(contentValues, "tracking_id", user.trackingId);
        context2.getContentResolver().insert(AppProvideContract.Tables.Users.CONTENT_URI, contentValues);
        AuthHelper.saveOrUpdateUserInRealm(AuthHelper.getUserId(context, user.email), user.email, user.token, user.tokenExpired, user.trackingId, user.password);
    }

    public static void setUser(Context context, com.getqardio.android.datamodel.User user) {
        AuthHelper.setUser(context, context, user);
    }

    static boolean updatePassword(Context context, Context context2, String string2, String string3) {
        ContentValues contentValues = new ContentValues();
        HelperUtils.put(context, contentValues, "password", string3, true);
        return context2.getContentResolver().update(AppProvideContract.Tables.Users.CONTENT_URI, contentValues, "email = ? ", new String[]{CipherManager.encrypt(context, string2)}) > 0;
    }

    public static boolean updatePassword(Context context, String string2, String string3) {
        return AuthHelper.updatePassword(context, context, string2, string3);
    }

    public static boolean updateTokenAndTracking(Context context, String string2, String string3, Long l, String string4) {
        ContentValues contentValues = new ContentValues();
        HelperUtils.put(contentValues, "token", CipherManager.encrypt(context, string3));
        HelperUtils.put(contentValues, "token_expired", l);
        if (!TextUtils.isEmpty((CharSequence)string4)) {
            HelperUtils.put(contentValues, "tracking_id", string4);
        }
        AuthHelper.saveOrUpdateUserInRealm(AuthHelper.getUserId(context, string2), string2, string3, l, string4, null);
        return context.getContentResolver().update(AppProvideContract.Tables.Users.CONTENT_URI, contentValues, "email = ? ", new String[]{CipherManager.encrypt(context, string2)}) > 0;
    }

    public static boolean updateUser(Context context, long l, com.getqardio.android.datamodel.User user) {
        Uri uri = Uri.withAppendedPath((Uri)AppProvideContract.Tables.Users.CONTENT_URI, (String)Long.toString(l));
        ContentValues contentValues = new ContentValues();
        HelperUtils.put(context, contentValues, "email", user.email, true);
        HelperUtils.put(contentValues, "email_hash", user.emailHash);
        HelperUtils.put(context, contentValues, "password", user.password, true);
        HelperUtils.put(context, contentValues, "token", user.token, true);
        HelperUtils.put(contentValues, "token_expired", user.tokenExpired);
        if (user.trackingId != null) {
            HelperUtils.put(contentValues, "tracking_id", user.trackingId);
        }
        return context.getContentResolver().update(uri, contentValues, null, null) > 0;
    }
}

