/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.wearable;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.wearable.MessageEvent;

@Deprecated
public interface MessageApi {
    public PendingResult<SendMessageResult> sendMessage(GoogleApiClient var1, String var2, String var3, byte[] var4);

    @Deprecated
    public static interface MessageListener {
        public void onMessageReceived(MessageEvent var1);
    }

    @Deprecated
    public static interface SendMessageResult
    extends Result {
    }

}

