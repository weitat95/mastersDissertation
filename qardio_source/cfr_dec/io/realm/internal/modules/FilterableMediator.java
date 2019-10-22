/*
 * Decompiled with CFR 0.147.
 */
package io.realm.internal.modules;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.internal.ColumnInfo;
import io.realm.internal.OsObjectSchemaInfo;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.RealmProxyMediator;
import io.realm.internal.Row;
import io.realm.internal.SharedRealm;
import io.realm.internal.Util;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FilterableMediator
extends RealmProxyMediator {
    private final Set<Class<? extends RealmModel>> allowedClasses;
    private final RealmProxyMediator originalMediator;

    public FilterableMediator(RealmProxyMediator object, Collection<Class<? extends RealmModel>> object2) {
        this.originalMediator = object;
        HashSet<Class> hashSet = new HashSet<Class>();
        if (object != null) {
            object = ((RealmProxyMediator)object).getModelClasses();
            object2 = object2.iterator();
            while (object2.hasNext()) {
                Class class_ = (Class)object2.next();
                if (!object.contains(class_)) continue;
                hashSet.add(class_);
            }
        }
        this.allowedClasses = Collections.unmodifiableSet(hashSet);
    }

    private void checkSchemaHasClass(Class<? extends RealmModel> class_) {
        if (!this.allowedClasses.contains(class_)) {
            throw new IllegalArgumentException(class_.getSimpleName() + " is not part of the schema for this Realm");
        }
    }

    @Override
    public <E extends RealmModel> E copyOrUpdate(Realm realm, E e, boolean bl, Map<RealmModel, RealmObjectProxy> map) {
        this.checkSchemaHasClass(Util.getOriginalModelClass(e.getClass()));
        return this.originalMediator.copyOrUpdate(realm, e, bl, map);
    }

    @Override
    public <E extends RealmModel> E createDetachedCopy(E e, int n, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> map) {
        this.checkSchemaHasClass(Util.getOriginalModelClass(e.getClass()));
        return this.originalMediator.createDetachedCopy(e, n, map);
    }

    @Override
    public Map<Class<? extends RealmModel>, OsObjectSchemaInfo> getExpectedObjectSchemaInfoMap() {
        HashMap<Class<? extends RealmModel>, OsObjectSchemaInfo> hashMap = new HashMap<Class<? extends RealmModel>, OsObjectSchemaInfo>();
        for (Map.Entry<Class<? extends RealmModel>, OsObjectSchemaInfo> entry : this.originalMediator.getExpectedObjectSchemaInfoMap().entrySet()) {
            if (!this.allowedClasses.contains(entry.getKey())) continue;
            hashMap.put(entry.getKey(), entry.getValue());
        }
        return hashMap;
    }

    @Override
    public Set<Class<? extends RealmModel>> getModelClasses() {
        return this.allowedClasses;
    }

    @Override
    public String getTableName(Class<? extends RealmModel> class_) {
        this.checkSchemaHasClass(class_);
        return this.originalMediator.getTableName(class_);
    }

    @Override
    public <E extends RealmModel> E newInstance(Class<E> class_, Object object, Row row, ColumnInfo columnInfo, boolean bl, List<String> list) {
        this.checkSchemaHasClass(class_);
        return this.originalMediator.newInstance(class_, object, row, columnInfo, bl, list);
    }

    @Override
    public boolean transformerApplied() {
        if (this.originalMediator == null) {
            return true;
        }
        return this.originalMediator.transformerApplied();
    }

    @Override
    public ColumnInfo validateTable(Class<? extends RealmModel> class_, SharedRealm sharedRealm, boolean bl) {
        this.checkSchemaHasClass(class_);
        return this.originalMediator.validateTable(class_, sharedRealm, bl);
    }
}

