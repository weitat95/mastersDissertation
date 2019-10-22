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
import com.getqardio.android.datamodel.ClaimMeasurement;
import com.getqardio.android.io.network.AsyncReceiverHandler;
import com.getqardio.android.io.network.JSONParser;
import com.getqardio.android.io.network.NetworkContract;
import com.getqardio.android.io.network.NetworkRequestHelper;
import com.getqardio.android.io.network.request.RequestHandler;
import com.getqardio.android.io.network.response.BaseError;
import com.getqardio.android.io.network.response.BaseResponse;
import com.getqardio.android.io.network.response.ClaimMeasurementResponse;
import com.getqardio.android.provider.MeasurementHelper;
import com.getqardio.android.utils.ui.BasicNameValuePair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import timber.log.Timber;

public class ClaimMeasurementsRequestHandler
extends RequestHandler {
    public static Intent createGetClaimMeasurementsIntent(Context context, long l, String string2) {
        context = AsyncReceiverHandler.createIntent(context, 19, l);
        context.putExtra("com.getqardio.android.extra.ACTION_TYPE", 0);
        context.putExtra("com.getqardio.android.extra.DEVICE_ID", string2);
        return context;
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private RequestHandler.ProcessResult getClaimMeasurements(Context object, Intent object2, long l, String iterator) {
        Iterator iterator2;
        void var3_4;
        Object object3;
        RequestHandler.ProcessResult processResult = RequestHandler.ProcessResult.SUCCESS;
        if ((object3 = this.requestGetClaimMeasurements((String)((Object)iterator2), object3.getStringExtra("com.getqardio.android.extra.DEVICE_ID"))).isSuccessful()) {
            iterator2 = object3.getData();
            object3 = processResult;
            if (iterator2 == null) return object3;
            object3 = ((ClaimMeasurementResponse)iterator2).data;
            if (object3 == null || object3.size() == 0) {
                MeasurementHelper.Claim.deleteClaimMeasurements(object, (long)var3_4);
                return processResult;
            }
            this.replaceOrDeleteClaimMeasurements((Context)object, (long)var3_4, (List<ClaimMeasurement>)object3);
            return processResult;
        }
        iterator2 = object3.getError().iterator();
        while (iterator2.hasNext()) {
            if (!"hsynch.association.device.id.not.found".equals(((BaseError)iterator2.next()).messageKey)) continue;
            MeasurementHelper.Claim.deleteClaimMeasurements(object, (long)var3_4);
        }
        RequestHandler.ProcessResult processResult2 = this.getErrorCode((List)object3.getError());
        Timber.e("Error getting claim measurements: ", new Object[0]);
        iterator2 = ((List)object3.getError()).iterator();
        do {
            object3 = processResult2;
            if (!iterator2.hasNext()) return object3;
            object3 = (BaseError)iterator2.next();
            Timber.e("%s : %s", ((BaseError)object3).messageKey, ((BaseError)object3).defaultMessageText);
        } while (true);
    }

    private RequestHandler.ProcessResult processGetRequest(Context context, Intent intent, long l, String string2) {
        RequestHandler.ProcessResult processResult = RequestHandler.ProcessResult.UNKNOWN_ERROR;
        if (this.syncClaimMeasurements(context, l, string2) == RequestHandler.ProcessResult.SUCCESS) {
            processResult = this.getClaimMeasurements(context, intent, l, string2);
        }
        intent = new Intent("com.getqardio.android.NetworkNotification.CLAIM_GET_FINISHED");
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        return processResult;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void replaceOrDeleteClaimMeasurements(Context context, Long l, List<ClaimMeasurement> list) {
        Cursor cursor = MeasurementHelper.Claim.getAllClaimMeasurementsCursor(context, l, null);
        ArrayList<ClaimMeasurement> arrayList = new ArrayList<ClaimMeasurement>();
        ArrayList<Integer> arrayList2 = new ArrayList<Integer>();
        if (cursor != null && cursor.moveToFirst()) {
            boolean bl;
            do {
                int n;
                ClaimMeasurement claimMeasurement;
                if ((n = list.indexOf(claimMeasurement = MeasurementHelper.Claim.parseClaimMeasurement(cursor))) > -1) {
                    arrayList.add(list.get(n));
                } else {
                    arrayList2.add(claimMeasurement.id);
                }
                cursor.moveToNext();
            } while (!(bl = cursor.isAfterLast()));
        }
        arrayList.addAll(list);
        MeasurementHelper.Claim.replaceClaimMeasurements(context, l, arrayList);
        MeasurementHelper.Claim.deleteClaimMeasurementsFromCache(context, l, arrayList2);
        return;
        finally {
            cursor.close();
        }
    }

    private BaseResponse<String, List<BaseError>> requestClaimMeasurement(int n, String object) {
        ArrayList<BasicNameValuePair> arrayList = new ArrayList<BasicNameValuePair>();
        arrayList.add(new BasicNameValuePair("authToken", (String)object));
        arrayList.add(new BasicNameValuePair("measurementId", String.valueOf(n)));
        object = new BaseResponse();
        JSONParser.parseStatusAndErrors(NetworkRequestHelper.request(NetworkRequestHelper.Method.POST, NetworkContract.UpdateMeasurementUserId.URI, arrayList).getResponseBody(), object);
        return object;
    }

    private BaseResponse<String, List<BaseError>> requestDeleteMeasurement(int n, String object) {
        ArrayList<BasicNameValuePair> arrayList = new ArrayList<BasicNameValuePair>();
        arrayList.add(new BasicNameValuePair("authToken", (String)object));
        arrayList.add(new BasicNameValuePair("measurementId", String.valueOf(n)));
        object = new BaseResponse();
        JSONParser.parseStatusAndErrors(NetworkRequestHelper.request(NetworkRequestHelper.Method.POST, NetworkContract.DeleteMeasurementById.URI, arrayList).getResponseBody(), object);
        return object;
    }

    private BaseResponse<ClaimMeasurementResponse, List<BaseError>> requestGetClaimMeasurements(String baseResponse, String string2) {
        ArrayList<BasicNameValuePair> arrayList = new ArrayList<BasicNameValuePair>();
        arrayList.add(new BasicNameValuePair("authToken", (String)((Object)baseResponse)));
        arrayList.add(new BasicNameValuePair("deviceId", string2));
        baseResponse = NetworkRequestHelper.request(NetworkRequestHelper.Method.GET, NetworkContract.GetLastWeightMeasurementBySn.URI, arrayList);
        if (((NetworkRequestHelper.HttpResponse)((Object)baseResponse)).isSuccessful()) {
            return JSONParser.parseClaimMeasurements(((NetworkRequestHelper.HttpResponse)((Object)baseResponse)).getResponseBody());
        }
        baseResponse = new BaseResponse<ClaimMeasurementResponse, List<BaseError>>();
        BaseError.setNetworkErrorResult(baseResponse);
        return baseResponse;
    }

    /*
     * Exception decompiling
     */
    private RequestHandler.ProcessResult syncClaimMeasurements(Context var1_1, long var2_4, String var4_5) {
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
    public RequestHandler.ProcessResult processIntent(Context context, Intent intent, long l, String string2) {
        switch (intent.getIntExtra("com.getqardio.android.extra.ACTION_TYPE", -1)) {
            default: {
                return RequestHandler.ProcessResult.UNKNOWN_ERROR;
            }
            case 0: 
        }
        return this.processGetRequest(context, intent, l, string2);
    }
}

