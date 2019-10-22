/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewGroup
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.mixpanel.android.viewcrawler;

import android.view.View;
import android.view.ViewGroup;
import com.mixpanel.android.util.MPLog;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

class Pathfinder {
    private final IntStack mIndexStack = new IntStack();

    private View findPrefixedMatch(PathElement pathElement, View view, int n) {
        int n2 = this.mIndexStack.read(n);
        if (this.matches(pathElement, view)) {
            this.mIndexStack.increment(n);
            if (pathElement.index == -1 || pathElement.index == n2) {
                return view;
            }
        }
        if (pathElement.prefix == 1 && view instanceof ViewGroup) {
            view = (ViewGroup)view;
            int n3 = view.getChildCount();
            for (n2 = 0; n2 < n3; ++n2) {
                View view2 = this.findPrefixedMatch(pathElement, view.getChildAt(n2), n);
                if (view2 == null) continue;
                return view2;
            }
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void findTargetsInMatchedView(View view, List<PathElement> list, Accumulator accumulator) {
        if (list.isEmpty()) {
            accumulator.accumulate(view);
            return;
        }
        if (!(view instanceof ViewGroup)) return;
        {
            if (this.mIndexStack.full()) {
                MPLog.v("MixpanelAPI.PathFinder", "Path is too deep, will not match");
                return;
            }
        }
        view = (ViewGroup)view;
        PathElement pathElement = list.get(0);
        list = list.subList(1, list.size());
        int n = view.getChildCount();
        int n2 = this.mIndexStack.alloc();
        int n3 = 0;
        do {
            block12: {
                block11: {
                    if (n3 >= n) break block11;
                    View view2 = this.findPrefixedMatch(pathElement, view.getChildAt(n3), n2);
                    if (view2 != null) {
                        this.findTargetsInMatchedView(view2, list, accumulator);
                    }
                    if (pathElement.index < 0 || this.mIndexStack.read(n2) <= pathElement.index) break block12;
                }
                this.mIndexStack.free();
                return;
            }
            ++n3;
        } while (true);
    }

    private static boolean hasClassName(Object class_, String string2) {
        class_ = class_.getClass();
        while (!class_.getCanonicalName().equals(string2)) {
            if (class_ == Object.class) {
                return false;
            }
            class_ = class_.getSuperclass();
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean matches(PathElement pathElement, View view) {
        block3: {
            block2: {
                if (pathElement.viewClassName != null && !Pathfinder.hasClassName((Object)view, pathElement.viewClassName) || -1 != pathElement.viewId && view.getId() != pathElement.viewId || pathElement.contentDescription != null && !pathElement.contentDescription.equals(view.getContentDescription())) break block2;
                String string2 = pathElement.tag;
                if (pathElement.tag == null || view.getTag() != null && string2.equals(view.getTag().toString())) break block3;
            }
            return false;
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void findTargetsInRoot(View view, List<PathElement> list, Accumulator accumulator) {
        block5: {
            block4: {
                if (list.isEmpty()) break block4;
                if (this.mIndexStack.full()) {
                    MPLog.w("MixpanelAPI.PathFinder", "There appears to be a concurrency issue in the pathfinding code. Path will not be matched.");
                    return;
                }
                PathElement pathElement = list.get(0);
                list = list.subList(1, list.size());
                view = this.findPrefixedMatch(pathElement, view, this.mIndexStack.alloc());
                this.mIndexStack.free();
                if (view != null) break block5;
            }
            return;
        }
        this.findTargetsInMatchedView(view, list, accumulator);
    }

    public static interface Accumulator {
        public void accumulate(View var1);
    }

    private static class IntStack {
        private final int[] mStack = new int[256];
        private int mStackSize = 0;

        public int alloc() {
            int n = this.mStackSize++;
            this.mStack[n] = 0;
            return n;
        }

        public void free() {
            --this.mStackSize;
            if (this.mStackSize < 0) {
                throw new ArrayIndexOutOfBoundsException(this.mStackSize);
            }
        }

        public boolean full() {
            return this.mStack.length == this.mStackSize;
        }

        public void increment(int n) {
            int[] arrn = this.mStack;
            arrn[n] = arrn[n] + 1;
        }

        public int read(int n) {
            return this.mStack[n];
        }
    }

    public static class PathElement {
        public final String contentDescription;
        public final int index;
        public final int prefix;
        public final String tag;
        public final String viewClassName;
        public final int viewId;

        public PathElement(int n, String string2, int n2, int n3, String string3, String string4) {
            this.prefix = n;
            this.viewClassName = string2;
            this.index = n2;
            this.viewId = n3;
            this.contentDescription = string3;
            this.tag = string4;
        }

        public String toString() {
            try {
                Object object = new JSONObject();
                if (this.prefix == 1) {
                    object.put("prefix", (Object)"shortest");
                }
                if (this.viewClassName != null) {
                    object.put("view_class", (Object)this.viewClassName);
                }
                if (this.index > -1) {
                    object.put("index", this.index);
                }
                if (this.viewId > -1) {
                    object.put("id", this.viewId);
                }
                if (this.contentDescription != null) {
                    object.put("contentDescription", (Object)this.contentDescription);
                }
                if (this.tag != null) {
                    object.put("tag", (Object)this.tag);
                }
                object = object.toString();
                return object;
            }
            catch (JSONException jSONException) {
                throw new RuntimeException("Can't serialize PathElement to String", jSONException);
            }
        }
    }

}

