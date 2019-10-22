/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 *  android.view.View
 */
package com.getqardio.android.ui.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ViewUtils;
import android.view.View;

public class HorizontalSpaceItemDecoration
extends RecyclerView.ItemDecoration {
    private final int space;

    public HorizontalSpaceItemDecoration(int n) {
        this.space = n;
    }

    @Override
    public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
        block3: {
            block2: {
                if (recyclerView.getChildAdapterPosition(view) >= state.getItemCount() - 1) break block2;
                if (!ViewUtils.isLayoutRtl((View)recyclerView)) break block3;
                rect.left = this.space;
            }
            return;
        }
        rect.right = this.space;
    }
}

