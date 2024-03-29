/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.util.Log
 *  android.view.View
 */
package android.support.v7.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

public class DividerItemDecoration
extends RecyclerView.ItemDecoration {
    private static final int[] ATTRS = new int[]{16843284};
    private final Rect mBounds = new Rect();
    private Drawable mDivider;
    private int mOrientation;

    public DividerItemDecoration(Context context, int n) {
        context = context.obtainStyledAttributes(ATTRS);
        this.mDivider = context.getDrawable(0);
        if (this.mDivider == null) {
            Log.w((String)"DividerItem", (String)"@android:attr/listDivider was not set in the theme used for this DividerItemDecoration. Please set that attribute all call setDrawable()");
        }
        context.recycle();
        this.setOrientation(n);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void drawHorizontal(Canvas canvas, RecyclerView recyclerView) {
        int n;
        int n2;
        canvas.save();
        if (recyclerView.getClipToPadding()) {
            n2 = recyclerView.getPaddingTop();
            n = recyclerView.getHeight() - recyclerView.getPaddingBottom();
            canvas.clipRect(recyclerView.getPaddingLeft(), n2, recyclerView.getWidth() - recyclerView.getPaddingRight(), n);
        } else {
            n2 = 0;
            n = recyclerView.getHeight();
        }
        int n3 = recyclerView.getChildCount();
        int n4 = 0;
        do {
            if (n4 >= n3) {
                canvas.restore();
                return;
            }
            View view = recyclerView.getChildAt(n4);
            recyclerView.getLayoutManager().getDecoratedBoundsWithMargins(view, this.mBounds);
            int n5 = this.mBounds.right + Math.round(view.getTranslationX());
            int n6 = this.mDivider.getIntrinsicWidth();
            this.mDivider.setBounds(n5 - n6, n2, n5, n);
            this.mDivider.draw(canvas);
            ++n4;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void drawVertical(Canvas canvas, RecyclerView recyclerView) {
        int n;
        int n2;
        canvas.save();
        if (recyclerView.getClipToPadding()) {
            n = recyclerView.getPaddingLeft();
            n2 = recyclerView.getWidth() - recyclerView.getPaddingRight();
            canvas.clipRect(n, recyclerView.getPaddingTop(), n2, recyclerView.getHeight() - recyclerView.getPaddingBottom());
        } else {
            n = 0;
            n2 = recyclerView.getWidth();
        }
        int n3 = recyclerView.getChildCount();
        int n4 = 0;
        do {
            if (n4 >= n3) {
                canvas.restore();
                return;
            }
            View view = recyclerView.getChildAt(n4);
            recyclerView.getDecoratedBoundsWithMargins(view, this.mBounds);
            int n5 = this.mBounds.bottom + Math.round(view.getTranslationY());
            int n6 = this.mDivider.getIntrinsicHeight();
            this.mDivider.setBounds(n, n5 - n6, n2, n5);
            this.mDivider.draw(canvas);
            ++n4;
        } while (true);
    }

    @Override
    public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
        if (this.mDivider == null) {
            rect.set(0, 0, 0, 0);
            return;
        }
        if (this.mOrientation == 1) {
            rect.set(0, 0, 0, this.mDivider.getIntrinsicHeight());
            return;
        }
        rect.set(0, 0, this.mDivider.getIntrinsicWidth(), 0);
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
        if (recyclerView.getLayoutManager() == null || this.mDivider == null) {
            return;
        }
        if (this.mOrientation == 1) {
            this.drawVertical(canvas, recyclerView);
            return;
        }
        this.drawHorizontal(canvas, recyclerView);
    }

    public void setOrientation(int n) {
        if (n != 0 && n != 1) {
            throw new IllegalArgumentException("Invalid orientation. It should be either HORIZONTAL or VERTICAL");
        }
        this.mOrientation = n;
    }
}

