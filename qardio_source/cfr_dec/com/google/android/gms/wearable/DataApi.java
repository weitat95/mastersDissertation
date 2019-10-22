/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package com.google.android.gms.wearable;

import android.net.Uri;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItemBuffer;
import com.google.android.gms.wearable.PutDataRequest;

@Deprecated
public interface DataApi {
    public PendingResult<DeleteDataItemsResult> deleteDataItems(GoogleApiClient var1, Uri var2);

    public PendingResult<DataItemBuffer> getDataItems(GoogleApiClient var1);

    public PendingResult<DataItemResult> putDataItem(GoogleApiClient var1, PutDataRequest var2);

    @Deprecated
    public static interface DataItemResult
    extends Result {
    }

    @Deprecated
    public static interface DataListener {
        public void onDataChanged(DataEventBuffer var1);
    }

    @Deprecated
    public static interface DeleteDataItemsResult
    extends Result {
    }

}

