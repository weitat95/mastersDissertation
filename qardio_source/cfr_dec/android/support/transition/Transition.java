/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.TimeInterpolator
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.content.res.XmlResourceParser
 *  android.graphics.Path
 *  android.graphics.Rect
 *  android.util.AttributeSet
 *  android.util.SparseArray
 *  android.util.SparseIntArray
 *  android.view.InflateException
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewParent
 *  android.view.animation.AnimationUtils
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  org.xmlpull.v1.XmlPullParser
 */
package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.transition.AnimatorUtils;
import android.support.transition.PathMotion;
import android.support.transition.Styleable;
import android.support.transition.TransitionPropagation;
import android.support.transition.TransitionSet;
import android.support.transition.TransitionValues;
import android.support.transition.TransitionValuesMaps;
import android.support.transition.ViewUtils;
import android.support.transition.WindowIdImpl;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.LongSparseArray;
import android.support.v4.util.SimpleArrayMap;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.InflateException;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.AnimationUtils;
import android.widget.ListAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import org.xmlpull.v1.XmlPullParser;

public abstract class Transition
implements Cloneable {
    private static final int[] DEFAULT_MATCH_ORDER = new int[]{2, 1, 3, 4};
    private static final PathMotion STRAIGHT_PATH_MOTION = new PathMotion(){

        @Override
        public Path getPath(float f, float f2, float f3, float f4) {
            Path path = new Path();
            path.moveTo(f, f2);
            path.lineTo(f3, f4);
            return path;
        }
    };
    private static ThreadLocal<ArrayMap<Animator, AnimationInfo>> sRunningAnimators = new ThreadLocal();
    private ArrayList<Animator> mAnimators;
    boolean mCanRemoveViews = false;
    private ArrayList<Animator> mCurrentAnimators;
    long mDuration = -1L;
    private TransitionValuesMaps mEndValues;
    private ArrayList<TransitionValues> mEndValuesList;
    private boolean mEnded = false;
    private EpicenterCallback mEpicenterCallback;
    private TimeInterpolator mInterpolator = null;
    private ArrayList<TransitionListener> mListeners = null;
    private int[] mMatchOrder;
    private String mName = this.getClass().getName();
    private ArrayMap<String, String> mNameOverrides;
    private int mNumInstances = 0;
    TransitionSet mParent = null;
    private PathMotion mPathMotion;
    private boolean mPaused = false;
    TransitionPropagation mPropagation;
    private ViewGroup mSceneRoot = null;
    private long mStartDelay = -1L;
    private TransitionValuesMaps mStartValues;
    private ArrayList<TransitionValues> mStartValuesList;
    private ArrayList<View> mTargetChildExcludes = null;
    private ArrayList<View> mTargetExcludes = null;
    private ArrayList<Integer> mTargetIdChildExcludes = null;
    private ArrayList<Integer> mTargetIdExcludes = null;
    ArrayList<Integer> mTargetIds = new ArrayList();
    private ArrayList<String> mTargetNameExcludes = null;
    private ArrayList<String> mTargetNames = null;
    private ArrayList<Class> mTargetTypeChildExcludes = null;
    private ArrayList<Class> mTargetTypeExcludes = null;
    private ArrayList<Class> mTargetTypes = null;
    ArrayList<View> mTargets = new ArrayList();

    public Transition() {
        this.mStartValues = new TransitionValuesMaps();
        this.mEndValues = new TransitionValuesMaps();
        this.mMatchOrder = DEFAULT_MATCH_ORDER;
        this.mCurrentAnimators = new ArrayList();
        this.mAnimators = new ArrayList();
        this.mPathMotion = STRAIGHT_PATH_MOTION;
    }

    public Transition(Context object, AttributeSet attributeSet) {
        int n;
        this.mStartValues = new TransitionValuesMaps();
        this.mEndValues = new TransitionValuesMaps();
        this.mMatchOrder = DEFAULT_MATCH_ORDER;
        this.mCurrentAnimators = new ArrayList();
        this.mAnimators = new ArrayList();
        this.mPathMotion = STRAIGHT_PATH_MOTION;
        TypedArray typedArray = object.obtainStyledAttributes(attributeSet, Styleable.TRANSITION);
        attributeSet = (XmlResourceParser)attributeSet;
        long l = TypedArrayUtils.getNamedInt(typedArray, (XmlPullParser)attributeSet, "duration", 1, -1);
        if (l >= 0L) {
            this.setDuration(l);
        }
        if ((l = (long)TypedArrayUtils.getNamedInt(typedArray, (XmlPullParser)attributeSet, "startDelay", 2, -1)) > 0L) {
            this.setStartDelay(l);
        }
        if ((n = TypedArrayUtils.getNamedResourceId(typedArray, (XmlPullParser)attributeSet, "interpolator", 0, 0)) > 0) {
            this.setInterpolator((TimeInterpolator)AnimationUtils.loadInterpolator((Context)object, (int)n));
        }
        if ((object = TypedArrayUtils.getNamedString(typedArray, (XmlPullParser)attributeSet, "matchOrder", 3)) != null) {
            this.setMatchOrder(Transition.parseMatchOrder((String)object));
        }
        typedArray.recycle();
    }

    private void addUnmatched(ArrayMap<View, TransitionValues> object, ArrayMap<View, TransitionValues> arrayMap) {
        int n;
        for (n = 0; n < ((SimpleArrayMap)object).size(); ++n) {
            TransitionValues transitionValues = (TransitionValues)((SimpleArrayMap)object).valueAt(n);
            if (!this.isValidTarget(transitionValues.view)) continue;
            this.mStartValuesList.add(transitionValues);
            this.mEndValuesList.add(null);
        }
        for (n = 0; n < arrayMap.size(); ++n) {
            object = (TransitionValues)arrayMap.valueAt(n);
            if (!this.isValidTarget(((TransitionValues)object).view)) continue;
            this.mEndValuesList.add((TransitionValues)object);
            this.mStartValuesList.add(null);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private static void addViewValues(TransitionValuesMaps transitionValuesMaps, View view, TransitionValues object) {
        transitionValuesMaps.mViewValues.put(view, (TransitionValues)object);
        int n = view.getId();
        if (n >= 0) {
            if (transitionValuesMaps.mIdValues.indexOfKey(n) >= 0) {
                transitionValuesMaps.mIdValues.put(n, null);
            } else {
                transitionValuesMaps.mIdValues.put(n, (Object)view);
            }
        }
        if ((object = ViewCompat.getTransitionName(view)) != null) {
            if (transitionValuesMaps.mNameValues.containsKey(object)) {
                transitionValuesMaps.mNameValues.put((String)object, null);
            } else {
                transitionValuesMaps.mNameValues.put((String)object, view);
            }
        }
        if (view.getParent() instanceof ListView && (object = (ListView)view.getParent()).getAdapter().hasStableIds()) {
            long l = object.getItemIdAtPosition(object.getPositionForView(view));
            if (transitionValuesMaps.mItemIdValues.indexOfKey(l) < 0) {
                ViewCompat.setHasTransientState(view, true);
                transitionValuesMaps.mItemIdValues.put(l, view);
                return;
            }
            view = transitionValuesMaps.mItemIdValues.get(l);
            if (view != null) {
                ViewCompat.setHasTransientState(view, false);
                transitionValuesMaps.mItemIdValues.put(l, null);
            }
        }
    }

    private static boolean alreadyContains(int[] arrn, int n) {
        int n2 = arrn[n];
        for (int i = 0; i < n; ++i) {
            if (arrn[i] != n2) continue;
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void captureHierarchy(View view, boolean bl) {
        if (view == null) return;
        {
            int n = view.getId();
            if (this.mTargetIdExcludes != null && this.mTargetIdExcludes.contains(n) || this.mTargetExcludes != null && this.mTargetExcludes.contains((Object)view)) return;
            {
                int n2;
                if (this.mTargetTypeExcludes != null) {
                    int n3 = this.mTargetTypeExcludes.size();
                    for (n2 = 0; n2 < n3; ++n2) {
                        if (this.mTargetTypeExcludes.get(n2).isInstance((Object)view)) return;
                        {
                            continue;
                        }
                    }
                } else {
                    if (view.getParent() instanceof ViewGroup) {
                        TransitionValues transitionValues = new TransitionValues();
                        transitionValues.view = view;
                        if (bl) {
                            this.captureStartValues(transitionValues);
                        } else {
                            this.captureEndValues(transitionValues);
                        }
                        transitionValues.mTargetedTransitions.add(this);
                        this.capturePropagationValues(transitionValues);
                        if (bl) {
                            Transition.addViewValues(this.mStartValues, view, transitionValues);
                        } else {
                            Transition.addViewValues(this.mEndValues, view, transitionValues);
                        }
                    }
                    if (!(view instanceof ViewGroup) || this.mTargetIdChildExcludes != null && this.mTargetIdChildExcludes.contains(n) || this.mTargetChildExcludes != null && this.mTargetChildExcludes.contains((Object)view)) return;
                    {
                        if (this.mTargetTypeChildExcludes != null) {
                            n = this.mTargetTypeChildExcludes.size();
                            for (n2 = 0; n2 < n; ++n2) {
                                if (this.mTargetTypeChildExcludes.get(n2).isInstance((Object)view)) return;
                                {
                                    continue;
                                }
                            }
                        } else {
                            view = (ViewGroup)view;
                            for (n2 = 0; n2 < view.getChildCount(); ++n2) {
                                this.captureHierarchy(view.getChildAt(n2), bl);
                            }
                        }
                    }
                }
            }
        }
    }

    private static ArrayMap<Animator, AnimationInfo> getRunningAnimators() {
        ArrayMap<Animator, AnimationInfo> arrayMap;
        ArrayMap<Object, AnimationInfo> arrayMap2 = arrayMap = sRunningAnimators.get();
        if (arrayMap == null) {
            arrayMap2 = new ArrayMap();
            sRunningAnimators.set(arrayMap2);
        }
        return arrayMap2;
    }

    private static boolean isValidMatch(int n) {
        return n >= 1 && n <= 4;
    }

    private static boolean isValueChanged(TransitionValues object, TransitionValues object2, String string2) {
        object = ((TransitionValues)object).values.get(string2);
        object2 = ((TransitionValues)object2).values.get(string2);
        if (object == null && object2 == null) {
            return false;
        }
        if (object == null || object2 == null) {
            return true;
        }
        return !object.equals(object2);
    }

    private void matchIds(ArrayMap<View, TransitionValues> arrayMap, ArrayMap<View, TransitionValues> arrayMap2, SparseArray<View> sparseArray, SparseArray<View> sparseArray2) {
        int n = sparseArray.size();
        for (int i = 0; i < n; ++i) {
            View view;
            View view2 = (View)sparseArray.valueAt(i);
            if (view2 == null || !this.isValidTarget(view2) || (view = (View)sparseArray2.get(sparseArray.keyAt(i))) == null || !this.isValidTarget(view)) continue;
            TransitionValues transitionValues = (TransitionValues)arrayMap.get((Object)view2);
            TransitionValues transitionValues2 = (TransitionValues)arrayMap2.get((Object)view);
            if (transitionValues == null || transitionValues2 == null) continue;
            this.mStartValuesList.add(transitionValues);
            this.mEndValuesList.add(transitionValues2);
            arrayMap.remove((Object)view2);
            arrayMap2.remove((Object)view);
        }
    }

    private void matchInstances(ArrayMap<View, TransitionValues> arrayMap, ArrayMap<View, TransitionValues> arrayMap2) {
        for (int i = arrayMap.size() - 1; i >= 0; --i) {
            Object object = (View)arrayMap.keyAt(i);
            if (object == null || !this.isValidTarget((View)object) || (object = (TransitionValues)arrayMap2.remove(object)) == null || object.view == null || !this.isValidTarget(object.view)) continue;
            TransitionValues transitionValues = (TransitionValues)arrayMap.removeAt(i);
            this.mStartValuesList.add(transitionValues);
            this.mEndValuesList.add((TransitionValues)object);
        }
    }

    private void matchItemIds(ArrayMap<View, TransitionValues> arrayMap, ArrayMap<View, TransitionValues> arrayMap2, LongSparseArray<View> longSparseArray, LongSparseArray<View> longSparseArray2) {
        int n = longSparseArray.size();
        for (int i = 0; i < n; ++i) {
            View view;
            View view2 = longSparseArray.valueAt(i);
            if (view2 == null || !this.isValidTarget(view2) || (view = longSparseArray2.get(longSparseArray.keyAt(i))) == null || !this.isValidTarget(view)) continue;
            TransitionValues transitionValues = (TransitionValues)arrayMap.get((Object)view2);
            TransitionValues transitionValues2 = (TransitionValues)arrayMap2.get((Object)view);
            if (transitionValues == null || transitionValues2 == null) continue;
            this.mStartValuesList.add(transitionValues);
            this.mEndValuesList.add(transitionValues2);
            arrayMap.remove((Object)view2);
            arrayMap2.remove((Object)view);
        }
    }

    private void matchNames(ArrayMap<View, TransitionValues> arrayMap, ArrayMap<View, TransitionValues> arrayMap2, ArrayMap<String, View> arrayMap3, ArrayMap<String, View> arrayMap4) {
        int n = arrayMap3.size();
        for (int i = 0; i < n; ++i) {
            View view;
            View view2 = (View)arrayMap3.valueAt(i);
            if (view2 == null || !this.isValidTarget(view2) || (view = (View)arrayMap4.get(arrayMap3.keyAt(i))) == null || !this.isValidTarget(view)) continue;
            TransitionValues transitionValues = (TransitionValues)arrayMap.get((Object)view2);
            TransitionValues transitionValues2 = (TransitionValues)arrayMap2.get((Object)view);
            if (transitionValues == null || transitionValues2 == null) continue;
            this.mStartValuesList.add(transitionValues);
            this.mEndValuesList.add(transitionValues2);
            arrayMap.remove((Object)view2);
            arrayMap2.remove((Object)view);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void matchStartAndEnd(TransitionValuesMaps transitionValuesMaps, TransitionValuesMaps transitionValuesMaps2) {
        ArrayMap<View, TransitionValues> arrayMap = new ArrayMap<View, TransitionValues>(transitionValuesMaps.mViewValues);
        ArrayMap<View, TransitionValues> arrayMap2 = new ArrayMap<View, TransitionValues>(transitionValuesMaps2.mViewValues);
        int n = 0;
        do {
            if (n >= this.mMatchOrder.length) {
                this.addUnmatched(arrayMap, arrayMap2);
                return;
            }
            switch (this.mMatchOrder[n]) {
                case 1: {
                    this.matchInstances(arrayMap, arrayMap2);
                    break;
                }
                case 2: {
                    this.matchNames(arrayMap, arrayMap2, transitionValuesMaps.mNameValues, transitionValuesMaps2.mNameValues);
                    break;
                }
                case 3: {
                    this.matchIds(arrayMap, arrayMap2, transitionValuesMaps.mIdValues, transitionValuesMaps2.mIdValues);
                    break;
                }
                case 4: {
                    this.matchItemIds(arrayMap, arrayMap2, transitionValuesMaps.mItemIdValues, transitionValuesMaps2.mItemIdValues);
                    break;
                }
            }
            ++n;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static int[] parseMatchOrder(String arrn) {
        StringTokenizer stringTokenizer = new StringTokenizer((String)arrn, ",");
        arrn = new int[stringTokenizer.countTokens()];
        int n = 0;
        while (stringTokenizer.hasMoreTokens()) {
            int[] arrn2 = stringTokenizer.nextToken().trim();
            if ("id".equalsIgnoreCase((String)arrn2)) {
                arrn[n] = 3;
            } else if ("instance".equalsIgnoreCase((String)arrn2)) {
                arrn[n] = 1;
            } else if ("name".equalsIgnoreCase((String)arrn2)) {
                arrn[n] = 2;
            } else if ("itemId".equalsIgnoreCase((String)arrn2)) {
                arrn[n] = 4;
            } else {
                if (!arrn2.isEmpty()) {
                    throw new InflateException("Unknown match type in matchOrder: '" + (String)arrn2 + "'");
                }
                arrn2 = new int[arrn.length - 1];
                System.arraycopy(arrn, 0, arrn2, 0, n);
                arrn = arrn2;
                --n;
            }
            ++n;
        }
        return arrn;
    }

    private void runAnimator(Animator animator2, final ArrayMap<Animator, AnimationInfo> arrayMap) {
        if (animator2 != null) {
            animator2.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(){

                public void onAnimationEnd(Animator animator2) {
                    arrayMap.remove((Object)animator2);
                    Transition.this.mCurrentAnimators.remove((Object)animator2);
                }

                public void onAnimationStart(Animator animator2) {
                    Transition.this.mCurrentAnimators.add(animator2);
                }
            });
            this.animate(animator2);
        }
    }

    public Transition addListener(TransitionListener transitionListener) {
        if (this.mListeners == null) {
            this.mListeners = new ArrayList();
        }
        this.mListeners.add(transitionListener);
        return this;
    }

    protected void animate(Animator animator2) {
        if (animator2 == null) {
            this.end();
            return;
        }
        if (this.getDuration() >= 0L) {
            animator2.setDuration(this.getDuration());
        }
        if (this.getStartDelay() >= 0L) {
            animator2.setStartDelay(this.getStartDelay());
        }
        if (this.getInterpolator() != null) {
            animator2.setInterpolator(this.getInterpolator());
        }
        animator2.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(){

            public void onAnimationEnd(Animator animator2) {
                Transition.this.end();
                animator2.removeListener((Animator.AnimatorListener)this);
            }
        });
        animator2.start();
    }

    public abstract void captureEndValues(TransitionValues var1);

    /*
     * Enabled aggressive block sorting
     */
    void capturePropagationValues(TransitionValues transitionValues) {
        block2: {
            block3: {
                String[] arrstring;
                if (this.mPropagation == null || transitionValues.values.isEmpty() || (arrstring = this.mPropagation.getPropagationProperties()) == null) break block3;
                boolean bl = true;
                int n = 0;
                do {
                    block5: {
                        boolean bl2;
                        block4: {
                            bl2 = bl;
                            if (n >= arrstring.length) break block4;
                            if (transitionValues.values.containsKey(arrstring[n])) break block5;
                            bl2 = false;
                        }
                        if (bl2) break;
                        break block2;
                    }
                    ++n;
                } while (true);
            }
            return;
        }
        this.mPropagation.captureValues(transitionValues);
    }

    public abstract void captureStartValues(TransitionValues var1);

    /*
     * Enabled aggressive block sorting
     */
    void captureValues(ViewGroup object, boolean bl) {
        int n;
        Object object2;
        this.clearValues(bl);
        if (this.mTargetIds.size() <= 0 && this.mTargets.size() <= 0 || this.mTargetNames != null && !this.mTargetNames.isEmpty() || this.mTargetTypes != null && !this.mTargetTypes.isEmpty()) {
            this.captureHierarchy((View)object, bl);
        } else {
            for (n = 0; n < this.mTargetIds.size(); ++n) {
                View view = object.findViewById(this.mTargetIds.get(n).intValue());
                if (view == null) continue;
                object2 = new TransitionValues();
                ((TransitionValues)object2).view = view;
                if (bl) {
                    this.captureStartValues((TransitionValues)object2);
                } else {
                    this.captureEndValues((TransitionValues)object2);
                }
                ((TransitionValues)object2).mTargetedTransitions.add(this);
                this.capturePropagationValues((TransitionValues)object2);
                if (bl) {
                    Transition.addViewValues(this.mStartValues, view, (TransitionValues)object2);
                    continue;
                }
                Transition.addViewValues(this.mEndValues, view, (TransitionValues)object2);
            }
            for (n = 0; n < this.mTargets.size(); ++n) {
                object = this.mTargets.get(n);
                TransitionValues transitionValues = new TransitionValues();
                transitionValues.view = object;
                if (bl) {
                    this.captureStartValues(transitionValues);
                } else {
                    this.captureEndValues(transitionValues);
                }
                transitionValues.mTargetedTransitions.add(this);
                this.capturePropagationValues(transitionValues);
                if (bl) {
                    Transition.addViewValues(this.mStartValues, (View)object, transitionValues);
                    continue;
                }
                Transition.addViewValues(this.mEndValues, (View)object, transitionValues);
            }
        }
        if (!bl && this.mNameOverrides != null) {
            int n2 = this.mNameOverrides.size();
            object = new ArrayList<E>(n2);
            for (n = 0; n < n2; ++n) {
                String string2 = (String)this.mNameOverrides.keyAt(n);
                ((ArrayList)object).add(this.mStartValues.mNameValues.remove(string2));
            }
            for (n = 0; n < n2; ++n) {
                View view = (View)((ArrayList)object).get(n);
                if (view == null) continue;
                object2 = (String)this.mNameOverrides.valueAt(n);
                this.mStartValues.mNameValues.put((String)object2, view);
            }
        }
    }

    void clearValues(boolean bl) {
        if (bl) {
            this.mStartValues.mViewValues.clear();
            this.mStartValues.mIdValues.clear();
            this.mStartValues.mItemIdValues.clear();
            return;
        }
        this.mEndValues.mViewValues.clear();
        this.mEndValues.mIdValues.clear();
        this.mEndValues.mItemIdValues.clear();
    }

    public Transition clone() {
        try {
            Transition transition = (Transition)super.clone();
            transition.mAnimators = new ArrayList<E>();
            transition.mStartValues = new TransitionValuesMaps();
            transition.mEndValues = new TransitionValuesMaps();
            transition.mStartValuesList = null;
            transition.mEndValuesList = null;
            return transition;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            return null;
        }
    }

    public Animator createAnimator(ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
        return null;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected void createAnimators(ViewGroup var1_1, TransitionValuesMaps var2_2, TransitionValuesMaps var3_3, ArrayList<TransitionValues> var4_4, ArrayList<TransitionValues> var5_5) {
        var22_6 = Transition.getRunningAnimators();
        var10_7 = Long.MAX_VALUE;
        var21_8 = new SparseIntArray();
        var8_9 = var4_4.size();
        var6_10 = 0;
        block0: do {
            block18: {
                block16: {
                    block17: {
                        if (var6_10 >= var8_9) break block16;
                        var14_14 = var4_4.get(var6_10);
                        var15_15 = var5_5.get(var6_10);
                        var2_2 = var14_14;
                        if (var14_14 != null) {
                            var2_2 = var14_14;
                            if (!var14_14.mTargetedTransitions.contains(this)) {
                                var2_2 = null;
                            }
                        }
                        var14_14 = var15_15;
                        if (var15_15 != null) {
                            var14_14 = var15_15;
                            if (!var15_15.mTargetedTransitions.contains(this)) {
                                var14_14 = null;
                            }
                        }
                        if (var2_2 != null || var14_14 != null) break block17;
                        var12_13 = var10_7;
                        break block18;
                    }
                    var7_11 = var2_2 == null || var14_14 == null || this.isTransitionRequired((TransitionValues)var2_2, var14_14) != false ? 1 : 0;
                    var12_13 = var10_7;
                    if (var7_11 == 0) break block18;
                    var18_18 = this.createAnimator(var1_1, (TransitionValues)var2_2, var14_14);
                    var12_13 = var10_7;
                    if (var18_18 == null) break block18;
                    var20_20 = null;
                    if (var14_14 != null) {
                        var19_19 = var14_14.view;
                        var23_21 = this.getTransitionProperties();
                        var15_15 = var19_19;
                        var16_16 = var20_20;
                        var17_17 = var18_18;
                        if (var19_19 != null) {
                            var15_15 = var19_19;
                            var16_16 = var20_20;
                            var17_17 = var18_18;
                            if (var23_21 != null) {
                                var15_15 = var19_19;
                                var16_16 = var20_20;
                                var17_17 = var18_18;
                                if (var23_21.length > 0) {
                                    var20_20 = new TransitionValues();
                                    var20_20.view = var19_19;
                                    var15_15 = (TransitionValues)var3_3.mViewValues.get((Object)var19_19);
                                    if (var15_15 != null) {
                                        for (var7_11 = 0; var7_11 < var23_21.length; ++var7_11) {
                                            var20_20.values.put(var23_21[var7_11], var15_15.values.get(var23_21[var7_11]));
                                        }
                                    }
                                    break;
                                }
                            }
                        }
                    } else {
                        var15_15 = var2_2.view;
                        var16_16 = var20_20;
                        var17_17 = var18_18;
                    }
                    ** GOTO lbl89
                }
                if (var10_7 == 0L) return;
                var6_10 = 0;
                while (var6_10 < var21_8.size()) {
                    var7_11 = var21_8.keyAt(var6_10);
                    var1_1 = this.mAnimators.get(var7_11);
                    var1_1.setStartDelay((long)var21_8.valueAt(var6_10) - var10_7 + var1_1.getStartDelay());
                    ++var6_10;
                }
                return;
            }
lbl73:
            // 3 sources
            do {
                ++var6_10;
                var10_7 = var12_13;
                continue block0;
                break;
            } while (true);
            break;
        } while (true);
        var9_12 = var22_6.size();
        var7_11 = 0;
        do {
            block20: {
                block19: {
                    var15_15 = var19_19;
                    var16_16 = var20_20;
                    var17_17 = var18_18;
                    if (var7_11 >= var9_12) break block19;
                    var15_15 = (AnimationInfo)var22_6.get((Object)((Animator)var22_6.keyAt(var7_11)));
                    if (var15_15.mValues == null || var15_15.mView != var19_19 || !var15_15.mName.equals(this.getName()) || !var15_15.mValues.equals(var20_20)) break block20;
                    var17_17 = null;
                    var16_16 = var20_20;
                    var15_15 = var19_19;
                }
                var12_13 = var10_7;
                if (var17_17 == null) ** GOTO lbl73
                var12_13 = var10_7;
                if (this.mPropagation != null) {
                    var12_13 = this.mPropagation.getStartDelay(var1_1, this, (TransitionValues)var2_2, var14_14);
                    var21_8.put(this.mAnimators.size(), (int)var12_13);
                    var12_13 = Math.min(var12_13, var10_7);
                }
                var22_6.put(var17_17, new AnimationInfo((View)var15_15, this.getName(), this, ViewUtils.getWindowId((View)var1_1), var16_16));
                this.mAnimators.add(var17_17);
                ** continue;
            }
            ++var7_11;
        } while (true);
    }

    protected void end() {
        --this.mNumInstances;
        if (this.mNumInstances == 0) {
            ArrayList arrayList;
            int n;
            if (this.mListeners != null && this.mListeners.size() > 0) {
                arrayList = (ArrayList)this.mListeners.clone();
                int n2 = arrayList.size();
                for (n = 0; n < n2; ++n) {
                    ((TransitionListener)arrayList.get(n)).onTransitionEnd(this);
                }
            }
            for (n = 0; n < this.mStartValues.mItemIdValues.size(); ++n) {
                arrayList = this.mStartValues.mItemIdValues.valueAt(n);
                if (arrayList == null) continue;
                ViewCompat.setHasTransientState((View)arrayList, false);
            }
            for (n = 0; n < this.mEndValues.mItemIdValues.size(); ++n) {
                arrayList = this.mEndValues.mItemIdValues.valueAt(n);
                if (arrayList == null) continue;
                ViewCompat.setHasTransientState((View)arrayList, false);
            }
            this.mEnded = true;
        }
    }

    public long getDuration() {
        return this.mDuration;
    }

    public Rect getEpicenter() {
        if (this.mEpicenterCallback == null) {
            return null;
        }
        return this.mEpicenterCallback.onGetEpicenter(this);
    }

    public TimeInterpolator getInterpolator() {
        return this.mInterpolator;
    }

    /*
     * Enabled aggressive block sorting
     */
    TransitionValues getMatchedTransitionValues(View object, boolean bl) {
        int n;
        if (this.mParent != null) {
            return this.mParent.getMatchedTransitionValues((View)object, bl);
        }
        ArrayList<TransitionValues> arrayList = bl ? this.mStartValuesList : this.mEndValuesList;
        if (arrayList == null) {
            return null;
        }
        int n2 = arrayList.size();
        int n3 = -1;
        int n4 = 0;
        do {
            block8: {
                block7: {
                    n = n3;
                    if (n4 >= n2) break block7;
                    TransitionValues transitionValues = arrayList.get(n4);
                    if (transitionValues == null) {
                        return null;
                    }
                    if (transitionValues.view != object) break block8;
                    n = n4;
                }
                object = null;
                if (n < 0) return object;
                if (!bl) break;
                object = this.mEndValuesList;
                return (TransitionValues)((ArrayList)object).get(n);
            }
            ++n4;
        } while (true);
        object = this.mStartValuesList;
        return (TransitionValues)((ArrayList)object).get(n);
    }

    public String getName() {
        return this.mName;
    }

    public PathMotion getPathMotion() {
        return this.mPathMotion;
    }

    public long getStartDelay() {
        return this.mStartDelay;
    }

    public String[] getTransitionProperties() {
        return null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public TransitionValues getTransitionValues(View view, boolean bl) {
        TransitionValuesMaps transitionValuesMaps;
        if (this.mParent != null) {
            return this.mParent.getTransitionValues(view, bl);
        }
        if (bl) {
            transitionValuesMaps = this.mStartValues;
            do {
                return (TransitionValues)transitionValuesMaps.mViewValues.get((Object)view);
                break;
            } while (true);
        }
        transitionValuesMaps = this.mEndValues;
        return (TransitionValues)transitionValuesMaps.mViewValues.get((Object)view);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean isTransitionRequired(TransitionValues transitionValues, TransitionValues transitionValues2) {
        boolean bl;
        boolean bl2 = bl = false;
        if (transitionValues == null) return bl2;
        bl2 = bl;
        if (transitionValues2 == null) return bl2;
        Object object = this.getTransitionProperties();
        if (object != null) {
            int n = ((String[])object).length;
            int n2 = 0;
            do {
                bl2 = bl;
                if (n2 >= n) return bl2;
                if (Transition.isValueChanged(transitionValues, transitionValues2, (String)object[n2])) {
                    return true;
                }
                ++n2;
            } while (true);
        }
        object = transitionValues.values.keySet().iterator();
        do {
            bl2 = bl;
            if (!object.hasNext()) return bl2;
        } while (!Transition.isValueChanged(transitionValues, transitionValues2, (String)object.next()));
        return true;
    }

    boolean isValidTarget(View view) {
        int n;
        int n2 = view.getId();
        if (this.mTargetIdExcludes != null && this.mTargetIdExcludes.contains(n2)) {
            return false;
        }
        if (this.mTargetExcludes != null && this.mTargetExcludes.contains((Object)view)) {
            return false;
        }
        if (this.mTargetTypeExcludes != null) {
            int n3 = this.mTargetTypeExcludes.size();
            for (n = 0; n < n3; ++n) {
                if (!this.mTargetTypeExcludes.get(n).isInstance((Object)view)) continue;
                return false;
            }
        }
        if (this.mTargetNameExcludes != null && ViewCompat.getTransitionName(view) != null && this.mTargetNameExcludes.contains(ViewCompat.getTransitionName(view))) {
            return false;
        }
        if (this.mTargetIds.size() == 0 && this.mTargets.size() == 0 && (this.mTargetTypes == null || this.mTargetTypes.isEmpty()) && (this.mTargetNames == null || this.mTargetNames.isEmpty())) {
            return true;
        }
        if (this.mTargetIds.contains(n2) || this.mTargets.contains((Object)view)) {
            return true;
        }
        if (this.mTargetNames != null && this.mTargetNames.contains(ViewCompat.getTransitionName(view))) {
            return true;
        }
        if (this.mTargetTypes != null) {
            for (n = 0; n < this.mTargetTypes.size(); ++n) {
                if (!this.mTargetTypes.get(n).isInstance((Object)view)) continue;
                return true;
            }
        }
        return false;
    }

    public void pause(View object) {
        if (!this.mEnded) {
            ArrayMap<Animator, AnimationInfo> arrayMap = Transition.getRunningAnimators();
            int n = arrayMap.size();
            object = ViewUtils.getWindowId((View)object);
            --n;
            while (n >= 0) {
                AnimationInfo animationInfo = (AnimationInfo)arrayMap.valueAt(n);
                if (animationInfo.mView != null && object.equals(animationInfo.mWindowId)) {
                    AnimatorUtils.pause((Animator)arrayMap.keyAt(n));
                }
                --n;
            }
            if (this.mListeners != null && this.mListeners.size() > 0) {
                object = (ArrayList)this.mListeners.clone();
                int n2 = ((ArrayList)object).size();
                for (n = 0; n < n2; ++n) {
                    ((TransitionListener)((ArrayList)object).get(n)).onTransitionPause(this);
                }
            }
            this.mPaused = true;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    void playTransition(ViewGroup viewGroup) {
        this.mStartValuesList = new ArrayList<E>();
        this.mEndValuesList = new ArrayList<E>();
        this.matchStartAndEnd(this.mStartValues, this.mEndValues);
        ArrayMap<Animator, AnimationInfo> arrayMap = Transition.getRunningAnimators();
        int n = arrayMap.size();
        WindowIdImpl windowIdImpl = ViewUtils.getWindowId((View)viewGroup);
        --n;
        do {
            AnimationInfo animationInfo;
            if (n < 0) {
                this.createAnimators(viewGroup, this.mStartValues, this.mEndValues, this.mStartValuesList, this.mEndValuesList);
                this.runAnimators();
                return;
            }
            Animator animator2 = (Animator)arrayMap.keyAt(n);
            if (animator2 != null && (animationInfo = (AnimationInfo)arrayMap.get((Object)animator2)) != null && animationInfo.mView != null && windowIdImpl.equals(animationInfo.mWindowId)) {
                TransitionValues transitionValues = animationInfo.mValues;
                View view = animationInfo.mView;
                TransitionValues transitionValues2 = this.getTransitionValues(view, true);
                TransitionValues transitionValues3 = this.getMatchedTransitionValues(view, true);
                boolean bl = (transitionValues2 != null || transitionValues3 != null) && animationInfo.mTransition.isTransitionRequired(transitionValues, transitionValues3);
                if (bl) {
                    if (animator2.isRunning() || animator2.isStarted()) {
                        animator2.cancel();
                    } else {
                        arrayMap.remove((Object)animator2);
                    }
                }
            }
            --n;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    public Transition removeListener(TransitionListener transitionListener) {
        block3: {
            block2: {
                if (this.mListeners == null) break block2;
                this.mListeners.remove(transitionListener);
                if (this.mListeners.size() == 0) break block3;
            }
            return this;
        }
        this.mListeners = null;
        return this;
    }

    public void resume(View object) {
        if (this.mPaused) {
            if (!this.mEnded) {
                ArrayMap<Animator, AnimationInfo> arrayMap = Transition.getRunningAnimators();
                int n = arrayMap.size();
                object = ViewUtils.getWindowId((View)object);
                --n;
                while (n >= 0) {
                    AnimationInfo animationInfo = (AnimationInfo)arrayMap.valueAt(n);
                    if (animationInfo.mView != null && object.equals(animationInfo.mWindowId)) {
                        AnimatorUtils.resume((Animator)arrayMap.keyAt(n));
                    }
                    --n;
                }
                if (this.mListeners != null && this.mListeners.size() > 0) {
                    object = (ArrayList)this.mListeners.clone();
                    int n2 = ((ArrayList)object).size();
                    for (n = 0; n < n2; ++n) {
                        ((TransitionListener)((ArrayList)object).get(n)).onTransitionResume(this);
                    }
                }
            }
            this.mPaused = false;
        }
    }

    protected void runAnimators() {
        this.start();
        ArrayMap<Animator, AnimationInfo> arrayMap = Transition.getRunningAnimators();
        for (Animator animator2 : this.mAnimators) {
            if (!arrayMap.containsKey((Object)animator2)) continue;
            this.start();
            this.runAnimator(animator2, arrayMap);
        }
        this.mAnimators.clear();
        this.end();
    }

    public Transition setDuration(long l) {
        this.mDuration = l;
        return this;
    }

    public Transition setInterpolator(TimeInterpolator timeInterpolator) {
        this.mInterpolator = timeInterpolator;
        return this;
    }

    public void setMatchOrder(int ... arrn) {
        if (arrn == null || arrn.length == 0) {
            this.mMatchOrder = DEFAULT_MATCH_ORDER;
            return;
        }
        for (int i = 0; i < arrn.length; ++i) {
            if (!Transition.isValidMatch(arrn[i])) {
                throw new IllegalArgumentException("matches contains invalid value");
            }
            if (!Transition.alreadyContains(arrn, i)) continue;
            throw new IllegalArgumentException("matches contains a duplicate value");
        }
        this.mMatchOrder = (int[])arrn.clone();
    }

    public void setPropagation(TransitionPropagation transitionPropagation) {
        this.mPropagation = transitionPropagation;
    }

    public Transition setStartDelay(long l) {
        this.mStartDelay = l;
        return this;
    }

    protected void start() {
        if (this.mNumInstances == 0) {
            if (this.mListeners != null && this.mListeners.size() > 0) {
                ArrayList arrayList = (ArrayList)this.mListeners.clone();
                int n = arrayList.size();
                for (int i = 0; i < n; ++i) {
                    ((TransitionListener)arrayList.get(i)).onTransitionStart(this);
                }
            }
            this.mEnded = false;
        }
        ++this.mNumInstances;
    }

    public String toString() {
        return this.toString("");
    }

    String toString(String string2) {
        String string3;
        block13: {
            int n;
            block12: {
                string2 = string3 = string2 + this.getClass().getSimpleName() + "@" + Integer.toHexString(this.hashCode()) + ": ";
                if (this.mDuration != -1L) {
                    string2 = string3 + "dur(" + this.mDuration + ") ";
                }
                string3 = string2;
                if (this.mStartDelay != -1L) {
                    string3 = string2 + "dly(" + this.mStartDelay + ") ";
                }
                string2 = string3;
                if (this.mInterpolator != null) {
                    string2 = string3 + "interp(" + (Object)this.mInterpolator + ") ";
                }
                if (this.mTargetIds.size() > 0) break block12;
                string3 = string2;
                if (this.mTargets.size() <= 0) break block13;
            }
            string2 = string3 = string2 + "tgts(";
            if (this.mTargetIds.size() > 0) {
                n = 0;
                do {
                    string2 = string3;
                    if (n >= this.mTargetIds.size()) break;
                    string2 = string3;
                    if (n > 0) {
                        string2 = string3 + ", ";
                    }
                    string3 = string2 + this.mTargetIds.get(n);
                    ++n;
                } while (true);
            }
            string3 = string2;
            if (this.mTargets.size() > 0) {
                n = 0;
                do {
                    string3 = string2;
                    if (n >= this.mTargets.size()) break;
                    string3 = string2;
                    if (n > 0) {
                        string3 = string2 + ", ";
                    }
                    string2 = string3 + (Object)this.mTargets.get(n);
                    ++n;
                } while (true);
            }
            string3 = string3 + ")";
        }
        return string3;
    }

    private static class AnimationInfo {
        String mName;
        Transition mTransition;
        TransitionValues mValues;
        View mView;
        WindowIdImpl mWindowId;

        AnimationInfo(View view, String string2, Transition transition, WindowIdImpl windowIdImpl, TransitionValues transitionValues) {
            this.mView = view;
            this.mName = string2;
            this.mValues = transitionValues;
            this.mWindowId = windowIdImpl;
            this.mTransition = transition;
        }
    }

    public static abstract class EpicenterCallback {
        public abstract Rect onGetEpicenter(Transition var1);
    }

    public static interface TransitionListener {
        public void onTransitionEnd(Transition var1);

        public void onTransitionPause(Transition var1);

        public void onTransitionResume(Transition var1);

        public void onTransitionStart(Transition var1);
    }

}

