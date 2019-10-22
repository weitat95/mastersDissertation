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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CompositeMediator
extends RealmProxyMediator {
    private final Map<Class<? extends RealmModel>, RealmProxyMediator> mediators;

    public CompositeMediator(RealmProxyMediator ... arrrealmProxyMediator) {
        HashMap<Class<? extends RealmModel>, RealmProxyMediator> hashMap = new HashMap<Class<? extends RealmModel>, RealmProxyMediator>();
        if (arrrealmProxyMediator != null) {
            for (RealmProxyMediator realmProxyMediator : arrrealmProxyMediator) {
                Iterator<Class<? extends RealmModel>> iterator = realmProxyMediator.getModelClasses().iterator();
                while (iterator.hasNext()) {
                    hashMap.put(iterator.next(), realmProxyMediator);
                }
            }
        }
        this.mediators = Collections.unmodifiableMap(hashMap);
    }

    private RealmProxyMediator getMediator(Class<? extends RealmModel> class_) {
        RealmProxyMediator realmProxyMediator = this.mediators.get(class_);
        if (realmProxyMediator == null) {
            throw new IllegalArgumentException(class_.getSimpleName() + " is not part of the schema for this Realm");
        }
        return realmProxyMediator;
    }

    @Override
    public <E extends RealmModel> E copyOrUpdate(Realm realm, E e, boolean bl, Map<RealmModel, RealmObjectProxy> map) {
        return this.getMediator(Util.getOriginalModelClass(e.getClass())).copyOrUpdate(realm, e, bl, map);
    }

    @Override
    public <E extends RealmModel> E createDetachedCopy(E e, int n, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> map) {
        return this.getMediator(Util.getOriginalModelClass(e.getClass())).createDetachedCopy(e, n, map);
    }

    @Override
    public Map<Class<? extends RealmModel>, OsObjectSchemaInfo> getExpectedObjectSchemaInfoMap() {
        HashMap<Class<? extends RealmModel>, OsObjectSchemaInfo> hashMap = new HashMap<Class<? extends RealmModel>, OsObjectSchemaInfo>();
        Iterator<RealmProxyMediator> iterator = this.mediators.values().iterator();
        while (iterator.hasNext()) {
            hashMap.putAll(iterator.next().getExpectedObjectSchemaInfoMap());
        }
        return hashMap;
    }

    @Override
    public Set<Class<? extends RealmModel>> getModelClasses() {
        return this.mediators.keySet();
    }

    @Override
    public String getTableName(Class<? extends RealmModel> class_) {
        return this.getMediator(class_).getTableName(class_);
    }

    @Override
    public <E extends RealmModel> E newInstance(Class<E> class_, Object object, Row row, ColumnInfo columnInfo, boolean bl, List<String> list) {
        return this.getMediator(class_).newInstance(class_, object, row, columnInfo, bl, list);
    }

    @Override
    public boolean transformerApplied() {
        Iterator<Map.Entry<Class<? extends RealmModel>, RealmProxyMediator>> iterator = this.mediators.entrySet().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getValue().transformerApplied()) continue;
            return false;
        }
        return true;
    }

    @Override
    public ColumnInfo validateTable(Class<? extends RealmModel> class_, SharedRealm sharedRealm, boolean bl) {
        return this.getMediator(class_).validateTable(class_, sharedRealm, bl);
    }
}

