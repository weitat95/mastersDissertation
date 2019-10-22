/*
 * Decompiled with CFR 0.147.
 */
package io.realm;

import io.realm.BaseRealm;
import io.realm.RealmConfiguration;
import io.realm.RealmModel;
import io.realm.RealmObjectSchema;
import io.realm.internal.ColumnIndices;
import io.realm.internal.ColumnInfo;
import io.realm.internal.RealmProxyMediator;
import io.realm.internal.SharedRealm;
import io.realm.internal.Table;
import io.realm.internal.Util;
import io.realm.internal.util.Pair;
import java.util.HashMap;
import java.util.Map;

public class RealmSchema {
    private final Map<Class<? extends RealmModel>, RealmObjectSchema> classToSchema;
    private final Map<Class<? extends RealmModel>, Table> classToTable;
    private ColumnIndices columnIndices;
    private final Map<String, RealmObjectSchema> dynamicClassToSchema;
    private final Map<String, Table> dynamicClassToTable = new HashMap<String, Table>();
    private final BaseRealm realm;

    RealmSchema(BaseRealm baseRealm) {
        this.classToTable = new HashMap<Class<? extends RealmModel>, Table>();
        this.classToSchema = new HashMap<Class<? extends RealmModel>, RealmObjectSchema>();
        this.dynamicClassToSchema = new HashMap<String, RealmObjectSchema>();
        this.realm = baseRealm;
    }

    private void checkIndices() {
        if (!this.haveColumnInfo()) {
            throw new IllegalStateException("Attempt to use column index before set.");
        }
    }

    @Deprecated
    public void close() {
    }

    final ColumnInfo getColumnInfo(Class<? extends RealmModel> class_) {
        this.checkIndices();
        return this.columnIndices.getColumnInfo(class_);
    }

    protected final ColumnInfo getColumnInfo(String string2) {
        this.checkIndices();
        return this.columnIndices.getColumnInfo(string2);
    }

    final ColumnIndices getImmutableColumnIndicies() {
        this.checkIndices();
        return new ColumnIndices(this.columnIndices, false);
    }

    RealmObjectSchema getSchemaForClass(Class<? extends RealmModel> class_) {
        Object object = this.classToSchema.get(class_);
        if (object != null) {
            return object;
        }
        Class<? extends RealmModel> class_2 = Util.getOriginalModelClass(class_);
        if (this.isProxyClass(class_2, class_)) {
            object = this.classToSchema.get(class_2);
        }
        RealmObjectSchema realmObjectSchema = object;
        if (object == null) {
            object = this.getTable(class_);
            realmObjectSchema = new RealmObjectSchema(this.realm, this, (Table)object, this.getColumnInfo(class_2));
            this.classToSchema.put(class_2, realmObjectSchema);
        }
        if (this.isProxyClass(class_2, class_)) {
            this.classToSchema.put(class_, realmObjectSchema);
        }
        return realmObjectSchema;
    }

    final long getSchemaVersion() {
        this.checkIndices();
        return this.columnIndices.getSchemaVersion();
    }

    Table getTable(Class<? extends RealmModel> class_) {
        Table table = this.classToTable.get(class_);
        if (table != null) {
            return table;
        }
        Class<? extends RealmModel> class_2 = Util.getOriginalModelClass(class_);
        if (this.isProxyClass(class_2, class_)) {
            table = this.classToTable.get(class_2);
        }
        Table table2 = table;
        if (table == null) {
            table2 = this.realm.getSharedRealm().getTable(this.realm.getConfiguration().getSchemaMediator().getTableName(class_2));
            this.classToTable.put(class_2, table2);
        }
        if (this.isProxyClass(class_2, class_)) {
            this.classToTable.put(class_, table2);
        }
        return table2;
    }

    Table getTable(String string2) {
        Table table = this.dynamicClassToTable.get(string2 = Table.getTableNameForClass(string2));
        if (table != null) {
            return table;
        }
        table = this.realm.getSharedRealm().getTable(string2);
        this.dynamicClassToTable.put(string2, table);
        return table;
    }

    final boolean haveColumnInfo() {
        return this.columnIndices != null;
    }

    final boolean isProxyClass(Class<? extends RealmModel> class_, Class<? extends RealmModel> class_2) {
        return class_.equals(class_2);
    }

    final void setInitialColumnIndices(long l, Map<Pair<Class<? extends RealmModel>, String>, ColumnInfo> map) {
        if (this.columnIndices != null) {
            throw new IllegalStateException("An instance of ColumnIndices is already set.");
        }
        this.columnIndices = new ColumnIndices(l, map);
    }

    final void setInitialColumnIndices(ColumnIndices columnIndices) {
        if (this.columnIndices != null) {
            throw new IllegalStateException("An instance of ColumnIndices is already set.");
        }
        this.columnIndices = new ColumnIndices(columnIndices, true);
    }

    void updateColumnIndices(ColumnIndices columnIndices) {
        this.columnIndices.copyFrom(columnIndices);
    }
}

