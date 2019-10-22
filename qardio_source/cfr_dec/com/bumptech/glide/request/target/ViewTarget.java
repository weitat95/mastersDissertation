/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.content.Context
 *  android.graphics.Point
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.Log
 *  android.view.Display
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewTreeObserver
 *  android.view.ViewTreeObserver$OnPreDrawListener
 *  android.view.WindowManager
 */
package com.bumptech.glide.request.target;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.target.BaseTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class ViewTarget<T extends View, Z>
extends BaseTarget<Z> {
    private static boolean isTagUsedAtLeastOnce = false;
    private static Integer tagId = null;
    private final SizeDeterminer sizeDeterminer;
    protected final T view;

    public ViewTarget(T t) {
        if (t == null) {
            throw new NullPointerException("View must not be null!");
        }
        this.view = t;
        this.sizeDeterminer = new SizeDeterminer((View)t);
    }

    private Object getTag() {
        if (tagId == null) {
            return this.view.getTag();
        }
        return this.view.getTag(tagId.intValue());
    }

    private void setTag(Object object) {
        if (tagId == null) {
            isTagUsedAtLeastOnce = true;
            this.view.setTag(object);
            return;
        }
        this.view.setTag(tagId.intValue(), object);
    }

    @Override
    public Request getRequest() {
        block3: {
            Request request;
            block2: {
                Object object = this.getTag();
                request = null;
                if (object == null) break block2;
                if (!(object instanceof Request)) break block3;
                request = (Request)object;
            }
            return request;
        }
        throw new IllegalArgumentException("You must not call setTag() on a view Glide is targeting");
    }

    @Override
    public void getSize(SizeReadyCallback sizeReadyCallback) {
        this.sizeDeterminer.getSize(sizeReadyCallback);
    }

    public T getView() {
        return this.view;
    }

    @Override
    public void setRequest(Request request) {
        this.setTag(request);
    }

    public String toString() {
        return "Target for: " + this.view;
    }

    private static class SizeDeterminer {
        private final List<SizeReadyCallback> cbs = new ArrayList<SizeReadyCallback>();
        private Point displayDimens;
        private SizeDeterminerLayoutListener layoutListener;
        private final View view;

        public SizeDeterminer(View view) {
            this.view = view;
        }

        /*
         * Enabled aggressive block sorting
         */
        private void checkCurrentDimens() {
            int n;
            int n2;
            block5: {
                block4: {
                    if (this.cbs.isEmpty()) break block4;
                    n2 = this.getViewWidthOrParam();
                    n = this.getViewHeightOrParam();
                    if (this.isSizeValid(n2) && this.isSizeValid(n)) break block5;
                }
                return;
            }
            this.notifyCbs(n2, n);
            ViewTreeObserver viewTreeObserver = this.view.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.removeOnPreDrawListener((ViewTreeObserver.OnPreDrawListener)this.layoutListener);
            }
            this.layoutListener = null;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @TargetApi(value=13)
        private Point getDisplayDimens() {
            if (this.displayDimens != null) {
                return this.displayDimens;
            }
            Display display = ((WindowManager)this.view.getContext().getSystemService("window")).getDefaultDisplay();
            if (Build.VERSION.SDK_INT >= 13) {
                this.displayDimens = new Point();
                display.getSize(this.displayDimens);
                do {
                    return this.displayDimens;
                    break;
                } while (true);
            }
            this.displayDimens = new Point(display.getWidth(), display.getHeight());
            return this.displayDimens;
        }

        private int getSizeForParam(int n, boolean bl) {
            if (n == -2) {
                Point point = this.getDisplayDimens();
                if (bl) {
                    return point.y;
                }
                return point.x;
            }
            return n;
        }

        private int getViewHeightOrParam() {
            ViewGroup.LayoutParams layoutParams = this.view.getLayoutParams();
            if (this.isSizeValid(this.view.getHeight())) {
                return this.view.getHeight();
            }
            if (layoutParams != null) {
                return this.getSizeForParam(layoutParams.height, true);
            }
            return 0;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        private int getViewWidthOrParam() {
            int n = 0;
            ViewGroup.LayoutParams layoutParams = this.view.getLayoutParams();
            if (this.isSizeValid(this.view.getWidth())) {
                return this.view.getWidth();
            }
            if (layoutParams == null) return n;
            return this.getSizeForParam(layoutParams.width, false);
        }

        private boolean isSizeValid(int n) {
            return n > 0 || n == -2;
        }

        private void notifyCbs(int n, int n2) {
            Iterator<SizeReadyCallback> iterator = this.cbs.iterator();
            while (iterator.hasNext()) {
                iterator.next().onSizeReady(n, n2);
            }
            this.cbs.clear();
        }

        /*
         * Enabled aggressive block sorting
         */
        public void getSize(SizeReadyCallback sizeReadyCallback) {
            int n = this.getViewWidthOrParam();
            int n2 = this.getViewHeightOrParam();
            if (this.isSizeValid(n) && this.isSizeValid(n2)) {
                sizeReadyCallback.onSizeReady(n, n2);
                return;
            } else {
                if (!this.cbs.contains(sizeReadyCallback)) {
                    this.cbs.add(sizeReadyCallback);
                }
                if (this.layoutListener != null) return;
                {
                    sizeReadyCallback = this.view.getViewTreeObserver();
                    this.layoutListener = new SizeDeterminerLayoutListener(this);
                    sizeReadyCallback.addOnPreDrawListener((ViewTreeObserver.OnPreDrawListener)this.layoutListener);
                    return;
                }
            }
        }

        private static class SizeDeterminerLayoutListener
        implements ViewTreeObserver.OnPreDrawListener {
            private final WeakReference<SizeDeterminer> sizeDeterminerRef;

            public SizeDeterminerLayoutListener(SizeDeterminer sizeDeterminer) {
                this.sizeDeterminerRef = new WeakReference<SizeDeterminer>(sizeDeterminer);
            }

            public boolean onPreDraw() {
                SizeDeterminer sizeDeterminer;
                if (Log.isLoggable((String)"ViewTarget", (int)2)) {
                    Log.v((String)"ViewTarget", (String)("OnGlobalLayoutListener called listener=" + this));
                }
                if ((sizeDeterminer = (SizeDeterminer)this.sizeDeterminerRef.get()) != null) {
                    sizeDeterminer.checkCurrentDimens();
                }
                return true;
            }
        }

    }

}

