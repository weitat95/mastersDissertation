/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package com.facebook.common.internal;

import com.facebook.common.internal.Preconditions;
import javax.annotation.Nullable;

public final class Throwables {
    public static RuntimeException propagate(Throwable throwable) {
        Throwables.propagateIfPossible(Preconditions.checkNotNull(throwable));
        throw new RuntimeException(throwable);
    }

    public static <X extends Throwable> void propagateIfInstanceOf(@Nullable Throwable throwable, Class<X> class_) throws Throwable {
        if (throwable != null && class_.isInstance(throwable)) {
            throw (Throwable)class_.cast(throwable);
        }
    }

    public static void propagateIfPossible(@Nullable Throwable throwable) {
        Throwables.propagateIfInstanceOf(throwable, Error.class);
        Throwables.propagateIfInstanceOf(throwable, RuntimeException.class);
    }
}

