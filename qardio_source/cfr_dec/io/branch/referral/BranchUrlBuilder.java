/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package io.branch.referral;

import android.content.Context;
import io.branch.referral.Branch;

abstract class BranchUrlBuilder<T extends BranchUrlBuilder> {
    protected Branch branchReferral_ = Branch.getInstance();
    private final Context context_;
    private boolean defaultToLongUrl_;
    protected int duration_ = 0;
    protected int type_ = 0;

    protected BranchUrlBuilder(Context context) {
        this.context_ = context.getApplicationContext();
        this.defaultToLongUrl_ = true;
    }
}

