/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.TimeInterpolator
 *  android.animation.ValueAnimator
 *  android.view.View
 *  android.view.ViewPropertyAnimator
 */
package android.support.v7.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.View;
import android.view.ViewPropertyAnimator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class DefaultItemAnimator
extends SimpleItemAnimator {
    private static TimeInterpolator sDefaultInterpolator;
    ArrayList<RecyclerView.ViewHolder> mAddAnimations;
    ArrayList<ArrayList<RecyclerView.ViewHolder>> mAdditionsList;
    ArrayList<RecyclerView.ViewHolder> mChangeAnimations;
    ArrayList<ArrayList<ChangeInfo>> mChangesList;
    ArrayList<RecyclerView.ViewHolder> mMoveAnimations;
    ArrayList<ArrayList<MoveInfo>> mMovesList;
    private ArrayList<RecyclerView.ViewHolder> mPendingAdditions;
    private ArrayList<ChangeInfo> mPendingChanges;
    private ArrayList<MoveInfo> mPendingMoves;
    private ArrayList<RecyclerView.ViewHolder> mPendingRemovals = new ArrayList();
    ArrayList<RecyclerView.ViewHolder> mRemoveAnimations;

    public DefaultItemAnimator() {
        this.mPendingAdditions = new ArrayList();
        this.mPendingMoves = new ArrayList();
        this.mPendingChanges = new ArrayList();
        this.mAdditionsList = new ArrayList();
        this.mMovesList = new ArrayList();
        this.mChangesList = new ArrayList();
        this.mAddAnimations = new ArrayList();
        this.mMoveAnimations = new ArrayList();
        this.mRemoveAnimations = new ArrayList();
        this.mChangeAnimations = new ArrayList();
    }

    private void animateRemoveImpl(final RecyclerView.ViewHolder viewHolder) {
        final View view = viewHolder.itemView;
        final ViewPropertyAnimator viewPropertyAnimator = view.animate();
        this.mRemoveAnimations.add(viewHolder);
        viewPropertyAnimator.setDuration(this.getRemoveDuration()).alpha(0.0f).setListener((Animator.AnimatorListener)new AnimatorListenerAdapter(){

            public void onAnimationEnd(Animator animator2) {
                viewPropertyAnimator.setListener(null);
                view.setAlpha(1.0f);
                DefaultItemAnimator.this.dispatchRemoveFinished(viewHolder);
                DefaultItemAnimator.this.mRemoveAnimations.remove(viewHolder);
                DefaultItemAnimator.this.dispatchFinishedWhenDone();
            }

            public void onAnimationStart(Animator animator2) {
                DefaultItemAnimator.this.dispatchRemoveStarting(viewHolder);
            }
        }).start();
    }

    private void endChangeAnimation(List<ChangeInfo> list, RecyclerView.ViewHolder viewHolder) {
        for (int i = list.size() - 1; i >= 0; --i) {
            ChangeInfo changeInfo = list.get(i);
            if (!this.endChangeAnimationIfNecessary(changeInfo, viewHolder) || changeInfo.oldHolder != null || changeInfo.newHolder != null) continue;
            list.remove(changeInfo);
        }
    }

    private void endChangeAnimationIfNecessary(ChangeInfo changeInfo) {
        if (changeInfo.oldHolder != null) {
            this.endChangeAnimationIfNecessary(changeInfo, changeInfo.oldHolder);
        }
        if (changeInfo.newHolder != null) {
            this.endChangeAnimationIfNecessary(changeInfo, changeInfo.newHolder);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean endChangeAnimationIfNecessary(ChangeInfo changeInfo, RecyclerView.ViewHolder viewHolder) {
        boolean bl = false;
        if (changeInfo.newHolder == viewHolder) {
            changeInfo.newHolder = null;
        } else {
            if (changeInfo.oldHolder != viewHolder) {
                return false;
            }
            changeInfo.oldHolder = null;
            bl = true;
        }
        viewHolder.itemView.setAlpha(1.0f);
        viewHolder.itemView.setTranslationX(0.0f);
        viewHolder.itemView.setTranslationY(0.0f);
        this.dispatchChangeFinished(viewHolder, bl);
        return true;
    }

    private void resetAnimation(RecyclerView.ViewHolder viewHolder) {
        if (sDefaultInterpolator == null) {
            sDefaultInterpolator = new ValueAnimator().getInterpolator();
        }
        viewHolder.itemView.animate().setInterpolator(sDefaultInterpolator);
        this.endAnimation(viewHolder);
    }

    @Override
    public boolean animateAdd(RecyclerView.ViewHolder viewHolder) {
        this.resetAnimation(viewHolder);
        viewHolder.itemView.setAlpha(0.0f);
        this.mPendingAdditions.add(viewHolder);
        return true;
    }

    void animateAddImpl(final RecyclerView.ViewHolder viewHolder) {
        final View view = viewHolder.itemView;
        final ViewPropertyAnimator viewPropertyAnimator = view.animate();
        this.mAddAnimations.add(viewHolder);
        viewPropertyAnimator.alpha(1.0f).setDuration(this.getAddDuration()).setListener((Animator.AnimatorListener)new AnimatorListenerAdapter(){

            public void onAnimationCancel(Animator animator2) {
                view.setAlpha(1.0f);
            }

            public void onAnimationEnd(Animator animator2) {
                viewPropertyAnimator.setListener(null);
                DefaultItemAnimator.this.dispatchAddFinished(viewHolder);
                DefaultItemAnimator.this.mAddAnimations.remove(viewHolder);
                DefaultItemAnimator.this.dispatchFinishedWhenDone();
            }

            public void onAnimationStart(Animator animator2) {
                DefaultItemAnimator.this.dispatchAddStarting(viewHolder);
            }
        }).start();
    }

    @Override
    public boolean animateChange(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2, int n, int n2, int n3, int n4) {
        if (viewHolder == viewHolder2) {
            return this.animateMove(viewHolder, n, n2, n3, n4);
        }
        float f = viewHolder.itemView.getTranslationX();
        float f2 = viewHolder.itemView.getTranslationY();
        float f3 = viewHolder.itemView.getAlpha();
        this.resetAnimation(viewHolder);
        int n5 = (int)((float)(n3 - n) - f);
        int n6 = (int)((float)(n4 - n2) - f2);
        viewHolder.itemView.setTranslationX(f);
        viewHolder.itemView.setTranslationY(f2);
        viewHolder.itemView.setAlpha(f3);
        if (viewHolder2 != null) {
            this.resetAnimation(viewHolder2);
            viewHolder2.itemView.setTranslationX((float)(-n5));
            viewHolder2.itemView.setTranslationY((float)(-n6));
            viewHolder2.itemView.setAlpha(0.0f);
        }
        this.mPendingChanges.add(new ChangeInfo(viewHolder, viewHolder2, n, n2, n3, n4));
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    void animateChangeImpl(final ChangeInfo changeInfo) {
        RecyclerView.ViewHolder viewHolder = changeInfo.oldHolder;
        viewHolder = viewHolder == null ? null : viewHolder.itemView;
        RecyclerView.ViewHolder viewHolder2 = changeInfo.newHolder;
        viewHolder2 = viewHolder2 != null ? viewHolder2.itemView : null;
        if (viewHolder != null) {
            final ViewPropertyAnimator viewPropertyAnimator = viewHolder.animate().setDuration(this.getChangeDuration());
            this.mChangeAnimations.add(changeInfo.oldHolder);
            viewPropertyAnimator.translationX((float)(changeInfo.toX - changeInfo.fromX));
            viewPropertyAnimator.translationY((float)(changeInfo.toY - changeInfo.fromY));
            viewPropertyAnimator.alpha(0.0f).setListener((Animator.AnimatorListener)new AnimatorListenerAdapter((View)viewHolder){
                final /* synthetic */ View val$view;
                {
                    this.val$view = view;
                }

                public void onAnimationEnd(Animator animator2) {
                    viewPropertyAnimator.setListener(null);
                    this.val$view.setAlpha(1.0f);
                    this.val$view.setTranslationX(0.0f);
                    this.val$view.setTranslationY(0.0f);
                    DefaultItemAnimator.this.dispatchChangeFinished(changeInfo.oldHolder, true);
                    DefaultItemAnimator.this.mChangeAnimations.remove(changeInfo.oldHolder);
                    DefaultItemAnimator.this.dispatchFinishedWhenDone();
                }

                public void onAnimationStart(Animator animator2) {
                    DefaultItemAnimator.this.dispatchChangeStarting(changeInfo.oldHolder, true);
                }
            }).start();
        }
        if (viewHolder2 != null) {
            viewHolder = viewHolder2.animate();
            this.mChangeAnimations.add(changeInfo.newHolder);
            viewHolder.translationX(0.0f).translationY(0.0f).setDuration(this.getChangeDuration()).alpha(1.0f).setListener((Animator.AnimatorListener)new AnimatorListenerAdapter((ViewPropertyAnimator)viewHolder, (View)viewHolder2){
                final /* synthetic */ View val$newView;
                final /* synthetic */ ViewPropertyAnimator val$newViewAnimation;
                {
                    this.val$newViewAnimation = viewPropertyAnimator;
                    this.val$newView = view;
                }

                public void onAnimationEnd(Animator animator2) {
                    this.val$newViewAnimation.setListener(null);
                    this.val$newView.setAlpha(1.0f);
                    this.val$newView.setTranslationX(0.0f);
                    this.val$newView.setTranslationY(0.0f);
                    DefaultItemAnimator.this.dispatchChangeFinished(changeInfo.newHolder, false);
                    DefaultItemAnimator.this.mChangeAnimations.remove(changeInfo.newHolder);
                    DefaultItemAnimator.this.dispatchFinishedWhenDone();
                }

                public void onAnimationStart(Animator animator2) {
                    DefaultItemAnimator.this.dispatchChangeStarting(changeInfo.newHolder, false);
                }
            }).start();
        }
    }

    @Override
    public boolean animateMove(RecyclerView.ViewHolder viewHolder, int n, int n2, int n3, int n4) {
        View view = viewHolder.itemView;
        this.resetAnimation(viewHolder);
        int n5 = n3 - (n += (int)viewHolder.itemView.getTranslationX());
        int n6 = n4 - (n2 += (int)viewHolder.itemView.getTranslationY());
        if (n5 == 0 && n6 == 0) {
            this.dispatchMoveFinished(viewHolder);
            return false;
        }
        if (n5 != 0) {
            view.setTranslationX((float)(-n5));
        }
        if (n6 != 0) {
            view.setTranslationY((float)(-n6));
        }
        this.mPendingMoves.add(new MoveInfo(viewHolder, n, n2, n3, n4));
        return true;
    }

    void animateMoveImpl(final RecyclerView.ViewHolder viewHolder, final int n, final int n2, int n3, int n4) {
        final View view = viewHolder.itemView;
        n = n3 - n;
        n2 = n4 - n2;
        if (n != 0) {
            view.animate().translationX(0.0f);
        }
        if (n2 != 0) {
            view.animate().translationY(0.0f);
        }
        final ViewPropertyAnimator viewPropertyAnimator = view.animate();
        this.mMoveAnimations.add(viewHolder);
        viewPropertyAnimator.setDuration(this.getMoveDuration()).setListener((Animator.AnimatorListener)new AnimatorListenerAdapter(){

            public void onAnimationCancel(Animator animator2) {
                if (n != 0) {
                    view.setTranslationX(0.0f);
                }
                if (n2 != 0) {
                    view.setTranslationY(0.0f);
                }
            }

            public void onAnimationEnd(Animator animator2) {
                viewPropertyAnimator.setListener(null);
                DefaultItemAnimator.this.dispatchMoveFinished(viewHolder);
                DefaultItemAnimator.this.mMoveAnimations.remove(viewHolder);
                DefaultItemAnimator.this.dispatchFinishedWhenDone();
            }

            public void onAnimationStart(Animator animator2) {
                DefaultItemAnimator.this.dispatchMoveStarting(viewHolder);
            }
        }).start();
    }

    @Override
    public boolean animateRemove(RecyclerView.ViewHolder viewHolder) {
        this.resetAnimation(viewHolder);
        this.mPendingRemovals.add(viewHolder);
        return true;
    }

    @Override
    public boolean canReuseUpdatedViewHolder(RecyclerView.ViewHolder viewHolder, List<Object> list) {
        return !list.isEmpty() || super.canReuseUpdatedViewHolder(viewHolder, list);
    }

    void cancelAll(List<RecyclerView.ViewHolder> list) {
        for (int i = list.size() - 1; i >= 0; --i) {
            list.get((int)i).itemView.animate().cancel();
        }
    }

    void dispatchFinishedWhenDone() {
        if (!this.isRunning()) {
            this.dispatchAnimationsFinished();
        }
    }

    @Override
    public void endAnimation(RecyclerView.ViewHolder viewHolder) {
        int n;
        ArrayList<Object> arrayList;
        View view = viewHolder.itemView;
        view.animate().cancel();
        for (n = this.mPendingMoves.size() - 1; n >= 0; --n) {
            if (this.mPendingMoves.get((int)n).holder != viewHolder) continue;
            view.setTranslationY(0.0f);
            view.setTranslationX(0.0f);
            this.dispatchMoveFinished(viewHolder);
            this.mPendingMoves.remove(n);
        }
        this.endChangeAnimation(this.mPendingChanges, viewHolder);
        if (this.mPendingRemovals.remove(viewHolder)) {
            view.setAlpha(1.0f);
            this.dispatchRemoveFinished(viewHolder);
        }
        if (this.mPendingAdditions.remove(viewHolder)) {
            view.setAlpha(1.0f);
            this.dispatchAddFinished(viewHolder);
        }
        for (n = this.mChangesList.size() - 1; n >= 0; --n) {
            arrayList = this.mChangesList.get(n);
            this.endChangeAnimation(arrayList, viewHolder);
            if (!arrayList.isEmpty()) continue;
            this.mChangesList.remove(n);
        }
        block2: for (n = this.mMovesList.size() - 1; n >= 0; --n) {
            arrayList = this.mMovesList.get(n);
            int n2 = arrayList.size() - 1;
            do {
                if (n2 < 0) continue block2;
                if (((MoveInfo)arrayList.get((int)n2)).holder == viewHolder) {
                    view.setTranslationY(0.0f);
                    view.setTranslationX(0.0f);
                    this.dispatchMoveFinished(viewHolder);
                    arrayList.remove(n2);
                    if (!arrayList.isEmpty()) continue block2;
                    this.mMovesList.remove(n);
                    continue block2;
                }
                --n2;
            } while (true);
        }
        for (n = this.mAdditionsList.size() - 1; n >= 0; --n) {
            arrayList = this.mAdditionsList.get(n);
            if (!arrayList.remove(viewHolder)) continue;
            view.setAlpha(1.0f);
            this.dispatchAddFinished(viewHolder);
            if (!arrayList.isEmpty()) continue;
            this.mAdditionsList.remove(n);
        }
        if (this.mRemoveAnimations.remove(viewHolder)) {
            // empty if block
        }
        if (this.mAddAnimations.remove(viewHolder)) {
            // empty if block
        }
        if (this.mChangeAnimations.remove(viewHolder)) {
            // empty if block
        }
        if (this.mMoveAnimations.remove(viewHolder)) {
            // empty if block
        }
        this.dispatchFinishedWhenDone();
    }

    @Override
    public void endAnimations() {
        ArrayList<Object> arrayList;
        Object object;
        int n;
        int n2;
        for (n2 = this.mPendingMoves.size() - 1; n2 >= 0; --n2) {
            arrayList = this.mPendingMoves.get(n2);
            object = arrayList.holder.itemView;
            object.setTranslationY(0.0f);
            object.setTranslationX(0.0f);
            this.dispatchMoveFinished(((MoveInfo)arrayList).holder);
            this.mPendingMoves.remove(n2);
        }
        for (n2 = this.mPendingRemovals.size() - 1; n2 >= 0; --n2) {
            this.dispatchRemoveFinished(this.mPendingRemovals.get(n2));
            this.mPendingRemovals.remove(n2);
        }
        for (n2 = this.mPendingAdditions.size() - 1; n2 >= 0; --n2) {
            arrayList = this.mPendingAdditions.get(n2);
            ((RecyclerView.ViewHolder)arrayList).itemView.setAlpha(1.0f);
            this.dispatchAddFinished((RecyclerView.ViewHolder)((Object)arrayList));
            this.mPendingAdditions.remove(n2);
        }
        for (n2 = this.mPendingChanges.size() - 1; n2 >= 0; --n2) {
            this.endChangeAnimationIfNecessary(this.mPendingChanges.get(n2));
        }
        this.mPendingChanges.clear();
        if (!this.isRunning()) {
            return;
        }
        for (n2 = this.mMovesList.size() - 1; n2 >= 0; --n2) {
            arrayList = this.mMovesList.get(n2);
            for (n = arrayList.size() - 1; n >= 0; --n) {
                object = arrayList.get(n);
                View view = object.holder.itemView;
                view.setTranslationY(0.0f);
                view.setTranslationX(0.0f);
                this.dispatchMoveFinished(object.holder);
                arrayList.remove(n);
                if (!arrayList.isEmpty()) continue;
                this.mMovesList.remove(arrayList);
            }
        }
        for (n2 = this.mAdditionsList.size() - 1; n2 >= 0; --n2) {
            arrayList = this.mAdditionsList.get(n2);
            for (n = arrayList.size() - 1; n >= 0; --n) {
                object = (RecyclerView.ViewHolder)arrayList.get(n);
                object.itemView.setAlpha(1.0f);
                this.dispatchAddFinished((RecyclerView.ViewHolder)object);
                arrayList.remove(n);
                if (!arrayList.isEmpty()) continue;
                this.mAdditionsList.remove(arrayList);
            }
        }
        for (n2 = this.mChangesList.size() - 1; n2 >= 0; --n2) {
            arrayList = this.mChangesList.get(n2);
            for (n = arrayList.size() - 1; n >= 0; --n) {
                this.endChangeAnimationIfNecessary((ChangeInfo)arrayList.get(n));
                if (!arrayList.isEmpty()) continue;
                this.mChangesList.remove(arrayList);
            }
        }
        this.cancelAll(this.mRemoveAnimations);
        this.cancelAll(this.mMoveAnimations);
        this.cancelAll(this.mAddAnimations);
        this.cancelAll(this.mChangeAnimations);
        this.dispatchAnimationsFinished();
    }

    @Override
    public boolean isRunning() {
        return !this.mPendingAdditions.isEmpty() || !this.mPendingChanges.isEmpty() || !this.mPendingMoves.isEmpty() || !this.mPendingRemovals.isEmpty() || !this.mMoveAnimations.isEmpty() || !this.mRemoveAnimations.isEmpty() || !this.mAddAnimations.isEmpty() || !this.mChangeAnimations.isEmpty() || !this.mMovesList.isEmpty() || !this.mAdditionsList.isEmpty() || !this.mChangesList.isEmpty();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void runPendingAnimations() {
        boolean bl;
        boolean bl2;
        boolean bl3;
        Runnable runnable;
        ArrayList<MoveInfo> arrayList;
        block12: {
            block11: {
                bl2 = !this.mPendingRemovals.isEmpty();
                bl = !this.mPendingMoves.isEmpty();
                bl3 = !this.mPendingChanges.isEmpty();
                boolean bl4 = !this.mPendingAdditions.isEmpty();
                if (!bl2 && !bl && !bl4 && !bl3) break block11;
                arrayList = this.mPendingRemovals.iterator();
                while (arrayList.hasNext()) {
                    this.animateRemoveImpl(arrayList.next());
                }
                this.mPendingRemovals.clear();
                if (bl) {
                    arrayList = new ArrayList();
                    arrayList.addAll(this.mPendingMoves);
                    this.mMovesList.add(arrayList);
                    this.mPendingMoves.clear();
                    runnable = new Runnable(){

                        @Override
                        public void run() {
                            for (MoveInfo moveInfo : arrayList) {
                                DefaultItemAnimator.this.animateMoveImpl(moveInfo.holder, moveInfo.fromX, moveInfo.fromY, moveInfo.toX, moveInfo.toY);
                            }
                            arrayList.clear();
                            DefaultItemAnimator.this.mMovesList.remove(arrayList);
                        }
                    };
                    if (bl2) {
                        ViewCompat.postOnAnimationDelayed(((MoveInfo)arrayList.get((int)0)).holder.itemView, runnable, this.getRemoveDuration());
                    } else {
                        runnable.run();
                    }
                }
                if (bl3) {
                    arrayList = new ArrayList<MoveInfo>();
                    arrayList.addAll(this.mPendingChanges);
                    this.mChangesList.add(arrayList);
                    this.mPendingChanges.clear();
                    runnable = new Runnable(){

                        @Override
                        public void run() {
                            for (ChangeInfo changeInfo : arrayList) {
                                DefaultItemAnimator.this.animateChangeImpl(changeInfo);
                            }
                            arrayList.clear();
                            DefaultItemAnimator.this.mChangesList.remove(arrayList);
                        }
                    };
                    if (bl2) {
                        ViewCompat.postOnAnimationDelayed(((ChangeInfo)arrayList.get((int)0)).oldHolder.itemView, runnable, this.getRemoveDuration());
                    } else {
                        runnable.run();
                    }
                }
                if (bl4) break block12;
            }
            return;
        }
        arrayList = new ArrayList();
        arrayList.addAll(this.mPendingAdditions);
        this.mAdditionsList.add(arrayList);
        this.mPendingAdditions.clear();
        runnable = new Runnable(){

            @Override
            public void run() {
                for (RecyclerView.ViewHolder viewHolder : arrayList) {
                    DefaultItemAnimator.this.animateAddImpl(viewHolder);
                }
                arrayList.clear();
                DefaultItemAnimator.this.mAdditionsList.remove(arrayList);
            }
        };
        if (!(bl2 || bl || bl3)) {
            runnable.run();
            return;
        }
        long l = bl2 ? this.getRemoveDuration() : 0L;
        long l2 = bl ? this.getMoveDuration() : 0L;
        long l3 = bl3 ? this.getChangeDuration() : 0L;
        l2 = Math.max(l2, l3);
        ViewCompat.postOnAnimationDelayed(((RecyclerView.ViewHolder)arrayList.get((int)0)).itemView, runnable, l + l2);
    }

    private static class ChangeInfo {
        public int fromX;
        public int fromY;
        public RecyclerView.ViewHolder newHolder;
        public RecyclerView.ViewHolder oldHolder;
        public int toX;
        public int toY;

        private ChangeInfo(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2) {
            this.oldHolder = viewHolder;
            this.newHolder = viewHolder2;
        }

        ChangeInfo(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2, int n, int n2, int n3, int n4) {
            this(viewHolder, viewHolder2);
            this.fromX = n;
            this.fromY = n2;
            this.toX = n3;
            this.toY = n4;
        }

        public String toString() {
            return "ChangeInfo{oldHolder=" + this.oldHolder + ", newHolder=" + this.newHolder + ", fromX=" + this.fromX + ", fromY=" + this.fromY + ", toX=" + this.toX + ", toY=" + this.toY + '}';
        }
    }

    private static class MoveInfo {
        public int fromX;
        public int fromY;
        public RecyclerView.ViewHolder holder;
        public int toX;
        public int toY;

        MoveInfo(RecyclerView.ViewHolder viewHolder, int n, int n2, int n3, int n4) {
            this.holder = viewHolder;
            this.fromX = n;
            this.fromY = n2;
            this.toX = n3;
            this.toY = n4;
        }
    }

}

