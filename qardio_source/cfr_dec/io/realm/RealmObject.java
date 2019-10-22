/*
 * Decompiled with CFR 0.147.
 */
package io.realm;

import io.realm.BaseRealm;
import io.realm.ProxyState;
import io.realm.RealmModel;
import io.realm.internal.InvalidRow;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Row;
import io.realm.internal.Table;

public abstract class RealmObject
implements RealmModel {
    public static <E extends RealmModel> void deleteFromRealm(E object) {
        if (!(object instanceof RealmObjectProxy)) {
            throw new IllegalArgumentException("Object not managed by Realm, so it cannot be removed.");
        }
        if ((object = (RealmObjectProxy)object).realmGet$proxyState().getRow$realm() == null) {
            throw new IllegalStateException("Object malformed: missing object in Realm. Make sure to instantiate RealmObjects with Realm.createObject()");
        }
        if (object.realmGet$proxyState().getRealm$realm() == null) {
            throw new IllegalStateException("Object malformed: missing Realm. Make sure to instantiate RealmObjects with Realm.createObject()");
        }
        object.realmGet$proxyState().getRealm$realm().checkIfValid();
        Row row = object.realmGet$proxyState().getRow$realm();
        row.getTable().moveLastOver(row.getIndex());
        object.realmGet$proxyState().setRow$realm(InvalidRow.INSTANCE);
    }

    public static <E extends RealmModel> boolean isManaged(E e) {
        return e instanceof RealmObjectProxy;
    }

    public static <E extends RealmModel> boolean isValid(E object) {
        return !(object instanceof RealmObjectProxy) || (object = ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm()) != null && object.isAttached();
    }
}

