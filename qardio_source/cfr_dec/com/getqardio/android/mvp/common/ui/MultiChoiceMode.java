/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Parcelable
 */
package com.getqardio.android.mvp.common.ui;

import android.os.Bundle;
import android.os.Parcelable;
import com.getqardio.android.mvp.common.data_structure.ParcelableSparseBooleanArray;

public class MultiChoiceMode {
    private ParcelableSparseBooleanArray checkStates = new ParcelableSparseBooleanArray();

    public void clearChecks() {
        this.checkStates.clear();
    }

    public int getCheckedCount() {
        return this.checkStates.size();
    }

    public boolean isChecked(int n) {
        return this.checkStates.get(n, false);
    }

    public void onRestoreInstanceState(Bundle bundle) {
        if (bundle != null) {
            this.checkStates = (ParcelableSparseBooleanArray)bundle.getParcelable("checkStates");
        }
    }

    public void onSaveInstanceState(Bundle bundle) {
        bundle.putParcelable("checkStates", (Parcelable)this.checkStates);
    }

    public void setChecked(int n, boolean bl) {
        if (bl) {
            this.checkStates.put(n, true);
            return;
        }
        this.checkStates.delete(n);
    }
}

