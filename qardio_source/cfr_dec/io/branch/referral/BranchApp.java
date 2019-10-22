/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Application
 *  android.content.Context
 */
package io.branch.referral;

import android.app.Application;
import android.content.Context;
import io.branch.referral.Branch;
import io.branch.referral.BranchUtil;

public class BranchApp
extends Application {
    public void onCreate() {
        super.onCreate();
        if (!BranchUtil.isTestModeEnabled((Context)this)) {
            Branch.getInstance((Context)this);
            return;
        }
        Branch.getTestInstance((Context)this);
    }
}

