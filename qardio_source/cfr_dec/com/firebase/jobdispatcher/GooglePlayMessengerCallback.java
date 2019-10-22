/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Message
 *  android.os.Messenger
 *  android.os.RemoteException
 */
package com.firebase.jobdispatcher;

import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import com.firebase.jobdispatcher.JobCallback;

class GooglePlayMessengerCallback
implements JobCallback {
    private final Messenger messenger;
    private final String tag;

    GooglePlayMessengerCallback(Messenger messenger, String string2) {
        this.messenger = messenger;
        this.tag = string2;
    }

    private Message createResultMessage(int n) {
        Message message = Message.obtain();
        message.what = 3;
        message.arg1 = n;
        Bundle bundle = new Bundle();
        bundle.putString("tag", this.tag);
        message.setData(bundle);
        return message;
    }

    @Override
    public void jobFinished(int n) {
        try {
            this.messenger.send(this.createResultMessage(n));
            return;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException);
        }
    }
}

