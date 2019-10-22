/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.util.SparseArray
 *  android.view.View
 */
package android.support.transition;

import android.support.transition.TransitionValues;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.LongSparseArray;
import android.util.SparseArray;
import android.view.View;

class TransitionValuesMaps {
    final SparseArray<View> mIdValues;
    final LongSparseArray<View> mItemIdValues;
    final ArrayMap<String, View> mNameValues;
    final ArrayMap<View, TransitionValues> mViewValues = new ArrayMap();

    TransitionValuesMaps() {
        this.mIdValues = new SparseArray();
        this.mItemIdValues = new LongSparseArray();
        this.mNameValues = new ArrayMap();
    }
}

