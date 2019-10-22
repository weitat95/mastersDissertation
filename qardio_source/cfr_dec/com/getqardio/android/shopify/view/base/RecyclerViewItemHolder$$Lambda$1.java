/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.base;

import com.getqardio.android.shopify.view.base.ListItemViewHolder;
import com.getqardio.android.shopify.view.base.ListItemViewModel;
import com.getqardio.android.shopify.view.base.RecyclerViewItemHolder;
import java.lang.invoke.LambdaForm;

final class RecyclerViewItemHolder$$Lambda$1
implements ListItemViewHolder.OnClickListener {
    private final RecyclerViewItemHolder.OnClickListener arg$1;

    private RecyclerViewItemHolder$$Lambda$1(RecyclerViewItemHolder.OnClickListener onClickListener) {
        this.arg$1 = onClickListener;
    }

    public static ListItemViewHolder.OnClickListener lambdaFactory$(RecyclerViewItemHolder.OnClickListener onClickListener) {
        return new RecyclerViewItemHolder$$Lambda$1(onClickListener);
    }

    @LambdaForm.Hidden
    public void onClick(ListItemViewModel listItemViewModel) {
        this.arg$1.onClick(listItemViewModel);
    }
}

