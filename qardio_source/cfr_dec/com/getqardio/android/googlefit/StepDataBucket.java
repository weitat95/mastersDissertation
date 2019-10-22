/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.googlefit;

import com.getqardio.android.googlefit.DataBucket;

public class StepDataBucket
extends DataBucket {
    private int steps;

    public StepDataBucket(long l, long l2, int n) {
        super(l, l2);
        this.steps = n;
    }

    public int getSteps() {
        return this.steps;
    }
}

