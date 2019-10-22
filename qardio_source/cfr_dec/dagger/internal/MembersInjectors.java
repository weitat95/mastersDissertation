/*
 * Decompiled with CFR 0.147.
 */
package dagger.internal;

import dagger.MembersInjector;
import dagger.internal.Preconditions;

public final class MembersInjectors {
    public static <T> T injectMembers(MembersInjector<T> membersInjector, T t) {
        membersInjector.injectMembers(t);
        return t;
    }

    public static <T> MembersInjector<T> noOp() {
        return NoOpMembersInjector.INSTANCE;
    }

    private static enum NoOpMembersInjector implements MembersInjector<Object>
    {
        INSTANCE;


        @Override
        public void injectMembers(Object object) {
            Preconditions.checkNotNull(object);
        }
    }

}

