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
import com.getqardio.android.datamodel.Goal;
import com.getqardio.android.io.network.AsyncReceiverHandler;
import com.getqardio.android.io.network.JSONParser;
import com.getqardio.android.io.network.NetworkContract;
import com.getqardio.android.io.network.NetworkRequestHelper;
import com.getqardio.android.io.network.request.RequestHandler;
import com.getqardio.android.io.network.response.BaseError;
import com.getqardio.android.io.network.response.BaseResponse;
import com.getqardio.android.provider.DataHelper;
import com.getqardio.android.utils.MetricUtils;
import com.getqardio.android.utils.ui.BasicNameValuePair;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import timber.log.Timber;

public class CurrentGoalRequestHandler
extends RequestHandler {
    public static Intent createSyncCurrentGoalIntent(Context context, long l) {
        context = AsyncReceiverHandler.createIntent(context, 21, l);
        context.putExtra(" com.getqardio.android.extra.ACTION_TYPE", 1);
        return context;
    }

    public static Intent createUpdateCurrentGoalIntent(Context context, long l) {
        context = AsyncReceiverHandler.createIntent(context, 21, l);
        context.putExtra(" com.getqardio.android.extra.ACTION_TYPE", 0);
        return context;
    }

    public static BaseResponse<String, List<BaseError>> deleteCurrentGoal(String baseResponse) {
        ArrayList<BasicNameValuePair> arrayList = new ArrayList<BasicNameValuePair>(1);
        arrayList.add(new BasicNameValuePair("authToken", (String)((Object)baseResponse)));
        baseResponse = NetworkRequestHelper.request(NetworkContract.DeleteCurrentGoal.METHOD, NetworkContract.DeleteCurrentGoal.URI, arrayList);
        if (((NetworkRequestHelper.HttpResponse)((Object)baseResponse)).isSuccessful()) {
            return JSONParser.parseDeleteCurrentGoal(((NetworkRequestHelper.HttpResponse)((Object)baseResponse)).getResponseBody());
        }
        baseResponse = new BaseResponse<String, List<BaseError>>();
        BaseError.setNetworkErrorResult(baseResponse);
        return baseResponse;
    }

    private static boolean goalDoesnotExistError(List<BaseError> list) {
        if (list == null) {
            return false;
        }
        for (int i = 0; i < list.size(); ++i) {
            if (!"hsynch.user.goal.not.exist".equals(list.get((int)i).messageKey)) continue;
            return true;
        }
        return false;
    }

    public static BaseResponse<Goal, List<BaseError>> requestCurrentGoal(String baseResponse) {
        ArrayList<BasicNameValuePair> arrayList = new ArrayList<BasicNameValuePair>(1);
        arrayList.add(new BasicNameValuePair("authToken", (String)((Object)baseResponse)));
        baseResponse = NetworkRequestHelper.request(NetworkContract.GetCurrentGoal.METHOD, NetworkContract.GetCurrentGoal.URI, arrayList);
        if (((NetworkRequestHelper.HttpResponse)((Object)baseResponse)).isSuccessful()) {
            return JSONParser.parseGetCurrentGoal(((NetworkRequestHelper.HttpResponse)((Object)baseResponse)).getResponseBody());
        }
        baseResponse = new BaseResponse<Goal, List<BaseError>>();
        BaseError.setNetworkErrorResult(baseResponse);
        return baseResponse;
    }

    public static BaseResponse<String, List<BaseError>> requestSaveGoal(String baseResponse, Goal goal) {
        ArrayList<BasicNameValuePair> arrayList = new ArrayList<BasicNameValuePair>(5);
        arrayList.add(new BasicNameValuePair("authToken", (String)((Object)baseResponse)));
        arrayList.add(new BasicNameValuePair("type", "weight"));
        arrayList.add(new BasicNameValuePair("target", String.valueOf(MetricUtils.convertWeightToHectograms(goal.target.floatValue()))));
        arrayList.add(new BasicNameValuePair("targetPerWeek", String.valueOf(MetricUtils.convertWeightToHectograms(goal.targetPerWeek.floatValue()))));
        arrayList.add(new BasicNameValuePair("startDate", String.valueOf(goal.startDate)));
        baseResponse = NetworkRequestHelper.request(NetworkContract.CreateOrUpdateCurrentGoal.METHOD, NetworkContract.CreateOrUpdateCurrentGoal.URI, arrayList);
        if (((NetworkRequestHelper.HttpResponse)((Object)baseResponse)).isSuccessful()) {
            return JSONParser.parseSaveCurrentGoal(((NetworkRequestHelper.HttpResponse)((Object)baseResponse)).getResponseBody());
        }
        baseResponse = new BaseResponse<String, List<BaseError>>();
        BaseError.setNetworkErrorResult(baseResponse);
        return baseResponse;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private RequestHandler.ProcessResult syncCurrentGoal(Context var1_1, Intent var2_4, long var3_5, String var5_6) {
        block20: {
            block19: {
                var6_19 = RequestHandler.ProcessResult.SUCCESS;
                var7_29 = DataHelper.CurrentGoalHelper.getGoalsForUpload(var1_1, (long)var3_17, null);
                var2_5 = var6_19;
                if (!var7_29.moveToFirst()) break block19;
                var8_30 = DataHelper.CurrentGoalHelper.parseGoal(var7_29);
                var2_6 = CurrentGoalRequestHandler.requestSaveGoal((String)var5_18, (Goal)var8_30);
                if (var2_6.isSuccessful()) {
                    DataHelper.CurrentGoalHelper.changeSyncStatus(var1_1, (long)var3_17, var8_30.syncStatus & -2);
                    var2_7 = var6_19;
                    break block19;
                }
                Timber.e("Error updating weight goals: ", new Object[0]);
                var6_25 = this.getErrorCode(var2_6.getError());
                var8_30 = var2_6.getError().iterator();
                do {
                    var2_14 = var6_25;
                    if (!var8_30.hasNext()) ** break;
                    var2_15 = (BaseError)var8_30.next();
                    Timber.e("%s : %s", new Object[]{var2_15.messageKey, var2_15.defaultMessageText});
                } while (true);
            }
            var7_29 = DataHelper.CurrentGoalHelper.getGoalsForDelete(var1_1, (long)var3_17, DataHelper.CurrentGoalHelper.DELETE_GOAL_PROJECTION);
            var6_21 = var2_8;
            if (var7_29.getCount() > 0) {
                if (!(var5_18 = CurrentGoalRequestHandler.deleteCurrentGoal((String)var5_18)).isSuccessful() && !CurrentGoalRequestHandler.goalDoesnotExistError(var5_18.getError())) break block20;
                var6_22 = var2_8;
                if (var3_17 != -1L) {
                    DataHelper.CurrentGoalHelper.deleteGoalFromLocalCache(var1_1, (long)var3_17);
                    var6_23 = var2_8;
                }
            }
            ** GOTO lbl45
        }
        try {
            Timber.e("Error deleting weight goals: ", new Object[0]);
            var2_16 = this.getErrorCode(var5_18.getError());
            var5_18 = var5_18.getError().iterator();
            do {
                block21: {
                    var6_27 = var2_16;
                    if (var5_18.hasNext()) break block21;
lbl45:
                    // 2 sources
                    var2_10 = var6_24;
                    if (var6_24 != RequestHandler.ProcessResult.SUCCESS) return var2_12;
                    if (DataHelper.CurrentGoalHelper.getGoalsCountForSync(var1_1, (long)var3_17) <= 0) return RequestHandler.ProcessResult.SUCCESS;
                    var2_11 = RequestHandler.ProcessResult.UNKNOWN_ERROR;
                    return var2_12;
                }
                var6_28 = (BaseError)var5_18.next();
                Timber.e("%s : %s", new Object[]{var6_28.messageKey, var6_28.defaultMessageText});
            } while (true);
        }
        catch (Throwable var1_3) {
            throw var1_3;
        }
        finally {
            var7_29.close();
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private RequestHandler.ProcessResult updateCurrentGoal(Context object, Intent object2, long l, String iterator) {
        Object object3;
        void var2_7;
        RequestHandler.ProcessResult processResult = RequestHandler.ProcessResult.SUCCESS;
        if (((BaseResponse)(object3 = CurrentGoalRequestHandler.requestCurrentGoal((String)object3))).isSuccessful()) {
            void var3_11;
            RequestHandler.ProcessResult processResult2 = processResult;
            if (var3_11 == -1L) return var2_7;
            {
                Goal goal = ((BaseResponse)object3).getData();
                if (goal == null) {
                    DataHelper.CurrentGoalHelper.deleteGoalFromLocalCache(object, (long)var3_11);
                    return processResult;
                }
                object3.getData().userId = (long)var3_11;
                DataHelper.CurrentGoalHelper.saveGoal(object, goal, false);
                RequestHandler.ProcessResult processResult3 = processResult;
                return var2_7;
            }
        } else {
            RequestHandler.ProcessResult processResult4 = this.getErrorCode((List<BaseError>)((BaseResponse)object3).getError());
            Timber.e("Error getting goal: ", new Object[0]);
            object3 = ((BaseResponse)object3).getError().iterator();
            do {
                RequestHandler.ProcessResult processResult5 = processResult4;
                if (!object3.hasNext()) return var2_7;
                BaseError baseError = (BaseError)object3.next();
                Timber.e("%s : %s", baseError.messageKey, baseError.defaultMessageText);
            } while (true);
        }
    }

    @Override
    public RequestHandler.ProcessResult processIntent(Context context, Intent intent, long l, String string2) {
        switch (intent.getIntExtra(" com.getqardio.android.extra.ACTION_TYPE", -1)) {
            default: {
                return RequestHandler.ProcessResult.UNKNOWN_REQUEST;
            }
            case 0: {
                if (this.syncCurrentGoal(context, intent, l, string2) == RequestHandler.ProcessResult.SUCCESS) {
                    return this.updateCurrentGoal(context, intent, l, string2);
                }
                return RequestHandler.ProcessResult.UNKNOWN_ERROR;
            }
            case 1: 
        }
        return this.syncCurrentGoal(context, intent, l, string2);
    }
}

