/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package com.getqardio.android.mvp.common.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class Binder<T>
extends RecyclerView.ViewHolder {
    public Binder(View view) {
        super(view);
    }

    public abstract void bind(T var1);
}

