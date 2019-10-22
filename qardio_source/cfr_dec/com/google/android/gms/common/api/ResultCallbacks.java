/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.common.api;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public abstract class ResultCallbacks<R extends Result>
implements ResultCallback<R> {
    public abstract void onFailure(Status var1);

    public abstract void onSuccess(R var1);
}

