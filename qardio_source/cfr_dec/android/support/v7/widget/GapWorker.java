/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package android.support.v7.widget;

import android.support.v4.os.TraceCompat;
import android.support.v7.widget.AdapterHelper;
import android.support.v7.widget.ChildHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;

final class GapWorker
implements Runnable {
    static final ThreadLocal<GapWorker> sGapWorker = new ThreadLocal();
    static Comparator<Task> sTaskComparator = new Comparator<Task>(){

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public int compare(Task task, Task task2) {
            int n;
            int n2 = -1;
            int n3 = task.view == null ? 1 : 0;
            if (n3 != (n = task2.view == null)) {
                if (task.view != null) return -1;
                return 1;
            }
            if (task.immediate != task2.immediate) {
                if (!task.immediate) return 1;
                return n2;
            }
            n3 = task2.viewVelocity - task.viewVelocity;
            if (n3 != 0) {
                return n3;
            }
            n3 = task.distanceToItem - task2.distanceToItem;
            if (n3 == 0) return 0;
            return n3;
        }
    };
    long mFrameIntervalNs;
    long mPostTimeNs;
    ArrayList<RecyclerView> mRecyclerViews = new ArrayList();
    private ArrayList<Task> mTasks = new ArrayList();

    GapWorker() {
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void buildTaskList() {
        var5_1 = this.mRecyclerViews.size();
        var2_2 = 0;
        for (var1_3 = 0; var1_3 < var5_1; ++var1_3) {
            var8_5 = this.mRecyclerViews.get(var1_3);
            var3_4 = var2_2;
            if (var8_5.getWindowVisibility() == 0) {
                var8_5.mPrefetchRegistry.collectPrefetchPositionsFromView((RecyclerView)var8_5, false);
                var3_4 = var2_2 + var8_5.mPrefetchRegistry.mCount;
            }
            var2_2 = var3_4;
        }
        this.mTasks.ensureCapacity(var2_2);
        var1_3 = 0;
        var2_2 = 0;
        block1: do {
            if (var2_2 >= var5_1) {
                Collections.sort(this.mTasks, GapWorker.sTaskComparator);
                return;
            }
            var9_9 = this.mRecyclerViews.get(var2_2);
            if (var9_9.getWindowVisibility() != 0) {
                var4_6 = var1_3;
            } else {
                var10_10 = var9_9.mPrefetchRegistry;
                var6_7 = Math.abs(var10_10.mPrefetchDx) + Math.abs(var10_10.mPrefetchDy);
                var3_4 = 0;
                break;
            }
            do {
                ++var2_2;
                var1_3 = var4_6;
                continue block1;
                break;
            } while (true);
            break;
        } while (true);
        do {
            var4_6 = var1_3;
            if (var3_4 >= var10_10.mCount * 2) ** continue;
            if (var1_3 >= this.mTasks.size()) {
                var8_5 = new Task();
                this.mTasks.add((Task)var8_5);
            } else {
                var8_5 = this.mTasks.get(var1_3);
            }
            var7_8 = (var4_6 = var10_10.mPrefetchArray[var3_4 + 1]) <= var6_7;
            var8_5.immediate = var7_8;
            var8_5.viewVelocity = var6_7;
            var8_5.distanceToItem = var4_6;
            var8_5.view = var9_9;
            var8_5.position = var10_10.mPrefetchArray[var3_4];
            ++var1_3;
            var3_4 += 2;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void flushTaskWithDeadline(Task object, long l) {
        long l2 = ((Task)object).immediate ? Long.MAX_VALUE : l;
        object = this.prefetchPositionWithDeadline(((Task)object).view, ((Task)object).position, l2);
        if (object != null && ((RecyclerView.ViewHolder)object).mNestedRecyclerView != null && ((RecyclerView.ViewHolder)object).isBound() && !((RecyclerView.ViewHolder)object).isInvalid()) {
            this.prefetchInnerRecyclerViewWithDeadline((RecyclerView)((RecyclerView.ViewHolder)object).mNestedRecyclerView.get(), l);
        }
    }

    private void flushTasksWithDeadline(long l) {
        int n = 0;
        do {
            Task task;
            block4: {
                block3: {
                    if (n >= this.mTasks.size()) break block3;
                    task = this.mTasks.get(n);
                    if (task.view != null) break block4;
                }
                return;
            }
            this.flushTaskWithDeadline(task, l);
            task.clear();
            ++n;
        } while (true);
    }

    static boolean isPrefetchPositionAttached(RecyclerView recyclerView, int n) {
        int n2 = recyclerView.mChildHelper.getUnfilteredChildCount();
        for (int i = 0; i < n2; ++i) {
            RecyclerView.ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(recyclerView.mChildHelper.getUnfilteredChildAt(i));
            if (viewHolder.mPosition != n || viewHolder.isInvalid()) continue;
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void prefetchInnerRecyclerViewWithDeadline(RecyclerView recyclerView, long l) {
        if (recyclerView == null) return;
        if (recyclerView.mDataSetHasChangedAfterLayout && recyclerView.mChildHelper.getUnfilteredChildCount() != 0) {
            recyclerView.removeAndRecycleViews();
        }
        LayoutPrefetchRegistryImpl layoutPrefetchRegistryImpl = recyclerView.mPrefetchRegistry;
        layoutPrefetchRegistryImpl.collectPrefetchPositionsFromView(recyclerView, true);
        if (layoutPrefetchRegistryImpl.mCount == 0) {
            return;
        }
        try {
            TraceCompat.beginSection("RV Nested Prefetch");
            recyclerView.mState.prepareForNestedPrefetch(recyclerView.mAdapter);
            for (int i = 0; i < layoutPrefetchRegistryImpl.mCount * 2; i += 2) {
                this.prefetchPositionWithDeadline(recyclerView, layoutPrefetchRegistryImpl.mPrefetchArray[i], l);
            }
            return;
        }
        finally {
            TraceCompat.endSection();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private RecyclerView.ViewHolder prefetchPositionWithDeadline(RecyclerView recyclerView, int n, long l) {
        if (GapWorker.isPrefetchPositionAttached(recyclerView, n)) {
            return null;
        }
        RecyclerView.Recycler recycler = recyclerView.mRecycler;
        try {
            recyclerView.onEnterLayoutOrScroll();
            RecyclerView.ViewHolder viewHolder = recycler.tryGetViewHolderForPositionByDeadline(n, false, l);
            if (viewHolder == null) return viewHolder;
            if (viewHolder.isBound() && !viewHolder.isInvalid()) {
                recycler.recycleView(viewHolder.itemView);
                return viewHolder;
            }
            recycler.addViewHolderToRecycledViewPool(viewHolder, false);
            return viewHolder;
        }
        finally {
            recyclerView.onExitLayoutOrScroll(false);
        }
    }

    public void add(RecyclerView recyclerView) {
        this.mRecyclerViews.add(recyclerView);
    }

    void postFromTraversal(RecyclerView recyclerView, int n, int n2) {
        if (recyclerView.isAttachedToWindow() && this.mPostTimeNs == 0L) {
            this.mPostTimeNs = recyclerView.getNanoTime();
            recyclerView.post((Runnable)this);
        }
        recyclerView.mPrefetchRegistry.setPrefetchVector(n, n2);
    }

    void prefetch(long l) {
        this.buildTaskList();
        this.flushTasksWithDeadline(l);
    }

    public void remove(RecyclerView recyclerView) {
        this.mRecyclerViews.remove(recyclerView);
    }

    @Override
    public void run() {
        block10: {
            TraceCompat.beginSection("RV Prefetch");
            boolean bl = this.mRecyclerViews.isEmpty();
            if (!bl) break block10;
            this.mPostTimeNs = 0L;
            TraceCompat.endSection();
            return;
        }
        int n = this.mRecyclerViews.size();
        long l = 0L;
        for (int i = 0; i < n; ++i) {
            RecyclerView recyclerView = this.mRecyclerViews.get(i);
            long l2 = l;
            if (recyclerView.getWindowVisibility() == 0) {
                l2 = Math.max(recyclerView.getDrawingTime(), l);
            }
            l = l2;
            continue;
        }
        if (l == 0L) {
            this.mPostTimeNs = 0L;
            TraceCompat.endSection();
            return;
        }
        try {
            this.prefetch(TimeUnit.MILLISECONDS.toNanos(l) + this.mFrameIntervalNs);
            return;
        }
        catch (Throwable throwable) {
            throw throwable;
        }
        finally {
            this.mPostTimeNs = 0L;
            TraceCompat.endSection();
        }
    }

    static class LayoutPrefetchRegistryImpl
    implements RecyclerView.LayoutManager.LayoutPrefetchRegistry {
        int mCount;
        int[] mPrefetchArray;
        int mPrefetchDx;
        int mPrefetchDy;

        LayoutPrefetchRegistryImpl() {
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void addPosition(int n, int n2) {
            if (n < 0) {
                throw new IllegalArgumentException("Layout positions must be non-negative");
            }
            if (n2 < 0) {
                throw new IllegalArgumentException("Pixel distance must be non-negative");
            }
            int n3 = this.mCount * 2;
            if (this.mPrefetchArray == null) {
                this.mPrefetchArray = new int[4];
                Arrays.fill(this.mPrefetchArray, -1);
            } else if (n3 >= this.mPrefetchArray.length) {
                int[] arrn = this.mPrefetchArray;
                this.mPrefetchArray = new int[n3 * 2];
                System.arraycopy(arrn, 0, this.mPrefetchArray, 0, arrn.length);
            }
            this.mPrefetchArray[n3] = n;
            this.mPrefetchArray[n3 + 1] = n2;
            ++this.mCount;
        }

        void clearPrefetchPositions() {
            if (this.mPrefetchArray != null) {
                Arrays.fill(this.mPrefetchArray, -1);
            }
            this.mCount = 0;
        }

        /*
         * Enabled aggressive block sorting
         */
        void collectPrefetchPositionsFromView(RecyclerView recyclerView, boolean bl) {
            this.mCount = 0;
            if (this.mPrefetchArray != null) {
                Arrays.fill(this.mPrefetchArray, -1);
            }
            RecyclerView.LayoutManager layoutManager = recyclerView.mLayout;
            if (recyclerView.mAdapter != null && layoutManager != null && layoutManager.isItemPrefetchEnabled()) {
                if (bl) {
                    if (!recyclerView.mAdapterHelper.hasPendingUpdates()) {
                        layoutManager.collectInitialPrefetchPositions(recyclerView.mAdapter.getItemCount(), this);
                    }
                } else if (!recyclerView.hasPendingAdapterUpdates()) {
                    layoutManager.collectAdjacentPrefetchPositions(this.mPrefetchDx, this.mPrefetchDy, recyclerView.mState, this);
                }
                if (this.mCount > layoutManager.mPrefetchMaxCountObserved) {
                    layoutManager.mPrefetchMaxCountObserved = this.mCount;
                    layoutManager.mPrefetchMaxObservedInInitialPrefetch = bl;
                    recyclerView.mRecycler.updateViewCacheSize();
                }
            }
        }

        boolean lastPrefetchIncludedPosition(int n) {
            if (this.mPrefetchArray != null) {
                int n2 = this.mCount;
                for (int i = 0; i < n2 * 2; i += 2) {
                    if (this.mPrefetchArray[i] != n) continue;
                    return true;
                }
            }
            return false;
        }

        void setPrefetchVector(int n, int n2) {
            this.mPrefetchDx = n;
            this.mPrefetchDy = n2;
        }
    }

    static class Task {
        public int distanceToItem;
        public boolean immediate;
        public int position;
        public RecyclerView view;
        public int viewVelocity;

        Task() {
        }

        public void clear() {
            this.immediate = false;
            this.viewVelocity = 0;
            this.distanceToItem = 0;
            this.view = null;
            this.position = 0;
        }
    }

}

