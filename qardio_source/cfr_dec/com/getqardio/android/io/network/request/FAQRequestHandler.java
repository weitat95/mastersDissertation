/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.net.Uri
 */
package com.getqardio.android.io.network.request;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.getqardio.android.datamodel.Faq;
import com.getqardio.android.io.network.AsyncReceiverHandler;
import com.getqardio.android.io.network.JSONParser;
import com.getqardio.android.io.network.NetworkContract;
import com.getqardio.android.io.network.NetworkRequestHelper;
import com.getqardio.android.io.network.request.RequestHandler;
import com.getqardio.android.io.network.response.BaseError;
import com.getqardio.android.io.network.response.BaseResponse;
import com.getqardio.android.io.network.response.LoadFAQListResponse;
import com.getqardio.android.provider.DataHelper;
import com.getqardio.android.utils.ui.BasicNameValuePair;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import timber.log.Timber;

public class FAQRequestHandler
extends RequestHandler {
    public static Intent createUpdateArmFAQListIntent(Context context, long l) {
        return FAQRequestHandler.createUpdateFAQListIntent(context, "QardioArm", l);
    }

    public static Intent createUpdateBaseFAQListIntent(Context context, long l) {
        return FAQRequestHandler.createUpdateFAQListIntent(context, "QardioBase", l);
    }

    private static Intent createUpdateFAQListIntent(Context context, String string2, long l) {
        context = AsyncReceiverHandler.createIntent(context, 0, l);
        context.putExtra("com.getqardio.android.extra.DEVICE_TYPE", string2);
        return context;
    }

    public static BaseResponse<LoadFAQListResponse, List<BaseError>> requestLoadFAQList(String baseResponse, int n, int n2, long l, long l2, String string2) {
        ArrayList<BasicNameValuePair> arrayList = new ArrayList<BasicNameValuePair>();
        arrayList.add(new BasicNameValuePair("authToken", (String)((Object)baseResponse)));
        arrayList.add(new BasicNameValuePair("startRow", Integer.toString(n)));
        arrayList.add(new BasicNameValuePair("pageSize", Integer.toString(n2)));
        arrayList.add(new BasicNameValuePair("version", "1.41"));
        arrayList.add(new BasicNameValuePair("deviceType", string2));
        if (l >= 0L) {
            arrayList.add(new BasicNameValuePair("startDate", Long.toString(l)));
        }
        if (l2 >= 0L) {
            arrayList.add(new BasicNameValuePair("endDate", Long.toString(l2)));
        }
        if (((NetworkRequestHelper.HttpResponse)((Object)(baseResponse = NetworkRequestHelper.request(NetworkRequestHelper.Method.GET, NetworkContract.Faqs.URI, arrayList)))).isSuccessful()) {
            return JSONParser.parseFAQList(((NetworkRequestHelper.HttpResponse)((Object)baseResponse)).getResponseBody());
        }
        baseResponse = new BaseResponse<LoadFAQListResponse, List<BaseError>>();
        BaseError.setNetworkErrorResult(baseResponse);
        return baseResponse;
    }

    private RequestHandler.ProcessResult updateFAQList(Context object, String object2, String object3) {
        RequestHandler.ProcessResult processResult = RequestHandler.ProcessResult.SUCCESS;
        Object object4 = FAQRequestHandler.requestLoadFAQList((String)object2, 0, 20, -1L, -1L, (String)object3);
        if (((BaseResponse)object4).isSuccessful()) {
            DataHelper.FaqHelper.deleteAllFaq((Context)object, (String)object3);
            DataHelper.FaqHelper.setFaqList((Context)object, ((BaseResponse)object4).getData().getQuestions());
            int n = ((BaseResponse)object4).getData().getQuestions().size();
            int n2 = ((LoadFAQListResponse)((BaseResponse)object4).getData()).getCount();
            do {
                object4 = processResult;
                if (n < n2) {
                    object4 = FAQRequestHandler.requestLoadFAQList((String)object2, n, 20, -1L, -1L, (String)object3);
                    DataHelper.FaqHelper.setFaqList((Context)object, ((LoadFAQListResponse)((BaseResponse)object4).getData()).getQuestions());
                    n += ((LoadFAQListResponse)((BaseResponse)object4).getData()).getQuestions().size();
                    n2 = ((LoadFAQListResponse)((BaseResponse)object4).getData()).getCount();
                    continue;
                }
                break;
            } while (true);
        } else {
            Timber.e("Error loading FAQ list: ", new Object[0]);
            object = this.getErrorCode((List<BaseError>)((BaseResponse)object4).getError());
            object2 = ((BaseResponse)object4).getError().iterator();
            do {
                object4 = object;
                if (!object2.hasNext()) break;
                object3 = (BaseError)object2.next();
                Timber.e("%s : %s", ((BaseError)object3).messageKey, ((BaseError)object3).defaultMessageText);
            } while (true);
        }
        return object4;
    }

    @Override
    public RequestHandler.ProcessResult processIntent(Context context, Intent intent, long l, String string2) {
        return this.updateFAQList(context, string2, intent.getStringExtra("com.getqardio.android.extra.DEVICE_TYPE"));
    }
}

