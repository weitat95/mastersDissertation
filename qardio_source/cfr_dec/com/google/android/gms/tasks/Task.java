/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.tasks;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import java.util.concurrent.Executor;

public abstract class Task<TResult> {
    public Task<TResult> addOnCompleteListener(Executor executor, OnCompleteListener<TResult> onCompleteListener) {
        throw new UnsupportedOperationException("addOnCompleteListener is not implemented");
    }

    public abstract Task<TResult> addOnFailureListener(Executor var1, OnFailureListener var2);

    public abstract Task<TResult> addOnSuccessListener(Executor var1, OnSuccessListener<? super TResult> var2);

    public abstract Exception getException();

    public abstract TResult getResult();

    public abstract boolean isComplete();

    public abstract boolean isSuccessful();
}

