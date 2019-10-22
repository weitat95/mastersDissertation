/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.os.Handler
 *  android.os.Looper
 *  android.view.View
 *  android.view.ViewTreeObserver
 *  android.view.ViewTreeObserver$OnGlobalLayoutListener
 *  android.view.Window
 */
package com.mixpanel.android.viewcrawler;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import com.mixpanel.android.viewcrawler.UIThreadSet;
import com.mixpanel.android.viewcrawler.ViewVisitor;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

class EditState
extends UIThreadSet<Activity> {
    private final Set<EditBinding> mCurrentEdits;
    private final Map<String, List<ViewVisitor>> mIntendedEdits;
    private final Handler mUiThreadHandler = new Handler(Looper.getMainLooper());

    public EditState() {
        this.mIntendedEdits = new HashMap<String, List<ViewVisitor>>();
        this.mCurrentEdits = new HashSet<EditBinding>();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void applyChangesFromList(View view, List<ViewVisitor> list) {
        Set<EditBinding> set = this.mCurrentEdits;
        synchronized (set) {
            int n = list.size();
            int n2 = 0;
            while (n2 < n) {
                EditBinding editBinding = new EditBinding(view, list.get(n2), this.mUiThreadHandler);
                this.mCurrentEdits.add(editBinding);
                ++n2;
            }
            return;
        }
    }

    private void applyEditsOnUiThread() {
        if (Thread.currentThread() == this.mUiThreadHandler.getLooper().getThread()) {
            this.applyIntendedEdits();
            return;
        }
        this.mUiThreadHandler.post(new Runnable(){

            @Override
            public void run() {
                EditState.this.applyIntendedEdits();
            }
        });
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void applyIntendedEdits() {
        Iterator iterator = this.getAll().iterator();
        while (iterator.hasNext()) {
            Object object = (Activity)iterator.next();
            Object object2 = object.getClass().getCanonicalName();
            View view = object.getWindow().getDecorView().getRootView();
            object = this.mIntendedEdits;
            // MONITORENTER : object
            object2 = this.mIntendedEdits.get(object2);
            List<ViewVisitor> list = this.mIntendedEdits.get(null);
            // MONITOREXIT : object
            if (object2 != null) {
                this.applyChangesFromList(view, (List<ViewVisitor>)object2);
            }
            if (list == null) continue;
            this.applyChangesFromList(view, list);
        }
    }

    @Override
    public void add(Activity activity) {
        super.add(activity);
        this.applyEditsOnUiThread();
    }

    @Override
    public void remove(Activity activity) {
        super.remove(activity);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setEdits(Map<String, List<ViewVisitor>> map) {
        Object object = this.mCurrentEdits;
        synchronized (object) {
            Iterator<EditBinding> iterator = this.mCurrentEdits.iterator();
            do {
                if (!iterator.hasNext()) {
                    this.mCurrentEdits.clear();
                    // MONITOREXIT [4, 6, 7] lbl7 : MonitorExitStatement: MONITOREXIT : var2_2
                    object = this.mIntendedEdits;
                    synchronized (object) {
                        this.mIntendedEdits.clear();
                        this.mIntendedEdits.putAll(map);
                    }
                    this.applyEditsOnUiThread();
                    return;
                }
                iterator.next().kill();
            } while (true);
        }
    }

    private static class EditBinding
    implements ViewTreeObserver.OnGlobalLayoutListener,
    Runnable {
        private boolean mAlive;
        private volatile boolean mDying;
        private final ViewVisitor mEdit;
        private final Handler mHandler;
        private final WeakReference<View> mViewRoot;

        public EditBinding(View view, ViewVisitor viewVisitor, Handler handler) {
            this.mEdit = viewVisitor;
            this.mViewRoot = new WeakReference<View>(view);
            this.mHandler = handler;
            this.mAlive = true;
            this.mDying = false;
            view = view.getViewTreeObserver();
            if (view.isAlive()) {
                view.addOnGlobalLayoutListener((ViewTreeObserver.OnGlobalLayoutListener)this);
            }
            this.run();
        }

        private void cleanUp() {
            if (this.mAlive) {
                View view = (View)this.mViewRoot.get();
                if (view != null && (view = view.getViewTreeObserver()).isAlive()) {
                    view.removeGlobalOnLayoutListener((ViewTreeObserver.OnGlobalLayoutListener)this);
                }
                this.mEdit.cleanup();
            }
            this.mAlive = false;
        }

        public void kill() {
            this.mDying = true;
            this.mHandler.post((Runnable)this);
        }

        public void onGlobalLayout() {
            this.run();
        }

        @Override
        public void run() {
            if (!this.mAlive) {
                return;
            }
            View view = (View)this.mViewRoot.get();
            if (view == null || this.mDying) {
                this.cleanUp();
                return;
            }
            this.mEdit.visit(view);
            this.mHandler.removeCallbacks((Runnable)this);
            this.mHandler.postDelayed((Runnable)this, 1000L);
        }
    }

}

