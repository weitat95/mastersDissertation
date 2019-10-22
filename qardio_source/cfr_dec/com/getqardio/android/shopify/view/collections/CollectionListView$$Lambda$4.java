/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.collections;

import android.support.v4.widget.SwipeRefreshLayout;
import com.getqardio.android.shopify.view.collections.CollectionListView;
import java.lang.invoke.LambdaForm;

final class CollectionListView$$Lambda$4
implements SwipeRefreshLayout.OnRefreshListener {
    private final CollectionListView arg$1;

    private CollectionListView$$Lambda$4(CollectionListView collectionListView) {
        this.arg$1 = collectionListView;
    }

    public static SwipeRefreshLayout.OnRefreshListener lambdaFactory$(CollectionListView collectionListView) {
        return new CollectionListView$$Lambda$4(collectionListView);
    }

    @LambdaForm.Hidden
    @Override
    public void onRefresh() {
        this.arg$1.lambda$onFinishInflate$2();
    }
}

