/*
 * Decompiled with CFR 0.147.
 */
package io.realm;

import io.realm.RealmModel;
import java.util.Collection;

public interface RealmCollection<E extends RealmModel>
extends Collection<E> {
    public boolean isLoaded();
}

