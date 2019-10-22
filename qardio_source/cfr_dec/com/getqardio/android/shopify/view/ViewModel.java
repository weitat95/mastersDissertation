/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view;

import com.getqardio.android.shopify.view.ProgressLiveData;
import com.getqardio.android.shopify.view.UserErrorCallback;

public interface ViewModel {
    public void cancelAllRequests();

    public void cancelRequest(int var1);

    public UserErrorCallback errorErrorCallback();

    public ProgressLiveData progressLiveData();
}

