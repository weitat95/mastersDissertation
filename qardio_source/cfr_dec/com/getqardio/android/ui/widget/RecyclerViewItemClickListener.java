/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.GestureDetector
 *  android.view.GestureDetector$OnGestureListener
 *  android.view.GestureDetector$SimpleOnGestureListener
 *  android.view.MotionEvent
 *  android.view.View
 */
package com.getqardio.android.ui.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class RecyclerViewItemClickListener
implements RecyclerView.OnItemTouchListener {
    private GestureDetector gestureDetector;
    private OnItemClickListener listener;

    public RecyclerViewItemClickListener(Context context, OnItemClickListener onItemClickListener) {
        this.listener = onItemClickListener;
        this.gestureDetector = new GestureDetector(context, (GestureDetector.OnGestureListener)new GestureDetector.SimpleOnGestureListener(){

            public boolean onSingleTapUp(MotionEvent motionEvent) {
                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        View view = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
        if (view != null && this.listener != null && this.gestureDetector.onTouchEvent(motionEvent)) {
            this.listener.onItemClick(view, recyclerView.getChildAdapterPosition(view));
        }
        return false;
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean bl) {
    }

    @Override
    public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
    }

    public static interface OnItemClickListener {
        public void onItemClick(View var1, int var2);
    }

}

