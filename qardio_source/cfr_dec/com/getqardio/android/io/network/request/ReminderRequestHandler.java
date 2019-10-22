/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.database.Cursor
 *  android.net.Uri
 */
package com.getqardio.android.io.network.request;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import com.getqardio.android.datamodel.Reminder;
import com.getqardio.android.io.network.AsyncReceiverHandler;
import com.getqardio.android.io.network.JSONParser;
import com.getqardio.android.io.network.NetworkContract;
import com.getqardio.android.io.network.NetworkRequestHelper;
import com.getqardio.android.io.network.request.RequestHandler;
import com.getqardio.android.io.network.response.BaseError;
import com.getqardio.android.io.network.response.BaseResponse;
import com.getqardio.android.io.network.response.ReminderResponse;
import com.getqardio.android.provider.DataHelper;
import com.getqardio.android.utils.NotificationHelper;
import com.getqardio.android.utils.Utils;
import com.getqardio.android.utils.ui.BasicNameValuePair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import timber.log.Timber;

public class ReminderRequestHandler
extends RequestHandler {
    public static Intent createSyncRemindersIntent(Context context, long l, String[] arrstring) {
        context = AsyncReceiverHandler.createIntent(context, 7, l);
        context.putExtra("com.getqardio.android.extra.ACTION_TYPE", 1);
        context.putExtra("com.getqardio.android.extra.TYPES", arrstring);
        return context;
    }

    public static Intent createUpdateRemindersIntent(Context context, long l, String[] arrstring) {
        context = AsyncReceiverHandler.createIntent(context, 7, l);
        context.putExtra("com.getqardio.android.extra.ACTION_TYPE", 0);
        context.putExtra("com.getqardio.android.extra.TYPES", arrstring);
        return context;
    }

    private static List<Integer> getDaysList(int n) {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        for (int i = 0; i < 7; ++i) {
            if ((1 << i & n) <= 0) continue;
            arrayList.add(i);
        }
        return arrayList;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private RequestHandler.ProcessResult getReminders(Context context, long l, String object, String[] arrstring) {
        void var4_6;
        void var5_8;
        RequestHandler.ProcessResult processResult = RequestHandler.ProcessResult.SUCCESS;
        BaseResponse<List<ReminderResponse>, List<BaseError>> baseResponse = this.requestGetReminders((String)object, (String[])var5_8);
        if (baseResponse.isSuccessful()) {
            RequestHandler.ProcessResult processResult2 = processResult;
            if (!DataHelper.ReminderHelper.updateRemindersAfterGet(context, l, ReminderResponse.convertToReminderList(baseResponse.getData()), (String[])var5_8)) {
                BaseError.setUnknownErrorResult(baseResponse);
                RequestHandler.ProcessResult processResult3 = this.getErrorCode(baseResponse.getError());
            }
        } else {
            RequestHandler.ProcessResult processResult4 = this.getErrorCode(baseResponse.getError());
        }
        boolean bl = var4_6 == RequestHandler.ProcessResult.SUCCESS;
        ReminderRequestHandler.notifySyncRemindersCompleted(context, bl, baseResponse.getError());
        return var4_6;
    }

    private static void notifySyncRemindersCompleted(Context context, boolean bl, List<BaseError> intent) {
        if (bl) {
            intent = NotificationHelper.SyncRemindersNotification.createSuccessResult();
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            return;
        }
        intent = NotificationHelper.SyncRemindersNotification.createErrorsResult();
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    private static boolean reminderDoesnotExistError(List<BaseError> list) {
        if (list == null) {
            return false;
        }
        for (int i = 0; i < list.size(); ++i) {
            if (!"hsynch.reminders.does.not.exists".equals(list.get((int)i).messageKey)) continue;
            return true;
        }
        return false;
    }

    private BaseResponse<Long, List<BaseError>> requestCreateUpdateReminder(String baseResponse, Reminder object) {
        block6: {
            block5: {
                ArrayList<BasicNameValuePair> arrayList = new ArrayList<BasicNameValuePair>();
                arrayList.add(new BasicNameValuePair("authToken", (String)((Object)baseResponse)));
                if (((Reminder)object).reminderId != null) {
                    arrayList.add(new BasicNameValuePair("id", ((Reminder)object).reminderId.toString()));
                }
                arrayList.add(new BasicNameValuePair("time", String.valueOf(((Reminder)object).remindTime)));
                arrayList.add(new BasicNameValuePair("repeat", ((Reminder)object).repeat.toString()));
                arrayList.add(new BasicNameValuePair("type", ((Reminder)object).type));
                baseResponse = ReminderRequestHandler.getDaysList(((Reminder)object).days).iterator();
                while (baseResponse.hasNext()) {
                    arrayList.add(new BasicNameValuePair("days", ((Integer)baseResponse.next()).toString()));
                }
                object = NetworkRequestHelper.request(NetworkRequestHelper.Method.POST, NetworkContract.CreateUpdateReminder.URI, arrayList);
                baseResponse = new BaseResponse<Long, List<BaseError>>();
                JSONParser.parseStatusAndErrors(((NetworkRequestHelper.HttpResponse)object).getResponseBody(), baseResponse);
                if (!baseResponse.isSuccessful()) break block5;
                if ((object = ReminderResponse.parseId(((NetworkRequestHelper.HttpResponse)object).getResponseBody())) == null) break block6;
                baseResponse.setData((Long)object);
            }
            return baseResponse;
        }
        BaseError.setUnknownErrorResult(baseResponse);
        return baseResponse;
    }

    private BaseResponse<Long, List<BaseError>> requestDeleteReminder(String object, long l) {
        Object object2 = new ArrayList<BasicNameValuePair>();
        object2.add(new BasicNameValuePair("authToken", (String)object));
        object2.add(new BasicNameValuePair("id", String.valueOf(l)));
        object = NetworkRequestHelper.request(NetworkRequestHelper.Method.POST, NetworkContract.DeleteReminder.URI, object2);
        object2 = new BaseResponse();
        JSONParser.parseReminderDeletion(((NetworkRequestHelper.HttpResponse)object).getResponseBody(), object2);
        return object2;
    }

    private BaseResponse<List<ReminderResponse>, List<BaseError>> requestGetReminders(String object, String[] object2) {
        block3: {
            block2: {
                ArrayList<BasicNameValuePair> arrayList = new ArrayList<BasicNameValuePair>();
                arrayList.add(new BasicNameValuePair("authToken", (String)object));
                arrayList.add(new BasicNameValuePair("types", Utils.arrayToString((Object[])object2)));
                object2 = NetworkRequestHelper.request(NetworkRequestHelper.Method.GET, NetworkContract.GetReminders.URI, arrayList);
                object = new BaseResponse();
                JSONParser.parseStatusAndErrors(((NetworkRequestHelper.HttpResponse)object2).getResponseBody(), object);
                if (!((BaseResponse)object).isSuccessful()) break block2;
                if ((object2 = ReminderResponse.parseList(((NetworkRequestHelper.HttpResponse)object2).getResponseBody())) == null) break block3;
                ((BaseResponse)object).setData(object2);
            }
            return object;
        }
        BaseError.setUnknownErrorResult(object);
        return object;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private RequestHandler.ProcessResult syncReminders(Context context, long l, String string2, String[] object) {
        void var5_8;
        RequestHandler.ProcessResult processResult = RequestHandler.ProcessResult.SUCCESS;
        Cursor cursor = DataHelper.ReminderHelper.getRemindersForSync(context, null, l, (String[])object);
        if (cursor == null) {
            return RequestHandler.ProcessResult.UNKNOWN_ERROR;
        }
        List<Reminder> list = DataHelper.ReminderHelper.parseReminderList(cursor);
        ArrayList<BaseError> arrayList = new ArrayList<BaseError>();
        Iterator<Reminder> iterator = list.iterator();
        RequestHandler.ProcessResult processResult2 = processResult;
        while (iterator.hasNext()) {
            RequestHandler.ProcessResult processResult3;
            BaseResponse<Long, List<BaseError>> baseResponse;
            Reminder reminder = iterator.next();
            if ((reminder.syncStatus & 5) == 5) {
                DataHelper.ReminderHelper.deleteReminderAfterSync(context, l, reminder._id);
                continue;
            }
            if ((reminder.syncStatus & 4) > 0) {
                RequestHandler.ProcessResult processResult4;
                baseResponse = this.requestDeleteReminder(string2, reminder.reminderId);
                if (baseResponse.isSuccessful() || ReminderRequestHandler.reminderDoesnotExistError(baseResponse.getError())) {
                    DataHelper.ReminderHelper.deleteReminderAfterSync(context, l, reminder._id);
                    continue;
                }
                arrayList.addAll((Collection<BaseError>)baseResponse.getError());
                RequestHandler.ProcessResult processResult5 = processResult4 = this.getErrorCode(baseResponse.getError());
                if (processResult4 != RequestHandler.ProcessResult.INVALID_TOKEN) continue;
                return processResult4;
            }
            if ((reminder.syncStatus & 3) <= 0) continue;
            baseResponse = this.requestCreateUpdateReminder(string2, reminder);
            if (baseResponse.isSuccessful()) {
                DataHelper.ReminderHelper.updateIdAndSyncStatusAfterSync(context, l, reminder._id, baseResponse.getData(), 0);
                continue;
            }
            arrayList.addAll((Collection<BaseError>)baseResponse.getError());
            RequestHandler.ProcessResult processResult6 = processResult3 = this.getErrorCode(baseResponse.getError());
            if (processResult3 != RequestHandler.ProcessResult.INVALID_TOKEN) continue;
            return processResult3;
        }
        boolean bl = var5_8 == RequestHandler.ProcessResult.SUCCESS;
        ReminderRequestHandler.notifySyncRemindersCompleted(context, bl, arrayList);
        return var5_8;
    }

    @Override
    public RequestHandler.ProcessResult processIntent(Context context, Intent arrstring, long l, String string2) {
        int n = arrstring.getIntExtra("com.getqardio.android.extra.ACTION_TYPE", -1);
        if ((arrstring = arrstring.getStringArrayExtra("com.getqardio.android.extra.TYPES")) == null || arrstring.length == 0) {
            Timber.e("Types for reminder sync are empty", new Object[0]);
            return RequestHandler.ProcessResult.UNKNOWN_ERROR;
        }
        switch (n) {
            default: {
                return RequestHandler.ProcessResult.UNKNOWN_ERROR;
            }
            case 0: {
                if (this.syncReminders(context, l, string2, arrstring) == RequestHandler.ProcessResult.SUCCESS) {
                    return this.getReminders(context, l, string2, arrstring);
                }
                return RequestHandler.ProcessResult.UNKNOWN_ERROR;
            }
            case 1: 
        }
        return this.syncReminders(context, l, string2, arrstring);
    }
}

