/*
 * Decompiled with CFR 0.147.
 */
package io.realm.internal;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.exceptions.RealmException;
import io.realm.internal.ColumnInfo;
import io.realm.internal.OsObjectSchemaInfo;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Row;
import io.realm.internal.SharedRealm;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class RealmProxyMediator {
    protected static void checkClass(Class<? extends RealmModel> class_) {
        if (class_ == null) {
            throw new NullPointerException("A class extending RealmObject must be provided");
        }
    }

    protected static RealmException getMissingProxyClassException(Class<? extends RealmModel> class_) {
        return new RealmException(class_ + " is not part of the schema for this Realm.");
    }

    public abstract <E extends RealmModel> E copyOrUpdate(Realm var1, E var2, boolean var3, Map<RealmModel, RealmObjectProxy> var4);

    public abstract <E extends RealmModel> E createDetachedCopy(E var1, int var2, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> var3);

    public boolean equals(Object object) {
        if (!(object instanceof RealmProxyMediator)) {
            return false;
        }
        object = (RealmProxyMediator)object;
        return this.getModelClasses().equals(((RealmProxyMediator)object).getModelClasses());
    }

    public abstract Map<Class<? extends RealmModel>, OsObjectSchemaInfo> getExpectedObjectSchemaInfoMap();

    public abstract Set<Class<? extends RealmModel>> getModelClasses();

    public abstract String getTableName(Class<? extends RealmModel> var1);

    public int hashCode() {
        return this.getModelClasses().hashCode();
    }

    public abstract <E extends RealmModel> E newInstance(Class<E> var1, Object var2, Row var3, ColumnInfo var4, boolean var5, List<String> var6);

    public boolean transformerApplied() {
        return false;
    }

    public abstract ColumnInfo validateTable(Class<? extends RealmModel> var1, SharedRealm var2, boolean var3);
}

