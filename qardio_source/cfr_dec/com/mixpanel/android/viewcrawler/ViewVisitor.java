/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.graphics.Bitmap
 *  android.graphics.drawable.BitmapDrawable
 *  android.text.Editable
 *  android.text.TextWatcher
 *  android.util.SparseArray
 *  android.view.View
 *  android.view.View$AccessibilityDelegate
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.RelativeLayout
 *  android.widget.RelativeLayout$LayoutParams
 *  android.widget.TextView
 */
package com.mixpanel.android.viewcrawler;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.mixpanel.android.util.MPLog;
import com.mixpanel.android.viewcrawler.Caller;
import com.mixpanel.android.viewcrawler.Pathfinder;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.WeakHashMap;

@TargetApi(value=16)
abstract class ViewVisitor
implements Pathfinder.Accumulator {
    private final List<Pathfinder.PathElement> mPath;
    private final Pathfinder mPathfinder;

    protected ViewVisitor(List<Pathfinder.PathElement> list) {
        this.mPath = list;
        this.mPathfinder = new Pathfinder();
    }

    public abstract void cleanup();

    protected List<Pathfinder.PathElement> getPath() {
        return this.mPath;
    }

    protected Pathfinder getPathfinder() {
        return this.mPathfinder;
    }

    public void visit(View view) {
        this.mPathfinder.findTargetsInRoot(view, this.mPath, this);
    }

    public static class AddAccessibilityEventVisitor
    extends EventTriggeringVisitor {
        private final int mEventType;
        private final WeakHashMap<View, TrackingAccessibilityDelegate> mWatching;

        public AddAccessibilityEventVisitor(List<Pathfinder.PathElement> list, int n, String string2, OnEventListener onEventListener) {
            super(list, string2, onEventListener, false);
            this.mEventType = n;
            this.mWatching = new WeakHashMap();
        }

        private View.AccessibilityDelegate getOldDelegate(View view) {
            try {
                view = (View.AccessibilityDelegate)view.getClass().getMethod("getAccessibilityDelegate", new Class[0]).invoke((Object)view, new Object[0]);
                return view;
            }
            catch (InvocationTargetException invocationTargetException) {
                MPLog.w("MixpanelAPI.ViewVisitor", "getAccessibilityDelegate threw an exception when called.", invocationTargetException);
                return null;
            }
            catch (IllegalAccessException illegalAccessException) {
                return null;
            }
            catch (NoSuchMethodException noSuchMethodException) {
                return null;
            }
        }

        @Override
        public void accumulate(View view) {
            View.AccessibilityDelegate accessibilityDelegate = this.getOldDelegate(view);
            if (accessibilityDelegate instanceof TrackingAccessibilityDelegate && ((TrackingAccessibilityDelegate)accessibilityDelegate).willFireEvent(this.getEventName())) {
                return;
            }
            accessibilityDelegate = new TrackingAccessibilityDelegate(accessibilityDelegate);
            view.setAccessibilityDelegate(accessibilityDelegate);
            this.mWatching.put(view, (TrackingAccessibilityDelegate)accessibilityDelegate);
        }

        @Override
        public void cleanup() {
            for (Map.Entry<View, TrackingAccessibilityDelegate> entry : this.mWatching.entrySet()) {
                View view = entry.getKey();
                TrackingAccessibilityDelegate object = entry.getValue();
                View.AccessibilityDelegate accessibilityDelegate = this.getOldDelegate(view);
                if (accessibilityDelegate == object) {
                    view.setAccessibilityDelegate(object.getRealDelegate());
                    continue;
                }
                if (!(accessibilityDelegate instanceof TrackingAccessibilityDelegate)) continue;
                ((TrackingAccessibilityDelegate)accessibilityDelegate).removeFromDelegateChain(object);
            }
            this.mWatching.clear();
        }

        private class TrackingAccessibilityDelegate
        extends View.AccessibilityDelegate {
            private View.AccessibilityDelegate mRealDelegate;

            public TrackingAccessibilityDelegate(View.AccessibilityDelegate accessibilityDelegate) {
                this.mRealDelegate = accessibilityDelegate;
            }

            public View.AccessibilityDelegate getRealDelegate() {
                return this.mRealDelegate;
            }

            /*
             * Enabled aggressive block sorting
             */
            public void removeFromDelegateChain(TrackingAccessibilityDelegate trackingAccessibilityDelegate) {
                if (this.mRealDelegate == trackingAccessibilityDelegate) {
                    this.mRealDelegate = trackingAccessibilityDelegate.getRealDelegate();
                    return;
                } else {
                    if (!(this.mRealDelegate instanceof TrackingAccessibilityDelegate)) return;
                    {
                        ((TrackingAccessibilityDelegate)this.mRealDelegate).removeFromDelegateChain(trackingAccessibilityDelegate);
                        return;
                    }
                }
            }

            public void sendAccessibilityEvent(View view, int n) {
                if (n == AddAccessibilityEventVisitor.this.mEventType) {
                    AddAccessibilityEventVisitor.this.fireEvent(view);
                }
                if (this.mRealDelegate != null) {
                    this.mRealDelegate.sendAccessibilityEvent(view, n);
                }
            }

            public boolean willFireEvent(String string2) {
                if (AddAccessibilityEventVisitor.this.getEventName() == string2) {
                    return true;
                }
                if (this.mRealDelegate instanceof TrackingAccessibilityDelegate) {
                    return ((TrackingAccessibilityDelegate)this.mRealDelegate).willFireEvent(string2);
                }
                return false;
            }
        }

    }

    public static class AddTextChangeListener
    extends EventTriggeringVisitor {
        private final Map<TextView, TextWatcher> mWatching = new HashMap<TextView, TextWatcher>();

        public AddTextChangeListener(List<Pathfinder.PathElement> list, String string2, OnEventListener onEventListener) {
            super(list, string2, onEventListener, true);
        }

        @Override
        public void accumulate(View view) {
            if (view instanceof TextView) {
                view = (TextView)view;
                TrackingTextWatcher trackingTextWatcher = new TrackingTextWatcher(view);
                TextWatcher textWatcher = this.mWatching.get((Object)view);
                if (textWatcher != null) {
                    view.removeTextChangedListener(textWatcher);
                }
                view.addTextChangedListener((TextWatcher)trackingTextWatcher);
                this.mWatching.put((TextView)view, trackingTextWatcher);
            }
        }

        @Override
        public void cleanup() {
            for (Map.Entry<TextView, TextWatcher> entry : this.mWatching.entrySet()) {
                entry.getKey().removeTextChangedListener(entry.getValue());
            }
            this.mWatching.clear();
        }

        private class TrackingTextWatcher
        implements TextWatcher {
            private final View mBoundTo;

            public TrackingTextWatcher(View view) {
                this.mBoundTo = view;
            }

            public void afterTextChanged(Editable editable) {
                AddTextChangeListener.this.fireEvent(this.mBoundTo);
            }

            public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }

            public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }
        }

    }

    private static class CycleDetector {
        private CycleDetector() {
        }

        private boolean detectSubgraphCycle(TreeMap<View, List<View>> treeMap, View view, List<View> list) {
            if (list.contains((Object)view)) {
                return false;
            }
            if (treeMap.containsKey((Object)view)) {
                List<View> list2 = treeMap.remove((Object)view);
                list.add(view);
                int n = list2.size();
                for (int i = 0; i < n; ++i) {
                    if (this.detectSubgraphCycle(treeMap, list2.get(i), list)) continue;
                    return false;
                }
                list.remove((Object)view);
            }
            return true;
        }

        public boolean hasCycle(TreeMap<View, List<View>> treeMap) {
            ArrayList<View> arrayList = new ArrayList<View>();
            while (!treeMap.isEmpty()) {
                if (this.detectSubgraphCycle(treeMap, treeMap.firstKey(), arrayList)) continue;
                return false;
            }
            return true;
        }
    }

    private static abstract class EventTriggeringVisitor
    extends ViewVisitor {
        private final boolean mDebounce;
        private final String mEventName;
        private final OnEventListener mListener;

        public EventTriggeringVisitor(List<Pathfinder.PathElement> list, String string2, OnEventListener onEventListener, boolean bl) {
            super(list);
            this.mListener = onEventListener;
            this.mEventName = string2;
            this.mDebounce = bl;
        }

        protected void fireEvent(View view) {
            this.mListener.OnEvent(view, this.mEventName, this.mDebounce);
        }

        protected String getEventName() {
            return this.mEventName;
        }
    }

    public static class LayoutErrorMessage {
        private final String mErrorType;
        private final String mName;

        public LayoutErrorMessage(String string2, String string3) {
            this.mErrorType = string2;
            this.mName = string3;
        }

        public String getErrorType() {
            return this.mErrorType;
        }

        public String getName() {
            return this.mName;
        }
    }

    public static class LayoutRule {
        public final int anchor;
        public final int verb;
        public final int viewId;

        public LayoutRule(int n, int n2, int n3) {
            this.viewId = n;
            this.verb = n2;
            this.anchor = n3;
        }
    }

    public static class LayoutUpdateVisitor
    extends ViewVisitor {
        private static final Set<Integer> mHorizontalRules = new HashSet<Integer>(Arrays.asList(0, 1, 5, 7));
        private static final Set<Integer> mVerticalRules = new HashSet<Integer>(Arrays.asList(2, 3, 4, 6, 8));
        private boolean mAlive;
        private final List<LayoutRule> mArgs;
        private final CycleDetector mCycleDetector;
        private final String mName;
        private final OnLayoutErrorListener mOnLayoutErrorListener;
        private final WeakHashMap<View, int[]> mOriginalValues = new WeakHashMap();

        public LayoutUpdateVisitor(List<Pathfinder.PathElement> list, List<LayoutRule> list2, String string2, OnLayoutErrorListener onLayoutErrorListener) {
            super(list);
            this.mArgs = list2;
            this.mName = string2;
            this.mAlive = true;
            this.mOnLayoutErrorListener = onLayoutErrorListener;
            this.mCycleDetector = new CycleDetector();
        }

        private boolean verifyLayout(Set<Integer> set, SparseArray<View> sparseArray) {
            TreeMap<View, List<View>> treeMap = new TreeMap<View, List<View>>(new Comparator<View>(){

                @Override
                public int compare(View view, View view2) {
                    if (view == view2) {
                        return 0;
                    }
                    if (view == null) {
                        return -1;
                    }
                    if (view2 == null) {
                        return 1;
                    }
                    return view2.hashCode() - view.hashCode();
                }
            });
            int n = sparseArray.size();
            for (int i = 0; i < n; ++i) {
                View view = (View)sparseArray.valueAt(i);
                int[] arrn = ((RelativeLayout.LayoutParams)view.getLayoutParams()).getRules();
                ArrayList<Object> arrayList = new ArrayList<Object>();
                Iterator<Integer> iterator = set.iterator();
                while (iterator.hasNext()) {
                    int n2 = arrn[iterator.next()];
                    if (n2 <= 0 || n2 == view.getId()) continue;
                    arrayList.add(sparseArray.get(n2));
                }
                treeMap.put(view, arrayList);
            }
            return this.mCycleDetector.hasCycle(treeMap);
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         */
        @Override
        public void accumulate(View object) {
            int n;
            View view;
            ViewGroup viewGroup = (ViewGroup)object;
            SparseArray sparseArray = new SparseArray();
            int n2 = viewGroup.getChildCount();
            for (n = 0; n < n2; ++n) {
                view = viewGroup.getChildAt(n);
                int n3 = view.getId();
                if (n3 <= 0) continue;
                sparseArray.put(n3, (Object)view);
            }
            n2 = this.mArgs.size();
            for (n = 0; n < n2; ++n) {
                void var1_7;
                RelativeLayout.LayoutParams layoutParams;
                int[] arrn;
                LayoutRule layoutRule = this.mArgs.get(n);
                view = (View)sparseArray.get(layoutRule.viewId);
                if (view == null || (arrn = (int[])(layoutParams = (RelativeLayout.LayoutParams)view.getLayoutParams()).getRules().clone())[layoutRule.verb] == layoutRule.anchor) continue;
                if (!this.mOriginalValues.containsKey((Object)view)) {
                    this.mOriginalValues.put(view, arrn);
                }
                layoutParams.addRule(layoutRule.verb, layoutRule.anchor);
                if (mHorizontalRules.contains(layoutRule.verb)) {
                    Set<Integer> set = mHorizontalRules;
                } else if (mVerticalRules.contains(layoutRule.verb)) {
                    Set<Integer> set = mVerticalRules;
                } else {
                    Object var1_9 = null;
                }
                if (var1_7 != null && !this.verifyLayout((Set<Integer>)var1_7, (SparseArray<View>)sparseArray)) {
                    this.cleanup();
                    this.mOnLayoutErrorListener.onLayoutError(new LayoutErrorMessage("circular_dependency", this.mName));
                    return;
                }
                view.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
            }
        }

        @Override
        public void cleanup() {
            for (Map.Entry<View, int[]> entry : this.mOriginalValues.entrySet()) {
                View view = entry.getKey();
                int[] arrn = entry.getValue();
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)view.getLayoutParams();
                for (int i = 0; i < arrn.length; ++i) {
                    layoutParams.addRule(i, arrn[i]);
                }
                view.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
            }
            this.mAlive = false;
        }

        @Override
        public void visit(View view) {
            if (this.mAlive) {
                this.getPathfinder().findTargetsInRoot(view, this.getPath(), this);
            }
        }

    }

    public static interface OnEventListener {
        public void OnEvent(View var1, String var2, boolean var3);
    }

    public static interface OnLayoutErrorListener {
        public void onLayoutError(LayoutErrorMessage var1);
    }

    public static class PropertySetVisitor
    extends ViewVisitor {
        private final Caller mAccessor;
        private final Caller mMutator;
        private final Object[] mOriginalValueHolder;
        private final WeakHashMap<View, Object> mOriginalValues;

        public PropertySetVisitor(List<Pathfinder.PathElement> list, Caller caller, Caller caller2) {
            super(list);
            this.mMutator = caller;
            this.mAccessor = caller2;
            this.mOriginalValueHolder = new Object[1];
            this.mOriginalValues = new WeakHashMap();
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void accumulate(View view) {
            block10: {
                Object object;
                block12: {
                    Object object2;
                    block13: {
                        block11: {
                            Object[] arrobject;
                            if (this.mAccessor == null || 1 != (arrobject = this.mMutator.getArgs()).length) break block10;
                            object2 = arrobject[0];
                            object = this.mAccessor.applyMethod(view);
                            if (object2 == object) break block11;
                            if (object2 == null) break block12;
                            if (!(object2 instanceof Bitmap) || !(object instanceof Bitmap)) break block13;
                            if (!((Bitmap)object2).sameAs((Bitmap)object)) break block12;
                        }
                        return;
                    }
                    if (object2 instanceof BitmapDrawable && object instanceof BitmapDrawable) {
                        object2 = ((BitmapDrawable)object2).getBitmap();
                        Bitmap bitmap = ((BitmapDrawable)object).getBitmap();
                        if (object2 != null && object2.sameAs(bitmap)) {
                            return;
                        }
                    } else if (object2.equals(object)) {
                        return;
                    }
                }
                if (!(object instanceof Bitmap || object instanceof BitmapDrawable || this.mOriginalValues.containsKey((Object)view))) {
                    this.mOriginalValueHolder[0] = object;
                    if (this.mMutator.argsAreApplicable(this.mOriginalValueHolder)) {
                        this.mOriginalValues.put(view, object);
                    } else {
                        this.mOriginalValues.put(view, null);
                    }
                }
            }
            this.mMutator.applyMethod(view);
        }

        @Override
        public void cleanup() {
            for (Map.Entry<View, Object> entry : this.mOriginalValues.entrySet()) {
                View view = entry.getKey();
                Object object = entry.getValue();
                if (object == null) continue;
                this.mOriginalValueHolder[0] = object;
                this.mMutator.applyMethodWithArguments(view, this.mOriginalValueHolder);
            }
        }
    }

    public static class ViewDetectorVisitor
    extends EventTriggeringVisitor {
        private boolean mSeen = false;

        public ViewDetectorVisitor(List<Pathfinder.PathElement> list, String string2, OnEventListener onEventListener) {
            super(list, string2, onEventListener, false);
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void accumulate(View view) {
            if (view != null && !this.mSeen) {
                this.fireEvent(view);
            }
            boolean bl = view != null;
            this.mSeen = bl;
        }

        @Override
        public void cleanup() {
        }
    }

}

