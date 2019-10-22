/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.net.Uri
 *  android.text.TextUtils
 */
package com.getqardio.android.io.network.request;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import com.getqardio.android.datamodel.BPMeasurement;
import com.getqardio.android.datamodel.VisitorMeasurement;
import com.getqardio.android.io.network.AsyncReceiverHandler;
import com.getqardio.android.io.network.JSONParser;
import com.getqardio.android.io.network.NetworkContract;
import com.getqardio.android.io.network.NetworkRequestHelper;
import com.getqardio.android.io.network.request.RequestHandler;
import com.getqardio.android.io.network.response.BPMeasurementsResponse;
import com.getqardio.android.io.network.response.BaseError;
import com.getqardio.android.io.network.response.BaseResponse;
import com.getqardio.android.provider.AuthHelper;
import com.getqardio.android.provider.MeasurementHelper;
import com.getqardio.android.shealth.ShealthDataHelper;
import com.getqardio.android.utils.NotificationHelper;
import com.getqardio.android.utils.Utils;
import com.getqardio.android.utils.ui.BasicNameValuePair;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import timber.log.Timber;

public class BPMeasurementsRequestHandler
extends RequestHandler {
    public static Intent createGetBPMeasurementsIntent(Context context, long l) {
        context = AsyncReceiverHandler.createIntent(context, 6, l);
        context.putExtra("com.getqardio.android.extra.ACTION_TYPE", 0);
        return context;
    }

    public static Intent createSaveOldVisitorMeasurementsIntent(Context context, long l) {
        context = AsyncReceiverHandler.createIntent(context, 6, l);
        context.putExtra("com.getqardio.android.extra.ACTION_TYPE", 3);
        return context;
    }

    public static Intent createSaveVisitorMeasurementIntent(Context context, long l, int n, String string2) {
        context = AsyncReceiverHandler.createIntent(context, 6, l);
        context.putExtra("com.getqardio.android.extra.ACTION_TYPE", 2);
        context.putExtra("com.getqardio.android.extra.VISITOR_MEASUREMENT_ID", n);
        context.putExtra("com.getqardio.android.extra.USER_EMAIL", string2);
        return context;
    }

    public static Intent createSyncBPMeasurementsIntent(Context context, long l) {
        context = AsyncReceiverHandler.createIntent(context, 6, l);
        context.putExtra("com.getqardio.android.extra.ACTION_TYPE", 1);
        return context;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private RequestHandler.ProcessResult getBPMeasurements(Context var1_1, Intent var2_2, long var3_3, String var5_4) {
        var2_3 = RequestHandler.ProcessResult.SUCCESS;
        var14_10 = AuthHelper.getUserEmail(var1_1, (long)var3_8);
        var8_11 = 0L;
        var12_12 = null;
        var6_16 = true;
        block0: do {
            if (!var6_16) {
                ShealthDataHelper.BpMeasurements.requestSaveMeasurements(var1_1, (long)var3_8);
                return var2_4;
            }
            var7_17 = false;
            var15_20 = BPMeasurementsRequestHandler.requestGetShortMeasurements((String)var5_9, var14_10, 100L, var8_11);
            if (var15_20.isSuccessful()) {
                var13_19 = var12_13;
                if (var12_13 == null) {
                    var13_19 = new ArrayList<E>(var15_20.getData().totalCount);
                }
                var13_19.addAll(var15_20.getData().measurements);
                var10_18 = var13_19.size();
                var7_17 = var10_18 < (long)((BPMeasurementsResponse)var15_20.getData()).totalCount;
                var8_11 = var10_18;
                var6_16 = var7_17;
                var12_14 = var13_19;
                if (var7_17) continue;
                MeasurementHelper.BloodPressure.replaceMeasurements(var1_1, (long)var3_8, (List<BPMeasurement>)var13_19);
                var8_11 = var10_18;
                var6_16 = var7_17;
                var12_15 = var13_19;
                continue;
            }
            var13_19 = this.getErrorCode(var15_20.getError());
            Timber.e("Error getting BP measurements", new Object[0]);
            var15_20 = var15_20.getError().iterator();
            do {
                var6_16 = var7_17;
                var2_6 = var13_19;
                if (var15_20.hasNext()) ** break;
                continue block0;
                var2_7 = (BaseError)var15_20.next();
                Timber.e("%s : %s", new Object[]{var2_7.messageKey, var2_7.defaultMessageText});
            } while (true);
            break;
        } while (true);
    }

    private RequestHandler.ProcessResult processGetRequest(Context context, Intent intent, long l, String string2) {
        RequestHandler.ProcessResult processResult = RequestHandler.ProcessResult.UNKNOWN_ERROR;
        if (this.syncBPMeasurements(context, intent, l, string2) == RequestHandler.ProcessResult.SUCCESS) {
            processResult = this.getBPMeasurements(context, intent, l, string2);
        }
        intent = new Intent("com.getqardio.android.NetworkNotification.BP_GET_FINISHED");
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        return processResult;
    }

    public static BaseResponse<String, List<BaseError>> requestDeleteMeasurements(String baseResponse, long l, long l2) {
        ArrayList<BasicNameValuePair> arrayList = new ArrayList<BasicNameValuePair>();
        arrayList.add(new BasicNameValuePair("authToken", (String)((Object)baseResponse)));
        arrayList.add(new BasicNameValuePair("fromDate", String.valueOf(l)));
        arrayList.add(new BasicNameValuePair("toDate", String.valueOf(l2)));
        arrayList.add(new BasicNameValuePair("type", "bp"));
        arrayList.add(new BasicNameValuePair("memberName", "mn"));
        baseResponse = NetworkRequestHelper.request(NetworkRequestHelper.Method.POST, NetworkContract.DeleteMeasurements.URI, arrayList);
        if (((NetworkRequestHelper.HttpResponse)((Object)baseResponse)).isSuccessful()) {
            return JSONParser.parseDeleteMeasurements(((NetworkRequestHelper.HttpResponse)((Object)baseResponse)).getResponseBody());
        }
        baseResponse = new BaseResponse<String, List<BaseError>>();
        BaseError.setNetworkErrorResult(baseResponse);
        return baseResponse;
    }

    public static BaseResponse<BPMeasurementsResponse, List<BaseError>> requestGetShortMeasurements(String baseResponse, String string2, long l, long l2) {
        ArrayList<BasicNameValuePair> arrayList = new ArrayList<BasicNameValuePair>();
        arrayList.add(new BasicNameValuePair("authToken", (String)((Object)baseResponse)));
        arrayList.add(new BasicNameValuePair("userId", string2));
        if (l > 0L) {
            arrayList.add(new BasicNameValuePair("limit", Long.toString(l)));
        }
        if (l2 > 0L) {
            arrayList.add(new BasicNameValuePair("offset", Long.toString(l2)));
        }
        arrayList.add(new BasicNameValuePair("memberName", "mn"));
        arrayList.add(new BasicNameValuePair("measureTypes", "bp"));
        baseResponse = NetworkRequestHelper.request(NetworkRequestHelper.Method.GET, NetworkContract.GetShortMeasurements.URI, arrayList);
        if (((NetworkRequestHelper.HttpResponse)((Object)baseResponse)).isSuccessful()) {
            return JSONParser.parseBPMeasurements(((NetworkRequestHelper.HttpResponse)((Object)baseResponse)).getResponseBody());
        }
        baseResponse = new BaseResponse<BPMeasurementsResponse, List<BaseError>>();
        BaseError.setNetworkErrorResult(baseResponse);
        return baseResponse;
    }

    public static BaseResponse<String, List<BaseError>> requestSaveMeasurementInVisitorMode(String baseResponse, String string2, VisitorMeasurement visitorMeasurement) {
        ArrayList<BasicNameValuePair> arrayList = new ArrayList<BasicNameValuePair>();
        arrayList.add(new BasicNameValuePair("authToken", (String)((Object)baseResponse)));
        arrayList.add(new BasicNameValuePair("deviceId", visitorMeasurement.deviceId));
        arrayList.add(new BasicNameValuePair("startDate", String.valueOf(visitorMeasurement.measureDate.getTime())));
        arrayList.add(new BasicNameValuePair("data1", visitorMeasurement.sys.toString()));
        arrayList.add(new BasicNameValuePair("data2", visitorMeasurement.dia.toString()));
        arrayList.add(new BasicNameValuePair("note", ""));
        arrayList.add(new BasicNameValuePair("data3", visitorMeasurement.pulse.toString()));
        arrayList.add(new BasicNameValuePair("irregularHeartBeat", visitorMeasurement.irregularHeartBeat.toString()));
        arrayList.add(new BasicNameValuePair("frequency", String.valueOf(1)));
        arrayList.add(new BasicNameValuePair("type", "bp"));
        arrayList.add(new BasicNameValuePair("memberName", URLEncoder.encode(string2)));
        arrayList.add(new BasicNameValuePair("visitor", "true"));
        arrayList.add(new BasicNameValuePair("timezone", Utils.encodeString(visitorMeasurement.timezone)));
        baseResponse = NetworkRequestHelper.request(NetworkRequestHelper.Method.POST, NetworkContract.SaveMeasurements.URI, arrayList);
        if (((NetworkRequestHelper.HttpResponse)((Object)baseResponse)).isSuccessful()) {
            return JSONParser.parseSaveMeasurements(((NetworkRequestHelper.HttpResponse)((Object)baseResponse)).getResponseBody());
        }
        baseResponse = new BaseResponse<String, List<BaseError>>();
        BaseError.setNetworkErrorResult(baseResponse);
        return baseResponse;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static BaseResponse<String, List<BaseError>> requestSaveMeasurements(String object, BPMeasurement bPMeasurement) {
        ArrayList<BasicNameValuePair> arrayList = new ArrayList<BasicNameValuePair>();
        arrayList.add(new BasicNameValuePair("authToken", (String)object));
        object = !TextUtils.isEmpty((CharSequence)bPMeasurement.deviceId) ? bPMeasurement.deviceId : "0001091";
        arrayList.add(new BasicNameValuePair("deviceId", (String)object));
        arrayList.add(new BasicNameValuePair("startDate", String.valueOf(bPMeasurement.measureDate.getTime())));
        arrayList.add(new BasicNameValuePair("data1", bPMeasurement.sys.toString()));
        arrayList.add(new BasicNameValuePair("data2", bPMeasurement.dia.toString()));
        arrayList.add(new BasicNameValuePair("data3", bPMeasurement.pulse.toString()));
        arrayList.add(new BasicNameValuePair("irregularHeartBeat", bPMeasurement.irregularHeartBeat.toString()));
        object = bPMeasurement.note != null ? Utils.encodeString(bPMeasurement.note) : "";
        arrayList.add(new BasicNameValuePair("note", (String)object));
        arrayList.add(new BasicNameValuePair("frequency", String.valueOf(1)));
        arrayList.add(new BasicNameValuePair("type", "bp"));
        arrayList.add(new BasicNameValuePair("memberName", "mn"));
        arrayList.add(new BasicNameValuePair("timezone", Utils.encodeString(bPMeasurement.timezone)));
        object = bPMeasurement.source != null ? bPMeasurement.source.toString() : String.valueOf(0);
        arrayList.add(new BasicNameValuePair("source", (String)object));
        if (bPMeasurement.longitude != null) {
            arrayList.add(new BasicNameValuePair("longitude", bPMeasurement.longitude.toString()));
        }
        if (bPMeasurement.latitude != null) {
            arrayList.add(new BasicNameValuePair("latitude", bPMeasurement.latitude.toString()));
        }
        if (bPMeasurement.tag != null) {
            arrayList.add(new BasicNameValuePair("tag", bPMeasurement.tag.toString()));
        }
        if (((NetworkRequestHelper.HttpResponse)(object = NetworkRequestHelper.request(NetworkRequestHelper.Method.POST, NetworkContract.SaveMeasurements.URI, arrayList))).isSuccessful()) {
            return JSONParser.parseSaveMeasurements(((NetworkRequestHelper.HttpResponse)object).getResponseBody());
        }
        object = new BaseResponse<String, List<BaseError>>();
        BaseError.setNetworkErrorResult(object);
        return object;
    }

    private RequestHandler.ProcessResult saveOldVisitorMeasurements(Context context, Intent object, long l, String string2) {
        object = RequestHandler.ProcessResult.SUCCESS;
        for (VisitorMeasurement visitorMeasurement : MeasurementHelper.BloodPressure.getVisitorMeasurementsList(context, l)) {
            if (TextUtils.isEmpty((CharSequence)visitorMeasurement.memberName)) {
                MeasurementHelper.BloodPressure.deleteVisitorMeasurement(context, l, visitorMeasurement._id);
                continue;
            }
            BaseResponse<String, List<BaseError>> baseResponse = BPMeasurementsRequestHandler.requestSaveMeasurementInVisitorMode(string2, visitorMeasurement.memberName, visitorMeasurement);
            if (baseResponse.isSuccessful()) {
                MeasurementHelper.BloodPressure.deleteVisitorMeasurement(context, l, visitorMeasurement._id);
                continue;
            }
            Timber.e("Cannot save measurement in visitor mode", new Object[0]);
            object = this.getErrorCode(baseResponse.getError());
            MeasurementHelper.BloodPressure.updateVisitorMeasurement(context, l, visitorMeasurement._id, visitorMeasurement.memberName);
        }
        return object;
    }

    private RequestHandler.ProcessResult saveVisitorMeasurements(Context context, Intent object, long l, String object2) {
        RequestHandler.ProcessResult processResult = RequestHandler.ProcessResult.SUCCESS;
        int n = object.getIntExtra("com.getqardio.android.extra.VISITOR_MEASUREMENT_ID", -1);
        object = object.getStringExtra("com.getqardio.android.extra.USER_EMAIL");
        VisitorMeasurement visitorMeasurement = MeasurementHelper.BloodPressure.getVisitorMeasurement(context, l, n);
        if (visitorMeasurement != null) {
            object2 = BPMeasurementsRequestHandler.requestSaveMeasurementInVisitorMode((String)object2, (String)object, visitorMeasurement);
            LocalBroadcastManager.getInstance(context).sendBroadcast(NotificationHelper.SendVisitorMeasurementNotification.createIntent());
            if (((BaseResponse)object2).isSuccessful()) {
                MeasurementHelper.BloodPressure.deleteVisitorMeasurement(context, l, n);
                return processResult;
            }
            Timber.e("Cannot save measurement in visitor mode", new Object[0]);
            object2 = this.getErrorCode((List)((BaseResponse)object2).getError());
            MeasurementHelper.BloodPressure.updateVisitorMeasurement(context, l, n, (String)object);
            return object2;
        }
        return RequestHandler.ProcessResult.UNKNOWN_ERROR;
    }

    /*
     * Exception decompiling
     */
    private RequestHandler.ProcessResult syncBPMeasurements(Context var1_1, Intent var2_4, long var3_5, String var5_6) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [8[UNCONDITIONALDOLOOP]], but top level block is 17[SIMPLE_IF_TAKEN]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:619)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:750)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:238)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:183)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:922)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:813)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:239)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:120)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    @Override
    public boolean checkUserId(Intent intent, Long l, long l2) {
        if (intent.getIntExtra("com.getqardio.android.extra.ACTION_TYPE", -1) == 0) {
            return true;
        }
        return super.checkUserId(intent, l, l2);
    }

    @Override
    public RequestHandler.ProcessResult processIntent(Context context, Intent intent, long l, String string2) {
        switch (intent.getIntExtra("com.getqardio.android.extra.ACTION_TYPE", -1)) {
            default: {
                return RequestHandler.ProcessResult.UNKNOWN_REQUEST;
            }
            case 0: {
                return this.processGetRequest(context, intent, l, string2);
            }
            case 1: {
                return this.syncBPMeasurements(context, intent, l, string2);
            }
            case 2: {
                return this.saveVisitorMeasurements(context, intent, l, string2);
            }
            case 3: 
        }
        return this.saveOldVisitorMeasurements(context, intent, l, string2);
    }
}

