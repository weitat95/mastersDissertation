/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.app.AppOpsManager
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.os.Messenger
 *  android.util.Log
 */
package com.firebase.jobdispatcher;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import com.firebase.jobdispatcher.ExecutionDelegator;
import com.firebase.jobdispatcher.GooglePlayMessengerCallback;
import com.firebase.jobdispatcher.GooglePlayReceiver;
import com.firebase.jobdispatcher.JobCallback;
import com.firebase.jobdispatcher.JobInvocation;

@TargetApi(value=21)
class GooglePlayMessageHandler
extends Handler {
    private final GooglePlayReceiver googlePlayReceiver;

    public GooglePlayMessageHandler(Looper looper, GooglePlayReceiver googlePlayReceiver) {
        super(looper);
        this.googlePlayReceiver = googlePlayReceiver;
    }

    private void handleStartMessage(Message object) {
        Bundle bundle = object.getData();
        object = object.replyTo;
        String string2 = bundle.getString("tag");
        if (object == null || string2 == null) {
            if (Log.isLoggable((String)"FJD.GooglePlayReceiver", (int)3)) {
                Log.d((String)"FJD.GooglePlayReceiver", (String)"Invalid start execution message.");
            }
            return;
        }
        object = new GooglePlayMessengerCallback((Messenger)object, string2);
        object = this.googlePlayReceiver.prepareJob((JobCallback)object, bundle);
        this.googlePlayReceiver.getExecutionDelegator().executeJob((JobInvocation)object);
    }

    private void handleStopMessage(Message object) {
        object = GooglePlayReceiver.getJobCoder().decode(object.getData());
        if (object == null) {
            if (Log.isLoggable((String)"FJD.GooglePlayReceiver", (int)3)) {
                Log.d((String)"FJD.GooglePlayReceiver", (String)"Invalid stop execution message.");
            }
            return;
        }
        ExecutionDelegator.stopJob(((JobInvocation.Builder)object).build(), true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void handleMessage(Message message) {
        if (message == null) {
            return;
        }
        AppOpsManager appOpsManager = (AppOpsManager)this.googlePlayReceiver.getApplicationContext().getSystemService("appops");
        try {
            appOpsManager.checkPackage(message.sendingUid, "com.google.android.gms");
        }
        catch (SecurityException securityException) {
            Log.e((String)"FJD.GooglePlayReceiver", (String)"Message was not sent from GCM.");
            return;
        }
        switch (message.what) {
            case 4: {
                return;
            }
            default: {
                Log.e((String)"FJD.GooglePlayReceiver", (String)("Unrecognized message received: " + (Object)message));
                return;
            }
            case 1: {
                this.handleStartMessage(message);
                return;
            }
            case 2: 
        }
        this.handleStopMessage(message);
    }
}

