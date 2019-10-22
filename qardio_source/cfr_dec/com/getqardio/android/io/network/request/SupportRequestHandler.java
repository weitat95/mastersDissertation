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
import android.support.v4.content.LocalBroadcastManager;
import com.getqardio.android.datamodel.SupportTicket;
import com.getqardio.android.io.network.AsyncReceiverHandler;
import com.getqardio.android.io.network.JSONParser;
import com.getqardio.android.io.network.NetworkContract;
import com.getqardio.android.io.network.NetworkRequestHelper;
import com.getqardio.android.io.network.request.RequestHandler;
import com.getqardio.android.io.network.response.BaseError;
import com.getqardio.android.io.network.response.BaseResponse;
import com.getqardio.android.provider.SupportTicketsHelper;
import com.getqardio.android.utils.ErrorHelper;
import com.getqardio.android.utils.NotificationHelper;
import com.getqardio.android.utils.Utils;
import com.getqardio.android.utils.ui.BasicNameValuePair;
import java.util.ArrayList;
import java.util.List;

public class SupportRequestHandler
extends RequestHandler {
    private List<BasicNameValuePair> buildParams(SupportTicket supportTicket, String string2) {
        ArrayList<BasicNameValuePair> arrayList = new ArrayList<BasicNameValuePair>();
        arrayList.add(new BasicNameValuePair("authToken", string2));
        arrayList.add(new BasicNameValuePair("subject", Utils.encodeString(supportTicket.subject)));
        arrayList.add(new BasicNameValuePair("body", Utils.encodeString(supportTicket.messageBody)));
        return arrayList;
    }

    public static Intent createSendTicketIntent(Context context, long l, int n) {
        context = AsyncReceiverHandler.createIntent(context, 13, l);
        context.putExtra("com.qardio.android.extra.EXTRA_ACTION_TYPE", 1);
        context.putExtra("com.qardio.android.extra.SUPPORT_TICKET_ID", n);
        return context;
    }

    private void notifyFailedSend(Context context, List<BaseError> list) {
        Intent intent = NotificationHelper.SupportNotification.createErrorsResult();
        ErrorHelper.putErrorsToIntent(intent, list);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    private void notifySuccessfulSend(Context context) {
        Intent intent = NotificationHelper.SupportNotification.createSuccessResult();
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    private void sendSupportTicket(Context context, int n, String object) {
        Object object2;
        block3: {
            block2: {
                object2 = SupportTicketsHelper.getSupportTicket(context, n);
                if (object2 == null) break block2;
                object = this.buildParams((SupportTicket)object2, (String)object);
                object = NetworkRequestHelper.request(NetworkRequestHelper.Method.POST, NetworkContract.Support.URI, (List<BasicNameValuePair>)object);
                object2 = new BaseResponse();
                JSONParser.parseStatusAndErrors(((NetworkRequestHelper.HttpResponse)object).getResponseBody(), object2);
                SupportTicketsHelper.deleteTicket(context, n);
                if (!((BaseResponse)object2).isSuccessful()) break block3;
                this.notifySuccessfulSend(context);
            }
            return;
        }
        this.notifyFailedSend(context, (List)((BaseResponse)object2).getError());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public RequestHandler.ProcessResult processIntent(Context context, Intent intent, long l, String string2) {
        switch (intent.getIntExtra("com.qardio.android.extra.EXTRA_ACTION_TYPE", 0)) {
            default: {
                do {
                    return RequestHandler.ProcessResult.SUCCESS;
                    break;
                } while (true);
            }
            case 1: 
        }
        this.sendSupportTicket(context, intent.getIntExtra("com.qardio.android.extra.SUPPORT_TICKET_ID", 0), string2);
        return RequestHandler.ProcessResult.SUCCESS;
    }
}

