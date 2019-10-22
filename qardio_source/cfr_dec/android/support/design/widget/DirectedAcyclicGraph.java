/*
 * Decompiled with CFR 0.147.
 */
package android.support.design.widget;

import android.support.v4.util.Pools;
import android.support.v4.util.SimpleArrayMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

final class DirectedAcyclicGraph<T> {
    private final SimpleArrayMap<T, ArrayList<T>> mGraph;
    private final Pools.Pool<ArrayList<T>> mListPool = new Pools.SimplePool<ArrayList<T>>(10);
    private final ArrayList<T> mSortResult;
    private final HashSet<T> mSortTmpMarked;

    DirectedAcyclicGraph() {
        this.mGraph = new SimpleArrayMap();
        this.mSortResult = new ArrayList();
        this.mSortTmpMarked = new HashSet();
    }

    private void dfs(T t, ArrayList<T> arrayList, HashSet<T> hashSet) {
        if (arrayList.contains(t)) {
            return;
        }
        if (hashSet.contains(t)) {
            throw new RuntimeException("This graph contains cyclic dependencies");
        }
        hashSet.add(t);
        ArrayList<T> arrayList2 = this.mGraph.get(t);
        if (arrayList2 != null) {
            int n = arrayList2.size();
            for (int i = 0; i < n; ++i) {
                this.dfs(arrayList2.get(i), arrayList, hashSet);
            }
        }
        hashSet.remove(t);
        arrayList.add(t);
    }

    private ArrayList<T> getEmptyList() {
        ArrayList<T> arrayList;
        ArrayList<Object> arrayList2 = arrayList = this.mListPool.acquire();
        if (arrayList == null) {
            arrayList2 = new ArrayList();
        }
        return arrayList2;
    }

    private void poolList(ArrayList<T> arrayList) {
        arrayList.clear();
        this.mListPool.release(arrayList);
    }

    void addEdge(T t, T t2) {
        ArrayList<T> arrayList;
        if (!this.mGraph.containsKey(t) || !this.mGraph.containsKey(t2)) {
            throw new IllegalArgumentException("All nodes must be present in the graph before being added as an edge");
        }
        ArrayList<T> arrayList2 = arrayList = this.mGraph.get(t);
        if (arrayList == null) {
            arrayList2 = this.getEmptyList();
            this.mGraph.put(t, arrayList2);
        }
        arrayList2.add(t2);
    }

    void addNode(T t) {
        if (!this.mGraph.containsKey(t)) {
            this.mGraph.put(t, null);
        }
    }

    void clear() {
        int n = this.mGraph.size();
        for (int i = 0; i < n; ++i) {
            ArrayList<T> arrayList = this.mGraph.valueAt(i);
            if (arrayList == null) continue;
            this.poolList(arrayList);
        }
        this.mGraph.clear();
    }

    boolean contains(T t) {
        return this.mGraph.containsKey(t);
    }

    List getIncomingEdges(T t) {
        return this.mGraph.get(t);
    }

    List<T> getOutgoingEdges(T t) {
        ArrayList<T> arrayList = null;
        int n = this.mGraph.size();
        for (int i = 0; i < n; ++i) {
            ArrayList<T> arrayList2 = this.mGraph.valueAt(i);
            ArrayList<T> arrayList3 = arrayList;
            if (arrayList2 != null) {
                arrayList3 = arrayList;
                if (arrayList2.contains(t)) {
                    arrayList3 = arrayList;
                    if (arrayList == null) {
                        arrayList3 = new ArrayList<T>();
                    }
                    arrayList3.add(this.mGraph.keyAt(i));
                }
            }
            arrayList = arrayList3;
        }
        return arrayList;
    }

    ArrayList<T> getSortedList() {
        this.mSortResult.clear();
        this.mSortTmpMarked.clear();
        int n = this.mGraph.size();
        for (int i = 0; i < n; ++i) {
            this.dfs(this.mGraph.keyAt(i), this.mSortResult, this.mSortTmpMarked);
        }
        return this.mSortResult;
    }

    boolean hasOutgoingEdges(T t) {
        int n = this.mGraph.size();
        for (int i = 0; i < n; ++i) {
            ArrayList<T> arrayList = this.mGraph.valueAt(i);
            if (arrayList == null || !arrayList.contains(t)) continue;
            return true;
        }
        return false;
    }
}

