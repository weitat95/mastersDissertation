/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.base;

import com.getqardio.android.shopify.view.base.ListItemViewModel;
import com.getqardio.android.shopify.view.base.RecyclerViewAdapter;
import com.getqardio.android.shopify.view.base.RecyclerViewItemHolder;
import java.lang.invoke.LambdaForm;

final class RecyclerViewAdapter$$Lambda$1
implements RecyclerViewItemHolder.OnClickListener {
    private static final RecyclerViewAdapter$$Lambda$1 instance = new RecyclerViewAdapter$$Lambda$1();

    private RecyclerViewAdapter$$Lambda$1() {
    }

    public static RecyclerViewItemHolder.OnClickListener lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public void onClick(ListItemViewModel listItemViewModel) {
        RecyclerViewAdapter.lambda$new$0(listItemViewModel);
    }
}

