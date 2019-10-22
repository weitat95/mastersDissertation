/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package android.support.v7.widget;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class SimpleItemAnimator
extends RecyclerView.ItemAnimator {
    boolean mSupportsChangeAnimations = true;

    public abstract boolean animateAdd(RecyclerView.ViewHolder var1);

    @Override
    public boolean animateAppearance(RecyclerView.ViewHolder viewHolder, RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo, RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo2) {
        if (itemHolderInfo != null && (itemHolderInfo.left != itemHolderInfo2.left || itemHolderInfo.top != itemHolderInfo2.top)) {
            return this.animateMove(viewHolder, itemHolderInfo.left, itemHolderInfo.top, itemHolderInfo2.left, itemHolderInfo2.top);
        }
        return this.animateAdd(viewHolder);
    }

    public abstract boolean animateChange(RecyclerView.ViewHolder var1, RecyclerView.ViewHolder var2, int var3, int var4, int var5, int var6);

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean animateChange(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2, RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo, RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo2) {
        int n;
        int n2;
        int n3 = itemHolderInfo.left;
        int n4 = itemHolderInfo.top;
        if (viewHolder2.shouldIgnore()) {
            n2 = itemHolderInfo.left;
            n = itemHolderInfo.top;
            do {
                return this.animateChange(viewHolder, viewHolder2, n3, n4, n2, n);
                break;
            } while (true);
        }
        n2 = itemHolderInfo2.left;
        n = itemHolderInfo2.top;
        return this.animateChange(viewHolder, viewHolder2, n3, n4, n2, n);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean animateDisappearance(RecyclerView.ViewHolder viewHolder, RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo, RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo2) {
        int n = itemHolderInfo.left;
        int n2 = itemHolderInfo.top;
        itemHolderInfo = viewHolder.itemView;
        int n3 = itemHolderInfo2 == null ? itemHolderInfo.getLeft() : itemHolderInfo2.left;
        int n4 = itemHolderInfo2 == null ? itemHolderInfo.getTop() : itemHolderInfo2.top;
        if (!(viewHolder.isRemoved() || n == n3 && n2 == n4)) {
            itemHolderInfo.layout(n3, n4, itemHolderInfo.getWidth() + n3, itemHolderInfo.getHeight() + n4);
            return this.animateMove(viewHolder, n, n2, n3, n4);
        }
        return this.animateRemove(viewHolder);
    }

    public abstract boolean animateMove(RecyclerView.ViewHolder var1, int var2, int var3, int var4, int var5);

    @Override
    public boolean animatePersistence(RecyclerView.ViewHolder viewHolder, RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo, RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo2) {
        if (itemHolderInfo.left != itemHolderInfo2.left || itemHolderInfo.top != itemHolderInfo2.top) {
            return this.animateMove(viewHolder, itemHolderInfo.left, itemHolderInfo.top, itemHolderInfo2.left, itemHolderInfo2.top);
        }
        this.dispatchMoveFinished(viewHolder);
        return false;
    }

    public abstract boolean animateRemove(RecyclerView.ViewHolder var1);

    @Override
    public boolean canReuseUpdatedViewHolder(RecyclerView.ViewHolder viewHolder) {
        return !this.mSupportsChangeAnimations || viewHolder.isInvalid();
    }

    public final void dispatchAddFinished(RecyclerView.ViewHolder viewHolder) {
        this.onAddFinished(viewHolder);
        this.dispatchAnimationFinished(viewHolder);
    }

    public final void dispatchAddStarting(RecyclerView.ViewHolder viewHolder) {
        this.onAddStarting(viewHolder);
    }

    public final void dispatchChangeFinished(RecyclerView.ViewHolder viewHolder, boolean bl) {
        this.onChangeFinished(viewHolder, bl);
        this.dispatchAnimationFinished(viewHolder);
    }

    public final void dispatchChangeStarting(RecyclerView.ViewHolder viewHolder, boolean bl) {
        this.onChangeStarting(viewHolder, bl);
    }

    public final void dispatchMoveFinished(RecyclerView.ViewHolder viewHolder) {
        this.onMoveFinished(viewHolder);
        this.dispatchAnimationFinished(viewHolder);
    }

    public final void dispatchMoveStarting(RecyclerView.ViewHolder viewHolder) {
        this.onMoveStarting(viewHolder);
    }

    public final void dispatchRemoveFinished(RecyclerView.ViewHolder viewHolder) {
        this.onRemoveFinished(viewHolder);
        this.dispatchAnimationFinished(viewHolder);
    }

    public final void dispatchRemoveStarting(RecyclerView.ViewHolder viewHolder) {
        this.onRemoveStarting(viewHolder);
    }

    public void onAddFinished(RecyclerView.ViewHolder viewHolder) {
    }

    public void onAddStarting(RecyclerView.ViewHolder viewHolder) {
    }

    public void onChangeFinished(RecyclerView.ViewHolder viewHolder, boolean bl) {
    }

    public void onChangeStarting(RecyclerView.ViewHolder viewHolder, boolean bl) {
    }

    public void onMoveFinished(RecyclerView.ViewHolder viewHolder) {
    }

    public void onMoveStarting(RecyclerView.ViewHolder viewHolder) {
    }

    public void onRemoveFinished(RecyclerView.ViewHolder viewHolder) {
    }

    public void onRemoveStarting(RecyclerView.ViewHolder viewHolder) {
    }

    public void setSupportsChangeAnimations(boolean bl) {
        this.mSupportsChangeAnimations = bl;
    }
}

