/*
 * Decompiled with CFR 0.147.
 */
package android.support.v7.util;

import android.support.v7.util.BatchingListUpdateCallback;
import android.support.v7.util.ListUpdateCallback;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DiffUtil {
    private static final Comparator<Snake> SNAKE_COMPARATOR = new Comparator<Snake>(){

        @Override
        public int compare(Snake snake, Snake snake2) {
            int n;
            int n2 = n = snake.x - snake2.x;
            if (n == 0) {
                n2 = snake.y - snake2.y;
            }
            return n2;
        }
    };

    public static DiffResult calculateDiff(Callback callback) {
        return DiffUtil.calculateDiff(callback, true);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static DiffResult calculateDiff(Callback callback, boolean bl) {
        int n = callback.getOldListSize();
        int n2 = callback.getNewListSize();
        ArrayList<Snake> arrayList = new ArrayList<Snake>();
        ArrayList<Range> arrayList2 = new ArrayList<Range>();
        arrayList2.add(new Range(0, n, 0, n2));
        n = n + n2 + Math.abs(n - n2);
        int[] arrn = new int[n * 2];
        int[] arrn2 = new int[n * 2];
        ArrayList<Range> arrayList3 = new ArrayList<Range>();
        do {
            if (arrayList2.isEmpty()) {
                Collections.sort(arrayList, SNAKE_COMPARATOR);
                return new DiffResult(callback, arrayList, arrn, arrn2, bl);
            }
            Range range = (Range)arrayList2.remove(arrayList2.size() - 1);
            Snake snake = DiffUtil.diffPartial(callback, range.oldListStart, range.oldListEnd, range.newListStart, range.newListEnd, arrn, arrn2, n);
            if (snake != null) {
                if (snake.size > 0) {
                    arrayList.add(snake);
                }
                snake.x += range.oldListStart;
                snake.y += range.newListStart;
                Range range2 = arrayList3.isEmpty() ? new Range() : (Range)arrayList3.remove(arrayList3.size() - 1);
                range2.oldListStart = range.oldListStart;
                range2.newListStart = range.newListStart;
                if (snake.reverse) {
                    range2.oldListEnd = snake.x;
                    range2.newListEnd = snake.y;
                } else if (snake.removal) {
                    range2.oldListEnd = snake.x - 1;
                    range2.newListEnd = snake.y;
                } else {
                    range2.oldListEnd = snake.x;
                    range2.newListEnd = snake.y - 1;
                }
                arrayList2.add(range2);
                if (snake.reverse) {
                    if (snake.removal) {
                        range.oldListStart = snake.x + snake.size + 1;
                        range.newListStart = snake.y + snake.size;
                    } else {
                        range.oldListStart = snake.x + snake.size;
                        range.newListStart = snake.y + snake.size + 1;
                    }
                } else {
                    range.oldListStart = snake.x + snake.size;
                    range.newListStart = snake.y + snake.size;
                }
                arrayList2.add(range);
                continue;
            }
            arrayList3.add(range);
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static Snake diffPartial(Callback object, int n, int n2, int n3, int n4, int[] arrn, int[] arrn2, int n5) {
        int n6 = n2 - n;
        int n7 = n4 - n3;
        if (n2 - n < 1 || n4 - n3 < 1) {
            return null;
        }
        int n8 = n6 - n7;
        int n9 = (n6 + n7 + 1) / 2;
        Arrays.fill(arrn, n5 - n9 - 1, n5 + n9 + 1, 0);
        Arrays.fill(arrn2, n5 - n9 - 1 + n8, n5 + n9 + 1 + n8, n6);
        n4 = n8 % 2 != 0 ? 1 : 0;
        int n10 = 0;
        do {
            int n11;
            int n12;
            boolean bl;
            if (n10 > n9) {
                throw new IllegalStateException("DiffUtil hit an unexpected case while trying to calculate the optimal path. Please make sure your data is not changing during the diff calculation.");
            }
            for (n11 = -n10; n11 <= n10; n11 += 2) {
                if (n11 == -n10 || n11 != n10 && arrn[n5 + n11 - 1] < arrn[n5 + n11 + 1]) {
                    n2 = arrn[n5 + n11 + 1];
                    bl = false;
                } else {
                    n2 = arrn[n5 + n11 - 1] + 1;
                    bl = true;
                }
                for (n12 = n2 - n11; n2 < n6 && n12 < n7 && ((Callback)object).areItemsTheSame(n + n2, n3 + n12); ++n2, ++n12) {
                }
                arrn[n5 + n11] = n2;
                if (n4 == 0 || n11 < n8 - n10 + 1 || n11 > n8 + n10 - 1 || arrn[n5 + n11] < arrn2[n5 + n11]) continue;
                object = new Snake();
                ((Snake)object).x = arrn2[n5 + n11];
                ((Snake)object).y = ((Snake)object).x - n11;
                ((Snake)object).size = arrn[n5 + n11] - arrn2[n5 + n11];
                ((Snake)object).removal = bl;
                ((Snake)object).reverse = false;
                return object;
            }
            for (n11 = -n10; n11 <= n10; n11 += 2) {
                int n13 = n11 + n8;
                if (n13 == n10 + n8 || n13 != -n10 + n8 && arrn2[n5 + n13 - 1] < arrn2[n5 + n13 + 1]) {
                    n2 = arrn2[n5 + n13 - 1];
                    bl = false;
                } else {
                    n2 = arrn2[n5 + n13 + 1] - 1;
                    bl = true;
                }
                for (n12 = n2 - n13; n2 > 0 && n12 > 0 && ((Callback)object).areItemsTheSame(n + n2 - 1, n3 + n12 - 1); --n2, --n12) {
                }
                arrn2[n5 + n13] = n2;
                if (n4 != 0 || n11 + n8 < -n10 || n11 + n8 > n10 || arrn[n5 + n13] < arrn2[n5 + n13]) continue;
                object = new Snake();
                ((Snake)object).x = arrn2[n5 + n13];
                ((Snake)object).y = ((Snake)object).x - n13;
                ((Snake)object).size = arrn[n5 + n13] - arrn2[n5 + n13];
                ((Snake)object).removal = bl;
                ((Snake)object).reverse = true;
                return object;
            }
            ++n10;
        } while (true);
    }

    public static abstract class Callback {
        public abstract boolean areContentsTheSame(int var1, int var2);

        public abstract boolean areItemsTheSame(int var1, int var2);

        public Object getChangePayload(int n, int n2) {
            return null;
        }

        public abstract int getNewListSize();

        public abstract int getOldListSize();
    }

    public static class DiffResult {
        private final Callback mCallback;
        private final boolean mDetectMoves;
        private final int[] mNewItemStatuses;
        private final int mNewListSize;
        private final int[] mOldItemStatuses;
        private final int mOldListSize;
        private final List<Snake> mSnakes;

        DiffResult(Callback callback, List<Snake> list, int[] arrn, int[] arrn2, boolean bl) {
            this.mSnakes = list;
            this.mOldItemStatuses = arrn;
            this.mNewItemStatuses = arrn2;
            Arrays.fill(this.mOldItemStatuses, 0);
            Arrays.fill(this.mNewItemStatuses, 0);
            this.mCallback = callback;
            this.mOldListSize = callback.getOldListSize();
            this.mNewListSize = callback.getNewListSize();
            this.mDetectMoves = bl;
            this.addRootSnake();
            this.findMatchingItems();
        }

        /*
         * Enabled aggressive block sorting
         */
        private void addRootSnake() {
            Snake snake = this.mSnakes.isEmpty() ? null : this.mSnakes.get(0);
            if (snake == null || snake.x != 0 || snake.y != 0) {
                snake = new Snake();
                snake.x = 0;
                snake.y = 0;
                snake.removal = false;
                snake.size = 0;
                snake.reverse = false;
                this.mSnakes.add(0, snake);
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        private void dispatchAdditions(List<PostponedUpdate> list, ListUpdateCallback listUpdateCallback, int n, int n2, int n3) {
            if (!this.mDetectMoves) {
                listUpdateCallback.onInserted(n, n2);
                return;
            }
            --n2;
            while (n2 >= 0) {
                int n4 = this.mNewItemStatuses[n3 + n2] & 0x1F;
                switch (n4) {
                    default: {
                        throw new IllegalStateException("unknown flag for pos " + (n3 + n2) + " " + Long.toBinaryString(n4));
                    }
                    case 0: {
                        listUpdateCallback.onInserted(n, 1);
                        for (PostponedUpdate postponedUpdate : list) {
                            ++postponedUpdate.currentPos;
                        }
                        break;
                    }
                    case 4: 
                    case 8: {
                        int n5 = this.mNewItemStatuses[n3 + n2] >> 5;
                        listUpdateCallback.onMoved(DiffResult.removePostponedUpdate(list, (int)n5, (boolean)true).currentPos, n);
                        if (n4 != 4) break;
                        listUpdateCallback.onChanged(n, 1, this.mCallback.getChangePayload(n5, n3 + n2));
                        break;
                    }
                    case 16: {
                        list.add(new PostponedUpdate(n3 + n2, n, false));
                    }
                }
                --n2;
            }
            return;
        }

        /*
         * Enabled aggressive block sorting
         */
        private void dispatchRemovals(List<PostponedUpdate> list, ListUpdateCallback listUpdateCallback, int n, int n2, int n3) {
            if (!this.mDetectMoves) {
                listUpdateCallback.onRemoved(n, n2);
                return;
            }
            --n2;
            while (n2 >= 0) {
                int n4 = this.mOldItemStatuses[n3 + n2] & 0x1F;
                switch (n4) {
                    default: {
                        throw new IllegalStateException("unknown flag for pos " + (n3 + n2) + " " + Long.toBinaryString(n4));
                    }
                    case 0: {
                        listUpdateCallback.onRemoved(n + n2, 1);
                        for (PostponedUpdate postponedUpdate : list) {
                            --postponedUpdate.currentPos;
                        }
                        break;
                    }
                    case 4: 
                    case 8: {
                        int n5 = this.mOldItemStatuses[n3 + n2] >> 5;
                        PostponedUpdate postponedUpdate = DiffResult.removePostponedUpdate(list, n5, false);
                        listUpdateCallback.onMoved(n + n2, postponedUpdate.currentPos - 1);
                        if (n4 != 4) break;
                        listUpdateCallback.onChanged(postponedUpdate.currentPos - 1, 1, this.mCallback.getChangePayload(n3 + n2, n5));
                        break;
                    }
                    case 16: {
                        list.add(new PostponedUpdate(n3 + n2, n + n2, true));
                    }
                }
                --n2;
            }
            return;
        }

        private void findAddition(int n, int n2, int n3) {
            if (this.mOldItemStatuses[n - 1] != 0) {
                return;
            }
            this.findMatchingItem(n, n2, n3, false);
        }

        /*
         * Enabled aggressive block sorting
         */
        private boolean findMatchingItem(int n, int n2, int n3, boolean bl) {
            int n4;
            int n5;
            int n6;
            if (bl) {
                n4 = n2 - 1;
                n6 = n;
                n5 = n2 - 1;
                n2 = n6;
            } else {
                n4 = n - 1;
                n6 = n - 1;
                n5 = n2;
                n2 = n6;
            }
            while (n3 >= 0) {
                Snake snake = this.mSnakes.get(n3);
                n6 = snake.x;
                int n7 = snake.size;
                int n8 = snake.y;
                int n9 = snake.size;
                if (bl) {
                    --n2;
                    while (n2 >= n6 + n7) {
                        if (this.mCallback.areItemsTheSame(n2, n4)) {
                            n = this.mCallback.areContentsTheSame(n2, n4) ? 8 : 4;
                            this.mNewItemStatuses[n4] = n2 << 5 | 0x10;
                            this.mOldItemStatuses[n2] = n4 << 5 | n;
                            return true;
                        }
                        --n2;
                    }
                } else {
                    for (n2 = n5 - 1; n2 >= n8 + n9; --n2) {
                        if (!this.mCallback.areItemsTheSame(n4, n2)) continue;
                        n3 = this.mCallback.areContentsTheSame(n4, n2) ? 8 : 4;
                        this.mOldItemStatuses[n - 1] = n2 << 5 | 0x10;
                        this.mNewItemStatuses[n2] = n - 1 << 5 | n3;
                        return true;
                    }
                }
                n2 = snake.x;
                n5 = snake.y;
                --n3;
            }
            return false;
        }

        /*
         * Enabled aggressive block sorting
         */
        private void findMatchingItems() {
            int n = this.mOldListSize;
            int n2 = this.mNewListSize;
            int n3 = this.mSnakes.size() - 1;
            while (n3 >= 0) {
                int n4;
                Snake snake = this.mSnakes.get(n3);
                int n5 = snake.x;
                int n6 = snake.size;
                int n7 = snake.y;
                int n8 = snake.size;
                if (this.mDetectMoves) {
                    do {
                        if (n <= n5 + n6) break;
                        this.findAddition(n, n2, n3);
                        --n;
                    } while (true);
                    for (n4 = n2; n4 > n7 + n8; --n4) {
                        this.findRemoval(n, n4, n3);
                    }
                }
                for (n2 = 0; n2 < snake.size; ++n2) {
                    n4 = snake.x + n2;
                    n7 = snake.y + n2;
                    n = this.mCallback.areContentsTheSame(n4, n7) ? 1 : 2;
                    this.mOldItemStatuses[n4] = n7 << 5 | n;
                    this.mNewItemStatuses[n7] = n4 << 5 | n;
                }
                n = snake.x;
                n2 = snake.y;
                --n3;
            }
            return;
        }

        private void findRemoval(int n, int n2, int n3) {
            if (this.mNewItemStatuses[n2 - 1] != 0) {
                return;
            }
            this.findMatchingItem(n, n2, n3, true);
        }

        /*
         * Enabled aggressive block sorting
         */
        private static PostponedUpdate removePostponedUpdate(List<PostponedUpdate> list, int n, boolean bl) {
            int n2;
            PostponedUpdate postponedUpdate;
            block3: {
                n2 = list.size() - 1;
                while (n2 >= 0) {
                    postponedUpdate = list.get(n2);
                    if (postponedUpdate.posInOwnerList != n || postponedUpdate.removal != bl) {
                        --n2;
                        continue;
                    }
                    break block3;
                }
                return null;
            }
            list.remove(n2);
            n = n2;
            do {
                PostponedUpdate postponedUpdate2 = postponedUpdate;
                if (n >= list.size()) return postponedUpdate2;
                postponedUpdate2 = list.get(n);
                int n3 = postponedUpdate2.currentPos;
                n2 = bl ? 1 : -1;
                postponedUpdate2.currentPos = n2 + n3;
                ++n;
            } while (true);
        }

        /*
         * Enabled aggressive block sorting
         */
        public void dispatchUpdatesTo(ListUpdateCallback listUpdateCallback) {
            listUpdateCallback = listUpdateCallback instanceof BatchingListUpdateCallback ? (BatchingListUpdateCallback)listUpdateCallback : new BatchingListUpdateCallback(listUpdateCallback);
            ArrayList<PostponedUpdate> arrayList = new ArrayList<PostponedUpdate>();
            int n = this.mOldListSize;
            int n2 = this.mNewListSize;
            int n3 = this.mSnakes.size() - 1;
            do {
                if (n3 < 0) {
                    ((BatchingListUpdateCallback)listUpdateCallback).dispatchLastEvent();
                    return;
                }
                Snake snake = this.mSnakes.get(n3);
                int n4 = snake.size;
                int n5 = snake.x + n4;
                int n6 = snake.y + n4;
                if (n5 < n) {
                    this.dispatchRemovals(arrayList, listUpdateCallback, n5, n - n5, n5);
                }
                if (n6 < n2) {
                    this.dispatchAdditions(arrayList, listUpdateCallback, n5, n2 - n6, n6);
                }
                for (n2 = n4 - 1; n2 >= 0; --n2) {
                    if ((this.mOldItemStatuses[snake.x + n2] & 0x1F) != 2) continue;
                    ((BatchingListUpdateCallback)listUpdateCallback).onChanged(snake.x + n2, 1, this.mCallback.getChangePayload(snake.x + n2, snake.y + n2));
                }
                n = snake.x;
                n2 = snake.y;
                --n3;
            } while (true);
        }

        public void dispatchUpdatesTo(final RecyclerView.Adapter adapter) {
            this.dispatchUpdatesTo(new ListUpdateCallback(){

                @Override
                public void onChanged(int n, int n2, Object object) {
                    adapter.notifyItemRangeChanged(n, n2, object);
                }

                @Override
                public void onInserted(int n, int n2) {
                    adapter.notifyItemRangeInserted(n, n2);
                }

                @Override
                public void onMoved(int n, int n2) {
                    adapter.notifyItemMoved(n, n2);
                }

                @Override
                public void onRemoved(int n, int n2) {
                    adapter.notifyItemRangeRemoved(n, n2);
                }
            });
        }

    }

    private static class PostponedUpdate {
        int currentPos;
        int posInOwnerList;
        boolean removal;

        public PostponedUpdate(int n, int n2, boolean bl) {
            this.posInOwnerList = n;
            this.currentPos = n2;
            this.removal = bl;
        }
    }

    static class Range {
        int newListEnd;
        int newListStart;
        int oldListEnd;
        int oldListStart;

        public Range() {
        }

        public Range(int n, int n2, int n3, int n4) {
            this.oldListStart = n;
            this.oldListEnd = n2;
            this.newListStart = n3;
            this.newListEnd = n4;
        }
    }

    static class Snake {
        boolean removal;
        boolean reverse;
        int size;
        int x;
        int y;

        Snake() {
        }
    }

}

