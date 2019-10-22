/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package com.getqardio.android.shopify.view.base;

import android.view.View;
import butterknife.ButterKnife;
import com.getqardio.android.shopify.util.Util;
import com.getqardio.android.shopify.view.base.ListItemViewModel;

public abstract class ListItemViewHolder<T, MODEL extends ListItemViewModel<T>> {
    private MODEL itemModel;
    private final OnClickListener onClickListener;

    public ListItemViewHolder(OnClickListener onClickListener) {
        this.onClickListener = Util.checkNotNull(onClickListener, "clickListener == null");
    }

    public void bindModel(MODEL MODEL, int n) {
        this.itemModel = MODEL;
    }

    protected void bindView(View view) {
        ButterKnife.bind(this, view);
    }

    public MODEL itemModel() {
        return this.itemModel;
    }

    protected void notifyOnClickListener() {
        this.onClickListener.onClick(this.itemModel);
    }

    protected OnClickListener onClickListener() {
        return this.onClickListener;
    }

    public static interface OnClickListener<T, MODEL extends ListItemViewModel<T>> {
        public void onClick(MODEL var1);
    }

}

