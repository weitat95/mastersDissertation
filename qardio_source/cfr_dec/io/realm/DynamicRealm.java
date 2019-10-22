/*
 * Decompiled with CFR 0.147.
 */
package io.realm;

import io.realm.BaseRealm;
import io.realm.RealmCache;
import io.realm.RealmConfiguration;
import io.realm.RealmSchema;

public class DynamicRealm
extends BaseRealm {
    private DynamicRealm(RealmCache realmCache) {
        super(realmCache);
    }

    private DynamicRealm(RealmConfiguration realmConfiguration) {
        super(realmConfiguration);
    }

    static DynamicRealm createInstance(RealmCache realmCache) {
        return new DynamicRealm(realmCache);
    }

    static DynamicRealm createInstance(RealmConfiguration realmConfiguration) {
        return new DynamicRealm(realmConfiguration);
    }
}

