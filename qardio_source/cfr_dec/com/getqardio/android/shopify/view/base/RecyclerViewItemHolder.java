/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package com.getqardio.android.shopify.view.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.getqardio.android.shopify.util.Util;
import com.getqardio.android.shopify.view.base.ListItemViewHolder;
import com.getqardio.android.shopify.view.base.ListItemViewModel;
import com.getqardio.android.shopify.view.base.RecyclerViewItemHolder$$Lambda$1;

final class RecyclerViewItemHolder
extends RecyclerView.ViewHolder {
    private final ListItemViewHolder.OnClickListener onListItemClickListener;
    private ListItemViewHolder viewItemHolder;

    RecyclerViewItemHolder(View view, OnClickListener onClickListener) {
        super(view);
        Util.checkNotNull(onClickListener, "onClickListener == null");
        onClickListener.getClass();
        this.onListItemClickListener = RecyclerViewItemHolder$$Lambda$1.lambdaFactory$(onClickListener);
    }

    void bindModel(ListItemViewModel listItemViewModel, int n) {
        if (this.viewItemHolder == null) {
            this.viewItemHolder = listItemViewModel.createViewHolder(this.onListItemClickListener);
            this.viewItemHolder.bindView(this.itemView);
        }
        listItemViewModel.bindView(this.viewItemHolder, n);
    }

    public ListItemViewHolder viewItemHolder() {
        return this.viewItemHolder;
    }

    static interface OnClickListener<T, MODEL extends ListItemViewModel<T>> {
        public void onClick(MODEL var1);
    }

}

