/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.util.Log
 */
package com.github.mikephil.charting.data;

import android.annotation.SuppressLint;
import android.util.Log;
import com.github.mikephil.charting.data.Entry;

@SuppressLint(value={"ParcelCreator"})
public class PieEntry
extends Entry {
    private String label;

    public String getLabel() {
        return this.label;
    }

    @Deprecated
    @Override
    public float getX() {
        Log.i((String)"DEPRECATED", (String)"Pie entries do not have x values");
        return super.getX();
    }
}

