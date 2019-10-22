/*
 * Decompiled with CFR 0.147.
 */
package io.realm;

import io.realm.RealmCollection;
import io.realm.RealmModel;
import java.util.List;

public interface OrderedRealmCollection<E extends RealmModel>
extends RealmCollection<E>,
List<E> {
}

