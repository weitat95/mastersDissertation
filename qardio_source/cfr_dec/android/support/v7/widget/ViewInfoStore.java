/*
 * Decompiled with CFR 0.147.
 */
package android.support.v7.widget;

import android.support.v4.util.ArrayMap;
import android.support.v4.util.LongSparseArray;
import android.support.v4.util.Pools;
import android.support.v7.widget.RecyclerView;

class ViewInfoStore {
    final ArrayMap<RecyclerView.ViewHolder, InfoRecord> mLayoutHolderMap = new ArrayMap();
    final LongSparseArray<RecyclerView.ViewHolder> mOldChangedHolders = new LongSparseArray();

    ViewInfoStore() {
    }

    /*
     * Enabled aggressive block sorting
     */
    private RecyclerView.ItemAnimator.ItemHolderInfo popFromLayoutStep(RecyclerView.ViewHolder object, int n) {
        Object object2 = null;
        int n2 = this.mLayoutHolderMap.indexOfKey(object);
        if (n2 < 0) {
            return object2;
        }
        InfoRecord infoRecord = (InfoRecord)this.mLayoutHolderMap.valueAt(n2);
        Object object3 = object2;
        if (infoRecord == null) return object3;
        object3 = object2;
        if ((infoRecord.flags & n) == 0) return object3;
        infoRecord.flags &= ~n;
        if (n == 4) {
            object = infoRecord.preInfo;
        } else {
            if (n != 8) {
                throw new IllegalArgumentException("Must provide flag PRE or POST");
            }
            object = infoRecord.postInfo;
        }
        object3 = object;
        if ((infoRecord.flags & 0xC) != 0) return object3;
        this.mLayoutHolderMap.removeAt(n2);
        InfoRecord.recycle(infoRecord);
        return object;
    }

    void addToAppearedInPreLayoutHolders(RecyclerView.ViewHolder viewHolder, RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo) {
        InfoRecord infoRecord;
        InfoRecord infoRecord2 = infoRecord = (InfoRecord)this.mLayoutHolderMap.get(viewHolder);
        if (infoRecord == null) {
            infoRecord2 = InfoRecord.obtain();
            this.mLayoutHolderMap.put(viewHolder, infoRecord2);
        }
        infoRecord2.flags |= 2;
        infoRecord2.preInfo = itemHolderInfo;
    }

    void addToDisappearedInLayout(RecyclerView.ViewHolder viewHolder) {
        InfoRecord infoRecord;
        InfoRecord infoRecord2 = infoRecord = (InfoRecord)this.mLayoutHolderMap.get(viewHolder);
        if (infoRecord == null) {
            infoRecord2 = InfoRecord.obtain();
            this.mLayoutHolderMap.put(viewHolder, infoRecord2);
        }
        infoRecord2.flags |= 1;
    }

    void addToOldChangeHolders(long l, RecyclerView.ViewHolder viewHolder) {
        this.mOldChangedHolders.put(l, viewHolder);
    }

    void addToPostLayout(RecyclerView.ViewHolder viewHolder, RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo) {
        InfoRecord infoRecord;
        InfoRecord infoRecord2 = infoRecord = (InfoRecord)this.mLayoutHolderMap.get(viewHolder);
        if (infoRecord == null) {
            infoRecord2 = InfoRecord.obtain();
            this.mLayoutHolderMap.put(viewHolder, infoRecord2);
        }
        infoRecord2.postInfo = itemHolderInfo;
        infoRecord2.flags |= 8;
    }

    void addToPreLayout(RecyclerView.ViewHolder viewHolder, RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo) {
        InfoRecord infoRecord;
        InfoRecord infoRecord2 = infoRecord = (InfoRecord)this.mLayoutHolderMap.get(viewHolder);
        if (infoRecord == null) {
            infoRecord2 = InfoRecord.obtain();
            this.mLayoutHolderMap.put(viewHolder, infoRecord2);
        }
        infoRecord2.preInfo = itemHolderInfo;
        infoRecord2.flags |= 4;
    }

    void clear() {
        this.mLayoutHolderMap.clear();
        this.mOldChangedHolders.clear();
    }

    RecyclerView.ViewHolder getFromOldChangeHolders(long l) {
        return this.mOldChangedHolders.get(l);
    }

    boolean isDisappearing(RecyclerView.ViewHolder object) {
        return (object = (InfoRecord)this.mLayoutHolderMap.get(object)) != null && (((InfoRecord)object).flags & 1) != 0;
    }

    boolean isInPreLayout(RecyclerView.ViewHolder object) {
        return (object = (InfoRecord)this.mLayoutHolderMap.get(object)) != null && (((InfoRecord)object).flags & 4) != 0;
    }

    void onDetach() {
        InfoRecord.drainCache();
    }

    public void onViewDetached(RecyclerView.ViewHolder viewHolder) {
        this.removeFromDisappearedInLayout(viewHolder);
    }

    RecyclerView.ItemAnimator.ItemHolderInfo popFromPostLayout(RecyclerView.ViewHolder viewHolder) {
        return this.popFromLayoutStep(viewHolder, 8);
    }

    RecyclerView.ItemAnimator.ItemHolderInfo popFromPreLayout(RecyclerView.ViewHolder viewHolder) {
        return this.popFromLayoutStep(viewHolder, 4);
    }

    /*
     * Enabled aggressive block sorting
     */
    void process(ProcessCallback processCallback) {
        int n = this.mLayoutHolderMap.size() - 1;
        while (n >= 0) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder)this.mLayoutHolderMap.keyAt(n);
            InfoRecord infoRecord = (InfoRecord)this.mLayoutHolderMap.removeAt(n);
            if ((infoRecord.flags & 3) == 3) {
                processCallback.unused(viewHolder);
            } else if ((infoRecord.flags & 1) != 0) {
                if (infoRecord.preInfo == null) {
                    processCallback.unused(viewHolder);
                } else {
                    processCallback.processDisappeared(viewHolder, infoRecord.preInfo, infoRecord.postInfo);
                }
            } else if ((infoRecord.flags & 0xE) == 14) {
                processCallback.processAppeared(viewHolder, infoRecord.preInfo, infoRecord.postInfo);
            } else if ((infoRecord.flags & 0xC) == 12) {
                processCallback.processPersistent(viewHolder, infoRecord.preInfo, infoRecord.postInfo);
            } else if ((infoRecord.flags & 4) != 0) {
                processCallback.processDisappeared(viewHolder, infoRecord.preInfo, null);
            } else if ((infoRecord.flags & 8) != 0) {
                processCallback.processAppeared(viewHolder, infoRecord.preInfo, infoRecord.postInfo);
            } else if ((infoRecord.flags & 2) != 0) {
                // empty if block
            }
            InfoRecord.recycle(infoRecord);
            --n;
        }
        return;
    }

    void removeFromDisappearedInLayout(RecyclerView.ViewHolder object) {
        if ((object = (InfoRecord)this.mLayoutHolderMap.get(object)) == null) {
            return;
        }
        ((InfoRecord)object).flags &= 0xFFFFFFFE;
    }

    void removeViewHolder(RecyclerView.ViewHolder object) {
        int n = this.mOldChangedHolders.size() - 1;
        do {
            block6: {
                block5: {
                    if (n < 0) break block5;
                    if (object != this.mOldChangedHolders.valueAt(n)) break block6;
                    this.mOldChangedHolders.removeAt(n);
                }
                if ((object = (InfoRecord)this.mLayoutHolderMap.remove(object)) != null) {
                    InfoRecord.recycle((InfoRecord)object);
                }
                return;
            }
            --n;
        } while (true);
    }

    static class InfoRecord {
        static Pools.Pool<InfoRecord> sPool = new Pools.SimplePool<InfoRecord>(20);
        int flags;
        RecyclerView.ItemAnimator.ItemHolderInfo postInfo;
        RecyclerView.ItemAnimator.ItemHolderInfo preInfo;

        private InfoRecord() {
        }

        static void drainCache() {
            while (sPool.acquire() != null) {
            }
        }

        static InfoRecord obtain() {
            InfoRecord infoRecord;
            InfoRecord infoRecord2 = infoRecord = sPool.acquire();
            if (infoRecord == null) {
                infoRecord2 = new InfoRecord();
            }
            return infoRecord2;
        }

        static void recycle(InfoRecord infoRecord) {
            infoRecord.flags = 0;
            infoRecord.preInfo = null;
            infoRecord.postInfo = null;
            sPool.release(infoRecord);
        }
    }

    static interface ProcessCallback {
        public void processAppeared(RecyclerView.ViewHolder var1, RecyclerView.ItemAnimator.ItemHolderInfo var2, RecyclerView.ItemAnimator.ItemHolderInfo var3);

        public void processDisappeared(RecyclerView.ViewHolder var1, RecyclerView.ItemAnimator.ItemHolderInfo var2, RecyclerView.ItemAnimator.ItemHolderInfo var3);

        public void processPersistent(RecyclerView.ViewHolder var1, RecyclerView.ItemAnimator.ItemHolderInfo var2, RecyclerView.ItemAnimator.ItemHolderInfo var3);

        public void unused(RecyclerView.ViewHolder var1);
    }

}

