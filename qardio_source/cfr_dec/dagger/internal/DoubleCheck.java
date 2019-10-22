/*
 * Decompiled with CFR 0.147.
 */
package dagger.internal;

import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class DoubleCheck<T>
implements Provider<T> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private static final Object UNINITIALIZED;
    private volatile Object instance = UNINITIALIZED;
    private volatile Provider<T> provider;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !DoubleCheck.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
        UNINITIALIZED = new Object();
    }

    private DoubleCheck(Provider<T> provider) {
        if (!$assertionsDisabled && provider == null) {
            throw new AssertionError();
        }
        this.provider = provider;
    }

    public static <T> Provider<T> provider(Provider<T> provider) {
        Preconditions.checkNotNull(provider);
        if (provider instanceof DoubleCheck) {
            return provider;
        }
        return new DoubleCheck<T>(provider);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public T get() {
        Object object;
        Object object2 = object = this.instance;
        if (object == UNINITIALIZED) {
            synchronized (this) {
                object2 = object = this.instance;
                if (object == UNINITIALIZED) {
                    object2 = this.provider.get();
                    object = this.instance;
                    if (object != UNINITIALIZED && object != object2) {
                        throw new IllegalStateException("Scoped provider was invoked recursively returning different results: " + object + " & " + object2 + ". This is likely due to a circular dependency.");
                    }
                    this.instance = object2;
                    this.provider = null;
                }
            }
        }
        return (T)object2;
    }
}

