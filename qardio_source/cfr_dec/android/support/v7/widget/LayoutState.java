/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package android.support.v7.widget;

import android.support.v7.widget.RecyclerView;
import android.view.View;

class LayoutState {
    int mAvailable;
    int mCurrentPosition;
    int mEndLine = 0;
    boolean mInfinite;
    int mItemDirection;
    int mLayoutDirection;
    boolean mRecycle = true;
    int mStartLine = 0;
    boolean mStopInFocusable;

    LayoutState() {
    }

    boolean hasMore(RecyclerView.State state) {
        return this.mCurrentPosition >= 0 && this.mCurrentPosition < state.getItemCount();
    }

    View next(RecyclerView.Recycler recycler) {
        recycler = recycler.getViewForPosition(this.mCurrentPosition);
        this.mCurrentPosition += this.mItemDirection;
        return recycler;
    }

    public String toString() {
        return "LayoutState{mAvailable=" + this.mAvailable + ", mCurrentPosition=" + this.mCurrentPosition + ", mItemDirection=" + this.mItemDirection + ", mLayoutDirection=" + this.mLayoutDirection + ", mStartLine=" + this.mStartLine + ", mEndLine=" + this.mEndLine + '}';
    }
}

