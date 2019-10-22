/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.ValueAnimator
 *  android.util.StateSet
 */
package android.support.design.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.util.StateSet;
import java.util.ArrayList;

final class StateListAnimator {
    private final Animator.AnimatorListener mAnimationListener;
    private Tuple mLastMatch = null;
    ValueAnimator mRunningAnimator = null;
    private final ArrayList<Tuple> mTuples = new ArrayList();

    StateListAnimator() {
        this.mAnimationListener = new AnimatorListenerAdapter(){

            public void onAnimationEnd(Animator animator2) {
                if (StateListAnimator.this.mRunningAnimator == animator2) {
                    StateListAnimator.this.mRunningAnimator = null;
                }
            }
        };
    }

    private void cancel() {
        if (this.mRunningAnimator != null) {
            this.mRunningAnimator.cancel();
            this.mRunningAnimator = null;
        }
    }

    private void start(Tuple tuple) {
        this.mRunningAnimator = tuple.mAnimator;
        this.mRunningAnimator.start();
    }

    public void addState(int[] object, ValueAnimator valueAnimator) {
        object = new Tuple((int[])object, valueAnimator);
        valueAnimator.addListener(this.mAnimationListener);
        this.mTuples.add((Tuple)object);
    }

    public void jumpToCurrentState() {
        if (this.mRunningAnimator != null) {
            this.mRunningAnimator.end();
            this.mRunningAnimator = null;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    void setState(int[] arrn) {
        Tuple tuple;
        Tuple tuple2 = null;
        int n = this.mTuples.size();
        int n2 = 0;
        do {
            block8: {
                block7: {
                    tuple = tuple2;
                    if (n2 >= n) break block7;
                    tuple = this.mTuples.get(n2);
                    if (!StateSet.stateSetMatches((int[])tuple.mSpecs, (int[])arrn)) break block8;
                }
                if (tuple != this.mLastMatch) break;
                return;
            }
            ++n2;
        } while (true);
        if (this.mLastMatch != null) {
            this.cancel();
        }
        this.mLastMatch = tuple;
        if (tuple == null) {
            return;
        }
        this.start(tuple);
    }

    static class Tuple {
        final ValueAnimator mAnimator;
        final int[] mSpecs;

        Tuple(int[] arrn, ValueAnimator valueAnimator) {
            this.mSpecs = arrn;
            this.mAnimator = valueAnimator;
        }
    }

}

