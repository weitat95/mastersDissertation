/*
 * Decompiled with CFR 0.147.
 */
package io.realm;

import io.realm.RealmSchema;
import io.realm.internal.ColumnInfo;
import io.realm.internal.Table;
import io.realm.internal.fields.FieldDescriptor;

class SchemaConnector
implements FieldDescriptor.SchemaProxy {
    private final RealmSchema schema;

    public SchemaConnector(RealmSchema realmSchema) {
        this.schema = realmSchema;
    }

    @Override
    public ColumnInfo getColumnInfo(String string2) {
        return this.schema.getColumnInfo(string2);
    }

    @Override
    public long getNativeTablePtr(String string2) {
        return this.schema.getTable(string2).getNativePtr();
    }

    @Override
    public boolean hasCache() {
        return this.schema.haveColumnInfo();
    }
}

