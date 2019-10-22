/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.base;

import com.getqardio.android.shopify.view.base.ListItemViewModel;
import com.getqardio.android.shopify.view.base.RecyclerViewAdapter;
import com.getqardio.android.shopify.view.base.RecyclerViewItemHolder;
import java.lang.invoke.LambdaForm;

final class RecyclerViewAdapter$$Lambda$2
implements RecyclerViewItemHolder.OnClickListener {
    private final RecyclerViewAdapter.OnItemClickListener arg$1;

    private RecyclerViewAdapter$$Lambda$2(RecyclerViewAdapter.OnItemClickListener onItemClickListener) {
        this.arg$1 = onItemClickListener;
    }

    public static RecyclerViewItemHolder.OnClickListener lambdaFactory$(RecyclerViewAdapter.OnItemClickListener onItemClickListener) {
        return new RecyclerViewAdapter$$Lambda$2(onItemClickListener);
    }

    @LambdaForm.Hidden
    public void onClick(ListItemViewModel listItemViewModel) {
        this.arg$1.onItemClick(listItemViewModel);
    }
}

