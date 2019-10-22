/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.AsyncTask
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package io.branch.referral;

import android.os.AsyncTask;
import android.os.Build;
import java.util.concurrent.Executor;

public abstract class BranchAsyncTask<Params, Progress, Result>
extends AsyncTask<Params, Progress, Result> {
    public AsyncTask<Params, Progress, Result> executeTask(Params ... arrParams) {
        if (Build.VERSION.SDK_INT >= 11) {
            try {
                AsyncTask asyncTask = this.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Object[])arrParams);
                return asyncTask;
            }
            catch (Throwable throwable) {
                return this.execute((Object[])arrParams);
            }
        }
        return this.execute((Object[])arrParams);
    }
}

