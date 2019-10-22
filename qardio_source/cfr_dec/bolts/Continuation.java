/*
 * Decompiled with CFR 0.147.
 */
package bolts;

import bolts.Task;

public interface Continuation<TTaskResult, TContinuationResult> {
    public TContinuationResult then(Task<TTaskResult> var1) throws Exception;
}

