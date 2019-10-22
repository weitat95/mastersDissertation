/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.getqardio.android.shopify.view.collections;

import android.view.View;
import com.getqardio.android.shopify.view.collections.CollectionListActivity;
import java.lang.invoke.LambdaForm;

final class CollectionListActivity$$Lambda$1
implements View.OnClickListener {
    private final CollectionListActivity arg$1;

    private CollectionListActivity$$Lambda$1(CollectionListActivity collectionListActivity) {
        this.arg$1 = collectionListActivity;
    }

    public static View.OnClickListener lambdaFactory$(CollectionListActivity collectionListActivity) {
        return new CollectionListActivity$$Lambda$1(collectionListActivity);
    }

    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$onCreateOptionsMenu$0(view);
    }
}

