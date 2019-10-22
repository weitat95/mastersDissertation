/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorInflater
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.AnimatorSet
 *  android.animation.PropertyValuesHolder
 *  android.animation.ValueAnimator
 *  android.content.Context
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.content.res.Resources$NotFoundException
 *  android.content.res.TypedArray
 *  android.graphics.Paint
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Parcelable
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.SparseArray
 *  android.view.LayoutInflater
 *  android.view.LayoutInflater$Factory2
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.animation.AccelerateInterpolator
 *  android.view.animation.AlphaAnimation
 *  android.view.animation.Animation
 *  android.view.animation.Animation$AnimationListener
 *  android.view.animation.AnimationSet
 *  android.view.animation.AnimationUtils
 *  android.view.animation.DecelerateInterpolator
 *  android.view.animation.Interpolator
 *  android.view.animation.ScaleAnimation
 */
package android.support.v4.app;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.support.v4.app.BackStackRecord;
import android.support.v4.app.BackStackState;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentContainer;
import android.support.v4.app.FragmentHostCallback;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManagerNonConfig;
import android.support.v4.app.FragmentManagerState;
import android.support.v4.app.FragmentState;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentTransition;
import android.support.v4.app.LoaderManagerImpl;
import android.support.v4.app.SuperNotCalledException;
import android.support.v4.util.ArraySet;
import android.support.v4.util.DebugUtils;
import android.support.v4.util.LogWriter;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

final class FragmentManagerImpl
extends FragmentManager
implements LayoutInflater.Factory2 {
    static final Interpolator ACCELERATE_CUBIC;
    static final Interpolator ACCELERATE_QUINT;
    static final int ANIM_DUR = 220;
    public static final int ANIM_STYLE_CLOSE_ENTER = 3;
    public static final int ANIM_STYLE_CLOSE_EXIT = 4;
    public static final int ANIM_STYLE_FADE_ENTER = 5;
    public static final int ANIM_STYLE_FADE_EXIT = 6;
    public static final int ANIM_STYLE_OPEN_ENTER = 1;
    public static final int ANIM_STYLE_OPEN_EXIT = 2;
    static boolean DEBUG = false;
    static final Interpolator DECELERATE_CUBIC;
    static final Interpolator DECELERATE_QUINT;
    static final String TAG = "FragmentManager";
    static final String TARGET_REQUEST_CODE_STATE_TAG = "android:target_req_state";
    static final String TARGET_STATE_TAG = "android:target_state";
    static final String USER_VISIBLE_HINT_TAG = "android:user_visible_hint";
    static final String VIEW_STATE_TAG = "android:view_state";
    static Field sAnimationListenerField;
    SparseArray<Fragment> mActive;
    final ArrayList<Fragment> mAdded = new ArrayList();
    ArrayList<Integer> mAvailBackStackIndices;
    ArrayList<BackStackRecord> mBackStack;
    ArrayList<FragmentManager.OnBackStackChangedListener> mBackStackChangeListeners;
    ArrayList<BackStackRecord> mBackStackIndices;
    FragmentContainer mContainer;
    ArrayList<Fragment> mCreatedMenus;
    int mCurState = 0;
    boolean mDestroyed;
    Runnable mExecCommit;
    boolean mExecutingActions;
    boolean mHavePendingDeferredStart;
    FragmentHostCallback mHost;
    private final CopyOnWriteArrayList<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> mLifecycleCallbacks = new CopyOnWriteArrayList();
    boolean mNeedMenuInvalidate;
    int mNextFragmentIndex = 0;
    String mNoTransactionsBecause;
    Fragment mParent;
    ArrayList<OpGenerator> mPendingActions;
    ArrayList<StartEnterTransitionListener> mPostponedTransactions;
    Fragment mPrimaryNav;
    FragmentManagerNonConfig mSavedNonConfig;
    SparseArray<Parcelable> mStateArray = null;
    Bundle mStateBundle = null;
    boolean mStateSaved;
    ArrayList<Fragment> mTmpAddedFragments;
    ArrayList<Boolean> mTmpIsPop;
    ArrayList<BackStackRecord> mTmpRecords;

    static {
        DEBUG = false;
        sAnimationListenerField = null;
        DECELERATE_QUINT = new DecelerateInterpolator(2.5f);
        DECELERATE_CUBIC = new DecelerateInterpolator(1.5f);
        ACCELERATE_QUINT = new AccelerateInterpolator(2.5f);
        ACCELERATE_CUBIC = new AccelerateInterpolator(1.5f);
    }

    FragmentManagerImpl() {
        this.mExecCommit = new Runnable(){

            @Override
            public void run() {
                FragmentManagerImpl.this.execPendingActions();
            }
        };
    }

    /*
     * Enabled aggressive block sorting
     */
    private void addAddedFragments(ArraySet<Fragment> arraySet) {
        if (this.mCurState >= 1) {
            int n = Math.min(this.mCurState, 4);
            int n2 = this.mAdded.size();
            for (int i = 0; i < n2; ++i) {
                Fragment fragment = this.mAdded.get(i);
                if (fragment.mState >= n) continue;
                this.moveToState(fragment, n, fragment.getNextAnim(), fragment.getNextTransition(), false);
                if (fragment.mView == null || fragment.mHidden || !fragment.mIsNewlyAdded) continue;
                arraySet.add(fragment);
            }
        }
    }

    private void animateRemoveFragment(final Fragment fragment, AnimationOrAnimator animationOrAnimator, int n) {
        final View view = fragment.mView;
        fragment.setStateAfterAnimating(n);
        if (animationOrAnimator.animation != null) {
            Animation animation = animationOrAnimator.animation;
            fragment.setAnimatingAway(fragment.mView);
            animation.setAnimationListener((Animation.AnimationListener)new AnimationListenerWrapper(FragmentManagerImpl.getAnimationListener(animation)){

                @Override
                public void onAnimationEnd(Animation animation) {
                    super.onAnimationEnd(animation);
                    if (fragment.getAnimatingAway() != null) {
                        fragment.setAnimatingAway(null);
                        FragmentManagerImpl.this.moveToState(fragment, fragment.getStateAfterAnimating(), 0, 0, false);
                    }
                }
            });
            FragmentManagerImpl.setHWLayerAnimListenerIfAlpha(view, animationOrAnimator);
            fragment.mView.startAnimation(animation);
            return;
        }
        Animator animator2 = animationOrAnimator.animator;
        fragment.setAnimator(animationOrAnimator.animator);
        final ViewGroup viewGroup = fragment.mContainer;
        if (viewGroup != null) {
            viewGroup.startViewTransition(view);
        }
        animator2.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(){

            public void onAnimationEnd(Animator animator2) {
                if (viewGroup != null) {
                    viewGroup.endViewTransition(view);
                }
                if (fragment.getAnimator() != null) {
                    fragment.setAnimator(null);
                    FragmentManagerImpl.this.moveToState(fragment, fragment.getStateAfterAnimating(), 0, 0, false);
                }
            }
        });
        animator2.setTarget((Object)fragment.mView);
        FragmentManagerImpl.setHWLayerAnimListenerIfAlpha(fragment.mView, animationOrAnimator);
        animator2.start();
    }

    private void burpActive() {
        if (this.mActive != null) {
            for (int i = this.mActive.size() - 1; i >= 0; --i) {
                if (this.mActive.valueAt(i) != null) continue;
                this.mActive.delete(this.mActive.keyAt(i));
            }
        }
    }

    private void checkStateLoss() {
        if (this.mStateSaved) {
            throw new IllegalStateException("Can not perform this action after onSaveInstanceState");
        }
        if (this.mNoTransactionsBecause != null) {
            throw new IllegalStateException("Can not perform this action inside of " + this.mNoTransactionsBecause);
        }
    }

    private void cleanupExec() {
        this.mExecutingActions = false;
        this.mTmpIsPop.clear();
        this.mTmpRecords.clear();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void completeExecute(BackStackRecord backStackRecord, boolean bl, boolean bl2, boolean bl3) {
        if (bl) {
            backStackRecord.executePopOps(bl3);
        } else {
            backStackRecord.executeOps();
        }
        Object object = new ArrayList<BackStackRecord>(1);
        ArrayList<Boolean> arrayList = new ArrayList<Boolean>(1);
        ((ArrayList)object).add(backStackRecord);
        arrayList.add(bl);
        if (bl2) {
            FragmentTransition.startTransitions(this, object, arrayList, 0, 1, true);
        }
        if (bl3) {
            this.moveToState(this.mCurState, true);
        }
        if (this.mActive != null) {
            int n = this.mActive.size();
            for (int i = 0; i < n; ++i) {
                object = (Fragment)this.mActive.valueAt(i);
                if (object == null || ((Fragment)object).mView == null || !((Fragment)object).mIsNewlyAdded || !backStackRecord.interactsWith(((Fragment)object).mContainerId)) continue;
                if (((Fragment)object).mPostponedAlpha > 0.0f) {
                    ((Fragment)object).mView.setAlpha(((Fragment)object).mPostponedAlpha);
                }
                if (bl3) {
                    ((Fragment)object).mPostponedAlpha = 0.0f;
                    continue;
                }
                ((Fragment)object).mPostponedAlpha = -1.0f;
                ((Fragment)object).mIsNewlyAdded = false;
            }
        }
    }

    private void dispatchStateChange(int n) {
        this.mExecutingActions = true;
        this.moveToState(n, false);
        this.execPendingActions();
        return;
        finally {
            this.mExecutingActions = false;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void endAnimatingAwayFragments() {
        int n = this.mActive == null ? 0 : this.mActive.size();
        int n2 = 0;
        while (n2 < n) {
            Fragment fragment = (Fragment)this.mActive.valueAt(n2);
            if (fragment != null) {
                if (fragment.getAnimatingAway() != null) {
                    int n3 = fragment.getStateAfterAnimating();
                    View view = fragment.getAnimatingAway();
                    fragment.setAnimatingAway(null);
                    Animation animation = view.getAnimation();
                    if (animation != null) {
                        animation.cancel();
                        view.clearAnimation();
                    }
                    this.moveToState(fragment, n3, 0, 0, false);
                } else if (fragment.getAnimator() != null) {
                    fragment.getAnimator().end();
                }
            }
            ++n2;
        }
        return;
    }

    private void ensureExecReady(boolean bl) {
        if (this.mExecutingActions) {
            throw new IllegalStateException("FragmentManager is already executing transactions");
        }
        if (Looper.myLooper() != this.mHost.getHandler().getLooper()) {
            throw new IllegalStateException("Must be called from main thread of fragment host");
        }
        if (!bl) {
            this.checkStateLoss();
        }
        if (this.mTmpRecords == null) {
            this.mTmpRecords = new ArrayList();
            this.mTmpIsPop = new ArrayList();
        }
        this.mExecutingActions = true;
        try {
            this.executePostponedTransaction(null, null);
            return;
        }
        finally {
            this.mExecutingActions = false;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private static void executeOps(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2, int n, int n2) {
        while (n < n2) {
            BackStackRecord backStackRecord = arrayList.get(n);
            if (arrayList2.get(n).booleanValue()) {
                backStackRecord.bumpBackStackNesting(-1);
                boolean bl = n == n2 - 1;
                backStackRecord.executePopOps(bl);
            } else {
                backStackRecord.bumpBackStackNesting(1);
                backStackRecord.executeOps();
            }
            ++n;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void executeOpsTogether(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2, int n, int n2) {
        int n3;
        boolean bl = arrayList.get((int)n).mReorderingAllowed;
        boolean bl2 = false;
        if (this.mTmpAddedFragments == null) {
            this.mTmpAddedFragments = new ArrayList();
        } else {
            this.mTmpAddedFragments.clear();
        }
        this.mTmpAddedFragments.addAll(this.mAdded);
        Object object = this.getPrimaryNavigationFragment();
        for (n3 = n; n3 < n2; ++n3) {
            BackStackRecord backStackRecord = arrayList.get(n3);
            object = arrayList2.get(n3) == false ? backStackRecord.expandOps(this.mTmpAddedFragments, (Fragment)object) : backStackRecord.trackAddedFragmentsInPop(this.mTmpAddedFragments, (Fragment)object);
            bl2 = bl2 || backStackRecord.mAddToBackStack;
        }
        this.mTmpAddedFragments.clear();
        if (!bl) {
            FragmentTransition.startTransitions(this, arrayList, arrayList2, n, n2, false);
        }
        FragmentManagerImpl.executeOps(arrayList, arrayList2, n, n2);
        n3 = n2;
        if (bl) {
            object = new ArraySet();
            this.addAddedFragments((ArraySet<Fragment>)object);
            n3 = this.postponePostponableTransactions(arrayList, arrayList2, n, n2, (ArraySet<Fragment>)object);
            this.makeRemovedFragmentsInvisible((ArraySet<Fragment>)object);
        }
        if (n3 != n && bl) {
            FragmentTransition.startTransitions(this, arrayList, arrayList2, n, n3, true);
            this.moveToState(this.mCurState, true);
        }
        while (n < n2) {
            object = arrayList.get(n);
            if (arrayList2.get(n).booleanValue() && ((BackStackRecord)object).mIndex >= 0) {
                this.freeBackStackIndex(((BackStackRecord)object).mIndex);
                ((BackStackRecord)object).mIndex = -1;
            }
            ((BackStackRecord)object).runOnCommitRunnables();
            ++n;
        }
        if (bl2) {
            this.reportBackStackChanged();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void executePostponedTransaction(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2) {
        int n = this.mPostponedTransactions == null ? 0 : this.mPostponedTransactions.size();
        int n2 = 0;
        int n3 = n;
        n = n2;
        do {
            int n4;
            block8: {
                StartEnterTransitionListener startEnterTransitionListener;
                block9: {
                    block7: {
                        if (n >= n3) {
                            return;
                        }
                        startEnterTransitionListener = this.mPostponedTransactions.get(n);
                        if (arrayList == null || startEnterTransitionListener.mIsBack || (n2 = arrayList.indexOf(startEnterTransitionListener.mRecord)) == -1 || !arrayList2.get(n2).booleanValue()) break block7;
                        startEnterTransitionListener.cancelTransaction();
                        n4 = n3;
                        n2 = n;
                        break block8;
                    }
                    if (startEnterTransitionListener.isReady()) break block9;
                    n2 = n;
                    n4 = n3;
                    if (arrayList == null) break block8;
                    n2 = n;
                    n4 = n3;
                    if (!startEnterTransitionListener.mRecord.interactsWith(arrayList, 0, arrayList.size())) break block8;
                }
                this.mPostponedTransactions.remove(n);
                n2 = n - 1;
                n4 = n3 - 1;
                if (arrayList != null && !startEnterTransitionListener.mIsBack && (n = arrayList.indexOf(startEnterTransitionListener.mRecord)) != -1 && arrayList2.get(n).booleanValue()) {
                    startEnterTransitionListener.cancelTransaction();
                } else {
                    startEnterTransitionListener.completeTransaction();
                }
            }
            n = n2 + 1;
            n3 = n4;
        } while (true);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private Fragment findFragmentUnder(Fragment fragment) {
        ViewGroup viewGroup = fragment.mContainer;
        View view = fragment.mView;
        if (viewGroup == null) return null;
        if (view == null) {
            return null;
        }
        int n = this.mAdded.indexOf(fragment) - 1;
        while (n >= 0) {
            Fragment fragment2 = this.mAdded.get(n);
            if (fragment2.mContainer == viewGroup) {
                fragment = fragment2;
                if (fragment2.mView != null) return fragment;
            }
            --n;
        }
        return null;
    }

    private void forcePostponedTransactions() {
        if (this.mPostponedTransactions != null) {
            while (!this.mPostponedTransactions.isEmpty()) {
                this.mPostponedTransactions.remove(0).completeTransaction();
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean generateOpsForPendingActions(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2) {
        boolean bl = false;
        synchronized (this) {
            if (this.mPendingActions == null || this.mPendingActions.size() == 0) {
                return false;
            }
            int n = this.mPendingActions.size();
            int n2 = 0;
            do {
                if (n2 >= n) {
                    this.mPendingActions.clear();
                    this.mHost.getHandler().removeCallbacks(this.mExecCommit);
                    return bl;
                }
                bl |= this.mPendingActions.get(n2).generateOps(arrayList, arrayList2);
                ++n2;
            } while (true);
        }
    }

    private static Animation.AnimationListener getAnimationListener(Animation animation) {
        try {
            if (sAnimationListenerField == null) {
                sAnimationListenerField = Animation.class.getDeclaredField("mListener");
                sAnimationListenerField.setAccessible(true);
            }
            animation = (Animation.AnimationListener)sAnimationListenerField.get((Object)animation);
            return animation;
        }
        catch (NoSuchFieldException noSuchFieldException) {
            Log.e((String)TAG, (String)"No field with the name mListener is found in Animation class", (Throwable)noSuchFieldException);
            return null;
        }
        catch (IllegalAccessException illegalAccessException) {
            Log.e((String)TAG, (String)"Cannot access Animation's mListener field", (Throwable)illegalAccessException);
            return null;
        }
    }

    static AnimationOrAnimator makeFadeAnimation(Context context, float f, float f2) {
        context = new AlphaAnimation(f, f2);
        context.setInterpolator(DECELERATE_CUBIC);
        context.setDuration(220L);
        return new AnimationOrAnimator((Animation)context);
    }

    static AnimationOrAnimator makeOpenCloseAnimation(Context context, float f, float f2, float f3, float f4) {
        context = new AnimationSet(false);
        ScaleAnimation scaleAnimation = new ScaleAnimation(f, f2, f, f2, 1, 0.5f, 1, 0.5f);
        scaleAnimation.setInterpolator(DECELERATE_QUINT);
        scaleAnimation.setDuration(220L);
        context.addAnimation((Animation)scaleAnimation);
        scaleAnimation = new AlphaAnimation(f3, f4);
        scaleAnimation.setInterpolator(DECELERATE_CUBIC);
        scaleAnimation.setDuration(220L);
        context.addAnimation((Animation)scaleAnimation);
        return new AnimationOrAnimator((Animation)context);
    }

    private void makeRemovedFragmentsInvisible(ArraySet<Fragment> arraySet) {
        int n = arraySet.size();
        for (int i = 0; i < n; ++i) {
            Fragment fragment = arraySet.valueAt(i);
            if (fragment.mAdded) continue;
            View view = fragment.getView();
            fragment.mPostponedAlpha = view.getAlpha();
            view.setAlpha(0.0f);
        }
    }

    static boolean modifiesAlpha(Animator object) {
        block4: {
            block3: {
                if (object == null) {
                    return false;
                }
                if (!(object instanceof ValueAnimator)) break block3;
                object = ((ValueAnimator)object).getValues();
                for (int i = 0; i < ((PropertyValuesHolder[])object).length; ++i) {
                    if (!"alpha".equals(object[i].getPropertyName())) continue;
                    return true;
                }
                break block4;
            }
            if (!(object instanceof AnimatorSet)) break block4;
            object = ((AnimatorSet)object).getChildAnimations();
            for (int i = 0; i < object.size(); ++i) {
                if (!FragmentManagerImpl.modifiesAlpha((Animator)object.get(i))) continue;
                return true;
            }
        }
        return false;
    }

    static boolean modifiesAlpha(AnimationOrAnimator object) {
        if (((AnimationOrAnimator)object).animation instanceof AlphaAnimation) {
            return true;
        }
        if (((AnimationOrAnimator)object).animation instanceof AnimationSet) {
            object = ((AnimationSet)((AnimationOrAnimator)object).animation).getAnimations();
            for (int i = 0; i < object.size(); ++i) {
                if (!(object.get(i) instanceof AlphaAnimation)) continue;
                return true;
            }
            return false;
        }
        return FragmentManagerImpl.modifiesAlpha(((AnimationOrAnimator)object).animator);
    }

    private boolean popBackStackImmediate(String string2, int n, int n2) {
        FragmentManager fragmentManager;
        this.execPendingActions();
        this.ensureExecReady(true);
        if (this.mPrimaryNav != null && n < 0 && string2 == null && (fragmentManager = this.mPrimaryNav.peekChildFragmentManager()) != null && fragmentManager.popBackStackImmediate()) {
            return true;
        }
        boolean bl = this.popBackStackState(this.mTmpRecords, this.mTmpIsPop, string2, n, n2);
        if (bl) {
            this.mExecutingActions = true;
            this.removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
        }
        this.doPendingDeferredStart();
        this.burpActive();
        return bl;
        finally {
            this.cleanupExec();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private int postponePostponableTransactions(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2, int n, int n2, ArraySet<Fragment> arraySet) {
        int n3 = n2;
        int n4 = n2 - 1;
        while (n4 >= n) {
            BackStackRecord backStackRecord = arrayList.get(n4);
            boolean bl = arrayList2.get(n4);
            boolean bl2 = backStackRecord.isPostponed() && !backStackRecord.interactsWith(arrayList, n4 + 1, n2);
            int n5 = n3;
            if (bl2) {
                if (this.mPostponedTransactions == null) {
                    this.mPostponedTransactions = new ArrayList();
                }
                StartEnterTransitionListener startEnterTransitionListener = new StartEnterTransitionListener(backStackRecord, bl);
                this.mPostponedTransactions.add(startEnterTransitionListener);
                backStackRecord.setOnStartPostponedListener(startEnterTransitionListener);
                if (bl) {
                    backStackRecord.executeOps();
                } else {
                    backStackRecord.executePopOps(false);
                }
                if (n4 != (n5 = n3 - 1)) {
                    arrayList.remove(n4);
                    arrayList.add(n5, backStackRecord);
                }
                this.addAddedFragments(arraySet);
            }
            --n4;
            n3 = n5;
        }
        return n3;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void removeRedundantOperationsAndExecute(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2) {
        int n;
        int n2;
        block10: {
            block9: {
                if (arrayList == null || arrayList.isEmpty()) break block9;
                if (arrayList2 == null || arrayList.size() != arrayList2.size()) {
                    throw new IllegalStateException("Internal error with the back stack records");
                }
                this.executePostponedTransaction(arrayList, arrayList2);
                n = arrayList.size();
                n2 = 0;
                int n3 = 0;
                while (n3 < n) {
                    int n4 = n3;
                    int n5 = n2;
                    if (!arrayList.get((int)n3).mReorderingAllowed) {
                        if (n2 != n3) {
                            this.executeOpsTogether(arrayList, arrayList2, n2, n3);
                        }
                        n5 = n2 = n3 + 1;
                        if (arrayList2.get(n3).booleanValue()) {
                            do {
                                n5 = n2;
                                if (n2 >= n) break;
                                n5 = n2;
                                if (!arrayList2.get(n2).booleanValue()) break;
                                n5 = n2++;
                            } while (!arrayList.get((int)n2).mReorderingAllowed);
                        }
                        this.executeOpsTogether(arrayList, arrayList2, n3, n5);
                        n3 = n5;
                        n4 = n5 - 1;
                        n5 = n3;
                    }
                    n3 = n4 + 1;
                    n2 = n5;
                }
                if (n2 != n) break block10;
            }
            return;
        }
        this.executeOpsTogether(arrayList, arrayList2, n2, n);
    }

    public static int reverseTransit(int n) {
        switch (n) {
            default: {
                return 0;
            }
            case 4097: {
                return 8194;
            }
            case 8194: {
                return 4097;
            }
            case 4099: 
        }
        return 4099;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void scheduleCommit() {
        boolean bl = true;
        synchronized (this) {
            boolean bl2 = this.mPostponedTransactions != null && !this.mPostponedTransactions.isEmpty();
            if (this.mPendingActions == null || this.mPendingActions.size() != 1) {
                bl = false;
            }
            if (bl2 || bl) {
                this.mHost.getHandler().removeCallbacks(this.mExecCommit);
                this.mHost.getHandler().post(this.mExecCommit);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private static void setHWLayerAnimListenerIfAlpha(View view, AnimationOrAnimator animationOrAnimator) {
        if (view == null || animationOrAnimator == null || !FragmentManagerImpl.shouldRunOnHWLayer(view, animationOrAnimator)) {
            return;
        }
        if (animationOrAnimator.animator != null) {
            animationOrAnimator.animator.addListener((Animator.AnimatorListener)new AnimatorOnHWLayerIfNeededListener(view));
            return;
        }
        Animation.AnimationListener animationListener = FragmentManagerImpl.getAnimationListener(animationOrAnimator.animation);
        view.setLayerType(2, null);
        animationOrAnimator.animation.setAnimationListener((Animation.AnimationListener)new AnimateOnHWLayerIfNeededListener(view, animationListener));
    }

    /*
     * Enabled aggressive block sorting
     */
    private static void setRetaining(FragmentManagerNonConfig iterator) {
        if (iterator != null) {
            List<Fragment> list = ((FragmentManagerNonConfig)((Object)iterator)).getFragments();
            if (list != null) {
                list = list.iterator();
                while (list.hasNext()) {
                    ((Fragment)list.next()).mRetaining = true;
                }
            }
            if ((iterator = ((FragmentManagerNonConfig)((Object)iterator)).getChildNonConfigs()) != null) {
                iterator = iterator.iterator();
                while (iterator.hasNext()) {
                    FragmentManagerImpl.setRetaining((FragmentManagerNonConfig)iterator.next());
                }
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    static boolean shouldRunOnHWLayer(View view, AnimationOrAnimator animationOrAnimator) {
        return view != null && animationOrAnimator != null && Build.VERSION.SDK_INT >= 19 && view.getLayerType() == 0 && ViewCompat.hasOverlappingRendering(view) && FragmentManagerImpl.modifiesAlpha(animationOrAnimator);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void throwException(RuntimeException runtimeException) {
        Log.e((String)TAG, (String)runtimeException.getMessage());
        Log.e((String)TAG, (String)"Activity state:");
        PrintWriter printWriter = new PrintWriter(new LogWriter(TAG));
        if (this.mHost != null) {
            try {
                this.mHost.onDump("  ", null, printWriter, new String[0]);
            }
            catch (Exception exception) {
                Log.e((String)TAG, (String)"Failed dumping state", (Throwable)exception);
                throw runtimeException;
            }
            do {
                throw runtimeException;
                break;
            } while (true);
        }
        try {
            this.dump("  ", null, printWriter, new String[0]);
            throw runtimeException;
        }
        catch (Exception exception) {
            Log.e((String)TAG, (String)"Failed dumping state", (Throwable)exception);
            throw runtimeException;
        }
    }

    public static int transitToStyleIndex(int n, boolean bl) {
        switch (n) {
            default: {
                return -1;
            }
            case 4097: {
                if (bl) {
                    return 1;
                }
                return 2;
            }
            case 8194: {
                if (bl) {
                    return 3;
                }
                return 4;
            }
            case 4099: 
        }
        if (bl) {
            return 5;
        }
        return 6;
    }

    void addBackStackState(BackStackRecord backStackRecord) {
        if (this.mBackStack == null) {
            this.mBackStack = new ArrayList();
        }
        this.mBackStack.add(backStackRecord);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void addFragment(Fragment fragment, boolean bl) {
        if (DEBUG) {
            Log.v((String)TAG, (String)("add: " + fragment));
        }
        this.makeActive(fragment);
        if (!fragment.mDetached) {
            if (this.mAdded.contains(fragment)) {
                throw new IllegalStateException("Fragment already added: " + fragment);
            }
            ArrayList<Fragment> arrayList = this.mAdded;
            synchronized (arrayList) {
                this.mAdded.add(fragment);
            }
            fragment.mAdded = true;
            fragment.mRemoving = false;
            if (fragment.mView == null) {
                fragment.mHiddenChanged = false;
            }
            if (fragment.mHasMenu && fragment.mMenuVisible) {
                this.mNeedMenuInvalidate = true;
            }
            if (bl) {
                this.moveToState(fragment);
            }
        }
    }

    @Override
    public void addOnBackStackChangedListener(FragmentManager.OnBackStackChangedListener onBackStackChangedListener) {
        if (this.mBackStackChangeListeners == null) {
            this.mBackStackChangeListeners = new ArrayList();
        }
        this.mBackStackChangeListeners.add(onBackStackChangedListener);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int allocBackStackIndex(BackStackRecord backStackRecord) {
        synchronized (this) {
            if (this.mAvailBackStackIndices == null || this.mAvailBackStackIndices.size() <= 0) {
                if (this.mBackStackIndices == null) {
                    this.mBackStackIndices = new ArrayList();
                }
                int n = this.mBackStackIndices.size();
                if (DEBUG) {
                    Log.v((String)TAG, (String)("Setting back stack index " + n + " to " + backStackRecord));
                }
                this.mBackStackIndices.add(backStackRecord);
                return n;
            }
            int n = this.mAvailBackStackIndices.remove(this.mAvailBackStackIndices.size() - 1);
            if (DEBUG) {
                Log.v((String)TAG, (String)("Adding back stack index " + n + " with " + backStackRecord));
            }
            this.mBackStackIndices.set(n, backStackRecord);
            return n;
        }
    }

    public void attachController(FragmentHostCallback fragmentHostCallback, FragmentContainer fragmentContainer, Fragment fragment) {
        if (this.mHost != null) {
            throw new IllegalStateException("Already attached");
        }
        this.mHost = fragmentHostCallback;
        this.mContainer = fragmentContainer;
        this.mParent = fragment;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void attachFragment(Fragment fragment) {
        if (DEBUG) {
            Log.v((String)TAG, (String)("attach: " + fragment));
        }
        if (fragment.mDetached) {
            fragment.mDetached = false;
            if (!fragment.mAdded) {
                if (this.mAdded.contains(fragment)) {
                    throw new IllegalStateException("Fragment already added: " + fragment);
                }
                if (DEBUG) {
                    Log.v((String)TAG, (String)("add from attach: " + fragment));
                }
                ArrayList<Fragment> arrayList = this.mAdded;
                synchronized (arrayList) {
                    this.mAdded.add(fragment);
                }
                fragment.mAdded = true;
                if (fragment.mHasMenu && fragment.mMenuVisible) {
                    this.mNeedMenuInvalidate = true;
                }
            }
        }
    }

    @Override
    public FragmentTransaction beginTransaction() {
        return new BackStackRecord(this);
    }

    /*
     * Enabled aggressive block sorting
     */
    void completeShowHideFragment(final Fragment fragment) {
        if (fragment.mView != null) {
            boolean bl;
            int n = fragment.getNextTransition();
            AnimationOrAnimator animationOrAnimator = this.loadAnimation(fragment, n, bl = !fragment.mHidden, fragment.getNextTransitionStyle());
            if (animationOrAnimator != null && animationOrAnimator.animator != null) {
                animationOrAnimator.animator.setTarget((Object)fragment.mView);
                if (fragment.mHidden) {
                    if (fragment.isHideReplaced()) {
                        fragment.setHideReplaced(false);
                    } else {
                        final ViewGroup viewGroup = fragment.mContainer;
                        final View view = fragment.mView;
                        viewGroup.startViewTransition(view);
                        animationOrAnimator.animator.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(){

                            public void onAnimationEnd(Animator animator2) {
                                viewGroup.endViewTransition(view);
                                animator2.removeListener((Animator.AnimatorListener)this);
                                if (fragment.mView != null) {
                                    fragment.mView.setVisibility(8);
                                }
                            }
                        });
                    }
                } else {
                    fragment.mView.setVisibility(0);
                }
                FragmentManagerImpl.setHWLayerAnimListenerIfAlpha(fragment.mView, animationOrAnimator);
                animationOrAnimator.animator.start();
            } else {
                if (animationOrAnimator != null) {
                    FragmentManagerImpl.setHWLayerAnimListenerIfAlpha(fragment.mView, animationOrAnimator);
                    fragment.mView.startAnimation(animationOrAnimator.animation);
                    animationOrAnimator.animation.start();
                }
                n = fragment.mHidden && !fragment.isHideReplaced() ? 8 : 0;
                fragment.mView.setVisibility(n);
                if (fragment.isHideReplaced()) {
                    fragment.setHideReplaced(false);
                }
            }
        }
        if (fragment.mAdded && fragment.mHasMenu && fragment.mMenuVisible) {
            this.mNeedMenuInvalidate = true;
        }
        fragment.mHiddenChanged = false;
        fragment.onHiddenChanged(fragment.mHidden);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void detachFragment(Fragment fragment) {
        if (DEBUG) {
            Log.v((String)TAG, (String)("detach: " + fragment));
        }
        if (fragment.mDetached) return;
        fragment.mDetached = true;
        if (!fragment.mAdded) return;
        if (DEBUG) {
            Log.v((String)TAG, (String)("remove from detach: " + fragment));
        }
        ArrayList<Fragment> arrayList = this.mAdded;
        // MONITORENTER : arrayList
        this.mAdded.remove(fragment);
        // MONITOREXIT : arrayList
        if (fragment.mHasMenu && fragment.mMenuVisible) {
            this.mNeedMenuInvalidate = true;
        }
        fragment.mAdded = false;
    }

    public void dispatchActivityCreated() {
        this.mStateSaved = false;
        this.dispatchStateChange(2);
    }

    public void dispatchConfigurationChanged(Configuration configuration) {
        for (int i = 0; i < this.mAdded.size(); ++i) {
            Fragment fragment = this.mAdded.get(i);
            if (fragment == null) continue;
            fragment.performConfigurationChanged(configuration);
        }
    }

    public boolean dispatchContextItemSelected(MenuItem menuItem) {
        for (int i = 0; i < this.mAdded.size(); ++i) {
            Fragment fragment = this.mAdded.get(i);
            if (fragment == null || !fragment.performContextItemSelected(menuItem)) continue;
            return true;
        }
        return false;
    }

    public void dispatchCreate() {
        this.mStateSaved = false;
        this.dispatchStateChange(1);
    }

    public boolean dispatchCreateOptionsMenu(Menu object, MenuInflater menuInflater) {
        int n;
        boolean bl = false;
        ArrayList<Fragment> arrayList = null;
        for (n = 0; n < this.mAdded.size(); ++n) {
            Fragment fragment = this.mAdded.get(n);
            ArrayList<Fragment> arrayList2 = arrayList;
            boolean bl2 = bl;
            if (fragment != null) {
                arrayList2 = arrayList;
                bl2 = bl;
                if (fragment.performCreateOptionsMenu((Menu)object, menuInflater)) {
                    bl2 = true;
                    arrayList2 = arrayList;
                    if (arrayList == null) {
                        arrayList2 = new ArrayList<Fragment>();
                    }
                    arrayList2.add(fragment);
                }
            }
            arrayList = arrayList2;
            bl = bl2;
        }
        if (this.mCreatedMenus != null) {
            for (n = 0; n < this.mCreatedMenus.size(); ++n) {
                object = this.mCreatedMenus.get(n);
                if (arrayList != null && arrayList.contains(object)) continue;
                ((Fragment)object).onDestroyOptionsMenu();
            }
        }
        this.mCreatedMenus = arrayList;
        return bl;
    }

    public void dispatchDestroy() {
        this.mDestroyed = true;
        this.execPendingActions();
        this.dispatchStateChange(0);
        this.mHost = null;
        this.mContainer = null;
        this.mParent = null;
    }

    public void dispatchDestroyView() {
        this.dispatchStateChange(1);
    }

    public void dispatchLowMemory() {
        for (int i = 0; i < this.mAdded.size(); ++i) {
            Fragment fragment = this.mAdded.get(i);
            if (fragment == null) continue;
            fragment.performLowMemory();
        }
    }

    public void dispatchMultiWindowModeChanged(boolean bl) {
        for (int i = this.mAdded.size() - 1; i >= 0; --i) {
            Fragment fragment = this.mAdded.get(i);
            if (fragment == null) continue;
            fragment.performMultiWindowModeChanged(bl);
        }
    }

    void dispatchOnFragmentActivityCreated(Fragment fragment, Bundle bundle, boolean bl) {
        Object object;
        if (this.mParent != null && (object = this.mParent.getFragmentManager()) instanceof FragmentManagerImpl) {
            ((FragmentManagerImpl)object).dispatchOnFragmentActivityCreated(fragment, bundle, true);
        }
        for (Pair pair : this.mLifecycleCallbacks) {
            if (bl && !((Boolean)pair.second).booleanValue()) continue;
            ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentActivityCreated(this, fragment, bundle);
        }
    }

    void dispatchOnFragmentAttached(Fragment fragment, Context context, boolean bl) {
        Object object;
        if (this.mParent != null && (object = this.mParent.getFragmentManager()) instanceof FragmentManagerImpl) {
            ((FragmentManagerImpl)object).dispatchOnFragmentAttached(fragment, context, true);
        }
        for (Pair pair : this.mLifecycleCallbacks) {
            if (bl && !((Boolean)pair.second).booleanValue()) continue;
            ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentAttached(this, fragment, context);
        }
    }

    void dispatchOnFragmentCreated(Fragment fragment, Bundle bundle, boolean bl) {
        Object object;
        if (this.mParent != null && (object = this.mParent.getFragmentManager()) instanceof FragmentManagerImpl) {
            ((FragmentManagerImpl)object).dispatchOnFragmentCreated(fragment, bundle, true);
        }
        for (Pair pair : this.mLifecycleCallbacks) {
            if (bl && !((Boolean)pair.second).booleanValue()) continue;
            ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentCreated(this, fragment, bundle);
        }
    }

    void dispatchOnFragmentDestroyed(Fragment fragment, boolean bl) {
        Object object;
        if (this.mParent != null && (object = this.mParent.getFragmentManager()) instanceof FragmentManagerImpl) {
            ((FragmentManagerImpl)object).dispatchOnFragmentDestroyed(fragment, true);
        }
        for (Pair pair : this.mLifecycleCallbacks) {
            if (bl && !((Boolean)pair.second).booleanValue()) continue;
            ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentDestroyed(this, fragment);
        }
    }

    void dispatchOnFragmentDetached(Fragment fragment, boolean bl) {
        Object object;
        if (this.mParent != null && (object = this.mParent.getFragmentManager()) instanceof FragmentManagerImpl) {
            ((FragmentManagerImpl)object).dispatchOnFragmentDetached(fragment, true);
        }
        for (Pair pair : this.mLifecycleCallbacks) {
            if (bl && !((Boolean)pair.second).booleanValue()) continue;
            ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentDetached(this, fragment);
        }
    }

    void dispatchOnFragmentPaused(Fragment fragment, boolean bl) {
        Object object;
        if (this.mParent != null && (object = this.mParent.getFragmentManager()) instanceof FragmentManagerImpl) {
            ((FragmentManagerImpl)object).dispatchOnFragmentPaused(fragment, true);
        }
        for (Pair pair : this.mLifecycleCallbacks) {
            if (bl && !((Boolean)pair.second).booleanValue()) continue;
            ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentPaused(this, fragment);
        }
    }

    void dispatchOnFragmentPreAttached(Fragment fragment, Context context, boolean bl) {
        Object object;
        if (this.mParent != null && (object = this.mParent.getFragmentManager()) instanceof FragmentManagerImpl) {
            ((FragmentManagerImpl)object).dispatchOnFragmentPreAttached(fragment, context, true);
        }
        for (Pair pair : this.mLifecycleCallbacks) {
            if (bl && !((Boolean)pair.second).booleanValue()) continue;
            ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentPreAttached(this, fragment, context);
        }
    }

    void dispatchOnFragmentPreCreated(Fragment fragment, Bundle bundle, boolean bl) {
        Object object;
        if (this.mParent != null && (object = this.mParent.getFragmentManager()) instanceof FragmentManagerImpl) {
            ((FragmentManagerImpl)object).dispatchOnFragmentPreCreated(fragment, bundle, true);
        }
        for (Pair pair : this.mLifecycleCallbacks) {
            if (bl && !((Boolean)pair.second).booleanValue()) continue;
            ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentPreCreated(this, fragment, bundle);
        }
    }

    void dispatchOnFragmentResumed(Fragment fragment, boolean bl) {
        Object object;
        if (this.mParent != null && (object = this.mParent.getFragmentManager()) instanceof FragmentManagerImpl) {
            ((FragmentManagerImpl)object).dispatchOnFragmentResumed(fragment, true);
        }
        for (Pair pair : this.mLifecycleCallbacks) {
            if (bl && !((Boolean)pair.second).booleanValue()) continue;
            ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentResumed(this, fragment);
        }
    }

    void dispatchOnFragmentSaveInstanceState(Fragment fragment, Bundle bundle, boolean bl) {
        Object object;
        if (this.mParent != null && (object = this.mParent.getFragmentManager()) instanceof FragmentManagerImpl) {
            ((FragmentManagerImpl)object).dispatchOnFragmentSaveInstanceState(fragment, bundle, true);
        }
        for (Pair pair : this.mLifecycleCallbacks) {
            if (bl && !((Boolean)pair.second).booleanValue()) continue;
            ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentSaveInstanceState(this, fragment, bundle);
        }
    }

    void dispatchOnFragmentStarted(Fragment fragment, boolean bl) {
        Object object;
        if (this.mParent != null && (object = this.mParent.getFragmentManager()) instanceof FragmentManagerImpl) {
            ((FragmentManagerImpl)object).dispatchOnFragmentStarted(fragment, true);
        }
        for (Pair pair : this.mLifecycleCallbacks) {
            if (bl && !((Boolean)pair.second).booleanValue()) continue;
            ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentStarted(this, fragment);
        }
    }

    void dispatchOnFragmentStopped(Fragment fragment, boolean bl) {
        Object object;
        if (this.mParent != null && (object = this.mParent.getFragmentManager()) instanceof FragmentManagerImpl) {
            ((FragmentManagerImpl)object).dispatchOnFragmentStopped(fragment, true);
        }
        for (Pair pair : this.mLifecycleCallbacks) {
            if (bl && !((Boolean)pair.second).booleanValue()) continue;
            ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentStopped(this, fragment);
        }
    }

    void dispatchOnFragmentViewCreated(Fragment fragment, View view, Bundle bundle, boolean bl) {
        Object object;
        if (this.mParent != null && (object = this.mParent.getFragmentManager()) instanceof FragmentManagerImpl) {
            ((FragmentManagerImpl)object).dispatchOnFragmentViewCreated(fragment, view, bundle, true);
        }
        for (Pair pair : this.mLifecycleCallbacks) {
            if (bl && !((Boolean)pair.second).booleanValue()) continue;
            ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentViewCreated(this, fragment, view, bundle);
        }
    }

    void dispatchOnFragmentViewDestroyed(Fragment fragment, boolean bl) {
        Object object;
        if (this.mParent != null && (object = this.mParent.getFragmentManager()) instanceof FragmentManagerImpl) {
            ((FragmentManagerImpl)object).dispatchOnFragmentViewDestroyed(fragment, true);
        }
        for (Pair pair : this.mLifecycleCallbacks) {
            if (bl && !((Boolean)pair.second).booleanValue()) continue;
            ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentViewDestroyed(this, fragment);
        }
    }

    public boolean dispatchOptionsItemSelected(MenuItem menuItem) {
        for (int i = 0; i < this.mAdded.size(); ++i) {
            Fragment fragment = this.mAdded.get(i);
            if (fragment == null || !fragment.performOptionsItemSelected(menuItem)) continue;
            return true;
        }
        return false;
    }

    public void dispatchOptionsMenuClosed(Menu menu2) {
        for (int i = 0; i < this.mAdded.size(); ++i) {
            Fragment fragment = this.mAdded.get(i);
            if (fragment == null) continue;
            fragment.performOptionsMenuClosed(menu2);
        }
    }

    public void dispatchPause() {
        this.dispatchStateChange(4);
    }

    public void dispatchPictureInPictureModeChanged(boolean bl) {
        for (int i = this.mAdded.size() - 1; i >= 0; --i) {
            Fragment fragment = this.mAdded.get(i);
            if (fragment == null) continue;
            fragment.performPictureInPictureModeChanged(bl);
        }
    }

    public boolean dispatchPrepareOptionsMenu(Menu menu2) {
        boolean bl = false;
        for (int i = 0; i < this.mAdded.size(); ++i) {
            Fragment fragment = this.mAdded.get(i);
            boolean bl2 = bl;
            if (fragment != null) {
                bl2 = bl;
                if (fragment.performPrepareOptionsMenu(menu2)) {
                    bl2 = true;
                }
            }
            bl = bl2;
        }
        return bl;
    }

    public void dispatchReallyStop() {
        this.dispatchStateChange(2);
    }

    public void dispatchResume() {
        this.mStateSaved = false;
        this.dispatchStateChange(5);
    }

    public void dispatchStart() {
        this.mStateSaved = false;
        this.dispatchStateChange(4);
    }

    public void dispatchStop() {
        this.mStateSaved = true;
        this.dispatchStateChange(3);
    }

    void doPendingDeferredStart() {
        if (this.mHavePendingDeferredStart) {
            boolean bl = false;
            for (int i = 0; i < this.mActive.size(); ++i) {
                Fragment fragment = (Fragment)this.mActive.valueAt(i);
                boolean bl2 = bl;
                if (fragment != null) {
                    bl2 = bl;
                    if (fragment.mLoaderManager != null) {
                        bl2 = bl | fragment.mLoaderManager.hasRunningLoaders();
                    }
                }
                bl = bl2;
            }
            if (!bl) {
                this.mHavePendingDeferredStart = false;
                this.startPendingDeferredFragments();
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public void dump(String string2, FileDescriptor object, PrintWriter printWriter, String[] arrstring) {
        int n;
        int n2;
        Object object2;
        String string3 = string2 + "    ";
        if (this.mActive != null && (n2 = this.mActive.size()) > 0) {
            printWriter.print(string2);
            printWriter.print("Active Fragments in ");
            printWriter.print(Integer.toHexString(System.identityHashCode(this)));
            printWriter.println(":");
            for (n = 0; n < n2; ++n) {
                object2 = (Fragment)this.mActive.valueAt(n);
                printWriter.print(string2);
                printWriter.print("  #");
                printWriter.print(n);
                printWriter.print(": ");
                printWriter.println(object2);
                if (object2 == null) continue;
                ((Fragment)object2).dump(string3, (FileDescriptor)object, printWriter, arrstring);
            }
        }
        if ((n2 = this.mAdded.size()) > 0) {
            printWriter.print(string2);
            printWriter.println("Added Fragments:");
            for (n = 0; n < n2; ++n) {
                object2 = this.mAdded.get(n);
                printWriter.print(string2);
                printWriter.print("  #");
                printWriter.print(n);
                printWriter.print(": ");
                printWriter.println(((Fragment)object2).toString());
            }
        }
        if (this.mCreatedMenus != null && (n2 = this.mCreatedMenus.size()) > 0) {
            printWriter.print(string2);
            printWriter.println("Fragments Created Menus:");
            for (n = 0; n < n2; ++n) {
                object2 = this.mCreatedMenus.get(n);
                printWriter.print(string2);
                printWriter.print("  #");
                printWriter.print(n);
                printWriter.print(": ");
                printWriter.println(((Fragment)object2).toString());
            }
        }
        if (this.mBackStack != null && (n2 = this.mBackStack.size()) > 0) {
            printWriter.print(string2);
            printWriter.println("Back Stack:");
            for (n = 0; n < n2; ++n) {
                object2 = this.mBackStack.get(n);
                printWriter.print(string2);
                printWriter.print("  #");
                printWriter.print(n);
                printWriter.print(": ");
                printWriter.println(((BackStackRecord)object2).toString());
                ((BackStackRecord)object2).dump(string3, (FileDescriptor)object, printWriter, arrstring);
            }
        }
        // MONITORENTER : this
        if (this.mBackStackIndices != null && (n2 = this.mBackStackIndices.size()) > 0) {
            printWriter.print(string2);
            printWriter.println("Back Stack Indices:");
            for (n = 0; n < n2; ++n) {
                object = this.mBackStackIndices.get(n);
                printWriter.print(string2);
                printWriter.print("  #");
                printWriter.print(n);
                printWriter.print(": ");
                printWriter.println(object);
            }
        }
        if (this.mAvailBackStackIndices != null && this.mAvailBackStackIndices.size() > 0) {
            printWriter.print(string2);
            printWriter.print("mAvailBackStackIndices: ");
            printWriter.println(Arrays.toString(this.mAvailBackStackIndices.toArray()));
        }
        // MONITOREXIT : this
        if (this.mPendingActions != null && (n2 = this.mPendingActions.size()) > 0) {
            printWriter.print(string2);
            printWriter.println("Pending Actions:");
            for (n = 0; n < n2; ++n) {
                object = this.mPendingActions.get(n);
                printWriter.print(string2);
                printWriter.print("  #");
                printWriter.print(n);
                printWriter.print(": ");
                printWriter.println(object);
            }
        }
        printWriter.print(string2);
        printWriter.println("FragmentManager misc state:");
        printWriter.print(string2);
        printWriter.print("  mHost=");
        printWriter.println(this.mHost);
        printWriter.print(string2);
        printWriter.print("  mContainer=");
        printWriter.println(this.mContainer);
        if (this.mParent != null) {
            printWriter.print(string2);
            printWriter.print("  mParent=");
            printWriter.println(this.mParent);
        }
        printWriter.print(string2);
        printWriter.print("  mCurState=");
        printWriter.print(this.mCurState);
        printWriter.print(" mStateSaved=");
        printWriter.print(this.mStateSaved);
        printWriter.print(" mDestroyed=");
        printWriter.println(this.mDestroyed);
        if (this.mNeedMenuInvalidate) {
            printWriter.print(string2);
            printWriter.print("  mNeedMenuInvalidate=");
            printWriter.println(this.mNeedMenuInvalidate);
        }
        if (this.mNoTransactionsBecause == null) return;
        printWriter.print(string2);
        printWriter.print("  mNoTransactionsBecause=");
        printWriter.println(this.mNoTransactionsBecause);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void enqueueAction(OpGenerator opGenerator, boolean bl) {
        if (!bl) {
            this.checkStateLoss();
        }
        synchronized (this) {
            if (this.mDestroyed || this.mHost == null) {
                if (bl) {
                    return;
                }
                throw new IllegalStateException("Activity has been destroyed");
            }
            if (this.mPendingActions == null) {
                this.mPendingActions = new ArrayList();
            }
            this.mPendingActions.add(opGenerator);
            this.scheduleCommit();
            return;
        }
    }

    void ensureInflatedFragmentView(Fragment fragment) {
        block5: {
            block4: {
                if (!fragment.mFromLayout || fragment.mPerformedCreateView) break block4;
                fragment.mView = fragment.performCreateView(fragment.performGetLayoutInflater(fragment.mSavedFragmentState), null, fragment.mSavedFragmentState);
                if (fragment.mView == null) break block5;
                fragment.mInnerView = fragment.mView;
                fragment.mView.setSaveFromParentEnabled(false);
                if (fragment.mHidden) {
                    fragment.mView.setVisibility(8);
                }
                fragment.onViewCreated(fragment.mView, fragment.mSavedFragmentState);
                this.dispatchOnFragmentViewCreated(fragment, fragment.mView, fragment.mSavedFragmentState, false);
            }
            return;
        }
        fragment.mInnerView = null;
    }

    public boolean execPendingActions() {
        this.ensureExecReady(true);
        boolean bl = false;
        while (this.generateOpsForPendingActions(this.mTmpRecords, this.mTmpIsPop)) {
            this.mExecutingActions = true;
            try {
                this.removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
                bl = true;
            }
            finally {
                this.cleanupExec();
            }
        }
        this.doPendingDeferredStart();
        this.burpActive();
        return bl;
    }

    public void execSingleAction(OpGenerator opGenerator, boolean bl) {
        if (bl && (this.mHost == null || this.mDestroyed)) {
            return;
        }
        this.ensureExecReady(bl);
        if (opGenerator.generateOps(this.mTmpRecords, this.mTmpIsPop)) {
            this.mExecutingActions = true;
            this.removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
        }
        this.doPendingDeferredStart();
        this.burpActive();
        return;
        finally {
            this.cleanupExec();
        }
    }

    @Override
    public boolean executePendingTransactions() {
        boolean bl = this.execPendingActions();
        this.forcePostponedTransactions();
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public Fragment findFragmentById(int n) {
        int n2;
        Fragment fragment;
        for (n2 = this.mAdded.size() - 1; n2 >= 0; --n2) {
            fragment = this.mAdded.get(n2);
            if (fragment != null && fragment.mFragmentId == n) return fragment;
            {
                continue;
            }
        }
        if (this.mActive == null) return null;
        {
            for (n2 = this.mActive.size() - 1; n2 >= 0; --n2) {
                Fragment fragment2 = (Fragment)this.mActive.valueAt(n2);
                if (fragment2 == null) continue;
                fragment = fragment2;
                if (fragment2.mFragmentId == n) return fragment;
                {
                    continue;
                }
            }
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public Fragment findFragmentByTag(String string2) {
        int n;
        Fragment fragment;
        if (string2 != null) {
            for (n = this.mAdded.size() - 1; n >= 0; --n) {
                fragment = this.mAdded.get(n);
                if (fragment != null && string2.equals(fragment.mTag)) return fragment;
                {
                    continue;
                }
            }
        } else {
            if (this.mActive == null || string2 == null) return null;
            {
                for (n = this.mActive.size() - 1; n >= 0; --n) {
                    Fragment fragment2 = (Fragment)this.mActive.valueAt(n);
                    if (fragment2 == null) continue;
                    fragment = fragment2;
                    if (string2.equals(fragment2.mTag)) return fragment;
                    {
                        continue;
                    }
                }
            }
            return null;
        }
    }

    public Fragment findFragmentByWho(String string2) {
        if (this.mActive != null && string2 != null) {
            for (int i = this.mActive.size() - 1; i >= 0; --i) {
                Fragment fragment = (Fragment)this.mActive.valueAt(i);
                if (fragment == null || (fragment = fragment.findFragmentByWho(string2)) == null) continue;
                return fragment;
            }
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void freeBackStackIndex(int n) {
        synchronized (this) {
            this.mBackStackIndices.set(n, null);
            if (this.mAvailBackStackIndices == null) {
                this.mAvailBackStackIndices = new ArrayList();
            }
            if (DEBUG) {
                Log.v((String)TAG, (String)("Freeing back stack index " + n));
            }
            this.mAvailBackStackIndices.add(n);
            return;
        }
    }

    int getActiveFragmentCount() {
        if (this.mActive == null) {
            return 0;
        }
        return this.mActive.size();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    List<Fragment> getActiveFragments() {
        if (this.mActive == null) {
            return null;
        }
        int n = this.mActive.size();
        ArrayList<Object> arrayList = new ArrayList<Object>(n);
        int n2 = 0;
        do {
            ArrayList<Object> arrayList2 = arrayList;
            if (n2 >= n) return arrayList2;
            arrayList.add(this.mActive.valueAt(n2));
            ++n2;
        } while (true);
    }

    @Override
    public FragmentManager.BackStackEntry getBackStackEntryAt(int n) {
        return this.mBackStack.get(n);
    }

    @Override
    public int getBackStackEntryCount() {
        if (this.mBackStack != null) {
            return this.mBackStack.size();
        }
        return 0;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    @Override
    public Fragment getFragment(Bundle object, String string2) {
        void var1_3;
        void var2_5;
        Fragment fragment;
        int n = object.getInt((String)var2_5, -1);
        if (n == -1) {
            return var1_3;
        }
        Fragment fragment2 = fragment = (Fragment)this.mActive.get(n);
        if (fragment != null) {
            return var1_3;
        }
        this.throwException(new IllegalStateException("Fragment no longer exists for key " + (String)var2_5 + ": index " + n));
        return fragment;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public List<Fragment> getFragments() {
        if (this.mAdded.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        ArrayList<Fragment> arrayList = this.mAdded;
        synchronized (arrayList) {
            return (List)this.mAdded.clone();
        }
    }

    LayoutInflater.Factory2 getLayoutInflaterFactory() {
        return this;
    }

    @Override
    public Fragment getPrimaryNavigationFragment() {
        return this.mPrimaryNav;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void hideFragment(Fragment fragment) {
        boolean bl = true;
        if (DEBUG) {
            Log.v((String)TAG, (String)("hide: " + fragment));
        }
        if (!fragment.mHidden) {
            fragment.mHidden = true;
            if (fragment.mHiddenChanged) {
                bl = false;
            }
            fragment.mHiddenChanged = bl;
        }
    }

    @Override
    public boolean isDestroyed() {
        return this.mDestroyed;
    }

    boolean isStateAtLeast(int n) {
        return this.mCurState >= n;
    }

    @Override
    public boolean isStateSaved() {
        return this.mStateSaved;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    AnimationOrAnimator loadAnimation(Fragment object, int n, boolean bl, int n2) {
        block24: {
            boolean bl2;
            int n3;
            boolean bl3;
            block25: {
                boolean bl4;
                n3 = ((Fragment)object).getNextAnim();
                Animation animation = ((Fragment)object).onCreateAnimation(n, bl, n3);
                if (animation != null) {
                    return new AnimationOrAnimator(animation);
                }
                if ((object = ((Fragment)object).onCreateAnimator(n, bl, n3)) != null) {
                    return new AnimationOrAnimator((Animator)object);
                }
                if (n3 == 0) break block24;
                bl3 = "anim".equals(this.mHost.getContext().getResources().getResourceTypeName(n3));
                bl2 = bl4 = false;
                if (bl3) {
                    object = AnimationUtils.loadAnimation((Context)this.mHost.getContext(), (int)n3);
                    if (object != null) {
                        return new AnimationOrAnimator((Animation)object);
                    }
                    bl2 = true;
                    break block25;
                    catch (RuntimeException runtimeException) {
                        bl2 = bl4;
                    }
                }
            }
            if (!bl2) {
                try {
                    object = AnimatorInflater.loadAnimator((Context)this.mHost.getContext(), (int)n3);
                    if (object != null) {
                        return new AnimationOrAnimator((Animator)object);
                    }
                }
                catch (RuntimeException runtimeException) {
                    if (bl3) {
                        throw runtimeException;
                    }
                    Animation animation = AnimationUtils.loadAnimation((Context)this.mHost.getContext(), (int)n3);
                    if (animation == null) break block24;
                    return new AnimationOrAnimator(animation);
                }
            }
        }
        if (n == 0) {
            return null;
        }
        if ((n = FragmentManagerImpl.transitToStyleIndex(n, bl)) < 0) {
            return null;
        }
        switch (n) {
            default: {
                n = n2;
                if (n2 == 0) {
                    n = n2;
                    if (this.mHost.onHasWindowAnimations()) {
                        n = this.mHost.onGetWindowAnimations();
                    }
                }
                if (n != 0) return null;
                return null;
            }
            case 1: {
                return FragmentManagerImpl.makeOpenCloseAnimation(this.mHost.getContext(), 1.125f, 1.0f, 0.0f, 1.0f);
            }
            case 2: {
                return FragmentManagerImpl.makeOpenCloseAnimation(this.mHost.getContext(), 1.0f, 0.975f, 1.0f, 0.0f);
            }
            case 3: {
                return FragmentManagerImpl.makeOpenCloseAnimation(this.mHost.getContext(), 0.975f, 1.0f, 0.0f, 1.0f);
            }
            case 4: {
                return FragmentManagerImpl.makeOpenCloseAnimation(this.mHost.getContext(), 1.0f, 1.075f, 1.0f, 0.0f);
            }
            case 5: {
                return FragmentManagerImpl.makeFadeAnimation(this.mHost.getContext(), 0.0f, 1.0f);
            }
            case 6: 
        }
        return FragmentManagerImpl.makeFadeAnimation(this.mHost.getContext(), 1.0f, 0.0f);
    }

    /*
     * Enabled aggressive block sorting
     */
    void makeActive(Fragment fragment) {
        block5: {
            block4: {
                if (fragment.mIndex >= 0) break block4;
                int n = this.mNextFragmentIndex;
                this.mNextFragmentIndex = n + 1;
                fragment.setIndex(n, this.mParent);
                if (this.mActive == null) {
                    this.mActive = new SparseArray();
                }
                this.mActive.put(fragment.mIndex, (Object)fragment);
                if (DEBUG) break block5;
            }
            return;
        }
        Log.v((String)TAG, (String)("Allocated fragment index " + fragment));
    }

    void makeInactive(Fragment fragment) {
        if (fragment.mIndex < 0) {
            return;
        }
        if (DEBUG) {
            Log.v((String)TAG, (String)("Freeing fragment index " + fragment));
        }
        this.mActive.put(fragment.mIndex, null);
        this.mHost.inactivateFragment(fragment.mWho);
        fragment.initState();
    }

    /*
     * Enabled aggressive block sorting
     */
    void moveFragmentToExpectedState(Fragment fragment) {
        block13: {
            block12: {
                int n;
                if (fragment == null) break block12;
                int n2 = n = this.mCurState;
                if (fragment.mRemoving) {
                    n2 = fragment.isInBackStack() ? Math.min(n, 1) : Math.min(n, 0);
                }
                this.moveToState(fragment, n2, fragment.getNextTransition(), fragment.getNextTransitionStyle(), false);
                if (fragment.mView != null) {
                    Object object = this.findFragmentUnder(fragment);
                    if (object != null) {
                        object = ((Fragment)object).mView;
                        ViewGroup viewGroup = fragment.mContainer;
                        n2 = viewGroup.indexOfChild((View)object);
                        n = viewGroup.indexOfChild(fragment.mView);
                        if (n < n2) {
                            viewGroup.removeViewAt(n);
                            viewGroup.addView(fragment.mView, n2);
                        }
                    }
                    if (fragment.mIsNewlyAdded && fragment.mContainer != null) {
                        if (fragment.mPostponedAlpha > 0.0f) {
                            fragment.mView.setAlpha(fragment.mPostponedAlpha);
                        }
                        fragment.mPostponedAlpha = 0.0f;
                        fragment.mIsNewlyAdded = false;
                        object = this.loadAnimation(fragment, fragment.getNextTransition(), true, fragment.getNextTransitionStyle());
                        if (object != null) {
                            FragmentManagerImpl.setHWLayerAnimListenerIfAlpha(fragment.mView, (AnimationOrAnimator)object);
                            if (((AnimationOrAnimator)object).animation != null) {
                                fragment.mView.startAnimation(((AnimationOrAnimator)object).animation);
                            } else {
                                ((AnimationOrAnimator)object).animator.setTarget((Object)fragment.mView);
                                ((AnimationOrAnimator)object).animator.start();
                            }
                        }
                    }
                }
                if (fragment.mHiddenChanged) break block13;
            }
            return;
        }
        this.completeShowHideFragment(fragment);
    }

    /*
     * Enabled aggressive block sorting
     */
    void moveToState(int n, boolean bl) {
        block16: {
            block13: {
                int n2;
                int n3;
                Fragment fragment;
                if (this.mHost == null && n != 0) {
                    throw new IllegalStateException("No activity");
                }
                if (!bl && n == this.mCurState) break block13;
                this.mCurState = n;
                if (this.mActive == null) break block13;
                n = 0;
                int n4 = this.mAdded.size();
                for (n2 = 0; n2 < n4; ++n2) {
                    fragment = this.mAdded.get(n2);
                    this.moveFragmentToExpectedState(fragment);
                    n3 = n;
                    if (fragment.mLoaderManager != null) {
                        n3 = n | fragment.mLoaderManager.hasRunningLoaders();
                    }
                    n = n3;
                }
                n4 = this.mActive.size();
                n3 = 0;
                n2 = n;
                for (n = n3; n < n4; ++n) {
                    block14: {
                        block15: {
                            fragment = (Fragment)this.mActive.valueAt(n);
                            n3 = n2;
                            if (fragment == null) break block14;
                            if (fragment.mRemoving) break block15;
                            n3 = n2;
                            if (!fragment.mDetached) break block14;
                        }
                        n3 = n2;
                        if (!fragment.mIsNewlyAdded) {
                            this.moveFragmentToExpectedState(fragment);
                            n3 = n2;
                            if (fragment.mLoaderManager != null) {
                                n3 = n2 | fragment.mLoaderManager.hasRunningLoaders();
                            }
                        }
                    }
                    n2 = n3;
                }
                if (n2 == 0) {
                    this.startPendingDeferredFragments();
                }
                if (this.mNeedMenuInvalidate && this.mHost != null && this.mCurState == 5) break block16;
            }
            return;
        }
        this.mHost.onSupportInvalidateOptionsMenu();
        this.mNeedMenuInvalidate = false;
    }

    void moveToState(Fragment fragment) {
        this.moveToState(fragment, this.mCurState, 0, 0, false);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void moveToState(Fragment fragment, int n, int n2, int n3, boolean bl) {
        int n4;
        block91: {
            block89: {
                block90: {
                    block88: {
                        int n5;
                        block87: {
                            block86: {
                                if (!fragment.mAdded) break block86;
                                n5 = n;
                                if (!fragment.mDetached) break block87;
                            }
                            n5 = n;
                            if (n > 1) {
                                n5 = 1;
                            }
                        }
                        n4 = n5;
                        if (fragment.mRemoving) {
                            n4 = n5;
                            if (n5 > fragment.mState) {
                                n4 = fragment.mState == 0 && fragment.isInBackStack() ? 1 : fragment.mState;
                            }
                        }
                        n = n4;
                        if (fragment.mDeferStart) {
                            n = n4;
                            if (fragment.mState < 4) {
                                n = n4;
                                if (n4 > 3) {
                                    n = 3;
                                }
                            }
                        }
                        if (fragment.mState > n) break block88;
                        if (fragment.mFromLayout && !fragment.mInLayout) break block89;
                        if (fragment.getAnimatingAway() != null || fragment.getAnimator() != null) {
                            fragment.setAnimatingAway(null);
                            fragment.setAnimator(null);
                            this.moveToState(fragment, fragment.getStateAfterAnimating(), 0, 0, true);
                        }
                        n3 = n;
                        n4 = n;
                        n5 = n;
                        n2 = n;
                        switch (fragment.mState) {
                            default: {
                                n4 = n;
                                break;
                            }
                            case 0: {
                                Object object;
                                n3 = n;
                                if (n > 0) {
                                    if (DEBUG) {
                                        Log.v((String)TAG, (String)("moveto CREATED: " + fragment));
                                    }
                                    n3 = n;
                                    if (fragment.mSavedFragmentState != null) {
                                        fragment.mSavedFragmentState.setClassLoader(this.mHost.getContext().getClassLoader());
                                        fragment.mSavedViewState = fragment.mSavedFragmentState.getSparseParcelableArray(VIEW_STATE_TAG);
                                        fragment.mTarget = this.getFragment(fragment.mSavedFragmentState, TARGET_STATE_TAG);
                                        if (fragment.mTarget != null) {
                                            fragment.mTargetRequestCode = fragment.mSavedFragmentState.getInt(TARGET_REQUEST_CODE_STATE_TAG, 0);
                                        }
                                        fragment.mUserVisibleHint = fragment.mSavedFragmentState.getBoolean(USER_VISIBLE_HINT_TAG, true);
                                        n3 = n;
                                        if (!fragment.mUserVisibleHint) {
                                            fragment.mDeferStart = true;
                                            n3 = n;
                                            if (n > 3) {
                                                n3 = 3;
                                            }
                                        }
                                    }
                                    fragment.mHost = this.mHost;
                                    fragment.mParentFragment = this.mParent;
                                    object = this.mParent != null ? this.mParent.mChildFragmentManager : this.mHost.getFragmentManagerImpl();
                                    fragment.mFragmentManager = object;
                                    if (fragment.mTarget != null) {
                                        if (this.mActive.get(fragment.mTarget.mIndex) != fragment.mTarget) {
                                            throw new IllegalStateException("Fragment " + fragment + " declared target fragment " + fragment.mTarget + " that does not belong to this FragmentManager!");
                                        }
                                        if (fragment.mTarget.mState < 1) {
                                            this.moveToState(fragment.mTarget, 1, 0, 0, true);
                                        }
                                    }
                                    this.dispatchOnFragmentPreAttached(fragment, this.mHost.getContext(), false);
                                    fragment.mCalled = false;
                                    fragment.onAttach(this.mHost.getContext());
                                    if (!fragment.mCalled) {
                                        throw new SuperNotCalledException("Fragment " + fragment + " did not call through to super.onAttach()");
                                    }
                                    if (fragment.mParentFragment == null) {
                                        this.mHost.onAttachFragment(fragment);
                                    } else {
                                        fragment.mParentFragment.onAttachFragment(fragment);
                                    }
                                    this.dispatchOnFragmentAttached(fragment, this.mHost.getContext(), false);
                                    if (!fragment.mIsCreated) {
                                        this.dispatchOnFragmentPreCreated(fragment, fragment.mSavedFragmentState, false);
                                        fragment.performCreate(fragment.mSavedFragmentState);
                                        this.dispatchOnFragmentCreated(fragment, fragment.mSavedFragmentState, false);
                                    } else {
                                        fragment.restoreChildFragmentState(fragment.mSavedFragmentState);
                                        fragment.mState = 1;
                                    }
                                    fragment.mRetaining = false;
                                }
                            }
                            case 1: {
                                Object object;
                                this.ensureInflatedFragmentView(fragment);
                                n4 = n3;
                                if (n3 > 1) {
                                    if (DEBUG) {
                                        Log.v((String)TAG, (String)("moveto ACTIVITY_CREATED: " + fragment));
                                    }
                                    if (!fragment.mFromLayout) {
                                        object = null;
                                        if (fragment.mContainerId != 0) {
                                            if (fragment.mContainerId == -1) {
                                                this.throwException(new IllegalArgumentException("Cannot create fragment " + fragment + " for a container view with no id"));
                                            }
                                            ViewGroup viewGroup = (ViewGroup)this.mContainer.onFindViewById(fragment.mContainerId);
                                            object = viewGroup;
                                            if (viewGroup == null) {
                                                object = viewGroup;
                                                if (!fragment.mRestored) {
                                                    try {
                                                        object = fragment.getResources().getResourceName(fragment.mContainerId);
                                                    }
                                                    catch (Resources.NotFoundException notFoundException) {
                                                        object = "unknown";
                                                    }
                                                    this.throwException(new IllegalArgumentException("No view found for id 0x" + Integer.toHexString(fragment.mContainerId) + " (" + (String)object + ") for fragment " + fragment));
                                                    object = viewGroup;
                                                }
                                            }
                                        }
                                        fragment.mContainer = object;
                                        fragment.mView = fragment.performCreateView(fragment.performGetLayoutInflater(fragment.mSavedFragmentState), (ViewGroup)object, fragment.mSavedFragmentState);
                                        if (fragment.mView != null) {
                                            fragment.mInnerView = fragment.mView;
                                            fragment.mView.setSaveFromParentEnabled(false);
                                            if (object != null) {
                                                object.addView(fragment.mView);
                                            }
                                            if (fragment.mHidden) {
                                                fragment.mView.setVisibility(8);
                                            }
                                            fragment.onViewCreated(fragment.mView, fragment.mSavedFragmentState);
                                            this.dispatchOnFragmentViewCreated(fragment, fragment.mView, fragment.mSavedFragmentState, false);
                                            bl = fragment.mView.getVisibility() == 0 && fragment.mContainer != null;
                                            fragment.mIsNewlyAdded = bl;
                                        } else {
                                            fragment.mInnerView = null;
                                        }
                                    }
                                    fragment.performActivityCreated(fragment.mSavedFragmentState);
                                    this.dispatchOnFragmentActivityCreated(fragment, fragment.mSavedFragmentState, false);
                                    if (fragment.mView != null) {
                                        fragment.restoreViewState(fragment.mSavedFragmentState);
                                    }
                                    fragment.mSavedFragmentState = null;
                                    n4 = n3;
                                }
                            }
                            case 2: {
                                n5 = n4;
                                if (n4 > 2) {
                                    fragment.mState = 3;
                                    n5 = n4;
                                }
                            }
                            case 3: {
                                n2 = n5;
                                if (n5 > 3) {
                                    if (DEBUG) {
                                        Log.v((String)TAG, (String)("moveto STARTED: " + fragment));
                                    }
                                    fragment.performStart();
                                    this.dispatchOnFragmentStarted(fragment, false);
                                    n2 = n5;
                                }
                            }
                            case 4: {
                                n4 = n2;
                                if (n2 <= 4) break;
                                if (DEBUG) {
                                    Log.v((String)TAG, (String)("moveto RESUMED: " + fragment));
                                }
                                fragment.performResume();
                                this.dispatchOnFragmentResumed(fragment, false);
                                fragment.mSavedFragmentState = null;
                                fragment.mSavedViewState = null;
                                n4 = n2;
                                break;
                            }
                        }
                        break block90;
                    }
                    n4 = n;
                    if (fragment.mState > n) {
                        switch (fragment.mState) {
                            default: {
                                n4 = n;
                                break;
                            }
                            case 5: {
                                if (n < 5) {
                                    if (DEBUG) {
                                        Log.v((String)TAG, (String)("movefrom RESUMED: " + fragment));
                                    }
                                    fragment.performPause();
                                    this.dispatchOnFragmentPaused(fragment, false);
                                }
                            }
                            case 4: {
                                if (n < 4) {
                                    if (DEBUG) {
                                        Log.v((String)TAG, (String)("movefrom STARTED: " + fragment));
                                    }
                                    fragment.performStop();
                                    this.dispatchOnFragmentStopped(fragment, false);
                                }
                            }
                            case 3: {
                                if (n < 3) {
                                    if (DEBUG) {
                                        Log.v((String)TAG, (String)("movefrom STOPPED: " + fragment));
                                    }
                                    fragment.performReallyStop();
                                }
                            }
                            case 2: {
                                Object object;
                                if (n < 2) {
                                    if (DEBUG) {
                                        Log.v((String)TAG, (String)("movefrom ACTIVITY_CREATED: " + fragment));
                                    }
                                    if (fragment.mView != null && this.mHost.onShouldSaveFragmentState(fragment) && fragment.mSavedViewState == null) {
                                        this.saveFragmentViewState(fragment);
                                    }
                                    fragment.performDestroyView();
                                    this.dispatchOnFragmentViewDestroyed(fragment, false);
                                    if (fragment.mView != null && fragment.mContainer != null) {
                                        fragment.mView.clearAnimation();
                                        fragment.mContainer.endViewTransition(fragment.mView);
                                        Object var9_12 = null;
                                        object = var9_12;
                                        if (this.mCurState > 0) {
                                            object = var9_12;
                                            if (!this.mDestroyed) {
                                                object = var9_12;
                                                if (fragment.mView.getVisibility() == 0) {
                                                    object = var9_12;
                                                    if (fragment.mPostponedAlpha >= 0.0f) {
                                                        object = this.loadAnimation(fragment, n2, false, n3);
                                                    }
                                                }
                                            }
                                        }
                                        fragment.mPostponedAlpha = 0.0f;
                                        if (object != null) {
                                            this.animateRemoveFragment(fragment, (AnimationOrAnimator)object, n);
                                        }
                                        fragment.mContainer.removeView(fragment.mView);
                                    }
                                    fragment.mContainer = null;
                                    fragment.mView = null;
                                    fragment.mInnerView = null;
                                    fragment.mInLayout = false;
                                }
                            }
                            case 1: {
                                Object object;
                                n4 = n;
                                if (n >= 1) break;
                                if (this.mDestroyed) {
                                    if (fragment.getAnimatingAway() != null) {
                                        object = fragment.getAnimatingAway();
                                        fragment.setAnimatingAway(null);
                                        object.clearAnimation();
                                    } else if (fragment.getAnimator() != null) {
                                        object = fragment.getAnimator();
                                        fragment.setAnimator(null);
                                        object.cancel();
                                    }
                                }
                                if (fragment.getAnimatingAway() != null || fragment.getAnimator() != null) {
                                    fragment.setStateAfterAnimating(n);
                                    n4 = 1;
                                    break;
                                }
                                if (DEBUG) {
                                    Log.v((String)TAG, (String)("movefrom CREATED: " + fragment));
                                }
                                if (!fragment.mRetaining) {
                                    fragment.performDestroy();
                                    this.dispatchOnFragmentDestroyed(fragment, false);
                                } else {
                                    fragment.mState = 0;
                                }
                                fragment.performDetach();
                                this.dispatchOnFragmentDetached(fragment, false);
                                n4 = n;
                                if (bl) break;
                                if (!fragment.mRetaining) {
                                    this.makeInactive(fragment);
                                    n4 = n;
                                    break;
                                }
                                fragment.mHost = null;
                                fragment.mParentFragment = null;
                                fragment.mFragmentManager = null;
                                n4 = n;
                            }
                        }
                    }
                }
                if (fragment.mState != n4) break block91;
            }
            return;
        }
        Log.w((String)TAG, (String)("moveToState: Fragment state for " + fragment + " not updated inline; " + "expected state " + n4 + " found " + fragment.mState));
        fragment.mState = n4;
    }

    public void noteStateNotSaved() {
        this.mSavedNonConfig = null;
        this.mStateSaved = false;
        int n = this.mAdded.size();
        for (int i = 0; i < n; ++i) {
            Fragment fragment = this.mAdded.get(i);
            if (fragment == null) continue;
            fragment.noteStateNotSaved();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public View onCreateView(View object, String object2, Context context, AttributeSet attributeSet) {
        if (!"fragment".equals(object2)) {
            return null;
        }
        object2 = attributeSet.getAttributeValue(null, "class");
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, FragmentTag.Fragment);
        Object object3 = object2;
        if (object2 == null) {
            object3 = typedArray.getString(0);
        }
        int n = typedArray.getResourceId(1, -1);
        String string2 = typedArray.getString(2);
        typedArray.recycle();
        if (!Fragment.isSupportFragmentClass(this.mHost.getContext(), (String)object3)) {
            return null;
        }
        int n2 = object != null ? object.getId() : 0;
        if (n2 == -1 && n == -1 && string2 == null) {
            throw new IllegalArgumentException(attributeSet.getPositionDescription() + ": Must specify unique android:id, android:tag, or have a parent with an id for " + (String)object3);
        }
        object2 = n != -1 ? this.findFragmentById(n) : null;
        object = object2;
        if (object2 == null) {
            object = object2;
            if (string2 != null) {
                object = this.findFragmentByTag(string2);
            }
        }
        object2 = object;
        if (object == null) {
            object2 = object;
            if (n2 != -1) {
                object2 = this.findFragmentById(n2);
            }
        }
        if (DEBUG) {
            Log.v((String)TAG, (String)("onCreateView: id=0x" + Integer.toHexString(n) + " fname=" + (String)object3 + " existing=" + object2));
        }
        if (object2 == null) {
            object = this.mContainer.instantiate(context, (String)object3, null);
            ((Fragment)object).mFromLayout = true;
            int n3 = n != 0 ? n : n2;
            ((Fragment)object).mFragmentId = n3;
            ((Fragment)object).mContainerId = n2;
            ((Fragment)object).mTag = string2;
            ((Fragment)object).mInLayout = true;
            ((Fragment)object).mFragmentManager = this;
            ((Fragment)object).mHost = this.mHost;
            ((Fragment)object).onInflate(this.mHost.getContext(), attributeSet, ((Fragment)object).mSavedFragmentState);
            this.addFragment((Fragment)object, true);
        } else {
            if (((Fragment)object2).mInLayout) {
                throw new IllegalArgumentException(attributeSet.getPositionDescription() + ": Duplicate id 0x" + Integer.toHexString(n) + ", tag " + string2 + ", or parent id 0x" + Integer.toHexString(n2) + " with another fragment for " + (String)object3);
            }
            ((Fragment)object2).mInLayout = true;
            ((Fragment)object2).mHost = this.mHost;
            object = object2;
            if (!((Fragment)object2).mRetaining) {
                ((Fragment)object2).onInflate(this.mHost.getContext(), attributeSet, ((Fragment)object2).mSavedFragmentState);
                object = object2;
            }
        }
        if (this.mCurState < 1 && ((Fragment)object).mFromLayout) {
            this.moveToState((Fragment)object, 1, 0, 0, false);
        } else {
            this.moveToState((Fragment)object);
        }
        if (((Fragment)object).mView == null) {
            throw new IllegalStateException("Fragment " + (String)object3 + " did not create a view.");
        }
        if (n != 0) {
            ((Fragment)object).mView.setId(n);
        }
        if (((Fragment)object).mView.getTag() == null) {
            ((Fragment)object).mView.setTag((Object)string2);
        }
        return ((Fragment)object).mView;
    }

    public View onCreateView(String string2, Context context, AttributeSet attributeSet) {
        return this.onCreateView(null, string2, context, attributeSet);
    }

    public void performPendingDeferredStart(Fragment fragment) {
        block3: {
            block2: {
                if (!fragment.mDeferStart) break block2;
                if (!this.mExecutingActions) break block3;
                this.mHavePendingDeferredStart = true;
            }
            return;
        }
        fragment.mDeferStart = false;
        this.moveToState(fragment, this.mCurState, 0, 0, false);
    }

    @Override
    public void popBackStack() {
        this.enqueueAction(new PopBackStackState(null, -1, 0), false);
    }

    @Override
    public void popBackStack(int n, int n2) {
        if (n < 0) {
            throw new IllegalArgumentException("Bad id: " + n);
        }
        this.enqueueAction(new PopBackStackState(null, n, n2), false);
    }

    @Override
    public void popBackStack(String string2, int n) {
        this.enqueueAction(new PopBackStackState(string2, -1, n), false);
    }

    @Override
    public boolean popBackStackImmediate() {
        this.checkStateLoss();
        return this.popBackStackImmediate(null, -1, 0);
    }

    @Override
    public boolean popBackStackImmediate(int n, int n2) {
        this.checkStateLoss();
        this.execPendingActions();
        if (n < 0) {
            throw new IllegalArgumentException("Bad id: " + n);
        }
        return this.popBackStackImmediate(null, n, n2);
    }

    @Override
    public boolean popBackStackImmediate(String string2, int n) {
        this.checkStateLoss();
        return this.popBackStackImmediate(string2, -1, n);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    boolean popBackStackState(ArrayList<BackStackRecord> var1_1, ArrayList<Boolean> var2_2, String var3_3, int var4_4, int var5_5) {
        block5: {
            if (this.mBackStack == null) {
                return false;
            }
            if (var3_3 == null && var4_4 < 0 && (var5_5 & 1) == 0) {
                var4_4 = this.mBackStack.size() - 1;
                if (var4_4 < 0) return false;
                var1_1.add(this.mBackStack.remove(var4_4));
                var2_2.add(true);
                return true;
            }
            var6_6 = -1;
            if (var3_3 == null && var4_4 < 0) break block5;
            for (var7_7 = this.mBackStack.size() - 1; var7_7 >= 0; --var7_7) {
                var8_8 = this.mBackStack.get(var7_7);
                if (var3_3 != null && var3_3.equals(var8_8.getName()) || var4_4 >= 0 && var4_4 == var8_8.mIndex) break;
            }
            if (var7_7 < 0) return false;
            var6_6 = var7_7;
            if ((var5_5 & 1) == 0) break block5;
            var5_5 = var7_7 - 1;
            do lbl-1000:
            // 3 sources
            {
                var6_6 = --var5_5;
                if (var5_5 < 0) break;
                var8_8 = this.mBackStack.get(var5_5);
                if (var3_3 != null && var3_3.equals(var8_8.getName())) ** GOTO lbl-1000
                var6_6 = var5_5;
                if (var4_4 < 0) break;
                var6_6 = var5_5;
            } while (var4_4 == var8_8.mIndex);
        }
        if (var6_6 == this.mBackStack.size() - 1) return false;
        var4_4 = this.mBackStack.size() - 1;
        while (var4_4 > var6_6) {
            var1_1.add(this.mBackStack.remove(var4_4));
            var2_2.add(true);
            --var4_4;
        }
        return true;
    }

    @Override
    public void putFragment(Bundle bundle, String string2, Fragment fragment) {
        if (fragment.mIndex < 0) {
            this.throwException(new IllegalStateException("Fragment " + fragment + " is not currently in the FragmentManager"));
        }
        bundle.putInt(string2, fragment.mIndex);
    }

    @Override
    public void registerFragmentLifecycleCallbacks(FragmentManager.FragmentLifecycleCallbacks fragmentLifecycleCallbacks, boolean bl) {
        this.mLifecycleCallbacks.add(new Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>(fragmentLifecycleCallbacks, bl));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void removeFragment(Fragment fragment) {
        if (DEBUG) {
            Log.v((String)TAG, (String)("remove: " + fragment + " nesting=" + fragment.mBackStackNesting));
        }
        boolean bl = !fragment.isInBackStack();
        if (fragment.mDetached) {
            if (!bl) return;
        }
        ArrayList<Fragment> arrayList = this.mAdded;
        // MONITORENTER : arrayList
        this.mAdded.remove(fragment);
        // MONITOREXIT : arrayList
        if (fragment.mHasMenu && fragment.mMenuVisible) {
            this.mNeedMenuInvalidate = true;
        }
        fragment.mAdded = false;
        fragment.mRemoving = true;
    }

    @Override
    public void removeOnBackStackChangedListener(FragmentManager.OnBackStackChangedListener onBackStackChangedListener) {
        if (this.mBackStackChangeListeners != null) {
            this.mBackStackChangeListeners.remove(onBackStackChangedListener);
        }
    }

    void reportBackStackChanged() {
        if (this.mBackStackChangeListeners != null) {
            for (int i = 0; i < this.mBackStackChangeListeners.size(); ++i) {
                this.mBackStackChangeListeners.get(i).onBackStackChanged();
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void restoreAllState(Parcelable object, FragmentManagerNonConfig object2) {
        int n;
        List<Fragment> list;
        FragmentManagerState fragmentManagerState;
        FragmentState fragmentState;
        int n2;
        Object object3;
        block28: {
            block27: {
                if (object == null) break block27;
                fragmentManagerState = (FragmentManagerState)object;
                if (fragmentManagerState.mActive != null) break block28;
            }
            return;
        }
        object = null;
        if (object2 != null) {
            list = ((FragmentManagerNonConfig)object2).getFragments();
            object3 = ((FragmentManagerNonConfig)object2).getChildNonConfigs();
            n2 = list != null ? list.size() : 0;
            n = 0;
            do {
                int n3;
                object = object3;
                if (n >= n2) break;
                object = list.get(n);
                if (DEBUG) {
                    Log.v((String)TAG, (String)("restoreAllState: re-attaching retained " + object));
                }
                for (n3 = 0; n3 < fragmentManagerState.mActive.length && fragmentManagerState.mActive[n3].mIndex != ((Fragment)object).mIndex; ++n3) {
                }
                if (n3 == fragmentManagerState.mActive.length) {
                    this.throwException(new IllegalStateException("Could not find active fragment with index " + ((Fragment)object).mIndex));
                }
                fragmentState = fragmentManagerState.mActive[n3];
                fragmentState.mInstance = object;
                ((Fragment)object).mSavedViewState = null;
                ((Fragment)object).mBackStackNesting = 0;
                ((Fragment)object).mInLayout = false;
                ((Fragment)object).mAdded = false;
                ((Fragment)object).mTarget = null;
                if (fragmentState.mSavedFragmentState != null) {
                    fragmentState.mSavedFragmentState.setClassLoader(this.mHost.getContext().getClassLoader());
                    ((Fragment)object).mSavedViewState = fragmentState.mSavedFragmentState.getSparseParcelableArray(VIEW_STATE_TAG);
                    ((Fragment)object).mSavedFragmentState = fragmentState.mSavedFragmentState;
                }
                ++n;
            } while (true);
        }
        this.mActive = new SparseArray(fragmentManagerState.mActive.length);
        for (n2 = 0; n2 < fragmentManagerState.mActive.length; ++n2) {
            fragmentState = fragmentManagerState.mActive[n2];
            if (fragmentState == null) continue;
            list = null;
            object3 = list;
            if (object != null) {
                object3 = list;
                if (n2 < object.size()) {
                    object3 = (FragmentManagerNonConfig)object.get(n2);
                }
            }
            object3 = fragmentState.instantiate(this.mHost, this.mContainer, this.mParent, (FragmentManagerNonConfig)object3);
            if (DEBUG) {
                Log.v((String)TAG, (String)("restoreAllState: active #" + n2 + ": " + object3));
            }
            this.mActive.put(((Fragment)object3).mIndex, object3);
            fragmentState.mInstance = null;
        }
        if (object2 != null) {
            object = ((FragmentManagerNonConfig)object2).getFragments();
            n2 = object != null ? object.size() : 0;
            for (n = 0; n < n2; ++n) {
                object2 = (Fragment)object.get(n);
                if (((Fragment)object2).mTargetIndex < 0) continue;
                ((Fragment)object2).mTarget = (Fragment)this.mActive.get(((Fragment)object2).mTargetIndex);
                if (((Fragment)object2).mTarget != null) continue;
                Log.w((String)TAG, (String)("Re-attaching retained fragment " + object2 + " target no longer exists: " + ((Fragment)object2).mTargetIndex));
            }
        }
        this.mAdded.clear();
        if (fragmentManagerState.mAdded != null) {
            for (n2 = 0; n2 < fragmentManagerState.mAdded.length; ++n2) {
                object2 = (Fragment)this.mActive.get(fragmentManagerState.mAdded[n2]);
                if (object2 == null) {
                    this.throwException(new IllegalStateException("No instantiated fragment for index #" + fragmentManagerState.mAdded[n2]));
                }
                ((Fragment)object2).mAdded = true;
                if (DEBUG) {
                    Log.v((String)TAG, (String)("restoreAllState: added #" + n2 + ": " + object2));
                }
                if (this.mAdded.contains(object2)) {
                    throw new IllegalStateException("Already added!");
                }
                object = this.mAdded;
                synchronized (object) {
                    this.mAdded.add((Fragment)object2);
                    continue;
                }
            }
        }
        if (fragmentManagerState.mBackStack != null) {
            this.mBackStack = new ArrayList(fragmentManagerState.mBackStack.length);
            for (n2 = 0; n2 < fragmentManagerState.mBackStack.length; ++n2) {
                object = fragmentManagerState.mBackStack[n2].instantiate(this);
                if (DEBUG) {
                    Log.v((String)TAG, (String)("restoreAllState: back stack #" + n2 + " (index " + ((BackStackRecord)object).mIndex + "): " + object));
                    object2 = new PrintWriter(new LogWriter(TAG));
                    ((BackStackRecord)object).dump("  ", (PrintWriter)object2, false);
                    ((PrintWriter)object2).close();
                }
                this.mBackStack.add((BackStackRecord)object);
                if (((BackStackRecord)object).mIndex < 0) continue;
                this.setBackStackIndex(((BackStackRecord)object).mIndex, (BackStackRecord)object);
            }
        } else {
            this.mBackStack = null;
        }
        if (fragmentManagerState.mPrimaryNavActiveIndex >= 0) {
            this.mPrimaryNav = (Fragment)this.mActive.get(fragmentManagerState.mPrimaryNavActiveIndex);
        }
        this.mNextFragmentIndex = fragmentManagerState.mNextFragmentIndex;
    }

    FragmentManagerNonConfig retainNonConfig() {
        FragmentManagerImpl.setRetaining(this.mSavedNonConfig);
        return this.mSavedNonConfig;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    Parcelable saveAllState() {
        void var6_17;
        int n;
        int n2;
        FragmentState[] arrfragmentState;
        void var5_8;
        block22: {
            block23: {
                block21: {
                    this.forcePostponedTransactions();
                    this.endAnimatingAwayFragments();
                    this.execPendingActions();
                    this.mStateSaved = true;
                    this.mSavedNonConfig = null;
                    if (this.mActive == null || this.mActive.size() <= 0) break block21;
                    int n3 = this.mActive.size();
                    arrfragmentState = new FragmentState[n3];
                    n2 = 0;
                    for (n = 0; n < n3; ++n) {
                        FragmentState fragmentState;
                        Fragment fragment = (Fragment)this.mActive.valueAt(n);
                        if (fragment == null) continue;
                        if (fragment.mIndex < 0) {
                            this.throwException(new IllegalStateException("Failure saving state: active " + fragment + " has cleared index: " + fragment.mIndex));
                        }
                        int n4 = 1;
                        arrfragmentState[n] = fragmentState = new FragmentState(fragment);
                        if (fragment.mState > 0 && fragmentState.mSavedFragmentState == null) {
                            fragmentState.mSavedFragmentState = this.saveFragmentBasicState(fragment);
                            if (fragment.mTarget != null) {
                                if (fragment.mTarget.mIndex < 0) {
                                    this.throwException(new IllegalStateException("Failure saving state: " + fragment + " has target not in fragment manager: " + fragment.mTarget));
                                }
                                if (fragmentState.mSavedFragmentState == null) {
                                    fragmentState.mSavedFragmentState = new Bundle();
                                }
                                this.putFragment(fragmentState.mSavedFragmentState, TARGET_STATE_TAG, fragment.mTarget);
                                if (fragment.mTargetRequestCode != 0) {
                                    fragmentState.mSavedFragmentState.putInt(TARGET_REQUEST_CODE_STATE_TAG, fragment.mTargetRequestCode);
                                }
                            }
                        } else {
                            fragmentState.mSavedFragmentState = fragment.mSavedFragmentState;
                        }
                        n2 = n4;
                        if (!DEBUG) continue;
                        Log.v((String)TAG, (String)("Saved state of " + fragment + ": " + (Object)fragmentState.mSavedFragmentState));
                        n2 = n4;
                    }
                    if (n2 != 0) break block22;
                    if (DEBUG) break block23;
                }
                return null;
            }
            Log.v((String)TAG, (String)"saveAllState: no fragments!");
            return null;
        }
        Object var5_7 = null;
        Object var7_18 = null;
        n2 = this.mAdded.size();
        if (n2 > 0) {
            int[] arrn = new int[n2];
            n = 0;
            do {
                int[] arrn2 = arrn;
                if (n >= n2) break;
                arrn[n] = this.mAdded.get((int)n).mIndex;
                if (arrn[n] < 0) {
                    this.throwException(new IllegalStateException("Failure saving state: active " + this.mAdded.get(n) + " has cleared index: " + arrn[n]));
                }
                if (DEBUG) {
                    Log.v((String)TAG, (String)("saveAllState: adding fragment #" + n + ": " + this.mAdded.get(n)));
                }
                ++n;
            } while (true);
        }
        Object var6_13 = var7_18;
        if (this.mBackStack != null) {
            n2 = this.mBackStack.size();
            Object var6_14 = var7_18;
            if (n2 > 0) {
                BackStackState[] arrbackStackState = new BackStackState[n2];
                n = 0;
                do {
                    BackStackState[] arrbackStackState2 = arrbackStackState;
                    if (n >= n2) break;
                    arrbackStackState[n] = new BackStackState(this.mBackStack.get(n));
                    if (DEBUG) {
                        Log.v((String)TAG, (String)("saveAllState: adding back stack #" + n + ": " + this.mBackStack.get(n)));
                    }
                    ++n;
                } while (true);
            }
        }
        FragmentManagerState fragmentManagerState = new FragmentManagerState();
        fragmentManagerState.mActive = arrfragmentState;
        fragmentManagerState.mAdded = var5_8;
        fragmentManagerState.mBackStack = var6_17;
        if (this.mPrimaryNav != null) {
            fragmentManagerState.mPrimaryNavActiveIndex = this.mPrimaryNav.mIndex;
        }
        fragmentManagerState.mNextFragmentIndex = this.mNextFragmentIndex;
        this.saveNonConfig();
        return fragmentManagerState;
    }

    Bundle saveFragmentBasicState(Fragment fragment) {
        Bundle bundle = null;
        if (this.mStateBundle == null) {
            this.mStateBundle = new Bundle();
        }
        fragment.performSaveInstanceState(this.mStateBundle);
        this.dispatchOnFragmentSaveInstanceState(fragment, this.mStateBundle, false);
        if (!this.mStateBundle.isEmpty()) {
            bundle = this.mStateBundle;
            this.mStateBundle = null;
        }
        if (fragment.mView != null) {
            this.saveFragmentViewState(fragment);
        }
        Bundle bundle2 = bundle;
        if (fragment.mSavedViewState != null) {
            bundle2 = bundle;
            if (bundle == null) {
                bundle2 = new Bundle();
            }
            bundle2.putSparseParcelableArray(VIEW_STATE_TAG, fragment.mSavedViewState);
        }
        bundle = bundle2;
        if (!fragment.mUserVisibleHint) {
            bundle = bundle2;
            if (bundle2 == null) {
                bundle = new Bundle();
            }
            bundle.putBoolean(USER_VISIBLE_HINT_TAG, fragment.mUserVisibleHint);
        }
        return bundle;
    }

    @Override
    public Fragment.SavedState saveFragmentInstanceState(Fragment fragment) {
        Fragment.SavedState savedState = null;
        if (fragment.mIndex < 0) {
            this.throwException(new IllegalStateException("Fragment " + fragment + " is not currently in the FragmentManager"));
        }
        Fragment.SavedState savedState2 = savedState;
        if (fragment.mState > 0) {
            fragment = this.saveFragmentBasicState(fragment);
            savedState2 = savedState;
            if (fragment != null) {
                savedState2 = new Fragment.SavedState((Bundle)fragment);
            }
        }
        return savedState2;
    }

    /*
     * Enabled aggressive block sorting
     */
    void saveFragmentViewState(Fragment fragment) {
        block6: {
            block5: {
                if (fragment.mInnerView == null) break block5;
                if (this.mStateArray == null) {
                    this.mStateArray = new SparseArray();
                } else {
                    this.mStateArray.clear();
                }
                fragment.mInnerView.saveHierarchyState(this.mStateArray);
                if (this.mStateArray.size() > 0) break block6;
            }
            return;
        }
        fragment.mSavedViewState = this.mStateArray;
        this.mStateArray = null;
    }

    /*
     * Enabled aggressive block sorting
     */
    void saveNonConfig() {
        ArrayList<Object> arrayList = null;
        ArrayList<Object> arrayList2 = null;
        ArrayList<Object> arrayList3 = null;
        ArrayList<Object> arrayList4 = null;
        if (this.mActive != null) {
            int n = 0;
            do {
                arrayList3 = arrayList4;
                arrayList = arrayList2;
                if (n >= this.mActive.size()) break;
                Object object = (Fragment)this.mActive.valueAt(n);
                arrayList = arrayList4;
                ArrayList<Object> arrayList5 = arrayList2;
                if (object != null) {
                    int n2;
                    arrayList3 = arrayList2;
                    if (((Fragment)object).mRetainInstance) {
                        arrayList = arrayList2;
                        if (arrayList2 == null) {
                            arrayList = new ArrayList<Object>();
                        }
                        arrayList.add(object);
                        n2 = ((Fragment)object).mTarget != null ? object.mTarget.mIndex : -1;
                        ((Fragment)object).mTargetIndex = n2;
                        arrayList3 = arrayList;
                        if (DEBUG) {
                            Log.v((String)TAG, (String)("retainNonConfig: keeping retained " + object));
                            arrayList3 = arrayList;
                        }
                    }
                    if (((Fragment)object).mChildFragmentManager != null) {
                        ((Fragment)object).mChildFragmentManager.saveNonConfig();
                        object = object.mChildFragmentManager.mSavedNonConfig;
                    } else {
                        object = ((Fragment)object).mChildNonConfig;
                    }
                    arrayList2 = arrayList4;
                    if (arrayList4 == null) {
                        arrayList2 = arrayList4;
                        if (object != null) {
                            arrayList4 = new ArrayList(this.mActive.size());
                            n2 = 0;
                            do {
                                arrayList2 = arrayList4;
                                if (n2 >= n) break;
                                arrayList4.add(null);
                                ++n2;
                            } while (true);
                        }
                    }
                    arrayList = arrayList2;
                    arrayList5 = arrayList3;
                    if (arrayList2 != null) {
                        arrayList2.add(object);
                        arrayList5 = arrayList3;
                        arrayList = arrayList2;
                    }
                }
                ++n;
                arrayList4 = arrayList;
                arrayList2 = arrayList5;
            } while (true);
        }
        if (arrayList == null && arrayList3 == null) {
            this.mSavedNonConfig = null;
            return;
        }
        this.mSavedNonConfig = new FragmentManagerNonConfig(arrayList, arrayList3);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setBackStackIndex(int n, BackStackRecord backStackRecord) {
        synchronized (this) {
            int n2;
            if (this.mBackStackIndices == null) {
                this.mBackStackIndices = new ArrayList();
            }
            if (n < n2) {
                if (DEBUG) {
                    Log.v((String)TAG, (String)("Setting back stack index " + n + " to " + backStackRecord));
                }
                this.mBackStackIndices.set(n, backStackRecord);
            } else {
                for (int i = n2 = this.mBackStackIndices.size(); i < n; ++i) {
                    this.mBackStackIndices.add(null);
                    if (this.mAvailBackStackIndices == null) {
                        this.mAvailBackStackIndices = new ArrayList();
                    }
                    if (DEBUG) {
                        Log.v((String)TAG, (String)("Adding available back stack index " + i));
                    }
                    this.mAvailBackStackIndices.add(i);
                }
                if (DEBUG) {
                    Log.v((String)TAG, (String)("Adding back stack index " + n + " with " + backStackRecord));
                }
                this.mBackStackIndices.add(backStackRecord);
            }
            return;
        }
    }

    public void setPrimaryNavigationFragment(Fragment fragment) {
        if (fragment != null && (this.mActive.get(fragment.mIndex) != fragment || fragment.mHost != null && fragment.getFragmentManager() != this)) {
            throw new IllegalArgumentException("Fragment " + fragment + " is not an active fragment of FragmentManager " + this);
        }
        this.mPrimaryNav = fragment;
    }

    public void showFragment(Fragment fragment) {
        boolean bl = false;
        if (DEBUG) {
            Log.v((String)TAG, (String)("show: " + fragment));
        }
        if (fragment.mHidden) {
            fragment.mHidden = false;
            if (!fragment.mHiddenChanged) {
                bl = true;
            }
            fragment.mHiddenChanged = bl;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    void startPendingDeferredFragments() {
        if (this.mActive != null) {
            for (int i = 0; i < this.mActive.size(); ++i) {
                Fragment fragment = (Fragment)this.mActive.valueAt(i);
                if (fragment == null) continue;
                this.performPendingDeferredStart(fragment);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(128);
        stringBuilder.append("FragmentManager{");
        stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
        stringBuilder.append(" in ");
        if (this.mParent != null) {
            DebugUtils.buildShortClassTag(this.mParent, stringBuilder);
        } else {
            DebugUtils.buildShortClassTag(this.mHost, stringBuilder);
        }
        stringBuilder.append("}}");
        return stringBuilder.toString();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void unregisterFragmentLifecycleCallbacks(FragmentManager.FragmentLifecycleCallbacks fragmentLifecycleCallbacks) {
        CopyOnWriteArrayList<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> copyOnWriteArrayList = this.mLifecycleCallbacks;
        synchronized (copyOnWriteArrayList) {
            int n = 0;
            int n2 = this.mLifecycleCallbacks.size();
            do {
                block6: {
                    block5: {
                        if (n >= n2) break block5;
                        if (this.mLifecycleCallbacks.get((int)n).first != fragmentLifecycleCallbacks) break block6;
                        this.mLifecycleCallbacks.remove(n);
                    }
                    return;
                }
                ++n;
            } while (true);
        }
    }

    private static class AnimateOnHWLayerIfNeededListener
    extends AnimationListenerWrapper {
        View mView;

        AnimateOnHWLayerIfNeededListener(View view, Animation.AnimationListener animationListener) {
            super(animationListener);
            this.mView = view;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void onAnimationEnd(Animation animation) {
            if (ViewCompat.isAttachedToWindow(this.mView) || Build.VERSION.SDK_INT >= 24) {
                this.mView.post(new Runnable(){

                    @Override
                    public void run() {
                        AnimateOnHWLayerIfNeededListener.this.mView.setLayerType(0, null);
                    }
                });
            } else {
                this.mView.setLayerType(0, null);
            }
            super.onAnimationEnd(animation);
        }

    }

    private static class AnimationListenerWrapper
    implements Animation.AnimationListener {
        private final Animation.AnimationListener mWrapped;

        private AnimationListenerWrapper(Animation.AnimationListener animationListener) {
            this.mWrapped = animationListener;
        }

        public void onAnimationEnd(Animation animation) {
            if (this.mWrapped != null) {
                this.mWrapped.onAnimationEnd(animation);
            }
        }

        public void onAnimationRepeat(Animation animation) {
            if (this.mWrapped != null) {
                this.mWrapped.onAnimationRepeat(animation);
            }
        }

        public void onAnimationStart(Animation animation) {
            if (this.mWrapped != null) {
                this.mWrapped.onAnimationStart(animation);
            }
        }
    }

    private static class AnimationOrAnimator {
        public final Animation animation;
        public final Animator animator;

        private AnimationOrAnimator(Animator animator2) {
            this.animation = null;
            this.animator = animator2;
            if (animator2 == null) {
                throw new IllegalStateException("Animator cannot be null");
            }
        }

        private AnimationOrAnimator(Animation animation) {
            this.animation = animation;
            this.animator = null;
            if (animation == null) {
                throw new IllegalStateException("Animation cannot be null");
            }
        }
    }

    private static class AnimatorOnHWLayerIfNeededListener
    extends AnimatorListenerAdapter {
        View mView;

        AnimatorOnHWLayerIfNeededListener(View view) {
            this.mView = view;
        }

        public void onAnimationEnd(Animator animator2) {
            this.mView.setLayerType(0, null);
            animator2.removeListener((Animator.AnimatorListener)this);
        }

        public void onAnimationStart(Animator animator2) {
            this.mView.setLayerType(2, null);
        }
    }

    static class FragmentTag {
        public static final int[] Fragment = new int[]{16842755, 16842960, 16842961};
        public static final int Fragment_id = 1;
        public static final int Fragment_name = 0;
        public static final int Fragment_tag = 2;

        FragmentTag() {
        }
    }

    static interface OpGenerator {
        public boolean generateOps(ArrayList<BackStackRecord> var1, ArrayList<Boolean> var2);
    }

    private class PopBackStackState
    implements OpGenerator {
        final int mFlags;
        final int mId;
        final String mName;

        PopBackStackState(String string2, int n, int n2) {
            this.mName = string2;
            this.mId = n;
            this.mFlags = n2;
        }

        @Override
        public boolean generateOps(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2) {
            FragmentManager fragmentManager;
            if (FragmentManagerImpl.this.mPrimaryNav != null && this.mId < 0 && this.mName == null && (fragmentManager = FragmentManagerImpl.this.mPrimaryNav.peekChildFragmentManager()) != null && fragmentManager.popBackStackImmediate()) {
                return false;
            }
            return FragmentManagerImpl.this.popBackStackState(arrayList, arrayList2, this.mName, this.mId, this.mFlags);
        }
    }

    static class StartEnterTransitionListener
    implements Fragment.OnStartEnterTransitionListener {
        private final boolean mIsBack;
        private int mNumPostponed;
        private final BackStackRecord mRecord;

        StartEnterTransitionListener(BackStackRecord backStackRecord, boolean bl) {
            this.mIsBack = bl;
            this.mRecord = backStackRecord;
        }

        public void cancelTransaction() {
            this.mRecord.mManager.completeExecute(this.mRecord, this.mIsBack, false, false);
        }

        /*
         * Enabled aggressive block sorting
         */
        public void completeTransaction() {
            Object object;
            boolean bl = false;
            boolean bl2 = this.mNumPostponed > 0;
            FragmentManagerImpl fragmentManagerImpl = this.mRecord.mManager;
            int n = fragmentManagerImpl.mAdded.size();
            for (int i = 0; i < n; ++i) {
                object = fragmentManagerImpl.mAdded.get(i);
                ((Fragment)object).setOnStartEnterTransitionListener(null);
                if (!bl2 || !((Fragment)object).isPostponed()) continue;
                ((Fragment)object).startPostponedEnterTransition();
            }
            fragmentManagerImpl = this.mRecord.mManager;
            object = this.mRecord;
            boolean bl3 = this.mIsBack;
            if (!bl2) {
                bl = true;
            }
            fragmentManagerImpl.completeExecute((BackStackRecord)object, bl3, bl, true);
        }

        public boolean isReady() {
            return this.mNumPostponed == 0;
        }

        @Override
        public void onStartEnterTransition() {
            --this.mNumPostponed;
            if (this.mNumPostponed != 0) {
                return;
            }
            this.mRecord.mManager.scheduleCommit();
        }

        @Override
        public void startListening() {
            ++this.mNumPostponed;
        }
    }

}

